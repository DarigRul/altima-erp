
$(document).ready(function () {

	listarClasificacion();

});
$('#detalleClasificacion').on('shown.bs.modal', function () {
	$(document).off('focusin.modal');
});

function agregarClasificacion(){
	Swal.fire({
		  title: 'Nueva clasificacion',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-sm-12">'+
				  	'<label for="descripcionMovimiento">Nombre</label>'+
				  	'<input type="text" class="form-control" id="clasificacion" name="clasificacion" placeholder="Clasificacion">'+
				  '</div>'+
				  
			  '</div>',
		  inputAttributes: {
		    autocapitalize: 'off'
		  },
		  showCancelButton: true,
		  confirmButtonText: 'Agregar',
		  cancelButtonText: 'Cancelar',
		  showLoaderOnConfirm: true,
		  preConfirm: (clasificacion) => {
			  
			  if(document.getElementById("clasificacion").value.length<1){
					Swal.showValidationMessage(
							`Complete todos los campos`
					)
				}
		  
		  },
		  allowOutsideClick: () => !Swal.isLoading()
		}).then((result) => {
		  if (result.value && document.getElementById("clasificacion").value ) {
				var clasificacion = document.getElementById("clasificacion").value;
			  $.ajax({
					type: "GET",
					url: "/verificar-duplicado-amp",
					data: {
						'Lookup': clasificacion,
						'Tipo': "Clasificacion"


					}

				}).done(function (data) {
					if(data==false){

						$.ajax({
							type: "POST",
							url: "/guardar-catalogo-amp",
							data: {
								"_csrf": $('#token').val(),
								'clasificacion': clasificacion

							}

						}).done(function (data) {
							listarClasificacion();
						});
						Swal.fire({
							position: 'center',
							icon: 'success',
							title: 'Insertado correctamente',
							showConfirmButton: false,
							timer: 1250
						})
						// / window.setTimeout(function(){location.reload()}, 2000);
					}// /fin segundoif
					else{
						Swal.fire({
							position: 'center',
							icon: 'error',
							title: 'registro duplicado no se ha insertado',
							showConfirmButton: false,
							timer: 1250
						})

					}
				});
		  }
		});
}


function agregarLinea(){
	Swal.fire({
		  title: 'Nueva Linea',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-sm-6">'+
				  	'<label for="descripcionMovimiento">Nombre</label>'+
				  	'<input type="text" class="form-control" id="linea" name="linea" placeholder="Linea">'+
				  '</div>'+
				  
				  '<div class="form-group col-sm-6">'+
				  	'<label for="ubicacionTalla">Clasificaci&oacute;n</label>'+
				  	'<select class="form-control" id="clasificacion" name="clasificacion">'+
				      
				   '</select>'+
				  '</div>'+
				  
			  '</div>',
		  inputAttributes: {
		    autocapitalize: 'off'
		  },
		  showCancelButton: true,
		  confirmButtonText: 'Agregar',
		  cancelButtonText: 'Cancelar',
		  showLoaderOnConfirm: true,
		  preConfirm: (linea) => {
			  
			  if(document.getElementById("linea").value.length<1 ){
					Swal.showValidationMessage(
							`Complete todos los campos`
					)
				}
		  
		  },
		  allowOutsideClick: () => !Swal.isLoading()
		}).then((result) => {
		  if (result.value && document.getElementById("linea").value ) {
				var linea = document.getElementById("linea").value;
				var clasificacion = document.getElementById("clasificacion").value;
				
			  $.ajax({
					type: "GET",
					url: "/verificar-duplicado-amp",
					data: {
						'Lookup': linea,
						'Tipo': "Linea"


					}

				}).done(function (data) {
					if(data==false){

						$.ajax({
							type: "POST",
							url: "/guardar-catalogo-amp",
							data: {
								"_csrf": $('#token').val(),
								'id_clasificacion': clasificacion,
								'linea':linea

							}

						}).done(function (data) {
							listarClasificacion();
						});
						Swal.fire({
							position: 'center',
							icon: 'success',
							title: 'Insertado correctamente',
							showConfirmButton: false,
							timer: 1250
						})
						// / window.setTimeout(function(){location.reload()}, 2000);
					}// /fin segundoif
					else{
						Swal.fire({
							position: 'center',
							icon: 'error',
							title: 'registro duplicado no se ha insertado',
							showConfirmButton: false,
							timer: 1250
						})

					}
				});
		  }
		});
	
	$.ajax({
		method: "GET",
		url: "/listar-amp",
		data:{
			"Tipo":"Clasificacion"
		} ,
		success: (data) => {
			$.each(data, function(key, val) {
	    		$('#clasificacion').append('<option value="' + val.idLookup + '">'+val.nombreLookup+'</option>');})
	    		//$('.selectpicker').selectpicker(["refresh"]);
		},
		error: (e) => {

		}
	})
}


