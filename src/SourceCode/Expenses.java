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

import start.Splash;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JRadioButton;

public class Expenses extends JFrame {

	private JPanel contentPane;
	JLabel date;
	static JTable table;
	ButtonGroup group = new ButtonGroup();
	private JTable monthTable;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Expenses frame = new Expenses();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void setMonthTable() {
			try {
			
			Connection con  = Connect.getConnect();

			PreparedStatement create4 = con.prepareStatement("IF OBJECT_ID(N'dbo.MonthTable', N'U') IS NULL BEGIN   \r\n"
					+ "CREATE TABLE MonthTable \r\n"
					+ "(Serial int ,Month VARCHAR(100))\r\n"
					+ "END;");
			create4.executeUpdate();
			
			String rowName = date.getText().substring(date.getText().indexOf("-")+1).replaceAll("-"," ");
			
			String url = "SELECT MAX(Serial) FROM MonthTable";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			while(rs.next()) {
				con  = Connect.getConnect();
				PreparedStatement create = con.prepareStatement("BEGIN IF NOT EXISTS (SELECT * FROM MonthTable WHERE Month = '"+rowName+"')\r\n"
						+ "BEGIN\r\n"
						+ "   INSERT INTO MonthTable (Serial,Month) VALUES ("+(rs.getInt(1)+1)+",'"+rowName+"') \r\n"
						+ "END END");
						
				create.executeUpdate();

				}
			
			DefaultTableModel dtm = (DefaultTableModel) monthTable.getModel();
			dtm.setRowCount(0);
			String url1 = "SELECT * FROM MonthTable ORDER BY [Serial] ASC";
			PreparedStatement pst1 = con.prepareStatement(url1);
			ResultSet rs1= pst1.executeQuery();	
			DefaultTableModel model = (DefaultTableModel) monthTable.getModel();
				while(rs1.next()) {
					model.addRow(new Object[] {rs1.getString(2)});	
				}
			}
			
			catch(Exception e) {//JOptionPane.showMessageDialog(null, e);
				System.out.println(e);
			} 
	}
	
	public static String getDate() {
		return table.getValueAt(table.getSelectedRow(), 0).toString();
	}
	
	public static String getTableName() {
		return table.getValueAt(table.getSelectedRow(),1)+"_"+table.getValueAt(table.getSelectedRow(),0).toString().replaceAll(" ", "_")+"Expenses";
	}
	
	public static String getParentTable() {
		return table.getValueAt(table.getSelectedRow(),0).toString().replaceAll(" ", "_")+"Expenses";
		
	}
	
	public  void putTable() {
		try {
			
			String tableName = monthTable.getValueAt(monthTable.getSelectedRow(),0).toString().replaceAll(" ","_")+"Expenses";
			
			
			
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
			dtm.setRowCount(0);
			Connection con =  Connect.getConnect();
			
			String url = "SELECT * FROM "+tableName+" ORDER BY [Serial] ASC";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			while(rs.next()) {
				model.addRow(new Object[] {rs.getString(2),rs.getString(3),rs.getString(4)});	
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}
	
	
	public void createTables() {
		try {
			
			String tableName = monthTable.getValueAt(monthTable.getSelectedRow(),0).toString().replaceAll(" ","_")+"Expenses";
			String xyz = monthTable.getValueAt(monthTable.getSelectedRow(),0).toString();
			
			
			Connection con  = Connect.getConnect();
			String row = date.getText().substring(date.getText().indexOf(",")+1).substring(0,3).replaceAll("-"," ")+xyz;

			PreparedStatement create4 = con.prepareStatement("IF OBJECT_ID(N'dbo."+tableName+"', N'U') IS NULL BEGIN\r\n"
					+ "CREATE TABLE "+tableName+" \r\n"
					+ "(Serial int, Date VARCHAR(100) ,Day VARCHAR(100), Expenses int)\r\n"
					+ "END;");
			create4.executeUpdate();
			
			

			
			String url = "SELECT MAX(Serial) FROM "+tableName;
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();
			while(rs.next()) {
				PreparedStatement create = con.prepareStatement("BEGIN IF NOT EXISTS (SELECT * FROM "+tableName+" WHERE Date = '"+row+"')\r\n"
						+ "BEGIN\r\n"
						+ "   INSERT INTO "+tableName+" (Serial,Date,Day,Expenses) VALUES ('"+(rs.getInt(1)+1)+"','"+(row)+"','"+date.getText().substring(0,date.getText().indexOf(","))+"','0') \r\n"
						+ "END END");
						
				create.executeUpdate();
				}

		}

			
			catch(Exception e) {//JOptionPane.showMessageDialog(null, e);
				System.out.println(e);
			} 
	}
	

	public Expenses() {
		
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
						   new Expenses().setVisible(true);
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

		scrollPane.setBounds(229, 107, 1111, 468);
		contentPane.add(scrollPane);
		table = new JTable();
		table.setToolTipText("double click for the Expenses Ledger");
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					new ExpensesLedger().setVisible(true);
					dispose();
				}
			}
		});
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Date", "Day", "Expense"
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
		table.getColumnModel().getColumn(1).setPreferredWidth(15);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(15);
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
			table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
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
		
		
		JLabel lblPartyTrialBalance = new JLabel("Expenses");
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
				new Expenses().setVisible(true);
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
		
		JPanel finalTotalPanel = new JPanel();
		finalTotalPanel.setLayout(null);
		finalTotalPanel.setBorder(new LineBorder(Color.WHITE));
		finalTotalPanel.setBackground(Color.DARK_GRAY);
		finalTotalPanel.setBounds(1119, 581, 221, 34);
		contentPane.add(finalTotalPanel);
		
		JLabel total = new JLabel("0");
		total.setHorizontalTextPosition(SwingConstants.CENTER);
		total.setHorizontalAlignment(SwingConstants.CENTER);
		total.setForeground(Color.WHITE);
		total.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 28));
		total.setAlignmentX(0.5f);
		total.setBounds(0, 0, 221, 34);
		finalTotalPanel.add(total);

		
		
		

		table.getModel().addTableModelListener(
				new TableModelListener() 
				{

					@Override
					public void tableChanged(TableModelEvent e) {
						int tot = 0;
						
						for(int i =0;i<table.getRowCount();i++) {
							tot+= Integer.valueOf(table.getValueAt(i,2).toString());
						}
						
						total.setText(String.valueOf(tot));
						
					}
				});
		
		int tot = 0;
		
		for(int i =0;i<table.getRowCount();i++) {
			tot+= Integer.valueOf(table.getValueAt(i,2).toString());
		}
		
		total.setText(String.valueOf(tot));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 107, 209, 468);
		contentPane.add(scrollPane_1);
		
		monthTable = new JTable();
		monthTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					createTables();
			    	putTable();
				}
				
			}
		});
		monthTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Select Month"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		monthTable.getColumnModel().getColumn(0).setResizable(false);
		monthTable.setToolTipText("double click for the Expenses Ledger");
		monthTable.setRowHeight(26);
		monthTable.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		scrollPane_1.setViewportView(monthTable);

		
		setMonthTable();
		
		

	}
}
