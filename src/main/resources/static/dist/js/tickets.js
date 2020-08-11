function listar(id) {
	var fila="";
	 $.ajax({
	        data: {id:id},
	        url:   '/lista-seguimientos',
	        type:  'GET',
	        success:  function (r) 
	        {
	            $(r).each(function(i, v){ // indice, valor
	                
	            	fila = '<tr> <td>'+v[1]+'</td>  <td >'+ v[2] +'</td> <td >'+ v[3] +'</td> <td >'+ v[4] +'</td>  <td   class="text-center"><a onclick="eliminar('+v[0]+')" class="btn btn-danger btn-circle btn-sm popoverxd" data-target="popover" data-placement="top" data-content="Eliminar"><i class="fas fa-times text-white"></i></a></td> </tr>'+fila ;
	            	
	            })
	            document.getElementById("tableSeguimientoBody").innerHTML =fila;
	       	
	            
	        },
	        error: function(){
	            alert('Ocurrio un error en el servidor de modelo ..');
	            select.prop('disabled', false);
	        }
	    });
	 
	 
	   $("#estatus").val(id);
		 $('#estatus').selectpicker('refresh');
		 $("#comentario").val(null);
}

function seguimiento(e) {
	var id = e.getAttribute("id");
	 $("#tableSeguimiento tbody").empty();
	 listar(id)
	 $("#idTicket2").val(id);
	 
	 
	 
	 
}

function agregarSeguimiento() {	
	 var fila="";
	if ( document.getElementById("estatus").value && 
			document.getElementById("comentario").value ){
		
		var estatus = $("#estatus").val();

		var comentario  = $("#comentario").val(); 
		var idTicket2  = $("#idTicket2").val(); 
		
		$.ajax({
	        type: "POST",
	        url:"/guardar-seguimiento-ticket",
	        data: { 
	        	"idTicket":idTicket2,
	        	"estatus":estatus,
	        	"comentario":comentario,
	        	
	             "_csrf": $('#token').val()
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
	        	
	        
	        	 Swal.fire({
	        		 position: 'center',
	     				icon: 'success',
	     				title: 'Agregado correctamente',
	     				showConfirmButton: false,
						timer: 1250
	                 
	             });
	       },
	       complete: function() {   
	    	   listar(idTicket2)
	    	   
			
		    },
	    })
	}
	else{
		Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Datos incompletos',
            showConfirmButton: false,
            timer: 1250
          })   
	}

}

function eliminar(id) {
	console.log(id)
	
	var idTicket2  = $("#idTicket2").val(); 
	Swal.fire({
				  title: '&iquest;Est&aacute; seguro que desea dar de baja a este seguimiento?',
				  icon: 'warning',
				  showCancelButton: true,
				  confirmButtonColor: '#3085d6',
				  cancelButtonColor: '#d33',
				  cancelButtonText: 'Cancelar',
				  confirmButtonText: 'Si, dar de baja',
				  reverseButtons: true

				}).then((result) => {
				  if (result.value) {
					  $.ajax({
						  data: {id:id},
					        url:   '/baja-seguimientos',
					        type:  'GET',
					        beforeSend: function () {
					        	 Swal.fire({
					        		 position: 'center',
					     				icon: 'success',
					     				title: 'Dado de baja correctamente',
					                 allowOutsideClick: false,
					                 timerProgressBar: true,
					                 showConfirmButton: false,
					                 onBeforeOpen: () => {
					                    
					                 },
					             });
					        	
					        },
					    
					        success: function(data) {
					        	
					        
					        	 Swal.fire({
					        		 position: 'center',
					     				icon: 'success',
					     				title: 'Dado de baja correctamente',
					     				showConfirmButton: false,
										timer: 1250
					                 
					             });
					       },
					       complete: function() {   
					    	   listar(idTicket2)
							
						    },
					    })
					  
					  
					  
					 
				  }
				})
	
}

