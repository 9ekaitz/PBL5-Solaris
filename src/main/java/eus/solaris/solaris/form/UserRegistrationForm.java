package eus.solaris.solaris.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import eus.solaris.solaris.validation.ValidFieldsMatch;
import eus.solaris.solaris.validation.ValidPassword;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ValidFieldsMatch(field = "password", fieldMatch = "verifyPassword", message = "page.register.field.verifyPassword.fieldMatch")
public class UserRegistrationForm {

    @NotEmpty(message = "{page.register.field.username.notEmpty}")
    private String username;

    @ValidPassword(message = "{page.register.field.password.valid}")
    private String password;

    private String verifyPassword;

    @NotEmpty(message = "{page.register.field.name.notEmpty}")
    private String name;

    @NotEmpty(message = "{page.register.field.firstSurname.notEmpty}")
    private String firstSurname;

    @NotEmpty(message = "{page.register.field.secondSurname.notEmpty}")
    private String secondSurname;

    @Email(message = "{page.register.field.email.valid}")
    private String email;

}
