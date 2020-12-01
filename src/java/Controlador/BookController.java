/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DTO.Book;
import Negocio.Bookstore;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Usuario
 */
public class BookController extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Bookstore bk = new Bookstore();
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            Book b = bk.getLibro(bookId);
            String bookJsonString = new Gson().toJson(b);
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream out = response.getOutputStream();
            out.print(bookJsonString);
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            request.getSession().setAttribute("error", e.getMessage());
        }
//        String action = request.getServletPath();
//        
//        switch (action) {
//            case "/book/insert":
//                insertBook(request, response);
//                break;
//            case "/book/delete":
//                deleteBook(request, response);
//                break;
//            case "/book/edit":
//                showEditForm(request, response);
//                break;
//            case "/book/update":
//                updateBook(request, response);
//                break;
//            case "/book/get":
//                getBook(request, response);
//                break;
//            default:
//                listBooks(request, response);
//                break;
//        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Bookstore bk = new Bookstore();
            Book book = new Gson().fromJson(request.getReader().readLine(), Book.class);

            if (bk.insertarLibro(book.getBookId(), book.getTitle(), book.getAuthor(), book.getPrice())) {
                String ans = new Gson().toJson("Registro del libro " + book.getTitle() + " exitoso");
                response.setContentType("application/json;charset=UTF-8");
                ServletOutputStream out = response.getOutputStream();
                out.print(ans);
            } else {
                request.setAttribute("error", "Libro ya registrado en el sistema");
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println(e.getMessage());
            request.getSession().setAttribute("error", e.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Bookstore bk = new Bookstore();
//            int bookId = new Gson().fromJson(req.getReader().readLine(), );
            Book book = new Gson().fromJson(req.getReader().readLine(), Book.class);
            int bookId = book.getBookId();
            
            if (bk.eliminarLibro(bookId)) {
                String ans = new Gson().toJson("El libro " + bookId + " fue eliminado");
                resp.setContentType("application/json;charset=UTF-8");
                ServletOutputStream out = resp.getOutputStream();
                out.print(ans);
            } else {
                req.getSession().setAttribute("error", "No fue posible eliminar el libro");
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println(e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void insertBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Bookstore bk = new Bookstore();
            int bookId = 0;
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            float price = Float.parseFloat(request.getParameter("price"));

            if (bk.insertarLibro(bookId, title, author, price)) {
                String ans = new Gson().toJson("Registro del libro " + title + " exitoso");
                response.setContentType("application/json;charset=UTF-8");
                ServletOutputStream out = response.getOutputStream();
                out.print(ans);
            } else {
                request.setAttribute("error", "Libro ya registrado en el sistema");
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println(e.getMessage());
            request.getSession().setAttribute("error", e.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Bookstore bk = new Bookstore();
            int bookId = Integer.parseInt(request.getParameter("bookId"));

            if (bk.eliminarLibro(bookId)) {
                String ans = new Gson().toJson("El libro " + bookId + " fue eliminado");
                response.setContentType("application/json;charset=UTF-8");
                ServletOutputStream out = response.getOutputStream();
                out.print(ans);
            } else {
                request.getSession().setAttribute("error", "No fue posible eliminar el libro");
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println(e.getMessage());
            request.getSession().setAttribute("error", e.getMessage());
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("mandarlo al html de edicion").forward(request, response);
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Bookstore bk = new Bookstore();
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            float price = Float.parseFloat(request.getParameter("price"));
            Book b = new Book(bookId, title, author, price);

            if (bk.actualizarLibro(b)) {
                String ans = new Gson().toJson(b);
                response.setContentType("application/json;charset=UTF-8");
                ServletOutputStream out = response.getOutputStream();
                out.print(ans);
            } else {
                request.getSession().setAttribute("error", "Dato ya registrado en el sistema");
            }

        } catch (IOException | NumberFormatException e) {
            System.err.println(e.getMessage());
            request.getSession().setAttribute("error", e.getMessage());
        }
    }

    private void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Bookstore bk = new Bookstore();
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            Book b = bk.getLibro(bookId);
            String bookJsonString = new Gson().toJson(b);
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream out = response.getOutputStream();
            out.print(bookJsonString);
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            request.getSession().setAttribute("error", e.getMessage());
        }
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Bookstore bk = new Bookstore();
            List<Book> b = bk.getLibros();
            String booksJsonString = new Gson().toJson(b);
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream out = response.getOutputStream();
            out.print(booksJsonString);
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            request.getSession().setAttribute("error", e.getMessage());
        }
    }
}
