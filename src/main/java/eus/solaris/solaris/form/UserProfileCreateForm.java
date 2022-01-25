package eus.solaris.solaris.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.Generated;

@Data
@Generated
public class UserProfileCreateForm {
    @NotEmpty(message = "{alert.createuser.username.notEmpty}")
    private String username;

    @NotEmpty(message = "{alert.createuser.name.notEmpty}")
    private String name;
    
    @NotEmpty(message = "{alert.createuser.firstSurname.notEmpty}")
    private String firstSurname;

    @NotEmpty(message = "{alert.createuser.secondSurname.notEmpty}")
    private String secondSurname;

    @NotEmpty(message = "{page.profile.field.email.notEmpty}")
    @Email(message = "{page.profile.field.email.valid}")
    private String email;

    @NotEmpty(message = "{alert.createuser.password.notEmpty}")
    private String newPassword;

    @NotEmpty(message = "{alert.createuser.repeatPassword.notEmpty}")
    private String verifyNewPasword;

    private Long roleId;
}