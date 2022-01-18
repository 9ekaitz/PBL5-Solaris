package eus.solaris.solaris.controller.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.service.UserService;
import eus.solaris.solaris.util.SpringContextUtil;

public class MultiPanelDataFilter implements HandlerInterceptor {

    private UserService userService;

    public MultiPanelDataFilter() {
        this.userService = SpringContextUtil.getBean(UserService.class);
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

        User user = userService.findByUsername(authentication.getName());

        Long userId = -1L;

        try {
            userId = Long.valueOf(request.getParameter("id"));
        } catch (Exception e) {
            response.setStatus(400);
            return false;
        }

        if (user.getId().equals(userId)) {
            response.setStatus(403);
            return false;
        }

        return true;
    }
}
