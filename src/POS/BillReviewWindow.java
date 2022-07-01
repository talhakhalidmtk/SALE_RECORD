package POS;

import java.awt.Color;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import BillDetails.BillDetails;
import Customer.CustomerPortfolio;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import Extras.*;
import SourceCode.CommonMethods;
import POS.Connect;
//import Schools.MainWindow;
import Schools.MainWindow;
import SourceCode.DailySaleReport;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import start.Splash;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.PrinterName;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import java.awt.Toolkit;
import java.awt.Window.Type;
import javax.swing.JToolBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.awt.Button;
import java.awt.Label;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.JTextArea;

//import Schools.MainWindow;


@SuppressWarnings("serial")
public class BillReviewWindow extends JFrame {

	static Connection con;
	private JPanel contentPane;
	static JLabel totalStationery;
	static JLabel date  ;
	static JLabel totalBook;
	static JLabel totalBag;
	static JLabel totalUniform;
	static JLabel totalGarments;
	static JLabel totalTotal;
	static JLabel totalExpenses, totalExpenses_2,totalExpenses_1;
	static JTextField customerID;
	private static JTextField customerName;
	private static JTable mainTable;
	private static promptTextField dis1;
	private static promptTextField dis2;
	private static promptTextField perUnit;
	private JTable customerTable;
	private JScrollPane customerList;
	private static promptTextField code;
	private static promptTextField searchBar;
	
	public String categoryType = null;
	private JTable productTable;
	private JScrollPane productList;
	
	static ArrayList <Product> products = new ArrayList <Product>();
	public static promptTextField quantity;
	private JLabel total;
	static promptTextField bill;
	private JLabel finalTotal;
	private JLabel grandTotal;
	private JLabel totalQuantity;
	private static promptTextField dis;
	private RoundedPanel panel_1_3_1;
	private JLabel status;
	private promptTextField adjustment;
	private JLabel lblNewLabel_1_1,lblNewLabel_1;
	private JPanel newProductPanel;
	private JScrollPane mainScrollPane;
	private promptTextField productCode;
	private promptTextField salePrice;
	private JComboBox<String> category;
	private JButton btnEnter_1;
	final JTableHeader head;
	private RoundedPanel panel_1_3_1_1;
	private JLabel change;
	private promptTextField totalReceived;
	private JButton mailButton;
	private JButton btnLabourSheet;
	private promptTextField salesPerson;
	private static JToggleButton member;
	private static JTextArea comments;
	private JButton btnEnter_2;
	private static promptTextField billType;
	private JTable billTypeTable;
	private JScrollPane billList;
	
	public String billTypeDumy;
	


	public void updateCustomer() {
		String dat = date.getText().substring(date.getText().indexOf(",")+1);
		if(billTypeDumy.equalsIgnoreCase("Credit Sales") && billType.getText().equalsIgnoreCase("Cash Sales") 
		&& (! customerName.getText().equals("Counter Sale"))) {
			
			getValuesForDailySaleReport();
			try {
				String url = "SELECT MAX(Serial) FROM CustomerLedger";
				PreparedStatement pst = con.prepareStatement(url);
				ResultSet rs= pst.executeQuery();
				while(rs.next()) {
					PreparedStatement create2 = null;
					create2 = con.prepareStatement("INSERT INTO CustomerLedger (Serial, CustomerID, CustomerName, Date ,Description, Amount, Type) "
							+ "VALUES ('"+(rs.getInt(1)+2)+"','"+customerID.getText()+"',"
							+ "'"+customerName.getText()+"','"+dat+"','"+"Bill # "+bill.getText()+"',"
							+ "'"+grandTotal.getText()+"','2') \r\n");	
					create2.executeUpdate();
					
					PreparedStatement create = null;
					create = con.prepareStatement("UPDATE CustomerRecord SET AccountBalance=\r\n"
							+ "(SELECT SUM(CustomerLedger.Amount) FROM CustomerLedger \r\n"
							+ "WHERE ID='"+customerID.getText()+"' AND Name='"+customerName.getText()+"' AND Type=1) -\r\n"
							+ "(SELECT SUM(CustomerLedger.Amount) FROM CustomerLedger \r\n"
							+ "WHERE ID='"+customerID.getText()+"' AND Name='"+customerName.getText()+"' AND Type=2) \r\n"
							+ "WHERE ID='"+customerID.getText()+"' and Name='"+customerName.getText()+"';");	
					create.executeUpdate();
				}
				}
				catch(Exception e) {
					System.err.print(e);
				}
			
			billTypeDumy = "Cash Sales";
		}
		
		if(billTypeDumy.equalsIgnoreCase("Information Bill") && billType.getText().equalsIgnoreCase("Cash Sales")) {
			getValuesForDailySaleReport();
			try {
				String url = "SELECT MAX(Serial) FROM CustomerLedger";
				PreparedStatement pst = con.prepareStatement(url);
				ResultSet rs= pst.executeQuery();
				while(rs.next()) {
					
					PreparedStatement create1 = null;
					create1 = con.prepareStatement("INSERT INTO CustomerLedger (Serial, CustomerID, CustomerName, Date ,Description, Amount, Type) "
							+ "VALUES ('"+(rs.getInt(1)+1)+"','"+customerID.getText()+"',"
							+ "'"+customerName.getText()+"','"+dat+"','"+"Bill # "+bill.getText()+"',"
							+ "'"+grandTotal.getText()+"','1') \r\n");	
					create1.executeUpdate();
					
					PreparedStatement create2 = null;
					create2 = con.prepareStatement("INSERT INTO CustomerLedger (Serial, CustomerID, CustomerName, Date ,Description, Amount, Type) "
							+ "VALUES ('"+(rs.getInt(1)+2)+"','"+customerID.getText()+"',"
							+ "'"+customerName.getText()+"','"+dat+"','"+"Bill # "+bill.getText()+"',"
							+ "'"+grandTotal.getText()+"','2') \r\n");	
					create2.executeUpdate();
					
					PreparedStatement create = null;
					create = con.prepareStatement("UPDATE CustomerRecord SET AccountBalance=\r\n"
							+ "(SELECT SUM(CustomerLedger.Amount) FROM CustomerLedger \r\n"
							+ "WHERE ID='"+customerID.getText()+"' AND Name='"+customerName.getText()+"' AND Type=1) -\r\n"
							+ "(SELECT SUM(CustomerLedger.Amount) FROM CustomerLedger \r\n"
							+ "WHERE ID='"+customerID.getText()+"' AND Name='"+customerName.getText()+"' AND Type=2) \r\n"
							+ "WHERE ID='"+customerID.getText()+"' and Name='"+customerName.getText()+"';");	
					create.executeUpdate();
				}
				
				
				}
				catch(Exception e) {
					System.err.print(e);
				}
			
			billTypeDumy = "Cash Sales";
		}
		
		if(billTypeDumy.equalsIgnoreCase("Information Bill") && billType.getText().equalsIgnoreCase("Credit Sales")) {
			try {
				String url = "SELECT MAX(Serial) FROM CustomerLedger";
				PreparedStatement pst = con.prepareStatement(url);
				ResultSet rs= pst.executeQuery();
				while(rs.next()) {
					
					PreparedStatement create1 = null;
					create1 = con.prepareStatement("INSERT INTO CustomerLedger (Serial, CustomerID, CustomerName, Date ,Description, Amount, Type) "
							+ "VALUES ('"+(rs.getInt(1)+1)+"','"+customerID.getText()+"',"
							+ "'"+customerName.getText()+"','"+dat+"','"+"Bill # "+bill.getText()+"',"
							+ "'"+grandTotal.getText()+"','1') \r\n");	
					create1.executeUpdate();
					PreparedStatement create = null;
					create = con.prepareStatement("UPDATE CustomerRecord SET AccountBalance=\r\n"
							+ "(SELECT SUM(CustomerLedger.Amount) FROM CustomerLedger \r\n"
							+ "WHERE ID='"+customerID.getText()+"' AND Name='"+customerName.getText()+"' AND Type=1) -\r\n"
							+ "(SELECT SUM(CustomerLedger.Amount) FROM CustomerLedger \r\n"
							+ "WHERE ID='"+customerID.getText()+"' AND Name='"+customerName.getText()+"' AND Type=2) \r\n"
							+ "WHERE ID='"+customerID.getText()+"' and Name='"+customerName.getText()+"';");	
					create.executeUpdate();
				}
				
				
				}
				catch(Exception e) {
					System.err.print(e);
				}
			
			billTypeDumy = "Credit Sales";
		}
	}
	
