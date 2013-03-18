package nfi;
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
	
	//Static final string voor het pad + bestands naam.
	public static final String RESULT = "C://test2.pdf";
	//Open het document
	private Document pdfDocument = new Document();
	
	public PdfExport(String path){
		//Set 
		try {
			openStream(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
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
	public void setHeader() {
		try {
			//Set the author of the document
			pdfDocument.addTitle("");
			//Set the document date
			pdfDocument.addCreationDate();
			//Set the document subject
			pdfDocument.addSubject("Bestanden plotter");
			// Set the title
			pdfDocument.add(new Paragraph("test"));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Set the document footer
	 * The footer is optional.
	 *
	 */
	public void setFooter() {
	
	}
	/**
	 * End the document
	 */
	public void endDocument(){
		try {
			//Close the document
			pdfDocument.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
