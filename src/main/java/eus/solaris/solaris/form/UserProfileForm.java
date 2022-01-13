package eus.solaris.solaris.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserProfileForm {
    
    // private String username;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String age;
    private String email;
    private Long roleId;

}
