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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.form.UserAddressForm;
import eus.solaris.solaris.form.UserInformationEditForm;
import eus.solaris.solaris.form.UserPasswordModificationForm;
import eus.solaris.solaris.form.UserPaymentMethodForm;
import eus.solaris.solaris.service.AddressService;
import eus.solaris.solaris.service.CountryService;
import eus.solaris.solaris.service.PaymentMethodService;
import eus.solaris.solaris.service.ProvinceService;
import eus.solaris.solaris.service.UserService;

@Controller
@RequestMapping("/profile")
@PreAuthorize("hasAuthority('AUTH_LOGGED_READ')")
public class ProfileController {

    static final String SUCCESS_ATTRIBUTE = "success";
    static final String ERROR_ATTRIBUTE = "error";
    static final String ERROR_FORM = "errors";
    static final String FORM_FORM = "form";
    static final String PROVINCE_ATTRIBUTE = "provinces";
    static final String COUNTRY_ATTRIBUTE = "countries";
    static final String YEAR_ATTRIBUTE = "years";
    static final String MONTH_ATTRIBUTE = "months";

    static final String PAGE_PROFILE_ADDRESS_EDIT = "page/profile_address_edit";
    static final String PAGE_PROFILE_PAYMENT_METHOD_EDIT = "page/profile_payment_method_edit";

    static final String REDIRECT_PROFILE_ADDRESS = "redirect:/profile/address";
    static final String REDIRECT_PROFILE_PAYMENT_METHOD = "redirect:/profile/payment-method";

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

    @Autowired
    PaymentMethodService paymentMethodService;

    @GetMapping("")
    public String profile() {

        return "page/profile";
    }

    @GetMapping("/security")
    public String profileSecurity(Model model) {

        model.addAttribute(FORM_FORM, new UserPasswordModificationForm());

        return "page/profile_security";
    }

    @PostMapping("/security")
    public String profileSecurity(Model model, RedirectAttributes redirectAttributes, UserPasswordModificationForm form) {

        User user = userService.editPassword(form.getVerifyNewPasword(), form.getOldPassword(), (User) model.getAttribute("user"));
        boolean resultSQL = user.getPassword().equals(passwordEncoder.encode(form.getVerifyNewPasword()));
        addFlashAttribute(resultSQL, redirectAttributes, "alert.password.success", "alert.password.error");

        return "redirect:/profile";
    }

    @GetMapping("/delete-account")
    public String deleteForm() {

        return "page/delete_account";
    }

    @PostMapping("/delete-account")
    public String deleteAccount(Model model, RedirectAttributes redirectAttributes) {

        User user = (User) model.getAttribute("user");
        User changedUser = userService.disableUser(user);
        Boolean resultSQL = Boolean.FALSE.equals(changedUser.getEnabled());
        addFlashAttribute(resultSQL, redirectAttributes, "alert.delete.success", "alert.delete.error");

        return "redirect:/logout";
    }
    
    @GetMapping("/edit")
    public String profileEdit() {

        return "page/profile_edit";
    }

    @PostMapping("/edit")
    public String profileEdit(@Validated @ModelAttribute UserInformationEditForm form, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if(result.hasErrors() && (User) model.getAttribute("user") != null){
            List<ObjectError> errors = new ArrayList<>(result.getAllErrors());
            model.addAttribute(ERROR_FORM, errors);
            model.addAttribute(FORM_FORM, form);

            return "page/profile_edit";
        }
        else{
            User user = userService.editUser(form, (User) model.getAttribute("user"));
            boolean resultSQL = user != null;
            addFlashAttribute(resultSQL, redirectAttributes, "alert.profile.success", "alert.profile.error");

            return "redirect:/profile";
        }

    }

    @GetMapping("/address")
    public String profileAddress(Model model, Authentication authentication) {

        model.addAttribute("addresses", userService.getUserAddresses((User)model.getAttribute("user")));

        return "page/profile_address";
    }

    @GetMapping("/address/add")
    public String profileAddressAdd(Model model) {

        addProfileAddressAttributes(model);

        return PAGE_PROFILE_ADDRESS_EDIT;
    }

    @PostMapping("/address/add")
    public String profileAddressAdd(@Validated @ModelAttribute UserAddressForm form, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if(result.hasErrors() && (User)model.getAttribute("user") != null){

            profileAddressFormFailed(model, form, result);

            return PAGE_PROFILE_ADDRESS_EDIT;
        }
        else{
            Address address = new Address();
            getAddressInformation(address, form, model);
            Address addressSQL = addressService.save(address);
            Boolean resultSQL = addressSQL != null;

            addFlashAttribute(resultSQL, redirectAttributes, "alert.address.add.success", "alert.address.add.error");

            return REDIRECT_PROFILE_ADDRESS;
        }

    }

