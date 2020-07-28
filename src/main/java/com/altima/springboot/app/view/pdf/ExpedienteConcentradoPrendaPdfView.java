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
import com.altima.springboot.app.models.entity.ComercialCoordinado;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.service.IComercialConcentradoPrendasService;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("/imprimir-expediente-concentrado-prenda")
public class ExpedienteConcentradoPrendaPdfView extends AbstractPdfView{
	
	@Autowired
	private IComercialConcentradoPrendasService concentradoPrendasService;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		PdfPCell cell = null;
		PdfPTable tabla3 = null;
		
		//Color fuerte
		Color fuerte = new Color(255, 102, 102);
		
		//Color bajito jsjs
		Color bajito = new Color(255, 137, 137);
		
		//Fuente
		BaseFont bf = BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED);
		BaseFont bf2 = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED);
        Font font = new Font(bf, 9);
        Font font2 = new Font(bf2, 9);
		
		//Se inicia la tabla
		writer.setPageEvent(new HeaderFooterPdfView());
		HeaderFooterPdfView valores = new HeaderFooterPdfView();
		@SuppressWarnings("unchecked")
		List<Object> empleados = (List<Object>) model.get("empleados");
		ComercialCliente cliente = (ComercialCliente) model.get("cliente");
		ComercialPedidoInformacion pedido = (ComercialPedidoInformacion) model.get("pedido");
		valores.Asignar("Cliente: " + cliente.getNombre().toString(), "No. Pedido: " + pedido.getIdText().toString());
		document.setPageSize(PageSize.A4.rotate());
	    document.setMargins(20, 20, 20, 25);
		document.open();
		
		ArrayList coordinados = (ArrayList) model.get("coordinados");
		
		//Este es el JSON para guardar la relacion MODELO/PRENDA - POSICION;
		JSONArray modeloPosicionArray = new JSONArray();
		JSONObject modeloPosicion = new JSONObject();
		
		
		for(int i = 0; i < coordinados.size(); i++) {
			//Se iguala el objeto de la lista a una entidad para usarla.
			ComercialCoordinado coordinado = (ComercialCoordinado) coordinados.get(i);
			//Se consultan las prendas de ese coordinado en esa vuelta en especifico.
			List<Object> prendasDeCoordinado = (List<Object>)concentradoPrendasService.findMaterialPrendaTelafromCoordinado(coordinado.getIdCoordinado());
			
			//Se declara la tabla con el numero de filas en esta vuelta;
			tabla3 = new PdfPTable((prendasDeCoordinado.size() + 1));
			
			//Se pinta el nombre de la fila, en este caso numero de coordiando
			cell = new PdfPCell(new Phrase("Coordinado", font));
			cell.setBackgroundColor(fuerte);
			cell.setPadding(3f);
			tabla3.addCell(cell);
			
				//Se hace otro ciclo para pintar el numero de el coordinado en turno
				for(int j = 0; j < prendasDeCoordinado.size(); j++) {
					Object[] aux = (Object[]) prendasDeCoordinado.get(j);
					cell = new PdfPCell(new Phrase(aux[3].toString(), font2));
					cell.setBackgroundColor(bajito);
					cell.setPadding(3f);
					tabla3.addCell(cell);
				}
				
			//Se pinta el nombre de la fila, en este caso el modelo
			cell = new PdfPCell(new Phrase("Modelo", font));
			cell.setBackgroundColor(fuerte);
			cell.setPadding(3f);
			tabla3.addCell(cell);
			
			
				//Se hace otro ciclo para pintar los modelos de turno
				for(int j = 0; j < prendasDeCoordinado.size(); j++) {
					Object[] aux = (Object[]) prendasDeCoordinado.get(j);
					cell = new PdfPCell(new Phrase(aux[1].toString(), font2));
					cell.setBackgroundColor(bajito);
					cell.setPadding(3f);
					tabla3.addCell(cell);
					
					//Aqui se guarda la posicion en la que se pinto el modelo
					modeloPosicion.put("identificador", aux[0].toString());
					modeloPosicion.put("posicion", j);
					modeloPosicionArray.put(modeloPosicion);
					modeloPosicion = new JSONObject();
				}
				
				
			//Se pinta el nombre de la fila, en este caso nombre de la tela
			cell = new PdfPCell(new Phrase("Tela", font));
			cell.setBackgroundColor(fuerte);
			cell.setPadding(3f);
			tabla3.addCell(cell);
				
				//Se hace otro ciclo para pintar las telas de turno
				for(int j = 0; j < prendasDeCoordinado.size(); j++) {
					Object[] aux = (Object[]) prendasDeCoordinado.get(j);
					cell = new PdfPCell(new Phrase(aux[2].toString(), font2));
					cell.setBackgroundColor(bajito);
					cell.setPadding(3f);
					tabla3.addCell(cell);
				}
				
			//Se pinta el nombre de la fila, en este caso nombre de la prenda
			cell = new PdfPCell(new Phrase("Prenda", font));
			cell.setBackgroundColor(fuerte);
			cell.setPadding(3f);
			tabla3.addCell(cell);
					
					//Se hace otro ciclo para pintar las prendas de turno
					for(int j = 0; j < prendasDeCoordinado.size(); j++) {
						Object[] aux = (Object[]) prendasDeCoordinado.get(j);
						cell = new PdfPCell(new Phrase(aux[4].toString(), font2));
						cell.setBackgroundColor(bajito);
						cell.setPadding(3f);
						tabla3.addCell(cell);
					}
					
					
			//Se pinta el nombre de la fila, en este caso el color
			cell = new PdfPCell(new Phrase("Color", font));
			cell.setBackgroundColor(fuerte);
			cell.setPadding(3f);
			tabla3.addCell(cell);
							
						//Se hace otro ciclo para pintar los colores de turno
						for(int j = 0; j < prendasDeCoordinado.size(); j++) {
							Object[] aux = (Object[]) prendasDeCoordinado.get(j);
							cell = new PdfPCell(new Phrase(aux[5].toString(), font2));
							cell.setBackgroundColor(bajito);
							cell.setPadding(3f);
							tabla3.addCell(cell);
						}
			//Esto es para pintar el cabezero
			tabla3.setWidthPercentage(100f);
			document.add(tabla3);
						
			/**
			 * 
			 * 
			 * 
			 * Cabezero de la tabla empleado
			 * 
			 * 
			 * 
			 */
			//Se declaran las columnas de la tabla.
			tabla3 = new PdfPTable(((prendasDeCoordinado.size() * 2) + 1));
			//Se pinta el cabezero
			cell = new PdfPCell(new Phrase("Empleado", font2));
			cell.setBackgroundColor(fuerte);
			cell.setPadding(3f);
			tabla3.addCell(cell);
			//Este array es para las medidas
			float[] medidas = new float[((modeloPosicionArray.length() * 2) + 1)];
			float anchura = (100 / ((prendasDeCoordinado.size() * 2) + 1) * 2);
			medidas[0] = anchura;
			//Este ciclo construye el ancho de las columnas de empleado
			for(int p = 1; p <= (modeloPosicionArray.length() * 2); p++) {
				medidas[p] = (anchura / 2);
			}
			//Se pinta cantidad y especial de cabezero
			for(int p = 0; p < modeloPosicionArray.length(); p++) {
				cell = new PdfPCell(new Phrase("Cantidad", font2));
				cell.setBackgroundColor(fuerte);
				cell.setPadding(3f);
				tabla3.addCell(cell);
				cell = new PdfPCell(new Phrase("Especial", font2));
				cell.setBackgroundColor(fuerte);
				cell.setPadding(3f);
				tabla3.addCell(cell);
			}
			
			/**
			 * 
			 * 
			 * 
			 * 
			 * 
			 * Cuerpo de la tabla de empleado
			 * 
			 * 
			 * 
			 * 
			 * 
			 */
			//Este apartado es para acomodar los empleados con sus prendas.
			//Este primer ciclo es para ponerle un 0 a todos los empleados en sus cantidades
			JSONArray EmpleadoYCantidadesArray = new JSONArray();
			for(int con1 = 0; con1 < empleados.size(); con1++) {
				
				//Esto es para acceder a las propiedades del objeto de empleado del ciclo
				Object[] aux = (Object[]) empleados.get(con1);
				//Este es el objeto que se llenara si es que es nuevo el empleado dentro del ciclo
				JSONObject EmpleadoYCantidadesAcomodadas = new JSONObject();

				//Ciclo para saber si ya se habia guardado antes el empleado y sus cantidades
				JSONObject EmpleadoYCantidadesAcomodadasPosible = new JSONObject();
				for(int con2 = 0; con2 < EmpleadoYCantidadesArray.length(); con2++) {
					JSONObject empleadoCantidadesTemporal =  EmpleadoYCantidadesArray.getJSONObject(con2);
					if(empleadoCantidadesTemporal.get("id").toString().equalsIgnoreCase(aux[0].toString())) {
						EmpleadoYCantidadesAcomodadasPosible = EmpleadoYCantidadesArray.getJSONObject(con2);
					}
				}
				
				//Si el EmpleadoYCantidadesAcomodadasPosible esta vacio, es porque no se habia visto ese empleado, tons se pinta por 1ra vez
				if(EmpleadoYCantidadesAcomodadasPosible.isEmpty()) {
					EmpleadoYCantidadesAcomodadas.put("id", aux[0].toString());
					EmpleadoYCantidadesAcomodadas.put("nombre", aux[1].toString());
						
						//En este ciclo se agregan las cantidades de las prendas
						for(int con3 = 0; con3 < modeloPosicionArray.length(); con3++) {
							JSONObject modeloPosicionAuxiliar = modeloPosicionArray.getJSONObject(con3);
							EmpleadoYCantidadesAcomodadas.put("modelo-" + con3, modeloPosicionAuxiliar.get("identificador"));
							EmpleadoYCantidadesAcomodadas.put("cantidad-modelo-" + modeloPosicionAuxiliar.get("identificador"), 0);
							EmpleadoYCantidadesAcomodadas.put("especial-modelo-" + modeloPosicionAuxiliar.get("identificador"), 0);
						}
					
					EmpleadoYCantidadesArray.put(EmpleadoYCantidadesAcomodadas);
				}
				else {
					//Si llega aqui, es que esta lleno, o sea ya existe, tons solo se le agregan las cantidades nuevas
					//En este ciclo se agregan las cantidades de las prendas
					for(int con3 = 0; con3 < modeloPosicionArray.length(); con3++) {
						JSONObject modeloPosicionAuxiliar = modeloPosicionArray.getJSONObject(con3);
						EmpleadoYCantidadesAcomodadas.put("modelo-" + con3, modeloPosicionAuxiliar.get("identificador"));
						EmpleadoYCantidadesAcomodadas.put("cantidad-modelo-" + modeloPosicionAuxiliar.get("identificador"), 0);
						EmpleadoYCantidadesAcomodadas.put("especial-modelo-" + modeloPosicionAuxiliar.get("identificador"), 0);
					}
				}	
			}
			
			//Este ciclo ya les pone sus cantidades reales
			for(int con4 = 0; con4 < EmpleadoYCantidadesArray.length(); con4++){
				JSONObject EmpleadoYCantidadeAuxiliar = EmpleadoYCantidadesArray.getJSONObject(con4);
					for(int con5 = 0; con5 < empleados.size(); con5++) {
						Object[] aux = (Object[]) empleados.get(con5);
						if(EmpleadoYCantidadeAuxiliar.get("id").toString().equalsIgnoreCase(aux[0].toString())) {
							EmpleadoYCantidadesArray.getJSONObject(con4).put("cantidad-modelo-" + aux[2].toString(), aux[3].toString());
							EmpleadoYCantidadesArray.getJSONObject(con4).put("especial-modelo-" + aux[2].toString(), aux[4].toString());
						}
					}
			}
			
			//Ahora ya con todo acomodado, se pintan por fin xdxddddd
			JSONObject EmpleadoYCantidadeAuxiliar2 = EmpleadoYCantidadesArray.getJSONObject(0);
			for(int con6 = 0; con6 < EmpleadoYCantidadesArray.length(); con6++) {
				
				JSONObject EmpleadoYCantidadeAuxiliar = EmpleadoYCantidadesArray.getJSONObject(con6);
				
				//Se pinta el nombre del empleado
				cell = new PdfPCell(new Phrase(EmpleadoYCantidadeAuxiliar.get("nombre").toString(), font2));
				cell.setBackgroundColor(bajito);
				cell.setPadding(3f);
				tabla3.addCell(cell);
				
					//Se agregan las celdas de las cantidades
					for(int p = 0; p < modeloPosicionArray.length(); p++) {
						JSONObject modeloPosicionAuxiliar = modeloPosicionArray.getJSONObject(p);
						//Se pintan las cantidades normales
						cell = new PdfPCell(new Phrase(EmpleadoYCantidadeAuxiliar.get("cantidad-modelo-" + modeloPosicionAuxiliar.get("identificador")).toString(), font2));
						cell.setPadding(3f);
						tabla3.addCell(cell);
						//Se pintan las cantidades especiales
						cell = new PdfPCell(new Phrase(EmpleadoYCantidadeAuxiliar.get("especial-modelo-" + modeloPosicionAuxiliar.get("identificador")).toString(), font2));
						cell.setPadding(3f);
						tabla3.addCell(cell);
					}
			}
			
			/**
			 * 
			 * 
			 * 
			 * Detalles extra
			 * 
			 * 
			 * 
			 */
			//Se establece el ancho de las columnas y la tabla en si.
			tabla3.setWidthPercentage(100f);
			tabla3.setWidths(medidas);
			//Se agrega la tabla al documento
			document.add(tabla3);
			//Ya que esta en el ultimo coordinado, se cambia de hoja para ser mas ordenados
			if(i < (coordinados.size() - 1)) {
				document.newPage();
			}
			//Se reinicia el array de los modelos para el siguiente coordinado
			modeloPosicionArray = new JSONArray();
		}
		
		document.close();
		
	}

}
