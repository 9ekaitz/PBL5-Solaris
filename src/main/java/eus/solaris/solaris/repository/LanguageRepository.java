package eus.solaris.solaris.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    public List<Language> findAll();
    
}
