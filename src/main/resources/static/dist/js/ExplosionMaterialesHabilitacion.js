var arrayReg=[]; 
var tablaMultialmacenes;
$(document).ready(function () {

	console.log(document.getElementById("idpedido").value);
	
});

$('#materialesAlmacen').on('shown.bs.modal', function () {
	$(document).off('focusin.modal');
});





function tablamulti(material,disponible_actual,disponible_inicio,apartados,surtir_actual,faltante,surtir_inicio){
	 //return false;
	 //return true;
	//false;
	
	
	var disponible;
	var surtir;
	var apartado=apartados;
	if(disponible_actual==null || disponible_actual=="null"){
		
		disponible=disponible_inicio;
		
	}
	else{
		
		
		disponible=disponible_actual;
	}
	if(surtir_actual==null || surtir_actual=="null"){
		
		surtir=surtir_inicio;
	}else{
		surtir=surtir_actual;
	}
	
	$.ajax({
		method: "GET",
		url: "/explosion-materiales-habilitacion",
		data: {

			'IdArticulo':material
		},
		success: (data) => {
			$('#quitarmultialmacenes').remove();
			$('#materialesAlmacenes').append("<div class='modal-body modal-rounded-footer' id='quitarmultialmacenes'>"+
                "<ul class='list-group list-group-horizontal-md' style='margin-bottom: 20px;'>"+
                    "<li class='list-group-item' style='width: 25%;'><strong>Disponible: </strong><a>"+disponible+"</a></li>"+
                    "<li class='list-group-item' style='width: 25%;'><strong>Requerido: </strong><a>"+surtir+"</a></li>"+
                    "<li class='list-group-item' style='width: 25%;'><strong>Apartado: </strong><a>"+apartado+"</a></li>"+
                    "<li class='list-group-item' style='width: 25%;'><strong>Restante: </strong><a>0</a></li>"+
                "</ul>"+
                "<table class='table tablaGeneral table-striped table-bordered' style='width:100%;' id='idtablemultialmacenes'>"+
                    "<thead>"+
                        "<tr>"+
                            "<th>Almac&eacute;n</th>"+
                            "<th>Existencia</th>"+
                            "<th>Traspaso</th>"+
                            "<th>Acciones</th>"+
                        "</tr>"+
                    "</thead>"+
					  "</table>"+
            "</div>");
			var a;
			var b = [];

			for (i in data) {
				// var creacion = data[i].actualizadoPor == null ? "" :
				// data[i].actualizadoPor;
				
				
				var separateddata= "'" + data[i].join("','") + "'";;
				
				a = [
					'<tr>' +
					'<td>' + data[i][3] + '</td>',
					
					'<td>' + data[i][9] + '</td>',
					
					'<td>' + data[i][8] + '</td>',
					'<td>' + 
					'<button id="modalTomar" onclick="inputapartados('+separateddata+','+surtir+')" class="btn btn-altima btn-sm btn-circle popoverxd"     data-placement="top" data-content="Tomar"><i class="fas fa-hand-pointer"></i></button>'+
					'</td>',

					'<tr>'
					];
				b.push(a);
			}

			 tablaMultialmacenes = $('#idtablemultialmacenes').DataTable({
				"data": b,
				"ordering": false,
				"pageLength": 5,
				"responsive": true,
				"stateSave": true,
				"drawCallback": function() {
					$('.popoverxd').popover({
						container: 'body',
						trigger: 'hover'
					});
				},
				"columnDefs": [{
					"type": "html",
					"targets": '_all'
				},
				{
					targets: 1,
					className: 'dt-body-center'
				}
				],
				"lengthMenu": [
					[5, 10, 25, 50, 100],
					[5, 10, 25, 50, 100]
					],
					"language": {
						"sProcessing": "Procesando...",
						"sLengthMenu": "Mostrar _MENU_ registros",
						"sZeroRecords": "No se encontraron resultados",
						"sEmptyTable": "NingÃºn dato disponible en esta tabla =(",
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
							"sLast": "Ãšltimo",
							"sNext": "Siguiente",
							"sPrevious": "Anterior"
						},

						"buttons": {
							"copy": "Copiar",
							"colvis": "Visibilidad"
						}
					}
			});
			new $.fn.dataTable.FixedHeader(tablaMultialmacenes);
		},
		error: (e) => {

		}
	})

}


function inputapartados(multialmacen,destino,origen,almacen,articulo,traspaso,traspasodetalle,
		existencia,apartadoanterior,disponible,surtir		
		){

			Swal.fire({
				  title: 'Ingrese la cantidad a apartar',
				  input: 'text',
				  //inputValue: inputValue,
				  showCancelButton: true,
				  inputValidator: (value) => {
					  console.log("disponible"+disponible);
					  console.log("surtir"+surtir);
				    if (!value) {
				      return 'Ingrese un valor valido no puede estar vacio'
				    }
				    else if (value>disponible || value>surtir) {
				    	return 'Ingrese un valor valido la cantidad a apartadar no puede ser mayor a la cantidad disponible: '+disponible+' o a la cantida a surtir: '+surtir+''
					} 
				    else{
				    	var Reg = new Object();
				    	Reg.destino = destino;
				    	Reg.origen = origen;
				    	Reg.almacen = almacen;
				    	Reg.articulo = articulo;
				    	Reg.traspaso = traspaso;
				    	Reg.traspasodetalle = traspasodetalle;
				    	Reg.existencia = existencia;
				    	Reg.apartadoanterior = apartadoanterior;
				    	Reg.disponible = disponible;
				    	Reg.apartados = value;
				    	//let arrayReg=[];
				    	
				    		arrayReg.push(Reg);

				    	console.log(tablaMultialmacenes);
				    	
				    	$( "#modalTomar" ).click(function() {
				    		  alert( "Handler for .click() called." );
				    		});
				    	
				    	$('#idtablemultialmacenes tbody').on( 'click', '.swal2-confirm swal2-styled', function () {
				    		console.log("fds");
				    		tablaMultialmacenes
				    	        .row( $(this).parents('tr') )
				    	        .remove()
				    	        .draw();
				    	} );
				    	
				    	//console.log(arrayReg);
				    	for ( var i in arrayReg) {
				    		console.log(arrayReg[i]);
							//console.log(arrayReg[i].destino);
						}
				    }
				  }
				})

			//	if (ipAddress) {
				//  Swal.fire(`Your IP address is ${ipAddress}`)
				//}

				
		}