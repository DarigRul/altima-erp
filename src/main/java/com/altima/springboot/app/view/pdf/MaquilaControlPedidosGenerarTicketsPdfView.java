package com.altima.springboot.app.view.pdf;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.altima.springboot.app.models.entity.MaquilaAsignacionTickets;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("/maquilacontrolpedidostickets")
public class MaquilaControlPedidosGenerarTicketsPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);
		@SuppressWarnings("unchecked")
		List<MaquilaAsignacionTickets> tickets =  (List<MaquilaAsignacionTickets>) model.get("tickets");

		try {

			document.open();
			PdfContentByte cb = writer.getDirectContent();
			document.addTitle("Tickets control de pedidos");
			
			for (MaquilaAsignacionTickets maquilaAsignacionTickets : tickets) {

				PdfPTable table = new PdfPTable(1);

				table.setWidthPercentage(50);
				table.getDefaultCell().setBorder(Rectangle.BOX);

				PdfPTable table2 = new PdfPTable(2);
				table2.getDefaultCell().setBorder(Rectangle.NO_BORDER);

				table2.addCell("Pedido:");
				table2.addCell(maquilaAsignacionTickets.getPedido());
				table2.addCell("Bulto número:");
				table2.addCell(maquilaAsignacionTickets.getBulto());
				table2.addCell("Cantidad:");
				table2.addCell(maquilaAsignacionTickets.getCantidadPredaBulto());
				table2.addCell("Modelo:");
				table2.addCell(maquilaAsignacionTickets.getModelo());
				table2.addCell("Operación:");
				table2.addCell(maquilaAsignacionTickets.getOperacion());
				table2.addCell("Tiempo estimado:");
				table2.addCell(maquilaAsignacionTickets.getTiempoEstimado());
				table2.addCell("");
				table2.addCell(new Phrase(new Chunk(CreateBarCode((maquilaAsignacionTickets.getTicket()), cb), 0, 0)));

				table.addCell(table2);

				document.add(table);
			}

			

		} catch (Exception de) {
			de.printStackTrace();
		}
		document.close();

	}

	private static Image CreateBarCode(String title, PdfContentByte cb) {
		Barcode128 code128 = new Barcode128();
		code128.setCode(title);
		Image image128 = code128.createImageWithBarcode(cb, null, null);
		return image128;
	}

}
