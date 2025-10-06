DROP DATABASE IF EXISTS Computec_DB;
CREATE DATABASE Computec_DB;
USE Computec_DB;

-- ==============================
-- CLIENTE
-- ==============================
CREATE TABLE CLIENTE (
  rut VARCHAR(12) NOT NULL,
  nombre VARCHAR(90) NOT NULL,
  a_paterno VARCHAR(45) NOT NULL,
  a_materno VARCHAR(45) NOT NULL,
  direccion VARCHAR(180) NOT NULL,
  comuna VARCHAR(80) NOT NULL,
  correo VARCHAR(120) NOT NULL,
  telefono INT NOT NULL,
  PRIMARY KEY(rut)
) ENGINE=InnoDB;

-- ==============================
-- EQUIPO
-- ==============================
CREATE TABLE EQUIPO (
  id_equipo INT NOT NULL AUTO_INCREMENT,
  modelo VARCHAR(120) NOT NULL,
  cpu VARCHAR(120) NOT NULL,
  disco_gb INT NOT NULL,
  ram_gb INT NOT NULL,
  precio INT NOT NULL,
  PRIMARY KEY (id_equipo)
) ENGINE=InnoDB;

-- ==============================
-- LAPTOP
-- ==============================
CREATE TABLE LAPTOP (
  id_equipo INT NOT NULL,
  tam_pantalla_pulg DECIMAL(4,1) NOT NULL,
  es_touch CHAR(1) NOT NULL,
  puertos_usb INT NOT NULL,
  PRIMARY KEY (id_equipo),
  CONSTRAINT FK_LAPTOP_EQUIPO FOREIGN KEY (id_equipo) REFERENCES EQUIPO(id_equipo)
    ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

-- ==============================
-- DESKTOP
-- ==============================
CREATE TABLE DESKTOP (
  id_equipo INT NOT NULL,
  potencia_fuente_w INT NOT NULL,
  placa VARCHAR(20) NOT NULL,
  PRIMARY KEY (id_equipo),
  CONSTRAINT FK_DESKTOP_EQUIPO FOREIGN KEY (id_equipo) REFERENCES EQUIPO(id_equipo)
    ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

-- ==============================
-- VENTA
-- ==============================
CREATE TABLE VENTA(
  id_venta INT NOT NULL AUTO_INCREMENT,
  fecha_hora DATETIME NOT NULL,
  id_equipo INT NOT NULL,
  rut VARCHAR(12) NOT NULL,
  precio_final INT NOT NULL,
  descuento_aplicado VARCHAR(50),
  PRIMARY KEY (id_venta),
  CONSTRAINT FK_VENTA_EQUIPO FOREIGN KEY (id_equipo) REFERENCES EQUIPO(id_equipo)
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT FK_VENTA_CLIENTE FOREIGN KEY(rut) REFERENCES CLIENTE(rut)
    ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

/*
-- ==============================
-- POBLAMIENTO (opcional)
-- ==============================
USE Computec_DB;

-- CLIENTES
INSERT INTO CLIENTE (rut, nombre, a_paterno, a_materno, direccion, comuna, correo, telefono) VALUES
('12.111.111-1', 'María',   'González', 'Pérez',     'Pasaje 101', 'Santiago',     'maria1@mail.com',   911111101),
('12.222.222-2', 'Pedro',   'Ramírez',  'Soto',      'Calle 202',  'Valparaíso',   'pedro2@mail.com',   922222202),
('12.333.333-3', 'Ana',     'López',    'Díaz',      'Av. 303',    'Concepción',   'ana3@mail.com',     933333303),
('12.444.444-4', 'Luis',    'Hidalgo',  'Mora',      'Jr. 404',    'La Florida',   'luis4@mail.com',    944444404),
('12.555.555-5', 'Carla',   'Reyes',    'Silva',     'Psj. 505',   'Maipú',        'carla5@mail.com',   955555505),
('12.666.666-6', 'Diego',   'Rojas',    'Vega',      'Calle 606',  'Ñuñoa',        'diego6@mail.com',   966666606),
('12.777.777-7', 'Paula',   'Campos',   'Ibarra',    'Av. 707',    'Puente Alto',  'paula7@mail.com',   977777707),
('12.888.888-8', 'Javier',  'Ortiz',    'Navarro',   'Jr. 808',    'Rancagua',     'javier8@mail.com',  988888808),
('12.999.999-9', 'Sofía',   'Vargas',   'Pinto',     'Psj. 909',   'Talca',        'sofia9@mail.com',   999999909),
('13.000.000-0', 'Tomás',   'Muñoz',    'Quiroz',    'Calle 010',  'Antofagasta',  'tomas0@mail.com',   900000010);

-- EQUIPOS DESKTOP
INSERT INTO EQUIPO (modelo, cpu, disco_gb, ram_gb, precio) VALUES
('PC Gamer A123',        'Ryzen 5 5600',   512, 16,  799990),
('Workstation Pro D1',   'Intel i7 12700', 1024,32, 1299990),
('Oficina Compacta',     'Intel i5 11400', 256,  8,  499990),
('PC Render X',          'Ryzen 7 5700X',  1024,32, 1399990),
('Escritorio Básico',    'Intel i3 10100', 256,  8,  379990),
('Gaming Mid B450',      'Ryzen 5 3600',   512, 16,  699990),
('Diseño Creativo',      'Intel i7 9700',  512, 16,  899990),
('Servidor Hogar',       'Xeon E3-1220',   1024,16,  849990),
('PC Streaming',         'Ryzen 7 3700X',  512, 16,  949990),
('Escritorio Pro Maxi',  'Intel i9 10900', 2048,64, 1999990);

-- EQUIPOS LAPTOP
INSERT INTO EQUIPO (modelo, cpu, disco_gb, ram_gb, precio) VALUES
('Ultraliviano X14',     'Intel i5 1240P', 512,  16,  749990),
('Notebook Pro 15',      'Intel i7 1260P', 1024, 16, 1099990),
('Portátil Estudiante',  'Intel i3 1115G4',256,   8,  349990),
('Creator Book 16',      'Ryzen 7 6800H',  1024, 32, 1399990),
('TravelMate 13',        'Ryzen 5 5600U',  512,  16,  679990),
('Gamer Mobile 15',      'Intel i7 12700H',1024, 16, 1449990),
('Business 14',          'Intel i5 1135G7',512,   8,  629990),
('Convertible 13',       'Intel i5 1235U', 512,  16,  899990),
('EconoBook 14',         'Ryzen 3 5300U',  256,   8,  429990),
('Studio 15 OLED',       'Intel i7 13700H',1024, 32, 1899990);

-- DESKTOP (relación)
INSERT INTO DESKTOP (id_equipo, potencia_fuente_w, placa) VALUES
(1,  650, 'ATX'),
(2,  750, 'ATX'),
(3,  500, 'MICROATX'),
(4,  750, 'ATX'),
(5,  450, 'MICROATX'),
(6,  650, 'ATX'),
(7,  600, 'ATX'),
(8,  550, 'MICROATX'),
(9,  700, 'ATX'),
(10, 850, 'EATX');

-- LAPTOP (relación)
INSERT INTO LAPTOP (id_equipo, tam_pantalla_pulg, es_touch, puertos_usb) VALUES
(11, 14.0, 'N', 3),
(12, 15.6, 'N', 3),
(13, 14.0, 'N', 2),
(14, 16.0, 'N', 4),
(15, 13.3, 'N', 3),
(16, 15.6, 'N', 3),
(17, 14.0, 'N', 3),
(18, 13.5, 'S', 2),
(19, 14.0, 'N', 2),
(20, 15.6, 'S', 3);

-- VENTAS con fechas entre enero y hoy
INSERT INTO VENTA (fecha_hora, id_equipo, rut, precio_final, descuento_aplicado) VALUES
(STR_TO_DATE('2025-01-15 10:30:00','%Y-%m-%d %H:%i:%s'),  1,  '12.111.111-1', 719991,  '10% de descuento'),
(STR_TO_DATE('2025-02-02 14:45:00','%Y-%m-%d %H:%i:%s'), 12,  '12.222.222-2', 989991,  '10% de descuento'),
(STR_TO_DATE('2025-02-10 09:15:00','%Y-%m-%d %H:%i:%s'),  3,  '12.333.333-3', 449990,  '10% de descuento'),
(STR_TO_DATE('2025-03-05 16:10:00','%Y-%m-%d %H:%i:%s'), 14,  '12.444.444-4', 1119992, '20% de descuento'),
(STR_TO_DATE('2025-03-12 11:20:00','%Y-%m-%d %H:%i:%s'),  5,  '12.555.555-5', 329990,  '$50.000 de descuento'),
(STR_TO_DATE('2025-04-01 13:00:00','%Y-%m-%d %H:%i:%s'), 16,  '12.666.666-6', 1299990, '10% de descuento'),
(STR_TO_DATE('2025-04-18 17:40:00','%Y-%m-%d %H:%i:%s'),  7,  '12.777.777-7', 809991,  '10% de descuento'),
(STR_TO_DATE('2025-05-06 08:55:00','%Y-%m-%d %H:%i:%s'), 18,  '12.888.888-8', 809991,  '10% de descuento'),
(STR_TO_DATE('2025-06-14 15:25:00','%Y-%m-%d %H:%i:%s'),  9,  '12.999.999-9', 899991,  '5% de descuento'),
(STR_TO_DATE('2025-07-03 12:05:00','%Y-%m-%d %H:%i:%s'), 20,  '13.000.000-0', 1699991, '10% de descuento'),
(STR_TO_DATE('2025-07-20 19:45:00','%Y-%m-%d %H:%i:%s'),  2,  '12.222.222-2', 1199990, '$100.000 de descuento'),
(STR_TO_DATE('2025-08-01 10:50:00','%Y-%m-%d %H:%i:%s'), 11,  '12.111.111-1', 699991,  '7% de descuento'),
(STR_TO_DATE('2025-08-17 09:30:00','%Y-%m-%d %H:%i:%s'),  4,  '12.444.444-4', 1199991, '15% de descuento'),
(STR_TO_DATE('2025-09-05 16:15:00','%Y-%m-%d %H:%i:%s'), 13,  '12.333.333-3', 299990,  '$50.000 de descuento'),
(STR_TO_DATE('2025-09-22 18:20:00','%Y-%m-%d %H:%i:%s'),  6,  '12.666.666-6', 629991,  '10% de descuento'),
(STR_TO_DATE('2025-10-01 13:40:00','%Y-%m-%d %H:%i:%s'), 15,  '12.555.555-5', 609991,  '10% de descuento'),
(STR_TO_DATE('2025-10-02 09:10:00','%Y-%m-%d %H:%i:%s'),  8,  '12.777.777-7', 799991,  '5% de descuento'),
(STR_TO_DATE('2025-10-03 14:30:00','%Y-%m-%d %H:%i:%s'), 17,  '12.888.888-8', 566991,  '10% de descuento'),
(STR_TO_DATE('2025-10-03 15:00:00','%Y-%m-%d %H:%i:%s'), 10,  '13.000.000-0', 1799991, '10% de descuento'),
(STR_TO_DATE('2025-10-03 15:30:00','%Y-%m-%d %H:%i:%s'), 19,  '12.999.999-9', 379991,  '$50.000 de descuento');
*/