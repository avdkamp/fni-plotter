package nfi.gui.panel;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GraphPanel extends JPanel {

	private static final long serialVersionUID = 7518936767614981258L;
	
	private Color CustomColor=new Color(21,66,115);
	private final JLabel lblStatistics = new JLabel(" Statistics");
	private final JPanel statisticsPanel = new JPanel();
	private final JPanel GraphPanel_2 = new JPanel();
	private final JLabel lblGraph = new JLabel(" Graph");
	private final JLabel backLabel = new JLabel("");
	private final JLabel updateLabel = new JLabel("");
	private final JLabel forwardLabel = new JLabel("");
	private final JLabel exportLabel = new JLabel("");
	
	private OnGraphEventListener onGraphEventListener;
	
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
		GraphPanel_2.setLayout(null);
		GraphPanel_2.setBackground(SystemColor.menu);
		GraphPanel_2.setBorder(new LineBorder(CustomColor));
		GraphPanel_2.setBounds(262, 13, 601, 492);
		
		this.add(GraphPanel_2);
		lblStatistics.setBounds(32, 0, 84, 23);
		this.add(lblStatistics);
		lblStatistics.setOpaque(true);
		lblStatistics.setForeground(Color.WHITE);
		lblStatistics.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblStatistics.setBackground(new Color(21, 66, 115));
		
		statisticsPanel.setBounds(10, 11, 242, 221);
		this.add(statisticsPanel);
		statisticsPanel.setLayout(null);
		statisticsPanel.setBackground(SystemColor.menu);
		statisticsPanel.setBorder(new LineBorder(CustomColor));
		
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
//				btnHome.setBackground(SystemColor.menu);
//				btnInfo.setBackground(SystemColor.menu);
//				infoPanel.setVisible(false);
//				plotFilePanel.setVisible(false);
//				this.setVisible(false);
//				homePanel.setVisible(false);
//				exportPanel.setVisible(true);
//				btnPlotFile.setBackground(Color.WHITE);
						
			}
		});
		this.add(exportLabel);
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
