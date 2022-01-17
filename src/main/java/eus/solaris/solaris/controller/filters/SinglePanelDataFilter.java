package eus.solaris.solaris.controller.filters;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import eus.solaris.solaris.domain.SolarPanel;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.SolarPanelRepository;
import eus.solaris.solaris.service.UserService;
import eus.solaris.solaris.util.SpringContextUtil;

public class SinglePanelDataFilter implements HandlerInterceptor {

    private UserService userService;
    private SolarPanelRepository solarPanelRepository;

    public SinglePanelDataFilter() {
        this.userService = SpringContextUtil.getBean(UserService.class);
        ;
        this.solarPanelRepository = SpringContextUtil.getBean(SolarPanelRepository.class);
        ;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            response.setStatus(401);
            return false;
        }

        Optional<SolarPanel> panel = Optional.empty();
        User user = userService.findByUsername(authentication.getName());

        try {
            Long panelid = Long.valueOf(request.getParameter("id"));

            panel = solarPanelRepository.findById(panelid);

        } catch (Exception e) {
            response.setStatus(400);
        }

        if (!panel.isPresent()) {
            response.setStatus(404);
            return false;
        }

        if (!panel.get().getUser().equals(user)) {
            response.setStatus(403);
            return false;
        }

        return true;
    }
}
