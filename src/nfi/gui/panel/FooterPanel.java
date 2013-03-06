package nfi.gui.panel;

import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nfi.ResourceLoader;

public class FooterPanel extends JPanel {

	private static final long serialVersionUID = -8859929453390075048L;
	
	private final JLabel hvaLabel = new JLabel("");
	
	public FooterPanel(){
		this.setBackground(SystemColor.menu);
		this.setBounds(0, 646, 900, 25);
		this.setLayout(null);
		hvaLabel.setIcon(ResourceLoader.loadImageIcon("/images/hva.png"));
		hvaLabel.setBounds(859, 0, 31, 25);
		
		this.add(hvaLabel);
	}
}
