-- Insertar roles si aún no existen
INSERT INTO Rol(id, tipo) VALUES (1, 'Guerrero');
INSERT INTO Rol(id, tipo) VALUES (2, 'Mago');
INSERT INTO Rol(id, tipo) VALUES (3, 'Bandido');
INSERT INTO Guerrero(id) VALUES (1);
INSERT INTO Mago(id) VALUES (2);
INSERT INTO Bandido(id) VALUES (3);

-- Insertar un personaje
INSERT INTO Personaje(nombre, genero, rol_id, fuerza, inteligencia, armadura, agilidad, imagen, oro, codigoAmigo)
VALUES ('Arthas', 'Masculino', 1, 100, 20, 80, 60, '/spring/img/luchador.png', 500, 'codigo01');

-- Insertar un usuario asociado al personaje (ID = 1)
INSERT INTO Usuario(email, password, rol, activo, personaje_id)
VALUES ('test@unlam.edu.ar', 'test', 'ADMIN', true, 1);

-- Equipamiento en inventario del personaje (rol_id = 1 = Guerrero, 2 = Mago, 3 = Bandido)
INSERT INTO Equipamiento(
    nombre, fuerza, inteligencia, armadura, agilidad,
    costoCompra, costoMejora, costoVenta, nivel, equipado,
    personaje_id, rol_id, imagen
) VALUES
      ('Espada', 10, 0, 0, 0, 50, 20, 30, 0, FALSE, 1, 1, 'img/espada.png'),
      ('Bastón', 0, 12, 0, 0, 40, 15, 25, 0, FALSE, 1, 2, 'img/bastón.png'),
      ('Daga', 4, 0, 0, 10, 35, 10, 20, 0, FALSE, 1, 3, 'img/daga.png'),
      ('Casco', 0, 0, 5, 0, 35, 10, 20, 0, FALSE, 1, 1, 'img/casco.png');

-- Subtablas
INSERT INTO Arma(id) VALUES (1);
INSERT INTO Arma(id) VALUES (2);
INSERT INTO Arma(id) VALUES (3);
INSERT INTO Casco(id) VALUES (4);

-- Crear mercado
INSERT INTO Mercado(id) VALUES (2);

-- Equipamientos del mercado
INSERT INTO Equipamiento(
    id, nombre, fuerza, inteligencia, armadura, agilidad,
    costoCompra, costoMejora, costoVenta, nivel, equipado,
    personaje_id, rol_id, mercado_id, imagen
) VALUES
      (10, 'Tunica azul', 0, 10, 5, 5, 50, 20, 100, 1, FALSE, null, 2, 2, 'img/tunica-azul.png'),
      (11, 'Abrigo gris', 0, 0, 15, 5, 80, 25, 200, 1, FALSE, null, 2, 2, 'img/abrigo-gris.png'),
      (12, 'Capucha de Sombras', 0, 12, 5, 10, 60, 15, 120, 1, FALSE, null, 2, 2, 'img/capucha-sombras.png'),
      (13, 'Zapatos grises', 0, 0, 8, 20, 20, 20, 250, 1, FALSE, null, 2, 2, 'img/zapatos-gris.png'),
      (14, 'Zapatos reforzados', 0, 0, 12, 15, 50, 18, 100, 1, FALSE, null, 2, 2, 'img/zapatos-reforzados.png'),
      (15, 'Cinturon dorado', 5, 0, 10, 10, 30, 22, 250, 1, FALSE, null, 2, 2, 'img/cinturon-oro.png');

-- Subclases
INSERT INTO Pechera(id) VALUES (10);
INSERT INTO Pechera(id) VALUES (11);
INSERT INTO Casco(id) VALUES (12);
INSERT INTO Botas(id) VALUES (13);
INSERT INTO Botas(id) VALUES (14);
INSERT INTO Pantalones(id) VALUES (15);
