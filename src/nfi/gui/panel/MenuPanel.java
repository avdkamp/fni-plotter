package nfi.gui.panel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = -8850675340470968449L;

	private JButton btnPlotFile = new JButton("Plot File");

	private OnMenuEventListener onMenuEventListener;

	public MenuPanel() {
		FlowLayout flMenuPanel = (FlowLayout) this.getLayout();
		flMenuPanel.setAlignment(FlowLayout.LEFT);
		this.setBackground(new Color(240, 240, 240));
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		this.setBounds(0, 82, 900, 25);

		

		this.add(btnPlotFile);

	}

	private void setButtonEventListeners() {
		btnPlotFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				btnPlotFile.setBackground(Color.WHITE);
				onMenuEventListener.onPlotFileClick();
				
			}
		});

		btnPlotFile.doClick();
	}

	

	/*
	 * initializes the interface
	 */
	public void setOnMenuEventListener(OnMenuEventListener listener) {
		this.onMenuEventListener = listener;

		setButtonEventListeners();
	}

	/*
	 * Inner callback interface
	 */
	public static interface OnMenuEventListener {
		

		public void onPlotFileClick();

		
	}
}