	public void updateBill() {
		try {
			updateCustomer();
//			updateBillIntoSaleRecord();

			Connection con1 =  Connect.getConnect();
			PreparedStatement create5 = null;
			create5 = con1.prepareStatement("UPDATE BillListDetails SET [CustomerName]=?, [SalesPerson]=?, "
					+ "[TotalAmount]=?, [Discount]=?, [Adjustment]=?, [GrandTotal]=?, [Received]=?, [Change]=?, [Quantity]=?,[Comments]=?,"
					+ "[BillType]=?,[Member]=?"
					+ " WHERE [ID]="+(bill.getText())+"");
			create5.setString(1, customerName.getText());
			create5.setString(2, salesPerson.getText());
			create5.setString(3, finalTotal.getText());
			create5.setString(4, dis.getText());
			create5.setString(5, adjustment.getText());
			create5.setString(6, grandTotal.getText());
			create5.setString(7, totalReceived.getText());
			create5.setString(8, change.getText());
			create5.setString(9, totalQuantity.getText());
			create5.setString(10, comments.getText());
			create5.setString(11, billType.getText());
			if(member.isSelected()) {
				create5.setString(12, "Member");
			}else {
				create5.setString(12, "Not a Member");
			}
			create5.executeUpdate();
			con1.close();

			Connection con3  = BillConnect.getConnect();
			PreparedStatement create = con3.prepareStatement("DROP TABLE ["+bill.getText()+"]");
			create.executeUpdate();
			
			Connection con  = BillConnect.getConnect();
			PreparedStatement create1 = con.prepareStatement("CREATE TABLE ["+bill.getText().toString()+"] \r\n"
					+ "(Serial int , ProductCode VARCHAR(50), ProductTitle VARCHAR(50), ProductCategory VARCHAR(50) "
					+ ",Quantity VARCHAR(50) , PerUnit VARCHAR(50) , Total VARCHAR(50), Discount VARCHAR(100) ,NetTotal VARCHAR(100))\r\n"
					);
			create1.executeUpdate();
			
			for(int i =0;i<mainTable.getRowCount();i++) {
				PreparedStatement ps = con.prepareStatement("insert into ["+bill.getText().toString()+"] values(?,?,?,?,?,?,?,?,?)");
	            ps.setString(1, mainTable.getValueAt(i, 0).toString());
	            ps.setString(2, mainTable.getValueAt(i, 1).toString());
	            ps.setString(3, mainTable.getValueAt(i, 2).toString());
	            ps.setString(4, mainTable.getValueAt(i, 3).toString());
	            ps.setString(5, mainTable.getValueAt(i, 4).toString());
	            ps.setString(6, mainTable.getValueAt(i, 5).toString());
	            ps.setString(7, mainTable.getValueAt(i, 6).toString());
	            ps.setString(8, mainTable.getValueAt(i, 7).toString());
	            ps.setString(9, mainTable.getValueAt(i, 8).toString());
	            ps.executeUpdate();
			}
			
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public void setTable() {
		
		 DefaultTableModel dtm = (DefaultTableModel) mainTable.getModel();
			dtm.setRowCount(0);
		
		try{
			
			Connection con = BillConnect.getConnect();
		 	String details = "Select * from ["+BillDetailsLIST.getBillID()+"]";
	      PreparedStatement pst = (PreparedStatement) con.prepareStatement(details);
	        ResultSet rs= pst.executeQuery();	        
		while(rs.next()){	
			DefaultTableModel tab = (DefaultTableModel) mainTable.getModel();
			tab.addRow(new Object[] {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)});
			 }
		
		
		
		 con.close();			 
}
	 catch(Exception e){
		 System.out.println(e);
	
     }
		
		
	}
	public void saveOnly() {
		try {	
			 /* Output file location */
	        String outputFile = "D:\\POSTalhaKhalid\\" + "Bill#"+String.valueOf(Integer.valueOf(bill.getText()))+"-"+customerID.getText()+".pdf";
	        
	        
	        List<TableForReceipt> listItems = new ArrayList<TableForReceipt>();
	        TableForReceipt[] array = new TableForReceipt[mainTable.getRowCount()];
	        JTable tab = mainTable;
	        for(int i=0;i<array.length;i++) {
		        	array[i] = new TableForReceipt();

		        	array[i].setSerial(String.valueOf(Integer.valueOf((int) Math.floor(Double.valueOf(tab.getValueAt(i, 0).toString())))));
		        	array[i].setNetTotal(String.valueOf(Integer.valueOf((int) Math.floor(Double.valueOf(tab.getValueAt(i, 8).toString())))+"  "));
		        	array[i].setPerUnit(String.valueOf(Integer.valueOf((int) Math.floor(Double.valueOf(tab.getValueAt(i, 5).toString())))));
		        	array[i].setTitle(WordUtils.capitalizeFully(String.valueOf(((tab.getValueAt(i, 2).toString())))));
		        	array[i].setQuantity(String.valueOf(Integer.valueOf((int) Math.floor(Double.valueOf(tab.getValueAt(i, 4).toString())))));
		        	listItems.add(array[i]);
	        }
	        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);
	        
	        /* Map to hold Jasper report Parameters */
	        Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("bill#",String.valueOf(Integer.valueOf(bill.getText())-1));
	        //parameters.put("customer", customerId.getText()+"-"+NewSaleWindow.customerName());
	        parameters.put("table", itemsJRBean);
	        parameters.put("total", String.valueOf(Integer.valueOf((int) Math.floor(Double.valueOf(finalTotal.getText()))))+"  ");
	        
	        
	        parameters.put("grandTotal", String.valueOf(Integer.valueOf((int) Math.floor(Double.valueOf(grandTotal.getText()))))+"  ");
	        parameters.put("received", totalReceived.getText()+"  ");
	        parameters.put("change", change.getText()+"  ");
	    
	        parameters.put("totalQuantity", totalQuantity.getText());
	        parameters.put("customerName", customerName.getText());
	        
//	        BufferedImage img = new BufferedImage(MainSaleWindow.class.getResource("/Images/StudentMart.PNG"));
//	        ImageIcon imageIcon = (new ImageIcon(MainSaleWindow.class.getResource("/Images/StudentMart.png")));
//	        InputStream imgInputStream = 
//	        	    this.getClass().getResourceAsStream("/Images/StudentMart.png");
//	        parameters.put("logo",imgInputStream);
	        
	        double a =  Double.valueOf(dis.getText());
	        if(a==0||a==0.0) {	
	        }else {
	        	parameters.put("discount", dis.getText()+"  ");
	        }

	        
	        
	        //read jrxml file and creating jasperdesign object
//	        InputStream input = new FileInputStream(new File("src\\Extras\\Blank_A4.jrxml"));
	        InputStream input = new FileInputStream(new File("D:\\POSTalhaKhalid\\ReceiptSample\\Blank_A4.jrxml"));
	        
	        
	        
	        JasperDesign jasperDesign = JRXmlLoader.load(input);
	        /*compiling jrxml with help of JasperReport class*/
	        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        
	        /* Using compiled version(.jasper) of Jasper report to generate PDF */
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

	        /* outputStream to create PDF */
	        OutputStream outputStream = new FileOutputStream(new File(outputFile));
	        /* Write content to PDF file */
	        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	        //JasperViewer.viewReport(jasperPrint);
	       status.setText("File Generated: " + outputFile);
//	        JasperPrintManager.printReport(jasperPrint, true);
	      // PrintReportToPrinter(jasperPrint);
	      
	        
	        
	    } catch (JRException ex) {
	    	JOptionPane.showMessageDialog(null, ex);
	        ex.printStackTrace();
	    } catch (FileNotFoundException ex) {
	    	JOptionPane.showMessageDialog(null, ex);
	        ex.printStackTrace();
	    }
		
		}
	
