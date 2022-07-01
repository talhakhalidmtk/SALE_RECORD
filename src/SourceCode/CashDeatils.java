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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JRadioButton;
import Extras.promptTextField;
import start.Splash;
import Extras.RoundedPanel;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CashDeatils extends JFrame {

	private JPanel contentPane;
	JLabel date;
	private JLabel fiveThousandMultiple;
	private JLabel thousandMultiple;
	private JLabel fiveHundredMutiple;
	private JLabel hundredMultiple;
	private JLabel fiftyMultiple;
	private JLabel twentyMultiple;
	private JLabel tenMultiple;
	private JLabel total;
	private promptTextField address;
	private promptTextField partyName;
	private promptTextField contactNumber;
	private promptTextField openingBalance;
	private static promptTextField accountNumber;
	private static JLabel total_1;
	private promptTextField partyName_1;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CashDeatils frame = new CashDeatils();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static String getTotal() {
		int a = Integer.valueOf(accountNumber.getText());
		int b = Integer.valueOf(total_1.getText());
		
		int val= 0;
		if(a<0) {
			val = b;
		}else {
			val = b-a;
		}
		return String.valueOf(val);
	}
	
	public void setTable() {
		try {
			Connection con =  Connect.getConnect();
			String url = "SELECT * FROM CashDetails";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			while(rs.next()) {
				partyName.setText(rs.getString(1));
				address.setText(rs.getString(2));
				contactNumber.setText(rs.getString(3));

			}
			
		}
		catch(Exception e) {
			
		}
	}
	
	 void UpdateTable(){
		
		try {
			Connection con =  Connect.getConnect();
	
			PreparedStatement s1t = con.prepareStatement("UPDATE CashDetails SET [CashInHand]=?, [CashInEasyPaisa]=?, [CashInJazzCash]=?");
			s1t.setString(1, partyName.getText().toString());
			s1t.setString(2, address.getText().toString());
			s1t.setString(3, contactNumber.getText().toString());

			s1t.executeUpdate();	
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Update not successfull!!!");
		}
		
	}
	
	public static void createTables() {
		try {

			Connection con  = Connect.getConnect();

			PreparedStatement create4 = con.prepareStatement("IF OBJECT_ID(N'dbo.CashDetails', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE CashDetails \r\n"
					+ "(CashInHand VARCHAR(100), CashInEasyPaisa VARCHAR(100), CashInJazzCash VARCHAR(100) )\r\n"
					+ "   INSERT INTO CashDetails  "
					+ "VALUES "
					+ "('0','0', '0')\r\n"
					+ "END;");
			create4.executeUpdate();

			}
			
			catch(Exception e) {//JOptionPane.showMessageDialog(null, e);
				System.out.println(e);
			} 
	}
	
	public void setTotal() {
		
		int tot = Integer.valueOf(fiveThousandMultiple.getText())+Integer.valueOf(thousandMultiple.getText())+
		Integer.valueOf(fiveHundredMutiple.getText())+Integer.valueOf(hundredMultiple.getText())+
		Integer.valueOf(fiftyMultiple.getText())+Integer.valueOf(twentyMultiple.getText())+
		Integer.valueOf(tenMultiple.getText());
		
		total.setText(String.valueOf(tot));
		
	}
	
	public void setTotalAgain() {
		
		String i,j,k,l,m;
		
		i =(openingBalance.getText());
		if(i.equals("")) {
			i = "0";
		}
		
		j = contactNumber.getText();
		if(j.equals("")) {
			j="0";
		}
		
		k = address.getText();
		if(k.equals("")) {
			k = "0";
		}
		
		l = partyName.getText();
		if(l.equals("")) {
			l = "0";
		}
		
		m = accountNumber.getText();
		if(m.equals("")) {
			m="0";
		}
		
		
		int tot = Integer.valueOf(i)+Integer.valueOf(j)+Integer.valueOf(k)+Integer.valueOf(l)+Integer.valueOf(m)+Integer.valueOf(partyName_1.getText());
		
		total_1.setText(String.valueOf(tot));
	}
	
	
	public CashDeatils() {
		
		
		ShopExpenses se = new ShopExpenses();
		se.setVisible(false);
		se.dispose();
		
		YearlyRecord s = new YearlyRecord();
		s.setVisible(false);
		s.dispose();
		
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
						   new CashDeatils().setVisible(true);
						dispose();
					   }
					};
					getRootPane().registerKeyboardAction(action3,KeyStroke.getKeyStroke("F5"),JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		
		setFont(new Font("Century Gothic", Font.PLAIN, 18));
//		setUndecorated(true);
		
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
		time.setHorizontalAlignment(SwingConstants.RIGHT);
		time.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		time.setBounds(1219, 0, 111, 24);
		panel.add(time);
		
		date = new JLabel("Date:");
		date.setHorizontalAlignment(SwingConstants.RIGHT);
		date.setBounds(890, 0, 319, 24);
		panel.add(date);
		date.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		date.setText(a.format(d));

		//extracted from CommonMethods
		CommonMethods.timeFromCommon(time);
				
			DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
			rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				
			UIDefaults defaults = UIManager.getLookAndFeelDefaults();
			defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);

		
		
		JLabel lblStudentMart = new JLabel("Student Mart");
		
		Font font = new Font("Times New Roman", Font.BOLD, 30);
		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblStudentMart.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentMart.setFont(new Font("Times New Roman", Font.BOLD, 44));
		lblStudentMart.setBounds(0, 11, 1366, 34);
		lblStudentMart.setFont(font.deriveFont(attributes));
		contentPane.add(lblStudentMart);
		
		
		JLabel lblPartyTrialBalance = new JLabel("Cash Distribution Sheet");
		lblPartyTrialBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblPartyTrialBalance.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblPartyTrialBalance.setBounds(0, 44, 1366, 23);
		contentPane.add(lblPartyTrialBalance);
		
		JLabel lblPartyTrialBalance_1 = new JLabel("");
		lblPartyTrialBalance_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPartyTrialBalance_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPartyTrialBalance_1.setBounds(0, 73, 1366, 23);
		lblPartyTrialBalance_1.setText("As on "+date.getText());
		contentPane.add(lblPartyTrialBalance_1);
		
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
		btnNewButton.setBounds(289, 626, 213, 34);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Refresh (F5)");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CashDeatils().setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setBounds(1127, 626, 213, 34);
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
		btnAnnualSaleRecord.setBounds(568, 626, 214, 34);
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
		btnNewButton_2_1.setBounds(848, 626, 213, 34);
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
		btnDailyRecordf.setBounds(10, 626, 213, 34);
		contentPane.add(btnDailyRecordf);
		
		RoundedPanel panel_1_3_1 = new RoundedPanel(34);
		panel_1_3_1.setBackground(null);
		panel_1_3_1.setBounds(755, 190, 306, 264);
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
		
		JPanel finalTotalPanel_1 = new JPanel();
		finalTotalPanel_1.setLayout(null);
		finalTotalPanel_1.setBorder(new LineBorder(Color.WHITE));
		finalTotalPanel_1.setBackground(Color.DARK_GRAY);
		finalTotalPanel_1.setBounds(501, 465, 246, 34);
		contentPane.add(finalTotalPanel_1);
		
		total_1 = new JLabel("0");
		total_1.setHorizontalTextPosition(SwingConstants.CENTER);
		total_1.setHorizontalAlignment(SwingConstants.CENTER);
		total_1.setForeground(Color.WHITE);
		total_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		total_1.setAlignmentX(0.5f);
		total_1.setBounds(0, 0, 246, 34);
		finalTotalPanel_1.add(total_1);
		
		RoundedPanel panel_1_5 = new RoundedPanel(34);
		panel_1_5.setBackground(null);
		panel_1_5.setBounds(244, 190, 501, 264);
		contentPane.add(panel_1_5);
		panel_1_5.setLayout(null);
		
		JLabel lblDate_1_5 = new JLabel("Expense Balance Redemption");
		lblDate_1_5.setBounds(21, 11, 226, 34);
		panel_1_5.add(lblDate_1_5);
		lblDate_1_5.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_5.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_5.setForeground(Color.BLACK);
		lblDate_1_5.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblDate_1_5.setAlignmentX(0.5f);
		
		partyName_1 = new promptTextField(34, "");
		partyName_1.setBounds(245, 11, 246, 34);
		panel_1_5.add(partyName_1);
		partyName_1.setHorizontalAlignment(SwingConstants.CENTER);
		partyName_1.setAlignmentX(5.0f);
		partyName_1.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		partyName_1.setText(YearlyRecord.getReal());
		
		JLabel lblDate_1_3 = new JLabel("Shop Expenditure Balance");
		lblDate_1_3.setBounds(21, 216, 230, 34);
		panel_1_5.add(lblDate_1_3);
		lblDate_1_3.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_3.setForeground(Color.BLACK);
		lblDate_1_3.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_3.setAlignmentX(0.5f);
		
		accountNumber = new promptTextField(34, "");
		
		accountNumber.setBounds(245, 216, 246, 34);
		panel_1_5.add(accountNumber);
		accountNumber.setHorizontalAlignment(SwingConstants.CENTER);
		accountNumber.setAlignmentX(5.0f);
		accountNumber.setText(ShopExpenses.getCash());
		
		
		RoundedPanel panel_1_4 = new RoundedPanel(34);
		panel_1_4.setBounds(10, 216, 481, 34);
		panel_1_5.add(panel_1_4);
		panel_1_4.setBackground(null);
		
		partyName = new promptTextField(34, "0");
		partyName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				UpdateTable();
				setTotalAgain();
			}
		});
		partyName.setBounds(245, 52, 246, 34);
		panel_1_5.add(partyName);
		partyName.setHorizontalAlignment(SwingConstants.CENTER);
		partyName.setAlignmentX(5.0f);
		
		JLabel lblDate_1 = new JLabel("Cash at Bank");
		lblDate_1.setBounds(21, 52, 230, 34);
		panel_1_5.add(lblDate_1);
		lblDate_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1.setForeground(Color.BLACK);
		lblDate_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1.setAlignmentX(0.5f);
		
		RoundedPanel panel_1 = new RoundedPanel(34);
		panel_1.setBounds(10, 52, 481, 34);
		panel_1_5.add(panel_1);
		panel_1.setBackground(null);
		
		JLabel lblDate_1_1 = new JLabel("Easy Paisa & Jazz Cash A/C");
		lblDate_1_1.setBounds(21, 93, 230, 34);
		panel_1_5.add(lblDate_1_1);
		lblDate_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_1.setForeground(Color.BLACK);
		lblDate_1_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_1.setAlignmentX(0.5f);
		
		address = new promptTextField(34, "0");
		address.setBounds(245, 93, 246, 34);
		address.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				UpdateTable();
				setTotalAgain();
			}
		});
		panel_1_5.add(address);
		address.setHorizontalAlignment(SwingConstants.CENTER);
		address.setAlignmentX(5.0f);
		
		RoundedPanel panel_1_1 = new RoundedPanel(34);
		panel_1_1.setBounds(10, 93, 481, 34);
		panel_1_5.add(panel_1_1);
		panel_1_1.setBackground(null);
		
		JLabel lblDate_1_2 = new JLabel("External Loan");
		lblDate_1_2.setBounds(21, 134, 230, 34);
		panel_1_5.add(lblDate_1_2);
		lblDate_1_2.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_2.setForeground(Color.BLACK);
		lblDate_1_2.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_2.setAlignmentX(0.5f);
		
		contactNumber = new promptTextField(34, "0");
		contactNumber.setBounds(245, 134, 246, 34);
		contactNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				UpdateTable();
				setTotalAgain();
			}
		});
		panel_1_5.add(contactNumber);
		contactNumber.setHorizontalAlignment(SwingConstants.CENTER);
		contactNumber.setAlignmentX(5.0f);
		
		RoundedPanel panel_1_2 = new RoundedPanel(34);
		panel_1_2.setBounds(10, 134, 481, 34);
		panel_1_5.add(panel_1_2);
		panel_1_2.setBackground(null);
		
		JLabel lblDate_1_2_1 = new JLabel("Cash in Hand");
		lblDate_1_2_1.setBounds(21, 175, 230, 34);
		panel_1_5.add(lblDate_1_2_1);
		lblDate_1_2_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDate_1_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate_1_2_1.setForeground(Color.BLACK);
		lblDate_1_2_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblDate_1_2_1.setAlignmentX(0.5f);
		
		openingBalance = new promptTextField(34, "");
		openingBalance.setBounds(245, 175, 246, 34);
		panel_1_5.add(openingBalance);
		openingBalance.setHorizontalAlignment(SwingConstants.CENTER);
		openingBalance.setAlignmentX(5.0f);
		openingBalance.setText(YearlyRecord.getCash());
		
		
		RoundedPanel panel_1_3 = new RoundedPanel(34);
		panel_1_3.setBounds(10, 175, 481, 34);
		panel_1_5.add(panel_1_3);
		panel_1_3.setBackground(null);
		
		JPanel finalTotalPanel_1_1 = new JPanel();
		finalTotalPanel_1_1.setLayout(null);
		finalTotalPanel_1_1.setBorder(new LineBorder(Color.WHITE));
		finalTotalPanel_1_1.setBackground(Color.DARK_GRAY);
		finalTotalPanel_1_1.setBounds(928, 465, 133, 34);
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
		openingBalance.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		contactNumber.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		address.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		partyName.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		accountNumber.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		
		RoundedPanel panel_1_6 = new RoundedPanel(34);
		panel_1_6.setBounds(10, 11, 481, 34);
		panel_1_5.add(panel_1_6);
		panel_1_6.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		panel_1_6.setBackground(null);
		
		
		createTables();
		setTable();
		UpdateTable();
		setTotalAgain();
		setTotal();
	}
}
