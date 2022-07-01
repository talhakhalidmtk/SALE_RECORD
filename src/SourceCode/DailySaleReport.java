package SourceCode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Extras.*;
import POS.MainSaleWindow;
import start.Splash;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import java.awt.Toolkit;
import java.awt.Window.Type;
import javax.swing.JToolBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


@SuppressWarnings("serial")
public class DailySaleReport extends JFrame {

	static Connection con;
	private JPanel contentPane;
	public static JLabel totalStationery;
	public static JTable table;
	private JTextField newStationery;
	private JTextField newBook;
	private JTextField newBag;
	private JTextField newUniform;
	private JTextField newGarments;
	static JLabel date  ;
	public static JLabel totalBook;
	public static JLabel totalBag;
	public static JLabel totalUniform;
	public static JLabel totalGarments;
	public static JLabel totalTotal;
	static JLabel totalExpenses, totalExpenses_2,totalExpenses_1;
	public static JLabel stationeryPayment;
	public static JLabel booksPayment;
	public static JLabel bagsPayment;
	public static JLabel uniformPayment;
	public static JLabel garmentsPayment;
	private static JLabel changeStationery;
	private static JLabel changeBook;
	private static JLabel changeBag;
	private static JLabel changeUniform;
	private static JLabel changeGarments;
	private static JLabel changeTotal;
	public static JLabel totalPayment;
	public static Object totalBooks;
	private JLabel totalShort;
	private JPanel expensePanel1;
	private JPanel expensePanel2;
	private JPanel expensePanel3;


	
	public static void setTableFromPOS(String stationery,String book,String bag, String uniform, String Garments) {
		createTables();
		createRows();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		if(stationery.length()==0) {
			stationery = "0";
		} if(book.length()==0) {
			book = "0";
		} if(bag.length()==0) {
			bag = "0";
		} if(uniform.length()==0) {
			uniform = "0";
		} if(Garments.length()==0) {
			Garments = "0";
		}
		String total = String.valueOf(Integer.valueOf(stationery)+ Integer.valueOf(book)+Integer.valueOf(bag)+Integer.valueOf(uniform)+
		Integer.valueOf(Garments));

		setTotal(table, totalStationery, totalBook, totalBag, totalUniform, totalGarments, totalTotal, totalExpenses, totalExpenses_1);
		
		int result = getEntryZeroForDaily();
		String tableName = getTableNameDay();
		if(result==-1) {
			try {
				Connection con = Connect.getConnect();
				PreparedStatement create1  = con.prepareStatement("INSERT INTO "+tableName+" (Serial, Stationery,Books,Bags,Uniform,Garments,TotalSale,TotalExpenses) VALUES ("+((int)table.getRowCount())+",'"+stationery+"','"+book+"','"+bag+"','"+uniform+"','"+Garments+"','"+total+"','0') \r\n");	
				create1.executeUpdate();
				model.addRow(new Object[] {stationery, book,bag,uniform,Garments,total,"0"});
				
				 
				setTotal(table, totalStationery, totalBook, totalBag, totalUniform, totalGarments, totalTotal, totalExpenses, totalExpenses_1);
				UpdateTable();		
			 }
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, e);
				} 

			
		}else {
			try {
				Connection con =  Connect.getConnect();
				
				PreparedStatement s1t = con.prepareStatement("UPDATE "+tableName+" SET [Stationery]=?, [Books]=?, [Bags]=?, [Uniform]=?, [Garments]=?, [TotalSale]=?, [TotalExpenses]=? WHERE [Serial]="+result);
				s1t.setString(1, stationery);
				model.setValueAt(stationery,result,0);
				s1t.setString(2, book);
				model.setValueAt(book,result,1);
				s1t.setString(3, bag);
				model.setValueAt(bag,result,2);
				s1t.setString(4, uniform);
				model.setValueAt(uniform,result,3);
				s1t.setString(5, Garments);
				model.setValueAt(Garments,result,4);
				s1t.setString(6, total);
				model.setValueAt(total,result,5);
				s1t.setString(7, table.getValueAt(result,6).toString());
				

				s1t.executeUpdate();
				setTotal(table, totalStationery, totalBook, totalBag, totalUniform, totalGarments, totalTotal, totalExpenses, totalExpenses_1);
				UpdateTable();		
			 }
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, e);
				} 
		}
		
	}
	
	
	
	public static String getTableNameDay() {
		String tableName = date.getText();
		 tableName = tableName.replace("-", "_");
		 tableName = tableName.replace(",", "_");
		 return tableName;
	}
	
	public static String getTableName() {
		String tableName = date.getText().substring(date.getText().indexOf("-")+1);
		 tableName = tableName.replace("-", "_");
		 return tableName;
	}
	
	public static String getTableNameMonth() {
		String tableNameMonth = date.getText().substring(date.getText().length()-4);
		 tableNameMonth = "Y"+tableNameMonth;
		 return tableNameMonth;
	}
	
	
	public static void putTable(JTable table, String tableName) {
		try {
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
			dtm.setRowCount(0);
			Connection con =  Connect.getConnect();
			String url = "SELECT * FROM "+tableName+" ORDER BY [Serial] ASC";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			while(rs.next()) {
				model.addRow(new Object[] {rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6), rs.getString(7)});	
			}
		}
		catch(Exception e) {
			//JOptionPane.showMessageDialog(null, e);
		}

	}
	
	
	public void setPaymentData() {
		try {

			con  = Connect.getConnect();
			
			
			PreparedStatement create4 = con.prepareStatement("IF OBJECT_ID(N'dbo.PaymentRecord', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE PaymentRecord \r\n"
					+ "(Date VARCHAR(100) ,Stationery int, Books int, Bags int ,Uniform int ,Garments int ,PRIMARY KEY (Date))\r\n"
					+ "END;");
			create4.executeUpdate();

			PreparedStatement create = con.prepareStatement("BEGIN IF NOT EXISTS (SELECT * FROM PaymentRecord WHERE Date = '"+date.getText().toString().replaceAll("-","_").replaceAll(",","_")+"')\r\n"
					+ "BEGIN\r\n"
					+ "   INSERT INTO PaymentRecord (Date,Stationery,Books,Bags,Uniform,Garments) VALUES ('"+date.getText().toString().replaceAll("-","_").replaceAll(",","_")+"','0','0','0','0','0') \r\n"
					+ "END END");
					
			create.executeUpdate();
			
			PreparedStatement create5 = con.prepareStatement("IF OBJECT_ID(N'dbo.PreviousBalance', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE PreviousBalance \r\n"
					+ "(Serial int, Date VARCHAR(45) ,Stationery int, Books int, Bags int ,Uniform int ,Garments int,Total int, Expenses int ,PRIMARY KEY (Date))\r\n"
					+ "END;");
			create5.executeUpdate();
			PreparedStatement create6 = con.prepareStatement("BEGIN IF NOT EXISTS (SELECT * FROM PreviousBalance WHERE Serial = '0')\r\n"
					+ "BEGIN\r\n"
					+ "   INSERT INTO PreviousBalance (Serial, Date,Stationery,Books,Bags,Uniform,Garments,Total,Expenses) VALUES ('0','0','0','0','0','0','0','0','0') \r\n"
					+ "END END");
					
			create6.executeUpdate();
			
			
			PreparedStatement create1 = con.prepareStatement("IF OBJECT_ID(N'dbo.ShopExpenses', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE ShopExpenses \r\n"
					+ "(Serial int ,Month VARCHAR(100), Balance VARCHAR(100), PRIMARY KEY (Serial))\r\n"
					+ "END;");
			create1.executeUpdate();
			
			
			Connection con =  Connect.getConnect();
			String url = "SELECT MAX(Serial) FROM ShopExpenses";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			while(rs.next()) {
				PreparedStatement create7 = con.prepareStatement("BEGIN IF NOT EXISTS (SELECT * FROM ShopExpenses WHERE Month = '"+date.getText().toString().substring(date.getText().indexOf("-")+1).replaceAll("-","_")+"')\r\n"
						+ "BEGIN\r\n"
						+ "   INSERT INTO ShopExpenses (Serial, Month,Balance) VALUES ('"+(rs.getInt(1)+1)+"','"+date.getText().toString().substring(date.getText().indexOf("-")+1).replaceAll("-","_")+"','0') \r\n"
						+ "END END");
						
				create7.executeUpdate();
			}
			
			
			
			
			}
			
			catch(Exception e) {
				//JOptionPane.showMessageDialog(null, e);
			} 
	}
	

	
	public static void createTables() {
		try {
			
			String tableNameDay = getTableNameDay();
			
			String tableNameMonth = getTableNameMonth();
			 
			con  = Connect.getConnect();
			
			
			
			
			PreparedStatement create4 = con.prepareStatement("IF OBJECT_ID(N'dbo.PaymentRecord', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE PaymentRecord \r\n"
					+ "(Date VARCHAR(100) ,Stationery int, Books int, Bags int ,Uniform int ,Garments int ,PRIMARY KEY (Date))\r\n"
					+ "END;");
			create4.executeUpdate();
			
			
			PreparedStatement create1 = con.prepareStatement("IF OBJECT_ID(N'dbo."+tableNameDay+"', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE "+tableNameDay +" \r\n"
					+ "(Serial int, Stationery VARCHAR(100), Books VARCHAR(100), Bags VARCHAR(100) ,Uniform VARCHAR(100) ,Garments VARCHAR(100) ,TotalSale VARCHAR(100),TotalExpenses VARCHAR(100),PRIMARY KEY (Serial))\r\n"
					+ "END;");
			create1.executeUpdate();
			
			
			
			
			PreparedStatement create2 = con.prepareStatement("IF OBJECT_ID(N'dbo."+tableNameMonth+"', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE "+tableNameMonth +" \r\n"
					+ "(Serial int, Month VARCHAR(100), Stationery VARCHAR(100), Books VARCHAR(100), Bags VARCHAR(100) ,Uniform VARCHAR(100) ,Garments VARCHAR(100) ,TotalSale VARCHAR(100),TotalExpenses VARCHAR(100) ,PRIMARY KEY (Month))\r\n"
					+ "END;");
			create2.executeUpdate();

			 String tableName = getTableName();
			 
			 
			PreparedStatement create = con.prepareStatement("IF OBJECT_ID(N'dbo."+tableName+"', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE "+tableName +" \r\n"
					+ "(Date VARCHAR(100), Stationery VARCHAR(100), Books VARCHAR(100), Bags VARCHAR(100) ,Uniform VARCHAR(100) ,Garments VARCHAR(100) ,TotalSale VARCHAR(100),TotalExpenses VARCHAR(100) ,PRIMARY KEY (Date))\r\n"
					+ "END;");
			create.executeUpdate();
			
			}
			
			catch(Exception e) {//JOptionPane.showMessageDialog(null, e);
				//System.out.println(e);
			} 
	}
	
	public static void createRows() {
		try {
			String tableName = getTableName();
			String tableNameMonth = getTableNameMonth();

			
			String rowColumnName = date.getText();
			rowColumnName = rowColumnName.replace(",","_");
			rowColumnName = rowColumnName.replace("-","_");
			String rowNameMonth = tableName;
			
			
			Connection con =  Connect.getConnect();
			String url = "SELECT MAX(Serial) FROM "+tableNameMonth;
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			while(rs.next()) {
				PreparedStatement create1 = con.prepareStatement("BEGIN IF NOT EXISTS (SELECT * FROM "+tableNameMonth+" WHERE Month = '"+rowNameMonth+"')\r\n"
						+ "BEGIN\r\n"
						+ "   INSERT INTO "+tableNameMonth+" (Serial, Month,Stationery,Books,Bags,Uniform,Garments,TotalSale,TotalExpenses) VALUES ("+(rs.getInt(1)+1)+",'"+rowNameMonth+"','0','0','0','0','0','0','0') \r\n"
						+ "END END");
						
				create1.executeUpdate();
			
			
			PreparedStatement create = con.prepareStatement("BEGIN IF NOT EXISTS (SELECT * FROM "+tableName+" WHERE Date = '"+rowColumnName+"')\r\n"
					+ "BEGIN\r\n"
					+ "   INSERT INTO "+tableName+" (Date,Stationery,Books,Bags,Uniform,Garments,TotalSale,TotalExpenses) VALUES ('"+rowColumnName+"','0','0','0','0','0','0','0') \r\n"
					+ "END END");
					
			create.executeUpdate();
			
			
			


			}
			
			
			}
			
			catch(Exception e) {
				//JOptionPane.showMessageDialog(null, e);
			} 
	}
	
	
	
	static void UpdateTableForDay(int index){
		String tableNameDay = getTableNameDay();
		try {
			Connection con =  Connect.getConnect();
	
			PreparedStatement s1t = con.prepareStatement("UPDATE "+tableNameDay+" SET [Stationery]=?, [Books]=?, [Bags]=?, [Uniform]=?, [Garments]=?, [TotalSale]=?, [TotalExpenses]=? WHERE [Serial]="+index);
			s1t.setString(1, table.getValueAt(index,0).toString());
			s1t.setString(2, table.getValueAt(index,1).toString());
			s1t.setString(3, table.getValueAt(index,2).toString());
			s1t.setString(4, table.getValueAt(index,3).toString());
			s1t.setString(5, table.getValueAt(index,4).toString());
			s1t.setString(6, table.getValueAt(index,5).toString());
			s1t.setString(7, table.getValueAt(index,6).toString());

			s1t.executeUpdate();	
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Update not successfull!!!");
		}
		
	}
	
	
	public static void getTotalForYear() {
		String tableName = getTableName();
		String tableNameMonth = getTableNameMonth();
		String rowNameMonth = tableName;
		
		try {
			Connection con =  Connect.getConnect();
			String url = "SELECT * FROM "+tableName;
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			int stationery = 0,book = 0, bag =0,uniform=0,Garments=0,total=0,expenses=0;
			while(rs.next()) {
				stationery+=Integer.valueOf(rs.getString(2));
				book+=Integer.valueOf(rs.getString(3));
				bag+=Integer.valueOf(rs.getString(4));
				uniform+=Integer.valueOf(rs.getString(5));
				Garments+=Integer.valueOf(rs.getString(6));
				total+=Integer.valueOf(rs.getString(7));
				expenses+=Integer.valueOf(rs.getString(8));
			}
			
			PreparedStatement st1 = con.prepareStatement("UPDATE "+tableNameMonth+" SET [Stationery]=?, [Books]=?, [Bags]=?, [Uniform]=?, [Garments]=?, [TotalSale]=?, [TotalExpenses]=? WHERE [Month]='"+rowNameMonth+"'");
			st1.setString(1, String.valueOf(stationery));
			st1.setString(2, String.valueOf(book));
			st1.setString(3, String.valueOf(bag));
			st1.setString(4, String.valueOf(uniform));
			st1.setString(5, String.valueOf(Garments));
			st1.setString(6, String.valueOf(total));
			st1.setString(7, String.valueOf(expenses));

			st1.executeUpdate();

			
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}
	
	
	public static void UpdateTable() {
		String tableName = getTableName();

		String rowColumnName = date.getText();
		rowColumnName = rowColumnName.replace(",","_");
		rowColumnName = rowColumnName.replace("-","_");

		try {
			Connection con =  Connect.getConnect();
			PreparedStatement st = con.prepareStatement("UPDATE "+tableName+" SET [Stationery]=?, [Books]=?, [Bags]=?, [Uniform]=?, [Garments]=?, [TotalSale]=?, [TotalExpenses]=? WHERE [Date]='"+rowColumnName+"'");

			st.setString(1, totalStationery.getText());
			st.setString(2, totalBook.getText());
			st.setString(3, totalBag.getText());
			st.setString(4, totalUniform.getText());
			st.setString(5, totalGarments.getText());
			st.setString(6, totalTotal.getText());
			st.setString(7, totalExpenses_1.getText());

			st.executeUpdate();	
			getTotalForYear();

			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
	}
	
	
	public static void insertRowsIntoDailyReport(String text, int i) {
		try {
			String tableName = getTableNameDay();
			PreparedStatement create1 = null;
			if(i==0) {
				create1 = con.prepareStatement("INSERT INTO "+tableName+" (Serial, Stationery,Books,Bags,Uniform,Garments,TotalSale,TotalExpenses) VALUES ('"+((int)table.getRowCount()-1)+"','"+text+"','0','0','0','0','"+text+"','0') \r\n");	
			}else if(i==1) {
				
					create1 = con.prepareStatement("INSERT INTO "+tableName+" (Serial, Stationery,Books,Bags,Uniform,Garments,TotalSale,TotalExpenses) VALUES ('"+((int)table.getRowCount()-1)+"','0','"+text+"','0','0','0','"+text+"','0') \r\n");	
				
			}else if(i==2) {
				
				create1 = con.prepareStatement("INSERT INTO "+tableName+" (Serial, Stationery,Books,Bags,Uniform,Garments,TotalSale,TotalExpenses) VALUES ('"+((int)table.getRowCount()-1)+"','0','0','"+text+"','0','0','"+text+"','0') \r\n");	
			
		}else if(i==3) {
			
			create1 = con.prepareStatement("INSERT INTO "+tableName+" (Serial, Stationery,Books,Bags,Uniform,Garments,TotalSale,TotalExpenses) VALUES ('"+((int)table.getRowCount()-1)+"','0','0','0','"+text+"','0','"+text+"','0') \r\n");	
		
	}		else if(i==4) {
		
		create1 = con.prepareStatement("INSERT INTO "+tableName+" (Serial, Stationery,Books,Bags,Uniform,Garments,TotalSale,TotalExpenses) VALUES ('"+((int)table.getRowCount()-1)+"','0','0','0','0','"+text+"','"+text+"','0') \r\n");	
	
}else if(i==6) {
	
	create1 = con.prepareStatement("INSERT INTO "+tableName+" (Serial, Stationery,Books,Bags,Uniform,Garments,TotalSale,TotalExpenses) VALUES ('"+((int)table.getRowCount()-1)+"','0','0','0','0','0','0','"+text+"') \r\n");	

}
			create1.executeUpdate();
			}
			
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			} 
	}
	
	
	public static void setTotal(JTable table, JLabel totalStationery, JLabel totalBook,JLabel totalBag,JLabel totalUniform,JLabel totalGarments,JLabel totalTotal, JLabel totalExpenses, JLabel totalExpenses_1) {
		int rows = table.getRowCount();
		long stationery = 0,book=0,bag=0,uniform=0,Garments=0,total=0,expenses=0;
		
		for (int i =0; i<rows;i++) {
			stationery += Long.valueOf(table.getValueAt(i,0).toString());
			book += Long.valueOf(table.getValueAt(i,1).toString());
			bag += Long.valueOf(table.getValueAt(i,2).toString());
			uniform += Long.valueOf(table.getValueAt(i,3).toString());
			Garments += Long.valueOf(table.getValueAt(i,4).toString());
			total += Long.valueOf(table.getValueAt(i,5).toString());
			expenses += Long.valueOf(table.getValueAt(i,6).toString());
		}
			totalStationery.setText(String.valueOf(stationery));
			totalBook.setText(String.valueOf(book));
			totalBag.setText(String.valueOf(bag));
			totalUniform.setText(String.valueOf(uniform));
			totalGarments.setText(String.valueOf(Garments));
			totalTotal.setText(String.valueOf(total));
			totalExpenses.setText(String.format("%.0f",((Double.valueOf(totalTotal.getText()))*10)/100));
			totalExpenses_1.setText(String.valueOf(expenses));
			totalExpenses_2.setText(String.valueOf(Integer.valueOf(totalExpenses.getText())-Integer.valueOf(totalExpenses_1.getText())));
			changeStationery.setText(String.valueOf(Integer.valueOf(totalStationery.getText())-Integer.valueOf(stationeryPayment.getText())));
			changeBook.setText(String.valueOf(Integer.valueOf(totalBook.getText())-Integer.valueOf(booksPayment.getText())));
			changeBag.setText(String.valueOf(Integer.valueOf(totalBag.getText())-Integer.valueOf(bagsPayment.getText())));
			changeUniform.setText(String.valueOf(Integer.valueOf(totalUniform.getText())-Integer.valueOf(uniformPayment.getText())));
			changeGarments.setText(String.valueOf(Integer.valueOf(totalGarments.getText())-Integer.valueOf(garmentsPayment.getText())));
			changeTotal.setText(String.valueOf(Integer.valueOf(totalTotal.getText())-Integer.valueOf(totalPayment.getText())));
			
	}
	
	
	public static int getEntryZero(JTable table,int i) {
		int rows = table.getRowCount();	
		int result = -1;
		if(rows>0) {
			for(int j =0;j<rows;j++) {
				String entry = table.getValueAt(j,i).toString();
				if(Integer.valueOf(entry) == 0) {
					result = j;
					break;
				}
			}
		}
		//System.out.println(result);
		return result;
	}
	
	public static void iterationThroughTable(JTable table, JTextField text, int i) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if(i==0) {
			model.addRow(new Object[] {text.getText(), "0","0","0","0",text.getText(),"0"});
		}else if (i==1) {
			model.addRow(new Object[] {"0", text.getText(),"0","0","0",text.getText(),"0"});
		}else if (i==2) {
			model.addRow(new Object[] {"0","0" ,text.getText(),"0","0",text.getText(),"0"});
		}else if (i==3) {
			model.addRow(new Object[] {"0", "0","0",text.getText(),"0",text.getText(),"0"});
		}else if (i==4) {
			model.addRow(new Object[] {"0", "0","0","0",text.getText(),text.getText(),"0"});
		}else if (i==6) {
			model.addRow(new Object[] {"0", "0","0","0","0","0",text.getText()});
		}

	}
	
	public static int getEntryZeroForDaily() {
		int rows = table.getRowCount();	
		int result = -1;
		if(rows>0) {
			for(int j =0;j<rows;j++) {
				String stationery = table.getValueAt(j,0).toString();
				String book = table.getValueAt(j,1).toString();
				String bag = table.getValueAt(j,2).toString();
				String uniform = table.getValueAt(j,3).toString();
				String Garments = table.getValueAt(j,4).toString();
				if(Integer.valueOf(stationery) == 0 && Integer.valueOf(book) == 0 && Integer.valueOf(bag) == 0 && Integer.valueOf(uniform) == 0 && Integer.valueOf(Garments) == 0) {
					result = j;
					break;
				}
			}
		}
		return result;
	}
	
	
	public void setTableNew() {
		createTables();
		createRows();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		String stationery = newStationery.getText();
		String book = newBook.getText();
		String bag = newBag.getText();
		String uniform = newUniform.getText();
		String Garments = newGarments.getText();
		
		if(stationery.length()==0) {
			stationery = "0";
		} if(book.length()==0) {
			book = "0";
		} if(bag.length()==0) {
			bag = "0";
		} if(uniform.length()==0) {
			uniform = "0";
		} if(Garments.length()==0) {
			Garments = "0";
		}
		String total = String.valueOf(Integer.valueOf(stationery)+ Integer.valueOf(book)+Integer.valueOf(bag)+Integer.valueOf(uniform)+
		Integer.valueOf(Garments));

		setTotal(table, totalStationery, totalBook, totalBag, totalUniform, totalGarments, totalTotal, totalExpenses, totalExpenses_1);
		
		int result = getEntryZeroForDaily();
		String tableName = getTableNameDay();
		if(result==-1) {
			try {
				Connection con = Connect.getConnect();
				PreparedStatement create1  = con.prepareStatement("INSERT INTO "+tableName+" (Serial, Stationery,Books,Bags,Uniform,Garments,TotalSale,TotalExpenses) VALUES ("+((int)table.getRowCount())+",'"+stationery+"','"+book+"','"+bag+"','"+uniform+"','"+Garments+"','"+total+"','0') \r\n");	
				create1.executeUpdate();
				model.addRow(new Object[] {stationery, book,bag,uniform,Garments,total,"0"});
				
				 
				setTotal(table, totalStationery, totalBook, totalBag, totalUniform, totalGarments, totalTotal, totalExpenses, totalExpenses_1);
				UpdateTable();		
				newStationery.setText("");newBook.setText("");newBag.setText("");newUniform.setText("");newGarments.setText("");
			 }
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, e);
				} 

			
		}else {
			try {
				Connection con =  Connect.getConnect();
				
				PreparedStatement s1t = con.prepareStatement("UPDATE "+tableName+" SET [Stationery]=?, [Books]=?, [Bags]=?, [Uniform]=?, [Garments]=?, [TotalSale]=?, [TotalExpenses]=? WHERE [Serial]="+result);
				s1t.setString(1, stationery);
				model.setValueAt(stationery,result,0);
				s1t.setString(2, book);
				model.setValueAt(book,result,1);
				s1t.setString(3, bag);
				model.setValueAt(bag,result,2);
				s1t.setString(4, uniform);
				model.setValueAt(uniform,result,3);
				s1t.setString(5, Garments);
				model.setValueAt(Garments,result,4);
				s1t.setString(6, total);
				model.setValueAt(total,result,5);
				s1t.setString(7, table.getValueAt(result,6).toString());
				

				s1t.executeUpdate();
				setTotal(table, totalStationery, totalBook, totalBag, totalUniform, totalGarments, totalTotal, totalExpenses, totalExpenses_1);
				UpdateTable();		
				newStationery.setText("");newBook.setText("");newBag.setText("");newUniform.setText("");newGarments.setText("");
			 }
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, e);
				} 
		}
		
	}
	
	
	public static void setTable(JTable table,JTextField text, int i) {
		int rows = table.getRowCount();	
		createTables();
		createRows();
		if(rows>0) {
			int index = getEntryZero(table,i);
			if(index==-1) {
				iterationThroughTable(table,text,i);
				setTotal(table, totalStationery, totalBook, totalBag, totalUniform, totalGarments, totalTotal, totalExpenses, totalExpenses_1);
				
				insertRowsIntoDailyReport(text.getText(), i);
				UpdateTable();
				text.setText("");
				
			}else {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.setValueAt(text.getText(), index, i);
				long totalVal = Long.valueOf((String) table.getValueAt(index,0))+Long.valueOf((String) table.getValueAt(index,1))+Long.valueOf((String) table.getValueAt(index,2))+
						Long.valueOf((String) table.getValueAt(index,3))+Long.valueOf((String) table.getValueAt(index,4));
				model.setValueAt(String.valueOf(totalVal), index, 5);
				
				setTotal(table, totalStationery, totalBook, totalBag, totalUniform, totalGarments, totalTotal, totalExpenses, totalExpenses_1);
				
				UpdateTableForDay(index);
				UpdateTable();
				text.setText("");
			}
		}else {
			iterationThroughTable(table,text,i);
			setTotal(table, totalStationery, totalBook, totalBag, totalUniform, totalGarments, totalTotal, totalExpenses, totalExpenses_1);
			
			insertRowsIntoDailyReport(text.getText(), i);
			UpdateTable();
			text.setText("");
			
		}
		
	}
	
	
	public void setPayment() {
		try {
			Connection con =  Connect.getConnect();
			String url = "SELECT SUM(Stationery),SUM(Books),SUM(Bags),SUM(Uniform),SUM(Garments) FROM PaymentRecord WHERE Date='"+date.getText().toString().replaceAll("-","_").replaceAll(",","_")+"' GROUP BY Date";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			while(rs.next()) {
				stationeryPayment.setText(rs.getString(1));
				booksPayment.setText(rs.getString(2));
				bagsPayment.setText(rs.getString(3));
				uniformPayment.setText(rs.getString(4));
				garmentsPayment.setText(rs.getString(5));
			}
				totalPayment.setText(String.valueOf(Integer.valueOf(stationeryPayment.getText())+Integer.valueOf(booksPayment.getText())+Integer.valueOf(bagsPayment.getText())+Integer.valueOf(uniformPayment.getText())+Integer.valueOf(garmentsPayment.getText())));
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}
	
	
	public void setShortTotalValue() {
		String stationery = newStationery.getText();
		String book = newBook.getText();
		String bag = newBag.getText();
		String uniform = newUniform.getText();
		String Garments = newGarments.getText();
		
		if(stationery.length()==0) {
			stationery = "0";
		} if(book.length()==0) {
			book = "0";
		} if(bag.length()==0) {
			bag = "0";
		} if(uniform.length()==0) {
			uniform = "0";
		} if(Garments.length()==0) {
			Garments = "0";
		}
		String total = String.valueOf(Integer.valueOf(stationery)+ Integer.valueOf(book)+Integer.valueOf(bag)+Integer.valueOf(uniform)+
		Integer.valueOf(Garments));
		
		totalShort.setText(total);
	}
	
	
	
	public DailySaleReport() {
		
		CashDeatils cd = new CashDeatils();
		cd.setVisible(false);;
		cd.dispose();
		
		
		 
		setFont(new Font("Century Gothic", Font.PLAIN, 16));
		
		try {
            //here you can put the selected theme class name in JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
            

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DailySaleReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DailySaleReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DailySaleReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DailySaleReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

		ActionListener action=new ActionListener(){
			   public void actionPerformed(ActionEvent ae)
			   {
				   new DailySaleReport().setVisible(true);
					dispose();
			   }
			};
			getRootPane().registerKeyboardAction(action,KeyStroke.getKeyStroke("F1"),JComponent.WHEN_IN_FOCUSED_WINDOW);
	
		
		ActionListener action1=new ActionListener(){
			   public void actionPerformed(ActionEvent ae)
			   {
				   new MonthlySaleRecord().setVisible(true);
					dispose();
			   }
			};
			getRootPane().registerKeyboardAction(action1,KeyStroke.getKeyStroke("F2"),JComponent.WHEN_IN_FOCUSED_WINDOW);
			
			ActionListener action2=new ActionListener(){
				   public void actionPerformed(ActionEvent ae)
				   {
					   new YearlyRecord().setVisible(true);
						dispose();
				   }
				};
				getRootPane().registerKeyboardAction(action2,KeyStroke.getKeyStroke("F3"),JComponent.WHEN_IN_FOCUSED_WINDOW);
				
				ActionListener action5=new ActionListener(){
					   public void actionPerformed(ActionEvent ae)
					   {
						   new PartyTrialBalance().setVisible(true);
							dispose();
					   }
					};
					getRootPane().registerKeyboardAction(action5,KeyStroke.getKeyStroke("F4"),JComponent.WHEN_IN_FOCUSED_WINDOW);
				
				
				ActionListener action3=new ActionListener(){
					   public void actionPerformed(ActionEvent ae)
					   {
						   new ShopExpenses().setVisible(true);
						dispose();
					   }
					};
					getRootPane().registerKeyboardAction(action3,KeyStroke.getKeyStroke("F5"),JComponent.WHEN_IN_FOCUSED_WINDOW);
	
		
		
		ImageIcon img =new ImageIcon(Splash.class.getResource("/SourceCode/edit.png"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DailySaleReport.class.getResource("/SourceCode/edit.png")));
		
					
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();  
		setMaximizedBounds(env.getMaximumWindowBounds());  
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.BLACK);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLayout(null);
		panel.setBounds(10, 671, 1330, 24);
		contentPane.add(panel);
		Date d= new Date();
		SimpleDateFormat a = new SimpleDateFormat("EEEE,dd-MMMM-yyyy");

		JLabel time = new JLabel("Time:");
		time.setForeground(Color.BLACK);
		time.setHorizontalAlignment(SwingConstants.RIGHT);
		time.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		time.setBounds(1219, 0, 111, 24);
		panel.add(time);
		
		date = new JLabel("Date:");
		date.setForeground(Color.BLACK);
		date.setHorizontalAlignment(SwingConstants.RIGHT);
		date.setBounds(890, 0, 319, 24);
		panel.add(date);
		date.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		date.setText(a.format(d));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 1330, 464);
		contentPane.add(scrollPane);
		JScrollBar sb = scrollPane.getVerticalScrollBar();
        sb.setPreferredSize(new Dimension(10, 0));
		
		table = new JTable();
		table.setToolTipText("double click to edit the record");
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					String tableName = getTableNameDay();
					
					int i = table.getSelectedRow();

					try {
						Connection con =  Connect.getConnect();
						PreparedStatement st = con.prepareStatement("UPDATE "+tableName+" SET [Stationery]=?, [Books]=?, [Bags]=?, [Uniform]=?, [Garments]=?, [TotalSale]=?, [TotalExpenses]=? WHERE [Serial]="+(i)+"");

						st.setString(1, table.getValueAt(table.getSelectedRow(),0).toString());
						st.setString(2, table.getValueAt(table.getSelectedRow(),1).toString());
						st.setString(3, table.getValueAt(table.getSelectedRow(),2).toString());
						st.setString(4, table.getValueAt(table.getSelectedRow(),3).toString());
						st.setString(5, table.getValueAt(table.getSelectedRow(),4).toString());
						st.setString(6, String.valueOf(Integer.valueOf(table.getValueAt(table.getSelectedRow(),0).toString())+Integer.valueOf(table.getValueAt(table.getSelectedRow(),1).toString())+
								Integer.valueOf(table.getValueAt(table.getSelectedRow(),2).toString())+Integer.valueOf(table.getValueAt(table.getSelectedRow(),3).toString())+
								Integer.valueOf(table.getValueAt(table.getSelectedRow(),4).toString())));
						st.setString(7, table.getValueAt(table.getSelectedRow(),6).toString());

						st.executeUpdate();	
						
						
						table.setValueAt(String.valueOf(Integer.valueOf(table.getValueAt(table.getSelectedRow(),0).toString())+Integer.valueOf(table.getValueAt(table.getSelectedRow(),1).toString())+
								Integer.valueOf(table.getValueAt(table.getSelectedRow(),2).toString())+Integer.valueOf(table.getValueAt(table.getSelectedRow(),3).toString())+
								Integer.valueOf(table.getValueAt(table.getSelectedRow(),4).toString())),table.getSelectedRow(),5);
						setTotal(table, totalStationery, totalBook, totalBag, totalUniform, totalGarments	, totalTotal, totalExpenses, totalExpenses_1);
						UpdateTable();
						setTotal(table, totalStationery, totalBook, totalBag, totalUniform, totalGarments	, totalTotal, totalExpenses, totalExpenses_1);

						}
						catch(Exception e1) {
							JOptionPane.showMessageDialog(null, e);
						}
				}
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Stationery", "Books", "Bags", "Uniform", "Gift Items", "Total Sale"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, String.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, true, true, true, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(5).setResizable(false);
//		table.getColumnModel().getColumn(6).setResizable(false);
		table.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		table.setAutoscrolls(true);
		
		UIManager.put("TableHeader.font", new Font("Century Gothic", Font.BOLD, 16));
		
		scrollPane.setViewportView(table);
		table.setRowHeight(23);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
