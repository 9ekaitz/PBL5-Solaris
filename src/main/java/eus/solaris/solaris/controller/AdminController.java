package eus.solaris.solaris.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.PasswordUpdateForm;
import eus.solaris.solaris.form.UserProfileCreateForm;
import eus.solaris.solaris.form.UserProfileUpdateForm;
import eus.solaris.solaris.service.RoleService;
import eus.solaris.solaris.service.UserService;

@Controller
@RequestMapping("/dashboard")
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    ModelMapper modelMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/manage-users")
    public String manageUser(Authentication authentication, Model model) {
        model.addAttribute("page_title", "GENERAL");
        List<User> users = userService.findManageableUsers();
        PagedListHolder<User> pagedListHolder = userService.getPagesFromUsersList(users);
        model.addAttribute("users", pagedListHolder.getPageList());
        model.addAttribute("totalPages", pagedListHolder.getPageCount());
        return "page/admin-dashboard/manage-users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/manage-users/{page}")
    public String manageUserPageHandler(Authentication authentication, @PathVariable int page, Model model) {
        List<User> users = userService.findManageableUsers();
        PagedListHolder<User> pagedListHolder = userService.getPagesFromUsersList(users);
        pagedListHolder.setPage(--page);
        model.addAttribute("actualPage", page);
        model.addAttribute("users", pagedListHolder.getPageList());
        model.addAttribute("totalPages", pagedListHolder.getPageCount());
        return "page/admin-dashboard/manage-users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/edit-user/{id}")
    public String editUser(@PathVariable(value = "id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("userToEdit", user);
        return "page/admin-dashboard/edit-user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/update-user/{id}")
    public String updateUser(@PathVariable(value = "id") Long id, @ModelAttribute UserProfileUpdateForm upuf, BindingResult result, Model model) {
        userService.update(id, upuf);
        return "redirect:/dashboard/manage-users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/update-user-password/{id}")
    public String updateUserPassword(@PathVariable(value = "id") Long id, @ModelAttribute PasswordUpdateForm puf, BindingResult result, Model model) {
        if (checkPasswords(puf.getPassword(), puf.getRepeatPassword())) {
            userService.updateUserPassword(id, puf.getPassword());
        } else {
            model.addAttribute("error", "Las contraseñas no coinciden");
            // TODO: mostrar mensaje de error
            return "redirect:/dashboard/edit-user/" + id.toString();
        }
        return "redirect:/dashboard/manage-users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/delete-user/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id, Model model) {
        User user = userService.findById(id);
        userService.disable(user);
        return "redirect:/dashboard/manage-users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/create-user")
    public String showCreateUserPage(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "page/admin-dashboard/create-user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/create-user")
    public String createUser(@ModelAttribute UserProfileCreateForm upcf, BindingResult result, Model model) {
        if (checkPasswords(upcf.getPassword(), upcf.getRepeatPassword())) {
            userService.create(upcf);
        } else {
            model.addAttribute("error", "Las contraseñas no coinciden");
            // TODO: mostrar mensaje de error
            return "/dashboard/create-user";
        }
        return "redirect:/dashboard/manage-users";
    }

    public boolean checkPasswords(String psw1, String psw2) {
        return psw1.equals(psw2) && psw1.length() > 0;
    }
}
