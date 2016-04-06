/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ssm.bookwebapp.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Scott
 */
@Entity
@Table(name = "books")
@XmlRootElement
@NamedQueries({
     @NamedQuery(name = "Books.findAll", query = "SELECT b FROM Books b"),
     @NamedQuery(name = "Books.findByBookId", query = "SELECT b FROM Books b WHERE b.bookId = :bookId"),
     @NamedQuery(name = "Books.findByTitle", query = "SELECT b FROM Books b WHERE b.title = :title"),
     @NamedQuery(name = "Books.findByIsbn", query = "SELECT b FROM Books b WHERE b.isbn = :isbn")})
public class Books implements Serializable {

     private static final long serialVersionUID = 1L;
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Basic(optional = false)
     @Column(name = "book_id")
     private Integer bookId;
     @Size(max = 255)
     @Column(name = "title")
     private String title;
     @Size(max = 45)
     @Column(name = "isbn")
     private String isbn;
     @JoinColumn(name = "author_id", referencedColumnName = "author_id")
     @ManyToOne
     private Author authorId;

     public Books() {
     }

     public Books(Integer bookId) {
          this.bookId = bookId;
     }

     public Integer getBookId() {
          return bookId;
     }

     public void setBookId(Integer bookId) {
          this.bookId = bookId;
     }

     public String getTitle() {
          return title;
     }

     public void setTitle(String title) {
          this.title = title;
     }

     public String getIsbn() {
          return isbn;
     }

     public void setIsbn(String isbn) {
          this.isbn = isbn;
     }

     public Author getAuthorId() {
          return authorId;
     }

     public void setAuthorId(Author authorId) {
          this.authorId = authorId;
     }

     @Override
     public int hashCode() {
          int hash = 0;
          hash += (bookId != null ? bookId.hashCode() : 0);
          return hash;
     }

     @Override
     public boolean equals(Object object) {
          // TODO: Warning - this method won't work in the case the id fields are not set
          if (!(object instanceof Books)) {
               return false;
          }
          Books other = (Books) object;
          if ((this.bookId == null && other.bookId != null) || (this.bookId != null && !this.bookId.equals(other.bookId))) {
               return false;
          }
          return true;
     }

     @Override
     public String toString() {
          return "edu.wctc.ssm.bookwebapp.model.Books[ bookId=" + bookId + " ]";
     }
     
}
