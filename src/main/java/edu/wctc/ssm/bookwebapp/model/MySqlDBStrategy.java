package edu.wctc.ssm.bookwebapp.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.sql.DataSource;

/**
 * Allows generic access of our SQL database.
 * @author Scott
 */
@Dependent
public class MySqlDBStrategy implements DBStrategy,Serializable {

    private Connection conn;
    
    /** default constructor needed for dependency injection */
    public MySqlDBStrategy() {
    }

    @Override
    public final void openConnection(String driverClass, String url,
            String userName, String password) throws ClassNotFoundException, SQLException {

        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }

    @Override
    public final void closeConnection() throws SQLException {
        conn.close();
    }
    
    /**
     * Open a connection using a connection pool configured on server.
     *
     * @param ds - a reference to a connection pool via a JNDI name, producing
     * this object. Typically done in a servlet using InitalContext object.
      * @throws java.sql.SQLException
     */
    @Override
    public final void openConnection(DataSource ds) throws SQLException {
        try {
            conn = ds.getConnection();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(),ex.getCause());
        }
    }

    /**
     * Make sure you open and close connection when using method. Future
     * optimization may include change the return type of an Array.
     *
     * @param tableName
     * @param maxRecords - limit records found to first maxRecords or if
     * maxRecords is zero (0) then no limit.
     * @throws java.sql.SQLException
     * @return records
     */
    @Override
    public final List<Map<String, Object>> findAllRecords(String tableName, int maxRecords) throws SQLException {
        String sql;
        if (maxRecords <= 1) {
            sql = "select * from " + tableName;
        } else {
            sql = "select * from " + tableName + " limit " + maxRecords;
        }
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        List<Map<String, Object>> records = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> record = new HashMap<>();
            for (int colNo = 1; colNo <= columnCount; colNo++) {
                Object colData = rs.getObject(colNo);
                String colName = rsmd.getColumnName(colNo);
                record.put(colName, colData);
            }
            records.add(record);
        }

        return records;
    }

    /**
     * Deletes a record by its Id
     *
     * @param tableName
     * @param pkColName
     * @param value
     * @return psmt.executeUpdate();
     * @throws java.sql.SQLException
     */
    @Override
    public final int deleteById(String tableName, String pkColName, Object value) throws SQLException {
        String sql = "delete from " + tableName + " where " + pkColName + " = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setObject(1, value);
        return psmt.executeUpdate();
    }

    /**
	 * Updates one or more records in a table based on a single, matching field value.
	 * 
	 * @param tableName - a <code>String</code> representing the table name
     * @param colNames
	 * @param colDescriptors - a <code>List</code> containing the column descriptors for
	 * the fields that can be updated.
     * @param pkColName
     * @param value
	 * @param colValues - a <code>List</code> containing the values for the fields that
	 * can be updated.
	 * @param whereField - a <code>String</code> representing the field name for the
	 * search criteria.
	 * @param whereValue - n <code>Object</code> containing the value for the search criteria.
	 * @param closeConnection - true if connection should be closed automatically; if
	 * false, connection must be explicitly closed using the closeConnection method.
	 * @return an <code>int</code> containing the number of records updated.
	 * @throws SQLException if database access error or illegal sql
	 * @throws Exception for all other problems
	 */
    @Override
    public final int updateRecordById(String tableName, List<String> colNames, List<Object> colValues, String pkColName, Object value) throws SQLException {
        PreparedStatement pstmt = null;
        int recsUpdated = 0;

        // do this in an excpetion handler so that we can depend on the
        // finally clause to close the connection
        try {
            pstmt = buildUpdateStatement(conn, tableName, colNames, pkColName);

            final Iterator i = colValues.iterator();
            int index = 1;
            Object obj = null;

            // set params for column values
            while (i.hasNext()) {
                obj = i.next();
                pstmt.setObject(index++, obj);
            }
            // and finally set param for wehere value
            pstmt.setObject(index, value);

            recsUpdated = pstmt.executeUpdate();

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                throw e;
            } // end try
        } // end finally

        return recsUpdated;
    }
    
    /*
	 * Builds a java.sql.PreparedStatement for an sql insert
	 * @param conn - a valid connection
	 * @param tableName - a <code>String</code> representing the table name
	 * @param colDescriptors - a <code>List</code> containing the column descriptors for
	 * the fields that can be inserted.
	 * @return java.sql.PreparedStatement
	 * @throws SQLException
	 */
	private PreparedStatement buildInsertStatement(Connection conn_loc, String tableName, List colDescriptors) throws SQLException {
		StringBuffer sql = new StringBuffer("INSERT INTO ");
		(sql.append(tableName)).append(" (");
		final Iterator i=colDescriptors.iterator();
		while( i.hasNext() ) {
			(sql.append( (String)i.next() )).append(", ");
		}
		sql = new StringBuffer( (sql.toString()).substring( 0,(sql.toString()).lastIndexOf(", ") ) + ") VALUES (" );
		for( int j = 0; j < colDescriptors.size(); j++ ) {
			sql.append("?, ");
		}
		final String finalSQL=(sql.toString()).substring(0,(sql.toString()).lastIndexOf(", ")) + ")";
		//System.out.println(finalSQL);
		return conn_loc.prepareStatement(finalSQL);
	}

    /*
	 * Builds a java.sql.PreparedStatement for an sql update using only one where clause test
	 * @param conn - a JDBC <code>Connection</code> object
	 * @param tableName - a <code>String</code> representing the table name
	 * @param colDescriptors - a <code>List</code> containing the column descriptors for
	 * the fields that can be updated.
	 * @param whereField - a <code>String</code> representing the field name for the
	 * search criteria.
	 * @return java.sql.PreparedStatement
	 * @throws SQLException
     */
    private PreparedStatement buildUpdateStatement(Connection conn_loc, String tableName, List colNames, String pkColName) throws SQLException {
        StringBuffer sql = new StringBuffer("UPDATE ");
        (sql.append(tableName)).append(" SET ");
        final Iterator i = colNames.iterator();
        while (i.hasNext()) {
            (sql.append((String) i.next())).append(" = ?, ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")));
        ((sql.append(" WHERE ")).append(pkColName)).append(" = ?");
        final String finalSQL = sql.toString();
        return conn_loc.prepareStatement(finalSQL);
    }

    /**
     * Inserts one record into the database, id is created by database.
     * @param tablename - Name of Table
     * @param colNames - Names of columns
     * @param values - Values to be entered into columns
     * @return
     * @throws SQLException
     */
    @Override
    public final int insertOneRecord(String tablename, List<String> colNames, List<Object> values) throws SQLException {
        int recsUpdated = 0;
        try {
            PreparedStatement psmt = buildInsertStatement(conn, tablename, colNames);
            final Iterator i = values.iterator();
            int index = 1;
            Object obj = null;
            while (i.hasNext()) {
                obj = i.next();
                psmt.setObject(index++, obj);
            }
            recsUpdated = psmt.executeUpdate();

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        }

        return recsUpdated;
    }
    
    @Override
    public final Map<String, Object> findById(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws SQLException {

        String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKeyFieldName + " = ?";
        PreparedStatement stmt = null;
        final Map<String, Object> record = new HashMap();

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setObject(1, primaryKeyValue);
            ResultSet rs = stmt.executeQuery();
            final ResultSetMetaData metaData = rs.getMetaData();
            final int fields = metaData.getColumnCount();

            // Retrieve the raw data from the ResultSet and copy the values into a Map
            // with the keys being the column names of the table.
            if (rs.next()) {
                for (int i = 1; i <= fields; i++) {
                    record.put(metaData.getColumnName(i), rs.getObject(i));
                }
            }
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(),e.getCause());
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new SQLException(e.getMessage(),e.getCause());
            } // end try
        } // end finally

        return record;
    }
     
     //    @Override
