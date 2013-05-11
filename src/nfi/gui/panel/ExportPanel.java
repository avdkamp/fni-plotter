package nfi.gui.panel;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ExportPanel extends JPanel {

	private static final long serialVersionUID = -8394418126759717567L;
	private OnExportEventListener onExportEventListener;
	
	private Color CustomColor=new Color(21,66,115);
	private String[] ImageOptions = {"1024x768", "800x600", "1920x1080" ,"Screen size"};
	private final JPanel ExportToPDFpanel = new JPanel();
	private final JLabel lblExportToPdf = new JLabel(" Export to PDF");
	private final JLabel lblTitle = new JLabel("Title");
	private JTextField TitleTextField;
	private JTextField SINtextField;
	private JTextField imageWidthTextField;
	private JTextField ImageHeightTextField;
	private JTextField textField;
	private final JCheckBox chckbxHashes;
	private JTextField textField_1;
	//TODO: add a go back to graph button
	
	public ExportPanel(){
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
		ExportToPDFpanel.setBounds(10, 11, 854, 247);
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
		
		JCheckBox chckbxFooter = new JCheckBox("Footer");
		chckbxFooter.setBounds(224, 79, 97, 23);
		ExportToPDFpanel.add(chckbxFooter);
		
		JLabel lblAdditionalInfo = new JLabel("Additional Info");
		lblAdditionalInfo.setBounds(10, 114, 82, 14);
		ExportToPDFpanel.add(lblAdditionalInfo);
		
		final JTextArea AddInfoTextArea = new JTextArea();
		AddInfoTextArea.setBounds(110, 109, 440, 83);
		ExportToPDFpanel.add(AddInfoTextArea);
		
		JButton ExportToPDFbutton = new JButton("Export");
		ExportToPDFbutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String extraInfo = new String();
				boolean isHashSelected = chckbxHashes.isSelected();
				
				//TODO: sin and title are required!!!!!
				String title = TitleTextField.getText();
				String sin = SINtextField.getText();
	
				if (AddInfoTextArea.getText().equals("")) {
					extraInfo = "Geen extra informatie beschikbaar.";
				} else {
					extraInfo = AddInfoTextArea.getText();
				}
				
				onExportEventListener.exportToPDF(title, sin, extraInfo, isHashSelected);
			}
		});
		ExportToPDFbutton.setForeground(Color.WHITE);
		ExportToPDFbutton.setBackground(new Color(21, 66, 115));
		ExportToPDFbutton.setBounds(441, 203, 109, 33);
		ExportToPDFpanel.add(ExportToPDFbutton);
//		
//		
//		JLabel ExportToImageLabel = new JLabel(" Export to Image");
//		ExportToImageLabel.setOpaque(true);
//		ExportToImageLabel.setForeground(Color.WHITE);
//		ExportToImageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
//		ExportToImageLabel.setBackground(new Color(21, 66, 115));
//		ExportToImageLabel.setBounds(32, 269, 84, 23);
//		this.add(ExportToImageLabel);
//		
//		JPanel ExportToImagePanel = new JPanel();
//		ExportToImagePanel.setLayout(null);
//		ExportToImagePanel.setBorder(new LineBorder(CustomColor));
//		ExportToImagePanel.setBackground(SystemColor.menu);
//		ExportToImagePanel.setBounds(10, 280, 417, 182);
//		this.add(ExportToImagePanel);
//		
//		JLabel lblWidth = new JLabel("Width");
//		lblWidth.setBounds(10, 27, 46, 14);
//		ExportToImagePanel.add(lblWidth);
//		
//		JLabel lblHeight = new JLabel("Height");
//		lblHeight.setBounds(10, 52, 46, 14);
//		ExportToImagePanel.add(lblHeight);
//		
//		imageWidthTextField = new JTextField();
//		imageWidthTextField.setBounds(112, 24, 154, 20);
//		ExportToImagePanel.add(imageWidthTextField);
//		imageWidthTextField.setColumns(10);
//		
//		ImageHeightTextField = new JTextField();
//		ImageHeightTextField.setBounds(112, 49, 154, 20);
//		ExportToImagePanel.add(ImageHeightTextField);
//		ImageHeightTextField.setColumns(10);
//		
//		
//		JComboBox<?> ExportToImageComboBox = new JComboBox<Object>(ImageOptions);
//		ExportToImageComboBox.setBounds(112, 80, 154, 20);
//		ExportToImagePanel.add(ExportToImageComboBox);
//		
//		JButton ExportToImageButton = new JButton("Export");
//		ExportToImageButton.setForeground(Color.WHITE);
//		ExportToImageButton.setBackground(new Color(21, 66, 115));
//		ExportToImageButton.setBounds(278, 123, 109, 33);
//		ExportToImagePanel.add(ExportToImageButton);
//		
//		JLabel ExportZoomedImageLabel = new JLabel(" Export Zoomed Image");
//		ExportZoomedImageLabel.setOpaque(true);
//		ExportZoomedImageLabel.setForeground(Color.WHITE);
//		ExportZoomedImageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
//		ExportZoomedImageLabel.setBackground(new Color(21, 66, 115));
//		ExportZoomedImageLabel.setBounds(469, 269, 116, 23);
//		this.add(ExportZoomedImageLabel);
//		
//		JPanel ExportZoomedImagePanel = new JPanel();
//		ExportZoomedImagePanel.setBorder(new LineBorder(CustomColor));
//		ExportZoomedImagePanel.setLayout(null);
//		ExportZoomedImagePanel.setBackground(SystemColor.menu);
//		ExportZoomedImagePanel.setBounds(447, 280, 417, 182);
//		this.add(ExportZoomedImagePanel);
//		
//		JButton ExportZoomedImageButton = new JButton("Export");
//		ExportZoomedImageButton.setForeground(Color.WHITE);
//		ExportZoomedImageButton.setBackground(new Color(21, 66, 115));
//		ExportZoomedImageButton.setBounds(278, 123, 109, 33);
//		ExportZoomedImagePanel.add(ExportZoomedImageButton);
//		
//		
//		JComboBox<?> ExportZoomedImageComboBox = new JComboBox<Object>(ImageOptions);
//		ExportZoomedImageComboBox.setBounds(112, 80, 154, 20);
//		ExportZoomedImagePanel.add(ExportZoomedImageComboBox);
//		
//		textField = new JTextField();
//		textField.setColumns(10);
//		textField.setBounds(112, 49, 154, 20);
//		ExportZoomedImagePanel.add(textField);
//		
//		textField_1 = new JTextField();
//		textField_1.setColumns(10);
//		textField_1.setBounds(112, 24, 154, 20);
//		ExportZoomedImagePanel.add(textField_1);
//		
//		JLabel label = new JLabel("Width");
//		label.setBounds(10, 27, 46, 14);
//		ExportZoomedImagePanel.add(label);
//		
//		JLabel label_1 = new JLabel("Height");
//		label_1.setBounds(10, 52, 46, 14);
//		ExportZoomedImagePanel.add(label_1);
	}
	/**
	 * initializes the interface
	 */
	public void setOnExportEventListener(OnExportEventListener listener){
		this.onExportEventListener = listener;
	}
	/**
	 * Inner callback interface
	 */
	public static interface OnExportEventListener{
		public void exportToPDF(String title, String sin, String extraInfo, boolean isHashSelected);
	}
}
