package com.altima.springboot.app.view.pdf;

import java.awt.Color;
import java.util.List;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.altima.springboot.app.models.entity.DisenioLookup;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("/imprimir")
public class LookupPdfView extends AbstractPdfView {
/* IMPORTANTE ESTE METODO SIRVE PARA TODOS LOS CATALOGOS SOLO AGREGAR EN EL CONTROLADOR CATALOGO CONTROLLER MAS CONDICIONES EN EL METODO VER */
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		@SuppressWarnings("unchecked")
		List<DisenioLookup> lookup = (List<DisenioLookup>) model.get("lookup");

		PdfPCell cell = null;

		PdfPTable tabla3 = new PdfPTable(4);
		cell = new PdfPCell(new Phrase("Clave"));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);
		
		tabla3.addCell(cell);
		cell = new PdfPCell(new Phrase("Nombre"));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);
		
		tabla3.addCell(cell);
		cell = new PdfPCell(new Phrase("Descripción"));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);
		
		tabla3.addCell(cell);
		cell = new PdfPCell(new Phrase("Creado por"));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);
		
		tabla3.addCell(cell);
		tabla3.setWidths(new float[] { 1, 1, 3.5f, 1 });
		
		

		for (DisenioLookup lookup1 : lookup) {
			tabla3.addCell(lookup1.getIdText());
			tabla3.addCell(lookup1.getNombreLookup());
			tabla3.addCell(lookup1.getDescripcionLookup());
			tabla3.addCell(lookup1.getCreadoPor());
			////////////////agregar mas

		}


		document.add(tabla3);

	}

}
