package nfi;
import java.awt.EventQueue;


import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;

import javax.swing.JButton;

//import java.awt.datatransfer.DataFlavor;
//import java.awt.dnd.DnDConstants;
//import java.awt.dnd.DropTarget;
//import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.border.LineBorder;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;

import nfi.gui.chart.FastScatterPlotter;

import org.jfree.ui.RefineryUtilities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.io.File;
//import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



public class GUI {

	private JFrame frmHi;
	private String[] ImageOptions = {"1024x768", "800x600", "1920x1080" ,"Screen size"};
	private Color CustomColor=new Color(21,66,115);
	private String[] PlotOptions = {"512", "1024", "2048" ,"4096" ,"8192" ,"16384" ,"32768"};
	private JComboBox<?> BlockSizeComboBox = new JComboBox<Object>(PlotOptions);
	private JSpinner MemorySizeSpinner = new JSpinner();
	private final JPanel Menu_Panel = new JPanel();
	private final JPanel PlotFilePanel = new JPanel();
	private final JPanel GraphPanel = new JPanel();
	private final JPanel ExportPanel = new JPanel();
	JButton btnPlotFile = new JButton("Plot File");
	JButton btnHome = new JButton("Home");
	JButton btnInfo = new JButton("Info");
	private final JPanel InfoPanel = new JPanel();
	private final JPanel HomePanel = new JPanel();
	private final JLabel HVA_Label = new JLabel("");
	private final JLabel Option_Label = new JLabel(" Plot Options");
	private final JLabel lblBlockSize = new JLabel("Block Size");
	private final JLabel lblMemorySize = new JLabel("Memory Size");
	private final JLabel lblFile = new JLabel("File");
	private final JLabel lblFilePath = new JLabel("File Path");
	private final JLabel DragNDrop = new JLabel("");
	private final JLabel lblOr = new JLabel("Or");
	private JTextField FilePathTextField;
	private final JTextPane txtpnAtVeroEos = new JTextPane();
	private final JTextPane txtpnAtVeroEos_1 = new JTextPane();
	private final JLabel lblStatistics = new JLabel(" Statistics");
	private final JPanel StatisticsPanel = new JPanel();
	private final JPanel GraphPanel_2 = new JPanel();
	private final JLabel lblGraph = new JLabel(" Graph");
	private final JLabel BackLabel = new JLabel("");
	private final JLabel UpdateLabel = new JLabel("");
	private final JLabel ForwardLabel = new JLabel("");
	private final JLabel ExportLabel = new JLabel("");
	private final JPanel ExportToPDFpanel = new JPanel();
	private final JLabel lblExportToPdf = new JLabel(" Export to PDF");
	private final JLabel lblTitle = new JLabel("Title");
	private JTextField TitleTextField;
	private JTextField SINtextField;
	private JTextField imageWidthTextField;
	private JTextField ImageHeightTextField;
	private JTextField textField;
	private JTextField textField_1;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmHi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHi = new JFrame();
		frmHi.setResizable(false);
		frmHi.setTitle("Plotting Entropy");
		frmHi.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/images/icon.png")));
		frmHi.getContentPane().setBackground(Color.WHITE);
		frmHi.setBounds(100, 100, 900, 700);
		frmHi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHi.getContentPane().setLayout(null);
		
		
		JPanel Blue_Header_Panel = new JPanel();
		Blue_Header_Panel.setBackground(CustomColor);
		Blue_Header_Panel.setBounds(0, 0, 900, 25);
		frmHi.getContentPane().add(Blue_Header_Panel);
		
		JLabel Logo_label = new JLabel("");
		Logo_label.setIcon(new ImageIcon(GUI.class.getResource("/images/logo.png")));
		Logo_label.setBounds(26, 11, 235, 71);
		frmHi.getContentPane().add(Logo_label);
		FlowLayout fl_Menu_Panel = (FlowLayout) Menu_Panel.getLayout();
		fl_Menu_Panel.setAlignment(FlowLayout.LEFT);
		Menu_Panel.setBackground(new Color(240, 240, 240));
		Menu_Panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		Menu_Panel.setBounds(0, 82, 900, 25);
		frmHi.getContentPane().add(Menu_Panel);
		
