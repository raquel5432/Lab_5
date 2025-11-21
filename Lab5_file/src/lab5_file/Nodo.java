/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5_file;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ALISSONRAQUELMARTINE
 */
public class Nodo {
 
    public String nombre;
    public boolean esArchivo;
    public String contenido;
    public Map<String , Nodo> hijo;
    public Nodo padre;
    
    
    public Nodo(String nombre,boolean esArchivo, String contenido, Nodo padre ){
    
      this.esArchivo = esArchivo;
      this.padre = padre;
      this.nombre = nombre;
      
      if (esArchivo){
          contenido="";
          hijo = null; 
      }else{
          contenido = null;
          hijo = new HashMap<>();
      }   
    }
}
