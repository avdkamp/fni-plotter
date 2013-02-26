package nfi.gui.chart;

import java.awt.RenderingHints;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.ui.ApplicationFrame;

public class FastScatterPlotter extends ApplicationFrame {
	
	/**
	 * Generated frame ID
	 */
	private static final long serialVersionUID = 1049714305982614154L;

    /** 
     * The data 
     */
    private float[][] data;
	
	public FastScatterPlotter(String title, double[] values) {
		super(title);
		
		populateData(values);
        final NumberAxis domainAxis = new NumberAxis("X - in bytes");
        domainAxis.setAutoRangeIncludesZero(false);
        final NumberAxis rangeAxis = new NumberAxis("Y - in byte value");
        rangeAxis.setAutoRangeIncludesZero(false);
        final FastScatterPlot plot = new FastScatterPlot(this.data, domainAxis, rangeAxis);
        final JFreeChart chart = new JFreeChart("Fast Scatter Plot", plot);

        // force aliasing of the rendered content..
        chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final ChartPanel panel = new ChartPanel(chart, true);
        panel.setPreferredSize(new java.awt.Dimension(500, 270));
        panel.setMinimumDrawHeight(10);
        panel.setMaximumDrawHeight(2000);
        panel.setMinimumDrawWidth(20);
        panel.setMaximumDrawWidth(2000);
        
        setContentPane(panel);
	}
	
	 /**
     * Populates the data array with values.
     */
    private void populateData(double[] values) {
    	/*
    	 * data[0][float] = x
    	 * data[1][float] = y
    	 */
    	data = new float[2][values.length];
    	
        for (int i = 0; i < values.length; i++) {
            this.data[0][i] = i;
            this.data[1][i] = (float) values[i];
        }
    }
}
