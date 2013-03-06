package nfi.gui.panel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = -8850675340470968449L;
	
	private JButton btnHome = new JButton("Home");
	private JButton btnInfo = new JButton("Info");
	private JButton btnPlotFile = new JButton("Plot File");
	private JButton btnGraph = new JButton("Graph");
	private OnMenuEventListener onMenuEventListener;
	
	public MenuPanel(){
		FlowLayout flMenuPanel = (FlowLayout) this.getLayout();
		flMenuPanel.setAlignment(FlowLayout.LEFT);
		this.setBackground(new Color(240, 240, 240));
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		this.setBounds(0, 82, 900, 25);
		
		btnHome.setBackground(SystemColor.menu);
		btnPlotFile.setBackground(SystemColor.menu);
		btnInfo.setBackground(SystemColor.menu);
		btnGraph.setBackground(SystemColor.menu);
		
		this.add(btnHome);
		this.add(btnPlotFile);
		this.add(btnInfo);
		this.add(btnGraph);
	}
	private void setButtonEventListeners(){
		btnPlotFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnHome.setBackground(SystemColor.menu);
				btnInfo.setBackground(SystemColor.menu);
				btnGraph.setBackground(SystemColor.menu);
				btnPlotFile.setBackground(Color.WHITE);
				onMenuEventListener.onPlotFileClick();
			}
		});
		
		btnInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnHome.setBackground(SystemColor.menu);
				btnPlotFile.setBackground(SystemColor.menu);
				btnGraph.setBackground(SystemColor.menu);
				btnInfo.setBackground(Color.WHITE);
				onMenuEventListener.onInfoClick();
			}
		});
		
		btnHome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnPlotFile.setBackground(SystemColor.menu);
				btnInfo.setBackground(SystemColor.menu);
				btnGraph.setBackground(SystemColor.menu);
				btnHome.setBackground(Color.WHITE);
				onMenuEventListener.onHomeClick();
			}
		});
		btnGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnPlotFile.setBackground(SystemColor.menu);
				btnInfo.setBackground(SystemColor.menu);
				btnHome.setBackground(SystemColor.menu);
				btnGraph.setBackground(Color.WHITE);
				onMenuEventListener.onGraphClick();
			}
		});
		btnGraph.setVisible(false);
		btnPlotFile.doClick();
	}
	public void showGraphBtn(){
		btnGraph.setVisible(true);
		btnPlotFile.setBackground(SystemColor.menu);
		btnInfo.setBackground(SystemColor.menu);
		btnHome.setBackground(SystemColor.menu);
		btnGraph.setBackground(Color.WHITE);
	}
	/*
	 * initializes the interface
	 */
	public void setOnMenuEventListener(OnMenuEventListener listener){
		this.onMenuEventListener = listener;
		
		setButtonEventListeners();
	}
	/*
	 * Inner callback interface
	 */
	public static interface OnMenuEventListener{
		public void onHomeClick();
		public void onPlotFileClick();
		public void onInfoClick();
		public void onGraphClick();
	}
}