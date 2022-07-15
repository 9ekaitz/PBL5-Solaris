package eus.solaris.solaris.form;

import javax.validation.constraints.NotEmpty;

import eus.solaris.solaris.validation.ValidFieldsMatch;
import eus.solaris.solaris.validation.ValidPassword;
import lombok.Data;
import lombok.Generated;

@Data
@Generated
@ValidFieldsMatch(field = "newPassword", fieldMatch = "verifyNewPasword", message = "{page.manageusers.field.verifyPassword.fieldMatch}")
public class PasswordUpdateForm {
    @ValidPassword(message = "{page.manageusers.field.password.valid}")
    @NotEmpty(message = "{alert.password.notEmpty}")
    private String newPassword;
    private String verifyNewPasword;
}
