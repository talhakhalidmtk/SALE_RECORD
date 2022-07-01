package SourceCode;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import start.Splash;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.KeyStroke;
import javax.swing.RowSorter;
import javax.swing.SortOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.border.LineBorder;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class MonthlySaleRecord extends JFrame {

	static Connection con;
	private JPanel contentPane;
	private static JTable table;
	static JLabel date  ;
	static JLabel totalUniform;
	static JLabel totalTotal;
	static JLabel totalExpenses,totalExpenses_2,totalExpenses_1;
	static JLabel totalStationery,totalBook, totalBag,totalGarments;
	private static JLabel stationeryPayment;
	private static JLabel booksPayment;
	private static JLabel uniformPayment;
	private static JLabel bagsPayment;
	private static JLabel garmentsPayment;
	private static JLabel totalPayment;
	private static JLabel changeStationery;
	private static JLabel changeBooks;
	private static JLabel changeUniform;
	private static JLabel changeBags;
	private static JLabel changeGarments;
	private static JLabel changeTotal;
	private JLabel previousExpenses;
	private JLabel previousTotal;
	private JLabel previousGarments;
	private JLabel previousUniforms;
	private JLabel previousBags;
	private JLabel previousBooks;
	private JLabel previousStationery;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MonthlySaleRecord frame = new MonthlySaleRecord();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setTotals() {
		int rows = table.getRowCount();
		long stationery = 0,book=0,bag=0,uniform=0,Garments=0,total=0,expense=0;
		
		
		for (int i =0; i<rows;i++) {
			stationery += Long.valueOf(table.getValueAt(i,1).toString());
			book += Long.valueOf(table.getValueAt(i,2).toString());
			bag += Long.valueOf(table.getValueAt(i,3).toString());
			uniform += Long.valueOf(table.getValueAt(i,4).toString());
			Garments += Long.valueOf(table.getValueAt(i,5).toString());
			total += Long.valueOf(table.getValueAt(i,6).toString());
			expense += Long.valueOf(table.getValueAt(i,7).toString());
			
		}
		
			totalStationery.setText(String.valueOf(stationery+Integer.valueOf(previousStationery.getText())));
			totalBook.setText(String.valueOf(book+Integer.valueOf(previousBooks.getText())));
			totalBag.setText(String.valueOf(bag+Integer.valueOf(previousBags.getText())));
			totalUniform.setText(String.valueOf(uniform+Integer.valueOf(previousTotal.getText())));
			totalGarments.setText(String.valueOf(Garments+Integer.valueOf(previousGarments.getText())));
			totalTotal.setText(String.valueOf(total+Integer.valueOf(previousTotal.getText())));
			totalExpenses.setText(String.valueOf(expense+Integer.valueOf(previousExpenses.getText())));
			
			
		totalExpenses_2.setText(totalExpenses.getText());
		totalExpenses.setText(String.format("%.0f",((Double.valueOf(totalTotal.getText())-Integer.valueOf(previousTotal.getText()))*10)/100));
		totalExpenses_1.setText(String.valueOf(Integer.valueOf(totalExpenses.getText())-Integer.valueOf(totalExpenses_2.getText())));
		changeStationery.setText(String.valueOf(Integer.valueOf(totalStationery.getText())-Integer.valueOf(stationeryPayment.getText())));
		changeBooks.setText(String.valueOf(Integer.valueOf(totalBook.getText())-Integer.valueOf(booksPayment.getText())));
		changeBags.setText(String.valueOf(Integer.valueOf(totalBag.getText())-Integer.valueOf(bagsPayment.getText())));
		changeUniform.setText(String.valueOf(Integer.valueOf(totalUniform.getText())-Integer.valueOf(uniformPayment.getText())));
		changeGarments.setText(String.valueOf(Integer.valueOf(totalGarments.getText())-Integer.valueOf(garmentsPayment.getText())));
		changeTotal.setText(String.valueOf(Integer.valueOf(totalTotal.getText())-Integer.valueOf(totalPayment.getText())));
		
		
		YearlyRecord yr = new YearlyRecord();
		yr.setVisible(false);
		yr.dispose();
		
//		YearlyRecord.putTable();
//		YearlyRecord.setPayment();
//		YearlyRecord.setTotals();
		
		
		try {
			con  = Connect.getConnect();
			String url = "SELECT MAX(Serial) FROM PreviousBalance";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			while(rs.next()) {
				PreparedStatement create6 = con.prepareStatement("BEGIN IF NOT EXISTS (SELECT * FROM PreviousBalance WHERE Date = '"+date.getText().toString().substring(date.getText().indexOf("-")).replaceAll("-","_").replaceAll(",","_")+"')\r\n"
						+ "BEGIN\r\n"
						+ "   INSERT INTO PreviousBalance (Serial, Date,Stationery,Books,Bags,Uniform,Garments,Total,Expenses) VALUES ('"+String.valueOf(Integer.valueOf(rs.getString(1))+1)+"','"+date.getText().toString().substring(date.getText().indexOf("-")).replaceAll("-","_").replaceAll(",","_")+"','"+YearlyRecord.changeStationery.getText()+"','"+YearlyRecord.changeBooks.getText()+"','"+YearlyRecord.changeBags.getText()+"','"+YearlyRecord.changeUniform.getText()+"','"+YearlyRecord.changeGarments.getText()+"','"+YearlyRecord.changeTotal.getText()+"','"+YearlyRecord.totalExpenses_1.getText()+"') \r\n"
						+ "END END");
						
				create6.executeUpdate();
				
				PreparedStatement create = con.prepareStatement("BEGIN IF EXISTS (SELECT * FROM PreviousBalance WHERE Date = '"+date.getText().toString().substring(date.getText().indexOf("-")).replaceAll("-","_").replaceAll(",","_")+"')\r\n"
						+ "BEGIN\r\n"
						+ "   UPDATE PreviousBalance SET [Stationery]=?, [Books]=?, [Bags]=?, [Uniform]=?, [Garments]=?, [Total]=?, [Expenses]=? WHERE [Date]='"+date.getText().toString().substring(date.getText().indexOf("-")).replaceAll("-","_").replaceAll(",","_")+"' \r\n"
						+ "END END");
				
				create.setString(1, String.valueOf(YearlyRecord.changeStationery.getText()));
				create.setString(2, String.valueOf(YearlyRecord.changeBooks.getText()));
				create.setString(3, String.valueOf(YearlyRecord.changeBags.getText()));
				create.setString(4, String.valueOf(YearlyRecord.changeUniform.getText()));
				create.setString(5, String.valueOf(YearlyRecord.changeGarments.getText()));
				create.setString(6, String.valueOf(YearlyRecord.changeTotal.getText()));
				
				int c = -Integer.valueOf(String.valueOf(YearlyRecord.totalExpenses_1.getText()));
				
				create.setString(7, String.valueOf(c));

				create.executeUpdate();

				}
			
			}
			
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			} 
		
		
		
		
		
		
		
	}

	public void putTable() {
		String tableName = DailySaleReport.getTableName();
		//method extracted from CommonMethods
		CommonMethods.putTableFromCommon(table, tableName);
		
		
	}
	
	public boolean dateExists() {
		boolean flag = true;
		if(table.getRowCount()==0) {
			flag = false;
		}
		else {
			try {
				String tableName = DailySaleReport.getTableName();
				
				String rowColumnName = date.getText();
				rowColumnName = rowColumnName.replace(",","_");
				rowColumnName = rowColumnName.replace("-","_");
				
				con  = Connect.getConnect();
				String url = "SELECT COUNT(*) FROM "+tableName+" WHERE Date='"+rowColumnName+"'";
				PreparedStatement pst = con.prepareStatement(url);
				ResultSet rs= pst.executeQuery();	
				while(rs.next()) {
					if(rs.getInt(1)!=1) {
						flag = false;
					}
				}
			
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);;
			}
		}
		
		return flag; 
	}
	
	
	public void setPayment() {
		try {
			Connection con =  Connect.getConnect();
			String url = "SELECT SUM(Stationery),SUM(Books),SUM(Bags),SUM(Uniform),SUM(Garments) FROM PaymentRecord WHERE Date LIKE '%"+date.getText().toString().substring(date.getText().indexOf("-")).replaceAll("-","_").replaceAll(",","_")+"'";
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
			JOptionPane.showMessageDialog(null, e);;
		}
	}

	public MonthlySaleRecord() {
		
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 1330, 474);
		contentPane.add(scrollPane);
		
		table = new JTable(){
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
	            Component comp = super.prepareRenderer(renderer, row, column);
	            Color three = Color.LIGHT_GRAY;
	            
	            if(!comp.getBackground().equals(getSelectionBackground())) {
	               Color c = null;
	               if(row%2==1) {
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
				"Date", "Stationery", "Books", "Bags", "Uniform", "Gift Items", "Total Sale", "Expenses"
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
		
		putTable();
		
		
		
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
		scrollPane.setViewportView(table);

		
		JPanel panel_1_4_6_1_1_1_5_1 = new JPanel();
		panel_1_4_6_1_1_1_5_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_1.setLayout(null);
		panel_1_4_6_1_1_1_5_1.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_1.setBounds(10, 521, 217, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_1);
		
		JLabel lblTotal = new JLabel("Receipt:");
		lblTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotal.setForeground(Color.WHITE);
		lblTotal.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		lblTotal.setAlignmentX(0.5f);
		lblTotal.setBounds(0, 0, 217, 34);
		panel_1_4_6_1_1_1_5_1.add(lblTotal);
		
		JPanel panel_1_4_6_1_1_1_5_4 = new JPanel();
		panel_1_4_6_1_1_1_5_4.setLayout(null);
		panel_1_4_6_1_1_1_5_4.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_4.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_4.setBounds(703, 521, 159, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_4);
		
		totalUniform = new JLabel("0");
		totalUniform.setHorizontalTextPosition(SwingConstants.CENTER);
		totalUniform.setHorizontalAlignment(SwingConstants.CENTER);
		totalUniform.setForeground(Color.WHITE);
		totalUniform.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalUniform.setAlignmentX(0.5f);
		totalUniform.setBounds(0, 0, 159, 34);
		panel_1_4_6_1_1_1_5_4.add(totalUniform);
		
		JPanel t = new JPanel();
		t.setLayout(null);
		t.setBorder(new LineBorder(Color.WHITE));
		t.setBackground(Color.DARK_GRAY);
		t.setBounds(1021, 521, 159, 34);
		contentPane.add(t);
		
		totalTotal = new JLabel("0");
		totalTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		totalTotal.setHorizontalAlignment(SwingConstants.CENTER);
		totalTotal.setForeground(Color.WHITE);
		totalTotal.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalTotal.setAlignmentX(0.5f);
		totalTotal.setBounds(0, 0, 159, 34);
		t.add(totalTotal);
		
		JPanel panel_1_4_6_1_1_1_5_6 = new JPanel();
		panel_1_4_6_1_1_1_5_6.setLayout(null);
		panel_1_4_6_1_1_1_5_6.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_6.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_6.setBounds(1180, 521, 160, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_6);
		
		totalExpenses = new JLabel("0");
		totalExpenses.setHorizontalTextPosition(SwingConstants.CENTER);
		totalExpenses.setHorizontalAlignment(SwingConstants.CENTER);
		totalExpenses.setForeground(Color.WHITE);
		totalExpenses.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalExpenses.setAlignmentX(0.5f);
		totalExpenses.setBounds(0, 0, 160, 34);
		panel_1_4_6_1_1_1_5_6.add(totalExpenses);
		
		//extracted from CommonMethods
		CommonMethods.timeFromCommon(time);
		
		JPanel t_1 = new JPanel();
		t_1.setLayout(null);
		t_1.setBorder(new LineBorder(Color.WHITE));
		t_1.setBackground(Color.DARK_GRAY);
		t_1.setBounds(862, 521, 159, 34);
		contentPane.add(t_1);
		
		 totalGarments = new JLabel("0");
		totalGarments.setHorizontalTextPosition(SwingConstants.CENTER);
		totalGarments.setHorizontalAlignment(SwingConstants.CENTER);
		totalGarments.setForeground(Color.WHITE);
		totalGarments.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalGarments.setAlignmentX(0.5f);
		totalGarments.setBounds(0, 0, 159, 34);
		t_1.add(totalGarments);
		
		JPanel panel_1_4_6_1_1_1_5_3 = new JPanel();
		panel_1_4_6_1_1_1_5_3.setLayout(null);
		panel_1_4_6_1_1_1_5_3.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_3.setBackground(Color.DARK_GRAY);
		panel_1_4_6_1_1_1_5_3.setBounds(544, 521, 159, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_3);
		
		totalBag = new JLabel("0");
		totalBag.setHorizontalTextPosition(SwingConstants.CENTER);
		totalBag.setHorizontalAlignment(SwingConstants.CENTER);
		totalBag.setForeground(Color.WHITE);
		totalBag.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalBag.setAlignmentX(0.5f);
		totalBag.setBounds(0, 0, 159, 34);
		panel_1_4_6_1_1_1_5_3.add(totalBag);
		
		JPanel panel_1_4_6_1_1_1_5_2 = new JPanel();
		panel_1_4_6_1_1_1_5_2.setBounds(385, 521, 159, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_2);
		panel_1_4_6_1_1_1_5_2.setLayout(null);
		panel_1_4_6_1_1_1_5_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_2.setBackground(Color.DARK_GRAY);
		
		totalBook = new JLabel("0");
		totalBook.setHorizontalTextPosition(SwingConstants.CENTER);
		totalBook.setHorizontalAlignment(SwingConstants.CENTER);
		totalBook.setForeground(Color.WHITE);
		totalBook.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalBook.setAlignmentX(0.5f);
		totalBook.setBounds(0, 0, 159, 34);
		panel_1_4_6_1_1_1_5_2.add(totalBook);
		
		JPanel panel_1_4_6_1_1_1_5 = new JPanel();
		panel_1_4_6_1_1_1_5.setBounds(227, 521, 158, 34);
		contentPane.add(panel_1_4_6_1_1_1_5);
		panel_1_4_6_1_1_1_5.setLayout(null);
		panel_1_4_6_1_1_1_5.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5.setBackground(Color.DARK_GRAY);
		
		totalStationery = new JLabel("0");
		totalStationery.setHorizontalTextPosition(SwingConstants.CENTER);
		totalStationery.setHorizontalAlignment(SwingConstants.CENTER);
		totalStationery.setForeground(Color.WHITE);
		totalStationery.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalStationery.setAlignmentX(0.5f);
		totalStationery.setBounds(0, 0, 158, 34);
		panel_1_4_6_1_1_1_5.add(totalStationery);
		
		JPanel panel_1_4_6_1_1_1_5_1_2 = new JPanel();
		panel_1_4_6_1_1_1_5_1_2.setLayout(null);
		panel_1_4_6_1_1_1_5_1_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_1_2.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_1_2.setBounds(10, 556, 217, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_1_2);
		
		JLabel lblPayments = new JLabel("Payments:");
		lblPayments.setBackground(SystemColor.activeCaption);
		lblPayments.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPayments.setHorizontalAlignment(SwingConstants.CENTER);
		lblPayments.setForeground(Color.WHITE);
		lblPayments.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		lblPayments.setAlignmentX(0.5f);
		lblPayments.setBounds(0, 0, 217, 34);
		panel_1_4_6_1_1_1_5_1_2.add(lblPayments);
		
		JPanel panel_1_4_6_1_1_1_5_4_2 = new JPanel();
		panel_1_4_6_1_1_1_5_4_2.setLayout(null);
		panel_1_4_6_1_1_1_5_4_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_4_2.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_4_2.setBounds(703, 556, 159, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_4_2);
		
		uniformPayment = new JLabel("0");
		uniformPayment.setBackground(SystemColor.activeCaption);
		uniformPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		uniformPayment.setHorizontalAlignment(SwingConstants.CENTER);
		uniformPayment.setForeground(Color.WHITE);
		uniformPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		uniformPayment.setAlignmentX(0.5f);
		uniformPayment.setBounds(0, 0, 159, 34);
		panel_1_4_6_1_1_1_5_4_2.add(uniformPayment);
		
		JPanel t_3 = new JPanel();
		t_3.setLayout(null);
		t_3.setBorder(new LineBorder(Color.WHITE));
		t_3.setBackground(new Color(169, 169, 169));
		t_3.setBounds(1021, 556, 159, 34);
		contentPane.add(t_3);
		
		totalPayment = new JLabel("0");
		totalPayment.setBackground(SystemColor.activeCaption);
		totalPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		totalPayment.setHorizontalAlignment(SwingConstants.CENTER);
		totalPayment.setForeground(Color.WHITE);
		totalPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalPayment.setAlignmentX(0.5f);
		totalPayment.setBounds(0, 0, 159, 34);
		t_3.add(totalPayment);
		
		JPanel panel_1_4_6_1_1_1_5_6_2 = new JPanel();
		panel_1_4_6_1_1_1_5_6_2.setLayout(null);
		panel_1_4_6_1_1_1_5_6_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_6_2.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_6_2.setBounds(1180, 556, 160, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_6_2);
		
		totalExpenses_2 = new JLabel("0");
		totalExpenses_2.setBackground(SystemColor.activeCaption);
		totalExpenses_2.setHorizontalTextPosition(SwingConstants.CENTER);
		totalExpenses_2.setHorizontalAlignment(SwingConstants.CENTER);
		totalExpenses_2.setForeground(Color.WHITE);
		totalExpenses_2.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalExpenses_2.setAlignmentX(0.5f);
		totalExpenses_2.setBounds(0, 0, 160, 34);
		panel_1_4_6_1_1_1_5_6_2.add(totalExpenses_2);
		
		JPanel t_1_2 = new JPanel();
		t_1_2.setLayout(null);
		t_1_2.setBorder(new LineBorder(Color.WHITE));
		t_1_2.setBackground(new Color(169, 169, 169));
		t_1_2.setBounds(862, 556, 159, 34);
		contentPane.add(t_1_2);
		
		garmentsPayment = new JLabel("0");
		garmentsPayment.setBackground(SystemColor.activeCaption);
		garmentsPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		garmentsPayment.setHorizontalAlignment(SwingConstants.CENTER);
		garmentsPayment.setForeground(Color.WHITE);
		garmentsPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		garmentsPayment.setAlignmentX(0.5f);
		garmentsPayment.setBounds(0, 0, 159, 34);
		t_1_2.add(garmentsPayment);
		
		JPanel panel_1_4_6_1_1_1_5_3_2 = new JPanel();
		panel_1_4_6_1_1_1_5_3_2.setLayout(null);
		panel_1_4_6_1_1_1_5_3_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_3_2.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_3_2.setBounds(544, 556, 159, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_3_2);
		
		bagsPayment = new JLabel("0");
		bagsPayment.setBackground(SystemColor.activeCaption);
		bagsPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		bagsPayment.setHorizontalAlignment(SwingConstants.CENTER);
		bagsPayment.setForeground(Color.WHITE);
		bagsPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		bagsPayment.setAlignmentX(0.5f);
		bagsPayment.setBounds(0, 0, 159, 34);
		panel_1_4_6_1_1_1_5_3_2.add(bagsPayment);
		
		JPanel panel_1_4_6_1_1_1_5_2_2 = new JPanel();
		panel_1_4_6_1_1_1_5_2_2.setLayout(null);
		panel_1_4_6_1_1_1_5_2_2.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_2_2.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_2_2.setBounds(385, 556, 159, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_2_2);
		
		booksPayment = new JLabel("0");
		booksPayment.setBackground(SystemColor.activeCaption);
		booksPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		booksPayment.setHorizontalAlignment(SwingConstants.CENTER);
		booksPayment.setForeground(Color.WHITE);
		booksPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		booksPayment.setAlignmentX(0.5f);
		booksPayment.setBounds(0, 0, 159, 34);
		panel_1_4_6_1_1_1_5_2_2.add(booksPayment);
		
		JPanel panel_1_4_6_1_1_1_5_7 = new JPanel();
		panel_1_4_6_1_1_1_5_7.setLayout(null);
		panel_1_4_6_1_1_1_5_7.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_7.setBackground(new Color(169, 169, 169));
		panel_1_4_6_1_1_1_5_7.setBounds(227, 556, 158, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_7);
		
		stationeryPayment = new JLabel("0");
		stationeryPayment.setBackground(SystemColor.activeCaption);
		stationeryPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		stationeryPayment.setHorizontalAlignment(SwingConstants.CENTER);
		stationeryPayment.setForeground(Color.WHITE);
		stationeryPayment.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		stationeryPayment.setAlignmentX(0.5f);
		stationeryPayment.setBounds(0, 0, 158, 34);
		panel_1_4_6_1_1_1_5_7.add(stationeryPayment);
		
		JPanel panel_1_4_6_1_1_1_5_1_1 = new JPanel();
		panel_1_4_6_1_1_1_5_1_1.setLayout(null);
		panel_1_4_6_1_1_1_5_1_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_1_1.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_1_1.setBounds(10, 591, 217, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_1_1);
		
		JLabel lblBalance = new JLabel("Balance:");
		lblBalance.setBackground(new Color(0, 128, 0));
		lblBalance.setHorizontalTextPosition(SwingConstants.CENTER);
		lblBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblBalance.setForeground(Color.WHITE);
		lblBalance.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		lblBalance.setAlignmentX(0.5f);
		lblBalance.setBounds(0, 0, 217, 34);
		panel_1_4_6_1_1_1_5_1_1.add(lblBalance);
		
		JPanel panel_1_4_6_1_1_1_5_4_1 = new JPanel();
		panel_1_4_6_1_1_1_5_4_1.setLayout(null);
		panel_1_4_6_1_1_1_5_4_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_4_1.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_4_1.setBounds(703, 591, 159, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_4_1);
		
		changeUniform = new JLabel("0");
		changeUniform.setHorizontalTextPosition(SwingConstants.CENTER);
		changeUniform.setHorizontalAlignment(SwingConstants.CENTER);
		changeUniform.setForeground(Color.WHITE);
		changeUniform.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		changeUniform.setAlignmentX(0.5f);
		changeUniform.setBounds(0, 0, 159, 34);
		panel_1_4_6_1_1_1_5_4_1.add(changeUniform);
		
		JPanel t_2 = new JPanel();
		t_2.setLayout(null);
		t_2.setBorder(new LineBorder(Color.WHITE));
		t_2.setBackground(new Color(112, 128, 144));
		t_2.setBounds(1021, 591, 159, 34);
		contentPane.add(t_2);
		
		changeTotal = new JLabel("0");
		changeTotal.setHorizontalTextPosition(SwingConstants.CENTER);
		changeTotal.setHorizontalAlignment(SwingConstants.CENTER);
		changeTotal.setForeground(Color.WHITE);
		changeTotal.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		changeTotal.setAlignmentX(0.5f);
		changeTotal.setBounds(0, 0, 159, 34);
		t_2.add(changeTotal);
		
		JPanel panel_1_4_6_1_1_1_5_6_1 = new JPanel();
		panel_1_4_6_1_1_1_5_6_1.setLayout(null);
		panel_1_4_6_1_1_1_5_6_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_6_1.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_6_1.setBounds(1181, 591, 159, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_6_1);
		
		totalExpenses_1 = new JLabel("0");
		totalExpenses_1.setHorizontalTextPosition(SwingConstants.CENTER);
		totalExpenses_1.setHorizontalAlignment(SwingConstants.CENTER);
		totalExpenses_1.setForeground(Color.WHITE);
		totalExpenses_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		totalExpenses_1.setAlignmentX(0.5f);
		totalExpenses_1.setBounds(0, 0, 159, 34);
		panel_1_4_6_1_1_1_5_6_1.add(totalExpenses_1);
		
		JPanel t_1_1 = new JPanel();
		t_1_1.setLayout(null);
		t_1_1.setBorder(new LineBorder(Color.WHITE));
		t_1_1.setBackground(new Color(112, 128, 144));
		t_1_1.setBounds(862, 591, 159, 34);
		contentPane.add(t_1_1);
		
		changeGarments = new JLabel("0");
		changeGarments.setHorizontalTextPosition(SwingConstants.CENTER);
		changeGarments.setHorizontalAlignment(SwingConstants.CENTER);
		changeGarments.setForeground(Color.WHITE);
		changeGarments.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		changeGarments.setAlignmentX(0.5f);
		changeGarments.setBounds(0, 0, 159, 34);
		t_1_1.add(changeGarments);
		
		JPanel panel_1_4_6_1_1_1_5_3_1 = new JPanel();
		panel_1_4_6_1_1_1_5_3_1.setLayout(null);
		panel_1_4_6_1_1_1_5_3_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_3_1.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_3_1.setBounds(544, 591, 159, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_3_1);
		
		changeBags = new JLabel("0");
		changeBags.setHorizontalTextPosition(SwingConstants.CENTER);
		changeBags.setHorizontalAlignment(SwingConstants.CENTER);
		changeBags.setForeground(Color.WHITE);
		changeBags.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		changeBags.setAlignmentX(0.5f);
		changeBags.setBounds(0, 0, 159, 34);
		panel_1_4_6_1_1_1_5_3_1.add(changeBags);
		
		JPanel panel_1_4_6_1_1_1_5_2_1 = new JPanel();
		panel_1_4_6_1_1_1_5_2_1.setLayout(null);
		panel_1_4_6_1_1_1_5_2_1.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_2_1.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_2_1.setBounds(385, 591, 159, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_2_1);
		
		changeBooks = new JLabel("0");
		changeBooks.setHorizontalTextPosition(SwingConstants.CENTER);
		changeBooks.setHorizontalAlignment(SwingConstants.CENTER);
		changeBooks.setForeground(Color.WHITE);
		changeBooks.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		changeBooks.setAlignmentX(0.5f);
		changeBooks.setBounds(0, 0, 159, 34);
		panel_1_4_6_1_1_1_5_2_1.add(changeBooks);
		
		JPanel panel_1_4_6_1_1_1_5_5 = new JPanel();
		panel_1_4_6_1_1_1_5_5.setLayout(null);
		panel_1_4_6_1_1_1_5_5.setBorder(new LineBorder(Color.WHITE));
		panel_1_4_6_1_1_1_5_5.setBackground(new Color(112, 128, 144));
		panel_1_4_6_1_1_1_5_5.setBounds(227, 591, 158, 34);
		contentPane.add(panel_1_4_6_1_1_1_5_5);
		
		changeStationery = new JLabel("0");
		changeStationery.setBackground(new Color(0, 128, 0));
		changeStationery.setHorizontalTextPosition(SwingConstants.CENTER);
		changeStationery.setHorizontalAlignment(SwingConstants.CENTER);
		changeStationery.setForeground(Color.WHITE);
		changeStationery.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
		changeStationery.setAlignmentX(0.5f);
		changeStationery.setBounds(0, 0, 158, 34);
		panel_1_4_6_1_1_1_5_5.add(changeStationery);
		
		
		
		JButton btnNewButton = new JButton("Refresh (F2)");
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
		
		JButton btnAnnualSaleRecord = new JButton("Annual Record (F3)");
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
		
		
		
TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		
		sorter.setComparator(0, new Comparator<String>() {
			 
		    @Override
		    public int compare(String name1, String name2) {
		    	name1 = name1.substring(name1.indexOf("_")+1);
		    	name2 = name2.substring(name2.indexOf("_")+1);
		        return name1.compareTo(name2);
		    }
		});
		
		sorter.setSortsOnUpdates(true);
        List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        table.setRowSorter(sorter);
        
        JPanel panel_1_4_6_1_1_1_5_1_1_1 = new JPanel();
        panel_1_4_6_1_1_1_5_1_1_1.setLayout(null);
        panel_1_4_6_1_1_1_5_1_1_1.setBorder(new LineBorder(Color.WHITE));
        panel_1_4_6_1_1_1_5_1_1_1.setBackground(new Color(112, 128, 144));
        panel_1_4_6_1_1_1_5_1_1_1.setBounds(10, 11, 217, 34);
        contentPane.add(panel_1_4_6_1_1_1_5_1_1_1);
        
        JLabel lblPreviousBalance = new JLabel("Previous Balance:");
        lblPreviousBalance.setHorizontalTextPosition(SwingConstants.CENTER);
        lblPreviousBalance.setHorizontalAlignment(SwingConstants.CENTER);
        lblPreviousBalance.setForeground(Color.WHITE);
        lblPreviousBalance.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
        lblPreviousBalance.setBackground(new Color(0, 128, 0));
        lblPreviousBalance.setAlignmentX(0.5f);
        lblPreviousBalance.setBounds(0, 0, 217, 34);
        panel_1_4_6_1_1_1_5_1_1_1.add(lblPreviousBalance);
        
        JPanel panel_1_4_6_1_1_1_5_4_1_1 = new JPanel();
        panel_1_4_6_1_1_1_5_4_1_1.setLayout(null);
        panel_1_4_6_1_1_1_5_4_1_1.setBorder(new LineBorder(Color.WHITE));
        panel_1_4_6_1_1_1_5_4_1_1.setBackground(new Color(112, 128, 144));
        panel_1_4_6_1_1_1_5_4_1_1.setBounds(703, 11, 159, 34);
        contentPane.add(panel_1_4_6_1_1_1_5_4_1_1);
        
        previousUniforms = new JLabel("0");
        previousUniforms.setHorizontalTextPosition(SwingConstants.CENTER);
        previousUniforms.setHorizontalAlignment(SwingConstants.CENTER);
        previousUniforms.setForeground(Color.WHITE);
        previousUniforms.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
        previousUniforms.setAlignmentX(0.5f);
        previousUniforms.setBounds(0, 0, 159, 34);
        panel_1_4_6_1_1_1_5_4_1_1.add(previousUniforms);
        
        JPanel t_2_1 = new JPanel();
        t_2_1.setLayout(null);
        t_2_1.setBorder(new LineBorder(Color.WHITE));
        t_2_1.setBackground(new Color(112, 128, 144));
        t_2_1.setBounds(1021, 11, 159, 34);
        contentPane.add(t_2_1);
        
        previousTotal = new JLabel("0");
        previousTotal.setHorizontalTextPosition(SwingConstants.CENTER);
        previousTotal.setHorizontalAlignment(SwingConstants.CENTER);
        previousTotal.setForeground(Color.WHITE);
        previousTotal.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
        previousTotal.setAlignmentX(0.5f);
        previousTotal.setBounds(0, 0, 159, 34);
        t_2_1.add(previousTotal);
        
        JPanel panel_1_4_6_1_1_1_5_6_1_1 = new JPanel();
        panel_1_4_6_1_1_1_5_6_1_1.setLayout(null);
        panel_1_4_6_1_1_1_5_6_1_1.setBorder(new LineBorder(Color.WHITE));
        panel_1_4_6_1_1_1_5_6_1_1.setBackground(new Color(112, 128, 144));
        panel_1_4_6_1_1_1_5_6_1_1.setBounds(1181, 11, 159, 34);
        contentPane.add(panel_1_4_6_1_1_1_5_6_1_1);
        
        previousExpenses = new JLabel("0");
        previousExpenses.setHorizontalTextPosition(SwingConstants.CENTER);
        previousExpenses.setHorizontalAlignment(SwingConstants.CENTER);
        previousExpenses.setForeground(Color.WHITE);
        previousExpenses.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
        previousExpenses.setAlignmentX(0.5f);
        previousExpenses.setBounds(0, 0, 159, 34);
        panel_1_4_6_1_1_1_5_6_1_1.add(previousExpenses);
        
        JPanel t_1_1_1 = new JPanel();
        t_1_1_1.setLayout(null);
        t_1_1_1.setBorder(new LineBorder(Color.WHITE));
        t_1_1_1.setBackground(new Color(112, 128, 144));
        t_1_1_1.setBounds(862, 11, 159, 34);
        contentPane.add(t_1_1_1);
        
        previousGarments = new JLabel("0");
        previousGarments.setHorizontalTextPosition(SwingConstants.CENTER);
        previousGarments.setHorizontalAlignment(SwingConstants.CENTER);
        previousGarments.setForeground(Color.WHITE);
        previousGarments.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
        previousGarments.setAlignmentX(0.5f);
        previousGarments.setBounds(0, 0, 159, 34);
        t_1_1_1.add(previousGarments);
        
        JPanel panel_1_4_6_1_1_1_5_3_1_1 = new JPanel();
        panel_1_4_6_1_1_1_5_3_1_1.setLayout(null);
        panel_1_4_6_1_1_1_5_3_1_1.setBorder(new LineBorder(Color.WHITE));
        panel_1_4_6_1_1_1_5_3_1_1.setBackground(new Color(112, 128, 144));
        panel_1_4_6_1_1_1_5_3_1_1.setBounds(544, 11, 159, 34);
        contentPane.add(panel_1_4_6_1_1_1_5_3_1_1);
        
        previousBags = new JLabel("0");
        previousBags.setHorizontalTextPosition(SwingConstants.CENTER);
        previousBags.setHorizontalAlignment(SwingConstants.CENTER);
        previousBags.setForeground(Color.WHITE);
        previousBags.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
        previousBags.setAlignmentX(0.5f);
        previousBags.setBounds(0, 0, 159, 34);
        panel_1_4_6_1_1_1_5_3_1_1.add(previousBags);
        
        JPanel panel_1_4_6_1_1_1_5_2_1_1 = new JPanel();
        panel_1_4_6_1_1_1_5_2_1_1.setLayout(null);
        panel_1_4_6_1_1_1_5_2_1_1.setBorder(new LineBorder(Color.WHITE));
        panel_1_4_6_1_1_1_5_2_1_1.setBackground(new Color(112, 128, 144));
        panel_1_4_6_1_1_1_5_2_1_1.setBounds(385, 11, 159, 34);
        contentPane.add(panel_1_4_6_1_1_1_5_2_1_1);
        
        previousBooks = new JLabel("0");
        previousBooks.setHorizontalTextPosition(SwingConstants.CENTER);
        previousBooks.setHorizontalAlignment(SwingConstants.CENTER);
        previousBooks.setForeground(Color.WHITE);
        previousBooks.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
        previousBooks.setAlignmentX(0.5f);
        previousBooks.setBounds(0, 0, 159, 34);
        panel_1_4_6_1_1_1_5_2_1_1.add(previousBooks);
        
        JPanel panel_1_4_6_1_1_1_5_5_1 = new JPanel();
        panel_1_4_6_1_1_1_5_5_1.setLayout(null);
        panel_1_4_6_1_1_1_5_5_1.setBorder(new LineBorder(Color.WHITE));
        panel_1_4_6_1_1_1_5_5_1.setBackground(new Color(112, 128, 144));
        panel_1_4_6_1_1_1_5_5_1.setBounds(227, 11, 158, 34);
        contentPane.add(panel_1_4_6_1_1_1_5_5_1);
        
        previousStationery = new JLabel("0");
        previousStationery.setHorizontalTextPosition(SwingConstants.CENTER);
        previousStationery.setHorizontalAlignment(SwingConstants.CENTER);
        previousStationery.setForeground(Color.WHITE);
        previousStationery.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 20));
        previousStationery.setBackground(new Color(0, 128, 0));
        previousStationery.setAlignmentX(0.5f);
        previousStationery.setBounds(0, 0, 158, 34);
        panel_1_4_6_1_1_1_5_5_1.add(previousStationery);
		
        setPayment();
        setTotals();
		
        try {
			Connection con =  Connect.getConnect();
				String url1 = "SELECT * FROM PreviousBalance WHERE Serial = (SELECT MAX(Serial) FROM PreviousBalance WHERE Serial NOT IN (SELECT MAX(Serial) FROM PreviousBalance))";
				PreparedStatement pst1 = con.prepareStatement(url1);
				ResultSet rs1= pst1.executeQuery();	
				while(rs1.next()) {
					previousStationery.setText(rs1.getString(3));
					previousBooks.setText(rs1.getString(4));
					previousBags.setText(rs1.getString(5));
					previousUniforms.setText(rs1.getString(6));
					previousGarments.setText(rs1.getString(7));
					previousTotal.setText(rs1.getString(8));
					previousExpenses.setText(rs1.getString(9));
				}

		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);;
		}
        
        setTotals();
        table.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent e) {
		    	table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
		    }
		});
        
        JScrollBar sb = scrollPane.getVerticalScrollBar();
        sb.setPreferredSize(new Dimension(10, 0));
		
		
	}
}
