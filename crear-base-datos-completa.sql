-- =====================================================
-- SCRIPT DE CREACIÓN COMPLETA DE BASE DE DATOS
-- Sistema de Salud Digital
-- =====================================================
-- 
-- Este script crea la base de datos completa desde cero
-- Basado en el análisis de las entidades JPA del sistema
-- Ejecutar en PostgreSQL 12+
--
-- Autor: Sistema de Salud Digital
-- Fecha: 2025-06-19
-- Versión: 1.0
-- =====================================================

-- Configuración inicial
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- =====================================================
-- 1. CREAR BASE DE DATOS (ejecutar como superusuario)
-- =====================================================

-- Terminar conexiones existentes (si las hay)
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'sistema_salud' AND pid <> pg_backend_pid();

-- Eliminar base de datos si existe
DROP DATABASE IF EXISTS sistema_salud;

-- Crear nueva base de datos
CREATE DATABASE sistema_salud
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Spain.1252'
    LC_CTYPE = 'Spanish_Spain.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Conectar a la nueva base de datos
\c sistema_salud;

-- =====================================================
-- 2. CONFIGURACIÓN DE ESQUEMA
-- =====================================================

-- Configurar esquema público
ALTER SCHEMA public OWNER TO postgres;
COMMENT ON SCHEMA public IS 'Esquema principal del Sistema de Salud Digital';

-- =====================================================
-- 3. SECUENCIAS (AUTO INCREMENT)
-- =====================================================

-- Secuencia para usuarios
CREATE SEQUENCE usuarios_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Secuencia para pacientes
CREATE SEQUENCE pacientes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Secuencia para médicos
CREATE SEQUENCE medicos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Secuencia para citas
CREATE SEQUENCE citas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Secuencia para medicamentos
CREATE SEQUENCE medicamentos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Secuencia para recetas
CREATE SEQUENCE recetas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Secuencia para historial médico
CREATE SEQUENCE historial_medico_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Secuencia para resultados de laboratorio
CREATE SEQUENCE resultado_laboratorio_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- =====================================================
-- 4. TABLA USUARIOS (Base del sistema de autenticación)
-- =====================================================

CREATE TABLE usuarios (
    id BIGINT NOT NULL DEFAULT nextval('usuarios_id_seq'),
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    correo_electronico VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL CHECK (rol IN ('ADMINISTRADOR', 'MEDICO', 'PACIENTE', 'ENFERMERO')),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT pk_usuarios PRIMARY KEY (id)
);

-- Índices para usuarios
CREATE INDEX idx_usuarios_nombre_usuario ON usuarios(nombre_usuario);
CREATE INDEX idx_usuarios_email ON usuarios(correo_electronico);
CREATE INDEX idx_usuarios_rol ON usuarios(rol);
CREATE INDEX idx_usuarios_activo ON usuarios(activo);

-- Comentarios para usuarios
COMMENT ON TABLE usuarios IS 'Tabla principal de usuarios del sistema';
COMMENT ON COLUMN usuarios.id IS 'Identificador único del usuario';
COMMENT ON COLUMN usuarios.nombre_usuario IS 'Nombre de usuario para login';
COMMENT ON COLUMN usuarios.correo_electronico IS 'Email único del usuario';
COMMENT ON COLUMN usuarios.contrasena IS 'Contraseña hasheada del usuario';
COMMENT ON COLUMN usuarios.rol IS 'Rol del usuario en el sistema';

-- =====================================================
-- 5. TABLA PACIENTES
-- =====================================================

CREATE TABLE pacientes (
    id BIGINT NOT NULL DEFAULT nextval('pacientes_id_seq'),
    id_usuario BIGINT,
    
    -- Información personal básica
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    genero VARCHAR(10) CHECK (genero IN ('MASCULINO', 'FEMENINO', 'OTRO')),
    
    -- Información de contacto
    telefono VARCHAR(20),
    correo_electronico VARCHAR(100),
    direccion TEXT,
    
    -- Contacto de emergencia
    contacto_emergencia VARCHAR(100),
    telefono_emergencia VARCHAR(20),
    
    -- Información médica
    tipo_sangre VARCHAR(5),
    alergias TEXT,
    seguro_medico VARCHAR(100),
    
    -- Información sociodemográfica
    numero_identificacion VARCHAR(20) UNIQUE,
    estado_civil VARCHAR(20),
    ocupacion VARCHAR(100),
    
    -- Control de estado
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT pk_pacientes PRIMARY KEY (id),
    CONSTRAINT fk_pacientes_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE SET NULL
);

