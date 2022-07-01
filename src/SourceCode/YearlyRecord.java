package SourceCode;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import start.Splash;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.KeyStroke;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.Rectangle;

@SuppressWarnings("serial")
public class YearlyRecord extends JFrame {

	
	static JLabel time;
	static Connection con;
	static JLabel date;
	private JPanel contentPane;
	private static JTable table;
	static JLabel totalUniform;
	static JLabel totalTotal;
	static JLabel totalExpenses,totalExpenses_2,totalExpenses_1;
	static JLabel totalStationery,totalBook, totalBag,totalGarments;	
	 static JLabel stationeryPayment;
	 static JLabel booksPayment;
	 static JLabel uniformPayment;
	 static JLabel bagsPayment;
	 static JLabel garmentsPayment;
	 static JLabel totalPayment;
	 static JLabel changeStationery;
	 static JLabel changeBooks;
	 static JLabel changeUniform;
	 static JLabel changeBags;
	 static JLabel changeGarments;
	 static JLabel changeTotal;
	 private static JLabel stationerySales;
	 private static JLabel bagSales;
	 private static JLabel bookSales;
	 private static JLabel uniformSales;
	 private static JLabel expensesSales;
	 private static JLabel totalSales;
	 private static JLabel garmentsSales;
	 private static JLabel stationeryRatio;
	 private static JLabel booksRatio;
	 private static JLabel bagsRatio;
	 private static JLabel uniformRatio;
	 private static JLabel garmentsRatio;
	 private static JLabel totalRatio;
	 private static JLabel expensesRatio;
	 private static JLabel stationeryBalance;
	 private static JLabel booksBalance;
	 private static JLabel bagsBalance;
	 private static JLabel uniformBalance;
	 private static JLabel garmentsBalance;
	 private static JLabel totalBalance;
	 private static JLabel expensesBalance;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YearlyRecord frame = new YearlyRecord();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static String getCash() {
		return String.valueOf(Integer.valueOf(changeTotal.getText())-Integer.valueOf(totalExpenses.getText()));
		
	}
	
	public static String getReal() {
		String val = "";
		if (Integer.valueOf(totalExpenses_1.getText())<0){
			val =  totalExpenses_1.getText();
		}else {
			val = "0";
		}
		return val;
	}
	
	public static void setTotals() {
		int rows = table.getRowCount();
		long stationery = 0,book=0,bag=0,uniform=0,Garments=0,total=0,expense=0;
		
		
		for (int i =0; i<rows;i+=3) {
			stationery += Long.valueOf(table.getValueAt(i,1).toString());
			book += Long.valueOf(table.getValueAt(i,2).toString());
			bag += Long.valueOf(table.getValueAt(i,3).toString());
			uniform += Long.valueOf(table.getValueAt(i,4).toString());
			Garments += Long.valueOf(table.getValueAt(i,5).toString());
			total += Long.valueOf(table.getValueAt(i,6).toString());
			expense += Long.valueOf(table.getValueAt(i+1,7).toString());
		}
			totalStationery.setText(String.valueOf(stationery));
			totalBook.setText(String.valueOf(book));
			totalBag.setText(String.valueOf(bag));
			totalUniform.setText(String.valueOf(uniform));
			totalGarments.setText(String.valueOf(Garments));
			totalTotal.setText(String.valueOf(total));
			totalExpenses.setText(String.valueOf(expense));
			
		totalExpenses_2.setText(totalExpenses.getText());
		totalExpenses.setText(String.format("%.0f",((Double.valueOf(totalTotal.getText()))*10)/100));
		totalExpenses_1.setText(String.valueOf(Integer.valueOf(totalExpenses.getText())-Integer.valueOf(totalExpenses_2.getText())));
		changeStationery.setText(String.valueOf(Integer.valueOf(totalStationery.getText())-Integer.valueOf(stationeryPayment.getText())));
		changeBooks.setText(String.valueOf(Integer.valueOf(totalBook.getText())-Integer.valueOf(booksPayment.getText())));
		changeBags.setText(String.valueOf(Integer.valueOf(totalBag.getText())-Integer.valueOf(bagsPayment.getText())));
		changeUniform.setText(String.valueOf(Integer.valueOf(totalUniform.getText())-Integer.valueOf(uniformPayment.getText())));
		changeGarments.setText(String.valueOf(Integer.valueOf(totalGarments.getText())-Integer.valueOf(garmentsPayment.getText())));
		changeTotal.setText(String.valueOf(Integer.valueOf(totalTotal.getText())-Integer.valueOf(totalPayment.getText())));
		
		stationerySales.setText(String.format("%.0f", 100*(Double.valueOf(totalStationery.getText())/Double.valueOf(totalTotal.getText()))));
		bookSales.setText(String.format("%.0f", 100*(Double.valueOf(totalBook.getText())/Double.valueOf(totalTotal.getText()))));
		bagSales.setText(String.format("%.0f", 100*(Double.valueOf(totalBag.getText())/Double.valueOf(totalTotal.getText()))));
		uniformSales.setText(String.format("%.0f", 100*(Double.valueOf(totalUniform.getText())/Double.valueOf(totalTotal.getText()))));
		garmentsSales.setText(String.format("%.0f", 100*(Double.valueOf(totalGarments.getText())/Double.valueOf(totalTotal.getText()))));
		totalSales.setText("100%");
		expensesSales.setText("");
		
//		stationeryRatio.setText(String.format("%.2f",((Double.valueOf(stationerySales.getText())*Double.valueOf(totalExpenses.getText())/100))));
//		booksRatio.setText(String.format("%.2f", ((Double.valueOf(bookSales.getText())*Double.valueOf(totalExpenses.getText())/100))));
//		bagsRatio.setText(String.format("%.2f", ((Double.valueOf(bagSales.getText())*Double.valueOf(totalExpenses.getText())/100))));
//		uniformRatio.setText(String.format("%.2f",((Double.valueOf(uniformSales.getText())*Double.valueOf(totalExpenses.getText())/100))));
//		garmentsRatio.setText(String.format("%.2f",((Double.valueOf(garmentsSales.getText())*Double.valueOf(totalExpenses.getText())/100))));
//		totalRatio.setText("");
//		expensesRatio.setText(String.valueOf((Math.floor(Double.valueOf(stationeryRatio.getText())
//														+Double.valueOf(booksRatio.getText())
//														+Double.valueOf(bagsRatio.getText())
//														+Double.valueOf(uniformRatio.getText())
//														+Double.valueOf(garmentsRatio.getText()))
//				)));
		
		stationerySales.setText(stationerySales.getText()+" %");
		bookSales.setText(bookSales.getText()+" %");
		bagSales.setText(bagSales.getText()+" %");
		uniformSales.setText(uniformSales.getText()+" %");
		garmentsSales.setText(garmentsSales.getText()+" %");
		
//		stationeryBalance.setText(String.format("%.2f", Double.valueOf(changeStationery.getText())-Double.valueOf(stationeryRatio.getText())));
//		booksBalance.setText(String.format("%.2f", Double.valueOf(changeBooks.getText())-Double.valueOf(booksRatio.getText())));
//		bagsBalance.setText(String.format("%.2f", Double.valueOf(changeBags.getText())-Double.valueOf(bagsRatio.getText())));
//		uniformBalance.setText(String.format("%.2f", Double.valueOf(changeUniform.getText())-Double.valueOf(uniformRatio.getText())));
//		garmentsBalance.setText(String.format("%.2f", Double.valueOf(changeGarments.getText())-Double.valueOf(garmentsRatio.getText())));
//		totalBalance.setText(String.valueOf(Math.floor(Double.valueOf(stationeryBalance.getText())+
//											Double.valueOf(booksBalance.getText())+
//											Double.valueOf(bagsBalance.getText())+
//											Double.valueOf(uniformBalance.getText())+
//											Double.valueOf(garmentsBalance.getText())
//											)));
//		expensesBalance.setText(String.valueOf(Double.valueOf(changeTotal.getText())-Double.valueOf(totalExpenses.getText())));

	}
	
	
	
