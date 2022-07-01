package SourceCode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Extras.RoundedPanel;
import Extras.promptTextField;
import start.Splash;

import javax.swing.JTextField;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PartyTrialBalanceEssentials extends JFrame {

	private JPanel contentPane;
	private JTextField partyName;
	private JTextField address;
	private JTextField contactNumber;
	private JTextField accountNumber;
	JComboBox<String> category ;
	private JTextField iban;
	private JTextField openingBalance;
	Connection con;
	JLabel status,date;

	/**
	 * Launch the application.
	 */
	
	public String getTableName() {
		return "PartyTrialBalance";
	}
	
	public void createTables() {
		try {
			
			String tableName = getTableName();
			 
			con  = Connect.getConnect();
			
			PreparedStatement create1 = con.prepareStatement("IF OBJECT_ID(N'dbo."+tableName+"', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE "+tableName +" \r\n"
					+ "(Name VARCHAR(100), Address VARCHAR(100), ContactNumber VARCHAR(100) ,Balance VARCHAR(100) ,AccountNumber VARCHAR(100) ,IBAN VARCHAR(100),Category VARCHAR(100))\r\n"
					+ "END;");
			create1.executeUpdate();

			}
			
			catch(Exception e) {
				System.err.print(e);
			} 
	}
	
	public void insertTable() {
		try {
			String tableName = getTableName();
			PreparedStatement create1 = null;
			create1 = con.prepareStatement("INSERT INTO "+tableName+" (Name , Address,ContactNumber,Balance,AccountNumber,IBAN,Category) VALUES ('"+partyName.getText()+"','"+address.getText()+"','"+contactNumber.getText()+"','"+openingBalance.getText()+"','"+accountNumber.getText()+"','"+iban.getText()+"','"+category.getSelectedItem().toString()+"') \r\n");	
			create1.executeUpdate();
			
			String tableNameBill = partyName.getText().replaceAll(" ","")+"BillInvoices";
			
			String tableNamePayment = partyName.getText().replaceAll(" ","")+"Payments";
			 
			con  = Connect.getConnect();
			
			PreparedStatement create4 = con.prepareStatement("IF OBJECT_ID(N'dbo."+tableNameBill+"', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE "+tableNameBill +" \r\n"
					+ "(Serial int, Date VARCHAR(100), Description VARCHAR(100), Amount VARCHAR(100))\r\n"
					+ "END;");
			create4.executeUpdate();
			
			PreparedStatement create2 = con.prepareStatement("IF OBJECT_ID(N'dbo."+tableNamePayment+"', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE "+tableNamePayment +" \r\n"
					+ "(Serial int, Date VARCHAR(100), Description VARCHAR(100), Amount VARCHAR(100))\r\n"
					+ "END;");
			create2.executeUpdate();
			
			PreparedStatement create = null;
			create = con.prepareStatement("INSERT INTO "+tableNameBill+" (Serial, Date , Description,Amount) VALUES ('0','"+date.getText().substring(date.getText().indexOf(",")+1)+"','Opening Balance','"+openingBalance.getText()+"') \r\n");	
			create.executeUpdate();
		}
			catch(Exception e) {
				System.err.print(e);
			} 
	}
	
	
	

	public PartyTrialBalanceEssentials() {
		
		
		
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
	
		
		createTables();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 469, 447);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(211, 211, 211));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setDefaultLookAndFeelDecorated(false);
		
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.BLACK);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLayout(null);
		panel.setBounds(0, 421, 469, 24);
		contentPane.add(panel);
		
		
		
		partyName = new promptTextField(34,"");
		partyName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					address.requestFocus();
				}
			}
		});
		partyName.setHorizontalAlignment(SwingConstants.CENTER);
		partyName.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		partyName.setColumns(10);
		partyName.setBounds(new Rectangle(5, 0, 0, 0));
		partyName.setAlignmentX(5.0f);
		partyName.setBounds(201, 61, 246, 34);
		contentPane.add(partyName);
		
		address = new promptTextField(34,"");
		address.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					contactNumber.requestFocus();
				}
			}
		});
		address.setHorizontalAlignment(SwingConstants.CENTER);
		address.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		address.setColumns(10);
		address.setBounds(new Rectangle(5, 0, 0, 0));
		address.setAlignmentX(5.0f);
		address.setBounds(201, 106, 246, 34);
		contentPane.add(address);
		
		contactNumber =new promptTextField(34,"");
		contactNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					openingBalance.requestFocus();
				}
			}
		});
		contactNumber.setHorizontalAlignment(SwingConstants.CENTER);
		contactNumber.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		contactNumber.setColumns(10);
		contactNumber.setBounds(new Rectangle(5, 0, 0, 0));
		contactNumber.setAlignmentX(5.0f);
		contactNumber.setBounds(201, 151, 246, 34);
		contentPane.add(contactNumber);
		
		accountNumber = new promptTextField(34,"");
		accountNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					iban.requestFocus();
				}
			}
		});
		accountNumber.setHorizontalAlignment(SwingConstants.CENTER);
		accountNumber.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		accountNumber.setColumns(10);
		accountNumber.setBounds(new Rectangle(5, 0, 0, 0));
		accountNumber.setAlignmentX(5.0f);
		accountNumber.setBounds(201, 241, 246, 34);
		contentPane.add(accountNumber);

		
		
		category = new JComboBox<String>();
		category.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		category.setBounds(201, 331, 246, 34);
		contentPane.add(category);
		category.addItem("");
		category.addItem("Books");
		category.addItem("Bags");
		category.addItem("Stationery");
		category.addItem("Uniform");
		category.addItem("Garments");
		
		JButton btnEnter = new JButton("Cancel");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnEnter.setForeground(new Color(0, 0, 0));
		btnEnter.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		btnEnter.setBackground(null);
		btnEnter.setBounds(237, 376, 100, 34);
		contentPane.add(btnEnter);
		
		JButton btnEnter_1 = new JButton("Enter");
		btnEnter_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				insertTable();
				status.setText("Added Successfully!!!");
				
				
				DefaultTableModel model = (DefaultTableModel) PartyTrialBalance.table.getModel();
				model.addRow(new Object[] {PartyTrialBalance.table.getRowCount()+1,category.getSelectedItem().toString(),partyName.getText(),openingBalance.getText()});
				
				
				partyName.setText("");address.setText("");contactNumber.setText("");openingBalance.setText("0");accountNumber.setText("");
				iban.setText("");category.setSelectedItem("");
				
				PartyTrialBalance.setTotal();
			}
		});
		btnEnter_1.setForeground(new Color(0, 0, 0));
		btnEnter_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		btnEnter_1.setBackground(null);
		btnEnter_1.setBounds(347, 376, 100, 34);
		contentPane.add(btnEnter_1);
		
		iban = new promptTextField(34,"");
		iban.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {}
					//category.requestFocus();
			}
		});
		iban.setHorizontalAlignment(SwingConstants.CENTER);
		iban.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		iban.setColumns(10);
		iban.setBounds(new Rectangle(5, 0, 0, 0));
		iban.setAlignmentX(5.0f);
		iban.setBounds(201, 286, 246, 34);
		contentPane.add(iban);
		
		openingBalance = new promptTextField(34,"0");
		openingBalance.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					accountNumber.requestFocus();
				}
			}
		});
		openingBalance.setHorizontalAlignment(SwingConstants.CENTER);
		openingBalance.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		openingBalance.setColumns(10);
		openingBalance.setBounds(new Rectangle(5, 0, 0, 0));
		openingBalance.setAlignmentX(5.0f);
		openingBalance.setBounds(201, 196, 246, 34);
		contentPane.add(openingBalance);
		
		status = new JLabel("");
		status.setHorizontalTextPosition(SwingConstants.CENTER);
		status.setHorizontalAlignment(SwingConstants.CENTER);
		status.setForeground(Color.WHITE);
		status.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		status.setAlignmentX(0.5f);
		status.setBounds(10, 373, 229, 34);
		contentPane.add(status);
		
		Date d= new Date();
		SimpleDateFormat a = new SimpleDateFormat("EEEE,dd-MMMM-yyyy");
	        
		
		
		JLabel time = new JLabel("Time:");
		time.setForeground(Color.BLACK);
		time.setHorizontalAlignment(SwingConstants.RIGHT);
		time.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		time.setBounds(340, 0, 111, 24);
		panel.add(time);
		
		date = new JLabel("Date:");
		date.setForeground(Color.BLACK);
		date.setHorizontalAlignment(SwingConstants.RIGHT);
		date.setBounds(10, 0, 319, 24);
		panel.add(date);
		date.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		date.setText(a.format(d));
		//extracted from CommonMethods
				CommonMethods.timeFromCommon(time);
				
				JLabel lblDate_1 = new JLabel("Party Name");
				lblDate_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1.setForeground(Color.BLACK);
				lblDate_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1.setAlignmentX(0.5f);
				lblDate_1.setBounds(10, 61, 200, 34);
				contentPane.add(lblDate_1);
				
				JLabel lblDate_1_1 = new JLabel("Address");
				lblDate_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_1.setForeground(Color.BLACK);
				lblDate_1_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_1.setAlignmentX(0.5f);
				lblDate_1_1.setBounds(10, 106, 200, 34);
				contentPane.add(lblDate_1_1);
				
				JLabel lblDate_1_2 = new JLabel("Contact Number");
				lblDate_1_2.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_2.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_2.setForeground(Color.BLACK);
				lblDate_1_2.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_2.setAlignmentX(0.5f);
				lblDate_1_2.setBounds(10, 151, 200, 34);
				contentPane.add(lblDate_1_2);
				
				JLabel lblDate_1_2_1 = new JLabel("Opening Balance");
				lblDate_1_2_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_2_1.setForeground(Color.BLACK);
				lblDate_1_2_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_2_1.setAlignmentX(0.5f);
				lblDate_1_2_1.setBounds(10, 196, 200, 34);
				contentPane.add(lblDate_1_2_1);
				
				JLabel lblDate_1_3 = new JLabel("Account Number");
				lblDate_1_3.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_3.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_3.setForeground(Color.BLACK);
				lblDate_1_3.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_3.setAlignmentX(0.5f);
				lblDate_1_3.setBounds(10, 241, 200, 34);
				contentPane.add(lblDate_1_3);
				
				JLabel lblDate_1_3_1 = new JLabel("IBAN");
				lblDate_1_3_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_3_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_3_1.setForeground(Color.BLACK);
				lblDate_1_3_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_3_1.setAlignmentX(0.5f);
				lblDate_1_3_1.setBounds(10, 286, 200, 34);
				contentPane.add(lblDate_1_3_1);
				
				JLabel lblDate_1_4 = new JLabel("Category");
				lblDate_1_4.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_4.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_4.setForeground(Color.BLACK);
				lblDate_1_4.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_4.setAlignmentX(0.5f);
				lblDate_1_4.setBounds(10, 331, 200, 34);
				contentPane.add(lblDate_1_4);
				
				JPanel panel_1 = new RoundedPanel(34);
				panel_1.setBackground(null);
				panel_1.setBounds(10, 61, 436, 34);
				contentPane.add(panel_1);
				
				RoundedPanel panel_1_1 = new RoundedPanel(34);
				panel_1_1.setBackground(null);
				panel_1_1.setBounds(10, 106, 436, 34);
				contentPane.add(panel_1_1);
				
				RoundedPanel panel_1_2 = new RoundedPanel(34);
				panel_1_2.setBackground(null);
				panel_1_2.setBounds(10, 151, 436, 34);
				contentPane.add(panel_1_2);
				
				RoundedPanel panel_1_3 = new RoundedPanel(34);
				panel_1_3.setBackground(null);
				panel_1_3.setBounds(10, 196, 436, 34);
				contentPane.add(panel_1_3);
				
				RoundedPanel panel_1_4 = new RoundedPanel(34);
				panel_1_4.setBackground(null);
				panel_1_4.setBounds(10, 241, 436, 34);
				contentPane.add(panel_1_4);
				
				RoundedPanel panel_1_5 = new RoundedPanel(34);
				panel_1_5.setBackground(null);
				panel_1_5.setBounds(10, 286, 436, 34);
				contentPane.add(panel_1_5);
				
				JLabel lblDate_1_4_1 = new JLabel("Add Party");
				lblDate_1_4_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_4_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_4_1.setForeground(Color.BLACK);
				lblDate_1_4_1.setFont(new Font("Century Gothic", Font.BOLD, 20));
				lblDate_1_4_1.setAlignmentX(0.5f);
				lblDate_1_4_1.setBounds(10, 21, 437, 34);
				contentPane.add(lblDate_1_4_1);
				
				RoundedPanel panel_1_3_1 = new RoundedPanel(34);
				panel_1_3_1.setBackground(null);
				panel_1_3_1.setBounds(10, 331, 436, 34);
				contentPane.add(panel_1_3_1);
				
				
		
		
	}
}
