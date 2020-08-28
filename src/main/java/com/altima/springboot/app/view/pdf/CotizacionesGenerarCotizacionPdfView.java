package com.altima.springboot.app.view.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.altima.springboot.app.models.service.IEnviarCorreoService;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSmartCopy;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.mail.smtp.SMTPTransport;

@Component("/imprimir-cotizacion")
public class CotizacionesGenerarCotizacionPdfView extends AbstractPdfView{
	
	private static final String FILE1 = "src/main/resources/static/dist/pdf/cotizaciones/CV_Estatico.pdf";
	public static final String DEST = "src/main/resources/static/dist/pdf/cotizaciones/Resultado.pdf";

	@Autowired
    private IEnviarCorreoService enviarCorreoService;
	
	@SuppressWarnings("static-access")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		/*
		 * 
		 * Variables del controller y de tiempo
		 * 
		 */
		@SuppressWarnings("unchecked")
		List<Object[]> prendas = (List<Object[]>) model.get("ListaCotizacionPrendas");
		DateTime jodaTime = new DateTime();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd HH-mm-ss-SSS");
		String nombrePDF = "Cotizacion_" + (String) model.get("id") + "_" + formatter.print(jodaTime) + ".pdf";
		String archivo = "src/main/resources/static/dist/pdf/cotizaciones/" + nombrePDF;
		String tipoCotizacion = (String) model.get("tipo");
		String correo = (String) model.get("mail");
		boolean totales = (boolean) model.get("totales");
		boolean cv = (boolean) model.get("cv");
		SimpleDateFormat formato = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
		String fecha = formato.format(new Date());

