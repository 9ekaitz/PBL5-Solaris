package eus.solaris.solaris.controller;

import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.dto.UserInformationEditForm;
import eus.solaris.solaris.service.UserService;

@Controller
public class ProfileController {

    @Autowired
	UserService userService;

    @Autowired
	PasswordEncoder passwordEncoder;

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

    @PostMapping("/profile/security")
    public String profileSecurity(Model model, Authentication authentication, RedirectAttributes redirectAttributes, String old_password_1, String old_password_2, String new_password) {
        if(authentication != null){
            getUser(model, authentication);
        }
        else{
            return "redirect:/login";
        }

        boolean result = userService.editPassword(new_password, old_password_1, authentication);
        if(result){
            redirectAttributes.addFlashAttribute("success", "alert.password.success");
        }
        else{
            redirectAttributes.addFlashAttribute("error", "alert.password.error");
        }

        return "redirect:/profile";
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
    
    @GetMapping("/profile/edit")
    public String profileEdit(Model model, Authentication authentication) {
        if(authentication != null){
            getUser(model, authentication);
        }
        else{
            return "redirect:/login";
        }

        return "page/profile_edit";
    }

    @PostMapping("/profile/edit")
    public String profileEdit(@Validated @ModelAttribute("user") UserInformationEditForm form, BindingResult result, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        if(authentication != null){
            getUser(model, authentication);
        }
        else{
            return "redirect:/login";
        }

        if(result.hasErrors() && userService.findByUsername(authentication.getName()) != null){
            List<ObjectError> errors = new ArrayList<>(result.getAllErrors());
            model.addAttribute("errors", errors);
            model.addAttribute("form", form);

            return "page/profile_edit";
        }
        else{
            boolean resultSQL = userService.editUser(form.getName(), form.getFirstSurname(), form.getSecondSurname(), form.getEmail(), authentication);
            if(resultSQL){
                redirectAttributes.addFlashAttribute("success", "alert.profile.success");
            }
            else{
                redirectAttributes.addFlashAttribute("error", "alert.profile.error");
            }
            return "redirect:/profile";
        }

    }

    private void getUser(Model model, Authentication authentication) {
        String name = authentication.getName();
        User user = userService.findByUsername(name);
        if (user != null)
            model.addAttribute("user", user);
    }
    
}
