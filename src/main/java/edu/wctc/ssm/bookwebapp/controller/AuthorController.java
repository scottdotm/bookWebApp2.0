package edu.wctc.ssm.bookwebapp.controller;

import edu.wctc.ssm.bookwebapp.model.Author;
import edu.wctc.ssm.bookwebapp.ejb.AbstractFacade;
import edu.wctc.ssm.bookwebapp.model.Book;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The main controller for author-related activities. This servlet is configured
 * in web.xml, so no annotations are used here.
 *
 * @author Scott
 */
public class AuthorController extends HttpServlet {

    // NO MAGIC NUMBERS!
    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listAuthors.jsp";
    private static final String ADD_EDIT_AUTHORS_PAGE = "/addEditAuthors.jsp";
    private static final String LIST_ACTION = "list";
    private static final String ADD_EDIT_DELETE_ACTION = "addEditDelete";
    private static final String SUBMIT_ACTION = "submit";
    private static final String ADD_EDIT_ACTION = "Add/Edit";
    private static final String ACTION_PARAM = "action";
    private static final String SAVE_ACTION = "Save";
    private static final String CANCEL_ACTION = "Cancel";

    @Inject
    private AbstractFacade<Author> authService;
    @Inject
    private AbstractFacade<Book> bookService;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        Author author = null;

        try {
            /*
             Determine what action to take based on a passed in QueryString
             Parameter
             */
            switch (action) {
                case LIST_ACTION:
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                    break;

                case ADD_EDIT_DELETE_ACTION:
                    String subAction = request.getParameter(SUBMIT_ACTION);

                    if (subAction.equals(ADD_EDIT_ACTION)) {
                        // must be add or edit, go to addEdit page
                        String[] authorIds = request.getParameterValues("authorId");
                        if (authorIds == null) {
                            // must be an add action, nothing to do but
                            // go to edit page
                        } else {
                            // must be an edit action, need to get data
                            // for edit and then forward to edit page
                            
                            // Only process first row selected
                            String authorId = authorIds[0];
                            author = authService.find(new Integer(authorId));
                            request.setAttribute("author", author);
                        }

                        destination = ADD_EDIT_AUTHORS_PAGE;

                    } else {
                        // must be DELETE
                        // get array based on records checked
                        String[] authorIds = request.getParameterValues("authorId");
                        for (String id : authorIds) {
                            author = authService.find(new Integer(id));
                            authService.remove(author);
                        }

                        this.refreshList(request, authService);
                        destination = LIST_PAGE;
                    }
                    break;
                    
                case SAVE_ACTION:
                    String authorName = request.getParameter("authorName");
                    String authorId = request.getParameter("authorId");
                    if(authorId == null) {
                        // it must be new
                        author = new Author();
                        author.setAuthorName(authorName);
                        author.setDateAdded(new Date());
                        
                        Book book = new Book();
                        Set<Book> bookSet = new HashSet<>();
                        bookSet.add(book);
                        author.setBookSet(bookSet);
                        
                    } else {
                        // it must be an update
                        author = authService.find(new Integer(authorId));
                        author.setAuthorName(authorName);
                    }
                    
                    authService.edit(author);
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                    break;
                    
                case CANCEL_ACTION:
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                    break;

                default:
                    // no param identified in request, must be an error
                    request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
                    destination = LIST_PAGE;
                    break;
            }

        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }

        // Forward to destination page
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
        

    }

    // Avoid D-R-Y
    private void refreshList(HttpServletRequest request, AbstractFacade<Author> authService) throws Exception {
        List<Author> authors = authService.findAll();
        request.setAttribute("authors", authors);
    }

    /**
     * Called after the constructor is called by the container. This is the
     * correct place to do one-time initialization.
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        // Get init params from web.xml


        // You can't do the Java Reflection stuff here because exceptions
        // are thrown that can't be handled by this stock init() method
        // because the method signature can't be modified -- remember, you 
        // are overriding the method.
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
        processRequest(request, response);
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
        processRequest(request, response);
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

}
