package nfi.gui.panel;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ExportPanel extends JPanel {

	private static final long serialVersionUID = -8394418126759717567L;
	private OnExportEventListener onExportEventListener;

	private Color CustomColor = new Color(21, 66, 115);
	private final JPanel ExportToPDFpanel = new JPanel();
	private final JLabel lblExportToPdf = new JLabel(" Export to PDF");
	private final JLabel lblTitle = new JLabel("Title");
	private JTextField TitleTextField;
	private JTextField SINtextField;
	private final JProgressBar progressBar = new JProgressBar();
	private final JCheckBox chckbxHashes;
	private final JCheckBox chckbxFooter;
	JButton ExportToPDFbutton = new JButton("Export");
	// TODO: add a go back to graph button

	public ExportPanel() {
		this.setVisible(false);
		this.setBackground(Color.WHITE);
		this.setBounds(10, 119, 874, 516);
		this.setLayout(null);
		lblExportToPdf.setOpaque(true);
		lblExportToPdf.setForeground(Color.WHITE);
		lblExportToPdf.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblExportToPdf.setBackground(new Color(21, 66, 115));
		lblExportToPdf.setBounds(32, 0, 84, 23);

		this.add(lblExportToPdf);

		ExportToPDFpanel.setLayout(null);
		ExportToPDFpanel.setBackground(SystemColor.menu);
		ExportToPDFpanel.setBorder(new LineBorder(CustomColor));
		ExportToPDFpanel.setBounds(10, 11, 586, 326);
		this.add(ExportToPDFpanel);

		lblTitle.setBounds(10, 30, 46, 14);

		ExportToPDFpanel.add(lblTitle);

		TitleTextField = new JTextField();
		TitleTextField.setBounds(110, 27, 157, 20);
		ExportToPDFpanel.add(TitleTextField);
		TitleTextField.setColumns(10);

		JLabel lblNewLabel = new JLabel("SIN");
		lblNewLabel.setBounds(10, 55, 46, 14);
		ExportToPDFpanel.add(lblNewLabel);

		SINtextField = new JTextField();
		SINtextField.setBounds(110, 52, 157, 20);
		ExportToPDFpanel.add(SINtextField);
		SINtextField.setColumns(10);

		chckbxHashes = new JCheckBox("Hashes");
		chckbxHashes.setBounds(110, 79, 97, 23);
		ExportToPDFpanel.add(chckbxHashes);

		chckbxFooter = new JCheckBox("Footer");
		chckbxFooter.setBounds(224, 79, 97, 23);
		ExportToPDFpanel.add(chckbxFooter);

		JLabel lblAdditionalInfo = new JLabel("Additional Info");
		lblAdditionalInfo.setBounds(10, 114, 82, 14);
		ExportToPDFpanel.add(lblAdditionalInfo);

		final JTextArea AddInfoTextArea = new JTextArea();
		AddInfoTextArea.setBounds(110, 109, 440, 83);
		ExportToPDFpanel.add(AddInfoTextArea);

		progressBar.setStringPainted(true);
		progressBar.setIndeterminate(false);
		progressBar.setBounds(110, 283, 440, 25);
		
		ExportToPDFpanel.add(progressBar);
		progressBar.setVisible(false);

		
		ExportToPDFbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String title = TitleTextField.getText();
				String sin = SINtextField.getText();
				setProgressBar(true, "Exporting is in Progress!", true, false);
				// Check wether the user selected this feature
				boolean isHashSelected = chckbxHashes.isSelected();
				// Check wether the user selected this feature
				boolean isFooterSelected = chckbxFooter.isSelected();

				if (!(title.equals("") || sin.equals(""))) {
					String extraInfo = new String();
					// Set the export directory for the pdf
					JFileChooser exportDirectory = new JFileChooser(
							"Select export directory");
					exportDirectory
							.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					exportDirectory.setAcceptAllFileFilterUsed(false);
					exportDirectory.showSaveDialog(null);
					String filename = exportDirectory.getSelectedFile()
							.getName();

					// String Export directory path
					File exportPath = exportDirectory.getCurrentDirectory();

					File checkFile = new File(exportDirectory.getSelectedFile()
							.getPath() + "_entropieplot.pdf");

					if (checkFile.exists()) {
						int result = JOptionPane.showConfirmDialog(null,
								"The file exists, overwrite?", "Existing file",
								JOptionPane.YES_NO_CANCEL_OPTION);

						switch (result) {
						case JOptionPane.YES_OPTION:
							
							exportDirectory.approveSelection();

							// sin and title are required!!!!!

							if (AddInfoTextArea.getText().equals("")) {
								extraInfo = "Geen extra informatie beschikbaar.";
							} else {
								extraInfo = AddInfoTextArea.getText();
							}

							onExportEventListener.exportToPDF(title, sin,
									extraInfo, isHashSelected,
									isFooterSelected, exportPath, filename);
							return;
						case JOptionPane.CANCEL_OPTION:
							exportDirectory.cancelSelection();
							return;
						default:
							return;
						}
					} else {
						
						exportDirectory.approveSelection();
						// sin and title are required!!!!!

						if (AddInfoTextArea.getText().equals("")) {
							extraInfo = "Geen extra informatie beschikbaar.";
						} else {
							extraInfo = AddInfoTextArea.getText();
						}

						onExportEventListener.exportToPDF(title, sin,
								extraInfo, isHashSelected, isFooterSelected,
								exportPath, filename);
					}

				}else{
					JOptionPane.showMessageDialog(null,
							"The Title and SIN number fields are required!");
				}
			}

		});
		ExportToPDFbutton.setForeground(Color.WHITE);
		ExportToPDFbutton.setBackground(new Color(21, 66, 115));
		ExportToPDFbutton.setBounds(441, 203, 109, 33);
		ExportToPDFpanel.add(ExportToPDFbutton);
		
	}

	/**
	 * initializes the interface
	 */
	public void setOnExportEventListener(OnExportEventListener listener) {
		this.onExportEventListener = listener;
	}

	/**
	 * Inner callback interface
	 */
	public static interface OnExportEventListener {
		public void exportToPDF(String title, String sin, String extraInfo,
				boolean isHashSelected, boolean isFooterSelected,
				File exportPath, String filename);

	}

	public void setProgressBar(boolean activateBar, String txt, boolean visible, boolean enabledExportBtn) {
		ExportToPDFbutton.setEnabled(enabledExportBtn);
		progressBar.setVisible(visible);
		progressBar.setString(txt);
		progressBar.setIndeterminate(activateBar);

	}
}