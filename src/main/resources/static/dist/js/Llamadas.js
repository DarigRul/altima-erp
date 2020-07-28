$(document).ready(function () {
	$.ajax({
		type: "GET",
		url: "obtener-clientes", 
		success: function(result){
			var i = 0;
			for(;i<result.length;i++){
				listarseguimientos(result[i])
			}
		}});
});


var lengthid=$('div[id^=llamadasAgente]').length;
var id='#llamadasAgente';
for (i = 0; i < lengthid; i++) {
	$(id.concat(i)).on('shown.bs.modal',function(){
		$(document).off('focusin.modal');

	})
}   

// Habilitar form de SweetAlert2
function listarseguimientos(idcli) {
	var idcliente=idcli;
	var id='#contenedor';
	var idquit='#quitar';
	var idtabl='#idtable';
	$.ajax({
		method: "GET",
		url: "/llamadas-cliente",
		data:{
			"id":idcliente
		} ,
		success: (data) => {
			$(idquit.concat(idcliente)).remove();
			$(id.concat(idcliente)).append("<div class='modal-body' id='quitar"+(idcliente)+"'>" +
					"<table class='table table-striped table-bordered' id='idtable"+(idcliente)+"' style='width:100%'>" +
					"<thead>" +
					"<tr>" +
					"<th>Fecha de llamada</th>" +
					"<th>Observaciones</th>" +
					"<th></th>" +
					"</tr>" +
					"</thead>" +
					"</table>" + "</div>");
			var a;
			var b = [];
			for (i in data) {
				var startdate = data[i].start;
				start = startdate.replace('T',' ');
				a = [
					"<tr>" +
					"<td>" + start + "</td>",
					"<td>" + data[i].description + "</td>",
					"<td>"+
					"<a onclick='editarLlamadaAgente(this);'  idcliente='" + data[i].idCliente + "'  idcalendario='" + data[i].idCalendario + "'  description='" + data[i].description + "'  class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-placement='top' data-content='Actualizar'><i class='fas fa-pen'></i></a>" ,
					"</td>"+
					"<tr>"
					];
				b.push(a);
			}
			var tablaLlamadas = $(idtabl.concat(idcliente)).DataTable({
				"data": b,
				"ordering": true,
				"pageLength": 5,
				"responsive": true,
				"drawCallback": function() {
					$('.popoverxd').popover({
						container: 'body',
						trigger: 'hover'
					});
				},
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
		},
		error: (e) => {

		}
	})


}
// ///////////////////////
function agendarLlamadaAgente(idcliente){
	var today = new Date();
	var dd = String(today.getDate()).padStart(2, '0');
	var mm = String(today.getMonth() + 1).padStart(2, '0');
	var yyyy = today.getFullYear();
	date = yyyy + '-' + mm + '-' + dd;
	var hour= String(today.getHours()).padStart(2, '0');
	var minute=String(today.getMinutes()).padStart(2, '0');
	var time = hour + ":" + minute;
	var dateTime = date+'T'+time;
	Swal.fire({
		title: 'Agendar llamada',
		html:'<div class="row">'+
		'<div class="form-group col-sm-12">'+
		'<label>Fecha de la llamada</label>'+
		'<input type="datetime-local" value="'+dateTime +'" class="form-control" id="fechaLlamada">'+
		'</div>'+
		'<div class="form-group col-sm-12">'+
		'<label>Duración</label>'+
		 '<select class="form-control" id="duracion">'+
	      '<option value="15">15 minutos</option>'+
	      '<option value="30">30 minutos</option>'+
	      '<option value="45">45 minutos</option>'+
	      '<option value="60">60 minutos</option>'+
	    '</select>'+
		'</div>'+
		'<input type="hidden" id="estatusLlamada" value="Pendiente">'+
		'<div class="form-group col-sm-12">'+
		'<label>Observaciones</label>'+
		'<textarea class="form-control" id="observacionLlamada"></textarea>'+
		'</div>'+
		'</div>',
		showCancelButton: true,
		cancelButtonColor: '#6C757D',
		cancelButtonText: 'Cancelar',
		confirmButtonText: 'Agendar',
		confirmButtonColor:'#343a40',
	}).then((result) => {
		if (result.value && document.getElementById("fechaLlamada").value && document.getElementById("observacionLlamada").value && document.getElementById("duracion").value) {
			var Fecha = document.getElementById("fechaLlamada").value;
			var Observacion= document.getElementById("observacionLlamada").value;
			var Idcliente=idcliente;
			var duracion=document.getElementById("duracion").value;
			console.log(duracion);
			$.ajax({
				type: "POST",
				url: "/guardar-seguimientos",
				data: {
					"_csrf": $('#token').val(),
					'fecha': Fecha,
					'observacion':Observacion,
					'idcliente':Idcliente,
					'duracion':duracion
				}

			}).done(function (data) {
				listarseguimientos(Idcliente);
			});
			Swal.fire({
				position: 'center',
				icon: 'success',
				title: 'Insertado correctamente',
				showConfirmButton: false,
				timer: 1250
			})
		}
		else{
			Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'Ingrese todos los datos requeridos',
				showConfirmButton: false,
				timer: 1250
			})

		}
	})
}

