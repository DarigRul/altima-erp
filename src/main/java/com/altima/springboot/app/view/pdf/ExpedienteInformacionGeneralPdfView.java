package com.altima.springboot.app.view.pdf;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("/imprimir-expediente-informacion-general")
public class ExpedienteInformacionGeneralPdfView extends AbstractPdfView {
	
	@Autowired
	HeaderFooterPdfView headerFooter;
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ComercialPedidoInformacion pedido = (ComercialPedidoInformacion) model.get("pedido");
		List<ComercialCliente> clientes = (List<ComercialCliente>) model.get("clientes");
		
		HeaderFooterPdfView valores = new HeaderFooterPdfView();
		valores.Asignar(" ", "No. Pedido: " + pedido.getIdText().toString());
		
		PdfPCell cell = null;
		//Se añade el Nombre de la empresa
		PdfPTable tabla3 = new PdfPTable(1);
		cell = new PdfPCell(new Phrase("Nombre de la empresa"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(2f);
		tabla3.addCell(cell);
		for(int c = 0; c < clientes.size(); c++) {
			if(clientes.get(c).getIdCliente().toString().equalsIgnoreCase(pedido.getIdEmpresa().toString())) {
				cell = new PdfPCell(new Phrase(clientes.get(c).getNombre().toString()));
			}
		}
		cell.setPadding(2f);
		tabla3.addCell(cell);
		tabla3.setWidths(new float[] { 12f });
		
		//Espacio en blanco xd
		PdfPTable espacio = new PdfPTable(1);
		cell = new PdfPCell(new Phrase(" "));
		cell.setBorder(0);
		espacio.addCell(cell);
		
		//Se añade la fecha de tallas y la fecha de anticipo
		PdfPTable tablaTomaTallasAnticipo = new PdfPTable(3);
		cell = new PdfPCell(new Phrase("Fecha de Toma de Tallas"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(2f);
		tablaTomaTallasAnticipo.addCell(cell);
		cell = new PdfPCell(new Phrase(" "));
		cell.setBorder(0);
		tablaTomaTallasAnticipo.addCell(cell);
		cell = new PdfPCell(new Phrase("Fecha de Anticipo"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(2f);
		tablaTomaTallasAnticipo.addCell(cell);
		cell = new PdfPCell(new Phrase(pedido.getFechaTomaTalla().toString()));
		cell.setPadding(2f);
		tablaTomaTallasAnticipo.addCell(cell);
		cell = new PdfPCell(new Phrase(" "));
		cell.setBorder(0);
		tablaTomaTallasAnticipo.addCell(cell);
		if(pedido.getFechaAnticipo() != null) {
			cell = new PdfPCell(new Phrase(pedido.getFechaAnticipo().toString()));
		}
		else {
			cell = new PdfPCell(new Phrase("Sin Fecha"));
		}
		cell.setPadding(2f);
		tablaTomaTallasAnticipo.addCell(cell);
		tablaTomaTallasAnticipo.setWidths(new float[] { 6f, 1f, 6f });
		
		
		//Se añade la tabla de Fecha de entrega y dias estimados
		PdfPTable tablaFechaEntregaDiasEstimados = new PdfPTable(3);
		cell = new PdfPCell(new Phrase("Fecha de Entrega"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(2f);
		tablaFechaEntregaDiasEstimados.addCell(cell);
		cell = new PdfPCell(new Phrase(" "));
		cell.setBorder(0);
		tablaFechaEntregaDiasEstimados.addCell(cell);
		cell = new PdfPCell(new Phrase("Dias estimados de Entrega"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(2f);
		tablaFechaEntregaDiasEstimados.addCell(cell);
		cell = new PdfPCell(new Phrase(pedido.getFechaEntrega().toString()));
		cell.setPadding(2f);
		tablaFechaEntregaDiasEstimados.addCell(cell);
		cell = new PdfPCell(new Phrase(" "));
		cell.setBorder(0);
		tablaFechaEntregaDiasEstimados.addCell(cell);
		cell = new PdfPCell(new Phrase(pedido.getDiaEstimados().toString()));
		cell.setPadding(2f);
		tablaFechaEntregaDiasEstimados.addCell(cell);
		tablaFechaEntregaDiasEstimados.setWidths(new float[] { 6f, 1f, 6f });
		
		//Observaciones
		PdfPTable Observaciones = new PdfPTable(1);
		cell = new PdfPCell(new Phrase("Observaciones"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(2f);
		Observaciones.addCell(cell);
		cell = new PdfPCell(new Phrase(pedido.getObservacion().toString()));
		cell.setPadding(20f);
		Observaciones.addCell(cell);
		Observaciones.setWidths(new float[] { 12f });
		
		writer.setPageEvent(new HeaderFooterPdfView());
		document.open();
		document.add(tabla3);
		document.add(espacio);
		document.add(tablaTomaTallasAnticipo);
		document.add(espacio);
		document.add(tablaFechaEntregaDiasEstimados);
		document.add(espacio);
		document.add(Observaciones);
		document.add(espacio);
		document.close();
	}

}
