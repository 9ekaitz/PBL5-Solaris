package eus.solaris.solaris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.service.UserService;

@Controller
public class ProfileController {

    @Autowired
	UserService userService;

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        if(authentication != null){
            getUser(model, authentication);
        }
        else{
            return "redirect:/login";
        }

        return "page/profile";
    }

    @GetMapping("/profile/security")
    public String profileSecurity(Model model, Authentication authentication) {
        if(authentication != null){
            getUser(model, authentication);
        }
        else{
            return "redirect:/login";
        }

        return "page/profile_security";
    }

    @GetMapping("/delete-account")
    public String deleteForm(Model model, Authentication authentication) {
        if(authentication != null){
            getUser(model, authentication);
        }
        else{
            return "redirect:/login";
        }

        return "page/delete_account";
    }

    @PostMapping("/delete-account")
    public String deleteAccount(Model model, Authentication authentication) {
        if(authentication != null){
            getUser(model, authentication);
        }
        else{
            return "redirect:/login";
        }

        String name = authentication.getName();
        User user = userService.findByUsername(name);
        userService.deleteUser(user.getId());

        return "redirect:/";
    }
    
    private void getUser(Model model, Authentication authentication) {
        String name = authentication.getName();
        User user = userService.findByUsername(name);
        if (user != null)
            model.addAttribute("user", user);
    }
    
}
