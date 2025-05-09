/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinalgrupo8.Librerias.Lista;

import com.mycompany.proyectofinalgrupo8.Jugador;

/**
 *
 * @author Eddy Mena
 */
public class ListaCircular {
    private NodoCircular primero;
    private NodoCircular ultimo;
    
    public ListaCircular(){
        this.primero = null;
        this.ultimo = null;
    }

    /**
     * Obtiene el primer nodo de la lista circular.
     * 
     * @return El primer nodo de la lista.
     */
    public NodoCircular getPrimero() {
        return primero;
    }

    /**
     * Establece el primer nodo de la lista circular.
     * 
     * @param primero El nuevo primer nodo de la lista.
     */
    public void setPrimero(NodoCircular primero) {
        this.primero = primero;
    }

    /**
     * Obtiene el último nodo de la lista circular.
     * 
     * @return El último nodo de la lista.
     */
    public NodoCircular getUltimo() {
        return ultimo;
    }

    /**
     * Establece el último nodo de la lista circular.
     * 
     * @param ultimo El nuevo último nodo de la lista.
     */
    public void setUltimo(NodoCircular ultimo) {
        this.ultimo = ultimo;
    }
    
    /**
     * Calcula el tamaño del laberinto, es decir, el número de posiciones en la lista circular.
     * 
     * @return El número de posiciones en el laberinto.
     */
    public int tamanioLaberinto() {
        int posicionesLaberinto = 0;

        if (primero == null) {
            System.out.println("La lista esta vacia.");
            return posicionesLaberinto;
        } else if (primero == ultimo){
            posicionesLaberinto = 1;
            return posicionesLaberinto;
        }
        else {
            NodoCircular actual = getPrimero();
            NodoCircular fin = getUltimo();
            while (actual != fin) {
                posicionesLaberinto++;
                actual = actual.getSiguiente();
            }
            return posicionesLaberinto;
        }
        
    }
    
    /**
    * Crea un laberinto con el tamaño especificado.
    * @param tamanio Tamaño del laberinto a crear.
    */
    public void crearLaberinto(int tamanio){
        for (int i = 0; i <= tamanio; i++) {
            insertarOrdenado(i);
        }
        System.out.println("Se ha creado el laberinto con " + tamanioLaberinto() + " posiciones.");
        
    }
    
