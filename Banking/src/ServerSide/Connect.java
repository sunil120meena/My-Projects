/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Sunil
 */
public class Connect 
{
    Connection conn=null;
    public Connection getConnection() throws SQLException
    {
       try
       {
           Class.forName("com.mysql.jdbc.Driver");
           conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking","root","");
       }
       catch(ClassNotFoundException | SQLException e)
       {
                   System.out.println("Databse connection at connect.java...."+e);

       }
        return conn;
       
    }
}
