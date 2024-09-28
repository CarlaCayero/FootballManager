package football;

import java.util.ArrayList;

public class Equipos implements Comparable<Equipos>{

    private int id;
    private static int contador = 0;
    protected final String nombreEquipo;
    protected final int anoFundacion;
    protected final String ciudad;
    protected String nombreEstadio;
    protected String nombrePresidente;
    protected int puntos;
    protected int partidosDisputados;
    protected int golesAFavor;
    protected int golesEnContra;
    protected Entrenadores entrenador;
    protected ArrayList<Jugadores> jugadores = new ArrayList<>();
    private int partidosGanados;
    private int partidosEmpatados;

    /**
     * Constructor de la clase Equipos.
     * @param nombreEquipo El nombre del equipo.
     * @param anoFundacion El año de fundación del equipo.
     * @param ciudad La ciudad del equipo.
     * @param nombreEstadio El nombre del estadio del equipo.
     * @param nombrePresidente  El nombre del presidente del equipo.
     * @param puntos La puntuación del equipo.
     * @param partidosDisputados La cantidad de partidos disputados por el equipo.
     * @param golesAFavor La cantidad de goles a favor del equipo.
     * @param golesEnContra  La cantidad de goles en contra del equipo.
     */
    public Equipos(String nombreEquipo, int anoFundacion, String ciudad, String nombreEstadio, String nombrePresidente, int puntos, int partidosDisputados, int golesAFavor, int golesEnContra) {
        this.nombreEquipo = nombreEquipo;
        this.anoFundacion = anoFundacion;
        this.ciudad = ciudad;
        this.nombreEstadio = nombreEstadio;
        this.nombrePresidente = nombrePresidente;
        this.puntos = puntos;
        this.partidosDisputados = partidosDisputados;
        this.partidosGanados = 0;
        this.partidosEmpatados = 0;
        this.golesAFavor = golesAFavor;
        this.golesEnContra = golesEnContra;
        this.id = contador;
        contador++;
    }

    public int getPartidosGanados() {
        return partidosGanados;
    }

    public int getPartidosEmpatados() {
        return partidosEmpatados;
    }

    public void setPartidosDisputados(int partidosDisputados) {
        this.partidosDisputados = partidosDisputados;
    }

    public void setGolesAFavor(int golesAFavor) {
        this.golesAFavor = golesAFavor;
    }

    public void setGolesEnContra(int golesEnContra) {
        this.golesEnContra = golesEnContra;
    }

    public void setPartidosGanados(int partidosGanados) {
        this.partidosGanados = partidosGanados;
    }

    public void setPartidosEmpatados(int partidosEmpatados) {
        this.partidosEmpatados = partidosEmpatados;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPuntos() {
        return puntos;
    }
    public int getId() {
        return id;
    }
    public int getPartidosDisputados() {
        return partidosDisputados;
    }
    public int getGolesAFavor() {
        return golesAFavor;
    }
    public int getGolesEnContra() {
        return golesEnContra;
    }
    public String getNombreEquipo() {
        return this.nombreEquipo;
    }
    public String getNombrePresidente() {return this.nombrePresidente;}
    public void setNombrePresidente(String nombrePresidente) {this.nombrePresidente = nombrePresidente;}
    public Entrenadores getEntrenador() {
        return this.entrenador;
    }
    public void setEntrenador(Entrenadores entrenador) {
        this.entrenador = entrenador;
    }
    public ArrayList<Jugadores> getJugadores() {
        return jugadores;
    }

    /**
     * Genera una representación en forma de cadena de texto de los datos del equipo.
     * @return Una cadena que contiene los datos del equipo.
     */
    @Override
    public String toString() {
        return "Equipo: \n" +
                "Nombre del equipo: " + nombreEquipo +
                ", Año fundacion: " + anoFundacion +
                ", Ciudad: " + ciudad +
                ", Estadio: " + nombreEstadio +
                ", Presidente: " + nombrePresidente +
                ", Puntuación: " + puntos +
                ", Partidos disputados: " + partidosDisputados +
                ", Goles a favor: " + golesAFavor +
                ", Goles en contra: " + golesEnContra;
    }

    /**
     * Actualiza los datos de un equipo cuando gana un partido.
     * @param golesAFavor Número de goles a favor en el partido ganado.
     * @param golesEnContra Número de goles en contra en el partido ganado.
     */
    public void ganarPartidos(int golesAFavor, int golesEnContra){
        // Incrementa los puntos del equipo al ganar un partido
        this.puntos += 3;
        // Actualiza los goles a favor y en contra del equipo
        this.golesAFavor += golesAFavor;
        this.golesEnContra += golesEnContra;
        this.partidosGanados++;
        this.partidosDisputados++;

    }
    /**
     * Método que actualiza los datos de un equipo cuando empata un partido.
     * @param goles Número de goles marcados en el partido empatado.
     */
    public void empatarPartido (int goles){
        // Incrementa los puntos del equipo al empatar un partido
        this.puntos += 1;
        // Actualiza los goles a favor y en contra del equipo
        this.golesAFavor += goles;
        this.golesEnContra += goles;
        this.partidosEmpatados++;
        this.partidosDisputados++;
    }
    /**
     * Método que actualiza los datos de un equipo cuando pierde un partido.
     * @param golesAFavor Número de goles a favor en el partido perdido.
     * @param golesEnContra Número de goles en contra en el partido perdido.
     */
    public void perderPartido(int golesAFavor, int golesEnContra){
        // Actualiza los goles a favor y en contra del equipo
        this.golesAFavor += golesAFavor;
        this.golesEnContra += golesEnContra;
        this.partidosDisputados++;

    }

    @Override
    public int compareTo(Equipos o) {
        if (this.puntos != o.getPuntos()) {
            return o.getPuntos() - this.puntos; // Orden descendente por puntos
        } else {
            // En caso de empate en puntos, ordena por diferencia de goles
            return (o.getGolesAFavor() - o.getGolesEnContra()) - (this.golesAFavor - this.golesEnContra);
        }
    }
}
