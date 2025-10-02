
CREATE DATABASE IF NOT EXISTS Cine_DB;
USE Cine_DB;
show databases;

CREATE TABLE IF NOT EXISTS Cartelera (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    director VARCHAR(120) NOT NULL,
    año INTEGER NOT NULL,
    duracion VARCHAR(20),
    genero VARCHAR(50)
);

SELECT * FROM Cartelera;

INSERT INTO Cartelera (titulo, director, año, duracion, genero) VALUES
('El Padrino', 'Francis Ford Coppola', 1972, '175', 'Crimen'),
('El Padrino II', 'Francis Ford Coppola', 1974, '202', 'Crimen'),
('Pulp Fiction', 'Quentin Tarantino', 1994, '154', 'Crimen'),
('Reservoir Dogs', 'Quentin Tarantino', 1992, '99', 'Crimen'),
('La lista de Schindler', 'Steven Spielberg', 1993, '195', 'Drama'),
('Jurassic Park', 'Steven Spielberg', 1993, '127', 'Aventura'),
('Inception', 'Christopher Nolan', 2010, '148', 'Ciencia Ficción'),
('Interestelar', 'Christopher Nolan', 2014, '169', 'Ciencia Ficción'),
('El Caballero de la Noche', 'Christopher Nolan', 2008, '152', 'Acción'),
('Memento', 'Christopher Nolan', 2000, '113', 'Thriller'),
('Parasite', 'Bong Joon-ho', 2019, '132', 'Drama'),
('Memories of Murder', 'Bong Joon-ho', 2003, '131', 'Crimen'),
('Oldboy', 'Park Chan-wook', 2003, '120', 'Thriller'),
('El Señor de los Anillos: La Comunidad del Anillo', 'Peter Jackson', 2001, '178', 'Fantasía'),
('El Señor de los Anillos: Las Dos Torres', 'Peter Jackson', 2002, '179', 'Fantasía'),
('El Señor de los Anillos: El Retorno del Rey', 'Peter Jackson', 2003, '201', 'Fantasía'),
('Forrest Gump', 'Robert Zemeckis', 1994, '142', 'Drama'),
('Náufrago', 'Robert Zemeckis', 2000, '143', 'Aventura'),
('Regreso al Futuro', 'Robert Zemeckis', 1985, '116', 'Ciencia Ficción'),
('El Exorcista', 'William Friedkin', 1973, '122', 'Terror'),
('El Resplandor', 'Stanley Kubrick', 1980, '146', 'Terror'),
('2001: Odisea del Espacio', 'Stanley Kubrick', 1968, '149', 'Ciencia Ficción'),
('Naranja Mecánica', 'Stanley Kubrick', 1971, '136', 'Drama'),
('Apocalypse Now', 'Francis Ford Coppola', 1979, '147', 'Guerra'),
('Gladiador', 'Ridley Scott', 2000, '155', 'Acción'),
('Blade Runner', 'Ridley Scott', 1982, '117', 'Ciencia Ficción'),
('Alien: El Octavo Pasajero', 'Ridley Scott', 1979, '117', 'Terror'),
('Titanic', 'James Cameron', 1997, '195', 'Romance'),
('Terminator 2: El Juicio Final', 'James Cameron', 1991, '137', 'Acción');
