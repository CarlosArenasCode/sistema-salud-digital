package com.clinica.salud.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal; // Added for consultation_fee

@Schema(description = "Entidad que representa a un médico")
public class Medico {
    @Schema(description = "ID del médico", example = "1")
    private int id; // Renamed from idMedico to id

    @Schema(description = "ID del usuario asociado", example = "101")
    private Integer userId; // Added userId field

    @NotBlank(message = "El primer nombre es obligatorio")
    @Schema(description = "Primer nombre del médico", example = "Ana")
    private String firstName; // Renamed from nombre to firstName

    @NotBlank(message = "El apellido es obligatorio")
    @Schema(description = "Apellido del médico", example = "López")
    private String lastName; // Added lastName field

    @NotBlank(message = "La especialidad es obligatoria")
    @Schema(description = "Especialidad médica", example = "Cardiología")
    private String specialization; // Renamed from especialidad

    @NotBlank(message = "El número de licencia es obligatorio")
    @Schema(description = "Número de licencia del médico", example = "LIC12345")
    private String licenseNumber; // Added licenseNumber field

    @Schema(description = "Teléfono de contacto", example = "5559876")
    private String phone; // Renamed from telefono

    @Schema(description = "Correo electrónico", example = "ana.lopez@example.com")
    private String email;

    @Schema(description = "Años de experiencia", example = "10")
    private Integer yearsOfExperience; // Added yearsOfExperience field

    @Schema(description = "Tarifa de consulta", example = "75.50")
    private BigDecimal consultationFee; // Added consultationFee field

    @Schema(description = "Días disponibles (JSON string)", example = "[\"Monday\", \"Wednesday\", \"Friday\"]")
    private String availableDays; // Added availableDays field

    @Schema(description = "Horas disponibles (JSON string)", example = "[\"09:00-12:00\", \"14:00-17:00\"]")
    private String availableHours; // Added availableHours field

    public Medico() {}

    // Constructor updated to include all new fields
    public Medico(int id, Integer userId, String firstName, String lastName, String specialization, String licenseNumber, String phone, String email, Integer yearsOfExperience, BigDecimal consultationFee, String availableDays, String availableHours) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.licenseNumber = licenseNumber;
        this.phone = phone;
        this.email = email;
        this.yearsOfExperience = yearsOfExperience;
        this.consultationFee = consultationFee;
        this.availableDays = availableDays;
        this.availableHours = availableHours;
    }

    // Getters y Setters for all fields
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(Integer yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

    public BigDecimal getConsultationFee() { return consultationFee; }
    public void setConsultationFee(BigDecimal consultationFee) { this.consultationFee = consultationFee; }

    public String getAvailableDays() { return availableDays; }    public void setAvailableDays(String availableDays) { this.availableDays = availableDays; }

    public String getAvailableHours() { return availableHours; }
    public void setAvailableHours(String availableHours) { this.availableHours = availableHours; }
    
    // Métodos compatibles con nombres en español (para compatibilidad con DAO existente)
    public int getIdMedico() { return id; }
    public void setIdMedico(int idMedico) { this.id = idMedico; }
}
