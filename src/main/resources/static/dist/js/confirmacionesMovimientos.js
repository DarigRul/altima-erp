
$(document).ready(function() {
	listarVendedores()
    listarEmpresas();
    listarMuestras();

});


function limpiarModal(){
	$('#vendedorMovi').find("option").remove();
	$('#empresaMovi').find("option").remove();
	$('#prendaMovi').find("option").remove();
	$('#encargadoRecibir').val("");
	$('#movimiento').val('');
	$('#borrarDatos').remove();
	$('#crearDatos').append("<div class='form-group col-sm-12' id='borrarDatos'>" +
                      			"<table class='table table-striped table-bordered' id ='tablaMuestra'>" +
				   						"<thead>" +
                              			"<tr>" +
				   								"<th>C&oacute;digo de barras</th>" +
                                  			"<th>Prendas</th>" +
				   								"<th>Modelo Prenda</th>" +
				   								"<th>C&oacute;digo Tela</th>" +
				   								"<th></th>" +
				   							"</tr>" +
                          			"</thead>" +
                      			"</table>" +
	   						"</div>");
	   listarEmpresas();
	   listarMuestras();
	   listarVendedores();
	   $('.selectCustom').selectpicker('refresh');

}

function listarVendedores(){
	$.ajax({
		 method: "GET",
		    url: "/listarVendedores",
		    data: {
		    },
		    success: (data) => {
				for (i in data){
					if(data[i][2]==null || data[i][3]==null){
						$('#vendedorMovi').append("<option value='"+data[i][0]+"'>"+ data[i][1] + "</option>");
					}
					else{
						$('#vendedorMovi').append("<option value='"+data[i][0]+"'>"+ data[i][1] + " " + data[i][2] + " " + data[i][3] +"</option>");
					}
				}
				$('#vendedorMovi').selectpicker('refresh');
		    },
		    error: (e) => {
		    }
		});
}

function listarVendedoresTraspaso(){
	$.ajax({
		 method: "GET",
		    url: "/listarVendedores",
		    data: {
		    },
		    success: (data) => {
				for (i in data){
					if(data[i][2]==null || data[i][3]==null){
						$('#vendedorTraspaso').append("<option value='"+data[i][0]+"'>"+ data[i][1] + "</option>");
					}
					else{
						$('#vendedorTraspaso').append("<option value='"+data[i][0]+"'>"+ data[i][1] + " " + data[i][2] + " " + data[i][3] +"</option>");
					}
				}
				$('#vendedorTraspaso').selectpicker('refresh');
		    },
		    error: (e) => {
		    }
		});
}

function listarEmpresas(){

	$.ajax({
		 method: "GET",
		    url: "/listarEmpresasMovimiento",
		    data: {

		    },
		    success: (data) => {
				for (i in data){
					if(data[i].apellidoPaterno==null || data[i].apellidoMaterno==null){
						$('#empresaMovi').append("<option value='"+data[i].idCliente+"'>"+ data[i].nombre + "</option>");
					}
					else{
						$('#empresaMovi').append("<option value='"+data[i].idCliente+"'>"+ data[i].nombre + " " + data[i].apellidoPaterno + " " + data[i].apellidoMaterno +"</option>");
					}
				}

				$('#empresaMovi').selectpicker('refresh');
		    },
		    error: (e) => {
		        // location.reload();

		    }
		});
}

function listarEmpresasTraspaso(){
	$.ajax({
		 method: "GET",
		    url: "/listarEmpresasMovimiento",
		    data: {
		    },
		    success: (data) => {
				for (i in data){
					if(data[i].apellidoPaterno==null || data[i].apellidoMaterno==null){
						$('#empresaTraspaso').append("<option value='"+data[i].idCliente+"'>"+ data[i].nombre + "</option>");
					}
					else{
						$('#empresaTraspaso').append("<option value='"+data[i].idCliente+"'>"+ data[i].nombre + " " + data[i].apellidoPaterno + " " + data[i].apellidoMaterno +"</option>");
					}
				}
				$('#empresaTraspaso').selectpicker('refresh');
		    },
		    error: (e) => {
		    }
		});
}

function listarMuestras(){
	$.ajax({
		method: "GET",
		url: "/listarMuestras",
		success: (data) => {
			$('#prendaMovi').append('<option value="default">Seleccione uno...</option>')
			for (i in data){
				$('#prendaMovi').append("<option value='"+data[i][3]+"' name='"+data[i][0].substring(0,3).toUpperCase()  + data[i][1].substring(0,3).toUpperCase()  + data[i][2].substring(0,3).toUpperCase()  + ("00" + data[i][3]).slice(-3) +"' >"+ 
				data[i][0].substring(0,3).toUpperCase()  + 
				data[i][1].substring(0,3).toUpperCase()  + 
				data[i][2].substring(0,3).toUpperCase()  + 
				("00" + data[i][3]).slice(-3) +
				"___" +
				data[i][0] + "</option>");
			}

			$('#prendaMovi').selectpicker('refresh');
		},
		error: (e) => {
		}
	});
} 

