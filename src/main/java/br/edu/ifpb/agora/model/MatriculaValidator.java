package br.edu.ifpb.agora.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MatriculaValidator implements ConstraintValidator<MatriculaConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("[0-9]{11}");
    }
    
    @Override
    public void initialize(MatriculaConstraint contactNumber) {
    }
    
}

