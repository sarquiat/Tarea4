/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import DAO.*;
import DTO.Book;
import java.text.ParseException;

/**
 *
 * @author Usuario
 */
public class Test {
    
    public static void main(String[] args) throws ParseException {
            Conexion con = Conexion.getConexion();
            BookJpaController bj = new BookJpaController(con.getBd());
            Book b = new Book(0 ,"erick hace la tarea 4", "erick", 123);
            try {
                System.out.println(bj.findBook(1).toString());
            } catch (Exception e) {
                 System.out.println(e);
            }
           
    }
    
}
