package eus.solaris.solaris.exception;

public class AvatarNotCreatedException extends Exception{

  private static final long serialVersionUID = 2670718853585732484L;

  public AvatarNotCreatedException() {
      super();
  }

  public AvatarNotCreatedException(String username) {
      super("Could not create avatr picture for user " + username);
  }
}