-- Índices para pacientes
CREATE INDEX idx_pacientes_nombres ON pacientes(nombres);
CREATE INDEX idx_pacientes_apellidos ON pacientes(apellidos);
CREATE INDEX idx_pacientes_numero_identificacion ON pacientes(numero_identificacion);
CREATE INDEX idx_pacientes_email ON pacientes(correo_electronico);
CREATE INDEX idx_pacientes_activo ON pacientes(activo);
CREATE INDEX idx_pacientes_usuario ON pacientes(id_usuario);

-- Comentarios para pacientes
COMMENT ON TABLE pacientes IS 'Tabla de pacientes del sistema de salud';
COMMENT ON COLUMN pacientes.numero_identificacion IS 'Documento de identidad único';
COMMENT ON COLUMN pacientes.tipo_sangre IS 'Tipo de sangre del paciente (A+, B-, O+, etc.)';

-- =====================================================
-- 6. TABLA MÉDICOS
-- =====================================================

CREATE TABLE medicos (
    id BIGINT NOT NULL DEFAULT nextval('medicos_id_seq'),
    id_usuario BIGINT,
    
    -- Datos personales
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    
    -- Información profesional
    especializacion VARCHAR(100) NOT NULL,
    numero_licencia VARCHAR(50) NOT NULL UNIQUE,
    anos_experiencia INTEGER CHECK (anos_experiencia >= 0),
    universidad VARCHAR(100),
    fecha_graduacion DATE,
    
    -- Información laboral
    fecha_contratacion DATE,
    salario DECIMAL(10,2),
    horario_trabajo VARCHAR(100),
    consultorio VARCHAR(50),
    
    -- Control de estado
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT pk_medicos PRIMARY KEY (id),
    CONSTRAINT fk_medicos_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE SET NULL
);

-- Índices para médicos
CREATE INDEX idx_medicos_nombres ON medicos(nombres);
CREATE INDEX idx_medicos_apellidos ON medicos(apellidos);
CREATE INDEX idx_medicos_especializacion ON medicos(especializacion);
CREATE INDEX idx_medicos_numero_licencia ON medicos(numero_licencia);
CREATE INDEX idx_medicos_activo ON medicos(activo);
CREATE INDEX idx_medicos_usuario ON medicos(id_usuario);

-- Comentarios para médicos
COMMENT ON TABLE medicos IS 'Tabla de médicos del sistema';
COMMENT ON COLUMN medicos.numero_licencia IS 'Número de licencia médica único';
COMMENT ON COLUMN medicos.especializacion IS 'Especialidad médica del doctor';

-- =====================================================
-- 7. TABLA CITAS
-- =====================================================

CREATE TABLE citas (
    id BIGINT NOT NULL DEFAULT nextval('citas_id_seq'),
    id_paciente BIGINT NOT NULL,
    id_medico BIGINT NOT NULL,
    
    -- Información de la cita
    fecha_cita TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    motivo_consulta TEXT,
    estado VARCHAR(20) DEFAULT 'PROGRAMADA' CHECK (estado IN ('PROGRAMADA', 'CONFIRMADA', 'EN_CURSO', 'COMPLETADA', 'CANCELADA', 'NO_ASISTIO')),
    observaciones TEXT,
    
    -- Control de fechas
    fecha_creacion TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT pk_citas PRIMARY KEY (id),
    CONSTRAINT fk_citas_paciente FOREIGN KEY (id_paciente) REFERENCES pacientes(id) ON DELETE CASCADE,
    CONSTRAINT fk_citas_medico FOREIGN KEY (id_medico) REFERENCES medicos(id) ON DELETE CASCADE
);

-- Índices para citas
CREATE INDEX idx_citas_fecha ON citas(fecha_cita);
CREATE INDEX idx_citas_paciente ON citas(id_paciente);
CREATE INDEX idx_citas_medico ON citas(id_medico);
CREATE INDEX idx_citas_estado ON citas(estado);
CREATE INDEX idx_citas_fecha_estado ON citas(fecha_cita, estado);

-- Comentarios para citas
COMMENT ON TABLE citas IS 'Tabla de citas médicas programadas';
COMMENT ON COLUMN citas.estado IS 'Estado actual de la cita médica';

-- =====================================================
-- 8. TABLA MEDICAMENTOS
-- =====================================================

