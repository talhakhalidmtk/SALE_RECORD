package start;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;

import Extras.RoundedPanel;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JProgressBar;

public class Splash extends JFrame {

	private JPanel contentPane;
	 Timer timer = null;
	int delay = 50;
	int count = 0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Splash frame = new Splash();
					frame.setShape(new RoundRectangle2D.Double(0, 0, 451, 255, 25, 25));
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
	public Splash() {
		
		
		ImageIcon img =new ImageIcon(Splash.class.getResource("/SourceCode/edit.png"));
		setIconImage(img.getImage());
		
		
	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 255);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		setUndecorated(true);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		
		JPanel panel = new RoundedPanel(25);
		panel.setBounds(0, 0, 451, 255);
		panel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(5, 214, 431, 18);
		progressBar.setForeground(Color.decode("#2F2E41"));
		progressBar.setBorderPainted(true);
		progressBar.setStringPainted(true);
		
			    progressBar.setValue(0);
			    progressBar.setFont(new Font("Century Gothic", Font.PLAIN, 14));
			    progressBar.setBackground(Color.LIGHT_GRAY);
			    
			    panel.add(progressBar);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 441, 245);
		//lblNewLabel.setForeground(Color.WHITE);
		
		lblNewLabel.setIcon(new ImageIcon(Splash.class.getResource("/Images/splashScreen.png")));
		panel.add(lblNewLabel);
		
		
	    
		
	    loginWindow fr = new loginWindow();
	    fr.setVisible(false);
		 timer= new Timer( delay, new ActionListener(){
		  @Override
		  public void actionPerformed( ActionEvent e ){
			  count++;
			  progressBar.setValue(count);
			  if (progressBar.getValue()<10) {
				  progressBar.setValue(progressBar.getValue()+1);
			  }
			  if(progressBar.getValue()==10) {
				  timer.stop();
				  fr.setVisible(true);
				  dispose();  
			  }
  
		  }
		});
		timer.setRepeats(true);
		timer.start();
		
	}
	
	
}


