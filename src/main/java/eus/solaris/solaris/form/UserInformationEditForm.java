package eus.solaris.solaris.form;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInformationEditForm {

    @NotEmpty(message = "{page.profile.field.name.notEmpty}")
    private String name;

    @NotEmpty(message = "{page.profile.field.firstSurname.notEmpty}")
    private String firstSurname;

    @NotEmpty(message = "{page.profile.field.secondSurname.notEmpty}")
    private String secondSurname;

    @NotEmpty(message = "{page.profile.field.email.notEmpty}")
    @Email(message = "{page.profile.field.email.valid}")
    private String email;

}
