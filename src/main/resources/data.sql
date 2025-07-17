-- Insertar roles si aún no existen
INSERT INTO Rol(id, tipo) VALUES (1, 'Guerrero');
INSERT INTO Rol(id, tipo) VALUES (2, 'Mago');
INSERT INTO Rol(id, tipo) VALUES (3, 'Bandido');
INSERT INTO Guerrero(id) VALUES (1);
INSERT INTO Mago(id) VALUES (2);
INSERT INTO Bandido(id) VALUES (3);

-- Insertar un personaje
INSERT INTO Personaje(nombre, genero, rol_id, fuerza, inteligencia, armadura, agilidad, imagen, oro, codigoAmigo, nivel, vida, esTuTurno)
VALUES ('Arthas', 'Masculino', 1, 100, 20, 80, 60, '/spring/img/luchador.png', 500, 'codigo01', 26, 390, false);

-- Insertar un usuario asociado al personaje (ID = 1)
INSERT INTO Usuario(email, password, rol, activo, personaje_id)
VALUES ('test@unlam.edu.ar', '$2a$10$18z5TDsK7/VTCo5mocGB..miVXF0k/U6jrn1vbaKnaJYFuOormE/i', 'ADMIN', true, 1);

-- Equipamiento en inventario del personaje (rol_id = 1 = Guerrero, 2 = Mago, 3 = Bandido)
INSERT INTO Equipamiento(
    nombre, fuerza, inteligencia, armadura, agilidad,
    costoCompra, costoMejora, costoVenta, nivel, equipado,
    personaje_id, rol_id, imagen
) VALUES
      ('Espada', 10, 0, 0, 0, 50, 20, 30, 5, FALSE, 1, 1, 'img/espada.png'),
      ('Baston', 0, 12, 0, 0, 40, 15, 25, 0, FALSE, 1, 2, 'img/baston.png'),
      ('Daga', 4, 0, 0, 10, 35, 10, 20, 0, FALSE, 1, 3, 'img/daga.png'),
      ('Casco', 0, 0, 5, 0, 35, 1000, 20, 0, FALSE, 1, 1, 'img/casco.png');

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
      (7,'Espada', 10, 0, 0, 0, 50, 20, 30, 0, FALSE, null, 1, 2,  'img/espada.png'),
      (8,'Baston', 0, 12, 0, 0, 40, 15, 25, 0, FALSE, null, 2, 2,'img/baston.png'),
      (9,'Daga', 4, 0, 0, 10, 35, 10, 20, 0, FALSE, null, 3, 2, 'img/daga.png'),

      (10, 'Escudo de Hierro', 4, 0, 12, 2, 65, 20, 130, 0, FALSE, null, 1, 2, 'img/escudo-hierro.png'),
      (11, 'Escudo Encantado', 0, 8, 10, 5, 70, 22, 140, 0, FALSE, null, 2, 2, 'img/escudo-encantado.png'),
      (12, 'Escudo Ligero', 2, 0, 8, 8, 55, 18, 110, 0, FALSE, null, 3, 2, 'img/escudo-ligero.png'),

      (13, 'Mascara encapuchada', 2, 3, 8, 6, 60, 17, 120, 0, FALSE, null, 1, 2, 'img/mascara-encapuchada.png'),
      (14, 'Capucha runica', 0, 12, 4, 3, 55, 15, 110, 0, FALSE, null, 2, 2, 'img/capucha-runica.png'),
      (15, 'Capucha Sombras', 0, 10, 5, 10, 60, 15, 120, 0, FALSE, null, 3, 2, 'img/capucha-sombras.png'),

      (16, 'Chaleco oscuro', 5, 0, 15, 5, 30, 22, 250, 0, FALSE, null, 1, 2, 'img/chaleco-oscuro.png'),
      (17, 'Tunica azul', 0, 8, 9, 4, 55, 20, 130, 0, FALSE, null, 2, 2, 'img/img/tunica-azul.png'),
      (18, 'Abrigo gris', 0, 0, 10, 10, 80, 25, 200, 0, FALSE, null, 3, 2, 'img/abrigo-gris.png'),

      (19, 'Pantalones reforzados', 4, 0, 10, 6, 50, 18, 105, 0, FALSE, null, 1, 2, 'img/pantalones-reforzados.png'),
      (20, 'Cinturon dorado', 0, 10, 5, 5, 50, 20, 100, 0, FALSE, null, 2, 2, 'img/cinturon-oro.png'),
      (21, 'Pantalones de sombra', 0, 6, 8, 10, 55, 19, 115, 0, FALSE, null, 3, 2, 'img/pantalones-sombra.png'),

      (22, 'Zapatos reforzados', 0, 0, 15, 12, 50, 18, 100, 0, FALSE, null, 1, 2, 'img/zapatos-reforzados.png'),
      (23, 'Zapatos grises', 0, 5, 5, 10, 20, 20, 250, 0, FALSE, null, 2, 2, 'img/zapatos-gris.png'),
      (24, 'Botas sigilosas', 0, 0, 6, 20, 45, 17, 95, 0, FALSE, null, 3, 2, 'img/botas-sigilosas.png');


-- Subclases
INSERT INTO Arma(id) VALUES (7);
INSERT INTO Arma(id) VALUES (8);
INSERT INTO Arma(id) VALUES (9);

INSERT INTO Escudo(id) VALUES (10);
INSERT INTO Escudo(id) VALUES (11);
INSERT INTO Escudo(id) VALUES (12);

INSERT INTO Casco(id) VALUES (13);
INSERT INTO Casco(id) VALUES (14);
INSERT INTO Casco(id) VALUES (15);

INSERT INTO Pechera(id) VALUES (16);
INSERT INTO Pechera(id) VALUES (17);
INSERT INTO Pechera(id) VALUES (18);

INSERT INTO Pantalones(id) VALUES (19);
INSERT INTO Pantalones(id) VALUES (20);
INSERT INTO Pantalones(id) VALUES (21);

INSERT INTO Botas(id) VALUES (22);
INSERT INTO Botas(id) VALUES (23);
INSERT INTO Botas(id) VALUES (24);


INSERT INTO Enemigo(id, nombre, descripcion, imagenEnemigo, imagenFondo, fuerza, inteligencia, armadura, agilidad, nivel, vida)
VALUES (1, 'Slime', 'Una masa gelatinosa y ácida que se arrastra lentamente. Pese a su apariencia inofensiva, puede disolver lo que toca.', 'img/slime.png', 'img/calabozo.png', 20, 5, 20, 5, 5, 75),
(2, 'Lobo', 'Rápido y letal, este lobo acecha desde la oscuridad del bosque. Ataca en manada y no muestra piedad con los débiles.', 'img/lobo.png', 'img/bosque.png', 40, 40, 10, 60, 15, 225),
(3, 'Trol', 'Gigante bruto de las cavernas. Fuerte, resistente y con una piel que se regenera, aunque lento y fácil de engañar', 'img/trol.png', 'img/caverna.png', 250, 0, 150, 100, 50, 750);

INSERT INTO Slime (id) VALUES (1);
INSERT INTO Lobo (id) VALUES (2);
INSERT INTO Trol (id) VALUES (3);

INSERT INTO Producto (id, nombre, precio, cantidadProducto, imagen)
VALUES (1, 'Oro', '1000', 100, 'img/monedas.png'),
(2, 'Mas oro', '2000', 250, 'img/oro.png'),
(3, 'Muchisimo oro', '3000', 400, 'img/olla-oro.png');