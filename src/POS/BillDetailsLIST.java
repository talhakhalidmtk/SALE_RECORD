package POS;

import java.awt.Color;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
import SourceCode.DailySaleReport;
import start.Splash;

import javax.swing.JLabel;

import javax.swing.RowFilter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


@SuppressWarnings("serial")
public class BillDetailsLIST extends JFrame {
	
	
	static ArrayList <billView> list = new ArrayList<billView>();

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
	
	
	static billView sampleBill;

	private static JTable table;
	private promptTextField textField;
	
	public static String getBillID() {
		return sampleBill.getID();
	}
	public static String getCustomerName() {
		return sampleBill.getCustomerName();
	}
	public static String getSalePerson() {
		return sampleBill.getSalesPerson();
	}
	public static String getQuantity() {
		return sampleBill.getQuantity();
	}
	public static String getFinalTotal() {
		return sampleBill.getTotalAmount();
	}
	public static String getDiscount() {
		return sampleBill.getDiscount();
	}
	public static String getAdjustment() {
		return sampleBill.getAdjustment();
	}
	public static String getGrandTotal() {
		return sampleBill.getGrandTotal();
	}
	public static String getReceived() {
		return sampleBill.getReceived();
	}
	public static String getChange() {
		return sampleBill.getChange();
	}
	public static String getComments() {
		return sampleBill.getComments();
	}
	public static String getBillType() {
		return sampleBill.getBillType();
	}
	public static String getMember() {
		return sampleBill.getMember();
	}

	
	public static void getTable() {
		
		 try{
			 
			 DefaultTableModel dtm = (DefaultTableModel) table.getModel();
				dtm.setRowCount(0);
				
				Connection con  = Connect.getConnect();
			 	String details = "Select * from BillListDetails WHERE ID NOT IN (SELECT MIN(ID) FROM BillListDetails) ORDER BY ID";
		      PreparedStatement pst = (PreparedStatement) con.prepareStatement(details);
		        ResultSet rs= pst.executeQuery();	        
			while(rs.next()){
				
				String mem = "Not a Member";
				
				if(rs.getString(13).equals("1")) {
					mem = "Member";
				}


						DefaultTableModel tab = (DefaultTableModel) table.getModel();
						 tab.addRow(new Object[] {rs.getString(1),rs.getString(14),rs.getString(2),rs.getString(3),rs.getString(10),
								 rs.getString(4),rs.getString(7),rs.getString(12)});
					
						 list.add(new billView(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(10),
								 rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),mem,rs.getString(12),rs.getString(11)));
			        
				
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
					BillDetailsLIST frame = new BillDetailsLIST();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	public BillDetailsLIST() {

		
ImageIcon img =new ImageIcon(Splash.class.getResource("/SourceCode/edit.png"));
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(BillDetailsLIST.class.getResource("/SourceCode/edit.png")));
					
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
		

		textField = new promptTextField(35, "Search Here...");
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				DefaultTableModel tab = (DefaultTableModel) table.getModel();
				String search = WordUtils.capitalizeFully(textField.getText());
				TableRowSorter <DefaultTableModel> tr = new TableRowSorter <DefaultTableModel>(tab);
				table.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter(search));
			}
		});
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		textField.setBounds(10, 11, 326, 35);
		contentPane.add(textField);
		
UIManager.put("TableHeader.font", new Font("Century Gothic", Font.BOLD, 16));

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 1330, 603);
		contentPane.add(scrollPane);
		
		
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					for(billView bills: list) {
						if (bills.getID().equals(table.getValueAt(table.getSelectedRow(), 0).toString())) {
							sampleBill = bills;
						}
					}
					new BillReviewWindow().setVisible(true);
					
					dispose();
				}
			}
		});
		
		table.setBorder(null);
		table.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID","Date", "Customer Name", "Sales Person", "Quantity", "Total Amount", "Grand Total", "BillType"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class
			};
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false,false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(5);
		table.getColumnModel().getColumn(1).setPreferredWidth(5);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(15);
		table.getColumnModel().getColumn(5).setPreferredWidth(50);
		table.getColumnModel().getColumn(6).setPreferredWidth(15);
		table.getColumnModel().getColumn(7).setPreferredWidth(50);
		
		scrollPane.setViewportView(table);
		

		defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
		table.setRowHeight(23);
		
		UIManager.put("TableHeader.font", new Font("Century Gothic", Font.BOLD, 16));
		
		scrollPane.setViewportView(table);
				table.setRowHeight(23);


				rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				

				
				
				table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
				table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
				table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
				table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
				table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
				table.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);

				
				
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
				
				JButton btnRefresh = new JButton("Refresh");
				btnRefresh.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new BillDetailsLIST().setVisible(true);
						dispose();
					}
				});
				btnRefresh.setFont(new Font("Century Gothic", Font.PLAIN, 16));
				btnRefresh.setBounds(1050, 11, 100, 35);
				contentPane.add(btnRefresh);
				
				getTable();
				
				
				
				table.addComponentListener(new ComponentAdapter() {
				    public void componentResized(ComponentEvent e) {
				    	table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
				    }
				});
		

       
		
		
	}
}


