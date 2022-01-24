package eus.solaris.solaris.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Language;
import eus.solaris.solaris.repository.LanguageRepository;
import eus.solaris.solaris.service.LanguageService;

@Service
public class LanguageServiceImpl implements LanguageService{

    @Autowired
    private LanguageRepository languageRepository;

    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }

    @Override
    public Language findById(Long id) {
        return languageRepository.findById(id).orElse(null);
    }

    
    
}
