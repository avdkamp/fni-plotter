package nfi.gui.panel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

    private static final long serialVersionUID = -8850675340470968449L;
    
 
    private JButton btnExport = new JButton("Export");
    private JButton btnPlotFile = new JButton("Plot File");

    private OnMenuEventListener onMenuEventListener;
    
    public MenuPanel(){
            FlowLayout flMenuPanel = (FlowLayout) this.getLayout();
            flMenuPanel.setAlignment(FlowLayout.LEFT);
            this.setBackground(new Color(240, 240, 240));
            this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
            this.setBounds(0, 82, 900, 25);
            
            btnExport.setVisible(false);
            btnPlotFile.setBackground(SystemColor.menu);
          
            
       
            this.add(btnPlotFile);
            this.add(btnExport);
            
    }
    private void setButtonEventListeners(){
            btnPlotFile.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                            
                            btnExport.setVisible(false);
                            btnPlotFile.setBackground(Color.WHITE);
                            onMenuEventListener.onPlotFileClick();
                    }
            });
            
           
            
           
            btnPlotFile.doClick();
    }
    public void showExportBtn(){
    	btnExport.setVisible(true);
            btnPlotFile.setBackground(SystemColor.menu);
            
            btnExport.setBackground(Color.WHITE);
    }
    /*
     * initializes the interface
     */
    public void setOnMenuEventListener(OnMenuEventListener listener){
            this.onMenuEventListener = listener;
            
            setButtonEventListeners();
    }
    /*
     * Inner callback interface
     */
    public static interface OnMenuEventListener{
           
            public void onPlotFileClick();
          ;
    }
}