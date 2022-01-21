package eus.solaris.solaris.repository.filters;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    
}