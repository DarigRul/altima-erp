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
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("/imprimir-expediente-detalle-precios")
public class ExpedienteDetallePrecioPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		PdfPCell cell = null;	 
		
		@SuppressWarnings("rawtypes")
		ArrayList precios = (ArrayList) model.get("listCoor");
		ComercialPedidoInformacion pedido = (ComercialPedidoInformacion) model.get("pedido");
		ComercialCliente cliente = (ComercialCliente) model.get("cliente");
		HeaderFooterPdfView valores = new HeaderFooterPdfView();
		valores.Asignar("Cliente: " + cliente.getNombre().toString(), "No. Pedido: " + pedido.getIdText().toString());
         
		//Se declara la tabla y los cabezeros
		PdfPTable tabla3 = new PdfPTable(11);
		cell = new PdfPCell(new Phrase("Coordinado"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);

		cell = new PdfPCell(new Phrase("Prenda"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Modelo"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Tela"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Composición"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Color"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);

		cell = new PdfPCell(new Phrase("Precio Bor."));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Precio"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Adicional (%)"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Monto Adicional"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Precio Final"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);
		
		tabla3.setWidths(new float[] { 5f, 5f, 5f, 5f, 6.5f, 3.5f, 5f, 4f, 6f, 5f, 5f });
		tabla3.setWidthPercentage(100f);
		
		
		for(int i = 0; i < precios.size(); i++) {
			Object[] aux = (Object[]) precios.get(i);
			tabla3.addCell(aux[1].toString());
			tabla3.addCell(aux[2].toString());
			tabla3.addCell(aux[3].toString());
			tabla3.addCell(aux[4].toString());//tela
			tabla3.addCell(aux[5].toString());
			tabla3.addCell(aux[6].toString());
			tabla3.addCell(aux[7].toString());
			tabla3.addCell(aux[8].toString());
			tabla3.addCell(aux[9].toString());
			tabla3.addCell(aux[10].toString());
			tabla3.addCell(aux[11].toString());
		}

		
		writer.setPageEvent(new HeaderFooterPdfView());
		document.setPageSize(PageSize.A4.rotate());
		document.open();
		document.add(tabla3);
		document.close();
		
	}

}
