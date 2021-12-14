package eus.solaris.solaris.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
    
}