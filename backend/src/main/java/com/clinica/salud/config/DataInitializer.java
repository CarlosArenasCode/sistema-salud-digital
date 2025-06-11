package com.clinica.salud.config;

import com.clinica.salud.entity.UsuarioEntity;
import com.clinica.salud.entity.PacienteEntity;
import com.clinica.salud.entity.MedicoEntity;
import com.clinica.salud.entity.MedicamentoEntity;
import com.clinica.salud.entity.CitaEntity;
import com.clinica.salud.repository.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

/**
 * Inicializa datos de prueba en la base de datos con referencias correctas de IDs
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioJpaRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PacienteJpaRepository pacienteRepository;
    @Autowired
    private MedicoJpaRepository medicoRepository;
    @Autowired
    private MedicamentoJpaRepository medicamentoRepository;
    @Autowired
    private CitaJpaRepository citaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Crear usuarios de prueba si no existen
        if (usuarioRepository.count() == 0) {
            // ===== ADMINISTRADORES =====
            UsuarioEntity admin = new UsuarioEntity();
            admin.setNombreUsuario("admin");
            admin.setContrasena(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@clinica.com");
            admin.setRol("ADMINISTRADOR");
            admin.setActivo(true);
            usuarioRepository.save(admin);

            UsuarioEntity admin2 = new UsuarioEntity();
            admin2.setNombreUsuario("admin_sistema");
            admin2.setContrasena(passwordEncoder.encode("admin123"));
            admin2.setEmail("admin.sistema@saluddigital.com");
            admin2.setRol("ADMINISTRADOR");
            admin2.setActivo(true);
            usuarioRepository.save(admin2);

            System.out.println("=== SISTEMA DE SALUD DIGITAL - CREDENCIALES CREADAS ===");
            System.out.println("👑 ADMINISTRADORES:");
            System.out.println("- admin/admin123 (ADMINISTRADOR)");
            System.out.println("- admin_sistema/admin123 (ADMINISTRADOR)");
            System.out.println("✅ Total: 2 usuarios administradores creados");
            System.out.println("🔐 Contraseñas: admin123");
        }

        System.out.println("🔄 Cargando datos de ejemplo para el resto del sistema...");
        
        // Guardar pacientes y capturar sus IDs
        List<PacienteEntity> pacientes = new ArrayList<>();
        if (pacienteRepository.count() == 0) {            pacientes.add(pacienteRepository.save(PacienteEntity.builder()
                .nombres("María").apellidos("López")
                .numeroIdentificacion("12345678901")
                .fechaNacimiento(LocalDate.of(1985,3,15)).genero("FEMENINO")
                .telefono("555-1111").email("maria.lopez@email.com").direccion("Calle 123 #45-67")
                .contactoEmergencia("José López").telefonoEmergencia("555-2222")
                .tipoSangre("O+").alergias("Ninguna").seguroMedico("SaludPlus")
                .estadoCivil("Soltera").ocupacion("Ingeniera").activo(true)
                .build()));
            
            pacientes.add(pacienteRepository.save(PacienteEntity.builder()
                .nombres("Carlos").apellidos("Pérez")
                .numeroIdentificacion("23456789012")
                .fechaNacimiento(LocalDate.of(1990,7,22)).genero("MASCULINO")
                .telefono("555-3333").email("carlos.perez@email.com").direccion("Avenida Siempre Viva 742")
                .contactoEmergencia("María Pérez").telefonoEmergencia("555-4444")
                .tipoSangre("A+").alergias("Ninguna").seguroMedico("SaludTotal")
                .estadoCivil("Casado").ocupacion("Arquitecto").activo(true)
                .build()));
            
            pacientes.add(pacienteRepository.save(PacienteEntity.builder()
                .nombres("Ana").apellidos("García")
                .numeroIdentificacion("34567890123")
                .fechaNacimiento(LocalDate.of(1978,1,30)).genero("FEMENINO")
                .telefono("555-5555").email("ana.garcia@email.com").direccion("Carrera 10 #20-30")
                .contactoEmergencia("Luis García").telefonoEmergencia("555-6666")
                .tipoSangre("B+").alergias("Penicilina").seguroMedico("Medisalud")
                .estadoCivil("Divorciada").ocupacion("Abogada").activo(true)
                .build()));
                
            System.out.println("✅ " + pacientes.size() + " pacientes creados");
        }

        // Guardar médicos y capturar sus IDs
        List<MedicoEntity> medicos = new ArrayList<>();
        if (medicoRepository.count() == 0) {
            medicos.add(medicoRepository.save(MedicoEntity.builder()
                .nombres("Juan").apellidos("Pérez")
                .especializacion("Cardiología").numeroLicencia("LIC001")
                .telefono("555-1234").email("juan.perez@clinica.com")
                .anosExperiencia(15).tarifaConsulta(BigDecimal.valueOf(150))
                .consultorio("Consultorio A").universidad("Universidad Nacional").activo(true)
                .build()));
                
            medicos.add(medicoRepository.save(MedicoEntity.builder()
                .nombres("María").apellidos("López")
                .especializacion("Pediatría").numeroLicencia("LIC002")
                .telefono("555-2345").email("maria.lopez@clinica.com")
                .anosExperiencia(10).tarifaConsulta(BigDecimal.valueOf(120))
                .consultorio("Consultorio B").universidad("Universidad Central").activo(true)
                .build()));
                
            medicos.add(medicoRepository.save(MedicoEntity.builder()
                .nombres("Carlos").apellidos("García")
                .especializacion("Dermatología").numeroLicencia("LIC003")
                .telefono("555-3456").email("carlos.garcia@clinica.com")
                .anosExperiencia(8).tarifaConsulta(BigDecimal.valueOf(100))
                .consultorio("Consultorio C").universidad("Universidad del Valle").activo(true)
                .build()));
                
            System.out.println("✅ " + medicos.size() + " médicos creados");
        }

        // Guardar medicamentos
        if (medicamentoRepository.count() == 0) {
            medicamentoRepository.save(MedicamentoEntity.builder()
                .nombre("Paracetamol").descripcion("Analgésico y antipirético")
                .codigo("MED001").precio(BigDecimal.valueOf(12.5))
                .stock(150).stockMinimo(20)
                .categoria("Analgésicos").fabricante("Genfar")
                .requiereReceta(false).activo(true)
                .build());
                
            medicamentoRepository.save(MedicamentoEntity.builder()
                .nombre("Ibuprofeno").descripcion("Antiinflamatorio y analgésico")
                .codigo("MED002").precio(BigDecimal.valueOf(10))
                .stock(200).stockMinimo(30)
                .categoria("Antiinflamatorios").fabricante("Bago")
                .requiereReceta(false).activo(true)
                .build());
                
            System.out.println("✅ 2 medicamentos creados");
        }

        // Crear citas usando los IDs reales de pacientes y médicos
        if (citaRepository.count() == 0 && !pacientes.isEmpty() && !medicos.isEmpty()) {
            citaRepository.save(CitaEntity.builder()
                .idPaciente(pacientes.get(0).getId())
                .idMedico(medicos.get(0).getId())
                .fechaCita(LocalDateTime.of(2025,6,15,9,0))
                .motivoConsulta("Consulta control cardiológico").estado("PROGRAMADA")
                .build());
                
            citaRepository.save(CitaEntity.builder()
                .idPaciente(pacientes.get(1).getId())
                .idMedico(medicos.get(1).getId())
                .fechaCita(LocalDateTime.of(2025,6,16,10,0))
                .motivoConsulta("Chequeo pediátrico general").estado("PROGRAMADA")
                .build());
                
            if (pacientes.size() > 2 && medicos.size() > 2) {
                citaRepository.save(CitaEntity.builder()
                    .idPaciente(pacientes.get(2).getId())
                    .idMedico(medicos.get(2).getId())
                    .fechaCita(LocalDateTime.of(2025,6,17,11,0))
                    .motivoConsulta("Consulta dermatológica").estado("PROGRAMADA")
                    .build());
            }
            
            System.out.println("✅ Citas médicas creadas con IDs válidos");
        }

        System.out.println("✅ Datos de ejemplo cargados correctamente.");
        System.out.println("🏥 Sistema de Salud Digital listo para usar.");
    }
}