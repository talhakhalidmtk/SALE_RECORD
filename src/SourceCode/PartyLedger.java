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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PartyLedger extends JFrame {

private JPanel contentPane;
JLabel date;
static JTable invoicesTable;JLabel finalTotal;JLabel totalAmountBill,totalAmountPayment;
private JTable paymentsTable;
private JTextField paymentAmount;
private JTextField paymentDescription;
private JTextField billDescription;
private JTextField billAmount;
private JPanel finalTotalPanel;
Connection con;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PartyLedger frame = new PartyLedger();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public boolean isStringInt(String s)
	{
	    try
	    {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException ex)
	    {
	        return false;
	    }
	}
	
	public void updateTable() {
		try {
			Connection con =  Connect.getConnect();
			
			PreparedStatement s1t = con.prepareStatement("UPDATE PartyTrialBalance SET [Balance]=? WHERE [Name]='"+PartyTrialBalance.getPartyName()+"'");
			s1t.setString(1, finalTotal.getText());

			s1t.executeUpdate();
			
		 }
			catch(Exception e) {
				System.err.print(e);
			} 
		
		
		
	}
	
	
	
	public void setInvoicesTable() {
		
		if(isStringInt(billAmount.getText())){
			DefaultTableModel model = (DefaultTableModel) invoicesTable.getModel();
			String dat = date.getText().substring(date.getText().indexOf(",")+1); 
			model.addRow(new Object[] {dat,billDescription.getText(),billAmount.getText()});
			con = Connect.getConnect();
			
			try {
				String tableName = getTableNameBill();
				String url = "SELECT MAX(Serial) FROM "+tableName;
				PreparedStatement pst = con.prepareStatement(url);
				ResultSet rs= pst.executeQuery();
				while(rs.next()) {
					PreparedStatement create1 = null;
					create1 = con.prepareStatement("INSERT INTO "+tableName+" (Serial, Date ,Description, Amount) VALUES ('"+(rs.getInt(1)+1)+"','"+dat+"','"+billDescription.getText()+"','"+billAmount.getText()+"') \r\n");	
					create1.executeUpdate();
				}
				
				
				}
				
				catch(Exception e) {
					System.err.print(e);
				}
			
			int rows = invoicesTable.getRowCount();
			int total=0;
			for(int i =0; i<rows;i++) {
				total+=Integer.valueOf(invoicesTable.getValueAt(i,2).toString());
			}
			
			totalAmountBill.setText(String.valueOf(total));
			finalTotal.setText(String.valueOf(Integer.valueOf(totalAmountBill.getText())-Integer.valueOf(totalAmountPayment.getText())));
			billDescription.setText("Bill Description");
			billAmount.setText("Amount");
			
			if(Integer.valueOf(finalTotal.getText())>0) {
				finalTotalPanel.setBackground(Color.RED);
			}else if(Integer.valueOf(finalTotal.getText())<0) {
				finalTotalPanel.setBackground(Color.GREEN);
			}
			updateTable();
		}
		else {
			JOptionPane.showMessageDialog(null, "Invalid Entered Amount");
			
		}
		
		
		
		

	} 
	
	public void setPaymentsTable() {
		if(isStringInt(paymentAmount.getText())){
		DefaultTableModel model = (DefaultTableModel) paymentsTable.getModel();
		String dat = date.getText().substring(date.getText().indexOf(",")+1); 
		model.addRow(new Object[] {dat,paymentDescription.getText(),paymentAmount.getText()});
		
		Connection con = Connect.getConnect();
		try {
			
			String tableName = getTableNamePayments();
			String url = "SELECT MAX(Serial) FROM "+tableName;
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();
			while(rs.next()) {
				PreparedStatement create1 = null;
				create1 = con.prepareStatement("INSERT INTO "+tableName+" (Serial, Date ,Description, Amount) VALUES ('"+(rs.getInt(1)+1)+"','"+dat+"','"+paymentDescription.getText()+"','"+paymentAmount.getText()+"') \r\n");	
				create1.executeUpdate();
			}
			}
			
			catch(Exception e) {
				System.err.print(e);
			}
		int payment = 0;
		try {
			String url = "SELECT SUM("+PartyTrialBalance.getPartyCategory()+") FROM PaymentRecord WHERE Date='"+date.getText().toString().replaceAll("-","_").replaceAll(",","_")+"' GROUP BY Date";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			while(rs.next()) {
				payment = Integer.valueOf(rs.getString(1));
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		try {
			PreparedStatement st = con.prepareStatement("UPDATE PaymentRecord SET ["+PartyTrialBalance.getPartyCategory()+"]=? WHERE [Date]='"+date.getText().toString().replaceAll("-","_").replaceAll(",","_")+"'");

			st.setString(1, String.valueOf(Integer.valueOf(paymentAmount.getText())+payment));
			st.executeUpdate();	

			}
			catch(Exception e) {
				System.out.println(e);
				//JOptionPane.showMessageDialog(null, "Update not successfull!!!");
			}
		
		
		
		int rows = paymentsTable.getRowCount();
		int total=0;
		for(int i =0; i<rows;i++) {
			total+=Integer.valueOf(paymentsTable.getValueAt(i,2).toString());
		}
		
		totalAmountPayment.setText(String.valueOf(total));
		finalTotal.setText(String.valueOf(Integer.valueOf(totalAmountBill.getText())-Integer.valueOf(totalAmountPayment.getText())));
		
		if(Integer.valueOf(finalTotal.getText())>0) {
			finalTotalPanel.setBackground(Color.RED);
		}else if(Integer.valueOf(finalTotal.getText())<0) {
			finalTotalPanel.setBackground(Color.GREEN);
		}
		
		updateTable();
		
		paymentDescription.setText("Payment Description");
		paymentAmount.setText("Amount");
		}
		
		else {
			JOptionPane.showMessageDialog(null, "Invalid Entered Amount");
		}
	

	} 
	
	
	public String getTableNameBill() {
		return PartyTrialBalance.getPartyName().replaceAll(" ","")+"BillInvoices";
	}
	public String getTableNamePayments() {
		return PartyTrialBalance.getPartyName().replaceAll(" ","")+"Payments";
	}
	
	
	public void setTwoTables() {
		try {
			DefaultTableModel dtm = (DefaultTableModel) invoicesTable.getModel();
			dtm.setRowCount(0);
			Connection con =  Connect.getConnect();
			String url = "SELECT * FROM "+getTableNameBill()+" ORDER BY Serial";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			DefaultTableModel model = (DefaultTableModel) invoicesTable.getModel();
			while(rs.next()) {
				model.addRow(new Object[] {rs.getString(2),rs.getString(3),rs.getString(4)});	
			}
			
			DefaultTableModel dtm1 = (DefaultTableModel) paymentsTable.getModel();
			dtm1.setRowCount(0);
			Connection con1 =  Connect.getConnect();
			String url1 = "SELECT * FROM "+getTableNamePayments()+" ORDER BY Serial";
			PreparedStatement pst1 = con1.prepareStatement(url1);
			ResultSet rs1= pst1.executeQuery();	
			DefaultTableModel model1 = (DefaultTableModel) paymentsTable.getModel();
			while(rs1.next()) {
				model1.addRow(new Object[] {rs1.getString(2),rs1.getString(3),rs1.getString(4)});	
			}
			
			
			int total=0;
			for(int i =0; i<invoicesTable.getRowCount();i++) {
				total+=Integer.valueOf(invoicesTable.getValueAt(i,2).toString());
			}
			
			totalAmountBill.setText(String.valueOf(total));
			finalTotal.setText(String.valueOf(Integer.valueOf(totalAmountBill.getText())-Integer.valueOf(totalAmountPayment.getText())));
			

			int rows = paymentsTable.getRowCount();
			int total1=0;
			for(int i =0; i<rows;i++) {
				total1+=Integer.valueOf(paymentsTable.getValueAt(i,2).toString());
			}
			
			totalAmountPayment.setText(String.valueOf(total1));
			finalTotal.setText(String.valueOf(Integer.valueOf(totalAmountBill.getText())-Integer.valueOf(totalAmountPayment.getText())));
			
			if(Integer.valueOf(finalTotal.getText())>0) {
				finalTotalPanel.setBackground(Color.RED);
			}else if(Integer.valueOf(finalTotal.getText())<0) {
				finalTotalPanel.setBackground(Color.GREEN);
			}
		}
		catch(Exception e) {
			
		}
	}


	public PartyLedger() {
		
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
		scrollPane.setBounds(10, 156, 660, 395);
		contentPane.add(scrollPane);
		invoicesTable = new JTable();
		invoicesTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					String tableName  = getTableNameBill();
					int j = invoicesTable.getSelectedRow();
					
					try {
						Connection con =  Connect.getConnect();
						PreparedStatement st = con.prepareStatement("UPDATE "+tableName+" SET [Description]=?, [Amount]=? WHERE [Serial]="+(j)+"");

						st.setString(1, invoicesTable.getValueAt(invoicesTable.getSelectedRow(),1).toString());
						st.setString(2, invoicesTable.getValueAt(invoicesTable.getSelectedRow(),2).toString());

						st.executeUpdate();	
						
						int rows = invoicesTable.getRowCount();
						int total=0;
						for(int i =0; i<rows;i++) {
							total+=Integer.valueOf(invoicesTable.getValueAt(i,2).toString());
						}
						
						totalAmountBill.setText(String.valueOf(total));
						finalTotal.setText(String.valueOf(Integer.valueOf(totalAmountBill.getText())-Integer.valueOf(totalAmountPayment.getText())));
						billDescription.setText("Bill Description");
						billAmount.setText("Amount");
						
						if(Integer.valueOf(finalTotal.getText())>0) {
							finalTotalPanel.setBackground(Color.RED);
						}else if(Integer.valueOf(finalTotal.getText())<0) {
							finalTotalPanel.setBackground(Color.GREEN);
						}
						updateTable();
						
						}
						catch(Exception e1) {
							System.out.println(e1);
						}
					
				}
			}
		});

		invoicesTable.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 14));
		
		invoicesTable.setModel(new DefaultTableModel(
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
		});
		invoicesTable.getColumnModel().getColumn(0).setResizable(false);
		invoicesTable.getColumnModel().getColumn(0).setPreferredWidth(15);
		invoicesTable.getColumnModel().getColumn(1).setResizable(false);
		invoicesTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		invoicesTable.getColumnModel().getColumn(2).setResizable(false);
		invoicesTable.getColumnModel().getColumn(2).setPreferredWidth(15);
			invoicesTable.setFont(new Font("Century Gothic", Font.PLAIN, 14));
			//invoicesTable.setTableHeader(null);
			scrollPane.setViewportView(invoicesTable);
			invoicesTable.setRowHeight(23);
			
			
				
			DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
			rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			
			invoicesTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
			invoicesTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
			invoicesTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
				
			UIDefaults defaults = UIManager.getLookAndFeelDefaults();
			defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
			scrollPane.setViewportView(invoicesTable);
		
		
		JLabel lblStudentMart = new JLabel("Student Mart");
		
		Font font = new Font("Times New Roman", Font.BOLD, 30);
		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblStudentMart.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentMart.setFont(new Font("Times New Roman", Font.BOLD, 44));
		lblStudentMart.setBounds(0, 0, 1340, 34);
		lblStudentMart.setFont(font.deriveFont(attributes));
		contentPane.add(lblStudentMart);
		
		
		JLabel lblPartyTrialBalance = new JLabel("Party Ledger");
		lblPartyTrialBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblPartyTrialBalance.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblPartyTrialBalance.setBounds(0, 33, 1340, 23);
		contentPane.add(lblPartyTrialBalance);
		
		JLabel partyName = new JLabel(PartyTrialBalance.getPartyName()+"-"+PartyTrialBalance.getPartyCategory());
		partyName.setHorizontalAlignment(SwingConstants.CENTER);
		partyName.setFont(new Font("Times New Roman", Font.BOLD, 24));
		partyName.setBounds(0, 62, 1340, 23);
		contentPane.add(partyName);
		
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
		panel_1_2_1_5_1_2_2.setBounds(10, 122, 660, 32);
		contentPane.add(panel_1_2_1_5_1_2_2);
		
		JLabel lblNewLabel_2_1_5_1_2_2 = new JLabel("Bill Invoices");
		lblNewLabel_2_1_5_1_2_2.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_2_1_5_1_2_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1_5_1_2_2.setForeground(Color.WHITE);
		lblNewLabel_2_1_5_1_2_2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_2_1_5_1_2_2.setAlignmentX(0.5f);
		lblNewLabel_2_1_5_1_2_2.setBounds(0, 0, 660, 32);
		panel_1_2_1_5_1_2_2.add(lblNewLabel_2_1_5_1_2_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(680, 156, 660, 395);
		contentPane.add(scrollPane_1);
		
		paymentsTable = new JTable();
		paymentsTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					String tableName  = getTableNamePayments();
					int j = paymentsTable.getSelectedRow();

					try {
						

						
						int oldPayment = 0;
						
						Connection con =  Connect.getConnect();
						
						String url = "SELECT [Amount] FROM "+tableName+" WHERE [Serial]="+(j+1)+"";
						PreparedStatement pst = con.prepareStatement(url);
						ResultSet rs= pst.executeQuery();
						while(rs.next()) {
							oldPayment = rs.getInt(1);
						}
						

						
						int modifiedPayment = oldPayment - Integer.valueOf(paymentsTable.getValueAt(paymentsTable.getSelectedRow(),2).toString());
						

						
						PreparedStatement st = con.prepareStatement("UPDATE "+tableName+" SET [Description]=?, [Amount]=? WHERE [Serial]="+(j+1)+"");

						st.setString(1, paymentsTable.getValueAt(paymentsTable.getSelectedRow(),1).toString());
						st.setString(2, paymentsTable.getValueAt(paymentsTable.getSelectedRow(),2).toString());

						st.executeUpdate();	

						
						int payment = 0;
						try {
							String url1 = "SELECT SUM("+PartyTrialBalance.getPartyCategory()+") FROM PaymentRecord WHERE Date='"+date.getText().toString().replaceAll("-","_").replaceAll(",","_")+"' GROUP BY Date";
							PreparedStatement pst1 = con.prepareStatement(url1);
							ResultSet rs1= pst1.executeQuery();	
							while(rs1.next()) {
								payment = Integer.valueOf(rs1.getString(1));
							}
						
							
							
						}
						
						
						catch(Exception e1) {
							System.out.println(e1);
						}
						
						
						
						try {
							PreparedStatement st1 = con.prepareStatement("UPDATE PaymentRecord SET ["+PartyTrialBalance.getPartyCategory()+"]=? WHERE [Date]='"+date.getText().toString().replaceAll("-","_").replaceAll(",","_")+"'");

							
							
							st1.setString(1, String.valueOf(Integer.valueOf(payment-modifiedPayment)));
							st1.executeUpdate();	

							}
							catch(Exception e2) {
								System.out.println(e2);
								//JOptionPane.showMessageDialog(null, "Update not successfull!!!");
							}
						
						
						
						int rows = paymentsTable.getRowCount();
						int total=0;
						for(int i =0; i<rows;i++) {
							total+=Integer.valueOf(paymentsTable.getValueAt(i,2).toString());
						}
						
						totalAmountPayment.setText(String.valueOf(total));
						finalTotal.setText(String.valueOf(Integer.valueOf(totalAmountBill.getText())-Integer.valueOf(totalAmountPayment.getText())));
						
						if(Integer.valueOf(finalTotal.getText())>0) {
							finalTotalPanel.setBackground(Color.RED);
						}else if(Integer.valueOf(finalTotal.getText())<0) {
							finalTotalPanel.setBackground(Color.GREEN);
						}
						
						updateTable();
						
						paymentDescription.setText("Payment Description");
						paymentAmount.setText("Amount");
						
						}
						catch(Exception e1) {
							System.out.println(e1);
						}
					
				}
			}
		});
		paymentsTable.setModel(new DefaultTableModel(
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
		});
		paymentsTable.getColumnModel().getColumn(0).setResizable(false);
		paymentsTable.getColumnModel().getColumn(0).setPreferredWidth(15);
		paymentsTable.getColumnModel().getColumn(1).setResizable(false);
		paymentsTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		paymentsTable.getColumnModel().getColumn(2).setResizable(false);
		paymentsTable.getColumnModel().getColumn(2).setPreferredWidth(15);
		paymentsTable.setRowHeight(23);
		paymentsTable.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		//paymentsTable.setTableHeader(null);
		
		
		paymentsTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		paymentsTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		paymentsTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		
		
		
		
		scrollPane_1.setViewportView(paymentsTable);
		
		paymentAmount = new promptTextField(34,"Amount");
		paymentAmount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					setPaymentsTable();
				}
			}
		});
		paymentAmount.setHorizontalAlignment(SwingConstants.CENTER);
		paymentAmount.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		paymentAmount.setColumns(10);
		paymentAmount.setBounds(new Rectangle(5, 0, 0, 0));
		paymentAmount.setAlignmentX(5.0f);
		paymentAmount.setBounds(1190, 591, 150, 34);
		contentPane.add(paymentAmount);
		paymentAmount.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		
		paymentDescription = new promptTextField(34,"Payment Description");
		paymentDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					setPaymentsTable();
				}
			}
		});
		paymentDescription.setHorizontalAlignment(SwingConstants.CENTER);
		paymentDescription.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		paymentDescription.setColumns(10);
		paymentDescription.setBounds(new Rectangle(5, 0, 0, 0));
		paymentDescription.setAlignmentX(5.0f);
		paymentDescription.setBounds(980, 591, 200, 34);
		contentPane.add(paymentDescription);
		
		billDescription = new promptTextField(34,"Bill Description");
		billDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					setInvoicesTable();
				}
			}
		});
		billDescription.setHorizontalAlignment(SwingConstants.CENTER);
		billDescription.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		billDescription.setColumns(10);
		billDescription.setBounds(new Rectangle(5, 0, 0, 0));
		billDescription.setAlignmentX(5.0f);
		billDescription.setBounds(10, 591, 200, 34);
		contentPane.add(billDescription);
		
		billAmount = new promptTextField(34,"Amount");
		billAmount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					setInvoicesTable();
				}
			}
		});
		billAmount.setHorizontalAlignment(SwingConstants.CENTER);
		billAmount.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		billAmount.setColumns(10);
		billAmount.setBounds(new Rectangle(5, 0, 0, 0));
		billAmount.setAlignmentX(5.0f);
		billAmount.setBounds(220, 591, 150, 34);
		contentPane.add(billAmount);
		
		JLabel dateLabel = new JLabel(" Party Ledger As On: "+date.getText());
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		dateLabel.setBounds(0, 88, 1340, 23);
		contentPane.add(dateLabel);
		
		JPanel panel_1_2_1_5_1_2_2_1 = new JPanel();
		panel_1_2_1_5_1_2_2_1.setLayout(null);
		panel_1_2_1_5_1_2_2_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_2_1_5_1_2_2_1.setBackground(new Color(112, 128, 144));
		panel_1_2_1_5_1_2_2_1.setBounds(680, 122, 660, 32);
		contentPane.add(panel_1_2_1_5_1_2_2_1);
		
		JLabel lblNewLabel_2_1_5_1_2_2_1 = new JLabel("Payments");
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
		
		totalAmountBill = new JLabel("0");
		totalAmountBill.setHorizontalTextPosition(SwingConstants.CENTER);
		totalAmountBill.setHorizontalAlignment(SwingConstants.CENTER);
		totalAmountBill.setForeground(Color.WHITE);
		totalAmountBill.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		totalAmountBill.setAlignmentX(0.5f);
		totalAmountBill.setBounds(0, 0, 161, 28);
		panel_1_2_1_5_1_2_3_2.add(totalAmountBill);
		
		JPanel panel_1_2_1_5_1_2_3_2_1 = new JPanel();
		panel_1_2_1_5_1_2_3_2_1.setLayout(null);
		panel_1_2_1_5_1_2_3_2_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_2_1_5_1_2_3_2_1.setBackground(Color.DARK_GRAY);
		panel_1_2_1_5_1_2_3_2_1.setBounds(1179, 552, 161, 28);
		contentPane.add(panel_1_2_1_5_1_2_3_2_1);
		
		totalAmountPayment = new JLabel("0");
		totalAmountPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		totalAmountPayment.setHorizontalAlignment(SwingConstants.CENTER);
		totalAmountPayment.setForeground(Color.WHITE);
		totalAmountPayment.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		totalAmountPayment.setAlignmentX(0.5f);
		totalAmountPayment.setBounds(0, 0, 161, 28);
		panel_1_2_1_5_1_2_3_2_1.add(totalAmountPayment);
		
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
		
		
		setTwoTables();
		
	}
}
