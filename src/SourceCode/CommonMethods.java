package SourceCode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class CommonMethods {
	
	public static void setTotalFromCommon(JTable table, JLabel totalStationery, JLabel totalBook,JLabel totalBag,JLabel totalUniform,JLabel totalGarments,JLabel totalTotal, JLabel totalExpenses) {
			int rows = table.getRowCount();
			long stationery = 0,book=0,bag=0,uniform=0,Garments=0,total=0,expense=0;
			
			
			for (int i =0; i<rows;i++) {
				stationery += Long.valueOf(table.getValueAt(i,1).toString());
				book += Long.valueOf(table.getValueAt(i,2).toString());
				bag += Long.valueOf(table.getValueAt(i,3).toString());
				uniform += Long.valueOf(table.getValueAt(i,4).toString());
				Garments += Long.valueOf(table.getValueAt(i,5).toString());
				total += Long.valueOf(table.getValueAt(i,6).toString());
				expense += Long.valueOf(table.getValueAt(i,7).toString());
			}
				totalStationery.setText(String.valueOf(stationery));
				totalBook.setText(String.valueOf(book));
				totalBag.setText(String.valueOf(bag));
				totalUniform.setText(String.valueOf(uniform));
				totalGarments.setText(String.valueOf(Garments));
				totalTotal.setText(String.valueOf(total));
				totalExpenses.setText(String.valueOf(expense));
	}
	
	public static void putTableFromCommon(JTable table, String tableName) {
		try {
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
			dtm.setRowCount(0);
			Connection con =  Connect.getConnect();
			String url = "SELECT * FROM "+tableName;
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			while(rs.next()) {
				model.addRow(new Object[] {rs.getString(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6), rs.getString(7), rs.getString(8)});	
			}
		}
		catch(Exception e) {
			
		}

	}
	
	public static void timeFromCommon(JLabel time) {
		new Timer(0, new ActionListener(){
	           @Override
	           public void actionPerformed(ActionEvent e){
	                Date d= new Date()
		;
	        SimpleDateFormat a = new SimpleDateFormat("hh:mm:ss a");
	        time.setText(a.format(d));
	           }
	       }).start();
	}

}
