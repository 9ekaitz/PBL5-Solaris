package eus.solaris.solaris.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPaymentMethodForm {

    private Long id;

    @NotEmpty(message = "{page.profile.field.address.notEmpty}")
    @Size(min = 16, max = 16, message = "{page.profile.field.address.size}")
    private String cardNumber;

    @NotEmpty(message = "{page.profile.field.address.notEmpty}")
    private String cardHolderName;

    @NotEmpty(message = "{page.profile.field.address.notEmpty}")
    private String securityCode;

    private Long expirationYear;

    private Long expirationMonth;

}
