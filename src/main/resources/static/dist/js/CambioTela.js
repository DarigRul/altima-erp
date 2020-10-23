function editar (idPedido, idSolicitud){
	var url = "/coordinados-cambio-tela/"+idPedido+"/"+idSolicitud;  
	 $(location).attr('href',url);
}

function enviar (id){
	
	Swal.fire({
        title: '¿Deseas enviar esta solicitud',
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
    	        url:"/enviar-solicitud",
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
        title: '¿Deseas rechazar esta solicitud',
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
    	        url:"/rechazar-solicitud",
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
        title: '¿Deseas aceptar esta solicitud',
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
    	        url:"/aceptar-solicitud",
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

function SelectPedido(){
	$.ajax({
		method:'GET',
		url:'/listar-pedidos-cerrados',
		data:{},
		success: (data)=>{
			$.each(data, function (key,val){
				$('#numeroPedido').append('<option value="'+val[0]+'"> '+val[1]+'</option>');
			})
			$('#numeroPedido').selectpicker('refresh');
		},
		error:(e)=>{			
		}
	})
}
function infoPedido (id){
	$.ajax({
		method:'GET',
		url:'/info-pedido',
		data:{'id':id},
		success: (data)=>{
			$('#fechaPedido').val(data[0][1]);
			$('#clientePedido').val(data[0][0]);
		},
		error:(e)=>{
		}
	})
}
function agregarSolicitud(){
	if ( $('#motivoPedido').val() != '' && $('#numeroPedido').val() != '' ){
		$.ajax({
			method:'POST',
			url:'/guardar-solicitud-tela',
			data:{
				'idPedido':$('#numeroPedido').val(),
				'motivo': $('#motivoPedido').val(),
				"_csrf": $('#token').val()
				},
		        beforeSend: function () {
		        	 document.getElementById("btn-guarda").disabled=true;
		        },
			success: (data)=>{
				 Swal.fire({
	        		 position: 'center',
	     				icon: 'success',
	     				title: 'Agregado correctamente',
	     				showConfirmButton: false,
						timer: 1250
	             });
				 location.reload();
			},
			error:(e)=>{
			}
		})
	}
	
	else{
		console.log("else entra");
		if ( $('#numeroPedido').val() == '' ){
			Swal.fire({
				position: 'center',
				icon: 'warning',
				title: 'Seleccione un pedido',
				showConfirmButton: false,
				timer: 1250
			})
		}
		if ( $('#motivoPedido').val() == '' ){
			Swal.fire({
				position: 'center',
				icon: 'warning',
				title: 'Ingrese un motivo',
				showConfirmButton: false,
				timer: 1250
			})
		}
	}
}