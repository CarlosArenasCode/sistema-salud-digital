package com.clinica.salud.entity;

/**
 * Definición de roles de usuario en el sistema de salud digital
 * Contiene los diferentes tipos de usuarios que pueden acceder al sistema
 */
public enum RolUsuario {
    // Roles administrativos del sistema
    ADMINISTRADOR,  // Usuario con privilegios totales sobre el sistema
    RECEPCIONISTA,  // Usuario encargado de gestionar citas y recepción de pacientes
    
    // Roles de personal médico
    MEDICO,         // Usuario que puede atender pacientes y gestionar historias clínicas
    
    // Roles de pacientes
    PACIENTE        // Usuario que recibe atención médica en el sistema
}
