package nfi.gui.chart;

import java.awt.Color;
import java.awt.RenderingHints;
import java.text.NumberFormat;

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
import org.jfree.ui.ApplicationFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;

public class FastScatterPlotter extends ApplicationFrame {
	
	/**
	 * Generated frame ID
	 */
	private static final long serialVersionUID = 1049714305982614154L;
	private JFreeChart chart;
    /** 
     * The data 
     */
    private double[][] data;
    private final JButton btnNewButton = new JButton("Start");
    private JButton btnNewButton_1;
	
	public FastScatterPlotter(String title, final ShannonEntropy se) {
		super(title);
		
		final DefaultXYDataset dataSet = new DefaultXYDataset();
		
        chart = ChartFactory.createScatterPlot("titel", 
        			"X", 
        			"y", 
        			dataSet, 
        			PlotOrientation.VERTICAL, 
        			false, 
        			true, 
        			false);
        // force aliasing of the rendered content..
        chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        chart.setBackgroundPaint(Color.white);
        
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(0xffffe0));
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);

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
        
        final ChartPanel panel = new ChartPanel(chart, true);
        panel.setPreferredSize(new java.awt.Dimension(1300, 700));
        panel.setMinimumDrawHeight(10);
        panel.setMaximumDrawHeight(2000);
        panel.setMinimumDrawWidth(20);
        panel.setMaximumDrawWidth(2000);
        
        setContentPane(panel);
        btnNewButton.setBounds(280, 5, 89, 23);
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		se.run();
        	}
        });
        panel.setLayout(null);
        
        btnNewButton_1 = new JButton("Toggle");
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(renderer.getBaseItemLabelsVisible()){
        			renderer.setBaseItemLabelsVisible(false);
        		} else {
        			renderer.setBaseItemLabelsVisible(true);
        		}
        	}
        });
        btnNewButton_1.setBounds(58, 5, 89, 23);
        panel.add(btnNewButton_1);
        panel.add(btnNewButton);
        
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
			public void onProgressUpdate() {
				
			}
		});
	}
	
	 /**
     * Populates the data array with values.
     */
    private void populateData(double[] values) {
    	/*
    	 * data[0][float] = x
    	 * data[1][float] = y
    	 */
    	data = new double[2][values.length];
    	
        for (int i = 0; i < values.length; i++) {
            this.data[0][i] = i;
            this.data[1][i] = (float) values[i];
        }
    }
}
