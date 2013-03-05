package nfi;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;

import nfi.gui.panel.*;

import nfi.gui.panel.ExportPanel.OnExportEventListener;
import nfi.gui.panel.GraphPanel.OnGraphEventListener;
import nfi.gui.panel.MenuPanel.OnMenuEventListener;
import nfi.gui.panel.PlotFilePanel.OnPlotFileEventListener;

public class Main {
	
	private static final InfoPanel infoPanel = new InfoPanel();
	private static final HomePanel homePanel = new HomePanel();
	private static final PlotFilePanel plotFilePanel = new PlotFilePanel();
	private static final ExportPanel exportPanel = new ExportPanel();
	private static final GraphPanel graphPanel = new GraphPanel();
	private static final MenuPanel menuPanel = new MenuPanel();
	private static final HeaderPanel headerPanel = new HeaderPanel();
	private static final FooterPanel footerPanel = new FooterPanel();
	
	public static void main(String[] args) throws Exception {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					Main window = new Main();
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
		JFrame frmHi = new JFrame();
		frmHi.setResizable(false);
		frmHi.setVisible(true);
		frmHi.setTitle("Plotting Entropy");
		frmHi.setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
		frmHi.getContentPane().setBackground(Color.WHITE);
		frmHi.setBounds(100, 100, 900, 700);
		frmHi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHi.getContentPane().setLayout(null);
		
		frmHi.getContentPane().add(headerPanel);
		
		JLabel logoLabel = new JLabel("");
		logoLabel.setIcon(new ImageIcon("images/logo.png"));
		logoLabel.setBounds(26, 11, 235, 71);
		frmHi.getContentPane().add(logoLabel);
		frmHi.getContentPane().add(menuPanel);
		frmHi.getContentPane().add(homePanel);
		frmHi.getContentPane().add(plotFilePanel);
		frmHi.getContentPane().add(infoPanel);
		frmHi.getContentPane().add(graphPanel);
		frmHi.getContentPane().add(exportPanel);
		frmHi.getContentPane().add(footerPanel);
		
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
		
		plotFilePanel.setOnPlotFileEventListener(new OnPlotFileEventListener() {
			@Override
			public void showGraph() {
				infoPanel.setVisible(false);
				plotFilePanel.setVisible(false);
				graphPanel.setVisible(true);
				exportPanel.setVisible(false);
				homePanel.setVisible(false);
			}
		});
		
		graphPanel.setOnGraphEventListener(new OnGraphEventListener() {
			@Override
			public void exportResults() {
				homePanel.setVisible(false);
				plotFilePanel.setVisible(false);
				infoPanel.setVisible(false);
				graphPanel.setVisible(false);
				exportPanel.setVisible(true);
			}
		});
		
		exportPanel.setOnExportEventListener(new OnExportEventListener() {
			@Override
			public void exportToPDF() {
				//TODO: call PDF generate class here.
			}
		});
	}
}