		btnHome.setBackground(SystemColor.menu);
		btnPlotFile.setBackground(SystemColor.menu);
		
		btnInfo.setBackground(SystemColor.menu);
		
		
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPlotFile.setBackground(SystemColor.menu);
				btnInfo.setBackground(SystemColor.menu);
				InfoPanel.setVisible(false);
				PlotFilePanel.setVisible(false);
				GraphPanel.setVisible(false);
				ExportPanel.setVisible(false);
						btnHome.setBackground(Color.WHITE);
						HomePanel.setVisible(true);
						
					
			}
		});
		btnPlotFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnHome.setBackground(SystemColor.menu);
				btnInfo.setBackground(SystemColor.menu);
				HomePanel.setVisible(false);
				InfoPanel.setVisible(false);
				ExportPanel.setVisible(false);
						btnPlotFile.setBackground(Color.WHITE);
						PlotFilePanel.setVisible(true);
						GraphPanel.setVisible(false);
					
			}
		});
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnHome.setBackground(SystemColor.menu);
				btnPlotFile.setBackground(SystemColor.menu);
				HomePanel.setVisible(false);
				PlotFilePanel.setVisible(false);
				ExportPanel.setVisible(false);
						btnInfo.setBackground(Color.WHITE);
						InfoPanel.setVisible(true);
						GraphPanel.setVisible(false);
					
			}
		});
		
		
		
		Menu_Panel.add(btnHome);
		Menu_Panel.add(btnPlotFile);
		
		Menu_Panel.add(btnInfo);
		
		JPanel Footer_Panel = new JPanel();
		Footer_Panel.setBackground(SystemColor.menu);
		Footer_Panel.setBounds(0, 646, 900, 25);
		frmHi.getContentPane().add(Footer_Panel);
		Footer_Panel.setLayout(null);
		HVA_Label.setIcon(new ImageIcon(GUI.class.getResource("/images/hva.png")));
		HVA_Label.setBounds(859, 0, 31, 25);
		
		Footer_Panel.add(HVA_Label);
		
		
		ExportPanel.setVisible(false);		
		
		GraphPanel.setVisible(false);		
		
		PlotFilePanel.setVisible(false);		
		PlotFilePanel.setBackground(Color.WHITE);
		PlotFilePanel.setBounds(10, 119, 874, 516);
		frmHi.getContentPane().add(PlotFilePanel);
		PlotFilePanel.setLayout(null);
		Option_Label.setBackground(CustomColor);
		Option_Label.setForeground(Color.WHITE);
		Option_Label.setBorder(new LineBorder(new Color(0, 0, 0)));
		Option_Label.setOpaque(true);
		Option_Label.setBounds(34, 0, 84, 23);
		PlotFilePanel.add(Option_Label);
		
		JPanel PlotOptionPanel = new JPanel();
		PlotOptionPanel.setBackground(SystemColor.menu);
		PlotOptionPanel.setBorder(new LineBorder(CustomColor));
		PlotOptionPanel.setBounds(10, 11, 507, 272);
		PlotFilePanel.add(PlotOptionPanel);
		PlotOptionPanel.setLayout(null);
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
			public void actionPerformed(ActionEvent e) {
				btnHome.setBackground(SystemColor.menu);
				btnInfo.setBackground(SystemColor.menu);
				btnPlotFile.setBackground(Color.WHITE);
				HomePanel.setVisible(false);
				PlotFilePanel.setVisible(false);
				InfoPanel.setVisible(false);
				ExportPanel.setVisible(false);
				GraphPanel.setVisible(true);
				
				int blocksize = Integer.valueOf((String)BlockSizeComboBox.getSelectedItem());
				int memorysize = (int) MemorySizeSpinner.getValue();
				
				System.out.println(blocksize);
				String dataFile = "C:\\Users\\Robert\\Documents\\templates\\CCS template.psd";
//				String dataFile = "D:\\Downloads\\jfreechart-1.0.14\\lib\\jfreechart-1.0.14.jar";
				
				byte bytes[] = null; 
				File f = new File(dataFile);
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(f);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				bytes = new byte[(int)f.length()];
				try {
					fis.read(bytes);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					fis.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				final ShannonEntropy se = new ShannonEntropy(bytes, blocksize);
				
//				create chart example
				final FastScatterPlotter graphFrame = new FastScatterPlotter("Fast Scatter Plot", se);
		        graphFrame.pack();
		        RefineryUtilities.centerFrameOnScreen(graphFrame);
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
		
		DragNDrop.setIcon(new ImageIcon(GUI.class.getResource("/images/DragAndDrop.png")));
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
		GraphPanel.setBackground(Color.WHITE);
		GraphPanel.setBounds(10, 119, 874, 516);
		frmHi.getContentPane().add(GraphPanel);
		GraphPanel.setLayout(null);
		
		lblGraph.setOpaque(true);
		lblGraph.setForeground(Color.WHITE);
		lblGraph.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblGraph.setBackground(new Color(21, 66, 115));
		lblGraph.setBounds(284, 0, 84, 23);
		
		GraphPanel.add(lblGraph);
		GraphPanel_2.setLayout(null);
		GraphPanel_2.setBackground(SystemColor.menu);
		GraphPanel_2.setBorder(new LineBorder(CustomColor));
		GraphPanel_2.setBounds(262, 13, 601, 492);
		
		GraphPanel.add(GraphPanel_2);
		lblStatistics.setBounds(32, 0, 84, 23);
		GraphPanel.add(lblStatistics);
		lblStatistics.setOpaque(true);
		lblStatistics.setForeground(Color.WHITE);
		lblStatistics.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblStatistics.setBackground(new Color(21, 66, 115));
		
		StatisticsPanel.setBounds(10, 11, 242, 221);
		GraphPanel.add(StatisticsPanel);
		StatisticsPanel.setLayout(null);
		StatisticsPanel.setBackground(SystemColor.menu);
		StatisticsPanel.setBorder(new LineBorder(CustomColor));
		
		BackLabel.setIcon(new ImageIcon(GUI.class.getResource("/images/back.png")));
		BackLabel.setBounds(33, 273, 53, 44);
		
		GraphPanel.add(BackLabel);
		UpdateLabel.setIcon(new ImageIcon(GUI.class.getResource("/images/update.png")));
		UpdateLabel.setBounds(108, 275, 48, 55);
		
		GraphPanel.add(UpdateLabel);
		ForwardLabel.setIcon(new ImageIcon(GUI.class.getResource("/images/forward.png")));
		ForwardLabel.setBounds(166, 274, 58, 41);
		
		GraphPanel.add(ForwardLabel);
		ExportLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnHome.setBackground(SystemColor.menu);
				btnInfo.setBackground(SystemColor.menu);
				InfoPanel.setVisible(false);
				PlotFilePanel.setVisible(false);
				GraphPanel.setVisible(false);
				HomePanel.setVisible(false);
				ExportPanel.setVisible(true);
				btnPlotFile.setBackground(Color.WHITE);
						
			}
		});
		ExportLabel.setIcon(new ImageIcon(GUI.class.getResource("/images/export.png")));
		ExportLabel.setBounds(108, 341, 50, 50);
		
		GraphPanel.add(ExportLabel);
		ExportPanel.setBackground(Color.WHITE);
		ExportPanel.setBounds(10, 119, 874, 516);
		frmHi.getContentPane().add(ExportPanel);
		ExportPanel.setLayout(null);
		lblExportToPdf.setOpaque(true);
		lblExportToPdf.setForeground(Color.WHITE);
		lblExportToPdf.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblExportToPdf.setBackground(new Color(21, 66, 115));
		lblExportToPdf.setBounds(32, 0, 84, 23);
		
		ExportPanel.add(lblExportToPdf);
		ExportToPDFpanel.setLayout(null);
		ExportToPDFpanel.setBackground(SystemColor.menu);
		ExportToPDFpanel.setBorder(new LineBorder(CustomColor));
		ExportToPDFpanel.setBounds(10, 11, 854, 247);
		
		ExportPanel.add(ExportToPDFpanel);
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
		
		JCheckBox chckbxHashes = new JCheckBox("Hashes");
		chckbxHashes.setBounds(110, 79, 97, 23);
		ExportToPDFpanel.add(chckbxHashes);
		
		JCheckBox chckbxFooter = new JCheckBox("Footer");
		chckbxFooter.setBounds(224, 79, 97, 23);
		ExportToPDFpanel.add(chckbxFooter);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Zoomed Image");
		chckbxNewCheckBox.setBounds(345, 79, 97, 23);
		ExportToPDFpanel.add(chckbxNewCheckBox);
		
		JLabel lblAdditionalInfo = new JLabel("Additional Info");
		lblAdditionalInfo.setBounds(10, 114, 82, 14);
		ExportToPDFpanel.add(lblAdditionalInfo);
		
		JTextArea AddInfoTextArea = new JTextArea();
		AddInfoTextArea.setBounds(110, 109, 440, 83);
		ExportToPDFpanel.add(AddInfoTextArea);
		
		JButton ExportToPDFbutton = new JButton("Export");
		ExportToPDFbutton.setForeground(Color.WHITE);
		ExportToPDFbutton.setBackground(new Color(21, 66, 115));
		ExportToPDFbutton.setBounds(441, 203, 109, 33);
		ExportToPDFpanel.add(ExportToPDFbutton);
		
		JLabel ExportToImageLabel = new JLabel(" Export to Image");
		ExportToImageLabel.setOpaque(true);
		ExportToImageLabel.setForeground(Color.WHITE);
		ExportToImageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		ExportToImageLabel.setBackground(new Color(21, 66, 115));
		ExportToImageLabel.setBounds(32, 269, 84, 23);
		ExportPanel.add(ExportToImageLabel);
		
		JPanel ExportToImagePanel = new JPanel();
		ExportToImagePanel.setLayout(null);
		ExportToImagePanel.setBorder(new LineBorder(CustomColor));
		ExportToImagePanel.setBackground(SystemColor.menu);
		ExportToImagePanel.setBounds(10, 280, 417, 182);
		ExportPanel.add(ExportToImagePanel);
		
		JLabel lblWidth = new JLabel("Width");
		lblWidth.setBounds(10, 27, 46, 14);
		ExportToImagePanel.add(lblWidth);
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setBounds(10, 52, 46, 14);
		ExportToImagePanel.add(lblHeight);
		
		imageWidthTextField = new JTextField();
		imageWidthTextField.setBounds(112, 24, 154, 20);
		ExportToImagePanel.add(imageWidthTextField);
		imageWidthTextField.setColumns(10);
		
		ImageHeightTextField = new JTextField();
		ImageHeightTextField.setBounds(112, 49, 154, 20);
		ExportToImagePanel.add(ImageHeightTextField);
		ImageHeightTextField.setColumns(10);
		
		
		JComboBox<?> ExportToImageComboBox = new JComboBox<Object>(ImageOptions);
		ExportToImageComboBox.setBounds(112, 80, 154, 20);
		ExportToImagePanel.add(ExportToImageComboBox);
		
		JButton ExportToImageButton = new JButton("Export");
		ExportToImageButton.setForeground(Color.WHITE);
		ExportToImageButton.setBackground(new Color(21, 66, 115));
		ExportToImageButton.setBounds(278, 123, 109, 33);
		ExportToImagePanel.add(ExportToImageButton);
		
		JLabel ExportZoomedImageLabel = new JLabel(" Export Zoomed Image");
		ExportZoomedImageLabel.setOpaque(true);
		ExportZoomedImageLabel.setForeground(Color.WHITE);
		ExportZoomedImageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		ExportZoomedImageLabel.setBackground(new Color(21, 66, 115));
		ExportZoomedImageLabel.setBounds(469, 269, 116, 23);
		ExportPanel.add(ExportZoomedImageLabel);
		
		JPanel ExportZoomedImagePanel = new JPanel();
		ExportZoomedImagePanel.setBorder(new LineBorder(CustomColor));
		ExportZoomedImagePanel.setLayout(null);
		ExportZoomedImagePanel.setBackground(SystemColor.menu);
		ExportZoomedImagePanel.setBounds(447, 280, 417, 182);
		ExportPanel.add(ExportZoomedImagePanel);
		
		JButton ExportZoomedImageButton = new JButton("Export");
		ExportZoomedImageButton.setForeground(Color.WHITE);
		ExportZoomedImageButton.setBackground(new Color(21, 66, 115));
		ExportZoomedImageButton.setBounds(278, 123, 109, 33);
		ExportZoomedImagePanel.add(ExportZoomedImageButton);
		
		
		JComboBox<?> ExportZoomedImageComboBox = new JComboBox<Object>(ImageOptions);
		ExportZoomedImageComboBox.setBounds(112, 80, 154, 20);
		ExportZoomedImagePanel.add(ExportZoomedImageComboBox);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(112, 49, 154, 20);
		ExportZoomedImagePanel.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(112, 24, 154, 20);
		ExportZoomedImagePanel.add(textField_1);
		
		JLabel label = new JLabel("Width");
		label.setBounds(10, 27, 46, 14);
		ExportZoomedImagePanel.add(label);
		
		JLabel label_1 = new JLabel("Height");
		label_1.setBounds(10, 52, 46, 14);
		ExportZoomedImagePanel.add(label_1);
		
	
		
		
		
		
		
		InfoPanel.setVisible(false);		
		
		
		HomePanel.setVisible(false);		
		HomePanel.setBackground(Color.WHITE);
		HomePanel.setBounds(10, 119, 874, 516);
		frmHi.getContentPane().add(HomePanel);
		HomePanel.setLayout(null);
		txtpnAtVeroEos_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtpnAtVeroEos_1.setText("HomeAt vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.");
		txtpnAtVeroEos_1.setBounds(10, 11, 854, 261);
		
		HomePanel.add(txtpnAtVeroEos_1);
		InfoPanel.setBackground(Color.WHITE);
		InfoPanel.setBounds(10, 119, 874, 516);		
		frmHi.getContentPane().add(InfoPanel);
		InfoPanel.setLayout(null);
		txtpnAtVeroEos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtpnAtVeroEos.setText("At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.");
		txtpnAtVeroEos.setBounds(10, 11, 854, 494);
		
		InfoPanel.add(txtpnAtVeroEos);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(314, 416, -114, -67);
		frmHi.getContentPane().add(layeredPane);
		
//		textField.setDropTarget(new DropTarget() {
//	        public synchronized void drop(DropTargetDropEvent evt) {
//	            try {
//	                evt.acceptDrop(DnDConstants.ACTION_COPY);
//	                @SuppressWarnings("unchecked")
//					List<File> droppedFiles = (List<File>) evt
//	                        .getTransferable().getTransferData(
//	                                DataFlavor.javaFileListFlavor);
//	                for (File file : droppedFiles) {
//	                    /*
//	                     * NOTE:
//	                     *  When I change this to a println,
//	                     *  it prints the correct path
//	                     */
//	                	textField.setText(file.getAbsolutePath());
//	                }
//	            } catch (Exception ex) {
//	                ex.printStackTrace();
//	            }
//	        }
//		});
		btnPlotFile.doClick();
	}
}
