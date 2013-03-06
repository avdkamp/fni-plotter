package nfi.gui.chart;

import java.awt.RenderingHints;
import java.text.NumberFormat;
import java.util.ArrayList;

import nfi.ShannonEntropy;
import nfi.ShannonEntropy.OnShannonEntropyEventListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;

public class ScatterPlotChart {
	/**
	 * Generated frame ID
	 */
	private JFreeChart chart;
	private ChartPanel panel;
    /** 
     * The data 
     */
    private double[][] data;
//    private final JButton btnNewButton = new JButton("Start");
//    private JButton btnNewButton_1;
	
	public ScatterPlotChart(String pathToFile, int blockSize) {
		
		final DefaultXYDataset dataSet = new DefaultXYDataset();
		
        chart = ChartFactory.createScatterPlot(null, 
        			"X", 
        			"y", 
        			dataSet, 
        			PlotOrientation.VERTICAL, 
        			false, 
        			true, 
        			false);
        // force aliasing of the rendered content..
        chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        chart.setBackgroundPaint(Color.OPAQUE);
        chart.setBorderVisible(false);
        chart.setBackgroundImageAlpha(100);
        
        XYPlot plot = (XYPlot) chart.getPlot();
//        plot.setBackgroundPaint(new Color(0xffffe0));
        plot.setDomainGridlinesVisible(true);
//        plot.setDomainGridlinePaint(Color.lightGray);
//        plot.setRangeGridlinePaint(Color.lightGray);

        // set the plot's axes to display integers
        TickUnitSource ticks = NumberAxis.createIntegerTickUnits();
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setStandardTickUnits(ticks);
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setStandardTickUnits(ticks);
        
        final XYLineAndShapeRenderer renderer =
                new XYLineAndShapeRenderer(true, true);
            plot.setRenderer(renderer);
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
        
     // label the points
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        XYItemLabelGenerator generator =
            new StandardXYItemLabelGenerator(
                StandardXYItemLabelGenerator.DEFAULT_ITEM_LABEL_FORMAT,
                format, format);
        renderer.setBaseItemLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);
        
//        btnNewButton.setBounds(157, 5, 89, 23);
//        btnNewButton.addActionListener(new ActionListener() {
//        	public void actionPerformed(ActionEvent e) {
//        		se.run();
//        	}
//        });
////        panel.setLayout(null);
//        
//        btnNewButton_1 = new JButton("Toggle");
//        btnNewButton_1.addActionListener(new ActionListener() {
//        	public void actionPerformed(ActionEvent e) {
//        		if(renderer.getBaseItemLabelsVisible()){
//        			renderer.setBaseItemLabelsVisible(false);
//        		} else {
//        			renderer.setBaseItemLabelsVisible(true);
//        		}
//        	}
//        });
//        btnNewButton_1.setBounds(58, 5, 89, 23);
//        panel.add(btnNewButton_1);
//        panel.add(btnNewButton);
        
//        final JProgressBar progressBar = new JProgressBar();
//        progressBar.setBounds(353, 5, 262, 20);
//        progressBar.setValue(0);
//        panel.add(progressBar);
        
        final ShannonEntropy se = new ShannonEntropy(pathToFile, blockSize);
        se.setOnShannonEntropyEventListener(new OnShannonEntropyEventListener() {
			
			@Override
			public void onWorkerComplete() {
				populateData(se.getResults());
				if(dataSet.getSeriesCount() != 0){
					dataSet.removeSeries("Series0");
				}
//				progressBar.setValue(100);
				dataSet.addSeries("Series0", data);
			}
			
			@Override
			public void onProgressUpdate() {
//				progressBar.setValue(se.getProgressChunk());
			}
		});
        se.run();
	}
	
	public ChartPanel getScatterPlotChart(){
		panel = new ChartPanel(chart, true);
		panel.setBounds(0, 0, 601, 492);
		return panel;
	}
	 /**
     * Populates the data array with values.
     */
    private void populateData(ArrayList<Double> values) {
    	/*
    	 * data[0][float] = x
    	 * data[1][float] = y
    	 */
    	this.data = new double[2][values.size()];
        for (int i = 0; i < values.size(); i++) {
            this.data[0][i] = i;
            this.data[1][i] = (float) values.get(i).floatValue();
        }
    }
}
