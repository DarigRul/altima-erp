/**
 * author Víctor Hugo García Ilhuicatzi 
 * colaborador Erik Zempoaltecatl Zarate
 */
var TipoProcesoGlobal;
var idProcesoGlobal;
var idExplosionPrenda = [];
function listarExplosionPorProceso() {
	var idProceso = $('#procesosActivos').val();
	var programa = $('#programa').val();
	var tablaPrincipal = $('#tablaExplosionesPorProceso').DataTable();
	tablaPrincipal.rows().remove().draw();
	TipoProcesoGlobal = $("#procesosActivos option:selected").attr("descripcion");
	idProcesoGlobal = idProceso;
	$.ajax({
		method: "GET",
		url: "/listarExplosion",
		data: { idProceso,programa },
		beforeSend: function () {
			Swal.fire({
				title: 'Cargando ',
				html: 'Por favor espere',// add html attribute if you want or remove
				allowOutsideClick: false,
				timerProgressBar: true,
				onBeforeOpen: () => {
					Swal.showLoading()
				},
			});
		},
		success: (data) => {
			var finalizarProceso = "";
			var explosionPrendas = "";
			for (i in data) {

				if (data[i][11] != 2 && data[i][15] == 1) {
					finalizarProceso = '<a class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Finalizar proceso" onclick="finalizarProceso(' + data[i][0] + ')">' +
						'<i class="fas fa-thumbs-up"></i></a>';
				}
				else {
					finalizarProceso = "";
				}

				if (data[i][15] == 1) {
					explosionPrendas = '<a class="btn btn-primary text-white btn-circle btn-sm btn-alta popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Explosionar prendas" onclick="abrirTablaExplosionPrendas(' + data[i][0] + ',\'' + data[i][11] + '\')">' +
						'<i class="fas fa-certificate"></i> </a>';
				}

				else {
					explosionPrendas = '<a class="btn btn-primary text-white btn-circle btn-sm btn-alta popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Explosion de prendas" onclick="explosionarPrendas(' + data[i][0] + ')">' +
						'<i class="fas fa-certificate"></i> </a>';
				}


				tablaPrincipal.row.add([
					data[i][0],//id_explosion_pedido
					data[i][1],//id_text de pedido
					data[i][2],//nombre del cliente
					data[i][3],//coordinado
					data[i][4],//familia de prenda
					data[i][5],//descripcion de la prenda
					data[i][7],//clave de la tela
					data[i][8],//numero de prendas a confeccionar
					data[i][9],//fecha inicio
					data[i][10],//fecha fin
					(data[i][11] == 2) ? "Finalizado" : "En proceso",//estatus de proceso (0 sin proceso, 1 el proceso y 2 proceso finalizado)
					data[i][12],//programa
					data[i][13],//fecha entrega
					data[i][14],//tiempo de corte
					(data[i][15] == 1) ? "Explosionado" : "Pendiente",//estatus del registro en general (0 pendiente, 1 explosionado)
					explosionPrendas +

					($("#procesosActivos option:selected").attr("proceso") == 'Trazo' ? '<a class="btn btn-success text-white btn-circle btn-sm btn-alta popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Consumo real" onclick="consumoReal(' + data[i][0] + ',\'' + data[i][11] + '\')">' + '<i class="fas fa-exclamation"></i> </a>' : ' ') +

					finalizarProceso,
				]).draw(true);
			}

			Swal.fire({
				position: 'center',
				icon: 'success',
				title: '¡Listo!',
				showConfirmButton: false,
				timer: 500,
				onClose: () => {
					$('#SeleccionPrograma').modal("hide");
				}
			})
		},
		error: (data) => {

		}
	});

}

