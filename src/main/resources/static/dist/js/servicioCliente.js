function editar (id){
	var url = "/servicio-cliente-editar-solicitud/"+id+"";  
	 $(location).attr('href',url);
}
function detalles (id){
	var url = "/servicio-cliente-detalle-solicitud/"+id+"";  
	 $(location).attr('href',url);
}
function enviar(id) {
	Swal.fire({
        title: '¿Deseas enviar esta solicitud?',
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Enviar',
        confirmButtonColor: '#0288d1',
    }).then((result) => {
        if (result.value && id != null) {
        	$.ajax({
    	        type: "POST",
    	        url:"/enviar_servicio_cliente",
    	        data: {
    	        	'id':id,
    	             "_csrf": $('#token').val()
    	        },
    	        beforeSend: function () {
    	        },
    	        success: function(data) {
    	        	
    	        	 Swal.fire({
    	        		 position: 'center',
    	     				icon: 'success',
    	     				title: 'Enviado correctamente',
    	     				showConfirmButton: false,
    						timer: 1250
    	                 
    	             });
    	       },
    	       complete: function() {
    	    	   location.reload();
    		    },
    	    })
           
        } // ////////////termina result value
    })
}

function rechazar (id){
	
	Swal.fire({
        title: '¿Deseas rechazar esta solicitud?',
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Rechazar',
        confirmButtonColor: '#0288d1',
    }).then((result) => {
        if (result.value && id != null) {
        	
        	$.ajax({
    	        type: "POST",
    	        url:"/rechazar_servicio_cliente",
    	        data: {
    	        	'id':id,
    	             "_csrf": $('#token').val()
    	        },
    	        beforeSend: function () {
    	        },
    	    
    	        success: function(data) {
    	        	
    	        	
    	        	 Swal.fire({
    	        		 position: 'center',
    	     				icon: 'success',
    	     				title: 'Rechazada correctamente',
    	     				showConfirmButton: false,
    						timer: 1250
    	                 
    	             });
    	       },
    	       complete: function() {
    	    	 location.reload();
    		    },
    	    })
           
        } // ////////////termina result value
    })
}

function aceptar (id){
	
	Swal.fire({
        title: '¿Deseas aceptar esta solicitud?',
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Aceptar',
        confirmButtonColor: '#0288d1',
    }).then((result) => {
        if (result.value && id != null) {
        	
        	$.ajax({
    	        type: "POST",
    	        url:"/aceptar_servicio_cliente",
    	        data: {
    	        	'id':id,
    	             "_csrf": $('#token').val()
    	        },
    	        beforeSend: function () {
    	        	
 
    	        },
    	    
    	        success: function(data) {
    	        	
    	        	
    	        	 Swal.fire({
    	        		 position: 'center',
    	     				icon: 'success',
    	     				title: 'Aceptada correctamente',
    	     				showConfirmButton: false,
    						timer: 1250
    	                 
    	             });
    	       },
    	       complete: function() {
    	    	  location.reload();
    		    },
    	    })
           
        } // ////////////termina result value
    })
}