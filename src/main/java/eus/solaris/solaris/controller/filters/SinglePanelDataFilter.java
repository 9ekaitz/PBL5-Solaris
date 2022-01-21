package eus.solaris.solaris.controller.filters;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.SolarPanelRepository;
import eus.solaris.solaris.service.UserService;

public class SinglePanelDataFilter extends HttpFilter {

    private static final long serialVersionUID = -5465116158907781458L;

    @Autowired
    private UserService userService;

    @Autowired
    private SolarPanelRepository solarPanelRepository;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain handler)
            throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Optional<SolarPanel> panel = Optional.empty();
        User user = userService.findByUsername(authentication.getName());

        try {
            Long panelid = Long.valueOf(request.getParameter("id"));

            panel = solarPanelRepository.findById(panelid);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        if (!panel.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (!panel.get().getUser().equals(user)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        return;
    }
}
