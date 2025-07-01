-- INSERT INTO Usuario( email, password, rol, activo) VALUES( 'test@unlam.edu.ar', 'test', 'ADMIN', true);
-- Insertar roles si aún no existen
INSERT INTO Rol(id, tipo) VALUES (1, 'Guerrero');
INSERT INTO Rol(id, tipo) VALUES (2, 'Mago');
INSERT INTO Rol(id, tipo) VALUES (3, 'Bandido');
INSERT INTO Guerrero(id) VALUES (1);
INSERT INTO Mago(id) VALUES (2);
INSERT INTO Bandido(id) VALUES (3);

-- Insertar un personaje
INSERT INTO Personaje(nombre, genero, rol_id, fuerza, inteligencia, armadura, agilidad, imagen, oro)
VALUES ( 'Arthas', 'Masculino', 1, 100, 20, 80, 60, '/spring/img/luchador.png', 500);

-- Insertar un usuario asociado al personaje (suponiendo que el personaje tiene ID = 1)
INSERT INTO Usuario(email, password, rol, activo, personaje_id)
VALUES ('test@unlam.edu.ar', 'test', 'ADMIN', true, 1);

-- Insertar 3 equipamientos tipo arma con estadísticas embebidas y distintos roles

-- Arma 1: Guerrero
-- Arma 2: Mago
-- Arma 3: Bandido
-- Casco 4: Guerrero
INSERT INTO Equipamiento(
    nombre, fuerza, inteligencia, armadura, agilidad,
    costoCompra, costoMejora, costoVenta, nivel, equipado,
    personaje_id, rol_id, imagen
) VALUES (
             'espada', 10, 0, 0, 0,
             50, 20, 30, 0, FALSE,
             1, 1, 'img/espada.png'
         ),(
             'baston', 0, 12, 0, 0,
             40, 15, 25, 0, FALSE,
             1, 2, 'img/baston.png'
         ),(
             'daga', 4, 0, 0, 10,
             35, 10, 20, 0, FALSE,
             1, 3, 'img/daga.png'
         ),(
             'casco', 0, 0, 0, 0,
             35, 10, 20, 0, FALSE,
             1, 1, 'img/casco.png'
         );

-- Insertar en subtabla Arma (herencia JOINED) para vincular los equipamientos como armas
-- Suponiendo que los IDs de equipamiento generados fueron 1, 2 y 3 (puede variar)
INSERT INTO Arma(id) VALUES (1);
INSERT INTO Arma(id) VALUES (2);
INSERT INTO Arma(id) VALUES (3);
INSERT INTO Casco(id) VALUES (4);

-- Crear el mercado
INSERT INTO Mercado(id) VALUES (2);

-- Equipamientos para el mercado
INSERT INTO Equipamiento(
    id, nombre, fuerza, inteligencia, armadura, agilidad,
    costoCompra, costoMejora, costoVenta, nivel, equipado,
    personaje_id, rol_id, mercado_id, imagen
) VALUES
      (10, 'Tunica azul', 0, 10, 5, 5, 150, 20, 100, 1, FALSE, null, 1, 2, 'img/tunica-azul.png'),
      (11, 'Abrigo gris', 0, 0, 15, 5, 180, 25, 200, 1, FALSE, null, 1, 2, 'img/abrigo-gris.png'),
      (12, 'Capucha de Sombras', 0, 12, 5, 10, 160, 15, 120, 1, FALSE, null, 1, 2, 'img/capucha-sombras.png'),
      (13, 'Zapatos grises', 0, 0, 8, 20, 190, 20, 250, 1, FALSE, null, 1, 2, 'img/zapatos-gris.png'),
      (14, 'Zapatos reforzados', 0, 0, 12, 15, 170, 18, 100, 1, FALSE, null, 1, 2, 'img/zapatos-reforzados.png'),
      (15, 'Cinturon oro', 5, 0, 10, 10, 200, 22, 250, 1, FALSE, null, 1, 2, 'img/cinturon-oro.png');

-- Subclases (herencia JOINED)
INSERT INTO Tunica(id) VALUES (10);
INSERT INTO Abrigo(id) VALUES (11);
INSERT INTO Capucha(id) VALUES (12);
INSERT INTO ZapatoUno(id) VALUES (13);
INSERT INTO ZapatoDos(id) VALUES (14);
INSERT INTO Cinturon(id) VALUES (15);



----Tabla de taberna---
CREATE TABLE taberna (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  id_personaje BIGINT NOT NULL,
                                  personaje_taberna VARCHAR(50) NOT NULL,
                                  cervezas_invitadas INT NOT NULL,
                                  ultima_invitacion DATE,
                                  CONSTRAINT unique_personaje_taberna UNIQUE (id_personaje, personaje_taberna),
                                  CONSTRAINT fk_taberna_personaje FOREIGN KEY (id_personaje) REFERENCES personaje(id)
);
