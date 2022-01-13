package eus.solaris.solaris.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.dto.UserAddressForm;
import eus.solaris.solaris.dto.UserInformationEditForm;
import eus.solaris.solaris.service.AddressService;
import eus.solaris.solaris.service.CountryService;
import eus.solaris.solaris.service.ProvinceService;
import eus.solaris.solaris.service.UserService;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ProfileController {

    @Autowired
	UserService userService;

    @Autowired
	PasswordEncoder passwordEncoder;

    @Autowired
    CountryService countryService;

    @Autowired
    ProvinceService provinceService;

    @Autowired
    AddressService addressService;

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {

        return "page/profile";
    }

    @GetMapping("/profile/security")
    public String profileSecurity(Model model, Authentication authentication) {

        return "page/profile_security";
    }

    @PostMapping("/profile/security")
    public String profileSecurity(Model model, Authentication authentication, RedirectAttributes redirectAttributes, String old_password_1, String old_password_2, String new_password) {

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

        return "page/delete_account";
    }

    @PostMapping("/delete-account")
    public String deleteAccount(Model model, Authentication authentication) {

        String name = authentication.getName();
        User user = userService.findByUsername(name);
        userService.deleteUser(user.getId());

        return "redirect:/";
    }
    
    @GetMapping("/profile/edit")
    public String profileEdit(Model model, Authentication authentication) {

        return "page/profile_edit";
    }

    @PostMapping("/profile/edit")
    public String profileEdit(@Validated @ModelAttribute("user") UserInformationEditForm form, BindingResult result, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {

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

    @GetMapping("/profile/address")
    public String profileAddress(Model model, Authentication authentication) {

        model.addAttribute("addresses", userService.getUserAddresses(authentication));

        return "page/profile_address";
    }

    @GetMapping("/profile/address/add")
    public String profileAddressAdd(Model model, Authentication authentication) {

        model.addAttribute("provinces", provinceService.findAll());
        model.addAttribute("countries", countryService.findAll());

        return "page/profile_address_edit";
    }

    @PostMapping("/profile/address/add")
    public String profileAddressAdd(@Validated @ModelAttribute UserAddressForm form, BindingResult result, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {

        if(result.hasErrors() && userService.findByUsername(authentication.getName()) != null){
            List<ObjectError> errors = new ArrayList<>(result.getAllErrors());
            model.addAttribute("errors", errors);
            model.addAttribute("form", form);
            model.addAttribute("provinces", provinceService.findAll());
            model.addAttribute("countries", countryService.findAll());

            return "page/profile_address_edit";
        }
        else{
            Address address = new Address();
            User user = userService.findByUsername(authentication.getName());
            getAddressInformation(address, form, user);
            Boolean resultSQL = addressService.save(address);

            if(resultSQL){
                redirectAttributes.addFlashAttribute("success", "alert.address.add.success");
            }
            else{
                redirectAttributes.addFlashAttribute("error", "alert.address.add.error");
            }
            return "redirect:/profile/address";
        }

    }
    
    @GetMapping("/profile/address/edit/{id}")
    public String profileAddressEdit(@PathVariable("id") Long id, Model model, Authentication authentication) {
        Address address = addressService.findById(id);
        model.addAttribute("address", address);
        model.addAttribute("provinces", provinceService.findAll());
        model.addAttribute("countries", countryService.findAll());

        return "page/profile_address_edit";
    }

    @PostMapping("/profile/address/edit/{id}")
    public String profileAddressEdit(@PathVariable("id") Long id, @Validated @ModelAttribute("address") UserAddressForm form, BindingResult result, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {

        if(result.hasErrors() && userService.findByUsername(authentication.getName()) != null){
            List<ObjectError> errors = new ArrayList<>(result.getAllErrors());
            model.addAttribute("errors", errors);
            model.addAttribute("form", form);
            model.addAttribute("provinces", provinceService.findAll());
            model.addAttribute("countries", countryService.findAll());

            return "page/profile_address_edit";
        }
        else{
            Address address = addressService.findById(id);
            User user = userService.findByUsername(authentication.getName());
            getAddressInformation(address, form, user);
            Boolean resultSQL = addressService.save(address);

            if(resultSQL){
                redirectAttributes.addFlashAttribute("success", "alert.address.edit.success");
            }
            else{
                redirectAttributes.addFlashAttribute("error", "alert.address.edit.error");
            }
            return "redirect:/profile/address";
        }

    }
    
    @PostMapping("profile/address/edit/set-default/{id}")
    public String profileAddressEditSetDefault(@PathVariable("id") Long id, Authentication authentication, RedirectAttributes redirectAttributes) {
        Address address = addressService.findById(id);
        User user = userService.findByUsername(authentication.getName());
        
        for (Address address2 : user.getAddresses()) {
            if(address2.getId() == address.getId()){
                address2.setDefaultAddress(true);
            }
            else{
                address2.setDefaultAddress(false);
            }
        }
        userService.save(user);

        redirectAttributes.addFlashAttribute("success", "alert.address.edit.default.success");
        return "redirect:/profile/address";
    }

    @PostMapping("/profile/address/delete/{id}")
    public String profileAddressDelete(@PathVariable("id") Long id, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {

        Address address = addressService.findById(id);
        addressService.delete(address);
        if(address.getDefaultAddress()){
            User user = userService.findByUsername(authentication.getName());
            if(user.getAddresses().size() > 0){
                Address newDefault = user.getAddresses().iterator().next();
                newDefault.setDefaultAddress(true);
                addressService.save(newDefault);
            }
        }
        model.addAttribute("addresses", userService.getUserAddresses(authentication));
        redirectAttributes.addFlashAttribute("success", "alert.address.delete.success");

        return "redirect:/profile/address";
    }
    
    @GetMapping("/profile/payment-method")
    public String profilePaymentMethod(Model model, Authentication authentication) {

        model.addAttribute("paymentMethods", userService.getUserPaymentMethods(authentication));

        return "page/profile_payment_method";
    }

    @GetMapping("/profile/payment-method/add")
    public String profilePaymentMethodAdd(Model model, Authentication authentication) {

        return "page/profile_payment_method_edit";
    }
    
    private void getAddressInformation(Address address, UserAddressForm form, User user) {
        if(user.getAddresses().size() == 0){
            address.setDefaultAddress(true);
        }
        address.setAddress(form.getAddress());
        address.setCity(form.getCity());
        address.setCountry(countryService.findById(form.getCountryId()));
        address.setProvince(provinceService.findById(form.getProvinceId()));
        address.setPostcode(form.getPostcode());
        address.setNumber(form.getNumber());
        address.setUser(user);
    }
    
}
