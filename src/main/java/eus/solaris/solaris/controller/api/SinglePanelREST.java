package eus.solaris.solaris.controller.api;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.SolarPanelDataEntry;
import eus.solaris.solaris.dto.SolarPanelDataDTO;
import eus.solaris.solaris.repository.DataEntryRepository;
import eus.solaris.solaris.repository.SolarPanelRepository;
import eus.solaris.solaris.service.multithreading.FormatterJSON;
import eus.solaris.solaris.service.multithreading.Gatherer;
import eus.solaris.solaris.service.multithreading.ThreadController;
import eus.solaris.solaris.service.multithreading.conversions.ConversionType;
import eus.solaris.solaris.service.multithreading.modes.GroupMode;
import eus.solaris.solaris.service.multithreading.modes.Kind;

@RestController("/api/panel")
public class SinglePanelREST {

    private static final Integer THREADS = 12;

    @Autowired
    SolarPanelRepository solarPanelRepository;

    @Autowired
    DataEntryRepository dataEntryRepository;

    @GetMapping(path = "/api/panel/{id}/real-time", produces = "application/json")
    public String realTime(@PathVariable("id") Long id) {
        SolarPanel panel = solarPanelRepository.findById(id).get();
        Instant startOfDay = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant now = Instant.now();
        List<SolarPanelDataEntry> entries = dataEntryRepository.findBySolarPanelAndTimestampBetween(panel, startOfDay,
                now);
        Map<Instant, Double> data = Gatherer.extractData(entries);
        FormatterJSON fj = new FormatterJSON(data);
        fj.setKind(Kind.LINE);
        fj.setLabel("Potencia generada a tiempo real"); // TODO: Localize
        return fj.getJSON().toString();
    }

    @GetMapping(path = "/api/panel/{id}/grouped", produces = "application/json")
    public String grouped(@PathVariable("id") Long id, SolarPanelDataDTO dto) {
        Optional<SolarPanel> panel = solarPanelRepository.findById(id);
        if (panel.isEmpty()) {
            throw new NoSuchElementException("Panel not found");
        }
        SolarPanel p = panel.get();
        ThreadController tc = new ThreadController(6, dto.getStart(), dto.getEnd());
        List<SolarPanel> panels = new ArrayList<>();
        panels.add(p);
        Map<Instant, Double> data = tc.prepareData(panels, dto.getGroupMode(), ConversionType.NONE);
        FormatterJSON fj = new FormatterJSON(data);
        fj.setKind(Kind.BAR);
        fj.setLabel("Potencia generada por panel"); // TODO: Localize
        return fj.getJSON().toString();
    }

    @GetMapping(path = "/api/panel/{id}/general-data", produces = "application/json")
    public String generalData(@PathVariable("id") Long id) {
        Optional<SolarPanel> panel = solarPanelRepository.findById(id);
        if (panel.isEmpty()) {
            throw new NoSuchElementException("Panel not found");
        }
        SolarPanel p = panel.get();

        JSONObject json = new JSONObject();
        json.put("voltage", p.getModel().getVoltage());
        json.put("width", p.getModel().getWidth());
        json.put("height", p.getModel().getHeight());
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

}
