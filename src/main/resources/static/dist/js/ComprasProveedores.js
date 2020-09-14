 $(document).ready(function() {
	 if ($('#proveedorNumExt').val()==0 || $('#proveedorNumExt').val()=='' ||  $('#proveedorNumExt').val()==null ||  $('#proveedorNumExt').val()==undefined) {								//
	        $('.check').hide();						//
	        $('#proveedorNumExt').val("0")			//
	        $('#proveedorNumInt').val("")			//
	    } else {									//
	    	$('.check').show();						//
	    }	
});

function guardarDatosGenerales(){
	var record={};
		
	record ["nombreProveedor"] 		= $('#proveedorNombre').val();
	record ["tipo"]            		= $('#proveedorTipo').val();
	record ["calle"]           		= $('#proveedorCalle').val();
	record ["numeroExterior"]  		= $('#proveedorNumExt').val();
	record ["numeroInterior"] 	 	= $('#proveedorNumInt').val();
	record ["colonia"]         		= $('#proveedorColonia').val();
	record ["poblacion"]      	 	= $('#poblacionColonia').val();
	record ["codigoPostal"]  	  	= $('#cpColonia').val();
	record ["municipio"]       		= $('#proveedorMunicipio').val();
	record ["estado"]         	 	= $('#proveedorEstado').val();
	record ["pais"]            		= $('#proveedorPais').val();
	record ["clasificacion"]  		= $('#proveedorClasificacion').val();
	record ["zona"]					= $('#proveedorZona').val();
	record ["rfcProveedor"]			= $('#proveedorRFC').val();
	record ["curpProveedor"]		= $('#proveedorCURP').val();
	record ["telefonoProveedor"]	= $('#proveedorTelefono').val();
	record ["correo"]				= $('#proveedorCorreo').val();
	record ["paginaWebProveedor"]	= $('#proveedorWeb').val();
	record ["idProveedor"]			= $('#datosPrueba').val();
	record ["nomenclatura"]			= $('#nomenclatura').val();
	
	console.log(record);
	//Valida que sea un nuevo registro
	if($('#datosPrueba').val()==""){
		console.log("nuevo");
		$.ajax({
			method: "POST",
			url: "/GuardarDatosGeneralesProveedor",
			data: {"_csrf": $('#token').val(),
				lista:JSON.stringify(record)
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
				if(data!=-1){
					Swal.fire({
					      position: 'center',
				          icon: 'success',
				          title: '¡Se ha agregado un nuevo proveedor!',
				          showConfirmButton: false,
				          timer: 1550,
					      onClose: () => {
					    	  $('#datosPrueba').val(data);
					    	  $('#datos-alta-contactos-tab').click();
					      }
					})
				}
				else{
					Swal.fire({
					      position: 'center',
				          icon: 'error',
				          title: '¡Algo salió mal, intente más tarde!',
				          showConfirmButton: false,
				          timer: 1550
					})
				}
			},
			error: (e) => {
			}
		})
	}
	
	//es para editar un registro existente
	else{
		editarDatosGenerales(record);
	}
}

function editarDatosGenerales(record){
	
	console.log("edito");
	$.ajax({
		method: "POST",
		url: "/EditarDatosGeneralesProveedor",
		data: {"_csrf": $('#token').val(),
			lista:JSON.stringify(record)
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
				      position: 'center',
			          icon: 'success',
			          title: '¡Se han editado los datos del proveedor!',
			          showConfirmButton: false,
			          timer: 1550,
				      onClose: () => {
				    	  $('#datos-alta-contactos-tab').click();
				      }
				})
			}
			else{
				Swal.fire({
				      position: 'center',
			          icon: 'error',
			          title: '¡Algo salió mal, intente más tarde!',
			          showConfirmButton: false,
			          timer: 1550
				})
			}
		},
		error: (e) => {
		}
	})
}


