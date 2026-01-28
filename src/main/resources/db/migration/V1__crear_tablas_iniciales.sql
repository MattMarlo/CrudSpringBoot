-- 1. Crear tabla Clientes
CREATE TABLE clientes (
                          id BIGSERIAL PRIMARY KEY, -- BIGSERIAL equivale a BIGINT + AUTO_INCREMENT
                          nombre VARCHAR(255),
                          apellido VARCHAR(255),
                          cedula VARCHAR(255) UNIQUE,
                          email VARCHAR(255),
                          telefono VARCHAR(255),
                          direccion VARCHAR(255)
);

-- 2. Crear tabla Cuentas
CREATE TABLE cuentas (
                         id BIGSERIAL PRIMARY KEY,
                         numero_cuenta VARCHAR(255) UNIQUE,
                         tipo_cuenta VARCHAR(255),
                         saldo DECIMAL(19, 2),
                         fecha_apertura DATE,
                         cliente_id BIGINT,
                         CONSTRAINT fk_cliente_cuenta FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);