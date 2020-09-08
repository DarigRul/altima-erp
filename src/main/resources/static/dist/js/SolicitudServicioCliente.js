$(document).ready(function () {
	
	
});

/*
 * Esta funcion cambia la direccion, el telefono y el dirigirse con, del formulario en base al cliente seleccionado
 */
function CambiarDatosCliente(input){
	$('#direccionCita').val('');
	$('#cargandoDireccion').css('display', '');
	  $.ajax({
			type: "GET",
			url: "/get_datos_de_cliente",
			data: { id: input.value },
			success: (data) => {
				$('#direccionCita').val(data[0]);
				$('#telefonoCita').val(data[1]);
				$('#dirigirseCita').val(data[2]);
				
				  $.ajax({
						type: "GET",
						url: "/get_pedidos_de_cliente",
						data: { id: input.value },
						success: (data2) => {
							$('#pedidoCita').empty();
							for(var i = 0; i < data2.length; i++){
								$('#pedidoCita').append("<option value='" + data2[i][0] + "'>" + data2[i][1] + "</option>");
							}
							$('#pedidoCita').selectpicker("refresh");
							$('#cargandoDireccion').css('display', 'none');
						},
						error: (e) => {
							console.log(e);
						}
					});
				  
			},
			error: (e) => {
				console.log(e);
			}
		});
}

function CargarSelectsDeNuevo(){
	
	//Se muestran los spinners
	$('#CargandoGenerosSastres').css('display', '');
	$('#CargandoGenerosAuxiliarVentas').css('display', '');
	$('#CargandoMaterialesMaterial').css('display', '');
	$('#CargandoGenerosCorridas').css('display', '');
	
	//Se solicitan los nuevos datos
	$.ajax({
		type: "GET",
		url: "/get_nuevos_selects",
		data: { "idSolicitud": $('#idSolicitudServicioAlCliente').val() },
		success: (data) => {
			  console.log(data);
			  //Pegamos los de Sastre
			  $('#generoSastre').empty();
			  for(var sastre = 0; sastre < data[0].length; sastre++){
				  $('#generoSastre').append("<option value='" + data[0][sastre] + "'>" + data[0][sastre] + "</option>");
			  }
			  $('#generoSastre').selectpicker("refresh");
			  $('#CargandoGenerosSastres').css('display', 'none');
			  
			  //Pegamos los de Auxiliares
			  $('#generoAuxiliarVentas').empty();
			  for(var auxiliar = 0; auxiliar < data[1].length; auxiliar++){
				  $('#generoAuxiliarVentas').append("<option value='" + data[1][auxiliar] + "'>" + data[1][auxiliar] + "</option>");
			  }
			  $('#generoAuxiliarVentas').selectpicker("refresh");
			  $('#CargandoGenerosAuxiliarVentas').css('display', 'none');
			  
			  //Pegamos los de Materiales
			  $('#materialMaterial').empty();
			  for(var material = 0; material < data[2].length; material++){
				  $('#materialMaterial').append("<option value='" + data[2][material][0] + "'>" + data[2][material][1] + "</option>");
			  }
			  $('#materialMaterial').selectpicker("refresh");
			  $('#CargandoMaterialesMaterial').css('display', 'none');
			  
			  //Pegamos los de las Corridas
			  $('#generoCorridas').empty();
			  for(var corrida = 0; corrida < data[3].length; corrida++){
				  $('#generoCorridas').append("<option value='" + data[3][corrida] + "'>" + data[3][corrida] + "</option>");
			  }
			  $('#generoCorridas').selectpicker("refresh");
			  $('#CargandoGenerosCorridas').css('display', 'none');
		},
		error: (e) => {
			console.log(e);
		}
	});
}

/*
 * Esta funcion guarda la solicitud de servicio al cliente actual
 */
function GuardarSolicitudServicioCliente(){

	var Solicitud = {fechaCita: $('#fechaCita').val(), clienteID: $('#clienteCita').val(), fechaSalida: $('#fechaSalida').val(),
			actividadCita: $('#actividadCita').val(), damasAtender: parseInt($('#damasAtender').val()), idPedido: $('#pedidoCita').val(),
			caballerosAtender: parseInt($('#caballerosAtender').val()), comentarios: $('#comentariosCita').val()}
	console.log(Solicitud);
	$.ajax({
		type: "POST",
		url: "/save_solicitud_servicio_cliente",
		data: { 
			"_csrf": $('#token').val(),
			"Solicitud": JSON.stringify(Solicitud)
		},
		success: (data) => {
			console.log(data);
			$('#idSolicitudServicioAlCliente').val(data.idSolicitudServicioAlCliente);
			Swal.fire({
				icon: 'success',
				title: 'Guardado!',
				text: 'Se ha guardado el registro.'
			  })
		},
		error: (e) => {
			console.log(e);
		}
	});
}

