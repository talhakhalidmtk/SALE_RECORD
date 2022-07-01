package POS;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


public class BillConnect {
	
	
	public static Connection getConnect(){
		Connection con = null;
		
		try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
            String url="jdbc:sqlserver://localhost:1433;databaseName=Bills;username=StudentTraders;password=Student";
            con = DriverManager.getConnection(url);
            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
		return con;
	}
	
	

	
	
	
}
