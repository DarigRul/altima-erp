$(document).ready(function () {

	listarLargo();
	listarProceso();
	listarMaquila();

});

$('#detalleTalla').on('shown.bs.modal', function () {
	$(document).off('focusin.modal');
});
function agregarLargo(){
	Swal.fire({
		  title: 'Nuevo Largo',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-sm-6">'+
				  	'<label for="descripcionProceso">Nomenclatura</label>'+
				  	'<input type="text" class="form-control" id="nomenclatura" name="nomenclatura" placeholder="XXL">'+
				  '</div>'+
				  '<div class="form-group col-sm-6">'+
				  	'<label for="origenProceso">Descripci&oacute;n</label>'+
				  	'<input type="text" class="form-control" id="descripcion" name="descripcion" placeholder="Extra largo">'+
				  '</div>'+
			  '</div>',
		  inputAttributes: {
		    autocapitalize: 'off'
		  },
		  showCancelButton: true,
		  confirmButtonText: 'Agregar',
		  cancelButtonText: 'Cancelar',
		  showLoaderOnConfirm: true,
		  preConfirm: (nomenclatura) => {
			  if(document.getElementById("nomenclatura").value.length<1 || document.getElementById("descripcion").value.length<1){
					Swal.showValidationMessage(
							`Complete todos los campos`
					)
				}
		  },
		  allowOutsideClick: () => !Swal.isLoading()
		}).then((result) => {
		  if (result.value && document.getElementById("nomenclatura").value && document.getElementById("descripcion").value ) {
			  var nomenclatura = document.getElementById("nomenclatura").value;
			  var descripcion = document.getElementById("descripcion").value;
			  $.ajax({
					type: "GET",
					url: "/verificar-duplicado-produccion",
					data: {
						'Lookup': nomenclatura,
						'Tipo': "Largo"


					}

				}).done(function (data) {
					console.log ("dataaa----->"+data)
					if(data==false){

						$.ajax({
							type: "POST",
							url: "/guardar-catalogo-produccion",
							data: {
								"_csrf": $('#token').val(),
								'nomenclatura': nomenclatura,
								'descripcion': descripcion

							}

						}).done(function (data) {
							listarLargo();
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

function listarLargo() {

	$.ajax({
		method: "GET",
		url: "/listar-catalogo-produccion",
		data:{
			"Tipo":"Largo"
		} ,
		success: (data) => {
			$('#quitar2').remove();
			$('#contenedorTabla2').append("<div class='modal-body' id='quitar2'>" +
					"<table class='table table-striped table-bordered' id='idtable2' style='width:100%'>" +
					"<thead>" +
					"<tr>" +
					"<th>Clave</th>" +
					"<th>Nomenclatura</th>" +
					"<th>Descripción</th>" +
				
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
						"<td>" + data[i].descripcionLookup + "</td>",
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
							"<td>" + data[i].descripcionLookup + "</td>",
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


function listarProceso() {

	$.ajax({
		method: "GET",
		url: "/listar-catalogo-produccion",
		data:{
			"Tipo":"Proceso"
		} ,
		success: (data) => {

        	var tabla = $('#tableProceso').DataTable();
        	
        
        	 
        	tabla.clear();
        	    
            $(data).each(function(i, v){ // indice, valor
            	var fecha;
        		var actualizo;
            	if (v.actualizadoPor == null && v.ultimaFechaModificacion == null ){
            		fecha='';
            		actualizo='';
            	}else{
            		actualizo=v.actualizadoPor;
            		fecha=v.ultimaFechaModificacion;
            	}
            	
            	if (v.estatus == 1 ){
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
                		v.descripcionLookup ,
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editProceso(this)" idLookup ="'+v.idLookup+'"  nombre="'+v.nombreLookup+'" descripcion="'+v.descripcionLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>'+
    					'<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="deleteProceso('+v.idLookup+')" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>'
            
               		 ]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
                		v.descripcionLookup ,
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
                		'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editProceso(this)" idLookup ="'+v.idLookup+'"  nombre="'+v.nombreLookup+'" descripcion="'+v.descripcionLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>'+
    					'<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="reactiveProceso('+v.idLookup+')" data-container="body" data-toggle="popover" data-placement="top" data-content="Reactivar"><i class="fas fa-caret-up"></i></button>'
            
               		 ]).node().id ="row";
            	}
            	
           	tabla.draw( false );
            	//fila = '<tr> <td>'+v[1]+'</td>  <td >'+ v[2] +'</td> <td >'+ v[3] +'</td> <td >'+ v[4] +'</td>  </tr>' ;
            	
            	
            	
            })
            
       	
		},
		error: (e) => {

		}
	})
}

//Reactivar pieza de trazo
$('#detalleProceso').on('shown.bs.modal', function() {
    $(document).off('focusin.modal');
});

function addProceso(){
	Swal.fire({
		  title: 'Nuevo proceso',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-sm-6">'+
				  	'<label for="descripcionProceso">Descripci&oacute;n</label>'+
				  	'<input type="text" class="form-control" id="descripcionProceso" placeholder="Trazo">'+
				  '</div>'+
				  '<div class="form-group col-sm-6">'+
				  	'<label for="origenProceso">Origen</label>'+
				  	'<select class="form-control" id="origenProceso">'+
				      '<option value="Interno">Interno</option>'+
				      '<option value="Externo">Externo</option>'+
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
		  preConfirm: (descripcionProceso) => {
			  if(document.getElementById("descripcionProceso").value.length<1){
					Swal.showValidationMessage(
							`Complete todos los campos`
					)
				}
		  },
		  allowOutsideClick: () => !Swal.isLoading()
		}).then((result) => {
			if (result.value && document.getElementById("descripcionProceso").value && document.getElementById("origenProceso").value ) {
				  var descripcionProceso = document.getElementById("descripcionProceso").value;
				  var origenProceso = document.getElementById("origenProceso").value;
				  $.ajax({
						type: "GET",
						url: "/verificar-duplicado-produccion",
						data: {
							'Lookup': descripcionProceso,
							'descripcion': origenProceso,
							'Tipo': "Proceso"


						}

					}).done(function (data) {
						console.log ("dataaa----->"+data)
						if(data==false){

							$.ajax({
								type: "POST",
								url: "/guardar-catalogo-produccion",
								data: {
									"_csrf": $('#token').val(),
									'descripcionProceso': descripcionProceso,
									'origenProceso': origenProceso

								}

							}).done(function (data) {
								listarProceso();
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
function editProceso(e){
 
	Swal.fire({
		  title: 'Editar proceso',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-sm-6">'+
				  	'<label for="descripcionProceso">Descripci&oacute;n</label>'+
				  	'<input type="text" value="'+e.getAttribute("nombre")+'" class="form-control" id="descripcionProceso" placeholder="Trazo">'+
				  '</div>'+
				  '<div class="form-group col-sm-6">'+
				  	'<label for="origenProceso">Origen</label>'+
				  	'<select class="form-control" value="'+e.getAttribute("descripcion")+'" id="origenProceso">'+
				      '<option value="Interno">Interno</option>'+
				      '<option value ="Externo">Externo</option>'+
				   '</select>'+
		           '<input type="hidden" value=" ' + e.getAttribute("idlookup") + ' " class="swal2-input" id="idlookup" placeholder="Pantalón">' +
				  '</div>'+
			  '</div>',
		  inputAttributes: {
		    autocapitalize: 'off'
		  },
		  showCancelButton: true,
		  confirmButtonText: 'Agregar',
		  cancelButtonText: 'Cancelar',
		  showLoaderOnConfirm: true,
		  preConfirm: (descripcionProceso) => {
			  if(document.getElementById("descripcionProceso").value.length<1){
					Swal.showValidationMessage(
							`Complete todos los campos`
					)
				}
		  },
		  allowOutsideClick: () => !Swal.isLoading()
		}).then((result) => {
			if (result.value && document.getElementById("descripcionProceso").value && document.getElementById("origenProceso").value ) {
				  var descripcionProceso = document.getElementById("descripcionProceso").value;
				  var origenProceso = document.getElementById("origenProceso").value;
				  var idLookup = document.getElementById("idlookup").value;
				  $.ajax({
						type: "GET",
						url: "/verificar-duplicado-produccion",
						data: {
							'Lookup': descripcionProceso,
							'descripcion': origenProceso,
							'Tipo': "Proceso"


						}

					}).done(function (data) {
						console.log ("dataaa----->"+data)
						if(data==false){

							$.ajax({
								type: "POST",
								url: "/editar-catalogo-produccion",
								data: {
									"_csrf": $('#token').val(),
									'descripcionProceso': descripcionProceso,
									'origenProceso': origenProceso,
									'idLookup' : idLookup

								}

							}).done(function (data) {
								listarProceso();
							});
							Swal.fire({
								position: 'center',
								icon: 'success',
								title: 'Actualizado correctamente',
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

function deleteProceso(id){
	console.log (id)
	Swal.fire({
		  title: '¿Deseas dar de baja el proceso?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar'
		}).then((result) => {
		  if (result.value && id != null) {

	            $.ajax({
	                type: "POST",
	                url: "/baja-catalogo-produccion",
	                data: {
	                    "_csrf": $('#token').val(),
	                    'id': id
	                }

	            }).done(function(data) {

	            	listarProceso();
	            });
	            Swal.fire({
	                position: 'center',
	                icon: 'success',
	                title: 'Dado de baja correctamente',
	                showConfirmButton: false,
	                timer: 1250
	            })
		  }
		});
}
function reactiveProceso(id){
	Swal.fire({
		  title: '¿Deseas reactivar el proceso?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar'
		}).then((result) => {
		  if (result.value && id != null) {
			  $.ajax({
	                type: "POST",
	                url: "/reactivar-catalogo-produccion",
	                data: {
	                    "_csrf": $('#token').val(),
	                    'idcatalogo': id
	                }

	            }).done(function(data) {

	            	listarProceso();
	            });
	            Swal.fire({
	                position: 'center',
	                icon: 'success',
	                title: 'Reactivado correctamente',
	                showConfirmButton: false,
	                timer: 1250
	            })
		  }
		});
}

function addRuta(){
	Swal.fire({
		  title: 'Nueva ruta',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-sm-12">'+
					  '<label>Proceso</label>'+
					  '<div class="input-group mb-3">'+
						'<select class="form-control" id="origenProceso">'+
					    	'<option>Trazo</option>'+
					    	'<option>Confección</option>'+
					    '</select>'+
						  '<div class="input-group-append">'+
						    '<button class="btn btn-primary" type="button"><i class="fas fa-plus"></i></button>'+
						  '</div>'+
					  '</div>'+
				  '</div>'+
				  '<div class="form-group col-sm-12">'+
					  '<table class="table">'+
						  '<thead>'+
						    '<tr>'+
						      '<th scope="col">Proceso</th>'+
						      '<th scope="col">Eliminar</th>'+
						    '</tr>'+
						  '</thead>'+
						  '<tbody>'+
						    '<tr>'+
						      '<td>Trazo</td>'+
						      '<td class="text-center">'+
						      	'<a class="btn icon-btn btn-danger text-white" type="submit"><span class="btn-glyphicon fas fa-minus img-circle text-danger"></span>Eliminar</a>'+
						      '</td>'+
						    '</tr>'+
						  '</tbody>'+
					'</table>'+
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
				  title: 'Proceso agregado correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}
function editRuta(){
	Swal.fire({
		  title: 'Editar ruta',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-sm-12">'+
					  '<label>Proceso</label>'+
					  '<div class="input-group mb-3">'+
						'<select class="form-control" id="origenProceso">'+
					    	'<option>Trazo</option>'+
					    	'<option>Confección</option>'+
					    '</select>'+
						  '<div class="input-group-append">'+
						    '<button class="btn btn-primary" type="button"><i class="fas fa-plus"></i></button>'+
						  '</div>'+
					  '</div>'+
				  '</div>'+
				  '<div class="form-group col-sm-12">'+
					  '<table class="table">'+
						  '<thead>'+
						    '<tr>'+
						      '<th scope="col">Proceso</th>'+
						      '<th scope="col">Eliminar</th>'+
						    '</tr>'+
						  '</thead>'+
						  '<tbody>'+
						    '<tr>'+
						      '<td>Trazo</td>'+
						      '<td class="text-center">'+
						      	'<a class="btn icon-btn btn-danger text-white" type="submit"><span class="btn-glyphicon fas fa-minus img-circle text-danger"></span>Eliminar</a>'+
						      '</td>'+
						    '</tr>'+
						  '</tbody>'+
					'</table>'+
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
				  title: 'Proceso editado correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}

function deleteRuta(){
	Swal.fire({
		  title: '¿Deseas eliminar la ruta?',
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
				  title: 'Ruta eliminada correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}
function reactiveRuta(){
	Swal.fire({
		  title: '¿Deseas reactivar la ruta?',
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
				  title: 'Ruta reactivada correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}

// ALMACEN LOGICO
$('#detalleMaquileros').on('shown.bs.modal', function () {
	$(document).off('focusin.modal');
});

function listarMaquila() {

	$.ajax({
		method: "GET",
		url: "/listar-catalogo-produccion",
		data:{
			"Tipo":"Maquilero"
		} ,
		success: (data) => {

        	var tabla = $('#tableMaquila').DataTable();
        	
        
        	 
        	tabla.clear();
        	    
            $(data).each(function(i, v){ // indice, valor
            	var fecha;
        		var actualizo;
            	if (v.actualizadoPor == null && v.ultimaFechaModificacion == null ){
            		fecha='';
            		actualizo='';
            	}else{
            		actualizo=v.actualizadoPor;
            		fecha=v.ultimaFechaModificacion;
            	}
            	
            	if (v.estatus == 1 ){
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
                		v.descripcionLookup ,
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarMaquileros(this)" idLookup ="'+v.idLookup+'"  nombre="'+v.nombreLookup+'" descripcion="'+v.descripcionLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>'+
    					'<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="bajaMaquileros('+v.idLookup+')" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>'
            
               		 ]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
                		v.descripcionLookup ,
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarMaquileros(this)" idLookup ="'+v.idLookup+'"  nombre="'+v.nombreLookup+'" descripcion="'+v.descripcionLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>'+
    					'<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="altaMaquileros('+v.idLookup+')" data-container="body" data-toggle="popover" data-placement="top" data-content="Reactivar"><i class="fas fa-caret-up"></i></button>'
            
               		 ]).node().id ="row";
            	}
            	
           	tabla.draw( false );
            	//fila = '<tr> <td>'+v[1]+'</td>  <td >'+ v[2] +'</td> <td >'+ v[3] +'</td> <td >'+ v[4] +'</td>  </tr>' ;
            	
            	
            	
            })
            
       	
		},
		error: (e) => {

		}
	})
}

function agregarMaquileros(){
	Swal.fire({
		  title: 'Nueva maquileros',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-md-6">'+
				  	'<label for="nombreMaquileros">Nombre</label>'+
				  	'<input type="text" class="form-control" id="nombreMaquilero" name="nombreMaquilero" placeholder="Maquileros S.A. de C.V.">'+
				  '</div>'+
				  '<div class="form-group col-md-6">'+
				  	'<label for="telefonoMaquileros">Tel&eacute;fono</label>'+
				  	'<input type="number" class="form-control" id="telefonoMaquileros" placeholder="222 182 23 12">'+
				  '</div>'+
			  '</div>',
			  showCancelButton: true,
		        cancelButtonColor: '#dc3545',
		        cancelButtonText: 'Cancelar',
		        confirmButtonText: 'Agregar',
		        confirmButtonColor: '#0288d1',
		  preConfirm: (nombreMaquilero) => {
			  if(document.getElementById("nombreMaquilero").value.length<1 || document.getElementById("telefonoMaquileros").value.length!=10 ){
					console.log(document.getElementById("telefonoMaquileros").value.length);
				  Swal.showValidationMessage(
							`Complete todos los campos`
					)
				}
		  },
		}).then((result) => {
			
			if (result.value && document.getElementById("nombreMaquilero").value && document.getElementById("telefonoMaquileros").value ) {
				  var maquilero = document.getElementById("nombreMaquilero").value;
				  var telefono = document.getElementById("telefonoMaquileros").value;
				  $.ajax({
						type: "GET",
						url: "/verificar-duplicado-produccion",
						data: {
							'Lookup': maquilero,
							'descripcion': telefono,
							'Tipo': "Maquilero"


						}

					}).done(function (data) {
						console.log ("dataaa----->"+data)
						if(data==false){

							$.ajax({
								type: "POST",
								url: "/guardar-catalogo-produccion",
								data: {
									"_csrf": $('#token').val(),
									'maquilero': maquilero,
									'telefono': telefono

								}

							}).done(function (data) {
								listarProceso();
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
function editarMaquileros(e){
	Swal.fire({
		  title: 'Editar maquileros',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-md-6">'+
				  	'<label for="nombreMaquilerosE">Nombre</label>'+
				  	'<input type="text" class="form-control" id="nombreMaquilero" name="nombreMaquilero" value="'+e.getAttribute("nombre")+'" placeholder="Maquileros S.A. de C.V.">'+
				  '</div>'+
				  '<div class="form-group col-md-6">'+
				  	'<label for="telefonoMaquilerosE">Tel&eacute;fono</label>'+
				  	'<input type="number" class="form-control" value="'+e.getAttribute("descripcion")+'" id="telefonoMaquileros" placeholder="222 182 23 12">'+
				  '</div>'+
				  '<input type="hidden" value=" ' + e.getAttribute("idlookup") + ' " class="swal2-input" id="idlookup" placeholder="Pantalón">' +
			  '</div>',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar',
		  cancelButtonText: 'Cancelar' ,
		  preConfirm: (nombreMaquilero) => {
			  if(document.getElementById("nombreMaquilero").value.length<1 || document.getElementById("telefonoMaquileros").value.length!=10 ){
					console.log(document.getElementById("telefonoMaquileros").value.length);
				  Swal.showValidationMessage(
							`Complete todos los campos`
					)
				}
		  },
		}).then((result) => {
			
			if (result.value && document.getElementById("nombreMaquilero").value && document.getElementById("telefonoMaquileros").value ) {
				  var maquilero = document.getElementById("nombreMaquilero").value;
				  var telefono = document.getElementById("telefonoMaquileros").value;
				  var idLookup = document.getElementById("idlookup").value;
				  $.ajax({
						type: "GET",
						url: "/verificar-duplicado-produccion",
						data: {
							'Lookup': maquilero,
							'descripcion': telefono,
							'Tipo': "Maquilero"


						}

					}).done(function (data) {
						console.log ("dataaa----->"+data)
						if(data==false){

							$.ajax({
								type: "POST",
								url: "/editar-catalogo-produccion",
								data: {
									"_csrf": $('#token').val(),
									'maquilero': maquilero,
									'telefono': telefono,
									'idLookup' : idLookup

								}

							}).done(function (data) {
								listarMaquila();
							});
							Swal.fire({
								position: 'center',
								icon: 'success',
								title: 'Actualizado correctamente',
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
function bajaMaquileros(id){
	Swal.fire({
		  title: '¿Deseas dar de baja el maquilero?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar'
		}).then((result) => {
		  if (result.value && id != null) {

	            $.ajax({
	                type: "POST",
	                url: "/baja-catalogo-produccion",
	                data: {
	                    "_csrf": $('#token').val(),
	                    'id': id
	                }

	            }).done(function(data) {

	            	listarMaquila();
	            });
	            Swal.fire({
	                position: 'center',
	                icon: 'success',
	                title: 'Dado de baja correctamente',
	                showConfirmButton: false,
	                timer: 1250
	            })
		  }
		});
}
function altaMaquileros(id){
	Swal.fire({
		  title: '¿Deseas reactivar el maquilero?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar'
		}).then((result) => {
		  if (result.value && id != null) {
			  $.ajax({
	                type: "POST",
	                url: "/reactivar-catalogo-produccion",
	                data: {
	                    "_csrf": $('#token').val(),
	                    'idcatalogo': id
	                }

	            }).done(function(data) {

	            	listarMaquila();
	            });
	            Swal.fire({
	                position: 'center',
	                icon: 'success',
	                title: 'Reactivado correctamente',
	                showConfirmButton: false,
	                timer: 1250
	            })
		  }
		});
}

// ALMACEN LOGICO
$('#detalleEntrega').on('shown.bs.modal', function () {
	$(document).off('focusin.modal');
});
function agregarEntrega(){
	Swal.fire({
		  title: 'Nueva falla de entrega',
		  html:
		  '<div class="row">'+
			  '<div class="form-group col-md-12">'+
				  '<label for="fallaEntrega">Falla de entrega</label>'+
				  '<input type="text" class="form-control" id="fallaEntrega" placeholder="Especificar">'+
			  '</div>'+
		  '</div>',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar',
		  cancelButtonText: 'Cancelar'
		}).then((result) => {
		  if (result.value) {
			  Swal.fire({
				  position: 'center',
				  icon: 'success',
				  title: 'Falla de entrega agregada correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}
function editarEntrega(){
	Swal.fire({
		  title: 'Editar falla de entrega',
		  html:
		  '<div class="row">'+
			  '<div class="form-group col-md-12">'+
				  '<label for="fallaEntregaE">Falla de entrega</label>'+
				  '<input type="text" class="form-control" id="fallaEntregaE" placeholder="Especificar">'+
			  '</div>'+
		  '</div>',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar',
		  cancelButtonText: 'Cancelar'
		}).then((result) => {
		  if (result.value) {
			  Swal.fire({
				  position: 'center',
				  icon: 'success',
				  title: 'Falla de entrega editada correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}
function bajaEntrega(){
	Swal.fire({
		  title: '¿Deseas dar de baja la falla de entrega?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar',
		  cancelButtonText: 'Cancelar'
		}).then((result) => {
		  if (result.value) {
			  Swal.fire({
				  position: 'center',
				  icon: 'success',
				  title: 'Falla de entrega dada de baja correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}
function altaEntrega(){
	Swal.fire({
		  title: '¿Deseas dar de alta la falla de entrega?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar',
		  cancelButtonText: 'Cancelar'
		}).then((result) => {
		  if (result.value) {
			  Swal.fire({
				  position: 'center',
				  icon: 'success',
				  title: 'Falla de entrega dada de alta correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}