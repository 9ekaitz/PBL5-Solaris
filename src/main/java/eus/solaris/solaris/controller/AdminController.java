package eus.solaris.solaris.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.PasswordUpdateForm;
import eus.solaris.solaris.form.UserProfileForm;
import eus.solaris.solaris.service.RoleService;
import eus.solaris.solaris.service.UserService;

@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    ModelMapper modelMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dashboard")
    public String showInstallerPage(Authentication authentication, Model model) {
        model.addAttribute("page_title", "GENERAL");
        List<User> users = userService.findAll();
        //TODO: filtrar usuarios por rol, para eliminar usuarios normales
        model.addAttribute("users", users);
        return "page/admin-dashboard/manage-users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/dashboard/edit-user/{id}")
    public String editUser(@PathVariable(value = "id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("userToEdit", user);
        // UserProfileForm upf = new UserProfileForm();
        // upf.setId(user.getId());
        // upf.setUsername(user.getUsername());
        // upf.setEmail(user.getEmail());
        // upf.setFirstSurname(user.getFirstSurname());
        // upf.setSecondUsername(user.getSecondSurname());
        
        // model.addAttribute("form", attributeValue)
        return "page/admin-dashboard/edit-user";

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/dashboard/update-user/{id}")
    public String updateUser(@PathVariable(value = "id") Long id, @ModelAttribute UserProfileForm upf, BindingResult result, Model model) {
        userService.update(id, upf);
        return "redirect:/dashboard";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/dashboard/update-user-password/{id}")
    public String updateUserPassword(@PathVariable(value = "id") Long id, @ModelAttribute PasswordUpdateForm puf, BindingResult result, Model model) {
        String psw1, psw2;
        psw1 = puf.getPassword();
        psw2 = puf.getRepeatPassword();
        if (psw1.equals(psw2)) {
            userService.updateUserPassword(id, psw1);
        } else {
            model.addAttribute("error", "Las contraseñas no coinciden");
            // TODO: mostrar mensaje de error
            return "redirect:/dashboard/edit-user/" + id.toString();
        }
        return "redirect:/dashboard";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/dashboard/delete-user/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id, Model model) {
        User user = userService.findById(id);
        userService.disable(user);
        return "redirect:/dashboard";
    }
}
