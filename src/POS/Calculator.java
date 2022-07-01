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
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Calculator extends JFrame {

	private JPanel contentPane;
	Connection con;

	private JLabel fiveThousandMultiple;
	private JLabel thousandMultiple;
	private JLabel fiveHundredMutiple;
	private JLabel hundredMultiple;
	private JLabel fiftyMultiple;
	private JLabel twentyMultiple;
	private JLabel tenMultiple;
	private JLabel total;
	
	public void setTotal() {
		
		int tot = Integer.valueOf(fiveThousandMultiple.getText())+Integer.valueOf(thousandMultiple.getText())+
		Integer.valueOf(fiveHundredMutiple.getText())+Integer.valueOf(hundredMultiple.getText())+
		Integer.valueOf(fiftyMultiple.getText())+Integer.valueOf(twentyMultiple.getText())+
		Integer.valueOf(tenMultiple.getText());
		
		total.setText(String.valueOf(tot));
		
	}
	


	public static void main(String[] a) {
		ImageIcon img =new ImageIcon(Splash.class.getResource("/SourceCode/edit.png"));
		
		
		
		Calculator frame = new Calculator();
        frame.setTitle("Login Form");
        frame.setIconImage(img.getImage());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        

    }
	public Calculator() {
		
		
		
		ImageIcon img =new ImageIcon(Splash.class.getResource("/SourceCode/edit.png"));
		setIconImage(img.getImage());
		
//		try {
//            //here you can put the selected theme class name in JTattoo
//            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
//
//
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(DailySaleReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(DailySaleReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(DailySaleReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(DailySaleReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
	

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 351, 378);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(211, 211, 211));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		RoundedPanel panel_1_3_1 = new RoundedPanel(34);
		panel_1_3_1.setBackground(null);
		panel_1_3_1.setBounds(10, 11, 306, 264);
		contentPane.add(panel_1_3_1);
		panel_1_3_1.setLayout(null);
		
		RoundedPanel panel_1_3_2 = new RoundedPanel(34);
		panel_1_3_2.setBackground(null);
		panel_1_3_2.setBounds(10, 11, 285, 34);
		panel_1_3_1.add(panel_1_3_2);
		panel_1_3_2.setLayout(null);
		
		JLabel lblDate_1_4 = new JLabel("5000 x ");
		lblDate_1_4.setBounds(5, 0, 70, 34);
		panel_1_3_2.add(lblDate_1_4);
		lblDate_1_4.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_4.setForeground(Color.BLACK);
		lblDate_1_4.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_4.setAlignmentX(0.5f);
		
		promptTextField fiveThousand = new promptTextField(34, "");
		fiveThousand.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(fiveThousand.getText().equals("")) {
					fiveThousandMultiple.setText("0");
				}else {
					fiveThousandMultiple.setText(String.valueOf(Integer.valueOf(fiveThousand.getText())*5000));
				}
				
				setTotal();
				
				
			}
		});
		fiveThousand.setBounds(85, 0, 80, 34);
		panel_1_3_2.add(fiveThousand);
		fiveThousand.setHorizontalAlignment(SwingConstants.CENTER);
		fiveThousand.setAlignmentX(5.0f);
		
		fiveThousandMultiple = new JLabel("0");
		fiveThousandMultiple.setHorizontalTextPosition(SwingConstants.CENTER);
		fiveThousandMultiple.setHorizontalAlignment(SwingConstants.CENTER);
		fiveThousandMultiple.setForeground(Color.BLACK);
		fiveThousandMultiple.setFont(new Font("Century Gothic", Font.BOLD, 16));
		fiveThousandMultiple.setAlignmentX(0.5f);
		fiveThousandMultiple.setBounds(205, 0, 70, 34);
		panel_1_3_2.add(fiveThousandMultiple);
		
		JLabel lblDate_1_4_2_1 = new JLabel("=");
		lblDate_1_4_2_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_4_2_1.setForeground(Color.BLACK);
		lblDate_1_4_2_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_4_2_1.setAlignmentX(0.5f);
		lblDate_1_4_2_1.setBounds(175, 0, 20, 34);
		panel_1_3_2.add(lblDate_1_4_2_1);
		
		RoundedPanel panel_1_3_2_1 = new RoundedPanel(34);
		panel_1_3_2_1.setBackground(null);
		panel_1_3_2_1.setLayout(null);
		panel_1_3_2_1.setBounds(10, 46, 285, 34);
		panel_1_3_1.add(panel_1_3_2_1);
		
		JLabel lblDate_1_4_1 = new JLabel("1000 x ");
		lblDate_1_4_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_4_1.setForeground(Color.BLACK);
		lblDate_1_4_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_4_1.setAlignmentX(0.5f);
		lblDate_1_4_1.setBounds(5, 0, 70, 34);
		panel_1_3_2_1.add(lblDate_1_4_1);
		
		promptTextField thousand = new promptTextField(34, "");
		thousand.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				
				if(thousand.getText().equals("")) {
					thousandMultiple.setText("0");
				}else {
					thousandMultiple.setText(String.valueOf(Integer.valueOf(thousand.getText())*1000));
				}
				setTotal();
				
			}
		});
		thousand.setHorizontalAlignment(SwingConstants.CENTER);
		thousand.setAlignmentX(5.0f);
		thousand.setBounds(85, 0, 80, 34);
		panel_1_3_2_1.add(thousand);
		
		thousandMultiple = new JLabel("0");
		thousandMultiple.setHorizontalTextPosition(SwingConstants.CENTER);
		thousandMultiple.setHorizontalAlignment(SwingConstants.CENTER);
		thousandMultiple.setForeground(Color.BLACK);
		thousandMultiple.setFont(new Font("Century Gothic", Font.BOLD, 16));
		thousandMultiple.setAlignmentX(0.5f);
		thousandMultiple.setBounds(205, 0, 70, 34);
		panel_1_3_2_1.add(thousandMultiple);
		
		JLabel lblDate_1_4_2_1_1 = new JLabel("=");
		lblDate_1_4_2_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4_2_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_4_2_1_1.setForeground(Color.BLACK);
		lblDate_1_4_2_1_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_4_2_1_1.setAlignmentX(0.5f);
		lblDate_1_4_2_1_1.setBounds(175, 0, 20, 34);
		panel_1_3_2_1.add(lblDate_1_4_2_1_1);
		
		RoundedPanel panel_1_3_2_2 = new RoundedPanel(34);
		panel_1_3_2_2.setBackground(null);
		panel_1_3_2_2.setLayout(null);
		panel_1_3_2_2.setBounds(10, 81, 285, 34);
		panel_1_3_1.add(panel_1_3_2_2);
		
		JLabel lblDate_1_4_3 = new JLabel("500 x ");
		lblDate_1_4_3.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_4_3.setForeground(Color.BLACK);
		lblDate_1_4_3.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_4_3.setAlignmentX(0.5f);
		lblDate_1_4_3.setBounds(5, 0, 70, 34);
		panel_1_3_2_2.add(lblDate_1_4_3);
		
		promptTextField fiveHundred = new promptTextField(34, "");
		fiveHundred.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(fiveHundred.getText().equals("")) {
					fiveHundredMutiple.setText("0");
				}else {
					fiveHundredMutiple.setText(String.valueOf(Integer.valueOf(fiveHundred.getText())*500));
				}
				
				setTotal();
				
				
			}
		});
		fiveHundred.setHorizontalAlignment(SwingConstants.CENTER);
		fiveHundred.setAlignmentX(5.0f);
		fiveHundred.setBounds(85, 0, 80, 34);
		panel_1_3_2_2.add(fiveHundred);
		
		fiveHundredMutiple = new JLabel("0");
		fiveHundredMutiple.setHorizontalTextPosition(SwingConstants.CENTER);
		fiveHundredMutiple.setHorizontalAlignment(SwingConstants.CENTER);
		fiveHundredMutiple.setForeground(Color.BLACK);
		fiveHundredMutiple.setFont(new Font("Century Gothic", Font.BOLD, 16));
		fiveHundredMutiple.setAlignmentX(0.5f);
		fiveHundredMutiple.setBounds(205, 0, 70, 34);
		panel_1_3_2_2.add(fiveHundredMutiple);
		
		JLabel lblDate_1_4_2_1_2 = new JLabel("=");
		lblDate_1_4_2_1_2.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4_2_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_4_2_1_2.setForeground(Color.BLACK);
		lblDate_1_4_2_1_2.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_4_2_1_2.setAlignmentX(0.5f);
		lblDate_1_4_2_1_2.setBounds(175, 0, 20, 34);
		panel_1_3_2_2.add(lblDate_1_4_2_1_2);
		
		RoundedPanel panel_1_3_2_3 = new RoundedPanel(34);
		panel_1_3_2_3.setBackground(null);
		panel_1_3_2_3.setLayout(null);
		panel_1_3_2_3.setBounds(10, 116, 285, 34);
		panel_1_3_1.add(panel_1_3_2_3);
		
		JLabel lblDate_1_4_4 = new JLabel("100 x ");
		lblDate_1_4_4.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_4_4.setForeground(Color.BLACK);
		lblDate_1_4_4.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_4_4.setAlignmentX(0.5f);
		lblDate_1_4_4.setBounds(5, 0, 70, 34);
		panel_1_3_2_3.add(lblDate_1_4_4);
		
		promptTextField hundred = new promptTextField(34, "");
		hundred.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(hundred.getText().equals("")) {
					hundredMultiple.setText("0");
				}else {
					hundredMultiple.setText(String.valueOf(Integer.valueOf(hundred.getText())*100));
				}
				
				
				setTotal();
				
			}
		});
		hundred.setHorizontalAlignment(SwingConstants.CENTER);
		hundred.setAlignmentX(5.0f);
		hundred.setBounds(85, 0, 80, 34);
		panel_1_3_2_3.add(hundred);
		
		hundredMultiple = new JLabel("0");
		hundredMultiple.setHorizontalTextPosition(SwingConstants.CENTER);
		hundredMultiple.setHorizontalAlignment(SwingConstants.CENTER);
		hundredMultiple.setForeground(Color.BLACK);
		hundredMultiple.setFont(new Font("Century Gothic", Font.BOLD, 16));
		hundredMultiple.setAlignmentX(0.5f);
		hundredMultiple.setBounds(205, 0, 70, 34);
		panel_1_3_2_3.add(hundredMultiple);
		
		JLabel lblDate_1_4_2_1_3 = new JLabel("=");
		lblDate_1_4_2_1_3.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4_2_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_4_2_1_3.setForeground(Color.BLACK);
		lblDate_1_4_2_1_3.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_4_2_1_3.setAlignmentX(0.5f);
		lblDate_1_4_2_1_3.setBounds(175, 0, 20, 34);
		panel_1_3_2_3.add(lblDate_1_4_2_1_3);
		
		RoundedPanel panel_1_3_2_4 = new RoundedPanel(34);
		panel_1_3_2_4.setBackground(null);
		panel_1_3_2_4.setLayout(null);
		panel_1_3_2_4.setBounds(10, 151, 285, 34);
		panel_1_3_1.add(panel_1_3_2_4);
		
		JLabel lblDate_1_4_5 = new JLabel("50 x ");
		lblDate_1_4_5.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_4_5.setForeground(Color.BLACK);
		lblDate_1_4_5.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_4_5.setAlignmentX(0.5f);
		lblDate_1_4_5.setBounds(5, 0, 70, 34);
		panel_1_3_2_4.add(lblDate_1_4_5);
		
		promptTextField fifty = new promptTextField(34, "");
		fifty.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(fifty.getText().equals("")) {
					fiftyMultiple.setText("0");
				}else {
					fiftyMultiple.setText(String.valueOf(Integer.valueOf(fifty.getText())*50));
				}
				
				
				setTotal();
			}
		});
		fifty.setHorizontalAlignment(SwingConstants.CENTER);
		fifty.setAlignmentX(5.0f);
		fifty.setBounds(85, 0, 80, 34);
		panel_1_3_2_4.add(fifty);
		
		fiftyMultiple = new JLabel("0");
		fiftyMultiple.setHorizontalTextPosition(SwingConstants.CENTER);
		fiftyMultiple.setHorizontalAlignment(SwingConstants.CENTER);
		fiftyMultiple.setForeground(Color.BLACK);
		fiftyMultiple.setFont(new Font("Century Gothic", Font.BOLD, 16));
		fiftyMultiple.setAlignmentX(0.5f);
		fiftyMultiple.setBounds(205, 0, 70, 34);
		panel_1_3_2_4.add(fiftyMultiple);
		
		JLabel lblDate_1_4_2_1_4 = new JLabel("=");
		lblDate_1_4_2_1_4.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4_2_1_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_4_2_1_4.setForeground(Color.BLACK);
		lblDate_1_4_2_1_4.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_4_2_1_4.setAlignmentX(0.5f);
		lblDate_1_4_2_1_4.setBounds(175, 0, 20, 34);
		panel_1_3_2_4.add(lblDate_1_4_2_1_4);
		
		RoundedPanel panel_1_3_2_4_1 = new RoundedPanel(34);
		panel_1_3_2_4_1.setBackground(null);
		panel_1_3_2_4_1.setLayout(null);
		panel_1_3_2_4_1.setBounds(10, 186, 285, 34);
		panel_1_3_1.add(panel_1_3_2_4_1);
		
		JLabel lblDate_1_4_5_1 = new JLabel("20 x ");
		lblDate_1_4_5_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4_5_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_4_5_1.setForeground(Color.BLACK);
		lblDate_1_4_5_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_4_5_1.setAlignmentX(0.5f);
		lblDate_1_4_5_1.setBounds(5, 0, 70, 34);
		panel_1_3_2_4_1.add(lblDate_1_4_5_1);
		
		promptTextField twenty = new promptTextField(34, "");
		twenty.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(twenty.getText().equals("")) {
					twentyMultiple.setText("0");
				}else {
					twentyMultiple.setText(String.valueOf(Integer.valueOf(twenty.getText())*20));
				}
				setTotal();
			
			}
		});
		twenty.setHorizontalAlignment(SwingConstants.CENTER);
		twenty.setAlignmentX(5.0f);
		twenty.setBounds(85, 0, 80, 34);
		panel_1_3_2_4_1.add(twenty);
		
		twentyMultiple = new JLabel("0");
		twentyMultiple.setHorizontalTextPosition(SwingConstants.CENTER);
		twentyMultiple.setHorizontalAlignment(SwingConstants.CENTER);
		twentyMultiple.setForeground(Color.BLACK);
		twentyMultiple.setFont(new Font("Century Gothic", Font.BOLD, 16));
		twentyMultiple.setAlignmentX(0.5f);
		twentyMultiple.setBounds(205, 0, 70, 34);
		panel_1_3_2_4_1.add(twentyMultiple);
		
		JLabel lblDate_1_4_2_1_4_1 = new JLabel("=");
		lblDate_1_4_2_1_4_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4_2_1_4_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_4_2_1_4_1.setForeground(Color.BLACK);
		lblDate_1_4_2_1_4_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_4_2_1_4_1.setAlignmentX(0.5f);
		lblDate_1_4_2_1_4_1.setBounds(175, 0, 20, 34);
		panel_1_3_2_4_1.add(lblDate_1_4_2_1_4_1);
		
		RoundedPanel panel_1_3_2_4_2 = new RoundedPanel(34);
		panel_1_3_2_4_2.setBackground(null);
		panel_1_3_2_4_2.setLayout(null);
		panel_1_3_2_4_2.setBounds(10, 221, 285, 34);
		panel_1_3_1.add(panel_1_3_2_4_2);
		
		JLabel lblDate_1_4_5_2 = new JLabel("10 x ");
		lblDate_1_4_5_2.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4_5_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_4_5_2.setForeground(Color.BLACK);
		lblDate_1_4_5_2.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_4_5_2.setAlignmentX(0.5f);
		lblDate_1_4_5_2.setBounds(5, 0, 70, 34);
		panel_1_3_2_4_2.add(lblDate_1_4_5_2);
		
		promptTextField ten = new promptTextField(34, "");
		ten.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(ten.getText().equals("")) {
					tenMultiple.setText("0");
				}else {
					tenMultiple.setText(String.valueOf(Integer.valueOf(ten.getText())*10));
				}
				
				setTotal();
			}
		});
		ten.setHorizontalAlignment(SwingConstants.CENTER);
		ten.setAlignmentX(5.0f);
		ten.setBounds(85, 0, 80, 34);
		panel_1_3_2_4_2.add(ten);
		
		tenMultiple = new JLabel("0");
		tenMultiple.setHorizontalTextPosition(SwingConstants.CENTER);
		tenMultiple.setHorizontalAlignment(SwingConstants.CENTER);
		tenMultiple.setForeground(Color.BLACK);
		tenMultiple.setFont(new Font("Century Gothic", Font.BOLD, 16));
		tenMultiple.setAlignmentX(0.5f);
		tenMultiple.setBounds(205, 0, 70, 34);
		panel_1_3_2_4_2.add(tenMultiple);
		
		JLabel lblDate_1_4_2_1_4_2 = new JLabel("=");
		lblDate_1_4_2_1_4_2.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_4_2_1_4_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1_4_2_1_4_2.setForeground(Color.BLACK);
		lblDate_1_4_2_1_4_2.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_4_2_1_4_2.setAlignmentX(0.5f);
		lblDate_1_4_2_1_4_2.setBounds(175, 0, 20, 34);
		panel_1_3_2_4_2.add(lblDate_1_4_2_1_4_2);
		
		JPanel finalTotalPanel_1_1 = new JPanel();
		finalTotalPanel_1_1.setLayout(null);
		finalTotalPanel_1_1.setBorder(new LineBorder(Color.WHITE));
		finalTotalPanel_1_1.setBackground(Color.DARK_GRAY);
		finalTotalPanel_1_1.setBounds(183, 286, 133, 34);
		contentPane.add(finalTotalPanel_1_1);
		
		total = new JLabel("0");
		total.setHorizontalTextPosition(SwingConstants.CENTER);
		total.setHorizontalAlignment(SwingConstants.CENTER);
		total.setForeground(Color.WHITE);
		total.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		total.setAlignmentX(0.5f);
		total.setBounds(0, 0, 133, 34);
		finalTotalPanel_1_1.add(total);
		
		fiveThousand.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		thousand.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		hundred.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		fiveHundred.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		fifty.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		twenty.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		ten.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		
		JButton btnNewButton = new JButton("Exit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnNewButton.setBounds(10, 286, 103, 34);
		contentPane.add(btnNewButton);
		

		setTotal();
		setDefaultLookAndFeelDecorated(false);
		
		setLocationRelativeTo(null);

				
				
		
		
	}
}