function agregarMovimiento(){
	Swal.fire({
		  title: 'Nuevo movimiento',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-sm-6">'+
				  	'<label for="descripcionMovimiento">Nombre</label>'+
				  	'<input type="text" class="form-control" id="movimiento" name="movimiento" placeholder="Movimiento">'+
				  '</div>'+
				  
				  '<div class="form-group col-sm-6">'+
				  	'<label for="ubicacionTalla">Tipo</label>'+
				  	'<select class="form-control" id="tipo" name="tipo">'+
				  	'<option value="Entrada">Entrada</option>'+
				    '<option value="Salida">Salida</option>'+
				   '</select>'+
				  '</div>'+
				  
			  '</div>',
		  inputAttributes: {
		    autocapitalize: 'off'
		  },
		  showCancelButton: true,
		  confirmButtonText: 'Agregar',
		  cancelButtonText: 'Cancelar',
		  showLoaderOnConfirm: true,
		  preConfirm: (linea) => {
			  
			  if(document.getElementById("movimiento").value.length<1 ){
					Swal.showValidationMessage(
							`Complete todos los campos`
					)
				}
		  
		  },
		  allowOutsideClick: () => !Swal.isLoading()
		}).then((result) => {
		  if (result.value && document.getElementById("movimiento").value ) {
				var movimiento = document.getElementById("movimiento").value;
				var tipo = document.getElementById("tipo").value;
				
			  $.ajax({
					type: "GET",
					url: "/verificar-duplicado-amp",
					data: {
						'Lookup': movimiento,
						'Tipo': "Movimiento"


					}

				}).done(function (data) {
					if(data==false){

						$.ajax({
							type: "POST",
							url: "/guardar-catalogo-amp",
							data: {
								"_csrf": $('#token').val(),
								'movimiento': movimiento,
								'tipo':tipo

							}

						}).done(function (data) {
							listarClasificacion();
						});
						Swal.fire({
							position: 'center',
							icon: 'success',
							title: 'Insertado correctamente',
							showConfirmButton: false,
							timer: 1250
						})
						// / window.setTimeout(function(){location.reload()}, 2000);
					}// /fin segundoif
					else{
						Swal.fire({
							position: 'center',
							icon: 'error',
							title: 'registro duplicado no se ha insertado',
							showConfirmButton: false,
							timer: 1250
						})

					}
				});
		  }
		});
	

}

