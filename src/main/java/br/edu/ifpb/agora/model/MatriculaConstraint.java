package br.edu.ifpb.agora.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = MatriculaValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MatriculaConstraint {
    String message() default "Matrícula Inválida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