function listarMuestrasTraspaso(movi){
	$.ajax({
		method: "GET",
		url: "/listarMuestrasTraspaso",
		data: {idMovimiento: movi},
		success: (data) => {
			for (i in data){
				$('#prendaTraspaso').append("<option value='"+data[i][3]+"'>"+ 
														  data[i][0].substring(0,3).toUpperCase()  + 
														  data[i][1].substring(0,3).toUpperCase()  + 
														  data[i][2].substring(0,3).toUpperCase()  + 
														  ("00" + data[i][3]).slice(-3) +
														  "___" +
														  data[i][0] + "</option>");
			}
			$('#prendaTraspaso').selectpicker('refresh');
		},
		error: (e) => {
		}
	});
}

function nuevoTraspaso(){
	var movi = $('#mov').val();
	console.log(listamuestritas);
	$("#vendedorTraspaso").find("option").remove();
	$("#empresaTraspaso").find("option").remove();
	$("#prendaTraspaso").find("option").remove();
	$('.selectCustom').selectpicker('refresh');
	$('#borrarTablaTraspaso').remove();
	$('#crearTablaTraspaso').append("<div class='form-group col-sm-12' id='borrarTablaTraspaso'>" +
									"<table class='table table-striped table-bordered' id ='tablaMuestraTraspaso'>" +
										"<thead>" +
											"<tr style='color:#212529'>" +
												"<th>C&oacute;digo de barras</th>" +
												"<th>Prendas</th>" +
												"<th>Modelo Prenda</th>" +
												"<th>C&oacute;digo Tela</th>" +
												"<th></th>" +
											"</tr>" +
										"</thead>" +
									"</table>" +
									"</div>");
	
	listarVendedoresTraspaso();
	listarEmpresasTraspaso();
	listarMuestrasTraspaso(movi);
	$('.selectCustom').selectpicker('refresh');
}



//Función para agregar una nueva muestra a la tabla en el modal de agregas un nuevo movimiento //
function agregarMiniTabla(tablaMuestra){
	//$('#BotonAgregarMuestra').prop('disabled', true);
	result = $('#prendaMovi').val();
	var validador = 0;
	var table = document.getElementById(tablaMuestra);
	
	if(result=="default" || result=="" || result == null || result==undefined){
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: '¡Debe agregar una muestra existente!',
			showConfirmButton: false,
	        timer: 3500
		  })
	}

//Valida que sólo exista un registro en la tabla, para evitar duplicados  //
	else{
		foreach(table, 'tr:not(:first-child)', function(row) {

			if ($('#idMuestras'+result+'').val()==result){
				validador=1;
				
				Swal.fire({
					icon: 'error',
					title: 'Error',
					text: 'Ya se agregó esa muestra',
					showConfirmButton: false,
			        timer: 3500
				  })
			}
			});



//AJAX para extraer todos los datos de la muestra y colocarlos en la tabla  //
		if(validador==0){
			$.ajax({

				method: "GET",
				url: "/agregarMuestraTablita",
				data: {idMuestra: result},
				success: (data) => {
					var ceros = "00";
					$('#tablaMuestra').append("<tr>" +
		                                    "<td id='codigoBarras"+data[3]+"'>" +data[0].substring(0,3).toUpperCase()  + 
													data[1].substring(0,3).toUpperCase()  + 
													data[2].substring(0,3).toUpperCase()  + 
													(ceros + data[3]).slice(-3)+"</td>" +
		                                    "<td id='nombreMuestra"+data[3]+"'>" + data[0] + "</td>" +
		                                    "<td id='modeloPrenda"+data[3]+"'>" + data[4] + 
		                                    "<input type='hidden' id='idPrenda"+data[3]+"' class='idMuestras' value='"+data[6]+"'>" +
		                                    "</td>" +
		                                    "<td id='codigoTela"+data[3]+"'>" + data[5] + 
		                                    "<input type='hidden' id='idTela"+data[3]+"' class='idMuestras' value='"+data[7]+"'>" + 
		                                    "<input type='hidden' id='idMuestras"+data[3]+"' class='idMuestras' value='"+data[3]+"'>" +
		                                    "</td>" +
		                                    "<td class='tdcenter'>" +
		                                        "<a class='btn btn-danger btn-circle btn-sm text-white popoverxd' id='borrar' data-container='body' data-placement='top' data-content='Remover'><i class='fas fa-minus'></i></a>" +
		                                    "</td>" +
		                                "</tr>");
					listamuestritas.push(data[3]);
				},
				error: (e) => {
				}
				
			});
		}
	}
}
//  Borrar registro de la tabla de agregar una muestra  //
//                                                      //
  $(document).on('click', '#borrar', function (event) { //
      event.preventDefault();                           //
      $(this).closest('tr').remove();                   //
  });                                                   //
