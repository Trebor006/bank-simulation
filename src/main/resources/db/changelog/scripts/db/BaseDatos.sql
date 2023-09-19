-- create database bank;
-- set search_path = "public";


-- Crear la tabla de usuarios aleatorios
CREATE TABLE usuario
(
    id             SERIAL PRIMARY KEY,
    identificacion VARCHAR(255),
    nombre         VARCHAR(255),
    apellido       VARCHAR(255),
    correo         VARCHAR(255) UNIQUE,
    telefono       VARCHAR(15)
);

-- Crear la tabla de deudas de personas
CREATE TABLE deuda
(
    id                SERIAL PRIMARY KEY NOT NULL,
    usuario_id        INTEGER REFERENCES usuario (id),
    monto_deuda       DECIMAL(10, 2),
    fecha_vencimiento DATE
);

-- Crear la tabla de pagos de deudas
CREATE TABLE pago
(
    id    SERIAL PRIMARY KEY,
    deuda_id   INTEGER REFERENCES deuda (id),
    fecha_pago DATE,
    monto_pago DECIMAL(10, 2)
);