	public void print() {
		try {	
			 /* Output file location */
	        String outputFile = "D:\\POSTalhaKhalid\\" + "Bill#"+String.valueOf(Integer.valueOf(bill.getText()))+"-"+customerID.getText()+".pdf";
	        
	        
	        List<TableForReceipt> listItems = new ArrayList<TableForReceipt>();
	        TableForReceipt[] array = new TableForReceipt[mainTable.getRowCount()];
	        JTable tab = mainTable;
	        for(int i=0;i<array.length;i++) {
		        	array[i] = new TableForReceipt();

		        	array[i].setSerial(String.valueOf(Integer.valueOf((int) Math.floor(Double.valueOf(tab.getValueAt(i, 0).toString())))));
		        	array[i].setNetTotal(String.valueOf(Integer.valueOf((int) Math.floor(Double.valueOf(tab.getValueAt(i, 8).toString())))+"  "));
		        	array[i].setPerUnit(String.valueOf(Integer.valueOf((int) Math.floor(Double.valueOf(tab.getValueAt(i, 5).toString())))));
		        	array[i].setTitle(WordUtils.capitalizeFully(String.valueOf(((tab.getValueAt(i, 2).toString())))));
		        	array[i].setQuantity(String.valueOf(Integer.valueOf((int) Math.floor(Double.valueOf(tab.getValueAt(i, 4).toString())))));
		        	listItems.add(array[i]);
	        }
	        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);
	        
	        /* Map to hold Jasper report Parameters */
	        Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("bill#",String.valueOf(Integer.valueOf(bill.getText())-1));
	        //parameters.put("customer", customerId.getText()+"-"+NewSaleWindow.customerName());
	        parameters.put("table", itemsJRBean);
	        parameters.put("total", String.valueOf(Integer.valueOf((int) Math.floor(Double.valueOf(finalTotal.getText()))))+"  ");
	        
	        
	        parameters.put("grandTotal", String.valueOf(Integer.valueOf((int) Math.floor(Double.valueOf(grandTotal.getText()))))+"  ");
	        parameters.put("received", totalReceived.getText()+"  ");
	        parameters.put("change", change.getText()+"  ");
	    
	        parameters.put("totalQuantity", totalQuantity.getText());
	        parameters.put("customerName", customerName.getText());
	        

	        
	        double a =  Double.valueOf(dis.getText());
	        if(a==0||a==0.0) {	
	        }else {
	        	parameters.put("discount", dis.getText()+"  ");
	        }

	        
	        
	        //read jrxml file and creating jasperdesign object
//	        InputStream input = new FileInputStream(new File("src\\Extras\\Blank_A4.jrxml"));
	        InputStream input = new FileInputStream(new File("D:\\POSTalhaKhalid\\ReceiptSample\\Blank_A4.jrxml"));
	        
	        JasperDesign jasperDesign = JRXmlLoader.load(input);
	        /*compiling jrxml with help of JasperReport class*/
	        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        
	        /* Using compiled version(.jasper) of Jasper report to generate PDF */
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

	        /* outputStream to create PDF */
	        OutputStream outputStream = new FileOutputStream(new File(outputFile));
	        /* Write content to PDF file */
	        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	        //JasperViewer.viewReport(jasperPrint);
	       status.setText("File Generated: " + outputFile);
//	        JasperPrintManager.printReport(jasperPrint, true);
	       PrintReportToPrinter(jasperPrint);

	    } catch (JRException ex) {
	    	JOptionPane.showMessageDialog(null, ex);
	        ex.printStackTrace();
	    } catch (FileNotFoundException ex) {
	    	JOptionPane.showMessageDialog(null, ex);
	        ex.printStackTrace();
	    }
		
		}
	
	
	
	
	

	void getValuesForDailySaleReport() {
		int stationery = 0,book = 0,bag = 0,uniform = 0,garments = 0;
		for(int i =0;i<mainTable.getRowCount();i++) {
			if(mainTable.getValueAt(i, 3).toString().equals("Books")) {
				book+= Double.valueOf(mainTable.getValueAt(i, 8).toString());
			}if(mainTable.getValueAt(i, 3).toString().equals("Bags")) {
				bag+= Double.valueOf(mainTable.getValueAt(i, 8).toString());
			}if(mainTable.getValueAt(i, 3).toString().equals("Stationery")) {
				stationery+= Double.valueOf(mainTable.getValueAt(i, 8).toString());
			}if(mainTable.getValueAt(i, 3).toString().equals("Uniform")) {
				uniform+= Double.valueOf(mainTable.getValueAt(i, 8).toString());
			}if(mainTable.getValueAt(i, 3).toString().equals("Garments")) {
				garments+= Double.valueOf(mainTable.getValueAt(i, 8).toString());
			}
		}
		
		DailySaleReport ds = new DailySaleReport();
		ds.setVisible(false);ds.dispose();
		
		
		DailySaleReport.setTableFromPOS(String.valueOf(stationery), String.valueOf(book), String.valueOf(bag)
				, String.valueOf(uniform), String.valueOf(garments));
	}
	
	void getItemIntoMainList(){
		
		products.clear();
		
		try{

			Connection con  = Connect.getConnect();
		 	String details = "Select ProductCode, ProductTitle,Category,SalePrice,Available from ProductList";
	      PreparedStatement pst = (PreparedStatement) con.prepareStatement(details);
	        ResultSet rs= pst.executeQuery();	
	        
		while(rs.next()){
			products.add(new Product(rs.getString(1),WordUtils.capitalizeFully(rs.getString(2)),WordUtils.capitalizeFully(rs.getString(3)),rs.getString(4),rs.getString(5)));
		 }
		 con.close();	
			
 }
	 catch(Exception e){
		 System.out.println(e);
	
       }
	}
	
	void setBillNumber(){
		try{

			Connection con  = Connect.getConnect();
		 	String details = "Select MAX(ID) from BillRecord";
		 	PreparedStatement pst = (PreparedStatement) con.prepareStatement(details);
	        ResultSet rs= pst.executeQuery();	
	        while(rs.next()){
	        	Connection con1 = Connect.getConnect();
	        	PreparedStatement ps = con1.prepareStatement("BEGIN IF NOT EXISTS (SELECT * FROM BillList WHERE ID = '"+rs.getString(1)+"')\r\n"
						+ "BEGIN\r\n"
						+ "   INSERT INTO BillList (ID) VALUES ('"+rs.getString(1)+"') \r\n"
						+ "END END");
	           
	              ps.executeUpdate();
			 }
			
		 	String details1 = "Select MAX(ID) from BillList";
	      PreparedStatement pst1 = (PreparedStatement) con.prepareStatement(details1);
	        ResultSet rs1= pst1.executeQuery();	
	        
		while(rs1.next()){
			bill.setText(String.valueOf(rs1.getInt(1)+1));
		 }
		 con.close();	
			
 }
	 catch(Exception e){
		 System.out.println(e);
	
       }
	}
	
	
	
	public void getItemFromCode() {
		
		boolean val = false;
		
		Product pro = null;
		
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getCode().equals(code.getText())) {
				val = true;
				pro = products.get(i);
			}
		}
	
		if(val) {
			
			boolean sec = false;
			
			for(int i = 0; i<mainTable.getRowCount();i++) {
				if(mainTable.getValueAt(i, 2).toString().equals(pro.getTitle())&& mainTable.getValueAt(i, 5).toString().equals(pro.getSalePrice())&& mainTable.getValueAt(i, 7).toString().equals("0")) {
					mainTable.setValueAt((Integer.valueOf(mainTable.getValueAt(i, 4).toString())+1), i, 4);
					String tot = String.valueOf(Integer.valueOf(mainTable.getValueAt(i, 4).toString())*Double.valueOf(mainTable.getValueAt(i, 5).toString()));
					mainTable.setValueAt(tot, i, 6);
					mainTable.setValueAt(tot, i, 8);
					code.requestFocus();
					code.selectAll();
					
					sec = true;
					
					break;
					

				}
			}
			
			if(!sec) {
				DefaultTableModel tab = (DefaultTableModel) mainTable.getModel();
				tab.addRow(new Object[] {String.valueOf(mainTable.getRowCount()+1),pro.getCode(),
						pro.getTitle(),pro.getCategory(),'1',pro.getSalePrice()
						,pro.getSalePrice(),
						'0',pro.getSalePrice()});
				code.requestFocus();
				code.selectAll();
			}

		}else {
			
			searchBar.requestFocus();
			searchBar.setText("Product Name");
			searchBar.selectAll();
			
			productTable.setVisible(false);
			productTable.disable();
			productList.setVisible(false);
			productList.disable();
			
			newProductPanel.setVisible(true);
			newProductPanel.enable();
			
			
		}
		
		}
		
	
	
	public void getItemFromTitle() {
		
		for (int i = 0; i < products.size(); i++) {
			String a = productTable.getValueAt(productTable.getSelectedRow(), 0).toString();
			if (products.get(i).getTitle().equals(a)) {
				code.setText(products.get(i).getCode());
				searchBar.setText(products.get(i).getTitle());
				perUnit.setText(products.get(i).getSalePrice());
				quantity.requestFocus();
			}
		}
	
			 
			 productTable.setVisible(false);
			productTable.disable();
			productList.setVisible(false);
			productList.disable();
				
	 }

	
	public void setProductTable() {
		
		DefaultTableModel tab = (DefaultTableModel) productTable.getModel();
		tab.setRowCount(0);
		
		for (int i = 0; i < products.size(); i++) {
			tab.addRow(new Object[] {products.get(i).getCode(),products.get(i).getTitle()});

		}
	}

	
	public void setCustomerList() {
		try{
			 
			 DefaultTableModel dtm = (DefaultTableModel) customerTable.getModel();
				dtm.setRowCount(0);
				
				Connection con  = Connect.getConnect();
			 	String details = "Select ID, Name from CustomerRecord";
		      PreparedStatement pst = (PreparedStatement) con.prepareStatement(details);
		        ResultSet rs= pst.executeQuery();	        
			while(rs.next()){
				DefaultTableModel tab = (DefaultTableModel) customerTable.getModel();
				 tab.addRow(new Object[] {rs.getString(1),WordUtils.capitalizeFully(rs.getString(2))});
			 }
			 con.close();	
				
	 }
		 catch(Exception e){
			 System.out.println(e);
		
	       }
	}
	
	
	public void createBillTable() {
		con  = Connect.getConnect();
		PreparedStatement create4;
		try {
			create4 = con.prepareStatement("IF OBJECT_ID(N'dbo.BillList', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE BillList \r\n"
					+ "(ID int,CustomerName VARCHAR(50),SalesPerson VARCHAR(50), TotalAmount VARCHAR(50),"
					+ "Discount VARCHAR(50), Adjustment VARCHAR(50), GrandTotal VARCHAR(50), "
					+ "Received VARCHAR(50), Change VARCHAR(50), Quantity VARCHAR(50))\r\n"
					+ "END;");
			create4.executeUpdate();

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setBillDetails() {
		
		

		try {
			
			Connection con1 =  Connect.getConnect();
			PreparedStatement create5 = null;
			create5 = con1.prepareStatement("INSERT INTO BillList VALUES ('"+(Integer.valueOf(bill.getText()))+"','"+customerName.getText()+"','"+salesPerson.getText()+"','"+finalTotal.getText()+"','"+dis.getText()+"',"
					+ "'"+adjustment.getText()+"','"+grandTotal.getText()+"','"+totalReceived.getText()+"','"+change.getText()+"','"+totalQuantity.getText()+"') \r\n");	
			
			create5.executeUpdate();
			con1.close();

			
			Connection con  = BillConnect.getConnect();
			PreparedStatement create1 = con.prepareStatement("CREATE TABLE ["+bill.getText().toString()+"] \r\n"
					+ "(Serial int , ProductCode VARCHAR(50), ProductTitle VARCHAR(50), ProductCategory VARCHAR(50) "
					+ ",Quantity VARCHAR(50) , PerUnit VARCHAR(50) , Total VARCHAR(50), Discount VARCHAR(100) ,NetTotal VARCHAR(100))\r\n"
					);
			create1.executeUpdate();
			
			for(int i =0;i<mainTable.getRowCount();i++) {
				PreparedStatement ps = con.prepareStatement("insert into ["+bill.getText().toString()+"] values(?,?,?,?,?,?,?,?,?)");
	            ps.setString(1, mainTable.getValueAt(i, 0).toString());
	            ps.setString(2, mainTable.getValueAt(i, 1).toString());
	            ps.setString(3, mainTable.getValueAt(i, 2).toString());
	            ps.setString(4, mainTable.getValueAt(i, 3).toString());
	            ps.setString(5, mainTable.getValueAt(i, 4).toString());
	            ps.setString(6, mainTable.getValueAt(i, 5).toString());
	            ps.setString(7, mainTable.getValueAt(i, 6).toString());
	            ps.setString(8, mainTable.getValueAt(i, 7).toString());
	            ps.setString(9, mainTable.getValueAt(i, 8).toString());
	            ps.executeUpdate();
			}
			
			bill.setText(String.valueOf(Integer.valueOf(bill.getText())+1));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void createCustomerTable() {
		con  = Connect.getConnect();
		PreparedStatement create4;
		try {
			
			PreparedStatement create1 = con.prepareStatement("IF OBJECT_ID(N'dbo.LabourRecord', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE LabourRecord \r\n"
					+ "(ID int , Name VARCHAR(50), Number VARCHAR(50), Address VARCHAR(50) ,BasicSalary VARCHAR(50) , DailyWages VARCHAR(50) , Email VARCHAR(50), Nature VARCHAR(100) , PRIMARY KEY (ID))\r\n"
					+ "END;");
			create1.executeUpdate();
			
			create4 = con.prepareStatement("IF OBJECT_ID(N'dbo.CustomerRecord', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE CustomerRecord \r\n"
					+ "(ID int , Name VARCHAR(50), Number VARCHAR(50), Address VARCHAR(50) ,OpeningBalance int , CreditLimit int , Discount VARCHAR(50), Email VARCHAR(100) , Nature VARCHAR(50), AccountBalance VARCHAR(50), PRIMARY KEY (ID))\r\n"
					+ "END;");
			create4.executeUpdate();
			
			PreparedStatement create = con.prepareStatement("BEGIN IF NOT EXISTS (SELECT * FROM CustomerRecord WHERE ID = '1')\r\n"
					+ "BEGIN\r\n"
					+ "   INSERT INTO CustomerRecord (ID,Name) VALUES ('1','Counter Sale') \r\n"
					+ "END END");
					
			create.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void createProductTable() {
		con  = Connect.getConnect();
		PreparedStatement create4;
		try {
			create4 = con.prepareStatement("IF OBJECT_ID(N'dbo.ProductList', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE ProductList \r\n"
					+ "(ProductCode VARCHAR(50) , ProductTitle VARCHAR(50), Category VARCHAR(50), SalePrice VARCHAR(50) ,PurchasePrice VARCHAR(50) , Available VARCHAR(50) , Location VARCHAR(50), PRIMARY KEY (ProductTitle))\r\n"
					+ "END;");
			create4.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	
	public static void main(String[] args) {
		
	
		
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
	
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BillReviewWindow frame = new BillReviewWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	public BillReviewWindow() {
		

		
		setIconImage(Toolkit.getDefaultToolkit().getImage(BillReviewWindow.class.getResource("/SourceCode/edit.png")));
					
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
		
		newProductPanel = new JPanel();
		newProductPanel.addFocusListener(new FocusAdapter() {
		});
		
		JLabel lblNewLabel_1_2 = new JLabel("");
		lblNewLabel_1_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				productTable.setVisible(false);
				productTable.disable();
				productList.setVisible(false);
				productList.disable();
			}
		});
		
		billList = new JScrollPane();
		billList.setBounds(500, 62, 130, 100);
		contentPane.add(billList);
		
		billTypeTable = new JTable();
		billTypeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				billType.setText(billTypeTable.getValueAt(billTypeTable.getSelectedRow(), 0).toString());
				
				billList.setVisible(false);
				billList.disable();
				
				searchBar.requestFocus();
				searchBar.selectAll();
			}
		});
		billTypeTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					billType.setText(billTypeTable.getValueAt(billTypeTable.getSelectedRow(), 0).toString());
					
					billList.setVisible(false);
					billList.disable();
					
					searchBar.requestFocus();
					searchBar.selectAll();
				}
			}
		});
		billTypeTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"billTypeName"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		billTypeTable.getColumnModel().getColumn(0).setResizable(false);
		billTypeTable.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		billList.setViewportView(billTypeTable);
		
		
		
		lblNewLabel_1_2.setIcon(new ImageIcon(BillReviewWindow.class.getResource("/Images/exit.png")));
		lblNewLabel_1_2.setBounds(440, 82, 30, 30);
		contentPane.add(lblNewLabel_1_2);
		newProductPanel.setBackground(Color.GRAY);
		newProductPanel.setBorder(UIManager.getBorder("ScrollPane.border"));
		newProductPanel.setBounds(50, 150, 270, 227);
		contentPane.add(newProductPanel);
		newProductPanel.setLayout(null);
		
		billTypeTable.setTableHeader(null);
		
		DefaultTableModel model = (DefaultTableModel) billTypeTable.getModel();
		model.addRow(new Object[] {"Cash Sales"});
		model.addRow(new Object[] {"Credit Sales"});
		model.addRow(new Object[] {"Information Bill"});
		
		billList.setVisible(false);
		billList.disable();
		
		lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				newProductPanel.setVisible(false);
				newProductPanel.disable();
			}
		});
		lblNewLabel_1_1.setIcon(new ImageIcon(BillReviewWindow.class.getResource("/Images/exit.png")));
		lblNewLabel_1_1.setBounds(226, 11, 30, 30);
		newProductPanel.add(lblNewLabel_1_1);
		
		productCode = new promptTextField(34, "Product Code");
		productCode.setHorizontalAlignment(SwingConstants.CENTER);
		productCode.setAlignmentX(5.0f);
		productCode.setBounds(10, 52, 246, 34);
		productCode.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		newProductPanel.add(productCode);
		
		salePrice = new promptTextField(34, "Sale Price");
		salePrice.setHorizontalAlignment(SwingConstants.CENTER);
		salePrice.setAlignmentX(5.0f);
		salePrice.setBounds(10, 97, 246, 34);
		salePrice.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		newProductPanel.add(salePrice);
		
		category = new JComboBox<String>();
		category.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		category.setBounds(10, 142, 246, 34);
		newProductPanel.add(category);
		
		btnEnter_1 = new JButton("ADD");
		btnEnter_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				code.setText(productCode.getText());
				perUnit.setText(salePrice.getText());
				try{
				 	String a2 = code.getText().toUpperCase();
				 	String a3 = WordUtils.capitalizeFully(searchBar.getText());
				 	String a1 = WordUtils.capitalizeFully(category.getSelectedItem().toString());
				 	String a4 = perUnit.getText().toUpperCase();
				 	//String a5 = purchasePrice.getText().toUpperCase();
				 	//String a6 = qty.getText().toUpperCase();
				 	//String a7 = location.getText().toUpperCase();
				 	
				 	Connection con = Connect.getConnect();
				 	PreparedStatement ps = con.prepareStatement("insert into ProductList values(?,?,?,?,?,?,?)");
		            ps.setString(1, a2);
		            ps.setString(2, a3);
		            ps.setString(3, a1);
		            ps.setString(4, a4);
		            ps.setString(5, "0");
		            ps.setString(6, "0");
		            ps.setString(7, "0");
		            
		              ps.executeUpdate();
					 con.close();		
//					 products.add(new Product(code.getText(),searchBar.getText().toUpperCase(),category.getSelectedItem().toString().toUpperCase(),perUnit.getText().toUpperCase(),"0"));
					getItemIntoMainList();
					setProductTable();
					
					productCode.setText("Product Code");
					salePrice.setText("Sale Price");
					category.setSelectedIndex(1);
	 
			 }
				 catch(Exception e1){
			       }
				
				quantity.requestFocus();
				newProductPanel.setVisible(false);
				newProductPanel.disable();
				
				
				
				
				
			}
		});
		btnEnter_1.setForeground(Color.BLACK);
		btnEnter_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		btnEnter_1.setBackground((Color) null);
		btnEnter_1.setBounds(156, 187, 100, 34);
		newProductPanel.add(btnEnter_1);
		category.addItem("Select Category");
		category.setSelectedIndex(0);
		
		JLabel lblDate_1_4_1 = new JLabel("Add New Product");
		lblDate_1_4_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_4_1.setForeground(Color.BLACK);
		lblDate_1_4_1.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblDate_1_4_1.setAlignmentX(0.5f);
		lblDate_1_4_1.setBounds(10, 11, 206, 34);
		newProductPanel.add(lblDate_1_4_1);
		category.addItem("Books");
		category.addItem("Bags");
		category.addItem("Stationery");
		category.addItem("Uniform");
		category.addItem("Garments");
		
		
		
		productList = new JScrollPane();
		productList.setBounds(10, 121, 470, 299);
		contentPane.add(productList);
		
		productTable = new JTable();
		productTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					for (int i = 0; i < products.size(); i++) {
						String a = productTable.getValueAt(productTable.getSelectedRow(), 0).toString();
						String b = productTable.getValueAt(productTable.getSelectedRow(), 1).toString();
						if (products.get(i).getTitle().equals(b) || products.get(i).getCode().equals(a)) {
							code.setText(products.get(i).getCode());
							searchBar.setText(products.get(i).getTitle());
							perUnit.setText(products.get(i).getSalePrice());
							quantity.requestFocus();
					}
				}
						 
						productTable.setVisible(false);
						productTable.disable();
						productList.setVisible(false);
						productList.disable();
				}
			}
		});
		productTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getItemFromTitle();
				
			}
		});
		productTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Code","Name"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false,false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		productTable.getColumnModel().getColumn(0).setResizable(false);
		productTable.getColumnModel().getColumn(0).setPreferredWidth(1);
		productTable.getColumnModel().getColumn(1).setPreferredWidth(250);
		productTable.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		productTable.setTableHeader(null);
		productTable.setVisible(false);
		productTable.disable();
		productList.setColumnHeaderView(productTable);
		
		customerList = new JScrollPane();
		customerList.setBounds(95, 62, 130, 165);
		contentPane.add(customerList);
		
		customerTable = new JTable();
		customerTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				customerList.enable();
				customerTable.setEnabled(true);
				//System.out.print(customer);
				
				customerID.setText(customerTable.getValueAt(customerTable.getSelectedRow(), 0).toString());
				customerName.setText(customerTable.getValueAt(customerTable.getSelectedRow(), 1).toString());
				
				customerList.setVisible(false);
				customerList.disable();
				
			}
		});
		customerTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		customerTable.getColumnModel().getColumn(0).setResizable(false);
		customerTable.getColumnModel().getColumn(0).setPreferredWidth(15);
		customerTable.getColumnModel().getColumn(1).setResizable(false);
		customerTable.getColumnModel().getColumn(1).setPreferredWidth(90);
		customerTable.setFont(new Font("Century Gothic", Font.PLAIN, 10));
		customerList.setViewportView(customerTable);
		
		customerTable.setTableHeader(null);
		
		customerList.setVisible(false);
		customerList.disable();
		
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
		
		JButton btnCustomerf = new JButton();
		btnCustomerf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CustomerPortfolio().setVisible(true);
				dispose();
			}
		});
		btnCustomerf.setText("Customer");
		btnCustomerf.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnCustomerf.setBounds(95, 11, 130, 25);
		contentPane.add(btnCustomerf);
		
		customerID = new promptTextField(10,"1");
		customerID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				customerList.setVisible(true);
				customerList.enable();
				
				DefaultTableModel tab = (DefaultTableModel) customerTable.getModel();
				String search = WordUtils.capitalizeFully(customerID.getText());
				TableRowSorter <DefaultTableModel> tr = new TableRowSorter <DefaultTableModel>(tab);
				customerTable.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter(search));
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {

					customerID.setText(customerTable.getValueAt(0, 0).toString());
					customerName.setText(customerTable.getValueAt(0, 1).toString());
					
					customerList.setVisible(false);
					customerList.disable();
					
					code.requestFocus();
					code.selectAll();
				}
			}
		});
		customerID.setHorizontalAlignment(SwingConstants.CENTER);
		customerID.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		customerID.setColumns(10);
		customerID.setBounds(95, 36, 30, 25);
		contentPane.add(customerID);
		
		customerName = new promptTextField(10,"Counter Sale");
		customerName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				customerList.setVisible(true);
				customerList.enable();
				
				DefaultTableModel tab = (DefaultTableModel) customerTable.getModel();
				String search = WordUtils.capitalizeFully(customerName.getText());
				TableRowSorter <DefaultTableModel> tr = new TableRowSorter <DefaultTableModel>(tab);
				customerTable.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter(search));
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					
					customerList.enable();
					customerTable.setEnabled(true);
					
					customerID.setText(customerTable.getValueAt(0, 0).toString());
					customerName.setText(customerTable.getValueAt(0, 1).toString());
					
					
					customerList.setVisible(false);
					customerList.disable();
					
					code.requestFocus();
					code.selectAll();
				}
			}
		});
		customerName.setHorizontalAlignment(SwingConstants.CENTER);
		customerName.setFont(new Font("Century Gothic", Font.PLAIN, 10));
		customerName.setColumns(10);
		customerName.setBounds(125, 36, 100, 25);
		contentPane.add(customerName);
		
		JButton btnProdListf = new JButton();
		btnProdListf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ProductList().setVisible(true);;
				dispose();
			}
		});
		btnProdListf.setText("Product List");
		btnProdListf.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnProdListf.setBounds(230, 11, 130, 25);
		contentPane.add(btnProdListf);
		
		code = new promptTextField(10, "Product Id Here");
		code.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				code.selectAll();
			}
		});
		code.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					getItemFromCode();
				}
				
			}
		});
		code.setHorizontalAlignment(SwingConstants.CENTER);
		code.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		code.setBounds(230, 36, 130, 25);
		contentPane.add(code);
		
		JButton btnSalesPersonf = new JButton();
		btnSalesPersonf.setText("Sales Person");
		btnSalesPersonf.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnSalesPersonf.setBounds(365, 11, 130, 25);
		contentPane.add(btnSalesPersonf);
		
		salesPerson = new promptTextField(10, "Name");
		salesPerson.setHorizontalAlignment(SwingConstants.CENTER);
		salesPerson.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		salesPerson.setBounds(365, 36, 130, 25);
		contentPane.add(salesPerson);
		
		JButton btnCalculatorf = new JButton();
		btnCalculatorf.setToolTipText("Calculator");
		btnCalculatorf.setIcon(new ImageIcon(BillReviewWindow.class.getResource("/Images/calculator.png")));
		btnCalculatorf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Calculator().setVisible(true);
			}
		});
		btnCalculatorf.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		btnCalculatorf.setBorderPainted(false); 
		btnCalculatorf.setContentAreaFilled(false); 
		btnCalculatorf.setFocusPainted(false); 
		btnCalculatorf.setOpaque(false);
		btnCalculatorf.setBounds(1265, 11, 40, 35);
		
		contentPane.add(btnCalculatorf);
		
		JButton btnBillNumber = new JButton();
		btnBillNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new BillDetailsLIST().setVisible(true);
				dispose();
			}
		});
		btnBillNumber.setText("Bill List");
		btnBillNumber.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnBillNumber.setBounds(10, 11, 80, 25);
		contentPane.add(btnBillNumber);
		
		bill = new promptTextField(10, "Bill #");
		bill.setHorizontalAlignment(SwingConstants.CENTER);
		bill.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		bill.setBounds(10, 36, 80, 25);
		contentPane.add(bill);
		
		searchBar = new promptTextField(10, "Search Here");
		searchBar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				DefaultTableModel tab = (DefaultTableModel) productTable.getModel();
				String search = WordUtils.capitalizeFully(searchBar.getText());
				TableRowSorter <DefaultTableModel> tr = new TableRowSorter <DefaultTableModel>(tab);
				productTable.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter(search));

				
				if( searchBar.getText().equals("Search Here") || searchBar.getText().equals("")) {
					productTable.setVisible(false);
					productTable.disable();
					productList.setVisible(false);
					productList.disable();
					
					newProductPanel.setVisible(false);
					newProductPanel.disable();
					
				}else if (productTable.getRowCount()==0){
					productTable.setVisible(false);
					productTable.disable();
					productList.setVisible(false);
					productList.disable();
					
					newProductPanel.setVisible(true);
					newProductPanel.enable();
				}else {
					productTable.setVisible(true);
					productTable.enable();
					productList.setVisible(true);
					productList.enable();
					
					newProductPanel.setVisible(false);
					newProductPanel.disable();
					
				}
				
				
				
				status.setText("");

			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					for (int i = 0; i < products.size(); i++) {
						String a = productTable.getValueAt(0, 1).toString();
						if (products.get(i).getTitle().equals(a)) {
							code.setText(products.get(i).getCode());
							searchBar.setText(products.get(i).getTitle());
							perUnit.setText(products.get(i).getSalePrice());
							quantity.requestFocus();
					}
				}
						 
						productTable.setVisible(false);
						productTable.disable();
						productList.setVisible(false);
						productList.disable();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN) {
					productTable.requestFocus();
					productTable.changeSelection(0, 0, false, false);
				}
			}
		});
		searchBar.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(searchBar.getText().equals("Search Here") || searchBar.getText().equals("")) {
					productTable.setVisible(false);
					productTable.disable();
					productList.setVisible(false);
					productList.disable();
					
					newProductPanel.setVisible(false);
					newProductPanel.disable();
				}
				
			}
		});
		searchBar.setHorizontalAlignment(SwingConstants.LEFT);
		searchBar.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		searchBar.setBounds(10, 72, 470, 50);
		contentPane.add(searchBar);
		
		quantity = new promptTextField(10, "Qty");
		quantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					Product pro = null;
					boolean val = false;
					
					for (int i = 0; i < products.size(); i++) {
						if (products.get(i).getTitle().toUpperCase().equals(searchBar.getText().toUpperCase())) {
							val = true;
							pro = products.get(i);
						}
					}
					
					boolean sec = false;
					
					for(int i = 0; i<mainTable.getRowCount();i++) {
						if(mainTable.getValueAt(i, 2).toString().equals(pro.getTitle()) &&mainTable.getValueAt(i, 5).toString().equals(pro.getSalePrice()) && mainTable.getValueAt(i, 7).toString().equals("0")) {
							mainTable.setValueAt((Integer.valueOf(mainTable.getValueAt(i, 4).toString())+Integer.valueOf(quantity.getText())), i, 4);
							String tot = String.valueOf(Integer.valueOf(mainTable.getValueAt(i, 4).toString())*Double.valueOf(mainTable.getValueAt(i, 5).toString()));
							mainTable.setValueAt(tot, i, 6);
							mainTable.setValueAt(tot, i, 8);
							code.requestFocus();
							code.selectAll();
							
							sec = true;
							
							break;
							

						}
					}
					
					if(!sec) {
						total.setText(String.valueOf(((Integer.valueOf(quantity.getText())*Double.valueOf(perUnit.getText())))));

						DefaultTableModel tab = (DefaultTableModel) mainTable.getModel();
						
						String cat = null;
						for (int i = 0; i < products.size(); i++) {
							if (products.get(i).getTitle().toUpperCase().equals(searchBar.getText().toUpperCase())) {
									cat = products.get(i).getCategory();
						}
						}
						
						tab.addRow(new Object[] {String.valueOf(mainTable.getRowCount()+1),code.getText(),searchBar.getText(),cat,quantity.getText(),perUnit.getText()
								,total.getText(),'0',total.getText()});
					}
					
					code.setText("Product Id Here");
					quantity.setText("Qty");
					dis1.setText("%age");
					dis2.setText("Value");
					perUnit.setText("Price");
					searchBar.setText("Search Here");
					total.setText("0");
					totalReceived.setText("0");
					change.setText("0");
					
					searchBar.requestFocus();	
					
					productTable.setVisible(false);
					productTable.disable();
					productList.setVisible(false);
					productList.disable();
					
					newProductPanel.setVisible(false);
					newProductPanel.disable();
				}
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(quantity.getText().equals("")) {
					total.setText("0");
				}else {
					total.setText(String.valueOf(((Integer.valueOf(quantity.getText())*Double.valueOf(perUnit.getText())))));
				}
				
			}
		});
		quantity.setHorizontalAlignment(SwingConstants.CENTER);
		quantity.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		quantity.setBounds(490, 97, 75, 25);
		contentPane.add(quantity);
		
		dis1 = new promptTextField(10, "%age");
		dis1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					dis2.setText(String.format("%.2f", (Double.valueOf(perUnit.getText())*(Double.valueOf(dis1.getText())/100))));
					
					
					total.setText(String.valueOf(((Integer.valueOf(quantity.getText())*Double.valueOf(perUnit.getText()))-Math.floor(Double.valueOf(dis2.getText())*Double.valueOf(quantity.getText())))));
					
					
					DefaultTableModel tab = (DefaultTableModel) mainTable.getModel();
					
					String cat = null;

					for (int i = 0; i < products.size(); i++) {
						if (products.get(i).getTitle().equals(searchBar.getText())) {
							
								cat = products.get(i).getCategory();
							
					}
					}
					
					tab.addRow(new Object[] {String.valueOf(mainTable.getRowCount()+1),code.getText(),searchBar.getText(),cat,quantity.getText(),perUnit.getText()
							,String.valueOf(((Integer.valueOf(quantity.getText())*Double.valueOf(perUnit.getText())))),dis1.getText(),(int)Math.round(Double.valueOf(total.getText()))});
					
					
					
					
					

					code.setText("Product Code Here");
					quantity.setText("Qty");
					dis1.setText("%age");
					dis2.setText("Value");
					perUnit.setText("Price");
					searchBar.setText("Search Product by Name");
					total.setText("0");
					
					searchBar.requestFocus();	
					searchBar.selectAll();
					
				}
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(quantity.getText().equals("")) {
					total.setText("0");
				}
				if(dis1.getText().equals("")) {
					dis2.setText("0");
				}else {
					dis2.setText(String.format("%.2f", (Double.valueOf(perUnit.getText())*(Double.valueOf(dis1.getText())/100))));
					
				}
				
				
					total.setText(String.valueOf(((Double.valueOf(quantity.getText())*Double.valueOf(perUnit.getText()))-Math.floor(Double.valueOf(dis2.getText())*Double.valueOf(quantity.getText())))));
				
			}
		});
		dis1.setHorizontalAlignment(SwingConstants.CENTER);
		dis1.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		dis1.setBounds(575, 97, 60, 25);
		contentPane.add(dis1);
		
		perUnit = new promptTextField(10, "Per Unit");
		perUnit.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				perUnit.selectAll();
			}
		});
		perUnit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					Product pro = null;
					boolean val = false;
					
					for (int i = 0; i < products.size(); i++) {
						if (products.get(i).getTitle().toUpperCase().equals(searchBar.getText().toUpperCase())) {
							val = true;
							pro = products.get(i);
						}
					}
					
					boolean sec = false;
					
					for(int i = 0; i<mainTable.getRowCount();i++) {
						if(mainTable.getValueAt(i, 2).toString().equals(pro.getTitle()) && mainTable.getValueAt(i, 5).toString().equals(perUnit.getText()) && mainTable.getValueAt(i, 7).toString().equals("0")) {
							mainTable.setValueAt((Integer.valueOf(mainTable.getValueAt(i, 4).toString())+Integer.valueOf(quantity.getText())), i, 4);
							String tot = String.valueOf(Integer.valueOf(mainTable.getValueAt(i, 4).toString())*Double.valueOf(mainTable.getValueAt(i, 5).toString()));
							mainTable.setValueAt(tot, i, 6);
							mainTable.setValueAt(tot, i, 8);
							code.requestFocus();
							code.selectAll();
							
							sec = true;
							
							break;
							

						}
					}
					
					if(!sec) {
						total.setText(String.valueOf(((Integer.valueOf(quantity.getText())*Double.valueOf(perUnit.getText())))));

						DefaultTableModel tab = (DefaultTableModel) mainTable.getModel();
						
						String cat = null;
						for (int i = 0; i < products.size(); i++) {
							if (products.get(i).getTitle().toUpperCase().equals(searchBar.getText().toUpperCase())) {
									cat = products.get(i).getCategory();
						}
						}
						
						tab.addRow(new Object[] {String.valueOf(mainTable.getRowCount()+1),code.getText(),searchBar.getText(),cat,quantity.getText(),perUnit.getText()
								,total.getText(),'0',total.getText()});
					}
					
					code.setText("Product Id Here");
					quantity.setText("Qty");
					dis1.setText("%age");
					dis2.setText("Value");
					perUnit.setText("Price");
					searchBar.setText("Search Here");
					total.setText("0");
					totalReceived.setText("0");
					change.setText("0");
					
					searchBar.requestFocus();
					
					productTable.setVisible(false);
					productTable.disable();
					productList.setVisible(false);
					productList.disable();
					
					newProductPanel.setVisible(false);
					newProductPanel.disable();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(perUnit.getText().equals("")) {
					total.setText("0");
				}else {
					total.setText(String.valueOf(((Integer.valueOf(quantity.getText())*Double.valueOf(perUnit.getText())))));
				}
			}
		});
		perUnit.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		perUnit.setHorizontalAlignment(SwingConstants.CENTER);
		perUnit.setBounds(705, 97, 75, 25);
		contentPane.add(perUnit);
		
		JLabel lblNewLabel = new JLabel("Quantity");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblNewLabel.setBounds(490, 72, 75, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblDiscount = new JLabel("Discount");
		lblDiscount.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiscount.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblDiscount.setBounds(575, 72, 120, 25);
		contentPane.add(lblDiscount);
		
		JLabel lblPerUnit = new JLabel("Per Unit");
		lblPerUnit.setHorizontalAlignment(SwingConstants.CENTER);
		lblPerUnit.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblPerUnit.setBounds(705, 72, 75, 25);
		contentPane.add(lblPerUnit);
		
		total = new JLabel("0");
		total.setHorizontalAlignment(SwingConstants.CENTER);
		total.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		total.setBounds(1226, 72, 120, 50);
		contentPane.add(total);
		
		JLabel lblTotal = new JLabel("TOTAL");
		lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotal.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblTotal.setBounds(1106, 72, 120, 50);
		contentPane.add(lblTotal);
		
		dis2 = new promptTextField(10, "Value");
		dis2.setHorizontalAlignment(SwingConstants.CENTER);
		dis2.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		dis2.setBounds(635, 97, 60, 25);
		contentPane.add(dis2);
		
		mainScrollPane = new JScrollPane();
		mainScrollPane.setBounds(10, 133, 1336, 424);
		contentPane.add(mainScrollPane);
		
		mainTable = new JTable();
		mainTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					String qty = mainTable.getValueAt(mainTable.getSelectedRow(), 4).toString();
					String perUnit = mainTable.getValueAt(mainTable.getSelectedRow(), 5).toString();
					mainTable.setValueAt(Double.valueOf(Integer.valueOf(qty)*Double.valueOf(perUnit)),mainTable.getSelectedRow(), 6);
					
					double total = Double.valueOf(Double.valueOf(qty)*Double.valueOf(perUnit));
					double newTotal = (Double.valueOf(mainTable.getValueAt(mainTable.getSelectedRow(), 7).toString())*Double.valueOf(mainTable.getValueAt(mainTable.getSelectedRow(), 6).toString())/100);
					
					mainTable.setValueAt(total-newTotal, mainTable.getSelectedRow(), 8);
					
					
				}
			}
		});
		mainTable.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		mainTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Sr. #", "Product Code", "Product Title", "Product Category", "Quantity", "Per Unit", "Total", "Discount", "Net Total"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, true, false, false, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		mainTable.getColumnModel().getColumn(0).setResizable(false);
		mainTable.getColumnModel().getColumn(0).setPreferredWidth(15);
		mainTable.getColumnModel().getColumn(1).setResizable(false);
		mainTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		mainTable.getColumnModel().getColumn(2).setResizable(false);
		mainTable.getColumnModel().getColumn(2).setPreferredWidth(325);
		mainTable.getColumnModel().getColumn(3).setResizable(false);
		mainTable.getColumnModel().getColumn(4).setResizable(false);
		mainTable.getColumnModel().getColumn(4).setPreferredWidth(40);
		mainTable.getColumnModel().getColumn(5).setResizable(false);
		mainTable.getColumnModel().getColumn(5).setPreferredWidth(40);
		mainTable.getColumnModel().getColumn(6).setResizable(false);
		mainTable.getColumnModel().getColumn(6).setPreferredWidth(40);
		mainTable.getColumnModel().getColumn(7).setResizable(false);
		mainTable.getColumnModel().getColumn(7).setPreferredWidth(40);
		mainTable.getColumnModel().getColumn(8).setResizable(false);
		mainTable.getColumnModel().getColumn(8).setPreferredWidth(50);
		mainScrollPane.setViewportView(mainTable);
		

		
