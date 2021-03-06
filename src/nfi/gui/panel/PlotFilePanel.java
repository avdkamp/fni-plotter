package nfi.gui.panel;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import nfi.ResourceLoader;
import javax.swing.JCheckBox;

public class PlotFilePanel extends JPanel {

	private static final long serialVersionUID = 3296663492279627737L;
	
	private Color CustomColor=new Color(21,66,115);
	
	private String[] PlotOptions = {"64", "128", "256", "512", "1024", "2048" ,"4096" ,"8192" ,"16384" ,"32768"};
	private JLabel Option_Label = new JLabel(" Plot Options");
	private JLabel lblBlockSize = new JLabel("Block Size");
	private JLabel lblFile = new JLabel("File");
	private JLabel lblFilePath = new JLabel("File Path");
	private JLabel DragNDrop = new JLabel("");
	private JLabel lblOr = new JLabel("Or");
	private JComboBox<?> BlockSizeComboBox = new JComboBox<Object>(PlotOptions);
	private JTextField filePathTextField;
	private JCheckBox chckbxCalculateHashes = new JCheckBox("");
	private OnPlotFileEventListener onPlotFileEventListener;
	
	private int blocksize;
	
	public PlotFilePanel(){
		
		this.setVisible(false);		
		this.setBackground(Color.WHITE);
		this.setBounds(0, 119, 360, 310);
		this.setPreferredSize(new java.awt.Dimension(1300, 700));
        
		this.setLayout(null);
		Option_Label.setBackground(CustomColor);
		Option_Label.setForeground(Color.WHITE);
		Option_Label.setBorder(new LineBorder(new Color(0, 0, 0)));
		Option_Label.setOpaque(true);
		Option_Label.setBounds(34, 0, 84, 23);
		this.add(Option_Label);
		
		JPanel PlotOptionPanel = new JPanel();
		PlotOptionPanel.setBackground(SystemColor.menu);
		PlotOptionPanel.setBorder(new LineBorder(CustomColor));
		PlotOptionPanel.setBounds(10, 15, 341, 308);
		PlotOptionPanel.setLayout(null);
		this.add(PlotOptionPanel);
		lblBlockSize.setBounds(10, 30, 84, 14);
		PlotOptionPanel.add(lblBlockSize);
		lblFile.setBounds(10, 55, 84, 14);
		PlotOptionPanel.add(lblFile);
		lblFilePath.setBounds(10, 215, 94, 14);
		PlotOptionPanel.add(lblFilePath);
		
		final JCheckBox checkLogWritten = new JCheckBox("");
		checkLogWritten.setBounds(110, 261, 37, 14);
		PlotOptionPanel.add(checkLogWritten);
		
		
		JButton btnPlotEntropy = new JButton("Plot Entropy");
		btnPlotEntropy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				blocksize = Integer.valueOf((String)BlockSizeComboBox.getSelectedItem());
				onPlotFileEventListener.showGraph(getCalcHashes(), checkLogWritten.isSelected());
			}
		});
		
		btnPlotEntropy.setBounds(222, 261, 109, 33);
		PlotOptionPanel.add(btnPlotEntropy);
		btnPlotEntropy.setBackground(CustomColor);
		btnPlotEntropy.setForeground(Color.white);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				onPlotFileEventListener.fileExplorerPanel();
			}
		});
		btnBrowse.setBounds(139, 170, 188, 33);
		PlotOptionPanel.add(btnBrowse);
		btnBrowse.setBackground(CustomColor);
		btnBrowse.setForeground(Color.white);
		DragNDrop.setBounds(118, 55, 209, 104);
		PlotOptionPanel.add(DragNDrop);
		
		DragNDrop.setIcon(ResourceLoader.loadImageIcon("/images/DragAndDrop.png"));
		lblOr.setBounds(118, 179, 29, 14);
		PlotOptionPanel.add(lblOr);
		
		filePathTextField = new JTextField();
		filePathTextField.setBounds(114, 212, 213, 20);
		PlotOptionPanel.add(filePathTextField);
		filePathTextField.setColumns(10);
		
		BlockSizeComboBox.setBounds(118, 24, 209, 20);
		BlockSizeComboBox.setEditable(true);
		BlockSizeComboBox.setSelectedIndex(3);
		PlotOptionPanel.add(BlockSizeComboBox);
		
		JLabel lblCalculateHashes = new JLabel("Calculate Hashes");
		lblCalculateHashes.setBounds(10, 240, 114, 14);
		PlotOptionPanel.add(lblCalculateHashes);
		
		
		chckbxCalculateHashes.setBounds(110, 239, 37, 14);
		PlotOptionPanel.add(chckbxCalculateHashes);
		
		JLabel lblWriteLogFile = new JLabel("Make Log File");
		lblWriteLogFile.setBounds(10, 261, 114, 14);
		PlotOptionPanel.add(lblWriteLogFile);
		
	}
	public int getBlockSize(){
		return blocksize;
	}
	
	public boolean getCalcHashes(){
		return chckbxCalculateHashes.isSelected();
	}
	
	public void setFilePathTextField(String path){
		
		String string = path.toLowerCase();
		this.filePathTextField.setText(string);
	}
	public String getPathToFile(){
		return filePathTextField.getText();
	}
	/**
	 * initializes the interface
	 */
	public void setOnPlotFileEventListener(OnPlotFileEventListener listener){
		this.onPlotFileEventListener = listener;
	}
	/**
	 * Inner callback interface
	 */
	public static interface OnPlotFileEventListener{
		public void showGraph(Boolean hashes, boolean logFileOutput);
		public void fileExplorerPanel();
	}
}
