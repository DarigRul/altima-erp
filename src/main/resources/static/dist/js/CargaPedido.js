
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
        	  
        	  var isVisible = $("#fechaTallas").is(":visible");
        	  var   valid ;
        	  if ( isVisible == true){
        			var fecha = moment($('#fechaTallas').val());
        			valid = validateDateRange(fecha );
        	  }
        	  else{
        		  valid= true;
        	  }
        
        	console.log(document.getElementById("cargaTipopedido").value);
        	console.log(document.getElementById("cargaEmpresa").value);
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
                	 Swal.fire({
                         title: 'Cargando ',
                         html: 'Por favor espere, se est&aacute; extrayendo la informaci&oacute;n',// add html attribute if you want or remove
                         allowOutsideClick: false,
                         timerProgressBar: true,
                         onBeforeOpen: () => {
                             Swal.showLoading()
                         },
                     });
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
                	 else if (data =="5"){
                		 Swal.fire({
                     		 position: 'center',
                  				icon: 'warning',
                  				title: 'Solo se puede realizar un Resurtido por pedido'
                             
                          
                		 });    
                     	
                	 }
                	 else if ( data =="6"){
                		 Swal.fire({
                     		 position: 'center',
                  				icon: 'error',
                  				title: 'Solo se puede realizar un máximo de 2 Resurtidos por pedido'

                          });
                     	
                	 }
                	 else if (data =="7"){
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
	                             title: 'No se puede cerrar el pedido',
	                             showConfirmButton: false,
	                             timer: 2250
	                             
	                             
	                       });
                		
                	}

        			
               	 
                 }
            })
      		
		}
      	
//////////////////////////////////////////////////////////////////////////////////////
      	//desarrollado por Victor Hugo Garcia Ilhuicatzi
      	
function anadirExtras(idPedido){

	$('#idPed').val(idPedido);
	$('#numCubres').val("");
	$('#numPortas').val("");
	$('#numOtros').val("");
	
	$('#Otro').hide();
	$('#Otro').val("");
	$('#cubrePolvo').prop("checked", false);
	$('#portaTraje').prop("checked", false);
	$('#otroExtra').prop("checked", false);
	
	$.ajax({
        type: "GET",
        url: "/traerPedido",
        data: { 
     	   idPedido: idPedido
        },
        success: function(data) {
     	   if(data==null){
     		  Swal.fire({
	         	  position: 'center',
	               icon: 'error',
	               title: 'Algo salió mal, intente más tarde',
	               showConfirmButton: false,
	               timer: 2250
	         });
     	   }
     	   
     	   else{
     		   console.log(data);
     		   if(data.cubrePolvo!="" && data.cubrePolvo != null){
     			  $('#cubrePolvo').prop("checked", true);
     			 $('#numCubres').val(data.cubrePolvo);
     		   }
     		   if(data.portaTraje != "" && data.portaTraje != null){
     			  $('#portaTraje').prop("checked", true);
     			 $('#numPortas').val(data.portaTraje);
     		   }
     		   if(data.otros != null && data.otros != ""){
     			  $('#otroExtra').prop("checked", true);
     			 $('#numOtros').val(data.otros);
     			 $('#Otro').val(data.otrosTexto);
     			$('#Otro').show();
     		   }
     	   }
     	  $('#extrasCargaPedido').modal("toggle");
         }
    })
}

//ocultar o mostrar el cuadro de texto del modal de extras//
$('#otroExtra').on("change", function(){		//
	var checked = this.checked;				  	//
    if (checked) {								//
        $('#Otro').show();						//
    }											//
    else{										//
    	$('#Otro').hide();						//
    }											//
})												//
//===============================================//

function guardarExtra(){
	if(($('input:checkbox[name=cubrePolvo]:checked').val() == "Cubre polvo" && ($('#numCubres').val()!='' && $('#numCubres').val()!=null && $('#numCubres').val()!=undefined)) || 
			($('input:checkbox[name=portaTraje]:checked').val() == "Porta traje"  && ($('#numPortas').val()!='' && $('#numPortas').val()!=null && $('#numPortas').val()!=undefined)) ||
			($('#Otro').val() != "" && $('input:checkbox[name=otroExtra]:checked').val()== "Otro"  && ($('#numOtros').val()!='' && $('#numOtros').val()!=null && $('#numOtros').val()!=undefined))){
		var cubrePolvo = "";
		var portaTraje = "";
		var otros 	   = "";
		var otrosTexto = "";
		if($('input:checkbox[name=cubrePolvo]:checked')){
		var cubrePolvo = $('#numCubres').val();
		}
		if($('input:checkbox[name=portaTraje]:checked')){
		var portaTraje = $('#numPortas').val();
		}
		if($('input:checkbox[name=otroExtra]:checked')){
		var otros 	   = $('#numOtros').val();
		var otrosTexto = $('#Otro').val();
		}
		var idPedido = $('#idPed').val();
		
		 $.ajax({
               type: "GET",
               url: "/guardarExtras",
               data: { 
            	   cubrePolvo: cubrePolvo,
            	   portaTraje: portaTraje,
            	   otros: otros,
            	   otrosTexto: otrosTexto,
            	   idPedido: idPedido
               },
               success: function(data) {
            	   if(data==1){
	            	   Swal.fire({
	                  	  position: 'center',
	                        icon: 'success',
	                        title: 'Se ha guardado correctamente',
	                        showConfirmButton: false,
	                        timer: 2250,
	                        onClose: () => {
					        	location.reload();
						  }
	                  });
            	   }
            	   else{
            		   Swal.fire({
            	         	  position: 'center',
            	               icon: 'error',
            	               title: 'Algo salió mal, intente más tarde',
            	               showConfirmButton: false,
            	               timer: 2250
            	         });
            	   }
                }
           })
		
	}
	
	else{
		Swal.fire({
         	  position: 'center',
               icon: 'error',
               title: 'No se puede insertar campos vacíos',
               showConfirmButton: false,
               timer: 2250
         });
	}
}