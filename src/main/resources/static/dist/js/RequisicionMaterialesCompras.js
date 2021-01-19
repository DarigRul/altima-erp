function agregar (){
	var t = $('#tablaGeneral').DataTable();
	var repetido = false;
	if ( document.getElementById("materialRequisicion").selectedIndex != null
			&& document.getElementById("cantidadRequisicion").value>0){
		$('#tablaGeneral tr').each(function () {
			
			if ($(this).find('td').eq(0).html() == $("#materialRequisicion option:selected").attr("value") &&
					$(this).find('td').eq(1).html() == $("#materialRequisicion option:selected").attr("tipo")){
				Swal.fire({
					position: 'center',
					icon: 'error',
					title: 'Ya ha seleccionado ese material',
					showConfirmButton: false,
					timer: 1250
				});
				repetido = true;
			}
			

		});
		
	if ( repetido != true){
		t.row.add( [
			'<td  >'+$("#materialRequisicion option:selected").attr("value")+'</td>',
			'<td >'+$("#materialRequisicion option:selected").attr("tipo")+'</td>',
			'<td>'+document.getElementById("cantidadRequisicion").value+'</td>',
			'<td>'+$("#materialRequisicion option:selected").attr("idText")+'</td>',
			'<td>'+$("#materialRequisicion option:selected").attr("nombre")+'</td>',
			'<td>'+$("#materialRequisicion option:selected").attr("unidad")+'</td>',
			'<td>'+$("#materialRequisicion option:selected").attr("tamanio")+'</td>',
			'<td>'+$("#materialRequisicion option:selected").attr("color")+'</td>',
			'	<td><button type="button" onclick="eliminar(this)" class="btn btn-sm icon-btn btn-danger text-white btn_remove"><span class="btn-glyphicon spancircle fas fa-times fa-lg img-circle text-danger"></span>Eliminar</button></td>  '
		

        ] ).draw( false );
		
		document.getElementById("materialRequisicion").value = null;
		$('#materialRequisicion').change();
		document.getElementById("cantidadRequisicion").value = null;
	}
		
	
		}
	else{
		Swal.fire({
			position: 'center',
			icon: 'error',
			title: 'Complete todos los campos',
			showConfirmButton: false,
			timer: 1250
		});
	}
}

function eliminar(t) {
	Swal.fire({
		  title: '&iquest;Est&aacute; seguro que desea eliminar este registro?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  cancelButtonText: 'Cancelar',
		  confirmButtonText: 'Si, Eliminar',
		  reverseButtons: true
		}).then((result) => {
		  if (result.value) {
			  Swal.fire({
	        		 position: 'center',
	     				icon: 'success',
	     				title: 'Eliminado correctamente',
	     				showConfirmButton: false,
						timer: 1250
	                 
	             });
				var tabla = $('#tablaGeneral').DataTable();
				var td = t.parentNode;
				var tr = td.parentNode;
				var table = tr.parentNode;
				tabla.row(tr).remove().draw(false);
				
		  }
		})
}
function eliminar2(id, t) {
	Swal.fire({
		  title: '&iquest;Est&aacute; seguro que desea eliminar este registro?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  cancelButtonText: 'Cancelar',
		  confirmButtonText: 'Si, Eliminar',
		  reverseButtons: true
		}).then((result) => {
		  if (result.value) {
			  
			  $.ajax({
				  data: {'idRequision':id},
			        url:   '/elimiar-requisicion-materiales-compras',
			        type:  'GET',
			    
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
			    	   var tabla = $('#tablaGeneral').DataTable();
						var td = t.parentNode;
						var tr = td.parentNode;
						var table = tr.parentNode;
						tabla.row(tr).remove().draw(false);
					
				    },
			    })
		  }
		})
}


function editar (id){
	
	var url = "/requisicion-de-compras-editar/"+id+"";  
	 $(location).attr('href',url);
}
function enviarEstatus(id) {
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
    	        url:"/enviar-requisicion-compras",
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
    	        url:"/rechazar-requisicion-compras",
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
    	        url:"/aceptar-requisicion-compras",
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
function detalles(id) {
    console.log("id->" + id);
    $.ajax({
        method: "GET",
        url: "/detalles-requisicion-compras",
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
                    "<th>Cantidad</th>" +
                    "<th>Clave</th>" +
                    "<th>Nombre</th>" +
                    "<th>Color</th>" +
                    "</tr>" +
                    "</thead>" +
                    "</table>" +
                    "</div>"
            );
            var a;
            var b = [];
            for (i in data) {
                a = ["<tr>" + 
                	"<td>" + data[i][2] + "</td>",
                	"<td>" + data[i][3] + "</td>",
                	"<td>" + data[i][4] + "</td>",
                	"<td>" + data[i][7] + "</td>",
                
                	"<tr>"];

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

function compreas (id){
	
	var url = "/requisicion-de-compras/"+id+"";  
	 $(location).attr('href',url);
}


function enviarCompras() {

	 var  datos = [];
	$('#tablaGeneral tr').each(function () {
		 if ($(this).find('td').eq(1).html() !=null){
			 datos.push({
				 'id_material':$(this).find('td').eq(0).html(), 
				 'tipo':$(this).find('td').eq(1).html(),
				 'cantidad':$(this).find('td').eq(2).html()	 
			 });
		 }		
	});
	
	if ( $('#idEmpleadoSolicitante').val()== null || $('#idEmpleadoSolicitante').val()==0 || $('#idEmpleadoSolicitante').val()==""){
		Swal.fire({
			position: 'center',
			icon: 'error',
			title: 'Ingrese un solicitante, por favor',
			showConfirmButton: false,
			timer: 1250
		});
	}
	else if ( $.isEmptyObject(datos) ){
		Swal.fire({
			position: 'center',
			icon: 'error',
			title: 'Ingrese datos a la tabla, por favor',
			showConfirmButton: false,
			timer: 1250
		});
	}
	else{
		$.ajax({
	        type: "POST",
	        url:"/guardar-requisicion-compras",
	        data: { 
	        	datos :JSON.stringify(datos),
	        	'idRequisicion': $('#idRequisicion').val(),
	             "_csrf": $('#token').val(),
		         'idEmpleadoSolicitante':$('#idEmpleadoSolicitante').val(),
		         'idSolicitudAlamcen':$('#idSolicitudAlamcen').val()
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
	    	   var url = "/requisicion-de-compras";  
	    		 $(location).attr('href',url);
			
		    },
	    })
	}
	
}

$( "#idEmpleadoSolicitante" ).change(function() {
	$('#id-depa').html($("#idEmpleadoSolicitante option:selected").attr("depa")); 
  });
