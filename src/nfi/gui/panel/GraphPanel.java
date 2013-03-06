package nfi.gui.panel;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import nfi.gui.chart.ScatterPlotChart;

public class GraphPanel extends JPanel {

	private static final long serialVersionUID = 7518936767614981258L;
	private OnGraphEventListener onGraphEventListener;
	
	private Color CustomColor=new Color(21,66,115);
	private final JLabel lblStatistics = new JLabel(" Statistics");
	private final JPanel statisticsPanel = new JPanel();
	private final JPanel graphPanel = new JPanel();
	private final JLabel lblGraph = new JLabel("Graph");
	private final JLabel backLabel = new JLabel("");
	private final JLabel updateLabel = new JLabel("");
	private final JLabel forwardLabel = new JLabel("Forward");
	private final JLabel exportLabel = new JLabel("");
	
	private int blockSize;
	private String pathToFile;
	
	public GraphPanel(){
		this.setVisible(false);
		this.setBackground(Color.WHITE);
		this.setBounds(10, 119, 874, 516);
		this.setLayout(null);
		
		lblGraph.setOpaque(true);
		lblGraph.setForeground(Color.WHITE);
		lblGraph.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblGraph.setBackground(new Color(21, 66, 115));
		lblGraph.setBounds(284, 0, 84, 23);
		this.add(lblGraph);
		
		graphPanel.setLayout(null);
		graphPanel.setBackground(SystemColor.menu);
		graphPanel.setBorder(new LineBorder(CustomColor));
		graphPanel.setBounds(262, 13, 601, 492);
		this.add(graphPanel);
		
		lblStatistics.setBounds(32, 0, 84, 23);
		lblStatistics.setOpaque(true);
		lblStatistics.setForeground(Color.WHITE);
		lblStatistics.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblStatistics.setBackground(new Color(21, 66, 115));
		this.add(lblStatistics);
		
		statisticsPanel.setBounds(10, 11, 242, 221);
		statisticsPanel.setLayout(null);
		statisticsPanel.setBackground(SystemColor.menu);
		statisticsPanel.setBorder(new LineBorder(CustomColor));
		this.add(statisticsPanel);
		
		backLabel.setIcon(new ImageIcon("images/back.png"));
		backLabel.setBounds(33, 273, 53, 44);
		
		this.add(backLabel);
		updateLabel.setIcon(new ImageIcon("images/update.png"));
		updateLabel.setBounds(108, 275, 48, 55);
		
		this.add(updateLabel);
		forwardLabel.setIcon(new ImageIcon("images/forward.png"));
		forwardLabel.setBounds(166, 274, 58, 41);
		
		this.add(forwardLabel);
		
		exportLabel.setIcon(new ImageIcon("images/export.png"));
		exportLabel.setBounds(108, 341, 50, 50);
		exportLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onGraphEventListener.exportResults();
			}
		});
		this.add(exportLabel);
	}
	public void addGraph(){
		ScatterPlotChart spc = new ScatterPlotChart(pathToFile, blockSize);
		graphPanel.add(spc.getScatterPlotChart());
	}
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}
	public void setPathToFile(String pathToFile) {
		this.pathToFile = pathToFile;
	}
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
}
