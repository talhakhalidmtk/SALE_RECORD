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

import start.Splash;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ExpenseChanged extends JFrame {

	private JPanel contentPane;
	JLabel date;
	static JTable table;JLabel collectionTotal;JLabel disbursementTotal ;static JLabel finalTotal;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExpenseChanged frame = new ExpenseChanged();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static String getCash() {
		return finalTotal.getText();
	}
	
	public void setTotal() {
		Connection con = Connect.getConnect();
		int collection = 0;
		int dispersement =0;
			int rows = table.getRowCount();
			for(int i =0;i<rows;i++) {
				String tableName = table.getValueAt(i,1).toString()+"Collection";
				String tableName1 = table.getValueAt(i,1).toString()+"Dispersement";
				
				try {
					String url = "SELECT SUM(Amount) FROM "+tableName;
					PreparedStatement pst = con.prepareStatement(url);
					ResultSet rs= pst.executeQuery();
					while(rs.next()) {
						collection+=rs.getInt(1);
					}
					
					String url1 = "SELECT SUM(Amount) FROM "+tableName1;
					PreparedStatement pst1 = con.prepareStatement(url1);
					ResultSet rs1= pst1.executeQuery();
					while(rs1.next()) {
						dispersement+=rs1.getInt(1);
					}
					
				}
				catch(Exception e1) {
					System.err.print(e1);
				}
			}
			
		
		
		
		collectionTotal.setText(String.valueOf(collection));
		disbursementTotal.setText(String.valueOf(dispersement));
	}
	
	
	void addTables() {
		try {
		Connection con =  Connect.getConnect();
		String url = "SELECT Count(*) FROM ShopExpenses";
		PreparedStatement pst = con.prepareStatement(url);
		ResultSet rs= pst.executeQuery();	
		while(rs.next()) {
				String url1 = "SELECT * FROM ShopExpenses ORDER BY Serial";
				PreparedStatement pst1 = con.prepareStatement(url1);
				ResultSet rs1= pst1.executeQuery();
				while(rs1.next()) {
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.addRow(new Object[] {rs1.getString(1),rs1.getString(2),rs1.getString(3) });
					
				}
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} 
	}
	
	

	public ExpenseChanged() {
		
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
						   new ExpenseChanged().setVisible(true);
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
		
		
		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(10, 107, 1020, 508);
		contentPane.add(scrollPane);
		table = new JTable();
		table.setToolTipText("double click for the Balance Expenditure Ledger");
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					new BalanceExpensesLedger(table.getValueAt(table.getSelectedRow(),1).toString()).setVisible(true);
					dispose();
				}
			}
		});
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Serial", "Month", "Balance"
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
		table.getColumnModel().getColumn(1).setPreferredWidth(250);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
			table.setFont(new Font("Century Gothic", Font.PLAIN, 16));
			//table.setTableHeader(null);
			scrollPane.setViewportView(table);
			table.setRowHeight(26);
				
			DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
			rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				
			UIDefaults defaults = UIManager.getLookAndFeelDefaults();
			defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
			scrollPane.setViewportView(table);

			
			table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
			table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

		
		
		JLabel lblStudentMart = new JLabel("Student Mart");
		
		Font font = new Font("Times New Roman", Font.BOLD, 30);
		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblStudentMart.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentMart.setFont(new Font("Times New Roman", Font.BOLD, 44));
		lblStudentMart.setBounds(0, 11, 1366, 34);
		lblStudentMart.setFont(font.deriveFont(attributes));
		contentPane.add(lblStudentMart);
		
		
		JLabel lblPartyTrialBalance = new JLabel("Shop Expenditure");
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
		
		JPanel panel_1_2_1_5_1_1_4_1_1 = new JPanel();
		panel_1_2_1_5_1_1_4_1_1.setLayout(null);
		panel_1_2_1_5_1_1_4_1_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_2_1_5_1_1_4_1_1.setBackground(Color.GRAY);
		panel_1_2_1_5_1_1_4_1_1.setBounds(1040, 107, 300, 100);
		contentPane.add(panel_1_2_1_5_1_1_4_1_1);
		
		collectionTotal = new JLabel("0");
		collectionTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		collectionTotal.setHorizontalAlignment(SwingConstants.CENTER);
		collectionTotal.setForeground(Color.BLACK);
		collectionTotal.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 24));
		collectionTotal.setAlignmentX(0.5f);
		collectionTotal.setBounds(0, 50, 300, 50);
		panel_1_2_1_5_1_1_4_1_1.add(collectionTotal);
		
		
		JLabel lblBooks = new JLabel("Shop Expenses:");
		lblBooks.setHorizontalTextPosition(SwingConstants.CENTER);
		lblBooks.setHorizontalAlignment(SwingConstants.LEFT);
		lblBooks.setForeground(Color.BLACK);
		lblBooks.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
		lblBooks.setAlignmentX(0.5f);
		lblBooks.setBounds(0, 0, 300, 34);
		panel_1_2_1_5_1_1_4_1_1.add(lblBooks);
		
		JPanel panel_1_2_1_5_1_1_4_1_2 = new JPanel();
		panel_1_2_1_5_1_1_4_1_2.setLayout(null);
		panel_1_2_1_5_1_1_4_1_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_2_1_5_1_1_4_1_2.setBackground(Color.GRAY);
		panel_1_2_1_5_1_1_4_1_2.setBounds(1040, 309, 300, 100);
		contentPane.add(panel_1_2_1_5_1_1_4_1_2);
		
		 disbursementTotal = new JLabel("0");
		disbursementTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		disbursementTotal.setHorizontalAlignment(SwingConstants.CENTER);
		disbursementTotal.setForeground(Color.BLACK);
		disbursementTotal.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 24));
		disbursementTotal.setAlignmentX(0.5f);
		disbursementTotal.setBounds(0, 50, 300, 50);
		panel_1_2_1_5_1_1_4_1_2.add(disbursementTotal);
		
		JLabel lblBags = new JLabel("Personal Expenses:");
		lblBags.setHorizontalTextPosition(SwingConstants.CENTER);
		lblBags.setHorizontalAlignment(SwingConstants.LEFT);
		lblBags.setForeground(Color.BLACK);
		lblBags.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
		lblBags.setAlignmentX(0.5f);
		lblBags.setBounds(0, 0, 185, 34);
		panel_1_2_1_5_1_1_4_1_2.add(lblBags);
		
		JPanel panel_1_2_1_5_1_1_4_1_3 = new JPanel();
		panel_1_2_1_5_1_1_4_1_3.setLayout(null);
		panel_1_2_1_5_1_1_4_1_3.setBorder(new LineBorder(Color.WHITE));
		panel_1_2_1_5_1_1_4_1_3.setBackground(Color.GRAY);
		panel_1_2_1_5_1_1_4_1_3.setBounds(1040, 515, 300, 100);
		contentPane.add(panel_1_2_1_5_1_1_4_1_3);
		
		finalTotal = new JLabel("0");
		finalTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		finalTotal.setHorizontalAlignment(SwingConstants.CENTER);
		finalTotal.setForeground(Color.BLACK);
		finalTotal.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 24));
		finalTotal.setAlignmentX(0.5f);
		finalTotal.setBounds(0, 50, 300, 50);
		panel_1_2_1_5_1_1_4_1_3.add(finalTotal);
		
		JLabel lblUniform = new JLabel("Consumed Expenses:");
		lblUniform.setHorizontalTextPosition(SwingConstants.CENTER);
		lblUniform.setHorizontalAlignment(SwingConstants.LEFT);
		lblUniform.setForeground(Color.BLACK);
		lblUniform.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
		lblUniform.setAlignmentX(0.5f);
		lblUniform.setBounds(0, 0, 224, 34);
		panel_1_2_1_5_1_1_4_1_3.add(lblUniform);
		
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
		
		JButton btnNewButton_1 =  new JButton("Refresh (F5)");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ExpenseChanged().setVisible(true);
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
		addTables();
		setTotal();
		finalTotal.setText(String.valueOf(Integer.valueOf(collectionTotal.getText())-Integer.valueOf(disbursementTotal.getText())));
		
	}
}
