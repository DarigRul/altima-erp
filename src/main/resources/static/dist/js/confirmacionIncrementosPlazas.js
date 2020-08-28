function aceptarIncremento(valor) {
	Swal.fire({
		title: '¿Estás seguro de aprobar el incremento de la plaza?',
		text: "Una vez aprobado, no se podrá cambiar",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Confirmar',
		cancelButtonText: 'Cancelar'
	}).then((result) => {
		if (result.value) {
			Swal.fire(
				'Aprobada',
				'Incremento de plaza aprobada correctamente',
				'success',
			)
			location.href = "/rh-incrementos-aprobar/" + valor;
		}
	});
}
function rechazarIncremento(valor) {
	Swal.fire({
		title: '¿Estás seguro de rechazar el incremento de la plaza?',
		text: "Una vez rechazada, no se podrá cambiar. Escriba a continuación el motivo del rechazo",
		icon: 'warning',
		html:
			'<input id="motivo" class="swal2-input">',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Confirmar',
		cancelButtonText: 'Cancelar',
		preConfirm: () => {
			if (document.getElementById("motivo").value.length < 1) {
				Swal.showValidationMessage(
					`Complete todos los campos`
				)
			}
		}
	}).then((result) => {
		if (result.value) {
			var motivo = document.getElementById("motivo").value;
			$.ajax({
				type: "POST",
				url: "/motivoRechazo",
				data: {
					"_csrf": $('#token').val(),
					"motivo": motivo,
					"idIncremento": valor
				}
			}).done(function (data) {
				Swal.fire(
					'Rechazada',
					'Incremento de plaza rechazada correctamente',
					'success'
				)
				location.href = "/rh-incrementos-rechazar/" + valor;
			})
		}
	});
}
