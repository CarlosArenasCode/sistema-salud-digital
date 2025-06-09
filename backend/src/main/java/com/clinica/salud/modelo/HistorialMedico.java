package com.clinica.salud.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Entidad que representa un historial médico")
public class HistorialMedico {
    @Schema(description = "ID del historial médico", example = "1")
    private int id; // Renamed from idHistorial

    @NotNull(message = "ID del paciente es obligatorio")
    @Schema(description = "ID del paciente", example = "1")
    private Integer patientId; // Renamed from idPaciente and type changed to Integer

    @NotNull(message = "ID del médico es obligatorio")
    @Schema(description = "ID del médico", example = "1")
    private Integer doctorId; // Added doctorId field

    @Schema(description = "ID de la cita asociada", example = "1")
    private Integer appointmentId; // Added appointmentId field

    @NotBlank(message = "El diagnóstico es obligatorio")
    @Schema(description = "Diagnóstico médico", example = "Gripe")
    private String diagnosis; // Renamed from diagnostico

    @Schema(description = "Tratamiento indicado", example = "Reposo y líquidos")
    private String treatment; // Renamed from tratamiento

    @Schema(description = "Prescripción médica", example = "Paracetamol 500mg cada 8 horas")
    private String prescription; // Added prescription field

    @Schema(description = "Notas adicionales", example = "El paciente refiere dolor de cabeza.")
    private String notes; // Added notes field

    @Schema(description = "Fecha del registro (YYYY-MM-DD)", example = "2025-05-27")
    private LocalDate recordDate; // Renamed from fecha and type changed to LocalDate

    @Schema(description = "Fecha de seguimiento (YYYY-MM-DD)", example = "2025-06-03")
    private LocalDate followUpDate; // Added followUpDate field

    public HistorialMedico() {}

    // Constructor updated to include all new fields
    public HistorialMedico(int id, Integer patientId, Integer doctorId, Integer appointmentId, String diagnosis, String treatment, String prescription, String notes, LocalDate recordDate, LocalDate followUpDate) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentId = appointmentId;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.prescription = prescription;
        this.notes = notes;
        this.recordDate = recordDate;
        this.followUpDate = followUpDate;
    }

    // Getters y Setters for all fields
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getPatientId() { return patientId; }
    public void setPatientId(Integer patientId) { this.patientId = patientId; }

    public Integer getDoctorId() { return doctorId; }
    public void setDoctorId(Integer doctorId) { this.doctorId = doctorId; }

    public Integer getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Integer appointmentId) { this.appointmentId = appointmentId; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getTreatment() { return treatment; }
    public void setTreatment(String treatment) { this.treatment = treatment; }

    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }    public LocalDate getRecordDate() { return recordDate; }
    public void setRecordDate(LocalDate recordDate) { this.recordDate = recordDate; }

    public LocalDate getFollowUpDate() { return followUpDate; }
    public void setFollowUpDate(LocalDate followUpDate) { this.followUpDate = followUpDate; }
    
    // Métodos compatibles con nombres en español (para compatibilidad con DAO existente)
    public int getIdHistorial() { return id; }
    public void setIdHistorial(int idHistorial) { this.id = idHistorial; }
}
