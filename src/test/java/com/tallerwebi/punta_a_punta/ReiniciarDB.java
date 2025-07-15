package com.tallerwebi.punta_a_punta;

import java.io.IOException;

public class ReiniciarDB {
    public static void limpiarBaseDeDatos() {
        try {
            String dbHost = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "mysql";
            String dbPort = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "3306";
            String dbName = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "tallerwebi";
            String dbUser = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "user";
            String dbPassword = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "user";

            String sqlCommands = "SET FOREIGN_KEY_CHECKS = 0; DELETE FROM Arma WHERE id IN (1,2,3); DELETE FROM Casco WHERE id = 4; DELETE FROM Equipamiento WHERE id IN (1,2,3,4); DELETE FROM Usuario; DELETE FROM Personaje; ALTER TABLE Usuario AUTO_INCREMENT = 1; ALTER TABLE Personaje AUTO_INCREMENT = 1; INSERT INTO Personaje(nombre, genero, rol_id, fuerza, inteligencia, armadura, agilidad, imagen, oro, codigoAmigo, nivel, vida, esTuTurno) VALUES ('Arthas', 'Masculino', 1, 100, 20, 80, 60, '/spring/img/luchador.png', 500, 'codigo01', 26, 390, false); INSERT INTO Usuario(email, password, rol, activo, personaje_id) VALUES ('test@unlam.edu.ar', '$2a$10$18z5TDsK7/VTCo5mocGB..miVXF0k/U6jrn1vbaKnaJYFuOormE/i', 'ADMIN', true, 1); INSERT INTO Equipamiento(id, nombre, fuerza, inteligencia, armadura, agilidad, costoCompra, costoMejora, costoVenta, nivel, equipado, personaje_id, rol_id, imagen) VALUES (1, 'Espada', 10, 0, 0, 0, 50, 20, 30, 0, FALSE, 1, 1, 'img/espada.png'), (2, 'Bastón', 0, 12, 0, 0, 40, 15, 25, 0, FALSE, 1, 2, 'img/baston.png'), (3, 'Daga', 4, 0, 0, 10, 35, 10, 20, 0, FALSE, 1, 3, 'img/daga.png'), (4, 'Casco', 0, 0, 5, 0, 35, 10, 20, 0, FALSE, 1, 1, 'img/casco.png'); INSERT INTO Arma(id) VALUES (1); INSERT INTO Arma(id) VALUES (2); INSERT INTO Arma(id) VALUES (3); INSERT INTO Casco(id) VALUES (4);";

            String comando = String.format(
                    "docker exec tallerwebi-mysql mysql -u %s -p%s %s -e \"%s\"",
                    dbUser, dbPassword, dbName, sqlCommands
            );

            Process process = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", comando});
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Base de datos limpiada exitosamente");
            } else {
                System.err.println("Error al limpiar la base de datos. Exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error ejecutando script de limpieza: " + e.getMessage());
            e.printStackTrace();
        }
    }
}