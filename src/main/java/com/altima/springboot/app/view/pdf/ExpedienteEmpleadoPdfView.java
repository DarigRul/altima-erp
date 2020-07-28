package com.altima.springboot.app.view.pdf;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialClienteEmpleado;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("/imprimir-expediente-empleados")
public class ExpedienteEmpleadoPdfView extends AbstractPdfView {
	
	@Autowired
	HeaderFooterPdfView headerFooter;
	
	@SuppressWarnings("rawtypes") 
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PdfPCell cell = null;	 
		
		ArrayList empleados = (ArrayList) model.get("empleados");
		ComercialPedidoInformacion pedido = (ComercialPedidoInformacion) model.get("pedido");
		ComercialCliente cliente = (ComercialCliente) model.get("cliente");
		HeaderFooterPdfView valores = new HeaderFooterPdfView();
		valores.Asignar("Cliente: " + cliente.getNombre().toString(), "No. Pedido: " + pedido.getIdText().toString());
         
		PdfPTable tabla3 = new PdfPTable(5);
		//Se añade el cabezero de la tabla
		cell = new PdfPCell(new Phrase("Folio"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);

		cell = new PdfPCell(new Phrase("Nombre"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Sucursal"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Razón Social"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Creado Por"));
		cell.setBackgroundColor(new Color(255, 102, 102));
		cell.setPadding(3f);
		tabla3.addCell(cell);
		
		tabla3.setWidths(new float[] { 3f, 5f, 5f, 8f, 4f });
		
		
		for(int i = 0; i < empleados.size(); i++) {
			Object[] aux = (Object[]) empleados.get(i);
			tabla3.addCell(aux[0] + "EMP");
			tabla3.addCell(aux[1].toString());
			tabla3.addCell(aux[2].toString());
			tabla3.addCell(aux[3].toString());
			tabla3.addCell(aux[4].toString());
		}

		
		writer.setPageEvent(new HeaderFooterPdfView());
		document.open();
		document.add(tabla3);
		document.close();
	}

}
