package eus.solaris.solaris.controller.api;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;

import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.SolarPanelDataEntry;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.dto.SolarPanelRequestDTO;
import eus.solaris.solaris.repository.DataEntryRepository;
import eus.solaris.solaris.repository.SolarPanelRepository;
import eus.solaris.solaris.service.UserService;
import eus.solaris.solaris.service.multithreading.FormatterJSON;
import eus.solaris.solaris.service.multithreading.Gatherer;
import eus.solaris.solaris.service.multithreading.ThreadService;
import eus.solaris.solaris.service.multithreading.conversions.ConversionType;
import eus.solaris.solaris.service.multithreading.modes.Kind;
import javassist.tools.web.BadHttpRequest;

@RestController
@RequestMapping("/api/panel")
public class SinglePanelREST implements HandlerInterceptor {

    private static final Integer THREADS = 6;

    @Autowired
    SolarPanelRepository solarPanelRepository;

    @Autowired
    DataEntryRepository dataEntryRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    UserService userService;

    @GetMapping(path = "/real-time", produces = "application/json")
    public String realTime(SolarPanelRequestDTO dto, HttpServletResponse res, HttpServletRequest req)
            throws BadHttpRequest {
        if (!filterRequest(req, res))
            return null;
        Long panelId = dto.getId();
        if (panelId == null) {
            res.setStatus(400);
            throw new BadHttpRequest();
        }
        Optional<SolarPanel> oppanel = solarPanelRepository.findById(panelId);
        if (!oppanel.isPresent()) {
            res.setStatus(404);
            throw new NoSuchElementException();
        }
        SolarPanel panel = oppanel.get();
        Instant startOfDay = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant now = Instant.now();
        List<SolarPanelDataEntry> entries = dataEntryRepository.findBySolarPanelAndTimestampBetween(panel, startOfDay,
                now);
        Map<Instant, Double> data = Gatherer.extractData(entries);
        FormatterJSON fj = new FormatterJSON(data);
        fj.setKind(Kind.LINE);
        Locale locale = LocaleContextHolder.getLocale();
        fj.setLabel(messageSource.getMessage("rest.graph.single.real_time", null, locale));
        return fj.getJSON().toString();
    }

    @GetMapping(path = "/grouped", produces = "application/json")
    public String grouped(SolarPanelRequestDTO dto, HttpServletResponse res, HttpServletRequest req) {
        if (!filterRequest(req, res))
            return null;
        Optional<SolarPanel> panel = solarPanelRepository.findById(dto.getId());
        if (panel.isEmpty()) {
            throw new NoSuchElementException("Panel not found");
        }
        SolarPanel p = panel.get();
        ThreadService tc = new ThreadService(THREADS, dto.getStart(), dto.getEnd());
        List<SolarPanel> panels = new ArrayList<>();
        panels.add(p);
        Map<Instant, Double> data = tc.prepareData(panels, dto.getGroupMode(), ConversionType.NONE);
        FormatterJSON fj = new FormatterJSON(data);
        fj.setKind(Kind.BAR);
        Locale locale = LocaleContextHolder.getLocale();
        fj.setLabel(messageSource.getMessage("rest.graph.single.per_panel", null, locale));
        return fj.getJSON().toString();
    }

    @GetMapping(path = "/general-data", produces = "application/json")
    public String generalData(SolarPanelRequestDTO dto, HttpServletResponse res, HttpServletRequest req) {
        if (!filterRequest(req, res))
            return null;
        Optional<SolarPanel> panel = solarPanelRepository.findById(dto.getId());
        if (panel.isEmpty()) {
            throw new NoSuchElementException("Panel not found");
        }
        SolarPanel p = panel.get();

        JSONObject json = new JSONObject();
        json.put("voltage", p.getModel().getVoltage());
        json.put("width", p.getModel().getSize().getWidth());
        json.put("height", p.getModel().getSize().getHeight());
        json.put("energyThisMonth", thisMonth(p));
        json.put("energyLast30Days", last30Days(p));
        json.put("energyAllTime", allTime(p));

        return json.toString();
    }

    private Double thisMonth(SolarPanel p) {
        Instant startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant now = Instant.now();
        return getData(p, startOfMonth, now);
    }

    private Double last30Days(SolarPanel p) {
        Instant start = LocalDate.now().minusDays(30).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant now = Instant.now();
        return getData(p, start, now);
    }

    private Double allTime(SolarPanel panel) {
        Instant startAllTime = LocalDate.of(2019, 1, 1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant now = Instant.now();
        return getData(panel, startAllTime, now);
    }

    private Double getData(SolarPanel panel, Instant start, Instant end) {
        return dataEntryRepository.sumBySolarPanelAndTimestampBetween(panel, start, end);
    }

    public boolean filterRequest(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        Optional<SolarPanel> panel = Optional.empty();
        User user = userService.findByUsername(authentication.getName());

        try {
            Long panelid = Long.valueOf(request.getParameter("id"));

            panel = solarPanelRepository.findById(panelid);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;

        }

        if (!panel.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }

        if (!panel.get().getUser().equals(user)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        return true;
    }

}
