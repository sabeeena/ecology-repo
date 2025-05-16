package kz.iitu.se242m.yesniyazova.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumber {

    String pattern() default "^\\+?\\d{7,15}$";

    String message() default "Invalid phone number format";

    Class<?>[] groups()   default {};
    Class<? extends Payload>[] payload() default {};
}
