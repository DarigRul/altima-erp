
var idCoorPrenda = [];
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