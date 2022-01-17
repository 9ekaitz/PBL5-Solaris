package eus.solaris.solaris.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import eus.solaris.solaris.service.multithreading.conversions.ConversionType;
import eus.solaris.solaris.service.multithreading.modes.GroupMode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolarPanelDataDTO {

    private Long id;
    private GroupMode groupMode;
    private ConversionType conversionType;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate start;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate end;

}
