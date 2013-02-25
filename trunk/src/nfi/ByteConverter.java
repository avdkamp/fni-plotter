package nfi;

import java.util.BitSet;

public class ByteConverter {
	
	public static BitSet fromByteToBit(byte b){
		BitSet bits = new BitSet(8);
		for (int i = 0; i < 8; i++)  
	    {  
	        bits.set(i, (b & 1) == 1);  
	        b >>= 1;  
	    }  
	    return bits; 
	}
	
	public static int[] fromUnsignedBytesToIntegers(byte[] bytes){
		int[] byteVal = new int[bytes.length];
		int count = 0;
		
		for(byte b: bytes){
			byteVal[count++] = (b &0xFF);
		}
		
		return byteVal;
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
