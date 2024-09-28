package football;

import java.util.*;

public class Liga {
    private String nombre;
    private ArrayList<Equipos> listaEquiposLiga;

    /**
     * Constructor de la clase Liga.
     * @param nombre El nombre de la liga.
     * @param equipos La lista de equipos participantes en la liga.
     */
    public Liga(String nombre,  ArrayList<Equipos> equipos) {
        this.nombre = nombre;
        this.listaEquiposLiga = equipos;
    }
    public ArrayList<Equipos> getListaEquiposLiga() {
        return listaEquiposLiga;
    }


    /**
     * Retorna una representación en forma de cadena de la liga.
     * @return Los datos de la liga
     */
    @Override
    public String toString() {
        return "Puntuación de la Liga: " + nombre + "\n"+listaEquiposLiga;
    }


}
