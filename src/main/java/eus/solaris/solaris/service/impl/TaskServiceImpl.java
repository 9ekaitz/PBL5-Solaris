package eus.solaris.solaris.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Task;
import eus.solaris.solaris.repository.TaskRepository;
import eus.solaris.solaris.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
  
  @Autowired
  TaskRepository taskRepository;

  @Override
  public Task markCompleted(Task task) {
    task.setCompleted(true);
    return taskRepository.save(task);
  }

  @Override
  public Task findById(Long id) {
    return taskRepository.findById(id).orElse(null);
  }

  @Override
  public Task save(Task task) {
    return taskRepository.save(task);
  }
}
