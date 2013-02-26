package nfi;

import java.util.HashMap;
import java.util.Map;

public class ShannonEntropyCalc {
	
	private double[] shannonResults;
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
	public ShannonEntropyCalc(byte[] bytes, int blockSize){
		
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
	}
	
	public double[] getResults(){
		return shannonResults;
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
}