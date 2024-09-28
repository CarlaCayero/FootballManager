package football;
/**
 * Clase abstracta que representa a una persona relacionada con el fútbol.
 */
public abstract class Personas{

    protected String nombre;
    protected String apellido;
    protected String fechaNacimiento;
    protected double nivelMotivacion;
    protected double salario;
    protected Equipos equipo;

    /**
     * Constructor de la clase Personas.
     * @param nombre El nombre de la persona.
     * @param apellido El apellido de la persona.
     * @param fechaNacimiento La fecha de nacimiento de la persona.
     * @param salario El salario de la persona.
     * @param equipo El equipo al que pertenece la persona.
     */
    public Personas(String nombre, String apellido, String fechaNacimiento, double salario, Equipos equipo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nivelMotivacion = 5;
        this.salario = salario;
        this.equipo = equipo;
    }


    public String getNombre() {
        return this.nombre;
    }
    public String getApellido() {
        return this.apellido;
    }


    /**
     * Aumenta el nivel de motivación de la persona en 0.2.
     */
    public void entrenamiento(){
        this.nivelMotivacion += 0.2;
    }
}
