/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinalgrupo8;

/**
 *
 * @author fernandafajardo
 * @author William Bastos
 */
public class PilaPremios extends PilaGenerica {

    public PilaPremios() {
        super();
    }
    
    /**
     * Muestra el contenido de la pila de premios.
     */
    public void mostrarPremios(){
        System.out.println("Premios disponibles: ");
        NodoPila temp = getTop();
        while (temp != null){
            System.out.println("El premio con la descripcion: " + temp.getDescripcion() + " realizara la operacion: " + temp.getOperacion() + temp.getNumero() + " posiciones");
            temp = temp.getSig();
        }
        
    }

    public void agregarPremiosAleatorios(int numero) {
        for (int i = 0; i < numero; i++) {
            int numeroRandom = (int)(Math.random() * 10) + 1;
            this.push("+", numeroRandom, "Adelantas " + numeroRandom + " espacios.");
        }
    }
    
    
}
