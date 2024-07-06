package com.atm_machine.Connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static final String url = "jdbc:mysql://localhost:3306/atm_machine";
    private static final String user = "root";
    private static final String password = "Tu100den10*#";
    public static Connection getConnection() {
        Connection conn = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException|ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
