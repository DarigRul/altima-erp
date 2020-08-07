package com.altima.springboot.app.view.pdf;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("/imprimir-cotizacion")
public class CotizacionesGenerarCotizacionPdfView extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		//Objeto del controller
		@SuppressWarnings("unchecked")
		List<Object[]> prendas = (List<Object[]>) model.get("ListaCotizacionPrendas");
		String tipoCotizacion = (String) model.get("tipo");
		boolean totales = (boolean) model.get("totales");
		boolean cv = (boolean) model.get("cv");
		
		//Colores y Fuente
		Color fuerte = new Color(255, 102, 102);
		Color bajito = new Color(255, 137, 137);
		Font HelveticaBold = new Font(BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 9);
		Font Helvetica = new Font(BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED), 9);
		
		
		//Variables iniciadas
		writer.setPageEvent(new HeaderFooterCotizacionesPdfView());
		document.open();
		
		//Cuerpo del documento
		//Si pasa aqui, es 1, es decir una cotizacion General
		if(tipoCotizacion.equalsIgnoreCase("1")) {
			PdfPTable tablaPrendas = new PdfPTable(2);
			tablaPrendas.setWidths(new float[] { 10f, 5f });
			PdfPCell nombrePrendaCabezero = new PdfPCell(new Phrase("Descripción", HelveticaBold));
			PdfPCell precioPrendaCabezero = new PdfPCell(new Phrase("Precio", HelveticaBold));
			nombrePrendaCabezero.setBackgroundColor(fuerte);
			precioPrendaCabezero.setBackgroundColor(fuerte);
			nombrePrendaCabezero.setHorizontalAlignment(Element.ALIGN_CENTER);
			precioPrendaCabezero.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaPrendas.addCell(nombrePrendaCabezero);
			tablaPrendas.addCell(precioPrendaCabezero);
			for(int con = 0; con < prendas.size(); con++) {
				Object[] aux = (Object[]) prendas.get(con);
				PdfPCell nombrePrenda = new PdfPCell(new Phrase(aux[4].toString(), Helvetica));
				PdfPCell precioPrenda = new PdfPCell(new Phrase(aux[19].toString(), HelveticaBold));
				nombrePrenda.setPadding(3f);
				precioPrenda.setPadding(3f);
				tablaPrendas.addCell(nombrePrenda);
				tablaPrendas.addCell(precioPrenda);
			}
			
			//Si tiene lo de los totales, se le anexa
			if(totales) {
				float Total = 0;
				for(int con = 0; con < prendas.size(); con++) {
					Object[] aux = (Object[]) prendas.get(con);
					Total += Float.valueOf(aux[19].toString());
				}
				PdfPCell totalTitulo = new PdfPCell(new Phrase("Total", HelveticaBold));
				PdfPCell totalNumero = new PdfPCell(new Phrase(" " + Total, HelveticaBold));
				totalTitulo.setPadding(3f);
				totalNumero.setPadding(3f);
				tablaPrendas.addCell(totalTitulo);
				tablaPrendas.addCell(totalNumero);
				
			}
			
			document.add(tablaPrendas);
		}
		//Si pasa aqui es porque es una desglosada xd
		else {
			//Se declara la tabla
			PdfPTable tablaPrendas = new PdfPTable(5);
			tablaPrendas.setWidths(new float[] { 3f, 5f, 3f, 3f, 3f });
			PdfPCell Cabezero1 = new PdfPCell(new Phrase("No. Coordinado", HelveticaBold));
			PdfPCell Cabezero2 = new PdfPCell(new Phrase("Descripción", HelveticaBold));
			PdfPCell Cabezero3 = new PdfPCell(new Phrase("Cantidad", HelveticaBold));
			PdfPCell Cabezero4 = new PdfPCell(new Phrase("Pre. Unitario", HelveticaBold));
			PdfPCell Cabezero5 = new PdfPCell(new Phrase("Total", HelveticaBold));
			Cabezero1.setBackgroundColor(fuerte);
			Cabezero2.setBackgroundColor(fuerte);
			Cabezero3.setBackgroundColor(fuerte);
			Cabezero4.setBackgroundColor(fuerte);
			Cabezero5.setBackgroundColor(fuerte);
			tablaPrendas.addCell(Cabezero1);
			tablaPrendas.addCell(Cabezero2);
			tablaPrendas.addCell(Cabezero3);
			tablaPrendas.addCell(Cabezero4);
			tablaPrendas.addCell(Cabezero5);
			
			for(int con = 0; con < prendas.size(); con++) {
				Object[] aux = (Object[]) prendas.get(con);
				PdfPCell cuerpo1 = new PdfPCell(new Phrase(aux[9].toString(), Helvetica));
				PdfPCell cuerpo2 = new PdfPCell(new Phrase(aux[4].toString() + "-" + aux[7].toString(), Helvetica));
				PdfPCell cuerpo3 = new PdfPCell(new Phrase(aux[10].toString(), Helvetica));
				PdfPCell cuerpo4 = new PdfPCell(new Phrase(aux[18].toString(), Helvetica));
				PdfPCell cuerpo5 = new PdfPCell(new Phrase(aux[19].toString(), HelveticaBold));
				cuerpo1.setPadding(3f);
				cuerpo2.setPadding(3f);
				cuerpo3.setPadding(3f);
				cuerpo4.setPadding(3f);
				cuerpo5.setPadding(3f);
				tablaPrendas.addCell(cuerpo1);
				tablaPrendas.addCell(cuerpo2);
				tablaPrendas.addCell(cuerpo3);
				tablaPrendas.addCell(cuerpo4);
				tablaPrendas.addCell(cuerpo5);
			}
			
			document.add(tablaPrendas);
		}
		
		
				//Condiciones
				//Espacio en blanco xd
				PdfPTable espacio = new PdfPTable(1);
				PdfPCell cell = new PdfPCell(new Phrase(" "));
				cell.setBorder(0);
				espacio.addCell(cell);
				
		    	//Primera tabla de numero, nombre y fecha
		    	PdfPTable tablaFooter1 = new PdfPTable(1);
		    	PdfPCell leyenda1 = new PdfPCell(new Phrase("Condiciones: ", HelveticaBold));
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
		    	//Espacios para firmar
		    	PdfPCell espacioFirma1 = new PdfPCell(new Phrase("____________________"));
		    	PdfPCell espacioFirma2 = new PdfPCell(new Phrase("____________________"));
		    	espacioFirma1.setBorder(0);
		    	espacioFirma2.setBorder(0);
		    	espacioFirma1.setPadding(3f);
		    	espacioFirma2.setPadding(3f);
		    	espacioFirma1.setHorizontalAlignment(Element.ALIGN_LEFT);
		    	espacioFirma2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		    	tablaFooter2.addCell(espacioFirma1);
		    	tablaFooter2.addCell(espacioBlanco3);
		    	tablaFooter2.addCell(espacioFirma2);
		    	//Nombres de las firmas
		    	PdfPCell nombreFirma1 = new PdfPCell(new Phrase("              Diana Rodriguez", Helvetica));
		    	PdfPCell nombreFirma2 = new PdfPCell(new Phrase("Jorge Armando Cottone Morales", Helvetica));
		    	nombreFirma1.setBorder(0);
		    	nombreFirma2.setBorder(0);
		    	tablaFooter2.addCell(nombreFirma1);
		    	tablaFooter2.addCell(espacioBlanco3);
		    	tablaFooter2.addCell(nombreFirma2);
		    	//Puestos firmas
		    	PdfPCell puestosFirma1 = new PdfPCell(new Phrase("            Ejecutivo de Ventas", Helvetica));
		    	PdfPCell puestosFirma2 = new PdfPCell(new Phrase("           Gerente Comercial", Helvetica));
		    	puestosFirma1.setBorder(0);	 
		    	puestosFirma2.setBorder(0);
		    	tablaFooter2.addCell(puestosFirma1);
		    	tablaFooter2.addCell(espacioBlanco3);
		    	tablaFooter2.addCell(puestosFirma2);
		    	tablaFooter2.setWidths(new float[] { 5f, 5f, 5f });
		    	
		
		
		
		document.add(espacio);
		document.add(tablaFooter1);
		document.add(espacio);
		document.add(tablaFooter2);
		document.add(espacio);
		//Aqui se hace el merge del CV si es que existe
		if(cv) {
			//
		}
		document.close();
	}

}
