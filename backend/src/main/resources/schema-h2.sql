-- =====================================
-- ESQUEMA SIMPLIFICADO COMPATIBLE H2/PostgreSQL
-- Sistema de Salud Digital
-- =====================================

-- Tabla de usuarios (compatible con el DAO existente)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de médicos (compatible con el DAO existente)
CREATE TABLE IF NOT EXISTS doctors (
    doctor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    license_number VARCHAR(50) UNIQUE NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    schedule TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de pacientes (compatible con el DAO existente)
CREATE TABLE IF NOT EXISTS patients (
    patient_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    gender VARCHAR(20),
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    emergency_contact VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de medicamentos (compatible con el DAO existente)
CREATE TABLE IF NOT EXISTS medications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    dosage VARCHAR(50),
    side_effects TEXT,
    contraindications TEXT,
    price DECIMAL(10,2),
    stock INTEGER DEFAULT 0,
    expiration_date DATE,
    manufacturer VARCHAR(100),
    form VARCHAR(50), -- Agregado para compatibilidad con DAO
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de citas (compatible con el DAO existente)
CREATE TABLE IF NOT EXISTS appointments (
    appointment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    reason TEXT,
    status VARCHAR(20) DEFAULT 'PROGRAMADA',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de historiales médicos (compatible con el DAO existente)
CREATE TABLE IF NOT EXISTS medical_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    appointment_id BIGINT,
    diagnosis TEXT NOT NULL,
    treatment TEXT,
    prescription TEXT,
    notes TEXT,
    record_date DATE NOT NULL,
    follow_up_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de resultados de laboratorio (compatible con el DAO existente)
CREATE TABLE IF NOT EXISTS laboratory_results (
    result_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    test_name VARCHAR(100) NOT NULL,
    test_date DATE NOT NULL,
    results TEXT,
    reference_values TEXT,
    status VARCHAR(20) DEFAULT 'PENDIENTE',
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
