package eus.solaris.solaris.form;

import eus.solaris.solaris.validation.ValidFieldsMatch;
import eus.solaris.solaris.validation.ValidPassword;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ValidFieldsMatch(field = "password", fieldMatch = "verifyNewPasword", message = "page.manageusers.field.verifyPassword.fieldMatch")
public class PasswordUpdateForm {
    @ValidPassword
    private String newPassword;
    private String verifyNewPasword;
}
