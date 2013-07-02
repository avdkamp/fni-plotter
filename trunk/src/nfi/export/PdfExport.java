package nfi.export;
//Io imports
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import nfi.ResourceLoader;
//Itext imports
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author MatsOdolphij
 * 13-03-2013
 */
public class PdfExport {
	
	//Open het document
	private Document pdfDocument = new Document();
	private static Font titleFont;
	private static Font subTitleFont;
	private static Font fontFam;
	private static Font footerFam;
	private static Font extraInfoFam;
	
	public PdfExport(String path){
		//Set the document font family
		titleFont = ResourceLoader.loadTrueTypeFont("/fonts/Verdana Bold.ttf", 11);
		subTitleFont = ResourceLoader.loadTrueTypeFont("/fonts/Verdana Bold.ttf", 9);
		fontFam = ResourceLoader.loadTrueTypeFont("/fonts/Consolas.ttf", 10);
		extraInfoFam = ResourceLoader.loadTrueTypeFont("/fonts/Verdana.ttf", 10);
		footerFam = ResourceLoader.loadTrueTypeFont("/fonts/Times New Roman.ttf", 10);
		//Set 
		try {
			openStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		//open het document zodat het kan worden opgebouwd
		pdfDocument.open();
	}
	/**
	 * 
	 * @param path
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	private void openStream(String path) throws FileNotFoundException, DocumentException {
		//Open de input stream
		PdfWriter.getInstance(pdfDocument, new FileOutputStream(path));
	}
		
	/**
	 * This is optional - the header contains the following information.
	 */
	public void setHeader(String title) {
		//Set the title of the document
		pdfDocument.addTitle(title);
		//Set the document date
		pdfDocument.addCreationDate();
		//Set the author
		pdfDocument.addAuthor("NFI");
		//Set the document subject(same as the title)
		pdfDocument.addSubject(title);
		
	}
	/**
	 * This is where all the content gets written to the pdf
	 * @param Title
	 * @param sin
	 * @param extraInfo
	 * @param hashes
	 * @throws DocumentException
	 */

	public void setDocumentContent(String Title, String sin, String extraInfo, String[] hashes, String fileSize, String filePath, BufferedImage objBufferedImage, int blocksize) throws DocumentException {

			Paragraph topParagraph = new Paragraph();
			//Add title to the document.
			topParagraph.add(new Paragraph(Title, titleFont));
			topParagraph.add(new Paragraph());
			topParagraph.add(new Paragraph("Bestandsgegevens", subTitleFont));
			
			//Set SIN number
			topParagraph.add(new Paragraph("SIN nummer: " + sin, fontFam));
			
			//Set the fileSize
			topParagraph.add(new Paragraph("Bestandsgrootte: " + fileSize, fontFam));
			topParagraph.add(new Paragraph("Block Size: " + blocksize, fontFam));
			
			//Set the filePath
			topParagraph.add(new Paragraph("Bestandsnaam: " + filePath, fontFam));
			topParagraph.add(new Paragraph());
			//Set the hashes when selected
			
			
		    List list = new List(true, false, 10);
		    if (!hashes[0].isEmpty() & !hashes[1].isEmpty() & !hashes[2].isEmpty()) {
		    	topParagraph.add(new Paragraph("Hashes", subTitleFont));
		    	list.add(new ListItem("MD5:    " + hashes[0], fontFam ));
		    	list.add(new ListItem("SHA1:   " + hashes[1], fontFam ));
		    	list.add(new ListItem("SHA256: " + hashes[2], fontFam ));
		    	topParagraph.add(list);	
		    }
		    topParagraph.add(new Paragraph());
		    pdfDocument.add(topParagraph);
		    
		    Image graphImage = null;
			try {
				graphImage = Image.getInstance(Toolkit.getDefaultToolkit().createImage(objBufferedImage.getSource()), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		    pdfDocument.add(graphImage);
		    Paragraph bottomParagraph = new Paragraph();
		    bottomParagraph.add(new Paragraph());
		    // Set the extra info
		    bottomParagraph.add(new Paragraph("Extra informatie:", subTitleFont));
		    bottomParagraph.add(new Paragraph(extraInfo, extraInfoFam));
					    
		    //And finally add all the lines to the document.
			pdfDocument.add(bottomParagraph);		
	}
	/**
	 * Set the document footer
	 * The footer is optional.
	 * @throws DocumentException 
	 *
	 */
	public void setFooter() throws DocumentException {
		//Set the new paragraph
		Paragraph footer = new Paragraph();
		footer.add(new Paragraph("------------------------------------------------------------------------------------------------------------------------------"));
		//Add text to the paragraph
		footer.add(new Paragraph("In de informatietheorie is (Shannon-)entropie een maat voor de informatiedichtheid van een bericht (of een bestand). Een bepaald soort bestandheeft een vaak typerende entropie. Bij bijvoorbeeld veel tekstdocumenten is de entropie vrij laag, terwijl de entropie van versleutelde of gecomprimeerde bestanden meestal zeer hoog is.", footerFam));
		//And finaly add the line to the document.
		pdfDocument.add(footer);
	}
	/**
	 * End the document
	 */
	public void endDocument(){
		//Close the document
		pdfDocument.close();
		
	}
}