package com.example.festapp.validation;

import com.example.festapp.dto.RegisterRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterRequest> {

    @Override
    public boolean isValid(RegisterRequest value, ConstraintValidatorContext context) {
        System.out.println(">>> VALIDANDO PASSWORD MATCH <<<");
        if( value.getPassword() == null || value.getPasswordConfirm() == null) {
            return true;
        }

        return value.getPassword().equals(value.getPasswordConfirm());
    }

}
