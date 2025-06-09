package com.clinica.salud.config;

import com.clinica.salud.entity.UsuarioEntity;
import com.clinica.salud.entity.RolUsuario;
import com.clinica.salud.repository.jpa.UsuarioJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Inicializa datos de prueba en la base de datos
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioJpaRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Crear usuarios de prueba si no existen
        if (usuarioRepository.count() == 0) {
            
            // ===== ADMINISTRADORES =====
            UsuarioEntity admin = new UsuarioEntity();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@clinica.com");
            admin.setRol(RolUsuario.ADMINISTRADOR);
            admin.setActivo(true);
            usuarioRepository.save(admin);

            UsuarioEntity admin2 = new UsuarioEntity();
            admin2.setUsername("admin_sistema");
            admin2.setPassword(passwordEncoder.encode("admin123"));
            admin2.setEmail("admin.sistema@saluddigital.com");
            admin2.setRol(RolUsuario.ADMINISTRADOR);
            admin2.setActivo(true);
            usuarioRepository.save(admin2);

            UsuarioEntity admin3 = new UsuarioEntity();
            admin3.setUsername("administrador");
            admin3.setPassword(passwordEncoder.encode("admin123"));
            admin3.setEmail("administrador@saluddigital.com");
            admin3.setRol(RolUsuario.ADMINISTRADOR);
            admin3.setActivo(true);
            usuarioRepository.save(admin3);

            // ===== MÉDICOS =====
            UsuarioEntity doctor = new UsuarioEntity();
            doctor.setUsername("doctor");
            doctor.setPassword(passwordEncoder.encode("admin123"));
            doctor.setEmail("doctor@clinica.com");
            doctor.setRol(RolUsuario.MEDICO);
            doctor.setActivo(true);
            usuarioRepository.save(doctor);

            UsuarioEntity dr1 = new UsuarioEntity();
            dr1.setUsername("dr.smith");
            dr1.setPassword(passwordEncoder.encode("admin123"));
            dr1.setEmail("dr.smith@saluddigital.com");
            dr1.setRol(RolUsuario.MEDICO);
            dr1.setActivo(true);
            usuarioRepository.save(dr1);

            UsuarioEntity dr2 = new UsuarioEntity();
            dr2.setUsername("dr.martinez");
            dr2.setPassword(passwordEncoder.encode("admin123"));
            dr2.setEmail("dr.martinez@saluddigital.com");
            dr2.setRol(RolUsuario.MEDICO);
            dr2.setActivo(true);
            usuarioRepository.save(dr2);

            UsuarioEntity dr3 = new UsuarioEntity();
            dr3.setUsername("dr.garcia");
            dr3.setPassword(passwordEncoder.encode("admin123"));
            dr3.setEmail("dr.garcia@saluddigital.com");
            dr3.setRol(RolUsuario.MEDICO);
            dr3.setActivo(true);
            usuarioRepository.save(dr3);

            UsuarioEntity dra1 = new UsuarioEntity();
            dra1.setUsername("dra.lopez");
            dra1.setPassword(passwordEncoder.encode("admin123"));
            dra1.setEmail("dra.lopez@saluddigital.com");
            dra1.setRol(RolUsuario.MEDICO);
            dra1.setActivo(true);
            usuarioRepository.save(dra1);

            UsuarioEntity medico1 = new UsuarioEntity();
            medico1.setUsername("medico_guardia");
            medico1.setPassword(passwordEncoder.encode("admin123"));
            medico1.setEmail("medico.guardia@saluddigital.com");
            medico1.setRol(RolUsuario.MEDICO);
            medico1.setActivo(true);
            usuarioRepository.save(medico1);

            // ===== PACIENTES =====
            UsuarioEntity paciente = new UsuarioEntity();
            paciente.setUsername("paciente");
            paciente.setPassword(passwordEncoder.encode("admin123"));
            paciente.setEmail("paciente@clinica.com");
            paciente.setRol(RolUsuario.PACIENTE);
            paciente.setActivo(true);
            usuarioRepository.save(paciente);

            UsuarioEntity pac1 = new UsuarioEntity();
            pac1.setUsername("juan.perez");
            pac1.setPassword(passwordEncoder.encode("admin123"));
            pac1.setEmail("juan.perez@email.com");
            pac1.setRol(RolUsuario.PACIENTE);
            pac1.setActivo(true);
            usuarioRepository.save(pac1);

            UsuarioEntity pac2 = new UsuarioEntity();
            pac2.setUsername("maria.garcia");
            pac2.setPassword(passwordEncoder.encode("admin123"));
            pac2.setEmail("maria.garcia@email.com");
            pac2.setRol(RolUsuario.PACIENTE);
            pac2.setActivo(true);
            usuarioRepository.save(pac2);

            UsuarioEntity pac3 = new UsuarioEntity();
            pac3.setUsername("carlos.rodriguez");
            pac3.setPassword(passwordEncoder.encode("admin123"));
            pac3.setEmail("carlos.rodriguez@email.com");
            pac3.setRol(RolUsuario.PACIENTE);
            pac3.setActivo(true);
            usuarioRepository.save(pac3);

            UsuarioEntity pac4 = new UsuarioEntity();
            pac4.setUsername("ana.hernandez");
            pac4.setPassword(passwordEncoder.encode("admin123"));
            pac4.setEmail("ana.hernandez@email.com");
            pac4.setRol(RolUsuario.PACIENTE);
            pac4.setActivo(true);
            usuarioRepository.save(pac4);

            UsuarioEntity pac5 = new UsuarioEntity();
            pac5.setUsername("luis.morales");
            pac5.setPassword(passwordEncoder.encode("admin123"));
            pac5.setEmail("luis.morales@email.com");
            pac5.setRol(RolUsuario.PACIENTE);
            pac5.setActivo(true);
            usuarioRepository.save(pac5);

            System.out.println("=== SISTEMA DE SALUD DIGITAL - CREDENCIALES CREADAS ===");
            System.out.println("👑 ADMINISTRADORES:");
            System.out.println("- admin/admin123 (ADMINISTRADOR)");
            System.out.println("- admin_sistema/admin123 (ADMINISTRADOR)");
            System.out.println("- administrador/admin123 (ADMINISTRADOR)");
            System.out.println("");
            System.out.println("👨‍⚕️ MÉDICOS:");
            System.out.println("- doctor/admin123 (MEDICO)");
            System.out.println("- dr.smith/admin123 (MEDICO)");
            System.out.println("- dr.martinez/admin123 (MEDICO)");
            System.out.println("- dr.garcia/admin123 (MEDICO)");
            System.out.println("- dra.lopez/admin123 (MEDICO)");
            System.out.println("- medico_guardia/admin123 (MEDICO)");
            System.out.println("");
            System.out.println("👥 PACIENTES:");
            System.out.println("- paciente/admin123 (PACIENTE)");
            System.out.println("- juan.perez/admin123 (PACIENTE)");
            System.out.println("- maria.garcia/admin123 (PACIENTE)");
            System.out.println("- carlos.rodriguez/admin123 (PACIENTE)");
            System.out.println("- ana.hernandez/admin123 (PACIENTE)");
            System.out.println("- luis.morales/admin123 (PACIENTE)");
            System.out.println("");
            System.out.println("✅ Total: 15 usuarios creados con contraseñas BCrypt seguras");
            System.out.println("🔐 Todas las contraseñas: admin123");
        }
    }
}