function anadirContactoTablita(){
	var nombre = $('#altaNombreContacto').val();
	var cargo = $('#altaCargoContacto').val();
	var correo = $('#altaCorreoContacto').val();
	var telefono = $('#altaTelefonoContacto').val();
	var extension = $('#altaExtensionContacto').val();
	console.log("si entra aca");
	
	var tabla = $('#tablitaContactos').DataTable();
	
	tabla.row.add([	
		 nombre,
		 cargo ,
		 correo ,
		 telefono ,
		 extension,
		 "<input type='hidden' value=''>" +
		 "<button class='btn btn-danger btn-circle btn-sm popoverxd borrar'><i class='fas fa-times'></i></button>"    
		 ]).node().id ="row";
	tabla.draw( false );
}

//=Borrar registro de la tabla de agregar una muestra===========//
//																//
$(document).on('click', '.borrar', function (event) { 			//
event.preventDefault();                         				//
let hola = $(this).closest('tr').remove();        				//
$('#tablitaContactos').DataTable().row(hola).remove().draw();   //
});                                                   			//
//==============================================================//

function guardarContactosProveedor(){
	var listaContactos = [];
	var sizeTable = $("#tablitaContactos").DataTable().rows().count();
	var idProveedor = $('#datosPrueba').val();
	var contadorsito = 0;
	$("#tablitaContactos").DataTable().page( 'first' ).draw( 'page' );
	console.log(sizeTable);
	for(i=1; i<=sizeTable; i++){
		var filas = $("#tablitaContactos").find('tr');
		var celdas = $(filas[contadorsito+1]).find("td");
		var record = {nombreContacto:       $(celdas[0]).text(), 
					 cargoContacto: 	   $(celdas[1]).text(), 
					 correoContacto:  	   $(celdas[2]).text(), 
					 telefonoContacto:     $(celdas[3]).text(),
					 extensionContacto:    $(celdas[4]).text(),
					 idContactoProveedor:  $($(celdas[5]).children("input")[0]).val()};
		listaContactos.push(record);
		
		if(contadorsito==4){
			contadorsito=-1;
			$("#tablitaContactos").DataTable().page( 'next' ).draw( 'page' );
		}
		contadorsito++;
    }
	$("#tablitaContactos").DataTable().draw(true);
	console.log(listaContactos);
	//Valida que sea un nuevo registro
	if($('#datoContacto').val()=="" || $('#datoContacto').val()==null || $('#datoContacto').val()==undefined){
		$.ajax({
			method: "POST",
			url: "/GuardarContactosProveedor",
			data: {"_csrf": $('#token').val(),
				lista:JSON.stringify(listaContactos),
				idProveedor
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
				if(data[0]!="falso"){
					$('#datoContacto').val("editar");
					$("#tablitaContactos").DataTable().page( 'first' ).draw( 'page' );
					
					contadorsito = 0;
					for(i=1; i<=sizeTable; i++){
						var filas = $("#tablitaContactos").find('tr');
		    			var celdas = $(filas[contadorsito+1]).find("td");
		    			$($(celdas[5]).children("input")[0]).val(data[i-1]);
		    			if(contadorsito==4){
		    				contadorsito=-1;
		    				$("#tablitaContactos").DataTable().page( 'next' ).draw( 'page' );
		    			}
		    			contadorsito++;
					}
					$("#tablitaContactos").DataTable().draw(true);
					
					Swal.fire({
					      position: 'center',
				          icon: 'success',
				          title: '¡Se ha agregado un nuevo proveedor!',
				          showConfirmButton: false,
				          timer: 1850,
					      onClose: () => {
					    	  console.log(data);
					    	  $('#datos-compras-tab').click();
					      }
					})
				}
				else{
					Swal.fire({
					      position: 'center',
				          icon: 'error',
				          title: '¡Algo salió mal, intente más tarde o asegúrese de llenar el primer formulario!',
				          showConfirmButton: false,
				          timer: 4500
					})
				}
			},
			error: (e) => {
			}
		})
	}
	
	//Valida que sea un registro a editar
	else{
		EditarContactosProveedor(idProveedor, listaContactos);
		
	}

}

