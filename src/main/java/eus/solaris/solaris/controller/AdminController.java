package eus.solaris.solaris.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eus.solaris.solaris.domain.Brand;
import eus.solaris.solaris.domain.Color;
import eus.solaris.solaris.domain.Material;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.domain.Size;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.PasswordUpdateForm;
import eus.solaris.solaris.form.ProductCreateForm;
import eus.solaris.solaris.form.ProductFilterForm;
import eus.solaris.solaris.form.UserProfileCreateForm;
import eus.solaris.solaris.form.UserProfileUpdateForm;
import eus.solaris.solaris.repository.ImageRepository;
import eus.solaris.solaris.service.ProductService;
import eus.solaris.solaris.service.RoleService;
import eus.solaris.solaris.service.UserService;

@Controller
@RequestMapping("/dashboard")
public class AdminController {

    static final String SUCCESS_ATTRIBUTE = "success";
    static final String ERROR_ATTRIBUTE = "error";
    static final String ERROR_FORM = "errors";

    private static final String ACTUAL_PAGE = "actualPage";
    private static final String TOTAL_PAGES = "totalPages";
    private static final String PRODUCTS_MODEL = "products";
    private static final String USERS = "users";
    private static final String ROLES = "roles";
    private static final String USER_TO_EDIT = "userToEdit";

