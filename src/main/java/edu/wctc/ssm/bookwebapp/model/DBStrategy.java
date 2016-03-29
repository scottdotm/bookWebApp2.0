/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ssm.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
/**
 *
 * @author Scott
 */
public interface DBStrategy {

    abstract void openConnection(String driverClass, String url, String userName, String password) throws ClassNotFoundException, SQLException;
    
    abstract void openConnection(DataSource ds) throws SQLException;

    abstract void closeConnection() throws SQLException;

    abstract List<Map<String, Object>> findAllRecords(String tableName, int maxRecords) throws SQLException;

    abstract Map<String, Object> findById(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws SQLException;

    abstract int deleteById(String tableName, String pkColName, Object value) throws SQLException;

    abstract int updateRecordById(String tableName, List<String> colNames, List<Object> colValues, String pkColName, Object value) throws SQLException;

    abstract int insertOneRecord(String tablename, List<String> colNames, List<Object> values) throws SQLException;
    
}
