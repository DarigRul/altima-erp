
function bajaProveedor(id){
	Swal.fire({
		  title: '¿Desea dar de baja a este proveedor?',
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
			          title: '¡Se ha dado de baja al proveedor!',
			          showConfirmButton: false,
			          timer: 1550,
				      onClose: () => {
				    	  location.href = "/baja-proveedor/"+id;
				      }
				})
				
			}
		})
}

function altaProveedor(id){
	Swal.fire({
		  title: '¿Desea dar de alta a este proveedor?',
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
			          title: '¡Se ha dado de alta al proveedor!',
			          showConfirmButton: false,
			          timer: 1550,
				      onClose: () => {
				    	  location.href = "/alta-proveedor/"+id;
				      }
				})
				
			}
		})
}

function validarDatos(){
	
	if($('#proveedorNombre').val()			==null || $('#proveedorNombre').val()			== "" || $('#proveedorNombre').val()		==undefined ||
	   $('#proveedorTipo').val()			==null || $('#proveedorTipo').val()				== "" || $('#proveedorTipo').val()			==undefined ||
	   $('#proveedorCalle').val()			==null || $('#proveedorCalle').val()			== "" || $('#proveedorCalle').val()			==undefined ||
	   $('#proveedorNumExt').val()			==null || $('#proveedorNumExt').val()			== "" || $('#proveedorNumExt').val()		==undefined ||
	   $('#proveedorColonia').val()			==null || $('#proveedorColonia').val()			== "" || $('#proveedorColonia').val()		==undefined ||
	   $('#poblacionColonia').val()			==null || $('#poblacionColonia').val()			== "" || $('#poblacionColonia').val()		==undefined ||
	   $('#cpColonia').val()	 	 	   	==null || $('#cpColonia').val()					== "" || $('#cpColonia').val()				==undefined ||
	   $('#proveedorMunicipio').val()   	==null || $('#proveedorMunicipio').val()		== "" || $('#proveedorMunicipio').val()		==undefined ||
	   $('#proveedorEstado').val()	 		==null || $('#proveedorEstado').val()			== "" || $('#proveedorEstado').val()		==undefined ||
	   $('#proveedorPais').val()		   	==null || $('#proveedorPais').val()				== "" || $('#proveedorPais').val()			==undefined ||
	   $('#proveedorClasificacion').val()	==null || $('#proveedorClasificacion').val()	== "" || $('#proveedorClasificacion').val()	==undefined ||
	   $('#proveedorRFC').val()		   		==null || $('#proveedorRFC').val()				== "" || $('#proveedorRFC').val()			==undefined ||
	   $('#proveedorCURP').val()		   	==null || $('#proveedorCURP').val()				== "" || $('#proveedorCURP').val()			==undefined ||
	   $('#proveedorTelefono').val()     	==null || $('#proveedorTelefono').val()			== "" || $('#proveedorTelefono').val()		==undefined ){
		
		Swal.fire({
		      position: 'center',
	          icon: 'error',
	          title: '¡Todos los campos obligatorios deben ser llenados correctamente!',
	          showConfirmButton: false,
	          timer: 2750,
		})
	}
	
	else if ($('#proveedorTelefono').val().length!=10){
		Swal.fire({
		      position: 'center',
	          icon: 'error',
	          title: '¡Ingrese un número de teléfono válido!',
	          showConfirmButton: false,
	          timer: 2750,
		})
	}
	else{
//		if($('#datosPrueba')==null || $('#datosPrueba')== "" || $('#datosPrueba')==undefined){
//		Swal.fire({
//		      position: 'center',
//	          icon: 'success',
//	          title: '¡Se ha registrado un nuevo proveedor!',
//	          showConfirmButton: false,
//	          timer: 1950,
//		      onClose: () => {
//		    	  enviarDatos.click();
//		      }
//		})
//		}
//		else{
//			Swal.fire({
//			      position: 'center',
//		          icon: 'success',
//		          title: '¡Se han actualizado los datos del proveedor!',
//		          showConfirmButton: false,
//		          timer: 2200,
//			      onClose: () => {
			    	  enviarDatos.click();
//			      }
//			})
//		}
	}
	
}
