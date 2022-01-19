package eus.solaris.solaris.exception;

public class FileNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = -1717719095582607026L;

    public FileNotFoundException() {
        super();
    }

    public FileNotFoundException(String location) {
        super("Could not find file " + location);
    }

}
