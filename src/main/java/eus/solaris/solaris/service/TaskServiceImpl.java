package eus.solaris.solaris.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Task;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService{

  @Autowired
  TaskRepository taskRepository;

  @Override
  public List<Task> findByInstallerAndCompleted(User user, Boolean completed) {
    return taskRepository.findByInstallerAndCompleted(user, completed);
  }
  
}
