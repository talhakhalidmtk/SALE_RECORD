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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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

public class ExpensesLedger extends JFrame {

private JPanel contentPane;
JLabel date;
static JTable table;JLabel finalTotal;
private JTextField description;
private JTextField amount;
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
	
	public void setTable() {
		
		Connection con = Connect.getConnect();
		
		String tableName = Expenses.getTableName();
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		int serial = 0;
		
		String url = "SELECT MAX(Serial) FROM "+tableName;
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();
			while(rs.next()) {
				serial = rs.getInt(1)+1;
				}
			
			model.addRow(new Object[] {table.getRowCount()+1,description.getText(),amount.getText()});
			
			PreparedStatement create1 = null;
			create1 = con.prepareStatement("INSERT INTO "+tableName+" (Serial, Description, Expenses) VALUES ('"+(serial)+"','"+description.getText()+"','"+amount.getText()+"') \r\n");	
			create1.executeUpdate();
			
			
			int rows = table.getRowCount();
			int total=0;
			for(int i =0; i<rows;i++) {
				total+=Integer.valueOf(table.getValueAt(i,2).toString());
			}
			
			finalTotal.setText(String.valueOf(total));

			String parentTable = Expenses.getParentTable().substring(3);
			
			PreparedStatement s1t = con.prepareStatement("UPDATE "+parentTable+" SET [Expenses]=? WHERE [Date]='"+Expenses.getDate()+"'");
			s1t.setInt(1, total);

		
			
			s1t.executeUpdate();
			
			
			
			
			DailySaleReport.setTable(DailySaleReport.table,amount,6);
			
			
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	
		
		description.setText("Description");
		amount.setText("Amount");
	}
	
	public  void putTable() {
		try {
			
			String tableName = Expenses.getTableName();
			
			
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
			dtm.setRowCount(0);
			Connection con =  Connect.getConnect();
			
			String url = "SELECT * FROM "+tableName+" ORDER BY [Serial] ASC";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			while(rs.next()) {
				model.addRow(new Object[] {rs.getString(1),rs.getString(2),rs.getString(3)});	
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}
	
	public void createTables() {
		try {
			
			String tableName = Expenses.getTableName();
			
			Connection con  = Connect.getConnect();
			

			PreparedStatement create4 = con.prepareStatement("IF OBJECT_ID(N'dbo."+tableName+"', N'U') IS NULL BEGIN\r\n"
					+ "CREATE TABLE "+tableName+" \r\n"
					+ "(Serial int, Description VARCHAR(100) , Expenses int)\r\n"
					+ "END;");
			create4.executeUpdate();

		}

			
			catch(Exception e) {//JOptionPane.showMessageDialog(null, e);
				System.out.println(e);
			} 
	}
	

	
	
	public ExpensesLedger() {
		
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
		scrollPane.setBounds(10, 107, 1330, 473);
		contentPane.add(scrollPane);
		table = new JTable();
		table.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 14));
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Serial", "Description", "Amount"
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
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(15);
			table.setFont(new Font("Century Gothic", Font.PLAIN, 14));
			//invoicesTable.setTableHeader(null);
			scrollPane.setViewportView(table);
			table.setRowHeight(23);
			
			
				
			DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
			rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			
			table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
			table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
			table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
				
			UIDefaults defaults = UIManager.getLookAndFeelDefaults();
			defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
			scrollPane.setViewportView(table);
		
		
		JLabel lblStudentMart = new JLabel("Student Mart");
		
		Font font = new Font("Times New Roman", Font.BOLD, 30);
		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblStudentMart.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentMart.setFont(new Font("Times New Roman", Font.BOLD, 44));
		lblStudentMart.setBounds(0, 11, 1340, 34);
		lblStudentMart.setFont(font.deriveFont(attributes));
		contentPane.add(lblStudentMart);
		
		
		JLabel lblPartyTrialBalance = new JLabel("Expenses Ledger");
		lblPartyTrialBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblPartyTrialBalance.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblPartyTrialBalance.setBounds(0, 44, 1340, 23);
		contentPane.add(lblPartyTrialBalance);
		
		JLabel monthName = new JLabel();
		monthName.setHorizontalAlignment(SwingConstants.CENTER);
		monthName.setFont(new Font("Times New Roman", Font.BOLD, 24));
		monthName.setBounds(0, 73, 1340, 23);
		contentPane.add(monthName);
		
		finalTotalPanel = new JPanel();
		finalTotalPanel.setLayout(null);
		finalTotalPanel.setBorder(new LineBorder(Color.WHITE));
		finalTotalPanel.setBackground(Color.DARK_GRAY);
		finalTotalPanel.setBounds(1119, 591, 221, 34);
		contentPane.add(finalTotalPanel);
		
		 finalTotal = new JLabel("0");
		finalTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		finalTotal.setHorizontalAlignment(SwingConstants.CENTER);
		finalTotal.setForeground(Color.WHITE);
		finalTotal.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 28));
		finalTotal.setAlignmentX(0.5f);
		finalTotal.setBounds(0, 0, 221, 34);
		finalTotalPanel.add(finalTotal);
		
		description = new promptTextField(34,"Description");
		description.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					setTable();
				}
			}
		});
		description.setHorizontalAlignment(SwingConstants.CENTER);
		description.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		description.setColumns(10);
		description.setBounds(new Rectangle(5, 0, 0, 0));
		description.setAlignmentX(5.0f);
		description.setBounds(10, 591, 200, 34);
		contentPane.add(description);
		
		amount = new promptTextField(34,"Amount");
		amount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					setTable();
			}
			}
		});
		amount.setHorizontalAlignment(SwingConstants.CENTER);
		amount.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		amount.setColumns(10);
		amount.setBounds(new Rectangle(5, 0, 0, 0));
		amount.setAlignmentX(5.0f);
		amount.setBounds(220, 591, 150, 34);
		contentPane.add(amount);
		
		JLabel dateLabel = new JLabel();
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		dateLabel.setBounds(0, 78, 1340, 23);
		dateLabel.setText(" Balance Expenses Ledger As On: "+date.getText());
		contentPane.add(dateLabel);
		
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
		
		
		createTables();
		putTable();
		int rows = table.getRowCount();
		int total=0;
		for(int i =0; i<rows;i++) {
			total+=Integer.valueOf(table.getValueAt(i,2).toString());
		}
		
		finalTotal.setText(String.valueOf(total));

			
	}
	
	
}
