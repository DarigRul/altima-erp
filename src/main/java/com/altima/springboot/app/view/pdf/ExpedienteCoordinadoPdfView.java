package com.altima.springboot.app.view.pdf;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("/imprimir-expediente-coordinado")
public class ExpedienteCoordinadoPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		PdfPCell cell = null;	 
		
		ArrayList coordinados = (ArrayList) model.get("coordinados");
		ComercialPedidoInformacion pedido = (ComercialPedidoInformacion) model.get("pedido");
		ComercialCliente cliente = (ComercialCliente) model.get("cliente");
		HeaderFooterPdfView valores = new HeaderFooterPdfView();
		valores.Asignar("Cliente: " + cliente.getNombre().toString(), "No. Pedido: " + pedido.getIdText().toString());
         
		PdfPTable tabla3 = new PdfPTable(2);

		cell = new PdfPCell(new Phrase("Nombre"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Total de Prendas"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);
		
		tabla3.setWidths(new float[] { 3f, 5f });
		
		
		for(int i = 0; i < coordinados.size(); i++) {
			Object[] aux = (Object[]) coordinados.get(i);
			tabla3.addCell(aux[1].toString());
			tabla3.addCell(aux[2].toString());
		}

		
		writer.setPageEvent(new HeaderFooterPdfView());
		document.open();
		document.add(tabla3);
		document.close();
	}

}
