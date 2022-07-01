package POS;

import java.awt.Color;
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
import SourceCode.CommonMethods;
import POS.Connect;
import SourceCode.DailySaleReport;
import start.Splash;

import javax.swing.JTextField;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddLabour extends JFrame {

	private JPanel contentPane;
	private JTextField id;
	private JTextField name;
	private JTextField number;
	private JTextField basicSalary;
	JComboBox<String> nature ;
	private JTextField dailyWages;
	private JTextField address;
	Connection con;
	private promptTextField email;
	private JLabel status;

	/**
	 * Launch the application.
	 */
	
	
	public void getCustomerId() {
		id.setText("1");
		
		try {
			Connection con =  Connect.getConnect();
			String url = "SELECT MAX(ID)  FROM LabourRecord ";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			while(rs.next()) {
				id.setText(String.valueOf(Integer.valueOf(rs.getString(1))+1));
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}

	public AddLabour() {
		
		
		
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
	

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 469, 512);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(211, 211, 211));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setDefaultLookAndFeelDecorated(false);
		
		setLocationRelativeTo(null);
		
		
		
		id = new promptTextField(34,"");
		id.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					name.requestFocus();
				}
			}
		});
		id.setHorizontalAlignment(SwingConstants.CENTER);
		id.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		id.setColumns(10);
		id.setBounds(new Rectangle(5, 0, 0, 0));
		id.setAlignmentX(5.0f);
		id.setBounds(201, 61, 246, 34);
		contentPane.add(id);
		
		name = new promptTextField(34,"");
		name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					number.requestFocus();
				}
			}
		});
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		name.setColumns(10);
		name.setBounds(new Rectangle(5, 0, 0, 0));
		name.setAlignmentX(5.0f);
		name.setBounds(201, 106, 246, 34);
		contentPane.add(name);
		
		number =new promptTextField(34,"");
		number.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					address.requestFocus();
				}
			}
		});
		number.setHorizontalAlignment(SwingConstants.CENTER);
		number.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		number.setColumns(10);
		number.setBounds(new Rectangle(5, 0, 0, 0));
		number.setAlignmentX(5.0f);
		number.setBounds(201, 151, 246, 34);
		contentPane.add(number);
		
		basicSalary = new promptTextField(34,"");
		basicSalary.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					dailyWages.requestFocus();
				}
			}
		});
		basicSalary.setHorizontalAlignment(SwingConstants.CENTER);
		basicSalary.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		basicSalary.setColumns(10);
		basicSalary.setBounds(new Rectangle(5, 0, 0, 0));
		basicSalary.setAlignmentX(5.0f);
		basicSalary.setBounds(201, 241, 246, 34);
		contentPane.add(basicSalary);

		
		
		nature = new JComboBox<String>();
		nature.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		nature.setBounds(201, 376, 246, 34);
		contentPane.add(nature);
		
		nature.addItem("Director");
		nature.addItem("Assistant Director");
		nature.addItem("Manager");
		nature.addItem("Assistant Manager");
		nature.addItem("Store Supervisor");
		nature.addItem("Counter Sale Officer");
		nature.addItem("Cashier");

		
		JButton btnEnter = new JButton("Exit");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnEnter.setForeground(new Color(0, 0, 0));
		btnEnter.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		btnEnter.setBackground(null);
		btnEnter.setBounds(237, 431, 100, 34);
		contentPane.add(btnEnter);
		
		JButton btnEnter_1 = new JButton("ADD");
		btnEnter_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
