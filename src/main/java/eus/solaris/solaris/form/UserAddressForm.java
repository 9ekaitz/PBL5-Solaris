package eus.solaris.solaris.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressForm {

    private Long id;

    @NotEmpty(message = "{page.profile.field.address.notEmpty}")
    private String street;

    private Long countryId;

    @NotEmpty(message = "{page.profile.field.city.notEmpty}")
    private String city;

    @NotEmpty(message = "{page.profile.field.postalcode.notEmpty}")
    @Size(min = 5, max = 5, message = "{page.profile.field.postalcode.size}")
    private String postcode;

    private Long provinceId;

    @NotEmpty(message = "{page.profile.field.number.notEmpty}")
    private String number;

}
