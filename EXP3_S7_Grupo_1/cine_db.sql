CREATE DATABASE IF NOT EXISTS Cine_DB;
USE Cine_DB;

show databases;


CREATE TABLE IF NOT EXISTS Cartelera (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo  VARCHAR(50) NOT NULL,
    director VARCHAR(50)  NOT NULL,
    año   INTEGER	NOT NULL,
    duracion INTEGER,
    genero VARCHAR(50)
);

select * from Cartelera;

-- (Opcional) Datos de prueba
INSERT INTO Cartelera (titulo, director, año) VALUES
('El Resplandor', 'Stanley Kubric', '1997'),
('Matrix', 'H.Washobsky', '1999');