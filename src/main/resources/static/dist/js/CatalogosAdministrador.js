// ALMACEN LOGICO
$('#detalleFirma').on('shown.bs.modal', function () {
	$(document).off('focusin.modal');
});
function agregarFirma(){
	Swal.fire({
		  title: 'Nueva firma',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-md-6">'+
				  	'<label for="nombreFirma" style="margin-left: -20px;">Nombre</label>'+
				  	'<select class="form-control" id="nombreFirma">'+
				  	'<option>Listar todos los cargos con firma</option>'+
				  	'</select>'+
				  '</div>'+
				  '<div class="form-group col-md-6">'+
				  	'<label for="imagenFirma" style="margin-left: -100px;">Firma aut&oacute;grafa</label>'+
				  	'<div class="custom-file">'+
                    '<input type="file" class="custom-file-input" id="imagenFirma">'+
                    '<label class="custom-file-label" for="imagenFirma">Seleccionar</label>'+
                    '</div>'+
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
				  title: 'Firma agregada correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}
function editarFirma(){
	Swal.fire({
		  title: 'Editar firma',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-md-6">'+
				  	'<label for="nombreFirmaE" style="margin-left: -20px;">Nombre</label>'+
				  	'<select class="form-control" id="nombreFirmaE">'+
				  	'<option>Listar todos los cargos con firma</option>'+
				  	'</select>'+
				  '</div>'+
				  '<div class="form-group col-md-6">'+
				  	'<label for="imagenFirmaE" style="margin-left: -100px;">Firma aut&oacute;grafa</label>'+
				  	'<div class="custom-file">'+
                    '<input type="file" class="custom-file-input" id="imagenFirmaE">'+
                    '<label class="custom-file-label" for="imagenFirmaE">Seleccionar</label>'+
                    '</div>'+
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
				  title: 'Firma editada correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}
function bajaFirma(){
	Swal.fire({
		  title: '¿Deseas dar de baja la firma?',
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
				  title: 'Firma dada de baja correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}
function altaFirma(){
	Swal.fire({
		  title: '¿Deseas dar de alta la firma?',
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
				  title: 'Firma dada de alta correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}