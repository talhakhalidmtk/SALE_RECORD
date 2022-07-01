package start;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import POS.MainSaleWindow;
import SourceCode.DailySaleReport;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class loginWindow extends JFrame {

	private JPanel contentPane;
	private JTextField userTextField;
	private JPasswordField passwordField;
	private JButton resetButton;
	private JButton loginButton;
	private JCheckBox showPassword;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {


					
					
					loginWindow frame = new loginWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public loginWindow() {
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
		
		setUndecorated(true);
		
		setShape(new RoundRectangle2D.Double(0, 0, 570, 320, 25, 25));
		ImageIcon img =new ImageIcon(Splash.class.getResource("/SourceCode/edit.png"));
		setIconImage(img.getImage());
		setIconImage(Toolkit.getDefaultToolkit().getImage(loginWindow.class.getResource("/SourceCode/edit.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 320);
		contentPane = new JPanel();
		setLocationRelativeTo(null);
//		setUndecorated(true);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 userTextField.setText("");
		         passwordField.setText("");
			}
		});
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		lblNewLabel_2.setIcon(new ImageIcon(loginWindow.class.getResource("/Images/exit.png")));
		lblNewLabel_2.setBounds(524, 11, 25, 23);
		contentPane.add(lblNewLabel_2);
		resetButton.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		resetButton.setBounds(436, 244, 100, 35);
		contentPane.add(resetButton);
		
		loginButton = new JButton("Log In");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String userText;
		            String pwdText;
		            userText = userTextField.getText();
		            pwdText = passwordField.getText();
		            if (userText.equalsIgnoreCase("abc") && pwdText.equalsIgnoreCase("123")) {
		            	new MainSaleWindow().setVisible(true);
		            	dispose();
		               // JOptionPane.showMessageDialog(this, "Login Successful");
		            } else {
		                JOptionPane.showMessageDialog(null, "Invalid Username or Password");
		            }
			}
		});
		loginButton.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		loginButton.setBounds(326, 244, 100, 35);
		contentPane.add(loginButton);
		
		showPassword = new JCheckBox("show password");
		showPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (showPassword.isSelected()) {
	                passwordField.setEchoChar((char) 0);
	            } else {
	                passwordField.setEchoChar('*');
	            }
			}
		});
		showPassword.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		showPassword.setBounds(366, 182, 170, 23);
		showPassword.setBackground(Color.LIGHT_GRAY);
		contentPane.add(showPassword);
		
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		passwordField.setBounds(366, 147, 170, 35);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_1_1.setBounds(236, 147, 120, 35);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1 = new JLabel("User Name");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_1.setBounds(236, 101, 120, 35);
		contentPane.add(lblNewLabel_1);
		
		userTextField = new JTextField();
		userTextField.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		userTextField.setBounds(366, 101, 170, 35);
		contentPane.add(userTextField);
		userTextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 570, 319);
		lblNewLabel.setIcon(new ImageIcon(loginWindow.class.getResource("/Images/mainLogo.png")));
		contentPane.add(lblNewLabel);
	
		
	}
	
}