function finalizarProceso(idExplosion) {

	Swal.fire({
		title: '¿Deseas finalizar el proceso?',
		icon: 'question',
		showCancelButton: true,
		cancelButtonColor: '#dc3545',
		cancelButtonText: 'Cancelar',
		confirmButtonText: 'Finalizar',
		confirmButtonColor: '#28A745',
	}).then((result) => {
		if (result.value) {
			$.ajax({
				method: "GET",
				url: "/validar_no_nulos_explosion_prendas",
				data: { id: idExplosion },
				beforeSend: function () {
				},
				success: (data) => {
					if (data == 0) {
						location.href = "/finalizarProceso/" + idExplosion;
						Swal.fire({
							position: 'center',
							icon: 'success',
							title: '¡Proceso finalizado!',
							showConfirmButton: false
						})
					} else {

						Swal.fire({
							title: 'El proceso aun no esta completo',
							text: '¿Deseas continuar?',
							icon: 'warning',
							showCancelButton: true,
							cancelButtonColor: '#dc3545',
							cancelButtonText: 'Cancelar',
							confirmButtonText: 'Finalizar',
							confirmButtonColor: '#28A745',
						}).then((result) => {
							if (result.value) {

								location.href = "/finalizarProceso/" + idExplosion;
								Swal.fire({
									position: 'center',
									icon: 'success',
									title: '¡Proceso finalizado!',
									showConfirmButton: false
								})
							}
						})


					}


				},
				error: (data) => {

				}
			});


		}
	})

}

function explosionarPrendas(idExplosion) {
	$('#botonRealizo').attr("disabled", false);
	var tablaPrendasExplosionadas = $('#tablaPrendasExplosionadas').DataTable();
	tablaPrendasExplosionadas.rows().remove().draw();

	Swal.fire({
		title: '¿Deseas explosionar las prendas?',
		icon: 'question',
		showCancelButton: true,
		cancelButtonColor: '#dc3545',
		cancelButtonText: 'Cancelar',
		confirmButtonText: 'Explosionar',
		confirmButtonColor: '#28A745',
	}).then((result) => {
		if (result.value) {

			$.ajax({
				method: "GET",
				url: "/explosionarPrendas",
				data: {
					'idExplosion': idExplosion,
					'tipo': TipoProcesoGlobal
				},

				beforeSend: function () {
					Swal.fire({
						title: 'Cargando ',
						html: 'Por favor espere',// add html attribute if you want or remove
						allowOutsideClick: false,
						timerProgressBar: true,
						onBeforeOpen: () => {
							Swal.showLoading()
						},
					});
				},
				success: (data) => {
					console.log(data)
					for (i in data) {
						tablaPrendasExplosionadas.row.add([
							'<input type="checkbox" onchange="seleccionarxUNO(' + data[i][0] + ')" class="messageCheckbox" value="' + data[i][0] + '" id="check-' + data[i][0] + '" >',
							data[i][1],
							data[i][2],
							(data[i][3] == null) ? "Sin registro" : data[i][3],
							(data[i][4] == null) ? "Sin registro" : data[i][4],
							(data[i][5] == null) ? "Sin registro" : data[i][5],
							(data[i][6] == null) ? "Sin registro" : data[i][6],
							'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarExplosionPrenda(this)" id ="' + data[i][0] + '"  realizo="' + data[i][7] + '" fi="' + data[i][4] + '" ff="' + data[i][5] + '" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>'
						]).node().id = "row";
						table.draw(false);
					}
					Swal.fire({
						position: 'center',
						icon: 'success',
						title: '¡Listo!',
						showConfirmButton: false,
						timer: 500,
						onClose: () => {
							$('#tablaExplosionPrendas').modal("show");
						}
					})
				},
				error: (data) => {
					console.log("nmz")
				}
			});


		}
	})
}

function abrirTablaExplosionPrendas(idExplosion, estatus) {

	if (estatus != 2) {
		$("#botonRealizo").prop('disabled', false);

	} else {
		$("#botonRealizo").prop('disabled', true);
	}
	var tablaPrendasExplosionadas = $('#tablaPrendasExplosionadas').DataTable();
	var rows = tablaPrendasExplosionadas
		.rows()
		.remove()
		.draw();

	$.ajax({
		method: "GET",
		url: "/explosionarPrendas",
		data: {
			'idExplosion': idExplosion,
			'tipo': TipoProcesoGlobal
		},
		beforeSend: function () {
			Swal.fire({
				title: 'Cargando ',
				html: 'Por favor espere',// add html attribute if you want or remove
				allowOutsideClick: false,
				timerProgressBar: true,
				onBeforeOpen: () => {
					Swal.showLoading()
				},
			});
		},
		success: (data) => {
			console.log(data);
			for (i in data) {
				tablaPrendasExplosionadas.row.add([
					'<input type="checkbox" onchange="seleccionarxUNO(' + data[i][0] + ')" class="messageCheckbox" value="' + data[i][0] + '" id="check-' + data[i][0] + '" >',
					data[i][1],
					data[i][2],
					(data[i][3] == null) ? "Sin registro" : data[i][3],
					(data[i][4] == null) ? "Sin registro" : data[i][4],
					(data[i][5] == null) ? "Sin registro" : data[i][5],
					(data[i][6] == null) ? "Sin registro" : data[i][6],
					(estatus != 2 ? '<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarExplosionPrenda(this)" id ="' + data[i][0] + '"  realizo="' + data[i][7] + '" fi="' + data[i][4] + '" ff="' + data[i][5] + '" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' : 'Proceso finalizado')

				]).node().id = "row";
				table.draw(false);
			}
			Swal.fire({
				position: 'center',
				icon: 'success',
				title: '¡Listo!',
				showConfirmButton: false,
				timer: 500,
				onClose: () => {
					$('#tablaExplosionPrendas').modal("show");
				}
			})
		},
		error: (data) => {
			console.log("nmz");
		}
	});
}

