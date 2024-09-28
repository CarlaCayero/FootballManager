import football.*;

import java.util.*;

public class Main {
    /**
     * Método principal que inicia la ejecución del programa.
     * @param args Los argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        boolean exit;
        int opcion;
        ArrayList<Equipos> equipos = generarArrayListEquipos();
        ArrayList<Personas> personas = generarArrayListPersonas();

        exit = false;
        do {
            opcion = menuFootballManager();
            switch (opcion) {
                case 1:
                    verClasificacionLiga(equipos);
                    break;
                case 2:
                    GestionarEquipos(equipos, personas);
                    break;
                case 3:
                    darDeAltaEquipo(equipos, personas);
                    break;
                case 4:
                    darDeAltaJugadorOEntrenador(personas);
                    break;
                case 5:
                    consultarDatosEquipo(equipos);
                    break;
                case 6:
                    consultarDatosJugador(equipos);
                    break;
                case 7:
                    disputarNuevaLiga(equipos);
                    break;
                case 8:
                    realizarEntrenamiento(personas);
                    break;
                case 0:
                    exit = true;
                    break;
            }

        }while(!exit);

    }

    private static void verClasificacionLiga(ArrayList<Equipos> equipos) {
        Collections.sort(equipos);
        System.out.println("Clasificación de la liga:");
        for (int i = 0; i < equipos.size(); i++) {
            System.out.println((i + 1) + ". " + equipos.get(i).getNombreEquipo() + " - Puntos: " + equipos.get(i).getPuntos() + ", Partidos disputados: " + equipos.get(i).getPartidosDisputados() + ", Goles a favor: " + equipos.get(i).getGolesAFavor() + ", Goles en contra: " + equipos.get(i).getGolesEnContra());
        }
    }

    /**
     * Realiza un entrenamiento para cada persona en la lista proporcionada.
     * Si la persona es un jugador, realiza su entrenamiento y verifica si hay cambio de posición.
     * Si la persona es un entrenador, realiza su entrenamiento y aumenta su salario.
     * @param personas personas La lista de personas a entrenar, que puede contener tanto jugadores como entrenadores.
     */
    private static void realizarEntrenamiento(ArrayList<Personas> personas) {
        boolean cambio = false; // Variable para controlar si se produjo un cambio de posición
        for (int i = 0; i < personas.size(); i++) { // Itera sobre cada persona en la lista
            if(personas.get(i) instanceof Jugadores){ // Verifica si la persona es un jugador
                ((Jugadores) personas.get(i)).entrenamiento(); // Realiza el entrenamiento del jugador
                cambio = ((Jugadores) personas.get(i)).cambioPosicion(); // Verifica si hay cambio de posición

                if (cambio){  // Si hay cambio de posición, muestra un mensaje
                    System.out.println("El jugador a cambiado de posición");
                }
            }
            if (personas.get(i) instanceof Entrenadores){ // Si la persona no es un jugador, es un entrenador
                ((Entrenadores) personas.get(i)).entrenamiento(); // Realiza el entrenamiento del entrenador
                ((Entrenadores) personas.get(i)).incrementarSalario(); // Incrementa el salario del entrenador
            }
        }
    }

    /**
     * Crea una nueva liga con los equipos proporcionados.
     * Solicita al usuario el nombre de la liga y la cantidad de equipos que participarán en ella.
     * @param equipos La lista de equipos disponibles para la nueva liga.
     */
    private static void disputarNuevaLiga(ArrayList<Equipos> equipos) {
        Scanner sc = new Scanner(System.in);
        String nombreLiga;
        int indiceEquipo;
        int i = 0;
        int numEquipos = 0;
        ArrayList<Equipos> ListaEquiposLiga = new ArrayList<>();

        for (Equipos equipo : equipos) {
            equipo.setPuntos(0);
            equipo.setGolesAFavor(0);
            equipo.setGolesEnContra(0);
            equipo.setPartidosDisputados(0);
            equipo.setPartidosEmpatados(0);
            equipo.setPartidosGanados(0);
        }

        System.out.print("Introduce el nombre de la Liga: ");
        nombreLiga = sc.nextLine();
        System.out.println("Bienvenido a la Liga " + nombreLiga);
        System.out.println("Cuantos equipos jugarán en "+ nombreLiga);
        numEquipos = sc.nextInt();

        while(i < numEquipos){ // Mientras no se hayan seleccionado suficientes equipos
            // Genera un índice aleatorio para elegir un equipo de la lista
            indiceEquipo = (int) (Math.random()* equipos.size());
            Equipos equipoElegido = equipos.get(indiceEquipo);

            if (!ListaEquiposLiga.contains(equipoElegido)){ //Comprueba que dentro del arraylist ya existe este equipo
                ListaEquiposLiga.add(equipoElegido);
                System.out.println("Se ha añadido el equipo " + equipoElegido.getNombreEquipo());
                i++;
            }
        }
        Liga nuevaLiga = new Liga(nombreLiga, ListaEquiposLiga);
        disputarPartido(nuevaLiga.getListaEquiposLiga());
    }

