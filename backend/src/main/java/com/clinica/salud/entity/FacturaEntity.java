package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad JPA para facturas
 * Mapea con la tabla 'facturas' en PostgreSQL
 */
@Entity
@Table(name = "facturas")
public class FacturaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;
    
    @Column(name = "id_cita")
    private Long idCita;
    
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que 0")
    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    
    @NotBlank(message = "El estado de pago es obligatorio")
    @Column(name = "estado_pago", nullable = false, length = 20)
    private String estadoPago;
    
    @Column(name = "metodo_pago", length = 50)
    private String metodoPago;
    
    @Column(name = "id_transaccion", length = 100)
    private String idTransaccion;
    
    @NotNull(message = "La fecha de factura es obligatoria")
    @Column(name = "fecha_factura", nullable = false)
    private LocalDate fechaFactura;
    
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
    
    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;
    
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Relaciones JPA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", insertable = false, updatable = false)
    private PacienteEntity paciente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cita", insertable = false, updatable = false)
    private CitaEntity cita;
    
    // Constructor por defecto
    public FacturaEntity() {}
    
    // Constructor con campos básicos
    public FacturaEntity(Long idPaciente, BigDecimal monto, String estadoPago, LocalDate fechaFactura) {
        this.idPaciente = idPaciente;
        this.monto = monto;
        this.estadoPago = estadoPago;
        this.fechaFactura = fechaFactura;
    }
    
    // Métodos de auditoría
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getIdPaciente() {
        return idPaciente;
    }
    
    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }
    
    public Long getIdCita() {
        return idCita;
    }
    
    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }
    
    public BigDecimal getMonto() {
        return monto;
    }
    
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
    public String getEstadoPago() {
        return estadoPago;
    }
    
    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }
    
    public String getMetodoPago() {
        return metodoPago;
    }
    
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    
    public String getIdTransaccion() {
        return idTransaccion;
    }
    
    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }
    
    public LocalDate getFechaFactura() {
        return fechaFactura;
    }
    
    public void setFechaFactura(LocalDate fechaFactura) {
        this.fechaFactura = fechaFactura;
    }
    
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }
    
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
    public String getNotas() {
        return notas;
    }
    
    public void setNotas(String notas) {
        this.notas = notas;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
    public PacienteEntity getPaciente() {
        return paciente;
    }
    
    public void setPaciente(PacienteEntity paciente) {
        this.paciente = paciente;
    }
    
    public CitaEntity getCita() {
        return cita;
    }
    
    public void setCita(CitaEntity cita) {
        this.cita = cita;
    }
    
    @Override
    public String toString() {
        return "FacturaEntity{" +
                "id=" + id +
                ", idPaciente=" + idPaciente +
                ", monto=" + monto +
                ", estadoPago='" + estadoPago + '\'' +
                ", fechaFactura=" + fechaFactura +
                '}';
    }
}
