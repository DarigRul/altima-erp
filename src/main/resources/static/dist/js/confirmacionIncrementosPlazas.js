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

	document.getElementById('motivosRechazos').value = valor;
	Swal.fire({
		title: '¿Estás seguro de rechazar el incremento de la plaza?',
		text: "Una vez rechazada, no se podrá cambiar. Escriba a continuación el motivo del rechazo",
		icon: 'warning',
		 html:
			    '<input id="descripcion" class="swal2-input">',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Confirmar',
		cancelButtonText: 'Cancelar',
		preConfirm: () => {
			enviarMotivoError();
		},
		allowOutsideClick: () => !Swal.isLoading()
	}).then((result) => {
		if (result.value) {
			Swal.fire(
				'Rechazada',
				'Incremento de plaza rechazada correctamente',
				'success'
			)
			location.href = "/rh-incrementos-rechazar/" + valor;
		}
	});
}

function enviarMotivoError(){
	
	document.getElementById('descripcion2').value = document.getElementById('descripcion').value;
	console.log(document.getElementById('descripcion2').value);
	$('#motivosRechazos').click();
}