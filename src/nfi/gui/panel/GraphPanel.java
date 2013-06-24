package nfi.gui.panel;

import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import nfi.ResourceLoader;
import nfi.calc.HashChecksumGen;
import nfi.calc.ShannonEntropy;
import nfi.calc.HashChecksumGen.OnHashCalculationEventListener;
import nfi.calc.ShannonEntropy.OnShannonEntropyEventListener;

import javax.swing.JProgressBar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.event.ChartProgressEvent;
import org.jfree.chart.event.ChartProgressListener;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GraphPanel extends JPanel {

	private static final long serialVersionUID = 7518936767614981258L;
	private OnGraphEventListener onGraphEventListener;
	private Color CustomColor = new Color(21,66,115);
	private ShannonEntropy se;
	private DefaultXYDataset dataSet;
	private double[][] data;
	private int blockSize;
	private String pathToFile;
	private ChartPanel graphPanel;
	
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
	private final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(false, true);;
	private JFreeChart chart;
	
	public GraphPanel(){
		this.setVisible(false);
		this.setBackground(Color.WHITE);
		this.setBounds(0, 119, 1152, 683);
		this.setLayout(null);
		
		lblGraph.setOpaque(true);
		lblGraph.setForeground(Color.WHITE);
		lblGraph.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblGraph.setBackground(new Color(21, 66, 115));
		lblGraph.setBounds(381, 0, 84, 23);
		this.add(lblGraph);
		
		initGraph();
		this.add(graphPanel);
		graphPanel.setLayout(null);
		
		lblStatistics.setBounds(32, 345, 84, 23);
		lblStatistics.setOpaque(true);
		lblStatistics.setForeground(Color.WHITE);
		lblStatistics.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblStatistics.setBackground(new Color(21, 66, 115));
		this.add(lblStatistics);
		
		statisticsPanel.setBounds(10, 356, 341, 221);
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
		
		btnShowGetallen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(renderer.getBaseItemLabelsVisible()){
					renderer.setBaseItemLabelsVisible(false);
				} else {
					renderer.setBaseItemLabelsVisible(true);
				}
			}
		});
		btnShowGetallen.setBounds(213, 628, 136, 44);
		
		btnShowGetallen.setForeground(Color.WHITE);
		
		btnShowGetallen.setBackground(CustomColor);
		btnShowGetallen.setVisible(false);
		add(btnShowGetallen);
		
		
		lblExporteren.setBounds(64, 639, 65, 14);
		lblExporteren.setVisible(false);
		add(lblExporteren);
	}
	
	private void initGraph(){
		
		dataSet = new DefaultXYDataset();
		
	
	 chart = ChartFactory.createScatterPlot(null, 
        			"X", 
        			"y", 
        			dataSet, 
        			PlotOrientation.VERTICAL, 
        			false, 
        			true, 
        			false);
		

		chart.addProgressListener(new ChartProgressListener() {
			
			@Override
			public void chartProgress(ChartProgressEvent cpe) {
				//only called on drawing finished and started, not really a nice progress updatelistener
				if(cpe.getType() == ChartProgressEvent.DRAWING_FINISHED){
					progressBar.setString(progressBar.getMaximum() + "% - Done Drawing");
					progressBar.setValue(progressBar.getMaximum());
				} else if(cpe.getType() == ChartProgressEvent.DRAWING_STARTED){
					progressBar.setString(progressBar.getMinimum() + "% - Drawing Chart");
					progressBar.setValue(progressBar.getMinimum());
				} else {
					progressBar.setString(cpe.getPercent() + "% - Drawing Chart");
				}
			}
			
		});
        // force aliasing of the rendered content..
        chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        chart.setBorderVisible(false);
        
        chart.setBackgroundImageAlpha(100);
        
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainGridlinesVisible(true);

        // set the plot's axes to display integers
        TickUnitSource ticks = NumberAxis.createIntegerTickUnits();
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setStandardTickUnits(ticks);
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setStandardTickUnits(ticks);
        
        plot.setRenderer(renderer);
        renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(true);
        
        graphPanel = new ChartPanel(chart, true);
        graphPanel.setBackground(SystemColor.menu);
        graphPanel.setBorder(new LineBorder(CustomColor));
        graphPanel.setBounds(361, 22, 781, 615);
        
        
        // label the points
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        XYItemLabelGenerator generator = new StandardXYItemLabelGenerator(StandardXYItemLabelGenerator.DEFAULT_ITEM_LABEL_FORMAT, format, format);
        renderer.setBaseItemLabelGenerator(generator);
        
        //Set the chart for saving.

      
        
	}
	public void startCalculation(){
		se = new ShannonEntropy(pathToFile, blockSize);
        se.setOnShannonEntropyEventListener(new OnShannonEntropyEventListener() {
			
			@Override
			public void onWorkerComplete() {
				populateData(se.getResults());
				if(dataSet.getSeriesCount() != 0){
					dataSet.removeSeries("Series0");
				}
				dataSet.addSeries("Series0", data);
			}
			
			@Override
			public void onProgressUpdate(int progress) {
				progressBar.setValue(progress);
				progressBar.setString(progress + "% - Calculating Entropy");
			}
		});
        se.run();
	}
	/**
     * Populates the data array with values.
     */
    private void populateData(ArrayList<Double> values) {
    	/*
    	 * data[0][float] = x
    	 * data[1][float] = y
    	 */
    	progressBar.setValue(progressBar.getMinimum());
    	this.data = new double[2][values.size()];
        for (int i = 0; i < values.size(); i++) {
            this.data[0][i] = i;
            this.data[1][i] = (float) values.get(i).floatValue();
            progressBar.setValue((i*100)/values.size());
            progressBar.setString(((i*100)/values.size())+ "% - Processing Data");
        }
    }
	
    
    
    public void setHashes(){
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
				
				File f = new File(pathToFile);
				textFieldFileSize.setText(f.length() + " Bytes");
			}
		});
    }
    
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
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
        textFieldFileSize.setText(filesz+" Bytes");
    }

	//Start Get methods for use in the export class - added by Robert Ruizendaal
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
	public String getFilePath(){
		return pathToFile;
	}

	/*
	 * getChart() - added with Robert (mats).
	 */
	public JFreeChart getChart() {
		return chart;
	}
	//End get methods for use in the export class - added by Mats Odolphij
	
	/**
	 * initializes the interface
	 */
	public void setOnGraphEventListener(OnGraphEventListener listener){
		this.onGraphEventListener = listener;
	}
	/**
	 * Inner callback interface
	 */
	public static interface OnGraphEventListener{
		public void exportResults();
	}
	public void enableButtons(Boolean hashes){
		if(hashes){
		textFieldGetMD5.setEnabled(true);
		textFieldGetSHA1.setEnabled(true);
		textFieldGetSHA256.setEnabled(true);
		}
		btnShowGetallen.setVisible(true);
		exportLabel.setVisible(true);
		lblExporteren.setVisible(true);
		progressBar.setVisible(true);
	}
}