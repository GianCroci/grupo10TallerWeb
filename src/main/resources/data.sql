-- INSERT INTO Usuario( email, password, rol, activo) VALUES( 'test@unlam.edu.ar', 'test', 'ADMIN', true);
-- Insertar roles si aún no existen
INSERT INTO Rol(id, tipo) VALUES (1, 'Guerrero');
INSERT INTO Rol(id, tipo) VALUES (2, 'Mago');
INSERT INTO Rol(id, tipo) VALUES (3, 'Bandido');
INSERT INTO Guerrero(id) VALUES (1);
INSERT INTO Mago(id) VALUES (2);
INSERT INTO Bandido(id) VALUES (3);

-- Insertar un personaje
INSERT INTO Personaje(nombre, genero, rol, fuerza, inteligencia, armadura, agilidad, imagen, oro)
VALUES ( 'Arthas', 'Masculino', 'Guerrero', 10, 5, 8, 6, 'img/luchador.png', 500);

-- Insertar un usuario asociado al personaje (suponiendo que el personaje tiene ID = 1)
INSERT INTO Usuario(email, password, rol, activo, personaje_id)
VALUES ('test@unlam.edu.ar', 'test', 'ADMIN', true, 1);

-- Insertar 3 equipamientos tipo arma con estadísticas embebidas y distintos roles

-- Arma 1: Guerrero
INSERT INTO Equipamiento(
    nombre, fuerza, inteligencia, armadura, agilidad,
    costoCompra, costoMejora, costoVenta, nivel, equipado,
    idPersonaje, rol_id
) VALUES (
             'espada', 10, 0, 0, 0,
             50, 20, 30, 0, FALSE,
             1, 1
         );

-- Arma 2: Mago
INSERT INTO Equipamiento(
    nombre, fuerza, inteligencia, armadura, agilidad,
    costoCompra, costoMejora, costoVenta, nivel, equipado,
    idPersonaje, rol_id
) VALUES (
             'baston', 0, 12, 0, 0,
             40, 15, 25, 0, FALSE,
             1, 2
         );

-- Arma 3: Bandido
INSERT INTO Equipamiento(
    nombre, fuerza, inteligencia, armadura, agilidad,
    costoCompra, costoMejora, costoVenta, nivel, equipado,
    idPersonaje, rol_id
) VALUES (
             'daga', 4, 0, 0, 10,
             35, 10, 20, 0, FALSE,
             1, 3
         );

INSERT INTO Equipamiento(
    nombre, fuerza, inteligencia, armadura, agilidad,
    costoCompra, costoMejora, costoVenta, nivel, equipado,
    idPersonaje, rol_id
) VALUES (
             'casco', 0, 0, 0, 0,
             35, 10, 20, 0, FALSE,
             1, 1
         );

-- Insertar en subtabla Arma (herencia JOINED) para vincular los equipamientos como armas
-- Suponiendo que los IDs de equipamiento generados fueron 1, 2 y 3 (puede variar)
INSERT INTO Arma(id) VALUES (1);
INSERT INTO Arma(id) VALUES (2);
INSERT INTO Arma(id) VALUES (3);
INSERT INTO Casco(id) VALUES (4);
