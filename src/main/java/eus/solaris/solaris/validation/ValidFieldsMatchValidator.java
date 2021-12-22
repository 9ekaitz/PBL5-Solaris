package eus.solaris.solaris.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class ValidFieldsMatchValidator implements ConstraintValidator<ValidFieldsMatch, Object> {

    private String field;
    private String fieldMatch;
    private String message;

    public void initialize(ValidFieldsMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
        this.message = constraintAnnotation.message();
    }

    public boolean isValid(Object value, 
      ConstraintValidatorContext context) {

        boolean isValid = false;

        Object fieldValue = new BeanWrapperImpl(value)
          .getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value)
          .getPropertyValue(fieldMatch);
        
        if (fieldValue != null) {
            if(fieldValue.equals(fieldMatchValue)) {
                isValid = true;
            }
        } else {
            isValid = true;
        }
        
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate( message )
                    .addPropertyNode(fieldMatch).addConstraintViolation();
        }
        return isValid;
    }

}