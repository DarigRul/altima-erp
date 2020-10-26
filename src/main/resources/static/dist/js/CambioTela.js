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



$('#cambioFecha').on('hidden.bs.modal', function () {
	
	$("#numeroPedido").val(null);
	$('#numeroPedido').selectpicker('refresh');
	$("#clientePedido").val(null);
	$("#fechaPedido").val(null);
	$("#motivoPedido").val(null);
	
   
});
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
function detalles(id) {
    console.log("id->" + id);
    $.ajax({
        method: "GET",
        url: "/detalles-cambio-tela",
        data: {
            id: id,
            _csrf: $("#token").val(),
        },
        success: (data) => {
            $("#quitarDetalles").remove();
            $("#contenedorTablaContador").append(
                "<div class='modal-body' id='quitarDetalles'>" +
                    "<table class='table table-striped table-bordered' id='idtableDetalles' style='width:100%' >" +
                    "<thead>" +
                    "<tr>" +
                    "<th>Material</th>" +
                    "<th>Color</th>" +
                    "</tr>" +
                    "</thead>" +
                    "</table>" +
                    "</div>"
            );
            var a;
            var b = [];
            for (i in data) {
                console.log("material->" + data[i][0]);
                console.log("cofigo  ->" + data[i][2]);
                console.log("color   ->" + data[i][1]);
                a = ["<tr>" + "<td>" + data[i][0] + "</td>", "<td> <i class=" + "'fa fa-tint'" + "  style=" + "'color:" + data[i][2] + "'" + ";  > </i>  " + data[i][1] + "  </td> ", "<tr>"];

                b.push(a);
            }
            var tabla = $("#idtableDetalles").DataTable({
                data: b,
                ordering: true,
                pageLength: 5,
                lengthMenu: [
                    [5, 10, 25, 50, 10],
                    [5, 10, 25, 50, 10],
                ],
                "language": {
					"sProcessing": "Procesando...",
					"sLengthMenu": "Mostrar _MENU_ registros",
					"sZeroRecords": "No se encontraron resultados",
					"sEmptyTable": "Ningún dato disponible en esta tabla =(",
					"sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
					"sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
					"sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
					"sInfoPostFix": "",
					"sSearch": "Buscar:",
					"sUrl": "",
					"sInfoThousands": ",",
					"sLoadingRecords": "Cargando...",
					"oPaginate": {
						"sFirst": "Primero",
						"sLast": "Último",
						"sNext": "Siguiente",
						"sPrevious": "Anterior"
					},
					"oAria": {
						"sSortAscending": ": Activar para ordenar la columna de manera ascendente",
						"sSortDescending": ": Activar para ordenar la columna de manera descendente"
					},
					"buttons": {
						"copy": "Copiar",
						"colvis": "Visibilidad"
					}
				},
            });
            $("#detalles").modal("show");
        },
        error: (e) => {
            // location.reload();nnn
        },
    });
}