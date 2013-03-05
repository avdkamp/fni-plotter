package nfi.gui.panel;

import java.awt.Color;

import javax.swing.JPanel;

public class HeaderPanel extends JPanel {

	private static final long serialVersionUID = 2707761304839864339L;
	private Color CustomColor=new Color(21,66,115);
	
	public HeaderPanel(){
		JPanel BlueHeaderPanel = new JPanel();
		BlueHeaderPanel.setBackground(CustomColor);
		BlueHeaderPanel.setBounds(0, 0, 900, 25);
	}
}
