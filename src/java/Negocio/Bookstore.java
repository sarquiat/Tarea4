/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import DAO.*;
import DAO.exceptions.*;
import DTO.*;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;



/**
 *
 * @author Usuario
 */
public class Bookstore {
    private Book libro;
    private Conexion con;

    public Bookstore() {
        this.libro = new Book();
        this.con = Conexion.getConexion();
    }

    public boolean insertarLibro(int bookId, String title, String author, float price) throws ParseException{
        
        Book b = new Book(bookId, title, author, price);
        
        try {
            BookJpaController bj = new BookJpaController(con.getBd());
            bj.create(b);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Bookstore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean actualizarLibro(Book libro){
        
        try {
            BookJpaController bj = new BookJpaController(con.getBd());
            bj.edit(libro);
            return true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Bookstore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Bookstore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean eliminarLibro(int libro){
        try {
            BookJpaController bj = new BookJpaController(con.getBd());
            bj.destroy(libro);
            return true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Bookstore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public Book getLibro(int libro){
        BookJpaController bj = new BookJpaController(con.getBd());
        Book b = bj.findBook(libro);
        return b;
    }
    
    public List<Book> getLibros(){
        BookJpaController bj = new BookJpaController(con.getBd());
        List<Book> books = bj.findBookEntities();
        return books;
    }
}
