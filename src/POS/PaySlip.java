package POS;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang.WordUtils;

import Extras.TableForReceipt;
import Extras.promptTextField;
import SourceCode.CommonMethods;
import POS.Connect;
import SourceCode.DailySaleReport;
import SourceCode.ShopExpenses;
import SourceCode.YearlyRecord;
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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JRadioButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PaySlip extends JFrame {

	private JPanel contentPane;
	JLabel date;
	static JTable mainTable;
	ButtonGroup group = new ButtonGroup();
	private JScrollPane mainScrollPane;
	private JLabel main;
	private JTextField pendingBalance;
	private JTextField purchasing;
	private JTextField leaveDeduction;
	private JTextField security;
	private JTextField fine;
	private JTextField advanceSalary;
	private JLabel lblDate_1_5_1;
	private JTextField onTime;
	private JTextField overTime;
	private JTextField fridayDuty;
	private JTextField overFridayDuty;
	private JTextField basicSalary;
	private JTextField totalAdd;
	private JTextField totalDeduct;
	private JTextField netSalary;
	private JLabel additions;
	private JLabel deductions;
	
	
	public void print() {
		try {	
			 /* Output file location */
	        String outputFile = "D:\\POSTalhaKhalid\\" + main.getText()+".pdf";
       
	        /* Map to hold Jasper report Parameters */
	        Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("employeeName",LabourSheet.getEmployee());
	        parameters.put("salary", basicSalary.getText());
	        parameters.put("advanceSalary", advanceSalary.getText());
	        parameters.put("purchasing", purchasing.getText());
	        parameters.put("leaveDeduction", leaveDeduction.getText());
	        parameters.put("pendingBalance", pendingBalance.getText());
	        parameters.put("security", security.getText());
	        parameters.put("fine", fine.getText());
	        parameters.put("deductionTotal", totalDeduct.getText());
	        parameters.put("onTime", onTime.getText());
	        parameters.put("overTime", overTime.getText());
	        parameters.put("fridayDuty", fridayDuty.getText());
	        parameters.put("overTimeFriday", overFridayDuty.getText());
	        parameters.put("totalAdditions", totalAdd.getText());
	        parameters.put("basicSalary", basicSalary.getText());
	        parameters.put("netSalaryAmount", netSalary.getText());
	        
	        //read jrxml file and creating jasperdesign object
//	        InputStream input = new FileInputStream(new File("src\\Extras\\Blank_A4.jrxml"));
	        InputStream input = new FileInputStream(new File("D:\\POSTalhaKhalid\\ReceiptSample\\PaySlip.jrxml"));
	        
	        JasperDesign jasperDesign = JRXmlLoader.load(input);
	        /*compiling jrxml with help of JasperReport class*/
	        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        
	        /* Using compiled version(.jasper) of Jasper report to generate PDF */
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

	        /* outputStream to create PDF */
	        OutputStream outputStream = new FileOutputStream(new File(outputFile));
	        /* Write content to PDF file */
	        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	       // JasperViewer.viewReport(jasperPrint);

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
	
	private void PrintReportToPrinter(JasperPrint jp) throws JRException {
	    // TODO Auto-generated method stub
	    PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
	    printRequestAttributeSet.add(MediaSizeName.ISO_A4); //setting page size
	    printRequestAttributeSet.add(new Copies(1));

//	    PrinterName printerName = new PrinterName("Hewlett-Packard HP LaserJet 3055", null); //gets printer 
	    PrinterName printerName = new PrinterName("HP LaserJet 3055 PCL5", null); //gets printer 

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
	
	public void createPaySlip() {
		Connection con  = Connect.getConnect();
		try {
			
			PreparedStatement create1 = con.prepareStatement("IF OBJECT_ID(N'dbo.PaySlip"+LabourSheet.getEmployee().replace(" ", "")+"', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE PaySlip"+LabourSheet.getEmployee().replace(" ", "")+" \r\n"
					+ "(ID int ,MONTH VARCHAR(50), Additions VARCHAR(50), OnTime VARCHAR(50), OverTime VARCHAR(50) ,FridayDuty VARCHAR(50) , OverTimeFriday VARCHAR(50) ,"
					+ " Deductions VARCHAR(50), AdvanceSalary VARCHAR(100), Purchasing VARCHAR(100), PendingBalance VARCHAR(100), leaveDeductions VARCHAR(100)"
					+ ", security VARCHAR(100), fine VARCHAR(100), basicSalary VARCHAR(100), totalAdditions VARCHAR(100), totalDeductions VARCHAR(100), netSalary VARCHAR(100)"
					+ " , PRIMARY KEY (ID))\r\n"
					+ "END;");
			create1.executeUpdate();

			PreparedStatement create = con.prepareStatement("BEGIN IF NOT EXISTS (SELECT * FROM PaySlip"+LabourSheet.getEmployee().replace(" ", "")+" WHERE ID = '0')\r\n"
					+ "BEGIN\r\n"
					+ "   INSERT INTO PaySlip"+LabourSheet.getEmployee().replace(" ", "")+" VALUES ('0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0') \r\n"
					+ "END END");
					
			create.executeUpdate();
			
			int ID = 0;
			
			String details = "Select MAX(ID) from PaySlip"+LabourSheet.getEmployee().replace(" ", "");
		    PreparedStatement pst = (PreparedStatement) con.prepareStatement(details);
		    ResultSet rs= pst.executeQuery();	
		    while(rs.next()) {
		    	ID = rs.getInt(1);
		    }
		    
		    String netSalary = "0";
			String details1 = "Select netSalary from PaySlip"+LabourSheet.getEmployee().replace(" ", "")+" WHERE ID = '"+ID+"'";
		    PreparedStatement pst1 = (PreparedStatement) con.prepareStatement(details1);
		    ResultSet rs1= pst1.executeQuery();	
		    while(rs1.next()) {
		    	if(Double.valueOf(rs1.getString(1))<0) {
		    		netSalary = rs1.getString(1);
		    	}
		    }
		    
		    double finalSalary = Double.valueOf(LabourSheet.table.getValueAt(LabourSheet.table.getSelectedRow(), 5).toString())-Double.valueOf(netSalary);
		    
			
			PreparedStatement create2 = con.prepareStatement("BEGIN IF NOT EXISTS (SELECT * FROM PaySlip"+LabourSheet.getEmployee().replace(" ", "")+" WHERE MONTH = '"+date.getText().substring(date.getText().indexOf("-")+1).replaceAll("-"," ")+"')\r\n"
					+ "					BEGIN\r\n"
					+ "						INSERT INTO PaySlip"+LabourSheet.getEmployee().replace(" ", "")+" \r\n"
					+ "						\r\n"
					+ "						VALUES ("+(ID+1)+",'"+date.getText().substring(date.getText().indexOf("-")+1).replaceAll("-"," ")+"','0','0','0','0',\r\n"
					+ "								'0','0','0','0','"+netSalary+"','0','0','0','"+LabourSheet.table.getValueAt(LabourSheet.table.getSelectedRow(), 5).toString()+"','0','"+netSalary+"'\r\n"
					+ "								,'"+finalSalary+"') \r\n"
					+ "					END END;");

			create2.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void loadData() {
			 try{
				 
					Connection con  = Connect.getConnect();
				 	String details = "Select * from PaySlip"+LabourSheet.getEmployee().replace(" ", "")+" WHERE ID IN (SELECT MAX(ID) FROM PaySlip"+LabourSheet.getEmployee().replace(" ","")+")";
			      PreparedStatement pst = (PreparedStatement) con.prepareStatement(details);
			        ResultSet rs= pst.executeQuery();	        
				while(rs.next()){
					additions.setText(rs.getString(3));
					onTime.setText(rs.getString(4));
					overTime.setText(rs.getString(5));
					fridayDuty.setText(rs.getString(6));
					overFridayDuty.setText(rs.getString(7));
					deductions.setText(rs.getString(8));
					advanceSalary.setText(rs.getString(9));
					purchasing.setText(rs.getString(10));
					pendingBalance.setText(rs.getString(11));
					leaveDeduction.setText(rs.getString(12));
					security.setText(rs.getString(13));
					fine.setText(rs.getString(14));
					basicSalary.setText(rs.getString(15));
					totalAdd.setText(rs.getString(16));
					totalDeduct.setText(rs.getString(17));
					netSalary.setText(rs.getString(18));
					
					
				 }
				 con.close();	
		 }
			 catch(Exception e){
				 System.out.println(e);
			
		       }
	}
	
	public void updateData() {
		 try{
			 
			 Connection con  = Connect.getConnect();
			 
			 int ID = 0;
				
				String details = "Select MAX(ID) from PaySlip"+LabourSheet.getEmployee().replace(" ", "");
			    PreparedStatement pst = (PreparedStatement) con.prepareStatement(details);
			    ResultSet rs= pst.executeQuery();	
			    while(rs.next()) {
			    	ID = rs.getInt(1);
			    }
			 
				
				PreparedStatement st = con.prepareStatement("UPDATE PaySlip"+LabourSheet.getEmployee().replace(" ", "")+" SET "
						+ "[Additions]=?, [OnTime]=?, [OverTime]=?, [FridayDuty]=?, [OverTimeFriday]=? "
						+ ", [Deductions]=? , [AdvanceSalary]=? , [Purchasing]=? , [PendingBalance]=? "
						+ ", [leaveDeductions]=?, [security]=?, [fine]=?, [basicSalary]=?"
						+ ", [totalAdditions]=?, [totalDeductions]=?, [netSalary]=?"
						+ " WHERE ID = '"+ID+"'");

				st.setString(1, additions.getText());
				st.setString(2, onTime.getText());
				st.setString(3, overTime.getText());
				st.setString(4, fridayDuty.getText());
				st.setString(5, overFridayDuty.getText());
				st.setString(6, deductions.getText());
				st.setString(7, advanceSalary.getText());
				st.setString(8, purchasing.getText());
				st.setString(9, pendingBalance.getText());
				st.setString(10, leaveDeduction.getText());
				st.setString(11, security.getText());
				st.setString(12, fine.getText());
				st.setString(13, basicSalary.getText());
				st.setString(14, totalAdd.getText());
				st.setString(15, totalDeduct.getText());
				st.setString(16, netSalary.getText());
				
				st.executeUpdate();	
	
			 con.close();	
	 }
		 catch(Exception e){
			 System.out.println(e);
		
	       }
}
	
	
	public void setTotals() {
		
		if(onTime.getText().equals("")) {
			onTime.setText("0");
		}if(overTime.getText().equals("")) {
			overTime.setText("0");
		}if(fridayDuty.getText().equals("")) {
			fridayDuty.setText("0");
		}if(overFridayDuty.getText().equals("")) {
			overFridayDuty.setText("0");
		}
		

		if(advanceSalary.getText().equals("")) {
			advanceSalary.setText("0");
		}if(purchasing.getText().equals("")) {
			purchasing.setText("0");
		}if(pendingBalance.getText().equals("")) {
			pendingBalance.setText("0");
		}if(totalAdd.getText().equals("")) {
			totalAdd.setText("0");
		}if(leaveDeduction.getText().equals("")) {
			leaveDeduction.setText("0");
		}if(security.getText().equals("")) {
			security.setText("0");
		}if(fine.getText().equals("")) {
			fine.setText("0");
		}
		
		
		additions.setText(String.valueOf(Double.valueOf(onTime.getText())+Double.valueOf(overTime.getText())+Double.valueOf(fridayDuty.getText())+Double.valueOf(overFridayDuty.getText())));
		deductions.setText(String.valueOf(Double.valueOf(advanceSalary.getText())+Double.valueOf(purchasing.getText())+Double.valueOf(pendingBalance.getText())
		+Double.valueOf(leaveDeduction.getText())+Double.valueOf(security.getText())+Double.valueOf(fine.getText())));
		totalAdd.setText(additions.getText());
		totalDeduct.setText(deductions.getText());
		netSalary.setText(String.valueOf(Double.valueOf(totalAdd.getText())+Double.valueOf(basicSalary.getText())-Double.valueOf(totalDeduct.getText())));
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					AttendanceSheet frame = new AttendanceSheet();
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public PaySlip() {
		

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
	
		
		
		setFont(new Font("Century Gothic", Font.PLAIN, 18));
		setUndecorated(true);
		
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
		time.setHorizontalAlignment(SwingConstants.RIGHT);
		time.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		time.setBounds(1219, 0, 111, 24);
		panel.add(time);
		
		date = new JLabel("Date:");
		date.setHorizontalAlignment(SwingConstants.RIGHT);
		date.setBounds(890, 0, 319, 24);
		panel.add(date);
		date.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		date.setText(a.format(d));

		//extracted from CommonMethods
		CommonMethods.timeFromCommon(time);
		
		
		
		Font font = new Font("Times New Roman", Font.BOLD, 30);
		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		
		main = new JLabel("");
		main.setHorizontalAlignment(SwingConstants.CENTER);
		main.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 26));
		main.setBounds(10, 11, 1366, 30);
		main.setText("Pay Slip of "+LabourSheet.getEmployee());
		contentPane.add(main);
		
		JButton btnNewButton_1 = new JButton("Refresh");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PaySlip().setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setBounds(967, 626, 150, 34);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2_1 = new JButton("Main Window");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MainSaleWindow().setVisible(true);
				dispose();
			}
		});
		btnNewButton_2_1.setForeground(Color.BLACK);
		btnNewButton_2_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_2_1.setBackground(Color.WHITE);
		btnNewButton_2_1.setBounds(1127, 626, 213, 34);
		contentPane.add(btnNewButton_2_1);
		
		JPanel panel_1_5 = new JPanel();
		panel_1_5.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1_5.setBackground(null);
		panel_1_5.setBounds(175, 345, 369, 264);
		contentPane.add(panel_1_5);
		panel_1_5.setLayout(null);
		
		JLabel lblDate_1_5 = new JLabel("Advance Salary");
		lblDate_1_5.setBounds(21, 11, 145, 34);
		panel_1_5.add(lblDate_1_5);
		lblDate_1_5.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_5.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_5.setForeground(Color.BLACK);
		lblDate_1_5.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblDate_1_5.setAlignmentX(0.5f);
		
		advanceSalary = new JTextField();
		advanceSalary.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				advanceSalary.selectAll();
			}
		});
		advanceSalary.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				setTotals();
			}
		});
		advanceSalary.setText("0");
		advanceSalary.setBounds(181, 13, 176, 30);
		panel_1_5.add(advanceSalary);
		advanceSalary.setHorizontalAlignment(SwingConstants.CENTER);
		advanceSalary.setAlignmentX(5.0f);
		advanceSalary.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		advanceSalary.setBackground(null);
		
		JLabel lblDate_1_3 = new JLabel("Fine");
		lblDate_1_3.setBounds(21, 216, 145, 34);
		panel_1_5.add(lblDate_1_3);
		lblDate_1_3.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_3.setForeground(Color.BLACK);
		lblDate_1_3.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblDate_1_3.setAlignmentX(0.5f);
		
		fine = new JTextField();
		fine.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				fine.selectAll();
			}
		});
		fine.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				setTotals();
			}
		});
		fine.setText("0");
		fine.setBackground(null);
		
		fine.setBounds(181, 218, 176, 30);
		panel_1_5.add(fine);
		fine.setHorizontalAlignment(SwingConstants.CENTER);
		fine.setAlignmentX(5.0f);
		
		
		JPanel panel_1_4 = new JPanel();
		panel_1_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_4.setBounds(10, 216, 348, 34);
		panel_1_5.add(panel_1_4);
		panel_1_4.setBackground(null);
		
		purchasing = new JTextField();
		purchasing.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				purchasing.selectAll();
			}
			
		});
		purchasing.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				setTotals();
			}
		});
		purchasing.setText("0");
		purchasing.setBackground(null);
		
		purchasing.setBounds(181, 54, 176, 30);
		panel_1_5.add(purchasing);
		purchasing.setHorizontalAlignment(SwingConstants.CENTER);
		purchasing.setAlignmentX(5.0f);
		
		JLabel lblDate_1 = new JLabel("Purchasing");
		lblDate_1.setBounds(21, 52, 145, 34);
		panel_1_5.add(lblDate_1);
		lblDate_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1.setForeground(Color.BLACK);
		lblDate_1.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblDate_1.setAlignmentX(0.5f);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(10, 52, 348, 34);
		panel_1_5.add(panel_1);
		panel_1.setBackground(null);
		
		JLabel lblDate_1_1 = new JLabel("Pending Balance");
		lblDate_1_1.setBounds(21, 93, 145, 34);
		panel_1_5.add(lblDate_1_1);
		lblDate_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_1.setForeground(Color.BLACK);
		lblDate_1_1.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblDate_1_1.setAlignmentX(0.5f);
		
		pendingBalance = new JTextField();
		pendingBalance.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				pendingBalance.selectAll();
			}
		});
		pendingBalance.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				setTotals();
			}
		});
		pendingBalance.setText("0");
		pendingBalance.setBounds(181, 95, 176, 30);
		pendingBalance.setBackground(null);
		
		panel_1_5.add(pendingBalance);
		pendingBalance.setHorizontalAlignment(SwingConstants.CENTER);
		pendingBalance.setAlignmentX(5.0f);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_1.setBounds(10, 93, 348, 34);
		panel_1_5.add(panel_1_1);
		panel_1_1.setBackground(null);
		
		JLabel lblDate_1_2 = new JLabel("Leave Deduction");
		lblDate_1_2.setBounds(21, 134, 145, 34);
		panel_1_5.add(lblDate_1_2);
		lblDate_1_2.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_2.setForeground(Color.BLACK);
		lblDate_1_2.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblDate_1_2.setAlignmentX(0.5f);
		
		leaveDeduction = new JTextField();
		leaveDeduction.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				leaveDeduction.selectAll();
			}
		});
		leaveDeduction.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				setTotals();
			}
		});
		leaveDeduction.setText("0");
		leaveDeduction.setBackground(null);
		leaveDeduction.setBounds(181, 136, 176, 30);
		
		panel_1_5.add(leaveDeduction);
		leaveDeduction.setHorizontalAlignment(SwingConstants.CENTER);
		leaveDeduction.setAlignmentX(5.0f);
		
		JPanel panel_1_2 = new JPanel();
		panel_1_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_2.setBounds(10, 134, 348, 34);
		panel_1_5.add(panel_1_2);
		panel_1_2.setBackground(null);
		
		JLabel lblDate_1_2_1 = new JLabel("Security");
		lblDate_1_2_1.setBounds(21, 175, 145, 34);
		panel_1_5.add(lblDate_1_2_1);
		lblDate_1_2_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_2_1.setForeground(Color.BLACK);
		lblDate_1_2_1.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblDate_1_2_1.setAlignmentX(0.5f);
		
		security = new JTextField();
		security.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				security.selectAll();
			}
		});
		security.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				setTotals();
			}
		});
		security.setText("0");
		security.setBounds(181, 177, 176, 30);
		security.setBackground(null);
		panel_1_5.add(security);
		security.setHorizontalAlignment(SwingConstants.CENTER);
		security.setAlignmentX(5.0f);
		
		
		JPanel panel_1_3 = new JPanel();
		panel_1_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_3.setBounds(10, 175, 348, 34);
		panel_1_5.add(panel_1_3);
		panel_1_3.setBackground(null);
		
		
		JPanel panel_1_6 = new JPanel();
		panel_1_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_6.setBounds(10, 11, 348, 34);
		panel_1_5.add(panel_1_6);
		panel_1_6.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		panel_1_6.setBackground(null);
		
		security.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		leaveDeduction.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		pendingBalance.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		purchasing.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		fine.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		
		lblDate_1_5_1 = new JLabel("Deductions");
		lblDate_1_5_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_5_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_5_1.setForeground(Color.BLACK);
		lblDate_1_5_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
		lblDate_1_5_1.setAlignmentX(0.5f);
		lblDate_1_5_1.setBounds(175, 310, 182, 34);
		contentPane.add(lblDate_1_5_1);
		
		JLabel lblDate_1_5_1_1 = new JLabel("Additions");
		lblDate_1_5_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_5_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_5_1_1.setForeground(Color.BLACK);
		lblDate_1_5_1_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
		lblDate_1_5_1_1.setAlignmentX(0.5f);
		lblDate_1_5_1_1.setBounds(175, 78, 182, 34);
		contentPane.add(lblDate_1_5_1_1);
		
		JPanel panel_1_5_1 = new JPanel();
		panel_1_5_1.setLayout(null);
		panel_1_5_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1_5_1.setBackground((Color) null);
		panel_1_5_1.setBounds(175, 113, 369, 178);
		contentPane.add(panel_1_5_1);
		
		JLabel lblDate_1_5_2 = new JLabel("On Time Duty Bonus");
		lblDate_1_5_2.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_5_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_5_2.setForeground(Color.BLACK);
		lblDate_1_5_2.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblDate_1_5_2.setAlignmentX(0.5f);
		lblDate_1_5_2.setBounds(21, 11, 163, 34);
		panel_1_5_1.add(lblDate_1_5_2);
		
		onTime = new JTextField();
		onTime.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				onTime.selectAll();
			}
		});
		onTime.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				setTotals();
			}
		});
		onTime.setText("0");
		onTime.setHorizontalAlignment(SwingConstants.CENTER);
		onTime.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		onTime.setBackground((Color) null);
		onTime.setAlignmentX(5.0f);
		onTime.setBounds(181, 13, 176, 30);
		panel_1_5_1.add(onTime);
		
		overTime = new JTextField();
		overTime.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				overTime.selectAll();
			}
		});
		overTime.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				setTotals();
			}
		});
		overTime.setText("0");
		overTime.setHorizontalAlignment(SwingConstants.CENTER);
		overTime.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		overTime.setBackground((Color) null);
		overTime.setAlignmentX(5.0f);
		overTime.setBounds(181, 54, 176, 30);
		panel_1_5_1.add(overTime);
		
		JLabel lblDate_1_4 = new JLabel("Over Time");
		lblDate_1_4.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_4.setForeground(Color.BLACK);
		lblDate_1_4.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblDate_1_4.setAlignmentX(0.5f);
		lblDate_1_4.setBounds(21, 52, 145, 34);
		panel_1_5_1.add(lblDate_1_4);
		
		JPanel panel_1_7 = new JPanel();
		panel_1_7.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_7.setBackground((Color) null);
		panel_1_7.setBounds(10, 52, 348, 34);
		panel_1_5_1.add(panel_1_7);
		
		JLabel lblDate_1_1_1 = new JLabel("Friday Duty");
		lblDate_1_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_1_1.setForeground(Color.BLACK);
		lblDate_1_1_1.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblDate_1_1_1.setAlignmentX(0.5f);
		lblDate_1_1_1.setBounds(21, 93, 145, 34);
		panel_1_5_1.add(lblDate_1_1_1);
		
		fridayDuty = new JTextField();
		fridayDuty.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				fridayDuty.selectAll();
			}
		});
		fridayDuty.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				setTotals();
			}
		});
		fridayDuty.setText("0");
		fridayDuty.setHorizontalAlignment(SwingConstants.CENTER);
		fridayDuty.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		fridayDuty.setBackground((Color) null);
		fridayDuty.setAlignmentX(5.0f);
		fridayDuty.setBounds(181, 95, 176, 30);
		panel_1_5_1.add(fridayDuty);
		
		JPanel panel_1_1_1 = new JPanel();
		panel_1_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_1_1.setBackground((Color) null);
		panel_1_1_1.setBounds(10, 93, 348, 34);
		panel_1_5_1.add(panel_1_1_1);
		
		JLabel lblDate_1_2_2 = new JLabel("Over Time Friday");
		lblDate_1_2_2.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_2_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_2_2.setForeground(Color.BLACK);
		lblDate_1_2_2.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblDate_1_2_2.setAlignmentX(0.5f);
		lblDate_1_2_2.setBounds(21, 134, 145, 34);
		panel_1_5_1.add(lblDate_1_2_2);
		
		overFridayDuty = new JTextField();
		overFridayDuty.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				overFridayDuty.selectAll();
			}
		});
		overFridayDuty.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				setTotals();
			}
		});
		overFridayDuty.setText("0");
		overFridayDuty.setHorizontalAlignment(SwingConstants.CENTER);
		overFridayDuty.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		overFridayDuty.setBackground((Color) null);
		overFridayDuty.setAlignmentX(5.0f);
		overFridayDuty.setBounds(181, 136, 176, 30);
		panel_1_5_1.add(overFridayDuty);
		
		JPanel panel_1_2_1 = new JPanel();
		panel_1_2_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_2_1.setBackground((Color) null);
		panel_1_2_1.setBounds(10, 134, 348, 34);
		panel_1_5_1.add(panel_1_2_1);
		
		JPanel panel_1_6_1 = new JPanel();
		panel_1_6_1.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		panel_1_6_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_6_1.setBackground((Color) null);
		panel_1_6_1.setBounds(10, 11, 348, 34);
		panel_1_5_1.add(panel_1_6_1);
		
		additions = new JLabel("0000");
		additions.setBounds(362, 78, 182, 34);
		contentPane.add(additions);
		additions.setHorizontalTextPosition(SwingConstants.CENTER);
		additions.setHorizontalAlignment(SwingConstants.CENTER);
		additions.setForeground(Color.BLACK);
		additions.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
		additions.setAlignmentX(0.5f);
		
		deductions = new JLabel("0000");
		deductions.setBounds(362, 310, 182, 34);
		contentPane.add(deductions);
		deductions.setHorizontalTextPosition(SwingConstants.CENTER);
		deductions.setHorizontalAlignment(SwingConstants.CENTER);
		deductions.setForeground(Color.BLACK);
		deductions.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
		deductions.setAlignmentX(0.5f);
		
		JPanel panel_1_5_1_1 = new JPanel();
		panel_1_5_1_1.setLayout(null);
		panel_1_5_1_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1_5_1_1.setBackground((Color) null);
		panel_1_5_1_1.setBounds(810, 113, 369, 178);
		contentPane.add(panel_1_5_1_1);
		
		JLabel lblDate_1_5_2_1 = new JLabel("Basic Salary");
		lblDate_1_5_2_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_5_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_5_2_1.setForeground(Color.BLACK);
		lblDate_1_5_2_1.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblDate_1_5_2_1.setAlignmentX(0.5f);
		lblDate_1_5_2_1.setBounds(21, 11, 163, 34);
		panel_1_5_1_1.add(lblDate_1_5_2_1);
		
		basicSalary = new JTextField();
		basicSalary.setEditable(false);
		basicSalary.setText("0");
		basicSalary.setHorizontalAlignment(SwingConstants.CENTER);
		basicSalary.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		basicSalary.setBackground((Color) null);
		basicSalary.setAlignmentX(5.0f);
		basicSalary.setBounds(181, 13, 176, 30);
		panel_1_5_1_1.add(basicSalary);
		
		totalAdd = new JTextField();
		totalAdd.setEditable(false);
		totalAdd.setText("0");
		totalAdd.setHorizontalAlignment(SwingConstants.CENTER);
		totalAdd.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		totalAdd.setBackground((Color) null);
		totalAdd.setAlignmentX(5.0f);
		totalAdd.setBounds(181, 54, 176, 30);
		panel_1_5_1_1.add(totalAdd);
		
		JLabel lblDate_1_4_1 = new JLabel("Add: Additions");
		lblDate_1_4_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_4_1.setForeground(Color.BLACK);
		lblDate_1_4_1.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblDate_1_4_1.setAlignmentX(0.5f);
		lblDate_1_4_1.setBounds(21, 52, 145, 34);
		panel_1_5_1_1.add(lblDate_1_4_1);
		
		JPanel panel_1_7_1 = new JPanel();
		panel_1_7_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_7_1.setBackground((Color) null);
		panel_1_7_1.setBounds(10, 52, 348, 34);
		panel_1_5_1_1.add(panel_1_7_1);
		
		JLabel lblDate_1_1_1_1 = new JLabel("Less: Deductions");
		lblDate_1_1_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_1_1_1.setForeground(Color.BLACK);
		lblDate_1_1_1_1.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblDate_1_1_1_1.setAlignmentX(0.5f);
		lblDate_1_1_1_1.setBounds(21, 93, 145, 34);
		panel_1_5_1_1.add(lblDate_1_1_1_1);
		
		totalDeduct = new JTextField();
		totalDeduct.setEditable(false);
		totalDeduct.setText("0");
		totalDeduct.setHorizontalAlignment(SwingConstants.CENTER);
		totalDeduct.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		totalDeduct.setBackground((Color) null);
		totalDeduct.setAlignmentX(5.0f);
		totalDeduct.setBounds(181, 95, 176, 30);
		panel_1_5_1_1.add(totalDeduct);
		
		JPanel panel_1_1_1_1 = new JPanel();
		panel_1_1_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_1_1_1.setBackground((Color) null);
		panel_1_1_1_1.setBounds(10, 93, 348, 34);
		panel_1_5_1_1.add(panel_1_1_1_1);
		
		JLabel lblDate_1_2_2_1 = new JLabel("Net Salary Amount");
		lblDate_1_2_2_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_2_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_2_2_1.setForeground(Color.BLACK);
		lblDate_1_2_2_1.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblDate_1_2_2_1.setAlignmentX(0.5f);
		lblDate_1_2_2_1.setBounds(21, 134, 145, 34);
		panel_1_5_1_1.add(lblDate_1_2_2_1);
		
		netSalary = new JTextField();
		netSalary.setEditable(false);
		netSalary.setText("0");
		netSalary.setHorizontalAlignment(SwingConstants.CENTER);
		netSalary.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		netSalary.setBackground((Color) null);
		netSalary.setAlignmentX(5.0f);
		netSalary.setBounds(181, 136, 176, 30);
		panel_1_5_1_1.add(netSalary);
		
		JPanel panel_1_2_1_1 = new JPanel();
		panel_1_2_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_2_1_1.setBackground((Color) null);
		panel_1_2_1_1.setBounds(10, 134, 348, 34);
		panel_1_5_1_1.add(panel_1_2_1_1);
		
		JPanel panel_1_6_1_1 = new JPanel();
		panel_1_6_1_1.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		panel_1_6_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_6_1_1.setBackground((Color) null);
		panel_1_6_1_1.setBounds(10, 11, 348, 34);
		panel_1_5_1_1.add(panel_1_6_1_1);
		
		JButton btnNewButton_1_1 = new JButton("Update");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateData();
			}
		});
		btnNewButton_1_1.setForeground(Color.BLACK);
		btnNewButton_1_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_1_1.setBackground(Color.WHITE);
		btnNewButton_1_1.setBounds(807, 626, 150, 34);
		contentPane.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_2 = new JButton("Print");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				print();
			}
		});
		btnNewButton_1_2.setForeground(Color.BLACK);
		btnNewButton_1_2.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_1_2.setBackground(Color.WHITE);
		btnNewButton_1_2.setBounds(647, 626, 150, 34);
		contentPane.add(btnNewButton_1_2);
		
		createPaySlip();
		loadData();
//		basicSalary.setText(LabourSheet.table.getValueAt(LabourSheet.table.getSelectedRow(), 5).toString());
		
		
	}
}
