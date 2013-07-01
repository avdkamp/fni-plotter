package nfi.calc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
/**
 * Calculates the Shannon entropy values of unsigned bytes of the given blocksize.
 * 
 * @author Albert van de Kamp
 * @version 1.4
 * @since 23-02-2013
 */
public class ShannonEntropy  extends Thread{
	
	/**	made multi-dimensional so the graph only needs a pointer to this array */
	private float[][] shannonResults;
	private int blockSize;
	private String pathToFile;
	private int threadNo;
	private int amountOfThreads;
	/**	1000000 = 1KB, recommended to not increase due to perm-space errors in Java */
	private final static int MAX_READ_LENGTH = 1000000; 
	private OnShannonEntropyEventListener shannonEntropyEventListener;
	/**
	 * Initialize the class
	 * 
	 * @param pathToFile that needs to be processed.
	 * @param blockSize of the desired for the file.
	 */
	public ShannonEntropy(String pathToFile, int blockSize){
		this.pathToFile = pathToFile;
		this.blockSize = blockSize;
	}
	/**
	 * Initialize the class with multi-threading
	 * 
	 * @param pathToFile that needs to be processed.
	 * @param blockSize of the desired for the file.
	 * @param threadNo is the number of the thread.
	 * @param amountOfThreads is the amount of threads you wish to create eventually.
	 */
	public ShannonEntropy(String pathToFile, int blockSize, int threadNo, int amountOfThreads){
		this.pathToFile = pathToFile;
		this.blockSize = blockSize;
		this.threadNo = threadNo;
		this.amountOfThreads = amountOfThreads;
	}
	@Override
	public void run(){
		int resultTracker = 0;
		int counter = 0;
		int progress= 0;
		//if this is not null then it is the last block
		try {
			final int minus = MAX_READ_LENGTH%blockSize;
			final int actualMaxReadLength = MAX_READ_LENGTH - minus;
			// use RandomAccessFile because it supports readFully()
			RandomAccessFile in = new RandomAccessFile(pathToFile, "r");
			long maxReadSize = 0;
			long seek = 0;
			if((threadNo != 0) && (amountOfThreads != 0)){
				if(in.length() > actualMaxReadLength){
					while((in.length()/amountOfThreads) > seek){
						seek += actualMaxReadLength;
					}
					maxReadSize = (seek*threadNo) ;
					resultTracker = ((actualMaxReadLength/512)*threadNo)-(actualMaxReadLength/512);
					in.seek(maxReadSize - seek);
					if((maxReadSize*threadNo) > in.length()){
						if((int) (((in.length() - (maxReadSize - seek)) / blockSize)+1) > 0){
							shannonResults = new float[2][(int) (((in.length() - (maxReadSize - seek)) / blockSize)+1)];
							maxReadSize = in.length();
						} else {
							in.close();
							shannonEntropyEventListener.onWorkerComplete();
							return;
						}
					} else {
						shannonResults = new float[2][(int) ((seek / blockSize))];
					}
				} else if(!((amountOfThreads - (amountOfThreads - threadNo)) == 1)){
					in.close();
					shannonEntropyEventListener.onWorkerComplete();
					return;
				}
			}
			
			if(shannonResults == null){
				in.seek(0L);
				maxReadSize = in.length();
				shannonResults = new float[2][(int) ((seek / blockSize) + 1)];
			}
			while (in.getFilePointer() < maxReadSize){
				//makes sure that the amount that is read is 0 when devided with the blocksize
				// set max read length in bytes, prevents a Perm Space error.
			    int readSize = (int)Math.min(actualMaxReadLength, in.length() - in.getFilePointer());
			    byte[] bytes = new byte[readSize];  //creates new byte array.
			    
			    in.readFully(bytes); //puts file bytes in the array.
			    
	        	int[][] blockedValues = null;
	        	int containsLastBlock = readSize%blockSize;
	        	//the last part needs to be one bigger or will trigger array out of bounds exception
	        	if(containsLastBlock == 0){
	        		blockedValues = new int[bytes.length/blockSize][blockSize];
	        	} else {
	        		blockedValues = new int[bytes.length/blockSize+1][blockSize];
	        	}
				
				int block = 0;
				int blockPos = 0;
				//fill multidimensional array according to the assigned block sizes. 
				for (byte b: bytes) {
					blockedValues[block][blockPos++] = (b &0xFF); //turns byte value into unsigned byte integer value.
					if(blockPos >= blockSize){
						block++;
						blockPos = 0;
					}
				}
				bytes = null;
				//adds the calculated values to the ArrayList
				for (int i = 0; i < blockedValues.length; i++) {
					if(containsLastBlock != 0 && (i == blockedValues.length-1)){
						shannonResults[0][counter] = resultTracker++;
						shannonResults[1][counter++] = entropy(blockedValues[i], containsLastBlock); 
					} else {
						shannonResults[0][counter] = resultTracker++;
						shannonResults[1][counter++] = entropy(blockedValues[i], 0);
					}
				}
				//update the progress in %
				if(!(progress >=  (counter*100)/shannonResults[1].length)){
					progress = (counter*100)/shannonResults[1].length;
					shannonEntropyEventListener.onProgressUpdate(progress);
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		shannonEntropyEventListener.onWorkerComplete();
	}
	/**
	 * Calculates the Shannon value of unsigned bytes.
	 * This is altered because the last memory block is almost never 100% filled
	 * 
	 * @see http://whaticode.com/2010/05/24/a-java-implementation-for-shannon-entropy/
	 * @param Values - Array of unsigned byte values as integers.
	 * @param isLast - gives the length of the last block, if it is not the last then it should be 0.
	 * @return Shannon value of the given values.
	 */	
	private static float entropy(int[] values, int isLast) {
		final Map<Integer, Long> valueOccurances = new HashMap<Integer, Long>();
		//it is the last block if it is another value than 0
		int length = (isLast == 0) ? values.length : isLast; 
		//count the occurences of each value
		for (int i = 0; i < length; i++) {
			Long valueOccurance = valueOccurances.get(values[i]);
			valueOccurances.put(values[i], valueOccurance == null ? 1L : ++valueOccurance);
		}
		
		float combinedEntropy = 0.0f;
		//calculate the entropy
		for (Integer value : valueOccurances.keySet()) {
			float entropy = valueOccurances.get(value) / (float) length;
			combinedEntropy += entropy * (Math.log(entropy) / Math.log(2));
		}
		
		return -combinedEntropy;
	}
	/**
	 * Returns all the calculated values according to the Shannon calculation.
	 * Use onWorkerComplete eventlistener to get the value, it might return null or an incomplete array
	 * 
	 * @return all the results as double
	 */
	public float[][] getResults(){
		return shannonResults;
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