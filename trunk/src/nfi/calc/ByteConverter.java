package nfi.calc;

import java.util.BitSet;
/**
 * Gives several options to converting a byte.
 * <br>For example;
 * <br> - byte to a BitSet of 8
 * <br> - byte to an unsigned byte as an integer array.
 * <br> - byte to a signed byte as an integer array.
 * 
 * @author Albert van de Kamp
 * @version 1.0
 * @since 20-02-2013
 */
public class ByteConverter {
	/**
	 * Converts a byte to a BitSet of 8 bits resulting in the actual binary data of that byte.
	 * 
	 * @param byte - the byte that has to be converted.
	 * @return BitSet - returns the actual binary data of a byte.
	 */
	public static BitSet fromByteToBit(byte b){
		BitSet bits = new BitSet(8);
		for (int i = 0; i < 8; i++) {  
	        bits.set(i, (b & 1) == 1);  
	        b >>= 1;  
	    }  
	    return bits; 
	}
	
	public static int fromUnsignedByteToInteger(byte b){
		return (b &0xFF);
	}
	
	public static int[] fromUnsignedBytesToIntegers(byte[] bytes){
		int[] byteVal = new int[bytes.length];
		int count = 0;
		
		for(byte b: bytes){
			byteVal[count++] = (b &0xFF);
		}
		
		return byteVal;
	}
	
	public static int fromSignedByteToInteger(byte b){
		return b;
	}
	
	public static int[] fromSignedBytesToIntegers(byte[] bytes){
		int[] byteVal = new int[bytes.length];
		int count = 0;
		
		for(byte b: bytes){
			byteVal[count++] = b;
		}
		
		return byteVal;
	}
}