function EditarContactosProveedor(idProveedor, listaContactos){
	var sizeTable = $("#tablitaContactos").DataTable().rows().count();
	
	$.ajax({
		method: "POST",
		url: "/EditarContactosProveedor",
		data: {"_csrf": $('#token').val(),
			lista:JSON.stringify(listaContactos),
			idProveedor
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
			if(data[0]!="falso"){
				
				$("#tablitaContactos").DataTable().page( 'first' ).draw( 'page' );
				var contadorsito = 0;
				for(i=1; i<=sizeTable; i++){
					var filas = $("#tablitaContactos").find('tr');
	    			var celdas = $(filas[contadorsito+1]).find("td");
	    			$($(celdas[5]).children("input")[0]).val(data[i-1]);
	    			if(contadorsito==4){
	    				contadorsito=-1;
	    				$("#tablitaContactos").DataTable().page( 'next' ).draw( 'page' );
	    			}
	    			
	    			contadorsito++;
				}
				$("#tablitaContactos").DataTable().draw(true);
				
				Swal.fire({
					position: 'center',
					icon: 'success',
					title: '¡Se han editado los contactos del proveedor!',
					showConfirmButton: false,
					timer: 1850,
					onClose: () => {
						console.log(data);
						$('#datos-compras-tab').click();
					}
				})
			}
			else{
				Swal.fire({
				      position: 'center',
			          icon: 'error',
			          title: '¡Algo salió mal, intente más tarde!',
			          showConfirmButton: false,
			          timer: 4500
				})
			}
		},
		error: (e) => {
		}
	})
}

function GuardarDatosCredito(){
	
	var manejoCredito = (document.getElementById("manejoCredito").checked==true)?"1":"0";
	var diasCredito = $('#diasCredito').val();
	var limiteCredito = $('#limiteCredito').val();
	var saldoCredito = $('#saldoCredito').val();
	var formaPago = $('#formaPago').val();
	var observaciones = $('#pagoObservacion').val();
	var idProveedor = $('#datosPrueba').val();
	
	var record = {manejoCredito:  	manejoCredito, 
				  diasCredito: 		diasCredito,
				  limiteCredito: 	limiteCredito,
				  saldoCredito: 	saldoCredito,
				  formaPago: 		formaPago,
				  observaciones: 	observaciones,
				  idProveedor: 		idProveedor}
	
	console.log(record);
	
	if($('#datoCredito').val()=="" || $('#datoCredito').val()==null || $('#datoCredito').val()==undefined){
		$.ajax({
			method: "POST",
			url: "/GuardarCreditoProveedor",
			data: {"_csrf": $('#token').val(),
				lista:JSON.stringify(record),
				idProveedor
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
					$('#datoCredito').val("editar");
					Swal.fire({
						position: 'center',
						icon: 'success',
						title: '¡Se han guardado los datos de crédito del proveedor!',
						showConfirmButton: false,
						timer: 1850
					})
				}
				else{
					Swal.fire({
					      position: 'center',
				          icon: 'error',
				          title: '¡Algo salió mal, intente más tarde o asegúrese de llenar el primer formulario!',
				          showConfirmButton: false,
				          timer: 4500
					})
				}
			},
			error: (e) => {
			}
		})
	}
	else{
		EditarDatosCredito(idProveedor, record);
	}
	
}

