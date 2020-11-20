
function existencias(e) {
		var id = e.getAttribute("id");
		var idText = e.getAttribute("idText");
		var nombre = e.getAttribute ("nombre");
		var tipo =  e.getAttribute("tipo");	
		// CONTROLADOR ** AmpMultialmacenRestController** 
		$.ajax({
			method: "GET",
			url: "/multialmacen-articulos-disponibles",
			data: {

				'articulo': e.getAttribute("id"),
				'tipo':e.getAttribute("tipo")
			},
			success: (data) => {			
				console.log (data);
				Swal.fire({
					title: 'Disponible',
					html: '<div class="row">' +
		            '<div class="form-group col-sm-6">' +
		            '<label for="nombreClas">C&oacute;digo</label>' +
		            '<input type="text" disabled class="form-control" id="nombreClas" value="'+e.getAttribute("idText")+'" placeholder="Materia Prima">' +
		            '</div>' +
		            
		            '<div class="form-group col-sm-6">' +
		            '<label for="nombreClas">Cantidad</label>' +
		            '<input type="text" disabled class="form-control" id="nombreClas" value="'+data+'" placeholder="Materia Prima">' +
		            '</div>' +
		            '<div class="form-group col-sm-12">' +
		            '<label for="nombreClas">Nombre</label>' +
		            '<input type="text" disabled  class="form-control" id="nombreClas" value="'+e.getAttribute("nombre")+'" placeholder="Materia Prima">' +
		            '</div>' +
		            '</div>'
					 
					})
			},
			error: (e) => {

			}
		})
	}