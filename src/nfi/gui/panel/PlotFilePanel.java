package nfi.gui.panel;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import nfi.ShannonEntropy;
import nfi.ShannonEntropy.OnShannonEntropyEventListener;
import nfi.gui.chart.FastScatterPlotter;

public class PlotFilePanel extends JPanel {

	private static final long serialVersionUID = 3296663492279627737L;
	
	private Color CustomColor=new Color(21,66,115);
	
	private String[] PlotOptions = {"512", "1024", "2048" ,"4096" ,"8192" ,"16384" ,"32768"};
	private JLabel Option_Label = new JLabel(" Plot Options");
	private JLabel lblBlockSize = new JLabel("Block Size");
	private JLabel lblMemorySize = new JLabel("Memory Size");
	private JLabel lblFile = new JLabel("File");
	private JLabel lblFilePath = new JLabel("File Path");
	private JLabel DragNDrop = new JLabel("");
	private JLabel lblOr = new JLabel("Or");
	private JComboBox<?> BlockSizeComboBox = new JComboBox<Object>(PlotOptions);
	private JSpinner MemorySizeSpinner = new JSpinner();
	private JTextField FilePathTextField;
	private OnPlotFileEventListener onPlotFileEventListener;
	FastScatterPlotter graphFrame;
	
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
		lblMemorySize.setBounds(10, 62, 107, 14);
		PlotOptionPanel.add(lblMemorySize);
		lblFile.setBounds(10, 90, 84, 14);
		PlotOptionPanel.add(lblFile);
		lblFilePath.setBounds(10, 208, 94, 14);
		PlotOptionPanel.add(lblFilePath);
		
		JButton btnPlotEntropy = new JButton("Plot Entropy");
		btnPlotEntropy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				btnHome.setBackground(SystemColor.menu);
//				btnInfo.setBackground(SystemColor.menu);
//				btnPlotFile.setBackground(Color.WHITE);
//				HomePanel.setVisible(false);
//				PlotFilePanel.setVisible(false);
//				InfoPanel.setVisible(false);
//				ExportPanel.setVisible(false);
//				GraphPanel.setVisible(true);
				
				int blocksize = Integer.valueOf((String)BlockSizeComboBox.getSelectedItem());
//				int memorysize = (int) MemorySizeSpinner.getValue();
				
//				String dataFile = "C:\\Users\\Robert\\Documents\\templates\\CCS template.psd";
				String dataFile = "D:\\Downloads\\jfreechart-1.0.14\\lib\\jfreechart-1.0.14.jar";
				
				byte bytes[] = null; 
				File f = new File(dataFile);
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(f);
					bytes = new byte[(int)f.length()];
					fis.read(bytes);
					fis.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				final ShannonEntropy se = new ShannonEntropy(bytes, blocksize);
				
//				create chart example
				graphFrame = new FastScatterPlotter("Fast Scatter Plot", se);
		        graphFrame.pack();
//		        RefineryUtilities.centerFrameOnScreen(graphFrame);
		        graphFrame.setVisible(true);
			}
		});
		
		btnPlotEntropy.setBounds(387, 226, 109, 33);
		PlotOptionPanel.add(btnPlotEntropy);
		btnPlotEntropy.setBackground(CustomColor);
		btnPlotEntropy.setForeground(Color.white);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(387, 126, 109, 33);
		PlotOptionPanel.add(btnBrowse);
		btnBrowse.setBackground(CustomColor);
		btnBrowse.setForeground(Color.white);
		DragNDrop.setBounds(104, 90, 209, 104);
		PlotOptionPanel.add(DragNDrop);
		
		DragNDrop.setIcon(new ImageIcon("images/DragAndDrop.png"));
		lblOr.setBounds(337, 135, 40, 14);
		PlotOptionPanel.add(lblOr);
		
		FilePathTextField = new JTextField();
		FilePathTextField.setBounds(104, 205, 209, 20);
		PlotOptionPanel.add(FilePathTextField);
		FilePathTextField.setColumns(10);
		
		MemorySizeSpinner.setBounds(104, 59, 209, 20);
		PlotOptionPanel.add(MemorySizeSpinner);
		
		BlockSizeComboBox.setBounds(104, 34, 209, 20);
		PlotOptionPanel.add(BlockSizeComboBox);
		
//		textField.setDropTarget(new DropTarget() {
//        public synchronized void drop(DropTargetDropEvent evt) {
//            try {
//                evt.acceptDrop(DnDConstants.ACTION_COPY);
//                @SuppressWarnings("unchecked")
//				List<File> droppedFiles = (List<File>) evt
//                        .getTransferable().getTransferData(
//                                DataFlavor.javaFileListFlavor);
//                for (File file : droppedFiles) {
//                    /*
//                     * NOTE:
//                     *  When I change this to a println,
//                     *  it prints the correct path
//                     */
//                	textField.setText(file.getAbsolutePath());
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//	});
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
	}
}
