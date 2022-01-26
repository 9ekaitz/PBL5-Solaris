package eus.solaris.solaris.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.maven.artifact.repository.Authentication;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import eus.solaris.solaris.config.SpringWebAuxTestConfig;
import eus.solaris.solaris.domain.Privilege;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.domain.ProductDescription;
import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.service.impl.ProductServiceImpl;
import eus.solaris.solaris.service.impl.RoleServiceImpl;
import eus.solaris.solaris.service.impl.UserServiceImpl;

@Import(SpringWebAuxTestConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest({ AdminController.class})
class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceImpl userServiceImpl;

    @MockBean
    ProductServiceImpl productServiceImpl;

    @MockBean
    RoleServiceImpl roleServiceImpl;

    @MockBean
    ModelMapper modelMapper;

    Authentication authentication;
    User adminUser;
    List<User> users;
    List<Product> products;
    Role ROLE_ADMIN;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void loadAdminUser () {
        Long usernameId = 1L;
        Privilege privilegeUsers = createUserManagementPrivilege();
        Privilege privilegeProducts = createProductManagementPrivilege();

        ROLE_ADMIN = new Role(1L, "ROLE_ADMIN", true, null, "role.admin", Stream
        .of(privilegeUsers, privilegeProducts)
        .collect(Collectors.toSet()), 1);

        adminUser = createUser(usernameId);

        when(userServiceImpl.findById(usernameId)).thenReturn(adminUser);
    }

    @BeforeEach
    void loadUserList() {
        for (Long i = 2L; i < 10; i++) {
            User user = createUser(i);
            users.add(user);
            when(userServiceImpl.findById(i)).thenReturn(user);
        }
        when(userServiceImpl.findManageableUsers()).thenReturn(users);
    }

    @BeforeEach
    void loadProductList() {
        for (Long i = 0L; i < 10; i++) {
            Product product = createProduct(i);
            products.add(product);
            when(productServiceImpl.findById(i)).thenReturn(product);
        }
    }

    @Test
    @WithUserDetails(value = "1L")
    void getUserList() throws Exception {
        mockMvc.perform(get("https://localhost/dashboard/manage-users"))
        .andExpect(status().isOk())
        .andExpect(view().name("page/admin-dashboard/manage-users"))
        .andExpect(model().attribute("users", users));
    }

    @Test
    void getManageUsersListNotLoggedTest() throws Exception {
        mockMvc.perform(get("https://localhost/dashboard/manage-users"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/login"));
    }

    @Test
    @WithUserDetails(value = "1L")
    void getUserListPage() throws Exception {
        mockMvc.perform(get("https://localhost/dashboard/manage-users/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("page/admin-dashboard/manage-users"))
        .andExpect(model().attribute("users", users));
    }

    @Test
    void getManageUsersPageNotLoggedTest() throws Exception {
        mockMvc.perform(get("https://localhost/dashboard/manage-users/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/login"));
    }


    @Test
    @WithUserDetails(value = "1L")
    void getProductList() throws Exception {
        mockMvc.perform(get("https://localhost/dashboard/manage-products"))
        .andExpect(status().isOk())
        .andExpect(view().name("page/admin-dashboard/manage-products"))
        .andExpect(model().attribute("products",productServiceImpl.getPagesFromProductList(products).getPageList()
        ));
    }

    @Test
    void getManageProductsNotLoggedTest() throws Exception {
        mockMvc.perform(get("https://localhost/dashboard/manage-products"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/login"));
    }

    @Test
    @WithUserDetails(value = "1L")
    void getProductListPage() throws Exception {
        mockMvc.perform(get("https://localhost/dashboard/manage-products/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("page/admin-dashboard/manage-products"))
        .andExpect(model().attribute("products",productServiceImpl.getPagesFromProductList(products).getPageList()
        ));
    }

    @Test
    void getManageProductsNotLoggedPageTest() throws Exception {
        mockMvc.perform(get("https://localhost/dashboard/manage-products/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/login"));
    }

    @Test
    void editUserPrivilege() throws Exception {
        mockMvc.perform(get("https://localhost/dashboard/edit-user/2"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/login"));
    }

    @Test
    @WithUserDetails(value = "1L")
    void editUserPrivilegeWithPrivileges() throws Exception {
        mockMvc.perform(get("https://localhost/dashboard/edit-user/3"))
        .andExpect(status().isOk())
        .andExpect(view().name("page/admin-dashboard/manage-users"))
        .andExpect(model().attribute("userToEdit", userServiceImpl.findById(3L)
        ));
    }

    @Test
    void editProductNoPrivileges() throws Exception {
        mockMvc.perform(get("https://localhost/dashboard/edit-product/2"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/login"));
    }

    @Test
    @WithUserDetails(value = "1L")
    void editProductWithPrivileges() throws Exception {
        mockMvc.perform(get("https://localhost/dashboard/edit-product/3"))
        .andExpect(status().isOk())
        .andExpect(view().name("page/admin-dashboard/manage-products"))
        .andExpect(model().attribute("productToEdit", productServiceImpl.findById(3L)
        ));
    }

    private Product createProduct(Long id) {
        Product product = new Product();
        createProductDescriptionList(product);
        return product;
    }

    private void createProductDescriptionList(Product product) {
        new ProductDescription(1L, null, "name"+product.getId(), "subtitle", "description", product, 1);
        new ProductDescription(2L, null, "nombre"+product.getId(), "subtitulo", "descripcion", product, 1);
        new ProductDescription(2L, null, "izena"+product.getId(), "azpititulos", "deskribapena", product, 1);
    }

    private User createUser(Long id) {
        return new User(id, "testuser", "testy@foo", 
        "foo123", "Testy", "Tester", "User", true, 
        null,  null, ROLE_ADMIN, null, null, null, 1);
    }

    private Privilege createProductManagementPrivilege() {
        Privilege privilege = new Privilege();
        privilege.setId(1L);
        privilege.setCode("MANAGE_PRODUCTS");
        privilege.setI18n("manage.products");
        privilege.setEnabled(true);
        privilege.setVersion(1);
        return privilege;
    }

    private Privilege createUserManagementPrivilege() {
        Privilege privilege = new Privilege();
        privilege.setId(2L);
        privilege.setCode("MANAGE_USERS");
        privilege.setI18n("manage.users");
        privilege.setEnabled(true);
        privilege.setVersion(1);
        return privilege;
    }
}
