 $(document).ready(function(){
	 $('#asignacionticketsmodal').modal('show');   
	 
	 $("#asignacionticketsmodal").data('bs.modal')._config.backdrop = 'static'

		 $('#asignacionticketsmodal').off('keydown.dismiss.bs.modal');

	 $.ajax({
			method: "GET",
			url: "/obtener-operarios",
			data: {
			
			
			},
			success: (data) => { 
				console.log(data);
				var sel = $("#selectoperario");
			    sel.empty();
			    for (var i=0; i<data.length; i++) {
			      sel.append('<option value="' +  data[i][0]+ '">' +  data[i][1] + '</option>');
			    }
				
				

			}});
	 
	 $( "#GuardarBuscar" ).click(function() {
		 if(!document.getElementById("selectoperario").value || document.getElementById("selectoperario").value==null){
			 Swal.fire({
				  icon: 'error',
				  title: 'Error',
				  text: 'Ingrese un operario!'			})
			 
		 }else{
			 
			 $('#asignacionticketsmodal').modal('hide');   
            // alert("gfs"+document.getElementById("selectoperario").value);
			 listar($("#selectoperario").val());
		 }
		 
		 
		});
 });





function listar(operario){
	$("#idempoperario").val(operario);
	
	 $.ajax({
			method: "GET",
			url: "/obtener-numero-ticket",
			data: {
			
			
			},
			success: (data) => { 
				var sel = $("#selectickets");
			    sel.empty();
			    for (var i=0; i<data.length; i++) {
			      sel.append('<option value="' +  data[i][0]+ '">' +  data[i][1] + '</option>');
			    }
				
			      $('#selectickets').selectpicker('refresh');
	

			}});
	var quitar= "#quitar2";
	var contenedor = "#tabla";
	var modal = "#modalEspecificaciones";
	var tabla = "#idtable2";
	
	$.ajax({
		method: "GET",
		url: "/obtener-tickets-operarios",
		data: {
			"idoperario": operario
		},
		success: (data2) => {
			console.log(data2);
			$(quitar).remove();
			$(contenedor).append("<div class='modal-body' id='quitar2'>" +
				"<table class='table table-striped table-bordered' id='idtable2' style='width:100%'>" +
				"<thead>" +
				"<tr>" +
				"<th>Operario</th>" +
				"<th>Ticket</th>" +
				"<th>Fecha</th>" +
				"<th>Pedido</th>" +
				"<th>Modelo</th>" +
				"<th>Bulto</th>" +
				"<th>Cantidad</th>" +
				"<th>Operacion</th>" +
				"<th>SAM</th>" +
				"<th>Tiempo estimado</th>" +
				"<th></th>" +
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
					"<td>" + data2[i][17] + "</td>",
					"<td>" + data2[i][14] + "</td>",
					"<td>" + data2[i][16] + "</td>",
					"<td>" + data2[i][4] + "</td>",
					"<td>" + data2[i][5] + "</td>",
					"<td>" + data2[i][6] + "</td>",
					"<td>" + data2[i][7] + "</td>",
					"<td>" + data2[i][9] + "</td>",

					"<td>" + data2[i][10] + "</td>",
					"<td>" + data2[i][13] + "</td>",
					"<td style='text-align: center'>" +
					"<button onclick=eliminarembultado('" + data2[i][0] + "') class='btn btn-danger' type='button'>Eliminar</button>" +
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


$( "#GuardarBuscarTickets" ).click(function() {
	if($('#selectickets').val()==null || $('#idempoperario').val()==null ){
		Swal.fire({
			  icon: 'error',
			  title: 'Error',
			  text: 'Algo ha salido mal recargue la pagina'
			})	
		
	}
	
	else{
	$.ajax({
		method: "PUT",
		url: "/asignar-operario-ticket",
		data: {
			"_csrf": $(
			'#token')
		.val(),
		"idticket": $('#selectickets').val() ,
			"idoperario": $('#idempoperario').val()
		
		},
		success: (data) => { 
			if(data==true){
			Swal.fire({
				  icon: 'success',
				  title: 'Ingresado correctamente',
				  text: 'Ingresado correctamente'
				})		
				listar(document.getElementById("selectoperario").value);
			}else{
				
				Swal.fire({
					  icon: 'error',
					  title: 'Error',
					  text: 'Intentelo de nuevo'
					})		
					listar(document.getElementById("selectoperario").value);
				
			}
		}});
	//listar(document.getElementById("idcontrol").value);
	 // alert( "Handler for .click() called." );
	}
	
	});



$( "#Recargar" ).click(function() {
    location.reload();


});

function eliminarembultado(id){

	Swal.fire({
		  title: '¿Desasignar el ticket al operario?',
		  text: 'Puede asignarlo nuevamente en otro momento',

		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: `Confirmar`,
		  cancelButtonText: 'Cancelar'
		}).then((result) => {
		  /* Read more about isConfirmed, isDenied below */
		  if (result.value) {
			  $.ajax({
					method: "PUT",
					url: "/eliminar-asignacion",
					data: {
						"_csrf": $(
						'#token')
					.val(),
						"idticket": id
					
					},
					success: (data) => { 
						listar(document.getElementById("selectoperario").value);


					}});
		  
			
		  } else {
		    Swal.fire('No se ha desasignado el ticket al operario', '', 'info');
		  }
		})
	
	//////	
	
	
	//alert("es id "+id+"");
	
}