package POS;

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

import SourceCode.CommonMethods;
import POS.Connect;
import SourceCode.DailySaleReport;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JRadioButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AttendanceSheet extends JFrame {

	private JPanel contentPane;
	JLabel date;
	static JTable mainTable;
	ButtonGroup group = new ButtonGroup();
	private JTable monthTable;
	private JScrollPane mainScrollPane;
	private JLabel main;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					AttendanceSheet frame = new AttendanceSheet();
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void addWages() {
		try {
			Connection con = SourceCode.Connect.getConnect();
			String tableName = monthTable.getValueAt(monthTable.getSelectedRow(), 0).toString().replace(" ", "_")+"Dispersement";
			String url = "SELECT MAX(Serial) FROM "+tableName;
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();
			while(rs.next()) {
				PreparedStatement create1 = null;
				create1 = con.prepareStatement("INSERT INTO "+tableName+" (Serial, Date ,Description, Amount) VALUES ('"+(rs.getInt(1)+1)+"','"+mainTable.getValueAt(mainTable.getSelectedRow(),0).toString().replace(" ", "-")+"','"+LabourSheet.getEmployee()+" Daily Wage"+"','"+LabourSheet.getDailyWages()+"') \r\n");	
				create1.executeUpdate();
				}
			}
			
			catch(Exception e1) {
				System.err.print(e1);
			}
	}
	
	public void deleteWages() {
		try {
			Connection con = SourceCode.Connect.getConnect();
			String tableName = monthTable.getValueAt(monthTable.getSelectedRow(), 0).toString().replace(" ", "_")+"Dispersement";
		 	String sql = "DELETE FROM "+tableName+" WHERE Description=? AND Date=?";
		 	
		 	PreparedStatement statement = con.prepareStatement(sql);
		 	statement.setString(1, LabourSheet.getEmployee()+" Daily Wage");
		 	statement.setString(2, mainTable.getValueAt(mainTable.getSelectedRow(),0).toString().replace(" ", "-"));
		 	statement.executeUpdate();
		 	con.close();
		}
			
			catch(Exception e1) {
				System.err.print(e1);
			}
	}
	
	public void adjustWages() {
		try {
			
			int disp = 0;
			int collection=0;
			
			Connection con = SourceCode.Connect.getConnect();
			
			String tableName = monthTable.getValueAt(monthTable.getSelectedRow(), 0).toString().replace(" ", "_")+"Dispersement";
			String url = "SELECT SUM(Amount) FROM "+tableName;
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();
			while(rs.next()) {
				disp = rs.getInt(1);
				}

			String tableN = monthTable.getValueAt(monthTable.getSelectedRow(), 0).toString().replace(" ", "_")+"Collection";
			String url1 = "SELECT SUM(Amount) FROM "+tableN;
			PreparedStatement pst1 = con.prepareStatement(url1);
			ResultSet rs1= pst1.executeQuery();
			while(rs1.next()) {
				collection = rs1.getInt(1);
			}
			
		
		PreparedStatement s1t = con.prepareStatement("UPDATE ShopExpenses SET [Balance]=? WHERE [Month]='"+monthTable.getValueAt(monthTable.getSelectedRow(), 0).toString().replace(" ", "_")+"'");
		s1t.setString(1, String.valueOf(collection-disp));

		s1t.executeUpdate();
		}
			
			catch(Exception e1) {
				System.err.print(e1);
			}
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
	
	
	public void createTables() {
		try {
			
			String tableName =  monthTable.getValueAt(monthTable.getSelectedRow(),0).toString().replaceAll(" ","_")+LabourSheet.getEmployee().replace(" ", "")+"Expenses";
			String xyz = monthTable.getValueAt(monthTable.getSelectedRow(),0).toString();
			
			
			Connection con  = Connect.getConnect();
			String row = date.getText().substring(date.getText().indexOf(",")+1).substring(0,3).replaceAll("-"," ")+xyz;

			PreparedStatement create4 = con.prepareStatement("IF OBJECT_ID(N'dbo."+tableName+"', N'U') IS NULL BEGIN\r\n"
					+ "CREATE TABLE "+tableName+" \r\n"
					+ "(Serial int, Date VARCHAR(100) ,Day VARCHAR(100), TimeArrival VARCHAR(50), TimeDeparture VARCHAR(50), DailyWages VARCHAR(50)"
					+ ", WagesStatus VARCHAR(50), Remarks VARCHAR(50))\r\n"
					+ "END;");
			create4.executeUpdate();

			String url = "SELECT MAX(Serial) FROM "+tableName;
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();
			while(rs.next()) {
				PreparedStatement create = con.prepareStatement("BEGIN IF NOT EXISTS (SELECT * FROM "+tableName+" WHERE Date = '"+row+"')\r\n"
						+ "BEGIN\r\n"
						+ "   INSERT INTO "+tableName+" (Serial, Date,Day,TimeArrival,TimeDeparture,DailyWages,WagesStatus,Remarks) VALUES ('"+(rs.getInt(1)+1)+"','"+(row)+"','"+date.getText().substring(0,date.getText().indexOf(","))+"','0','0','"+LabourSheet.getDailyWages()+"','Un Paid','0') \r\n"
						+ "END END");
						
				create.executeUpdate();
				}

		}

			
			catch(Exception e) {//JOptionPane.showMessageDialog(null, e);
				System.out.println(e);
			} 
	}
	
	public  void putTable() {
		try {
			
			String tableName =  monthTable.getValueAt(monthTable.getSelectedRow(),0).toString().replaceAll(" ","_")+LabourSheet.getEmployee().replace(" ", "")+"Expenses";
			
			DefaultTableModel dtm = (DefaultTableModel) mainTable.getModel();
			dtm.setRowCount(0);
			Connection con =  Connect.getConnect();
			
			String url = "SELECT * FROM "+tableName+" ORDER BY [Serial] ASC";
			PreparedStatement pst = con.prepareStatement(url);
			ResultSet rs= pst.executeQuery();	
			DefaultTableModel model = (DefaultTableModel) mainTable.getModel();
			while(rs.next()) {
				model.addRow(new Object[] {rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)});	
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	public AttendanceSheet() {
		

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
		
		
		mainScrollPane = new JScrollPane();

		mainScrollPane.setBounds(160, 45, 1180, 570);
		contentPane.add(mainScrollPane);
		mainTable = new JTable();
		mainTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					if(mainTable.getSelectedColumn()==5) {
						if(mainTable.getValueAt(mainTable.getSelectedRow(), mainTable.getSelectedColumn()).toString().equals("Paid")) {
							mainTable.setValueAt("Un Paid", mainTable.getSelectedRow(), mainTable.getSelectedColumn());
							deleteWages();
							adjustWages();
						}else {
							mainTable.setValueAt("Paid", mainTable.getSelectedRow(), mainTable.getSelectedColumn());
							addWages();
							adjustWages();
						}
						
						try {

							
							String tableName =  monthTable.getValueAt(monthTable.getSelectedRow(),0).toString().replaceAll(" ","_")+LabourSheet.getEmployee().replace(" ", "")+"Expenses";
							
							int i = mainTable.getSelectedRow()+1;						
							
							Connection con =  Connect.getConnect();
							PreparedStatement st = con.prepareStatement("UPDATE "+tableName+" SET [WagesStatus]=? WHERE [Serial]="+(i)+"");

							st.setString(1, mainTable.getValueAt(mainTable.getSelectedRow(),5).toString());
							st.executeUpdate();	
							
							}
							catch(Exception e1) {
								System.out.println(e1);
							}
						
					}	
				}
				
			}
		});
		mainTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					try {

						
						String tableName =  monthTable.getValueAt(monthTable.getSelectedRow(),0).toString().replaceAll(" ","_")+LabourSheet.getEmployee().replace(" ", "")+"Expenses";
						
						int i = mainTable.getSelectedRow()+1;						
						
						Connection con =  Connect.getConnect();
						PreparedStatement st = con.prepareStatement("UPDATE "+tableName+" SET [TimeArrival]=?, [TimeDeparture]=?, [DailyWages]=?, [WagesStatus]=?, [Remarks]=? WHERE [Serial]="+(i)+"");

						st.setString(1, mainTable.getValueAt(mainTable.getSelectedRow(),2).toString());
						st.setString(2, mainTable.getValueAt(mainTable.getSelectedRow(),3).toString());
						st.setString(3, mainTable.getValueAt(mainTable.getSelectedRow(),4).toString());
						st.setString(4, mainTable.getValueAt(mainTable.getSelectedRow(),5).toString());
						st.setString(5, mainTable.getValueAt(mainTable.getSelectedRow(),6).toString());
						st.executeUpdate();	
						
						}
						catch(Exception e1) {
							System.out.println(e1);
						}
				}
			}
		});
		
		mainTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Date", "Day", "Time Arrival", "Time Departure", "Daily Wages", "Wages Status", "Remarks"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, Object.class, Object.class, Object.class, Object.class, String.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, true, true, true, true, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		mainTable.getColumnModel().getColumn(0).setPreferredWidth(15);
		mainTable.getColumnModel().getColumn(1).setPreferredWidth(15);
		mainTable.getColumnModel().getColumn(2).setPreferredWidth(15);
		mainTable.getColumnModel().getColumn(3).setPreferredWidth(15);
		mainTable.getColumnModel().getColumn(4).setPreferredWidth(15);
		mainTable.getColumnModel().getColumn(5).setPreferredWidth(15);
		mainTable.getColumnModel().getColumn(6).setPreferredWidth(200);

		
