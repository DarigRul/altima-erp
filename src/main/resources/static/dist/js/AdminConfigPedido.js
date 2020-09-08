function limpiar(){
	$("#tipoPedido").val(null);
	$('#tipoPedido').selectpicker('refresh');
	
	$("#nomenclatura").val(null);
	$("#cantidadPrenda").val(null);
	$("#minimoDias").val(null);
	$("#maximoDias").val(null);
	$("#minAdicionales").val(null);
	
	$("#locales").val(null);
	$("#foraneo").val(null);
	$("#diasBordado").val(null);
	$("#nombre").val(null);
	$("#stockTrueFalse").val(null);
	$('#stockTrueFalse').selectpicker('refresh');
	
	$("#minimoPersonas").val(null);
	$("#anticipoTrueFalse").val(null);
	$('#anticipoTrueFalse').selectpicker('refresh');
	
	$("#idConfiguracionPedido").val(null);
	$("#creadoPor").val(null);
	$("#actualizadoPor").val(null);
	$("#fechaCreacion").val(null);
	$("#ultimaFechaModificacion").val(null);
	$("#estatus").val(null);
	$("#minimoPersonas").attr("readonly",true);
}

$('#nombre').change(function() {
	
	  $.ajax({
	        data: {id:$("#tipoPedido").val() , nombre:$("#nombre").val()},
	        url:   '/validar-tipo',
	        type:  'GET',
	        success:  function (r) 
	        {
	        	if ( r == true ){
	        		
	        		   	 Swal.fire({
	        					position: 'center',
	        					icon: 'error',
	        					title: 'Este tipo ya fue registrado',
	        					showConfirmButton: false,
	        					timer: 1250
	        				})
	        		     
	        		   	$("#tipoPedido").val(null);
	        			$('#tipoPedido').selectpicker('refresh');
	        			$("#nombre").val(null)
	        	}
	        	//console.log ("la repuesta es:"+ r)
	        },
	        error: function(){
	            alert('Ocurrio un error en el servidor de modelo ..');
	            select.prop('disabled', false);
	        }
	    });
	  
	})

function valida_envia(){
	//valido el interés
   	if (document.fvalida.tipoPedido.selectedIndex==0){
   	 Swal.fire({
			position: 'center',
			icon: 'error',
			title: 'Seleccione un tipo',
			showConfirmButton: false,
			timer: 1250
		})
      		return 0;
   	}
   	//valido el nombre
   	if (document.fvalida.nomenclatura.value.length==0){
   	 Swal.fire({
			position: 'center',
			icon: 'error',
			title: 'Ingrese la nomenclatura',
			showConfirmButton: false,
			timer: 1250
		})
      		return 0;
   	}
   	if (document.fvalida.nombre.value.length==0){
      	 Swal.fire({
   			position: 'center',
   			icon: 'error',
   			title: 'Ingrese un nombre',
   			showConfirmButton: false,
   			timer: 1250
   		})
         		return 0;
      	}
	
   	if (document.fvalida.cantidadPrenda.value.length==0 || document.fvalida.cantidadPrenda.value<=0 ){
   	 Swal.fire({
			position: 'center',
			icon: 'error',
			title: 'Ingrese la cantidad de la prenda',
			showConfirmButton: false,
			timer: 1250
		})
  		return 0;
	}

   	
   	if (document.fvalida.minimoDias.value.length==0 || document.fvalida.minimoDias.value<=0 ){
   	 Swal.fire({
			position: 'center',
			icon: 'error',
			title: 'Ingrese minimo de dias',
			showConfirmButton: false,
			timer: 1250
		})
  		return 0;
	}

   	
   	if (document.fvalida.maximoDias.value.length==0  || document.fvalida.maximoDias.value<=0 ){
   	 Swal.fire({
			position: 'center',
			icon: 'error',
			title: 'Ingrese maximo de dias',
			showConfirmButton: false,
			timer: 1250
		})
  		return 0;
	}

   	if (document.fvalida.minAdicionales.value.length==0  || document.fvalida.minAdicionales.value<=0 ){
      	 Swal.fire({
   			position: 'center',
   			icon: 'error',
   			title: 'Ingrese minimo de adiccionales',
   			showConfirmButton: false,
   			timer: 1250
   		})
     		return 0;
   	}
   	
   	if (document.fvalida.locales.value.length==0  || document.fvalida.locales.value<=0 ){
   	 Swal.fire({
			position: 'center',
			icon: 'error',
			title: 'Ingrese local',
			showConfirmButton: false,
			timer: 1250
		})
  		return 0;
	}
   	
   	
   	if (document.fvalida.foraneo.value.length==0  || document.fvalida.foraneo.value<=0){
   	 Swal.fire({
			position: 'center',
			icon: 'error',
			title: 'Ingrese una foraneo',
			showConfirmButton: false,
			timer: 1250
		})
  		return 0;
	}
   	
   	if (document.fvalida.diasBordado.value.length==0  || document.fvalida.diasBordado.value<0 ){
   	 Swal.fire({
			position: 'center',
			icon: 'error',
			title: 'Ingrese dias bordado',
			showConfirmButton: false,
			timer: 1250
		})
  		return 0;
	}
   	if (document.fvalida.stockTrueFalse.selectedIndex==0 ){
  	   	 Swal.fire({
  				position: 'center',
  				icon: 'error',
  				title: 'Seleccione un stock',
  				showConfirmButton: false,
  				timer: 1250
  			})
  	  		return 0;
  	
  		
  	}
   	 if (document.fvalida.stockTrueFalse.value == "Si"){
   		if (document.fvalida.minimoPersonas.value.length==0){
   	   	 Swal.fire({
   				position: 'center',
   				icon: 'error',
   				title: 'Ingrese minimo de personas',
   				showConfirmButton: false,
   				timer: 1250
   			})
   	  		return 0;
   		}
   		
   	}
   	
   	
   	
	if (document.fvalida.anticipoTrueFalse.selectedIndex==0){
	   	 Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'Seleccione un anticipo',
				showConfirmButton: false,
				timer: 1250
			})
	      		return 0;
	   	}
 
	
   	
	
   	//el formulario se envia
   	document.fvalida.submit();
}

