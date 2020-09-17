
         $(document).ready(function() {
        	 
         	   $('.selectpicker').selectpicker({
         		   style: 'border border-bootstrap'
         	   });
         	   
         	    $("#id-pedido").prop('disabled', true);
         	   
         	    $("#cargaTipopedido").change(function(){
         	        // Guardamos el select de cursos
         	        var pedido = $("#id-pedido");
         
         	        var tipoPedido = $(this);
         	        var id = document.getElementById("cargaEmpresa").value;
         	        
         	        console.log("El select seleccionado es: "+$(this).val());
         
         	        if($(this).val() != 1)
         	        {
         	        	
         	       
         	            $.ajax({
         	                data: { id : id },
         	                url:   '/mostrar-pedidos',
         	                type:  'GET',
         	                
         	                beforeSend: function () 
         	                {
         	                	tipoPedido.prop('disabled', true);
         	                },
         	                success:  function (r) {
         	                	$("#div-ocultar").show();
         	                	$("#div-ocultar2").show();
         	                	 
         	                	tipoPedido.prop('disabled', false);
         
         	                    // Limpiamos el select
         	                    pedido.find('option').remove();
         
         	                    $(r).each(function(i, v){ // indice, valor
         	                    	console.log("hola"+ v[1] );
         	                    	pedido.append('<option value="' + v[0] + '">' + v[1] + '</option>');
         	                    	
         	                    	
         	                    	$('.idPedido').selectpicker('refresh');
         	                    	
         	                    })
         
         	                    $('.idPedido').prop('disabled', false);
         	                    $('.idPedido').selectpicker('refresh');
         	                },
         	                error: function()
         	                {
         	                    alert('Ocurrio un error en el servidor ..');
         	                    tipoPedido.prop('disabled', false);
         	                }
         	            });
         	        }
         	        else
         	        {
         	        	$("#div-ocultar").hide();
         	        	$("#div-ocultar2").hide();
         	        	$('.idPedido').find('option').remove();
         	        	$('.idPedido').prop('disabled', true);
                         $('.idPedido').selectpicker('refresh');
         	        }
         	    })
         	});
         
         function validateDateRange(fecha) {
        	 
        	 var hoy = new Date();
        	 if(!fecha.isValid() || fecha== null ) {
         		Swal.fire({
     				position: 'center',
     				icon: 'error',
     				title: 'La fecha no es válida.',
     				showConfirmButton: false,
     				timer: 1250
     			})
         		
         		return false; 
         	}
        	if ( hoy >= fecha){
            		Swal.fire({
        				position: 'center',
        				icon: 'error',
        				title: 'La fecha debe ser mayor o igual a la fecha actual.',
        				showConfirmButton: false,
        				timer: 1250
        			})
        			return false;
            	}
        	return true;
         }
         
         
         // Agregar procreso
          function agregar() {
        	 
        	var fecha = moment($('#fechaTallas').val());
        	var   valid = validateDateRange(fecha );
        	
               if (document.getElementById("cargaTipopedido").value && 
             		  document.getElementById("cargaEmpresa").value && valid  ) {
         		 var cargaTipopedido=document.getElementById("cargaTipopedido").value;
         		 var cargaEmpresa=document.getElementById("cargaEmpresa").value;
         		 var id_pedido=document.getElementById("id-pedido").value;
         	
         		
         		$.ajax({
                 type: "POST",
                 url: "/guardar-carga-pedido",
                 data: { 
                 	 "_csrf": $('#token').val(),
                 	'cargaTipopedido': cargaTipopedido,
                 	'cargaEmpresa': cargaEmpresa,
                 	'id_pedido': id_pedido,
                 	'fechaTallas': $('#fechaTallas').val()
                 },
                 
                 beforeSend: function () {
                 	 
                 },
                 success: function(data) {
                	 if ( data =="1"){
                		 Swal.fire({
                     		 position: 'center',
                  				icon: 'success',
                  				title: 'Agregado correctamente',
                              allowOutsideClick: false,
                              timerProgressBar: true,
                              showConfirmButton: false,
                              onBeforeOpen: () => {
                                 
                              },
                          });
                		location.reload()
                	 }
                	 else if (data =="2"){
                		 Swal.fire({
                     		 position: 'center',
                  				icon: 'warning',
                  				title: 'Solo se pueden realizar Stock de pedido cerrados '
                             
                          
                		 });    
                     	
                	 }
                	 else if ( data =="3"){
                		 Swal.fire({
                     		 position: 'center',
                  				icon: 'error',
                  				title: 'Solo se pueden realizar un maximo de 3 Stock por pedido'

                          });
                     	
                	 }
                	 else if (data =="4"){
                		 Swal.fire({
                     		 position: 'center',
                  				icon: 'error',
                  				title: 'Solo se puede realizar Stock un año antes'
                          });
                     	
                	 }
                    },
                    complete: function() {    		
             			
            	    },
             })
               }else 
               {
             	  Swal.fire({
                       position: 'center',
                       icon: 'warning',
                       title: 'Datos Incorrectos',
                       showConfirmButton: false,
                       timer: 1250
                     })      ; 
               }
         }
         
      	function cerrar(e) {
      		var valiMonto="";
      		$.ajax({
                type: "GET",
                url: "/validar-monto-pedido",
                data: { 
                	 "id": e
                },
                success: function(data) {
               	
                	if (data != "Error , no es posible calcular el monto"){
                		Swal.fire({
          				  title: '&iquest;Est&aacute; seguro que desea cerrar este pedido?',
          				  html: '<i class="fas fa-exclamation-triangle text-danger">&nbsp;</i><a class="text-danger">'+data+'</a>',
          				  icon: 'warning',
          				  showCancelButton: true,
          				  confirmButtonColor: '#3085d6',
          				  cancelButtonColor: '#d33',
          				  cancelButtonText: 'Cancelar',
          				  confirmButtonText: 'Si, cerrar',
          				  reverseButtons: true

          				}).then((result) => {
          				  if (result.value) {
          					 
          					 $.ajax({
          		                 type: "GET",
          		                 url: "/cerrar-expediente",
          		                 data: { 
          		                 	 "id": e
          		                 },
          		                 success: function(data) {
          		                	 if ( data == null || data==''   ){
          		                		 Swal.fire({
          		                       	  position: 'center',
          		                             icon: 'success',
          		                             title: 'El pedido fue cerrado correctamente',
          		                             showConfirmButton: false,
          		                             timer: 2250
          		                             
          		                             
          		                       });
          		                		 location.reload(true);
          		                	 }
          		                	 else{
          		                		 Swal.fire({
          			                       	  position: 'center',
          			                             icon: 'error',
          			                             title: 'No cumple con el minimo de piezas requerido.',
          			                             text: data,
          			                             showConfirmButton: true
          			                       });
          		                	 }

          		                	 console.log ( data)
          		                	 
          		                  }
          		             })
          				  }
          				})
                	}
                	else{
                		Swal.fire({
	                       	  position: 'center',
	                             icon: 'error',
	                             title: 'No se puedo cerrar el pedido',
	                             showConfirmButton: false,
	                             timer: 2250
	                             
	                             
	                       });
                		
                	}

        			
               	 
                 }
            })
      		
		}
      	
      	
      	function abrir(e) {
			Swal.fire({
				  title: '&iquest;Est&aacute; seguro que desea abrir este pedido?',
				  text: "",
				  icon: 'warning',
				  showCancelButton: true,
				  confirmButtonColor: '#3085d6',
				  cancelButtonColor: '#d33',
				  cancelButtonText: 'Cancelar',
				  confirmButtonText: 'Si, abrir',
				  reverseButtons: true

				}).then((result) => {
				  if (result.value) {
					 //location.href="/delete-cliente/"+e
					 
					 $.ajax({
		                 type: "GET",
		                 url: "/abrir-expediente",
		                 data: { 
		                 	 "id": e
		                 },
		                 success: function(data) {
		                		 Swal.fire({
		                       	  position: 'center',
		                             icon: 'success',
		                             title: 'El pedido fue abierto correctamente',
		                             showConfirmButton: false,
		                             timer: 2250
		                       });
		                		 location.reload(true);
		                	
		                	 console.log ( data)
		                	 
		                  }
		             })
				  }
				})
		}
      	
      	
      	function cerrarStock(e) {
      		var valiMonto="";
      		$.ajax({
                type: "GET",
                url: "/validar-stock-disponibles",
                data: { 
                	 "id": e
                },
                success: function(data) {
               	
                	if (data != null){
                	
                		Swal.fire({
          				  title: '&iquest;Est&aacute; seguro que desea cerrar este pedido?',
          				  html: '<i class="fas fa-exclamation-triangle text-danger">&nbsp;</i><a class="text-danger">'+data+'</a>',
          				  icon: 'warning',
          				  showCancelButton: true,
          				  confirmButtonColor: '#3085d6',
          				  cancelButtonColor: '#d33',
          				  cancelButtonText: 'Cancelar',
          				  confirmButtonText: 'Si, cerrar',
          				  reverseButtons: true

          				}).then((result) => {
          				  if (result.value) {
          					 
          					 $.ajax({
          		                 type: "GET",
          		                 url: "/cerrar-expediente-Stock",
          		                 data: { 
          		                 	 "id": e
          		                 },
          		                 success: function(data) {
          		                	 if ( data == null || data==''   ){
          		                		 Swal.fire({
          		                       	  position: 'center',
          		                             icon: 'success',
          		                             title: 'El pedido fue cerrado correctamente',
          		                             showConfirmButton: false,
          		                             timer: 2250
          		                             
          		                             
          		                       });
          		                		 location.reload(true);
          		                	 }
          		                	 else{
          		                		 Swal.fire({
          			                       	  position: 'center',
          			                             icon: 'error',
          			                             title: 'No cuenta con piezas.',
          			                             text: data,
          			                             showConfirmButton: true
          			                       });
          		                	 }

          		                	 console.log ( data)
          		                	 
          		                  }
          		             })
          				  }
          				})
                	}
                	else{
                		Swal.fire({
	                       	  position: 'center',
	                             icon: 'error',
	                             title: 'No se puedo cerrar el pedido',
	                             showConfirmButton: false,
	                             timer: 2250
	                             
	                             
	                       });
                		
                	}

        			
               	 
                 }
            })
      		
		}