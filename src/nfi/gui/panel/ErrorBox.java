package nfi.gui.panel;

import javax.swing.*;


public class ErrorBox {

	public ErrorBox(JPanel panel, String error) {

		JOptionPane.showMessageDialog(panel, error);

	}
}
