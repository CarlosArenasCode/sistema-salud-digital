package com.clinica.salud.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Validador personalizado para números de teléfono
 * Centraliza la lógica de validación de teléfonos
 */
public class TelefonoValidator implements ConstraintValidator<ValidTelefono, String> {
    
    private static final Pattern TELEFONO_PATTERN = Pattern.compile(
        "^(\\+?\\d{1,3}[- ]?)?\\(?\\d{3}\\)?[- ]?\\d{3}[- ]?\\d{4}$"
    );
    
    @Override
    public void initialize(ValidTelefono constraintAnnotation) {
        // Inicialización si es necesaria
    }
    
    @Override
    public boolean isValid(String telefono, ConstraintValidatorContext context) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return true; // Permitir null/vacío, usar @NotNull/@NotBlank si se requiere
        }
        
        return TELEFONO_PATTERN.matcher(telefono.trim()).matches();
    }
}
