-- Insertar Centro de Salud
INSERT INTO CentroDeSalud (id, nombre, direccion, telefono, email, activo)
VALUES
    (1, 'Centro de Salud Central', 'Avenida Principal 123, Ciudad Salud', '555987654', 'info@cscentral.example.com', true);

-- Insertar Médicos
INSERT INTO Medico (id,nombre, apellidos, dni, numeroColegiado, telefono, email, centro_id)
VALUES
    (1,'Dr. Luis', 'Martínez', '22334455C', 'MED456', '555789012', 'luis.martinez@example.com', 1);

-- Insertar Pacientes
INSERT INTO Paciente (id,nombre, apellidos, dni, tarjeta_sanitaria, NUMERO_SEGURIDAD_SOCIAL, direccion, telefono, email, fecha_nacimiento, centro_id, medico_id)
VALUES
    (2,'Juan', 'Pérez', '12345678A', 'TS123456', 'SS987654', 'Calle Falsa 123', '555123456', 'juan.perez@example.com', '1990-01-01', 1, 1);

-- Insertar Farmacias
INSERT INTO Farmacia (id,nombreEstablecimiento, nombreFarmaceutico, apellidosFarmaceutico, dni, numeroColegiado, direccion, telefono, email)
VALUES
    (3,'Farmacia Central', 'Ana', 'García', '87654321B', 'FAR123', 'Calle Salud 456', '555654321', 'ana.garcia@example.com');


-- Insertar Medicamentos
INSERT INTO Medicamento (id, nombreComercial, principioActivo, fabricante, familia, numeroDosis, activo)
VALUES
    (1, 'Ibuprofeno 600mg', 'Ibuprofeno', 'Farmalab', 'Antiinflamatorio', 20, true);

INSERT INTO Receta (id, paciente_id, medico_id, farmacia_id, medicamento_id, fechaInicio, fechaFin, estado)
VALUES
    (1, 2, 1, 3, 1, '2024-12-10', '2024-12-14', 'PLANIFICADA'),
    (2, 2, 1, 3, 1, '2024-01-16', '2025-01-31', 'SERVIDA');