    /**
     * Imprime por pantalla el resultado de un partido entre dos equipos.
     * @param equipos El primer equipo que participó en el partido.
     * @param equipos1 El segundo equipo que participó en el partido
     * @param golesGanador Los goles marcados por el primer equipo.
     * @param golesPerdedor Los goles marcados por el segundo equipo.
     */
    private static void resultadosLiga(Equipos equipos, Equipos equipos1, int golesGanador, int golesPerdedor){
        System.out.println(equipos.getNombreEquipo()+" "+golesGanador+" - "+golesPerdedor+" "+equipos1.getNombreEquipo());
    }

    /**
     * Método que simula la disputa de partidos entre equipos de una liga.
     * Recorre el ArrayList ListaEquiposLiga, enfrenta equipos y otorga numeros random como goles
     */
    public static void disputarPartido(ArrayList<Equipos> listaEquiposLiga){
        for (int i = 0; i < listaEquiposLiga.size(); i++) {
            double iCalidadTotalJugadores = calcularCalidadJugadores(i,listaEquiposLiga); //calcula la calidad total de los jugadores del equipo i
            for (int j = i + 1; j < listaEquiposLiga.size() ; j++) {
                double jCalidadTotalJugadores = calcularCalidadJugadores(j,listaEquiposLiga); //calcula la calidad total de los jugadores del equipo i
                if(iCalidadTotalJugadores > jCalidadTotalJugadores){
                    // Genera números aleatorios como goles para el equipo ganador y perdedor
                    int golesGanador = (int) (Math.random()* 6);
                    int golesPerdedor = (int) (Math.random()*golesGanador);
                    // Actualiza los datos del equipo ganador y perdedor
                    listaEquiposLiga.get(i).ganarPartidos(golesGanador,golesPerdedor);
                    listaEquiposLiga.get(j).perderPartido(golesPerdedor, golesGanador);
                    resultadosLiga(listaEquiposLiga.get(i),listaEquiposLiga.get(j),golesGanador,golesPerdedor);
                } else if (iCalidadTotalJugadores == jCalidadTotalJugadores) {
                    //En caso de empate, asigna números aleatorios como goles para ambos equipos
                    int goles = (int) (Math.random()* 2)+1;
                    // Actualiza los datos de ambos equipos al empatar
                    listaEquiposLiga.get(i).empatarPartido(goles);
                    listaEquiposLiga.get(j).empatarPartido(goles);
                    resultadosLiga(listaEquiposLiga.get(i),listaEquiposLiga.get(j),goles,goles);
                } else if (iCalidadTotalJugadores < jCalidadTotalJugadores) {
                    // Genera números aleatorios como goles para el equipo ganador y perdedor
                    int golesGanador = (int) (Math.random()* 6);
                    int golesPerdedor = (int) (Math.random()*golesGanador);

                    // Actualiza los datos del equipo ganador y perdedor
                    listaEquiposLiga.get(i).perderPartido(golesPerdedor, golesGanador);
                    listaEquiposLiga.get(j).ganarPartidos(golesGanador,golesPerdedor);
                    resultadosLiga(listaEquiposLiga.get(j),listaEquiposLiga.get(i),golesGanador,golesPerdedor);
                }
            }
        }

    }

    /**
     * Método que calcula la calidad total de los jugadores de un equipo.
     * @param i Índice del equipo en la lista.
     * @return  La calidad total de los jugadores del equipo.
     */
    private static double calcularCalidadJugadores(int i, ArrayList<Equipos> listaEquiposLiga) {
        double puntos = 0;
        // Suma la calidad de cada jugador del equipo
        for (int j = 0; j < listaEquiposLiga.get(i).getJugadores().size(); j++) {
            puntos += listaEquiposLiga.get(i).getJugadores().get(j).getCalidad();
        }
        return puntos;
    }

