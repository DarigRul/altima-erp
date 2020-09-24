(function() {
	var _div = document.createElement('div');
	jQuery.fn.dataTable.ext.type.search.html = function(data) {
		_div.innerHTML = data;
		return _div.textContent ?
				_div.textContent
				.replace(/[áÁàÀâÂäÄãÃåÅæÆ]/g, 'a')
				.replace(/[çÇ]/g, 'c')
				.replace(/[éÉèÈêÊëË]/g, 'e')
				.replace(/[íÍìÌîÎïÏîĩĨĬĭ]/g, 'i')
				.replace(/[ñÑ]/g, 'n')
				.replace(/[óÓòÒôÔöÖœŒ]/g, 'o')
				.replace(/[ß]/g, 's')
				.replace(/[úÚùÙûÛüÜ]/g, 'u')
				.replace(/[ýÝŷŶŸÿ]/g, 'n') :
					_div.innerText
					.replace(/[áÁàÀâÂäÄãÃåÅæÆ]/g, 'a')
					.replace(/[çÇ]/g, 'c')
					.replace(/[éÉèÈêÊëË]/g, 'e')
					.replace(/[íÍìÌîÎïÏîĩĨĬĭ]/g, 'i')
					.replace(/[ñÑ]/g, 'n')
					.replace(/[óÓòÒôÔöÖœŒ]/g, 'o')
					.replace(/[ß]/g, 's')
					.replace(/[úÚùÙûÛüÜ]/g, 'u')
					.replace(/[ýÝŷŶŸÿ]/g, 'n');
	};
})();
$(document).ready(function() {
	var table = $('.tablaConcentradoTallas')
	.DataTable({
		"ordering": false,
		"pageLength": 5,
		"responsive": true,
		"stateSave":true,
		"drawCallback": function() {
			$('.popoverxd').popover({
				container: 'body',
				trigger: 'hover'
			});
		},
		"columnDefs": [{
			"type": "html",
			"targets": '_all'
		}],
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
	new $.fn.dataTable.FixedHeader(table);

});

function redirect(e){
	console.log(e[0]);
	console.log(e[1]);
	console.log(e[2]);
	
	window.location.href="/editar-concentrado-de-tallas/"+e[0]+"/"+e[1]+""
	
	
	
}


function listarPrendas(e) {
	console.log(e[0]);
	console.log(e[1])
	console.log(e[2])
	var idpedido;
	if (e[2]!=0) {
		idpedido=e[2];
	} else {
       idpedido=e[0];
	}
	console.log("fdsf"+idpedido);
	var idempleado=e[1];
	var container='#contenedor2';
	var remover='#quitar3';
	var table='idtable3';
	var tabla='#idtable3';
	var modal='#modalConcentradoPrendasInfo';
	var wr='_wrapper';

	
	
	$(modal.concat(idempleado)).on('hidden.bs.modal', function () {
		//$(tabla.concat(idempleado,wr)).remove();
		//$(remover.concat(idempleado)).remove();
		console.log(remover.concat(idempleado));
		$(remover.concat(idempleado)).remove();
		$(tabla.concat(idempleado,wr)).remove();
		$(tabla.concat(idempleado)).DataTable().destroy();

	});
	

	$.ajax({
		method: "GET",
		url: "/prenda-empleado-pivote",
		data:{
			"idpedido":idpedido,
			"idempleado":idempleado
		} ,
		success: (data) => {
			$(remover.concat(idempleado)).remove();
			$(container.concat(idempleado)).append("<div class='modal-body' id="+remover.concat(idempleado)+">" +
					"<table class='table table-striped table-bordered' id="+table.concat(idempleado)+" style='width:100%'>" +
					"<thead>" +
					"<tr>" +
					"<th>Prenda</th>" +

					"<th>Especificaciones</th>" +

					"</tr>" +
					"</thead>" +
					"</table>" + "</div>");
			var a;
			var b = [];

			for (i in data) {

				const ids = [data[i][0],data[i][1]];
				a = [
					"<tr>" +
					"<td>" + data[i][2] + "</td>",

					"<td>" + data[i][5] + "</td>",

					"<tr>"
					];
				b.push(a);
			}


			var tablaColores2 = $(tabla.concat(idempleado)).DataTable({
				"data": b,
				"ordering": false,
				"pageLength": 5,
				"drawCallback": function() {
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
			new $.fn.dataTable.FixedHeader(tablaColores2);
			

		},
		error: (e) => {

		}
	})
}
// Remove accented character from search input as well
$('#example_filter input[type=search]').keyup( function () {
	var table = $('.tablaGeneral').DataTable(); 
	table.search(
			jQuery.fn.DataTable.ext.type.search.html(this.value)
	).draw();
} );