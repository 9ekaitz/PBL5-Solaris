package eus.solaris.solaris.form;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Generated
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
