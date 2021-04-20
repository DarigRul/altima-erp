
var idCoorPrenda = [];
var idExplosionPrenda = [];
let sumatoriaCarga = 0;
$('#selectAll').click(function (e) {
	if ($(this).hasClass('checkedAll')) {
		$('.messageCheckbox').prop('checked', false);
		$(this).removeClass('checkedAll');
		$(".messageCheckbox").removeClass('checkedThis');
		var inputElements = document.getElementsByClassName('messageCheckbox');
		for (var i = 0; i < inputElements.length; ++i) {
			if (!inputElements[i].checked) {
				var removeIndex = idCoorPrenda.indexOf(+inputElements[i].value)
				idCoorPrenda.splice(removeIndex, 1);
			}
		}
	} else {
		$('.messageCheckbox').prop('checked', true);
		$(this).addClass('checkedAll');
		$(".messageCheckbox").addClass('checkedThis');
		var inputElements = document.getElementsByClassName('messageCheckbox');
		for (var i = 0; i < inputElements.length; ++i) {
			if (inputElements[i].checked) {
				idCoorPrenda.push(+inputElements[i].value);
			}
			idCoorPrenda = [...new Set(idCoorPrenda)];
		}
	}
	//console.log(idCoorPrenda);
});
$(".messageCheckbox").change(function (e) {
	e.preventDefault();
	if ($(this).hasClass('checkedThis')) {
		$(this).removeClass('checkedThis');
		var removeIndex = idCoorPrenda.indexOf(+$(this).val())
		idCoorPrenda.splice(removeIndex, 1);
	} else {
		$(this).addClass('checkedThis');
		idCoorPrenda.push(+$(this).val());
	}
	//console.log(+$(this).val());
	//console.log(idCoorPrenda);
});

function nuevoFolio() {
	if ($.isEmptyObject(idCoorPrenda)) {
		Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Seleccione al menos un registro',
			showConfirmButton: true
		});
	} else {
		//data-toggle="modal" data-target="#"
		$('#nuevoFolio').modal('show');
	}

}
function nuevoPrograma() {
	if ($.isEmptyObject(idCoorPrenda)) {
		Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Seleccione al menos un registro',
			showConfirmButton: true
		});
	} else {
		//data-toggle="modal" data-target="#"
		$('#nuevoPrograma').modal('show');
	}

}
function nuevaRuta() {
	if ($.isEmptyObject(idCoorPrenda)) {
		Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Seleccione al menos un registro',
			showConfirmButton: true
		});
	} else {
		//data-toggle="modal" data-target="#"
		$('#nuevoRuta').modal('show');
	}

}

function nuevaSecuencia() {
	if ($.isEmptyObject(idExplosionPrenda)) {
		Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Seleccione al menos un registro',
			showConfirmButton: true
		});
	} else {
		//data-toggle="modal" data-target="#"
		$('#nuevaSecuencia').modal('show');
	}

}

function guardarRuta() {
	if ($('#idRuta').val() == null || $('#idRuta').val() == 0) {
		Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Seleccione una ruta',
			showConfirmButton: true
		});
	}
	else {

		$.ajax({
			type: "POST",
			url: "/guardar_ruta_a_coor_prenda",
			data: {
				ids: idCoorPrenda.toString(),
				'idRuta': $('#idRuta').val(),
				"_csrf": $('#token').val()

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
			},
			complete: function () {
				var url = "/programar-telas";
				$(location).attr('href', url);

			},
		})
	}
}


function guardarFolio() {
	if ($('#folioText').val() == null || $('#folioText').val() == 0) {
		Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Seleccione el folio',
			showConfirmButton: true
		});
	}
	else {

		$.ajax({
			type: "POST",
			url: "/guardar_folio_a_coor_prenda",
			data: {
				ids: idCoorPrenda.toString(),
				'folio': $('#folioText').val(),
				"_csrf": $('#token').val()

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
			},
			complete: function () {
				var url = "/empalme_telas";
				$(location).attr('href', url);

			},
		})
	}
}

function guardarPrograma() {
	if ($('#programaText').val() == null || $('#programaText').val() == "") {
		Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Ingrese el programa',
			showConfirmButton: true
		});
	}
	else {

		$.ajax({
			type: "POST",
			url: "/guardar_programa_a_coor_prenda",
			data: {
				ids: idCoorPrenda.toString(),
				'programa': $('#programaText').val(),
				"_csrf": $('#token').val()

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
			},
			complete: function () {
				var url = "/programar-telas";
				$(location).attr('href', url);

			},
		})
	}
}


