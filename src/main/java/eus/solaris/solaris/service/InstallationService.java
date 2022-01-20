package eus.solaris.solaris.service;

import java.util.List;

import eus.solaris.solaris.domain.Installation;
import eus.solaris.solaris.domain.User;

public interface InstallationService {
  public List<Installation> findByInstallerAndCompleted(User user, Boolean completed);
  public Installation findById(Long id);
}