UIManager.put("TableHeader.font", new Font("Century Gothic", Font.BOLD, 16));
		
		mainScrollPane.setViewportView(mainTable);
				mainTable.setRowHeight(23);

				DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
				rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				
				UIDefaults defaults = UIManager.getLookAndFeelDefaults();
				defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
				
				
				mainTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
				mainTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
				mainTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
				mainTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
				mainTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
				mainTable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
				mainTable.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
				
				mainScrollPane.setViewportView(mainTable);
				mainScrollPane.setViewportView(mainTable);
		
		Font font = new Font("Times New Roman", Font.BOLD, 30);
		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		
		main = new JLabel("");
		main.setHorizontalAlignment(SwingConstants.CENTER);
		main.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 26));
		main.setBounds(10, 11, 1366, 30);
		main.setText("Appraisal and Salary Detail of "+LabourSheet.getEmployee());
		contentPane.add(main);
		
		JButton btnNewButton_1 = new JButton("Refresh");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AttendanceSheet().setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setBounds(1127, 626, 213, 34);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2_1 = new JButton("Main Window");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MainSaleWindow().setVisible(true);
				dispose();
			}
		});
		btnNewButton_2_1.setForeground(Color.BLACK);
		btnNewButton_2_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_2_1.setBackground(Color.WHITE);
		btnNewButton_2_1.setBounds(848, 626, 213, 34);
		contentPane.add(btnNewButton_2_1);


		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 45, 140, 570);
		contentPane.add(scrollPane_1);
		
		monthTable = new JTable();
		monthTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				createTables();
				putTable();
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
		monthTable.setToolTipText("");
		monthTable.setRowHeight(26);
		monthTable.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		mainTable.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		monthTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		scrollPane_1.setViewportView(monthTable);
		
		JButton btnNewButton_1_1 = new JButton("Pay Slip");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			new PaySlip().setVisible(true);
			dispose();
			}
			
		});
		btnNewButton_1_1.setForeground(Color.BLACK);
		btnNewButton_1_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		btnNewButton_1_1.setBackground(Color.WHITE);
		btnNewButton_1_1.setBounds(10, 626, 140, 34);
		contentPane.add(btnNewButton_1_1);

		setMonthTable();
		
	}
}
