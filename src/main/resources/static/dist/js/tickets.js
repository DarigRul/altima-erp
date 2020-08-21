function listar(id) {
	
	 $.ajax({
	        data: {id:id},
	        url:   '/lista-seguimientos',
	        type:  'GET',
	        success:  function (r) 
	        {
	        	var tabla = $('#tableSeguimiento').DataTable();
	        	
	        
	        	 
	        	tabla.clear();
	        	    
	            $(r).each(function(i, v){ // indice, valor
	                
	            	tabla.row.add([	
	            		v[1],
	            		v[2],
	            		v[3],
	            		v[4]
	           		  
	           		 ]).node().id ="row";
	           	tabla.draw( false );
	            	//fila = '<tr> <td>'+v[1]+'</td>  <td >'+ v[2] +'</td> <td >'+ v[3] +'</td> <td >'+ v[4] +'</td>  </tr>' ;
	            	
	            	
	            	
	            })
	            
	       	
	            
	        },
	        error: function(){
	            alert('Ocurrio un error en el servidor de modelo ..');
	            select.prop('disabled', false);
	        }
	    });
	 
	 
	   $("#estatus").val(id);
		 $('#estatus').selectpicker('refresh');
		 $("#comentario").val(null);
		 
		 $.ajax({
		        data: {id:id},
		        url:   '/validar-ticket-estatus',
		        type:  'GET',
		        success:  function (r) 
		        {
		          console.log (r);
		          if ( r == 'Cancelado'){
		        	  $('#estatus').prop('disabled', true);
		        	  $('#estatus').selectpicker('refresh');
		        	  $("#btn-estatus").attr('onclick', 'mensaje ("Cancelado")');
		        	  
		        	  $('#comentario').prop('disabled', true);
		          }
		          else if ( r=='Realizado'){
		        	  $('#estatus').prop('disabled', true);
		        	  $('#estatus').selectpicker('refresh');
		        	  $("#btn-estatus").attr('onclick', 'mensaje ("Realizado")');
		        	  $('#comentario').prop('disabled', true);
		          }
		          else{
		        	  $('#estatus').prop('disabled', false);
		        	  $("#estatus").val(id);
		     		 $('#estatus').selectpicker('refresh');
		     		 $("#comentario").val(null);
		     		$("#btn-estatus").attr('onclick', 'agregarSeguimiento()');
		     		$('#comentario').prop('disabled', false);
		          }
		       	
		            
		        },
		        error: function(){
		           
		        }
		    });
}

function mensaje(estatus) {
	
	Swal.fire({
		  icon: 'error',
		  title: 'Lo sentimos.',
		  text: 'Este ticket cuenta con el estatus: '+estatus,
		  showConfirmButton: false,
	       timer: 2250
		})
	

	
}
function seguimiento(e) {
	var id = e.getAttribute("id");
	
	 listar(id)
	 $("#idTicket2").val(id);
	 
	 
	 
	 
}

