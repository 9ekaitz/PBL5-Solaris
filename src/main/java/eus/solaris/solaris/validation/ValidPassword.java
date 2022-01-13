package eus.solaris.solaris.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "Invalid Password";

    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    boolean lengthRule() default true;
    int minLength() default 8;
    int maxLength() default 30;
    
    boolean upperCaseRule() default false;
    int minUpperCase() default 1;
    
    boolean lowerCaseRule() default false;
    int minLowerCase() default 1;
    
    boolean digitRule() default false;
    int minDigit() default 1;
    
    boolean specialCharacterRule() default false;
    int minSpecialCharacter() default 1;
    
    boolean whitespaceRule() default false;

}