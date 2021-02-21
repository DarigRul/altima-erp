package com.altima.springboot.app.view.pdf;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("/maquilacontrolpedidostickets")
public class MaquilaControlPedidosGenerarTicketsPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        JSONArray rolloArray = (JSONArray) model.get("rolloArray");

        try {

            document.open();
            PdfContentByte cb = writer.getDirectContent();
            document.addTitle("Codigos de Barras");
            PdfPTable tablaNotaria = new PdfPTable(2);
            tablaNotaria.setWidthPercentage(100);
            PdfPCell numeroCotizacion = new PdfPCell(new Phrase("Bar Code"));
            PdfPCell lugarCotizacion = new PdfPCell(new Phrase(""));
            numeroCotizacion.setBorder(0);
            lugarCotizacion.setBorder(0);
            numeroCotizacion.setHorizontalAlignment(Element.ALIGN_RIGHT);
            lugarCotizacion.setHorizontalAlignment(Element.ALIGN_LEFT);
            tablaNotaria.addCell(lugarCotizacion);
            tablaNotaria.addCell(numeroCotizacion);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.getDefaultCell().setFixedHeight(70);
            for (int i = 0; i < rolloArray.length(); i++) {
                table.addCell(new Phrase(new Chunk(CreateBarCode(rolloArray.getJSONObject(i).optString("idText"), cb), 0, 0)));
            }
            table.addCell(" ");
            table.addCell(" ");
            document.add(tablaNotaria);
            document.add(table);

        } catch (Exception de) {
            de.printStackTrace();
        }
        document.close();
        // response.setContentType("application/pdf");
        // response.setHeader("content-disposition", "attachment; filename=rolloTelaBarCode.pdf");
        // // response.setHeader("Content-Length","3495");
        // OutputStream os = response.getOutputStream();
        // baos.writeTo(os);
        // os.flush();
        // os.close();

    }

    private static Image CreateBarCode(String title, PdfContentByte cb) {
        Barcode128 code128 = new Barcode128();
        code128.setCode(title);
        Image image128 = code128.createImageWithBarcode(cb, null, null);
        return image128;
    }

}
