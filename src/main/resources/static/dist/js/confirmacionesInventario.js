function bajaInventario(id){
	
	console.log("aqui esta el id de deetalle pedido   " + id)
	Swal.fire({
		  title: '¿Deseas dar de baja la muestra?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar',
		  cancelButtonText: 'Cancelar'
		}).then((result) => {
		  if (result.value) {
			  
			  
			  
			   $.ajax({
					type: "POST",
					url: "/declinado-detalle-pedido",
					data: {
						"_csrf": $('#token').val(),
						"id":id
					},
					success: (data) => {
	                  console.log(data);
	                  
	                  Swal.fire(
	            		      'Correcto',
	            		      'Muestra dada de baja correctamente',
	            		      'success'
	            		    )
	           
					},
					failure: function (errMsg) {
						alert(errMsg);
					}
				});
			  
			  
		   
		  }
		});
function activaInventario(){
	Swal.fire({
		  title: '¿Deseas reactivar la muestra?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar',
		  cancelButtonText: 'Cancelar'
		}).then((result) => {
		  if (result.value) {
		    Swal.fire(
		      'Correcto',
		      'Muestra reactivada correctamente',
		      'success'
		    )
		  }
		});
}
}