//		table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
		scrollPane.setViewportView(table);

		
		newStationery = new promptTextField(34,"");
		newStationery.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					setTableNew();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				setShortTotalValue();
			}
		});
		newStationery.setHorizontalAlignment(SwingConstants.CENTER);
		newStationery.setBounds(new Rectangle(5, 0, 0, 0));
		newStationery.setAlignmentX(5.0f);
		newStationery.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		newStationery.setBounds(97, 591, 100, 34);
		contentPane.add(newStationery);
		newStationery.setColumns(10);
		
		newBook = new promptTextField(34,"");
		newBook.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					setTableNew();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				setShortTotalValue();
			}
		});
		newBook.setHorizontalAlignment(SwingConstants.CENTER);
		newBook.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		newBook.setColumns(10);
		newBook.setBounds(288, 591, 100, 34);
		contentPane.add(newBook);
		
		newBag =new promptTextField(34,"");
		newBag.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					setTableNew();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				setShortTotalValue();
			}
		});
		newBag.setHorizontalAlignment(SwingConstants.CENTER);
		newBag.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		newBag.setColumns(10);
		newBag.setBounds(479, 591, 100, 34);
		contentPane.add(newBag);
		
		newUniform = new promptTextField(34,"");
		newUniform.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					setTableNew();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				setShortTotalValue();
			}
		});
		newUniform.setHorizontalAlignment(SwingConstants.CENTER);
		newUniform.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		newUniform.setColumns(10);
		newUniform.setBounds(670, 591, 100, 34);
		contentPane.add(newUniform);
		
		newGarments = new promptTextField(34,"");
		newGarments.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					setTableNew();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				setShortTotalValue();
			}
		});
		newGarments.setHorizontalAlignment(SwingConstants.CENTER);
		newGarments.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		newGarments.setColumns(10);
		newGarments.setBounds(860, 591, 100, 34);
		contentPane.add(newGarments);
		
		JPanel panel_1_4_6_1_1_1_5 = new JPanel();
		panel_1_4_6_1_1_1_5.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5.setLayout(null);
		panel_1_4_6_1_1_1_5.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5.setBounds(201, 476, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5);
		
		totalBook = new JLabel("0");
		totalBook.setHorizontalTextPosition(SwingConstants.CENTER);
		totalBook.setHorizontalAlignment(SwingConstants.CENTER);
		totalBook.setForeground(Color.WHITE);
		totalBook.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalBook.setAlignmentX(0.5f);
		totalBook.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5.add(totalBook);

		
		JPanel panel_1_4_6_1_1_1_5_1 = new JPanel();
		panel_1_4_6_1_1_1_5_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_1.setLayout(null);
		panel_1_4_6_1_1_1_5_1.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_1.setBounds(10, 476, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_1);
		
		totalStationery = new JLabel("0");
		totalStationery.setHorizontalTextPosition(SwingConstants.CENTER);
		totalStationery.setHorizontalAlignment(SwingConstants.CENTER);
		totalStationery.setForeground(Color.WHITE);
		totalStationery.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalStationery.setAlignmentX(0.5f);
		totalStationery.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_1.add(totalStationery);
		
		JPanel panel_1_4_6_1_1_1_5_2 = new JPanel();
		panel_1_4_6_1_1_1_5_2.setLayout(null);
		panel_1_4_6_1_1_1_5_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_2.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_2.setBounds(392, 476, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_2);
		
		totalBag = new JLabel("0");
		totalBag.setHorizontalTextPosition(SwingConstants.CENTER);
		totalBag.setHorizontalAlignment(SwingConstants.CENTER);
		totalBag.setForeground(Color.WHITE);
		totalBag.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalBag.setAlignmentX(0.5f);
		totalBag.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_2.add(totalBag);
		
		JPanel panel_1_4_6_1_1_1_5_3 = new JPanel();
		panel_1_4_6_1_1_1_5_3.setLayout(null);
		panel_1_4_6_1_1_1_5_3.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_3.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_3.setBounds(583, 476, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_3);
		
		totalUniform = new JLabel("0");
		totalUniform.setHorizontalTextPosition(SwingConstants.CENTER);
		totalUniform.setHorizontalAlignment(SwingConstants.CENTER);
		totalUniform.setForeground(Color.WHITE);
		totalUniform.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalUniform.setAlignmentX(0.5f);
		totalUniform.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_3.add(totalUniform);
		
		JPanel panel_1_4_6_1_1_1_5_4 = new JPanel();
		panel_1_4_6_1_1_1_5_4.setLayout(null);
		panel_1_4_6_1_1_1_5_4.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_4.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_4.setBounds(773, 476, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_4);
		
		totalGarments = new JLabel("0");
		totalGarments.setHorizontalTextPosition(SwingConstants.CENTER);
		totalGarments.setHorizontalAlignment(SwingConstants.CENTER);
		totalGarments.setForeground(Color.WHITE);
		totalGarments.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalGarments.setAlignmentX(0.5f);
		totalGarments.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_4.add(totalGarments);
		
		JPanel panel_1_4_6_1_1_1_5_5 = new JPanel();
		panel_1_4_6_1_1_1_5_5.setLayout(null);
		panel_1_4_6_1_1_1_5_5.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_5.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_5.setBounds(963, 476, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_5);
		
		totalTotal = new JLabel("0");
		totalTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		totalTotal.setHorizontalAlignment(SwingConstants.CENTER);
		totalTotal.setForeground(Color.WHITE);
		totalTotal.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalTotal.setAlignmentX(0.5f);
		totalTotal.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_5.add(totalTotal);

		
		//extracted from CommonMethods
		CommonMethods.timeFromCommon(time);
		
		JLabel date_1 = new JLabel();
		date_1.setHorizontalAlignment(SwingConstants.LEFT);
		date_1.setForeground(Color.BLACK);
		date_1.setFont(new Font("Century Gothic", Font.ITALIC, 16));
		date_1.setBounds(0, 0, 319, 24);
		panel.add(date_1);
		
		
		
		
		JPanel panel_1_4_6_1_1_1_5_7 = new JPanel();
		panel_1_4_6_1_1_1_5_7.setLayout(null);
		panel_1_4_6_1_1_1_5_7.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_7.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_7.setBounds(201, 511, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_7);
		
		booksPayment = new JLabel("0");
		booksPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		booksPayment.setHorizontalAlignment(SwingConstants.CENTER);
		booksPayment.setForeground(Color.WHITE);
		booksPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		booksPayment.setAlignmentX(0.5f);
		booksPayment.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_7.add(booksPayment);
		
		JPanel panel_1_4_6_1_1_1_5_1_1 = new JPanel();
		panel_1_4_6_1_1_1_5_1_1.setLayout(null);
		panel_1_4_6_1_1_1_5_1_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_1_1.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_1_1.setBounds(10, 511, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_1_1);
		
		stationeryPayment = new JLabel("0");
		stationeryPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		stationeryPayment.setHorizontalAlignment(SwingConstants.CENTER);
		stationeryPayment.setForeground(Color.WHITE);
		stationeryPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		stationeryPayment.setAlignmentX(0.5f);
		stationeryPayment.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_1_1.add(stationeryPayment);
		
		JPanel panel_1_4_6_1_1_1_5_2_1 = new JPanel();
		panel_1_4_6_1_1_1_5_2_1.setLayout(null);
		panel_1_4_6_1_1_1_5_2_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_2_1.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_2_1.setBounds(392, 511, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_2_1);
		
		bagsPayment = new JLabel("0");
		bagsPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		bagsPayment.setHorizontalAlignment(SwingConstants.CENTER);
		bagsPayment.setForeground(Color.WHITE);
		bagsPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		bagsPayment.setAlignmentX(0.5f);
		bagsPayment.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_2_1.add(bagsPayment);
		
		JPanel panel_1_4_6_1_1_1_5_3_1 = new JPanel();
		panel_1_4_6_1_1_1_5_3_1.setLayout(null);
		panel_1_4_6_1_1_1_5_3_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_3_1.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_3_1.setBounds(583, 511, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_3_1);
		
		uniformPayment = new JLabel("0");
		uniformPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		uniformPayment.setHorizontalAlignment(SwingConstants.CENTER);
		uniformPayment.setForeground(Color.WHITE);
		uniformPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		uniformPayment.setAlignmentX(0.5f);
		uniformPayment.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_3_1.add(uniformPayment);
		
		JPanel panel_1_4_6_1_1_1_5_4_1 = new JPanel();
		panel_1_4_6_1_1_1_5_4_1.setLayout(null);
		panel_1_4_6_1_1_1_5_4_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_4_1.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_4_1.setBounds(773, 511, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_4_1);
		
		garmentsPayment = new JLabel("0");
		garmentsPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		garmentsPayment.setHorizontalAlignment(SwingConstants.CENTER);
		garmentsPayment.setForeground(Color.WHITE);
		garmentsPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		garmentsPayment.setAlignmentX(0.5f);
		garmentsPayment.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_4_1.add(garmentsPayment);
		
		JPanel panel_1_4_6_1_1_1_5_5_1 = new JPanel();
		panel_1_4_6_1_1_1_5_5_1.setLayout(null);
		panel_1_4_6_1_1_1_5_5_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_5_1.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_5_1.setBounds(963, 511, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_5_1);
		
		totalPayment = new JLabel("0");
		totalPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		totalPayment.setHorizontalAlignment(SwingConstants.CENTER);
		totalPayment.setForeground(Color.WHITE);
		totalPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalPayment.setAlignmentX(0.5f);
		totalPayment.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_5_1.add(totalPayment);
		
		JPanel panel_1_4_6_1_1_1_5_8 = new JPanel();
		panel_1_4_6_1_1_1_5_8.setLayout(null);
		panel_1_4_6_1_1_1_5_8.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_8.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_8.setBounds(201, 546, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_8);
		
		changeBook = new JLabel("0");
		changeBook.setHorizontalTextPosition(SwingConstants.CENTER);
		changeBook.setHorizontalAlignment(SwingConstants.CENTER);
		changeBook.setForeground(Color.WHITE);
		changeBook.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		changeBook.setAlignmentX(0.5f);
		changeBook.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_8.add(changeBook);
		
		JPanel panel_1_4_6_1_1_1_5_1_2 = new JPanel();
		panel_1_4_6_1_1_1_5_1_2.setLayout(null);
		panel_1_4_6_1_1_1_5_1_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_1_2.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_1_2.setBounds(10, 546, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_1_2);
		
		changeStationery = new JLabel("0");
		changeStationery.setHorizontalTextPosition(SwingConstants.CENTER);
		changeStationery.setHorizontalAlignment(SwingConstants.CENTER);
		changeStationery.setForeground(Color.WHITE);
		changeStationery.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		changeStationery.setAlignmentX(0.5f);
		changeStationery.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_1_2.add(changeStationery);
		
		JPanel panel_1_4_6_1_1_1_5_2_2 = new JPanel();
		panel_1_4_6_1_1_1_5_2_2.setLayout(null);
		panel_1_4_6_1_1_1_5_2_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_2_2.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_2_2.setBounds(392, 546, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_2_2);
		
		changeBag = new JLabel("0");
		changeBag.setHorizontalTextPosition(SwingConstants.CENTER);
		changeBag.setHorizontalAlignment(SwingConstants.CENTER);
		changeBag.setForeground(Color.WHITE);
		changeBag.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		changeBag.setAlignmentX(0.5f);
		changeBag.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_2_2.add(changeBag);
		
		JPanel panel_1_4_6_1_1_1_5_3_2 = new JPanel();
		panel_1_4_6_1_1_1_5_3_2.setLayout(null);
		panel_1_4_6_1_1_1_5_3_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_3_2.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_3_2.setBounds(583, 546, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_3_2);
		
		changeUniform = new JLabel("0");
		changeUniform.setHorizontalTextPosition(SwingConstants.CENTER);
		changeUniform.setHorizontalAlignment(SwingConstants.CENTER);
		changeUniform.setForeground(Color.WHITE);
		changeUniform.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		changeUniform.setAlignmentX(0.5f);
		changeUniform.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_3_2.add(changeUniform);
		
		JPanel panel_1_4_6_1_1_1_5_4_2 = new JPanel();
		panel_1_4_6_1_1_1_5_4_2.setLayout(null);
		panel_1_4_6_1_1_1_5_4_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_4_2.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_4_2.setBounds(773, 546, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_4_2);
		
		changeGarments = new JLabel("0");
		changeGarments.setHorizontalTextPosition(SwingConstants.CENTER);
		changeGarments.setHorizontalAlignment(SwingConstants.CENTER);
		changeGarments.setForeground(Color.WHITE);
		changeGarments.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		changeGarments.setAlignmentX(0.5f);
		changeGarments.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_4_2.add(changeGarments);
		
		JPanel panel_1_4_6_1_1_1_5_5_2 = new JPanel();
		panel_1_4_6_1_1_1_5_5_2.setLayout(null);
		panel_1_4_6_1_1_1_5_5_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_5_2.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_5_2.setBounds(963, 546, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_5_2);
		
		changeTotal = new JLabel("0");
		changeTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		changeTotal.setHorizontalAlignment(SwingConstants.CENTER);
		changeTotal.setForeground(Color.WHITE);
		changeTotal.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		changeTotal.setAlignmentX(0.5f);
		changeTotal.setBounds(0, 0, 187, 34);
		panel_1_4_6_1_1_1_5_5_2.add(changeTotal);
		putTable(table, getTableNameDay());
		
		JButton btnNewButton = new JButton("Monthly Record (F2)");
		btnNewButton.setToolTipText("open monthly sale record");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MonthlySaleRecord().setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton.setBounds(233, 636, 213, 34);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Shop Expenses(F5)");
		btnNewButton_1.setToolTipText("shop expenses based on the months");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ShopExpenses().setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setBounds(904, 636, 213, 34);
		contentPane.add(btnNewButton_1);
		
		JButton btnAnnualSaleRecord = new JButton("Annual Record (F3)");
		btnAnnualSaleRecord.setToolTipText("opens annual sale record");
		btnAnnualSaleRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new YearlyRecord().setVisible(true);
				dispose();
			}
		});
		btnAnnualSaleRecord.setForeground(Color.BLACK);
		btnAnnualSaleRecord.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnAnnualSaleRecord.setBackground(Color.WHITE);
		btnAnnualSaleRecord.setBounds(456, 636, 214, 34);
		contentPane.add(btnAnnualSaleRecord);
		
		JButton btnNewButton_2_1 = new JButton("Party Trial Balance (F4)");
		btnNewButton_2_1.setToolTipText("opens party trial balances and their party ledgers");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PartyTrialBalance().setVisible(true);
				dispose();
			}
		});
		btnNewButton_2_1.setForeground(Color.BLACK);
		btnNewButton_2_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_2_1.setBackground(Color.WHITE);
		btnNewButton_2_1.setBounds(680, 636, 214, 34);
		contentPane.add(btnNewButton_2_1);
		
		JButton btnDailyRecordf = new JButton("Connect to POS");
		btnDailyRecordf.setToolTipText("refresh the window");
		btnDailyRecordf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new MainSaleWindow().setVisible(true);
				dispose();
				
				
