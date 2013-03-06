package nfi;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


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
}
