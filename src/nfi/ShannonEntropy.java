package nfi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ShannonEntropy {
	
	private double[] shannonResults;
	private int blockSize;
	private int progress;
	private String pathToFile;
	private OnShannonEntropyEventListener shannonEntropyEventListener;
	/**
	 * 
	 * @param bytes the bytes of the file that has to be processed.
	 * @param blockSize that has to be used for the calculation
	 */
	//TODO: klassen extreem goed documenteren
	public ShannonEntropy(String pathToFile, int blockSize){
		this.pathToFile = pathToFile;
		this.blockSize = blockSize;
		
	}
	
	public void run(){
		new worker().start();
	}
	//TODO: progress beter verdelen
	public class worker extends Thread{
		@Override
		public void run(){
			
			byte bytes[] = null; 
			File f = new File(pathToFile);
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(f);
				bytes = new byte[(int)f.length()];
				fis.read(bytes);
				fis.close();
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			} catch (IOException oie) {
				oie.printStackTrace();
			}
			
			int[] values = ByteConverter.fromUnsignedBytesToIntegers(bytes);
			int[][] blockedValues = new int[values.length/blockSize+1][blockSize];
			
			int block = 0;
			int blockPos = 0;
			
			for (int value: values) {
				blockedValues[block][blockPos++] = value;
				if(blockPos >= blockSize){
					block++;
					blockPos = 0;
				}
			}
			
			shannonResults = new double[blockedValues.length];
			
			
			final int bLength = blockedValues.length;
			
			for (int i = 0; i < blockedValues.length; i++) {
				progress = ((i*100)/bLength);
				shannonEntropyEventListener.onProgressUpdate();
				shannonResults[i] = entropy(blockedValues[i]);
			}
			
			shannonEntropyEventListener.onWorkerComplete();
		}
	}
	
	public static double entropy(int[] values) {	 
		final Map<Integer, Long> valueOccurances = new HashMap<Integer, Long>();
		
		for (Integer value : values) {
			Long valueOccurance = valueOccurances.get(value);
			valueOccurances.put(value, valueOccurance == null ? 1L : ++valueOccurance);
		}	
		
		double combinedEntropy = 0.0d;
		
		for (Integer value : valueOccurances.keySet()) {
			double entropy = valueOccurances.get(value) / (double) values.length;
			combinedEntropy += entropy * (Math.log(entropy) / Math.log(2));
		} 
		
		return -combinedEntropy;
	}
	
	public int getProgressChunk(){
		return progress;
	}
	/*
	 *  
	 */
	public double[] getResults(){
		return shannonResults;
	}
	/*
	 * initializes the interface
	 */
	public void setOnShannonEntropyEventListener(OnShannonEntropyEventListener listener){
		this.shannonEntropyEventListener = listener;
	}
	/*
	 * Inner callback interface
	 */
	public static interface OnShannonEntropyEventListener{
		public void onProgressUpdate();
		public void onWorkerComplete();
	}
}