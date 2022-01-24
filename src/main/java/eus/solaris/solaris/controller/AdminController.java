package eus.solaris.solaris.controller;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.i18n.LocaleContextHolder;
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

import eus.solaris.solaris.domain.Brand;
import eus.solaris.solaris.domain.Color;
import eus.solaris.solaris.domain.Material;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.domain.Size;
import eus.solaris.solaris.domain.SolarPanelModel;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.PasswordUpdateForm;
import eus.solaris.solaris.form.ProductCreateForm;
import eus.solaris.solaris.form.ProductFilterForm;
import eus.solaris.solaris.form.UserProfileCreateForm;
import eus.solaris.solaris.form.UserProfileUpdateForm;
import eus.solaris.solaris.service.ProductService;
import eus.solaris.solaris.service.RoleService;
import eus.solaris.solaris.service.UserService;

@Controller
@RequestMapping("/dashboard")
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    RoleService roleService;

    @Autowired
    ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @GetMapping("/manage-users")
    public String manageUser(Authentication authentication, Model model) {
        model.addAttribute("page_title", "USERS");
        List<User> users = userService.findManageableUsers();
        PagedListHolder<User> pagedListHolder = userService.getPagesFromUsersList(users);
        model.addAttribute("actualPage", 0);
        model.addAttribute("users", pagedListHolder.getPageList());
        model.addAttribute("totalPages", pagedListHolder.getPageCount());
        return "page/admin-dashboard/manage-users";
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
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

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @GetMapping(value = "/edit-user/{id}")
    public String editUser(@PathVariable(value = "id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("userToEdit", user);
        return "page/admin-dashboard/edit-user";
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @PostMapping(value = "/update-user/{id}")
    public String updateUser(@PathVariable(value = "id") Long id, @ModelAttribute UserProfileUpdateForm upuf, BindingResult result, Model model) {
        userService.update(id, upuf);
        return "redirect:/dashboard/manage-users";
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
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

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @GetMapping(value = "/delete-user/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id, Model model) {
        User user = userService.findById(id);
        userService.disable(user);
        return "redirect:/dashboard/manage-users";
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @GetMapping(value = "/create-user")
    public String showCreateUserPage(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "page/admin-dashboard/create-user";
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
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

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @GetMapping(value = "/manage-products")
    public String showManageProductsPage(Model model) {
        model.addAttribute("page_title", "PRODUCTS");
        setFilters(model);
        List<Product> products = productService.findAll();
        PagedListHolder<Product> pagedListHolder = productService.getPagesFromProductList(products);
        model.addAttribute("actualPage", 0);
        model.addAttribute("products", pagedListHolder.getPageList());
        model.addAttribute("totalPages", pagedListHolder.getPageCount());
        return "page/admin-dashboard/manage-products";
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @GetMapping(value = "/manage-products/{page}")
    public String showManageProductsPage(Model model, @PathVariable int page) {
        model.addAttribute("page_title", "PRODUCTS");
        List<Product> products = productService.findAll();
        setFilters(model);
        PagedListHolder<Product> pagedListHolder = productService.getPagesFromProductList(products);
        pagedListHolder.setPage(--page);
        model.addAttribute("actualPage", page);
        model.addAttribute("products", pagedListHolder.getPageList());
        model.addAttribute("totalPages", pagedListHolder.getPageCount());
        return "page/admin-dashboard/manage-products";
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @PostMapping(value = "/manage-products/filter")
    public String filterProducts(@ModelAttribute ProductFilterForm pff, BindingResult result, Model model) {
        model.addAttribute("page_title", "PRODUCTS");
        Set<Product> products = productService.getFilteredProducts(pff);
        setFilters(model);
        PagedListHolder<Product> pagedListHolder = productService.getPagesFromProductList(List.copyOf(products));
        model.addAttribute("actualPage", 0);
        model.addAttribute("products", pagedListHolder.getPageList());
        model.addAttribute("totalPages", pagedListHolder.getPageCount());
        return "page/admin-dashboard/manage-products";
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @GetMapping(value = "/edit-product/{id}")
    public String showEditProductForm(@PathVariable(value = "id") Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("productToEdit", product);
        Locale locale = LocaleContextHolder.getLocale();
        model.addAttribute("description", productService.getProductDescription(product, locale));
        setProductFieldsIntoModel(model);
        return "page/admin-dashboard/edit-product";
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @PostMapping(value = "/edit-product/{id}")
    public String editProduct(@PathVariable(value = "id") Long id, @ModelAttribute ProductCreateForm pcf, Model model) {
        Product product = productService.findById(id);
        Locale locale = LocaleContextHolder.getLocale();
        productService.update(product, pcf, locale);
        return "page/admin-dashboard/manage-products";
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @GetMapping(value = "/create-product")
    public String editProduct(Model model) {
        setProductFieldsIntoModel(model);
        return "page/admin-dashboard/create-product";
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @PostMapping(value = "/create-product")
    public String createProduct(@ModelAttribute ProductCreateForm pcf, BindingResult result, Model model) {
        productService.create(pcf);
        return "redirect:/dashboard/manage-products";
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @GetMapping(value = "/delete-product/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long id, Model model) {
        Product product = productService.findById(id);
        productService.delete(product);
        return "redirect:/dashboard/manage-products";
    }

    public void setProductFieldsIntoModel(Model model) {
        model.addAttribute("brands", productService.getBrands());
        model.addAttribute("materials", productService.getMaterials());
        model.addAttribute("models", productService.getModels());
        model.addAttribute("sizes", productService.getSizes());
        model.addAttribute("colors", productService.getColors());
    }

    public void setFilters(Model model) {
        List<Brand> brands = productService.getBrands();
        model.addAttribute("brands", brands);
        List<Color> colors = productService.getColors();
        model.addAttribute("colors", colors);
        List<Size> sizes = productService.getSizes();
        model.addAttribute("sizes", sizes);
        List<Material> materials = productService.getMaterials();
        model.addAttribute("materials", materials);
    }

    public boolean checkPasswords(String psw1, String psw2) {
        return psw1.equals(psw2) && psw1.length() > 0;
    }
}