function EditarDatosCredito(idProveedor, record){
	$.ajax({
		method: "POST",
		url: "/EditarCreditoProveedor",
		data: {"_csrf": $('#token').val(),
			lista:JSON.stringify(record),
			idProveedor
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
					position: 'center',
					icon: 'success',
					title: '¡Se han editado los datos de crédito del proveedor!',
					showConfirmButton: false,
					timer: 1850
				})
			}
			else{
				Swal.fire({
				      position: 'center',
			          icon: 'error',
			          title: '¡Algo salió mal, intente más tarde!',
			          showConfirmButton: false,
			          timer: 4500
				})
			}
		},
		error: (e) => {
		}
	})
}


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
	var validador = 1;
	
	if($('#proveedorNombre').val()			==null || $('#proveedorNombre').val()			== "" || $('#proveedorNombre').val()		==undefined ||
	   $('#proveedorTipo').val()			==null || $('#proveedorTipo').val()				== "" || $('#proveedorTipo').val()			==undefined ||
	   $('#proveedorCalle').val()			==null || $('#proveedorCalle').val()			== "" || $('#proveedorCalle').val()			==undefined ||
	   $('#proveedorEstado').val()	 		==null || $('#proveedorEstado').val()			== "" || $('#proveedorEstado').val()		==undefined ||
	   $('#proveedorPais').val()		   	==null || $('#proveedorPais').val()				== "" || $('#proveedorPais').val()			==undefined ||
	   $('#proveedorTelefono').val()     	==null || $('#proveedorTelefono').val()			== "" || $('#proveedorTelefono').val()		==undefined ){
		validador = 0;
		Swal.fire({
		      position: 'center',
	          icon: 'error',
	          title: '¡Todos los campos obligatorios deben ser llenados correctamente!',
	          showConfirmButton: false,
	          timer: 2750,
		})
	}
	
	else if ($('#proveedorTelefono').val().length!=10){
		validador = 0;
		Swal.fire({
		      position: 'center',
	          icon: 'error',
	          title: '¡Ingrese un número de teléfono válido!',
	          showConfirmButton: false,
	          timer: 2750,
		})
	}

	else{	
		if(document.getElementById("inlineFormCheck").checked==false && 
						 				($('#proveedorNumExt').val()==null || 
				 						$('#proveedorNumExt').val()== "" || 
				 						$('#proveedorNumExt').val()==undefined)){
			Swal.fire({
			      position: 'center',
		          icon: 'error',
		          title: '¡Ingrese un número exterior!',
		          showConfirmButton: false,
		          timer: 2750,
			})
		
		}
		else{
			if(validador==1){
				console.log("si");
				guardarDatosGenerales();
			}
		}
	}
}

function validarContacto(){
	var validarCorreo = /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i;
	var validarTelefono = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/im;
	$('#altaNombreContacto').val();
	$('#altaCargoContacto').val();
	$('#altaCorreoContacto').val();
	$('#altaTelefonoContacto').val();
	$('#altaExtensionContacto').val();
	validador=1;
	
	if ($('#altaNombreContacto').val().length== null || $('#altaNombreContacto').val().length== "" || $('#altaNombreContacto').val().length== undefined){
		 validador=0;
			Swal.fire({
			      position: 'center',
		          icon: 'error',
		          title: '¡Ingrese un nombre válido!',
		          showConfirmButton: false,
		          timer: 2750,
			})
	}
	else if ($('#altaCargoContacto').val()== null || $('#altaCargoContacto').val().length== "" || $('#altaCargoContacto').val().length== undefined){
		 validador=0;
			Swal.fire({
			      position: 'center',
		          icon: 'error',
		          title: '¡Ingrese un cargo válido!',
		          showConfirmButton: false,
		          timer: 2750,
			})
	}
	else if (!validarCorreo.test($('#altaCorreoContacto').val()) || $('#altaCorreoContacto').val()== null || $('#altaCorreoContacto').val().length== "" || $('#altaCorreoContacto').val().length== undefined){
		  
		 validador=0;
			Swal.fire({
			      position: 'center',
		          icon: 'error',
		          title: '¡Ingrese un correo válido!',
		          showConfirmButton: false,
		          timer: 2750,
			})
	}
	else if ($('#altaTelefonoContacto').val().length!=10 || !validarTelefono.test($('#altaTelefonoContacto').val())){
		 validador=0;
			Swal.fire({
			      position: 'center',
		          icon: 'error',
		          title: '¡Ingrese un número de teléfono válido!',
		          showConfirmButton: false,
		          timer: 2750,
			})
	}
	else if ($('#altaExtensionContacto').val().length!=3){
			validador = 0;
			Swal.fire({
			      position: 'center',
		          icon: 'error',
		          title: '¡Ingrese una extensión válida!',
		          showConfirmButton: false,
		          timer: 2750,
			})
	}
	else{
		 if(validador==1){
			 anadirContactoTablita();
		 }
	 }
}

