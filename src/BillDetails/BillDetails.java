package BillDetails;

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
import javax.swing.table.TableRowSorter;

import org.apache.commons.lang.WordUtils;

import Extras.*;
import SourceCode.CommonMethods;
import POS.Connect;
import POS.MainSaleWindow;
import SourceCode.DailySaleReport;
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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import java.awt.Button;
import java.awt.Label;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;


@SuppressWarnings("serial")
public class BillDetails extends JFrame {

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
	

	static JTable table;
	private promptTextField searchBar;
	private JScrollPane dateList;
	private JTable dateTable;
	private promptTextField dateSearch;

	
	
	
	public void setDateTable() {
		try{
			 
			 DefaultTableModel dtm = (DefaultTableModel) dateTable.getModel();
				dtm.setRowCount(0);
				
				Connection con  = Connect.getConnect();
			 	String details = "Select DISTINCT Date from TotalBills ORDER BY Date DESC";
		      PreparedStatement pst = (PreparedStatement) con.prepareStatement(details);
		        ResultSet rs= pst.executeQuery();	        
			while(rs.next()){
				DefaultTableModel tab = (DefaultTableModel) dateTable.getModel();
				 tab.addRow(new Object[] {rs.getString(1)});
			 }
			 con.close();	
				
	 }
		 catch(Exception e){
			 System.out.println(e);
		
	       }
	}
	
	
	public void setMainTable(String Date) {
		try{
			 
			 DefaultTableModel dtm = (DefaultTableModel) table.getModel();
				dtm.setRowCount(0);
				
				Connection con  = Connect.getConnect();
			 	String details = "Select ProductCode, ProductTitle, Category, Quantity, PerUnit from TotalBills WHERE Date='"+dateSearch.getText()+"' "
			 			;
		      PreparedStatement pst = (PreparedStatement) con.prepareStatement(details);
		        ResultSet rs= pst.executeQuery();	        
			while(rs.next()){
				DefaultTableModel tab = (DefaultTableModel) table.getModel();
				 tab.addRow(new Object[] {(table.getRowCount()+1),rs.getString(1),rs.getString(2),rs.getString(4)
						 ,rs.getString(5),
						 String.valueOf(Integer.valueOf(rs.getString(4).toString())*Double.valueOf(rs.getString(5).toString()))});
			 }
			 con.close();	
				
	 }
		 catch(Exception e){
			 System.out.println(e);
		
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
					BillDetails frame = new BillDetails();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	public BillDetails() {
		

		
		

		
ImageIcon img =new ImageIcon(Splash.class.getResource("/SourceCode/edit.png"));
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(BillDetails.class.getResource("/SourceCode/edit.png")));
					
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
		
		dateList = new JScrollPane();
		dateList.setBounds(10, 46, 187, 148);
		contentPane.add(dateList);
		
		dateList.setVisible(false);
		dateList.disable();
		
		dateTable = new JTable();
		dateTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dateSearch.setText(dateTable.getValueAt(dateTable.getSelectedRow(), 0).toString());
				setMainTable(dateSearch.getText());
				
				searchBar.requestFocus();
				
				dateList.setVisible(false);
				dateList.disable();
			}
		});
		dateTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Date"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		dateTable.getColumnModel().getColumn(0).setResizable(false);
		dateTable.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		
		
		
		dateTable.setTableHeader(null);
		dateList.setViewportView(dateTable);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.BLACK);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLayout(null);
		panel.setBounds(10, 671, 1330, 24);
		contentPane.add(panel);
		Date d= new Date();
		SimpleDateFormat a = new SimpleDateFormat("EEEE,dd-MMMM-yyyy");
		
		JLabel date_1 = new JLabel("");
		date_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().open(new File("D:\\POSTalhaKhalid\\ReceiptSample\\"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		date_1.setHorizontalAlignment(SwingConstants.LEFT);
		date_1.setForeground(Color.BLACK);
		date_1.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		date_1.setBounds(0, 0, 997, 24);
		panel.add(date_1);

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
		
		searchBar = new promptTextField(35, "Search Product...");
		searchBar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				DefaultTableModel tab = (DefaultTableModel) table.getModel();
				String search = WordUtils.capitalizeFully(searchBar.getText());
				TableRowSorter <DefaultTableModel> tr = new TableRowSorter <DefaultTableModel>(tab);
				table.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter(search));
				
			}
		});
		searchBar.setHorizontalAlignment(SwingConstants.CENTER);
		searchBar.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		searchBar.setBounds(207, 11, 418, 35);
		contentPane.add(searchBar);
		
