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
		//Set the author of the document
		pdfDocument.addTitle(title);
		//Set the document date
		pdfDocument.addCreationDate();
	}
	
	public void setDocumentContent(String Title, String sin, String extraInfo, String[] hashes) {
		try {
			//Set SIN number
			pdfDocument.add(new Paragraph("SIN nummer : " + sin));
			//Set the document subject
			pdfDocument.addSubject("Bestanden plotter");
			// Set the title
			pdfDocument.add(new Paragraph("Extra informatie :  "+ extraInfo));
			pdfDocument.add(new Paragraph("Hashes"));
			
		} catch (DocumentException e) {
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
