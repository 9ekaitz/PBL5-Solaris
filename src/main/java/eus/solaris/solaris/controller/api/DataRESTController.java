package eus.solaris.solaris.controller.api;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.SolarPanelDataEntry;
import eus.solaris.solaris.repository.DataEntryRepository;
import eus.solaris.solaris.repository.SolarPanelRepository;
import eus.solaris.solaris.service.multithreading.FormatterJSON;
import eus.solaris.solaris.service.multithreading.Gatherer;
import eus.solaris.solaris.service.multithreading.modes.Kind;

@RestController("/api")
public class DataRESTController {

    static final String NO_SUCH_ELEMENT_ERROR = "{error: \"Bad query, element with id provided not found\"}";

    @Autowired
    SolarPanelRepository solarPanelRepository;

    @Autowired
    DataEntryRepository dataEntryRepository;

    @GetMapping(path = "/api/{id}/real-time", produces = "application/json")
    public String realTime(@PathVariable("id") Long id) {
        SolarPanel panel = solarPanelRepository.findById(id).get();
        Instant startOfDay = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant now = Instant.now();
        List<SolarPanelDataEntry> entries = dataEntryRepository.findByIdAndTimestampBetween(panel, startOfDay, now);
        Map<Instant, Double> data = Gatherer.extractData(entries);
        FormatterJSON fj = new FormatterJSON(data);
        fj.setKind(Kind.LINE);
        fj.setLabel("Potencia generada a tiempo real");
        return fj.getJSON().toString();
    }

    @GetMapping(path = "/api/{id}/grouped?={group}&start={start}&end={end}", produces = "application/json")
    public String grouped(@PathVariable("id") Long id, @PathVariable("group") String group) {
        try {
            SolarPanel panel = solarPanelRepository.findById(id).get();
        } catch (Exception NoSuchElementException) {
            return NO_SUCH_ELEMENT_ERROR;
        }
        return "";
    }

}