try{
					
				 	String a1 = id.getText().toUpperCase();
				 	String a2 = name.getText().toUpperCase();
				 	String a3 = number.getText().toUpperCase();
				 	String a4 = address.getText().toUpperCase();
				 	String a5 = basicSalary.getText().toUpperCase();
				 	String a6 = dailyWages.getText().toUpperCase();
				 	String a7 = email.getText().toUpperCase();
				 	String a8 = nature.getSelectedItem().toString().toUpperCase();
			

				 	Connection con = Connect.getConnect();
				 	PreparedStatement ps = con.prepareStatement("insert into LabourRecord values(?,?,?,?,?,?,?,?)");
		            ps.setString(1, a1);
		            ps.setString(2, a2);
		            ps.setString(3, a3);
		            ps.setString(4, a4);
		            ps.setString(5, a5);
		            ps.setString(6, a6);
		            ps.setString(7, a7);
		            ps.setString(8, a8);
		            ps.executeUpdate();
				 con.close();	
				 
				id.setText("");name.setText("");number.setText("");address.setText("");basicSalary.setText("");dailyWages.setText("");email.setText("");;
				status.setText("Employee Added Successfully.");
				getCustomerId();
				LabourSheet.getTable();
}
			 catch(Exception e1){
				 System.out.println(e1);
		       }
				
			}
		});
		btnEnter_1.setForeground(new Color(0, 0, 0));
		btnEnter_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		btnEnter_1.setBackground(null);
		btnEnter_1.setBounds(347, 431, 100, 34);
		contentPane.add(btnEnter_1);
		
		dailyWages = new promptTextField(34,"");
		dailyWages.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					email.requestFocus();
				}
					
			}
		});
		dailyWages.setHorizontalAlignment(SwingConstants.CENTER);
		dailyWages.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		dailyWages.setColumns(10);
		dailyWages.setBounds(new Rectangle(5, 0, 0, 0));
		dailyWages.setAlignmentX(5.0f);
		dailyWages.setBounds(201, 286, 246, 34);
		contentPane.add(dailyWages);
		
		address = new promptTextField(34,"");
		address.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					basicSalary.requestFocus();
				}
			}
		});
		address.setHorizontalAlignment(SwingConstants.CENTER);
		address.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		address.setColumns(10);
		address.setBounds(new Rectangle(5, 0, 0, 0));
		address.setAlignmentX(5.0f);
		address.setBounds(201, 196, 246, 34);
		contentPane.add(address);
		
		Date d= new Date();
		SimpleDateFormat a = new SimpleDateFormat("EEEE,dd-MMMM-yyyy");
				
				JLabel lblDate_1 = new JLabel("Employee ID");
				lblDate_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1.setForeground(Color.BLACK);
				lblDate_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1.setAlignmentX(0.5f);
				lblDate_1.setBounds(10, 61, 200, 34);
				contentPane.add(lblDate_1);
				
				JLabel lblDate_1_1 = new JLabel("Name");
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
				
				JLabel lblDate_1_2_1 = new JLabel("Address");
				lblDate_1_2_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_2_1.setForeground(Color.BLACK);
				lblDate_1_2_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_2_1.setAlignmentX(0.5f);
				lblDate_1_2_1.setBounds(10, 196, 200, 34);
				contentPane.add(lblDate_1_2_1);
				
				JLabel lblDate_1_3 = new JLabel("Basic Salary");
				lblDate_1_3.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_3.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_3.setForeground(Color.BLACK);
				lblDate_1_3.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_3.setAlignmentX(0.5f);
				lblDate_1_3.setBounds(10, 241, 200, 34);
				contentPane.add(lblDate_1_3);
				
				JLabel lblDate_1_3_1 = new JLabel("Daily Wages");
				lblDate_1_3_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_3_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_3_1.setForeground(Color.BLACK);
				lblDate_1_3_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_3_1.setAlignmentX(0.5f);
				lblDate_1_3_1.setBounds(10, 286, 200, 34);
				contentPane.add(lblDate_1_3_1);
				
				JLabel lblDate_1_4 = new JLabel("Employee Nature");
				lblDate_1_4.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_4.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_4.setForeground(Color.BLACK);
				lblDate_1_4.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_4.setAlignmentX(0.5f);
				lblDate_1_4.setBounds(10, 376, 200, 34);
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
				
				JLabel lblDate_1_4_1 = new JLabel("Add Employee");
				lblDate_1_4_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_4_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_4_1.setForeground(Color.BLACK);
				lblDate_1_4_1.setFont(new Font("Century Gothic", Font.BOLD, 20));
				lblDate_1_4_1.setAlignmentX(0.5f);
				lblDate_1_4_1.setBounds(10, 21, 437, 34);
				contentPane.add(lblDate_1_4_1);
				
				RoundedPanel panel_1_3_1 = new RoundedPanel(34);
				panel_1_3_1.setBackground(null);
				panel_1_3_1.setBounds(10, 376, 436, 34);
				contentPane.add(panel_1_3_1);
				
				email = new promptTextField(34, "");
				email.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode()==10) {
							
						}
					}
				});
				email.setHorizontalAlignment(SwingConstants.CENTER);
				email.setAlignmentX(5.0f);
				email.setFont(new Font("Century Gothic", Font.PLAIN, 16));
				email.setBounds(201, 331, 246, 34);
				contentPane.add(email);
				
				JLabel lblDate_1_3_1_1 = new JLabel("Email");
				lblDate_1_3_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_3_1_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_3_1_1.setForeground(Color.BLACK);
				lblDate_1_3_1_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_3_1_1.setAlignmentX(0.5f);
				lblDate_1_3_1_1.setBounds(10, 331, 200, 34);
				contentPane.add(lblDate_1_3_1_1);
				
				RoundedPanel panel_1_5_1 = new RoundedPanel(34);
				panel_1_5_1.setBackground(null);
				panel_1_5_1.setBounds(10, 331, 436, 34);
				contentPane.add(panel_1_5_1);
				
				status = new JLabel("Status");
				status.setHorizontalTextPosition(SwingConstants.CENTER);
				status.setHorizontalAlignment(SwingConstants.CENTER);
				status.setForeground(Color.BLACK);
				status.setFont(new Font("Century Gothic", Font.PLAIN, 14));
				status.setAlignmentX(0.5f);
				status.setBounds(10, 431, 213, 34);
				contentPane.add(status);
				
				getCustomerId();
				
				addWindowListener(new WindowAdapter(){ 
					  public void windowOpened( WindowEvent e){ 
						  name.requestFocus();
					  } 
					}); 
				
		
	}
}
