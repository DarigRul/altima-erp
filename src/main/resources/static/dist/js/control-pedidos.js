
function listar(idcontrolpedido,idprenda){
	
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
			$("#tela").val(data.claveTela);
			$("#cantidad").val(data.confeccion);
			$("#restante").val(data.confeccion);
			$("#idcontrol").val(data.idControlPedido);
			$("#idprenda").val(data.idPrenda);

		}});
	
	$.ajax({
		method: "GET",
		url: "/contar-operaciones",
		data: {
			"idprenda": idprenda
		
		},
		success: (data) => { 
			console.log(data);
			$("#operaciones").val(data);

		}});
	
	
	$.ajax({
		method: "GET",
		url: "/listar-control-pedidos-bulto",
		data: {
			"id": idcontrolpedido
		},
		success: (data2) => {
			console.log(data2);
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
console.log(i);
console.log(data2);
				a = [
					"<tr>" +
					"<td>" + parseInt(parseInt(i)+1) + "</td>",
					"<td>" + data2[i].cantidadPrendaBulto + "</td>",
					"<td style='text-align: center'>" +
					"<button onclick=eliminarembultado('" + data2[i].idControlPedidoEmbultado + "') class='btn btn-danger' type='button'>Eliminar</button>" +
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


$( "#Guardar" ).click(function() {
	console.log(document.getElementById("idcontrol").value);
	if($('#cantidadprenda').val()==null || $('#cantidadprenda').val()==null || $('#cantidadprenda').val() ==0 || $('#cantidadprenda').val()===0 ){
		Swal.fire({
			  icon: 'error',
			  title: 'Ingrese un valor',
			  text: 'El campo prenda por bulto no puede estar vacio o ser 0'
			})	
		
	}
	
	else{
	$.ajax({
		method: "POST",
		url: "/guardar-control-pedidos-bulto",
		data: {
			"_csrf": $(
			'#token')
		.val(),
		"cantidadprenda": $(
			'#cantidadprenda')
		.val() ,
			"id": document.getElementById("idcontrol").value
		
		},
		success: (data) => { 
			console.log(data);
			console.log(data.actualizadoPor);
			Swal.fire({
				  icon: 'success',
				  title: 'Ingresado correctamente',
				  text: 'Ingresado correctamente'
				})		
				listar(document.getElementById("idcontrol").value,document.getElementById("idprenda").value);

		}});
	//listar(document.getElementById("idcontrol").value);
	 // alert( "Handler for .click() called." );
	}
	
	});



$( "#enviarOrden" ).click(function() {
	
	 window.open("/maquilacontrolpedidostickets?format=pdf");
	 
	

});

function eliminarembultado(id){
	
	console.log("fads");
	$.ajax({
		method: "DELETE",
		url: "/eliminar-embultado",
		data: {
			"_csrf": $(
			'#token')
		.val(),
			"id": id
		
		},
		success: (data) => { 
			listar(document.getElementById("idcontrol").value,document.getElementById("idprenda").value);


		}});
	//alert("es id "+id+"");
	
}