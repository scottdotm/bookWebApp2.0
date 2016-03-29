/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ssm.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Scott
 */
public interface AuthorDaoStrategy {

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException;

    abstract int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException;

    public abstract int createOneAuthor(Object name, Object date) throws ClassNotFoundException, SQLException;

    public abstract int updateAuthor(Object id, Object name, Object date) throws ClassNotFoundException, SQLException;
    
    public abstract Author getAuthorById(Integer authorId) throws ClassNotFoundException, SQLException;

    public abstract DBStrategy getDb();

    public abstract void setDb(DBStrategy db);

    public abstract void initDao(String driver, String url, String user, String pass, String table, String colone, String coltwo, String primarykey);
    
    public abstract void initDao(DataSource ds) throws SQLException;

    public abstract void setDRIVER(String DRIVER);

    public abstract void setURL(String URL);

    public abstract void setUSER(String USER);

    public abstract void setPASSWORD(String PASSWORD);
    
    public abstract void setTable(String table);
    
    public abstract void setColone(String colone);
    
    public abstract void setColtwo(String coltwo);
    
    public abstract void setPrimarykey(String primarykey);
   
    public abstract void setDs(DataSource ds);
    
    public abstract String getPrimarykey();
    
    public abstract String getColtwo();
    
    public abstract String getColone();
    
    public abstract String getTable();

    public abstract String getPASSWORD();

    public abstract String getUSER();

    public abstract String getURL();

    public abstract String getDRIVER();
    
    public abstract DataSource getDs();

}