//======================================================//





//Es una funcion que hace recorrido a la tabla de agregar una nueva muestra  //
//para verificar si existe o no un registro                                  //
  function foreach(root, selector, callback) {
	   if (typeof selector == 'string') {
	      var all = root.querySelectorAll(selector);
	      for (var each = 0; each < all.length; each++) {
	         callback(all[each]);
	      }
	   } else {
	      for (var each = 0; each < selector.length; each++) {
	         foreach(root, selector[each], callback);
	      }
	   }
	}




//Funcion para guardar un nuevo movimiento con sus muestras  //  
	function guardarNuevoMovimiento(tablaMuestra) {
	   var table = document.getElementById(tablaMuestra);
	   var filas = $("#tablaMuestra").find('tr:not(:first-child)'); 
	   var datosJson = [];
	   var vendedorMovi = $('#vendedorMovi').val();
	   var empresaMovi = $('#empresaMovi').val();
	   var movimiento = $('#movimiento').val();
	   var encargado = $('#encargadoRecibir').val();
	   var validacion=true;
	   var validador = 0;
	   var i = 0;


//hace uso de la funcion de foreach para validar que realmente estén llenados los campos en el modal  //
	   foreach(table, 'tr:not(:first-child)', function(row) {validador=1});
	   if(vendedorMovi=="" || vendedorMovi==null || vendedorMovi==undefined || 
		  empresaMovi=="" || empresaMovi==null || empresaMovi==undefined || validador==0 ||
		  encargado=="" || encargado==null || encargado==undefined){
		   console.log("faltan datos");
		   Swal.fire({
				icon: 'error',
				title: 'Error',
				text: '¡Todos los campos deben de estar llenos!',
				showConfirmButton: false,
		        timer: 3500
			  })
			validacion=false;
	   }

//La función de este ciclo es realizar un JSON con todas las muestras agregadas en la tabla  //
	   if (table) {
		   if(validacion==true){
			   for(i=0; i<filas.length; i++){
				   var celdas = $(filas[i]).find("td");
			       var record = {codigoBarras:  $(celdas[0]).text(), 
								 nombreMuestra: $(celdas[1]).text(), 
								 modeloPrenda:  $(celdas[2]).text(), 
								 idPrenda:      $($(celdas[2]).children("input")[0]).val(),
								 codigoTela:    $(celdas[3]).text(),
								 idTela:      	$($(celdas[3]).children("input")[0]).val(),
								 idmuestra:		$($(celdas[3]).children("input")[1]).val()};
		         datosJson.push(record);
		         console.log($($(celdas[2]).children("input")[0]).val());
		         console.log($($(celdas[3]).children("input")[0]).val());
			   }
		   }
	   }
	   if(validacion==true){

//AJAX para mandar los datos del JSON y los datos del vendedor y la empresa(Cliente)  //
		   $.ajax({

			   method: "POST",
			   url: "/guardarNuevoMovimiento",
			   data:{
				   "_csrf": $('#token').val(),
				   vendedor: vendedorMovi,
				   empresa: empresaMovi,
				   encargado:encargado,
				   idMovimiento:movimiento,
				   "object_muestras": JSON.stringify(datosJson)
			   },
			   beforeSend: function () {
		        	 Swal.fire({
		                 title: 'Cargando ',
		                 html: 'Por favor espere',// add html attribute if you want or remove
		                 allowOutsideClick: false,
		                 timerProgressBar: true,
		                 onBeforeOpen: () => {
		                     Swal.showLoading()
		                 },
		             });
			   },
			   success: (data) => {
				   if(data==1){
					   Swal.fire({
							icon: 'success',
							title: 'Movimiento Agregado',
							text: '¡Se ha modificado el movimiento!',
							showConfirmButton: false,
					        timer: 2000,
					        onClose: () => {
					        	location.href = "/movimientos";
					        }
						  })
				   }
				   else if(data==2){
					   Swal.fire({
							icon: 'success',
							title: 'Movimiento Agregado',
							text: '¡Se ha agregado un nuevo movimiento!',
							showConfirmButton: false,
					        timer: 2000,
					        onClose: () => {
					        	location.href = "/movimientos";
					        }
						  })
				   }
				   else{
					   Swal.fire({
							icon: 'error',
							title: '¡Algo salió mal!',
							text: 'Intente de nuevo más tarde',
							showConfirmButton: false,
					        timer: 2000,
					        onClose: () => {
					        	location.href = "/movimientos";
					        }
						  })
				   }
					  
			   },
			   error: (e) =>{
			   }
		   });
	   }
	   return datosJson;
	}


