package eus.solaris.solaris.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class UserProfileUpdateForm {
    @NotEmpty(message = "{alert.createuser.username.notEmpty}")
    private String username;
    
    @NotEmpty(message = "{alert.createuser.name.notEmpty}")
    private String name;

    @NotEmpty(message = "{alert.createuser.firstSurname.notEmpty}")
    private String firstSurname;

    @NotEmpty(message = "{alert.createuser.secondSurname.notEmpty}")
    private String secondSurname;
    
    @NotEmpty(message = "{alert.createuser.email.notEmpty}")
    @Email(message = "{page.profile.field.email.valid}")
    private String email;

    private Long roleId;
}
