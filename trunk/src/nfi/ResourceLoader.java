package nfi;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.itextpdf.text.pdf.BaseFont;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
/**
 * @author Albert van de Kamp
 * @version 1.0
 * @since 01-03-2013
 */
public class ResourceLoader {
	
	public static Image loadImage(String pathToFile){
		Image img = null;
		try {
			img = ImageIO.read(ResourceLoader.class.getResourceAsStream(pathToFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static ImageIcon loadImageIcon(String pathToFile){
		ImageIcon imgI = null;
		imgI = new ImageIcon(ResourceLoader.class.getResource(pathToFile));
		return imgI;
	}
	
	public static Font loadTrueTypeFont(String pathToFile, int size){
		
//		URL fontpath = ResourceLoader.class.getResource(pathToFile);
		Font font = null;
		BaseFont customfont;
		try {
			customfont = BaseFont.createFont(pathToFile, BaseFont.CP1252, BaseFont.EMBEDDED);
			font = new Font(customfont, size);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return font;
	}
}
