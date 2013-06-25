package nfi.calc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * Calculates the Shannon entropy values of unsigned bytes of the given blocksize.
 * 
 * @author Albert van de Kamp
 * @version 1.2
 * @since 23-02-2013
 */
public class ShannonEntropy {
	
	private ArrayList<Double> allShannonResults;
	private int blockSize;
	private int progress;
	private String pathToFile;
	private byte[] bytes;
	private OnShannonEntropyEventListener shannonEntropyEventListener;
	/**
	 * Initialize the class
	 * 
	 * @param path to the file that needs to be processed.
	 * @param the blocksize of that has to be used for the file.
	 */
	public ShannonEntropy(String pathToFile, int blockSize){
		this.pathToFile = pathToFile;
		this.blockSize = blockSize;
		allShannonResults = new ArrayList<Double>();
	}
	/**
	 * Start the Thread worker.
	 */
	public void run(){
		new CalculateEntropy().start();
	}
	/**
	 * The worker which processes file in a different thread to prevent the GUI from freezing.
	 * 
	 * @author Albert van de Kamp
	 * @since 23-02-2013
	 */
	private class CalculateEntropy extends Thread{
		@SuppressWarnings("resource")
		@Override
		public void run(){
			try {
				// use RandomAccessFile because it supports readFully()
				RandomAccessFile in = new RandomAccessFile(pathToFile, "r");
				in.seek(0L);
				while (in.getFilePointer() < in.length()){
					// set max read length in bytes, prevents a Perm Space error.
				    int readSize = (int)Math.min(1000000, in.length() - in.getFilePointer());
				    bytes = new byte[readSize];  //creates new byte array.
				    in.readFully(bytes); //puts file bytes in the array.
				    
		        	int[] values = ByteConverter.fromUnsignedBytesToIntegers(bytes);
		        	//creates a contained for the integer in blocks, +1 is done because an integer always rounds a digit down.
					int[][] blockedValues = new int[values.length/blockSize+1][blockSize];
					
					int block = 0;
					int blockPos = 0;
					//fill multidimensional array according to the assigned block sizes. 
					for (int value: values) {
						blockedValues[block][blockPos++] = value;
						if(blockPos >= blockSize){
							block++;
							blockPos = 0;
						}
					}
					//adds the calculated values to the ArrayList
					for (int i = 0; i < blockedValues.length; i++) {
						allShannonResults.add(entropy(blockedValues[i]));
					}
					//update the progress in %
					shannonEntropyEventListener.onProgressUpdate((int) ((in.getFilePointer()*100)/in.length()));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			shannonEntropyEventListener.onWorkerComplete();
		}
	}
	/**
	 * Calculates the Shannon value of unsigned bytes.
	 * 
	 * @see http://whaticode.com/2010/05/24/a-java-implementation-for-shannon-entropy/
	 * @param values - Array of unsigned byte values as integers.
	 * @return Shannon value of the given values.
	 */
	private static double entropy(int[] values) {	 
		final Map<Integer, Long> valueOccurances = new HashMap<Integer, Long>();
		//count the occurences of each value
		for (Integer value : values) {
			Long valueOccurance = valueOccurances.get(value);
			valueOccurances.put(value, valueOccurance == null ? 1L : ++valueOccurance);
		}	
		
		double combinedEntropy = 0.0d;
		//calculate the entropy
		for (Integer value : valueOccurances.keySet()) {
			double entropy = valueOccurances.get(value) / (double) values.length;
			combinedEntropy += entropy * (Math.log(entropy) / Math.log(2));
		}
		return -combinedEntropy;
	}
	/**
	 * Allows a progressbar to be set by the given %.
	 * Use onProgressUpdate eventlistener to get the values.
	 * 
	 * @return the progress in % of the worker.
	 */
	public int getProgress(){
		return progress;
	}
	/**
	 * Returns all the calculated values according to the Shannon calculation.
	 * Use onWorkerComplete eventlistener to get the value, it might return null or an incomplete ArrayList
	 * 
	 * @return all the results as double in an ArrayList
	 */
	public ArrayList<Double> getResults(){
		if(allShannonResults.size() >= 1){
			return allShannonResults;
		} else {
			return null;
		}
	}
	/**
	 * Initializes the eventlistener.
	 * 
	 * @param new OnShannonEntropyEventListener
	 */
	public void setOnShannonEntropyEventListener(OnShannonEntropyEventListener listener){
		this.shannonEntropyEventListener = listener;
	}
	/**
	 * Inner callback interface
	 */
	public static interface OnShannonEntropyEventListener{
		/**
		 * Called when there is a progress update of the worker.
		 */
		public void onProgressUpdate(int progress);
		/**
		 * Called when the worker has completed its task.
		 */
		public void onWorkerComplete();
	}
}