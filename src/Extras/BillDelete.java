package Extras;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import POS.Connect;

public class BillDelete {

	public BillDelete() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		ArrayList <String> tableNames = new ArrayList <String>();
		tableNames.clear();
		
		try {
			Connection con = Connect.getConnect();
			String url = "SELECT ID FROM BillListDetails WHERE CustomerName='Counter Sale'";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			while(rs.next()) {
				tableNames.add(rs.getString(1));	
			}
			
			for(String tableName : tableNames) {
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
