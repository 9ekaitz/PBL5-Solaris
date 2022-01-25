package eus.solaris.solaris.form;

import lombok.Data;
import lombok.Generated;

@Data
@Generated
public class UserProfileCreateForm {
    private String username;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String email;
    private String password;
    private String repeatPassword;
    private Long roleId;
}