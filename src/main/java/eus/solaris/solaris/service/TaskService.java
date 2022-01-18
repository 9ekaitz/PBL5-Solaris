package eus.solaris.solaris.service;

import eus.solaris.solaris.domain.Task;

public interface TaskService {
  public Task markCompleted(Task task);
  public Task findById(Long id);
}
