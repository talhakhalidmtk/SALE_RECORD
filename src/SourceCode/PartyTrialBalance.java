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

import Extras.RoundedPanel;
import start.Splash;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PartyTrialBalance extends JFrame {

	private JPanel contentPane;
	JLabel date;
	static JTable table;
	static JLabel stationeryTotal, booksTotal, bagsTotal , uniformTotal, garmentsTotal,finalTotal;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PartyTrialBalance frame = new PartyTrialBalance();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public static String getPartyName() {
		return table.getValueAt(table.getSelectedRow(),2).toString();
	}
	public static String getPartyCategory() {
		return table.getValueAt(table.getSelectedRow(),1).toString();
	}
	
	public static String getTableName() {
		return "PartyTrialBalance";
	}
	
	public static void putTable() {
		try {
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
			dtm.setRowCount(0);
			String tableName = getTableName();
			Connection con =  Connect.getConnect();
			String url = "SELECT * FROM "+tableName+" ORDER BY Category";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			while(rs.next()) {
				model.addRow(new Object[] {table.getRowCount()+1,rs.getString(7),rs.getString(1),rs.getString(4)});	
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}

	}
	
	public static void setTotal() {
		Long sTotal=0l;
		Long bTotal=0l;
		Long baTotal=0l;
		Long uniTotal=0l;
		Long garTotal=0l;
		
		int rows = table.getRowCount();
		
		for(int i =0 ; i<rows;i++) {
			if(table.getValueAt(i,1).toString().toLowerCase().equals("stationery")) {
				sTotal+= Long.valueOf(table.getValueAt(i,3).toString());
			}else if(table.getValueAt(i,1).toString().toLowerCase().equals("books")) {
				bTotal+= Long.valueOf(table.getValueAt(i,3).toString());
			}else if(table.getValueAt(i,1).toString().toLowerCase().equals("bags")) {
				baTotal+= Long.valueOf(table.getValueAt(i,3).toString());
			}else if(table.getValueAt(i,1).toString().toLowerCase().equals("uniform")) {
				uniTotal+= Long.valueOf(table.getValueAt(i,3).toString());
			}else if(table.getValueAt(i,1).toString().toLowerCase().equals("garments")) {
				garTotal+= Long.valueOf(table.getValueAt(i,3).toString());
			}
		}
		
		stationeryTotal.setText(String.valueOf(sTotal));
		booksTotal.setText(String.valueOf(bTotal));
		bagsTotal.setText(String.valueOf(baTotal));
		uniformTotal.setText(String.valueOf(uniTotal));
		garmentsTotal.setText(String.valueOf(garTotal));
		finalTotal.setText(String.valueOf(sTotal+bTotal+baTotal+uniTotal+garTotal));
		
		
		
	}
	

	public PartyTrialBalance() {
		
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
					
				
					ActionListener action8=new ActionListener(){
						   public void actionPerformed(ActionEvent ae)
						   {
							   new ShopExpenses().setVisible(true);
								dispose();
						   }
						};
						getRootPane().registerKeyboardAction(action8,KeyStroke.getKeyStroke("F5"),JComponent.WHEN_IN_FOCUSED_WINDOW);
				
				
				ActionListener action3=new ActionListener(){
					   public void actionPerformed(ActionEvent ae)
					   {
						   new PartyTrialBalanceEssentials().setVisible(true);
							
					   }
					};
					getRootPane().registerKeyboardAction(action3,KeyStroke.getKeyStroke("F6"),JComponent.WHEN_IN_FOCUSED_WINDOW);
	
		
		
		
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
		scrollPane.setBounds(10, 107, 1330, 473);
		contentPane.add(scrollPane);
		table = new JTable();
		table.setToolTipText("double click to open party ledger");
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					new PartyLedger().setVisible(true);
					dispose();
				}
			}
		});
		
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Serial", "Party Type", "Party Name", "Party Balance"
				}
			) {
				Class[] columnTypes = new Class[] {
					String.class, Object.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(15);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(350);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setPreferredWidth(100);
			table.setFont(new Font("Century Gothic", Font.PLAIN, 16));
			//table.setTableHeader(null);
			scrollPane.setViewportView(table);
			table.setRowHeight(26);
				
			DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
			rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			
			table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
			table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
			//table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
			table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
				
			UIDefaults defaults = UIManager.getLookAndFeelDefaults();
			defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
			scrollPane.setViewportView(table);

		
		
		JLabel lblStudentMart = new JLabel("Student Mart");
		
		Font font = new Font("Times New Roman", Font.BOLD, 30);
		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblStudentMart.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentMart.setFont(new Font("Times New Roman", Font.BOLD, 44));
		lblStudentMart.setBounds(0, 0, 1366, 34);
		lblStudentMart.setFont(font.deriveFont(attributes));
		contentPane.add(lblStudentMart);
		
		
		JLabel lblPartyTrialBalance = new JLabel("Party Trial Balance");
		lblPartyTrialBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblPartyTrialBalance.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblPartyTrialBalance.setBounds(0, 33, 1366, 23);
		contentPane.add(lblPartyTrialBalance);
		
		JLabel lblPartyTrialBalance_1 = new JLabel("");
		lblPartyTrialBalance_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPartyTrialBalance_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPartyTrialBalance_1.setBounds(0, 62, 1366, 23);
		lblPartyTrialBalance_1.setText("As on "+date.getText());
		contentPane.add(lblPartyTrialBalance_1);
		
		JPanel panel_1_2_1_5_1_1_3_1 = new RoundedPanel(34);
		panel_1_2_1_5_1_1_3_1.setLayout(null);
		panel_1_2_1_5_1_1_3_1.setBackground(null);
		panel_1_2_1_5_1_1_3_1.setBounds(1126, 591, 214, 34);
		contentPane.add(panel_1_2_1_5_1_1_3_1);
		
		 finalTotal = new JLabel("0");
		finalTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		finalTotal.setHorizontalAlignment(SwingConstants.CENTER);
		finalTotal.setForeground(Color.BLACK);
		finalTotal.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 28));
		finalTotal.setAlignmentX(0.5f);
		finalTotal.setBounds(0, 0, 214, 34);
		panel_1_2_1_5_1_1_3_1.add(finalTotal);
		
		JPanel panel_1_2_1_5_1_1_4_1 = new RoundedPanel(34);
		panel_1_2_1_5_1_1_4_1.setLayout(null);
		panel_1_2_1_5_1_1_4_1.setBackground(null);
		panel_1_2_1_5_1_1_4_1.setBounds(10, 591, 213, 34);
		contentPane.add(panel_1_2_1_5_1_1_4_1);
		
		JLabel lblStationery = new JLabel(" Stationery:");
		lblStationery.setHorizontalTextPosition(SwingConstants.CENTER);
		lblStationery.setHorizontalAlignment(SwingConstants.LEFT);
		lblStationery.setForeground(Color.BLACK);
		lblStationery.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
		lblStationery.setAlignmentX(0.5f);
		lblStationery.setBounds(0, 0, 110, 34);
		panel_1_2_1_5_1_1_4_1.add(lblStationery);
		
		stationeryTotal = new JLabel("0");
		stationeryTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		stationeryTotal.setHorizontalAlignment(SwingConstants.CENTER);
		stationeryTotal.setForeground(Color.BLACK);
		stationeryTotal.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 24));
		stationeryTotal.setAlignmentX(0.5f);
		stationeryTotal.setBounds(120, 0, 95, 34);
		panel_1_2_1_5_1_1_4_1.add(stationeryTotal);
		
		JPanel panel_1_2_1_5_1_1_4_1_1 = new RoundedPanel(34);
		panel_1_2_1_5_1_1_4_1_1.setLayout(null);
		
		panel_1_2_1_5_1_1_4_1_1.setBackground(null);
		panel_1_2_1_5_1_1_4_1_1.setBounds(233, 591, 213, 34);
		contentPane.add(panel_1_2_1_5_1_1_4_1_1);
		
		booksTotal = new JLabel("0");
		booksTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		booksTotal.setHorizontalAlignment(SwingConstants.CENTER);
		booksTotal.setForeground(Color.BLACK);
		booksTotal.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 24));
		booksTotal.setAlignmentX(0.5f);
		booksTotal.setBounds(120, 0, 95, 34);
		panel_1_2_1_5_1_1_4_1_1.add(booksTotal);
		
		JLabel lblBooks = new JLabel(" Books:");
		lblBooks.setHorizontalTextPosition(SwingConstants.CENTER);
		lblBooks.setHorizontalAlignment(SwingConstants.LEFT);
		lblBooks.setForeground(Color.BLACK);
		lblBooks.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
		lblBooks.setAlignmentX(0.5f);
		lblBooks.setBounds(0, 0, 110, 34);
		panel_1_2_1_5_1_1_4_1_1.add(lblBooks);
		
		JPanel panel_1_2_1_5_1_1_4_1_2 = new RoundedPanel(34);
		panel_1_2_1_5_1_1_4_1_2.setLayout(null);
		panel_1_2_1_5_1_1_4_1_2.setBackground(null);
		panel_1_2_1_5_1_1_4_1_2.setBounds(456, 591, 213, 34);
		contentPane.add(panel_1_2_1_5_1_1_4_1_2);
		
		 bagsTotal = new JLabel("0");
		bagsTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		bagsTotal.setHorizontalAlignment(SwingConstants.CENTER);
		bagsTotal.setForeground(Color.BLACK);
		bagsTotal.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 24));
		bagsTotal.setAlignmentX(0.5f);
		bagsTotal.setBounds(120, 0, 95, 34);
		panel_1_2_1_5_1_1_4_1_2.add(bagsTotal);
		
		JLabel lblBags = new JLabel(" Bags:");
		lblBags.setHorizontalTextPosition(SwingConstants.CENTER);
		lblBags.setHorizontalAlignment(SwingConstants.LEFT);
		lblBags.setForeground(Color.BLACK);
		lblBags.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
		lblBags.setAlignmentX(0.5f);
		lblBags.setBounds(0, 0, 110, 34);
		panel_1_2_1_5_1_1_4_1_2.add(lblBags);
		
		JPanel panel_1_2_1_5_1_1_4_1_3 = new RoundedPanel(34);
		panel_1_2_1_5_1_1_4_1_3.setLayout(null);
		panel_1_2_1_5_1_1_4_1_3.setBackground(null);
		panel_1_2_1_5_1_1_4_1_3.setBounds(679, 591, 213, 34);
		contentPane.add(panel_1_2_1_5_1_1_4_1_3);
		
		uniformTotal = new JLabel("0");
		uniformTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		uniformTotal.setHorizontalAlignment(SwingConstants.CENTER);
		uniformTotal.setForeground(Color.BLACK);
		uniformTotal.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 24));
		uniformTotal.setAlignmentX(0.5f);
		uniformTotal.setBounds(120, 0, 95, 34);
		panel_1_2_1_5_1_1_4_1_3.add(uniformTotal);
		
		JLabel lblUniform = new JLabel(" Uniform:");
		lblUniform.setHorizontalTextPosition(SwingConstants.CENTER);
		lblUniform.setHorizontalAlignment(SwingConstants.LEFT);
		lblUniform.setForeground(Color.BLACK);
		lblUniform.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
		lblUniform.setAlignmentX(0.5f);
		lblUniform.setBounds(0, 0, 110, 34);
		panel_1_2_1_5_1_1_4_1_3.add(lblUniform);
		
		JPanel panel_1_2_1_5_1_1_4_1_3_1 = new RoundedPanel(34);
		panel_1_2_1_5_1_1_4_1_3_1.setLayout(null);
		panel_1_2_1_5_1_1_4_1_3_1.setBackground(null);
		panel_1_2_1_5_1_1_4_1_3_1.setBounds(903, 591, 213, 34);
		contentPane.add(panel_1_2_1_5_1_1_4_1_3_1);
		
		garmentsTotal = new JLabel("0");
		garmentsTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		garmentsTotal.setHorizontalAlignment(SwingConstants.CENTER);
		garmentsTotal.setForeground(Color.BLACK);
		garmentsTotal.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 24));
		garmentsTotal.setAlignmentX(0.5f);
		garmentsTotal.setBounds(120, 0, 94, 34);
		panel_1_2_1_5_1_1_4_1_3_1.add(garmentsTotal);
		
		JLabel lblGarments = new JLabel(" Garments:");
		lblGarments.setHorizontalTextPosition(SwingConstants.CENTER);
		lblGarments.setHorizontalAlignment(SwingConstants.LEFT);
		lblGarments.setForeground(Color.BLACK);
		lblGarments.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
		lblGarments.setAlignmentX(0.5f);
		lblGarments.setBounds(0, 0, 110, 34);
		panel_1_2_1_5_1_1_4_1_3_1.add(lblGarments);
		
		JButton btnAddParty = new JButton("Add Party (F6)");
		btnAddParty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PartyTrialBalanceEssentials().setVisible(true);
				
			}
		});
		btnAddParty.setForeground(Color.BLACK);
		btnAddParty.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnAddParty.setBackground(Color.WHITE);
		btnAddParty.setBounds(1126, 636, 214, 34);
		contentPane.add(btnAddParty);
		
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
		btnNewButton.setBounds(233, 636, 213, 34);
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
		btnNewButton_1.setBounds(903, 636, 213, 34);
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
		btnAnnualSaleRecord.setBounds(456, 636, 214, 34);
		contentPane.add(btnAnnualSaleRecord);
		
		JButton btnNewButton_2_1 = new JButton("Refresh (F4)");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PartyTrialBalance().setVisible(true);
				dispose();
			}
		});
		btnNewButton_2_1.setForeground(Color.BLACK);
		btnNewButton_2_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_2_1.setBackground(Color.WHITE);
		btnNewButton_2_1.setBounds(680, 636, 213, 34);
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
		
		
		
		putTable();
		setTotal();
		
	}
}