    /**
     * Mueve un jugador en el laberinto según el resultado de los dados y un posible bono o castigo.
     * @param jugador Jugador a mover.
     * @param dados Número obtenido en los dados.
     * @param bonus Bono o castigo aplicado.
     * @param operacion Tipo de operación (+, -, =).
     */
    public void moverJugador(Jugador jugador, int dados, int bonus, String operacion){
        int posicion = buscarJugador(jugador);
        int nuevaPosicion = 0;
        // calcular premio o castigo
        if(operacion == "+"){
            System.out.println("Haz conseguido sacar un numero par se te aplicara un premio");
            System.out.println("Como premio avanzaras " + bonus + " posiciones");
            nuevaPosicion = posicion + (dados + bonus);
            
        } else if (operacion == "-"){
            System.out.println("Haz conseguido sacar un numero impar se te aplicara un castigo");
            System.out.println("Como castigo te retrocederas " + bonus + " posiciones");
            nuevaPosicion = posicion + (dados - bonus);
            
        } else if (operacion == "="){
            System.out.println("Haz conseguido sacar un numero impar se te aplicara un castigo");
            System.out.println("Como castigo te retrocederas a la posicion 1");
            nuevaPosicion = 0;
            
        } else {
            System.out.println("Error: la operacion es incorrecta");
        }                

        // mover jugador a nueva posicion
        if( nuevaPosicion < 0){
            System.out.println("El jugador " + jugador.getNombre() + " esta en la posicion " + buscarJugador(jugador));
            if(removerJugador(jugador)){
                if (getPrimero().getNombreJugador().isBlank()){ // Se coloca en la primera posicion y no hay nadie
                    getPrimero().setNombreJugador(jugador.getNombre());
                } else if (!getPrimero().getNombreJugador().isBlank()){ // Se coloca en la primera posicion y pero ya hay alguien, se coloca separado con un :
                    String otrosJugadores = getPrimero().getNombreJugador();
                    getPrimero().setNombreJugador(otrosJugadores + ":" + jugador.getNombre());                    
                }
            }
        } else if (nuevaPosicion > tamanioLaberinto()) {
            System.out.println("El jugador " + jugador.getNombre() + " esta en la posicion " + buscarJugador(jugador));
            int posicionCalculada = tamanioLaberinto() - (nuevaPosicion - tamanioLaberinto());
            if(removerJugador(jugador)){
                NodoCircular actual = getPrimero();
                for (int i = 0; i < posicionCalculada; i++) {
                    actual = actual.getSiguiente();
                }
                if (actual.getNombreJugador().isBlank()){ // Se coloca en la primera posicion y no hay nadie
                    actual.setNombreJugador(jugador.getNombre());
                } else if (!actual.getNombreJugador().isBlank()){ // Se coloca en la primera posicion y pero ya hay alguien, se coloca separado con un :
                    String otrosJugadores = actual.getNombreJugador();
                    actual.setNombreJugador(otrosJugadores + ":" + jugador.getNombre());                    
                }
            }
            
        } else if (nuevaPosicion == tamanioLaberinto()){
            System.out.println("El jugador " + jugador.getNombre() + " esta en la posicion " + buscarJugador(jugador));
            if(removerJugador(jugador)){
                NodoCircular actual = getUltimo();
                if (actual.getNombreJugador().isBlank()){ // Se coloca en la primera posicion y no hay nadie
                    actual.setNombreJugador(jugador.getNombre());
                } else if (!actual.getNombreJugador().isBlank()){ // Se coloca en la primera posicion y pero ya hay alguien, se coloca separado con un :
                    String otrosJugadores = actual.getNombreJugador();
                    actual.setNombreJugador(otrosJugadores + ":" + jugador.getNombre());                    
                } 
                
                System.out.println("El jugador " + jugador.getNombre() + " ha llegado a la posicion final " + buscarJugador(jugador));
                System.out.println("Felicidades!");
            }
        } else if (nuevaPosicion > 0 ){
            System.out.println("El jugador " + jugador.getNombre() + " esta en la posicion " + buscarJugador(jugador));
            if(removerJugador(jugador)){
                NodoCircular actual = getPrimero();
                for (int i = 0; i < nuevaPosicion; i++) {
                    actual = actual.getSiguiente();
                }
                if (actual.getNombreJugador().isBlank()){ // Se coloca en la primera posicion y no hay nadie
                    actual.setNombreJugador(jugador.getNombre());
                } else if (!actual.getNombreJugador().isBlank()){ // Se coloca en la primera posicion y pero ya hay alguien, se coloca separado con un :
                    String otrosJugadores = actual.getNombreJugador();
                    actual.setNombreJugador(otrosJugadores + ":" + jugador.getNombre());                    
                }
            }
        } 
    }
    