//Listar el modal para editar
function datosMovimiento(idMovimiento){
	console.log("Si entra a editar");
	$.ajax({

		   method: "POST",
		   url: "/datosMovimiento",
		   data:{
			   "_csrf": $('#token').val(),
			   movimiento: idMovimiento
		   },
		   success: (data) => {
			   $('#movimiento').val(data[0][9]);
			   $('#encargadoRecibir').val(data[0][10]);
			   $('#borrarDatos').remove();
			   $('#crearDatos').append("<div class='form-group col-sm-12' id='borrarDatos'>" +
		                        			"<table class='table table-striped table-bordered' id ='tablaMuestra'>" +
						   						"<thead>" +
		                                			"<tr>" +
						   								"<th>C&oacute;digo de barras</th>" +
		                                    			"<th>Prendas</th>" +
						   								"<th>Modelo Prenda</th>" +
						   								"<th>C&oacute;digo Tela</th>" +
						   								"<th></th>" +
						   							"</tr>" +
		                            			"</thead>" +
		                        			"</table>" +
			   							"</div>");
			   
			  
			   $('#vendedorMovi option[value="'+data[0][1]+'"]').attr("selected", true);
		   	   $('#empresaMovi option[value='+data[0][0]+']').attr("selected", true);
		   	   $('.selectCustom').selectpicker('refresh');
			   
			   for (i in data){
			   
				   $('#tablaMuestra').append("<tr>" +
	                       					 	"<td id='codigoBarras"+data[i][4]+"'>"+data[i][3]+"</td>" +
	                       					 	"<td id='nombreMuestra"+data[i][4]+"'>" + data[i][2] + "</td>" +
	                       					 	"<td id='modeloPrenda"+data[i][4]+"'>" + data[i][5] + 
	                       					 		"<input type='hidden' id='idPrenda"+data[i][4]+"' class='idMuestras' value='"+data[i][7]+"'>" +
	                       					 	"</td>" +
	                       					 	"<td id='codigoTela"+data[i][4]+"'>" + data[i][6] + 
	                       					 		"<input type='hidden' id='idTela"+data[i][4]+"' class='idMuestras' value='"+data[i][8]+"'>" + 
	                       					 		"<input type='hidden' id='idMuestras"+data[i][4]+"' class='idMuestras' value='"+data[i][4]+"'>" +
	                       					 	"</td>" +
	                       					 	"<td class='tdcenter'>" +
	                       					 		"<a class='btn btn-danger btn-circle btn-sm text-white popoverxd' id='borrar' data-container='body' data-placement='top' data-content='Remover'><i class='fas fa-minus'></i></a>" +
	                       					 	"</td>" +
				   							"</tr>");
			   }
		   },
		   error: (e) =>{
		   }
	   });
}
	
	
	

