package nfi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingWorker;

public class ShannonEntropy {
	
	private double[] shannonResults;
	private byte[] bytes;
	private int blockSize;
	private int progressChunk;
	private OnShannonEntropyEventListener shannonEntropyEventListener;
	/**
	 * 
	 * @param bytes the bytes of the file that has to be processed.
	 * @param blockSize that has to be used for the calculation
	 */
	//TODO: klasse threaded maken en indien mogelijk zelfs multi-threaded maken.
	//dit kan alleen getest worden met grote bestanden.
	//TODO: callback functie maken dat berekening klaar is.
	//TODO: klassen extreem goed documenteren
	//TODO: waar mogelijk de code optimalizeren om extreem snel data te kunnen verwerken
	public ShannonEntropy(byte[] bytes, int blockSize){
		this.bytes = bytes;
		this.blockSize = blockSize;
		
	}
	
	public void run(){
		new worker().start();
	}
	
	public class worker extends Thread{
		
		public void run(){
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
			
			for (int i = 0; i < blockedValues.length; i++) {
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
		return progressChunk;
	}
	
	public double[] getResults(){
		return shannonResults;
	}
	
	public void setOnShannonEntropyEventListener(OnShannonEntropyEventListener listener){
		this.shannonEntropyEventListener = listener;
	}
	
	public static interface OnShannonEntropyEventListener{
		public void onProgressUpdate();
		public void onWorkerComplete();
	}
}