(function () {
	var _div = document.createElement('div');
	jQuery.fn.dataTable.ext.type.search.html = function (data) {
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
$(document).ready(function () {

	// listarEspecificaciones();
	// $('#idtable2').dataTable().fnClearTable();
	listarPrendas();
	if ($('#empleado2').val()) {
		var empl = $('#empleado2').val();
		var ped = $('#idpedido').val();
		const variableempedido = [empl, ped];
		document.getElementById("empleado").value = empl;
		var selectobject;
		selectobject = document.getElementById("empleado");
		selectobject.disabled = true;
		$('#empleado').selectpicker('refresh');
		listarPrendas(...variableempedido);
		muestraPrendas();
	}


});


// //////////////////////77
function muestraPrendas() {

	var idempleado = document.getElementById("empleado").value;
	var idpedido = document.getElementById("idpedido").value;
	var res0 = idempleado.split("-");
	$.ajax({
		type: "GET",
		url: "/prendas-empleado",
		data: {
			'idempleado': res0[0],
			'idpedido': idpedido

			// ,'Descripcion':Descripcion
		}

	}).done(function (data) {


		$("#prenda").empty();
		// /////////////////////7
		for (i = 0; i < data.length; i++) {
			var id = data[i][1];
			var name = data[i][2];
			// $("#prenda").append("<option
			// value='"+name+"'>"+name+"</option>");
			$("#prenda").append("<option value='" + id + "'>" + name + "</option>");

		}
		$('#prenda').selectpicker('refresh');

		// ///////////////////777

	});
}

// //////////////////////777
function listarEspecificaciones(prenda, id) {

	// /importante poner el id prenda insertado previamente en el ajax ya que toma
	// el del formulario
	var idprenda = id;
	var prenda2 = prenda;
	var quitar = "#quitar2";
	var contenedor = "#contenedor";
	var modal = "#modalEspecificaciones";
	var tabla = "#idtable2";
	var wr = '_wrapper';

	$(modal.concat(prenda2)).on('hidden.bs.modal', function () {

		$(quitar.concat(prenda2)).remove();
		$(tabla.concat(prenda2, wr)).remove();
		$(tabla.concat(prenda2)).DataTable().destroy();

	});

	$.ajax({
		method: "GET",
		url: "/listar-especificacion",
		data: {
			"idpedido": $('#idpedido').val(),
			"idempleado": $('#empleado').val(),
			"idprenda": idprenda
		},
		success: (data) => {
			$(quitar.concat(prenda2)).remove();
			$(contenedor.concat(prenda2)).append("<div class='modal-body' id='quitar2" + prenda2 + "'>" +
				"<table class='table table-striped table-bordered' id='idtable2" + prenda2 + "' style='width:100%'>" +
				"<thead>" +
				"<tr>" +
				"<th>Pulgadas</th>" +
				"<th>Especificación</th>" +
				"<th>Eliminar</th>" +
				"</tr>" +
				"</thead>" +
				"</table>" + "</div>");
			var a;
			var b = [];

			for (i in data) {

				a = [
					"<tr>" +
					"<td>" + data[i][5] + "</td>",
					"<td>" + data[i][8] + "</td>",
					"<td style='text-align: center'>" +
					"<button onclick=eliminarEspecificacion('" + data[i][0] + "','" + data[i][11] + "','" + data[i][2] + "') class='btn btn-danger' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'>Eliminar</button>" +
					"</td>" +

					"<tr>"
				];
				b.push(a);
			}

			var tablaes = "#idtable2";
			var tablaColores = $(tablaes.concat(prenda2)).DataTable({
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
// //////////////////////////777
function eliminarEspecificacion(e, prenda, idprenda) {
	var empleado = $('#empleado').val();
	var pedido = $('#idpedido').val();
	var prenda = prenda;
	var idprenda = idprenda;
	Swal.fire({
		  title: '&iquest;Est&aacute; seguro que desea eliminar esta especificación?',
		  text: "Esta acción es irrevesible",
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  cancelButtonText: 'Cancelar',
		  confirmButtonText: 'Si, eliminar',
		  reverseButtons: true

		}).then((result) => {
		  if (result.value) {
			 /////
			  $.ajax({
					type: "DELETE",
					url: "/eliminar-especificacion",
					data: {
						"_csrf": $('#token').val(),
						'id': e

					}

				}).done(function (data) {
					if (data == true) {
						listarEspecificaciones(prenda, idprenda);
						listarPrendas(empleado, pedido);
						Swal
						.fire({
							position: 'center',
							icon: 'success',
							title: 'Especificacíón eliminada correctamente',
							showConfirmButton: false,
							timer: 2500
						})
					} else {
						Swal
							.fire({
								position: 'center',
								icon: 'error',
								title: 'Algo ha fallado al intentar eliminar reintente por favor',
								showConfirmButton: false,
								timer: 2500
							})

					}

				});
			  ///////////7
		  }
		})
	
}
// /////////////77
function prendaEmpleado() {
	$('#idtable2').dataTable().fnClearTable();
	// listarPrendas();
	var emple = $('#empleado').val();
	var ped = $('#idpedido').val();
	const variablesempedido = [emple, ped];
	listarPrendas(...variablesempedido);


	$("#eliminarboton").remove();

}

// ////////////////////////////777
function listarPrendas(empleado, pedido) {

	var idempleado = empleado;
	var idpedido = pedido;
	$.ajax({
		method: "GET",
		url: "/prenda-empleado",
		data: {

			"idpedido": idpedido,
			"idempleado": idempleado
		},
		success: (data) => {
			$('#quitar3').remove();
			$('#contenedor2').append("<div class='modal-body' id='quitar3'>" +
				"<table class='table table-striped table-bordered' id='idtable3' style='width:100%'>" +
				"<thead>" +
				"<tr>" +
				"<th>Prenda</th>" +
				"<th>Talla</th>" +
				"<th>Largo</th>" +
				"<th>Especificaciones</th>" +
				"<th>Acciones</th>" +
				"</tr>" +
				"</thead>" +
				"</table>" + "</div>");
			var a;
			var b = [];

			for (i in data) {
				var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
				const ids = [data[i][0], data[i][1]];
				a = [
					"<tr>" +
					"<td>" + data[i][2] + "</td>",
					"<td>" + data[i][3] + "</td>",
					"<td>" + data[i][4] + "</td>",
					"<td><a data-toggle='modal' data-target='#modalEspecificaciones" + data[i][2] + "' onclick='agregaridmodal(\"" + data[i][2] + "\",\"" + data[i][1] + "\"); listarEspecificaciones(\"" + data[i][2] + "\",\"" + data[i][1] + "\");' class='btn icon-btn btn-success text-white mr-auto'><span class='btn-glyphicon spancircle fas fa-list-ol img-circle text-success'></span>Especificaciones</a></td>",
					"<td style='text-align: center'>" +
					"<a data-toggle='modal' data-target='#modalEditarTalla' onclick='configurarmodal(\"" + data[i][2] + "\",\"" + data[i][1] + "\",\"" + data[i][3] + "\",\"" + data[i][4] + "\");' class='btn icon-btn btn-warning mr-auto'><span class='btn-glyphicon spancircle fas fa-pen img-circle text-dark'></span>Editar</a>" +
					"</td>" +
					"<tr>"
				];
				b.push(a);
			}


			var tablaColores2 = $('#idtable3').DataTable({
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
			new $.fn.dataTable.FixedHeader(tablaColores2);
		},
		error: (e) => {

		}
	})
}

// //////////777

function guardarespecificaciones() {
	var pulgadas = $('#pulgada').val();
	var especificaciones = $('#cargaTipopedido10').val();

	if (document.getElementById("pulgada").value && document.getElementById("cargaTipopedido10").value) {

		$.ajax({
			method: "GET",
			url: "/prendas-especificaciones",
			data: {
				"idpedido": $('#idpedido').val(),
				"idempleado": $('#empleado').val(),
				"idprenda": $('#id_prenda').val()
			},
			success: (data) => {

				if (data == 'primero') {
					// ///////////////////////////obtener datos
					$.ajax({
						method: "GET",
						url: "/prenda-especificacion1",
						data: {
							"idpedido": $('#idpedido').val(),
							"idempleado": $('#empleado').val(),
							"idprenda": $('#id_prenda').val()
						},
						success: (data) => {
							var idactualizar = data[0][0];
							if (especificaciones.length == 1) {

								$
									.ajax({
										type: "POST",
										url: "/guardar-primer-concentrado-tallas",
										data: {
											"_csrf": $(
													'#token')
												.val(),
											'values[]': especificaciones[0],
											'Pulgadas': $('#pulgada').val(),
											'Id': idactualizar
										}

									})
									.done(
										function (data) {
											listarEspecificaciones(document.getElementById("nombre_prenda").value, document.getElementById("id_prenda").value);
										})

							} else {

								$
									.ajax({
										type: "POST",
										url: "/guardar-primer-concentrado-tallas",
										data: {
											"_csrf": $(
													'#token')
												.val(),
											'values[]': especificaciones,
											'Pulgadas': $('#pulgada').val(),
											'Id': idactualizar
										}

									})
									.done(
										function (data) {
											listarEspecificaciones(document.getElementById("nombre_prenda").value, document.getElementById("id_prenda").value);
										})

							}

						},
						error: (e) => {

						}
					})

				} else {
					// //////////////// segundo
					$.ajax({
						method: "GET",
						url: "/prenda-especificacion2",
						data: {
							"idpedido": $('#idpedido').val(),
							"idempleado": $('#empleado').val(),
							"idprenda": $('#id_prenda').val()
						},
						success: (data) => {
							var pedido = data[0][7];
							var empleado = data[0][1];
							var prenda = data[0][2];
							var talla = data[0][3];
							var largo = data[0][4];

							$
								.ajax({
									type: "GET",
									url: "/verifduplicadoconcentradotalla",
									data: {
										'values[]': especificaciones,
										'Empleado': empleado,
										'Largo': largo,
										'PrendaCliente': prenda,
										'Talla': talla,
										'Pulgadas': pulgadas,
										'Pedido': $('#idpedido').val()

									}

								})
								.done(
									function (datos) {
										if (datos == false) {
											$.ajax({
													type: "POST",
													url: "/guardar-concentrado-tallas1",
													data: {
														"_csrf": $(
																'#token')
															.val(),
														'values[]': especificaciones,
														'Empleado': empleado,
														'Largo': largo,
														'PrendaCliente': prenda,
														'Talla': talla,
														'IdPedido': pedido,
														'Pulgadas': pulgadas
													}

												})
												.done(
													function (data) {
														listarEspecificaciones(document.getElementById("nombre_prenda").value, document.getElementById("id_prenda").value);

													})
										} else {
											Swal
												.fire({
													position: 'center',
													icon: 'error',
													title: 'Ya ha registrado esa especificacion anteriormente no se ha insertado verifique en la siguiente tabla',
													showConfirmButton: false,
													timer: 2500
												})

										}
									})
						}

					})

				}

			},
			error: (e) => {

			}
		})

	} else {

		Swal.fire({
			icon: 'error',
			title: 'Ingrese todos los campos requeridos',
			showConfirmButton: false,
			timer: 2500
		})

	}

}


function eliminarPrenda(e, a) {

	var id_empleado = e;
	var id_prenda = a;
	Swal.fire({
		title: '¿Está seguro que desea eliminar esta prenda?',
		text: "Esta acción es permanente",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: '¡Si eliminar!'
	}).then((result) => {
		if (result.value) {

			$.ajax({
				type: "DELETE",
				url: "/eliminar-prenda",
				data: {
					"_csrf": $('#token').val(),
					'id_empleado': id_empleado,
					'id_prenda': id_prenda

				}

			}).done(function (data) {

				if (data == true) {
					listarPrendas();
				} else {
					Swal
						.fire({
							position: 'center',
							icon: 'error',
							title: 'Algo ha fallado al intentar eliminar reintente por favor',
							showConfirmButton: false,
							timer: 2500
						})

				}

			});
			// ///////////7
			Swal.fire(
				'¡Eliminado!',
				'La prenda ha sido borrada.',
				'success'
			)
			listarPrendas();
		}
	})

}

function eliminarTodo() {

	Swal.fire({
		title: '¿Está seguro que desea eliminar todas las prendas?',
		text: "Esta acción es permanente",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: '¡Si eliminar!'
	}).then((result) => {
		if (result.value) {

			$.ajax({
				type: "DELETE",
				url: "/eliminar-todo",
				data: {
					"_csrf": $('#token').val(),
					'id_empleado': $('#empleado').val()


				}

			}).done(function (data) {
				if (data == true) {
					listarPrendas();
				} else {
					Swal
						.fire({
							position: 'center',
							icon: 'error',
							title: 'Algo ha fallado al intentar eliminar reintente por favor',
							showConfirmButton: false,
							timer: 2500
						})

				}

			});
			// ///////////7
			Swal.fire(
				'¡Eliminado!',
				'Las prendas han sido borradas.',
				'success'
			)
			listarPrendas();
		}
	})

}


// Remove accented character from search input as well
$('#example_filter input[type=search]').keyup(function () {
	var table = $('#tablaEditable1').DataTable();
	table.search(
		jQuery.fn.DataTable.ext.type.search.html(this.value)
	).draw();
});
(function () {
	var _div = document.createElement('div');
	jQuery.fn.dataTable.ext.type.search.html = function (data) {
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
// //////////////////////////nuevo
function Savenew() {
	$('html, body').animate({
		scrollTop: $("#modalLogin").offset().top
	}, 2000);
	var selectobject;
	selectobject = document.getElementById("empleado");
	selectobject.disabled = true;
	$('#empleado').selectpicker('refresh');
	var selectobject2;
	selectobject2 = document.getElementById("prenda");
	selectobject2.disabled = false;
	$('#prenda').selectpicker('refresh');
	// ///////////////////////
	var selectobject3;
	selectobject3 = document.getElementById("talla");
	selectobject3.disabled = false;
	$('#talla').selectpicker('refresh');
	// ////////////////////
	var selectobject4;
	selectobject4 = document.getElementById("largo");
	selectobject4.disabled = false;
	$('#largo').selectpicker('refresh');


}

function guardar() {
	if ($('#empleado2').val()) {
		var pedido = $('#idpedido').val();
		Swal.fire({
			icon: 'success',
			title: 'Guardado correctamente',
			showConfirmButton: false,
			timer: 2500
		})
		window.location.href = "/concentrado-de-tallas/" + pedido + "";
	} else {
		Swal.fire({
			icon: 'success',
			title: 'Guardado correctamente',
			showConfirmButton: false,
			timer: 2500
		})
		location.reload();
	}
}
function insercionesposteriores(){
	
	$( "#prenda" ).change(function() {
		
		removeOptions(document.getElementById('talla'));
		$('#talla').selectpicker('refresh');
   	 var prenda2=prenda.options[prenda.selectedIndex].text;
   	 console.log(prenda2);
   	console.log( $("#genero").val());
   	var selectobjectemp;
		selectobjectemp = document.getElementById("empleado");
		selectobjectemp.disabled = true;
		$('#empleado').selectpicker('refresh');
		var selectobjectprend;
		selectobjectprend = document.getElementById("prenda");
		console.log(selectobjectprend);
		selectobjectprend.disabled = true;
		$('#prenda').selectpicker('refresh');
		var selectobjectgenero;
		selectobjectgenero = document.getElementById("genero");
		console.log(selectobjectgenero);
		selectobjectgenero.disabled = true;
		$('#genero').selectpicker('refresh');
		///////////////////////////////777
		$.ajax({
       type: "GET",
       url: "/obtener-posicion-prenda",
       data:{ "Prenda": prenda2},
       success: (data) => {
       	var posicion=data;
       	console.log(posicion);
       	$.ajax({
               type: "GET",
               url: "/obtener-tallas",
               data:{ "Posicion": posicion,
               	"Genero": $("#genero").val()},
               success: (data) => {
               	console.log(data);
               	var select = document.getElementById("talla");
               	for(index in data) {
               	    select.options[select.options.length] = new Option(data[index].nombreLookup, data[index].idLookup);
               	}
               	$('.selectpicker').selectpicker('refresh');
               }
       	});//////////////quitar; sino funciona
       }
	});
		
   	
   	////////////////poner los 2 ajax get
   	////despues bloquear genero y al botonazo desbloquear prenda
   	});
	
}

function removeOptions(selectElement) {
	   var i, L = selectElement.options.length - 1;
	   for(i = L; i >= 0; i--) {
	      selectElement.remove(i);
	   }
	}


function GuardarPrendaTalla() {
	$('#cargaTipopedido10').val();
	var empleado = $('#empleado').val();
	$('#largo').val();
	$('#prenda').val();
	$('#talla').val();
	$('#pulgada').val();
	var pedido = $('#idpedido').val();


	var values = $('#cargaTipopedido10').val();

	if ($('#empleado').val() && $('#largo').val() && $('#prenda').val() && $('#talla').val() && $('#idpedido').val()) {


		$
			.ajax({
				type: "POST",
				url: "/guardar-concentrado-tallas",
				data: {
					"_csrf": $(
							'#token')
						.val(),
					'values': values,
					'Empleado': $(
							'#empleado')
						.val(),
					'Largo': $(
							'#largo')
						.val(),
					'PrendaCliente': $(
							'#prenda')
						.val(),
					'Talla': $(
							'#talla')
						.val(),
					'IdPedido': $('#idpedido').val()

				}

			})
			.done(
				function (data) {
					var x = document.getElementById("prenda");
					x.remove(x.selectedIndex);
					var y = document.getElementById("talla");
					y.remove(y.selectedIndex);
					listarPrendas(empleado, pedido);
					var selectobject;
					selectobject = document.getElementById("empleado");
					selectobject.disabled = true;
					$('#empleado').selectpicker('refresh');
					Swal.fire({
						icon: 'success',
						title: 'Prenda guardada correctamente',
						showConfirmButton: false,
						timer: 2500
					})
					/////////////777
					var selectobjectprend2;
					selectobjectprend2 = document.getElementById("prenda");
					console.log(selectobjectprend2);
					selectobjectprend2.disabled = false;
					$('#prenda').selectpicker('refresh');
					$("#talla").empty();
					insercionesposteriores();
					//listarPrendas(empleado, pedido);

					/////////////77777
				});
	} else {
		Swal.fire({
			icon: 'error',
			title: 'Ingrese todos los campos requeridos',
			showConfirmButton: false,
			timer: 2500
		})
		//listarPrendas(empleado, pedido);
		var selectobject;
		selectobject = document.getElementById("empleado");
		selectobject.disabled = true;
		$('#empleado').selectpicker('refresh');


	}


}
// event.preventDefault();

// ///////////////////77
function editarprenda() {
	var empleado = document.getElementById("empleado").value; // sirve
	var prenda = document.getElementById("idprendaedit").value;
	var pedido = document.getElementById("idpedido").value;
	if (document.getElementById("talla22").value.length > 0 && document.getElementById("largo22").value.length == 0) {
		$.ajax({
				type: "POST",
				url: "/editar",
				data: {
					"_csrf": $('#token')
						.val(),
					'talla': $('#talla22').val(),
					'pedido': pedido,
					'empleado': empleado,
					'prenda': prenda


				}

			})
			.done(
				function (data) {
					if (data == true) {
						Swal
							.fire({
								position: 'center',
								icon: 'success',
								title: 'editado exitosamente',
								showConfirmButton: false,
								timer: 2500
							})
						listarPrendas(empleado, pedido);
						$('#modalEditarTalla').modal('hide');
					} else {
						Swal
							.fire({
								position: 'center',
								icon: 'error',
								title: 'Algo salio mal reintente por favor',
								showConfirmButton: false,
								timer: 2500
							})
						$('#modalEditarTalla').modal('hide');

					}
				})
		// //////////////7
	} else if (document.getElementById("talla22").value.length == 0 && document.getElementById("largo22").value.length > 0) {
		$.ajax({
				type: "POST",
				url: "/editar",
				data: {
					"_csrf": $('#token')
						.val(),
					'largo': $('#largo22').val(),
					'pedido': pedido,
					'empleado': empleado,
					'prenda': prenda


				}

			})
			.done(
				function (data) {
					if (data == true) {
						Swal
							.fire({
								position: 'center',
								icon: 'success',
								title: 'editado exitosamente',
								showConfirmButton: false,
								timer: 2500
							})
						listarPrendas(empleado, pedido);
						$('#modalEditarTalla').modal('hide');
					} else {
						Swal
							.fire({
								position: 'center',
								icon: 'error',
								title: 'Algo salio mal reintente por favor',
								showConfirmButton: false,
								timer: 2500
							})
						$('#modalEditarTalla').modal('hide');
					}
				})
		// ///////////
	} else if (document.getElementById("talla22").value.length == 0 && document.getElementById("largo22").value.length == 0) {} else {
		$.ajax({
				type: "POST",
				url: "/editar",
				data: {
					"_csrf": $('#token')
						.val(),
					'talla': $('#talla22').val(),
					'largo': $('#largo22').val(),
					'pedido': pedido,
					'empleado': empleado,
					'prenda': prenda


				}

			})
			.done(
				function (data) {
					if (data == true) {
						Swal
							.fire({
								position: 'center',
								icon: 'success',
								title: 'editado exitosamente',
								showConfirmButton: false,
								timer: 2500
							})
						listarPrendas(empleado, pedido);
						$('#modalEditarTalla').modal('hide');
					} else {
						Swal
							.fire({
								position: 'center',
								icon: 'error',
								title: 'Algo salio mal reintente por favor',
								showConfirmButton: false,
								timer: 2500
							})
						$('#modalEditarTalla').modal('hide');
					}
				})

	}

}

// ////////////777
function myFunction2() {
	document.getElementById("empleado").disabled = true;
}

// //////////////fin nuevo

// Remove accented character from search input as
$('#example_filter input[type=search]').keyup(function () {
	var table = $('#tablaEditable2').DataTable();
	table.search(
		jQuery.fn.DataTable.ext.type.search.html(this.value)
	).draw();
});