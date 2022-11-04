/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author OS
 */
public class ConnectSQL {
//    private Connection cn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private static Connection cn; 
    public static Connection getConnectSQL(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cn = DriverManager.getConnection("jdbc:sqlserver://VT-PGH:1433;databaseName=STOREBODYMIST;user=sa;password=12345");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cn;
    }
}