//    public int deleteById(String tableName, String pkColName, Object value) throws SQLException {
//        String sql = "delete from " + tableName + " where " + pkColName + " = ?";
//        PreparedStatement psmt = conn.prepareStatement(sql);
//        psmt.setObject(1, value);
//        return psmt.executeUpdate();
//    }

    //Testing
//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        DBStrategy db = new MySqlDBStrategy();
//        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
//        List<String> colNames = Arrays.asList("author_name", "date_added");
//        List<Object> colValues = Arrays.asList("Lucifer", "2000-11-11");
//        int result = db.updateRecordById("author", colNames, colValues, "author_id", 33);
//        db.closeConnection();
//        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
//        List rawData = db.findAllRecords("author", 0);
//        System.out.println(rawData);
//        db.closeConnection();
//
//        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
//        Object name = "'Bob Johnson'";
//        Object date = new Date();
//        String table = "author";
//        String col1 = "author_name";
//        String col2 = "date_added";
//        List colNames = new ArrayList();
//        colNames.add(col1);
//        colNames.add(col2);
//        List values = new ArrayList();
//        values.add(name);
//        values.add(date);
//        db.insertOneRecord(table, colNames, values);
//        List rawData = db.findAllRecords("author", 0);
//        System.out.println(rawData);
//
//        db.closeConnection();
//    }
}
