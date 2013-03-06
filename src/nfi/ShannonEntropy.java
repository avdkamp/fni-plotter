package nfi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShannonEntropy {
	
	private ArrayList<Double> allShannonResults;
	private int blockSize;
	private int progress;
	private String pathToFile;
	private byte[] bytes;
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
		allShannonResults = new ArrayList<Double>();
	}
	
	public void run(){
		new worker().start();
	}
	//TODO: progress beter verdelen
	public class worker extends Thread{
		@SuppressWarnings("resource")
		@Override
		public void run(){
			try {
				// use RandomAccessFile because it supports readFully()
				RandomAccessFile in = new RandomAccessFile(pathToFile, "r");
				in.seek(0L);
				while (in.getFilePointer() < in.length())
				{
				    int readSize = (int)Math.min(1000000, in.length() - in.getFilePointer());
				    bytes = new byte[readSize];
				    in.readFully(bytes);
				    
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
					
//					final int bLength = blockedValues.length;
					
					for (int i = 0; i < blockedValues.length; i++) {
//						progress = ((i*100)/bLength);
//						shannonEntropyEventListener.onProgressUpdate();
						allShannonResults.add(entropy(blockedValues[i]));
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	public ArrayList<Double> getResults(){
		return allShannonResults;
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