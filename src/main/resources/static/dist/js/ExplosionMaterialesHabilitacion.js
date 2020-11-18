var arrayReg=[]; 
var tablaMultialmacenes;
var tablerow;
var myTable2;

var surtir3;
var contador1;
var apartados;
 var disponible3;
 var separateddata3;
$(document).ready(function () {
	myTable2 = $('#tablaexplosion').DataTable(
			{
				
				"ordering": false,
				"pageLength": 5,
				 "scrollX": true,
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
				}],
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
						"oAria": {
							"sSortAscending": ": Activar para ordenar la columna de manera ascendente",
							"sSortDescending": ": Activar para ordenar la columna de manera descendente"
						},
						"buttons": {
							"copy": "Copiar",
							"colvis": "Visibilidad"
						}
					}
			}
	);
	
	console.log(document.getElementById("idpedido").value);

});

$('#materialesAlmacen').on('shown.bs.modal', function () {
	$(document).off('focusin.modal');
});





function tablamulti(material,disponible_actual,disponible_inicio,apartados,surtir_actual,faltante,surtir_inicio,tabla){
	
	if(
			arrayReg.filter(x => x.articulo === material).length>0
	
	){
		tablaMultialmacenes.clear().draw();

		for ( var i in arrayReg) {


			tablaMultialmacenes.row.add( [
				arrayReg[i].almacen,
				arrayReg[i].existencia,
				arrayReg[i].apartados,
				'<button id="modalTomar" onclick="contador('+i+','+(surtir3-arrayReg[contador1[0]].apartados)+','+(disponible3-arrayReg[contador1[0]].apartados)+');inputapartados('+separateddata3+');" class="btn btn-altima btn-sm btn-circle popoverxd"     data-placement="top" data-content="Tomar"><i class="fas fa-hand-pointer"></i></button>',




				] ).draw( false );
			console.log(arrayReg[i]);


		}
		
	}else{
	
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
					"<li class='list-group-item'  style='width: 25%;'><strong>Disponible: </strong><a id='disponible'>"+disponible+"</a></li>"+
					"<li class='list-group-item' style='width: 25%;'><strong>Requerido: </strong><a id='surtir'>"+surtir+"</a></li>"+
					"<li class='list-group-item' style='width: 25%;'><strong>Apartado: </strong><a id='apartado'>"+apartado+"</a></li>"+
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
				
				var separateddata= "'" + data.join("','") + "'";
				//console.log(separateddata);
				var tr2='\'tr\'';
				a = [
					'<tr>' +
					'<td>' + data[i][2] + '</td>',

					'<td>' + data[i][8] + '</td>',

					'<td>' + data[i][7] + '</td>',
					'<td>' + 

					
					'<button id="modalTomar" onclick="contador('+i+','+surtir+','+disponible+');inputapartados('+separateddata+');" class="btn btn-altima btn-sm btn-circle popoverxd"     data-placement="top" data-content="Tomar"><i class="fas fa-hand-pointer"></i></button>'+

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
}

function contador(contador,surtir,disponible){
	contador1=[contador,surtir,disponible];
	// console.log(contador1);
	return contador1;
}

function inputapartados(	
){
	// console.log(tablaMultialmacenes.rows().data());

	// console.log("contador"+contador1[0]);
	var surtir2=contador1[1];
	var disponible2=contador1[2];
	var i;
	var array2;
	var arreglo=[];
	for (i = 0; i < arguments.length; i++) {
		array2 = arguments[i].split(',');
		// console.log(array2[i]);
		// console.log(array2[0]);
		// console.log(array2);

		arreglo.push(array2);
		// console.log(arreglo);
	}
	var disponible_existencia_almacen=arreglo[contador1[0]][6];
	Swal.fire({
		title: 'Ingrese la cantidad a apartar',
		input: 'text',

		showCancelButton: true,
		onOpen: function() {document.getElementsByClassName('swal2-confirm swal2-styled')[0].setAttribute('id', 'btnconfirm');}
	,
	inputValidator: (value) => {


		// console.log("disponible"+disponible);
		// console.log("surtir"+surtir);
		var disponible4=parseInt(document.getElementById("disponible").innerHTML);
		var surtir4=parseInt(document.getElementById("surtir").innerHTML);
		if (!value) {

			return 'Ingrese un valor valido no puede estar vacio'
		}
		else if (parseInt(value)>disponible4 || parseInt(value)>surtir4 || parseInt(value)>parseInt(disponible_existencia_almacen)) {
			// console.log(value);
			// console.log(disponible2);
			// console.log(surtir2);
			// console.log(disponible_existencia_almacen);

			return 'Ingrese un valor valido la cantidad a apartadar no puede ser mayor a la cantidad disponible: '+disponible4+' , a la cantida a surtir: '+surtir4+' o a la cantidad exitente es este almacen: '+disponible_existencia_almacen+''
		} 
		else{

			// fila.remove().draw();
			tablaMultialmacenes.clear().draw();
            //console.log("borrar");
			// let arrayReg=[];

			 console.log(arguments);
			 
			//if(arrayReg.filter(x => x.articulo === array3).length<1){
			//}
			 var articuloarr;
			 for (i = 0; i < arguments.length; i++) {

					// console.log( arguments[i]);
					 articuloarr = arguments[i].split(',');
					// console.log(array[3]);
					/*try {
						if(arrayReg.filter(x => x.articulo === array3).length<1){
							
							
						}
						n=[];
						arrayReg=[];
					} catch (e) {
						// TODO: handle exception
					}*/
					articuloarr=articuloarr[3];
console.log(articuloarr);					
					
					
				}
			if(arrayReg.filter(x => x.articulo === articuloarr).length<1){
			for (i = 0; i < arguments.length; i++) {

				// console.log( arguments[i]);
				var array = arguments[i].split(',');
				// console.log(array[3]);
				/*try {
					if(arrayReg.filter(x => x.articulo === array3).length<1){
						
						
					}
					n=[];
					arrayReg=[];
				} catch (e) {
					// TODO: handle exception
				}*/
				var Reg = new Object();
				Reg.destino = array[0];
				Reg.origen = array[1];
				Reg.almacen = array[2];
				Reg.articulo = array[3];
				Reg.traspaso = array[4];
				Reg.traspasodetalle = array[5];
				Reg.existencia = parseInt(array[6]);
				Reg.apartadoanterior = parseInt(array[7]);
				Reg.disponible = parseInt(array[8]);
				Reg.apartados= parseInt(array[7]);	
				// for (var i = 0; i < arguments.length; i++) {
					// console.log(array[3]);
//console.log(Reg(0));
				// }
				arrayReg.push(Reg);
			}console.log(Reg);
			}else{
				console.log(arrayReg.filter(x => x.articulo === articuloarr)[contador1[0]]);
				console.log(arrayReg);
				console.log(arrayReg.filter(x => x.origen === '7'));
				
			}
			//arrayReg[contador1[0]].apartados=arrayReg[contador1[0]].apartadoanterior+parseInt(value);
			console.log(arrayReg);
			arrayReg[contador1[0]].apartados=arrayReg[contador1[0]].apartadoanterior+parseInt(value);
            arrayReg[contador1[0]].apartadoanterior=arrayReg[contador1[0]].apartadoanterior+parseInt(value);
			// console.log(arrayReg[i].apartadoanterior);
			arrayReg[contador1[0]].existencia=arrayReg[contador1[0]].existencia-parseInt(value);
			arrayReg[contador1[0]].disponible=arrayReg[contador1[0]].disponible-parseInt(value);
			// console.log(arrayReg[contador1[0]].existencia);
			// console.log(arrayReg[contador1[0]].existencia-parseInt(value));

			 disponible3=parseInt(document.getElementById("disponible").innerHTML);
//			document.getElementById("disponible").innerHTML=disponible3-arrayReg[contador1[0]].apartados;
			document.getElementById("disponible").innerHTML=disponible3-value;

			surtir3=parseInt(document.getElementById("surtir").innerHTML);
			//document.getElementById("surtir").innerHTML=surtir3-arrayReg[contador1[0]].apartados;
			document.getElementById("surtir").innerHTML=surtir3-value;

			var apartado3=parseInt(document.getElementById("apartado").innerHTML);
			//document.getElementById("apartado").innerHTML=apartado3+arrayReg[contador1[0]].apartados;
			document.getElementById("apartado").innerHTML=apartado3+parseInt(value);

			//console.log(disponible3);
			//console.log(surtir3);

			var arr2=[];
	console.log(arrayReg);// 2
			//console.log(arrayReg.find(x => x.destino === '9'));
			console.log(arrayReg.filter(x => x.origen === '7'));

			var arr9=[];
			for ( var j in arrayReg) {

//console.log(arrayReg);
				var arr8=[arrayReg[j].destino,arrayReg[j].origen,arrayReg[j].almacen,arrayReg[j].articulo,arrayReg[j].traspaso,arrayReg[j].traspasodetalle,arrayReg[j].existencia,arrayReg[j].apartados,arrayReg[j].disponible];
// console.log(arr8);
				arr9.push(arr8);
			}
			// console.log(arr9);
		 separateddata3= "'" + arr9.join("','") + "'";
			// console.log(separateddata3);
			//console.log(arrayReg.find(x => x.articulo === '7'));
			for ( var i in arrayReg) {


				tablaMultialmacenes.row.add( [
					arrayReg[i].almacen,
					arrayReg[i].existencia,
					arrayReg[i].apartados,
					'<button id="modalTomar" onclick="contador('+i+','+(surtir3-arrayReg[contador1[0]].apartados)+','+(disponible3-arrayReg[contador1[0]].apartados)+');inputapartados('+separateddata3+');" class="btn btn-altima btn-sm btn-circle popoverxd"     data-placement="top" data-content="Tomar"><i class="fas fa-hand-pointer"></i></button>',

/*hacer variables globales surtir3,arrayReg,contador1,apartados,disponible3,separateddata3
*/

					] ).draw( false );
			
				// console.log(arrayReg[i].destino);
			//	console.log(arrayReg);
				//console.log(document.getElementById("hola").innerHTML);
			//document.getElementById("hola").innerHTML="dsad";
				//console.log(arrayReg.find(x => x.articulo === '7'));


			}
			console.log(arrayReg);
			//console.log(document.getElementById("surtir".concat(arrayReg[0].articulo)));
			//console.log(document.getElementById("surtir").innerHTML);
			//document.getElementById("surtir".concat(arrayReg[0].articulo)).value=document.getElementById("surtir").innerHTML;
//console.log(document.getElementById("surtir".concat(arrayReg[0].articulo)).value);
console.log($("#surtir".concat(arrayReg[0].articulo)));

//.val(document.getElementById("surtir").innerHTML);
//$("#surtir".concat(arrayReg[0].articulo)).val();
//console.log($("#surtir".concat(arrayReg[0].articulo)).val());
document.getElementById("surtir".concat(arrayReg[0].articulo)).textContent = document.getElementById("surtir").innerHTML;
document.getElementById("disponible".concat(arrayReg[0].articulo)).textContent = document.getElementById("disponible").innerHTML;
document.getElementById("apartado".concat(arrayReg[0].articulo)).textContent = document.getElementById("apartado").innerHTML;
document.getElementById("faltante".concat(arrayReg[0].articulo)).textContent = document.getElementById("surtir").innerHTML;

console.log(document.getElementById("faltante".concat(arrayReg[0].articulo)).textContent);
if(document.getElementById("faltante".concat(arrayReg[0].articulo)).textContent==0){
	console.log(document.getElementById("status".concat(arrayReg[0].articulo)));
	//document.getElementById("status".concat(arrayReg[0].articulo)).textContent="Completo";
	document.getElementById("status".concat(arrayReg[0].articulo)).textContent = "Completo";

}

//$("#surtir7").val(2);
//alert("si llego");			// console.log(document.getElementById("disponible").innerHTML);
			// console.log(arrayReg[contador1[0]].apartados);

		}


	}
	})




}


function guardar(){
	
	Swal.fire({
		  title: '¿Está seguro que desea guardar los cambios?',
		  text: "Ésta operación es permanente!",
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: '!Si, deseo guardar los cambios!'
		}).then((result) => {
		  if (result.value) {
			  
			  $.ajax({
                  type: "POST",
                  url: "/guardar-habilitacion",
                  headers: {
                      'X-CSRF-Token': $('#token').val(),
                 }//,
                  //contentType: 'application/json'
                	  ,   data:{arrayReg: JSON.stringify(arrayReg)} //stringify is important

              }).done(function(data) {
            	  if(data==true){
            	  Swal.fire({
					  position: 'center',
					  icon: 'success',
					  title: 'Ingresado correctamente',
					  showConfirmButton: false,
					  timer: 1500
					})  
					location.reload();

            	  }
            	  
            	  else{
            		  Swal.fire({
    					  position: 'center',
    					  icon: 'error',
    					  title: 'Registro algo ha salido mal reintente',
    					  showConfirmButton: false,
    					  timer: 1500
    					})  
            	  }
            	  //listarColores();
            	 // listarAlmaceneslogicos();
              });
		   /* Swal.fire(
		      'Deleted!',
		      'Your file has been deleted.',
		      'success'
		    )*/
		  }
		})
	
	
}
