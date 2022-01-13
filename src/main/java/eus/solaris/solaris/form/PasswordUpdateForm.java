package eus.solaris.solaris.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordUpdateForm {
    private String password;
    private String repeatPassword;
}
