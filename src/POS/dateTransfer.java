package POS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class dateTransfer {

	public dateTransfer() {
		try {
			Connection con  = Connect.getConnect();
		 	String details = "ALTER TABLE BillListDetails ADD date VARCHAR(30) NULL;";
	      PreparedStatement pst = (PreparedStatement) con.prepareStatement(details);
	        pst.executeUpdate();	
	        
	      PreparedStatement ps = (PreparedStatement) con.prepareStatement
	    		  ("Select * from BillListDetails WHERE ID NOT IN (SELECT MIN(ID) FROM BillListDetails) ORDER BY ID");
	        ResultSet rs= ps.executeQuery();	        
	        while(rs.next()){
			
			Connection con1 = BillConnect.getConnect();
			String details1 = "SELECT create_date\r\n"
					+ "FROM sys.tables\r\n"
					+ "WHERE name='"+rs.getString(1)+"'";
		      PreparedStatement pst1 = (PreparedStatement) con1.prepareStatement(details1);
		        ResultSet rs1= pst1.executeQuery();
		        
		        while(rs1.next()) {
		        		PreparedStatement ps1 = (PreparedStatement) con.prepareStatement("UPDATE BillListDetails SET date='"+rs1.getString(1).substring(0, rs1.getString(1).indexOf(" ")+1) +"'"
		        				+ "WHERE id='"+rs.getString(1)+"';");
				        ps1.executeUpdate();
		        }
		        
		        
		        
			
		}
	        
	        JOptionPane.showMessageDialog(null,"DONE");
		}
		catch (Exception e) {
			   
		}
	}
	
	public static void main(String[] args) {
		new dateTransfer();
	}

}