	public static void putTable() {
		
		String tableName1 = "";
		tableName1 = "Y2021"+tableName1;
		try {
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
			dtm.setRowCount(0);
			Connection con =  Connect.getConnect();
			String url = "SELECT * FROM "+tableName1+" ORDER BY Serial";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			while(rs.next()) {
				model.addRow(new Object[] {rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getString(8), String.format("%.0f",((Double.valueOf(rs.getString(8)))*10)/100)});	
				try {
					String url1 = "SELECT SUM(Stationery),SUM(Books),SUM(Bags),SUM(Uniform),SUM(Garments) FROM PaymentRecord WHERE Date LIKE '%"+rs.getString(2).toString()+"'";
					PreparedStatement pst1 = con.prepareStatement(url1);
					ResultSet rs1= pst1.executeQuery();	
					while(rs1.next()) {
						model.addRow(new Object[] {rs.getString(2)+"-Payments", rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4),rs1.getString(5), String.valueOf(Integer.valueOf(rs1.getString(1))+Integer.valueOf(rs1.getString(2))+Integer.valueOf(rs1.getString(3))+Integer.valueOf(rs1.getString(4))+Integer.valueOf(rs1.getString(5))), rs.getString(9)});	
						model.addRow(new Object[] {rs.getString(2)+"-Balance", String.valueOf(Integer.valueOf(rs.getString(3))-Integer.valueOf(rs1.getString(1))),String.valueOf(Integer.valueOf(rs.getString(4))-Integer.valueOf(rs1.getString(2))),String.valueOf(Integer.valueOf(rs.getString(5))-Integer.valueOf(rs1.getString(3))),String.valueOf(Integer.valueOf(rs.getString(6))-Integer.valueOf(rs1.getString(4))),String.valueOf(Integer.valueOf(rs.getString(7))-Integer.valueOf(rs1.getString(5))), String.valueOf(Integer.valueOf(rs.getString(8))-(Integer.valueOf(rs1.getString(1))+Integer.valueOf(rs1.getString(2))+Integer.valueOf(rs1.getString(3))+Integer.valueOf(rs1.getString(4))+Integer.valueOf(rs1.getString(5)))), String.valueOf(Integer.valueOf(String.format("%.0f",((Double.valueOf(rs.getString(8)))*10)/100))-Integer.valueOf(rs.getString(9)))});	
						
					}	
				}
				catch(Exception e) {
					System.out.println(e);
				}
				//model.addRow(new Object[] {"0","0","0","0","0","0","0","0"});
			}
		}
		catch(Exception e) {
			
		}
	
