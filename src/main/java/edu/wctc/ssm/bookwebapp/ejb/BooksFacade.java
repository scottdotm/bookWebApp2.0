package edu.wctc.ssm.bookwebapp.ejb;

import edu.wctc.ssm.bookwebapp.model.Books;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Scott
 */
@Stateless
public class BooksFacade extends AbstractFacade<Books> {

     @PersistenceContext(unitName = "edu.wctc.ssm_bookWebApp_war_1.0-SNAPSHOTPU")
     private EntityManager em;

     @Override
     protected EntityManager getEntityManager() {
          return em;
     }

     public BooksFacade() {
          super(Books.class);
     }
     
}