CREATE TABLE medicamentos (
    id BIGINT NOT NULL DEFAULT nextval('medicamentos_id_seq'),
    
    -- Información básica
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    codigo VARCHAR(50) UNIQUE,
    fabricante VARCHAR(255),
    categoria VARCHAR(100),
    
    -- Información técnica
    forma_dosificacion VARCHAR(50),
    concentracion VARCHAR(100),
    principio_activo VARCHAR(255),
    
    -- Información comercial
    precio DECIMAL(10,2) CHECK (precio >= 0),
    stock INTEGER DEFAULT 0 CHECK (stock >= 0),
    stock_minimo INTEGER DEFAULT 10 CHECK (stock_minimo >= 0),
    
    -- Fechas importantes
    fecha_vencimiento DATE,
    lote VARCHAR(50),
    
    -- Control de estado
    activo BOOLEAN DEFAULT TRUE,
    requiere_receta BOOLEAN DEFAULT FALSE,
    fecha_creacion TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT pk_medicamentos PRIMARY KEY (id)
);

-- Índices para medicamentos
CREATE INDEX idx_medicamentos_nombre ON medicamentos(nombre);
CREATE INDEX idx_medicamentos_codigo ON medicamentos(codigo);
CREATE INDEX idx_medicamentos_categoria ON medicamentos(categoria);
CREATE INDEX idx_medicamentos_stock ON medicamentos(stock);
CREATE INDEX idx_medicamentos_activo ON medicamentos(activo);
CREATE INDEX idx_medicamentos_vencimiento ON medicamentos(fecha_vencimiento);

-- Comentarios para medicamentos
COMMENT ON TABLE medicamentos IS 'Tabla de medicamentos del sistema';
COMMENT ON COLUMN medicamentos.stock_minimo IS 'Stock mínimo para generar alertas';
COMMENT ON COLUMN medicamentos.requiere_receta IS 'Indica si el medicamento requiere receta médica';

-- =====================================================
-- 9. TABLA RECETAS
-- =====================================================

CREATE TABLE recetas (
    id BIGINT NOT NULL DEFAULT nextval('recetas_id_seq'),
    id_cita BIGINT NOT NULL,
    id_paciente BIGINT NOT NULL,
    id_medico BIGINT NOT NULL,
    
    -- Información de la receta
    fecha_prescripcion TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    diagnostico TEXT,
    observaciones TEXT,
    
    -- Control de estado
    activa BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT pk_recetas PRIMARY KEY (id),
    CONSTRAINT fk_recetas_cita FOREIGN KEY (id_cita) REFERENCES citas(id) ON DELETE CASCADE,
    CONSTRAINT fk_recetas_paciente FOREIGN KEY (id_paciente) REFERENCES pacientes(id) ON DELETE CASCADE,
    CONSTRAINT fk_recetas_medico FOREIGN KEY (id_medico) REFERENCES medicos(id) ON DELETE CASCADE
);

-- Índices para recetas
CREATE INDEX idx_recetas_cita ON recetas(id_cita);
CREATE INDEX idx_recetas_paciente ON recetas(id_paciente);
CREATE INDEX idx_recetas_medico ON recetas(id_medico);
CREATE INDEX idx_recetas_fecha ON recetas(fecha_prescripcion);
CREATE INDEX idx_recetas_activa ON recetas(activa);

-- Comentarios para recetas
COMMENT ON TABLE recetas IS 'Tabla de recetas médicas';
COMMENT ON COLUMN recetas.activa IS 'Indica si la receta está activa o ha sido utilizada';

-- =====================================================
-- 10. TABLA DETALLE_RECETAS (Medicamentos en recetas)
-- =====================================================

CREATE TABLE detalle_recetas (
    id BIGSERIAL PRIMARY KEY,
    id_receta BIGINT NOT NULL,
    id_medicamento BIGINT NOT NULL,
    
    -- Información de dosificación
    dosis VARCHAR(100) NOT NULL,
    frecuencia VARCHAR(100) NOT NULL,
    duracion VARCHAR(100),
    cantidad INTEGER NOT NULL CHECK (cantidad > 0),
    
    -- Instrucciones
    instrucciones TEXT,
    
    CONSTRAINT fk_detalle_recetas_receta FOREIGN KEY (id_receta) REFERENCES recetas(id) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_recetas_medicamento FOREIGN KEY (id_medicamento) REFERENCES medicamentos(id) ON DELETE CASCADE
);