function guardarSecuencia() {
	//
	if ($('#SecuenciaText').val() == null || $('#SecuenciaText').val() == "") {
		Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Ingrese la secuencia',
			showConfirmButton: true
		});
	}
	else {

		$.ajax({
			type: "POST",
			url: "/guardar_empalme_by_proceso",
			data: {
				ids: idExplosionPrenda.toString(),
				'secuencia': $('#SecuenciaText').val(),
				"_csrf": $('#token').val()

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
			},
			complete: function () {
				idExplosionPrenda = [];
				$("#selectAll").prop("checked", false);
				$('#nuevaSecuencia').modal('hide');
				listarPorProceso()
			},
		})
	}
}

function detalles(id) {
	var table = $('#tablaDetalles').DataTable();
	var rows = table
		.rows()
		.remove()
		.draw();

	$.ajax({
		type: "GET",
		url: "/empalme_telas_detalles",
		data: {
			id: id
		},
		success: function (data) {
			for (i in data) {
				table.row.add([
					data[i][0],
					data[i][1],
					data[i][2],
					data[i][3],
					data[i][4],
					data[i][5]


				]).node().id = "row";
				table.draw(false);
			}
			//console.log(data)
		}

	})

}

function listarPorProceso() {
	let tableEmpalme = $('#tableEmpalmeId').DataTable();
	let programa = $("#programa").val();
	let procesosActivos = $("#procesosActivos").val();
	tableEmpalme
		.clear()
		.draw();
	if (programa.trim() === "" || procesosActivos.trim() == "") {
		Swal.fire({
			position: 'center',
			icon: 'error',
			title: 'Todos los campos son requeridos!',
			showConfirmButton: true
		});
	} else {
		$.ajax({
			method: "GET",
			url: "/empalme_telas_by_proceso",
			data: {
				'idProceso': procesosActivos,
				'programa': programa

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
				let mensaje = false;
				sumatoriaCarga = 0;
				for (i in data) {
					mensaje = true;
					tableEmpalme.row.add([
						'<td style="text-align: center; vertical-align: middle;">' +
						'<input type="checkbox" onchange="seleccionarxUNO(' + data[i][0] + ')" class="messageCheckbox" value="' + data[i][0] + '" id="check-' + data[i][0] + '"  />' +
						'</td>',
						(data[i][17] == null ? '' : data[i][17]),
						data[i][1],
						data[i][2],
						data[i][3],
						data[i][4],
						data[i][5],
						data[i][6],
						data[i][8],
						data[i][9],
						data[i][10],
						data[i][11],
						data[i][12],
						data[i][13],
						data[i][14],
						'<p id="tiempoProcesoP' + data[i][0] + '"> ' + (data[i][15] == null ? '' : data[i][15]) + ' </p>',// tiempo
						'<p id="fechaProcesoP' + data[i][0] + '"> ' + (data[i][16] == null ? '' : data[i][16]) + ' </p>',//fecha
						// '<button class="btn btn-info btn-circle btn-sm" onclick="detalles(' + data[i][18] + ')" data-toggle="modal" data-target="#detalleTelas"><i class="fas fa-info"></i></button>' +
						'<button class="btn btn-primary btn-circle btn-sm" onclick="tiempo_proceso(this)" id="' + data[i][0] + '" tiempo="' + data[i][15] + '" data-content="Tiempo" > <i class="fas fa-clock"></i> </button>' +
						'<button class="btn btn-secondary btn-circle btn-sm" onclick="fecha_proceso(this)" id="' + data[i][0] + '" fecha="' + data[i][16] + '" data-content="Fecha" > <i class="fas fa-calendar-alt"></i> </button>',

					]).draw(true);
					sumatoriaCarga += data[i][15];
				}
				console.log(sumatoriaCarga);
				if (mensaje) {
					Swal.fire({
						position: 'center',
						icon: 'success',
						title: '¡Listo!',
						showConfirmButton: false,
						timer: 1000,
						onClose: () => {
							$('#SeleccionPrograma').modal("hide");
						}
					})
				} else {
					Swal.fire({
						position: 'center',
						icon: 'warning',
						title: '¡No existen registros!',
						showConfirmButton: false,
						timer: 1300,
						onClose: () => {
							$('#SeleccionPrograma').modal("hide");
						}
					})
				}


			},
			error: (data) => {

			}
		});
	}



}

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
function seleccionarTodos() {

	if ($('#testSelect').hasClass('checkedAll')) {
		console.log("nmz if")
		$('.messageCheckbox').prop('checked', false);
		$('#testSelect').removeClass('checkedAll');
		$(".messageCheckbox").removeClass('checkedThis');
		var inputElements = document.getElementsByClassName('messageCheckbox');
		for (var i = 0; i < inputElements.length; ++i) {
			if (!inputElements[i].checked) {
				var removeIndex = idExplosionPrenda.indexOf(+inputElements[i].value)
				idExplosionPrenda.splice(removeIndex, 1);
			}
		}
	} else {
		console.log("nmz else")
		$('.messageCheckbox').prop('checked', true);
		$('#testSelect').addClass('checkedAll');
		$(".messageCheckbox").addClass('checkedThis');
		var inputElements = document.getElementsByClassName('messageCheckbox');
		for (var i = 0; i < inputElements.length; ++i) {
			if (inputElements[i].checked) {
				idExplosionPrenda.push(+inputElements[i].value);
			}
			idExplosionPrenda = [...new Set(idExplosionPrenda)];
		}
	}
}