//				new DailySaleReport().setVisible(true);
//				dispose();
				
			}
		});
		btnDailyRecordf.setForeground(Color.BLACK);
		btnDailyRecordf.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnDailyRecordf.setBackground(Color.WHITE);
		btnDailyRecordf.setBounds(10, 636, 213, 34);
		contentPane.add(btnDailyRecordf);
		
		setPaymentData();
		setPayment();
		
		JLabel lblDate_1 = new JLabel("Stationery");
		lblDate_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1.setForeground(Color.BLACK);
		lblDate_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1.setAlignmentX(0.5f);
		lblDate_1.setBounds(10, 591, 87, 34);
		contentPane.add(lblDate_1);
		
		JLabel lblDate_1_1 = new JLabel("Books");
		lblDate_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_1.setForeground(Color.BLACK);
		lblDate_1_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_1.setAlignmentX(0.5f);
		lblDate_1_1.setBounds(201, 591, 87, 34);
		contentPane.add(lblDate_1_1);
		
		JLabel lblDate_1_2 = new JLabel("Bags");
		lblDate_1_2.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_2.setForeground(Color.BLACK);
		lblDate_1_2.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_2.setAlignmentX(0.5f);
		lblDate_1_2.setBounds(392, 591, 87, 34);
		contentPane.add(lblDate_1_2);
		
		JLabel lblDate_1_3 = new JLabel("Uniform");
		lblDate_1_3.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_3.setForeground(Color.BLACK);
		lblDate_1_3.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_3.setAlignmentX(0.5f);
		lblDate_1_3.setBounds(583, 591, 87, 34);
		contentPane.add(lblDate_1_3);
		
		JLabel lblDate_1_1_1 = new JLabel("Garments");
		lblDate_1_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_1_1.setForeground(Color.BLACK);
		lblDate_1_1_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_1_1.setAlignmentX(0.5f);
		lblDate_1_1_1.setBounds(773, 590, 87, 34);
		contentPane.add(lblDate_1_1_1);
		
		JLabel lblDate_1_1_1_1 = new JLabel("Cash");
		lblDate_1_1_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new CashDeatils().setVisible(true);
				dispose();
			}
		});
		lblDate_1_1_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_1_1_1.setForeground(Color.BLACK);
		lblDate_1_1_1_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_1_1_1.setAlignmentX(0.5f);
		lblDate_1_1_1_1.setBounds(1153, 591, 68, 34);
		contentPane.add(lblDate_1_1_1_1);
		
		JPanel panel_1_4_6_1_1_1_5_1_2_1 = new RoundedPanel(34);
		//panel_1_4_6_1_1_1_5_1_2_1.setLayout(null);
		panel_1_4_6_1_1_1_5_1_2_1.setBackground(null);
		panel_1_4_6_1_1_1_5_1_2_1.setBounds(10, 591, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_1_2_1);
		
		RoundedPanel panel_1_4_6_1_1_1_5_1_2_1_1 = new RoundedPanel(34);
		panel_1_4_6_1_1_1_5_1_2_1_1.setBackground(null);
		panel_1_4_6_1_1_1_5_1_2_1_1.setBounds(201, 591, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_1_2_1_1);
		
		RoundedPanel panel_1_4_6_1_1_1_5_1_2_1_2 = new RoundedPanel(34);
		panel_1_4_6_1_1_1_5_1_2_1_2.setBackground(null);
		panel_1_4_6_1_1_1_5_1_2_1_2.setBounds(392, 591, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_1_2_1_2);
		
		RoundedPanel panel_1_4_6_1_1_1_5_1_2_1_3 = new RoundedPanel(34);
		panel_1_4_6_1_1_1_5_1_2_1_3.setBackground(null);
		panel_1_4_6_1_1_1_5_1_2_1_3.setBounds(583, 591, 186, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_1_2_1_3);
		
		RoundedPanel panel_1_4_6_1_1_1_5_1_2_1_4 = new RoundedPanel(34);
		panel_1_4_6_1_1_1_5_1_2_1_4.setBackground(null);
		panel_1_4_6_1_1_1_5_1_2_1_4.setBounds(773, 591, 186, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_1_2_1_4);
		
		RoundedPanel panel_1_4_6_1_1_1_5_1_2_1_5 = new RoundedPanel(34);
		panel_1_4_6_1_1_1_5_1_2_1_5.setBackground(null);
		panel_1_4_6_1_1_1_5_1_2_1_5.setBounds(1153, 591, 187, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_1_2_1_5);
		panel_1_4_6_1_1_1_5_1_2_1_5.setLayout(null);
		
		JLabel availableCash = new JLabel("0");
		availableCash.setBounds(55, 5, 122, 26);
		availableCash.setHorizontalTextPosition(SwingConstants.CENTER);
		availableCash.setHorizontalAlignment(SwingConstants.CENTER);
		availableCash.setForeground(Color.BLACK);
		availableCash.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		availableCash.setBackground((Color) null);
		availableCash.setAlignmentX(0.5f);
		panel_1_4_6_1_1_1_5_1_2_1_5.add(availableCash);
		
		RoundedPanel panel_1_4_6_1_1_1_5_1_2_1_6 = new RoundedPanel(34);
		panel_1_4_6_1_1_1_5_1_2_1_6.setBounds(963, 591, 187, 34);
		panel_1_4_6_1_1_1_5_1_2_1_6.setBackground(null);
		contentPane.add(panel_1_4_6_1_1_1_5_1_2_1_6);
		panel_1_4_6_1_1_1_5_1_2_1_6.setLayout(null);
		
		
		totalShort = new JLabel("0");
		totalShort.setBounds(0, 0, 187, 34);
		totalShort.setHorizontalTextPosition(SwingConstants.CENTER);
		totalShort.setHorizontalAlignment(SwingConstants.CENTER);
		totalShort.setForeground(Color.BLACK);
		totalShort.setBackground(null);
		totalShort.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalShort.setAlignmentX(0.5f);
		panel_1_4_6_1_1_1_5_1_2_1_6.add(totalShort);
		
		
		table.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent e) {
		    	table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
		    }
		});
		
		table.getModel().addTableModelListener(
				new TableModelListener() 
				{

					@Override
					public void tableChanged(TableModelEvent e) {
						date_1.setText("Total Customers: "+String.valueOf(table.getRowCount()));
						
					}
				});
		date_1.setText("Total Customers: "+String.valueOf(table.getRowCount()));
		
		JButton btnNewButton_1_1 = new JButton("Expenses (F6)");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Expenses().setVisible(true);
				dispose();
			}
		});
		btnNewButton_1_1.setForeground(Color.BLACK);
		btnNewButton_1_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_1_1.setBackground(Color.WHITE);
		btnNewButton_1_1.setBounds(1127, 636, 213, 34);
		contentPane.add(btnNewButton_1_1);
		
		
		availableCash.setText(CashDeatils.getTotal());
		
		expensePanel2 = new JPanel();
		expensePanel2.setLayout(null);
		expensePanel2.setBorder(new LineBorder(Color.WHITE));
		expensePanel2.setBackground(new Color(169, 169, 169));
		expensePanel2.setBounds(1153, 511, 187, 34);
		contentPane.add(expensePanel2);
		
		totalExpenses_1 = new JLabel("0");
		totalExpenses_1.setHorizontalTextPosition(SwingConstants.CENTER);
		totalExpenses_1.setHorizontalAlignment(SwingConstants.CENTER);
		totalExpenses_1.setForeground(Color.WHITE);
		totalExpenses_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalExpenses_1.setAlignmentX(0.5f);
		totalExpenses_1.setBounds(0, 0, 187, 34);
		expensePanel2.add(totalExpenses_1);
		
		expensePanel3 = new JPanel();
		expensePanel3.setLayout(null);
		expensePanel3.setBorder(new LineBorder(Color.WHITE));
		expensePanel3.setBackground(new Color(112, 128, 144));
		expensePanel3.setBounds(1153, 546, 187, 34);
		contentPane.add(expensePanel3);
		
		totalExpenses_2 = new JLabel("0");
		totalExpenses_2.setHorizontalTextPosition(SwingConstants.CENTER);
		totalExpenses_2.setHorizontalAlignment(SwingConstants.CENTER);
		totalExpenses_2.setForeground(Color.WHITE);
		totalExpenses_2.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalExpenses_2.setAlignmentX(0.5f);
		totalExpenses_2.setBounds(0, 0, 187, 34);
		expensePanel3.add(totalExpenses_2);
		
		expensePanel1 = new JPanel();
		expensePanel1.setLayout(null);
		expensePanel1.setBorder(new LineBorder(Color.WHITE));
		expensePanel1.setBackground(Color.DARK_GRAY);
		expensePanel1.setBounds(1153, 476, 187, 34);
		contentPane.add(expensePanel1);
		
		totalExpenses = new JLabel("0");
		totalExpenses.setHorizontalTextPosition(SwingConstants.CENTER);
		totalExpenses.setHorizontalAlignment(SwingConstants.CENTER);
		totalExpenses.setForeground(Color.WHITE);
		totalExpenses.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalExpenses.setAlignmentX(0.5f);
		totalExpenses.setBounds(0, 0, 187, 34);
		expensePanel1.add(totalExpenses);
		setTotal(table, totalStationery, totalBook, totalBag, totalUniform, totalGarments	, totalTotal, totalExpenses, totalExpenses_1);
		
		
	}
}


class partyNamePayments {
	String name,category;

	public partyNamePayments(String name, String category) {
		super();
		this.name = name;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}