//Función para mostrar todas las muestras de acuerdo a su respectivo movimiento  //
function detalleMuestras(id){
	$('#mov').val(id);
	$('#vendedorTraspasoCodigo').val((Math.floor(Math.random() * (10 - 1)) + 1)+""+
									 (Math.floor(Math.random() * (10 - 1)) + 1)+""+
									 (Math.floor(Math.random() * (10 - 1)) + 1)+""+
									 (Math.floor(Math.random() * (10 - 1)) + 1));
	$('#borrarTabla').remove();
	$('#crearTabla').append("<div class='modal-body' id='borrarTabla'>" +
								"<div class='form-check'>" +
									"<input type='checkbox' class='form-check-input' id='selectAll' onclick='selectAllCheck()'>" +
									"<label class='form-check-label' for='selectAll'>Seleccionar todo</label>" +
								"</div>" +
								"<br>" +
								"<table class='table table-striped table-bordered' id='tablaTraspasoinfo'>" +
									"<thead>" +
										"<tr>" +
											"<th></th>" +
											"<th>C&oacute;digo de barras</th>" +
											"<th>Muestra</th>" +
											"<th>Modelo Prenda</th>" +
											"<th>C&oacute;digo Tela</th>" +
											"<th>Fecha de salida</th>" +
											"<th>Entregado por</th>" +
											"<th>Precio unitario</th>" +
											"<th>Recibido por</th>" +
											"<th>Recargos(d&iacute;as)</th>" +
											"<th>Estatus</th>" +
										"</tr>" +
									"</thead>" +
								"</table>" +
							"</div>");


//AJAX para hacer un correcto formato en la tabla del modal de las muestras  //	
	$.ajax({

		method:"POST",
		url: "/listDetalleMuestras",
		data:{
			"_csrf": $('#token').val(),
			idMovi:id
		},
		success:(data) => {
		/* lista de estatus en la tabla de muestras
		 * 
		 * 1 = "Pendiente de recoger"
		 * 2 = "Cancelado"
		 * 3 = "Devuelto"
		 * 4 = "Entregado a vendedor" con checkBox en la tabla
		 * 5 = "Entregado a vendedor" sin checkBox en la tabla
		 * 6 = "Traspaso" con checkBox en la tabla
		 * 7 = "Traspaso" sin checkBox en la tabla
		 * 8 = "Prestado a empresa" con checkBox en la tabla
		 * 9 = "Prestado a empresa" sin checkBox en la tabla
		 * 10= "Devuelto con recargos"
		 **********/
			var a;
		    var b = [];
			
			for (i in data){
				var check;
				var estatus;
				var validador1 = data[i][7];
				var validador2 = data[i][8];
				var validador3 = data[i][9];
				var validador4 = data[i][10];
				var validador5 = data[i][11];
				if(data[i][7]==null){validador1="";}
				if(data[i][8]==null){validador2="";}
				if(data[i][9]==null){validador3="";}
				if(data[i][10]==null){validador4="";}
				if(data[i][11]==null){validador5="";}
					
				validador1 = validador1.replace("T"," ").substring(0,19);
				//validador3 = validador3.replace("T"," ").substring(0,19);
				
				
				if(data[i][12]== 4 || data[i][12]== 6 || data[i][12]== 8){
					check="<td class='tdcenter' id='checks'>" +
          			"<div class='form-check'>" +
          				"<input class='form-check-input' type='checkbox' name='checkmuestra"+data[i][0]+"' value="+data[i][0]+">" +
          			"</div></td>";
					lista[i]= data[i][0];
				}
				else{
					check="<td id='check'></td>";
				}
				console.log(data[i][7]);
				if(data[i][12]==1) {estatus="Pendiente de recoger";}
				if(data[i][12]==2) {estatus="Cancelado";}
				if(data[i][12]==3) {estatus="Devolución";}
				if(data[i][12]==4) {estatus="Entregado a vendedor";}
				if(data[i][12]==5) {estatus="Entregado a vendedor";}
				if(data[i][12]==6) {estatus="Traspaso";}
				if(data[i][12]==7) {estatus="Traspaso";}
				if(data[i][12]==8) {estatus="Prestado a empresa";}
				if(data[i][12]==9) {estatus="Prestado a empresa";}
				if(data[i][12]==10){estatus="Devolución con recargos";}
				if(data[i][12]==11){estatus="Traspasado";}

//Mapeo de los datos que va a llevar la tabla  //				
				a= ["<tr>"+
			    		check,
						"<td>"+data[i][2]+"</td>",
						"<td>"+data[i][4]+"</td>",
						"<td>"+data[i][5]+"</td>", 
						"<td>"+data[i][6]+"</td>", 
						"<td>"+validador1+"</td>",
						"<td>"+validador2+"</td>",
						"<td>"+validador3+"</td>",//
						"<td>"+validador4+"</td>",
						"<td>"+validador5+"</td>",
						"<td>"+estatus+"</td>"+
					"<tr>"];
				b.push(a);
			}

//Estructura de la tabla //
			$('#tablaTraspasoinfo').DataTable({
				"data":	b,
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
	            "lengthMenu": [
	                [5, 10, 25, 50, 100],
	                [5, 10, 25, 50, 100]
	            ],
	            "language": {
	                "sProcessing": "Procesando...",
	                "sLengthMenu": "Mostrar _MENU_ registros",
	                "sZeroRecords": "No se encontraron resultados",
	                "sEmptyTable": "Ningún dato disponible en esta tabla =(",
	                "sInfo": "Del _START_ al _END_ de un total de _TOTAL_ registros",
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
			
			
			
		},
		error: (e) =>{
			
		}
	})	
	

	
	$('#infoTraspaso').modal(true);
}



//Función para cambiar el estatus de una solicitud de movimiento a cancelado, al igual que las muestras //
function cancelarSolicitud(idMovimiento){
	Swal.fire({
		  title: '¿Desea cancelar la solicitud?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar',
		  cancelButtonText: 'Cancelar'
		}).then((result) => {
			if (result.value) {
				$.ajax({
				
					method:"POST",
					url:"/cancelarMovimiento",
					data: {
					    	"_csrf": $('#token').val(),
					    	idMovi: idMovimiento
					},
					beforeSend: function () {
			        	 Swal.fire({
			                 title: 'Cargando ',
			                 html: 'Por favor espere',// add html attribute if you want or remove
			                 allowOutsideClick: false,
			                 timerProgressBar: true,
			                 onBeforeOpen: () => {
			                     Swal.showLoading()
			                 },
			             });
			        	
			        },
					success: (data)=> {
						Swal.fire({
						      position: 'center',
					          icon: 'success',
					          title: '¡Movimiento cancelado!',
					          showConfirmButton: false,
					          timer: 1550,
						      onClose: () => {
						    	  console.log("si entra hasta aca");
						    	  location.href = "/movimientos";
						      }
						})
					},
					error: (e) => {
					}
					
				})
		  }
		});

}

/*Función para cambiar el estatus de una solicitud de movimiento a 
  entregado a vendedor, al igual que las muestras */
function entregarSolicitud(idMovimiento){
	Swal.fire({
		  title: '¿Desea entregar la solicitud?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar',
		  cancelButtonText: 'Cancelar'
		}).then((result) => {
			if (result.value) {
			$.ajax({
			
				method:"POST",
				url:"/entregadoVendedor",
				data: {
				    	"_csrf": $('#token').val(),
				    	idMovi: idMovimiento
				},
				beforeSend: function () {
		        	 Swal.fire({
		                 title: 'Cargando ',
		                 html: 'Por favor espere',// add html attribute if you want or remove
		                 allowOutsideClick: false,
		                 timerProgressBar: true,
		                 onBeforeOpen: () => {
		                     Swal.showLoading()
		                 },
		             });
		        	
		        },
				success: (data)=> {
					Swal.fire({
					      position: 'center',
				          icon: 'success',
				          title: '¡Solicitud entregada!',
				          showConfirmButton: false,
				          timer: 1550,
					      onClose: () => {
					    	  console.log("si entra hasta aca");
					    	  location.href = "/movimientos";
					      }
					})
				},
				error: (e) => {
				}
				
			})
		  }
		});
}


//Función para cambiar el estatus de una solicitud de movimiento a devuelto, al igual que las muestras //
function devueltoSolicitud(idMovimiento){
	Swal.fire({
		  title: '¿Devolver préstamo completo?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar',
		  cancelButtonText: 'Cancelar'
		}).then((result) => {
			if (result.value) {
				$.ajax({
				
					method:"POST",
					url:"/devolverMovimiento",
					data: {
					    	"_csrf": $('#token').val(),
					    	idMovi: idMovimiento
					},
					beforeSend: function () {
			        	 Swal.fire({
			                 title: 'Cargando ',
			                 html: 'Por favor espere',// add html attribute if you want or remove
			                 allowOutsideClick: false,
			                 timerProgressBar: true,
			                 onBeforeOpen: () => {
			                     Swal.showLoading()
			                 },
			             });
			        	
			        },
					success: (data)=> {
						
						Swal.fire({
						      position: 'center',
					          icon: 'success',
					          title: '¡Préstamo entregado!',
					          showConfirmButton: false,
					          timer: 1550,
						      onClose: () => {
						    	  console.log("si entra hasta aca");
						    	  location.href = "/movimientos";
						      }
						})
					},
					error: (e) => {
					}
					
				})
		  }
		});
}


//Función para cambiar el estatus de una solicitud de muestra individual a devuelto//
function devueltoIndividualSolicitud(tablaTraspasoinfo){
	console.log(" entra a este sweet");
	var movi = $('#mov').val();
	var equis;
	var contador = 0;
	var listaMuestras = [];
	var filtered = lista.filter((valor, indiceActual, arreglo) => arreglo.indexOf(valor) === indiceActual);
	var table = $('#tablaTraspasoinfo').DataTable();
	var contadorsito = 0;
	
	table.rows(function (value) {
		for(i=0;i<filtered.length;i++){
			if($('input:checkbox[name=checkmuestra'+filtered[i]+']:checked')){
				equis = $('input:checkbox[name=checkmuestra'+filtered[i]+']:checked').val();
				
				if(equis!=undefined){
					listaMuestras[contador]=equis;
					contador++;
				}
			}
		}
		if(contadorsito==4){
			contadorsito=-1;
			table.page( 'next' ).draw( 'page' );
		}
		contadorsito++;
		console.log("entra al foreach");
	});
	filtered = listaMuestras.filter((valor, indiceActual, arreglo) => arreglo.indexOf(valor) === indiceActual);
	var dato = filtered.toString();
	Swal.fire({
		  title: '¿Desea devolver las muestras seleccionadas?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar',
		  cancelButtonText: 'Cancelar'
		}).then((result) => {
		  if (result.value) { 
			  if(dato.length==0){
				  Swal.fire({
						icon: 'error',
						title: 'Error',
						text: 'Debe seleccionar al menos un registro',
						showConfirmButton: false,
				        timer: 3500
					  })
				  
			  }
			  else{
				$.ajax({
					method:"POST",
					url:"/devolverIndividual",
					data:{
						"_csrf": $('#token').val(),
						idMuestras:	dato,
						movi:movi
					},
					beforeSend: function () {
			        	 Swal.fire({
			                 title: 'Cargando ',
			                 html: 'Por favor espere',// add html attribute if you want or remove
			                 allowOutsideClick: false,
			                 timerProgressBar: true,
			                 onBeforeOpen: () => {
			                     Swal.showLoading()
			                 },
			             });
			        	
			        },
					success:(data) => {
						Swal.fire({
						      position: 'center',
					          icon: 'success',
					          title: '¡Muestras devueltas correctamente!',
					          showConfirmButton: false,
					          timer: 1550,
						      onClose: () => {
						    	  detalleMuestras(movi);
						      }
						})
					}
				});
			  }
		  }
		});
}


//Función para cambiar el estatus de una solicitud de muestra individual a prestado a empresa//
function prestamoSolicitud(tablaTraspasoinfo){
	var movi = $('#mov').val();
	var equis;
	var contador = 0;
	var listaMuestras = [];
	var filtered = lista.filter((valor, indiceActual, arreglo) => arreglo.indexOf(valor) === indiceActual);
	var table = $('#tablaTraspasoinfo').DataTable();
	var contadorsito = 0;

	table.draw();
	table.rows(function (value) {
		for(i=0;i<filtered.length;i++){
			if($('input:checkbox[name=checkmuestra'+filtered[i]+']:checked')){
				equis = $('input:checkbox[name=checkmuestra'+filtered[i]+']:checked').val();
				
				if(equis!=undefined){
					listaMuestras[contador]=equis;
					contador++;
				}
			}
		}
		if(contadorsito==4){
			contadorsito=-1;
			table.page( 'next' ).draw( 'page' );
		}
		contadorsito++;
		console.log("entra al foreach");
	});
	filtered = listaMuestras.filter((valor, indiceActual, arreglo) => arreglo.indexOf(valor) === indiceActual);
	
		var dato = filtered.toString();
		Swal.fire({
			  title: '¿Seguro que desea Realizar un préstamo?',
			  icon: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Confirmar',
			  cancelButtonText: 'Cancelar'
			}).then((result) => {
			  if (result.value) {
				  if(dato.length==0){
					  Swal.fire({
							icon: 'error',
							title: 'Error',
							text: 'Debe seleccionar al menos un registro',
							showConfirmButton: false,
					        timer: 3500
					  })
				  }
				  else{
				  
					$.ajax({
						method:"POST",
						url:"/prestamoEmpresa",
						data:{
							"_csrf": $('#token').val(),
							idMuestras:	dato,
							movi:movi
						},
						beforeSend: function () {
				        	 Swal.fire({
				                 title: 'Cargando ',
				                 html: 'Por favor espere',// add html attribute if you want or remove
				                 allowOutsideClick: false,
				                 timerProgressBar: true,
				                 onBeforeOpen: () => {
				                     Swal.showLoading()
				                 },
				             });
				        	
				        },
						success:(data) => {
							Swal.fire({
							      position: 'center',
						          icon: 'success',
						          title: '¡Muestras prestadas!',
						          showConfirmButton: false,
						          timer: 1550,
							      onClose: () => {
							    	  detalleMuestras(movi);
							      }
							})
						}
					});
				  }
			  }
			});
	}


function agregarMiniTablaTraspaso (tablaMuestraTraspaso){
	
	result = $('#prendaTraspaso').val();
	vendedor = $('#vendedorTraspaso').val();
	empresa = $('#empresaTraspaso').val();
	var validador = 0;
	var table = document.getElementById(tablaMuestraTraspaso);
	
	
	if(result=="" || result==null || result==undefined){
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: '¡Debe agregar un campo válido!',
			showConfirmButton: false,
	        timer: 3500
		  })
		  validador = 1;
	}

//Valida que sólo exista un registro en la tabla, para evitar duplicados  //
	else{
		foreach(table, 'tr:not(:first-child)', function(row) {

			if ($('#idMuestras'+result+'').val()==result){
				validador=1;
				
				Swal.fire({
					icon: 'error',
					title: 'Error',
					text: 'Ya se agregó esa muestra',
					showConfirmButton: false,
			        timer: 3500
				  })
			}
			});

//AJAX para extraer todos los datos de la muestra y colocarlos en la tabla  //
		if(validador==0){
			$.ajax({

				method: "GET",
				url: "/agregarMuestraTablita",
				data: {idMuestra: result},
				success: (data) => {
					var ceros = "00";
					$('#tablaMuestraTraspaso').append("<tr style='color:#212529'>" +
		                                    "<td id='codigoBarras"+data[3]+"'>" +data[0].substring(0,3).toUpperCase()  + 
													data[1].substring(0,3).toUpperCase()  + 
													data[2].substring(0,3).toUpperCase()  + 
													(ceros + data[3]).slice(-3)+"</td>" +
		                                    "<td id='nombreMuestra"+data[3]+"'>" + data[0] + "</td>" +
		                                    "<td id='modeloPrenda"+data[3]+"'>" + data[4] + 
		                                    "<input type='hidden' id='idPrenda"+data[3]+"' class='idMuestras' value='"+data[6]+"'>" +
		                                    "</td>" +
		                                    "<td id='codigoTela"+data[3]+"'>" + data[5] + 
		                                    "<input type='hidden' id='idTela"+data[3]+"' class='idMuestras' value='"+data[7]+"'>" + 
		                                    "</td>" +
		                                    
		                                    "<td class='tdcenter'>" +
		                                    	"<input type='hidden' id='idMuestras"+data[3]+"' class='idMuestras' value='"+data[3]+"'>" +
		                                        "<a class='btn btn-danger btn-circle btn-sm text-white popoverxd' id='borrar' data-container='body' data-placement='top' data-content='Remover'><i class='fas fa-minus'></i></a>" +
		                                    "</td>" +
		                                "</tr>");
					listamuestritas.push(data[3]);
				},
				error: (e) => {
				}
			});
		}
	}
}




//Función para cambiar el estatus de una solicitud de muestra individual a traspaso//
function traspasoSolicitud(tablaTraspaso){
	var movi = $('#mov').val();
	var table = document.getElementById(tablaTraspaso);
	var datosJson = [];
	var listaMuestras = [];
	var filtered = lista.filter((valor, indiceActual, arreglo) => arreglo.indexOf(valor) === indiceActual);
	var vendedorTraspaso = $('#vendedorTraspaso').val();
	var empresaTraspaso = $('#empresaTraspaso').val();
	var filas = $("#tablaMuestraTraspaso").find('tr:not(:first-child)');
	
	if (table) {
	   for(i=0; i<filas.length; i++){
		   var celdas = $(filas[i]).find("td");
	       var record = {codigoBarras:  $(celdas[0]).text(), 
						 nombreMuestra: $(celdas[1]).text(), 
						 modeloPrenda:  $(celdas[2]).text(), 
						 idPrenda:      $($(celdas[2]).children("input")[0]).val(),
						 codigoTela:    $(celdas[3]).text(),
						 idTela:      	$($(celdas[3]).children("input")[0]).val(),
						 idmuestra:		$($(celdas[4]).children("input")[0]).val()};
         datosJson.push(record);
         listaMuestras[i] = $($(celdas[4]).children("input")[0]).val();
	   }
	}
	   
	
	filtered = listaMuestras.filter((valor, indiceActual, arreglo) => arreglo.indexOf(valor) === indiceActual);
	
	var dato = filtered.toString();
	Swal.fire({
		  title: '¿Seguro que desea Traspasar?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar',
		  cancelButtonText: 'Cancelar'
		}).then((result) => {
		  if (result.value) {
			  if(dato.length==0 || vendedorTraspaso=="" || vendedorTraspaso==null || vendedorTraspaso==undefined || 
				 empresaTraspaso== "" || empresaTraspaso== null || empresaTraspaso ==undefined){
				  Swal.fire({
						icon: 'error',
						title: 'Error',
						text: 'Asegurese de tener al menos un registro en la tabla y llenar todos los campos requeridos',
						showConfirmButton: false,
				        timer: 3500
				  })
			  }
			  else{
				
				$.ajax({
					method:"POST",
					url:"/traspasoSolicitud",
					data:{
						"_csrf": $('#token').val(),
						movimiento: movi,
						idMuestras:	dato,
						nuevoVendedor: vendedorTraspaso,
						empresaTraspaso: empresaTraspaso,
						"object_muestras": JSON.stringify(datosJson)
					},
					beforeSend: function () {
			        	 Swal.fire({
			                 title: 'Cargando ',
			                 html: 'Por favor espere',// add html attribute if you want or remove
			                 allowOutsideClick: false,
			                 timerProgressBar: true,
			                 onBeforeOpen: () => {
			                     Swal.showLoading()
			                 },
			             });
			        	
			        },
					success:(data) => {
						Swal.fire({
						      position: 'center',
					          icon: 'success',
					          title: '¡Traspaso exitoso!',
					          showConfirmButton: false,
					          timer: 1550,
						      onClose: () => {
						    	  $('#traspasoAgente').modal('hide');
						    	  
						    	  detalleMuestras(movi);
						    	  
						      }
						})
					}
				});
			  }
		  }
		});
}

//Función para marcar todas las casillas de check y contemplarlas para algún cambio de estatus//
function selectAllCheck(table){
	var data = $('#tablaTraspasoinfo').DataTable();
	var allPages = data.cells( ).nodes( );
    $('#selectAll').on("change", function () {
    	var checked = this.checked;
        if (checked) {
            $(allPages).find('input[type="checkbox"]').prop('checked', true);
        } else {
            $(allPages).find('input[type="checkbox"]').prop('checked', false);
        }
        $(this).toggleClass('allChecked');
    })
	data.draw(false);
}

onScan.attachTo(document, {
    suffixKeyCodes: [13], // enter-key expected at the end of a scan
    reactToPaste: true, // Compatibility to built-in scanners in paste-mode (as opposed to keyboard-mode)
	onScan: function(sCode, iQty) { // Alternative to document.addEventListener('scan')
		$('#prendaMovi').find('[value=default]').prop('selected', true);
		$('#prendaMovi').selectpicker('refresh');
		$('#scannerInput').val (sCode);
		$('#prendaMovi').find('[name='+sCode+']').prop('selected', true);
		$('#prendaMovi').selectpicker('refresh');
		$('#BotonAgregarMuestra').click();
    }
});

$('#infoTraspaso').on('hidden.bs.modal', function () {
	location.reload();
	})
	
	
$('#traspasoAgente').on('hidden.bs.modal', function () {
	detalleMuestras(movi);
})
	
	