    /**
     * Busca la posición de un jugador en el laberinto.
     * @param jugador Jugador a buscar.
     * @return Posición del jugador en el laberinto.
     */
    public int buscarJugador(Jugador jugador){
        int posicion = 0;

        NodoCircular actual = getPrimero();
        NodoCircular fin = getUltimo();
        while (actual != fin) {
            if(actual.getNombreJugador().contains(jugador.getNombre())){
                posicion = actual.getPosicion();
                return posicion;
            }
            actual = actual.getSiguiente();
        } 
        if(actual == fin){
            posicion = this.tamanioLaberinto();
        }
        return posicion;
        
    }
    /**
     * Remueve un jugador del laberinto.
     * @param jugador Jugador a remover.
     * @return true si el jugador fue removido, false en caso contrario.
     */
    public boolean removerJugador(Jugador jugador){      
        NodoCircular actual = getPrimero();
        NodoCircular fin = getUltimo();
        while (actual != fin) {
            if(actual.getNombreJugador().contains(jugador.getNombre())){
                String jugadores = actual.getNombreJugador();
                if (jugadores.contains(":")){
                    if(jugadores.contains((jugador.getNombre() + ":"))){
                        jugadores = jugadores.replaceFirst(jugador.getNombre() + ":", "");
                        actual.setNombreJugador(jugadores);
                    } else if (jugadores.contains((":" + jugador.getNombre()))){
                        jugadores = jugadores.replaceFirst(":" + jugador.getNombre(), "");
                        actual.setNombreJugador(jugadores);
                    }
                } else {
//                    System.out.println(jugadores);
                    jugadores = jugadores.replaceFirst(jugador.getNombre(), "");
                    actual.setNombreJugador(jugadores);
                }
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
    
    /**
     * Inserta un jugador en la primera posición del laberinto.
     * @param jugador Jugador a insertar.
     */
    public void insertarJugador(Jugador jugador){
        if(primero != null){
            if (getPrimero().getNombreJugador().isBlank()){ // Se coloca en la primera posicion y no hay nadie
                getPrimero().setNombreJugador(jugador.getNombre());
            } else if (!getPrimero().getNombreJugador().isBlank()){ // Se coloca en la primera posicion y pero ya hay alguien, se coloca separado con un :
                String otrosJugadores = getPrimero().getNombreJugador();
                getPrimero().setNombreJugador(otrosJugadores + ":" + jugador.getNombre());                    
            }   
        }
    }
    
    /**
     * Inserta un nuevo nodo en la lista circular de manera ordenada.
     * @param valor Valor del nodo a insertar.
     */
    public void insertarOrdenado(int valor){
        // caso 1: La lista esta vacia
        if (this.primero == null){ // siginifica que la lista esta vacia
            // paso 1.1 crear la cajita
            // paso 1.2 inicializo el primero
            primero = new NodoCircular(valor);
            // paso 1.3 ionicializar el ultimo
            ultimo = primero;
            // paso 1.4 la hago circular
            ultimo.setSiguiente(primero);
            
        // caso 2: El elemento a insertar es menor al primero
        } else if (valor <= primero.getPosicion()){
            // paso 2.1 crear la cajita
            NodoCircular aux = new NodoCircular(valor);
            // paso 2.2 amarramos la cajita al primero
            aux.setSiguiente(primero);
            // paso 2.3 mover el primero
            primero = aux;
            // paso 2.4 unir el ultimo al nuevo primero
            ultimo.setSiguiente(primero);
            
        // caso 3: El elemento a insertar es mayor al ultimo
        } else if (ultimo.getPosicion()< valor){
            // paso 3.1 crear la cajita
            NodoCircular aux = new NodoCircular(valor);
            // paso 3.2 Amarramos la cajita al primero
            ultimo.setSiguiente(aux);
            // paso 3.3 Mover el ultimo
            ultimo = aux;
            // paso 3.4 unir el nuevo ultimo al primero
            ultimo.setSiguiente(primero);
            
        // caso 4: El elemento a insertar va en una posicion interna de la lista
        } else {
            // vamos a iterar la lista para encontrar el lugar donde se inserta
            NodoCircular aux = primero;
            while(aux.getSiguiente().getPosicion()< valor){
                aux = aux.getSiguiente();
            }
            // paso 4.1 crear la cajita
            NodoCircular temp = new NodoCircular(valor);
            // paso 4.2 amarro el temp al aux.getSiguiente
            temp.setSiguiente(aux.getSiguiente());
            // paso 4.3 amarro el aux al nuevo siguiente (temp)
            aux.setSiguiente(temp);
        }
    }
}
