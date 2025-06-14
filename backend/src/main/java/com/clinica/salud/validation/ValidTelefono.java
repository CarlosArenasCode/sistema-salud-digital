package com.clinica.salud.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Anotación personalizada para validación de números de teléfono
 * Centraliza las reglas de validación de teléfonos
 */
@Documented
@Constraint(validatedBy = TelefonoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTelefono {
    
    String message() default "Formato de teléfono inválido";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
