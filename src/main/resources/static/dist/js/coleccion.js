 console.log("si entre al  js de colleccion");
 
 
	 function agregar() {
		 
		
		    
	    	
		 
		if       (document.getElementById("coorPrendac").value &&   		
	    		  document.getElementById("cantic").value &&
	    		  document.getElementById("pedidoc").value
	    		 ) {
			
			var idPrenda=document.getElementById("coorPrendac").value;
		
			var idPedido=document.getElementById("pedidoc").value;
			var cantidad=document.getElementById("cantic").value;
			
		
			
			
			console.log("El id de prenda es: "+idPrenda);		
			console.log("El id del pedido es: "+idPedido);		
			console.log("la cantidad es  es: "+cantidad);
			  
	
			
			console.log("si entre al 3");
			
			   console.log( $('#token').val());
			
			$.ajax({
	        type: "POST",
	        url:"/guardar-pedido-coleccion-rest",
	        data: { 
	        	//datosMateriales:datosMateriales,
	        		     	        		        	           	        	
	        	'idPrenda': idPrenda,	        	
	        	'idPedido': idPedido,
	        	'cantidad': cantidad,
	        	
	        	
	             "_csrf": $('#token').val(),
	        },
	        beforeSend: function () {
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
	        	
	        },
	    
	        success: function(data) {
           },
           complete: function() {   
        	   location.reload();
    		
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
	            })       
	      }
	}
	