function agregarAlmacen(){
	
	Swal.fire({
		  title: 'Nuevo almacen',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-sm-6">'+
				  	'<label for="descripcionMovimiento">Almacen</label>'+
				  	'<input type="text" class="form-control" id="almacen" name="almacen" placeholder="Almacen">'+
				  '</div>'+
			  
				  '<div class="form-group col-sm-6">'+
				  	'<label for="descripcionMovimiento">Encargado</label>'+
				  	'<input type="text" class="form-control" id="encargado" name="encargado" placeholder="Encargado">'+
				  '</div>'+
				  
				  '<div class="form-group col-sm-6">'+
				  	'<label for="descripcionMovimiento">Movimiento Entrada</label>'+
				  	'<input type="text" class="form-control" id="entreda" name="entreda" placeholder="Entrada">'+
				  '</div>'+
				  
				  '<div class="form-group col-sm-6">'+
				  	'<label for="descripcionMovimiento">Movimiento Salida</label>'+
				  	'<input type="text" class="form-control" id="salida" name="salida" placeholder="Salida">'+
				  '</div>'+
				  
				 
				  
			  '</div>',
		  inputAttributes: {
		    autocapitalize: 'off'
		  },
		  showCancelButton: true,
		  confirmButtonText: 'Agregar',
		  cancelButtonText: 'Cancelar',
		  showLoaderOnConfirm: true,
		  preConfirm: (almacen) => {
			  if(document.getElementById("almacen").value.length<1 ||
					  document.getElementById("encargado").value.length<1 ||
					  document.getElementById("entreda").value.length<1 ||
					  document.getElementById("salida").value.length<1){
					Swal.showValidationMessage(
							`Complete todos los campos`
					)
				}
		  
		  },
		  allowOutsideClick: () => !Swal.isLoading()
		}).then((result) => {
		  if (result.value && document.getElementById("almacen").value ) {
				var almacen = document.getElementById("almacen").value;
				var encargado = document.getElementById("encargado").value;
				var entrada = document.getElementById("entreda").value;
				var salida = document.getElementById("salida").value;
				
			  $.ajax({
					type: "GET",
					url: "/verificar-duplicado-almacen",
					data: {
						'almacen': almacen,
						'encargado': encargado


					}

				}).done(function (data) {
					if(data==false){

						$.ajax({
							type: "POST",
							url: "/guardar-catalogo-almacen",
							data: {
								"_csrf": $('#token').val(),
								'almacen': almacen,
								'encargado':encargado,
								'entrada':entrada,
								'salida':salida
								

							}

						}).done(function (data) {
							listarClasificacion();
						});
						Swal.fire({
							position: 'center',
							icon: 'success',
							title: 'Insertado correctamente',
							showConfirmButton: false,
							timer: 1250
						})
						// / window.setTimeout(function(){location.reload()}, 2000);
					}// /fin segundoif
					else{
						Swal.fire({
							position: 'center',
							icon: 'error',
							title: 'registro duplicado no se ha insertado',
							showConfirmButton: false,
							timer: 1250
						})

					}
				});
		  }
		});
	

}