function editar(e) {
	var id = e.getAttribute("id");
	console.log (id)
	
	$.ajax({
	        data: {id:id},
	        url:   '/buscar-ticket',
	        type:  'GET',
	        success:  function (r) 
	        {	
	        	$("#idEmpleadoSolicitante").val(r.idEmpleadoSolicitante);
	     	   	$('#idEmpleadoSolicitante').selectpicker('refresh');
	        
	     	   	$("#idEmpleadoAuxiliar").val(r.idEmpleadoAuxiliar);
	     	   	$('#idEmpleadoAuxiliar').selectpicker('refresh');
	     	 
	     	   	$("#idLookup").val(r.idLookup);
	     	   	$('#idLookup').selectpicker('refresh')
	     	
	     	   
	     	  $("#descripcion").val(r.descripcion);
	     	   	
	     	   	if (r.fechaCalendario == 1 ){
	     	   	$("#fechaCalendario").prop("checked", true);
	     	   document.getElementById('fechaInicio').readOnly = false;
	          	document.getElementById('fechaFin').readOnly = false;
		     	
	     	   	}
	     	 
	     	$("#fechaInicio").val(r.fechaInicio);
	     	
	     	$("#fechaFin").val(r.fechaFin);
	     	$("#idTicket").val(r.idTicket);
	     	 
	        },
	        error: function(){
	        }
	    });
	$('#nuevoTicket').modal('show');
}

function limpiarForm(solicitud, auxiliar){
	
	$("#idEmpleadoSolicitante").val(solicitud);
	$('#idEmpleadoSolicitante').selectpicker('refresh');

	   	$("#idEmpleadoAuxiliar").val(auxiliar);
	   	$('#idEmpleadoAuxiliar').selectpicker('refresh');
	   	
	   	$("#idLookup").val(null);
 	   	$('#idLookup').selectpicker('refresh');
 	   	
 	   $("#descripcion").val(null);
	   	
	   	
	   	$("#fechaCalendario").prop("checked", false);
	   document.getElementById('fechaInicio').readOnly = true;
     	document.getElementById('fechaFin').readOnly = true;
    	
	   	
	 
	$("#fechaInicio").val(null);
	
	$("#fechaFin").val(null);
	$("#idTicket").val(null);
	
}
function comprobar(obj)
{   
    if (obj.checked){
    	document.getElementById('fechaInicio').readOnly = false;
       	document.getElementById('fechaFin').readOnly = false;
   } else{
	   
   	document.getElementById('fechaInicio').readOnly = true;
	document.getElementById('fechaFin').readOnly = true;
   }     
}


function valida_envia(){
   	if (document.fvalida.idEmpleadoSolicitante.value.length==0){
   		Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Seleccione un solicitante',
            showConfirmButton: false,
            timer: 1250
          }) 
      		//document.fvalida.nombre.focus()
      		return 0;
   	}
   	
   	if (document.fvalida.idEmpleadoAuxiliar.value.length==0){
   		Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Seleccione un auxiliar',
            showConfirmButton: false,
            timer: 1250
          }) 
      		//document.fvalida.nombre.focus()
      		return 0;
   	}
   	
   	if (document.fvalida.idLookup.value.length==0){
   		Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Seleccione una categoria',
            showConfirmButton: false,
            timer: 1250
          }) 
      		//document.fvalida.nombre.focus()
      		return 0;
   	}
   	
   	if (document.fvalida.descripcion.value.length==0){
   		Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Ingrese una descripcion',
            showConfirmButton: false,
            timer: 1250
          }) 
      		//document.fvalida.nombre.focus()
      		return 0;
   	}
   	
   	if (  $('#fechaCalendario').is(':checked')){
   		console.log("si entra")
   		
   		if (document.fvalida.fechaInicio.value.length==0){
   			Swal.fire({
   	            position: 'center',
   	            icon: 'warning',
   	            title: 'Ingrese la fecha de inicio',
   	            showConfirmButton: false,
   	            timer: 1250
   	          }) 
   	      		//document.fvalida.nombre.focus()
   	      		return 0;
   		}
   		
   		if (document.fvalida.fechaFin.value.length==0){
   			Swal.fire({
   	            position: 'center',
   	            icon: 'warning',
   	            title: 'Ingrese la fecha de finalizacion',
   	            showConfirmButton: false,
   	            timer: 1250
   	          }) 
   	      		//document.fvalida.nombre.focus()
   	      		return 0;
   		}
   		
   	}
	
   
	
   	document.fvalida.submit();
}