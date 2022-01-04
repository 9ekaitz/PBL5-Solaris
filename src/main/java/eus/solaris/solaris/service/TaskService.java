package eus.solaris.solaris.service;

import java.util.List;

import eus.solaris.solaris.domain.Task;
import eus.solaris.solaris.domain.User;

public interface TaskService {
  public List<Task> findByInstallerAndCompleted(User user, Boolean completed);
}