    /**
     * Pide el equipo del que se quiere saber los datos de los jugadores y los muestra
     * @param equipos La lista de equipos disponibles para consultar.
     */
    private static void consultarDatosJugador(ArrayList<Equipos> equipos) {

        Scanner sc = new Scanner(System.in);
        String nombreEquipo;
        boolean existe = false;
        boolean encontrado = false;
        String nombre;
        int dorsal;


        System.out.println("Equipos:");
        for (Equipos e: equipos) {
            System.out.println("\t"+e.getNombreEquipo());
        }

        System.out.println("\nEscribe el nombre el Equipo del que quieras saber sus datos:");
        nombreEquipo = sc.nextLine();
        int i=0;
        // Busca el equipo en la lista de equipos
        while (!existe && i<equipos.size()){
            if (equipos.get(i).getNombreEquipo().equalsIgnoreCase(nombreEquipo)){
                existe=true;
            }
            else {
                i++;
            }
        }

        if (existe){ // Si el equipo existe, solicita al usuario el nombre y dorsal del jugador
            System.out.println("Escribe el nombre del jugador del que quieras saber todos sus datos: ");
            mostrarDatosJugador(equipos.get(i));
            nombre = sc.nextLine();
            System.out.println("Escribe el número del dorsal de tu jugador");
            dorsal = sc.nextInt();

            int j=0;
            while(j<equipos.get(i).getJugadores().size() && !encontrado){
                if (nombre.equalsIgnoreCase(equipos.get(i).getJugadores().get(j).getNombre()) && dorsal == equipos.get(i).getJugadores().get(j).getNumeroDorsal()){
                    System.out.println(equipos.get(i).getJugadores().get(j).toString());
                }
                j++;
            }

        }else {
            System.out.println("El equipo " + nombreEquipo + " no se ha encontrado");
        }

    }

    /**
     * Bucle que muestra los jugadores por su nombre y dorsal del equipo
     * @param equipos El equipo del que se van a mostrar los datos de los jugadores.
     */
    private static void mostrarDatosJugador(Equipos equipos) {
        for (int i = 0; i < equipos.getJugadores().size(); i++) {
            System.out.println("Nombre y Dorsal: " + equipos.getJugadores().get(i).getNombre() +" "+ equipos.getJugadores().get(i).getNumeroDorsal());

        }
    }

    /**
     * Muestra los nombres de los equipos y muestra los datos.
     *
     * @param equipos La lista de equipos disponibles para consultar.
     */
    private static void consultarDatosEquipo(ArrayList<Equipos> equipos) {
        Scanner sc = new Scanner(System.in);
        String nombreEquipo;
        boolean existe = false;

        System.out.println("Equipos:");
        for (Equipos e: equipos) {
            System.out.println("\t"+e.getNombreEquipo());
        }
        System.out.println("\nEscribe el nombre el Equipo del que quieras saber sus datos:");
        nombreEquipo = sc.nextLine();
        int i=0;
        while (!existe && i<equipos.size()){
            if (equipos.get(i).getNombreEquipo().equalsIgnoreCase(nombreEquipo)){
                existe=true;
            }
            else {
                i++;
            }
        }

        if (existe) {
            if (equipos.get(i).getEntrenador() != null && !equipos.get(i).getJugadores().isEmpty()) {
                mostrarDatosEquipo(equipos.get(i));
            } else {
                System.out.println("El equipo " + nombreEquipo + " no tiene suficientes datos para mostrar. Debe fichar al menos un jugador y un entrenador.");
            }
        }else {
            System.out.println("El equipo " + nombreEquipo + " no se ha encontrado");
        }

    }

    /**
     * Muestra los datos de un equipo, incluyendo sus jugadores y entrenador.
     * @param equipo El equipo del cual se mostrarán los datos.
     */
    private static void mostrarDatosEquipo(Equipos equipo) {
        System.out.println(equipo.toString());
        for (int i = 0; i < equipo.getJugadores().size(); i++) {
            System.out.println(equipo.getJugadores().get(i).toString());
        }
        System.out.println(equipo.getEntrenador().toString());

    }

