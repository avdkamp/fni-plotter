package nfi.calc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 
 * 
 * @author Albert van de Kamp
 * @version 1.0
 * @since 20-02-2013 
 */
public class HashChecksumGen {
	
	private static String[] allHashes = new String[3];
	private OnHashCalculationEventListener onHashCalculationEventListener;
	
	public static String GenerateMD5(String pathToFile) throws NoSuchAlgorithmException, FileNotFoundException, IOException{
		MessageDigest md = MessageDigest.getInstance("MD5");
	    
	    return checkSumCreater(md, pathToFile);
	}
	
	public static String GenerateSHA1(String pathToFile) throws NoSuchAlgorithmException, FileNotFoundException, IOException{
		MessageDigest md = MessageDigest.getInstance("SHA1");
		
		return checkSumCreater(md, pathToFile);
	}
	
	public static String GenerateSHA256(String pathToFile) throws NoSuchAlgorithmException, FileNotFoundException, IOException{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		return checkSumCreater(md, pathToFile);
	}
	/**
	 * @param path to the file
	 */
	public void GenerateAllHashes(String pathToFile){
		
		new AllHashGenerator(pathToFile).start();
	}
	
	private static String checkSumCreater(MessageDigest md, String pathToFile) throws NoSuchAlgorithmException, FileNotFoundException, IOException{
		FileInputStream fis = new FileInputStream(pathToFile);
		byte[] dataBytes = new byte[1024];
		
		int nread = 0; 
		
		while ((nread = fis.read(dataBytes)) != -1) {
			md.update(dataBytes, 0, nread);
		};
		fis.close();
		
		byte[] mdbytes = md.digest();
		
		//convert the byte to hex format
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		
		return sb.toString();
	}
	/**
	 * @return hashes in String array- [0] = MD5, [1] = SHA1, [2] = SHA256 
	 */
	public String[] getAllHashes(){
		return allHashes;
	}
	
	private class AllHashGenerator extends Thread{
		
		String pathToFile;
		
		public AllHashGenerator(String pathToFile){
			this.pathToFile = pathToFile;
		}
		
		@Override
		public void run(){
			try {
				allHashes[0] = GenerateMD5(pathToFile);
				allHashes[1] = GenerateSHA1(pathToFile);
				allHashes[2] = GenerateSHA256(pathToFile);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			onHashCalculationEventListener.doneCalculationAllHashes();
		}
	}
	/**
	 * initializes the interface
	 */
	public void setOnHashCalculationEventListener(OnHashCalculationEventListener listener){
		this.onHashCalculationEventListener = listener;
	}
	/**
	 * Inner callback interface
	 */
	public static interface OnHashCalculationEventListener{
		public void doneCalculationAllHashes();
	}
}