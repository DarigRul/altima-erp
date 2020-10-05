function addTalla(){
	
	//$("#generoTalla").empty();
	Swal.fire({
		  title: 'Nueva talla',
		  html:
			  '<div class="row">'+
				  '<div class="form-group col-sm-6">'+
				  	'<label for="numeroTalla">Talla</label>'+
				  	'<input type="number" class="form-control" id="talla" name="talla" placeholder="32">'+
				  '</div>'+
				  '<div class="form-group col-sm-6">'+
				  	'<label for="generoTalla">G&eacute;nero</label>'+
				  	'<select class="form-control" id="generoTalla" name="generoTalla">'+
				      
				   '</select>'+
				  '</div>'+
				
			  '</div>',
		  inputAttributes: {
		    autocapitalize: 'off'
		  },
		  showCancelButton: true,
		  confirmButtonText: 'Agregar',
		  cancelButtonText: 'Cancelar',
		  showLoaderOnConfirm: true,
		  preConfirm: (talla) => {
			  if(document.getElementById("talla").value.length<1 || document.getElementById("generoTalla").value.length<1){
					Swal.showValidationMessage(
							`Complete todos los campos`
					)
				}
		  },
		  allowOutsideClick: () => !Swal.isLoading()
		}).then((result) => {
		  if (result.value &&  document.getElementById("generoTalla").value  && document.getElementById("talla").value) {
			  var talla = document.getElementById("talla").value;
			  var select = document.getElementById("generoTalla");
			  var id_genero = select.value;
			  var genero = select.options[select.selectedIndex].innerText;
			  
			  
			  $.ajax({
					type: "GET",
					url: "/verificar-duplicado-produccion",
					data: {
						
						
						'Lookup': talla,
						'Tipo': "Talla",
						'Atributo1': id_genero,
						'Atributo2': genero
						


					}

				}).done(function (data) {
					console.log ("dataaa----->"+data)
					if(data==false){

						$.ajax({
							type: "POST",
							url: "/guardar-catalogo-produccion",
							data: {
								"_csrf": $('#token').val(),
								'num_talla': talla,
								'genero': genero,
								'id_genero': id_genero

							}

						}).done(function (data) {
							listarColores();
						});
						Swal.fire({
							position: 'center',
							icon: 'success',
							title: 'Insertado correctamente',
							showConfirmButton: false,
							timer: 1250
						})
						// / window.setTimeout(function(){location.reload()}, 2000);
					}// /fin segundoif
					else{
						Swal.fire({
							position: 'center',
							icon: 'error',
							title: 'registro duplicado no se ha insertado',
							showConfirmButton: false,
							timer: 1250
						})

					}
				});
		  }
		});
	
	
	$.ajax({
		method: "GET",
		url: "/listar",
		data:{
			"Tipo":"Familia Genero"
		} ,
		success: (data) => {
			$.each(data, function(key, val) {
	    		$('#generoTalla').append('<option value="' + val.idLookup + '">'+val.nombreLookup+'</option>');})
	    		//$('.selectpicker').selectpicker(["refresh"]);
		},
		error: (e) => {

		}
	})
		
}