package eus.solaris.solaris.exception;

public class FileNotFoundException extends RuntimeException {
    
    public FileNotFoundException() {
        super();
    }

    public FileNotFoundException(String location) {
        super("Could not find file " + location);
    }

}
