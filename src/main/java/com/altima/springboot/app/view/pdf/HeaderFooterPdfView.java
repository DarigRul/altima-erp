package com.altima.springboot.app.view.pdf;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

@Component("/agregar-header-footer")
public class HeaderFooterPdfView extends PdfPageEventHelper {
	
	/** The PdfTemplate that contains the total number of pages. */
    protected PdfTemplate total;
    
    public static String cliente; 
    	
    public static String numPedido;

    /**
     * Jaja nunca logro acceder a esto, no se que pez xdxf
     * 
     */
    public void onOpenDocument(PdfWriter writer, Document document) {
    	//
    }
    
    public void Asignar(String cliente1, String numPedido1) {
    	HeaderFooterPdfView.cliente = cliente1;
    	HeaderFooterPdfView.numPedido = numPedido1;
    }
    
    /**
     * Agrega el header cada vez que se cierra una pagina.
     * 
     */
    @Override
    public void onStartPage(PdfWriter writer,Document document) {
    	//Header
    	PdfPTable tablaHeader = new PdfPTable(2);
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		
		SimpleDateFormat formato = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
			String fecha = formato.format(new Date());
		Image img = null;
		try {
			img = Image.getInstance("src/main/resources/static/dist/img/logo.png");
		} catch (BadElementException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		img.scaleAbsolute(95f, 35f);
		PdfPCell cell = new PdfPCell(img);
		PdfPCell cell2 = new PdfPCell(new Phrase(" "));
		PdfPCell cell3 = new PdfPCell(new Phrase(numPedido));
		PdfPCell cell4 = new PdfPCell(new Phrase(cliente));
		PdfPCell cell5 = new PdfPCell(new Phrase("Generado por: " + auth.getName() + "\n" + fecha.substring(0, 1).toUpperCase() + fecha.substring(1)));
		cell.setBorder(0);
		cell2.setBorder(0);
		cell3.setBorder(0);
		cell4.setBorder(0);
		cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell5.setBorder(0);
		cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
		//Logo y celda vacia
		tablaHeader.addCell(cell);
		tablaHeader.addCell(cell5);
		//Celdas vacias
		tablaHeader.addCell(cell2);
		tablaHeader.addCell(cell2);
		//Cliente y num pedido
		tablaHeader.addCell(cell3);
		tablaHeader.addCell(cell4);
		//mas celdas vacias
		tablaHeader.addCell(cell2);
		tablaHeader.addCell(cell2);
		try {
			document.add(tablaHeader);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    

    /**
     * Agrega el footer cada vez que se cierra una pagina.
     * 
     */
    @Override
    public void onEndPage(PdfWriter writer, Document document) {    	
    	//Footer
    	total = writer.getDirectContent().createTemplate(100, 100);
        total.setBoundingBox(new Rectangle(-20, -20, 100, 100));
    	BaseFont helv = null;
		try {
			helv = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	PdfContentByte cb = writer.getDirectContent();
        cb.saveState();
        String text = "Página " + (writer.getPageNumber() - 1);
        float textBase = document.bottom() - 10;
        float textSize = helv.getWidthPoint(text, 12);
        cb.beginText();
        cb.setFontAndSize(helv, 12);
        float adjust = helv.getWidthPoint("0", 1);
        cb.setTextMatrix(document.right() - textSize - adjust, textBase);
        cb.showText(text);
        cb.endText();
        cb.addTemplate(total, document.right() - adjust, textBase);
        cb.restoreState();
    }

    /**
     * Esto solo se hara una vez, al cerrar el documento.
     * 
     */
    public void onCloseDocument(PdfWriter writer, Document document) {
    	//
    }
}
