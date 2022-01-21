package eus.solaris.solaris.controller.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.plexus.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.service.UserService;

@Component(role = HttpFilter.class)
@Order(1)
public class MultiPanelDataFilter extends HttpFilter {

    private static final long serialVersionUID = -7558919156063627292L;

    @Autowired
    private UserService userService;

    // public MultiPanelDataFilter() {
    // this.userService = SpringContextUtil.getBean(UserService.class);
    // }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = userService.findByUsername(authentication.getName());

        Long userId = -1L;

        try {
            userId = Long.valueOf(request.getParameter("id"));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (!user.getId().equals(userId)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return;
    }

}