function editar(e) {
	var id = e.getAttribute("id");
	console.log (id)
	 
	$.ajax({
        data: {id:id},
        url:   '/editar-pedido',
        type:  'GET',
        success:  function (r) 
        {
        	$("#tipoPedido").val(r.tipoPedido);
        	$('#tipoPedido').selectpicker('refresh');
        	
        	$("#nomenclatura").val(r.nomenclatura);
        	$("#nombre").val(r.nombre);
        	$("#cantidadPrenda").val(r.cantidadPrenda);
        	$("#minimoDias").val(r.minimoDias);
        	$("#maximoDias").val(r.maximoDias);
        	$("#minAdicionales").val(r.minAdicionales);	
        	$("#locales").val(r.locales);
        	$("#foraneo").val(r.foraneo);
        	$("#diasBordado").val(r.diasBordado);
        	
        	$("#stockTrueFalse").val(r.stockTrueFalse);
        	$('#stockTrueFalse').selectpicker('refresh');        	if (r.stockTrueFalse == "Si" ){
        			$("#minimoPersonas").attr("readonly",false);
        			$("#minimoPersonas").val(r.minimoPersonas);
        	}else{
        		$("#minimoPersonas").attr("readonly",true);
        		$("#minimoPersonas").val(null);
        	}
        		
        ;
        	$("#anticipoTrueFalse").val(r.anticipoTrueFalse);
        	$('#anticipoTrueFalse').selectpicker('refresh');
        	
        	$("#idConfiguracionPedido").val(r.idConfiguracionPedido);
        	$("#creadoPor").val(r.creadoPor);
        	$("#actualizadoPor").val(r.actualizadoPor);
        	$("#fechaCreacion").val(r.fechaCreacion);
        	$("#ultimaFechaModificacion").val(r.ultimaFechaModificacion);
        	$("#estatus").val(r.estatus);
       
        	
        	
        	
            	console.log (r.nomenclatura)
       
            
       	
            
        },
        error: function(){
            alert('Ocurrio un error en el servidor de modelo ..');
            select.prop('disabled', false);
        }
    });
	 
	 
}

function bajaPedido(e) {
	var id = e.getAttribute("id");
    Swal.fire({
        title: "¿Deseas dar de baja el tipo de pedido?",
        icon: "warning",
        showCancelButton: true,
        cancelButtonColor: "#dc3545",
        confirmButtonColor: "#0288d1",
        confirmButtonText: "Confirmar",
        cancelButtonText: "Cancelar",
    }).then((result) => {
        if (result.value) {
        	
        	$.ajax({
        		data: {id:id},
                url:   '/delete-pedido',
                type:  'GET',
                success:  function (r) 
                {
                	
                	 Swal.fire({
                         position: "center",
                         icon: "success",
                         title: "Tipo de pedido dado de baja correctamente",
                         showConfirmButton: false,
                         timer: 2500,
                     });
                	 location.reload();
                },
                error: function(){
                    alert('Ocurrio un error en el servidor de modelo ..');
                    select.prop('disabled', false);
                }
            });
        }
    });
}

function activarPedido(e) {
	var id = e.getAttribute("id");
    Swal.fire({
        title: "¿Deseas dar de alta el tipo de pedido?",
        icon: "warning",
        showCancelButton: true,
        cancelButtonColor: "#dc3545",
        confirmButtonColor: "#0288d1",
        confirmButtonText: "Confirmar",
        cancelButtonText: "Cancelar",
    }).then((result) => {
        if (result.value) {
        	
        	$.ajax({
        		data: {id:id},
                url:   '/active-pedido',
                type:  'GET',
                success:  function (r) 
                {
                	
                	 Swal.fire({
                         position: "center",
                         icon: "success",
                         title: "Tipo de pedido dado de alta correctamente",
                         showConfirmButton: false,
                         timer: 2500,
                     });
                	 location.reload();
                },
                error: function(){
                    alert('Ocurrio un error en el servidor de modelo ..');
                    select.prop('disabled', false);
                }
            });
           
        }
    });
}