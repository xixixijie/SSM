package com.ssm.util;

import java.sql.*;

/**
 * Created by xixi on 2018/6/15.
 */
public class DBUtil {
    public static Connection getConn(){
        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try {
                connection = DriverManager.getConnection("jdbc:oracle:thin:@10.25.49.20:1521:orcl","scott","tiger");
                if (connection == null) {
                    System.out.println(1);
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
    public static void beginTransaction(Connection connection){
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConn (Connection connection, Statement statement) {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
            if (!statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void closeConn (Connection connection, Statement statement, ResultSet rs){
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
            if (!statement.isClosed()) {
                statement.close();
            }
            if(!rs.isClosed()){
                rs.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void commit(Connection conn) {
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
