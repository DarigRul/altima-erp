var arrayReg=[]; 
var tablaMultialmacenes;
var myTable2;
 //var separateddata3;
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
	

});

$('#materialesAlmacen').on('shown.bs.modal', function () {
	$(document).off('focusin.modal');
});

////////////////RETRASAR
function wait(ms){
	   var start = new Date().getTime();
	   var end = start;
	   while(end < start + ms) {
	     end = new Date().getTime();
	  }
	}


////PRIMER PASO PRIMERA FUNCION
function tablamulti(materialp,surtirtotalp,disponiblep,apartadop,faltanteporsurtirp){

	if(
			/*SI LA CANTIDAD DE OBJETOS DE ACUERDO AL ID ARTICULO MAYOR 0
			 QUE ES CUANDO SE INGRESA PERO SE GUARDA EN EL FRONT*/
			arrayReg.filter(x => x.articulo === materialp).length>0
	){
		///SI LA CANTIDAD DE OBJETOS DE ACUERDO AL ID ARTICULO MAYOR 0
		/// QUE ES CUANDO SE INGRESA PERO SE GUARDA EN EL FRONT
	////SE BORRAN LOS DATOS DE LA TABLA DATATABLE
	console.log(arrayReg.filter(x => x.articulo === materialp));
	//console.log(separateddata3);
	
	var arr92=[];
	 var separateddata2;
		for ( var j in arrayReg.filter(x => x.articulo === materialp)) {

			var arr82=[arrayReg.filter(x => x.articulo === materialp)[j].destino,arrayReg.filter(x => x.articulo === materialp)[j].origen,arrayReg.filter(x => x.articulo === materialp)[j].almacen,arrayReg.filter(x => x.articulo === materialp)[j].articulo,arrayReg.filter(x => x.articulo === materialp)[j].traspaso,arrayReg.filter(x => x.articulo === materialp)[j].traspasodetalle,arrayReg.filter(x => x.articulo === materialp)[j].existencianoimportante,arrayReg.filter(x => x.articulo === materialp)[j].apartado,arrayReg.filter(x => x.articulo === materialp)[j].disponibleenalmacen];
			arr92.push(arr82);
		}
	 separateddata2= "'" + arr92.join("','") + "'";
	tablaMultialmacenes.clear().draw();

	///RECORRER EL ARRAY QUE TENGA ESE ARTICULO SOLAMENTE PARA EVITAR QUE SE DUPLIQUE
	for ( var i in arrayReg.filter(x => x.articulo === materialp)) {



			tablaMultialmacenes.row.add( [
				arrayReg.filter(x => x.articulo === materialp)[i].almacen,
				arrayReg.filter(x => x.articulo === materialp)[i].disponibleenalmacen,
				arrayReg.filter(x => x.articulo === materialp)[i].apartado,
				'<button id="modalTomar" onclick="posicion('+arrayReg.filter(x => x.articulo === materialp)[i].posicion+');inputapartados('+separateddata2+');" class="btn btn-altima btn-sm btn-circle popoverxd"     data-placement="top" data-content="Tomar"><i class="fas fa-hand-pointer"></i></button>',
				] ).draw( false );


		}
		
	}else{
////SI NO HAY NADA AUN INSERTADO EN EL FRONT PARA ESE ARTICULO ENTRA AQUI ESTO YA FUNCIONA CORRECTAMENTE

	$.ajax({
		method: "GET",
		url: "/explosion-materiales-habilitacion",
		data: {
			'Idpedido' : $('#idpedido').val() , 
			'IdArticulo':materialp
		},
		success: (data) => {
			$('#quitarmultialmacenes').remove();
			$('#materialesAlmacenes').append("<div class='modal-body modal-rounded-footer' id='quitarmultialmacenes'>"+
					"<ul class='list-group list-group-horizontal-md' style='margin-bottom: 20px;'>"+
					"<li class='list-group-item'  style='width: 25%;'><strong>Disponible en almacenes: </strong><a id='disponible'>"+disponiblep+"</a></li>"+
					"<li class='list-group-item' style='width: 25%;'><strong>Requerido inicio: </strong><a id='surtir'>"+surtirtotalp+"</a></li>"+
					"<li class='list-group-item' style='width: 25%;'><strong>Apartado: </strong><a id='apartado'>"+apartadop+"</a></li>"+
					"<li class='list-group-item' style='width: 25%;'><strong>Restante por surtir: </strong><a id='faltantesurtir'>"+faltanteporsurtirp+"</a></li>"+
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

					
					'<button id="modalTomar" onclick="posicion('+i+');inputapartados('+separateddata+');" class="btn btn-altima btn-sm btn-circle popoverxd"     data-placement="top" data-content="Tomar"><i class="fas fa-hand-pointer"></i></button>'+

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
///posicion al apretar boton
var posicionvar=null;
function posicion(posicion){
	
     posicionvar=posicion;
	return posicionvar;
}

function inputapartados(	
){
	//var surtir2=contador1[1];
	//var disponible2=contador1[2];
//	console.log(posicionvar);
	//console.log(arguments);
	var i;
	var array2;
	//console.log(array2);
	var arreglo=[];
	for (i = 0; i < arguments.length; i++) {

		array2 = arguments[i].split(',');
		arreglo.push(array2);
	}
	//console.log(arreglo[posicionvar][8]);
	//console.log(arreglo[0]);
	//console.log(arreglo[0][2]);
	
//	arreglo[0]=destino almacen
//	arreglo[1]=origen almacen
//	arreglo[2]=nombre almacen
//	arreglo[3]=articulo id
//    arreglo[4]=id traspaso
//	arreglo[5]=id traspaso detalle
//    arreglo[6]=existencia inicio no importante
//	arreglo[7]=apartado
//    arreglo[8]=disponible en almacenes
				console.log(arreglo);						
	Swal.fire({
		title: 'Ingrese la cantidad a apartar',
		input: 'number',
		 inputAttributes: {
		       min: 0,
		       max: 1000000
		       
		       
		    },
		showCancelButton: true,
		onOpen: function() {document.getElementsByClassName('swal2-confirm swal2-styled')[0].setAttribute('id', 'btnconfirm');}
	,
	inputValidator: (value) => {
		var disponible4=parseInt(document.getElementById("disponible").innerHTML);
		var requeridoinicio=parseInt(document.getElementById("surtir").innerHTML);
		var faltantesurtir=parseInt(document.getElementById("faltantesurtir").innerHTML);
		if (!value || value<0) {

			return 'Ingrese un valor valido no puede estar vacio ni ser un valor negativo'
		}
		else if (parseInt(value)>document.getElementById("faltantesurtir").innerHTML || parseInt(value)>disponible4 || parseInt(value)>requeridoinicio || parseInt(value)>arreglo[posicionvar][8]) {
			return 'Ingrese un valor valido la cantidad a apartadar no puede ser mayor a la cantidad disponible: '+disponible4+' , a la cantida requerida al inicio: '+requeridoinicio+' , a la cantidad exitente en este almacen: '+arreglo[posicionvar][8]+' , o a la cantidad faltante por surtir '+faltantesurtir+' si es necesario desaparte ingresando 0'
		} 
		else{

			tablaMultialmacenes.clear().draw();
			 
			
			 var articuloarr;
			 for (i = 0; i < arguments.length; i++) {

					articuloarr = arguments[i].split(',');
					articuloarr=articuloarr[3];
					 console.log("articulo"+articuloarr)

					
				}
			if(arrayReg.filter(x => x.articulo === articuloarr).length<1){
			for (i = 0; i < arguments.length; i++) {

				
				var array = arguments[i].split(',');
				var Reg = new Object();
				Reg.destino = array[0];
				Reg.origen = array[1];
				Reg.almacen = array[2];
				Reg.articulo = array[3];
				Reg.traspaso = array[4];
				Reg.traspasodetalle = array[5];
				Reg.existencianoimportante = parseInt(array[6]);
				Reg.apartado = parseInt(array[7]);
				Reg.disponibleenalmacen = parseInt(array[8]);
				Reg.posicion= i;
				arrayReg.push(Reg);
			}
			}else{
				//console.log(arrayReg.filter(x => x.articulo === articuloarr)[contador1[0]]);
				//console.log(arrayReg);
				//console.log(arrayReg.filter(x => x.origen === '7'));
				
			}
		
			/*var arr9=[];
			for ( var j in arrayReg) {

				var arr8=[arrayReg[j].destino,arrayReg[j].origen,arrayReg[j].almacen,arrayReg[j].articulo,arrayReg[j].traspaso,arrayReg[j].traspasodetalle,arrayReg[j].apartado,arrayReg[j].disponibleenalmacen];
				arr9.push(arr8);
			}
		 separateddata3= "'" + arr9.join("','") + "'";*/
		 
		 if(value==0){
			 console.log(arrayReg);
			 
			 ///cambiar header tabla modal
			document.getElementById("faltantesurtir").innerHTML=parseInt( document.getElementById("faltantesurtir").innerHTML)+parseInt(arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].apartado);
			document.getElementById("apartado").innerHTML=parseInt(document.getElementById("apartado").innerHTML)-parseInt(arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].apartado);
			document.getElementById("disponible").innerHTML=parseInt(document.getElementById("disponible").innerHTML)+parseInt(arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].apartado);
			 ///cambiar en tabla principal
			document.getElementById("disponible".concat(articuloarr)).textContent=document.getElementById("disponible").innerHTML;
			document.getElementById("apartado".concat(articuloarr)).textContent=document.getElementById("apartado").innerHTML;
			document.getElementById("faltante".concat(articuloarr)).textContent=document.getElementById("faltantesurtir").innerHTML;

			//para cambiar disponible en tabla modal
			 arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].disponibleenalmacen = parseInt(arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].disponibleenalmacen)+ parseInt(arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].apartado);
			 //para cambiar apartado en tabla modal
	         arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].apartado=0;
	        
	       
	         
		 }else if(value > 0 && value > arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].apartado)
		 {
			 ////valor a sumar
			var valuetosum=value-arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].apartado;
			 ///cambiar header tabla modal
			document.getElementById("faltantesurtir").innerHTML=parseInt( document.getElementById("faltantesurtir").innerHTML)-valuetosum;
			document.getElementById("apartado").innerHTML=parseInt(document.getElementById("apartado").innerHTML)+valuetosum;
			document.getElementById("disponible").innerHTML=parseInt(document.getElementById("disponible").innerHTML)-valuetosum;
			 ///cambiar en tabla principal
			document.getElementById("disponible".concat(articuloarr)).textContent=document.getElementById("disponible").innerHTML;
			document.getElementById("apartado".concat(articuloarr)).textContent=document.getElementById("apartado").innerHTML;
			document.getElementById("faltante".concat(articuloarr)).textContent=document.getElementById("faltantesurtir").innerHTML;

			//para cambiar disponible en tabla modal
			 arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].disponibleenalmacen = parseInt(arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].disponibleenalmacen)-valuetosum;
			 //para cambiar apartado en tabla modal
	         arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].apartado=value;
	         
			

		 }else if(value > 0 && value < arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].apartado){
			 ////valor a sumar
				var valuetosum=arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].apartado-value;
				 ///cambiar header tabla modal
				document.getElementById("faltantesurtir").innerHTML=parseInt( document.getElementById("faltantesurtir").innerHTML)+valuetosum;
				document.getElementById("apartado").innerHTML=parseInt(document.getElementById("apartado").innerHTML)-valuetosum;
				document.getElementById("disponible").innerHTML=parseInt(document.getElementById("disponible").innerHTML)+valuetosum;
				 ///cambiar en tabla principal
				document.getElementById("disponible".concat(articuloarr)).textContent=document.getElementById("disponible").innerHTML;
				document.getElementById("apartado".concat(articuloarr)).textContent=document.getElementById("apartado").innerHTML;
				document.getElementById("faltante".concat(articuloarr)).textContent=document.getElementById("faltantesurtir").innerHTML;

				//para cambiar disponible en tabla modal
				 arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].disponibleenalmacen = parseInt(arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].disponibleenalmacen)+valuetosum;
				 //para cambiar apartado en tabla modal
		         arrayReg.filter(x => x.articulo === articuloarr && x.posicion === posicionvar)[0].apartado=value;
		         
			 
			 
		 }
		 
		 var arr9=[];
		 var separateddata3;
			for ( var j in arrayReg.filter(x => x.articulo === articuloarr)) {

				var arr8=[arrayReg.filter(x => x.articulo === articuloarr)[j].destino,arrayReg.filter(x => x.articulo === articuloarr)[j].origen,arrayReg.filter(x => x.articulo === articuloarr)[j].almacen,arrayReg.filter(x => x.articulo === articuloarr)[j].articulo,arrayReg.filter(x => x.articulo === articuloarr)[j].traspaso,arrayReg.filter(x => x.articulo === articuloarr)[j].traspasodetalle,arrayReg.filter(x => x.articulo === articuloarr)[j].existencianoimportante,arrayReg.filter(x => x.articulo === articuloarr)[j].apartado,arrayReg.filter(x => x.articulo === articuloarr)[j].disponibleenalmacen];
				arr9.push(arr8);
			}
		 separateddata3= "'" + arr9.join("','") + "'";
		
			for ( var i in arrayReg.filter(x => x.articulo === articuloarr)) {

//console.log(contador1[0]);
				tablaMultialmacenes.row.add( [
					arrayReg.filter(x => x.articulo === articuloarr)[i].almacen,
					arrayReg.filter(x => x.articulo === articuloarr)[i].disponibleenalmacen,
					arrayReg.filter(x => x.articulo === articuloarr)[i].apartado,
					'<button id="modalTomar" onclick="posicion('+arrayReg.filter(x => x.articulo === articuloarr)[i].posicion+');inputapartados('+separateddata3+');" class="btn btn-altima btn-sm btn-circle popoverxd"     data-placement="top" data-content="Tomar"><i class="fas fa-hand-pointer"></i></button>',

/*hacer variables globales surtir3,arrayReg,contador1,apartados,disponible3,separateddata3
*/
//'<button id="modalTomar" onclick="contador('+i+','+(surtir3-arrayReg[contador1[0]].apartados)+','+(disponible3-arrayReg[contador1[0]].apartados)+');inputapartados('+separateddata3+');" class="btn btn-altima btn-sm btn-circle popoverxd"     data-placement="top" data-content="Tomar"><i class="fas fa-hand-pointer"></i></button>',


					] ).draw( false );
			}
//			console.log(arrayReg);
			//console.log(document.getElementById("disponible").innerHTML);
			//console.log(arrayReg[0].articulo);
			//document.getElementById("disponible".concat(arrayReg[0].articulo)).textContent = document.getElementById("disponible").innerHTML;
			//document.getElementById("apartado".concat(arrayReg[0].articulo)).textContent = document.getElementById("apartado").innerHTML;
			
			//document.getElementById("faltante".concat(arrayReg[0].articulo)).textContent = document.getElementById("surtir").innerHTML;

//if(document.getElementById("faltante".concat(arrayReg[0].articulo)).textContent==0){
	//document.getElementById("status".concat(arrayReg[0].articulo)).textContent = "Completo";
//}

		}


	}
	})




}


function guardar(){
	console.log(arrayReg);
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
                 }
                	  ,   data:{arrayReg: JSON.stringify(arrayReg),pedido: $("#idpedido").val()} //stringify is important

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
            	
              });
		  
		  }
		})
	
	

}


