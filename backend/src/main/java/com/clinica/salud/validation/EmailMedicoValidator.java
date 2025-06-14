package com.clinica.salud.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import java.util.Set;

/**
 * Validador personalizado para emails médicos
 * Incluye validaciones específicas del dominio de salud
 */
public class EmailMedicoValidator implements ConstraintValidator<ValidEmailMedico, String> {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    private static final Set<String> DOMINIOS_PROFESIONALES = Set.of(
        "hospital.com", "clinica.com", "medico.com", "salud.gov", 
        "colegiomedicco.com", "medicina.edu"
    );
    
    private boolean requireProfessionalDomain;
    
    @Override
    public void initialize(ValidEmailMedico constraintAnnotation) {
        this.requireProfessionalDomain = constraintAnnotation.requireProfessionalDomain();
    }
    
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.trim().isEmpty()) {
            return true; // Permitir null/vacío, usar @NotNull/@NotBlank si se requiere
        }
        
        email = email.trim().toLowerCase();
        
        // Validación básica de formato
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return false;
        }
        
        // Si se requiere dominio profesional, validar
        if (requireProfessionalDomain) {
            String dominio = email.substring(email.indexOf('@') + 1);
            return DOMINIOS_PROFESIONALES.contains(dominio);
        }
        
        return true;
    }
}
