 $(document).ready(function() {
	listarVendedores();
});
 

 function listarVendedores(){
		$.ajax({
			 method: "GET",
			    url: "/listarVendedores",
			    success: (data) => {
					for (i in data){
						if(data[i][2]==null || data[i][3]==null){
							$('#vendedorMovi').append("<option value='"+data[i][0]+"'>"+ data[i][1] + "</option>");
						}
						else{
							$('#vendedorMovi').append("<option value='"+data[i][0]+"'>"+ data[i][1] + " " + data[i][2] + " " + data[i][3] +"</option>");
						}
					}
					$('#vendedorMovi').selectpicker('refresh');
			    },
			    error: (e) => {
			    }
			});
	}
 
 $('#vendedorMovi').on('change',function(){
	 var idAgente = $(this).val();
	 $('#empresaMovi').find('option').remove();
	 $('#empresaMovi').selectpicker('refresh');
	 	$.ajax({
	 		 method: "GET",
	 		    url: "/listarEmpresasMovimiento",
	 		    data: {
	 		    	idAgente: idAgente
	 		    },
	 		    success: (data) => {
	 				for (i in data){
	 					$('#empresaMovi').append("<option value='"+data[i][0]+"'>"+ data[i][1] + "</option>");
	 				}

	 				$('#empresaMovi').selectpicker('refresh');
	 		    },
	 		    error: (e) => {
	 		        // location.reload();

	 		    }
	 		});
	 })
 
 function limpiarModal(){
		$('#vendedorMovi').find("option").remove();
		$('#empresaMovi').find("option").remove();
		$('#encargadoRecibir').val("");
		$('#movimiento').val('');
		   listarVendedores();
		   $('.selectCustom').selectpicker('refresh');
	}

 
 function foreach(root, selector, callback) {
	   if (typeof selector == 'string') {
	      var all = root.querySelectorAll(selector);
	      for (var each = 0; each < all.length; each++) {
	         callback(all[each]);
	      }
	   } else {
	      for (var each = 0; each < selector.length; each++) {
	         foreach(root, selector[each], callback);
	      }
	   }
	}


//Funcion para guardar un nuevo movimiento con sus muestras  //  
	function guardarNuevoMovimiento(tablaMuestra) {
	   var table = document.getElementById(tablaMuestra);
	   var filas = $("#tablaMuestra").find('tr'); 
	   var datosJson = [];
	   var vendedorMovi = $('#vendedorMovi').val();
	   var empresaMovi = $('#empresaMovi').val();
	   var movimiento = $('#movimiento').val();
	   var encargado = $('#encargadoRecibir').val();
	   var validacion=true;
	   var validador = 0;
	   var i = 0;


//hace uso de la funcion de foreach para validar que realmente estén llenados los campos en el modal  //
	   if(vendedorMovi=="" || vendedorMovi==null || vendedorMovi==undefined || 
		  empresaMovi=="" || empresaMovi==null || empresaMovi==undefined || shoppingCart.listCart()[0]==undefined ||
		  encargado=="" || encargado==null || encargado==undefined){
		   console.log("faltan datos");
		   Swal.fire({
				icon: 'error',
				title: 'Error',
				text: '¡Todos los campos deben de estar llenos!',
				showConfirmButton: false,
		        timer: 3500
			  })
			validacion=false;
	   }

//La función de este ciclo es realizar un JSON con todas las muestras agregadas en la tabla  //
	   if (table) {
		   if(validacion==true){
			   for(i=1; i<filas.length; i++){
				   var celdas = $(filas[i]).find("td");
			       var record = {cantidad:  $(celdas[0]).text(), 
								 nombreMuestra: $(celdas[1]).text(), 
								 modeloPrenda:  $(celdas[2]).text(), 
								 idPrenda:      $($(celdas[2]).children("input")[0]).val(),
								 codigoTela:    $(celdas[3]).text(),
								 idTela:      	$($(celdas[3]).children("input")[0]).val(),
								 idmuestra:		$($(celdas[3]).children("input")[1]).val()};
		         datosJson.push(record);
		         console.log($($(celdas[2]).children("input")[0]).val());
		         console.log($($(celdas[3]).children("input")[0]).val());
			   }
		   }
	   }
	   if(validacion==true){
console.log(encargado);
//AJAX para mandar los datos del JSON y los datos del vendedor y la empresa(Cliente)  //
		   $.ajax({
			   method: "POST",
			   url: "/guardarSolicitudMovimiento",
			   data:{
				   "_csrf": $('#token').val(),
				   vendedor: vendedorMovi,
				   empresa: empresaMovi,
				   encargado: encargado,
				   idMovimiento:movimiento,
				   "object_muestras": JSON.stringify(datosJson)
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
				   if(data==1){
					   Swal.fire({
							icon: 'success',
							title: 'Rack agregado',
							text: '¡Se ha modificado un movimiento!',
							showConfirmButton: false,
					        timer: 2000,
					        onClose: () => {
					        	$('#addTraspaso').modal(false);
					        	
					        }
						  })
				   }
				   else if(data==2){
					   Swal.fire({
							icon: 'success',
							title: 'Rack agregado',
							text: '¡Se ha agregado el Rack!',
							showConfirmButton: false,
					        timer: 2000,
					        onClose: () => {
					        	shoppingCart.clearCart();
					        	location.reload();
					        }
						  })
				   }
				   else{
					   Swal.fire({
							icon: 'error',
							title: '¡Algo salió mal!',
							text: 'Intente de nuevo más tarde',
							showConfirmButton: false,
					        timer: 2000,
					        onClose: () => {
					        }
						  })
				   }
			   },
			   error: (e) =>{
			   }
		   });
	   }
	   return datosJson;
	}