    @GetMapping("/address/edit/{id}")
    public String profileAddressEdit(@PathVariable("id") Long id, Model model) {
        Address address = addressService.findById(id);
        if(address.getUser() != (User) model.getAttribute("user")){
            return REDIRECT_PROFILE_ADDRESS;
        }
        else{
            model.addAttribute("address", address);
            addProfileAddressAttributes(model);

            return PAGE_PROFILE_ADDRESS_EDIT;
        }

    }

    @PostMapping("/address/edit/{id}")
    public String profileAddressEdit(@PathVariable("id") Long id, @Validated @ModelAttribute("address") UserAddressForm form, BindingResult result, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {

        if(result.hasErrors() && (User)model.getAttribute("user") != null){
            profileAddressFormFailed(model, form, result);

            return PAGE_PROFILE_ADDRESS_EDIT;
        }
        else{
            Address address = addressService.findById(id);
            getAddressInformation(address, form, model);
            Address addressSQL = addressService.save(address);
            Boolean resultSQL = addressSQL != null;
            
            addFlashAttribute(resultSQL, redirectAttributes, "alert.address.edit.success", "alert.address.edit.error");

            return REDIRECT_PROFILE_ADDRESS;
        }

    }
    
    @PostMapping("address/edit/set-default/{id}")
    public String profileAddressEditSetDefault(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Address address = addressService.findById(id);
        User user = (User) model.getAttribute("user");

        if(user != null){
            for (Address address2 : user.getAddresses()) {
                address2.setDefaultAddress(address2.getId().equals(address.getId()));
            }
            User userSQL = userService.save(user);
            Boolean resultSQL = userSQL != null;
            addFlashAttribute(resultSQL, redirectAttributes, "alert.address.edit.default.success", "alert.address.edit.default.error");
        }
        else{
            return "redirect:/";
        }
        
        
        return REDIRECT_PROFILE_ADDRESS;
    }

    @PostMapping("/address/delete/{id}")
    public String profileAddressDelete(@PathVariable("id") Long id, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {

        Address address = addressService.findById(id);
        boolean defaultAddress = address.getDefaultAddress();
        Address disabledAddress = addressService.disable(address);

        if(Boolean.TRUE.equals(defaultAddress)){
            List<Address> addresses = userService.getUserAddresses((User)model.getAttribute("user"));
            if(!addresses.isEmpty()){
                Address newDefault = addresses.iterator().next();
                newDefault.setDefaultAddress(true);
                addressService.save(newDefault);
            }
        }

        Boolean resultSQL = disabledAddress != null && Boolean.FALSE.equals(disabledAddress.isEnabled());
        addFlashAttribute(resultSQL, redirectAttributes, "alert.address.delete.success", "alert.address.delete.error");

        return REDIRECT_PROFILE_ADDRESS;
    }
    
    @GetMapping("/payment-method")
    public String profilePaymentMethod(Model model, Authentication authentication) {

        model.addAttribute("paymentMethods", userService.getUserPaymentMethods((User)model.getAttribute("user")));

        return "page/profile_payment_method";
    }

    @GetMapping("/payment-method/add")
    public String profilePaymentMethodAdd(Model model, Authentication authentication) {

        return PAGE_PROFILE_PAYMENT_METHOD_EDIT;
    }
    
    @PostMapping("/payment-method/add")
    public String profilePaymentMethodAdd(@Validated @ModelAttribute UserPaymentMethodForm form, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if(result.hasErrors() && (User) model.getAttribute("user") != null){

            profilePaymentMethodFormFailed(model, form, result);

            return PAGE_PROFILE_PAYMENT_METHOD_EDIT;
        }
        else{
            PaymentMethod paymentMethod = new PaymentMethod();
            getPaymentMethodInformation(paymentMethod, form, model);
            PaymentMethod paymentMethodSQL = paymentMethodService.save(paymentMethod);
            Boolean resultSQL = paymentMethodSQL != null;

            addFlashAttribute(resultSQL, redirectAttributes, "alert.payment_method.add.success", "alert.payment_method.add.error");

            return REDIRECT_PROFILE_PAYMENT_METHOD;
        }

    }
    
    @GetMapping("/payment-method/edit/{id}")
    public String profilePaymentMethodEdit(@PathVariable("id") Long id, Model model, Authentication authentication) {
        PaymentMethod paymentMethod = paymentMethodService.findById(id);
        if(paymentMethod.getUser() != userService.findByUsername(authentication.getName())){
            return REDIRECT_PROFILE_PAYMENT_METHOD;
        }
        else{
            model.addAttribute("paymentMethod", paymentMethod);

            return PAGE_PROFILE_PAYMENT_METHOD_EDIT;
        }
    }
    
    @PostMapping("/payment-method/edit/{id}")
    public String profilePaymentMethodEdit(@PathVariable("id") Long id, @Validated @ModelAttribute UserPaymentMethodForm form, BindingResult result, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {

        if(result.hasErrors() && (User)model.getAttribute("user") != null){

            profilePaymentMethodFormFailed(model, form, result);

            return PAGE_PROFILE_PAYMENT_METHOD_EDIT;
        }
        else{
            PaymentMethod paymentMethod = paymentMethodService.findById(id);
            getPaymentMethodInformation(paymentMethod, form, model);
            PaymentMethod paymentMethodSQL = paymentMethodService.save(paymentMethod);
            Boolean resultSQL = paymentMethodSQL != null;

            addFlashAttribute(resultSQL, redirectAttributes, "alert.payment_method.edit.success", "alert.payment_method.edit.error");

            return REDIRECT_PROFILE_PAYMENT_METHOD;
        }

    }
    
    @PostMapping("/payment-method/set-default/{id}")
    public String profilePaymentMethodSetDefault(@PathVariable("id") Long id, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {

        PaymentMethod paymentMethod = paymentMethodService.findById(id);
        User user = userService.findByUsername(authentication.getName());

        for (PaymentMethod pm : user.getPaymentMethods()) {
            pm.setDefaultMethod(pm.getId().equals(paymentMethod.getId()));
        }

        User userSQL = userService.save(user);
        Boolean resultSQL = userSQL != null;
        addFlashAttribute(resultSQL, redirectAttributes, "alert.payment_method.edit.default.success", "alert.payment_method.edit.default.error");

        return REDIRECT_PROFILE_PAYMENT_METHOD;
    }
    
    @PostMapping("/payment-method/delete/{id}")
    public String profilePaymentMethodDelete(@PathVariable("id") Long id, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {

        PaymentMethod paymentMethod = paymentMethodService.findById(id);
        Boolean defaultPaymentMethod = paymentMethod.getDefaultMethod();
        PaymentMethod disabledMethod =  paymentMethodService.disable(paymentMethod);

        if(Boolean.TRUE.equals(defaultPaymentMethod)){
            List<PaymentMethod> paymentMethods = userService.getUserPaymentMethods((User)model.getAttribute("user"));
            if(!paymentMethods.isEmpty()){
                PaymentMethod newDefault = paymentMethods.iterator().next();
                newDefault.setDefaultMethod(true);
                paymentMethodService.save(newDefault);
            }
        }

        Boolean resultSQL = disabledMethod != null && Boolean.FALSE.equals(disabledMethod.isEnabled());
        addFlashAttribute(resultSQL, redirectAttributes, "alert.payment_method.delete.success", "alert.payment_method.delete.error");

        return REDIRECT_PROFILE_PAYMENT_METHOD;
    }
    
    private void getPaymentMethodInformation(PaymentMethod paymentMethod, UserPaymentMethodForm form, Model model) {
        if(userService.getUserPaymentMethods((User)model.getAttribute("user")).isEmpty()){
            paymentMethod.setDefaultMethod(true);
        }
        paymentMethod.setUser((User)model.getAttribute("user"));
        paymentMethod.setCardNumber(form.getCardNumber());
        paymentMethod.setCardHolderName(form.getCardHolderName());
        paymentMethod.setExpirationMonth(form.getExpirationMonth());
        paymentMethod.setExpirationYear(form.getExpirationYear());
        paymentMethod.setSecurityCode(form.getSecurityCode());
    }

    private void getAddressInformation(Address address, UserAddressForm form, Model model) {
        if(userService.getUserAddresses((User)model.getAttribute("user")).isEmpty()){
            address.setDefaultAddress(true);
        }
        address.setStreet(form.getStreet());
        address.setCity(form.getCity());
        address.setCountry(countryService.findById(form.getCountryId()));
        address.setProvince(provinceService.findById(form.getProvinceId()));
        address.setPostcode(form.getPostcode());
        address.setNumber(form.getNumber());
        address.setUser((User) model.getAttribute("user"));
    }
    
    private void profileAddressFormFailed(Model model, UserAddressForm form, BindingResult result) {
        List<ObjectError> errors = new ArrayList<>(result.getAllErrors());
        model.addAttribute(ERROR_FORM, errors);
        model.addAttribute(FORM_FORM, form);
        addProfileAddressAttributes(model);
    }
    
    private void profilePaymentMethodFormFailed(Model model, UserPaymentMethodForm form, BindingResult result){
        List<ObjectError> errors = new ArrayList<>(result.getAllErrors());
        model.addAttribute(ERROR_FORM, errors);
        model.addAttribute(FORM_FORM, form);
    }
   
    private void addFlashAttribute(boolean resultSQL, RedirectAttributes redirectAttributes, String successMessage, String errorMessage) {
        if(Boolean.TRUE.equals(resultSQL)){
            redirectAttributes.addFlashAttribute(SUCCESS_ATTRIBUTE, successMessage);
        }
        else{
            redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, errorMessage);
        }
    }

    private void addProfileAddressAttributes(Model model) {
        model.addAttribute(PROVINCE_ATTRIBUTE, provinceService.findAll());
        model.addAttribute(COUNTRY_ATTRIBUTE, countryService.findAll());
    }
}
