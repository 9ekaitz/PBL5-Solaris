package eus.solaris.solaris.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import eus.solaris.solaris.domain.Task;
import eus.solaris.solaris.repository.TaskRepository;
import eus.solaris.solaris.service.impl.TaskServiceImpl;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

  @InjectMocks
  TaskServiceImpl taskServiceImpl;

  @Mock
  TaskRepository taskRepository;

  @Test
  void findByIdTest() {
    Long id = 1L;
    Task task = new Task(id, "Task_Desc 1", true, null, 1);

    Optional<Task> op = Optional.of(task);

    when(taskRepository.findById(id)).thenReturn(op);
    assertEquals(task, taskServiceImpl.findById(id));
    verify(taskRepository, times(1)).findById(id);
  }

  @Test
  void markCompletedTest() {
    Task completedTask = new Task(1L, "Task_Desc 1", true, null, 1);
    Task task = new Task(1L, "Task_Desc 1", false, null, 1);

    when(taskRepository.save(completedTask)).thenReturn(completedTask);
    assertEquals(completedTask, taskServiceImpl.markCompleted(task));
    verify(taskRepository, times(1)).save(completedTask);
  }
}
