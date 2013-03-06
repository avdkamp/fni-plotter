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
	private OnPlotFileEventListener onPlotFileEventListener;
	private int blocksize;
	
	public PlotFilePanel(){
		
		this.setVisible(false);		
		this.setBackground(Color.WHITE);
		this.setBounds(10, 119, 874, 516);
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
		PlotOptionPanel.setBounds(10, 11, 507, 272);
		PlotOptionPanel.setLayout(null);
		this.add(PlotOptionPanel);
		lblBlockSize.setBounds(10, 37, 84, 14);
		PlotOptionPanel.add(lblBlockSize);
		lblFile.setBounds(10, 90, 84, 14);
		PlotOptionPanel.add(lblFile);
		lblFilePath.setBounds(10, 208, 94, 14);
		PlotOptionPanel.add(lblFilePath);
		
		JButton btnPlotEntropy = new JButton("Plot Entropy");
		btnPlotEntropy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				blocksize = Integer.valueOf((String)BlockSizeComboBox.getSelectedItem());
				onPlotFileEventListener.showGraph();
			}
		});
		
		btnPlotEntropy.setBounds(387, 226, 109, 33);
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
		btnBrowse.setBounds(387, 126, 109, 33);
		PlotOptionPanel.add(btnBrowse);
		btnBrowse.setBackground(CustomColor);
		btnBrowse.setForeground(Color.white);
		DragNDrop.setBounds(104, 90, 209, 104);
		PlotOptionPanel.add(DragNDrop);
		
		DragNDrop.setIcon(ResourceLoader.loadImageIcon("/images/DragAndDrop.png"));
		lblOr.setBounds(337, 135, 40, 14);
		PlotOptionPanel.add(lblOr);
		
		filePathTextField = new JTextField();
		filePathTextField.setBounds(104, 205, 209, 20);
		PlotOptionPanel.add(filePathTextField);
		filePathTextField.setColumns(10);
		
		BlockSizeComboBox.setBounds(104, 34, 209, 20);
		BlockSizeComboBox.setEditable(true);
		BlockSizeComboBox.setSelectedIndex(3);
		PlotOptionPanel.add(BlockSizeComboBox);
		
	}
	public int getBlockSize(){
		return blocksize;
	}
	public void setFilePathTextField(String path){
		String str = path.replace("\\", "/");
		this.filePathTextField.setText(str);
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
		public void showGraph();
		public void fileExplorerPanel();
	}
}