		/*
		 * 
		 * Colores y Fuente
		 * 
		 */
		Color fuerte = new Color(2, 136, 209);
		Color bajito = new Color(255, 137, 137);
		Color borderTable = new Color(205,205,205);
		Color colorDatos = new Color(170,170,170);
		Color TitulosBlancos = new Color(255,255,255);
		Color colorBorderBottom = new Color(255,185,24);
		Color BackgroundTitle = new Color(90,90,90);
		Font HelveticaBold = new Font(BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 9);
		Font subtitulos = new Font(BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 9, 0, TitulosBlancos);
		Font Titulos = new Font(BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED), 12, 0, TitulosBlancos);
		Font datosGris = new Font(BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 9, 0, colorDatos);
		Font Helvetica = new Font(BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED), 9);

		/*
		 * 
		 * Celda para espacios en blanco
		 * 
		 */
		PdfPTable espacio = new PdfPTable(1);
		PdfPCell cell = new PdfPCell(new Phrase(" "));
		cell.setBorder(0);
		espacio.addCell(cell);
		
		/**
		 * 
		 * 
		 * Cabezero del Documento
		 * 
		 * 
		 */
    	PdfPTable tablaHeader1 = new PdfPTable(1);
    	tablaHeader1.setWidthPercentage(100);
    	PdfPCell numeroCotizacion = new PdfPCell(new Phrase("No. 25895", HelveticaBold));
    	PdfPCell lugarCotizacion = new PdfPCell(new Phrase("Notaria 33", HelveticaBold));
    	PdfPCell fechaCotizacion = new PdfPCell(new Phrase(fecha.substring(0, 1).toUpperCase() + fecha.substring(1), Helvetica));
    	numeroCotizacion.setBorder(0);
    	lugarCotizacion.setBorder(0);
    	fechaCotizacion.setBorder(0);
    	numeroCotizacion.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	lugarCotizacion.setHorizontalAlignment(Element.ALIGN_LEFT);
    	fechaCotizacion.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	fechaCotizacion.setPaddingBottom(15f);
    	tablaHeader1.addCell(numeroCotizacion);
    	tablaHeader1.addCell(lugarCotizacion);
    	tablaHeader1.addCell(fechaCotizacion);
    	
    	
    	PdfPCell celd = new PdfPCell(new Phrase("DATOS GENERALES DE LA EMPRESA", Titulos));
    	celd.setHorizontalAlignment(Element.ALIGN_CENTER);
    	celd.setVerticalAlignment(Element.ALIGN_CENTER);
    	celd.setBackgroundColor(BackgroundTitle);
    	celd.setPadding(5f);
    	celd.setPaddingBottom(8f);
    	celd.setBorder(0);
    	celd.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
    	celd.setBorderWidthBottom(3f);
    	celd.setBorderWidthRight(3f);
    	celd.setBorderColor(colorBorderBottom);
    	tablaHeader1.addCell(celd);
    	/*
    	 * 
    	 * Parte de la Direccion
    	 * 
    	 */
    	
    	PdfPTable tablaHeader2 = new PdfPTable(5);
    	tablaHeader2.setWidthPercentage(96);
    	PdfPCell celda;
    	
    	celda = new PdfPCell(new Phrase("Calle: ", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	celda.setBorder(Rectangle.BOTTOM);
    	celda.setBorderColorBottom(borderTable);
    	celda.setBorderWidthBottom(2);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Cantarranas No. 33", datosGris));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	celda.setBorder(Rectangle.BOTTOM);
    	celda.setBorderColorBottom(borderTable);
    	celda.setBorderWidthBottom(2);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase(" "));
    	celda.setBorder(0);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Interior:", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	celda.setBorder(Rectangle.BOTTOM);
    	celda.setBorderColorBottom(borderTable);
    	celda.setBorderWidthBottom(2);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase(" "));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	celda.setBorder(Rectangle.BOTTOM);
    	celda.setBorderColorBottom(borderTable);
    	celda.setBorderWidthBottom(2);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Colonia: ", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	celda.setBorder(Rectangle.BOTTOM);
    	celda.setBorderColorBottom(borderTable);
    	celda.setBorderWidthBottom(2);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Zona Centro", datosGris));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	celda.setBorder(Rectangle.BOTTOM);
    	celda.setBorderColorBottom(borderTable);
    	celda.setBorderWidthBottom(2);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase(" "));
    	celda.setBorder(0);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("CP:", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	celda.setBorder(Rectangle.BOTTOM);
    	celda.setBorderColorBottom(borderTable);
    	celda.setBorderWidthBottom(2);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("36000", datosGris));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	celda.setBorder(Rectangle.BOTTOM);
    	celda.setBorderColorBottom(borderTable);
    	celda.setBorderWidthBottom(2);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Delegación: ", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	celda.setBorder(Rectangle.BOTTOM);
    	celda.setBorderColorBottom(borderTable);
    	celda.setBorderWidthBottom(2);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Guanajuato", datosGris));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	celda.setBorder(Rectangle.BOTTOM);
    	celda.setBorderColorBottom(borderTable);
    	celda.setBorderWidthBottom(2);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase(" "));
    	celda.setBorder(0);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Estado", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	celda.setBorder(Rectangle.BOTTOM);
    	celda.setBorderColorBottom(borderTable);
    	celda.setBorderWidthBottom(2);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Gto.", datosGris));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	celda.setBorder(Rectangle.BOTTOM);
    	celda.setBorderColorBottom(borderTable);
    	celda.setBorderWidthBottom(2);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Correo: ", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	celda.setBorder(Rectangle.BOTTOM);
    	celda.setBorderColorBottom(borderTable);
    	celda.setBorderWidthBottom(2);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("patty_jama@hotmail.com", datosGris));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	celda.setBorder(Rectangle.BOTTOM);
    	celda.setBorderColorBottom(borderTable);
    	celda.setBorderWidthBottom(2);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase(" "));
    	celda.setBorder(0);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("Teléfono: ", Helvetica));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	celda.setBorder(Rectangle.BOTTOM);
    	celda.setBorderColorBottom(borderTable);
    	celda.setBorderWidthBottom(2);
    	tablaHeader2.addCell(celda);
    	celda = new PdfPCell(new Phrase("461 227 7646", datosGris));
    	celda.setBorder(0);
    	celda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	celda.setBorder(Rectangle.BOTTOM);
    	celda.setBorderColorBottom(borderTable);
    	celda.setBorderWidthBottom(2);
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
    	

    	/*
    	 * 
    	 * Tabla para el nombre y por medio de la presente
    	 * 
    	 */
    	PdfPTable tablaHeader3 = new PdfPTable(1);
    	tablaHeader3.setWidthPercentage(100);
    	PdfPCell tituloTabla = new PdfPCell(new Phrase("DATOS DE LA COTIZACIÓN", Titulos));
    	tituloTabla.setHorizontalAlignment(Element.ALIGN_CENTER);
    	tituloTabla.setVerticalAlignment(Element.ALIGN_CENTER);
    	tituloTabla.setBackgroundColor(BackgroundTitle);
    	tituloTabla.setPadding(5f);
    	tituloTabla.setPaddingBottom(8f);
    	tituloTabla.setBorder(0);
    	tituloTabla.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
    	tituloTabla.setBorderWidthBottom(3f);
    	tituloTabla.setBorderWidthRight(3f);
    	tituloTabla.setBorderColor(colorBorderBottom);
    	tablaHeader3.addCell(tituloTabla);
    	PdfPCell nombre = new PdfPCell(new Phrase("Atte. Patricia Jaime", HelveticaBold));
    	PdfPCell leyenda = new PdfPCell(new Phrase("Por medio de la presente nos permitimos poner a su consideración la cotización de las siguientes prendas: ", Helvetica));
    	nombre.setBorder(0);
    	leyenda.setBorder(0);
    	nombre.setHorizontalAlignment(Element.ALIGN_LEFT);
    	leyenda.setHorizontalAlignment(Element.ALIGN_LEFT);
    	nombre.setPaddingLeft(10f);
    	leyenda.setPaddingLeft(10f);
    	tablaHeader3.addCell(nombre);
    	tablaHeader3.addCell(leyenda);
		
		/**
		 * 
		 * 
		 * Cuerpo del documento 
		 * 
		 * 
		 */
		PdfPTable tablaPrendas = null;
		//Si pasa aqui, es 1, es decir una cotizacion General
		if(tipoCotizacion.equalsIgnoreCase("1")) {
			tablaPrendas = new PdfPTable(2);
			tablaPrendas.setWidthPercentage(100);
			tablaPrendas.setWidths(new float[] { 10f, 5f });
			PdfPCell nombrePrendaCabezero = new PdfPCell(new Phrase("Descripción", HelveticaBold));
			PdfPCell precioPrendaCabezero = new PdfPCell(new Phrase("Precio", HelveticaBold));
			nombrePrendaCabezero.setBackgroundColor(fuerte);
			nombrePrendaCabezero.setBorder(0);
			nombrePrendaCabezero.setPaddingBottom(8f);
			nombrePrendaCabezero.setPaddingTop(6f);
			nombrePrendaCabezero.setHorizontalAlignment(Element.ALIGN_CENTER);
			precioPrendaCabezero.setBackgroundColor(fuerte);
			precioPrendaCabezero.setBorder(0);
			precioPrendaCabezero.setPaddingBottom(8f);
			precioPrendaCabezero.setPaddingTop(6f);
			precioPrendaCabezero.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			
			
			tablaPrendas.addCell(nombrePrendaCabezero);
			tablaPrendas.addCell(precioPrendaCabezero);
			float Total = 0;
			for(int con = 0; con < prendas.size(); con++) {
				Object[] aux = (Object[]) prendas.get(con);
				Total += Float.valueOf(aux[19].toString());
				PdfPCell nombrePrenda = new PdfPCell(new Phrase(aux[4].toString(), Helvetica));
				PdfPCell precioPrenda = new PdfPCell(new Phrase(aux[19].toString(), HelveticaBold));
//				nombrePrenda.setPadding(3f);
//				precioPrenda.setPadding(3f);
				nombrePrenda.setBorderColorBottom(borderTable);
				nombrePrenda.setBorderWidthBottom(2);
				nombrePrenda.setBorder(Rectangle.BOTTOM);
				nombrePrenda.setPaddingBottom(18f);
				precioPrenda.setBorderColorBottom(borderTable);
				precioPrenda.setBorderWidthBottom(2);
				precioPrenda.setBorder(Rectangle.BOTTOM);
				precioPrenda.setPaddingBottom(18f);
				tablaPrendas.addCell(nombrePrenda);
				tablaPrendas.addCell(precioPrenda);
			}
			
			//Si tiene lo de los totales, se le anexa
			if(totales) {
				PdfPCell totalTitulo = new PdfPCell(new Phrase("Total", HelveticaBold));
				PdfPCell totalNumero = new PdfPCell(new Phrase("" + Total, HelveticaBold));
//				totalTitulo.setPadding(3f);
//				totalNumero.setPadding(3f);
				totalTitulo.setPaddingBottom(18f);
				totalTitulo.setBorder(0); 
				totalTitulo.setBorder(Rectangle.BOTTOM);
				totalTitulo.setBorderColorBottom(borderTable);
				totalTitulo.setBorderWidthBottom(2);
				totalNumero.setBorder(Rectangle.BOTTOM);
				totalNumero.setBorderColorBottom(borderTable);
				totalNumero.setBorderWidthBottom(2);
				tablaPrendas.addCell(totalTitulo);
				tablaPrendas.addCell(totalNumero);
			}
		}
		//Si pasa aqui es porque es una desglosada xd
		else {
			//Se declara la tabla
			tablaPrendas = new PdfPTable(5);
			tablaPrendas.setWidthPercentage(96);
			tablaPrendas.setWidths(new float[] { 2.8f, 6f, 1.5f, 2.2f, 1.5f });
			PdfPCell Cabezero1 = new PdfPCell(new Phrase("No. Coordinado", subtitulos));
			PdfPCell Cabezero2 = new PdfPCell(new Phrase("Descripción", subtitulos));
			PdfPCell Cabezero3 = new PdfPCell(new Phrase("Cantidad", subtitulos));
			PdfPCell Cabezero4 = new PdfPCell(new Phrase("Pre. Unitario", subtitulos));
			PdfPCell Cabezero5 = new PdfPCell(new Phrase("Total", subtitulos));
			Cabezero1.setBackgroundColor(fuerte);
			Cabezero1.setBorder(0);
			Cabezero1.setPaddingBottom(8f);
			Cabezero1.setPaddingTop(6f);
			Cabezero1.setVerticalAlignment(Element.ALIGN_CENTER);
			Cabezero2.setBackgroundColor(fuerte);
			Cabezero2.setBorder(0);
			Cabezero2.setPaddingBottom(8f);
			Cabezero2.setPaddingTop(6f);
			Cabezero2.setVerticalAlignment(Element.ALIGN_CENTER);
			Cabezero3.setBackgroundColor(fuerte);
			Cabezero3.setBorder(0);
			Cabezero3.setPaddingBottom(8f);
			Cabezero3.setPaddingTop(6f);
			Cabezero3.setVerticalAlignment(Element.ALIGN_CENTER);
			Cabezero4.setBackgroundColor(fuerte);
			Cabezero4.setBorder(0);
			Cabezero4.setPaddingBottom(8f);
			Cabezero4.setPaddingTop(6f);
			Cabezero4.setVerticalAlignment(Element.ALIGN_CENTER);
			Cabezero5.setBackgroundColor(fuerte);
			Cabezero5.setBorder(0);
			Cabezero5.setPaddingBottom(8f);
			Cabezero5.setPaddingTop(6f);
			Cabezero5.setVerticalAlignment(Element.ALIGN_CENTER);
			tablaPrendas.addCell(Cabezero1);
			tablaPrendas.addCell(Cabezero2);
			tablaPrendas.addCell(Cabezero3);
			tablaPrendas.addCell(Cabezero4);
			tablaPrendas.addCell(Cabezero5);
			
			float Subtotal = 0;
			float IVA = 0;
			float Total = 0;
			for(int con = 0; con < prendas.size(); con++) {
				Object[] aux = (Object[]) prendas.get(con);
				Subtotal += Float.valueOf(aux[19].toString());
				PdfPCell cuerpo1 = new PdfPCell(new Phrase(aux[9].toString(), Helvetica));
				PdfPCell cuerpo2 = new PdfPCell(new Phrase(aux[4].toString() + "-" + aux[7].toString(), Helvetica));
				PdfPCell cuerpo3 = new PdfPCell(new Phrase(aux[10].toString(), Helvetica));
				PdfPCell cuerpo4 = new PdfPCell(new Phrase("$" + aux[18].toString(), Helvetica));
				PdfPCell cuerpo5 = new PdfPCell(new Phrase("$" + aux[19].toString(), HelveticaBold));
				cuerpo1.setBorderColorBottom(borderTable);
				cuerpo1.setBorderWidthBottom(2);
				cuerpo1.setBorder(Rectangle.BOTTOM);
				cuerpo1.setPaddingBottom(18f);
				cuerpo2.setBorderColorBottom(borderTable);
				cuerpo2.setBorderWidthBottom(2);
				cuerpo2.setBorder(Rectangle.BOTTOM);
				cuerpo3.setBorderColorBottom(borderTable);
				cuerpo3.setBorderWidthBottom(2);
				cuerpo3.setBorder(Rectangle.BOTTOM);
				cuerpo4.setBorderColorBottom(borderTable);
				cuerpo4.setBorderWidthBottom(2);
				cuerpo4.setBorder(Rectangle.BOTTOM);
				cuerpo5.setBorderColorBottom(borderTable);
				cuerpo5.setBorderWidthBottom(2);
				cuerpo5.setBorder(Rectangle.BOTTOM);
				tablaPrendas.addCell(cuerpo1);
				tablaPrendas.addCell(cuerpo2);
				tablaPrendas.addCell(cuerpo3);
				tablaPrendas.addCell(cuerpo4);
				tablaPrendas.addCell(cuerpo5);
			}
			IVA = (int)( Subtotal * ( 16.0f/100.0f ));
			Total = (IVA + Subtotal);
					
			//Si tiene los totales se le anexan
			if(totales) {
				PdfPCell vacia = new PdfPCell(new Phrase(" "));
				PdfPCell subTotalLetra = new PdfPCell(new Phrase("Subtotal:", subtitulos));
				PdfPCell subTotalNumero = new PdfPCell(new Phrase("$" + Subtotal, HelveticaBold));
				PdfPCell ivaLetra = new PdfPCell(new Phrase("I.V.A: 16%", subtitulos));
				PdfPCell ivaNumero = new PdfPCell(new Phrase("$" + IVA, HelveticaBold));
				PdfPCell TotalLetra = new PdfPCell(new Phrase("Total:", subtitulos));
				PdfPCell TotalNumero = new PdfPCell(new Phrase("$" + Total, HelveticaBold));
				subTotalLetra.setBackgroundColor(fuerte);
				ivaLetra.setBackgroundColor(fuerte);
				TotalLetra.setBackgroundColor(fuerte);
				vacia.setBorder(0);
				vacia.setPadding(3f);
				subTotalLetra.setPaddingBottom(18f);
				subTotalLetra.setBorder(0);
				subTotalLetra.setBorder(Rectangle.BOTTOM);
				subTotalLetra.setBorderColorBottom(borderTable);
				subTotalLetra.setBorderWidthBottom(2);
				subTotalNumero.setBorder(Rectangle.BOTTOM);
				subTotalNumero.setBorderColorBottom(borderTable);
				subTotalNumero.setBorderWidthBottom(2);
				ivaLetra.setPaddingBottom(18f);
				ivaLetra.setBorder(0);
				ivaLetra.setBorder(Rectangle.BOTTOM);
				ivaLetra.setBorderColorBottom(borderTable);
				ivaLetra.setBorderWidthBottom(2);
				ivaNumero.setBorder(Rectangle.BOTTOM);
				ivaNumero.setBorderColorBottom(borderTable);
				ivaNumero.setBorderWidthBottom(2);
				TotalLetra.setPaddingBottom(18f);
				TotalLetra.setBorder(0); 
				TotalLetra.setBorder(Rectangle.BOTTOM);
				TotalLetra.setBorderColorBottom(borderTable);
				TotalLetra.setBorderWidthBottom(2);
				TotalNumero.setBorder(Rectangle.BOTTOM);
				TotalNumero.setBorderColorBottom(borderTable);
				TotalNumero.setBorderWidthBottom(2);
				tablaPrendas.addCell(vacia);
				tablaPrendas.addCell(vacia);
				tablaPrendas.addCell(vacia);
				tablaPrendas.addCell(subTotalLetra);
				tablaPrendas.addCell(subTotalNumero);
				tablaPrendas.addCell(vacia);
				tablaPrendas.addCell(vacia);
				tablaPrendas.addCell(vacia);
				tablaPrendas.addCell(ivaLetra);
				tablaPrendas.addCell(ivaNumero);
				tablaPrendas.addCell(vacia);
				tablaPrendas.addCell(vacia);
				tablaPrendas.addCell(vacia);
				tablaPrendas.addCell(TotalLetra);
				tablaPrendas.addCell(TotalNumero);
			}	
		}
				//Condiciones
				//Espacio en blanco xd
				PdfPTable espacioTabla = new PdfPTable(1);
				PdfPCell cellUltimo = new PdfPCell(new Phrase(" "));
				cellUltimo.setBorder(0);
				espacioTabla.addCell(cell);
				
		    	//Primera tabla de numero, nombre y fecha
		    	PdfPTable tablaFooter1 = new PdfPTable(1);
		    	tablaFooter1.setWidthPercentage(100);
		    	PdfPCell tituloTablaFirmas = new PdfPCell(new Phrase("CONDICIONES", Titulos));
		    	tituloTablaFirmas.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	tituloTablaFirmas.setVerticalAlignment(Element.ALIGN_CENTER);
		    	tituloTablaFirmas.setBackgroundColor(BackgroundTitle);
		    	tituloTablaFirmas.setPadding(5f);
		    	tituloTablaFirmas.setPaddingBottom(8f);
		    	tituloTablaFirmas.setBorder(0);
		    	tituloTablaFirmas.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
		    	tituloTablaFirmas.setBorderWidthBottom(3f);
		    	tituloTablaFirmas.setBorderWidthRight(3f);
		    	tituloTablaFirmas.setBorderColor(colorBorderBottom);
		    	tablaFooter1.addCell(tituloTablaFirmas);
		    	PdfPCell leyenda1 = new PdfPCell(new Phrase(" "));
		    	PdfPCell leyenda2 = new PdfPCell(new Phrase("* Estos precios son más I.V.A.", Helvetica));
		    	PdfPCell leyenda3 = new PdfPCell(new Phrase("* El pago será de 45% días hábiles para un máximo de 50 personas y de 60 días hábiles para un número mayor; a partir del anticipo, toma de tallas, modelos y colores autorizados por uds.", Helvetica));
		    	PdfPCell leyenda4 = new PdfPCell(new Phrase("* Los uniformes son sobre talla y no sobre medida.", Helvetica));
		    	PdfPCell leyenda5 = new PdfPCell(new Phrase("* Los ajustes se entregarán en un máximo de 3 semanas a partir de su toma total.", Helvetica));
		    	PdfPCell leyenda6 = new PdfPCell(new Phrase("* Para el tiempo de entrega no se contarán la semana santa y las ultimas dos semanas de Diciembre.", Helvetica));
		    	PdfPCell leyenda7 = new PdfPCell(new Phrase("* Vigencia de la cotización: 15 días.", Helvetica));
		    	leyenda1.setBorder(0);
		    	leyenda2.setBorder(0);
		    	leyenda3.setBorder(0);
		    	leyenda4.setBorder(0);
		    	leyenda5.setBorder(0);
		    	leyenda6.setBorder(0);
		    	leyenda7.setBorder(0);
		    	leyenda1.setPadding(3f);
		    	leyenda2.setPadding(3f);
		    	leyenda3.setPadding(3f);
		    	leyenda4.setPadding(3f);
		    	leyenda5.setPadding(3f);
		    	leyenda6.setPadding(3f);
		    	leyenda7.setPadding(3f);
		    	leyenda1.setHorizontalAlignment(Element.ALIGN_LEFT);
		    	leyenda2.setHorizontalAlignment(Element.ALIGN_LEFT);
		    	leyenda3.setHorizontalAlignment(Element.ALIGN_LEFT);
		    	leyenda4.setHorizontalAlignment(Element.ALIGN_LEFT);
		    	leyenda5.setHorizontalAlignment(Element.ALIGN_LEFT);
		    	leyenda6.setHorizontalAlignment(Element.ALIGN_LEFT);
		    	leyenda7.setHorizontalAlignment(Element.ALIGN_LEFT);
		    	tablaFooter1.addCell(leyenda1);
		    	tablaFooter1.addCell(leyenda2);
		    	tablaFooter1.addCell(leyenda3);
		    	tablaFooter1.addCell(leyenda4);
		    	tablaFooter1.addCell(leyenda5);
		    	tablaFooter1.addCell(leyenda6);
		    	tablaFooter1.addCell(leyenda7);
		    	//Segunda tabla de las firmas
		    	PdfPTable tablaFooter2 = new PdfPTable(3);
		    	tablaFooter2.setWidthPercentage(90);
		    	PdfPCell espacioBlanco1 = new PdfPCell(new Phrase(" "));
		    	PdfPCell leyenda8 = new PdfPCell(new Phrase("Atentamente: ", HelveticaBold));
		    	PdfPCell espacioBlanco2 = new PdfPCell(new Phrase(" "));
		    	PdfPCell espacioBlanco3 = new PdfPCell(new Phrase(" "));
		    	espacioBlanco1.setBorder(0);
		    	leyenda8.setBorder(0);
		    	espacioBlanco2.setBorder(0);
		    	espacioBlanco3.setBorder(0);
		    	espacioBlanco1.setPadding(3f);
		    	leyenda8.setPadding(3f);
		    	espacioBlanco2.setPadding(3f);
		    	espacioBlanco3.setPadding(3f);
		    	espacioBlanco1.setHorizontalAlignment(Element.ALIGN_LEFT);
		    	leyenda8.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	espacioBlanco2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		    	espacioBlanco3.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	tablaFooter2.addCell(espacioBlanco1);
		    	tablaFooter2.addCell(leyenda8);
		    	tablaFooter2.addCell(espacioBlanco2);
		    	//
		    	tablaFooter2.addCell(espacioBlanco1);
		    	tablaFooter2.addCell(espacioBlanco3);
		    	tablaFooter2.addCell(espacioBlanco2);
		    	//Firmas ya pro
		    	Image firmaVentas = Image.getInstance("src/main/resources/static/dist/img/firmaVentas.png");
		    	firmaVentas.scalePercent(40f);
		    	
		    	PdfPCell firmaImgVentas = new PdfPCell(firmaVentas);
		    	firmaImgVentas.setBorder(0);
		    	firmaImgVentas.setPadding(3f);
		    	firmaImgVentas.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	tablaFooter2.addCell(firmaImgVentas);
		    	
		    	PdfPCell firmaActual = new PdfPCell(new Phrase(" "));
		    	firmaActual.setBorder(0);
		    	firmaActual.setPadding(3f);
		    	Image firma = Image.getInstance("src/main/resources/static/dist/img/firma.png");
		    	
		    	firma.scalePercent(10f);
		    	
		    	tablaFooter2.addCell(firmaActual);
		    	PdfPCell firmaImg = new PdfPCell(firma);
		    	firmaImg.setBorder(0);
		    	firmaImg.setPadding(3f);
		    	firmaImg.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	tablaFooter2.addCell(firmaImg);
		    	//Espacios para firmar
		    	PdfPCell espacioFirma1 = new PdfPCell(new Phrase("________________________"));
		    	PdfPCell espacioFirma2 = new PdfPCell(new Phrase("________________________"));
		    	espacioFirma1.setBorder(0);
		    	espacioFirma2.setBorder(0);
		    	espacioFirma1.setPadding(3f);
		    	espacioFirma2.setPadding(3f);
		    	espacioFirma1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	espacioFirma2.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	tablaFooter2.addCell(espacioFirma1);
		    	tablaFooter2.addCell(espacioBlanco3);
		    	tablaFooter2.addCell(espacioFirma2);
		    	//Nombres de las firmas
		    	PdfPCell nombreFirma1 = new PdfPCell(new Phrase("Diana Rodriguez", Helvetica));
		    	PdfPCell nombreFirma2 = new PdfPCell(new Phrase("Jorge Armando Cottone Morales", Helvetica));
		    	nombreFirma1.setBorder(0);
		    	nombreFirma1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	nombreFirma2.setBorder(0);
		    	nombreFirma2.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	tablaFooter2.addCell(nombreFirma1);
		    	tablaFooter2.addCell(espacioBlanco3);
		    	tablaFooter2.addCell(nombreFirma2);
		    	//Puestos firmas
		    	PdfPCell puestosFirma1 = new PdfPCell(new Phrase("Ejecutivo de Ventas", Helvetica));
		    	PdfPCell puestosFirma2 = new PdfPCell(new Phrase("Gerente Comercial", Helvetica));
		    	puestosFirma1.setBorder(0);
		    	puestosFirma1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	puestosFirma2.setBorder(0);
		    	puestosFirma2.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	tablaFooter2.addCell(puestosFirma1);
		    	tablaFooter2.addCell(espacioBlanco3);
		    	tablaFooter2.addCell(puestosFirma2);
		    	tablaFooter2.setWidths(new float[] { 6f, 3f, 6f });
		    	
		    	
		//Aqui se hace el merge del CV si es que existe
		if(cv) {
			HeaderFooterCotizacionesPdfView event = new HeaderFooterCotizacionesPdfView();
			writer.setPageEvent(event);
			document.open();
			document.addTitle("Cotizacion" + (String) model.get("id") + "_" + formatter.print(jodaTime));
			document.add(tablaHeader1);
			document.add(espacio);
			document.add(tablaHeader2);
			document.add(espacio);
			document.add(tablaHeader3);
			document.add(espacio);
			document.add(tablaPrendas);
			document.newPage();
			document.add(espacio);
			document.add(tablaFooter1);
			document.add(espacio);
			document.add(tablaFooter2);
			document.add(espacio);
			document.newPage();
			PdfReader reader = new PdfReader(FILE1);
			PdfImportedPage page = writer.getImportedPage(reader, 1); 
			PdfContentByte cb = writer.getDirectContent();
			cb.addTemplate(page, 0, 0);
			document.close();
		}
		if(!cv) {
			HeaderFooterCotizacionesPdfView event = new HeaderFooterCotizacionesPdfView();
			writer.setPageEvent(event);
			document.open();
			document.addTitle("Cotizacion" + (String) model.get("id") + "_" + formatter.print(jodaTime));
			document.add(tablaHeader1);
			document.add(espacio);
			document.add(tablaHeader2);
			document.add(espacio);
			document.add(tablaHeader3);
			document.add(espacio);
			document.add(tablaPrendas);
			document.newPage();
			document.add(espacio);
			document.add(tablaFooter1);
			document.add(espacio);
			document.add(tablaFooter2);
			document.add(espacio);
		}
		if(!correo.equalsIgnoreCase("nulo")) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			document = new Document(PageSize.A4);
			writer =  PdfWriter.getInstance(document, baos);
			HeaderFooterCotizacionesPdfView event = new HeaderFooterCotizacionesPdfView();
			writer.setPageEvent(event);
			document.open();
			document.add(new Chunk(""));
			document.addTitle("Cotizacion" + (String) model.get("id") + "_" + formatter.print(jodaTime));
			document.add(tablaHeader1);
			document.add(espacio);
			document.add(tablaHeader2);
			document.add(espacio);
			document.add(tablaHeader3);
			document.add(espacio);
			document.add(tablaPrendas);
			document.newPage();
			document.add(espacio);
			document.add(tablaFooter1);
			document.add(espacio);
			document.add(tablaFooter2);
			document.add(espacio);
			document.newPage();
			PdfReader reader = new PdfReader(FILE1);
			PdfImportedPage page = writer.getImportedPage(reader, 1); 
			PdfContentByte cb = writer.getDirectContent();
			cb.addTemplate(page, 0, 0);
			document.close();
			enviarCorreoService.enviarCorreoArchivoAdjuntoConMime("dtu_test@uniformes-altima.com.mx", correo, "Envio de Cotización", "A continuación se anexa un informe de la cotización solicitada.", baos, "Cotizacion" + (String) model.get("id") + "_" + formatter.print(jodaTime) + ".pdf");
		}
	}
}
