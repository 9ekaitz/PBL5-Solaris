package eus.solaris.solaris.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController{
    
    @GetMapping("/error")
    public String handleError(HttpServletRequest  request, Model model) {  
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE).toString();
        model.addAttribute("message", message);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
        
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500";
            }
            else if(statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error/403";
            }
        }
        return "page/error";
    }
}