		String tableName = date.getText().substring(date.getText().length()-4);
		tableName = "Y"+tableName;
		try {
			Connection con =  Connect.getConnect();
			String url = "SELECT * FROM "+tableName+" ORDER BY Serial";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			while(rs.next()) {
				model.addRow(new Object[] {rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getString(8), String.format("%.0f",((Double.valueOf(rs.getString(8)))*10)/100)});	
				try {
					String url1 = "SELECT SUM(Stationery),SUM(Books),SUM(Bags),SUM(Uniform),SUM(Garments) FROM PaymentRecord WHERE Date LIKE '%"+rs.getString(2).toString()+"'";
					PreparedStatement pst1 = con.prepareStatement(url1);
					ResultSet rs1= pst1.executeQuery();	
					while(rs1.next()) {
						model.addRow(new Object[] {rs.getString(2)+"-Payments", rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4),rs1.getString(5), String.valueOf(Integer.valueOf(rs1.getString(1))+Integer.valueOf(rs1.getString(2))+Integer.valueOf(rs1.getString(3))+Integer.valueOf(rs1.getString(4))+Integer.valueOf(rs1.getString(5))), rs.getString(9)});	
						model.addRow(new Object[] {rs.getString(2)+"-Balance", String.valueOf(Integer.valueOf(rs.getString(3))-Integer.valueOf(rs1.getString(1))),String.valueOf(Integer.valueOf(rs.getString(4))-Integer.valueOf(rs1.getString(2))),String.valueOf(Integer.valueOf(rs.getString(5))-Integer.valueOf(rs1.getString(3))),String.valueOf(Integer.valueOf(rs.getString(6))-Integer.valueOf(rs1.getString(4))),String.valueOf(Integer.valueOf(rs.getString(7))-Integer.valueOf(rs1.getString(5))), String.valueOf(Integer.valueOf(rs.getString(8))-(Integer.valueOf(rs1.getString(1))+Integer.valueOf(rs1.getString(2))+Integer.valueOf(rs1.getString(3))+Integer.valueOf(rs1.getString(4))+Integer.valueOf(rs1.getString(5)))), String.valueOf(Integer.valueOf(String.format("%.0f",((Double.valueOf(rs.getString(8)))*10)/100))-Integer.valueOf(rs.getString(9)))});	
						
					}	
				}
				catch(Exception e) {
					System.out.println(e);
				}
				//model.addRow(new Object[] {"0","0","0","0","0","0","0","0"});
			}
		}
		catch(Exception e) {
			
		}
	}
	
	public static void setPayment() {
		try {
			Connection con =  Connect.getConnect();
			String url = "SELECT SUM(Stationery),SUM(Books),SUM(Bags),SUM(Uniform),SUM(Garments) FROM PaymentRecord WHERE Date LIKE '%2022' OR Date LIKE '%2021'";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			while(rs.next()) {
				stationeryPayment.setText(rs.getString(1));
				booksPayment.setText(rs.getString(2));
				bagsPayment.setText(rs.getString(3));
				uniformPayment.setText(rs.getString(4));
				garmentsPayment.setText(rs.getString(5));
			}
				totalPayment.setText(String.valueOf(Integer.valueOf(stationeryPayment.getText())+Integer.valueOf(booksPayment.getText())+Integer.valueOf(bagsPayment.getText())+Integer.valueOf(uniformPayment.getText())+Integer.valueOf(garmentsPayment.getText())));
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	
	public YearlyRecord() {
		
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
		
		
		ActionListener action3=new ActionListener(){
			   public void actionPerformed(ActionEvent ae)
			   {
				   new CashDeatils().setVisible(true);
				dispose();
			   }
			};
			getRootPane().registerKeyboardAction(action3,KeyStroke.getKeyStroke("F6"),JComponent.WHEN_IN_FOCUSED_WINDOW);
		
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
				
				
					ActionListener action9=new ActionListener(){
						   public void actionPerformed(ActionEvent ae)
						   {
							   new ShopExpenses().setVisible(true);
							dispose();
						   }
						};
						getRootPane().registerKeyboardAction(action9,KeyStroke.getKeyStroke("F5"),JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		setFont(new Font("Century Gothic", Font.PLAIN, 18));
		setUndecorated(true);
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();  
		setMaximizedBounds(env.getMaximumWindowBounds());  
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBackground(new Color(192, 192, 192));
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
	        
		
		
		time = new JLabel("Time:");
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
		scrollPane.setBounds(10, 11, 1330, 490);
		contentPane.add(scrollPane);
		
		table = new JTable() {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
	            Component comp = super.prepareRenderer(renderer, row, column);
	            Color three = Color.LIGHT_GRAY;
	            
	            if(!comp.getBackground().equals(getSelectionBackground())) {
	               Color c = null;
	               if(row%3==0) {
//	            	   c=one;
	            	   comp.setFont(new Font("Century Gothic", Font.BOLD + Font.ITALIC, 16));
	               }
	               if(row%3==1) {
//	            	   c=two;
	               }
	               if(row%3==2) {
	            	   c=three;
	               }
	               comp.setBackground(c);
	               c = null;
	            }
	            return comp;
	         }
		};
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Month", "Stationery", "Books", "Bags", "Uniform", "Gift Items", "Total Sale", "Expenses"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, String.class, String.class, Object.class
			
				
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(134);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(6).setResizable(false);
		table.getColumnModel().getColumn(7).setResizable(false);
		table.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		//table.setTableHeader(null);
		scrollPane.setViewportView(table);
		table.setRowHeight(table.getRowHeight()+8);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
		
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		//defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
		scrollPane.setViewportView(table);

		putTable();

		
		JPanel panel_1_4_6_1_1_1_5_1 = new JPanel();
		panel_1_4_6_1_1_1_5_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_1.setLayout(null);
		panel_1_4_6_1_1_1_5_1.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_1.setBounds(10, 502, 217, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_1);
		
		JLabel lblTotal = new JLabel(" Receipt:");
		lblTotal.setBounds(new Rectangle(10, 0, 0, 0));
		lblTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTotal.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotal.setForeground(Color.WHITE);
		lblTotal.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblTotal.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblTotal.setBounds(0, 0, 217, 30);
		panel_1_4_6_1_1_1_5_1.add(lblTotal);
		
		JPanel panel_1_4_6_1_1_1_5_4 = new JPanel();
		panel_1_4_6_1_1_1_5_4.setLayout(null);
		panel_1_4_6_1_1_1_5_4.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_4.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_4.setBounds(703, 502, 159, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_4);
		
		totalUniform = new JLabel("0");
		totalUniform.setHorizontalTextPosition(SwingConstants.CENTER);
		totalUniform.setHorizontalAlignment(SwingConstants.CENTER);
		totalUniform.setForeground(Color.WHITE);
		totalUniform.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		totalUniform.setAlignmentX(0.5f);
		totalUniform.setBounds(0, 0, 159, 30);
		panel_1_4_6_1_1_1_5_4.add(totalUniform);
		
		JPanel t = new JPanel();
		t.setLayout(null);
		t.setBorder(new LineBorder(Color.WHITE));
		t.setBackground(Color.DARK_GRAY);
		t.setBounds(1021, 502, 159, 30);
		contentPane.add(t);
		
		totalTotal = new JLabel("0");
		totalTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		totalTotal.setHorizontalAlignment(SwingConstants.CENTER);
		totalTotal.setForeground(Color.WHITE);
		totalTotal.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		totalTotal.setAlignmentX(0.5f);
		totalTotal.setBounds(0, 0, 159, 30);
		t.add(totalTotal);
		
		JPanel panel_1_4_6_1_1_1_5_6 = new JPanel();
		panel_1_4_6_1_1_1_5_6.setLayout(null);
		panel_1_4_6_1_1_1_5_6.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_6.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_6.setBounds(1180, 502, 160, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_6);
		
		totalExpenses = new JLabel("0");
		totalExpenses.setHorizontalTextPosition(SwingConstants.CENTER);
		totalExpenses.setHorizontalAlignment(SwingConstants.CENTER);
		totalExpenses.setForeground(Color.WHITE);
		totalExpenses.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		totalExpenses.setAlignmentX(0.5f);
		totalExpenses.setBounds(0, 0, 160, 30);
		panel_1_4_6_1_1_1_5_6.add(totalExpenses);
		
		JPanel t_1 = new JPanel();
		t_1.setLayout(null);
		t_1.setBorder(new LineBorder(Color.WHITE));
		t_1.setBackground(Color.DARK_GRAY);
		t_1.setBounds(862, 502, 159, 30);
		contentPane.add(t_1);
		
		 totalGarments = new JLabel("0");
		totalGarments.setHorizontalTextPosition(SwingConstants.CENTER);
		totalGarments.setHorizontalAlignment(SwingConstants.CENTER);
		totalGarments.setForeground(Color.WHITE);
		totalGarments.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		totalGarments.setAlignmentX(0.5f);
		totalGarments.setBounds(0, 0, 159, 30);
		t_1.add(totalGarments);
		
		JPanel panel_1_4_6_1_1_1_5_3 = new JPanel();
		panel_1_4_6_1_1_1_5_3.setLayout(null);
		panel_1_4_6_1_1_1_5_3.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_3.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_3.setBounds(544, 502, 159, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_3);
		
		totalBag = new JLabel("0");
		totalBag.setHorizontalTextPosition(SwingConstants.CENTER);
		totalBag.setHorizontalAlignment(SwingConstants.CENTER);
		totalBag.setForeground(Color.WHITE);
		totalBag.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		totalBag.setAlignmentX(0.5f);
		totalBag.setBounds(0, 0, 159, 30);
		panel_1_4_6_1_1_1_5_3.add(totalBag);
		
		JPanel panel_1_4_6_1_1_1_5_2 = new JPanel();
		panel_1_4_6_1_1_1_5_2.setBounds(385, 502, 159, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_2);
		panel_1_4_6_1_1_1_5_2.setLayout(null);
		panel_1_4_6_1_1_1_5_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_2.setBackground(Color.DARK_GRAY);
		
		totalBook = new JLabel("0");
		totalBook.setHorizontalTextPosition(SwingConstants.CENTER);
		totalBook.setHorizontalAlignment(SwingConstants.CENTER);
		totalBook.setForeground(Color.WHITE);
		totalBook.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		totalBook.setAlignmentX(0.5f);
		totalBook.setBounds(0, 0, 159, 30);
		panel_1_4_6_1_1_1_5_2.add(totalBook);
		
		JPanel panel_1_4_6_1_1_1_5 = new JPanel();
		panel_1_4_6_1_1_1_5.setBounds(227, 502, 158, 30);
		contentPane.add(panel_1_4_6_1_1_1_5);
		panel_1_4_6_1_1_1_5.setLayout(null);
		panel_1_4_6_1_1_1_5.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5.setBackground(Color.DARK_GRAY);
		
		totalStationery = new JLabel("0");
		totalStationery.setHorizontalTextPosition(SwingConstants.CENTER);
		totalStationery.setHorizontalAlignment(SwingConstants.CENTER);
		totalStationery.setForeground(Color.WHITE);
		totalStationery.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		totalStationery.setAlignmentX(0.5f);
		totalStationery.setBounds(0, 0, 158, 30);
		panel_1_4_6_1_1_1_5.add(totalStationery);
		
		JPanel panel_1_4_6_1_1_1_5_1_2 = new JPanel();
		panel_1_4_6_1_1_1_5_1_2.setLayout(null);
		panel_1_4_6_1_1_1_5_1_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_1_2.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_1_2.setBounds(10, 533, 217, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_1_2);
		
		JLabel lblPayments = new JLabel(" Payments:");
		lblPayments.setBounds(new Rectangle(5, 0, 0, 0));
		lblPayments.setBackground(SystemColor.activeCaption);
		lblPayments.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPayments.setHorizontalAlignment(SwingConstants.LEFT);
		lblPayments.setForeground(Color.WHITE);
		lblPayments.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblPayments.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblPayments.setBounds(0, 0, 217, 30);
		panel_1_4_6_1_1_1_5_1_2.add(lblPayments);
		
		JPanel panel_1_4_6_1_1_1_5_4_2 = new JPanel();
		panel_1_4_6_1_1_1_5_4_2.setLayout(null);
		panel_1_4_6_1_1_1_5_4_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_4_2.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_4_2.setBounds(703, 533, 159, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_4_2);
		
		uniformPayment = new JLabel("0");
		uniformPayment.setBackground(SystemColor.activeCaption);
		uniformPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		uniformPayment.setHorizontalAlignment(SwingConstants.CENTER);
		uniformPayment.setForeground(Color.WHITE);
		uniformPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		uniformPayment.setAlignmentX(0.5f);
		uniformPayment.setBounds(0, 0, 159, 30);
		panel_1_4_6_1_1_1_5_4_2.add(uniformPayment);
		
		JPanel t_3 = new JPanel();
		t_3.setLayout(null);
		t_3.setBorder(new LineBorder(Color.WHITE));
		t_3.setBackground(new Color(169, 169, 169));
		t_3.setBounds(1021, 533, 159, 30);
		contentPane.add(t_3);
		
		totalPayment = new JLabel("0");
		totalPayment.setBackground(SystemColor.activeCaption);
		totalPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		totalPayment.setHorizontalAlignment(SwingConstants.CENTER);
		totalPayment.setForeground(Color.WHITE);
		totalPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		totalPayment.setAlignmentX(0.5f);
		totalPayment.setBounds(0, 0, 159, 30);
		t_3.add(totalPayment);
		
		JPanel panel_1_4_6_1_1_1_5_6_2 = new JPanel();
		panel_1_4_6_1_1_1_5_6_2.setLayout(null);
		panel_1_4_6_1_1_1_5_6_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_6_2.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_6_2.setBounds(1180, 533, 160, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_6_2);
		
		totalExpenses_2 = new JLabel("0");
		totalExpenses_2.setBackground(SystemColor.activeCaption);
		totalExpenses_2.setHorizontalTextPosition(SwingConstants.CENTER);
		totalExpenses_2.setHorizontalAlignment(SwingConstants.CENTER);
		totalExpenses_2.setForeground(Color.WHITE);
		totalExpenses_2.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		totalExpenses_2.setAlignmentX(0.5f);
		totalExpenses_2.setBounds(0, 0, 160, 30);
		panel_1_4_6_1_1_1_5_6_2.add(totalExpenses_2);
		
		JPanel t_1_2 = new JPanel();
		t_1_2.setLayout(null);
		t_1_2.setBorder(new LineBorder(Color.WHITE));
		t_1_2.setBackground(new Color(169, 169, 169));
		t_1_2.setBounds(862, 533, 159, 30);
		contentPane.add(t_1_2);
		
		garmentsPayment = new JLabel("0");
		garmentsPayment.setBackground(SystemColor.activeCaption);
		garmentsPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		garmentsPayment.setHorizontalAlignment(SwingConstants.CENTER);
		garmentsPayment.setForeground(Color.WHITE);
		garmentsPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		garmentsPayment.setAlignmentX(0.5f);
		garmentsPayment.setBounds(0, 0, 159, 30);
		t_1_2.add(garmentsPayment);
		
		JPanel panel_1_4_6_1_1_1_5_3_2 = new JPanel();
		panel_1_4_6_1_1_1_5_3_2.setLayout(null);
		panel_1_4_6_1_1_1_5_3_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_3_2.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_3_2.setBounds(544, 533, 159, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_3_2);
		
		bagsPayment = new JLabel("0");
		bagsPayment.setBackground(SystemColor.activeCaption);
		bagsPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		bagsPayment.setHorizontalAlignment(SwingConstants.CENTER);
		bagsPayment.setForeground(Color.WHITE);
		bagsPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		bagsPayment.setAlignmentX(0.5f);
		bagsPayment.setBounds(0, 0, 159, 34);
		panel_1_4_6_1_1_1_5_3_2.add(bagsPayment);
		
		JPanel panel_1_4_6_1_1_1_5_2_2 = new JPanel();
		panel_1_4_6_1_1_1_5_2_2.setLayout(null);
		panel_1_4_6_1_1_1_5_2_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_2_2.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_2_2.setBounds(385, 533, 159, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_2_2);
		
		booksPayment = new JLabel("0");
		booksPayment.setBackground(SystemColor.activeCaption);
		booksPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		booksPayment.setHorizontalAlignment(SwingConstants.CENTER);
		booksPayment.setForeground(Color.WHITE);
		booksPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		booksPayment.setAlignmentX(0.5f);
		booksPayment.setBounds(0, 0, 159, 30);
		panel_1_4_6_1_1_1_5_2_2.add(booksPayment);
		
		JPanel panel_1_4_6_1_1_1_5_7 = new JPanel();
		panel_1_4_6_1_1_1_5_7.setLayout(null);
		panel_1_4_6_1_1_1_5_7.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_7.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_7.setBounds(227, 533, 158, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_7);
		
		stationeryPayment = new JLabel("0");
		stationeryPayment.setBackground(SystemColor.activeCaption);
		stationeryPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		stationeryPayment.setHorizontalAlignment(SwingConstants.CENTER);
		stationeryPayment.setForeground(Color.WHITE);
		stationeryPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		stationeryPayment.setAlignmentX(0.5f);
		stationeryPayment.setBounds(0, 0, 158, 30);
		panel_1_4_6_1_1_1_5_7.add(stationeryPayment);
		
		JPanel panel_1_4_6_1_1_1_5_1_1 = new JPanel();
		panel_1_4_6_1_1_1_5_1_1.setLayout(null);
		panel_1_4_6_1_1_1_5_1_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_1_1.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_1_1.setBounds(10, 564, 217, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_1_1);
		
		JLabel lblBalance = new JLabel(" Balance:");
		lblBalance.setBounds(new Rectangle(5, 0, 0, 0));
		lblBalance.setBackground(new Color(0, 128, 0));
		lblBalance.setHorizontalTextPosition(SwingConstants.CENTER);
		lblBalance.setHorizontalAlignment(SwingConstants.LEFT);
		lblBalance.setForeground(Color.WHITE);
		lblBalance.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblBalance.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblBalance.setBounds(0, 0, 217, 30);
		panel_1_4_6_1_1_1_5_1_1.add(lblBalance);
		
		JPanel panel_1_4_6_1_1_1_5_4_1 = new JPanel();
		panel_1_4_6_1_1_1_5_4_1.setLayout(null);
		panel_1_4_6_1_1_1_5_4_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_4_1.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_4_1.setBounds(703, 564, 159, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_4_1);
		
		changeUniform = new JLabel("0");
		changeUniform.setHorizontalTextPosition(SwingConstants.CENTER);
		changeUniform.setHorizontalAlignment(SwingConstants.CENTER);
		changeUniform.setForeground(Color.WHITE);
		changeUniform.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		changeUniform.setAlignmentX(0.5f);
		changeUniform.setBounds(0, 0, 159, 30);
		panel_1_4_6_1_1_1_5_4_1.add(changeUniform);
		
		JPanel t_2 = new JPanel();
		t_2.setLayout(null);
		t_2.setBorder(new LineBorder(Color.WHITE));
		t_2.setBackground(new Color(112, 128, 144));
		t_2.setBounds(1021, 564, 159, 30);
		contentPane.add(t_2);
		
		changeTotal = new JLabel("0");
		changeTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		changeTotal.setHorizontalAlignment(SwingConstants.CENTER);
		changeTotal.setForeground(Color.WHITE);
		changeTotal.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		changeTotal.setAlignmentX(0.5f);
		changeTotal.setBounds(0, 0, 159, 30);
		t_2.add(changeTotal);
		
		JPanel panel_1_4_6_1_1_1_5_6_1 = new JPanel();
		panel_1_4_6_1_1_1_5_6_1.setLayout(null);
		panel_1_4_6_1_1_1_5_6_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_6_1.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_6_1.setBounds(1180, 564, 160, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_6_1);
		
		 totalExpenses_1 = new JLabel("0");
		totalExpenses_1.setHorizontalTextPosition(SwingConstants.CENTER);
		totalExpenses_1.setHorizontalAlignment(SwingConstants.CENTER);
		totalExpenses_1.setForeground(Color.WHITE);
		totalExpenses_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		totalExpenses_1.setAlignmentX(0.5f);
		totalExpenses_1.setBounds(0, 0, 160, 30);
		panel_1_4_6_1_1_1_5_6_1.add(totalExpenses_1);
		
		JPanel t_1_1 = new JPanel();
		t_1_1.setLayout(null);
		t_1_1.setBorder(new LineBorder(Color.WHITE));
		t_1_1.setBackground(new Color(112, 128, 144));
		t_1_1.setBounds(862, 564, 159, 30);
		contentPane.add(t_1_1);
		
		changeGarments = new JLabel("0");
		changeGarments.setHorizontalTextPosition(SwingConstants.CENTER);
		changeGarments.setHorizontalAlignment(SwingConstants.CENTER);
		changeGarments.setForeground(Color.WHITE);
		changeGarments.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		changeGarments.setAlignmentX(0.5f);
		changeGarments.setBounds(0, 0, 159, 30);
		t_1_1.add(changeGarments);
		
		JPanel panel_1_4_6_1_1_1_5_3_1 = new JPanel();
		panel_1_4_6_1_1_1_5_3_1.setLayout(null);
		panel_1_4_6_1_1_1_5_3_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_3_1.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_3_1.setBounds(544, 564, 159, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_3_1);
		
		changeBags = new JLabel("0");
		changeBags.setHorizontalTextPosition(SwingConstants.CENTER);
		changeBags.setHorizontalAlignment(SwingConstants.CENTER);
		changeBags.setForeground(Color.WHITE);
		changeBags.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		changeBags.setAlignmentX(0.5f);
		changeBags.setBounds(0, 0, 159, 30);
		panel_1_4_6_1_1_1_5_3_1.add(changeBags);
		
		JPanel panel_1_4_6_1_1_1_5_2_1 = new JPanel();
		panel_1_4_6_1_1_1_5_2_1.setLayout(null);
		panel_1_4_6_1_1_1_5_2_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_2_1.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_2_1.setBounds(385, 564, 159, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_2_1);
		
		changeBooks = new JLabel("0");
		changeBooks.setHorizontalTextPosition(SwingConstants.CENTER);
		changeBooks.setHorizontalAlignment(SwingConstants.CENTER);
		changeBooks.setForeground(Color.WHITE);
		changeBooks.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		changeBooks.setAlignmentX(0.5f);
		changeBooks.setBounds(0, 0, 159, 30);
		panel_1_4_6_1_1_1_5_2_1.add(changeBooks);
		
		JPanel panel_1_4_6_1_1_1_5_5 = new JPanel();
		panel_1_4_6_1_1_1_5_5.setLayout(null);
		panel_1_4_6_1_1_1_5_5.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_5.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_5.setBounds(227, 564, 158, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_5);
		
		changeStationery = new JLabel("0");
		changeStationery.setBackground(new Color(0, 128, 0));
		changeStationery.setHorizontalTextPosition(SwingConstants.CENTER);
		changeStationery.setHorizontalAlignment(SwingConstants.CENTER);
		changeStationery.setForeground(Color.WHITE);
		changeStationery.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		changeStationery.setAlignmentX(0.5f);
		changeStationery.setBounds(0, 0, 158, 30);
		panel_1_4_6_1_1_1_5_5.add(changeStationery);
		
		JButton btnNewButton = new JButton("Monthly Record (F2)");
		btnNewButton.setToolTipText("open monthly sale record");
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
		btnNewButton_1.setToolTipText("shop expenses based on the months");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ShopExpenses().setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setBounds(904, 636, 213, 34);
		contentPane.add(btnNewButton_1);
		
		JButton btnAnnualSaleRecord = new JButton("Refresh (F3)");
		btnAnnualSaleRecord.setToolTipText("opens annual sale record");
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
		
		JButton btnNewButton_2_1 = new JButton("Party Trial Balance (F4)");
		btnNewButton_2_1.setToolTipText("opens party trial balances and their party ledgers");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PartyTrialBalance().setVisible(true);
				dispose();
			}
		});
		btnNewButton_2_1.setForeground(Color.BLACK);
		btnNewButton_2_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_2_1.setBackground(Color.WHITE);
		btnNewButton_2_1.setBounds(680, 636, 214, 34);
		contentPane.add(btnNewButton_2_1);
		
		JButton btnDailyRecordf = new JButton("Daily Record (F1)");
		btnDailyRecordf.setToolTipText("refresh the window");
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
		
		JButton btnNewButton_1_1 = new JButton("Cash (F6)");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CashDeatils().setVisible(true);
				dispose();
			}
		});
		btnNewButton_1_1.setForeground(Color.BLACK);
		btnNewButton_1_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_1_1.setBackground(Color.WHITE);
		btnNewButton_1_1.setBounds(1127, 636, 213, 34);
		contentPane.add(btnNewButton_1_1);
		
		JPanel panel_1_4_6_1_1_1_5_1_3 = new JPanel();
		panel_1_4_6_1_1_1_5_1_3.setLayout(null);
		panel_1_4_6_1_1_1_5_1_3.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_1_3.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_1_3.setBounds(10, 595, 217, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_1_3);
		
		JLabel lblTotal_1 = new JLabel(" Sales Balance Ratio:");
		lblTotal_1.setBounds(new Rectangle(5, 0, 0, 0));
		lblTotal_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTotal_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotal_1.setForeground(Color.WHITE);
		lblTotal_1.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblTotal_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblTotal_1.setBounds(0, 0, 217, 30);
		panel_1_4_6_1_1_1_5_1_3.add(lblTotal_1);
		
		JPanel panel_1_4_6_1_1_1_5_4_3 = new JPanel();
		panel_1_4_6_1_1_1_5_4_3.setLayout(null);
		panel_1_4_6_1_1_1_5_4_3.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_4_3.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_4_3.setBounds(703, 595, 159, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_4_3);
		
		uniformSales = new JLabel("0");
		uniformSales.setHorizontalTextPosition(SwingConstants.CENTER);
		uniformSales.setHorizontalAlignment(SwingConstants.CENTER);
		uniformSales.setForeground(Color.WHITE);
		uniformSales.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		uniformSales.setAlignmentX(0.5f);
		uniformSales.setBounds(0, 0, 159, 30);
		panel_1_4_6_1_1_1_5_4_3.add(uniformSales);
		
		JPanel t_4 = new JPanel();
		t_4.setLayout(null);
		t_4.setBorder(new LineBorder(Color.WHITE));
		t_4.setBackground(Color.DARK_GRAY);
		t_4.setBounds(1021, 595, 159, 30);
		contentPane.add(t_4);
		
		totalSales = new JLabel("0");
		totalSales.setHorizontalTextPosition(SwingConstants.CENTER);
		totalSales.setHorizontalAlignment(SwingConstants.CENTER);
		totalSales.setForeground(Color.WHITE);
		totalSales.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		totalSales.setAlignmentX(0.5f);
		totalSales.setBounds(0, 0, 159, 30);
		t_4.add(totalSales);
		
		JPanel panel_1_4_6_1_1_1_5_6_3 = new JPanel();
		panel_1_4_6_1_1_1_5_6_3.setLayout(null);
		panel_1_4_6_1_1_1_5_6_3.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_6_3.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_6_3.setBounds(1180, 595, 160, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_6_3);
		
		expensesSales = new JLabel("0");
		expensesSales.setHorizontalTextPosition(SwingConstants.CENTER);
		expensesSales.setHorizontalAlignment(SwingConstants.CENTER);
		expensesSales.setForeground(Color.WHITE);
		expensesSales.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		expensesSales.setAlignmentX(0.5f);
		expensesSales.setBounds(0, 0, 160, 30);
		panel_1_4_6_1_1_1_5_6_3.add(expensesSales);
		
		JPanel t_1_3 = new JPanel();
		t_1_3.setLayout(null);
		t_1_3.setBorder(new LineBorder(Color.WHITE));
		t_1_3.setBackground(Color.DARK_GRAY);
		t_1_3.setBounds(862, 595, 159, 30);
		contentPane.add(t_1_3);
		
		garmentsSales = new JLabel("0");
		garmentsSales.setHorizontalTextPosition(SwingConstants.CENTER);
		garmentsSales.setHorizontalAlignment(SwingConstants.CENTER);
		garmentsSales.setForeground(Color.WHITE);
		garmentsSales.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		garmentsSales.setAlignmentX(0.5f);
		garmentsSales.setBounds(0, 0, 159, 30);
		t_1_3.add(garmentsSales);
		
		JPanel panel_1_4_6_1_1_1_5_3_3 = new JPanel();
		panel_1_4_6_1_1_1_5_3_3.setLayout(null);
		panel_1_4_6_1_1_1_5_3_3.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_3_3.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_3_3.setBounds(544, 595, 159, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_3_3);
		
		bagSales = new JLabel("0");
		bagSales.setHorizontalTextPosition(SwingConstants.CENTER);
		bagSales.setHorizontalAlignment(SwingConstants.CENTER);
		bagSales.setForeground(Color.WHITE);
		bagSales.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		bagSales.setAlignmentX(0.5f);
		bagSales.setBounds(0, 0, 159, 30);
		panel_1_4_6_1_1_1_5_3_3.add(bagSales);
		
		JPanel panel_1_4_6_1_1_1_5_2_3 = new JPanel();
		panel_1_4_6_1_1_1_5_2_3.setLayout(null);
		panel_1_4_6_1_1_1_5_2_3.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_2_3.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_2_3.setBounds(385, 595, 159, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_2_3);
		
		bookSales = new JLabel("0");
		bookSales.setHorizontalTextPosition(SwingConstants.CENTER);
		bookSales.setHorizontalAlignment(SwingConstants.CENTER);
		bookSales.setForeground(Color.WHITE);
		bookSales.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		bookSales.setAlignmentX(0.5f);
		bookSales.setBounds(0, 0, 159, 30);
		panel_1_4_6_1_1_1_5_2_3.add(bookSales);
		
		JPanel panel_1_4_6_1_1_1_5_8 = new JPanel();
		panel_1_4_6_1_1_1_5_8.setLayout(null);
		panel_1_4_6_1_1_1_5_8.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_8.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_8.setBounds(227, 595, 158, 30);
		contentPane.add(panel_1_4_6_1_1_1_5_8);
		
		stationerySales = new JLabel("0");
		stationerySales.setHorizontalTextPosition(SwingConstants.CENTER);
		stationerySales.setHorizontalAlignment(SwingConstants.CENTER);
		stationerySales.setForeground(Color.WHITE);
		stationerySales.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
		stationerySales.setAlignmentX(0.5f);
		stationerySales.setBounds(0, 0, 158, 30);
		panel_1_4_6_1_1_1_5_8.add(stationerySales);
		
