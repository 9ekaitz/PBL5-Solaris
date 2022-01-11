package eus.solaris.solaris.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.RuleResultDetail;
import org.passay.WhitespaceRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Autowired
    private MessageSource messageSource;

    private boolean lengthRule;
    private int minLength;
    private int maxLength;

    private boolean upperCaseRule;
    private int minUpperCase;

    private boolean lowerCaseRule;
    private int minLowerCase;

    private boolean digitRule;
    private int minDigit;

    private boolean specialCharacterRule;
    private int minSpecialCharacter;

    private boolean whitespaceRule;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        lengthRule = constraintAnnotation.lengthRule();
        minLength = constraintAnnotation.minLength();
        maxLength = constraintAnnotation.maxLength();
        upperCaseRule = constraintAnnotation.upperCaseRule();
        minUpperCase = constraintAnnotation.minUpperCase();
        lowerCaseRule = constraintAnnotation.lowerCaseRule();
        minLowerCase = constraintAnnotation.minLowerCase();
        digitRule = constraintAnnotation.digitRule();
        minDigit = constraintAnnotation.minDigit();
        specialCharacterRule = constraintAnnotation.specialCharacterRule();
        minSpecialCharacter = constraintAnnotation.minSpecialCharacter();
        whitespaceRule = constraintAnnotation.whitespaceRule();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        if (password == null || password.equals(""))
            return true;
        PasswordValidator validator = createValidator();

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }

        List<String> messages = validator.getMessages(result);
        for (String s : messages) {
            System.out.println(s);
        }
        String messageTemplate = messages.stream()
                .collect(Collectors.joining(","));
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }

    private PasswordValidator createValidator() {
        SpringMessageResolver springMessageResolver = new SpringMessageResolver(messageSource);

        List<Rule> rules = new ArrayList<>();
        if (lengthRule)
            rules.add(new LengthRule(minLength, maxLength));
        if (upperCaseRule)
            rules.add(new CharacterRule(EnglishCharacterData.UpperCase, minUpperCase));
        if (lowerCaseRule)
            rules.add(new CharacterRule(EnglishCharacterData.LowerCase, minLowerCase));
        if (digitRule)
            rules.add(new CharacterRule(EnglishCharacterData.Digit, minDigit));
        if (specialCharacterRule)
            rules.add(new CharacterRule(EnglishCharacterData.Special, minSpecialCharacter));
        if (whitespaceRule)
            rules.add(new WhitespaceRule());

        return new PasswordValidator(springMessageResolver, rules);
    }

    public class SpringMessageResolver implements MessageResolver {

        private final MessageSourceAccessor messageSourceAccessor;

        private final MessageResolver fallbackMessageResolver = new PropertiesMessageResolver();

        public SpringMessageResolver(final MessageSource messageSource) {
            this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
        }

        public SpringMessageResolver(final MessageSource messageSource, final Locale locale) {
            this.messageSourceAccessor = new MessageSourceAccessor(messageSource, locale);
        }

        @Override
        public String resolve(RuleResultDetail detail) {
            try {
                return this.messageSourceAccessor.getMessage(detail.getErrorCode(), detail.getValues());
            } catch (NoSuchMessageException e) {
                return this.fallbackMessageResolver.resolve(detail);
            }
        }
    }
}