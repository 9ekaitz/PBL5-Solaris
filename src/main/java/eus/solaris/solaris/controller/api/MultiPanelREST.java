package eus.solaris.solaris.controller.api;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.TreeMap;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.SolarPanelDataEntry;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.dto.SolarPanelRequestDTO;
import eus.solaris.solaris.repository.DataEntryRepository;
import eus.solaris.solaris.repository.SolarPanelRepository;
import eus.solaris.solaris.repository.UserRepository;
import eus.solaris.solaris.service.multithreading.FormatterJSON;
import eus.solaris.solaris.service.multithreading.Gatherer;
import eus.solaris.solaris.service.multithreading.Processer;
import eus.solaris.solaris.service.multithreading.ThreadService;
import eus.solaris.solaris.service.multithreading.modes.Kind;

@RestController
@RequestMapping("/api/user-panel")
public class MultiPanelREST {

    private static final Integer THREADS = 12;
    private static final String ERROR_USER_NOT_FOUND = "User not found";

    @Autowired
    SolarPanelRepository solarPanelRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DataEntryRepository dataEntryRepository;

    @GetMapping(path = "/real-time", produces = "application/json")
    public String realTimeUser(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            SolarPanelRequestDTO dto) {

        if (!filterRequest(request, response, chain))
            return null;

        Optional<User> user = userRepository.findById(dto.getId());
        if (user.isEmpty()) {
            throw new NoSuchElementException(ERROR_USER_NOT_FOUND);
        }
        List<SolarPanel> panels = solarPanelRepository.findByUser(user.get());
        Instant startOfDay = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant now = Instant.now();
        List<SolarPanelDataEntry> entries = new ArrayList<>();
        for (SolarPanel panel : panels) {
            entries.addAll(dataEntryRepository.findBySolarPanelAndTimestampBetween(panel, startOfDay, now));
        }
        Map<Instant, Double> data = Gatherer.extractData(entries);
        data = Processer.groupPanels(data);

        FormatterJSON fj = new FormatterJSON(data);
        fj.setKind(Kind.LINE);
        fj.setLabel("Potencia generada a tiempo real"); // TODO: Localize
        return fj.getJSON().toString();
    }

    @GetMapping(path = "/grouped", produces = "application/json")
    public String grouped(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            SolarPanelRequestDTO dto) {
        if (!filterRequest(request, response, chain))
            return null;
        Optional<User> user = userRepository.findById(dto.getId());
        if (user.isEmpty()) {
            throw new NoSuchElementException(ERROR_USER_NOT_FOUND);
        }
        List<SolarPanel> panels = solarPanelRepository.findByUser(user.get());
        ThreadService tc = new ThreadService(THREADS, dto.getStart(), dto.getEnd());
        Map<Instant, Double> data = tc.prepareData(panels, dto.getGroupMode(), dto.getConversionType());
        FormatterJSON fj = new FormatterJSON(data);
        fj.setKind(Kind.BAR);
        fj.setLabel("Total"); // TODO: Localize
        return fj.getJSON().toString();
    }

    @GetMapping(path = "/general-data", produces = "application/json")
    public String generalData(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            SolarPanelRequestDTO dto) {
        if (!filterRequest(request, response, chain))
            return null;
        Optional<User> user = userRepository.findById(dto.getId());
        if (user.isEmpty()) {
            throw new NoSuchElementException(ERROR_USER_NOT_FOUND);
        }
        List<SolarPanel> panels = solarPanelRepository.findByUser(user.get());

        JSONObject json = new JSONObject();
        json.put("voltageList", new JSONObject(countVoltages(panels)));
        json.put("energyThisMonth", thisMonthUser(panels));
        json.put("energyLast30Days", last30DaysUser(panels));
        json.put("energyAllTime", allTimeUser(panels));

        return json.toString();
    }

    private Map<Integer, Integer> countVoltages(List<SolarPanel> panels) {
        Map<Integer, Integer> map = new TreeMap<>();

        for (SolarPanel panel : panels) {
            Integer voltage = panel.getModel().getVoltage().intValue();
            if (map.containsKey(voltage)) {
                map.put(voltage, map.get(voltage) + 1);
            } else {
                map.put(voltage, 1);
            }
        }

        return map;
    }

    private Double thisMonthUser(List<SolarPanel> panels) {
        Instant startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant now = Instant.now();
        return getData(panels, startOfMonth, now);
    }

    private Double last30DaysUser(List<SolarPanel> panels) {
        Instant start = LocalDate.now().minusDays(30).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant now = Instant.now();
        return getData(panels, start, now);
    }

    private Double allTimeUser(List<SolarPanel> panels) {
        Instant startAllTime = LocalDate.of(2019, 1, 1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant now = Instant.now();
        return getData(panels, startAllTime, now);

    }

    private Double getData(List<SolarPanel> panels, Instant start, Instant end) {
        Double val = 0.0;
        for (SolarPanel panel : panels) {
            val += dataEntryRepository.sumBySolarPanelAndTimestampBetween(panel, start, end);
        }
        return val;
    }

    protected Boolean filterRequest(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        User user = userRepository.findByUsername(authentication.getName());

        Long userId = -1L;

        try {
            userId = Long.valueOf(request.getParameter("id"));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        if (!user.getId().equals(userId)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return true;
    }

}
