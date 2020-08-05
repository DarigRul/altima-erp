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
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("/imprimir-expediente-totales-por-razon-social")
public class ExpedienteTotalesPorRazonSocialPdfView extends AbstractPdfView{
	
	@Autowired
	HeaderFooterPdfView headerFooter;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ComercialPedidoInformacion pedido = (ComercialPedidoInformacion) model.get("pedido");
		HeaderFooterPdfView valores = new HeaderFooterPdfView();
		valores.Asignar(" ", "No. Pedido: " + pedido.getIdText().toString());
		PdfPCell cell = null;
		Color fuerte = new Color(255, 102, 102);
		Color bajito = new Color(255, 137, 137);
		//Fuente
		BaseFont bf = BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED);
		BaseFont bf2 = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED);
		Font font = new Font(bf, 9);
		Font font2 = new Font(bf2, 9);
		float Padd = 4.5f;
		PdfPTable tabla3 = new PdfPTable(11);
		
		@SuppressWarnings("unchecked")
		List<Object []> lista = (List<Object []>) model.get("lisTotal");
		
		cell = new PdfPCell(new Phrase("Razon Social", font));
		cell.setBackgroundColor(fuerte);
		cell.setPadding(Padd);
		tabla3.addCell(cell);
		cell = new PdfPCell(new Phrase("Nombre Razon Social", font));
		cell.setBackgroundColor(fuerte);
		cell.setPadding(Padd);
		tabla3.addCell(cell);
		cell = new PdfPCell(new Phrase("No. Prendas", font));
		cell.setBackgroundColor(fuerte);
		cell.setPadding(Padd);
		tabla3.addCell(cell);
		cell = new PdfPCell(new Phrase("Subtotal 1", font));
		cell.setBackgroundColor(fuerte);
		cell.setPadding(Padd);
		tabla3.addCell(cell);
		cell = new PdfPCell(new Phrase("Descuento/Cargo", font));
		cell.setBackgroundColor(fuerte);
		cell.setPadding(Padd);
		tabla3.addCell(cell);
		cell = new PdfPCell(new Phrase("Subtotal 2", font));
		cell.setBackgroundColor(fuerte);
		cell.setPadding(Padd);
		tabla3.addCell(cell);
		cell = new PdfPCell(new Phrase("IVA", font));
		cell.setBackgroundColor(fuerte);
		cell.setPadding(Padd);
		tabla3.addCell(cell);
		cell = new PdfPCell(new Phrase("Total Pedido", font));
		cell.setBackgroundColor(fuerte);
		cell.setPadding(Padd);
		tabla3.addCell(cell);
		cell = new PdfPCell(new Phrase("Anticipo", font));
		cell.setBackgroundColor(fuerte);
		cell.setPadding(Padd);
		tabla3.addCell(cell);
		cell = new PdfPCell(new Phrase("Entrega", font));
		cell.setBackgroundColor(fuerte);
		cell.setPadding(Padd);
		tabla3.addCell(cell);
		cell = new PdfPCell(new Phrase("Saldo", font));
		cell.setBackgroundColor(fuerte);
		cell.setPadding(Padd);
		tabla3.addCell(cell);
		
		for(int con = 0; con < lista.size(); con++) {
			cell = new PdfPCell(new Phrase(lista.get(con)[0].toString(), font));
			cell.setPadding(Padd);
			tabla3.addCell(cell);
		}
		
		tabla3.setWidths(new float[] { 3.5f, 5.5f, 3.5f, 3f, 4.5f, 3f, 2f, 3.5f, 2.5f, 2.5f, 2.5f });
		document.setPageSize(PageSize.A4.rotate());
	    document.setMargins(0, 0, 20, 25);
		writer.setPageEvent(new HeaderFooterPdfView());
		document.open();
		document.add(tabla3);
		document.close();
	}

}
