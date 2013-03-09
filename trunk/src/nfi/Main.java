package nfi;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

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
		JFrame mainFrame = new JFrame();
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		mainFrame.setTitle("Plotting Entropy");
		mainFrame.setIconImage(ResourceLoader.loadImage("/images/icon.png"));
		mainFrame.getContentPane().setBackground(Color.WHITE);
		mainFrame.setBounds(100, 100, 900, 700);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);

		mainFrame.getContentPane().add(headerPanel);

		JLabel logoLabel = new JLabel("");
		logoLabel.setIcon(ResourceLoader.loadImageIcon("/images/logo.png"));
		logoLabel.setBounds(26, 11, 235, 71);
		mainFrame.getContentPane().add(logoLabel);
		mainFrame.getContentPane().add(menuPanel);
		mainFrame.getContentPane().add(homePanel);
		mainFrame.getContentPane().add(plotFilePanel);
		mainFrame.getContentPane().add(infoPanel);
		mainFrame.getContentPane().add(graphPanel);
		mainFrame.getContentPane().add(exportPanel);
		mainFrame.getContentPane().add(footerPanel);

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(314, 416, -114, -67);
		mainFrame.getContentPane().add(layeredPane);

		// TODO: is windows only op het moment, moet ook compatible met linux
		// zijn
		mainFrame.setDropTarget(new DropTarget() {
			private static final long serialVersionUID = 1284909278859440490L;

			@SuppressWarnings("unchecked")
			@Override
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					List<File> droppedFiles = (List<File>) evt
							.getTransferable().getTransferData(
									DataFlavor.javaFileListFlavor);
					for (File file : droppedFiles) {
						plotFilePanel.setFilePathTextField(file
								.getAbsolutePath());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		eventListeners();
	}

	private void eventListeners() {
		menuEventListeners();
		plotFileEventListeners();
		graphEventListeners();
		exportEventListeners();
	}

	private void menuEventListeners() {
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

			@Override
			public void onGraphClick() {
				infoPanel.setVisible(false);
				plotFilePanel.setVisible(false);
				graphPanel.setVisible(true);
				exportPanel.setVisible(false);
				homePanel.setVisible(false);
			}
		});
	}

	private void plotFileEventListeners() {
		plotFilePanel.setOnPlotFileEventListener(new OnPlotFileEventListener() {
			@Override
			public void showGraph() {
//				String pattern = "^(?:[\\w]\\:|\\\\)(\\\\[a-z_\\-\\s0-9\\.]+)+\\.(?i)(txt|gif|pdf|doc|docx|xls|xlsx)$";
//				if (!plotFilePanel.getPathToFile().isEmpty() && plotFilePanel.getPathToFile().matches(pattern)) {
				if (!plotFilePanel.getPathToFile().isEmpty()) {
					graphPanel.setBlockSize(plotFilePanel.getBlockSize());
					graphPanel.setPathToFile(plotFilePanel.getPathToFile());
					graphPanel.startCalculation();
					menuPanel.showGraphBtn();
					infoPanel.setVisible(false);
					plotFilePanel.setVisible(false);
					graphPanel.setVisible(true);
					exportPanel.setVisible(false);
					homePanel.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(plotFilePanel, "Set a valid path!");
					
				}
			}

			@Override
			public void fileExplorerPanel() {
				final JFileChooser fc = new JFileChooser();

				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.showOpenDialog(fc);
				
				File file = fc.getSelectedFile();

				plotFilePanel.setFilePathTextField(file.getAbsolutePath());
			}
		});
	}

	private void graphEventListeners() {
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
	}

	private void exportEventListeners() {
		exportPanel.setOnExportEventListener(new OnExportEventListener() {
			@Override
			public void exportToPDF() {
				// TODO: call PDF generate class here.
			}
		});
	}
}