function cerrarTablaExplosionPrendas() {
	$('#tablaExplosionPrendas').modal("hide");
}

function consumoReal(idExplosion, estatus) {
	var tablaConsumoReal = $('#tablaConsumoReal').DataTable();
	var rows = tablaConsumoReal
		.rows()
		.remove()
		.draw();
	$.ajax({
		method: "GET",
		url: "/listar_consumo_real",
		data: { 'idExplosion': idExplosion },
		beforeSend: function () {
			Swal.fire({
				title: 'Cargando ',
				html: 'Por favor espere',// add html attribute if you want or remove
				allowOutsideClick: false,
				timerProgressBar: true,
				onBeforeOpen: () => {
					Swal.showLoading()
				},
			});
		},
		success: (data) => {
			console.log(data);
			for (i in data) {
				//tablaPrendasExplosionadas.row.add([
				tablaConsumoReal.row.add([
					data[i][1],
					data[i][2],
					data[i][3],
					'<p id="consumoRealP' + data[i][0] + '"> ' + data[i][4] + '  </p>',
					(estatus != 2 ? '<button onclick="editarConsumoReal(this)" id="' + data[i][0] + '" consumo="' + data[i][4] + '" class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar consumo" onclick="editarConsumoReal()"><i class="fas fa-pen"></i></button>' : 'Proceso finalizado')

				]).node().id = "row";
				tablaConsumoReal.draw(false);
			}
			Swal.fire({
				position: 'center',
				icon: 'success',
				title: '¡Listo!',
				showConfirmButton: false,
				timer: 500,
				onClose: () => {
					$('#modalConsumoReal').modal("show");
				}
			})
		},
		error: (data) => {

		}
	});
}

function editarConsumoReal(e) {

	$('#consumoReal').val(e.getAttribute("consumo"));
	$('#idconsumoReal').val(e.getAttribute("id"));
	$('#modalEditarConsumoReal').modal("show");

}
function guardarEditarConsumoReal() {
	if ($('#consumoReal').val() == null || $('#consumoReal').val() == "") {

		Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Complete el formulario!',
			showConfirmButton: true
		});
	} else {

		//consumoRealP


		$.ajax({
			type: "GET",
			url: "/guardar_consumo_real",
			data: {
				id: $('#idconsumoReal').val(),
				'consumo': $('#consumoReal').val(),

			},

			success: function (data) {
				Swal.fire({
					position: 'center',
					icon: 'success',
					title: 'Guardado!',
					showConfirmButton: true
				});
				$('#modalEditarConsumoReal').modal("hide");

				$("#consumoRealP" + $('#idconsumoReal').val()).text($('#consumoReal').val());


			}
		})

	}
}

function cerrarTablaConsumoReal() {
	$('#modalConsumoReal').modal("hide");
}

function llenarSelectRealizo(id) {

	$.ajax({
		type: "GET",
		url: "/listar_select_realizo",
		data: { 'idProceso': idProcesoGlobal, 'tipoProceso': TipoProcesoGlobal },
		success: (data) => {
			$('#selectQuienRealizo').empty();
			$.each(data, function (key, val) {
				$('#selectQuienRealizo').append('<option value="' + val[0] + '" ubicacion="' + val[2] + '" >' + val[1] + '</option>');
			})
			$('#selectQuienRealizo').selectpicker('refresh')
		},
		error: (e) => {
		}, complete: function () {
			$('#selectQuienRealizo').val(id);
			$('#selectQuienRealizo').selectpicker('refresh');

		},
		error: (e) => {

		}
	});
}
function modalRealizo() {
	if ($.isEmptyObject(idExplosionPrenda)) {
		Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Seleccione al menos un registro',
			showConfirmButton: true
		});
	} else {

		llenarSelectRealizo(null);
		$('#fechaInicioModal').val(null);
		$('#fechaFinModal').val(null);

		$('#modalRealizo').modal("toggle");
	}

}