-- Índices para detalle_recetas
CREATE INDEX idx_detalle_recetas_receta ON detalle_recetas(id_receta);
CREATE INDEX idx_detalle_recetas_medicamento ON detalle_recetas(id_medicamento);

-- =====================================================
-- 11. TABLA HISTORIAL_MEDICO
-- =====================================================

CREATE TABLE historial_medico (
    id BIGINT NOT NULL DEFAULT nextval('historial_medico_id_seq'),
    id_paciente BIGINT NOT NULL,
    id_medico BIGINT NOT NULL,
    id_cita BIGINT,
    
    -- Información médica
    fecha_consulta TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    motivo_consulta TEXT,
    sintomas TEXT,
    diagnostico TEXT,
    tratamiento TEXT,
    observaciones TEXT,
    
    -- Signos vitales
    presion_arterial VARCHAR(20),
    frecuencia_cardiaca INTEGER,
    temperatura DECIMAL(4,2),
    peso DECIMAL(5,2),
    altura DECIMAL(5,2),
    
    -- Control
    fecha_creacion TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT pk_historial_medico PRIMARY KEY (id),
    CONSTRAINT fk_historial_paciente FOREIGN KEY (id_paciente) REFERENCES pacientes(id) ON DELETE CASCADE,
    CONSTRAINT fk_historial_medico FOREIGN KEY (id_medico) REFERENCES medicos(id) ON DELETE CASCADE,
    CONSTRAINT fk_historial_cita FOREIGN KEY (id_cita) REFERENCES citas(id) ON DELETE SET NULL
);

-- Índices para historial_medico
CREATE INDEX idx_historial_paciente ON historial_medico(id_paciente);
CREATE INDEX idx_historial_medico ON historial_medico(id_medico);
CREATE INDEX idx_historial_fecha ON historial_medico(fecha_consulta);
CREATE INDEX idx_historial_cita ON historial_medico(id_cita);

-- Comentarios para historial_medico
COMMENT ON TABLE historial_medico IS 'Historial médico de pacientes';
COMMENT ON COLUMN historial_medico.presion_arterial IS 'Presión arterial en formato 120/80';

-- =====================================================
-- 12. TABLA RESULTADO_LABORATORIO
-- =====================================================

CREATE TABLE resultado_laboratorio (
    id BIGINT NOT NULL DEFAULT nextval('resultado_laboratorio_id_seq'),
    id_paciente BIGINT NOT NULL,
    id_medico BIGINT NOT NULL,
    id_cita BIGINT,
    
    -- Información del examen
    tipo_examen VARCHAR(100) NOT NULL,
    nombre_examen VARCHAR(255) NOT NULL,
    fecha_examen TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    fecha_resultado TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    -- Resultados
    resultado TEXT NOT NULL,
    valores_referencia TEXT,
    unidades VARCHAR(50),
    estado VARCHAR(20) DEFAULT 'NORMAL' CHECK (estado IN ('NORMAL', 'ANORMAL', 'CRITICO', 'PENDIENTE')),
    
    -- Información adicional
    laboratorio VARCHAR(100),
    tecnico_responsable VARCHAR(100),
    observaciones TEXT,
    
    -- Control
    fecha_creacion TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT pk_resultado_laboratorio PRIMARY KEY (id),
    CONSTRAINT fk_resultado_paciente FOREIGN KEY (id_paciente) REFERENCES pacientes(id) ON DELETE CASCADE,
    CONSTRAINT fk_resultado_medico FOREIGN KEY (id_medico) REFERENCES medicos(id) ON DELETE CASCADE,
    CONSTRAINT fk_resultado_cita FOREIGN KEY (id_cita) REFERENCES citas(id) ON DELETE SET NULL
);

-- Índices para resultado_laboratorio
CREATE INDEX idx_resultado_paciente ON resultado_laboratorio(id_paciente);
CREATE INDEX idx_resultado_medico ON resultado_laboratorio(id_medico);
CREATE INDEX idx_resultado_fecha ON resultado_laboratorio(fecha_examen);
CREATE INDEX idx_resultado_tipo ON resultado_laboratorio(tipo_examen);
CREATE INDEX idx_resultado_estado ON resultado_laboratorio(estado);

-- =====================================================
-- 13. TRIGGERS PARA FECHAS DE ACTUALIZACIÓN
-- =====================================================

