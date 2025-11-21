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
public class Consola {
 
    public static Nodo root;
    public static Nodo actual;
    
     public static void main(String[] args) {
         
        root = new Nodo("C:",false,"",null);
        
        actual = root;
        
        Scanner sc = new Scanner(System.in); 
        
         System.out.println("____Bienvenidos____");
         
         while(true){
             System.out.println(rutaActual()+">");
             String comando =sc.nextLine();
         }
         
        
        
     }
     public static String rutaActual(){
         
         Nodo temp = actual;
         String ruta = temp.nombre;
         
           while (temp.padre != null) { 
               
           }
         }
}
