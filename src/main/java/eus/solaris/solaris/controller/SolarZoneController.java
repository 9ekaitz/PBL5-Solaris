package eus.solaris.solaris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.service.SolarPanelService;

@Controller
@RequestMapping("/solarzone")
public class SolarZoneController {

    private static final String PANEL = "panels";

    @Autowired
    private SolarPanelService solarPanelService;

    public Boolean checkUserHasPanels(User user) {
        return !solarPanelService.findByUser(user).isEmpty();
    }

    @PreAuthorize("hasAuthority('AUTH_DATA_READ')")
    @GetMapping
    public String solarZoneHome(Model model) {
        User user = (User) model.getAttribute("user");
        if (checkUserHasPanels(user).booleanValue()) {
            model.addAttribute(PANEL, solarPanelService.findByUser(user));
            return "page/solarzone_home";
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User doesn't have solar panels");
        }
    }

    @PreAuthorize("hasAuthority('AUTH_DATA_READ')")
    @GetMapping("/panel")
    public String solarZonePanel(Model model) {
        User user = (User) model.getAttribute("user");
        if (checkUserHasPanels(user).booleanValue()) {
            model.addAttribute(PANEL, solarPanelService.findByUser(user));
            return "page/solarzone_panel";
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User doesn't have solar panels");
        }
    }

    @PreAuthorize("hasAuthority('AUTH_DATA_READ')")
    @GetMapping("/economic")
    public String solarZoneEcon(Model model) {
        User user = (User) model.getAttribute("user");
        if (checkUserHasPanels(user).booleanValue()) {
            model.addAttribute(PANEL, solarPanelService.findByUser(user));
            return "page/solarzone_econ";
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User doesn't have solar panels");
        }
    }

    @PreAuthorize("hasAuthority('AUTH_DATA_READ')")
    @GetMapping("/eco")
    public String solarZoneEco(Model model) {
        User user = (User) model.getAttribute("user");
        if (checkUserHasPanels(user).booleanValue()) {
            model.addAttribute(PANEL, solarPanelService.findByUser(user));
            return "page/solarzone_eco";
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User doesn't have solar panels");
        }
    }

}