function ValidarTabs(id){
	if(parseInt($('#idSolicitudServicioAlCliente').val()) > 0){
		$('#' + id + '-tab-boton').click();
	}else{
		console.log('no paso');
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'La cantidad debe de ser mayor a 0!'
		  })
	}
}


function BorrarFilaSastre(input, id){
	$.ajax({
		type: "DELETE",
		url: "/delete_elemento_from_solicitud",
		data: { 
			"_csrf": $('#token').val(),
			"id": id,
			"entidad": "Sastre"
		},
		success: (data) => {
			tablaSastre.row( $(input).parents('tr') ).remove().draw();
			Swal.fire({
				icon: 'success',
				title: 'Eliminado!',
				text: 'Se ha eliminado el registro.'
			  })
			  //Se cargan de new los selects
			  CargarSelectsDeNuevo();
		},
		error: (e) => {
			console.log(e);
		}
	});
}

function BorrarFilaAuxiliarVentas(input, id){
	$.ajax({
		type: "DELETE",
		url: "/delete_elemento_from_solicitud",
		data: { 
			"_csrf": $('#token').val(),
			"id": id,
			"entidad": "AuxiliarVentas"
		},
		success: (data) => {
			tablaAuxiliarVentas.row( $(input).parents('tr') ).remove().draw();
			Swal.fire({
				icon: 'success',
				title: 'Eliminado!',
				text: 'Se ha eliminado el registro.'
			  })
			//Se cargan de new los selects
			  CargarSelectsDeNuevo();
		},
		error: (e) => {
			console.log(e);
		}
	});
}

function BorrarFilaMaterial(input, id){
	$.ajax({
		type: "DELETE",
		url: "/delete_elemento_from_solicitud",
		data: { 
			"_csrf": $('#token').val(),
			"id": id,
			"entidad": "Material"
		},
		success: (data) => {
			tablaMaterial.row( $(input).parents('tr') ).remove().draw();
			Swal.fire({
				icon: 'success',
				title: 'Eliminado!',
				text: 'Se ha eliminado el registro.'
			  })
			//Se cargan de new los selects
			  CargarSelectsDeNuevo();
		},
		error: (e) => {
			console.log(e);
		}
	});
}

function BorrarFilaCorrida(input, id){
	$.ajax({
		type: "DELETE",
		url: "/delete_elemento_from_solicitud",
		data: { 
			"_csrf": $('#token').val(),
			"id": id,
			"entidad": "Corrida"
		},
		success: (data) => {
			tablaCorrida.row( $(input).parents('tr') ).remove().draw();
			Swal.fire({
				icon: 'success',
				title: 'Eliminado!',
				text: 'Se ha eliminado el registro.'
			  })
			//Se cargan de new los selects
			  CargarSelectsDeNuevo();
		},
		error: (e) => {
			console.log(e);
		}
	});
}

/**
 * 
 * 
 * Sastre 
 * 
 *
 **/
function GuardarSastre(){
	
	var validacion = ValidarGuardarSastre();
	if(validacion){
		
		//Se agrega a la BD
		$.ajax({
			type: "POST",
			url: "/save_servicio_cliente_sastre",
			data: { 
				"_csrf": $('#token').val(),
				"idSolicitud": $('#idSolicitudServicioAlCliente').val(),
				"genero": $('#generoSastre').val(),
				"cantidad": $('#cantidadSastre').val()
			},
			success: (data) => {
				//Se cargan de new los selects
				  CargarSelectsDeNuevo();

				var fila = null;
				fila = tablaSastre.row.add([ data.genero, data.cantidad, "<td class='text-center'>" +
			  		"<button type='button' class='btn icon-btn btn-danger text-white' onclick='BorrarFilaSastre(this, " + data.idSolicitudServicioAlClienteSastre  + ")' >" + 
		  			"<span class='btn-glyphicon fas fa-times fa-lg img-circle text-danger'></span>Eliminar</button></td>" ]).draw().node();
				$( fila ).prop('id', "FILA_SASTRE_" + data.idSolicitudServicioAlClienteSastre);
				fila = null;
				
				Swal.fire({
					icon: 'success',
					title: 'Guardado!',
					text: 'Se ha guardado el registro.'
				  })
			},
			error: (e) => {
				console.log(e);
			}
		});
	}
}

function ValidarGuardarSastre(){
	
	if($('#generoSastre').val() != null && $('#cantidadSastre').val() != null){
		return true;
	}
	else{
		return false;
	}
	
}
/**
 * 
 * 
 * Auxiliar de Ventas
 * 
 *
 **/
