function listar2(folio){
	
	var quitar= "#quitar2";
	var contenedor = "#tabla22";
	var modal = "#modalEspecificaciones";
	var tabla = "#idtable2";	
	
	$.ajax({
		method: "GET",
		url: "/listar-devoluciones-herramientas",
		data: {
			"folio": folio
		},
		success: (data2) => {
			$(quitar).remove();
			$(contenedor).append("<div class='modal-body' id='quitar2'>" +
				"<table class='table table-striped table-bordered' id='idtable2' style='width:100%'>" +
				"<thead>" +
				"<tr>" +
				"<th>Cant. Prestada</th>" +
				"<th>Articulo</th>" +
				"<th>Marca</th>" +
				"<th>Costo</th>" +
				"<th>Cant. Devuelta</th>" +
				"<th>Fecha Devolucíon</th>" +
				"<th>Acciones</th>" +
				"</tr>" +
				"</thead>" +
				"</table>" + "</div>");
			var a;
			var b = [];
			
			
			
			//"<button onclick=eliminarembultado('" + data2[i].idControlPedidoEmbultado + "') class='btn btn-primary btn-sm btn-circle popoverxd'  data-placement='top' data-content='Avance' data-target='#avancestickets' data-toggle='modal' ><i class='fas fa-tape'></i></button>" +

			for (i in data2) {
				a = [
					"<tr>" +
					"<td>" + data2[i][2] + "</td>",
					"<td>" + data2[i][7] + "</td>",
					"<td>" + data2[i][8] + "</td>",
					"<td>" + data2[i][9] + "</td>",
					"<td>" + data2[i][3] + "</td>",
					"<td>" + data2[i][5] + "</td>",
					"<td style='text-align: center'>" +
					"<button onclick=listar3(" + data2[i][0] + ","+data2[i][1]+") type='button' class='btn btn-primary btn-sm btn-circle popoverxd' data-toggle='modal' data-target='#avancesticketsbultos'>"+
					 "<i class='fas fa-tape'>"+
					"</button>"+
					"</td>" +

					"<tr>"
				];
				b.push(a);
			}

			var tablaes = "#idtable2";
			var tablaColores = $(tablaes).DataTable({
				"data": b,
				"ordering": false,
				"pageLength": 5,
				"drawCallback": function () {
					$('.popoverxd').popover({
						container: 'body',
						trigger: 'hover'
					});
				},
				"responsive": true,
				"lengthMenu": [
					[5, 10, 25, 50, 100],
					[5, 10, 25, 50, 100]
				],
				"language": {
					"sProcessing": "Procesando...",
					"sLengthMenu": "Mostrar _MENU_ registros",
					"sZeroRecords": "No se encontraron resultados",
					"sEmptyTable": "NingÃºn dato disponible en esta tabla =(",
					"sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
					"sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
					"sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
					"sInfoPostFix": "",
					"sSearch": "Buscar:",
					"sUrl": "",
					"sInfoThousands": ",",
					"sLoadingRecords": "Cargando...",
					"oPaginate": {
						"sFirst": "Primero",
						"sLast": "Ãšltimo",
						"sNext": "Siguiente",
						"sPrevious": "Anterior"
					},
					"oAria": {
						"sSortAscending": ": Activar para ordenar la columna de manera ascendente",
						"sSortDescending": ": Activar para ordenar la columna de manera descendente"
					},
					"buttons": {
						"copy": "Copiar",
						"colvis": "Visibilidad"
					}
				}
			});
			new $.fn.dataTable.FixedHeader(tablaColores);
		},
		error: (e) => {

		}
	})
}


function listar3(idcontrolpedido,idcontrolpedidoembultado){
	
	var quitar= "#quitar23";
	var contenedor = "#tabla23";
	var modal = "#modalEspecificaciones";
	var tabla = "#idtable2";
	


	
	$.ajax({
		method: "GET",
		url: "/listar-avances-asignaciones-bultos",
		data: {
			"id": idcontrolpedido,
			"idcontrolpedidoembultado": idcontrolpedidoembultado
		},
		success: (data2) => {
			$(quitar).remove();
			$(contenedor).append("<div class='modal-body' id='quitar23'>" +
				"<table class='table table-striped table-bordered' id='idtable23' style='width:100%'>" +
				"<thead>" +
				"<tr>" +
				"<th>OperaciÃ³n</th>" +
				"<th>Operario</th>" +
				"<th>Fecha</th>" +
				"</tr>" +
				"</thead>" +
				"</table>" + "</div>");
			var a;
			var b = [];
			
			
			
			//"<button onclick=eliminarembultado('" + data2[i].idControlPedidoEmbultado + "') class='btn btn-primary btn-sm btn-circle popoverxd'  data-placement='top' data-content='Avance' data-target='#avancestickets' data-toggle='modal' ><i class='fas fa-tape'></i></button>" +

			for (i in data2) {
//					"<td style='text-align: center'>" +"2020/10/10" +"</td>" +

				a = [
					"<tr>" +
					"<td>" + data2[i][0] + "</td>",
					"<td>" + data2[i][1] + "</td>",
					"<td>" + data2[i][2] +"</td>" +

					"<tr>"
				];
				b.push(a);
			}

			var tablaes = "#idtable23";
			var tablaColores = $(tablaes).DataTable({
				"data": b,
				"ordering": false,
				"pageLength": 5,
				"drawCallback": function () {
					$('.popoverxd').popover({
						container: 'body',
						trigger: 'hover'
					});
				},
				"responsive": true,
				"lengthMenu": [
					[5, 10, 25, 50, 100],
					[5, 10, 25, 50, 100]
				],
				"language": {
					"sProcessing": "Procesando...",
					"sLengthMenu": "Mostrar _MENU_ registros",
					"sZeroRecords": "No se encontraron resultados",
					"sEmptyTable": "NingÃºn dato disponible en esta tabla =(",
					"sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
					"sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
					"sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
					"sInfoPostFix": "",
					"sSearch": "Buscar:",
					"sUrl": "",
					"sInfoThousands": ",",
					"sLoadingRecords": "Cargando...",
					"oPaginate": {
						"sFirst": "Primero",
						"sLast": "Ãšltimo",
						"sNext": "Siguiente",
						"sPrevious": "Anterior"
					},
					"oAria": {
						"sSortAscending": ": Activar para ordenar la columna de manera ascendente",
						"sSortDescending": ": Activar para ordenar la columna de manera descendente"
					},
					"buttons": {
						"copy": "Copiar",
						"colvis": "Visibilidad"
					}
				}
			});
			new $.fn.dataTable.FixedHeader(tablaColores);
		},
		error: (e) => {

		}
	})
}