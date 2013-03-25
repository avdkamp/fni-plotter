package nfi.export;
//Io imports
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//Itext imports
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author MatsOdolphij
 * 13-03-2013
 */
public class PdfExport {
	
	//Open het document
	private Document pdfDocument = new Document();
	//Set the document font family
	//TODO : correct font setting!
	private static Font fontFam = new Font(Font.FontFamily.COURIER, 18,Font.BOLD);
	private static Font footerFam = new Font(Font.FontFamily.COURIER, 11, Font.NORMAL);
	
	public PdfExport(String path){
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
	public void setDocumentContent(String Title, String sin, String extraInfo, String[] hashes, String fileSize, String filePath) throws DocumentException {
			Paragraph line = new Paragraph("", fontFam);
			//Add title to the document.
			line.add(new Paragraph("Titel : " + Title));
			line.add(new Paragraph(" "));
			
			//Set SIN number
			line.add(new Paragraph("SIN nummer : " + sin));
			line.add(new Paragraph(" "));
			
			//Set the fileSize
			line.add(new Paragraph("Bestandsgrootte : " + fileSize));
			line.add(new Paragraph(" "));
			
			//Set the filePath
			line.add(new Paragraph("Bestandsnaam en pad : " + filePath));
			line.add(new Paragraph(" "));
			// Set the extra info
			line.add(new Paragraph("Extra informatie :  "+ extraInfo));
			line.add(new Paragraph(" "));

			//Set the hashes when selected
			line.add(new Paragraph("Hashes"));
			
		    List list = new List(true, false, 10);
		    list.add(new ListItem("MD5     : " + hashes[0]));
		    list.add(new ListItem("SHA256  : " + hashes[1]));
		    list.add(new ListItem("SHA1    : " + hashes[2] ));
		    line.add(list);
		    line.add(new Paragraph(" "));
					    
		    //And finally add all the lines to the document.
			pdfDocument.add(line);		
	}
	/**
	 * Set the document footer
	 * The footer is optional.
	 * @throws DocumentException 
	 *
	 */
	public void setFooter() throws DocumentException {
		//Set the new paragraph
		Paragraph footer = new Paragraph("", footerFam);
		//Add text to the paragraph
		footer.add(new Paragraph("In de informatietheorie is (Shannon-)entropie een maat voor de informatiedichtheid van een bericht (of een bestand). Een bepaald soort bestandheeft een vaak typerende entropie. Bij bijvoorbeeld veel tekstdocumenten is de entropie vrij laag, terwijl de entropie van versleutelde of gecomprimeerde bestanden meestal zeer hoog is."));
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