UIManager.put("TableHeader.font", new Font("Century Gothic", Font.BOLD, 16));
		
mainScrollPane.setViewportView(mainTable);
		mainTable.setRowHeight(23);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
		
		
		mainTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		mainTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		mainTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		mainTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		mainTable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		mainTable.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		mainTable.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
		mainTable.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
		
		mainScrollPane.setViewportView(mainTable);
		
		JButton btnCalculatorf_1 = new JButton();
		btnCalculatorf_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DailySaleReport().setVisible(true);
				dispose();
			}
		});
		btnCalculatorf_1.setText("Sale Record");
		btnCalculatorf_1.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnCalculatorf_1.setBounds(1145, 11, 120, 35);
		contentPane.add(btnCalculatorf_1);
		

		//extracted from CommonMethods
		CommonMethods.timeFromCommon(time);
		
		status = new JLabel("");
		status.setBounds(0, -10, 605, 34);
		panel.add(status);
		status.setHorizontalTextPosition(SwingConstants.LEFT);
		status.setHorizontalAlignment(SwingConstants.LEFT);
		status.setForeground(Color.BLACK);
		status.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		status.setAlignmentX(0.5f);
		
		JButton btnDeleteEntry = new JButton();
		btnDeleteEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tab = (DefaultTableModel) mainTable.getModel();
			    int[] selectedRows = mainTable.getSelectedRows();
		        if (selectedRows.length > 0) {
		            for (int i = selectedRows.length - 1; i >= 0; i--) {
		            	tab.removeRow(selectedRows[i]);
		            }
		        }
		        for(int i =0; i<mainTable.getRowCount();i++) {
		        	mainTable.setValueAt((i+1), i, 0);
		        }
		        
			 
			JOptionPane.showMessageDialog(null, "Deleted Successfully.");
			}
		});
		btnDeleteEntry.setText("Delete Entry");
		btnDeleteEntry.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnDeleteEntry.setBounds(790, 92, 120, 30);
		contentPane.add(btnDeleteEntry);
		
		JPanel panel_1 = new RoundedPanel(30);
		panel_1.setBounds(1133, 568, 207, 30);
		panel_1.setBackground(null);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblTotal_1 = new JLabel("Total Amount");
		lblTotal_1.setBounds(10, 0, 118, 30);
		panel_1.add(lblTotal_1);
		lblTotal_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotal_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		
		finalTotal = new JLabel("0");
		finalTotal.setBounds(116, 0, 81, 30);
		panel_1.add(finalTotal);
		finalTotal.setHorizontalAlignment(SwingConstants.CENTER);
		finalTotal.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		
		RoundedPanel panel_1_1 = new RoundedPanel(30);
		panel_1_1.setLayout(null);
		panel_1_1.setBackground(null);
		panel_1_1.setBounds(1133, 599, 207, 30);
		contentPane.add(panel_1_1);
		
		JLabel lblTotal_1_3 = new JLabel("Discount");
		lblTotal_1_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotal_1_3.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblTotal_1_3.setBounds(10, 0, 76, 30);
		panel_1_1.add(lblTotal_1_3);
		
		dis = new promptTextField(30, "0");
		dis.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(dis.getText().equals("")) {
					grandTotal.setText(finalTotal.getText());
				}else {
					double tot = 0;
					int qt = 0;
					
					for(int i = 0; i<mainTable.getRowCount();i++) {
						tot+=Double.valueOf(mainTable.getValueAt(i, 8).toString());
						qt+=Double.valueOf(mainTable.getValueAt(i, 4).toString());
					}
					finalTotal.setText(String.valueOf(tot));
					totalQuantity.setText(String.valueOf(qt));
					
					double total = Double.valueOf(finalTotal.getText().toString());
					double newTotal = (Double.valueOf(dis.getText().toString())*Double.valueOf(total)/100);
					grandTotal.setText(String.format("%.2f",total-newTotal+Double.valueOf(adjustment.getText())));
				}
				
				
				
				
	
			}
		});
		dis.setHorizontalAlignment(SwingConstants.CENTER);
		dis.setBounds(106, 0, 101, 30);
		dis.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		dis.setBackground(null);
		panel_1_1.add(dis);
		
		RoundedPanel panel_1_2 = new RoundedPanel(15);
		panel_1_2.setLayout(null);
		panel_1_2.setBackground(null);
		panel_1_2.setBounds(916, 568, 207, 92);
		contentPane.add(panel_1_2);
		
		JLabel lblTotal_1_4 = new JLabel("Grand Total");
		lblTotal_1_4.setForeground(new Color(0, 0, 102));
		lblTotal_1_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotal_1_4.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 22));
		lblTotal_1_4.setBounds(10, 0, 160, 28);
		panel_1_2.add(lblTotal_1_4);
		
		grandTotal = new JLabel("0");
		grandTotal.setForeground(new Color(0, 0, 102));
		grandTotal.setHorizontalAlignment(SwingConstants.CENTER);
		grandTotal.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 24));
		grandTotal.setBounds(10, 62, 187, 30);
		panel_1_2.add(grandTotal);
		
		RoundedPanel panel_1_3 = new RoundedPanel(30);
		panel_1_3.setLayout(null);
		panel_1_3.setBounds(10, 568, 207, 30);
		panel_1_3.setBackground(null);
		contentPane.add(panel_1_3);
		
		JLabel lblTotal_1_1 = new JLabel("Total Quantity");
		lblTotal_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotal_1_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblTotal_1_1.setBounds(10, 0, 112, 30);
		panel_1_3.add(lblTotal_1_1);
		
		totalQuantity = new JLabel("0");
		totalQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		totalQuantity.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalQuantity.setBounds(112, 0, 85, 30);
		panel_1_3.add(totalQuantity);
		
		panel_1_3_1 = new RoundedPanel(30);
		panel_1_3_1.setLayout(null);
		panel_1_3_1.setBounds(1133, 630, 207, 30);
		panel_1_3_1.setBackground(null);
		contentPane.add(panel_1_3_1);
		
		JLabel lblTotal_1_1_1 = new JLabel("Adjustment");
		lblTotal_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotal_1_1_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblTotal_1_1_1.setBounds(10, 0, 112, 30);
		panel_1_3_1.add(lblTotal_1_1_1);
		
		adjustment = new promptTextField(30, "0");
		adjustment.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				double val = 0;
				if(adjustment.getText().equals("")) {
					val = 0;
				}else {
					val = Double.valueOf(adjustment.getText());
				}
				double tot = 0;
				int qt = 0;
				
				for(int i = 0; i<mainTable.getRowCount();i++) {
					tot+=Double.valueOf(mainTable.getValueAt(i, 8).toString());
					qt+=Double.valueOf(mainTable.getValueAt(i, 4).toString());
				}
				finalTotal.setText(String.valueOf(tot));
				totalQuantity.setText(String.valueOf(qt));
				
				double total = Double.valueOf(finalTotal.getText().toString());
				double newTotal = (Double.valueOf(dis.getText().toString())*Double.valueOf(total)/100);
				grandTotal.setText(String.format("%.2f",total-newTotal+val));
				
			}
		});
		adjustment.setHorizontalAlignment(SwingConstants.CENTER);
		adjustment.setBounds(106, 0, 101, 30);
		adjustment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		adjustment.setBackground(null);
		panel_1_3_1.add(adjustment);
		
		JButton btnEnter = new JButton("Print");
		btnEnter.setToolTipText("Print Bill");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