    /**
     * El usuario elige entre dar de alta a un jugador o un entrenador y realiza la acción correspondiente.
     * @param personas ArrayList que contiene a las personas, donde se agregará el nuevo jugador o entrenador.
     */
    private static void darDeAltaJugadorOEntrenador(ArrayList<Personas> personas) {
        int alta;
        Scanner sc = new Scanner(System.in);

        System.out.println("Quieres dar de Alta a un:\t(Selecciona una opción) \n1- Jugador\n2- Entrenador");
        alta = sc.nextInt();

        if (alta == 1) {
            darAltaJugador(personas);

        } else if(alta == 2){
            darAltaEntrenador(personas);

        } else {
            System.out.println("Introduce 1 para fichar un Jugador o un 2 para fichar un Entrenador");
        }

    }

    /**
     * Pide los datos de un nuevo Entrenador y los añade al ArrayList de personas.
     * @param personas ArrayList que contiene a las personas, donde se agregará el nuevo Entrenador.
     */
    private static void darAltaEntrenador(ArrayList<Personas> personas) {
        Scanner sc = new Scanner(System.in);

        System.out.println("¡Genial, a continuación rellena los datos del nuevo entrenador: !");
        System.out.print("\t- Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("\t- Apellido: ");
        String apellido = sc.nextLine();

        System.out.print("\t- Fecha de nacimiento (dd/mm/aaaa): ");
        String nacimiento = sc.nextLine();

        System.out.print("\t- Salario anual: ");
        int salario = sc.nextInt();

        System.out.print("\t- Seleccionador estatal (true/false): ");
        boolean estatal = sc.nextBoolean();

        Entrenadores entrenador = new Entrenadores(nombre,apellido,nacimiento,salario,0,estatal,null);
        personas.add(entrenador);
    }

    /**
     * Pide los datos de un nuevo Jugador y los añade al ArrayList de personas.
     * @param personas ArrayList que contiene a las personas, donde se agregará el nuevo Jugador.
     */
    private static void darAltaJugador(ArrayList<Personas> personas) {
        Scanner sc = new Scanner(System.in);

        System.out.println("¡Genial, a continuación rellena los datos del nuevo jugador: !");
        System.out.print("\t- Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("\t- Apellido: ");
        String apellido = sc.nextLine();

        System.out.print("\t- Fecha de nacimiento (dd/mm/aaaa): ");
        String nacimiento = sc.nextLine();

        System.out.print("\t- Salario anual: ");
        int salario = sc.nextInt();

        System.out.print("\t- Dorsal: ");
        int dorsal = sc.nextInt();

        sc.nextLine();
        System.out.print("\t- Posición (DEF, POR, MIG, DAV): ");
        String posicion = sc.nextLine();

        Jugadores jugador = new Jugadores(nombre, apellido, nacimiento, salario, dorsal, posicion,null);
        personas.add(jugador);

    }

    /**
     * Da de alta un nuevo equipo en la lista de equipos.
     * @param equipos La lista de equipos existentes.
     * @param personas La lista de personas disponibles para fichar.
     */
    private static void darDeAltaEquipo(ArrayList<Equipos> equipos, ArrayList<Personas> personas) {
        String nombreEq;
        boolean existe;
        Scanner sc = new Scanner(System.in);
        int i = 0;


        do{
            System.out.println("Introduce el nombre del nuevo Equipo");
            nombreEq = sc.nextLine();

            existe = false;
            while(i < equipos.size() && !existe){
                if (equipos.get(i).getNombreEquipo().equalsIgnoreCase(nombreEq)){
                    existe=true;
                    System.out.println("Este nombre de equipo existe actualmente.");
                }
                i++;
            }

        }while(existe);

        System.out.println("¡Genial! a continuación rellena los datos del nuevo equipo: ");

        System.out.print("\t- Año de fundación: ");
        int anoFundacion = sc.nextInt();

        System.out.print("\t- Ciudad: ");
        String ciudad = sc.nextLine();

        sc.nextLine();
        System.out.print("\t- Nombre del Estadio: ");
        String nombreEstadio = sc.nextLine();

        System.out.print("\t- Nombre del presidente: ");
        String nombrePresidente = sc.nextLine();

        // Crear el nuevo equipo y añadirlo a la lista de equipos
        Equipos equipo = new Equipos(nombreEq, anoFundacion, ciudad, nombreEstadio, nombrePresidente, 0, 0,0,0);
        equipos.add(equipo);

        fichajes(equipo, personas);

    }

    /**
     * ficha jugador o entrenador
     * @param equipo El equipo al que se fichará el jugador o el entrenador.
     * @param personas La lista de personas disponibles para fichar.
     */
    private static void fichajes(Equipos equipo, ArrayList<Personas> personas) {
        System.out.println("\n¡Hora de fichar jugador!");
        ficharJugador(equipo, personas);
        System.out.println("\n¡Hora de fichar Entrenador!");
        ficharEntrenador(equipo, personas);
    }

    /**
     * Muestra el menú principal
     * @return la opción numerica que ha entrado por teclado.
     */
    private static int menuFootballManager() {
        Scanner sc1 = new Scanner (System.in);
        int opcion;

        System.out.println("\nBienvenido/a a Politécnics Fútbol Manager");
        System.out.println("\t1: Ver clasificación de La Liga");
        System.out.println("\t2: Gestionar equipos");
        System.out.println("\t3: Dar de alta a un equipo");
        System.out.println("\t4: Dar de alta a un jugador/a o entrenador/a");
        System.out.println("\t5: Consultar datos de equipo");
        System.out.println("\t6: Consultar datos de jugador/a");
        System.out.println("\t7: Disputar nueva liga");
        System.out.println("\t8: Realizar sesión de Entrenamiento");
        System.out.println("\t0: Salir");

        System.out.println("\nElige una opción:");
        opcion = sc1.nextInt();
        return opcion;
    }

    /**
     * Permite gestionar un equipo proporcionando un menú de opciones.
     * @param equipos La lista de equipos disponibles.
     * @param personas La lista de personas asociadas a los equipos.
     */
    private static void GestionarEquipos(ArrayList<Equipos> equipos, ArrayList<Personas> personas) {
        Scanner sc = new Scanner(System.in);
        String nombreEquipo;
        boolean existe = false;

        System.out.println("Equipos:");
        for (Equipos e: equipos) {
            System.out.println("\t"+e.getNombreEquipo());
        }
        System.out.println("\nEscribe el nombre el Equipo que quieras gestionar:");
        nombreEquipo = sc.nextLine();
        int i=0;
        // Buscar el equipo en la lista
        while (!existe && i<equipos.size()){
            if (equipos.get(i).getNombreEquipo().equalsIgnoreCase(nombreEquipo)){
                existe=true;
            }
            else {
                i++;
            }
        }

        if (existe){ // Mostrar menú de gestión si se encuentra el equipo
            mostrarMenuTeamManager(equipos,equipos.get(i), personas);
        }else {
            System.out.println("El equipo " + nombreEquipo + " no se ha encontrado");
        }

    }

    /**
     * Muestra el menú secundario y al terminar, devuelve al menú principal
     * @param equipos La lista de equipos disponibles.
     * @param equipo El equipo que se está gestionando.
     * @param personas La lista de personas asociadas a los equipos.
     */
    private static void mostrarMenuTeamManager(ArrayList<Equipos> equipos, Equipos equipo, ArrayList<Personas> personas) {
        Scanner sc = new Scanner (System.in);
        int opcion;

        do {
            System.out.println("\nTeam Manager:");
            System.out.println("\t1: Dar de baja equipo");
            System.out.println("\t2: Modificar presidente/a");
            System.out.println("\t3: Destituir entrenador/a");
            System.out.println("\t4: Fichar jugador/a o entrenador/a");
            System.out.println("\t5: Transferir jugador/a");
            System.out.println("\t0: Salir");

            System.out.print("\nElige una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    if (darDeBajaEquipo(equipos, equipo)){
                        opcion=0;
                        }
                    break;
                case 2:
                    modificarPresidente(equipo);
                    break;
                case 3:
                    destituirEntrenador(equipo, personas);
                    break;
                case 4:
                    ficharJugadorOEntrenador(equipo, personas);
                    break;
                case 5:
                    transferirJugador(equipo, equipos);
                    break;
                case 0:
                    System.out.println("Volviendo a Football Manager");
                    opcion = 0;
            }
        }while (opcion != 0);
    }

    /**
     * Transfiere un jugador del equipo origen al equipo de destino.
     * @param equipo El equipo del que se transfiere el jugador.
     * @param equipos La lista de equipos disponibles.
     */
    private static void transferirJugador(Equipos equipo, ArrayList<Equipos> equipos) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        String opcion1;

        // Mostrar jugadores transferibles del equipo origen
        System.out.println("Elige al jugador que quieras transferir: ");
        for (int i = 0; i < equipo.getJugadores().size(); i++) {
            if (equipo.getJugadores().get(i).esTransferible()) {
                System.out.println(equipo.getJugadores().get(i).getId() + ")- " + equipo.getJugadores().get(i).getNombre() + " " + equipo.getJugadores().get(i).getApellido());

            }
        }
        opcion = sc.nextInt();

        // Solicitar equipo de destino
        System.out.println("Elige el equipo de destino:");
        for (int i = 0; i < equipos.size(); i++) {
            if (!equipos.get(i).equals(equipo)){
                System.out.println("\t"+equipos.get(i).getNombreEquipo());
            }
        }
        sc.nextLine();
        opcion1 = sc.nextLine();
        // Transferir el jugador al equipo de destino
        equipo.getJugadores().get(opcion).transferirAEquipo(equipos.get(buscarEquipoPosicion(equipos,opcion1)));

    }

