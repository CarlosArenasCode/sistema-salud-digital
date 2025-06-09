package com.clinica.salud.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Entidad que representa a un paciente")
public class Paciente {
    @Schema(description = "ID del paciente", example = "1")
    private int patientId; // Changed from idPaciente
    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre completo del paciente", example = "Juan Perez")
    private String name; // Changed from nombre
    @NotBlank(message = "La fecha de nacimiento es obligatoria")
    @Schema(description = "Fecha de nacimiento (YYYY-MM-DD)", example = "1990-01-01")
    private String dateOfBirth; // Changed from fechaNacimiento
    @NotBlank(message = "La dirección es obligatoria")
    @Schema(description = "Dirección del paciente", example = "Calle 123")
    private String address; // Changed from direccion
    @NotBlank(message = "El teléfono es obligatorio")
    @Schema(description = "Teléfono de contacto", example = "5551234")
    private String phone; // Changed from telefono
    @Email(message = "El email debe ser válido")
    @Schema(description = "Correo electrónico", example = "juan@correo.com")
    private String email;
    @NotBlank(message = "El género es obligatorio")
    @Schema(description = "Género del paciente", example = "M")
    private String gender; // Changed from genero

    public Paciente() {}

    public Paciente(int patientId, String name, String dateOfBirth, String address, String phone, String email, String gender) {
        this.patientId = patientId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
    }

    // Getters y Setters
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getAddress() { return address; }    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    // Métodos compatibles con nombres en español (para compatibilidad con DAO existente)
    public int getIdPaciente() { return patientId; }
    public void setIdPaciente(int idPaciente) { this.patientId = idPaciente; }
}
