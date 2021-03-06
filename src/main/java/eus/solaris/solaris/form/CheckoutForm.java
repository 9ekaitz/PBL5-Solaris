package eus.solaris.solaris.form;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckoutForm {

  private Long paymentMethodId;

  @NotEmpty(message = "{page.profile.field.number.notEmpty}")
  @Size(min = 16, max = 16, message = "{page.profile.field.cardNumber.size}")
  private String cardNumber;

  @NotEmpty(message = "{page.profile.field.cardHolderName.notEmpty}")
  private String cardHolderName;

  @NotEmpty(message = "{page.profile.field.securityCode.notEmpty}")
  private String securityCode;

  private Long expirationYear;

  private Long expirationMonth;

  private boolean savePaymentMethod;

  private Long addressId;

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

  private boolean saveAddress;

  @NotEmpty(message = "{page.shop.checkout.field.product.notEmpty}")
  List<Long> products;
}
