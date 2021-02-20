
function listar(idcontrolpedido){
	// /importante poner el id prenda insertado previamente en el ajax ya que toma
	// el del formulario
	var quitar= "#quitar2";
	var contenedor = "#tabla";
	var modal = "#modalEspecificaciones";
	var tabla = "#idtable2";
	

	$.ajax({
		method: "GET",
		url: "/listar-control-pedidos-one",
		data: {
			"id": idcontrolpedido
		
		},
		success: (data) => { 
			console.log(data);
			console.log(data.actualizadoPor);
			$("#modelo").val(data.modelo);
			$("#tela").val(data.clavePrenda);
			$("#cantidad").val(data.confeccion);

		}});
	
	
	$.ajax({
		method: "GET",
		url: "/listar-control-pedidos-one",
		data: {
			"id": idcontrolpedido
		},
		success: (data2) => {
			
			$(quitar).remove();
			$(contenedor).append("<div class='modal-body' id='quitar2'>" +
				"<table class='table table-striped table-bordered' id='idtable2' style='width:100%'>" +
				"<thead>" +
				"<tr>" +
				"<th>Embultado</th>" +
				"<th>Cantidad</th>" +
				"<th>Eliminar</th>" +
				"</tr>" +
				"</thead>" +
				"</table>" + "</div>");
			var a;
			var b = [];
			
			//console.log(data2[0]);
			
			
			
			for (i in data2) {
//console.log(i);

				a = [
					"<tr>" +
					"<td>" + data2.actualizadoPor + "</td>",
					"<td>" + data2.actualizadoPor + "</td>",
					"<td style='text-align: center'>" +
					"<button onclick=eliminarEspecificacion('" + data2.actualizadoPor + "','" + data2.actualizadoPor + "','" + data2.actualizadoPor + "') class='btn btn-danger' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'>Eliminar</button>" +
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
					"sEmptyTable": "Ningún dato disponible en esta tabla =(",
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
						"sLast": "Último",
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