package nfi.gui.panel;

import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import nfi.ResourceLoader;
import nfi.calc.HashChecksumGen;
import nfi.calc.ShannonEntropy;
import nfi.calc.HashChecksumGen.OnHashCalculationEventListener;
import nfi.calc.ShannonEntropy.OnShannonEntropyEventListener;
import nfi.export.MakeLogFile;

import javax.swing.JProgressBar;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLayeredPane;

public class GraphPanel extends JPanel {

	private static final long serialVersionUID = 7518936767614981258L;
	private OnGraphEventListener onGraphEventListener;
	private Color CustomColor = new Color(21, 66, 115);
	private ShannonEntropy se1;
	private ShannonEntropy se2;
	private ShannonEntropy se3;
	private static final int amountOfThreads = 3;
	private int startDrawing;
	private float[][] data;
	private int blockSize;
	private String pathToFile;
	private ChartPanel graphPanel;
	private MakeLogFile mkf;

	private final JLabel lblStatistics = new JLabel(" Statistics");
	private final JLabel lblExporteren = new JLabel("Export");
	private final JPanel statisticsPanel = new JPanel();
	private final JLabel lblGraph = new JLabel(" Graph");
	private final JLabel exportLabel = new JLabel("");
	private final JLabel textFieldFileSize = new JLabel("");
	private final JButton btnShowGetallen = new JButton("Show numbers");
	private final JProgressBar progressBar = new JProgressBar();
	private final JTextField textFieldGetSHA256 = new JTextField("");
	private final JTextField textFieldGetSHA1 = new JTextField("");
	private final JTextField textFieldGetMD5 = new JTextField("");
	private JLayeredPane layeredPane;
	private JFreeChart chart;
	private FastScatterPlot plot;
	private String filename = "";

	public GraphPanel() {
		this.setVisible(false);
		this.setBackground(Color.WHITE);
		this.setBounds(0, 119, 1152, 683);
		this.setLayout(null);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(361, 0, 781, 640);
		add(layeredPane);
		lblGraph.setBounds(34, 0, 84, 23);
		layeredPane.add(lblGraph, new Integer(1), 0);
		
		data = new float[2][0];
		drawChart(true);

		lblGraph.setOpaque(true);
		lblGraph.setForeground(Color.WHITE);
		lblGraph.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblGraph.setBackground(new Color(21, 66, 115));

		lblStatistics.setBounds(32, 375, 84, 23);
		lblStatistics.setOpaque(true);
		lblStatistics.setForeground(Color.WHITE);
		lblStatistics.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblStatistics.setBackground(new Color(21, 66, 115));
		this.add(lblStatistics);

		statisticsPanel.setBounds(10, 390, 341, 221);
		statisticsPanel.setLayout(null);
		statisticsPanel.setBackground(SystemColor.menu);
		statisticsPanel.setBorder(new LineBorder(CustomColor));

		this.add(statisticsPanel);

		JLabel lblFileSize = new JLabel("File Size:");
		lblFileSize.setBounds(10, 21, 66, 14);
		statisticsPanel.add(lblFileSize);

		JLabel lblMd5 = new JLabel("MD5:");
		lblMd5.setBounds(10, 46, 46, 14);
		statisticsPanel.add(lblMd5);

		JLabel lblSha1 = new JLabel("SHA-1:");
		lblSha1.setBounds(10, 93, 46, 14);
		statisticsPanel.add(lblSha1);

		JLabel lblSha256 = new JLabel("SHA-256:");
		lblSha256.setBounds(10, 136, 83, 14);
		statisticsPanel.add(lblSha256);

		textFieldFileSize.setBounds(86, 21, 146, 14);
		statisticsPanel.add(textFieldFileSize);

		textFieldGetMD5.setBounds(10, 68, 321, 20);
		statisticsPanel.add(textFieldGetMD5);
		textFieldGetMD5.setEnabled(false);

		textFieldGetSHA1.setBounds(10, 114, 321, 20);
		statisticsPanel.add(textFieldGetSHA1);
		textFieldGetSHA1.setEnabled(false);

		textFieldGetSHA256.setBounds(10, 161, 321, 20);
		statisticsPanel.add(textFieldGetSHA256);
		textFieldGetSHA256.setEnabled(false);

		exportLabel.setIcon(ResourceLoader.loadImageIcon("/images/export.png"));
		exportLabel.setBounds(10, 622, 50, 50);
		exportLabel.setVisible(false);

		exportLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onGraphEventListener.exportResults();
			}
		});
		this.add(exportLabel);

		progressBar.setStringPainted(true);
		progressBar.setString("Loading...");
		progressBar.setBounds(359, 647, 783, 25);
		this.add(progressBar);
		progressBar.setVisible(false);
		
		//TODO: bouw jFreeChart om zodat FastScatterPlotter ook getallen kan laten zien
