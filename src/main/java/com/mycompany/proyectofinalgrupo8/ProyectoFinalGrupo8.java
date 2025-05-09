/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectofinalgrupo8;

import com.mycompany.proyectofinalgrupo8.Librerias.Cola.NodoCola;
import com.mycompany.proyectofinalgrupo8.Librerias.Lista.ListaCircular;
import com.mycompany.proyectofinalgrupo8.Librerias.Lista.NodoDoble;
import com.mycompany.proyectofinalgrupo8.Librerias.Lista.ListaDobleC;
import java.util.Scanner;

/**
 *
 * @author Eddy Mena Lopez
 */
public class ProyectoFinalGrupo8 {

    public static void main(String[] args) throws Exception {
        
        // Variables
        boolean estaJugando = false; // Indica si el juego está en curso
        boolean permiteAgregarJugadores = true; // Controla si se pueden agregar jugadores durante la partida
        String opcionMenu = "0"; // Opción seleccionada en el menú principal
        boolean hayGanador = false; // Indica si ya hay un ganador en la partida
        int codigoAdministrador = 1010;
        
        // Constantes
        int MAXIMOJUGADORES = 4; // Número máximo de jugadores permitidos
        
        
        // Inicializaciones
        Scanner scanner = new Scanner(System.in); // Objeto scanner para pedir input a los jugadores
        
        PilaCastigos pilaCastigos = new PilaCastigos(); // Pila para almacenar castigos
        PilaPremios pilaPremios = new PilaPremios(); // Pila para almacenar premios
        ColaJugadores colaJugadores = new ColaJugadores(); // Cola para gestionar el turno de los jugadores
        ListaCircular laberinto = new ListaCircular(); // Lista circular que representa el laberinto del juego
        ListaDobleC bitacora = new ListaDobleC(); // Lista doblemente enlazada para el historial de jugadores
        ArbolChat chat = new ArbolChat(); //
        chat.preCargarArbol();
        // Menu principal         
        do {            
            System.out.println("1.Jugar" +
                    "\n2.Agregar jugador" +
                    "\n3.Estado de juego" +
                    "\n4.Bitácora-Historial" +
                    "\n5.Mantener pila" +
                    "\n6.Versión del juego" +
                    "\n7.ChatBot" +
                    "\n8.Salir" +
                    "\nSeleccione la opcion deseada: ");
            opcionMenu = scanner.next();
            switch (opcionMenu) {
                case "1" -> {
                    // Inicio del juego si no está en curso y no hay un ganador
                    if(!estaJugando && !hayGanador){ // primera partida se preguntan detalles de la partida
                        if(colaJugadores.esVacia()){
                            System.out.println("No se puede iniciar el juego sin Jugadores\n");
                            break;
                        }
                        // Preguntar tamanio laberinto
                        System.out.print("Ingrese el tamanio del laberinto?: ");
                        String tamanio = scanner.next();
                        laberinto = new ListaCircular();
                        laberinto.crearLaberinto(Integer.valueOf(tamanio));
                        
                        // Insertar jugadores en el laberinto
                        System.out.println("Se van a insertar estos jugadores: ");
                        colaJugadores.mostrarJugadores();
                        NodoCola actual = colaJugadores.getFrente();
                        for (int i = 0; i < colaJugadores.tamanoCola(); i++) {
                            laberinto.insertarJugador(actual.getJugador());
                            NodoDoble nodoJugador = bitacora.buscarJugador(actual.getJugador());
                            if(nodoJugador != null){
                                nodoJugador.getHistorial().insertaOrdenado(0, "Posicion 0 no tiene castigos/premios");
                                System.out.println("Se ha creado entrada en la bitacora");
                            } else {
                                System.out.println("- NO se ha creado entrada en la bitacora");
                            }
                            actual = actual.getSig();
                        }
                        System.out.println("Jugadores insertados " + laberinto.getPrimero().getNombreJugador());
                        
                        // Rellenar Castigos y Premios
                        pilaCastigos.agregarCastigosAleatorios(Integer.valueOf(tamanio));
                        pilaPremios.agregarPremiosAleatorios(Integer.valueOf(tamanio));
                        
                        // Preguntar agregar mas jugadores durante la partida
                        System.out.print("Desea permitir agregar jugadores durante la partida? (si/no): ");
                        String permite = scanner.next();
                        if(permite.toLowerCase().contentEquals("si")){
                            permiteAgregarJugadores = true;
                            System.out.println("Se permitira agregar jugadores durante la partida");
                        } else if (permite.toLowerCase().contentEquals("no")){
                            permiteAgregarJugadores = false;
                            System.out.println("No se permitira agregar jugadores durante la partida");
                        } else {
                            System.out.println("Error: opcion incorrecta... volviendo al menu");
                            break;
                        }
                        estaJugando = true;
                    }
                    
                    // Inicio de los turnos del juego
                    if(estaJugando && !colaJugadores.esVacia() && !hayGanador){
                        Jugar jugar = new Jugar(colaJugadores, pilaPremios, pilaCastigos, laberinto, bitacora);
                        if (jugar.turno()){
                            // si gano termina el juego
                            hayGanador = true;
                        }
                        
                    } else {
                        System.out.println("El juego se ha acabado ya hay un ganador");
                    }
                }
                case "2" -> {
                    // Agregar jugadores si está permitido
                    if(permiteAgregarJugadores){
                        Jugador jugadorObjeto;
                        if(colaJugadores.tamanoCola() <= MAXIMOJUGADORES){
                            System.out.println("Ingrese el nombre del Jugador");
                            String nuevoJugador = scanner.next();
                            int numeroJugador = colaJugadores.tamanoCola() + 1;
                            jugadorObjeto = new Jugador(nuevoJugador,numeroJugador);
                            colaJugadores.encolar(jugadorObjeto, true);
                            bitacora.insertarJugador(jugadorObjeto);
                            
                            // Agregar al laberinto si la partida está en curso
                            if(estaJugando){
                                // agregar al laberinto
                                laberinto.insertarJugador(jugadorObjeto);
                                bitacora.buscarJugador(jugadorObjeto).getHistorial().insertaOrdenado(0, "Posicion 0 no tiene castigos/premios");
                                System.out.println("Se ha creado entrada en la bitacora");
                            } 
                        } 
                    } else {
                        System.out.println("No se puede añadir jugador a la partida.\n");
                    }
                }
                case "3" -> {
                    // Mostrar estado del juego
                    if (estaJugando) {
                        // Llamamos al método de la clase EstadoJuego para imprimir el estado
                        EstadoJuego.imprimirEstadoJuego(colaJugadores, laberinto, estaJugando);
                    } else {
                        System.out.println("No hay una partida en curso.\n");
                    }  
                }
                case "4" -> {
                    // Ver bitácora de la partida
                    bitacora.verBitacora();
                }
                case "5" -> {
                    // Mantenimiento de pilas
                    System.out.println("Haz entrado a mantenimiento de pilas. Seleccione:" +
                            "\n1. Informacion de las pilas premio y castigo" +
                            "\n2. Llenar castigos" +
                            "\n3. Llenar premios" +
                            "\n4. Llenar ambos" +
                            "\n5. Crear castigo" +
                            "\n6. Crear premio");
                    int opcionPilas = scanner.nextInt();
                    if (opcionPilas == 1){
                        System.out.println("Informacion pilas: ");
                        System.out.println(">----------Pila premios--------<");
                        pilaPremios.mostrarPremios();
                        System.out.println(">-----------Pila castigos---------<");
                        pilaCastigos.mostrarCastigos();
                        System.out.println("Presione ENTER para continuar");
                    } else if (opcionPilas == 2) {
                        System.out.println("Llenando pila premios");
                        System.out.println("¿Cuantos premios desea agregar?");
                        int cantPremios = scanner.nextInt();
                        pilaPremios.agregarPremiosAleatorios(cantPremios);
                        System.out.println("Presione ENTER para continuar");
                    } else if (opcionPilas == 3) {
                        System.out.println("Llenando pila castigos");
                        System.out.println("¿Cuantos castigos desea agregar?");
                        int cantCastigos = scanner.nextInt();
                        pilaCastigos.agregarCastigosAleatorios(cantCastigos);
                        System.out.println("Presione ENTER para continuar");
                    } else if (opcionPilas == 4) {
                        System.out.println("Llenando pila premios");
                        System.out.println("¿Cuantos premios desea agregar?");
                        int cantPremios = scanner.nextInt();
                        pilaPremios.agregarPremiosAleatorios(cantPremios);
                        System.out.println("Llenando pila castigos");
                        System.out.println("¿Cuantos castigos desea agregar?");
                        int cantCastigos = scanner.nextInt();
                        pilaCastigos.agregarCastigosAleatorios(cantCastigos);
                        System.out.println("Presione ENTER para continuar");
                    } else if (opcionPilas == 5) {
                        // Crear castigo
                        System.out.println("Como esto es un castigo se van a restar movimientos extra.");
                        System.out.println("¿Cual es el numero de movimientos que desea agregar al castigo?");
                        int movimientosElegidos = scanner.nextInt();
                        pilaCastigos.agregarCastigoEspecifico(movimientosElegidos);
                        System.out.println("Presione ENTER para continuar");
                    } else if (opcionPilas == 6) {
                        // Crear premio
                        System.out.println("Como esto es un premio se van a agregar movimientos extra.");
                        System.out.println("¿Cual es el numero de movimientos que desea agregar al premio?");
                        int movimientosElegidos = scanner.nextInt();
                        pilaPremios.agregarPremioEspecifico(movimientosElegidos);
                        System.out.println("Presione ENTER para continuar");
                    } else {
                        System.out.println("No es una opcion valida...");
                    }
                }
                case "6" -> {
                    // Mostrar versión del juego
                    Ayuda.incrementarVersion();
                    Ayuda.incrementarVersion();
                    Ayuda.mostrarAyuda();
                }
                case "7" -> {
                    // Entrar al chatbot
                    // codigo iniciar chatbot
                    System.out.println("Seleccione la opcion que desea realizar:" +
                            "\n1. Ver chatbot" +
                            "\n2. Mantenimiento del chatbot" +
                            "\n3. Volver al menu anterior");
                    int opcionChatbot = scanner.nextInt();
                    if (opcionChatbot == 1){
                        //chat.preCargarArbol();
                        chat.iniciarChatBot();
                    } else if (opcionChatbot == 2){
                        // validar si es admin con codigo
                        System.out.println("Esta funcion es solo para administradores ingrese el codigo de administrador");
                        int codigo = scanner.nextInt();
                        if (codigo == codigoAdministrador) {
                            System.out.println("Codigo aceptado");
                            System.out.println("Seleccione la opcion que desea realizar:" +
                            "\n1. Insertar/Modificar preguntas padres" +
                            "\n2. Insertar/Modificar preguntas hijas" +
                            "\n3. Imprimir las preguntas" +
                            "\n4. Volver al menu principal");
                            
                            int opcionChatAdmin = scanner.nextInt();
                            
                            if (opcionChatAdmin == 1){
                                // Insertar/Modificar preguntas padres
                                System.out.println("--- Insertar/Modificar preguntas padres ---");
                                scanner.nextLine();

                                System.out.print("Ingrese el código del nodo padre (o deje vacío para raíz): ");
                                String codigoPadre = scanner.nextLine().trim();

                                System.out.print("Ingrese el nombre de la nueva/modificada pregunta padre: ");
                                String nombre = scanner.nextLine().trim();

                                chat.insertarOModificarPreguntaPadre(codigoPadre, nombre);
                            } else if (opcionChatAdmin == 2){
                                // Insertar/Modificar preguntas hijas
                                System.out.println("--- Insertar/Modificar preguntas hijas ---");
                                scanner.nextLine();
                                
                                System.out.println("Ingrese el código del nodo hoja donde desea agregar la pregunta: ");
                                String codigoNodo = scanner.nextLine().trim();
                                
                                System.out.println("Ingrese el código numérico de la pregunta (entero): ");
                                int codigoPregunta = 0;
                                try{
                                    codigoPregunta = Integer.parseInt(scanner.nextLine().trim());
                                }catch(NumberFormatException e){
                                    System.out.println("Error: El código debe de ser número entero.");
                                    break;
                                }
                                
                                System.out.println("Ingrese la pregunta: ");
                                String pregunta = scanner.nextLine().trim();
                                
                                System.out.println("Ingrese la respuesta: ");
                                String respuesta = scanner.nextLine().trim();
                                
                                chat.insertaroModificarPregHija(codigoNodo, codigoPregunta, pregunta, respuesta);
                            } else if (opcionChatAdmin == 3){
                                System.out.print("Ingrese el código del nodo para mostrar sus preguntas: ");
                                scanner.nextLine(); // limpiar buffer
                                String codigoNodo = scanner.nextLine();
                                chat.listarPreguntasPorNodo(codigoNodo);
                            } else if (opcionChatAdmin == 4){
                                System.out.println("Gracias por usar nuestro Chatbot\nVolviendo al menu principal..");
                            } else {
                                System.out.println("Error: codigo incorrecto volviendo al menu principal");
                            }
                        } else {
                            System.out.println("Error: codigo incorrecto volviendo al menu principal");
                        }
                    } else if (opcionChatbot == 3){
                        System.out.println("Volviendo al menu principal..");
                    } else {
                        System.out.println("No es una opcion valida...");
                    } 
                    
                }
                case "8" -> {
                    // Salir del juego
                    System.out.println("Gracias por jugar\nSaliendo..");
                    opcionMenu = "Q";
                }
                default -> System.out.println("Error intente de nuevo");
            }
            
        } while (opcionMenu != "Q");
        
        scanner.close();
    }
}
