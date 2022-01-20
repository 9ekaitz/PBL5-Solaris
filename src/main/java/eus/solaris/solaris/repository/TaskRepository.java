package eus.solaris.solaris.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  
}