//		btnShowGetallen.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (renderer.getBaseItemLabelsVisible()) {
//					renderer.setBaseItemLabelsVisible(false);
//				} else {
//					renderer.setBaseItemLabelsVisible(true);
//				}
//			}
//		});
//		btnShowGetallen.setBounds(213, 628, 136, 44);
//		btnShowGetallen.setForeground(Color.WHITE);
//		btnShowGetallen.setBackground(CustomColor);
//		btnShowGetallen.setVisible(false);
//		add(btnShowGetallen);

		lblExporteren.setBounds(64, 639, 65, 14);
		lblExporteren.setVisible(false);
		add(lblExporteren);
	}

	private void drawChart(boolean firstTime) {
		
		final NumberAxis domainAxis = new NumberAxis("Block");
        domainAxis.setAutoRangeIncludesZero(false);
        final NumberAxis rangeAxis = new NumberAxis("Entropy");
        rangeAxis.setRange(-0.25, 8);
        rangeAxis.setAutoRangeIncludesZero(false);
        if(!firstTime){
    		plot = new FastScatterPlot(se1.getResults(), se2.getResults(), se3.getResults(), domainAxis, rangeAxis);
    	} else {
    		plot = new FastScatterPlot(data, domainAxis, rangeAxis);
    		data = null;
    	}
        
        chart = new JFreeChart("", plot);
        // force aliasing of the rendered content..
        chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
		
		graphPanel = new ChartPanel(chart, true);
		graphPanel.setBounds(0, 23, 781, 615);
		layeredPane.add(graphPanel, 0);
		graphPanel.setBackground(SystemColor.menu);
		graphPanel.setBorder(new LineBorder(CustomColor));
	}
	
	private void repaintGraph(){
		startDrawing++;
    	if(startDrawing == amountOfThreads){
    		startDrawing = 0;
			this.remove(graphPanel);
			this.revalidate();
			drawChart(false);
			this.repaint();
			progressBar.setValue(100);
			progressBar.setString("Done");
			if(mkf != null){
				mkf.setData(se1.getResults(), se2.getResults(), se3.getResults());
				mkf.start();
			}
			mkf = null;
			se1 = null;
    		se2 = null;
    		se3 = null;
    	}
	}
	long start;
	public void startCalculation(boolean writeLogFile) {
		
		if(writeLogFile){
			exportOutputToTxt();
		}
		
		se1 = new ShannonEntropy(pathToFile, blockSize, 1, amountOfThreads);
		se2 = new ShannonEntropy(pathToFile, blockSize, 2, amountOfThreads);
		se3 = new ShannonEntropy(pathToFile, blockSize, 3, amountOfThreads);
		se1.setOnShannonEntropyEventListener(new OnShannonEntropyEventListener() {

			@Override
			public void onWorkerComplete() {
				repaintGraph();
			}

			@Override
			public void onProgressUpdate(int progress) {
			}
		});
		se2.setOnShannonEntropyEventListener(new OnShannonEntropyEventListener() {
			
			@Override
			public void onWorkerComplete() {
				repaintGraph();
			}
			
			@Override
			public void onProgressUpdate(int progress) {
				progressBar.setValue(progress);
				progressBar.setString(progress + "% - Calculating Entropy");
			}
		});
		se3.setOnShannonEntropyEventListener(new OnShannonEntropyEventListener() {
			
			@Override
			public void onWorkerComplete() {
				repaintGraph();
			}
			
			@Override
			public void onProgressUpdate(int progress) {
				//this thread will complete first.
			}
		});
		se1.start();
		se2.start();
		se3.start();

	}
	private void exportOutputToTxt(){
		
		JFileChooser exportDirectory = new JFileChooser("");
		exportDirectory.setDialogTitle("Select directory for Logfile");
		exportDirectory.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		exportDirectory.setAcceptAllFileFilterUsed(false);
		exportDirectory.showSaveDialog(null);
		
		mkf = new MakeLogFile(exportDirectory.getSelectedFile().getPath(), blockSize);
	}

	public void setHashes() {
		final HashChecksumGen hcg = new HashChecksumGen();
		hcg.GenerateAllHashes(pathToFile);
		hcg.setOnHashCalculationEventListener(new OnHashCalculationEventListener() {
			@Override
			public void doneCalculationAllHashes() {
				String[] hashes = hcg.getAllHashes();

				textFieldGetMD5.setEnabled(true);
				textFieldGetMD5.setText(hashes[0]);

				textFieldGetSHA1.setEnabled(true);
				textFieldGetSHA1.setText(hashes[1]);

				textFieldGetSHA256.setEnabled(true);
				textFieldGetSHA256.setText(hashes[2]);

			}
		});
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getBlockSize() {
		return this.blockSize;
	}

	public void setPathToFile(String pathToFile) {
		this.pathToFile = pathToFile;
	}

	public void setDotsFilesize(File fz) {
		long l = fz.length();
		String filesz = Long.toString(l);
		int count = 0;
		for (int i = filesz.length(); i > 0; i--) {
			if (count == 3) {
				filesz = new StringBuffer(filesz).insert(i, ".").toString();
				count = 0;
			}
			count++;
		}

		setFileName(fz.getName());
		textFieldFileSize.setText(filesz + " Bytes");
	}

	// Start Get methods for use in the export class - added by Robert
	// Ruizendaal
	public String getMD5() {

		return textFieldGetMD5.getText();
	}

	public String getSHA1() {

		return textFieldGetSHA1.getText();
	}

	public String getSHA256() {

		return textFieldGetSHA256.getText();
	}

	public String getFileSize() {
		return textFieldFileSize.getText();
	}

	public String getFilePath() {
		return pathToFile;
	}

	public void setFileName(String filename) {

		this.filename = filename;
	}

	public String getFileName() {

		return this.filename;
	}

	/*
	 * getChart() - added with Robert (mats).
	 */
	public JFreeChart getChart() {
		return chart;
	}

	// End get methods for use in the export class - added by Mats Odolphij

	/**
	 * initializes the interface
	 */
	public void setOnGraphEventListener(OnGraphEventListener listener) {
		this.onGraphEventListener = listener;
	}

	/**
	 * Inner callback interface
	 */
	public static interface OnGraphEventListener {
		public void exportResults();

	}

	public void enableButtons(Boolean hashes) {

		if (hashes) {
			textFieldGetMD5.setEnabled(true);
			textFieldGetSHA1.setEnabled(true);
			textFieldGetSHA256.setEnabled(true);
		}
		textFieldGetMD5.setEnabled(false);
		textFieldGetSHA1.setEnabled(false);
		textFieldGetSHA256.setEnabled(false);

		textFieldGetMD5.setText("");
		textFieldGetSHA1.setText("");
		textFieldGetSHA256.setText("");

		btnShowGetallen.setVisible(true);
		exportLabel.setVisible(true);
		lblExporteren.setVisible(true);
		progressBar.setVisible(true);
	}
}