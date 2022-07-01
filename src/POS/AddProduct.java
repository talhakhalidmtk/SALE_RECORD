package POS;

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
import SourceCode.CommonMethods;
import SourceCode.DailySaleReport;
import start.Splash;

import javax.swing.JTextField;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddProduct extends JFrame {

	private JPanel contentPane;
	private JTextField code;
	private JTextField salePrice;
	private JTextField purchasePrice;
	private JTextField title;
	Connection con;
	private promptTextField qty;
	private promptTextField location;
	private JLabel status;
	private JComboBox<String> category;

	/**
	 * Launch the application.
	 */
	
	
	

	public AddProduct() {
		
		
		
		ImageIcon img =new ImageIcon(Splash.class.getResource("/SourceCode/edit.png"));
		setIconImage(img.getImage());
			

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 469, 448);
		
//		setDefaultLookAndFeelDecorated(true);
//		setUndecorated(true);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(211, 211, 211));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		setLocationRelativeTo(null);
		
		code =new promptTextField(34,"");
		code.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					title.requestFocus();
				}
			}
		});
		code.setHorizontalAlignment(SwingConstants.CENTER);
		code.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		code.setColumns(10);
		code.setBounds(new Rectangle(5, 0, 0, 0));
		code.setAlignmentX(5.0f);
		code.setBounds(201, 56, 246, 34);
		contentPane.add(code);
		
		salePrice = new promptTextField(34,"");
		salePrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					purchasePrice.requestFocus();
				}
			}
		});
		salePrice.setHorizontalAlignment(SwingConstants.CENTER);
		salePrice.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		salePrice.setColumns(10);
		salePrice.setBounds(new Rectangle(5, 0, 0, 0));
		salePrice.setAlignmentX(5.0f);
		salePrice.setBounds(201, 146, 246, 34);
		contentPane.add(salePrice);
		
		JButton btnEnter = new JButton("Exit");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnEnter.setForeground(new Color(0, 0, 0));
		btnEnter.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		btnEnter.setBackground(null);
		btnEnter.setBounds(233, 368, 100, 34);
		contentPane.add(btnEnter);
		
		JButton btnEnter_1 = new JButton("ADD");
		btnEnter_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
				 	String a2 = code.getText().toUpperCase();
				 	String a3 = title.getText().toUpperCase();
				 	String a1 = category.getSelectedItem().toString().toUpperCase();
				 	String a4 = salePrice.getText().toUpperCase();
				 	String a5 = purchasePrice.getText().toUpperCase();
				 	String a6 = qty.getText().toUpperCase();
				 	String a7 = location.getText().toUpperCase();
				 	
				 	Connection con = Connect.getConnect();
				 	PreparedStatement ps = con.prepareStatement("insert into ProductList values(?,?,?,?,?,?,?)");
		            ps.setString(1, a2);
		            ps.setString(2, a3);
		            ps.setString(3, a1);
		            ps.setString(4, a4);
		            ps.setString(5, a5);
		            ps.setString(6, a6);
		            ps.setString(7, a7);
		            
		              ps.executeUpdate();
			       code.setText("");title.setText("");salePrice.setText("");purchasePrice.setText("");qty.setText("");location.setText("");
		            status.setText("Product Added Successfully.");
					 con.close();			 
					 
					 ProductList.setTable();
					 
			 }
				 catch(Exception e1){
			       }
			 	
				
				
				
			}
		});
		btnEnter_1.setForeground(new Color(0, 0, 0));
		btnEnter_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		btnEnter_1.setBackground(null);
		btnEnter_1.setBounds(343, 368, 100, 34);
		contentPane.add(btnEnter_1);
		
		purchasePrice = new promptTextField(34,"");
		purchasePrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					qty.requestFocus();
				}
					
			}
		});
		purchasePrice.setHorizontalAlignment(SwingConstants.CENTER);
		purchasePrice.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		purchasePrice.setColumns(10);
		purchasePrice.setBounds(new Rectangle(5, 0, 0, 0));
		purchasePrice.setAlignmentX(5.0f);
		purchasePrice.setBounds(201, 191, 246, 34);
		contentPane.add(purchasePrice);
		
		title = new promptTextField(34,"");
		title.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					salePrice.requestFocus();
				}
			}
		});
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		title.setColumns(10);
		title.setBounds(new Rectangle(5, 0, 0, 0));
		title.setAlignmentX(5.0f);
		title.setBounds(201, 101, 246, 34);
		contentPane.add(title);
		
		Date d= new Date();
		SimpleDateFormat a = new SimpleDateFormat("EEEE,dd-MMMM-yyyy");
				
				JLabel lblDate_1_2 = new JLabel("Product Code");
				lblDate_1_2.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_2.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_2.setForeground(Color.BLACK);
				lblDate_1_2.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_2.setAlignmentX(0.5f);
				lblDate_1_2.setBounds(10, 56, 200, 34);
				contentPane.add(lblDate_1_2);
				
				JLabel lblDate_1_2_1 = new JLabel("Product Title");
				lblDate_1_2_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_2_1.setForeground(Color.BLACK);
				lblDate_1_2_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_2_1.setAlignmentX(0.5f);
				lblDate_1_2_1.setBounds(10, 101, 200, 34);
				contentPane.add(lblDate_1_2_1);
				
				JLabel lblDate_1_3 = new JLabel("Sale Price");
				lblDate_1_3.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_3.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_3.setForeground(Color.BLACK);
				lblDate_1_3.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_3.setAlignmentX(0.5f);
				lblDate_1_3.setBounds(10, 146, 200, 34);
				contentPane.add(lblDate_1_3);
				
				JLabel lblDate_1_3_1 = new JLabel("Purchase Price");
				lblDate_1_3_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_3_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_3_1.setForeground(Color.BLACK);
				lblDate_1_3_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_3_1.setAlignmentX(0.5f);
				lblDate_1_3_1.setBounds(10, 191, 200, 34);
				contentPane.add(lblDate_1_3_1);
				
				RoundedPanel panel_1_2 = new RoundedPanel(34);
				panel_1_2.setBackground(null);
				panel_1_2.setBounds(10, 56, 436, 34);
				contentPane.add(panel_1_2);
				
				RoundedPanel panel_1_3 = new RoundedPanel(34);
				panel_1_3.setBackground(null);
				panel_1_3.setBounds(10, 101, 436, 34);
				contentPane.add(panel_1_3);
				
				RoundedPanel panel_1_4 = new RoundedPanel(34);
				panel_1_4.setBackground(null);
				panel_1_4.setBounds(10, 146, 436, 34);
				contentPane.add(panel_1_4);
				
				RoundedPanel panel_1_5 = new RoundedPanel(34);
				panel_1_5.setBackground(null);
				panel_1_5.setBounds(10, 191, 436, 34);
				contentPane.add(panel_1_5);
				
				JLabel lblDate_1_4_1 = new JLabel("Add Product");
				lblDate_1_4_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_4_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_4_1.setForeground(Color.BLACK);
				lblDate_1_4_1.setFont(new Font("Century Gothic", Font.BOLD, 20));
				lblDate_1_4_1.setAlignmentX(0.5f);
				lblDate_1_4_1.setBounds(10, 11, 437, 34);
				contentPane.add(lblDate_1_4_1);
				
				qty = new promptTextField(34, "");
				qty.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode()==10) {
							location.requestFocus();
						}
					}
				});
				qty.setHorizontalAlignment(SwingConstants.CENTER);
				qty.setAlignmentX(5.0f);
				qty.setFont(new Font("Century Gothic", Font.PLAIN, 16));
				qty.setBounds(201, 236, 246, 34);
				contentPane.add(qty);
				
				JLabel lblDate_1_3_1_1 = new JLabel("Available Quantity");
				lblDate_1_3_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_3_1_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_3_1_1.setForeground(Color.BLACK);
				lblDate_1_3_1_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_3_1_1.setAlignmentX(0.5f);
				lblDate_1_3_1_1.setBounds(10, 236, 200, 34);
				contentPane.add(lblDate_1_3_1_1);
				
				RoundedPanel panel_1_5_1 = new RoundedPanel(34);
				panel_1_5_1.setBackground(null);
				panel_1_5_1.setBounds(10, 236, 436, 34);
				contentPane.add(panel_1_5_1);
				
				location = new promptTextField(34, "");
				location.setHorizontalAlignment(SwingConstants.CENTER);
				location.setFont(new Font("Century Gothic", Font.PLAIN, 16));
				location.setAlignmentX(5.0f);
				location.setBounds(201, 278, 246, 34);
				contentPane.add(location);
				
				JLabel lblDate_1_3_1_2 = new JLabel("Product Location");
				lblDate_1_3_1_2.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_3_1_2.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_3_1_2.setForeground(Color.BLACK);
				lblDate_1_3_1_2.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_3_1_2.setAlignmentX(0.5f);
				lblDate_1_3_1_2.setBounds(10, 278, 200, 34);
				contentPane.add(lblDate_1_3_1_2);
				
				RoundedPanel panel_1_5_2 = new RoundedPanel(34);
				panel_1_5_2.setBackground(null);
				panel_1_5_2.setBounds(10, 278, 436, 34);
				contentPane.add(panel_1_5_2);
				
				category = new JComboBox<String>();
				category.setFont(new Font("Century Gothic", Font.PLAIN, 16));
				category.setBounds(201, 323, 246, 34);
				contentPane.add(category);
				category.addItem("");
				category.addItem("Books");
				category.addItem("Bags");
				category.addItem("Stationery");
				category.addItem("Uniform");
				category.addItem("Garments");
				
				JLabel lblDate_1_4 = new JLabel("Category");
				lblDate_1_4.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_4.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_4.setForeground(Color.BLACK);
				lblDate_1_4.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_4.setAlignmentX(0.5f);
				lblDate_1_4.setBounds(10, 323, 200, 34);
				contentPane.add(lblDate_1_4);
				
				RoundedPanel panel_1_3_1 = new RoundedPanel(34);
				panel_1_3_1.setBackground(null);
				panel_1_3_1.setBounds(10, 323, 436, 34);
				contentPane.add(panel_1_3_1);
				
				status = new JLabel("Status");
				status.setHorizontalTextPosition(SwingConstants.CENTER);
				status.setHorizontalAlignment(SwingConstants.CENTER);
				status.setForeground(Color.BLACK);
				status.setFont(new Font("Century Gothic", Font.PLAIN, 14));
				status.setAlignmentX(0.5f);
				status.setBounds(10, 368, 213, 34);
				contentPane.add(status);
				
				
		
		
	}
}
