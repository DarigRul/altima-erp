$(document).ready(function(){
	var tablaPrestamos;
	  $.ajax({
          url: '/listar-operarios-prestamos',
          type: 'GET',
          data: {
  			
  		
  		},
  		success: (data) => { 
console.log(data);
              var len = data.length;

              $("#operario").empty();
              $("#operario").append("<option >Seleccione un operario</option>");
              for( var i = 0; i<len; i++){
                  var id = data[i][0];
                  var name = data[i][1];
                  
                  $("#operario").append("<option value='"+id+"'>"+name+"</option>");

              }
          }
      });
	$('#generarTickets').on('hidden.bs.modal', function () {
		 location.reload();
		 
		});
    $("#operario").click(function(){
    	//alert("hola");
        //var prestamo = $(this).val();

      
        //////////////////
        $.ajax({
            url: '/listar-articulos',
            type: 'GET',
            data: {
    			
    		
    		},
    		success: (data) => { 
                var len = data.length;

                $("#articulo").empty();
                for( var i = 0; i<len; i++){
                    var id = data[i]['idLookup']+'|'+data[i]['descripcionLookup']+'|'+data[i]['nombreLookup']+'|'+data[i]['atributo1'];
                    var name = data[i]['nombreLookup'];
                    
                    $("#articulo").append("<option value='"+id+"'>"+data[i]['idText']+' '+name+' '+data[i]['descripcionLookup']+"</option>");

                }
            }
        });
    ///////////////////////7
    });//////////
listar();
});


function listar(){
	
	var quitar= "#quitar2";
	var contenedor = "#tabla";
	var modal = "#modalEspecificaciones";
	var tabla = "#idtable2";
	

	
	
	
	
		
		
		
			$(quitar).remove();
			$(contenedor).append("<div class='modal-body' id='quitar2'>" +
				"<table class='table table-striped table-bordered' id='idtable2' style='width:100%'>" +
				"<thead>" +
				"<tr>" +
				"<th>Cantidad</th>" +
				"<th>Articulo</th>" +
				"<th>Marca</th>" +
				"<th>Costo</th>" +
				"<th>Eliminar</th>" +
				"</tr>" +
				"</thead>" +
				"</table>" + "</div>");
			var a;
			var b = [];
			
			
			
			

			var tablaes = "#idtable2";
			tablaPrestamos = $(tablaes).DataTable({
				"data": b,
				"ordering": false,
				"pageLength": 5,
				"drawCallback": function () {
					$('.popoverxd').popover({
						container: 'body',
						trigger: 'hover'
					});
				},
				"responsive": true,
				"lengthMenu": [
					[5, 10, 25, 50, 100],
					[5, 10, 25, 50, 100]
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
				}
			});
			new $.fn.dataTable.FixedHeader(tablaPrestamos);
		
		error: (e) => {

		}
	
}
const folio=Math.floor(Math.random() * 10000);
var Lista=[]; 
var contador=0;
$( "#Guardar" ).click(function() {
	if($('#operario').val()==null || $('#operario').val()=="0" || isNaN($('#operario').val()) || $('#cantidad').val()==null || !($('#cantidad').val()) || $('#cantidad').val()<0 || $('#articulo').val()==null  ){
	
		Swal.fire({
			  icon: 'error',
			  title: 'Ingrese todos los campos',
			  text: 'Los campos no pueden estar vacios o la cantidad no puede ser menor a 0'
			})	;
	
	}
	else{
		 $('#operario').prop('disabled', 'disabled');
		// $('#concepto').prop('disabled', 'disabled');
		contador++;
		//console.log(document.getElementById("prestamo").value);
		//console.log($('#prestamo').val());
		//console.log($('#concepto').val());
		var articulo=$('#articulo').val();
		var operario=$('#operario').val();
		console.log(articulo);
		var articulonombre=articulo.split('|')[2];
		var articuloid=articulo.split('|')[0];
		var articulomarca=articulo.split('|')[1];
		var articulocosto=articulo.split('|')[3];
		var prestamo={
				
				"cantidad": $('#cantidad').val(),
				"articulonombre": articulonombre,
				"articulomarca": articulomarca,
				"articuloid": articuloid,
				"contador": contador,
				"folio": folio,
				"articulocosto": articulocosto,
				"operario": operario
				
		};
		Lista.push(prestamo);
		console.log(Lista);
		tablaPrestamos.clear().draw();

		for ( var i in Lista) {



			tablaPrestamos.row.add( [
				Lista[i].cantidad,
				Lista[i].articulonombre,
				Lista[i].articulomarca,
				Lista[i].articulocosto,
			'<button type="button" onclick="eliminar('+Lista[i].contador+');" class="btn btn-danger"      data-content="eliminar">Eliminar</button>',
				] ).draw( false );


		}
	}
	
	/*if($('#prestamo').val()==null || $('#concepto').val()=="0" || $('#cantidad').val()==null || $('#cantidad').val()<0 || $('#articulo').val()==null  ){
		Swal.fire({
			  icon: 'error',
			  title: 'Ingrese todos los campos',
			  text: 'Los campos no pueden estar vacios o la cantidad no puede ser menor a 0'
			})	
		
	}
	
	else{
		console.log( $(
			'#concepto')
		.val())
		;
	$.ajax({
		method: "POST",
		url: "/guardar-inventario-herramientas-entradas-salidas",
		data: {
			"_csrf": $(
			'#token')
		.val(),
		"concepto": $(
			'#concepto')
		.val() ,
			"articulo": document.getElementById("articulo").value,
			"cantidad": $(
			'#cantidad')
		.val() 
		},
		success: (data) => { 
			
			Swal.fire({
				  icon: 'success',
				  title: 'Ingresado correctamente',
				  text: 'Ingresado correctamente'
				})		
				listar();

		}});
	
	}*/
	
	
	});

