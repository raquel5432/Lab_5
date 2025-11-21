/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5_file;

import java.util.Scanner;

/**
 *
 * @author ALISSONRAQUELMARTINE
 */
public class Archivo {
    
    public String nombre;
    public String contenido;
    
    public Archivo(String nombre) {
        this.nombre = nombre;
        this.contenido = "";   
}

    public String getnombre(){
        return nombre;
        
    }
    
    public String getcontenido(){
        return contenido;
    }

    public void scribir(){
        contenido += texto +"\n";
        
    }






}
 
    