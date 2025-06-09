package com.clinica.salud.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Entidad que representa un resultado de laboratorio")
public class ResultadoLaboratorio {
    @Schema(description = "ID del resultado", example = "1")
    private int idResultado;
    @Schema(description = "ID del paciente", example = "1")
    private int idPaciente;
    @NotBlank(message = "El tipo de examen es obligatorio")
    @Schema(description = "Tipo de examen", example = "Hemograma")
    private String tipoExamen;
    @NotBlank(message = "El resultado es obligatorio")
    @Schema(description = "Resultado del examen", example = "Normal")
    private String resultado;
    @NotBlank(message = "La fecha es obligatoria")
    @Schema(description = "Fecha del resultado (YYYY-MM-DD)", example = "2025-05-27")
    private String fecha;

    public ResultadoLaboratorio() {}

    public ResultadoLaboratorio(int idResultado, int idPaciente, String tipoExamen, String resultado, String fecha) {
        this.idResultado = idResultado;
        this.idPaciente = idPaciente;
        this.tipoExamen = tipoExamen;
        this.resultado = resultado;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getIdResultado() { return idResultado; }
    public void setIdResultado(int idResultado) { this.idResultado = idResultado; }
    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
    public String getTipoExamen() { return tipoExamen; }
    public void setTipoExamen(String tipoExamen) { this.tipoExamen = tipoExamen; }
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}
