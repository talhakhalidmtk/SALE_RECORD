package SourceCode;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Extras.promptTextField;
import start.Splash;

import javax.swing.JTextField;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BalanceExpensesLedger extends JFrame {

private JPanel contentPane;
JLabel date;
static JTable collectionTable;JLabel finalTotal;JLabel totalAmountCollection,totalAmountDispersement;
private JTable dispersementTable;
private JTextField disbusmentAmount;
private JTextField disbusmentDescription;
private JTextField collectionDescription;
private JTextField collectionAmount;
private JPanel finalTotalPanel;
Connection con;
String tableNameCollection = null,tableNameDispersement=null;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					BalanceExpensesLedger frame = new BalanceExpensesLedger();
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

	
	
	public BalanceExpensesLedger(String month) {
		
		ImageIcon img =new ImageIcon(Splash.class.getResource("/SourceCode/edit.png"));
		setIconImage(img.getImage());
		
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
		
		//extracted from CommonMethods
		CommonMethods.timeFromCommon(time);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 166, 660, 385);
		contentPane.add(scrollPane);
		collectionTable = new JTable();
		collectionTable.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 14));
		
		collectionTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Date", "Description", "Amount"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Object.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		collectionTable.getColumnModel().getColumn(0).setResizable(false);
		collectionTable.getColumnModel().getColumn(0).setPreferredWidth(15);
		collectionTable.getColumnModel().getColumn(1).setResizable(false);
		collectionTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		collectionTable.getColumnModel().getColumn(2).setResizable(false);
		collectionTable.getColumnModel().getColumn(2).setPreferredWidth(15);
			collectionTable.setFont(new Font("Century Gothic", Font.PLAIN, 14));
			//invoicesTable.setTableHeader(null);
			scrollPane.setViewportView(collectionTable);
			collectionTable.setRowHeight(23);
			
			
				
			DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
			rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			
			collectionTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
			collectionTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
			collectionTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
				
			UIDefaults defaults = UIManager.getLookAndFeelDefaults();
			defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
			scrollPane.setViewportView(collectionTable);
		
		
		JLabel lblStudentMart = new JLabel("Student Mart");
		
		Font font = new Font("Times New Roman", Font.BOLD, 30);
		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblStudentMart.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentMart.setFont(new Font("Times New Roman", Font.BOLD, 44));
		lblStudentMart.setBounds(0, 11, 1340, 34);
		lblStudentMart.setFont(font.deriveFont(attributes));
		contentPane.add(lblStudentMart);
		
		
		JLabel lblPartyTrialBalance = new JLabel("Balance Expenses Ledger");
		lblPartyTrialBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblPartyTrialBalance.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblPartyTrialBalance.setBounds(0, 44, 1340, 23);
		contentPane.add(lblPartyTrialBalance);
		
		JLabel monthName = new JLabel(month);
		monthName.setHorizontalAlignment(SwingConstants.CENTER);
		monthName.setFont(new Font("Times New Roman", Font.BOLD, 24));
		monthName.setBounds(0, 73, 1340, 23);
		contentPane.add(monthName);
		
		finalTotalPanel = new JPanel();
		finalTotalPanel.setLayout(null);
		finalTotalPanel.setBorder(new LineBorder(Color.WHITE));
		finalTotalPanel.setBackground(Color.DARK_GRAY);
		finalTotalPanel.setBounds(577, 591, 221, 34);
		contentPane.add(finalTotalPanel);
		
		 finalTotal = new JLabel("0");
		finalTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		finalTotal.setHorizontalAlignment(SwingConstants.CENTER);
		finalTotal.setForeground(Color.WHITE);
		finalTotal.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 28));
		finalTotal.setAlignmentX(0.5f);
		finalTotal.setBounds(0, 0, 221, 34);
		finalTotalPanel.add(finalTotal);
		
		JPanel panel_1_2_1_5_1_2_2 = new JPanel();
		panel_1_2_1_5_1_2_2.setLayout(null);
		panel_1_2_1_5_1_2_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_2_1_5_1_2_2.setBackground(new Color(112, 128, 144));
		panel_1_2_1_5_1_2_2.setBounds(10, 133, 660, 32);
		contentPane.add(panel_1_2_1_5_1_2_2);
		
		JLabel lblNewLabel_2_1_5_1_2_2 = new JLabel("Collection");
		lblNewLabel_2_1_5_1_2_2.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_2_1_5_1_2_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1_5_1_2_2.setForeground(Color.WHITE);
		lblNewLabel_2_1_5_1_2_2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_2_1_5_1_2_2.setAlignmentX(0.5f);
		lblNewLabel_2_1_5_1_2_2.setBounds(0, 0, 660, 32);
		panel_1_2_1_5_1_2_2.add(lblNewLabel_2_1_5_1_2_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(680, 166, 660, 385);
		contentPane.add(scrollPane_1);
		
		dispersementTable = new JTable();
		dispersementTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Date", "Description", "Amount"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		dispersementTable.getColumnModel().getColumn(0).setResizable(false);
		dispersementTable.getColumnModel().getColumn(0).setPreferredWidth(15);
		dispersementTable.getColumnModel().getColumn(1).setResizable(false);
		dispersementTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		dispersementTable.getColumnModel().getColumn(2).setResizable(false);
		dispersementTable.getColumnModel().getColumn(2).setPreferredWidth(15);
		dispersementTable.setRowHeight(23);
		dispersementTable.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		//paymentsTable.setTableHeader(null);
		scrollPane_1.setViewportView(dispersementTable);
		
		dispersementTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		dispersementTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		dispersementTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		
		disbusmentAmount = new promptTextField(34,"Amount");
		disbusmentAmount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					DefaultTableModel model = (DefaultTableModel) dispersementTable.getModel();
					String dat = date.getText().substring(date.getText().indexOf(",")+1); 
					model.addRow(new Object[] {dat,disbusmentDescription.getText(),disbusmentAmount.getText()});
					
					Connection con = Connect.getConnect();
					try {
						String tableName = tableNameDispersement;
						String url = "SELECT MAX(Serial) FROM "+tableName;
						PreparedStatement pst = con.prepareStatement(url);
						ResultSet rs= pst.executeQuery();
						while(rs.next()) {
							PreparedStatement create1 = null;
							create1 = con.prepareStatement("INSERT INTO "+tableName+" (Serial, Date ,Description, Amount) VALUES ('"+(rs.getInt(1)+1)+"','"+dat+"','"+disbusmentDescription.getText()+"','"+disbusmentAmount.getText()+"') \r\n");	
							create1.executeUpdate();
							}
						}
						
						catch(Exception e1) {
							System.err.print(e1);
						}
					
					
					int rows = dispersementTable.getRowCount();
					int total=0;
					for(int i =0; i<rows;i++) {
						total+=Integer.valueOf(dispersementTable.getValueAt(i,2).toString());
					}
					
					totalAmountDispersement.setText(String.valueOf(total));
					finalTotal.setText(String.valueOf(Integer.valueOf(totalAmountCollection.getText())-Integer.valueOf(totalAmountDispersement.getText())));
					
					if(Integer.valueOf(finalTotal.getText())>0) {
						finalTotalPanel.setBackground(Color.GREEN);
					}else if(Integer.valueOf(finalTotal.getText())<0) {
						finalTotalPanel.setBackground(Color.RED);
					}else {
						finalTotalPanel.setBackground(Color.BLACK);
					}
					
					try {
						
						PreparedStatement s1t = con.prepareStatement("UPDATE ShopExpenses SET [Balance]=? WHERE [Month]='"+month+"'");
						s1t.setString(1, finalTotal.getText());

						s1t.executeUpdate();
						
					 }
						catch(Exception e1) {
							System.err.print(e1);
						} 
					
					disbusmentDescription.setText("Description");
					disbusmentAmount.setText("Amount");
				}
			}
		});
		disbusmentAmount.setHorizontalAlignment(SwingConstants.CENTER);
		disbusmentAmount.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		disbusmentAmount.setColumns(10);
		disbusmentAmount.setBounds(new Rectangle(5, 0, 0, 0));
		disbusmentAmount.setAlignmentX(5.0f);
		disbusmentAmount.setBounds(1190, 591, 150, 34);
		contentPane.add(disbusmentAmount);
		disbusmentAmount.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		
		disbusmentDescription = new promptTextField(34,"Description");
		disbusmentDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					DefaultTableModel model = (DefaultTableModel) dispersementTable.getModel();
					String dat = date.getText().substring(date.getText().indexOf(",")+1); 
					model.addRow(new Object[] {dat,disbusmentDescription.getText(),disbusmentAmount.getText()});
					
					Connection con = Connect.getConnect();
					try {
						String tableName = tableNameDispersement;
						String url = "SELECT MAX(Serial) FROM "+tableName;
						PreparedStatement pst = con.prepareStatement(url);
						ResultSet rs= pst.executeQuery();
						while(rs.next()) {
							PreparedStatement create1 = null;
							create1 = con.prepareStatement("INSERT INTO "+tableName+" (Serial, Date ,Description, Amount) VALUES ('"+(rs.getInt(1)+1)+"','"+dat+"','"+disbusmentDescription.getText()+"','"+disbusmentAmount.getText()+"') \r\n");	
							create1.executeUpdate();
							}
						}
						
						catch(Exception e1) {
							System.err.print(e1);
						}
					
					
					int rows = dispersementTable.getRowCount();
					int total=0;
					for(int i =0; i<rows;i++) {
						total+=Integer.valueOf(dispersementTable.getValueAt(i,2).toString());
					}
					
					totalAmountDispersement.setText(String.valueOf(total));
					finalTotal.setText(String.valueOf(Integer.valueOf(totalAmountCollection.getText())-Integer.valueOf(totalAmountDispersement.getText())));
					
					if(Integer.valueOf(finalTotal.getText())>0) {
						finalTotalPanel.setBackground(Color.GREEN);
					}else if(Integer.valueOf(finalTotal.getText())<0) {
						finalTotalPanel.setBackground(Color.RED);
					}else {
						finalTotalPanel.setBackground(Color.BLACK);
					}
					
					try {
						
						PreparedStatement s1t = con.prepareStatement("UPDATE ShopExpenses SET [Balance]=? WHERE [Month]='"+month+"'");
						s1t.setString(1, finalTotal.getText());

						s1t.executeUpdate();
						
					 }
						catch(Exception e1) {
							System.err.print(e1);
						} 
					

					disbusmentDescription.setText("Description");
					disbusmentAmount.setText("Amount");
					
					
				}
			}
		});
		disbusmentDescription.setHorizontalAlignment(SwingConstants.CENTER);
		disbusmentDescription.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		disbusmentDescription.setColumns(10);
		disbusmentDescription.setBounds(new Rectangle(5, 0, 0, 0));
		disbusmentDescription.setAlignmentX(5.0f);
		disbusmentDescription.setBounds(980, 591, 200, 34);
		contentPane.add(disbusmentDescription);
		
		collectionDescription = new promptTextField(34,"Description");
		collectionDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					DefaultTableModel model = (DefaultTableModel) collectionTable.getModel();
					String dat = date.getText().substring(date.getText().indexOf(",")+1); 
					model.addRow(new Object[] {dat,collectionDescription.getText(),collectionAmount.getText()});
					con = Connect.getConnect();
					
					try {
						String tableName = tableNameCollection;
						String url = "SELECT MAX(Serial) FROM "+tableName;
						PreparedStatement pst = con.prepareStatement(url);
						ResultSet rs= pst.executeQuery();
						while(rs.next()) {
							PreparedStatement create1 = null;
							create1 = con.prepareStatement("INSERT INTO "+tableName+" (Serial, Date ,Description, Amount) VALUES ('"+(rs.getInt(1)+1)+"','"+dat+"','"+collectionDescription.getText()+"','"+collectionAmount.getText()+"') \r\n");	
							create1.executeUpdate();
							}

						}
						
						catch(Exception e1) {
							System.err.print(e1);
						}
					
					int rows = collectionTable.getRowCount();
					int total=0;
					for(int i =0; i<rows;i++) {
						total+=Integer.valueOf(collectionTable.getValueAt(i,2).toString());
					}
					
					DailySaleReport.setTable(DailySaleReport.table,collectionAmount,6);
					
					totalAmountCollection.setText(String.valueOf(total));
					finalTotal.setText(String.valueOf(Integer.valueOf(totalAmountCollection.getText())-Integer.valueOf(totalAmountDispersement.getText())));
					collectionDescription.setText("Description");
					collectionAmount.setText("Amount");
					
					if(Integer.valueOf(finalTotal.getText())>0) {
						finalTotalPanel.setBackground(Color.GREEN);
					}else if(Integer.valueOf(finalTotal.getText())<0) {
						finalTotalPanel.setBackground(Color.RED);
					}else {
						finalTotalPanel.setBackground(Color.BLACK);
					}
					
					try {
						Connection con =  Connect.getConnect();
						
						PreparedStatement s1t = con.prepareStatement("UPDATE ShopExpenses SET [Balance]=? WHERE [Month]='"+month+"'");
						s1t.setString(1, finalTotal.getText());

						s1t.executeUpdate();
						
					 }
						catch(Exception e1) {
							System.err.print(e1);
						} 
					
					
				}
			}
		});
		collectionDescription.setHorizontalAlignment(SwingConstants.CENTER);
		collectionDescription.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		collectionDescription.setColumns(10);
		collectionDescription.setBounds(new Rectangle(5, 0, 0, 0));
		collectionDescription.setAlignmentX(5.0f);
		collectionDescription.setBounds(10, 591, 200, 34);
		contentPane.add(collectionDescription);
		
		collectionAmount = new promptTextField(34,"Amount");
		collectionAmount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					DefaultTableModel model = (DefaultTableModel) collectionTable.getModel();
					String dat = date.getText().substring(date.getText().indexOf(",")+1); 
					model.addRow(new Object[] {dat,collectionDescription.getText(),collectionAmount.getText()});
					con = Connect.getConnect();
					
					try {
						String tableName = tableNameCollection;
						String url = "SELECT MAX(Serial) FROM "+tableName;
						PreparedStatement pst = con.prepareStatement(url);
						ResultSet rs= pst.executeQuery();
						while(rs.next()) {
							PreparedStatement create1 = null;
							create1 = con.prepareStatement("INSERT INTO "+tableName+" (Serial, Date ,Description, Amount) VALUES ('"+(rs.getInt(1)+1)+"','"+dat+"','"+collectionDescription.getText()+"','"+collectionAmount.getText()+"') \r\n");	
							create1.executeUpdate();
							}

						}
						
						catch(Exception e1) {
							System.err.print(e1);
						}
					
					int rows = collectionTable.getRowCount();
					int total=0;
					for(int i =0; i<rows;i++) {
						total+=Integer.valueOf(collectionTable.getValueAt(i,2).toString());
					}
					
					totalAmountCollection.setText(String.valueOf(total));
					finalTotal.setText(String.valueOf(Integer.valueOf(totalAmountCollection.getText())-Integer.valueOf(totalAmountDispersement.getText())));
					
					DailySaleReport.setTable(DailySaleReport.table,collectionAmount,6);
					
					collectionDescription.setText("Description");
					collectionAmount.setText("Amount");
					
					if(Integer.valueOf(finalTotal.getText())>0) {
						finalTotalPanel.setBackground(Color.GREEN);
					}else if(Integer.valueOf(finalTotal.getText())<0) {
						finalTotalPanel.setBackground(Color.RED);
					}else {
						finalTotalPanel.setBackground(Color.BLACK);
					}
					
					try {
						Connection con =  Connect.getConnect();
						
						PreparedStatement s1t = con.prepareStatement("UPDATE ShopExpenses SET [Balance]=? WHERE [Month]='"+month+"'");
						s1t.setString(1, finalTotal.getText());

						s1t.executeUpdate();
						
					 }
						catch(Exception e1) {
							System.err.print(e1);
						} 
					
					
				}
			}
		});
		collectionAmount.setHorizontalAlignment(SwingConstants.CENTER);
		collectionAmount.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		collectionAmount.setColumns(10);
		collectionAmount.setBounds(new Rectangle(5, 0, 0, 0));
		collectionAmount.setAlignmentX(5.0f);
		collectionAmount.setBounds(220, 591, 150, 34);
		contentPane.add(collectionAmount);
		
		JLabel dateLabel = new JLabel();
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		dateLabel.setBounds(0, 99, 1340, 23);
		dateLabel.setText(" Balance Expenses Ledger As On: "+date.getText());
		contentPane.add(dateLabel);
		
		JPanel panel_1_2_1_5_1_2_2_1 = new JPanel();
		panel_1_2_1_5_1_2_2_1.setLayout(null);
		panel_1_2_1_5_1_2_2_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_2_1_5_1_2_2_1.setBackground(new Color(112, 128, 144));
		panel_1_2_1_5_1_2_2_1.setBounds(680, 133, 660, 32);
		contentPane.add(panel_1_2_1_5_1_2_2_1);
		
		JLabel lblNewLabel_2_1_5_1_2_2_1 = new JLabel("Disbursement");
		lblNewLabel_2_1_5_1_2_2_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_2_1_5_1_2_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1_5_1_2_2_1.setForeground(Color.WHITE);
		lblNewLabel_2_1_5_1_2_2_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_2_1_5_1_2_2_1.setAlignmentX(0.5f);
		lblNewLabel_2_1_5_1_2_2_1.setBounds(0, 0, 660, 32);
		panel_1_2_1_5_1_2_2_1.add(lblNewLabel_2_1_5_1_2_2_1);
		
		JPanel panel_1_2_1_5_1_2_3_2 = new JPanel();
		panel_1_2_1_5_1_2_3_2.setLayout(null);
		panel_1_2_1_5_1_2_3_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_2_1_5_1_2_3_2.setBackground(Color.DARK_GRAY);
		panel_1_2_1_5_1_2_3_2.setBounds(509, 552, 161, 28);
		contentPane.add(panel_1_2_1_5_1_2_3_2);
		
		totalAmountCollection = new JLabel("0");
		totalAmountCollection.setHorizontalTextPosition(SwingConstants.CENTER);
		totalAmountCollection.setHorizontalAlignment(SwingConstants.CENTER);
		totalAmountCollection.setForeground(Color.WHITE);
		totalAmountCollection.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		totalAmountCollection.setAlignmentX(0.5f);
		totalAmountCollection.setBounds(0, 0, 161, 28);
		panel_1_2_1_5_1_2_3_2.add(totalAmountCollection);
		
		JPanel panel_1_2_1_5_1_2_3_2_1 = new JPanel();
		panel_1_2_1_5_1_2_3_2_1.setLayout(null);
		panel_1_2_1_5_1_2_3_2_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_2_1_5_1_2_3_2_1.setBackground(Color.DARK_GRAY);
		panel_1_2_1_5_1_2_3_2_1.setBounds(1179, 552, 161, 28);
		contentPane.add(panel_1_2_1_5_1_2_3_2_1);
		
		totalAmountDispersement = new JLabel("0");
		totalAmountDispersement.setHorizontalTextPosition(SwingConstants.CENTER);
		totalAmountDispersement.setHorizontalAlignment(SwingConstants.CENTER);
		totalAmountDispersement.setForeground(Color.WHITE);
		totalAmountDispersement.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		totalAmountDispersement.setAlignmentX(0.5f);
		totalAmountDispersement.setBounds(0, 0, 161, 28);
		panel_1_2_1_5_1_2_3_2_1.add(totalAmountDispersement);
		
		JButton btnNewButton = new JButton("Monthly Record (F2)");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MonthlySaleRecord().setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton.setBounds(289, 636, 213, 34);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Shop Expenses(F5)");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ShopExpenses().setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setBounds(1127, 636, 213, 34);
		contentPane.add(btnNewButton_1);
		
		JButton btnAnnualSaleRecord = new JButton("Annual Record (F3)");
		btnAnnualSaleRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new YearlyRecord().setVisible(true);
				dispose();
			}
		});
		btnAnnualSaleRecord.setForeground(Color.BLACK);
		btnAnnualSaleRecord.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnAnnualSaleRecord.setBackground(Color.WHITE);
		btnAnnualSaleRecord.setBounds(568, 636, 214, 34);
		contentPane.add(btnAnnualSaleRecord);
		
		JButton btnNewButton_2_1 = new JButton("Party Trial Balance (F4)");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PartyTrialBalance().setVisible(true);
				dispose();
			}
		});
		btnNewButton_2_1.setForeground(Color.BLACK);
		btnNewButton_2_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_2_1.setBackground(Color.WHITE);
		btnNewButton_2_1.setBounds(848, 636, 213, 34);
		contentPane.add(btnNewButton_2_1);
		
		JButton btnDailyRecordf = new JButton("Daily Record (F1)");
		btnDailyRecordf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DailySaleReport().setVisible(true);
				dispose();
			}
		});
		btnDailyRecordf.setForeground(Color.BLACK);
		btnDailyRecordf.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnDailyRecordf.setBackground(Color.WHITE);
		btnDailyRecordf.setBounds(10, 636, 213, 34);
		contentPane.add(btnDailyRecordf);
		
		
		

			try {				
				tableNameCollection = month+"Collection";
				
				tableNameDispersement = month+"Dispersement";
				 
				con  = Connect.getConnect();
				
				PreparedStatement create4 = con.prepareStatement("IF OBJECT_ID(N'dbo."+tableNameCollection+"', N'U') IS NULL BEGIN   \r\n"
						+ "CREATE TABLE "+tableNameCollection +" \r\n"
						+ "(Serial int ,Date VARCHAR(100), Description VARCHAR(100), Amount int)\r\n"
						+ "END;");
				create4.executeUpdate();
				
				PreparedStatement create2 = con.prepareStatement("IF OBJECT_ID(N'dbo."+tableNameDispersement+"', N'U') IS NULL BEGIN   \r\n"
						+ "CREATE TABLE "+tableNameDispersement +" \r\n"
						+ "(Serial int ,Date VARCHAR(100), Description VARCHAR(100), Amount int)\r\n"
						+ "END;");
				create2.executeUpdate();

			}
				catch(Exception e) {
					System.err.print(e);
				} 
	
			try {
				DefaultTableModel dtm = (DefaultTableModel) collectionTable.getModel();
				dtm.setRowCount(0);
				Connection con =  Connect.getConnect();
				String url = "SELECT * FROM "+tableNameCollection+" ORDER BY Serial";
				PreparedStatement pst = con.prepareStatement(url);
				ResultSet rs= pst.executeQuery();	
				DefaultTableModel model = (DefaultTableModel) collectionTable.getModel();
				while(rs.next()) {
					model.addRow(new Object[] {rs.getString(2),rs.getString(3),rs.getString(4)});	
				}
				
				DefaultTableModel dtm1 = (DefaultTableModel) dispersementTable.getModel();
				dtm1.setRowCount(0);
				Connection con1 =  Connect.getConnect();
				String url1 = "SELECT * FROM "+tableNameDispersement+" ORDER BY Serial";
				PreparedStatement pst1 = con1.prepareStatement(url1);
				ResultSet rs1= pst1.executeQuery();	
				DefaultTableModel model1 = (DefaultTableModel) dispersementTable.getModel();
				while(rs1.next()) {
					model1.addRow(new Object[] {rs1.getString(2),rs1.getString(3),rs1.getString(4)});	
				}
				
				
				int total=0;
				for(int i =0; i<collectionTable.getRowCount();i++) {
					total+=Integer.valueOf(collectionTable.getValueAt(i,2).toString());
				}
				
				totalAmountCollection.setText(String.valueOf(total));
				finalTotal.setText(String.valueOf(Integer.valueOf(totalAmountCollection.getText())-Integer.valueOf(totalAmountDispersement.getText())));
				

				int rows = dispersementTable.getRowCount();
				int total1=0;
				for(int i =0; i<rows;i++) {
					total1+=Integer.valueOf(dispersementTable.getValueAt(i,2).toString());
				}
				
				totalAmountDispersement.setText(String.valueOf(total1));
				finalTotal.setText(String.valueOf(Integer.valueOf(totalAmountCollection.getText())-Integer.valueOf(totalAmountDispersement.getText())));
				
				if(Integer.valueOf(finalTotal.getText())>0) {
					finalTotalPanel.setBackground(Color.GREEN);
				}else if(Integer.valueOf(finalTotal.getText())<0) {
					finalTotalPanel.setBackground(Color.RED);
				}else {
					finalTotalPanel.setBackground(Color.BLACK);
				}
			}
			catch(Exception e) {
				
			}
			
			
			
			
			
	}
	
	
}