//		JPanel panel_1_4_6_1_1_1_5_1_2_1 = new JPanel();
//		panel_1_4_6_1_1_1_5_1_2_1.setLayout(null);
//		panel_1_4_6_1_1_1_5_1_2_1.setBorder(new LineBorder(Color.WHITE));
//		panel_1_4_6_1_1_1_5_1_2_1.setBackground(new Color(169, 169, 169));
//		panel_1_4_6_1_1_1_5_1_2_1.setBounds(10, 574, 217, 30);
//		contentPane.add(panel_1_4_6_1_1_1_5_1_2_1);
	
		
//		JLabel lblPayments_1 = new JLabel(" Exp. Ratio Exemptions:");
//		lblPayments_1.setBounds(new Rectangle(5, 0, 0, 0));
//		lblPayments_1.setHorizontalTextPosition(SwingConstants.CENTER);
//		lblPayments_1.setHorizontalAlignment(SwingConstants.LEFT);
//		lblPayments_1.setForeground(Color.WHITE);
//		lblPayments_1.setFont(new Font("Century Gothic", Font.BOLD, 17));
//		lblPayments_1.setBackground(SystemColor.activeCaption);
//		lblPayments_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
//		lblPayments_1.setBounds(0, 0, 217, 30);
//		panel_1_4_6_1_1_1_5_1_2_1.add(lblPayments_1);
		
