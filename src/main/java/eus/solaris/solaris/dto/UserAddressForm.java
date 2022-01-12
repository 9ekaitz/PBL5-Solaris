package eus.solaris.solaris.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressForm {

    @NotEmpty(message = "{page.profile.field.address.notEmpty}")
    private String address;

    private Long countryId;

    @NotEmpty(message = "{page.profile.field.city.notEmpty}")
    private String city;

    @NotEmpty(message = "{page.profile.field.postalcode.notEmpty}")
    @Size(min = 5, max = 5, message = "{page.profile.field.postalcode.size}")
    private String postcode;

    private Long provinceId;

    @NotEmpty(message = "{page.profile.field.number.notEmpty}")
    // @Pattern(regexp="(^$|[0-9]{10})", message = "{page.profile.field.number.valid}")
    private String number;

}