try {
			updateCustomer();
					updateBill();
					print();
					
					
				}catch(Exception e1) {
					System.out.println(e1);
//					JOptionPane.showMessageDialog(null, "Update not successfull!!!");
				}
			}
		});
		btnEnter.setForeground(Color.BLACK);
		btnEnter.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		btnEnter.setBackground((Color) null);
		btnEnter.setBounds(100, 620, 80, 40);
		contentPane.add(btnEnter);
		
		panel_1_3_1_1 = new RoundedPanel(30);
		panel_1_3_1_1.setLayout(null);
		panel_1_3_1_1.setBounds(703, 568, 207, 30);
		panel_1_3_1_1.setBackground(null);
		contentPane.add(panel_1_3_1_1);
		
		
		JLabel lblTotal_1_1_1_1 = new JLabel("Received");
		lblTotal_1_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotal_1_1_1_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblTotal_1_1_1_1.setBounds(10, 0, 112, 30);
		panel_1_3_1_1.add(lblTotal_1_1_1_1);
		
		totalReceived = new promptTextField(30, "0");
		totalReceived.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				double a = 0;
				if(totalReceived.getText().equals("")) {
					change.setText(String.valueOf(Double.valueOf(grandTotal.getText())));
				}else {
					a = Double.valueOf(totalReceived.getText());

					change.setText(String.format("%.2f",Double.valueOf(grandTotal.getText())-a));
				}
				
			}
		});
		totalReceived.setHorizontalAlignment(SwingConstants.CENTER);
		totalReceived.setBounds(106, 0, 101, 30);
		totalReceived.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalReceived.setBackground(null);
		panel_1_3_1_1.add(totalReceived);
		
		RoundedPanel panel_1_4 = new RoundedPanel(30);
		panel_1_4.setLayout(null);
		panel_1_4.setBackground(null);
		panel_1_4.setBounds(703, 630, 207, 30);
		contentPane.add(panel_1_4);
		
		JLabel lblTotal_1_2 = new JLabel("Change");
		lblTotal_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotal_1_2.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblTotal_1_2.setBounds(10, 0, 118, 30);
		panel_1_4.add(lblTotal_1_2);
		
		change = new JLabel("0");
		change.setHorizontalAlignment(SwingConstants.CENTER);
		change.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		change.setBounds(116, 0, 81, 30);
		panel_1_4.add(change);
		
		JButton btnRefresh = new JButton();
		btnRefresh.setToolTipText("Refresh the window");
		btnRefresh.setIcon(new ImageIcon(BillReviewWindow.class.getResource("/Images/refresh.png")));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MainSaleWindow().setVisible(true);dispose();
			}
		});
		btnRefresh.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		btnRefresh.setBounds(1306, 11, 40, 35);
		btnRefresh.setBorderPainted(false); 
		btnRefresh.setContentAreaFilled(false); 
		btnRefresh.setFocusPainted(false); 
		btnRefresh.setOpaque(false);
		contentPane.add(btnRefresh);
		
		JButton btnSave = new JButton("Save");
		btnSave.setToolTipText("Save Bill");
		btnSave.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
					
				 try {
						
						updateBill();
						saveOnly();

						}catch(Exception e1) {
							System.out.println(e1);
//							JOptionPane.showMessageDialog(null, "Update not successfull!!!");
						}
			}
		});
		btnSave.setForeground(Color.BLACK);
		btnSave.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		btnSave.setBackground((Color) null);
		btnSave.setBounds(10, 620, 80, 40);
		contentPane.add(btnSave);
		
		btnLabourSheet = new JButton();
		btnLabourSheet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LabourSheet().setVisible(true);
				dispose();
			}
		});
		btnLabourSheet.setText("Labour Sheet");
		btnLabourSheet.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnLabourSheet.setBounds(1010, 11, 130, 35);
		contentPane.add(btnLabourSheet);
		
		member = new JToggleButton("Member");
		member.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(member.isSelected()) {
					member.setBackground(Color.DARK_GRAY);
					member.setForeground(Color.WHITE);
					
					for(int i =0;i<mainTable.getRowCount();i++) {
						String qty = mainTable.getValueAt(i, 4).toString();
						String perUnit = mainTable.getValueAt(i, 5).toString();
						
						mainTable.setValueAt(3, i, 7);

						double total = Double.valueOf(Double.valueOf(qty)*Double.valueOf(perUnit));
						double newTotal = (Double.valueOf(3)*Double.valueOf(mainTable.getValueAt(i, 6).toString())/100);
						
						mainTable.setValueAt(total-newTotal, i, 8);
					}
					
					
				}else {
					member.setBackground(null);
					member.setForeground(Color.BLACK);
					
					for(int i =0;i<mainTable.getRowCount();i++) {
						String qty = mainTable.getValueAt(i, 4).toString();
						String perUnit = mainTable.getValueAt(i, 5).toString();
						
						mainTable.setValueAt(0, i, 7);

						double total = Double.valueOf(Double.valueOf(qty)*Double.valueOf(perUnit));
						double newTotal = (Double.valueOf(0)*Double.valueOf(mainTable.getValueAt(i, 6).toString())/100);
						
						mainTable.setValueAt(total-newTotal, i, 8);
					}
					
				}
			}
		});
		member.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		member.setBounds(905, 11, 100, 35);
		
		contentPane.add(member);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(227, 568, 468, 92);
		scrollPane.setBackground(null);
		contentPane.add(scrollPane);
		
		comments = new promptTextArea(0,"Comments");
		comments.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				comments.selectAll();
			}
		});
		comments.setWrapStyleWord(true);
		comments.setLineWrap(true);
		comments.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		comments.setBorder(BorderFactory.createCompoundBorder(
						comments.getBorder(), 
				        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		
		scrollPane.setViewportView(comments);
		
		JButton btnBillType = new JButton();
		btnBillType.setText("Bill Type");
		btnBillType.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnBillType.setBounds(500, 11, 130, 25);
		contentPane.add(btnBillType);
		
		billType = new promptTextField(10, "Cash Sales");
		billType.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				billList.setVisible(true);
				billList.enable();
			}

		});
		billType.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_DOWN) {
					billTypeTable.requestFocus();
					billTypeTable.changeSelection(0, 0, false, false);
				}
					
			}
		});
		billType.setHorizontalAlignment(SwingConstants.CENTER);
		billType.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		billType.setBounds(500, 36, 130, 25);
		contentPane.add(billType);
		
		JButton btnLabourSheet_1 = new JButton();
		btnLabourSheet_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new BillDetails().setVisible(true);
				dispose();
			}
		});
		btnLabourSheet_1.setText("Sale Report");
		btnLabourSheet_1.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnLabourSheet_1.setBounds(780, 11, 120, 35);
		contentPane.add(btnLabourSheet_1);
		
		JButton btnLabourSheet_1_1 = new JButton();
		btnLabourSheet_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				ArrayList<dataTransferProduct> arr = new ArrayList<dataTransferProduct>();
				arr.clear();
				for(int i =0; i<mainTable.getRowCount(); i++) {
					arr.add(new dataTransferProduct(mainTable.getValueAt(i, 1).toString(),mainTable.getValueAt(i, 2).toString(),
							mainTable.getValueAt(i, 3).toString(),mainTable.getValueAt(i, 4).toString(),mainTable.getValueAt(i, 5).toString(),
							mainTable.getValueAt(i, 6).toString(),mainTable.getValueAt(i, 7).toString(),mainTable.getValueAt(i, 8).toString()));
				}
				
				boolean me = false;
				if(member.isSelected()) {
					me = true;
				}
				
				DataTransferTable data = 
				new DataTransferTable("2",salesPerson.getText() ,bill.getText(), customerID.getText(), customerName.getText(),billType.getText(),me,dis.getText(), comments.getText(),arr, new Product() );
			
				new MainWindow(data).setVisible(true);