    /**
     * El usuario elige entre fichar a un jugador o un entrenador.
     * @param equipo El equipo al que se va a fichar.
     * @param personas La lista de personas disponibles (jugadores y entrenadores).
     */
    private static void ficharJugadorOEntrenador(Equipos equipo, ArrayList<Personas> personas) {
        Scanner sc = new Scanner(System.in);
        int fichar;
        boolean exit = false;

        do {
            System.out.println("Quieres fichar un:\t(Selecciona una opción) \n1- Jugador\n2- Entrenador");
            fichar = sc.nextInt();
            if (fichar == 1) {
                ficharJugador(equipo, personas);
                exit = true;

            } else if (fichar == 2) {
                ficharEntrenador(equipo, personas);
                exit = true;
            } else {
                System.out.println("Introduce 1 para fichar un Jugador o un 2 para fichar un Entrenador");
            }
        }while(!exit);

    }

    /**
     * El usuario ficha a un entrenador. Se añade a equipos y se elimina del ArrayList personas.
     * @param equipo El equipo al que se fichará el entrenador.
     * @param personas La lista de personas disponibles (jugadores y entrenadores).
     */
    private static void ficharEntrenador(Equipos equipo, ArrayList<Personas> personas) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        int i = 0;
        boolean encontrado = false;