-- Función para actualizar fecha_actualizacion
CREATE OR REPLACE FUNCTION actualizar_fecha_modificacion()
RETURNS TRIGGER AS $$
BEGIN
    NEW.fecha_actualizacion = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers para todas las tablas
CREATE TRIGGER trigger_usuarios_update
    BEFORE UPDATE ON usuarios
    FOR EACH ROW
    EXECUTE FUNCTION actualizar_fecha_modificacion();

CREATE TRIGGER trigger_pacientes_update
    BEFORE UPDATE ON pacientes
    FOR EACH ROW
    EXECUTE FUNCTION actualizar_fecha_modificacion();

CREATE TRIGGER trigger_medicos_update
    BEFORE UPDATE ON medicos
    FOR EACH ROW
    EXECUTE FUNCTION actualizar_fecha_modificacion();

CREATE TRIGGER trigger_citas_update
    BEFORE UPDATE ON citas
    FOR EACH ROW
    EXECUTE FUNCTION actualizar_fecha_modificacion();

CREATE TRIGGER trigger_medicamentos_update
    BEFORE UPDATE ON medicamentos
    FOR EACH ROW
    EXECUTE FUNCTION actualizar_fecha_modificacion();

CREATE TRIGGER trigger_recetas_update
    BEFORE UPDATE ON recetas
    FOR EACH ROW
    EXECUTE FUNCTION actualizar_fecha_modificacion();

CREATE TRIGGER trigger_historial_update
    BEFORE UPDATE ON historial_medico
    FOR EACH ROW
    EXECUTE FUNCTION actualizar_fecha_modificacion();

CREATE TRIGGER trigger_resultado_update
    BEFORE UPDATE ON resultado_laboratorio
    FOR EACH ROW
    EXECUTE FUNCTION actualizar_fecha_modificacion();

-- =====================================================
-- 14. DATOS INICIALES
-- =====================================================

-- Usuarios iniciales (contraseñas hasheadas con BCrypt)
INSERT INTO usuarios (nombre_usuario, correo_electronico, contrasena, rol) VALUES
('admin', 'admin@sistemasalud.com', '$2a$10$8cG1Uy6E7G1FpQ8K9J2V2e7XHpzG3p5xF8qP9Q2V1e8XHpzG3p5xF', 'ADMINISTRADOR'),
('doctor1', 'doctor1@sistemasalud.com', '$2a$10$8cG1Uy6E7G1FpQ8K9J2V2e7XHpzG3p5xF8qP9Q2V1e8XHpzG3p5xF', 'MEDICO'),
('enfermero1', 'enfermero1@sistemasalud.com', '$2a$10$8cG1Uy6E7G1FpQ8K9J2V2e7XHpzG3p5xF8qP9Q2V1e8XHpzG3p5xF', 'ENFERMERO');

-- Médicos iniciales
INSERT INTO medicos (id_usuario, nombres, apellidos, especializacion, numero_licencia, anos_experiencia, email) VALUES
(2, 'Dr. Juan Carlos', 'Pérez López', 'Medicina General', 'LIC-001-2020', 5, 'doctor1@sistemasalud.com'),
(NULL, 'Dra. María Elena', 'González Rodríguez', 'Cardiología', 'LIC-002-2018', 8, 'cardiologia@sistemasalud.com'),
(NULL, 'Dr. Roberto', 'Martínez Silva', 'Pediatría', 'LIC-003-2019', 6, 'pediatria@sistemasalud.com');

-- Medicamentos iniciales
INSERT INTO medicamentos (nombre, descripcion, categoria, precio, stock, forma_dosificacion, requiere_receta) VALUES
('Paracetamol 500mg', 'Analgésico y antipirético', 'Analgésicos', 2.50, 100, 'Tableta', FALSE),
('Ibuprofeno 400mg', 'Antiinflamatorio no esteroideo', 'Antiinflamatorios', 3.75, 80, 'Tableta', FALSE),
('Amoxicilina 500mg', 'Antibiótico de amplio espectro', 'Antibióticos', 8.90, 50, 'Cápsula', TRUE),
('Losartán 50mg', 'Antihipertensivo', 'Cardiovasculares', 12.30, 60, 'Tableta', TRUE),
('Metformina 500mg', 'Antidiabético', 'Endocrinos', 5.60, 75, 'Tableta', TRUE);

-- =====================================================
-- 15. VISTAS ÚTILES
-- =====================================================

