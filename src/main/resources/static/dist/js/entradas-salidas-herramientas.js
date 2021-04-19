$(document).ready(function(){
	var tablaMovimientos;
	$('#generarTickets').on('hidden.bs.modal', function () {
		 location.reload();
		 
		});
    $("#movimiento").change(function(){
        var movimiento = $(this).val();

        $.ajax({
            url: '/listar-conceptos',
            type: 'GET',
            data: {
    			"descripcion": movimiento
    		
    		},
    		success: (data) => { 
console.log(data);
                var len = data.length;

                $("#concepto").empty();
                $("#concepto").append("<option >Seleccione un concepto</option>");
                for( var i = 0; i<len; i++){
                    var id = data[i]['idLookup'];
                    var name = data[i]['nombreLookup'];
                    
                    $("#concepto").append("<option value='"+id+"'>"+name+"</option>");

                }
            }
        });
        //////////////////
        $.ajax({
            url: '/listar-articulos',
            type: 'GET',
            data: {
    			
    		
    		},
    		success: (data) => { 
console.log(data);
                var len = data.length;

                $("#articulo").empty();
                for( var i = 0; i<len; i++){
                    var id = data[i]['idLookup']+'|'+data[i]['descripcionLookup']+'|'+data[i]['nombreLookup'];
                    var name = data[i]['nombreLookup'];
                    
                    $("#articulo").append("<option value='"+id+"'>"+name+"</option>");

                }
            }
        });
        console.log($("#concepto").val());
    ///////////////////////7
    });//////////
    console.log($("#concepto").val());
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
				"<th>Eliminar</th>" +
				"</tr>" +
				"</thead>" +
				"</table>" + "</div>");
			var a;
			var b = [];
			
			
			
			

			var tablaes = "#idtable2";
			tablaMovimientos = $(tablaes).DataTable({
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
			new $.fn.dataTable.FixedHeader(tablaMovimientos);
		
		error: (e) => {

		}
	
}

var Lista=[]; 
var contador=0;
$( "#Guardar" ).click(function() {
	if($('#movimiento').val()==null || $('#concepto').val()=="0" || isNaN($('#concepto').val()) || $('#cantidad').val()==null || $('#cantidad').val()<0 || $('#articulo').val()==null  ){
		Swal.fire({
			  icon: 'error',
			  title: 'Ingrese todos los campos',
			  text: 'Los campos no pueden estar vacios o la cantidad no puede ser menor a 0'
			})	;
	
	}
	else{
		 $('#movimiento').prop('disabled', 'disabled');
		 $('#concepto').prop('disabled', 'disabled');
		contador++;
		console.log(document.getElementById("movimiento").value);
		console.log($('#movimiento').val());
		console.log($('#concepto').val());
		var articulo=$('#articulo').val();
		console.log(articulo);
		var articulonombre=articulo.split('|')[2];
		var articuloid=articulo.split('|')[0];
		var articulomarca=articulo.split('|')[1];
		
		var movimiento={
				"movimiento": $('#movimiento').val() ,
				"concepto": $('#concepto').val(),
				"cantidad": $('#cantidad').val(),
				"articulonombre": articulonombre,
				"articulomarca": articulomarca,
				"articuloid": articuloid,
				"observacion": $('#exampleFormControlTextarea1').val(),
				"contador": contador
				
		};
		Lista.push(movimiento);
		console.log(Lista);
		tablaMovimientos.clear().draw();

		for ( var i in Lista) {



			tablaMovimientos.row.add( [
				Lista[i].cantidad,
				Lista[i].articulonombre,
				Lista[i].articulomarca,
			'<button type="button" onclick="eliminar('+Lista[i].contador+');" class="btn btn-danger"     data-placement="top" data-content="eliminar">Eliminar</button>',
				] ).draw( false );


		}
	}
	
	/*if($('#movimiento').val()==null || $('#concepto').val()=="0" || $('#cantidad').val()==null || $('#cantidad').val()<0 || $('#articulo').val()==null  ){
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
	
	tablaMovimientos.clear().draw();

	for ( var i in Lista) {



		tablaMovimientos.row.add( [
			Lista[i].cantidad,
			Lista[i].articulonombre,
			Lista[i].articulomarca,
		'<button type="button" onclick="eliminar('+Lista[i].contador+');" class="btn btn-danger"     data-placement="top" data-content="eliminar">Eliminar</button>',
			] ).draw( false );


	}
}

$( "#enviarOrden" ).click(function() {
	
	
	if($("#restante").val()!=0 || !$("#restante").val() || $("#restante").val()<0 || $("#restante").val()>0  ){
		
	    Swal.fire('Faltan bultos por registrar no se pueden generar los tickets', '', 'error');

		
		
	}else{
		
		Swal.fire({
			  title: '¿Desea generar los tickets?',
			  text: 'Una vez generados no podrá modificarlos y estarán disponibles para su impresión',

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
		                text: 'Los tickets se estan generando..',
		                allowOutsideClick: false,
		                allowEscapeKey: false,
		                allowEnterKey: false,
		                onOpen: () => {
		                    Swal.showLoading()
		                }
		            })
			    var control=document.getElementById("idcontrol").value;
				var prenda=document.getElementById("idprenda").value;
				$.ajax({
					method: "POST",
					url: "/guardar-tickets-asignacion",
					data: {
						"_csrf": $(
						'#token')
					.val(),
						"idcontrol": control,
					    "idprenda":prenda
					},
					success: (data3) => { 
						if(data3==true){
						    Swal.fire('¡Tickets generados correctamente!', '', 'success')
						    $('#generarTickets').modal('hide');
						    location.reload();
							 window.open("/maquilacontrolpedidostickets/"+control+"/"+prenda+"/?format=pdf");

							
						}else{
							
						    Swal.fire('¡Algo ha salido mal reintente y verifique los datos!', '', 'error')

						}

					}});
			  } else {
			    Swal.fire('Puede generar los tickets en otro momento', '', 'info');
			  }
			})
		
	}
	
	
	 
	

});



