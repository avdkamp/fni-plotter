package nfi;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

import nfi.gui.panel.*;
import nfi.gui.panel.MenuPanel.OnMenuEventListener;

public class Main {
	
	private InfoPanel infoPanel = new InfoPanel();
	private HomePanel homePanel = new HomePanel();
	private PlotFilePanel plotFilePanel = new PlotFilePanel();
	private ExportPanel exportPanel = new ExportPanel();
	private GraphPanel graphPanel = new GraphPanel();
	private MenuPanel menuPanel = new MenuPanel();
	private HeaderPanel headerPanel = new HeaderPanel();
	private FooterPanel footerPanel = new FooterPanel();
	
	public JFrame frmHi;
	
	public static void main(String[] args) throws Exception {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmHi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
//		System.out.println("MD5   (in hex format): " + HashChecksumGen.GenerateMD5(dataFile));
//		System.out.println("SHA1  (in hex format): " + HashChecksumGen.GenerateSHA1(dataFile));
//		System.out.println("SHA256(in hex format): " + HashChecksumGen.GenerateSHA256(dataFile));
	}
	
	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHi = new JFrame();
		frmHi.setResizable(false);
		frmHi.setTitle("Plotting Entropy");
		frmHi.setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
		frmHi.getContentPane().setBackground(Color.WHITE);
		frmHi.setBounds(100, 100, 900, 700);
		frmHi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHi.getContentPane().setLayout(null);
		
		JLabel logoLabel = new JLabel("");
		logoLabel.setIcon(new ImageIcon("images/logo.png"));
		logoLabel.setBounds(26, 11, 235, 71);
		frmHi.getContentPane().add(logoLabel);
		
		frmHi.getContentPane().add(headerPanel);
		frmHi.getContentPane().add(menuPanel);
		frmHi.getContentPane().add(footerPanel);
		frmHi.getContentPane().add(plotFilePanel);
		frmHi.getContentPane().add(exportPanel);
		frmHi.getContentPane().add(homePanel);
		frmHi.getContentPane().add(infoPanel);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(314, 416, -114, -67);
		frmHi.getContentPane().add(layeredPane);
		
		eventListeners();
	}
	
	private void eventListeners(){
		menuPanel.setOnMenuEventListener(new OnMenuEventListener() {
			
			@Override
			public void onPlotFileClick() {
				homePanel.setVisible(false);
				infoPanel.setVisible(false);
				exportPanel.setVisible(false);
				plotFilePanel.setVisible(true);
				graphPanel.setVisible(false);
			}
			
			@Override
			public void onInfoClick() {
				homePanel.setVisible(false);
				plotFilePanel.setVisible(false);
				exportPanel.setVisible(false);
				infoPanel.setVisible(true);
				graphPanel.setVisible(false);
			}
			
			@Override
			public void onHomeClick() {
				infoPanel.setVisible(false);
				plotFilePanel.setVisible(false);
				graphPanel.setVisible(false);
				exportPanel.setVisible(false);
				homePanel.setVisible(true);
			}
		});
	}
}