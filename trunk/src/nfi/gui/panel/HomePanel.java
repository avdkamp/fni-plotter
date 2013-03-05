package nfi.gui.panel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextPane;

public class HomePanel extends JPanel {

	private static final long serialVersionUID = -4685977445572179372L;
	private final JTextPane txtpnAtVeroEos_1 = new JTextPane();
	
	public HomePanel(){
		
		this.setVisible(false);
		this.setBackground(Color.WHITE);
		this.setBounds(10, 119, 874, 516);
		this.setLayout(null);
		
		txtpnAtVeroEos_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtpnAtVeroEos_1.setText("HomeAt vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.");
		txtpnAtVeroEos_1.setBounds(10, 11, 854, 261);
		this.add(txtpnAtVeroEos_1);
	}
}
