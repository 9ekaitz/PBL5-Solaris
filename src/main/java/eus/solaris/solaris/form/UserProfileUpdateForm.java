package eus.solaris.solaris.form;

import lombok.Data;
import lombok.Generated;

@Data
@Generated
public class UserProfileUpdateForm {
    private String username;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String email;
    private Long roleId;
}
