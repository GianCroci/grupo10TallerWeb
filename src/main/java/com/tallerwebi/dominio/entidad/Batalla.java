package com.tallerwebi.dominio.entidad;

import com.tallerwebi.dominio.EstadoBatalla;
import com.tallerwebi.dominio.entidad.Personaje;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Batalla {
    private Personaje jugadorA;
    private Personaje jugadorB;
    private String turno;
    private final Map<String, Integer> turnosUsados = new HashMap<>();
    private final Map<String, Integer> hpRestante = new HashMap<>();

    public Batalla(Personaje a, Personaje b) {
        this.jugadorA = clonar(a);
        this.jugadorB = clonar(b);

        this.turno = a.getNombre();
        turnosUsados.put(a.getNombre(), 0);
        turnosUsados.put(b.getNombre(), 0);
        hpRestante.put(a.getNombre(), 100); // Asignale el HP inicial real si lo tenés
        hpRestante.put(b.getNombre(), 100);
    }

    public Integer getHpRestante(String nombre) {
        return hpRestante.get(nombre);
    }

    public EstadoBatalla atacar(String nombreAtacante) {
        String nombreDefensor = getRivalNombre(nombreAtacante);

        int daño = calcularDañoFinal(getPersonaje(nombreAtacante), getPersonaje(nombreDefensor));

        int hpActual = hpRestante.get(nombreDefensor);
        int nuevoHp = Math.max(0, hpActual - daño);
        hpRestante.put(nombreDefensor, nuevoHp);

        // Actualizar turnos
        turnosUsados.put(nombreAtacante, turnosUsados.get(nombreAtacante) + 1);

        // Verificar fin de batalla
        boolean fin = batallaTerminada();


        if (!fin) {
            turno = nombreDefensor;
        }

        return new EstadoBatalla(
                nombreAtacante + " atacó y causó " + daño + " de daño a " + nombreDefensor
                        + (fin ? ". La batalla terminó." : ""),
                hpRestante.get(jugadorA.getNombre()),
                hpRestante.get(jugadorB.getNombre()),
                fin ? "FIN" : turno
        );
    }

    public boolean batallaTerminada() {
        if (hpRestante.get(jugadorA.getNombre()) <= 0 || hpRestante.get(jugadorB.getNombre()) <= 0){
            return true;
        }
        return false;
    }

    private int calcularDañoFinal(Personaje atacante, Personaje defensor) {
        int ataqueRaw = calcularAtaque(atacante);
        int defensaRaw = calcularDefensa(defensor);


        int dañoBase = ataqueRaw - defensaRaw;


        int dañoFinal = Math.max(20, Math.min(70, dañoBase));


        if (dañoBase < 20) {
            dañoFinal = 20 + (int)(Math.random() * 16);
        }

        else if (dañoBase > 70) {
            dañoFinal = 55 + (int)(Math.random() * 16); // 55-70
        }

        return dañoFinal;
    }

    private int calcularAtaque(Personaje atacante) {
        int fuerza = atacante.getEstadisticas().getFuerza();
        int inteligencia = atacante.getEstadisticas().getInteligencia();
        int agilidad = atacante.getEstadisticas().getAgilidad();


        int base = (fuerza + inteligencia) / 2 + 15;

        int variante = (int) (Math.random() * 4);

        switch (variante) {
            case 0:

                return base + (int)(Math.random() * 20 + 10);

            case 1:

                return (int) (base * 1.3 + Math.random() * 25 + 15);

            case 2:

                return (int) ((inteligencia * 0.8 + fuerza * 0.4) + Math.random() * 20 + 10);

            case 3:

                return (int) (agilidad * 0.7 + fuerza * 0.3 + Math.random() * 18 + 12);

            default:
                return base + 10;
        }
    }

    private int calcularDefensa(Personaje defensor) {
        int armadura = defensor.getEstadisticas().getArmadura();
        int agilidad = defensor.getEstadisticas().getAgilidad();


        int base = (armadura + agilidad) / 3 + 5;

        int variante = (int) (Math.random() * 4);

        switch (variante) {
            case 0:

                return base + (int)(Math.random() * 15 + 5);

            case 1:

                return (int) (armadura * 0.8 + Math.random() * 15 + 5);

            case 2:

                return (int) (agilidad * 0.6 + Math.random() * 12 + 3);

            case 3:

                return Math.random() < 0.3 ?
                        (int)(base * 1.5 + Math.random() * 10) :
                        (int)(base * 0.7 + Math.random() * 8);

            default:
                return base + 5;
        }
    }



    private Personaje getPersonaje(String nombre) {
        return jugadorA.getNombre().equals(nombre) ? jugadorA : jugadorB;
    }

    private String getRivalNombre(String nombre) {
        return jugadorA.getNombre().equals(nombre) ? jugadorB.getNombre() : jugadorA.getNombre();
    }

    private Personaje clonar(Personaje p) {
        Personaje copia = new Personaje();
        copia.setId(p.getId());
        copia.setNombre(p.getNombre());
        copia.setImagen(p.getImagen());
        copia.setEstadisticas(p.getEstadisticas());
        return copia;
    }

    public Personaje getRival() {
        return jugadorB;
    }

    public Personaje getJugadorA() {
        return jugadorA;
    }

    public Personaje getJugadorB() {
        return jugadorB;
    }
}
