package com.clinica.salud.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Anotación personalizada para validación de emails en contexto médico
 * Permite validaciones específicas del dominio de salud
 */
@Documented
@Constraint(validatedBy = EmailMedicoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmailMedico {
    
    String message() default "Email médico inválido";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    boolean requireProfessionalDomain() default false;
}
