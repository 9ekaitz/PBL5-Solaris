package eus.solaris.solaris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.service.SolarPanelService;

@Controller
@RequestMapping("/solarzone")
public class SolarZoneController {

    @Autowired
    private SolarPanelService solarPanelService;

    public Boolean checkUserHasPanels(User user) {
        return solarPanelService.findByUser(user).size() > 0;
    }

    @PreAuthorize("hasAuthority('PRIVILEGE_VIEW_DATA')")
    @GetMapping
    public String solarZoneHome(Model model) {
        User user = (User) model.getAttribute("user");
        if (checkUserHasPanels(user)) {
            model.addAttribute("panels", solarPanelService.findByUser(user));
            return "page/solarzone_home";
        } else {
            return "error";
        }
    }

    @PreAuthorize("hasAuthority('PRIVILEGE_VIEW_DATA')")
    @GetMapping("/panel")
    public String solarZonePanel(Model model) {
        User user = (User) model.getAttribute("user");
        if (checkUserHasPanels(user)) {
            model.addAttribute("panels", solarPanelService.findByUser(user));
            return "page/solarzone_panel";
        } else {
            return "error";
        }
    }

    @PreAuthorize("hasAuthority('PRIVILEGE_VIEW_DATA')")
    @GetMapping("/economic")
    public String solarZoneEcon(Model model) {
        User user = (User) model.getAttribute("user");
        if (checkUserHasPanels(user)) {
            model.addAttribute("panels", solarPanelService.findByUser(user));
            return "page/solarzone_econ";
        } else {
            return "error";
        }
    }

}
