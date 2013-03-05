package nfi.gui.panel;

import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FooterPanel extends JPanel {

	private static final long serialVersionUID = -8859929453390075048L;
	
	private final JLabel HVA_Label = new JLabel("");
	
	public FooterPanel(){
		this.setBackground(SystemColor.menu);
		this.setBounds(0, 646, 900, 25);
		this.setLayout(null);
		HVA_Label.setIcon(new ImageIcon("images/hva.png"));
		HVA_Label.setBounds(859, 0, 31, 25);
		
		this.add(HVA_Label);
	}
}
