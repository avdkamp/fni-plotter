package nfi;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.itextpdf.text.DocumentException;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import nfi.gui.panel.*;

import nfi.export.*;

import nfi.gui.panel.ExportPanel.OnExportEventListener;
import nfi.gui.panel.GraphPanel.OnGraphEventListener;
import nfi.gui.panel.MenuPanel.OnMenuEventListener;
import nfi.gui.panel.PlotFilePanel.OnPlotFileEventListener;

public class Main {

	private static final PlotFilePanel plotFilePanel = new PlotFilePanel();
	private static final ExportPanel exportPanel = new ExportPanel();
	private static final GraphPanel graphPanel = new GraphPanel();
	private static final MenuPanel menuPanel = new MenuPanel();
	private static final HeaderPanel headerPanel = new HeaderPanel();
	private static final FooterPanel footerPanel = new FooterPanel();
	private final JLayeredPane layeredPane = new JLayeredPane();

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
		mainFrame.setBounds(100, 100, 1152, 864);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		layeredPane.setBounds(0, 0, 1152, 864);
		mainFrame.getContentPane().add(layeredPane);
		headerPanel.setBounds(0, 0, 1146, 25);
		layeredPane.add(headerPanel);

		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(0, 0, 235, 71);
		layeredPane.add(logoLabel);
		logoLabel.setIcon(ResourceLoader.loadImageIcon("/images/logo.png"));

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
		menuPanel.setBounds(0, 72, 1146, 25);
		layeredPane.add(menuPanel);
		menuPanel.setOnMenuEventListener(new OnMenuEventListener() {
			@Override
			public void onPlotFileClick() {

				exportPanel.setVisible(false);
				plotFilePanel.setVisible(true);
				graphPanel.setVisible(true);
			}

			

			
		});
	}

	private void plotFileEventListeners() {
		plotFilePanel.setBounds(0, 119, 360, 310);
		layeredPane.add(plotFilePanel, new Integer(1), 0);
		plotFilePanel.setOnPlotFileEventListener(new OnPlotFileEventListener() {
			@Override
			public void showGraph(Boolean hashes) {
				File f = new File(plotFilePanel.getPathToFile());

				if (!plotFilePanel.getPathToFile().isEmpty() && f.exists()) {
					graphPanel.setBlockSize(plotFilePanel.getBlockSize());
					graphPanel.setPathToFile(plotFilePanel.getPathToFile());
					graphPanel.startCalculation();
					// TODO: hashes generen moet optioneel worden
					graphPanel.enableButtons(hashes);
					if(hashes){
					graphPanel.setHashes();
					}

					plotFilePanel.setVisible(true);
					graphPanel.setVisible(true);
					exportPanel.setVisible(false);

				} else {
					JOptionPane.showMessageDialog(plotFilePanel,
							"Set a valid path!");
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
		graphPanel.setBounds(0, 119, 1152, 683);
		layeredPane.add(graphPanel);
		graphPanel.setOnGraphEventListener(new OnGraphEventListener() {
			@Override
			public void exportResults() {

				plotFilePanel.setVisible(false);

				graphPanel.setVisible(false);
				exportPanel.setVisible(true);
			}
		});
	}

	/**
	 * Call methods for export
	 */
	private void exportEventListeners() {
		footerPanel.setBounds(0, 812, 1146, 25);
		layeredPane.add(footerPanel);
		exportPanel.setBounds(0, 119, 874, 516);
		layeredPane.add(exportPanel);
		exportPanel.setOnExportEventListener(new OnExportEventListener() {
			
			@Override
			public void exportToPDF(String title, String sin, String extraInfo,
					boolean isHashSelected, boolean isFooterSelected,
					File exportPath, String filename) {
				// Beide velden moeten ingevuld zijn!
				if (!title.isEmpty() && !sin.isEmpty()) {
					// TODO: moet nog dynamisch ingesteld kunnen worden
					String path = exportPath.toString() + "\\" + filename;
					System.out.println("test 2" + path);
					final PdfExport pdf = new PdfExport(path + ".pdf");
					// Initialize chart
					JFreeChart ch = graphPanel.getChart();

					try {
						ChartUtilities.saveChartAsPNG(new File("images/Graph.png"), ch, 400, 400);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					
					// Create container for the hashes
					String[] hashes;
					hashes = new String[3];

					if (isHashSelected) {
						// TO DO  voor wanneer er in eerste instantie geen hashes zijn aangemaakt en tijdens het exporteren er wel voor word gekozen
						//moet wachten op thread waardoor je de hashes niet kan krijgen
						//graphPanel.setHashes();
						
						hashes[0] = graphPanel.getMD5();
						hashes[1] = graphPanel.getSHA256();
						hashes[2] = graphPanel.getSHA1();
					} else {
						hashes[0] = "";
						hashes[1] = "";
						hashes[2] = "";
					}

					// Set the fileSize
					String fileSize = graphPanel.getFileSize();

					// Set the filepath name
					String filePath = graphPanel.getFilePath();
					// set the header
					pdf.setHeader(title);

					// Call the setDocumentContent method with all the
					// parameters
					try {
						pdf.setDocumentContent(title, sin, extraInfo, hashes,
								fileSize, filePath);
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Set the img
					// TODO: moet nog dynamisch ingesteld kunnen worden
					String pathTest = "images/Graph.png";
					try {
						pdf.setGraphImg(pathTest);
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (DocumentException e1) {
						e1.printStackTrace();
					}

					// Set the footer - this is optional
					if (isFooterSelected) {
						try {
							pdf.setFooter();
						} catch (DocumentException e) {
							e.printStackTrace();
						}
					} else {
						// Do-nothing
					}
					// Close the document, The document can't be written to
					// after this statement.
					pdf.endDocument();
					JOptionPane.showMessageDialog(graphPanel,
							"The PDF has been exported.");
				} else {
					JOptionPane.showMessageDialog(graphPanel,
							"The Title and SIN number fields are required!");
				}

			}
		});
	}
}