function eliminar(e){
	console.log(e);
	Lista = Lista.filter(function( obj ) {
	    return obj.contador !== e;
	});
	
	tablaPrestamos.clear().draw();

	for ( var i in Lista) {



		tablaPrestamos.row.add( [
			Lista[i].cantidad,
			Lista[i].articulonombre,
			Lista[i].articulomarca,
			Lista[i].articulocosto,
		'<button type="button" onclick="eliminar('+Lista[i].contador+');" class="btn btn-danger"      data-content="eliminar">Eliminar</button>',
			] ).draw( false );


	}
}

$( "#enviarOrden" ).click(function() {
	if(Lista === undefined || Lista.length == 0){
		console.log(Lista.length);

	    Swal.fire('Ingrese al menos un prestamo', '', 'info');

		
	}else{
	
		
		Swal.fire({
			  title: '¿Desea ingresar los prestamos?',
			  text: 'Una vez ingresados no podra modificarlos',

			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: `Confirmar`,
			  cancelButtonText: 'Cancelar'
			}).then((result) => {
			  /* Read more about isConfirmed, isDenied below */
			  if (result.value) {
				  Swal.fire({
		                title: 'Por favor espere..!',
		                text: 'Los datos se estan guardando..',
		                allowOutsideClick: false,
		                allowEscapeKey: false,
		                allowEnterKey: false,
		                onOpen: () => {
		                    Swal.showLoading()
		                }
		            })
			   // var control=document.getElementById("idcontrol").value;
				//var prenda=document.getElementById("idprenda").value;
				//////////////////777
				/* $.ajax({
	                  type: "POST",
	                  url: "/guardar-habilitacion",
	                  headers: {
	                      'X-CSRF-Token': $('#token').val(),
	                 }
	                	  ,   data:{Lista: JSON.stringify(Lista)} //stringify is important

	              }).done(function(data) {
	            	  
	            	  
	            	  })*/
				/////////////77
				
				$.ajax({
					method: "POST",
					url: "/guardar-prestamo-herramientas-utileria",
					 headers: {
	                      'X-CSRF-Token': $('#token').val(),
	                 },
					data: {
						Lista: JSON.stringify(Lista)
					},
					success: (data3) => { 
						if(data3==true){
						    Swal.fire('¡Datos guardados correctamente!', '', 'success')
						    $('#generarTickets').modal('hide');
						    location.reload();
							// window.open("/maquilacontrolpedidostickets/"+control+"/"+prenda+"/?format=pdf");

							
						}else{
							
						    Swal.fire('¡Algo ha salido mal reintente y verifique los datos!', '', 'error')

						}

					}});
			  } else {
			    Swal.fire('Aun no se ha guardado ningun prestamo cancele la operacion o cierre el modal para hacer un nuevo prestamo', '', 'info');
			  }
			})
		
	
	
	
	 
	
	}
});