//				data = null;
				dispose();
			}
		});
		btnLabourSheet_1_1.setText("Books");
		btnLabourSheet_1_1.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnLabourSheet_1_1.setBounds(675, 11, 100, 35);
		contentPane.add(btnLabourSheet_1_1);
		
		
		
		


		
		getItemIntoMainList();
		
		createCustomerTable();
		createProductTable();
		createBillTable();
		setProductTable();
		setCustomerList();
		setBillNumber();
		

		productList.setVisible(false);
		productList.disable();
		
		newProductPanel.setVisible(false);
		newProductPanel.disable();
		
		mainTable.getModel().addTableModelListener(
				new TableModelListener() 
				{

					@Override
					public void tableChanged(TableModelEvent e) {
						
						double tot = 0;
						int qt = 0;
						
						for(int i = 0; i<mainTable.getRowCount();i++) {
							tot+=Double.valueOf(mainTable.getValueAt(i, 8).toString());
							qt+=Double.valueOf(mainTable.getValueAt(i, 4).toString());
						}
						finalTotal.setText(String.valueOf(tot));
						totalQuantity.setText(String.valueOf(qt));
						
						
						double total = Double.valueOf(finalTotal.getText().toString());
						double newTotal = (Double.valueOf(dis.getText().toString())*Double.valueOf(total)/100);
						
						grandTotal.setText(String.format("%.2f",total-newTotal+Double.valueOf(adjustment.getText())));
					}
				});
		

		head = mainTable.getTableHeader();
		
