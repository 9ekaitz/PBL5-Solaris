package eus.solaris.solaris.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

}
