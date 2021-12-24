package eus.solaris.solaris.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Language;
import eus.solaris.solaris.repository.LanguageRepository;

@Service
public class LanguageServiceImpl implements LanguageService{

    @Autowired
    private LanguageRepository languageRepository;

    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }
    
}
