package com.clinica.salud.modelo;

/**
 * Enumeración que representa los diferentes roles de usuario en el sistema
 */
public enum Rol {
    ADMINISTRADOR("ADMINISTRADOR"),
    MEDICO("MEDICO"), 
    PACIENTE("PACIENTE");
    
    private final String nombreRol;
    
    Rol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
    
    public String getNombreRol() {
        return nombreRol;
    }
    
    @Override
    public String toString() {
        return nombreRol;
    }
    
    /**
     * Convierte un string a enum Rol
     * @param rol String del rol
     * @return Rol enum correspondiente
     */
    public static Rol fromString(String rol) {
        if (rol == null) {
            return null;
        }
        
        for (Rol r : Rol.values()) {
            if (r.nombreRol.equalsIgnoreCase(rol.trim())) {
                return r;
            }
        }
        
        throw new IllegalArgumentException("Rol no válido: " + rol);
    }
}