function tiempo_proceso(e) {
	$("#TiempoText").val(e.getAttribute("tiempo"));
	$("#idTiempo").val(e.getAttribute("id"));
	$("#tiempoProceso").modal("show");

}
function guardarTiempoProceso() {

	if ($('#TiempoText').val() == null || $('#TiempoText').val() == "" || $('#idTiempo').val() == null || $('#idTiempo').val() == "") {
		Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Ingrese el tiempo',
			showConfirmButton: true
		});
	}
	else {

		$.ajax({
			type: "POST",
			//Long id, String tiempo,String fecha
			url: "/guardar_empalme_by_proceso_fecha_tiempo",
			data: {
				id: $('#idTiempo').val(),
				'tiempo': $('#TiempoText').val(),
				"_csrf": $('#token').val()

			},

			success: function (data) {
				if (data == true) {
					//
					$("#tiempoProcesoP" + $('#idTiempo').val()).text($('#TiempoText').val());
					Swal.fire({
						position: 'center',
						icon: 'success',
						title: 'Agregado correctamente',
						allowOutsideClick: false,
						timerProgressBar: true,
						showConfirmButton: true,
						onBeforeOpen: () => {

						},
					});

				}
			},
			complete: function () {
				$("#tiempoProceso").modal("hide");
			},
		})
	}
}
function fecha_proceso(e) {
	$("#fechaText").val(e.getAttribute("fecha"));
	$("#idfecha").val(e.getAttribute("id"));
	$("#fechaProceso").modal("show");

}
function guardarFechaProceso() {

	if ($('#fechaText').val() == null || $('#fechaText').val() == "" || $('#idfecha').val() == null || $('#idfecha').val() == "") {
		Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Ingrese la fecha',
			showConfirmButton: true
		});
	}
	else {

		$.ajax({
			type: "POST",
			//Long id, String tiempo,String fecha
			url: "/guardar_empalme_by_proceso_fecha_tiempo",
			data: {
				id: $('#idfecha').val(),
				'fecha': $('#fechaText').val(),
				"_csrf": $('#token').val()

			},

			success: function (data) {
				if (data == true) {
					//
					$("#fechaProcesoP" + $('#idfecha').val()).text($('#fechaText').val());
					Swal.fire({
						position: 'center',
						icon: 'success',
						title: 'Agregado correctamente',
						allowOutsideClick: false,
						timerProgressBar: true,
						showConfirmButton: true,
						onBeforeOpen: () => {

						},
					});

				}
			},
			complete: function () {
				$("#fechaProceso").modal("hide");
			},
		})
	}
}
function sumarDias(fecha, dias) {
	fecha.setDate(fecha.getDate() + dias);
	return fecha;
}
function verCalendario() {
	//calendarioFechaInicio  calendarioFechaFin
	let idProceso = $('#procesosActivos').val();
	var now = new Date();
	var day = ("0" + now.getDate()).slice(-2);
	var month = ("0" + (now.getMonth() + 1)).slice(-2);
	var today = now.getFullYear() + "-" + (month) + "-" + (day);
	$("#calendarioFechaInicio").val(today);

	now = sumarDias(now, +7);
	day = ("0" + now.getDate()).slice(-2);
	month = ("0" + (now.getMonth() + 1)).slice(-2);
	var today2 = now.getFullYear() + "-" + (month) + "-" + (day);
	$("#calendarioFechaFin").val(today2);

	var table = $('#tablaDetallesCalendario').DataTable();
	var rows = table
		.rows()
		.remove()
		.draw();
	$.ajax({
		type: "GET",
		url: "/listar_fechas_calendario",
		data: {
			'idProceso': idProceso,
			'fecha1': $("#calendarioFechaInicio").val(),
			'fecha2': $("#calendarioFechaFin").val()
		},

		success: function (data) {

			for (i in data) {
				table.row.add([
					data[i][0],
					sumarHoras(restarHoras("" + data[i][1] + "", "" + data[i][2] + ""), data[i][4]),
					formato("" + data[i][3] + ""),
					restarHoras(sumarHoras(restarHoras("" + data[i][1] + "", "" + data[i][2] + ""), data[i][4]), formato("" + data[i][3] + ""))
				]).node().id = "row";
				table.draw(false);
			}
			console.log(data)
		}
	})




	$('#verCalendarioModal').modal('show'); // abrir

}