function agregarSeguimiento() {	
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
	        	
	        	 document.getElementById("btn-estatus").disabled=true;
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
	    	   document.getElementById("btn-estatus").disabled=false;
	    	   listar(idTicket2)
	    	   
			var tabla = $('#table-ticket').DataTable();
	        	
		        
	        	 
	        	tabla.clear()
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
	        	if ( $("#solicitanteSelected").val () ==1    ){
	        		$("#idEmpleadoSolicitante").prop('disabled', true);
	        		$('#idEmpleadoSolicitante').selectpicker('refresh');
	        	}
	        	
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
	     	   		$("#fechaInicio").val(moment(r.fechaInicio).format("YYYY-MM-DDTHH:mm:ss"));
	     	   		$("#fechaFin").val(moment(r.fechaFin).format("YYYY-MM-DDTHH:mm:ss"));
		     	
	     	   	}
	     	 	else{
	     	   		$("#fechaCalendario").prop("checked", false);
	     	   		document.getElementById('fechaInicio').readOnly = true;
	     	   		document.getElementById('fechaFin').readOnly = true;
	     	   		$("#fechaInicio").val(null);
	     	   		$("#fechaFin").val(null);
	     	   	}
	     	   	
	     	
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
	   	
	   	$("#idLookup").val();
 	   	$('#idLookup').selectpicker('refresh');
 	   	
 	   $("#descripcion").val(null);
	   	
	   	
	   	$("#fechaCalendario").prop("checked", false);
	   document.getElementById('fechaInicio').readOnly = false;
     	document.getElementById('fechaFin').readOnly = false;
    	
	   	
	 
	$("#fechaInicio").val("2020-08-20T17:30:00");
	
	$("#fechaFin").val("2020-08-20T17:32:00");
	$("#idTicket").val(null);
	
	if (solicitud != null){
		$("#idEmpleadoSolicitante").prop('disabled', true);
		$('#idEmpleadoSolicitante').selectpicker('refresh');
	}
	if (auxiliar != null){
		$("#idEmpleadoAuxiliar").prop('disabled', true);
		$('#idEmpleadoAuxiliar').selectpicker('refresh');
	}
	
}
function comprobar(obj)
{   
    if (obj.checked){
    	document.getElementById('fechaInicio').readOnly = false;
       	document.getElementById('fechaFin').readOnly = false;
   } else{
	   
   	document.getElementById('fechaInicio').readOnly = false;
	document.getElementById('fechaFin').readOnly = false;
	$("#fechaInicio").val(null);
	$("#fechaFin").val(null);

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
   	
   	if(document.getElementById("idEmpleadoAuxiliar")){
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
   	
 
   		var start = moment(fechaInicio.value);
 		var end = moment(fechaFin.value);
 		
 		var hoy = moment(new Date()).format("YYYY-MM-DD"); 
 		
 
   		
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
   		if(!start.isValid() ) {
    		Swal.fire({
				position: 'center',
				icon: 'warning',
				title: 'La fecha de inicio no es válida.',
				showConfirmButton: false,
				timer: 1250
			})
    		
    		return false; 
    	}//dddfff
   		if(!end.isValid()) {
    		Swal.fire({
				position: 'center',
				icon: 'warning',
				title: 'La fecha de finalización no es válida.',
				showConfirmButton: false,
				timer: 1250
			})
    		return false; 
    	}
   		if(start.isAfter(end)) {
	 		Swal.fire({
				position: 'center',
				icon: 'warning',
				title: 'La fecha de finalización debe ser posterior a la fecha de inicio.',
				showConfirmButton: false,
				timer: 1250
			})
	 		return false; 
	 	}
   		if ( hoy >= start){
    		Swal.fire({
				position: 'center',
				icon: 'warning',
				title: 'La fecha de inicio debe ser mayor o igual a la fecha actual.',
				showConfirmButton: false,
				timer: 1250
			})
			return false;
    	}
    	
    	if ( hoy >= end){
    		Swal.fire({
				position: 'center',
				icon: 'warning',
				title: 'La fecha de finalizacion debe ser mayor o igual a la fecha actual.',
				showConfirmButton: false,
				timer: 1250
			})
			return false;
    	}
   		
   	
	
   
	
   	document.fvalida.submit();
}

function detalles(e) {
	var id = e.getAttribute("id");
	
	$.ajax({
        data: {id:id},
        url:   '/detalles-ticket',
        type:  'GET',
        success:  function (r) 
        {	
        	$(r).each(function(i, v){ // indice, valor
                
            	$('#text-solicita').html(v[0]); 
            	$('#text-auxiliar').html(v[1]); 
            	$('#text-categoria').html(v[2]); 
            	$('#text-descripcion').html(v[3]); 
            	$('#text-inicio').html(v[4]); 
            	$('#text-fin').html(v[5]); 
            	$('#text-estatus').html(v[6]); 
        	})
        	
        	
        },
        error: function(){
        }
    });
	
}

$('#nuevoSeguimiento').on('hidden.bs.modal', function () {
	
	 Swal.fire({
         title: 'Actualizando',
         icon: 'success',
         allowOutsideClick: false,
         timerProgressBar: true,
         onBeforeOpen: () => {
             Swal.showLoading()
             location.reload();
         },
     });
     
    
});

$(document).ready(function(){
	  var hoy = new Date();
	  var months = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];

	  for (var i = 0; i < 7; i++) {
		  if ( i >= hoy.getDay() ){
			  document.getElementById("d"+i).disabled=false;
			 
			  if (hoy.getDate() <10  ){
				  $('#d'+i).val(hoy.getFullYear()+'-'+months[hoy.getMonth()]+'-0'+hoy.getDate()+'T');
			  }
			  else{
				  $('#d'+i).val(hoy.getFullYear()+'-'+months[hoy.getMonth()]+'-'+hoy.getDate()+'T');
				 
			  }
			  
			  hoy.setDate(hoy.getDate() + 1);
		  }
		   
		}
	  
	  for (var i = 0; i < 7; i++) {
		 
			  document.getElementById("dd"+i).disabled=false;
			 
			  if (hoy.getDate() <10  ){
				  $('#dd'+i).val(hoy.getFullYear()+'-'+months[hoy.getMonth()]+'-0'+hoy.getDate()+'T');
				 // console.log (hoy.getFullYear()+'-'+months[hoy.getMonth()]+'-0'+hoy.getDate()+'T');
			  }
			  else{
				  $('#dd'+i).val(hoy.getFullYear()+'-'+months[hoy.getMonth()]+'-'+hoy.getDate()+'T');
				  //console.log (hoy.getFullYear()+'-'+months[hoy.getMonth()]+'-'+hoy.getDate()+'T');
				 
			  }
			  
			  hoy.setDate(hoy.getDate() + 1);
		  
		   
		}
	  
  });



function limpiarForm2(solicitud, auxiliar){
	
	console.log("fffffffffff");
	
 	   	
 	   $("#descripcion2").val(null);
	   	
 	  $("#idLookup2").val(null);
	   	$('#idLookup2').selectpicker('refresh');
	 
	
	if (solicitud != null){
		$("#idEmpleadoSolicitante2").prop('disabled', true);
		$('#idEmpleadoSolicitante2').selectpicker('refresh');
	}
	if (auxiliar != null){
		$("#idEmpleadoAuxiliar2").prop('disabled', true);
		$('#idEmpleadoAuxiliar2').selectpicker('refresh');
	}
	
}


$("#submit-modal2").click((e) => {
	
	e.preventDefault();
	var validFechas = false;
	var fechas = [];
	
	var valid = true;
	
	var valid = true;
	$("input:checkbox:checked").each(   
		    function() {
		    	//fechas.push({ fecha:$(this).val() });
		    	validFechas = true;
		    }
		    
		);
	
	valid = valid && descripcion2.value;
	if (valid && document.fvalida2.idLookup2.value.length!=0 && validFechas && document.fvalida2.timestart.value.length!=0 && document.fvalida2.timeend.value.length!=0 )	{
		
		$("input:checkbox:checked").each(   
			    function() {
			    	fechas.push({ fechaInicio:$(this).val()+$("#timestart").val() , fechaFin:$(this).val()+$("#timeend").val()    });
			    
			    }
			    
			);
		console.log (fechas);
		var Categoria=document.getElementById("idLookup2").value;
		var Descripcion=document.getElementById("descripcion2").value;
		$.ajax({
	        type: "POST",
	        url:"/guardar-ticket-masivo",
	        data: { 
	        	//datosMateriales:datosMateriales,
	        	
	        	fechas :JSON.stringify(fechas),
	           
	        	 'Categoria':Categoria,
				'Descripcion':Descripcion,
	
	       
	        	
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
	       },
	       complete: function() {   
	    	   location.reload();
			
		    },
	    })
		
		
		
	}
	
	else{
		if (!descripcion2.value){
			 Swal.fire({
					position: 'center',
					icon: 'error',
					title: 'Ingrese una descripcion',
					showConfirmButton: false,
					timer: 1250
				})
				return false;
		}
		
		
		if (  !validFechas){
			 Swal.fire({
					position: 'center',
					icon: 'error',
					title: 'Seleccione un al menos un día',
					showConfirmButton: false,
					timer: 1250
				})
				return false;
		}
		
		if (document.fvalida2.idLookup2.value.length==0){
			 Swal.fire({
					position: 'center',
					icon: 'error',
					title: 'Seleccione una categoria',
					showConfirmButton: false,
					timer: 1250
				})
				return false;
		}
		if(timestart.value == null || timestart.value == "" ) {
    		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La hora de inicio no es válida.',
				showConfirmButton: false,
				timer: 1250
			})
    		
			return false;
    	}//dddfff
		
		if(timeend.value == null || timeend.value == "" ) {
    		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La hora de finalizacion no es válida.',
				showConfirmButton: false,
				timer: 1250
			})
    		
			return false;
    	}
		
		if(timestart.value > timeend.value) {
	 		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La hora de finalización debe ser posterior a la hora de inicio.',
				showConfirmButton: false,
				timer: 1250
			})
	 		return false; 
	 	}
    	
    	if(timestart.value == timeend.value) {
    		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La hora de finalización debe ser posterior a la hora de inicio.',
				showConfirmButton: false,
				timer: 1250
			})
    		return false;
    	}
		
		
	}

});