function GuardarAuxiliarVentas(){
	
	var validacion = ValidarGuardarAuxiliarVentas();
	if(validacion){
		
		//Se agrega a la BD
		$.ajax({
			type: "POST",
			url: "/save_servicio_cliente_auxiliar_ventas",
			data: { 
				"_csrf": $('#token').val(),
				"idSolicitud": $('#idSolicitudServicioAlCliente').val(),
				"genero": $('#generoAuxiliarVentas').val(),
				"cantidad": $('#cantidadAuxiliarVentas').val()
			},
			success: (data) => {
				
				var fila = null;
				fila = tablaAuxiliarVentas.row.add([ data.genero, data.cantidad, "<td class='text-center'>" +
			  		"<button type='button' class='btn icon-btn btn-danger text-white' onclick='BorrarFilaAuxiliarVentas(this, " + data.idSolicitudServicioAlClienteAuxiliarVentas  + ")' >" + 
		  			"<span class='btn-glyphicon fas fa-times fa-lg img-circle text-danger'></span>Eliminar</button></td>" ]).draw().node();
				$( fila ).prop('id', "FILA_AUXILIAR_" + data.idSolicitudServicioAlClienteAuxiliarVentas);
				fila = null;
				
				//Se cargan de new los selects
				  CargarSelectsDeNuevo();
				
				Swal.fire({
					icon: 'success',
					title: 'Guardado!',
					text: 'Se ha guardado el registro.'
				  })
			},
			error: (e) => {
				console.log(e);
			}
		});
	}
}

function ValidarGuardarAuxiliarVentas(){
	
	if($('#generoAuxiliarVentas').val() != null && $('#cantidadAuxiliarVentas').val() != null){
		return true;
	}
	else{
		return false;
	}
}
/**
 * 
 * 
 * Material
 * 
 *
 **/
function GuardarMaterial(){
	var validacion = ValidarGuardarMaterial();
	if(validacion){
		
		//Se agrega a la BD
		$.ajax({
			type: "POST",
			url: "/save_servicio_cliente_material",
			data: { 
				"_csrf": $('#token').val(),
				"idSolicitud": $('#idSolicitudServicioAlCliente').val(),
				"material": $('#materialMaterial').val(),
				"cantidad": $('#cantidadMaterial').val()
			},
			success: (data) => {
				console.log(data);
				var fila = null;
				fila = tablaMaterial.row.add([ data.idLookup, data.cantidad, "<td class='text-center'>" +
			  		"<button type='button' class='btn icon-btn btn-danger text-white' onclick='BorrarFilaMaterial(this, " + data.idSolicitudServicioAlClienteMaterial  + ")' >" + 
		  			"<span class='btn-glyphicon fas fa-times fa-lg img-circle text-danger'></span>Eliminar</button></td>" ]).draw().node();
				$( fila ).prop('id', "FILA_MATERIAL_" + data.idSolicitudServicioAlClienteMaterial);
				fila = null;
				
				//Se cargan de new los selects
				  CargarSelectsDeNuevo();
				
				Swal.fire({
					icon: 'success',
					title: 'Guardado!',
					text: 'Se ha guardado el registro.'
				  })
			},
			error: (e) => {
				console.log(e);
			}
		});
	}
}

function ValidarGuardarMaterial(){
	
	if($('#materialMaterial').val() != null && $('#cantidadMaterial').val() != null){
		return true;
	}
	else{
		return false;
	}
}
/**
 * 
 * 
 * Corridas
 * 
 *
 **/
function GuardarCorridas(){
	var validacion = ValidarGuardarCorridas();
	if(validacion){
		
		//Se agrega a la BD
		$.ajax({
			type: "POST",
			url: "/save_servicio_cliente_corrida",
			data: { 
				"_csrf": $('#token').val(),
				"idSolicitud": $('#idSolicitudServicioAlCliente').val(),
				"genero": $('#generoCorridas').val(),
				"tipo": $('#tipoCorridas').val()
			},
			success: (data) => {
				
				var fila = null;
				fila = tablaCorrida.row.add([ data.genero, data.tipo, "<td class='text-center'>" +
			  		"<button type='button' class='btn icon-btn btn-danger text-white' onclick='BorrarFilaCorrida(this, " + data.idSolicitudServicioAlClienteCorrida  + ")' >" + 
		  			"<span class='btn-glyphicon fas fa-times fa-lg img-circle text-danger'></span>Eliminar</button></td>" ]).draw().node();
				$( fila ).prop('id', "FILA_CORRIDA_" + data.idSolicitudServicioAlClienteCorrida);
				fila = null;
				
				//Se cargan de new los selects
				  CargarSelectsDeNuevo();
				
				Swal.fire({
					icon: 'success',
					title: 'Guardado!',
					text: 'Se ha guardado el registro.'
				  })
			},
			error: (e) => {
				console.log(e);
			}
		});
	}
}

function ValidarGuardarCorridas(){
	
	if($('#generoCorridas').val() != null && $('#tipoCorridas').val() != null){
		return true;
	}
	else{
		return false;
	}
}