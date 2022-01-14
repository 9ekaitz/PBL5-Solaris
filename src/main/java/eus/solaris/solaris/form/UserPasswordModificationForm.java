package eus.solaris.solaris.form;

import javax.validation.constraints.NotEmpty;

import eus.solaris.solaris.validation.ValidFieldsMatch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ValidFieldsMatch(field = "password", fieldMatch = "verifyNewPasword", message = "page.register.field.verifyPassword.fieldMatch")
public class UserPasswordModificationForm {

    private String oldPassword;

    private String newPassword;

    private String verifyNewPasword;

    
}
