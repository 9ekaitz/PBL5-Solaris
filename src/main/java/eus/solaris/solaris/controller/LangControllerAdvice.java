package eus.solaris.solaris.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import eus.solaris.solaris.domain.Language;
import eus.solaris.solaris.service.LanguageService;

@ControllerAdvice
public class LangControllerAdvice {

    @Autowired
	LanguageService languageService;

    @ModelAttribute("languages")
    public List<Language> addLangToModel(){
        return languageService.findAll();
    }
    
}
