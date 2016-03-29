package edu.wctc.ssm.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.sql.DataSource;

/**
 *
 * @author Scott
 */
@Dependent
public class AuthorDao implements AuthorDaoStrategy, Serializable {

     @Inject
     private DBStrategy db;
     private DataSource ds;
     private String driver;
     private String url;
     private String user;
     private String pass;
     private String table = "author";
     private String colone = "author_name";
     private String coltwo = "date_added";
     private String primarykey = "author_id";
     private final List COLNAMES = new ArrayList();
     private final List VALUES = new ArrayList();

     /**
      * Blank Constructor for injectable objects
      */
     public AuthorDao() {
     }

     /**
      *
      * @param driver
      * @param url
      * @param user
      * @param pass
      * @param table
      * @param colone
      * @param primarykey
      * @param coltwo
      */
     @Override
     public final void initDao(String driver, String url, String user, String pass, String table, String colone, String coltwo, String primarykey) {
          setDRIVER(driver);
          setURL(url);
          setUSER(user);
          setPASSWORD(pass);
          setTable(table);
          setColone(colone);
          setColtwo(coltwo);
          setPrimarykey(primarykey);
     }

     @Override
     public void initDao(DataSource ds) throws SQLException {
          setDs(ds);
     }

     /**
      *
      * @return @throws ClassNotFoundException
      * @throws SQLException
      */
     @Override
     public final List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
          if (ds == null) {
               db.openConnection(driver, url, user, pass);
          } else {
               db.openConnection(ds);
          }

          List<Map<String, Object>> rawData
                  = db.findAllRecords("author", 0);
          List<Author> authors = new ArrayList<>();

          for (Map rec : rawData) {
               Author author = new Author();
               Integer id = new Integer(rec.get(primarykey).toString());
               author.setAuthorId(id);
               String name = rec.get(colone) == null ? "" : rec.get(colone).toString();
               author.setAuthorName(name);
               Date date = rec.get(coltwo) == null ? null : (Date) rec.get(coltwo);
               author.setDateAdded(date);
               authors.add(author);
          }

          db.closeConnection();

          return authors;
     }

     /**
      *
      * @param id
      * @return
      * @throws ClassNotFoundException
      * @throws SQLException
      */
     @Override
     public final int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException {
          if (ds == null) {
               db.openConnection(driver, url, user, pass);
          } else {
               db.openConnection(ds);
          }
          int result = db.deleteById(table, primarykey, id);
          db.closeConnection();
          return result;
     }

     /**
      *
      * @param name
      * @param date
      * @return
      * @throws ClassNotFoundException
      * @throws SQLException
      */
     @Override
     public final int createOneAuthor(Object name, Object date) throws ClassNotFoundException, SQLException {
          if (ds == null) {
               db.openConnection(driver, url, user, pass);
          } else {
               db.openConnection(ds);
          }
          COLNAMES.add(colone);
          COLNAMES.add(coltwo);
          VALUES.add(name);
          VALUES.add(date);
          int result = db.insertOneRecord(table, COLNAMES, VALUES);
          COLNAMES.clear();
          VALUES.clear();
          db.closeConnection();
          return result;
     }

     /**
      *
      * @param id
      * @param name
      * @param date
      * @return
      * @throws ClassNotFoundException
      * @throws SQLException
      */
     @Override
     public int updateAuthor(Object id, Object name, Object date) throws ClassNotFoundException, SQLException {
          COLNAMES.add(colone);
          COLNAMES.add(coltwo);
          VALUES.add(name);
          VALUES.add(date);
          if (ds == null) {
               db.openConnection(driver, url, user, pass);
          } else {
               db.openConnection(ds);
          }
          int result = db.updateRecordById(table, COLNAMES, VALUES, primarykey, id);
          COLNAMES.clear();
          VALUES.clear();
          db.closeConnection();
          return result;
     }

     /**
      *
      * @param authorId
      * @return
      * @throws ClassNotFoundException
      * @throws SQLException
      */
     @Override
     public final Author getAuthorById(Integer authorId) throws ClassNotFoundException, SQLException {
          if (ds == null) {
               db.openConnection(driver, url, user, pass);
          } else {
               db.openConnection(ds);
          }

          Map<String, Object> rawRec = db.findById(table, primarykey, authorId);
          Author author = new Author();
          author.setAuthorId((Integer) rawRec.get(primarykey));
          author.setAuthorName(rawRec.get(colone).toString());
          author.setDateAdded((Date) rawRec.get(coltwo));

          return author;
     }

     //Getters and Setters
     @Override
     public DataSource getDs() {
          return ds;
     }
     @Override
     public void setDs(DataSource ds) {
          this.ds=ds;
     }
     @Override
     public final String getTable() {
          return table;
     }

     @Override
     public final String getColone() {
          return colone;
     }

     @Override
     public final String getColtwo() {
          return coltwo;
     }

     @Override
     public final String getPrimarykey() {
          return primarykey;
     }

     @Override
     public final String getDRIVER() {
          return driver;
     }

     @Override
     public final String getURL() {
          return url;
     }

     @Override
     public final String getUSER() {
          return user;
     }

     @Override
     public final String getPASSWORD() {
          return pass;
     }

     @Override
     public final void setTable(String table) {
          this.table = table;
     }

     @Override
     public final void setColone(String colone) {
          this.colone = colone;
     }

     @Override
     public final void setColtwo(String coltwo) {
          this.coltwo = coltwo;
     }

     @Override
     public final void setPrimarykey(String primarykey) {
          this.primarykey = primarykey;
     }

     @Override
     public final void setDRIVER(String DRIVER) {
          this.driver = DRIVER;
     }

     @Override
     public final void setURL(String URL) {
          this.url = URL;
     }

     @Override
     public final void setUSER(String USER) {
          this.user = USER;
     }

     @Override
     public final void setPASSWORD(String PASSWORD) {
          this.pass = PASSWORD;
     }

     @Override
     public final DBStrategy getDb() {
          return db;
     }

     @Override
     public final void setDb(DBStrategy db) {
          this.db = db;
     }
     
     

//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        AuthorDaoStrategy dao = new AuthorDao();
//        Date date = new Date();
//        String name = "John Doe";
//        dao.createOneAuthor(name, date);
//        dao.updateAuthor(28, "Jim L.", 0);
//        List <Author> authors = dao.getAuthorList();
//        System.out.println(authors);
//    }
}
