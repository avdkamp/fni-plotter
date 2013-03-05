package nfi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashChecksumGen {
	
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
	 * @param pathToFile
	 * 
	 * @return hashes, [0] = MD5, [1] = SHA1, [2] = SHA256 
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String[] GenerateAllHashes(String pathToFile) throws NoSuchAlgorithmException, FileNotFoundException, IOException{
		String[] hashes = new String[3];
		
		hashes[0] = GenerateMD5(pathToFile);
		hashes[1] = GenerateSHA1(pathToFile);
		hashes[2] = GenerateSHA256(pathToFile);
		
		return hashes;
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
}