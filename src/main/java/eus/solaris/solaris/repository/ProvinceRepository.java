package eus.solaris.solaris.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long>{
    
}
