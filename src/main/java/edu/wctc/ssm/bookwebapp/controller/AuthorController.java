package edu.wctc.ssm.bookwebapp.controller;

import edu.wctc.ssm.bookwebapp.ejb.AbstractFacade;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.wctc.ssm.bookwebapp.model.Author;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.naming.NamingException;

/**
 * This servlet takes care of the interaction between the View(interface) and
 * Model (data and calculations).
 *
 * @author Scott
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {


    @Inject
    private AbstractFacade<Author> aus;
    
    //No magic numbers
    private static final String ADD_PAGE = "Add.jsp";
    private static final String EDIT_PAGE = "Edit.jsp";
    private static final String DEST_PAGE = "Authors.jsp";
    private static final String ADMIN_PAGE = "Home.jsp";
    private static final String SUBMIT_ACTION = "submit";
    private static final String DELETE_ACTION = "Delete";
    private static final String EDIT_ACTION = "Edit";
    private static final String ADD_ACTION = "Add";
    private static final String UPDATE_ACTION = "Update";
    private static final String CREATE_ACTION = "Create";
    private static final String HOME_ACTION = "Take me Home";
    private static final String REFRESH_ACTION = "Back to List";
    private static final String ALT_REFRESH_PAGE = "Show me the Table";
    private static final String NAME_EMPTY = "Name cannot be Empty.";
    private static final String ID_EMPTY = "No Id passed into Edit.";
     /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
      * @throws javax.naming.NamingException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, NamingException {
        response.setContentType("text/html;charset=UTF-8");
        
        String destination = DEST_PAGE;
        String subAction = request.getParameter(SUBMIT_ACTION);

            String id = request.getParameter("authorId");
            String name = request.getParameter("authorName");
            //String date = request.getParameter("authorDate");
            Author author = null;

            //delete
            if (subAction.equals(DELETE_ACTION)) {
                if (id != null && !"".equals(id)) {
                    author = aus.find(new Integer(id));
                    aus.remove(author);
                } else {
                    String error = ID_EMPTY;
                    request.setAttribute("error", error);
                }
            }
            //edit
            if (subAction.equals(EDIT_ACTION)) {
                if (id != null && !"".equals(id)) {
                    author = aus.find(new Integer(id));
                    request.setAttribute("author", author);
                } else {
                    String error = ID_EMPTY;
                    request.setAttribute("error", error);
                }
                destination = EDIT_PAGE;
            }

            //Re-direct to Add page (could just be a link, decided to show understanding of submit actions)
            if (subAction.equals(ADD_ACTION)) {
                destination = ADD_PAGE;
            }

            //update
            if (subAction.equals(UPDATE_ACTION)) {
                if (name == null || "".equals(name)) {
                    String error = NAME_EMPTY;
                    request.setAttribute("error", error);
                    destination = EDIT_PAGE;
                }
                author = aus.find(new Integer(id));
              author.setAuthorName(name);
         }

            //create
            if (subAction.equals(CREATE_ACTION)) {
                if (name == null || "".equals(name)) {
                   String error = NAME_EMPTY;
                   request.setAttribute("error", error);
                   destination = ADD_PAGE;
              } else {
                   author = new Author();
                   author.setAuthorName(name);
                   author.setDateAdded(new Date());
                   aus.edit(author);
              }
         }

            //Essentially refresh the page
            if (subAction.equals(REFRESH_ACTION) || subAction.equals(ALT_REFRESH_PAGE)) {
                List<Author> authors = aus.findAll();
                request.setAttribute("authors", authors);
            }
            
            //Back to home page (admin page)
            if (subAction.equals(HOME_ACTION)){
                destination = ADMIN_PAGE;
            }

            //Default, put the Authors in the List and display the list of Authors
            List<Author> authors = aus.findAll();
            request.setAttribute("authors", authors);


        RequestDispatcher view = request.getRequestDispatcher(destination);
        view.forward(request, response);
    }


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
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException | NamingException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException | NamingException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Book Web App made for Dist. Java";
    }// </editor-fold>
    //Override for web.xml

}
