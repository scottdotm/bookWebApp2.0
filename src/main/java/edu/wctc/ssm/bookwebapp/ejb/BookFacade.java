/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ssm.bookwebapp.ejb;

import edu.wctc.ssm.bookwebapp.model.Book;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Scott
 */
@Stateless
public class BookFacade extends AbstractFacade<Book> {

     @PersistenceContext(unitName = "edu.wctc.ssm_bookWebApp_war_1.0-SNAPSHOTPU")
     private EntityManager em;

     @Override
     protected EntityManager getEntityManager() {
          return em;
     }

     public BookFacade() {
          super(Book.class);
     }
     
}