-- Vista de pacientes con información completa
CREATE VIEW vista_pacientes_completa AS
SELECT 
    p.id,
    p.nombres,
    p.apellidos,
    p.nombres || ' ' || p.apellidos AS nombre_completo,
    p.fecha_nacimiento,
    EXTRACT(YEAR FROM AGE(p.fecha_nacimiento)) AS edad,
    p.genero,
    p.telefono,
    p.correo_electronico,
    p.numero_identificacion,
    p.tipo_sangre,
    p.activo,
    u.nombre_usuario,
    u.correo_electronico AS email_usuario
FROM pacientes p
LEFT JOIN usuarios u ON p.id_usuario = u.id;

-- Vista de citas con información de pacientes y médicos
CREATE VIEW vista_citas_completa AS
SELECT 
    c.id,
    c.fecha_cita,
    c.estado,
    c.motivo_consulta,
    p.nombres || ' ' || p.apellidos AS paciente,
    p.telefono AS telefono_paciente,
    m.nombres || ' ' || m.apellidos AS medico,
    m.especializacion,
    c.observaciones
FROM citas c
JOIN pacientes p ON c.id_paciente = p.id
JOIN medicos m ON c.id_medico = m.id;

-- Vista de medicamentos con stock bajo
CREATE VIEW vista_medicamentos_stock_bajo AS
SELECT 
    id,
    nombre,
    categoria,
    stock,
    stock_minimo,
    precio,
    fecha_vencimiento,
    (stock_minimo - stock) AS cantidad_a_reponer
FROM medicamentos
WHERE stock <= stock_minimo AND activo = TRUE;

-- =====================================================
-- 16. FUNCIONES ÚTILES
-- =====================================================

-- Función para calcular edad
CREATE OR REPLACE FUNCTION calcular_edad(fecha_nacimiento DATE)
RETURNS INTEGER AS $$
BEGIN
    RETURN EXTRACT(YEAR FROM AGE(fecha_nacimiento));
END;
$$ LANGUAGE plpgsql;

-- Función para obtener próximas citas de un médico
CREATE OR REPLACE FUNCTION obtener_proximas_citas(medico_id BIGINT, dias INTEGER DEFAULT 7)
RETURNS TABLE(
    cita_id BIGINT,
    fecha_cita TIMESTAMP,
    paciente VARCHAR,
    telefono VARCHAR,
    motivo TEXT
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        c.id,
        c.fecha_cita,
        (p.nombres || ' ' || p.apellidos)::VARCHAR,
        p.telefono,
        c.motivo_consulta
    FROM citas c
    JOIN pacientes p ON c.id_paciente = p.id
    WHERE c.id_medico = medico_id
    AND c.fecha_cita BETWEEN NOW() AND (NOW() + INTERVAL '1 day' * dias)
    AND c.estado IN ('PROGRAMADA', 'CONFIRMADA')
    ORDER BY c.fecha_cita;
END;
$$ LANGUAGE plpgsql;

-- =====================================================
-- 17. PERMISOS Y SEGURIDAD
-- =====================================================

-- Revocar permisos públicos
REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT USAGE ON SCHEMA public TO postgres;

-- Conceder permisos específicos
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO postgres;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO postgres;

-- =====================================================
-- 18. VERIFICACIÓN FINAL
-- =====================================================

-- Mostrar resumen de tablas creadas
SELECT 
    schemaname,
    tablename,
    tableowner
FROM pg_tables 
WHERE schemaname = 'public'
ORDER BY tablename;

-- Mostrar conteo de registros iniciales
SELECT 'usuarios' as tabla, COUNT(*) as registros FROM usuarios
UNION ALL
SELECT 'medicos' as tabla, COUNT(*) as registros FROM medicos
UNION ALL
SELECT 'medicamentos' as tabla, COUNT(*) as registros FROM medicamentos;

-- =====================================================
-- FINALIZACIÓN
-- =====================================================

-- Mensaje de confirmación
DO $$
BEGIN
    RAISE NOTICE '========================================';
    RAISE NOTICE 'BASE DE DATOS CREADA EXITOSAMENTE';
    RAISE NOTICE '========================================';
    RAISE NOTICE 'Tablas principales: %, %, %, %, %', 
        'usuarios', 'pacientes', 'medicos', 'citas', 'medicamentos';
    RAISE NOTICE 'Datos iniciales insertados';
    RAISE NOTICE 'Sistema listo para usar';
    RAISE NOTICE '========================================';
END $$;

-- Optimizar estadísticas
ANALYZE;

-- Fin del script
