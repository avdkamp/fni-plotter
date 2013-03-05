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
	private final JPanel StatisticsPanel = new JPanel();
	private final JPanel GraphPanel_2 = new JPanel();
	private final JLabel lblGraph = new JLabel(" Graph");
	private final JLabel BackLabel = new JLabel("");
	private final JLabel UpdateLabel = new JLabel("");
	private final JLabel ForwardLabel = new JLabel("");
	private final JLabel ExportLabel = new JLabel("");
	
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
		
		StatisticsPanel.setBounds(10, 11, 242, 221);
		this.add(StatisticsPanel);
		StatisticsPanel.setLayout(null);
		StatisticsPanel.setBackground(SystemColor.menu);
		StatisticsPanel.setBorder(new LineBorder(CustomColor));
		
		BackLabel.setIcon(new ImageIcon("images/back.png"));
		BackLabel.setBounds(33, 273, 53, 44);
		
		this.add(BackLabel);
		UpdateLabel.setIcon(new ImageIcon("images/update.png"));
		UpdateLabel.setBounds(108, 275, 48, 55);
		
		this.add(UpdateLabel);
		ForwardLabel.setIcon(new ImageIcon("images/forward.png"));
		ForwardLabel.setBounds(166, 274, 58, 41);
		
		this.add(ForwardLabel);
		ExportLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
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
		ExportLabel.setIcon(new ImageIcon("images/export.png"));
		ExportLabel.setBounds(108, 341, 50, 50);
	}
}
