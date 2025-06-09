package com.clinica.salud.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Entidad que representa una factura")
public class Factura {
    @Schema(description = "ID de la factura", example = "1")
    private int idFactura;
    @Schema(description = "ID del paciente", example = "1")
    private int idPaciente;
    @NotBlank(message = "La fecha es obligatoria")
    @Schema(description = "Fecha de la factura (YYYY-MM-DD)", example = "2025-05-27")
    private String fecha;
    @Schema(description = "Monto de la factura", example = "450.0")
    private double monto;

    public Factura() {}

    public Factura(int idFactura, int idPaciente, String fecha, double monto) {
        this.idFactura = idFactura;
        this.idPaciente = idPaciente;
        this.fecha = fecha;
        this.monto = monto;
    }

    // Getters y Setters
    public int getIdFactura() { return idFactura; }
    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }
    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
}
