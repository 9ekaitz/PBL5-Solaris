package eus.solaris.solaris.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long>{

    @Query("select p from Province p order by p.i18n")
    public List<Province> findAll(String name);
    
    
}
