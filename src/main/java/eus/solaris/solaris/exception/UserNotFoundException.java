package eus.solaris.solaris.exception;

public class UserNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = -4993044975338050124L;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String username) {
        super("Could not find user with username " + username);
    }

}