function buscarfecha() {
	let idProceso = $('#procesosActivos').val();
	var table = $('#tablaDetallesCalendario').DataTable();
	var rows = table
		.rows()
		.remove()
		.draw();
	$.ajax({
		type: "GET",
		url: "/listar_fechas_calendario",
		data: {
			'idProceso': idProceso,
			'fecha1': $("#calendarioFechaInicio").val(),
			'fecha2': $("#calendarioFechaFin").val()
		},

		success: function (data) {

			for (i in data) {

				table.row.add([
					data[i][0],
					sumarHoras(restarHoras("" + data[i][1] + "", "" + data[i][2] + ""), data[i][4]),
					formato("" + data[i][3] + ""),
					restarHoras(sumarHoras(restarHoras("" + data[i][1] + "", "" + data[i][2] + ""), data[i][4]), formato("" + data[i][3] + ""))
				]).node().id = "row";
				table.draw(false);
			}
			console.log(data)
		}
	})

}

function formato(hora) {
	hora = hora.replace(/[:]/gi, '.');

	var s = hora.split('.');
	hora = s[0] + "." + s[1];
	return hora;
}
function restarHoras(start, end) {
	s = start.split('.');
	e = end.split('.');
	min = s[1] - e[1];
	hour_carry = 0;
	if (min < 0) {
		min += 60;
		hour_carry += 1;
	}
	hour = s[0] - e[0] - hour_carry;

	if (hour < 10 && hour > 0) {
		hour = '0' + hour;

	} else if (hour < 0 && hour > -10) {
		hour = hour * -1;
		hour = '-0' + hour;
	}
	else if (hour == 0) {
		hour = '0' + hour;
	}
	if (min < 10) {
		min = '0' + min;
	}
	diff = hour + "." + min;

	return diff

}
function sumarHoras(start, end) {
	let s = start.split('.');
	let e = end.split('.');
	let min = (parseInt(s[1]) + parseInt(e[1]));
	let hour_carry = 0;
	if (min < 0) {
		min -= 60;
		hour_carry += 1;
	}
	let hour = parseInt(s[0]) + parseInt(e[0]) + hour_carry;
	console.log(hour)
	if (hour < 10 && hour > 0) {
		hour = '0' + hour;

	} else if (hour < 0 && hour > -10) {
		hour = hour * -1;
		hour = '-0' + hour;
	}
	else if (hour == 0) {
		hour = '0' + hour;
	}
	if (min < 10) {
		min = '0' + min;
	}
	diff = hour + "." + min;
	return diff

}

function listarPorPedido() {
	let table = $('#tablaProgramarTelas').DataTable();
	let pedido = $("#pedido").val();
	if (pedido.trim() === "") {
		Swal.fire({
			position: 'center',
			icon: 'error',
			title: 'Todos los campos son requeridos!',
			showConfirmButton: true
		});
	} else {
		$.ajax({
			method: "GET",
			url: `/getOrdenesProduccionByPedido/${pedido}`,
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

				data.map(orden => {
					table.row.add([
						`<input type="checkbox" class="messageCheckbox"
						value="${orden.idCoordinadoPrenda}" />`,
						orden.idTextPedido,
						orden.fechaEntrega,
						orden.numeroCoordinado,
						orden.familiaPrenda,
						orden.idTextPrenda,
						orden.bordado,
						orden.ruta,
						orden.programa
					]).draw(false);
				})

				Swal.fire({
					position: 'center',
					icon: 'success',
					title: '¡Listo!',
					showConfirmButton: false,
					timer: 500,
					onClose: () => {
						$('#modalPedido').modal("hide");
					}
				})
			},
			error: (data) => {

			}
		});
	}

}

function calcularHoras() {
	$("#sumHorasProceso").val(sumatoriaCarga);
	$("#modalCalcularHoras").modal("show");
}

$("#submitCalcularCarga").click(function (e) {
	e.preventDefault();
	let horasDia = $("#horasDia").val();
	let cortadores = $("#cortadores").val();
	let sumHorasProceso = $("#sumHorasProceso").val();
	let totalHorasDia = $("#totalHorasDia").val();
	if (cortadores.trim() === '' || horasDia.trim() === '' || sumHorasProceso.trim()===''||totalHorasDia.trim()==='') {
		Swal.fire({
			position: 'center',
			icon: 'error',
			title: 'Todos los campos son requeridos!',
			showConfirmButton: true
		});
		return false;
	}
	$("#cargaDias").val((sumHorasProceso/totalHorasDia).toFixed(2));
});

function calcularHorasDia() {
	let horasDia = $("#horasDia").val();
	let cortadores = $("#cortadores").val();
	if (cortadores.trim() === '' || horasDia.trim() === '') return false;
	$("#totalHorasDia").val((cortadores * 9) + Number(horasDia));
}
