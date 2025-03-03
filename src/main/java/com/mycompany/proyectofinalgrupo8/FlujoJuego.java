package com.mycompany.proyectofinalgrupo8;

import java.util.Scanner;

/**
 *
 * @author fernandafajardo
 * @author Andres Martinez
 */

public class FlujoJuego {
    private int numJugadores, tamPista;
    private Jugador[] jugadoresArray;
    private ColaJugadores colaJugadores;
    private PilaCastigos castigos;
    private PilaPremios premios;

    public FlujoJuego () {
        this.numJugadores = 0;
        this.jugadoresArray = new Jugador[numJugadores];
        this.colaJugadores = new ColaJugadores();
        this.premios = new PilaPremios();
        this.castigos = new PilaCastigos();
    }

    public int getNumJugadores() {
        return numJugadores;
    }

    public void setNumJugadores(int numJugadores) {
        this.numJugadores = numJugadores;
    }

    public int getTamPista() {
        return tamPista;
    }

    public void setTamPista(int tamPista) {
        this.tamPista = tamPista;
    }

    public Jugador[] getJugadoresArray() {
        return jugadoresArray;
    }

    public void setJugadoresArray(Jugador[] jugadoresArray) {
        this.jugadoresArray = jugadoresArray;
    }

    public ColaJugadores getColaJugadores() {
        return colaJugadores;
    }

    public void setColaJugadores(ColaJugadores colaJugadores) {
        this.colaJugadores = colaJugadores;
    }

    public PilaCastigos getCastigos() {
        return castigos;
    }

    public void setCastigos(PilaCastigos castigos) {
        this.castigos = castigos;
    }

    public PilaPremios getPremios() {
        return premios;
    }

    public void setPremios(PilaPremios premios) {
        this.premios = premios;
    }

    /***
     * Llena la cola de jugadores con los jugadores del arreglo "jugadoresArray".
     */
    public void llenarColaJugadores() {
        for (Jugador jugador : jugadoresArray) {
            colaJugadores.encolar(jugador,true);
            System.out.println("El jugador " + jugador.getNombre() + "ha sido agregado a la cola");
        }
    }
    
    /***
     * Llena la pila "premios" con bonificaciónes o castigos.
     */
    public void llenarPilasBonus() {
        premios.push("+",8,"Cargando premio");
        premios.push("+",2,"Cargando premio");
        premios.push("+",0,"Cargando premio");

        // Lenar pilas castigos
        premios.push("-",3,"Cargando castigo");
        premios.push("-",1,"Cargando castigo");
        premios.push("-",5,"Cargando castigo");
    }
    
    public boolean hayPremio() {
        return premios.getTop() == null;
    }

    public boolean hayCastigo() {
        return castigos.getTop() == null;
    }
    
    /***
     * Extrae un premio de la pila y devuelve la cantidad de avances que se aplicarán.
     * @return La cantidad de movimientos adicionales que obtiene el jugador como premio
     */
    public int aplicarPremio() {
        if (!hayPremio()) {
            System.out.println("La pila de premios está vacía...");
            return 0;
        } else {
            int premio = premios.pop().getNumero();
            System.out.println("Haz conseguido sacar un número par se te aplicará un premio");
            System.out.println("Como premio avanzarás " + premio + " veces");
            return premio;
        }
    }
    
    /***
     * Extrae un castigo de la pila y devuelve la cantidad de retrocesos que se aplicarán.
     * @return La cantidad de movimientos que le jugador debe retroceder como castigo
     */
    public int aplicarCastigo() {
        if (!hayCastigo()) {
            System.out.println("La pila de castigos está vacía...");
            return 0;
        } else {
            int castigo = castigos.pop().getNumero();
            System.out.println("Haz conseguido sacar un número impar se te aplicará un castigo");
            System.out.println("Como castigo, retrocederás " + castigo + " veces");
            return castigo;
        }
    }
    
    /***
     * Inicia el juego, permitiendo que cada jugador tenga su turno lanzando los dados.
     * Dependiendo del resultado, se les aplicará un premio o castigo.
     * @throws Exception Si ocurre un error inesperado durante la ejecución del juego
     */
    public void juego() throws Exception{
        Scanner scanner =  new Scanner(System.in);
        int totalDa2 = 0;
        Dados da2 = new Dados();
        
        //Loop para que cada jugador tenga su turno
        for(int i = 0; i < numJugadores; i++){
            Jugador jugadorTurno = colaJugadores.getFrente().getJugador();
            
            //Muestra quien es el siguiente en jugar
            colaJugadores.mostrarTurno();
            
            //Para mostrar los dados el jugador debera de dar Enter
            System.out.println("\n" + jugadorTurno.getNombre() + ", presione enter para lanzar los dados.");
            scanner.nextLine();
            
            //Tirar dados
            da2.tirar();
            //Muestra el resultado de los dados
            System.out.println(da2.mostrar());
            //Calcula el total de los dados
            totalDa2 = da2.getValorDado2() + da2.getValorDado1();
            
            //Aplica premio o castigo en base a valor total de los dados
            if(totalDa2 % 2 == 0){
                jugadorTurno.setPosicion(aplicarPremio());
            }else{
                jugadorTurno.setPosicion(aplicarCastigo());
            }
            
            //Mueve al jugador al final de la cola
            colaJugadores.encolar(colaJugadores.desencolar(), false);
        }
    }

}
