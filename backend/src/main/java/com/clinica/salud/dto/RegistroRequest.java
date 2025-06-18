package com.clinica.salud.dto;

// Validaciones para el formulario de registro
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

// DTO para solicitudes de registro de usuario con campos para médicos y pacientes
public class RegistroRequest {    
    // Nombre de usuario requerido (4-50 caracteres)
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, max = 50, message = "El nombre de usuario debe tener entre 4 y 50 caracteres")
    private String nombreUsuario;

    // Contraseña requerida (mínimo 6 caracteres)
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    // Email requerido con formato válido
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;

    // Rol del usuario (MEDICO, PACIENTE, etc.)
    private String rol;
    
    // Nombres de la persona
    private String nombres;
    // Apellidos de la persona
    private String apellidos;
    // Fecha de nacimiento
    private LocalDate fechaNacimiento;
    // Número de identificación oficial
    private String numeroIdentificacion;
    // Especialización médica (solo para médicos)
    private String especializacion;    // Constructor por defecto
    public RegistroRequest() {}
    
    // Constructor con parámetros básicos para registro
    public RegistroRequest(String nombreUsuario, String password, String email, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.email = email;
        this.rol = rol;    }
    
    // Getter para obtener el nombre de usuario
    public String getNombreUsuario() {
        return nombreUsuario;    }

    // Setter para establecer el nombre de usuario
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    // Getter para obtener la contraseña
    public String getPassword() {
        return password;
    }

    // Setter para establecer la contraseña
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter para obtener el email
    public String getEmail() {
        return email;
    }

    // Setter para establecer el email
    public void setEmail(String email) {
        this.email = email;
    }
    
    // Getter para obtener el rol
    public String getRol() {
        return rol;
    }

    // Setter para establecer el rol
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    // Getter para obtener los nombres
    public String getNombres() {
        return nombres;
    }

    // Setter para establecer los nombres
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    // Getter para obtener los apellidos
    public String getApellidos() {
        return apellidos;
    }

    // Setter para establecer los apellidos
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    // Getter para obtener la fecha de nacimiento
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    // Setter para establecer la fecha de nacimiento
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getter para obtener el número de identificación
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    // Setter para establecer el número de identificación
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    // Getter para obtener la especialización
    public String getEspecializacion() {
        return especializacion;
    }

    // Setter para establecer la especialización
    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }
}