UIManager.put("TableHeader.font", new Font("Century Gothic", Font.BOLD, 16));

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
		
		
		
		JButton refresh = new JButton("Refresh");
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new BillDetails().setVisible(true);
				dispose();
			}
		});
		refresh.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		refresh.setBounds(1030, 11, 120, 35);
		contentPane.add(refresh);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 1330, 603);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					
				}
				
			}
		});
		table.setBorder(null);
		table.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Sr. #", "Product ID", "Product Title", "Quantity","Per Unit", "Total"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class,String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false,false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		dateTable.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(1);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(15);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(15);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(4).setPreferredWidth(15);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(5).setPreferredWidth(15);
		
		scrollPane.setViewportView(table);
		

		defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
		table.setRowHeight(23);

		table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		
		UIManager.put("TableHeader.font", new Font("Century Gothic", Font.BOLD, 16));
		
		scrollPane.setViewportView(table);
				table.setRowHeight(23);


				rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				
				defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
				scrollPane.setViewportView(table);
				
				JButton btnMainSaleWindow = new JButton("Main Sale Window");
				btnMainSaleWindow.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new MainSaleWindow().setVisible(true);
						dispose();
					}
				});
				btnMainSaleWindow.setFont(new Font("Century Gothic", Font.PLAIN, 16));
				btnMainSaleWindow.setBounds(1160, 11, 180, 35);
				contentPane.add(btnMainSaleWindow);
				
				
				

				//extracted from CommonMethods
				CommonMethods.timeFromCommon(time);
				
				dateSearch = new promptTextField(35, "Select Date");
				dateSearch.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						dateList.setVisible(true);
						dateList.enable();
					}
				});
				dateSearch.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						
						if(e.getKeyCode()==10) {
							
							dateSearch.setText(dateTable.getValueAt(0, 0).toString());
							setMainTable(dateSearch.getText());
							
							dateList.setVisible(false);
							dateList.disable();
									
							}
						
						
							
						if(e.getKeyCode()==KeyEvent.VK_DOWN) {
							dateTable.requestFocus();
							dateTable.changeSelection(0, 0, false, false);
						}
					}
				});
				dateSearch.setFont(new Font("Century Gothic", Font.PLAIN, 16));
				dateSearch.setHorizontalAlignment(SwingConstants.CENTER);
				dateSearch.setBounds(10, 11, 187, 35);
				contentPane.add(dateSearch);
				
				JButton btnPrint = new JButton("Download");
				btnPrint.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						 try{
							 int count=table.getRowCount();
							 Document document=new Document();
							        PdfWriter.getInstance(document,new FileOutputStream("D:\\POSTalhaKhalid\\ReceiptSample\\"+dateSearch.getText()+".pdf"));
							        document.open();
							        PdfPTable tab=new PdfPTable(6);
							        tab.addCell("Serial #");
							        tab.addCell("Product ID");
							        tab.addCell("Product Title");
							        tab.addCell("Quantity");
							        tab.addCell("Per Unit");
							        tab.addCell("Total");
							 for(int i=0;i<count;i++){
								 Object obj1 = table.getModel().getValueAt(i,0);
								 Object obj2 =  table.getModel().getValueAt(i,1);
								 Object obj3 = table.getModel().getValueAt(i,2);
								 Object obj4 =  table.getModel().getValueAt(i,3);
								 Object obj5 = table.getModel().getValueAt(i,4);
								 Object obj6 =  table.getModel().getValueAt(i,5);
								 String value1=obj1.toString();
								 String value2=obj2.toString();
								 String value3=obj3.toString();
								 String value4=obj4.toString();
								 String value5=obj5.toString();
								 String value6=obj6.toString();
								 
	
								 tab.addCell(value1);
								 tab.addCell(value2);
								 tab.addCell(value3);
								 tab.addCell(value4);
								 tab.addCell(value5);
								 tab.addCell(value6);
							 }
							 document.add(tab);
							 document.close();
							 
							 date_1.setText("File Genrated: D:\\POSTalhaKhalid\\ReceiptSample\\"+dateSearch.getText()+".pdf");
							     }
							     catch(Exception e1){
							    	 System.out.println(e1);
							     }
					}
				});
				btnPrint.setFont(new Font("Century Gothic", Font.PLAIN, 16));
				btnPrint.setBounds(635, 11, 120, 35);
				contentPane.add(btnPrint);
				
		
				searchBar.requestFocusInWindow();
				
				setDateTable();
				
       
				
		
		
	}
}


