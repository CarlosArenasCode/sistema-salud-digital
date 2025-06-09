package com.clinica.salud.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Entidad que representa una cita médica")
public class Cita {
    @Schema(description = "ID de la cita", example = "1")
    private int appointmentId; // Changed from idCita
    @Schema(description = "ID del paciente", example = "1")
    private int patientId; // Changed from idPaciente
    @Schema(description = "ID del médico", example = "1")
    private int doctorId; // Changed from idMedico
    @NotBlank(message = "La fecha es obligatoria")
    @Schema(description = "Fecha de la cita (YYYY-MM-DD)", example = "2025-06-01")
    private String appointmentDate; // Changed from fecha
    @NotBlank(message = "La hora es obligatoria")
    @Schema(description = "Hora de la cita (HH:MM:SS)", example = "09:30:00") // Adjusted example for Time.valueOf
    private String appointmentTime; // Changed from hora
    @NotBlank(message = "El motivo es obligatorio")
    @Schema(description = "Motivo de la cita", example = "Consulta general")
    private String reason; // Changed from motivo

    public Cita() {}

    public Cita(int appointmentId, int patientId, int doctorId, String appointmentDate, String appointmentTime, String reason) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
    }    // Getters y Setters
    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public String getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }
    public String getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    
    // Métodos compatibles con nombres en español (para compatibilidad con DAO existente)
    public int getIdCita() { return appointmentId; }
    public void setIdCita(int idCita) { this.appointmentId = idCita; }
    public int getIdPaciente() { return patientId; }
    public void setIdPaciente(int idPaciente) { this.patientId = idPaciente; }
    public int getIdMedico() { return doctorId; }
    public void setIdMedico(int idMedico) { this.doctorId = idMedico; }
    public String getFecha() { return appointmentDate; }
    public void setFecha(String fecha) { this.appointmentDate = fecha; }
    public String getHora() { return appointmentTime; }
    public void setHora(String hora) { this.appointmentTime = hora; }
    public String getMotivo() { return reason; }
    public void setMotivo(String motivo) { this.reason = motivo; }
}
