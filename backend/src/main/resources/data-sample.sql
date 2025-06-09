-- =====================================
-- DATOS INICIALES PARA EL SISTEMA DE SALUD DIGITAL
-- =====================================

-- Insertar usuarios iniciales (password: admin123 para todos)
INSERT INTO usuarios (username, email, password, role, activo, created_at, updated_at) VALUES
('admin', 'admin@saluddigital.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'ADMIN', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('doctor1', 'doctor1@saluddigital.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'DOCTOR', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('paciente1', 'paciente1@saluddigital.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'PATIENT', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('recepcionista1', 'recep1@saluddigital.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'RECEPCIONISTA', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insertar médicos de ejemplo
INSERT INTO medicos (first_name, last_name, specialization, license_number, phone, email, years_of_experience, consultation_fee, available_days, available_hours, created_at, updated_at) VALUES
('Juan', 'Pérez', 'Cardiología', 'LIC001', '555-1234', 'juan.perez@saluddigital.com', 15, 150.00, 'Lunes,Martes,Miércoles,Jueves,Viernes', '08:00-17:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Ana', 'García', 'Pediatría', 'LIC002', '555-5678', 'ana.garcia@saluddigital.com', 10, 120.00, 'Lunes,Martes,Miércoles,Jueves,Viernes', '09:00-18:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Carlos', 'Rodríguez', 'Medicina General', 'LIC003', '555-9999', 'carlos.rodriguez@saluddigital.com', 8, 100.00, 'Lunes,Martes,Miércoles,Jueves,Viernes,Sábado', '07:00-15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insertar pacientes de ejemplo
INSERT INTO pacientes (first_name, last_name, date_of_birth, gender, phone, address, emergency_contact, emergency_phone, blood_type, created_at, updated_at) VALUES
('María', 'López', '1985-03-15', 'FEMALE', '555-1111', 'Calle 123 #45-67', 'José López', '555-2222', 'O+', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Pedro', 'Martínez', '1990-07-22', 'MALE', '555-3333', 'Carrera 89 #12-34', 'Ana Martínez', '555-4444', 'A+', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sofía', 'Hernández', '1992-12-10', 'FEMALE', '555-5555', 'Avenida 56 #78-90', 'Luis Hernández', '555-6666', 'B+', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insertar medicamentos de ejemplo
INSERT INTO medicamentos (name, description, codigo, price, stock, manufacturer, category, dosage_form, concentration, instructions, is_active, requires_prescription, created_at, updated_at) VALUES
('Paracetamol', 'Analgésico y antipirético para el alivio del dolor y la fiebre', 'MED001', 12.50, 150, 'Laboratorios Genfar', 'Analgésicos', 'Tableta', '500mg', 'Tomar 1 tableta cada 8 horas', true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Amoxicilina', 'Antibiótico de amplio espectro para infecciones bacterianas', 'MED002', 45.00, 80, 'Laboratorios Novartis', 'Antibióticos', 'Cápsula', '250mg', 'Tomar 1 cápsula cada 8 horas por 7 días', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Loratadina', 'Antihistamínico para el tratamiento de alergias', 'MED003', 28.00, 75, 'Laboratorios Bayer', 'Antihistamínicos', 'Tableta', '10mg', 'Tomar 1 tableta al día', true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Omeprazol', 'Inhibidor de la bomba de protones para problemas digestivos', 'MED004', 38.50, 45, 'Laboratorios Pfizer', 'Gastroprotectores', 'Cápsula', '20mg', 'Tomar 1 cápsula en ayunas', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Salbutamol', 'Broncodilatador para el tratamiento del asma', 'MED005', 85.00, 25, 'Laboratorios GSK', 'Broncodilatadores', 'Inhalador', '100mcg/dosis', '2 puffs cada 6 horas según necesidad', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insertar citas de ejemplo
INSERT INTO citas (patient_id, doctor_id, appointment_date, appointment_time, status, reason, notes, created_at, updated_at) VALUES
(1, 1, '2025-06-05', '09:00:00', 'PROGRAMADA', 'Consulta cardiológica de control', 'Paciente con antecedentes de hipertensión', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, '2025-06-06', '10:30:00', 'PROGRAMADA', 'Consulta pediátrica', 'Control de crecimiento y desarrollo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, '2025-06-07', '14:00:00', 'PROGRAMADA', 'Consulta medicina general', 'Síntomas de gripe', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
('Complejo B', 'Suplemento vitamínico del complejo B', 'Variable', 'Laboratorios Centrum', 'Tableta', 45.50, 90, '2026-05-30', 'Raramente reacciones alérgicas', 'Alergia a vitaminas del complejo B', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Diclofenaco', 'Antiinflamatorio no esteroideo para dolor e inflamación', '50mg', 'Laboratorios Voltaren', 'Tableta', 18.75, 3, '2025-10-12', 'Dolor de estómago, náuseas, mareos', 'Úlcera gástrica, alergia a AINEs, embarazo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cetirizina', 'Antihistamínico de segunda generación para alergias', '10mg', 'Laboratorios Zyrtec', 'Jarabe', 55.00, 35, '2025-12-08', 'Somnolencia leve, dolor de cabeza', 'Alergia a cetirizina, insuficiencia renal severa', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



-- Insertar citas de ejemplo
INSERT INTO citas (id_paciente, id_medico, fecha_cita, hora_cita, motivo, estado, fecha_creacion, fecha_actualizacion) VALUES
(1, 1, '2024-01-15', '10:00:00', 'Consulta cardiológica rutinaria', 'PROGRAMADA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, '2024-01-16', '14:30:00', 'Chequeo médico general', 'PROGRAMADA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, '2024-01-17', '09:15:00', 'Control pediátrico', 'PROGRAMADA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insertar historiales médicos de ejemplo
INSERT INTO historiales_medicos (id_paciente, id_medico, id_cita, diagnostico, tratamiento, prescripcion, notas, visit_date, followup_date, fecha_creacion, fecha_actualizacion) VALUES
(1, 1, 1, 'Hipertensión arterial leve', 'Dieta baja en sodio y ejercicio', 'Amlodipino 5mg diario', 'Paciente colaborador, buen estado general', '2024-01-15', '2024-03-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 2, 'Estado de salud normal', 'Mantener hábitos saludables', 'Vitaminas multivitamínicas', 'Paciente en excelente condición física', '2024-01-16', '2024-07-16', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
