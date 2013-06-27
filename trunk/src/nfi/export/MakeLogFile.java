package nfi.export;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class MakeLogFile extends Thread{
	
	private String filePath;
	private int blockSize;
	private float[][] data;
	
	public MakeLogFile(String filePath, int blockSize){
		this.filePath = filePath;
		this.blockSize = blockSize;
	}
	
	public void setData(float[][] data){
		this.data = data;
	}
	
	@Override
	public void run(){
		
		if(new File(filePath + "_entropy.txt").exists()){
			showFileConflictPopup(filePath + "_entropy.txt");
		} else if(new File(filePath).exists()){
			showFileConflictPopup(filePath);
		} else {
			writeFile(filePath + "_entropy.txt");
		}
	}
	
	private void showFileConflictPopup(String filePath){
		int result = JOptionPane.showConfirmDialog(null,
				"The TXT output already exists, overwrite?",
				"Existing file", JOptionPane.YES_NO_OPTION);
		switch (result) {
			case JOptionPane.YES_OPTION:
					writeFile(filePath);
				return;
				
			case JOptionPane.NO_OPTION:
				//TODO: maak 'no' optie, laat file chooser opnieuw zien of zo.
				return;
		}
	}
	
	private void writeFile(String filePath){
		try {
			PrintWriter output = new PrintWriter(new FileWriter(filePath, false));
			output.println("blocksize# " + blockSize);
			for (int i = 0; i < data[1].length; i++) {
				output.println(i + " - " + data[1][i]);
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}