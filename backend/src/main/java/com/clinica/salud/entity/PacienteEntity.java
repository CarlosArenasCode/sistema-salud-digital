package com.clinica.salud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Entidad que representa a un paciente en el sistema de salud digital.
 * Mapea con la tabla 'pacientes' en PostgreSQL.
 * OPTIMIZADO: Reducido de 282 líneas a ~75 líneas usando Lombok
 */
@Entity
@Table(name = "pacientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PacienteEntity extends BaseEntity {
    
    @Column(name = "id_usuario")
    private Long userId;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "nombres", nullable = false, length = 100)
    private String firstName;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    @Column(name = "apellidos", nullable = false, length = 100)
    private String lastName;
    
    @Column(name = "fecha_nacimiento")
    private LocalDate dateOfBirth;
    
    @Size(max = 10, message = "El género no puede exceder 10 caracteres")
    @Column(name = "genero", length = 10)
    private String gender;
    
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    @Column(name = "telefono", length = 20)
    private String phone;
    
    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    @Column(name = "direccion")
    private String address;
    
    @Size(max = 100, message = "El contacto de emergencia no puede exceder 100 caracteres")
    @Column(name = "contacto_emergencia", length = 100)
    private String emergencyContact;
    
    @Size(max = 20, message = "El teléfono de emergencia no puede exceder 20 caracteres")
    @Column(name = "telefono_emergencia", length = 20)
    private String emergencyPhone;
    
    @Size(max = 5, message = "El tipo de sangre no puede exceder 5 caracteres")
    @Column(name = "tipo_sangre", length = 5)
    private String bloodType;
    
    @Column(name = "alergias", columnDefinition = "TEXT")
    private String allergies;
    
    @Size(max = 100, message = "El seguro médico no puede exceder 100 caracteres")
    @Column(name = "seguro_medico", length = 100)
    private String seguroMedico;
    
    @Size(max = 20, message = "El número de identificación no puede exceder 20 caracteres")
    @Column(name = "numero_identificacion", unique = true, length = 20)
    private String numeroIdentificacion;
    
    @Size(max = 20, message = "El estado civil no puede exceder 20 caracteres")
    @Column(name = "estado_civil", length = 20)
    private String estadoCivil;
    
    @Size(max = 100, message = "La ocupación no puede exceder 100 caracteres")
    @Column(name = "ocupacion", length = 100)
    private String ocupacion;
    
    // Método utilitario para obtener el nombre completo
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