        if(hayEntrenador(equipo) == false){ // Verifica si ya hay un entrenador en el equipo
            System.out.println("Entrenadores disponibles: ");
            mostrarEntrenadores(personas);
            System.out.println("Elige al entrenador: ");
            opcion = sc.nextInt();
            while(!encontrado && i < personas.size()) {
                if (personas.get(i) instanceof Entrenadores) {
                    if (opcion == ((Entrenadores) personas.get(i)).getId());{
                        equipo.setEntrenador((Entrenadores) personas.get(i));
                        personas.remove(personas.get(i));
                        encontrado = true;
                    }
                }
                i++;
            }

        }else{
            System.out.println("Ya hay un entrenador en este equipo");
        }

    }

    /**
     * El usuario ficha a un jugador. Se añade al equipo y se elimina del ArrayList personas.
     * @param equipo   El equipo al que se fichará el jugador.
     * @param personas La lista de personas disponibles (jugadores y entrenadores).
     */
    private static void ficharJugador(Equipos equipo, ArrayList<Personas> personas) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        int i = 0;
        boolean encontrado = false;

        System.out.println("Jugadores disponibles: ");
        mostrarJugadores(personas);
        System.out.println("Elige al jugador: ");
        opcion = sc.nextInt();

        while (!encontrado && i < personas.size()) {
            if (personas.get(i) instanceof Jugadores) {
                if (opcion == ((Jugadores) personas.get(i)).getId()) {
                    // Ficha al jugador seleccionado y lo elimina de la lista de personas
                    equipo.getJugadores().add((Jugadores) personas.get(i));
                    personas.remove(personas.get(i));
                    encontrado = true;
                }
            }
            i++;
        }
    }

    /**
     * Muestra los entrenadores disponibles en la lista de personas.
     * @param personas La lista de personas que incluye entrenadores y jugadores.
     */
    private static void mostrarEntrenadores(ArrayList<Personas> personas) {
        for (Personas p: personas) { // Itera sobre la lista de personas
            if (p instanceof Entrenadores){ // Verifica si la persona es un entrenador
                System.out.println(((Entrenadores) p).getId() + ")- " + p.getNombre() + " " + p.getApellido());
            }
        }
    }

    /**
     * Muestra los jugadores disponibles en la lista de personas
     * @param personas La lista de personas que incluye jugadores y entrenadores.
     */
    private static void mostrarJugadores(ArrayList<Personas> personas) {
        for (Personas p: personas) { // Itera sobre la lista de personas
            if(p instanceof Jugadores){  // Verifica si la persona es un jugador
                System.out.println(((Jugadores) p).getId() + ")- " + p.getNombre() + " "+ p.getApellido());
            }
        }
    }

    /**
     * Comprueba si el Equipo tiene entrenador asignado.
     * @param equipo El equipo a verificar.
     * @return true si el equipo tiene un entrenador, false si no
     */
    private static boolean hayEntrenador(Equipos equipo){
        boolean tiene = false;
        // Verifica si el equipo tiene un entrenador asignado
        if(equipo.getEntrenador() != null){
            tiene= true;
            System.out.println("Este equipo ya tiene un entrenador fichado");
        } else {
            System.out.println("Este equipo no tiene entrenador ");
        }
        return tiene;
    }

    /**
     * Destituye al entrenador del equipo y lo añade a la lista de personas.
     * @param equipo El equipo del cual se destituirá al entrenador.
     * @param personas La lista de personas donde se añadirá al entrenador destituido.
     */
    private static void destituirEntrenador(Equipos equipo, ArrayList<Personas> personas) {
        Scanner sc = new Scanner(System.in);
        String confirmar;

        System.out.println("¿Estás seguro que quieres destituir al entrenador? (si/no)");
        confirmar = sc.nextLine();

        if (confirmar.equalsIgnoreCase("si") || confirmar.equalsIgnoreCase("s")){
            hayEntrenador(equipo);
            Entrenadores entrenador = equipo.getEntrenador();  // Obtiene al entrenador y lo elimina del equipo
            equipo.setEntrenador(null);
            personas.add(entrenador);

        } else if (confirmar.equalsIgnoreCase("no") || confirmar.equalsIgnoreCase("n")) {
            System.out.println("Operación cancelada");

        } else {
            System.out.println("volviendo al menú Team Manager...");
        }


    }

    /**
     * Modifica el nombre del presidente del equipo.
     * @param equipo El equipo al que se modificará el nombre del presidente.
     */
    private static void modificarPresidente(Equipos equipo) {
    Scanner sc = new Scanner(System.in);
    String nuevoPresidente;

    System.out.println("Ingrese el nombre del nuevo presidente:");
    nuevoPresidente = sc.nextLine();
        // Verifica si el nuevo nombre del presidente ya existe
        if (nuevoPresidente.equalsIgnoreCase(equipo.getNombrePresidente())){
            System.out.println("Este presidente ya existe.");
        }else {
            equipo.setNombrePresidente(nuevoPresidente); // Modifica el nombre del presidente
            System.out.println("El nombre del presidente a sido modificado de manera satisfactoria.\nEl nombre es: " + equipo.getNombrePresidente());
        }


    }

    /**
     * Elimina un equipo del ArrayList de equipos.
     * @param equipos El ArrayList que contiene todos los equipos.
     * @param equipo El equipo que se eliminará.
     * @return true si se ha eliminado el equipo, false si se cancela la operación
     */
    private static boolean darDeBajaEquipo(ArrayList<Equipos> equipos, Equipos equipo) {
        Scanner sc = new Scanner(System.in);
        boolean eliminado = false;
        String confirmar;

        System.out.println("¿Estás seguro que quieres dar de baja este equipo? (si/no)");
        confirmar = sc.nextLine();

        if (confirmar.equalsIgnoreCase("si") || confirmar.equalsIgnoreCase("s")) {
            equipos.remove(equipo);
            System.out.println("\nEl equipo " + equipo.getNombreEquipo() + " ha sido eliminado");
            System.out.println("\n\nLista de equipos actualizada:");
            eliminado = true;
            for (Equipos eq : equipos) {
                System.out.println(eq.getNombreEquipo());
            }
        } else if (confirmar.equalsIgnoreCase("no") || confirmar.equalsIgnoreCase("n")) {
            System.out.println("Operación cancelada");
            eliminado = false;

        } else {
            System.out.println("volviendo al menú Team Manager...");
        }
        return eliminado;
    }

    /**
     * Busca la posición de un equipo en un ArrayList de equipos por su nombre.
     * @param equipos El ArrayList que contiene todos los equipos.
     * @param nombreEquipo El nombre del equipo que se está buscando.
     * @return la posición del equipo en el ArrayList, o -1 si no se encuentra.
     */
    private static int buscarEquipoPosicion(ArrayList<Equipos> equipos, String nombreEquipo) {
        boolean encontrado = false;
        int pos = 0;
        // Itera sobre el ArrayList de equipos mientras no se haya encontrado el equipo y no se haya llegado al final
        while(!encontrado && pos < equipos.size()){
            // Comprueba si el nombre del equipo en la posición actual coincide con el nombre buscado, ignorando mayúsculas y minúsculas
            if (equipos.get(pos).getNombreEquipo().equalsIgnoreCase(nombreEquipo)){
                encontrado = true;
            }else {
                pos++;
            }
        }
        // Si el equipo no se encuentra, se establece la posición como -1
        if (!encontrado) {
            pos = -1;
        }
        return pos; // Devuelve la posición del equipo, o -1 si no se encuentra
    }

    /**
     * Genera un ArrayList de personas que incluye tanto entrenadores como jugadores.
     * @return ArrayList de personas generadas.
     */
    private static ArrayList<Personas> generarArrayListPersonas() {
        // Creación de instancias de entrenadores
        Entrenadores entrenador1 = new Entrenadores("Didac", "Cervera", "14/07/1999", 325000, 0, true, null);
        Entrenadores entrenador2 = new Entrenadores("Sabina", "Gómez", "16/04/1990", 387000, 0,true, null);
        Entrenadores entrenador3 = new Entrenadores("Maribel", "Hernández", "22/04/1967", 412000, 0, true, null);
        Entrenadores entrenador4 = new Entrenadores("Marc", "Bosch", "13/09/1996", 462000, 0,true, null);
        // Creación de instancias de jugadores
        Jugadores jugador1 = new Jugadores("Axel", "Ortega", "16/05/2005", 640000, 24, "DEF", null);
        Jugadores jugador2 = new Jugadores("Haitham", "AlShawwa", "15/12/1997", 760000, 7, "MIG",null);
        Jugadores jugador3 = new Jugadores("Valentín", "Cayero", "11/12/1994", 642000, 8, "POR", null);
        Jugadores jugador4 = new Jugadores("Natalia", "Sabadell", "17/03/1989", 663000, 13, "DAV", null);
        Jugadores jugador5 = new Jugadores("Gemma","LLorenç", "01/03/1991", 692000, 1, "MIG", null);
        Jugadores jugador6 = new Jugadores("Kevin", "Cayero", "18/02/1991", 745500, 9, "DAV", null);


        ArrayList<Personas> personas = new ArrayList<>();
        personas.add(entrenador1);
        personas.add(entrenador2);
        personas.add(entrenador3);
        personas.add(entrenador4);
        personas.add(jugador1);
        personas.add(jugador2);
        personas.add(jugador3);
        personas.add(jugador4);
        personas.add(jugador5);
        personas.add(jugador6);

        return personas;
    }

    /**
     * Genera un ArrayList de equipos con algunos equipos predefinidos.
     * @return ArrayList de equipos generados.
     */
    private static ArrayList<Equipos> generarArrayListEquipos() {
        Equipos barcelona = new Equipos("Barcelona", 1899, "Barcelona", "CampNou", "Joan Laporta", 0, 0, 0, 0);
        Equipos madrid = new Equipos("Real Madrid", 1902, "Madrid", "Bernavéu", "Florentino Perez Rodriguez", 0, 0, 0, 0);

        ArrayList<Equipos> equipos = new ArrayList<>();
        equipos.add(barcelona);
        equipos.add(madrid);

        return equipos;
    }

}