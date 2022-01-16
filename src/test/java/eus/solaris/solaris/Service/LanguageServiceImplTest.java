package eus.solaris.solaris.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import eus.solaris.solaris.domain.Language;
import eus.solaris.solaris.repository.LanguageRepository;
import eus.solaris.solaris.service.impl.LanguageServiceImpl;

@ExtendWith(MockitoExtension.class)
class LanguageServiceImplTest {
    
    @InjectMocks
    private LanguageServiceImpl languageServiceImpl;

    @Mock
    private LanguageRepository languageRepository;

    @Test
    void findAllTest() {
        List<Language> languages = new ArrayList<>();
        languages.add(new Language(1L, "LANGUAGE_SPANISH", "language.spanish", 1));
        languages.add(new Language(2L, "LANGUAGE_FRENCH", "language.french", 2));

        when(languageRepository.findAll()).thenReturn(languages);
        assertEquals(languages, languageServiceImpl.findAll());
    }
}
