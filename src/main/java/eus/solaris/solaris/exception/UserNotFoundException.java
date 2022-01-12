package eus.solaris.solaris.exception;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String username) {
        super("Could not find user with username " + username);
    }

}
