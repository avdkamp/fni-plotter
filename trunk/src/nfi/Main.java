package nfi;

import java.io.File;
import java.io.FileInputStream;

import org.jfree.ui.RefineryUtilities;

import nfi.gui.chart.FastScatterPlotter;


public class Main {


	/**
	 */
	public static void main(String[] args) throws Exception {
		
		String dataFile = "/home/albert/test";
//		String dataFile = "D:\\Downloads\\jfreechart-1.0.14\\lib\\jfreechart-1.0.14.jar";
		
		byte bytes[] = null; 
		File f = new File(dataFile);
		FileInputStream fis = new FileInputStream(f);
		bytes = new byte[(int)f.length()];
		fis.read(bytes);
		fis.close();
		
		final ShannonEntropy se = new ShannonEntropy(bytes, 1024);
		
//		create chart example
		final FastScatterPlotter graphFrame = new FastScatterPlotter("Fast Scatter Plot", se);
        graphFrame.pack();
        RefineryUtilities.centerFrameOnScreen(graphFrame);
        graphFrame.setVisible(true);
		
//		System.out.println("MD5   (in hex format): " + HashChecksumGen.GenerateMD5(dataFile));
//		System.out.println("SHA1  (in hex format): " + HashChecksumGen.GenerateSHA1(dataFile));
//		System.out.println("SHA256(in hex format): " + HashChecksumGen.GenerateSHA256(dataFile));
	}
}
