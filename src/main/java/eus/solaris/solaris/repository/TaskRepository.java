package eus.solaris.solaris.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.Task;
import eus.solaris.solaris.domain.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
  public List<Task> findByInstallerAndCompleted(User user, Boolean completed);
}
