package com.altima.springboot.app.view.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.altima.springboot.app.dto.OrdenCompraDetalleDto;
import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialCotizacionTotal;
import com.altima.springboot.app.models.entity.ComprasOrden;
import com.altima.springboot.app.models.entity.ComprasProveedores;
import com.altima.springboot.app.models.entity.HrDireccion;
import com.altima.springboot.app.models.service.IEnviarCorreoService;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

@Component("/orden-compra-reporte")
public class OrdenCompraPdfView extends AbstractPdfView {

        @SuppressWarnings("static-access")
        @Override
        protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
                // TODO Auto-generated method stub
                /*
                 * 
                 * Variables del controller y de tiempo
                 * 
                 */
                document.setMargins(40f, 40f, 10f, 100f);

                @SuppressWarnings("unchecked")
                DateTime jodaTime = new DateTime();
                DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd HH-mm-ss-SSS");

                List<OrdenCompraDetalleDto> detalle = (List<OrdenCompraDetalleDto>) model.get("detalles");
                ComprasOrden cabecero = (ComprasOrden) model.get("cabecero");
                ComprasProveedores proveedor = (ComprasProveedores) model.get("proveedor");

                SimpleDateFormat formato = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
                String fecha = formato.format(new Date());

                /*
                 * 
                 * Colores y Fuente
                 * 
                 */
                Color fuerte = new Color(2, 136, 209);
                Color bajito = new Color(255, 137, 137);
                Color borderTable = new Color(205, 205, 205);
                Color colorDatos = new Color(170, 170, 170);
                Color TitulosBlancos = new Color(255, 255, 255);
                Color colorBorderBottom = new Color(255, 185, 24);
                Color borderGray = new Color(52, 58, 64);
                Color backgroundWhite = new Color(255, 255, 255);
                Color BackgroundTitle = new Color(90, 90, 90);
                Color textDarkGray = new Color(33, 37, 41);
                Font HelveticaBold = new Font(
                                BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 9);
                Font subtitulos = new Font(
                                BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 9, 0,
                                textDarkGray);
                Font Titulos = new Font(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED), 11,
                                0);
                Font TitulosOscuros = new Font(
                                BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 12, 0,
                                textDarkGray);
                Font datosGris = new Font(
                                BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 9, 0,
                                colorDatos);
                Font Helvetica = new Font(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED),
                                9);
                Font letraCondiciones = new Font(
                                BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED), 8);

                /*
                 * 
                 * Celda para espacios en blanco
                 * 
                 */
                PdfPTable espacio = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase(" "));
                cell.setBorder(0);
                espacio.addCell(cell);

                /**
                 * 
                 * 
                 * Cabezero del Documento
                 * 
                 * 
                 */

                PdfPTable tablaNotaria = new PdfPTable(2);
                tablaNotaria.setWidthPercentage(100);

                PdfPTable tableInfo = new PdfPTable(3);
                tableInfo.setWidthPercentage(100);
                Image logo = Image.getInstance("src/main/resources/static/dist/img/logo.png");
                logo.scalePercent(25f);
                PdfPCell logoImg = new PdfPCell(new Phrase(""));
                PdfPCell tituloDocumento = new PdfPCell();

                tituloDocumento = new PdfPCell(new Phrase(cabecero.getIdText(), TitulosOscuros));

                PdfPCell fechaCotizacion2 = new PdfPCell(
                                new Phrase(fecha.substring(0, 1).toUpperCase() + fecha.substring(1), Helvetica));
                logoImg.setPaddingBottom(10f);
                logoImg.setHorizontalAlignment(Element.ALIGN_LEFT);
                logoImg.setBorder(0);
                logoImg.setBorderWidthBottom(2f);
                logoImg.setBorderColor(borderGray);
                tituloDocumento.setHorizontalAlignment(Element.ALIGN_CENTER);
                tituloDocumento.setVerticalAlignment(Element.ALIGN_CENTER);
                tituloDocumento.setBackgroundColor(backgroundWhite);
                tituloDocumento.setBorder(0);
                tituloDocumento.setBorder(Rectangle.BOTTOM);
                tituloDocumento.setBorderWidthBottom(2f);
                tituloDocumento.setBorderColor(borderGray);
                tituloDocumento.setPaddingBottom(13f);
                fechaCotizacion2.setBorder(0);
                fechaCotizacion2.setBorderWidthBottom(2f);
                fechaCotizacion2.setPaddingBottom(13f);
                fechaCotizacion2.setBorderColor(borderGray);
                fechaCotizacion2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                fechaCotizacion2.setVerticalAlignment(Element.ALIGN_CENTER);

                fechaCotizacion2.setPaddingTop(4f);
                tableInfo.addCell(logoImg);
                tableInfo.addCell(tituloDocumento);
                tableInfo.addCell(fechaCotizacion2);

                PdfPTable tablaHeader1 = new PdfPTable(1);
                tablaHeader1.setWidthPercentage(100);
                tablaHeader1.setSpacingAfter(8f);
                PdfPCell celd = new PdfPCell(new Phrase("DATOS GENERALES DE LA EMPRESA", TitulosOscuros));
                // celd.setHorizontalAlignment(Element.ALIGN_LEFT);
                // celd.setVerticalAlignment(Element.ALIGN_CENTER);
                // celd.setBackgroundColor(backgroundWhite);
                // celd.setPaddingTop(-8f);
                // celd.setBorder(0);
                // tablaHeader1.addCell(celd);

                celd = new PdfPCell(new Phrase(proveedor.getNombreProveedor().toUpperCase(), TitulosOscuros));

                celd.setHorizontalAlignment(Element.ALIGN_CENTER);
                celd.setVerticalAlignment(Element.ALIGN_CENTER);
                celd.setBackgroundColor(backgroundWhite);
                celd.setBorder(0);
                celd.setPaddingBottom(-4f);
                tablaHeader1.addCell(celd);
                /*
                 * 
                 * Parte de la Direccion
                 * 
                 */

                PdfPTable tablaHeader2 = new PdfPTable(5);
                tablaHeader2.setWidthPercentage(100);
                PdfPCell celda;

                celda = new PdfPCell(new Phrase("Calle: ", Helvetica));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setBorderColorBottom(borderTable);
                celda.setBorderWidthBottom(2);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase(proveedor.getCalle(), datosGris));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setBorderColorBottom(borderTable);
                celda.setBorderWidthBottom(2);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase(" "));
                celda.setBorder(0);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase("Número interior", Helvetica));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setBorderColorBottom(borderTable);
                celda.setBorderWidthBottom(2);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase(proveedor.getNumeroInterior(), datosGris));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setBorderColorBottom(borderTable);
                celda.setBorderWidthBottom(2);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase("Colonia: ", Helvetica));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setBorderColorBottom(borderTable);
                celda.setBorderWidthBottom(2);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase(proveedor.getColonia(), datosGris));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setBorderColorBottom(borderTable);
                celda.setBorderWidthBottom(2);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase(" "));
                celda.setBorder(0);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase("CP:", Helvetica));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setBorderColorBottom(borderTable);
                celda.setBorderWidthBottom(2);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase(proveedor.getCodigoPostal(), datosGris));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setBorderColorBottom(borderTable);
                celda.setBorderWidthBottom(2);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase("Delegación: ", Helvetica));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setBorderColorBottom(borderTable);
                celda.setBorderWidthBottom(2);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase(proveedor.getMunicipio(), datosGris));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setBorderColorBottom(borderTable);
                celda.setBorderWidthBottom(2);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase(" "));
                celda.setBorder(0);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase("Estado", Helvetica));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setBorderColorBottom(borderTable);
                celda.setBorderWidthBottom(2);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase(proveedor.getEstado(), datosGris));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setBorderColorBottom(borderTable);
                celda.setBorderWidthBottom(2);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase("Correo: ", Helvetica));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setBorderColorBottom(borderTable);
                celda.setBorderWidthBottom(2);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase(proveedor.getCorreoProveedor(), datosGris));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setBorderColorBottom(borderTable);
                celda.setBorderWidthBottom(2);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase(" "));
                celda.setBorder(0);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase("Teléfono: ", Helvetica));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setBorderColorBottom(borderTable);
                celda.setBorderWidthBottom(2);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase(proveedor.getTelefonoProveedor(), datosGris));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setBorder(Rectangle.BOTTOM);
                celda.setBorderColorBottom(borderTable);
                celda.setBorderWidthBottom(2);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase(" "));
                celda.setBorder(0);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase(" ", Helvetica));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tablaHeader2.addCell(celda);
                celda = new PdfPCell(new Phrase(" "));
                celda.setBorder(0);
                celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tablaHeader2.addCell(celda);
                try {
                        tablaHeader2.setWidths(new float[] { 3f, 5f, 1f, 3f, 3f });
                } catch (DocumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }

                /**
                 * 
                 * 
                 * Cuerpo del documento
                 * 
                 * 
                 */
                PdfPTable tablaPrendas = null;
                // Si pasa aqui, es 1, es decir una cotizacion General
                tablaPrendas = new PdfPTable(7);
                tablaPrendas.setWidthPercentage(100);
                tablaPrendas.setWidths(new float[] { 3f, 3f, 4f, 3f, 3f, 3.5f, 3f });

                PdfPCell vacio = new PdfPCell(new Phrase(""));
                vacio.setBorder(0);
                vacio.setBorderWidthBottom(2f);
                vacio.setBorderColorBottom(borderGray);
                PdfPCell cantidad = new PdfPCell(new Phrase("Cantidad", HelveticaBold));
                PdfPCell claveMaterial = new PdfPCell(new Phrase("Clave Material", HelveticaBold));
                PdfPCell descripcion = new PdfPCell(new Phrase("Descripción", HelveticaBold));
                PdfPCell color = new PdfPCell(new Phrase("Color", HelveticaBold));
                PdfPCell precioUnitario = new PdfPCell(new Phrase("Precio Unitario", HelveticaBold));
                PdfPCell subtotal = new PdfPCell(new Phrase("Subtotal", HelveticaBold));
                cantidad.setBorderColor(borderGray);
                cantidad.setBorder(0);
                cantidad.setBorderWidthBottom(2f);
                cantidad.setPaddingBottom(8f);
                cantidad.setPaddingTop(6f);
                cantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
                cantidad.setVerticalAlignment(Element.ALIGN_CENTER);

                claveMaterial.setBorderColor(borderGray);
                claveMaterial.setBorder(0);
                claveMaterial.setBorderWidthBottom(2f);
                claveMaterial.setPaddingBottom(8f);
                claveMaterial.setPaddingTop(6f);
                claveMaterial.setHorizontalAlignment(Element.ALIGN_CENTER);
                claveMaterial.setVerticalAlignment(Element.ALIGN_CENTER);

                descripcion.setBorderColor(borderGray);
                descripcion.setBorder(0);
                descripcion.setBorderWidthBottom(2f);
                descripcion.setPaddingBottom(8f);
                descripcion.setPaddingTop(6f);
                descripcion.setHorizontalAlignment(Element.ALIGN_CENTER);
                descripcion.setVerticalAlignment(Element.ALIGN_CENTER);

                color.setBorderColor(borderGray);
                color.setBorder(0);
                color.setBorderWidthBottom(2f);
                color.setPaddingBottom(8f);
                color.setPaddingTop(6f);
                color.setHorizontalAlignment(Element.ALIGN_CENTER);
                color.setVerticalAlignment(Element.ALIGN_CENTER);

                precioUnitario.setBorderColor(borderGray);
                precioUnitario.setBorder(0);
                precioUnitario.setBorderWidthBottom(2f);
                precioUnitario.setPaddingBottom(8f);
                precioUnitario.setPaddingTop(6f);
                precioUnitario.setHorizontalAlignment(Element.ALIGN_CENTER);
                precioUnitario.setVerticalAlignment(Element.ALIGN_CENTER);

                subtotal.setBorderColor(borderGray);
                subtotal.setBorder(0);
                subtotal.setBorderWidthBottom(2f);
                subtotal.setPaddingBottom(8f);
                subtotal.setPaddingTop(6f);
                subtotal.setHorizontalAlignment(Element.ALIGN_CENTER);
                subtotal.setVerticalAlignment(Element.ALIGN_CENTER);

                tablaPrendas.addCell(cantidad);
                tablaPrendas.addCell(claveMaterial);
                tablaPrendas.addCell(descripcion);
                tablaPrendas.addCell(color);
                tablaPrendas.addCell(precioUnitario);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(subtotal);
                double subTotal = 0;
                double descuento = 0;
                double iva = 0;
                double total = 0;
                for (OrdenCompraDetalleDto ordenCompraDetalleDto : detalle) {
                        subTotal += ordenCompraDetalleDto.getPrecioUnitario() * ordenCompraDetalleDto.getCantidad();
                        descuento += ordenCompraDetalleDto.getMontoCargoDescuento();

                        PdfPCell cantidadV = new PdfPCell(
                                        new Phrase(String.valueOf(ordenCompraDetalleDto.getCantidad()), Helvetica));
                        PdfPCell claveMaterialV = new PdfPCell(
                                        new Phrase(ordenCompraDetalleDto.getIdText(), Helvetica));
                        PdfPCell descripcionV = new PdfPCell(
                                        new Phrase(ordenCompraDetalleDto.getNombre(), HelveticaBold));
                        PdfPCell colorV = new PdfPCell(new Phrase(ordenCompraDetalleDto.getColor(), Helvetica));
                        PdfPCell precioUnitarioV = new PdfPCell(new Phrase(
                                        String.valueOf(ordenCompraDetalleDto.getPrecioUnitario()), Helvetica));
                        PdfPCell subtotalV = new PdfPCell(new Phrase(
                                        String.format("%.2f",
                                                        ordenCompraDetalleDto.getCantidad()
                                                                        * ordenCompraDetalleDto.getPrecioUnitario()),
                                        HelveticaBold));

                        // nombrePrenda.setPadding(3f);
                        // precioPrenda.setPadding(3f);
                        vacio.setBorderColorBottom(borderTable);

                        cantidadV.setBorderColorBottom(borderTable);
                        cantidadV.setBorderWidthBottom(2);
                        cantidadV.setBorder(Rectangle.BOTTOM);
                        cantidadV.setPaddingBottom(12f);
                        cantidadV.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cantidadV.setVerticalAlignment(Element.ALIGN_CENTER);

                        claveMaterialV.setBorderColorBottom(borderTable);
                        claveMaterialV.setBorderWidthBottom(2);
                        claveMaterialV.setBorder(Rectangle.BOTTOM);
                        claveMaterialV.setPaddingBottom(12f);
                        claveMaterialV.setHorizontalAlignment(Element.ALIGN_CENTER);
                        claveMaterialV.setVerticalAlignment(Element.ALIGN_CENTER);

                        descripcionV.setBorderColorBottom(borderTable);
                        descripcionV.setBorderWidthBottom(2);
                        descripcionV.setBorder(Rectangle.BOTTOM);
                        descripcionV.setPaddingBottom(12f);
                        descripcionV.setHorizontalAlignment(Element.ALIGN_CENTER);
                        descripcionV.setVerticalAlignment(Element.ALIGN_CENTER);

                        colorV.setBorderColorBottom(borderTable);
                        colorV.setBorderWidthBottom(2);
                        colorV.setBorder(Rectangle.BOTTOM);
                        colorV.setPaddingBottom(12f);
                        colorV.setHorizontalAlignment(Element.ALIGN_CENTER);
                        colorV.setVerticalAlignment(Element.ALIGN_CENTER);

                        precioUnitarioV.setBorderColorBottom(borderTable);
                        precioUnitarioV.setBorderWidthBottom(2);
                        precioUnitarioV.setBorder(Rectangle.BOTTOM);
                        precioUnitarioV.setPaddingBottom(12f);
                        precioUnitarioV.setHorizontalAlignment(Element.ALIGN_CENTER);
                        precioUnitarioV.setVerticalAlignment(Element.ALIGN_CENTER);

                        subtotalV.setBorderColorBottom(borderTable);
                        subtotalV.setBorderWidthBottom(2);
                        subtotalV.setBorder(Rectangle.BOTTOM);
                        subtotalV.setPaddingBottom(12f);
                        subtotalV.setHorizontalAlignment(Element.ALIGN_CENTER);
                        subtotalV.setVerticalAlignment(Element.ALIGN_CENTER);

                        tablaPrendas.addCell(cantidadV);
                        tablaPrendas.addCell(claveMaterialV);
                        tablaPrendas.addCell(descripcionV);
                        tablaPrendas.addCell(colorV);
                        tablaPrendas.addCell(precioUnitarioV);
                        tablaPrendas.addCell(vacio);
                        tablaPrendas.addCell(subtotalV);

                }
                iva += (subTotal + descuento) * (cabecero.getIva()/100);
                total = subTotal + descuento + iva;
                PdfPCell subTotalTitulo = new PdfPCell(new Phrase("Subotal", HelveticaBold));
                PdfPCell subTotalNumero = new PdfPCell(new Phrase("" + String.format("%.2f", subTotal), HelveticaBold));

                PdfPCell descuentoTitulo = new PdfPCell(new Phrase("Descuento/cargo", HelveticaBold));
                PdfPCell descuentoNumero = new PdfPCell(new Phrase(String.format("%.2f", descuento), HelveticaBold));

                PdfPCell ivaTitulo = new PdfPCell(new Phrase("Iva", HelveticaBold));
                PdfPCell ivaNumero = new PdfPCell(new Phrase("" + String.format("%.2f", iva), HelveticaBold));

                PdfPCell totalTitulo = new PdfPCell(new Phrase("Total", HelveticaBold));
                PdfPCell totalNumero = new PdfPCell(new Phrase("" + String.format("%.2f", total), HelveticaBold));
                // totalTitulo.setPadding(3f);
                // totalNumero.setPadding(3f);

                vacio.setBorder(0);
                subTotalTitulo.setPaddingBottom(18f);
                subTotalTitulo.setBorder(0);
                subTotalTitulo.setBorder(Rectangle.BOTTOM);
                subTotalTitulo.setBorderColorBottom(borderGray);
                subTotalTitulo.setBorderWidthBottom(2);
                subTotalTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
                subTotalNumero.setBorder(Rectangle.BOTTOM);
                subTotalNumero.setBorderColorBottom(borderGray);
                subTotalNumero.setBorderWidthBottom(2);
                subTotalNumero.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(subTotalTitulo);
                tablaPrendas.addCell(subTotalNumero);

                vacio.setBorder(0);
                descuentoTitulo.setPaddingBottom(18f);
                descuentoTitulo.setBorder(0);
                descuentoTitulo.setBorder(Rectangle.BOTTOM);
                descuentoTitulo.setBorderColorBottom(borderGray);
                descuentoTitulo.setBorderWidthBottom(2);
                descuentoTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
                descuentoNumero.setBorder(Rectangle.BOTTOM);
                descuentoNumero.setBorderColorBottom(borderGray);
                descuentoNumero.setBorderWidthBottom(2);
                descuentoNumero.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(descuentoTitulo);
                tablaPrendas.addCell(descuentoNumero);

                vacio.setBorder(0);
                ivaTitulo.setPaddingBottom(18f);
                ivaTitulo.setBorder(0);
                ivaTitulo.setBorder(Rectangle.BOTTOM);
                ivaTitulo.setBorderColorBottom(borderGray);
                ivaTitulo.setBorderWidthBottom(2);
                ivaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
                ivaNumero.setBorder(Rectangle.BOTTOM);
                ivaNumero.setBorderColorBottom(borderGray);
                ivaNumero.setBorderWidthBottom(2);
                ivaNumero.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(ivaTitulo);
                tablaPrendas.addCell(ivaNumero);

                vacio.setBorder(0);
                totalTitulo.setPaddingBottom(18f);
                totalTitulo.setBorder(0);
                totalTitulo.setBorder(Rectangle.BOTTOM);
                totalTitulo.setBorderColorBottom(borderGray);
                totalTitulo.setBorderWidthBottom(2);
                totalTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
                totalNumero.setBorder(Rectangle.BOTTOM);
                totalNumero.setBorderColorBottom(borderGray);
                totalNumero.setBorderWidthBottom(2);
                totalNumero.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(vacio);
                tablaPrendas.addCell(totalTitulo);
                tablaPrendas.addCell(totalNumero);

                HeaderFooterCotizacionesPdfView event = new HeaderFooterCotizacionesPdfView();
                writer.setPageEvent(event);
                document.open();
                document.addTitle("Cotizacion1" + "_" + formatter.print(jodaTime));
                document.add(tablaNotaria);
                document.add(espacio);
                document.add(tableInfo);
                document.add(espacio);
                document.add(tablaHeader1);
                document.add(tablaHeader2);
                document.add(espacio);
                document.add(espacio);
                document.add(tablaPrendas);
                document.add(espacio);
                document.close();

        }
}
