package com.altima.springboot.app.view.pdf;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javax.swing.border.Border;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

@Component("/agregar-header-footer-cotizaciones")
public class HeaderFooterCotizacionesPdfView extends PdfPageEventHelper {
	
	protected PdfTemplate total;
	
	/**
     * Jaja nunca logro acceder a esto, no se que pez xdxf
     * 
     */
    public void onOpenDocument(PdfWriter writer, Document document) {
    	//
    }
    
    /**
     * Agrega el header cada vez que se cierra una pagina.
     * 
     */
    @Override
    public void onStartPage(PdfWriter writer,Document document) {
    	//
    }
    
    /**
     * Agrega el footer cada vez que se cierra una pagina.
     * 
     */
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
    	//Variables a utilizar    	
    	Font HelveticaBold = null;
    	Font Helvetica = null;
    	Font Raya = null;
    	try {
    		HelveticaBold = new Font(BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 9);
			Helvetica = new Font(BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED), 9);
			Raya = new Font(BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 20);
			Raya.setColor(Color.RED);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
		//Tabla de Raya Roja xd
    	PdfPTable tablaRayaRoja = new PdfPTable(1);
    	tablaRayaRoja.setWidthPercentage(100);
    	PdfPCell RayaRoja = new PdfPCell(new Phrase("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯", Raya));
    	RayaRoja.setPaddingLeft(-65f);
    	RayaRoja.setBorder(0);
    	RayaRoja.setHorizontalAlignment(Element.ALIGN_LEFT);
    	tablaRayaRoja.addCell(RayaRoja);
    	
		//Tabla de Imagen
		PdfPTable tablaImagen = new PdfPTable(2);
		tablaImagen.setWidthPercentage(250f);
		//Imagen
		Image img = null;
		try {
			img = Image.getInstance("src/main/resources/static/dist/img/logo.png");
		} catch (BadElementException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		img.scaleAbsolute(95f, 35f);
		PdfPCell imagen = new PdfPCell(img);
		imagen.setBorder(PdfPCell.NO_BORDER);
		imagen.setHorizontalAlignment(Element.ALIGN_LEFT);
		imagen.setPaddingLeft(-65f);
		tablaImagen.addCell(imagen);
		//Texto
		PdfPCell leyenda = new PdfPCell(new Paragraph("www.uniformes-altima.com.mx     Aviso de Privacidad    \n Avenida Primero de Mayo #126 Col. 8 de Agosto  \n  CP: 03820 México D.F. 5272 9626 \n    altima@uniformes-altima.com.mx", Helvetica));
    	leyenda.setBorder(PdfPCell.NO_BORDER);	
    	leyenda.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	leyenda.setPaddingRight(-40f);
    	tablaImagen.addCell(leyenda);
    	
		
		//Se agrega todo al documento
		try {
			tablaImagen.setWidths(new float[] { 4f, 10f });
			tablaImagen.setTotalWidth(400f);
			tablaRayaRoja.setTotalWidth(400f);
			tablaImagen.writeSelectedRows(0, -1, 110, 80, writer.getDirectContent());
			tablaRayaRoja.writeSelectedRows(0, -1, 100, 90, writer.getDirectContent());
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    /**
     * Esto solo se hara una vez, al cerrar el documento.
     * 
     */
    public void onCloseDocument(PdfWriter writer, Document document) {
    	//
    }

}
