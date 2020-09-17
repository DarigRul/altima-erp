
$('#idEmpleado').on('change', function() {
	var txt = $("#idEmpleado option:selected").text(); 
	var val = $("#idEmpleado option:selected").val(); 
	$("#nombreEmpleado").val(txt);
	console.log (txt);
  })
  
  function agregarSPF(){
	
	if ( document.getElementById("idEmpleado").value){
		
		var idPedidoSpf = $("#idPedidoSpf").val();
		var idEmpleado =$("#idEmpleado").val();
		var nombreEmpleado = $("#nombreEmpleado").val();
		
		$.ajax({
	        type: "POST",
	        url:"/guardar-spf-individual",
	        data: { 
	        	"idPedidoSpf":idPedidoSpf,
	        	"idEmpleado":idEmpleado,
	        	"nombreEmpleado":nombreEmpleado,
	        	
	             "_csrf": $('#token').val()
	        },
	        beforeSend: function () {
	        	
	        	 document.getElementById("btn-enviar").disabled=true;
	        },
	    
	        success: function(data) {
	        	
	        	
	        	 Swal.fire({
	        		 position: 'center',
	     				icon: 'success',
	     				title: 'Agregado correctamente',
	     				showConfirmButton: false,
						timer: 1250
	                 
	             });
	       },
	       complete: function() {
	    	   document.getElementById("btn-enviar").disabled=false;
	    	   llenar_select();
	    	  
		    },
	    })
		
	}else{
		 Swal.fire({
             position: 'center',
             icon: 'warning',
             title: 'Seleccione un empleado',
             showConfirmButton: false,
             timer: 2500
           })      ; 
	}
}


function llenar_select () {
	var idPedidoSpf = $("#idPedidoSpf").val();
	
	var empleados = $("#idEmpleado");
	 $.ajax({
	        data: {id:idPedidoSpf},
	        url:   '/clientes-spf-disponibles',
	        type:  'GET',
	        success:  function (data) 
	        {
	        	empleados.find('option').remove();
	        	$('.idEmpleado').selectpicker('refresh');
	        	$.each(data, function(key, val) {
	        		empleados.append('<option value="'+val[0]+'">'+val[1]+'  </option> ');
		    		
		    		
            	})
            	$('.idEmpleado').selectpicker("refresh");
	        },
	        error: function(){
	            alert('Ocurrio un error en el servidor de modelo ..');
	            select.prop('disabled', false);
	        }
	    });
	 
}

function editar(id){
	$("#actualizarEmpleadoSPF").val(null);
	$("#idSpfEmpleado2").val(null);
	
	$.ajax({
        data: {id:id},
        url:   '/clientes-spf-buscar',
        type:  'GET',
        success:  function (data) 
        {
        	console.log(data.idSpfEmpleado)
        	$("#actualizarEmpleadoSPF").val(data.nombre_empleado);
        	$("#idSpfEmpleado2").val(data.idSpfEmpleado);
        	
        },
        error: function(){
            alert('Ocurrio un error en el servidor de modelo ..');
           
        }
    });
}


function agregarSPFeditar(){
	
	if ( document.getElementById("actualizarEmpleadoSPF").value){
		
		var idSpfEmpleado = $("#idSpfEmpleado2").val();
		var nombre =$("#actualizarEmpleadoSPF").val();
		
		$.ajax({
	        type: "POST",
	        url:"/clientes-spf-editar",
	        data: { 
	        	"idSpfEmpleado":idSpfEmpleado,
	        	"nombre":nombre,
	        	
	             "_csrf": $('#token').val()
	        },
	        beforeSend: function () {
	        	
	        	 document.getElementById("btn-enviar-editar").disabled=true;
	        },
	    
	        success: function(data) {
	        	
	        	
	        	 Swal.fire({
	        		 position: 'center',
	     				icon: 'success',
	     				title: 'Actualizado correctamente',
	     				showConfirmButton: false,
						timer: 1250
	                 
	             });
	       },
	       complete: function() {
	    	   document.getElementById("btn-enviar-editar").disabled=false;
	    	  location.reload();
	    	  
		    },
	    })
		
	}else{
		Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Ingrese un nombre',
            showConfirmButton: false,
            timer: 2500
          })      ; 
	}
}

function eliminar(e) {
	Swal.fire({
		  title: '&iquest;Est&aacute; seguro que desea eliminar a este empleado en SPF?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  cancelButtonText: 'Cancelar',
		  confirmButtonText: 'Si, eliminar',
		  reverseButtons: true

		}).then((result) => {
		  if (result.value) {
			 
			  
			  $.ajax({
			        type: "GET",
			        url:"/eliminar-spf/{id}",
			        data: { 
			        	"id":e
			        },
			        beforeSend: function () {
			        },
			    
			        success: function(data) {
			        	
			        	
			        	 Swal.fire({
			        		 position: 'center',
			     				icon: 'success',
			     				title: 'Eliminado correctamente',
			     				showConfirmButton: false,
								timer: 1250
			                 
			             });
			       },
			       complete: function() {
			    	  location.reload();
			    	  
				    },
			    })
			  
		  }
		})
}

function agregarmasivo (){
	
	var idPedidoSpf = $("#idPedidoSpf").val();
	$.ajax({
        type: "POST",
        url:"/agregar-spf-masivo",
        data: { 
        	"idPedidoSpf":idPedidoSpf,
        	
             "_csrf": $('#token').val()
        },
        beforeSend: function () {
        	
        	
        },
    
        success: function(data) {
        	
        	if ( data =="Lista vacia"){
        		Swal.fire({
              		 position: 'center',
           				icon: 'warning',
           				title: 'No hay nada que agregar',
           				showConfirmButton: false,
      					timer: 1250
                       
                   });
        	}else {
        		Swal.fire({
           		 position: 'center',
        				icon: 'success',
        				title: 'Agregados correctamente',
        				showConfirmButton: false,
   					timer: 1250
                    
                });
        		location.reload();
        	}
        	 
       }
    })
}