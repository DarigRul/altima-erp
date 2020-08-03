package com.altima.springboot.app.view.pdf;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("/imprimir-expediente-concentrado-de-tallas")
public class ExpedienteConcentradoPorTallasPdfView extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ComercialPedidoInformacion pedido = (ComercialPedidoInformacion) model.get("pedido");
		@SuppressWarnings("unchecked")
		List<ComercialCliente> clientes = (List<ComercialCliente>) model.get("clientes");
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) model.get("head");
		@SuppressWarnings("unchecked")
		List<Object[]> prendastallas = (List<Object[]>) model.get("prendastallas");
		@SuppressWarnings("unchecked")
		List<Object[]> empleados = (List<Object[]>) model.get("empleados10");
		
		//Variables de Fuente y Color
		//Color fuerte
		Color fuerte = new Color(255, 102, 102);		
		//Color bajito jsjs
		Color bajito = new Color(255, 137, 137);		
		//Fuente
		BaseFont bf = BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED);
		BaseFont bf2 = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED);
		Font font = new Font(bf, 9);
		Font font2 = new Font(bf2, 10);
		//Valores del pedido
		HeaderFooterPdfView valores = new HeaderFooterPdfView();
		valores.Asignar(" ", "No. Pedido: " + pedido.getIdText().toString());
		writer.setPageEvent(new HeaderFooterPdfView());
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(1, 1, 20, 25);
		document.open();
		
		PdfPCell cell = null;
		//Se añade el Nombre de la empresa
		PdfPTable tabla3 = new PdfPTable(list.size());
		//Se añaden cabezeros
		for(int c = 0; c < list.size(); c++) {
			cell = new PdfPCell(new Phrase(list.get(c).toString()));
			cell.setBackgroundColor(new Color(255, 102, 102));
			cell.setPadding(2f);
			tabla3.addCell(cell);
		}
		
		//Se añade el cuerpo de la tabla 
		for(int pt = 0; pt < prendastallas.size(); pt++) {
			Object[] aux = (Object[]) prendastallas.get(pt);
			cell = new PdfPCell(new Phrase(aux[0].toString(), font2));
			cell.setPadding(2f);
			tabla3.addCell(cell);
			for(int c = 1; c < aux.length; c++) {
				if(aux[c] != null) {
					cell = new PdfPCell(new Phrase(aux[c].toString(), font2));
				}
				else {
					cell = new PdfPCell(new Phrase("Sin Especificar", font2));
				}
				cell.setPadding(4f);
				tabla3.addCell(cell);
			}
		}

		document.add(tabla3);
		document.close();
	}

}
