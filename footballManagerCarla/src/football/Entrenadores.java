package football;
import java.util.Scanner;

public class Entrenadores extends Personas{

    private int id;
    private static int contador = 0;
    private int torneosGanados = 0;
    private boolean selecNacional;

    /**
     *  Constructor de la clase Entrenadores.
     * @param nombre El nombre del entrenador.
     * @param apellido El apellido del entrenador.
     * @param fechaNacimiento La fecha de nacimiento del entrenador (formato dd/mm/aaaa).
     * @param salario El salario anual del entrenador.
     * @param torneosGanados
     * @param selecNacional Indica si el entrenador es seleccionador nacional (true/false).
     * @param equipo
     */
    public Entrenadores(String nombre, String apellido, String fechaNacimiento, double salario, int torneosGanados, boolean selecNacional,Equipos equipo) {
        super(nombre, apellido, fechaNacimiento, salario,equipo);
        this.torneosGanados = torneosGanados;
        this.selecNacional = selecNacional;
        this.id = contador;
        contador++;
    }


    public int getId() {
        return this.id;
    }


    /**
     * Genera una representación en forma de cadena de texto de los datos del entrenador.
     * @return Una cadena que contiene los datos del entrenador.
     */
    @Override
    public String toString() {
        return "Entrenador: \n" +
                "Nombre: " + nombre +
                ", Apellido: " + apellido +
                ", Fecha de nacimiento: " + fechaNacimiento +
                ", Nivel de motivación: " + nivelMotivacion +
                ", Salario: " + salario +
                ", Torneos ganados: " + torneosGanados;
    }

    /**
     * Implementación del método de entrenamiento.
     * Incrementa el nivel de motivación del entrenador.
     */
    @Override
    public void entrenamiento() {
        System.out.print(this.nombre + "- Antes: " + this.nivelMotivacion);
        if(this.selecNacional){ //si selección nacional es true
            this.nivelMotivacion += 0.3;
        }else {
            this.nivelMotivacion += 0.15;
        }
        System.out.println("\tDespués: " + this.nivelMotivacion);
    }

    /**
     * Incrementa el salario del entrenador en un 0.5%.
     */
    public void incrementarSalario (){
        this.salario += (0.5 / this.salario) * 100;
    }
}
