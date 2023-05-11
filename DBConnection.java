/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package medicalbs;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author aniruddha
 */
public class DBConnection {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static Connection getConnection(){
        Connection con=null;
        try{
            Class.forName("org.postgresql.Driver");
            con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/medical_biling_system","postgres","password");
        }catch(Exception e){
            e.printStackTrace();
        }
        return con;
    }
    public static void main() throws Exception {
        // TODO code application logic here
       
    }
}