    private static final String REDIRECT_MANAGE_PRODUCTS = "redirect:/dashboard/manage-products";
    private static final String REDIRECT_MANAGE_USERS = "redirect:/dashboard/manage-users";
    private static final String RETURN_MANAGE_USERS = "page/admin-dashboard/manage-products";
    private static final String CREATE_USER = "page/admin-dashboard/create-user";
    private static final String EDIT_USER = "page/admin-dashboard/edit-user";
    private static final String RETURN_EDIT_PRODUCT = "page/admin-dashboard/edit-product";
    private static final String CREATE_PRODUCT = "page/admin-dashboard/create-product";

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    RoleService roleService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ImageRepository imageRepository;

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @GetMapping("/manage-users")
    public String manageUser(Authentication authentication, Model model) {
        List<User> users = userService.findManageableUsers();
        PagedListHolder<User> pagedListHolder = userService.getPagesFromUsersList(users);
        model.addAttribute(ACTUAL_PAGE, 0);
        model.addAttribute(USERS, pagedListHolder.getPageList());
        model.addAttribute(TOTAL_PAGES, pagedListHolder.getPageCount());
        return "page/admin-dashboard/manage-users";
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @GetMapping("/manage-users/{page}")
    public String manageUserPageHandler(Authentication authentication, @PathVariable(value = "page") int page, Model model) {
        List<User> users = userService.findManageableUsers();
        PagedListHolder<User> pagedListHolder = userService.getPagesFromUsersList(users);
        pagedListHolder.setPage(--page);
        model.addAttribute(ACTUAL_PAGE, page);
        model.addAttribute(USERS, pagedListHolder.getPageList());
        model.addAttribute(TOTAL_PAGES, pagedListHolder.getPageCount());
        return "page/admin-dashboard/manage-users";
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @GetMapping(value = "/edit-user/{id}")
    public String editUser(@PathVariable(value = "id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute(ROLES, roleService.findAll());
        model.addAttribute(USER_TO_EDIT, user);
        return EDIT_USER;
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @PostMapping(value = "/update-user/{id}")
    public String updateUser(@Validated @ModelAttribute UserProfileUpdateForm upuf, BindingResult result, @PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = new ArrayList<>(result.getAllErrors());
            model.addAttribute("errorsUserData", errors);
            User user = userService.findById(id);
            model.addAttribute(USER_TO_EDIT, user);
            model.addAttribute(ROLES, roleService.findAll());
            return EDIT_USER;
        } else {
            User userRes = userService.update(id, upuf);
            addFlashAttribute((userRes != null), redirectAttributes, "alert.userupdate.success", "alert.userupdate.error");
            return REDIRECT_MANAGE_USERS;
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @PostMapping(value = "/update-user-password/{id}")
    public String updateUserPassword(@Validated @ModelAttribute PasswordUpdateForm puf, BindingResult result, @PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = new ArrayList<>(result.getAllErrors());
            model.addAttribute("errorsPassword", errors);
            User user = userService.findById(id);
            model.addAttribute(USER_TO_EDIT, user);
            model.addAttribute(ROLES, roleService.findAll());
            return EDIT_USER;
        } else {
            User userRes = userService.updateUserPassword(id, puf.getNewPassword());
            addFlashAttribute((userRes != null), redirectAttributes, "alert.password.success", "alert.password.error");
            return REDIRECT_MANAGE_USERS;
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @GetMapping(value = "/delete-user/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.findById(id);
        User userRes = userService.disable(user);
        addFlashAttribute((userRes != null), redirectAttributes, "alert.deleteuser.success", "alert.deleteuser.error");
        return REDIRECT_MANAGE_USERS;
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @GetMapping(value = "/create-user")
    public String showCreateUserPage(Model model) {
        model.addAttribute(ROLES, roleService.findAll());
        model.addAttribute("form", new UserProfileCreateForm());
        return CREATE_USER;
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @PostMapping(value = "/create-user")
    public String createUser(@Validated @ModelAttribute UserProfileCreateForm upcf, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<ObjectError> errors = new ArrayList<>(result.getAllErrors());
            model.addAttribute(ERROR_FORM, errors);
            model.addAttribute("form", upcf);
            model.addAttribute(ROLES, roleService.findAll());
            return CREATE_USER;
        } else {
            boolean resultSQL = userService.create(upcf);
            addFlashAttribute(resultSQL, redirectAttributes, "alert.createuser.success", "alert.createuser.error");
            return REDIRECT_MANAGE_USERS;
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @GetMapping(value = "/manage-products")
    public String showManageProductsPage(Model model) {
        setFilters(model);
        List<Product> products = productService.findAll();
        PagedListHolder<Product> pagedListHolder = productService.getPagesFromProductList(products);
        model.addAttribute(ACTUAL_PAGE, 0);
        model.addAttribute(PRODUCTS_MODEL, pagedListHolder.getPageList());
        model.addAttribute(TOTAL_PAGES, pagedListHolder.getPageCount());
        return RETURN_MANAGE_USERS;
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @GetMapping(value = "/manage-products/{page}")
    public String showManageProductsPage(Model model, @PathVariable(value = "page") int page) {
        List<Product> products = productService.findAll();
        setFilters(model);
        PagedListHolder<Product> pagedListHolder = productService.getPagesFromProductList(products);
        pagedListHolder.setPage(--page);
        model.addAttribute(ACTUAL_PAGE, page);
        model.addAttribute(PRODUCTS_MODEL, pagedListHolder.getPageList());
        model.addAttribute(TOTAL_PAGES, pagedListHolder.getPageCount());
        return RETURN_MANAGE_USERS;
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @PostMapping(value = "/manage-products/filter")
    public String filterProducts(@ModelAttribute ProductFilterForm pff, BindingResult result, Model model) {
        setFilters(model);
        Page<Product> products = productService.getFilteredProducts(pff, 0);
        model.addAttribute(ACTUAL_PAGE, 0);
        model.addAttribute(PRODUCTS_MODEL, products.getContent());
        model.addAttribute(TOTAL_PAGES, products.getTotalPages());
        return RETURN_MANAGE_USERS;
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @GetMapping(value = "/edit-product/{id}")
    public String showEditProductForm(@PathVariable(value = "id") Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("productToEdit", product);
        Locale locale = LocaleContextHolder.getLocale();
        model.addAttribute("description", productService.getProductDescription(product, locale));
        setProductFieldsIntoModel(model);
        return RETURN_EDIT_PRODUCT;
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @PostMapping(value = "/edit-product/{id}")
    public String editProduct(@Validated(ProductCreateForm.EditProduct.class) @ModelAttribute ProductCreateForm pcf, BindingResult result, @PathVariable(value = "id") Long id, Model model, RedirectAttributes redirectAttributes) throws IOException {
        Product product = productService.findById(id);
        Locale locale = LocaleContextHolder.getLocale();
        if (result.hasErrors() && product != null) {
            List<ObjectError> errors = new ArrayList<>(result.getAllErrors());
            model.addAttribute(ERROR_FORM, errors);
            model.addAttribute("productToEdit", product);
            model.addAttribute("description", productService.getProductDescription(product, locale));
            setProductFieldsIntoModel(model);
            return RETURN_EDIT_PRODUCT;
        } else {
            Product productRes = productService.update(product, pcf, locale);
            addFlashAttribute((productRes != null), redirectAttributes, "alert.updateproduct.success", "alert.updateproduct.error");
            return REDIRECT_MANAGE_PRODUCTS;
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @GetMapping(value = "/create-product")
    public String editProduct(Model model) {
        model.addAttribute("form", new ProductCreateForm());
        setProductFieldsIntoModel(model);
        return CREATE_PRODUCT;
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @PostMapping(value = "/create-product")
    public String createProduct(@Validated(ProductCreateForm.CreateProduct.class) @ModelAttribute ProductCreateForm pcf, BindingResult result, Model model, RedirectAttributes redirectAttributes) throws IOException {
        if(result.hasErrors()) {
            List<ObjectError> errors = new ArrayList<>(result.getAllErrors());
            model.addAttribute(ERROR_FORM, errors);
            model.addAttribute("form", pcf);
            setProductFieldsIntoModel(model);
            return CREATE_PRODUCT;
        }
        Product productRes = productService.create(pcf);
        addFlashAttribute((productRes != null), redirectAttributes, "alert.createproduct.success", "alert.createproduct.error");
        return REDIRECT_MANAGE_PRODUCTS;
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCTS')")
    @GetMapping(value = "/delete-product/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Product product = productService.findById(id);
        Boolean resultSQL = productService.delete(product);
        addFlashAttribute(resultSQL, redirectAttributes, "alert.deleteproduct.success", "alert.deleteproduct.error");
        return REDIRECT_MANAGE_PRODUCTS;
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

    private void addFlashAttribute(boolean resultSQL, RedirectAttributes redirectAttributes, String successMessage, String errorMessage) {
        if(Boolean.TRUE.equals(resultSQL)){
            redirectAttributes.addFlashAttribute(SUCCESS_ATTRIBUTE, successMessage);
        }
        else{
            redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, errorMessage);
        }
    }
}
