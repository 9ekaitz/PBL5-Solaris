package eus.solaris.solaris.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRegistrationDto {
    
    private String username;
    private String password;
    private String verifyPassword;
    private String name;
    private String firstUsername;
    private String secondUsername;
    private String email;

}
