$('#detalleOperaciones').on('shown.bs.modal', function () {
	$(document).off('focusin.modal');
});
function addAsignacion(){
	Swal.fire({
		  title: 'Nueva asignaci&oacute;n',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-sm-12">'+
				  	'<label for="descripcionAsignacion">Descripci&oacute;n</label>'+
				  	'<input type="text" class="form-control" id="descripcionAsignacion" placeholder="Coser etiqueta a tabulejo">'+
				  '</div>'+
				  '<div class="form-group col-sm-4">'+
				  	'<label for="samAsignacion">SAM</label>'+
				  	'<input type="number" class="form-control" id="samAsignacion" placeholder="0.68">'+
				  '</div>'+
				  '<div class="form-group col-sm-4">'+
				  	'<label for="horaAsignacion">Producci&oacute;n hora</label>'+
				  	'<input type="number" class="form-control" id="horaAsignacion" placeholder="88">'+
				  '</div>'+
				  '<div class="form-group col-sm-4">'+
				  	'<label for="turnoAsignacion">Producci&oacute;n turno</label>'+
				  	'<input type="number" class="form-control" id="turnoAsignacion" placeholder="794">'+
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
				  title: 'Asignaci&oacute;n agregada correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}
function editAsignacion(){
	Swal.fire({
		  title: 'Editar asignaci&oacute;n',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-sm-12">'+
				  	'<label for="editdescripcionAsignacion">Descripci&oacute;n</label>'+
				  	'<input type="text" class="form-control" id="editdescripcionAsignacion" placeholder="Coser etiqueta a tabulejo">'+
				  '</div>'+
				  '<div class="form-group col-sm-4">'+
				  	'<label for="editsamAsignacion">SAM</label>'+
				  	'<input type="number" class="form-control" id="editsamAsignacion" placeholder="0.68">'+
				  '</div>'+
				  '<div class="form-group col-sm-4">'+
				  	'<label for="edithoraAsignacion">Producci&oacute;n hora</label>'+
				  	'<input type="number" class="form-control" id="edithoraAsignacion" placeholder="88">'+
				  '</div>'+
				  '<div class="form-group col-sm-4">'+
				  	'<label for="editturnoAsignacion">Producci&oacute;n turno</label>'+
				  	'<input type="number" class="form-control" id="editturnoAsignacion" placeholder="794">'+
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
				  title: 'Asignaci&oacute;n modificada correctamente',
				  showConfirmButton: false,
				  timer: 2500
				})
		  }
		});
}
function deleteAsignacion(){
		Swal.fire({
			  title: '¿Deseas eliminar la asignaci&oacute;n?',
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
					title: 'Asignaci&oacute;n eliminada correctamente',
					showConfirmButton: false,
					timer: 2400
			    })
			  }
			})
	}