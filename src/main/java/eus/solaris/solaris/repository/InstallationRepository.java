package eus.solaris.solaris.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.Installation;
import eus.solaris.solaris.domain.User;

@Repository
public interface InstallationRepository extends JpaRepository<Installation, Long>{
  public List<Installation> findByInstallerAndCompleted(User user, Boolean completed);
}