//		JPanel panel_1_4_6_1_1_1_5_4_2_1 = new JPanel();
//		panel_1_4_6_1_1_1_5_4_2_1.setLayout(null);
//		panel_1_4_6_1_1_1_5_4_2_1.setBorder(new LineBorder(Color.WHITE));
//		panel_1_4_6_1_1_1_5_4_2_1.setBackground(new Color(169, 169, 169));
//		panel_1_4_6_1_1_1_5_4_2_1.setBounds(703, 574, 159, 30);
//		contentPane.add(panel_1_4_6_1_1_1_5_4_2_1);
		
//		uniformRatio = new JLabel("0");
//		uniformRatio.setHorizontalTextPosition(SwingConstants.CENTER);
//		uniformRatio.setHorizontalAlignment(SwingConstants.CENTER);
//		uniformRatio.setForeground(Color.WHITE);
//		uniformRatio.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
//		uniformRatio.setBackground(SystemColor.activeCaption);
//		uniformRatio.setAlignmentX(0.5f);
//		uniformRatio.setBounds(0, 0, 159, 30);
//		panel_1_4_6_1_1_1_5_4_2_1.add(uniformRatio);
//		
//		JPanel t_3_1 = new JPanel();
//		t_3_1.setLayout(null);
//		t_3_1.setBorder(new LineBorder(Color.WHITE));
//		t_3_1.setBackground(new Color(169, 169, 169));
//		t_3_1.setBounds(1021, 574, 159, 30);
//		contentPane.add(t_3_1);
//		
//		totalRatio = new JLabel("0");
//		totalRatio.setHorizontalTextPosition(SwingConstants.CENTER);
//		totalRatio.setHorizontalAlignment(SwingConstants.CENTER);
//		totalRatio.setForeground(Color.WHITE);
//		totalRatio.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
//		totalRatio.setBackground(SystemColor.activeCaption);
//		totalRatio.setAlignmentX(0.5f);
//		totalRatio.setBounds(0, 0, 159, 30);
//		t_3_1.add(totalRatio);
//		
//		JPanel panel_1_4_6_1_1_1_5_6_2_1 = new JPanel();
//		panel_1_4_6_1_1_1_5_6_2_1.setLayout(null);
//		panel_1_4_6_1_1_1_5_6_2_1.setBorder(new LineBorder(Color.WHITE));
//		panel_1_4_6_1_1_1_5_6_2_1.setBackground(new Color(169, 169, 169));
//		panel_1_4_6_1_1_1_5_6_2_1.setBounds(1180, 574, 160, 30);
//		contentPane.add(panel_1_4_6_1_1_1_5_6_2_1);
//		
//		expensesRatio = new JLabel("0");
//		expensesRatio.setHorizontalTextPosition(SwingConstants.CENTER);
//		expensesRatio.setHorizontalAlignment(SwingConstants.CENTER);
//		expensesRatio.setForeground(Color.WHITE);
//		expensesRatio.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
//		expensesRatio.setBackground(SystemColor.activeCaption);
//		expensesRatio.setAlignmentX(0.5f);
//		expensesRatio.setBounds(0, 0, 160, 30);
//		panel_1_4_6_1_1_1_5_6_2_1.add(expensesRatio);
//		
//		JPanel t_1_2_1 = new JPanel();
//		t_1_2_1.setLayout(null);
//		t_1_2_1.setBorder(new LineBorder(Color.WHITE));
//		t_1_2_1.setBackground(new Color(169, 169, 169));
//		t_1_2_1.setBounds(862, 574, 159, 30);
//		contentPane.add(t_1_2_1);
//		
//		garmentsRatio = new JLabel("0");
//		garmentsRatio.setHorizontalTextPosition(SwingConstants.CENTER);
//		garmentsRatio.setHorizontalAlignment(SwingConstants.CENTER);
//		garmentsRatio.setForeground(Color.WHITE);
//		garmentsRatio.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
//		garmentsRatio.setBackground(SystemColor.activeCaption);
//		garmentsRatio.setAlignmentX(0.5f);
//		garmentsRatio.setBounds(0, 0, 159, 30);
//		t_1_2_1.add(garmentsRatio);
//		
//		JPanel panel_1_4_6_1_1_1_5_3_2_1 = new JPanel();
//		panel_1_4_6_1_1_1_5_3_2_1.setLayout(null);
//		panel_1_4_6_1_1_1_5_3_2_1.setBorder(new LineBorder(Color.WHITE));
//		panel_1_4_6_1_1_1_5_3_2_1.setBackground(new Color(169, 169, 169));
//		panel_1_4_6_1_1_1_5_3_2_1.setBounds(544, 574, 159, 30);
//		contentPane.add(panel_1_4_6_1_1_1_5_3_2_1);
//		
//		bagsRatio = new JLabel("0");
//		bagsRatio.setHorizontalTextPosition(SwingConstants.CENTER);
//		bagsRatio.setHorizontalAlignment(SwingConstants.CENTER);
//		bagsRatio.setForeground(Color.WHITE);
//		bagsRatio.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
//		bagsRatio.setBackground(SystemColor.activeCaption);
//		bagsRatio.setAlignmentX(0.5f);
//		bagsRatio.setBounds(0, 0, 159, 30);
//		panel_1_4_6_1_1_1_5_3_2_1.add(bagsRatio);
//		
//		JPanel panel_1_4_6_1_1_1_5_2_2_1 = new JPanel();
//		panel_1_4_6_1_1_1_5_2_2_1.setLayout(null);
//		panel_1_4_6_1_1_1_5_2_2_1.setBorder(new LineBorder(Color.WHITE));
//		panel_1_4_6_1_1_1_5_2_2_1.setBackground(new Color(169, 169, 169));
//		panel_1_4_6_1_1_1_5_2_2_1.setBounds(385, 574, 159, 30);
//		contentPane.add(panel_1_4_6_1_1_1_5_2_2_1);
//		
//		booksRatio = new JLabel("0");
//		booksRatio.setHorizontalTextPosition(SwingConstants.CENTER);
//		booksRatio.setHorizontalAlignment(SwingConstants.CENTER);
//		booksRatio.setForeground(Color.WHITE);
//		booksRatio.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
//		booksRatio.setBackground(SystemColor.activeCaption);
//		booksRatio.setAlignmentX(0.5f);
//		booksRatio.setBounds(0, 0, 159, 30);
//		panel_1_4_6_1_1_1_5_2_2_1.add(booksRatio);
//		
//		JPanel panel_1_4_6_1_1_1_5_7_1 = new JPanel();
//		panel_1_4_6_1_1_1_5_7_1.setLayout(null);
//		panel_1_4_6_1_1_1_5_7_1.setBorder(new LineBorder(Color.WHITE));
//		panel_1_4_6_1_1_1_5_7_1.setBackground(new Color(169, 169, 169));
//		panel_1_4_6_1_1_1_5_7_1.setBounds(227, 574, 158, 30);
//		contentPane.add(panel_1_4_6_1_1_1_5_7_1);
//		
//		stationeryRatio = new JLabel("0");
//		stationeryRatio.setHorizontalTextPosition(SwingConstants.CENTER);
//		stationeryRatio.setHorizontalAlignment(SwingConstants.CENTER);
//		stationeryRatio.setForeground(Color.WHITE);
//		stationeryRatio.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
//		stationeryRatio.setBackground(SystemColor.activeCaption);
//		stationeryRatio.setAlignmentX(0.5f);
//		stationeryRatio.setBounds(0, 0, 158, 30);
//		panel_1_4_6_1_1_1_5_7_1.add(stationeryRatio);
//		
//		JPanel panel_1_4_6_1_1_1_5_1_1_1 = new JPanel();
//		panel_1_4_6_1_1_1_5_1_1_1.setLayout(null);
//		panel_1_4_6_1_1_1_5_1_1_1.setBorder(new LineBorder(Color.WHITE));
//		panel_1_4_6_1_1_1_5_1_1_1.setBackground(new Color(112, 128, 144));
//		panel_1_4_6_1_1_1_5_1_1_1.setBounds(10, 605, 217, 30);
//		contentPane.add(panel_1_4_6_1_1_1_5_1_1_1);
//		
//		JLabel lblBalance_1 = new JLabel(" Balance After Expenses:");
//		lblBalance_1.setBounds(new Rectangle(5, 0, 0, 0));
//		lblBalance_1.setHorizontalTextPosition(SwingConstants.CENTER);
//		lblBalance_1.setHorizontalAlignment(SwingConstants.LEFT);
//		lblBalance_1.setForeground(Color.WHITE);
//		lblBalance_1.setFont(new Font("Century Gothic", Font.BOLD, 17));
//		lblBalance_1.setBackground(new Color(0, 128, 0));
//		lblBalance_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
//		lblBalance_1.setBounds(0, 0, 217, 30);
//		panel_1_4_6_1_1_1_5_1_1_1.add(lblBalance_1);
//		
//		JPanel panel_1_4_6_1_1_1_5_4_1_1 = new JPanel();
//		panel_1_4_6_1_1_1_5_4_1_1.setLayout(null);
//		panel_1_4_6_1_1_1_5_4_1_1.setBorder(new LineBorder(Color.WHITE));
//		panel_1_4_6_1_1_1_5_4_1_1.setBackground(new Color(112, 128, 144));
//		panel_1_4_6_1_1_1_5_4_1_1.setBounds(703, 605, 159, 30);
//		contentPane.add(panel_1_4_6_1_1_1_5_4_1_1);
//		
//		uniformBalance = new JLabel("0");
//		uniformBalance.setHorizontalTextPosition(SwingConstants.CENTER);
//		uniformBalance.setHorizontalAlignment(SwingConstants.CENTER);
//		uniformBalance.setForeground(Color.WHITE);
//		uniformBalance.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
//		uniformBalance.setAlignmentX(0.5f);
//		uniformBalance.setBounds(0, 0, 159, 30);
//		panel_1_4_6_1_1_1_5_4_1_1.add(uniformBalance);
//		
//		JPanel t_2_1 = new JPanel();
//		t_2_1.setLayout(null);
//		t_2_1.setBorder(new LineBorder(Color.WHITE));
//		t_2_1.setBackground(new Color(112, 128, 144));
//		t_2_1.setBounds(1021, 605, 159, 30);
//		contentPane.add(t_2_1);
//		
//		totalBalance = new JLabel("0");
//		totalBalance.setHorizontalTextPosition(SwingConstants.CENTER);
//		totalBalance.setHorizontalAlignment(SwingConstants.CENTER);
//		totalBalance.setForeground(Color.WHITE);
//		totalBalance.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
//		totalBalance.setAlignmentX(0.5f);
//		totalBalance.setBounds(0, 0, 159, 30);
//		t_2_1.add(totalBalance);
//		
//		JPanel panel_1_4_6_1_1_1_5_6_1_1 = new JPanel();
//		panel_1_4_6_1_1_1_5_6_1_1.setLayout(null);
//		panel_1_4_6_1_1_1_5_6_1_1.setBorder(new LineBorder(Color.WHITE));
//		panel_1_4_6_1_1_1_5_6_1_1.setBackground(new Color(112, 128, 144));
//		panel_1_4_6_1_1_1_5_6_1_1.setBounds(1180, 605, 160, 30);
//		contentPane.add(panel_1_4_6_1_1_1_5_6_1_1);
//		
//		expensesBalance = new JLabel("0");
//		expensesBalance.setHorizontalTextPosition(SwingConstants.CENTER);
//		expensesBalance.setHorizontalAlignment(SwingConstants.CENTER);
//		expensesBalance.setForeground(Color.WHITE);
//		expensesBalance.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
//		expensesBalance.setAlignmentX(0.5f);
//		expensesBalance.setBounds(0, 0, 160, 30);
//		panel_1_4_6_1_1_1_5_6_1_1.add(expensesBalance);
//		
//		JPanel t_1_1_1 = new JPanel();
//		t_1_1_1.setLayout(null);
//		t_1_1_1.setBorder(new LineBorder(Color.WHITE));
//		t_1_1_1.setBackground(new Color(112, 128, 144));
//		t_1_1_1.setBounds(862, 605, 159, 30);
//		contentPane.add(t_1_1_1);
//		
//		garmentsBalance = new JLabel("0");
//		garmentsBalance.setHorizontalTextPosition(SwingConstants.CENTER);
//		garmentsBalance.setHorizontalAlignment(SwingConstants.CENTER);
//		garmentsBalance.setForeground(Color.WHITE);
//		garmentsBalance.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
//		garmentsBalance.setAlignmentX(0.5f);
//		garmentsBalance.setBounds(0, 0, 159, 30);
//		t_1_1_1.add(garmentsBalance);
//		
//		JPanel panel_1_4_6_1_1_1_5_3_1_1 = new JPanel();
//		panel_1_4_6_1_1_1_5_3_1_1.setLayout(null);
//		panel_1_4_6_1_1_1_5_3_1_1.setBorder(new LineBorder(Color.WHITE));
//		panel_1_4_6_1_1_1_5_3_1_1.setBackground(new Color(112, 128, 144));
//		panel_1_4_6_1_1_1_5_3_1_1.setBounds(544, 605, 159, 30);
//		contentPane.add(panel_1_4_6_1_1_1_5_3_1_1);
//		
//		bagsBalance = new JLabel("0");
//		bagsBalance.setHorizontalTextPosition(SwingConstants.CENTER);
//		bagsBalance.setHorizontalAlignment(SwingConstants.CENTER);
//		bagsBalance.setForeground(Color.WHITE);
//		bagsBalance.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
//		bagsBalance.setAlignmentX(0.5f);
//		bagsBalance.setBounds(0, 0, 159, 30);
//		panel_1_4_6_1_1_1_5_3_1_1.add(bagsBalance);
//		
//		JPanel panel_1_4_6_1_1_1_5_2_1_1 = new JPanel();
//		panel_1_4_6_1_1_1_5_2_1_1.setLayout(null);
//		panel_1_4_6_1_1_1_5_2_1_1.setBorder(new LineBorder(Color.WHITE));
//		panel_1_4_6_1_1_1_5_2_1_1.setBackground(new Color(112, 128, 144));
//		panel_1_4_6_1_1_1_5_2_1_1.setBounds(385, 605, 159, 30);
//		contentPane.add(panel_1_4_6_1_1_1_5_2_1_1);
//		
//		booksBalance = new JLabel("0");
//		booksBalance.setHorizontalTextPosition(SwingConstants.CENTER);
//		booksBalance.setHorizontalAlignment(SwingConstants.CENTER);
//		booksBalance.setForeground(Color.WHITE);
//		booksBalance.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
//		booksBalance.setAlignmentX(0.5f);
//		booksBalance.setBounds(0, 0, 159, 30);
//		panel_1_4_6_1_1_1_5_2_1_1.add(booksBalance);
//		
//		JPanel panel_1_4_6_1_1_1_5_5_1 = new JPanel();
//		panel_1_4_6_1_1_1_5_5_1.setLayout(null);
//		panel_1_4_6_1_1_1_5_5_1.setBorder(new LineBorder(Color.WHITE));
//		panel_1_4_6_1_1_1_5_5_1.setBackground(new Color(112, 128, 144));
//		panel_1_4_6_1_1_1_5_5_1.setBounds(227, 605, 158, 30);
//		contentPane.add(panel_1_4_6_1_1_1_5_5_1);
//		
//		stationeryBalance = new JLabel("0");
//		stationeryBalance.setHorizontalTextPosition(SwingConstants.CENTER);
//		stationeryBalance.setHorizontalAlignment(SwingConstants.CENTER);
//		stationeryBalance.setForeground(Color.WHITE);
//		stationeryBalance.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
//		stationeryBalance.setBackground(new Color(0, 128, 0));
//		stationeryBalance.setAlignmentX(0.5f);
//		stationeryBalance.setBounds(0, 0, 158, 30);
//		panel_1_4_6_1_1_1_5_5_1.add(stationeryBalance);

		setPayment();
		setTotals();
		
		table.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent e) {
		    	table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
		    }
		});
		
		
		
	}
}