function validarTablaContactos(){
	var tablita = $("#tablitaContactos").DataTable().rows().data().toArray();
	var validador=1;
	if (tablita[0]==null){
		validador=0;
		Swal.fire({
		      position: 'center',
	          icon: 'error',
	          title: '¡Ingrese al menos un contacto a la tabla!',
	          showConfirmButton: false,
	          timer: 2750,
		})
	}
	else{
		if(validador==1){
			guardarContactosProveedor();
		}
	}
}

function validarDatosCredito(){
	
	var validador=1;
	
	if($('#formaPago').val()=="" || $('#formaPago').val()==null || $('#formaPago').val()==undefined){
		validador=0;
		Swal.fire({
			position: 'center',
			icon: 'error',
			title: '¡Seleccione una forma de pago!',
			showConfirmButton: false,
			timer: 2750,
		})
	}
	
	
	else{
		if(document.getElementById("manejoCredito").checked==true && 
							   ($('#diasCredito').val()==null || 
								$('#diasCredito').val()== "" || 
								$('#diasCredito').val()==undefined ||
								$('#limiteCredito').val()==null || 
								$('#limiteCredito').val()== "" || 
								$('#limiteCredito').val()==undefined ||
								$('#saldoCredito').val()==null || 
								$('#saldoCredito').val()== "" || 
								$('#saldoCredito').val()==undefined)){
			validador=0;
			Swal.fire({
				position: 'center',
				icon: 'error',
				title: '¡Complete toda la información del crédito o desmarque la casilla!',
				showConfirmButton: false,
				timer: 4550,
			})

		}
		else{
			if(validador==1){
				GuardarDatosCredito();
			}
		}
	}
}

//ocultar o mostrar el numero interior y exterior//
$('#inlineFormCheck').on("change", function(){	//
	var checked = this.checked;				  	//
    if (checked) {								//
        $('.check').hide();						//
        $('#proveedorNumExt').val("0")			//
        $('#proveedorNumInt').val("")			//
    } else {									//
    	$('.check').show();						//
    }											//
})												//
//===============================================//

//ocultar o mostrar el numero interior y exterior//
$('#manejoCredito').on("change", function(){	//
	var checked = this.checked;				  	//
    if (checked) {								//
        $('.checkCredito').show();				//
    } else {									//
    	$('.checkCredito').hide();				//
    	$('#diasCredito').val("");				//
    	$('#limiteCredito').val("");			//
    	$('#saldoCredito').val("");				//
    }											//
})												//
//===============================================//

$('#nomenclatura').change(function() {
	
	  $.ajax({
	        data: {nomen:$("#nomenclatura").val()},
	        url:   '/validar-nomen-proveedor',
	        type:  'GET',
	        success:  function (r) 
	        {
	        	if ( r == true ){
	        		
	        		   	 Swal.fire({
	        					position: 'center',
	        					icon: 'error',
	        					title: 'Esta nomenclatura ya fue registrada',
	        					showConfirmButton: false,
	        					timer: 2500
	        				})
	        		     
	        		   	$("#nomenclatura").val(null);
	        	}
	        	//console.log ("la repuesta es:"+ r)
	        },
	        error: function(){
	            alert('Ocurrio un error en el servidor de modelo ..');
	            select.prop('disabled', false);
	        }
	    });
	  
	})
