package com.tallerwebi.dominio.entidad;

import com.tallerwebi.dominio.EstadoBatalla;
import com.tallerwebi.dominio.entidad.Personaje;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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

    public boolean puedeAtacar(String nombre) {
        return turno.equals(nombre) && turnosUsados.get(nombre) < 3 && !batallaTerminada();
    }

    public boolean haAtacado(Long idPersonaje) {
        return turnosUsados.getOrDefault(idPersonaje.toString(), 0) > 0;
    }



    public EstadoBatalla atacar(String nombreAtacante) {
        String nombreDefensor = getRivalNombre(nombreAtacante);

        int daño = calcularDaño(getPersonaje(nombreAtacante));
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
        return turnosUsados.values().stream().allMatch(t -> t >= 3);
    }

    private int calcularDaño(Personaje atacante) {
        // Ejemplo simple basado en fuerza y armas
        int fuerza = atacante.getEstadisticas().getFuerza();
        int bonoArmas = atacante.getEquipamientos().size();
        return fuerza + bonoArmas; // podés hacer una fórmula más realista
    }

    private Personaje getPersonaje(String nombre) {
        return jugadorA.getNombre().equals(nombre) ? jugadorA : jugadorB;
    }

    private String getRivalNombre(String nombre) {
        return jugadorA.getNombre().equals(nombre) ? jugadorB.getNombre() : jugadorA.getNombre();
    }

    // Clon para no modificar los personajes reales
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
