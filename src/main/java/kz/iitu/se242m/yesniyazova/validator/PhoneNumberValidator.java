package kz.iitu.se242m.yesniyazova.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator
        implements ConstraintValidator<PhoneNumber, String> {

    private String regex;

    @Override
    public void initialize(PhoneNumber anno) {
        this.regex = anno.pattern();
    }

    @Override
    public boolean isValid(String value,
                           ConstraintValidatorContext ctx) {
        return value == null || value.matches(regex);
    }
}
