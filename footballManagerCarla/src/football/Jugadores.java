package football;

import java.util.ArrayList;
import java.util.Scanner;

public class Jugadores extends Personas implements Transferible{

    private int id;
    private static int contador = 0;
    private int numeroDorsal;
    private String posicion;
    private double calidad;
    private boolean transferible;

    /**
     * Constructor de la clase Jugadores.
     * @param nombre El nombre del jugador.
     * @param apellido El apellido del jugador.
     * @param fechaNacimiento La fecha de nacimiento del jugador en formato "dd/mm/aaaa".
     * @param salario El salario anual del jugador.
     * @param numeroDorsal El número de dorsal del jugador.
     * @param posicion La posición del jugador en el campo (DEF, POR, MIG, DAV).
     * @param equipo El equipo al que pertenece inicialmente el jugador.
     */
    public Jugadores(String nombre, String apellido, String fechaNacimiento, double salario, int numeroDorsal, String posicion, Equipos equipo) {
        super(nombre,apellido,fechaNacimiento,salario,equipo);
        this.numeroDorsal = numeroDorsal;
        this.posicion = posicion;
        this.calidad = (double) (Math.random()* (70 - 30 + 1) + 30);
        this.id = contador;
        contador++;
        this.transferible = false;
    }


    public int getNumeroDorsal() {
        return this.numeroDorsal;
    }
    public double getCalidad() {
        return this.calidad;
    }
    public int getId() {
        return this.id;
    }


    /**
     * Genera una representación en forma de cadena de texto de los datos del jugador.
     * @return Una cadena que contiene los datos del jugador.
     */
    @Override
    public String toString() {
        return "Jugadores: \n" +
                "Nombre: " + nombre +
                ", Apellido: " + apellido +
                ", Fecha de nacimiento: " + fechaNacimiento +
                ", Dorsal: " + numeroDorsal +
                ", Posición: " + posicion +
                ", Calidad: " + calidad +
                ", Nivel de motivación: " + nivelMotivacion +
                ", Salario: " + salario;
    }

    /**
     * Revisa si el jugador es transferible con la condición del salario.
     * @return true si es transferible, false si no es transferible.
     */
    @Override
    public boolean esTransferible() {
        boolean transfrible = false;
            if (this.salario>=550000){ // si el salario es mayor o igual que 550000 se puede transferir
                transfrible = true;
            }

        return transfrible;
    }

    /**
     * Transfiere un jugador del original a equipo de destino.
     * @param equipo El equipo al que se transferirá el jugador.
     */
    @Override
    public void transferirAEquipo(Equipos equipo) {
        equipo.getJugadores().add(this);
        if (!(this.equipo ==null)){ //si el jugador ya tiene un equipo lo elimina
            this.equipo.getJugadores().remove(this);
        }
        this.equipo=equipo;
    }

    /**
     * Cambia la posicion del jugador dependiendo de una probabilidad del 5%
     * @return true si se realizó el cambio de posición, false si no
     */
    public boolean cambioPosicion (){
        boolean cambio = false;
        int probabilidad = (int) (Math.random()*100)+1; //numero random 0-100
        int nuevaPosicion;
        if(probabilidad <= 5){ //si el numero es menor o igual que 5
            nuevaPosicion = (int) (Math.random()*4)+1; //se le otorga un numero del 1 al 4

            switch (nuevaPosicion){ // dependiendo del número otorgado cambia la posición
                case 1:
                    this.posicion = "POR";
                    cambio = true;
                    break;
                case 2:
                    this.posicion = "DEF";
                    cambio = true;
                    break;
                case 3:
                    this.posicion = "MIG";
                    cambio = true;
                    break;
                case 4:
                    this.posicion = "DAV";
                    cambio = true;
                    break;
                default:
                    break;
            }
            this.calidad += 1;
        }
        return cambio;
    }

    /**
     * Sobreescribe el método entrenamiento.
     * Actualizando la calidad del jugador según una probabilidad.
     */
    @Override
    public void entrenamiento() {
        System.out.print(this.nombre + " - Antes: " + this.nivelMotivacion);
        super.entrenamiento();
        int probabilidad = (int)(Math.random()*100)+1; //número random del 1-100
        if (probabilidad <= 70){ // mayor de 30%
            this.calidad += 0.1;
        } else if (probabilidad < 90) { //entre 30% y 10%
            this.calidad += 0.2;
        } else{ //10%
            this.calidad += 0.3;
        }
        System.out.println("\tDespués: " + this.nivelMotivacion);
    }
}