//--------------------------------------------------------------------------\\\
$('#selectAll').click(function (e) {
	if ($(this).hasClass('checkedAll')) {
		$('.messageCheckbox').prop('checked', false);
		$(this).removeClass('checkedAll');
		$(".messageCheckbox").removeClass('checkedThis');
		var inputElements = document.getElementsByClassName('messageCheckbox');
		for (var i = 0; i < inputElements.length; ++i) {
			if (!inputElements[i].checked) {
				var removeIndex = idExplosionPrenda.indexOf(+inputElements[i].value)
				idExplosionPrenda.splice(removeIndex, 1);
			}
		}
	} else {
		$('.messageCheckbox').prop('checked', true);
		$(this).addClass('checkedAll');
		$(".messageCheckbox").addClass('checkedThis');
		var inputElements = document.getElementsByClassName('messageCheckbox');
		for (var i = 0; i < inputElements.length; ++i) {
			if (inputElements[i].checked) {
				idExplosionPrenda.push(+inputElements[i].value);
			}
			idExplosionPrenda = [...new Set(idExplosionPrenda)];
		}
	}
});

function seleccionarxUNO(id) {
	if ($('#check-' + id).hasClass('checkedThis')) {
		$('#check-' + id).removeClass('checkedThis');
		var removeIndex = idExplosionPrenda.indexOf(+$('#check-' + id).val())
		idExplosionPrenda.splice(removeIndex, 1);
	} else {
		$('#check-' + id).addClass('checkedThis');
		idExplosionPrenda.push(+$('#check-' + id).val());
	}
}

//--------------------------------------------------------------------------///
function guardarRealizo() {
	if ($('#fechaInicioModal').val() == '' || $('#fechaFinModal').val() == '' || $('#selectQuienRealizo').val() == '' || $('#fechaInicioModal').val() == null || $('#fechaFinModal').val() == null || $('#selectQuienRealizo').val() == null) {
		Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Complete el formulario!',
			showConfirmButton: true
		});
	} else {
		//ubicacion
		//@RequestParam(name = "ids") String[] ids, String fechainicio, String fechafin,String realizo


		$.ajax({
			type: "GET",
			url: "/guardar_realizo_produccion_prendas",
			data: {
				ids: idExplosionPrenda.toString(),
				'fechainicio': $('#fechaInicioModal').val(),
				"fechafin": $('#fechaFinModal').val(),
				"realizo": $('#selectQuienRealizo').val(),
				"ubicacion": $("#selectQuienRealizo option:selected").attr("ubicacion")

			},
			beforeSend: function () {
				Swal.fire({
					position: 'center',
					icon: 'success',
					title: 'Agregado correctamente',
					allowOutsideClick: false,
					timerProgressBar: true,
					showConfirmButton: false,
					onBeforeOpen: () => {

					},
				});

			},

			success: function (data) {
				$('#modalRealizo').modal("hide");
				$('#tablaExplosionPrendas').modal("hide");
				idExplosionPrenda = [];
				abrirTablaExplosionPrendas(data);

			}
		})

	}

	//guardar_realizo_produccion_prendas
}

function editarExplosionPrenda(e) {

	idExplosionPrenda = [];
	idExplosionPrenda.push(+e.getAttribute("id"));

	llenarSelectRealizo(e.getAttribute("realizo"))

	$('#fechaInicioModal').val(e.getAttribute("fi").replace(" ", "T"));
	$('#fechaFinModal').val(e.getAttribute("ff").replace(" ", "T"));

	$('#modalRealizo').modal("toggle");

}

$('#modalRealizo').on('hidden.bs.modal', function () {

	$('#selectAll').prop('checked', false);
	$('#selectAll').removeClass('checkedAll');
	$("#selectAll").removeClass('checkedThis');
	var inputElements = document.getElementsByClassName('messageCheckbox');
	for (var i = 0; i < inputElements.length; ++i) {
		if (!inputElements[i].checked) {
			var removeIndex = idExplosionPrenda.indexOf(+inputElements[i].value)
			idExplosionPrenda.splice(removeIndex, 1);
		}
	}


});