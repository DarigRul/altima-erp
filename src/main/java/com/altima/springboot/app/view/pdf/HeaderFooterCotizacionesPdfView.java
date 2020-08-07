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
import com.lowagie.text.pdf.PdfWriter;

@Component("/agregar-header-footer-cotizaciones")
public class HeaderFooterCotizacionesPdfView extends PdfPageEventHelper {
	
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
    	//Variables a utilizar
    	Font HelveticaBold = null;
    	Font Helvetica = null;
    	try {
    		HelveticaBold = new Font(BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 10);
			Helvetica = new Font(BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED), 10);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SimpleDateFormat formato = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
		String fecha = formato.format(new Date());
		
		//Espacio en blanco xd
		PdfPTable espacio = new PdfPTable(1);
		PdfPCell cell = new PdfPCell(new Phrase(" "));
		cell.setBorder(0);
		espacio.addCell(cell);
		
		
    	//Primera tabla de numero, nombre y fecha
    	PdfPTable tablaHeader1 = new PdfPTable(1);
    	PdfPCell numeroCotizacion = new PdfPCell(new Phrase("No. 25895", HelveticaBold));
    	PdfPCell lugarCotizacion = new PdfPCell(new Phrase("Notaria 33", HelveticaBold));
    	PdfPCell fechaCotizacion = new PdfPCell(new Phrase(fecha.substring(0, 1).toUpperCase() + fecha.substring(1), Helvetica));
    	numeroCotizacion.setBorder(0);
    	lugarCotizacion.setBorder(0);
    	fechaCotizacion.setBorder(0);
    	numeroCotizacion.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	lugarCotizacion.setHorizontalAlignment(Element.ALIGN_LEFT);
    	fechaCotizacion.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	tablaHeader1.addCell(numeroCotizacion);
    	tablaHeader1.addCell(lugarCotizacion);
    	tablaHeader1.addCell(fechaCotizacion);
    	
    	//Segunda tabla con direccion
    	PdfPTable tablaHeader2 = new PdfPTable(5);
    	PdfPCell celda;
    	celda = new PdfPCell(new Phrase("Calle: ", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Cantarranas No. 33", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase(" "));
    	celda.setBorder(0);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Interior:", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase(" "));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	tablaHeader2.addCell(celda);
    	//
    	celda = new PdfPCell(new Phrase("Colonia: ", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Zona Centro", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase(" "));
    	celda.setBorder(0);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("CP:", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("36000"));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	tablaHeader2.addCell(celda);
    	//
    	celda = new PdfPCell(new Phrase("Delegación: ", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Guanajuato", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase(" "));
    	celda.setBorder(0);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Estado", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Gto."));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	tablaHeader2.addCell(celda);
    	//
    	celda = new PdfPCell(new Phrase("Teléfono: ", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("461 227 7646", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase(" "));
    	celda.setBorder(0);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Fax:", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase(" "));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	tablaHeader2.addCell(celda);
    	//
    	celda = new PdfPCell(new Phrase("Correo: ", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("patty_jama@hotmail.com", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase(" "));
    	celda.setBorder(0);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase(" ", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase(" "));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	tablaHeader2.addCell(celda);
    	
    	try {
			tablaHeader2.setWidths(new float[] { 3f, 5f, 1f, 3f, 3f });
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//Tercera tabla, para el nombre y por medio de la presente
    	PdfPTable tablaHeader3 = new PdfPTable(1);
    	PdfPCell nombre = new PdfPCell(new Phrase("Atte. Patricia Jaime", HelveticaBold));
    	PdfPCell leyenda = new PdfPCell(new Phrase("Por medio de la presente nos permitimos poner a su consideración la cotización de las siguientes prendas: ", Helvetica));
    	nombre.setBorder(0);
    	leyenda.setBorder(0);
    	nombre.setHorizontalAlignment(Element.ALIGN_CENTER);
    	leyenda.setHorizontalAlignment(Element.ALIGN_CENTER);
    	tablaHeader3.addCell(nombre);
    	tablaHeader3.addCell(leyenda);
    	
		try {
			
			document.add(tablaHeader1);
			document.add(espacio);
			document.add(tablaHeader2);
			document.add(espacio);
			document.add(tablaHeader3);
			document.add(espacio);
			
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
    	PdfPCell RayaRoja = new PdfPCell(new Phrase("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯", Raya));
    	RayaRoja.setBorder(PdfPCell.NO_BORDER);
    	tablaRayaRoja.addCell(RayaRoja);
    	
		//Tabla de Imagen
		PdfPTable tablaImagen = new PdfPTable(2);
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
		tablaImagen.addCell(imagen);
		//Texto
		PdfPCell leyenda = new PdfPCell(new Paragraph("www.uniformes-altima.com.mx     Aviso de Privacidad    \n Avenida Primero de Mayo #126 Col. 8 de Agosto  \n  CP: 03820 México D.F. 5272 9626 \n    altima@uniformes-altima.com.mx", Helvetica));
    	leyenda.setBorder(PdfPCell.NO_BORDER);	
    	leyenda.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	tablaImagen.addCell(leyenda);
    	
		
		//Se agrega todo al documento
		try {
			tablaImagen.setWidths(new float[] { 4f, 10f });
			document.add(tablaRayaRoja);
			document.add(tablaImagen);
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