function listarClasificacion() {
	console.log ("jhola");
	$.ajax({
		method: "GET",
		url: "/listar-amp",
		data:{
			"Tipo":"Clasificacion"
		} ,
		success: (data) => {
			$('#quitar2').remove();
			$('#contenedorTabla2').append("<div class='modal-body' id='quitar2'>" +
					"<table class='table table-striped table-bordered' id='idtable2' style='width:100%'>" +
					"<thead>" +
					"<tr>" +
					"<th>Clave</th>" +
					"<th>Nomenclatura</th>" +
					
				
					"<th>Acciones</th>" +
					"</tr>" +
					"</thead>" +
					"</table>" + "</div>");
			var a;
			var b = [];
			if(rolAdmin==1){
				for (i in data) {
					var creacion =data[i].actualizadoPor==null?"":data[i].actualizadoPor;

					a = [
						"<tr>" +
						"<td>" + data[i].idText + "</td>",
						"<td>" + data[i].nombreLookup + "</td>",
						"<td style='text-align: center'>" +
						"<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>"+data[i].creadoPor+" <br /><strong>Fecha de creación:</strong> "+data[i].fechaCreacion+"<br><strong>Modificado por:</strong>"+creacion+"<br><strong>Fecha de modicación:</strong>"+data[i].ultimaFechaModificacion+"'><i class='fas fa-info'></i></button> " +
						" <button id='" + data[i].idLookup + "' value='" + data[i].nombreLookup + "' color='" + data[i].atributo1 + "' class='btn btn-warning btn-circle btn-sm popoverxd edit_data_color' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " +
						(data[i].estatus == 1 ? "<button onclick='bajarColor(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " " ) +
						(data[i].estatus == 0 ? "<button onclick='reactivar(" + data[i].idLookup + ")' class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-sort-up'></i></button>" : " " ) +
						"</td>" +

						"<tr>"
						];
					b.push(a);
				}
			}
			else{
				for (i in data) {
					var creacion =data[i].actualizadoPor==null?"":data[i].actualizadoPor;
					if(data[i].estatus==1){
						a = [
							"<tr>" +
							"<td>" + data[i].idText + "</td>",
							"<td>" + data[i].nombreLookup + "</td>",
							"<td style='text-align: center'>" +
							"<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>"+data[i].creadoPor+" <br /><strong>Fecha de creación:</strong> "+data[i].fechaCreacion+"<br><strong>Modificado por:</strong>"+creacion+"<br><strong>Fecha de modicación:</strong>"+data[i].ultimaFechaModificacion+"'><i class='fas fa-info'></i></button> " +
							(rolEditar == 1 ? "<button id='" + data[i].idLookup + "' value='" + data[i].nombreLookup + "' color='" + data[i].atributo1 + "' class='btn btn-warning btn-circle btn-sm popoverxd edit_data_color' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>" : " ") +
							(rolEliminar == 1 ? "<button onclick='bajarColor(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
							"</td>" +

							"<tr>"
							];
						b.push(a);

					}
				}
			}
			var tablaColores = $('#idtable2').DataTable({
				"data": b,
				"ordering": true,
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
			new $.fn.dataTable.FixedHeader(tablaColores);
		},
		error: (e) => {

		}
	})
}

function addMovimiento(){
	Swal.fire({
		  title: 'Nuevo Movimiento',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-sm-6">'+
				  	'<label for="descripcionMovimiento">Descripci&oacute;n</label>'+
				  	'<input type="text" class="form-control" id="descripcionMovimiento" placeholder="Trazo">'+
				  '</div>'+
				  '<div class="form-group col-sm-6">'+
				  	'<label for="origenMovimiento">Tipo de movimiento</label>'+
				  	'<select class="form-control" id="origenMovimiento">'+
				      '<option>Entrada</option>'+
				      '<option>Salida</option>'+
				   '</select>'+
				  '</div>'+
			  '</div>',
		  inputAttributes: {
		    autocapitalize: 'off'
		  },
		  showCancelButton: true,
		  confirmButtonText: 'Agregar',
		  cancelButtonText: 'Cancelar',
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
				  position: 'center',
				  icon: 'success',
				  title: 'Movimiento agregado correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}
function editMovimiento(){
	Swal.fire({
		  title: 'Editar Movimiento',
		  html:
			  '<div class="row">'+
			  '<div class="form-group col-sm-6">'+
			  	'<label for="descripcionMovimientoEditar">Descripci&oacute;n</label>'+
			  	'<input type="text" class="form-control" id="descripcionMovimientoEditar" placeholder="Trazo">'+
			  '</div>'+
			  '<div class="form-group col-sm-6">'+
			  	'<label for="origenMovimientoEditar">Tipo de movimiento</label>'+
			  	'<select class="form-control" id="origenMovimientoEditar">'+
			      '<option>Entrada</option>'+
			      '<option>Salida</option>'+
			   '</select>'+
			  '</div>'+
		  '</div>',
		  inputAttributes: {
		    autocapitalize: 'off'
		  },
		  showCancelButton: true,
		  confirmButtonText: 'Agregar',
		  cancelButtonText: 'Cancelar',
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
				  position: 'center',
				  icon: 'success',
				  title: 'Movimiento editado correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}

function deleteMovimiento(){
	Swal.fire({
		  title: '¿Deseas eliminar el movimiento?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar'
		}).then((result) => {
		  if (result.value) {
			  Swal.fire({
				  position: 'center',
				  icon: 'success',
				  title: 'Movimiento eliminado correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}
function reactiveMovimiento(){
	Swal.fire({
		  title: '¿Deseas reactivar el movimiento?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar'
		}).then((result) => {
		  if (result.value) {
			  Swal.fire({
				  position: 'center',
				  icon: 'success',
				  title: 'Movimiento reactivado correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}