function editarLlamadaAgente(e){
	var descr = e.getAttribute("description");
	var id=e.getAttribute("idcalendario");
	var idcli=e.getAttribute("idcliente");
	Swal.fire({
		title: 'Actualizar llamada',
		html:'<div class="row">'+
		'<div class="form-group col-sm-12">'+
		'<label>Observaciones</label>'+
		'<textarea value="'+descr+'" class="form-control" id="observacionLlamada">'+descr+'</textarea>'+
		'</div>'+
		'</div>',
		showCancelButton: true,
		cancelButtonColor: '#6C757D',
		cancelButtonText: 'Cancelar',
		confirmButtonText: 'Actualizar',
		confirmButtonColor:'#343a40',
	}).then((result) => {
		if (result.value &&  document.getElementById("observacionLlamada").value) {
			var Observacion= document.getElementById("observacionLlamada").value;
			$.ajax({
				type: "POST",
				url: "/editar-llamadas-cliente",
				data: {
					"_csrf": $('#token').val(),
					'observacion':Observacion,
					'id':id
				}
			}).done(function (data) {
				listarseguimientos(idcli);
			});
			Swal.fire({
				position: 'center',
				icon: 'success',
				title: 'Editado correctamente',
				showConfirmButton: false,
				timer: 1250
			})
		}
		else{
			Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'Ingrese todos los datos requeridos',
				showConfirmButton: false,
				timer: 1250
			})
		}
	})
}


// Habilitar form de SweetAlert2
$('#accionesClienteAgente').on('shown.bs.modal', function() {
	$(document).off('focusin.modal');
});

function editarObservacionesClienteAgente(){
	Swal.fire({
		title: 'Actualizar observaciones',
		html:'<div class="row">'+
		'<div class="form-group col-sm-12">'+
		'<label>Observaciones</label>'+
		'<textarea class="form-control" id="cambioobservacionCliente"></textarea>'+
		'</div>'+
		'</div>',
		showCancelButton: true,
		cancelButtonColor: '#6C757D',
		cancelButtonText: 'Cancelar',
		confirmButtonText: 'Actualizar',
		confirmButtonColor:'#343a40',
		showLoaderOnConfirm: true,
		preConfirm: (login) => {
			return fetch(`//api.github.com/users/${login}`)
			.then(response => {
				if (!response.ok) {
					throw new Error(response.statusText)
				}
				return response.json()
			})
			.catch(error => {
				Swal.showValidationMessage(
						`Request failed: ${error}`
				)
			})
		},
		allowOutsideClick: () => !Swal.isLoading()
	}).then((result) => {
		if (result.value) {
			Swal.fire({
				title: `${result.value.login}'s avatar`,
				imageUrl: result.value.avatar_url
			})
		}
	})
}