//		mailButton.setVisible(false);
//		mailButton.disable();
		
		bill.setText(BillDetailsLIST.getBillID());
		customerName.setText(BillDetailsLIST.getCustomerName());
		
		
		try{

				Connection con  = Connect.getConnect();
			 	String details = "Select ID from CustomerRecord WHERE Name='"+customerName.getText()+"'";
		      PreparedStatement pst = (PreparedStatement) con.prepareStatement(details);
		        ResultSet rs= pst.executeQuery();	        
			while(rs.next()){
				customerID.setText(rs.getString(1));
			 }
			 con.close();	
				
	 }
		 catch(Exception e){
			 System.out.println(e);
		
	       }
		
		
		salesPerson.setText(BillDetailsLIST.getSalePerson());
		finalTotal.setText(BillDetailsLIST.getFinalTotal());
		totalQuantity.setText(BillDetailsLIST.getQuantity());
		dis.setText(BillDetailsLIST.getDiscount());
		adjustment.setText(BillDetailsLIST.getAdjustment());
		totalReceived.setText(BillDetailsLIST.getReceived());
		change.setText(BillDetailsLIST.getChange());
		grandTotal.setText(BillDetailsLIST.getGrandTotal());
		comments.setText(BillDetailsLIST.getComments());
		billType.setText(BillDetailsLIST.getBillType());
		if(BillDetailsLIST.getMember().equals("Member")) {
			member.setSelected(true);
		}
		setTable();
		
		
		billTypeDumy = billType.getText();
		
		
		
	}
	
	public static void setBook(DataTransferTable data) {

		searchBar.setText(data.getPro().getTitle());
//		quantity.setText("0");
//		dis1.setText("0");
//		dis2.setText("0");
		perUnit.setText(data.getPro().getSalePrice());
		code.setText(data.getPro().getCode());

		
		products.add(data.getPro());
		quantity.requestFocus();
	}
	
	
	
	private void PrintReportToPrinter(JasperPrint jp) throws JRException {
	    // TODO Auto-generated method stub
	    PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
	    // printRequestAttributeSet.add(MediaSizeName.ISO_A4); //setting page size
	    printRequestAttributeSet.add(new Copies(1));

	    PrinterName printerName = new PrinterName("BlackCopper 80mm Series", null); //gets printer 

	    PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
	    printServiceAttributeSet.add(printerName);

	    JRPrintServiceExporter exporter = new JRPrintServiceExporter();

	    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
	    exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
	    exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
	    exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
	    exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
	    exporter.exportReport();
	}
}


