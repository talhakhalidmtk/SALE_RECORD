package Customer;

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


@SuppressWarnings("serial")
public class CustomerPortfolio extends JFrame {

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

	private static JTable table;

	
	public static void getTable() {
		
		 try{
			 
			 DefaultTableModel dtm = (DefaultTableModel) table.getModel();
				dtm.setRowCount(0);
				
				Connection con  = Connect.getConnect();
			 	String details = "Select ID,Name,Number,Address,Discount,Nature,AccountBalance from CustomerRecord";
		      PreparedStatement pst = (PreparedStatement) con.prepareStatement(details);
		        ResultSet rs= pst.executeQuery();	        
			while(rs.next()){
				DefaultTableModel tab = (DefaultTableModel) table.getModel();
				 tab.addRow(new Object[] {"0",rs.getString(1),WordUtils.capitalizeFully(rs.getString(2)),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)});
			 }
			 con.close();	
				for(int i = 0 ;i<table.getRowCount();i++) {
					DefaultTableModel tab = (DefaultTableModel) table.getModel();
					tab.setValueAt(String.valueOf(i+1), i, 0);
				}
	 }
		 catch(Exception e){
			 System.out.println(e);
		
	       }
	}
	
	public static void main(String[] args) {
		
	

	
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerPortfolio frame = new CustomerPortfolio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	public CustomerPortfolio() {

		try {
            //here you can put the selected theme class name in JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
            

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CustomerPortfolio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerPortfolio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerPortfolio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerPortfolio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(CustomerPortfolio.class.getResource("/SourceCode/edit.png")));
					
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
		
		promptTextField txtProdId_2 = new promptTextField(35, "Customer Search Here");
		txtProdId_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				DefaultTableModel tab = (DefaultTableModel) table.getModel();
				String search = WordUtils.capitalizeFully(txtProdId_2.getText());
				TableRowSorter <DefaultTableModel> tr = new TableRowSorter <DefaultTableModel>(tab);
				table.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter(search));
				
			}
		});
		txtProdId_2.setHorizontalAlignment(SwingConstants.CENTER);
		txtProdId_2.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		txtProdId_2.setBounds(10, 11, 300, 35);
		contentPane.add(txtProdId_2);
		
UIManager.put("TableHeader.font", new Font("Century Gothic", Font.BOLD, 16));

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddCustomer().setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		btnNewButton.setBounds(320, 11, 100, 35);
		contentPane.add(btnNewButton);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

try{
					

		            final int row = table.getSelectedRow();

		            final String valueInCell = (String)table.getValueAt(row, 1);

				 	Connection con  = Connect.getConnect();
				 	String sql = "DELETE FROM CustomerRecord WHERE ID=?";
				 	PreparedStatement statement = con.prepareStatement(sql);
				 	statement.setString(1, valueInCell);
				 	statement.executeUpdate();
				 con.close();	
				 
				 DefaultTableModel tab = (DefaultTableModel) table.getModel();
				    int[] selectedRows = table.getSelectedRows();
			        if (selectedRows.length > 0) {
			            for (int i = selectedRows.length - 1; i >= 0; i--) {
			            	tab.removeRow(selectedRows[i]);
			            }
			        }
				    
				 
				JOptionPane.showMessageDialog(null, "Deleted Successfully.");
	
		 }
			 catch(Exception e1){
		       }
				
			}
			}
		);
		btnDelete.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		btnDelete.setBounds(430, 11, 100, 35);
		contentPane.add(btnDelete);
		
		JButton refresh = new JButton("Refresh");
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CustomerPortfolio().setVisible(true);
				dispose();
			}
		});
		refresh.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		refresh.setBounds(540, 11, 100, 35);
		contentPane.add(refresh);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 1330, 603);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2 && table.getSelectedColumn()==2) {
					new CustomerLedger().setVisible(true);
					dispose();
				}
			}
		});
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
						{
						try {
							
							int i = Integer.valueOf(table.getValueAt(table.getSelectedRow(), 1).toString());
							
							Connection con =  Connect.getConnect();
							PreparedStatement st = con.prepareStatement("UPDATE CustomerRecord SET [Number]=?, [Address]=?, [Discount]=?, [Nature]=?, [AccountBalance]=? WHERE [ID]="+(i)+"");
							
							
							
							for(int j =0; j<table.getColumnCount();j++) {
								if(table.getValueAt(table.getSelectedRow(), j)==null) {
									table.setValueAt("0", table.getSelectedRow(), j);
								}
							}
							
							String num = table.getValueAt(table.getSelectedRow(),3).toString();
							if(table.getValueAt(table.getSelectedRow(),3)==null) {
								num = "0";
							}
							String addr =  table.getValueAt(table.getSelectedRow(),4).toString().toUpperCase();
							if(table.getValueAt(table.getSelectedRow(),4)==null) {
								addr = "0";
							}
							String dis = table.getValueAt(table.getSelectedRow(),5).toString();
							if(table.getValueAt(table.getSelectedRow(),5)==null) {dis =  "0";}
							String nat =  table.getValueAt(table.getSelectedRow(),6).toString().toUpperCase();
							if(table.getValueAt(table.getSelectedRow(),6)==null) {
								nat = "0";
							}
							String acc = table.getValueAt(table.getSelectedRow(),7).toString();
							if(table.getValueAt(table.getSelectedRow(),7)==null) {acc =  "0";}

							st.setString(1, num);
							st.setString(2, addr);
							st.setString(3, dis);
							st.setString(4, nat);
							st.setString(5, acc);
							st.executeUpdate();	
							
							}
							catch(Exception e1) {
								System.out.println(e1);
							}
					}
					
				}
				
			}
		});
		table.setBorder(null);
		table.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Sr. #", "Customer Id", "Customer Name", "Contact Number", "Address", "Discount", "Customer Nature", "Account Balance"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, Object.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, true, true, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(15);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(5).setPreferredWidth(15);
		table.getColumnModel().getColumn(6).setResizable(false);
		table.getColumnModel().getColumn(6).setPreferredWidth(100);
		table.getColumnModel().getColumn(7).setResizable(false);
		table.getColumnModel().getColumn(7).setPreferredWidth(100);
		scrollPane.setViewportView(table);
		

		defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
		table.setRowHeight(23);

		table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
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
				getTable();

	}

	
	public static String getCustomerID() {
		return table.getValueAt(table.getSelectedRow(), 1).toString();
	}
	
	public static String getCustomerName() {
		return table.getValueAt(table.getSelectedRow(), 2).toString();
	}
	
}


