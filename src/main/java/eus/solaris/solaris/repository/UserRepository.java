package eus.solaris.solaris.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.solaris.solaris.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
    
}
