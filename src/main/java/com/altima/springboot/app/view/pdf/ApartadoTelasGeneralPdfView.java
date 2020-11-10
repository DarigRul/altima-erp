package com.altima.springboot.app.view.pdf;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


@SuppressWarnings("unchecked")
@Component("/detalle-reporte-general")
public class ApartadoTelasGeneralPdfView extends AbstractPdfView{

	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		document.setMargins(40f, 40f, 10f, 70f);
		HeaderFooterCotizacionesPdfView event = new HeaderFooterCotizacionesPdfView();
		writer.setPageEvent(event);
		document.open();
		document.addTitle("Reporte/General");
		
		DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
		separadoresPersonalizados.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("0.##", separadoresPersonalizados);
		
		SimpleDateFormat formato = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
		String fecha = formato.format(new Date());
		
		List<Object[]> listaTelas = (List<Object[]>) model.get("apartadoTelasReporte");
		
		
		
		// TODO Auto-generated method stub
		//Color fuerte = new Color(2, 136, 209);
		//Color bajito = new Color(255, 137, 137);
		Color borderTable = new Color(205,205,205);
		Color colorDatos = new Color(170,170,170);
		//Color TitulosBlancos = new Color(255,255,255);
		//Color colorBorderBottom = new Color(255,185,24);
		Color borderGray = new Color(52,58,64);
		//Color backgroundWhite = new Color(255,255,255);
		//Color BackgroundTitle = new Color(90,90,90);
		Color textDarkGray = new Color(33,37,41);
		Font HelveticaBold = new Font(BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 11);
		Font subtitulos = new Font(BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 11, 0, textDarkGray);
		//Font Titulos = new Font(BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED), 13, 0);
		//Font TitulosOscuros = new Font(BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 13, 0, textDarkGray);
		Font datosGris = new Font(BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 11, 0, colorDatos);
		Font Helvetica = new Font(BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED), 11);
		//Font letraCondiciones = new Font(BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED), 8);
		
		PdfPTable espacio = new PdfPTable(1);
		espacio.setWidthPercentage(100);
		PdfPCell fechaCotizacion2 = new PdfPCell(new Phrase(fecha.substring(0, 1).toUpperCase() + fecha.substring(1), Helvetica));
		fechaCotizacion2.setBorder(0);
		fechaCotizacion2.setBorderWidthBottom(2f);
		fechaCotizacion2.setPaddingBottom(13f);
		fechaCotizacion2.setBorderColor(borderGray);
		fechaCotizacion2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		fechaCotizacion2.setVerticalAlignment(Element.ALIGN_CENTER);


		espacio.addCell(fechaCotizacion2);
	
		PdfPTable tituloDocumento = new PdfPTable(5);
		tituloDocumento.setWidthPercentage(100);
		tituloDocumento.setWidths(new float[] { 3f, 4f, 2f, 3f, 4f});
		PdfPCell cellVacia = new PdfPCell(new Phrase(""));
		PdfPCell pedidoTitulo = new PdfPCell(new Phrase("Pedido: ", Helvetica));
		PdfPCell pedido = new PdfPCell(new Phrase());
		PdfPCell clienteTitulo = new PdfPCell(new Phrase());
		PdfPCell cliente = new PdfPCell(new Phrase());
		PdfPCell solicitanteTitulo = new PdfPCell(new Phrase());
		PdfPCell solicitante = new PdfPCell(new Phrase());
		PdfPCell apartadoTelaTitulo = new PdfPCell(new Phrase());
		PdfPCell apartadoTela = new PdfPCell(new Phrase());
		PdfPCell fechaSolTitulo = new PdfPCell(new Phrase());
		PdfPCell fechaSol = new PdfPCell(new Phrase());
		PdfPCell fechaEntreTitulo = new PdfPCell(new Phrase());
		PdfPCell fechaEntrega = new PdfPCell(new Phrase());
		PdfPCell PersonasTitulo = new PdfPCell(new Phrase());
		PdfPCell personas = new PdfPCell(new Phrase());
		PdfPCell SPFTitulo = new PdfPCell(new Phrase());
		PdfPCell SPF = new PdfPCell(new Phrase());
		
		try {
			Object[] filaUno = (Object[]) listaTelas.get(0);
			int numPersonas = Integer.parseInt(filaUno[18].toString());
			int numSpf = Integer.parseInt(filaUno[19].toString());
			 
			pedido = new PdfPCell(new Phrase(filaUno[7].toString(), datosGris));
			clienteTitulo = new PdfPCell(new Phrase("Cliente: ", Helvetica));
			cliente = new PdfPCell(new Phrase(filaUno[6].toString(), datosGris));
			solicitanteTitulo = new PdfPCell(new Phrase("Solicitante: ", Helvetica));
			solicitante = new PdfPCell(new Phrase("Agente de ventas ", datosGris));
			apartadoTelaTitulo = new PdfPCell(new Phrase("Apartado de tela: ", Helvetica));
			apartadoTela = new PdfPCell(new Phrase("Definitiva ", datosGris));
			fechaSolTitulo = new PdfPCell(new Phrase("Fecha de solicitud: ", Helvetica));
			fechaSol = new PdfPCell(new Phrase(filaUno[8].toString(), datosGris));
			fechaEntreTitulo = new PdfPCell(new Phrase("Fecha de entrega: ", Helvetica));
			fechaEntrega = new PdfPCell(new Phrase(filaUno[9].toString(), datosGris));
			PersonasTitulo = new PdfPCell(new Phrase("Personas: ", Helvetica));
			personas = new PdfPCell(new Phrase(""+numPersonas, datosGris));
			SPFTitulo = new PdfPCell(new Phrase("SPF: ", Helvetica));
			SPF = new PdfPCell(new Phrase(""+numSpf, datosGris));
		}
		catch(Exception e) {
			pedido = new PdfPCell(new Phrase("No hay registro", datosGris));
			clienteTitulo = new PdfPCell(new Phrase("Cliente: ", Helvetica));
			cliente = new PdfPCell(new Phrase("No hay registro", datosGris));
			solicitanteTitulo = new PdfPCell(new Phrase("Solicitante: ", Helvetica));
			solicitante = new PdfPCell(new Phrase("No hay registro", datosGris));
			apartadoTelaTitulo = new PdfPCell(new Phrase("Apartado de tela: ", Helvetica));
			apartadoTela = new PdfPCell(new Phrase("No hay registro", datosGris));
			fechaSolTitulo = new PdfPCell(new Phrase("Fecha de solicitud: ", Helvetica));
			fechaSol = new PdfPCell(new Phrase("No hay registro", datosGris));
			fechaEntreTitulo = new PdfPCell(new Phrase("Fecha de entrega: ", Helvetica));
			fechaEntrega = new PdfPCell(new Phrase("No hay registro", datosGris));
			PersonasTitulo = new PdfPCell(new Phrase("Personas: ", Helvetica));
			personas = new PdfPCell(new Phrase("No hay registro", datosGris));
			SPFTitulo = new PdfPCell(new Phrase("SPF: ", Helvetica));
			SPF = new PdfPCell(new Phrase("No hay registro", datosGris));
		}
		cellVacia.setBorder(0);
		
		pedidoTitulo.setBorder(0);
		pedidoTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
		pedidoTitulo.setBorder(Rectangle.BOTTOM);
		pedidoTitulo.setBorderColorBottom(borderTable);
		pedidoTitulo.setBorderWidthBottom(2);
		pedidoTitulo.setPaddingTop(6f);
		pedidoTitulo.setPaddingBottom(4f);
		
		pedido.setBorder(0);
		pedido.setHorizontalAlignment(Element.ALIGN_LEFT);
		pedido.setBorder(Rectangle.BOTTOM);
    	pedido.setBorderColorBottom(borderTable);
    	pedido.setBorderWidthBottom(2);
    	pedido.setPaddingTop(6f);
    	pedido.setPaddingBottom(4f);
    	
		clienteTitulo.setBorder(0);
		clienteTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
		clienteTitulo.setBorder(Rectangle.BOTTOM);
		clienteTitulo.setBorderColorBottom(borderTable);
		clienteTitulo.setBorderWidthBottom(2);
		clienteTitulo.setPaddingBottom(4f);

		cliente.setBorder(0);
		cliente.setHorizontalAlignment(Element.ALIGN_LEFT);
		cliente.setBorder(Rectangle.BOTTOM);
		cliente.setBorderColorBottom(borderTable);
		cliente.setBorderWidthBottom(2);
		cliente.setPaddingBottom(4f);
    	
		solicitanteTitulo.setBorder(0);
		solicitanteTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
		solicitanteTitulo.setBorder(Rectangle.BOTTOM);
		solicitanteTitulo.setBorderColorBottom(borderTable);
		solicitanteTitulo.setBorderWidthBottom(2);
		solicitanteTitulo.setPaddingBottom(4f);
		
		solicitante.setBorder(0);
		solicitante.setHorizontalAlignment(Element.ALIGN_LEFT);
		solicitante.setBorder(Rectangle.BOTTOM);
		solicitante.setBorderColorBottom(borderTable);
		solicitante.setBorderWidthBottom(2);
		solicitante.setPaddingBottom(4f);
		
		apartadoTelaTitulo.setBorder(0);
		apartadoTelaTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
		apartadoTelaTitulo.setBorder(Rectangle.BOTTOM);
		apartadoTelaTitulo.setBorderColorBottom(borderTable);
		apartadoTelaTitulo.setBorderWidthBottom(2);
		apartadoTelaTitulo.setPaddingBottom(4f);
		
		apartadoTela.setBorder(0);
		apartadoTela.setHorizontalAlignment(Element.ALIGN_LEFT);
		apartadoTela.setBorder(Rectangle.BOTTOM);
		apartadoTela.setBorderColorBottom(borderTable);
		apartadoTela.setBorderWidthBottom(2);
		apartadoTela.setPaddingBottom(4f);
		
		fechaSolTitulo.setBorder(0);
		fechaSolTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
		fechaSolTitulo.setBorder(Rectangle.BOTTOM);
		fechaSolTitulo.setBorderColorBottom(borderTable);
		fechaSolTitulo.setBorderWidthBottom(2);
		fechaSolTitulo.setPaddingTop(6f);
		fechaSolTitulo.setPaddingBottom(4f);
		
		fechaSol.setBorder(0);
		fechaSol.setHorizontalAlignment(Element.ALIGN_LEFT);
		fechaSol.setBorder(Rectangle.BOTTOM);
		fechaSol.setBorderColorBottom(borderTable);
		fechaSol.setBorderWidthBottom(2);
		fechaSol.setPaddingTop(6f);
		fechaSol.setPaddingBottom(4f);
    	
		fechaEntreTitulo.setBorder(0);
		fechaEntreTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
		fechaEntreTitulo.setBorder(Rectangle.BOTTOM);
		fechaEntreTitulo.setBorderColorBottom(borderTable);
		fechaEntreTitulo.setBorderWidthBottom(2);
		fechaEntreTitulo.setPaddingBottom(4f);

		fechaEntrega.setBorder(0);
		fechaEntrega.setHorizontalAlignment(Element.ALIGN_LEFT);
		fechaEntrega.setBorder(Rectangle.BOTTOM);
		fechaEntrega.setBorderColorBottom(borderTable);
		fechaEntrega.setBorderWidthBottom(2);
		fechaEntrega.setPaddingBottom(4f);
    	
		PersonasTitulo.setBorder(0);
		PersonasTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
		PersonasTitulo.setBorder(Rectangle.BOTTOM);
		PersonasTitulo.setBorderColorBottom(borderTable);
		PersonasTitulo.setBorderWidthBottom(2);
		PersonasTitulo.setPaddingBottom(4f);
		
		personas.setBorder(0);
		personas.setHorizontalAlignment(Element.ALIGN_LEFT);
		personas.setBorder(Rectangle.BOTTOM);
		personas.setBorderColorBottom(borderTable);
		personas.setBorderWidthBottom(2);
		personas.setPaddingBottom(4f);
		
		SPFTitulo.setBorder(0);
		SPFTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
		SPFTitulo.setBorder(Rectangle.BOTTOM);
		SPFTitulo.setBorderColorBottom(borderTable);
		SPFTitulo.setBorderWidthBottom(2);
		SPFTitulo.setPaddingBottom(4f);
		
		SPF.setBorder(0);
		SPF.setHorizontalAlignment(Element.ALIGN_LEFT);
		SPF.setBorder(Rectangle.BOTTOM);
		SPF.setBorderColorBottom(borderTable);
		SPF.setBorderWidthBottom(2);
		SPF.setPaddingBottom(4f);

		tituloDocumento.addCell(pedidoTitulo);
		tituloDocumento.addCell(pedido);
		tituloDocumento.addCell(cellVacia);
		tituloDocumento.addCell(fechaSolTitulo);
		tituloDocumento.addCell(fechaSol);
		
		tituloDocumento.addCell(clienteTitulo);
		tituloDocumento.addCell(cliente);
		tituloDocumento.addCell(cellVacia);
		tituloDocumento.addCell(fechaEntreTitulo);
		tituloDocumento.addCell(fechaEntrega);
		
		tituloDocumento.addCell(solicitanteTitulo);
		tituloDocumento.addCell(solicitante);
		tituloDocumento.addCell(cellVacia);
		tituloDocumento.addCell(PersonasTitulo);
		tituloDocumento.addCell(personas);
		
		tituloDocumento.addCell(apartadoTelaTitulo);
		tituloDocumento.addCell(apartadoTela);
		tituloDocumento.addCell(cellVacia);
		tituloDocumento.addCell(SPFTitulo);
		tituloDocumento.addCell(SPF);
		cellVacia.setPaddingBottom(12f);
		tituloDocumento.addCell(cellVacia);
		tituloDocumento.addCell(cellVacia);
		tituloDocumento.addCell(cellVacia);
		tituloDocumento.addCell(cellVacia);
		tituloDocumento.addCell(cellVacia);
		
		//-----------------------------------------------//
		//        Subtitulo de apartado de telas
		
		
		PdfPTable SubtituloDefinitiva = new PdfPTable(1);
		SubtituloDefinitiva.setWidthPercentage(100);
		PdfPCell subtitulo = new PdfPCell(new Phrase(" ", subtitulos));
		subtitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
		subtitulo.setVerticalAlignment(Element.ALIGN_CENTER);
		subtitulo.setBorder(0);
		subtitulo.setBorder(Rectangle.TOP);
		subtitulo.setBorderWidthTop(2f);
		subtitulo.setBorderColor(borderGray);
		subtitulo.setPaddingTop(6f);
		subtitulo.setPaddingBottom(-10f);
		
		SubtituloDefinitiva.addCell(subtitulo);
		
		//-----------------------------------------------//
		//  Empieza el listado de todas las telas con sus tablas
		
		PdfPTable tablaInformacionTelas = new PdfPTable(1);
		tablaInformacionTelas.setWidthPercentage(100);
		
		
		PdfPCell contenido = new PdfPCell();
		contenido.setBorder(0);
		
		PdfPTable bloqueInformacion = new PdfPTable(2);
		bloqueInformacion.setWidthPercentage(100);
		bloqueInformacion.setWidths(new float[] {10f, 10f});
		
		//Esta condición aplica cuando no existen datos almacenados en el pedido//
		if(listaTelas.isEmpty()) {
			
			PdfPTable TelasconImagen = new PdfPTable(3);
			TelasconImagen.setWidthPercentage(100);
			TelasconImagen.setWidths(new float[] { 2.4f, 3f, 6f});
			
			PdfPTable ContenidoTelas = new PdfPTable(2);
			ContenidoTelas.setWidthPercentage(100);
			ContenidoTelas.setWidths(new float[] {3f, 2f});
			PdfPCell ClaveTelaTitulo = new PdfPCell(new Phrase("Clave tela: ", Helvetica));
			PdfPCell ClaveTela = new PdfPCell(new Phrase("No hay registro", datosGris));
			PdfPCell totalConsumoTitulo = new PdfPCell(new Phrase("Total Consumo: ", Helvetica));
			PdfPCell totalConsumo = new PdfPCell(new Phrase("No hay registro", datosGris));
			PdfPCell surtirTitulo = new PdfPCell(new Phrase("Surtir: ", Helvetica));
			PdfPCell surtir = new PdfPCell(new Phrase("No hay registro", datosGris));
			PdfPCell spfTitulo = new PdfPCell(new Phrase("SPF: ", Helvetica));
			PdfPCell spf = new PdfPCell(new Phrase("No hay registro", datosGris));
			
			ClaveTelaTitulo.setBorder(0);
			ClaveTelaTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
			ClaveTelaTitulo.setBorder(Rectangle.BOTTOM);
			ClaveTelaTitulo.setBorderColorBottom(borderTable);
			ClaveTelaTitulo.setBorderWidthBottom(2);
			ClaveTelaTitulo.setPaddingBottom(4f);
			
			ClaveTela.setBorder(0);
			ClaveTela.setHorizontalAlignment(Element.ALIGN_LEFT);
			ClaveTela.setBorder(Rectangle.BOTTOM);
			ClaveTela.setBorderColorBottom(borderTable);
			ClaveTela.setBorderWidthBottom(2);
			ClaveTela.setPaddingBottom(4f);
	    	
			totalConsumoTitulo.setBorder(0);
			totalConsumoTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
			totalConsumoTitulo.setBorder(Rectangle.BOTTOM);
			totalConsumoTitulo.setBorderColorBottom(borderTable);
			totalConsumoTitulo.setBorderWidthBottom(2);
			totalConsumoTitulo.setPaddingBottom(4f);

			totalConsumo.setBorder(0);
			totalConsumo.setHorizontalAlignment(Element.ALIGN_LEFT);
			totalConsumo.setBorder(Rectangle.BOTTOM);
			totalConsumo.setBorderColorBottom(borderTable);
			totalConsumo.setBorderWidthBottom(2);
			totalConsumo.setPaddingBottom(4f);
	    	
			surtirTitulo.setBorder(0);
			surtirTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
			surtirTitulo.setBorder(Rectangle.BOTTOM);
			surtirTitulo.setBorderColorBottom(borderTable);
			surtirTitulo.setBorderWidthBottom(2);
			surtirTitulo.setPaddingBottom(4f);
			
			surtir.setBorder(0);
			surtir.setHorizontalAlignment(Element.ALIGN_LEFT);
			surtir.setBorder(Rectangle.BOTTOM);
			surtir.setBorderColorBottom(borderTable);
			surtir.setBorderWidthBottom(2);
			surtir.setPaddingBottom(4f);
			
			spfTitulo.setBorder(0);
			spfTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
			spfTitulo.setBorder(Rectangle.BOTTOM);
			spfTitulo.setBorderColorBottom(borderTable);
			spfTitulo.setBorderWidthBottom(2);
			spfTitulo.setPaddingBottom(4f);
			
			spf.setBorder(0);
			spf.setHorizontalAlignment(Element.ALIGN_LEFT);
			spf.setBorder(Rectangle.BOTTOM);
			spf.setBorderColorBottom(borderTable);
			spf.setBorderWidthBottom(2);
			spf.setPaddingBottom(4f);
			cellVacia.setPaddingBottom(1f);
			ContenidoTelas.addCell(ClaveTelaTitulo);
			ContenidoTelas.addCell(ClaveTela);
			ContenidoTelas.addCell(totalConsumoTitulo);
			ContenidoTelas.addCell(totalConsumo);
			ContenidoTelas.addCell(surtirTitulo);
			ContenidoTelas.addCell(surtir);
			ContenidoTelas.addCell(spfTitulo);
			ContenidoTelas.addCell(spf);

			contenido = new PdfPCell(ContenidoTelas);
			contenido.setBorder(0);
			
			TelasconImagen.addCell(cellVacia); //Aquí va la imagen de la tela
			TelasconImagen.addCell(contenido);
			TelasconImagen.addCell(cellVacia);
			
			contenido = new PdfPCell(TelasconImagen);
			contenido.setBorder(0);
			contenido.setPaddingTop(8f);
			tablaInformacionTelas.addCell(contenido);
			
			//-----------------------------------------------------//
			//  Empieza la tabla de la información de las telas
			
			PdfPTable tablaTelas = new PdfPTable(2);
			tablaTelas.setWidthPercentage(100);
			tablaTelas.setWidths(new float[] { 4.14f, 5f});
			
			PdfPCell nombrePrendaCabezero = new PdfPCell(new Phrase("Datos Generales", HelveticaBold));
			PdfPCell nombrePrendaCabezero2 = new PdfPCell(new Phrase("Consumo", HelveticaBold));
			nombrePrendaCabezero.setBorderColor(borderGray);
			nombrePrendaCabezero.setBorderColorRight(borderTable);
			nombrePrendaCabezero.setBorder(0);
			nombrePrendaCabezero.setBorderWidthBottom(2f);
			nombrePrendaCabezero.setBorderWidthRight(1f);
			nombrePrendaCabezero.setPaddingBottom(8f);
			nombrePrendaCabezero.setPaddingTop(6f);
			nombrePrendaCabezero.setHorizontalAlignment(Element.ALIGN_CENTER);
			nombrePrendaCabezero.setVerticalAlignment(Element.ALIGN_CENTER);
			
			nombrePrendaCabezero2.setBorderColor(borderGray);
			nombrePrendaCabezero2.setBorder(0);
			nombrePrendaCabezero2.setBorderWidthBottom(2f);
			nombrePrendaCabezero2.setPaddingBottom(8f);
			nombrePrendaCabezero2.setPaddingTop(6f);
			nombrePrendaCabezero2.setHorizontalAlignment(Element.ALIGN_CENTER);
			nombrePrendaCabezero2.setVerticalAlignment(Element.ALIGN_CENTER);
			
			tablaTelas.addCell(nombrePrendaCabezero);
			tablaTelas.addCell(nombrePrendaCabezero2);
			
			contenido = new PdfPCell(tablaTelas);
			contenido.setBorder(0);
			contenido.setPaddingTop(8f);
			tablaInformacionTelas.addCell(contenido);
			
			tablaTelas = new PdfPTable(7);
			tablaTelas.setWidthPercentage(100);
			tablaTelas.setWidths(new float[] { 3f, 4f, 2.5f, 2.5f, 2.5f, 2.5f, 4f });
			
			PdfPCell Cabezero1 = new PdfPCell(new Phrase("Modelo", subtitulos));
			PdfPCell Cabezero2 = new PdfPCell(new Phrase("#Personas", subtitulos));
			PdfPCell Cabezero3 = new PdfPCell(new Phrase("#SPF", subtitulos));
			PdfPCell Cabezero4 = new PdfPCell(new Phrase("Surtir", subtitulos));
			PdfPCell Cabezero5 = new PdfPCell(new Phrase("SPF", subtitulos));
			PdfPCell Cabezero6 = new PdfPCell(new Phrase("Total", subtitulos));
			PdfPCell Cabezero7 = new PdfPCell(new Phrase("Tipo", subtitulos));
			Cabezero1.setBorder(0);
			Cabezero1.setPaddingBottom(8f);
			Cabezero1.setPaddingTop(6f);
			Cabezero1.setVerticalAlignment(Element.ALIGN_CENTER);
			Cabezero1.setBorderWidthBottom(2f);
			Cabezero1.setBorderColor(borderGray);
			Cabezero2.setBorder(0);
			Cabezero2.setPaddingBottom(8f);
			Cabezero2.setPaddingTop(6f);
			Cabezero2.setVerticalAlignment(Element.ALIGN_CENTER);
			Cabezero2.setBorderWidthBottom(2f);
			Cabezero2.setBorderColor(borderGray);
			Cabezero3.setBorder(0);
			Cabezero3.setPaddingBottom(8f);
			Cabezero3.setPaddingTop(6f);
			Cabezero3.setVerticalAlignment(Element.ALIGN_CENTER);
			Cabezero3.setBorderWidthBottom(2f);
			Cabezero3.setBorderWidthRight(1f);
			Cabezero3.setBorderColor(borderGray);
			Cabezero3.setBorderColorRight(borderTable);
			Cabezero4.setBorder(0);
			Cabezero4.setPaddingBottom(8f);
			Cabezero4.setPaddingTop(6f);
			Cabezero4.setVerticalAlignment(Element.ALIGN_CENTER);
			Cabezero4.setBorderWidthBottom(2f);
			Cabezero4.setBorderColor(borderGray);
			Cabezero5.setBorder(0);
			Cabezero5.setPaddingBottom(8f);
			Cabezero5.setPaddingTop(6f);
			Cabezero5.setVerticalAlignment(Element.ALIGN_CENTER);
			Cabezero5.setBorderWidthBottom(2f);
			Cabezero5.setBorderColor(borderGray);
			Cabezero6.setBorder(0);
			Cabezero6.setPaddingBottom(8f);
			Cabezero6.setPaddingTop(6f);
			Cabezero6.setVerticalAlignment(Element.ALIGN_CENTER);
			Cabezero6.setBorderWidthBottom(2f);
			Cabezero6.setBorderColor(borderGray);
			Cabezero7.setBorder(0);
			Cabezero7.setPaddingBottom(8f);
			Cabezero7.setPaddingTop(6f);
			Cabezero7.setVerticalAlignment(Element.ALIGN_CENTER);
			Cabezero7.setBorderWidthBottom(2f);
			Cabezero7.setBorderColor(borderGray);

			tablaTelas.addCell(Cabezero1);
			tablaTelas.addCell(Cabezero2);
			tablaTelas.addCell(Cabezero3);
			tablaTelas.addCell(Cabezero4);
			tablaTelas.addCell(Cabezero5);
			tablaTelas.addCell(Cabezero6);
			tablaTelas.addCell(Cabezero7);
			
			contenido = new PdfPCell(tablaTelas);
			contenido.setBorder(0);
			
			tablaInformacionTelas.addCell(contenido);
		}
		
		else {
			
			String idTela = listaTelas.get(0)[4].toString();
			String imgUrl = "";
			float sumaPersonas = 0;
			float totalConsumoCantidad = 0;
			float spfConsumo = 0;
			int contador = 0;
			String codigoTela = listaTelas.get(0)[3].toString();
			int validador = 0;
			int validadorCelda = 0;
			
			//Verifica que solo existe un resitro de la primera tela a mapear//
			for (int i = 0;i<listaTelas.size();i++) {
				if(listaTelas.get(i)[4].toString().equals(listaTelas.get(0)[4].toString())) {
					validador++;
				}
			}
			
			//Empieza el recorrido de la query//
			for (int i = 0;i<listaTelas.size();i++) {
				Object[] fila = (Object[]) listaTelas.get(i);
				
				//Información de la prenda
				//Verifica que el id de la tela anterior coincida con el id tela del registro actual
				if(idTela.equals(fila[4].toString())){
				
					//  Suma el numero de personas, el consumo por la cantidad y suma consumo de spf
					sumaPersonas += Float.parseFloat(fila[11].toString());
					totalConsumoCantidad += (Float.parseFloat(fila[11].toString())+Float.parseFloat(fila[15].toString()));
					spfConsumo += Float.parseFloat(fila[15].toString());
			
					//Se asigna la url de la imagen de la tela actual
					imgUrl = fila[14].toString();
				}

				//Verifica que la tela anterior ya no es la misma de la actual o 
				//que el último registro coincida con el id de tela actual o
				//que en la primera tela solo tenga un registro
				if(!idTela.equals(fila[4].toString()) ||contador==listaTelas.size()-1 ||
						validador==1) {
					
					//Verifica que la primera tela solo contenga un registro para poder asignar el codigo de tela y la url de la imagen de la tela actual
					if(validador==1) {
						imgUrl = fila[14].toString();
						codigoTela = fila[3].toString();
						validador=0;
						System.out.println("entra");
					}
					
					//Verifica que la ultima tela sólo contenga un registro
					if(contador==listaTelas.size()-1 && !idTela.equals(fila[4].toString())) {
						
						if(contador==listaTelas.size()-1 && contador!=1) {
							System.out.println("entra al registro de ultima tela con 1 registro");
	//						//Información de cada tela
	//						
							PdfPTable TelasconImagen = new PdfPTable(3);
							TelasconImagen.setWidthPercentage(100);
							TelasconImagen.setWidths(new float[] { 5f, 5f, 0.5f});
							Image img = Image.getInstance("https://res.cloudinary.com/dti-consultores/image/upload/v1603465395/telas/"+imgUrl);
							img.scaleAbsolute(100f, 60f);
							PdfPCell imagenTela = new PdfPCell(img);
							imagenTela.setBorder(0);
							
							
							PdfPTable ContenidoTelas = new PdfPTable(2);
							ContenidoTelas.setWidthPercentage(100);
							ContenidoTelas.setWidths(new float[] {3f, 2f});
							PdfPCell ClaveTelaTitulo = new PdfPCell(new Phrase("Clave tela: ", Helvetica));
							PdfPCell ClaveTela = new PdfPCell(new Phrase(codigoTela, datosGris));
							PdfPCell totalConsumoTitulo = new PdfPCell(new Phrase("Total Consumo: ", Helvetica));
							PdfPCell totalConsumo = new PdfPCell(new Phrase(""+df.format(totalConsumoCantidad), datosGris));
							PdfPCell surtirTitulo = new PdfPCell(new Phrase("Surtir: ", Helvetica));
							PdfPCell surtir = new PdfPCell(new Phrase(""+df.format(sumaPersonas), datosGris));
							PdfPCell spfTitulo = new PdfPCell(new Phrase("SPF: ", Helvetica));
							PdfPCell spf = new PdfPCell(new Phrase(""+df.format(spfConsumo), datosGris));
							
							ClaveTelaTitulo.setBorder(0);
							ClaveTelaTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
							ClaveTelaTitulo.setBorder(Rectangle.BOTTOM);
							ClaveTelaTitulo.setBorderColorBottom(borderTable);
							ClaveTelaTitulo.setBorderWidthBottom(2);
							ClaveTelaTitulo.setPaddingBottom(4f);
							
							ClaveTela.setBorder(0);
							ClaveTela.setHorizontalAlignment(Element.ALIGN_LEFT);
							ClaveTela.setBorder(Rectangle.BOTTOM);
							ClaveTela.setBorderColorBottom(borderTable);
							ClaveTela.setBorderWidthBottom(2);
							ClaveTela.setPaddingBottom(4f);
					    	
							totalConsumoTitulo.setBorder(0);
							totalConsumoTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
							totalConsumoTitulo.setBorder(Rectangle.BOTTOM);
							totalConsumoTitulo.setBorderColorBottom(borderTable);
							totalConsumoTitulo.setBorderWidthBottom(2);
							totalConsumoTitulo.setPaddingBottom(4f);
					
							totalConsumo.setBorder(0);
							totalConsumo.setHorizontalAlignment(Element.ALIGN_LEFT);
							totalConsumo.setBorder(Rectangle.BOTTOM);
							totalConsumo.setBorderColorBottom(borderTable);
							totalConsumo.setBorderWidthBottom(2);
							totalConsumo.setPaddingBottom(4f);
					    	
							surtirTitulo.setBorder(0);
							surtirTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
							surtirTitulo.setBorder(Rectangle.BOTTOM);
							surtirTitulo.setBorderColorBottom(borderTable);
							surtirTitulo.setBorderWidthBottom(2);
							surtirTitulo.setPaddingBottom(4f);
							
							surtir.setBorder(0);
							surtir.setHorizontalAlignment(Element.ALIGN_LEFT);
							surtir.setBorder(Rectangle.BOTTOM);
							surtir.setBorderColorBottom(borderTable);
							surtir.setBorderWidthBottom(2);
							surtir.setPaddingBottom(4f);
							
							spfTitulo.setBorder(0);
							spfTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
							spfTitulo.setBorder(Rectangle.BOTTOM);
							spfTitulo.setBorderColorBottom(borderTable);
							spfTitulo.setBorderWidthBottom(2);
							spfTitulo.setPaddingBottom(4f);
							
							spf.setBorder(0);
							spf.setHorizontalAlignment(Element.ALIGN_LEFT);
							spf.setBorder(Rectangle.BOTTOM);
							spf.setBorderColorBottom(borderTable);
							spf.setBorderWidthBottom(2);
							spf.setPaddingBottom(4f);
							cellVacia.setPaddingBottom(1f);
							ContenidoTelas.addCell(ClaveTelaTitulo);
							ContenidoTelas.addCell(ClaveTela);
							ContenidoTelas.addCell(totalConsumoTitulo);
							ContenidoTelas.addCell(totalConsumo);
							ContenidoTelas.addCell(surtirTitulo);
							ContenidoTelas.addCell(surtir);
							ContenidoTelas.addCell(spfTitulo);
							ContenidoTelas.addCell(spf);
					
							contenido = new PdfPCell(ContenidoTelas);
							contenido.setBorder(0);
							
							//Se agregan a una sola tabla para poder modular los registros y no se desface el diseño
							TelasconImagen.addCell(imagenTela); //Aquí va la imagen de la tela
							TelasconImagen.addCell(contenido);
							TelasconImagen.addCell(cellVacia);
							
							contenido = new PdfPCell(TelasconImagen);
							contenido.setBorder(0);
							contenido.setPaddingTop(30f);
							tablaInformacionTelas.addCell(contenido);
							
							contenido = new PdfPCell(tablaInformacionTelas);
							contenido.setBorder(0);
							bloqueInformacion.addCell(contenido);
							validadorCelda++;
					}
						tablaInformacionTelas = new PdfPTable(1);
						tablaInformacionTelas.setWidthPercentage(100);
						
						//Se reinicia la suma para listar las prendas de la siguiente tela
						sumaPersonas = 0;
						totalConsumoCantidad = 0;
						spfConsumo = 0;
						
						imgUrl = fila[14].toString();
						codigoTela = fila[3].toString();
						validador=-1;
						
						sumaPersonas += Float.parseFloat(fila[11].toString());
						totalConsumoCantidad += (Float.parseFloat(fila[11].toString())+Float.parseFloat(fila[15].toString()));
						spfConsumo += Float.parseFloat(fila[15].toString());
						
						
					}
					
					if(validador==-1 || validador==0) {
						System.out.println("entra al registro de tela");
					//Inician los subtitulos de la tabla
//					//Información de cada tela
//					
					PdfPTable TelasconImagen = new PdfPTable(3);
					TelasconImagen.setWidthPercentage(100);
					TelasconImagen.setWidths(new float[] { 5f, 5f, 0.5f});
					Image img = Image.getInstance("https://res.cloudinary.com/dti-consultores/image/upload/v1603465395/telas/"+imgUrl);
					img.scaleAbsolute(100f, 60f);
					PdfPCell imagenTela = new PdfPCell(img);
					imagenTela.setBorder(0);
					
					
					PdfPTable ContenidoTelas = new PdfPTable(2);
					ContenidoTelas.setWidthPercentage(100);
					ContenidoTelas.setWidths(new float[] {3f, 2f});
					PdfPCell ClaveTelaTitulo = new PdfPCell(new Phrase("Clave tela: ", Helvetica));
					PdfPCell ClaveTela = new PdfPCell(new Phrase(codigoTela, datosGris));
					PdfPCell totalConsumoTitulo = new PdfPCell(new Phrase("Total Consumo: ", Helvetica));
					PdfPCell totalConsumo = new PdfPCell(new Phrase(""+df.format(totalConsumoCantidad), datosGris));
					PdfPCell surtirTitulo = new PdfPCell(new Phrase("Surtir: ", Helvetica));
					PdfPCell surtir = new PdfPCell(new Phrase(""+df.format(sumaPersonas), datosGris));
					PdfPCell spfTitulo = new PdfPCell(new Phrase("SPF: ", Helvetica));
					PdfPCell spf = new PdfPCell(new Phrase(""+df.format(spfConsumo), datosGris));
					
					ClaveTelaTitulo.setBorder(0);
					ClaveTelaTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
					ClaveTelaTitulo.setBorder(Rectangle.BOTTOM);
					ClaveTelaTitulo.setBorderColorBottom(borderTable);
					ClaveTelaTitulo.setBorderWidthBottom(2);
					ClaveTelaTitulo.setPaddingBottom(4f);
					
					ClaveTela.setBorder(0);
					ClaveTela.setHorizontalAlignment(Element.ALIGN_LEFT);
					ClaveTela.setBorder(Rectangle.BOTTOM);
					ClaveTela.setBorderColorBottom(borderTable);
					ClaveTela.setBorderWidthBottom(2);
					ClaveTela.setPaddingBottom(4f);
			    	
					totalConsumoTitulo.setBorder(0);
					totalConsumoTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
					totalConsumoTitulo.setBorder(Rectangle.BOTTOM);
					totalConsumoTitulo.setBorderColorBottom(borderTable);
					totalConsumoTitulo.setBorderWidthBottom(2);
					totalConsumoTitulo.setPaddingBottom(4f);
			
					totalConsumo.setBorder(0);
					totalConsumo.setHorizontalAlignment(Element.ALIGN_LEFT);
					totalConsumo.setBorder(Rectangle.BOTTOM);
					totalConsumo.setBorderColorBottom(borderTable);
					totalConsumo.setBorderWidthBottom(2);
					totalConsumo.setPaddingBottom(4f);
			    	
					surtirTitulo.setBorder(0);
					surtirTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
					surtirTitulo.setBorder(Rectangle.BOTTOM);
					surtirTitulo.setBorderColorBottom(borderTable);
					surtirTitulo.setBorderWidthBottom(2);
					surtirTitulo.setPaddingBottom(4f);
					
					surtir.setBorder(0);
					surtir.setHorizontalAlignment(Element.ALIGN_LEFT);
					surtir.setBorder(Rectangle.BOTTOM);
					surtir.setBorderColorBottom(borderTable);
					surtir.setBorderWidthBottom(2);
					surtir.setPaddingBottom(4f);
					
					spfTitulo.setBorder(0);
					spfTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
					spfTitulo.setBorder(Rectangle.BOTTOM);
					spfTitulo.setBorderColorBottom(borderTable);
					spfTitulo.setBorderWidthBottom(2);
					spfTitulo.setPaddingBottom(4f);
					
					spf.setBorder(0);
					spf.setHorizontalAlignment(Element.ALIGN_LEFT);
					spf.setBorder(Rectangle.BOTTOM);
					spf.setBorderColorBottom(borderTable);
					spf.setBorderWidthBottom(2);
					spf.setPaddingBottom(4f);
					cellVacia.setPaddingBottom(1f);
					ContenidoTelas.addCell(ClaveTelaTitulo);
					ContenidoTelas.addCell(ClaveTela);
					ContenidoTelas.addCell(totalConsumoTitulo);
					ContenidoTelas.addCell(totalConsumo);
					ContenidoTelas.addCell(surtirTitulo);
					ContenidoTelas.addCell(surtir);
					ContenidoTelas.addCell(spfTitulo);
					ContenidoTelas.addCell(spf);
			
					contenido = new PdfPCell(ContenidoTelas);
					contenido.setBorder(0);
					
					//Se agregan a una sola tabla para poder modular los registros y no se desface el diseño
					TelasconImagen.addCell(imagenTela); //Aquí va la imagen de la tela
					TelasconImagen.addCell(contenido);
					TelasconImagen.addCell(cellVacia);
					
					contenido = new PdfPCell(TelasconImagen);
					contenido.setBorder(0);
					contenido.setPaddingTop(30f);
					tablaInformacionTelas.addCell(contenido);
					
					contenido = new PdfPCell(tablaInformacionTelas);
					contenido.setBorder(0);
					bloqueInformacion.addCell(contenido);
					validadorCelda++;
					
					
					tablaInformacionTelas = new PdfPTable(1);
					tablaInformacionTelas.setWidthPercentage(100);
					
					//Se reinicia la suma para listar las prendas de la siguiente tela
					sumaPersonas = 0;
					totalConsumoCantidad = 0;
					spfConsumo = 0;
					}
					
					if(validador==2) {
						validador=-1;
					}
					if(validador==0) {
						validador=2;
					}
					
					//Verifica que existe un cambio de tela para poder contemplar ese registro
					if(!idTela.equals(fila[4].toString())){
						
						sumaPersonas += Float.parseFloat(fila[11].toString());
						totalConsumoCantidad += (Float.parseFloat(fila[11].toString())+Float.parseFloat(fila[15].toString()));
						spfConsumo += Float.parseFloat(fila[15].toString());
						
						imgUrl = fila[14].toString();
					}
					
					
					//-----------------------------------------------------//
				
					
				}
				//El id tela anterior pasa a ser el idTela actual (fila[4])
				codigoTela = fila[3].toString();
				idTela = fila[4].toString();
				contador++;
			}
			
			
			if ((validadorCelda%2)!=0) {
				System.out.println(validadorCelda%2);
				bloqueInformacion.addCell(cellVacia);
			}	
		}
		
		
		//----------------------------------------------------//
		//  Se agrega todo al documento
		
		document.add(espacio);
		document.add(tituloDocumento);
		document.add(SubtituloDefinitiva);
		document.add(bloqueInformacion);
		
		
		
	}
}
