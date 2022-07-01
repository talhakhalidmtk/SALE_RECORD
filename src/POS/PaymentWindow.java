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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentWindow extends JFrame {

	private JPanel contentPane;
	private JTextField totalToPay;
	private JTextField productCode;
	private JTextField salePrice;
	private JTextField purchasePrice;
	private JTextField productTitle;
	Connection con;
	private promptTextField availableQuantity;
	private promptTextField productLocation;
	private JLabel status;

	/**
	 * Launch the application.
	 */
	
	
	

	public PaymentWindow() {
		
		
		
		ImageIcon img =new ImageIcon(Splash.class.getResource("/SourceCode/edit.png"));
		setIconImage(img.getImage());
			

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 469, 567);
		
//		setDefaultLookAndFeelDecorated(true);
//		setUndecorated(true);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(211, 211, 211));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		setLocationRelativeTo(null);
		
		totalToPay = new promptTextField(34,"");
		totalToPay.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					productCode.requestFocus();
				}
			}
		});
		totalToPay.setHorizontalAlignment(SwingConstants.CENTER);
		totalToPay.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		totalToPay.setColumns(10);
		totalToPay.setBounds(new Rectangle(5, 0, 0, 0));
		totalToPay.setAlignmentX(5.0f);
		totalToPay.setBounds(201, 89, 246, 34);
		contentPane.add(totalToPay);
		
		productCode =new promptTextField(34,"");
		productCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					productTitle.requestFocus();
				}
			}
		});
		productCode.setHorizontalAlignment(SwingConstants.CENTER);
		productCode.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		productCode.setColumns(10);
		productCode.setBounds(new Rectangle(5, 0, 0, 0));
		productCode.setAlignmentX(5.0f);
		productCode.setBounds(201, 134, 246, 34);
		contentPane.add(productCode);
		
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
		salePrice.setBounds(201, 224, 246, 34);
		contentPane.add(salePrice);
		
		JButton btnEnter = new JButton("Print");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnEnter.setForeground(new Color(0, 0, 0));
		btnEnter.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		btnEnter.setBackground(null);
		btnEnter.setBounds(237, 491, 100, 34);
		contentPane.add(btnEnter);
		
		JButton btnEnter_1 = new JButton("Save");
		btnEnter_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				status.setText("Saved.");
				
			}
		});
		btnEnter_1.setForeground(new Color(0, 0, 0));
		btnEnter_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		btnEnter_1.setBackground(null);
		btnEnter_1.setBounds(347, 491, 100, 34);
		contentPane.add(btnEnter_1);
		
		purchasePrice = new promptTextField(34,"");
		purchasePrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					availableQuantity.requestFocus();
				}
					
			}
		});
		purchasePrice.setHorizontalAlignment(SwingConstants.CENTER);
		purchasePrice.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		purchasePrice.setColumns(10);
		purchasePrice.setBounds(new Rectangle(5, 0, 0, 0));
		purchasePrice.setAlignmentX(5.0f);
		purchasePrice.setBounds(201, 269, 246, 34);
		contentPane.add(purchasePrice);
		
		productTitle = new promptTextField(34,"");
		productTitle.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					salePrice.requestFocus();
				}
			}
		});
		productTitle.setHorizontalAlignment(SwingConstants.CENTER);
		productTitle.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		productTitle.setColumns(10);
		productTitle.setBounds(new Rectangle(5, 0, 0, 0));
		productTitle.setAlignmentX(5.0f);
		productTitle.setBounds(201, 179, 246, 34);
		contentPane.add(productTitle);
		
		Date d= new Date();
		SimpleDateFormat a = new SimpleDateFormat("EEEE,dd-MMMM-yyyy");
				
				JLabel lblDate_1_1 = new JLabel("Total to Pay");
				lblDate_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_1.setForeground(Color.BLACK);
				lblDate_1_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_1.setAlignmentX(0.5f);
				lblDate_1_1.setBounds(10, 89, 200, 34);
				contentPane.add(lblDate_1_1);
				
				JLabel lblDate_1_2 = new JLabel("Adjustment");
				lblDate_1_2.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_2.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_2.setForeground(Color.BLACK);
				lblDate_1_2.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_2.setAlignmentX(0.5f);
				lblDate_1_2.setBounds(10, 134, 200, 34);
				contentPane.add(lblDate_1_2);
				
				JLabel lblDate_1_2_1 = new JLabel("Net Payable");
				lblDate_1_2_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_2_1.setForeground(Color.BLACK);
				lblDate_1_2_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_2_1.setAlignmentX(0.5f);
				lblDate_1_2_1.setBounds(10, 179, 200, 34);
				contentPane.add(lblDate_1_2_1);
				
				JLabel lblDate_1_3 = new JLabel("Cash Payment");
				lblDate_1_3.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_3.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_3.setForeground(Color.BLACK);
				lblDate_1_3.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_3.setAlignmentX(0.5f);
				lblDate_1_3.setBounds(10, 224, 200, 34);
				contentPane.add(lblDate_1_3);
				
				JLabel lblDate_1_3_1 = new JLabel("Mobile Transfer");
				lblDate_1_3_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_3_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_3_1.setForeground(Color.BLACK);
				lblDate_1_3_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_3_1.setAlignmentX(0.5f);
				lblDate_1_3_1.setBounds(10, 269, 200, 34);
				contentPane.add(lblDate_1_3_1);
				
				RoundedPanel panel_1_1 = new RoundedPanel(34);
				panel_1_1.setBackground(null);
				panel_1_1.setBounds(10, 89, 436, 34);
				contentPane.add(panel_1_1);
				
				RoundedPanel panel_1_2 = new RoundedPanel(34);
				panel_1_2.setBackground(null);
				panel_1_2.setBounds(10, 134, 436, 34);
				contentPane.add(panel_1_2);
				
				RoundedPanel panel_1_3 = new RoundedPanel(34);
				panel_1_3.setBackground(null);
				panel_1_3.setBounds(10, 179, 436, 34);
				contentPane.add(panel_1_3);
				
				RoundedPanel panel_1_4 = new RoundedPanel(34);
				panel_1_4.setBackground(null);
				panel_1_4.setBounds(10, 224, 436, 34);
				contentPane.add(panel_1_4);
				
				RoundedPanel panel_1_5 = new RoundedPanel(34);
				panel_1_5.setBackground(null);
				panel_1_5.setBounds(10, 269, 436, 34);
				contentPane.add(panel_1_5);
				
				JLabel lblDate_1_4_1 = new JLabel("Payment Window");
				lblDate_1_4_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_4_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_4_1.setForeground(Color.BLACK);
				lblDate_1_4_1.setFont(new Font("Century Gothic", Font.BOLD, 20));
				lblDate_1_4_1.setAlignmentX(0.5f);
				lblDate_1_4_1.setBounds(10, 11, 437, 34);
				contentPane.add(lblDate_1_4_1);
				
				availableQuantity = new promptTextField(34, "");
				availableQuantity.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode()==10) {
							productLocation.requestFocus();
						}
					}
				});
				availableQuantity.setHorizontalAlignment(SwingConstants.CENTER);
				availableQuantity.setAlignmentX(5.0f);
				availableQuantity.setFont(new Font("Century Gothic", Font.PLAIN, 16));
				availableQuantity.setBounds(201, 314, 246, 34);
				contentPane.add(availableQuantity);
				
				JLabel lblDate_1_3_1_1 = new JLabel("Bank Transfer");
				lblDate_1_3_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_3_1_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_3_1_1.setForeground(Color.BLACK);
				lblDate_1_3_1_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_3_1_1.setAlignmentX(0.5f);
				lblDate_1_3_1_1.setBounds(10, 314, 200, 34);
				contentPane.add(lblDate_1_3_1_1);
				
				RoundedPanel panel_1_5_1 = new RoundedPanel(34);
				panel_1_5_1.setBackground(null);
				panel_1_5_1.setBounds(10, 314, 436, 34);
				contentPane.add(panel_1_5_1);
				
				productLocation = new promptTextField(34, "");
				productLocation.setHorizontalAlignment(SwingConstants.CENTER);
				productLocation.setFont(new Font("Century Gothic", Font.PLAIN, 16));
				productLocation.setAlignmentX(5.0f);
				productLocation.setBounds(201, 356, 246, 34);
				contentPane.add(productLocation);
				
				JLabel lblDate_1_3_1_2 = new JLabel("Cheque");
				lblDate_1_3_1_2.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_3_1_2.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_3_1_2.setForeground(Color.BLACK);
				lblDate_1_3_1_2.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_3_1_2.setAlignmentX(0.5f);
				lblDate_1_3_1_2.setBounds(10, 356, 200, 34);
				contentPane.add(lblDate_1_3_1_2);
				
				RoundedPanel panel_1_5_2 = new RoundedPanel(34);
				panel_1_5_2.setBackground(null);
				panel_1_5_2.setBounds(10, 356, 436, 34);
				contentPane.add(panel_1_5_2);
				
				status = new JLabel("Status");
				status.setHorizontalTextPosition(SwingConstants.CENTER);
				status.setHorizontalAlignment(SwingConstants.CENTER);
				status.setForeground(Color.BLACK);
				status.setFont(new Font("Century Gothic", Font.PLAIN, 14));
				status.setAlignmentX(0.5f);
				status.setBounds(14, 491, 213, 34);
				contentPane.add(status);
				
				promptTextField productLocation_1 = new promptTextField(34, "");
				productLocation_1.setHorizontalAlignment(SwingConstants.CENTER);
				productLocation_1.setAlignmentX(5.0f);
				productLocation_1.setBounds(201, 401, 246, 34);
				productLocation_1.setFont(new Font("Century Gothic", Font.PLAIN, 14));
				contentPane.add(productLocation_1);
				
				
				JLabel lblDate_1_3_1_2_1 = new JLabel("Total Received");
				lblDate_1_3_1_2_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_3_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_3_1_2_1.setForeground(Color.BLACK);
				lblDate_1_3_1_2_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_3_1_2_1.setAlignmentX(0.5f);
				lblDate_1_3_1_2_1.setBounds(10, 401, 200, 34);
				contentPane.add(lblDate_1_3_1_2_1);
				
				RoundedPanel panel_1_5_2_1 = new RoundedPanel(34);
				panel_1_5_2_1.setBackground(null);
				panel_1_5_2_1.setBounds(10, 401, 436, 34);
				contentPane.add(panel_1_5_2_1);
				
				promptTextField productLocation_2 = new promptTextField(34, "");
				productLocation_2.setHorizontalAlignment(SwingConstants.CENTER);
				productLocation_2.setAlignmentX(5.0f);
				productLocation_2.setBounds(201, 446, 246, 34);
				productLocation_2.setFont(new Font("Century Gothic", Font.PLAIN, 14));
				contentPane.add(productLocation_2);
				
				JLabel lblDate_1_3_1_2_2 = new JLabel("Change");
				lblDate_1_3_1_2_2.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_3_1_2_2.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_3_1_2_2.setForeground(Color.BLACK);
				lblDate_1_3_1_2_2.setFont(new Font("Century Gothic", Font.BOLD, 16));
				lblDate_1_3_1_2_2.setAlignmentX(0.5f);
				lblDate_1_3_1_2_2.setBounds(10, 446, 200, 34);
				contentPane.add(lblDate_1_3_1_2_2);
				
				RoundedPanel panel_1_5_2_2 = new RoundedPanel(34);
				panel_1_5_2_2.setBackground(null);
				panel_1_5_2_2.setBounds(10, 446, 436, 34);
				contentPane.add(panel_1_5_2_2);
				
				JLabel lblDate_1_4_1_1 = new JLabel("Bill #");
				lblDate_1_4_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_4_1_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_4_1_1.setForeground(Color.BLACK);
				lblDate_1_4_1_1.setFont(new Font("Century Gothic", Font.PLAIN, 18));
				lblDate_1_4_1_1.setAlignmentX(0.5f);
				lblDate_1_4_1_1.setBounds(10, 44, 51, 34);
				contentPane.add(lblDate_1_4_1_1);
				
				promptTextField bill = new promptTextField(34, "");
				bill.setHorizontalAlignment(SwingConstants.CENTER);
				bill.setAlignmentX(5.0f);
				bill.setFont(new Font("Century Gothic", Font.PLAIN, 14));
				bill.setBounds(60, 44, 100, 34);
				contentPane.add(bill);
				
				JLabel lblDate_1_4_1_1_1 = new JLabel("Customer ID");
				lblDate_1_4_1_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblDate_1_4_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
				lblDate_1_4_1_1_1.setForeground(Color.BLACK);
				lblDate_1_4_1_1_1.setFont(new Font("Century Gothic", Font.PLAIN, 18));
				lblDate_1_4_1_1_1.setAlignmentX(0.5f);
				lblDate_1_4_1_1_1.setBounds(208, 44, 141, 34);
				contentPane.add(lblDate_1_4_1_1_1);
				
				promptTextField customer = new promptTextField(34, "");
				customer.setHorizontalAlignment(SwingConstants.CENTER);
				customer.setFont(new Font("Century Gothic", Font.PLAIN, 14));
				customer.setAlignmentX(5.0f);
				customer.setBounds(347, 44, 100, 34);
				contentPane.add(customer);
				
				bill.setText(MainSaleWindow.bill.getText());
				customer.setText(MainSaleWindow.customerID.getText());
				
				addWindowListener(new WindowAdapter(){ 
					  public void windowOpened( WindowEvent e){ 
						  totalToPay.requestFocus();
					  } 
					}); 
				
				
		
		
	}
}
