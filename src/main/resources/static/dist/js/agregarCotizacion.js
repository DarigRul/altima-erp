/**


 * 
 */
$(document).ready(function() {
	$("#telaCotizacion").prop("disabled", true);
    $("#modeloCotizacion").prop("disabled", true);
	marcarSelects();
	mapearTablita();
});

var contadorGeneral=0;

function marcarSelects(){
	var Gerente = $('#Gerente').val();
	var Agente = $('#Agente').val();
	var Cliente = $('#Cliente').val();
	if(Gerente!="" || Gerente!=null || Gerente!=undefined){
		$('#gerenteVentas option[value="'+Gerente+'"]').attr("selected", true);}
	if(Agente!="" || Agente!=null || Agente!=undefined){  
		$('#agenteCotizacion option[value="'+Agente+'"]').attr("selected", true);}
	if(Cliente!="" || Cliente!=null || Cliente!=undefined){
		$('#clienteCotizacion option[value="'+Cliente+'"]').attr("selected", true);}
	$('.selectpicker').selectpicker('refresh');
}

//OCULTAR COLUMNAS DE LA TABLA

$('#prendaCotizacion').on('change', function () {
	  var idPrenda = $('#prendaCotizacion').val();
	  console.log(idPrenda);
	  $.ajax({
		  method: "GET",
		    url: "/ExtraerModelos",
		    data: {idFamPrenda : idPrenda},
		    beforeSend: function () {
		    	$('#prendaCotizacion').prop("disabled", true);
          },
		    success: function (r) {
              
		    	$('#prendaCotizacion').prop("disabled", false);

              // Limpiamos el select
		    	$("#modeloCotizacion").find("option").remove();
              $(".selectpicker").selectpicker("refresh");
              $(r).each(function (i, v) {
                  // indice, valor
              	$("#modeloCotizacion").append('<option value="' + v[0] + '">' + v[1] + "</option>");
              });

              $("#modeloCotizacion").prop("disabled", false);
              $("#modeloCotizacion").selectpicker("refresh");
          },
          error: function () {
              alert("Ocurrio un error en el servidor de modelo ..");
              $('#prendaCotizacion').prop("disabled", false);
          },
	  });
	  
	  $.ajax({
		  method: "GET",
		    url: "/ExtraerTelas",
		    data: {idFamPrenda : idPrenda},
		    beforeSend: function () {
		    	$('#modeloCotizacion').prop("disabled", true);
          },
		    success: function (r) {
		    	$('#modeloCotizacion').prop("disabled", false);

              // Limpiamos el select
		    	$("#telaCotizacion").find("option").remove();
              $("#telaCotizacion").selectpicker("refresh");
              $(r).each(function (i, v) {
                  // indice, valor
              	$("#telaCotizacion").append('<option value="' + v[0] + '">' + v[1] + "</option>");
              });

              $("#telaCotizacion").prop("disabled", false);
              $("#telaCotizacion").selectpicker("refresh");
          },
          error: function () {
              alert("Ocurrio un error en el servidor de modelo ..");
              $('#modeloCotizacion').prop("disabled", false);
          },
	  });
});

function mapearTablita(){
	
	var tipoPrecio = $('#tipoPrecioVentas').val();
	for(i in tablaPrendasCotizar){
		var precioInicial = 0;
		
		if(tipoPrecio==1){
			precioInicial = tablaPrendasCotizar[i][11];
		}
		if(tipoPrecio==2){
			precioInicial = tablaPrendasCotizar[i][12];
		}
		if(tipoPrecio==3){
			precioInicial = tablaPrendasCotizar[i][13];
		}
		if(tipoPrecio==4){
			precioInicial = tablaPrendasCotizar[i][14];
		}
		table.row.add([	
			 "<input type='hidden' id='idPrenda"+contadorGeneral+"' class='form-control' value='"+tablaPrendasCotizar[i][1]+"'>"+tablaPrendasCotizar[i][2]+
			 "<input type='hidden' class='form-control' id='idCotizacionPrenda"+contadorGeneral+"' value="+tablaPrendasCotizar[i][0]+">",
			 "<input type='hidden' id='idModelo"+contadorGeneral+"' class='form-control' value='"+tablaPrendasCotizar[i][3]+"'>"+tablaPrendasCotizar[i][4]+
			 "<input type='hidden' class='form-control idCoordinadoPrenda' id='idCoordinadoPrenda"+contadorGeneral+"' value="+tablaPrendasCotizar[i][9]+">" + 
			 "<input type='hidden' class='form-control cantidad' id='cantidad"+contadorGeneral+"' value="+tablaPrendasCotizar[i][10]+">",
			 "<input type='hidden' id='idTela"+contadorGeneral+"' class='form-control' value='"+tablaPrendasCotizar[i][5]+"'>"+tablaPrendasCotizar[i][6] ,
			 tablaPrendasCotizar[i][7] ,
			 tablaPrendasCotizar[i][8] ,
			 tablaPrendasCotizar[i][9] ,
			 tablaPrendasCotizar[i][10] ,
			 precioInicial,
			 "<input type='number' class='form-control bordadoPrecioCotizacion' id='bordadoPrecioCotizacion"+contadorGeneral+"' placeholder='10'  value="+tablaPrendasCotizar[i][15]+">" ,
			 "<input type='number' class='form-control porcentajeCotizacion' id='porcentajeCotizacion"+contadorGeneral+"' placeholder='5' value="+tablaPrendasCotizar[i][16]+">" ,
			 "<input type='number' class='form-control montoCotizacion' id='montoCotizacion"+contadorGeneral+"' placeholder='15' value="+tablaPrendasCotizar[i][17]+">" ,
			 "<input type='number' class='form-control precioFinal' id='precioFinal"+contadorGeneral+"' value="+tablaPrendasCotizar[i][18]+" disabled>",
			 "<input type='number' class='form-control importeFinal' id='importeFinal"+contadorGeneral+"' value="+tablaPrendasCotizar[i][19]+" disabled>",
			 "<a class='btn btn-danger btn-circle btn-sm text-white popoverxd' id='borrar' data-container='body' data-placement='top'><i class='fas fa-minus'></i></a>"    
			 ]).node().id ="row"+contadorGeneral;
		table.draw( false );
		contadorGeneral++;
	}
}

function AgregarRegistroTablita (){
	var cantidad = $('#cantidadCotizacion').val();
	var idCoordinadoPrenda = $('#coordinadoCotizacion').val();
	var idPrenda = $('#prendaCotizacion').val();
	var idModelo = $('#modeloCotizacion').val();
	var idTela = $('#telaCotizacion').val();
	var tipoPrecio = $('#tipoPrecioVentas').val();
	
	
	$.ajax({

		method: "POST",
		url: "/agregarCotizacionPrendaTablita",
		data: { "_csrf": $('#token').val(),
				idModelo: idModelo,
				idTela: idTela
			  },
		success: (data) => {
			var precioInicial = 0;
			
			if(tipoPrecio==1){
				precioInicial = data[2];
			}
			if(tipoPrecio==2){
				precioInicial = data[3];
			}
			if(tipoPrecio==3){
				precioInicial = data[4];
			}
			if(tipoPrecio==4){
				precioInicial = data[5];
			}
			
			console.log(data);
			table.row.add([	
							 "<input type='hidden' id='idPrenda"+contadorGeneral+"' class='form-control' value='"+idPrenda+"'>"+$('#prendaCotizacion').find('option:selected').text()+
							 "<input type='hidden' class='form-control' id='idCotizacionPrenda"+contadorGeneral+"' value="+-1+">",
							 "<input type='hidden' id='idModelo"+contadorGeneral+"' class='form-control' value='"+idModelo+"'>"+$('#modeloCotizacion').find('option:selected').text()+
							 "<input type='hidden' class='form-control idCoordinadoPrenda' id='idCoordinadoPrenda"+contadorGeneral+"' value="+idCoordinadoPrenda+">" + 
							 "<input type='hidden' class='form-control cantidad' id='cantidad"+contadorGeneral+"' value="+cantidad+">",
							 "<input type='hidden' id='idTela"+contadorGeneral+"' class='form-control' value='"+idTela  +"'>"+$('#telaCotizacion').find('option:selected').text() ,
							 data[0] ,
							 data[1] ,
							 idCoordinadoPrenda ,
							 cantidad ,
							 precioInicial,
							 "<input type='number' class='form-control bordadoPrecioCotizacion' id='bordadoPrecioCotizacion"+contadorGeneral+"' placeholder='10'  value=0>" ,
							 "<input type='number' class='form-control porcentajeCotizacion' id='porcentajeCotizacion"+contadorGeneral+"' placeholder='5' value=0>" ,
							 "<input type='number' class='form-control montoCotizacion' id='montoCotizacion"+contadorGeneral+"' placeholder='15' value=0>" ,
							 "<input type='number' class='form-control precioFinal' id='precioFinal"+contadorGeneral+"' value="+precioInicial+" disabled>",
							 "<input type='number' class='form-control importeFinal' id='importeFinal"+contadorGeneral+"' value="+precioInicial*cantidad+" disabled>",
							 "<a class='btn btn-danger btn-circle btn-sm text-white popoverxd' id='borrar' data-container='body' data-placement='top'><i class='fas fa-minus'></i></a>"    
							 ]).node().id ="row"+contadorGeneral;
			table.draw( false );
			contadorGeneral++;
		},
		error: (e) => {
		}
		
	});
}

//=Borrar registro de la tabla de agregar una muestra==//
//                                                     //
 $(document).on('click', '#borrar', function (event) { //
     event.preventDefault();                           //
     let hola = $(this).closest('tr').remove();        //
     table.row(hola).remove().draw();                  //
 });                                                   //
//=====================================================//

//===========Cambiar dinámicamente el precio del bordado en el precio unitario final============//
 $(document).on('change', '.bordadoPrecioCotizacion', function (event) {						//
	 event.preventDefault(); 																	//
	 var bordadoPrecioCotizacion = parseFloat($(this).val());									//
	 let fila = $(this).closest('tr');															//
	 var array = table.row(fila).data();														//
	 var montoCotizacion = parseFloat(fila.find('.montoCotizacion').val());						//
	 var precio = parseFloat(array[7])+bordadoPrecioCotizacion+montoCotizacion;					//
	 fila.find('.precioFinal').val(precio.toFixed(2)); 											//
	 fila.find('.importeFinal').val((precio*parseFloat(fila.find('.cantidad').val())).toFixed(2));//
	 table.draw(false);  																		//
 });																							//
 //=============================================================================================//

 //=================Cambiar dinámicamente el porcentaje adicional en el precio unitario final===================//
 $(document).on('change', '.porcentajeCotizacion', function (event) {											//
	 event.preventDefault(); 																					//
	 var porcentajeCotizacion = parseFloat($(this).val());														//
	 let fila = $(this).closest('tr');																			//
	 var array = table.row(fila).data();																		//
	 var bordadoPrecioCotizacion = parseFloat(fila.find('.bordadoPrecioCotizacion').val());						//
	 var precio = parseFloat(array[7])*(porcentajeCotizacion/100);												//
	 fila.find('.montoCotizacion').val(precio.toFixed(2));														//
	 var montoCotizacion = parseFloat(precio);																	//
	 fila.find('.precioFinal').val((parseFloat(array[7])+montoCotizacion+bordadoPrecioCotizacion).toFixed(2));	//
	 fila.find('.importeFinal').val((parseFloat(fila.find('.precioFinal').val())*parseFloat(fila.find('.cantidad').val())).toFixed(2));//
	 table.draw(false); 																						//
 });	 																										//
 //=============================================================================================================//
 
 
 //==============================Cambiar dinámicamente el monto adicional en el precio unitario final===============================//
 $(document).on('change', '.montoCotizacion', function (event) {																	//
	 event.preventDefault();  																										//
	 var montoCotizacion = parseFloat($(this).val());																				//
	 let fila = $(this).closest('tr'); 																								//
	 var array = table.row(fila).data(); 																							//
	 var bordadoPrecioCotizacion = parseFloat(fila.find('.bordadoPrecioCotizacion').val());											//
	 var precio = parseFloat(montoCotizacion*100/parseFloat(array[7]));																//
	 fila.find('.porcentajeCotizacion').val((precio>=0)?precio.toFixed(2):0); 														//
	 fila.find('.precioFinal').val(((parseFloat(array[7])+montoCotizacion+bordadoPrecioCotizacion)>=0)?								//
			 						(parseFloat(array[7])+montoCotizacion+bordadoPrecioCotizacion).toFixed(2):parseFloat(array[7]));//
	 fila.find('.importeFinal').val((parseFloat(fila.find('.precioFinal').val())*parseFloat(fila.find('.cantidad').val())).toFixed(2));//
	 table.draw(false);  																											//
 });	 																															//
 //=================================================================================================================================// 
	 
 
 //======================Cambiar dinámicamente el porcentaje de anticipo en el subtotal=========================//
 $(document).on('change', '#anticipoCotizacion', function (event) {												//
	 event.preventDefault(); 																					//
	 var porcentajeAnticipo = parseFloat($(this).val());														//
	 var subtotal = parseFloat($('#SubtotalInicial').val());													//
	 var Total = parseFloat($('#Subtotal').text());																//
	 $('#anticipoMontoCotizacion').val((subtotal*(porcentajeAnticipo/100)).toFixed(2));							//
	 $('#Subtotal').text((subtotal-parseFloat($('#anticipoMontoCotizacion').val())-parseFloat($('#descuentoMontoCotizacion').val())).toFixed(2));//
	 $('#IVAMonto').text((parseFloat($('#Subtotal').text())*(parseFloat($('#IVACotizacion').val())/100)).toFixed(2));//
	 $('#Total').text((parseFloat($('#Subtotal').text())+parseFloat($('#IVAMonto').text())).toFixed(2));		//
 });	 																										//
 //=============================================================================================================//

 //========================Cambiar dinámicamente el monto de anticipo en el subtotal============================//
 $(document).on('change', '#anticipoMontoCotizacion', function (event) {										//
	 event.preventDefault(); 																					//
	 var anticipoMontoCotizacion = parseFloat($(this).val());													//
	 var subtotal = parseFloat($('#SubtotalInicial').val());													//
	 var Total = parseFloat($('#Subtotal').text());																//
	 $('#anticipoCotizacion').val(((anticipoMontoCotizacion*100)/subtotal).toFixed(2));							//
	 $('#Subtotal').text((subtotal-parseFloat($('#anticipoMontoCotizacion').val())-parseFloat($('#descuentoMontoCotizacion').val())).toFixed(2));//
	 $('#IVAMonto').text((parseFloat($('#Subtotal').text())*(parseFloat($('#IVACotizacion').val())/100)).toFixed(2));//
	 $('#Total').text((parseFloat($('#Subtotal').text())+parseFloat($('#IVAMonto').text())).toFixed(2));		//
 });	 																										//
 //=============================================================================================================//
 
 //=====================Cambiar dinámicamente el porcentaje de descuento en el subtotal=========================//
 $(document).on('change', '#descuentoCotizacion', function (event) {											//
	 event.preventDefault(); 																					//
	 var descuentoCotizacion = parseFloat($(this).val());														//
	 var subtotal = parseFloat($('#SubtotalInicial').val());													//
	 var Total = parseFloat($('#Subtotal').text());																//
	 $('#descuentoMontoCotizacion').val((subtotal*(descuentoCotizacion/100)).toFixed(2));						//
	 $('#Subtotal').text((subtotal-parseFloat($('#anticipoMontoCotizacion').val())-parseFloat($('#descuentoMontoCotizacion').val())).toFixed(2));//
	 $('#IVAMonto').text((parseFloat($('#Subtotal').text())*(parseFloat($('#IVACotizacion').val())/100)).toFixed(2));//
	 $('#Total').text((parseFloat($('#Subtotal').text())+parseFloat($('#IVAMonto').text())).toFixed(2));		//
 });	 																										//
 //=============================================================================================================//
 
 //========================Cambiar dinámicamente el monto de descuento en el subtotal============================//
 $(document).on('change', '#descuentoMontoCotizacion', function (event) {										//
	 event.preventDefault(); 																					//
	 var descuentoMontoCotizacion = parseFloat($(this).val());													//
	 var subtotal = parseFloat($('#SubtotalInicial').val());													//
	 var Total = parseFloat($('#Subtotal').text());																//
	 $('#descuentoCotizacion').val(((descuentoMontoCotizacion*100)/subtotal).toFixed(2));						//
	 $('#Subtotal').text((subtotal-parseFloat($('#anticipoMontoCotizacion').val())-parseFloat($('#descuentoMontoCotizacion').val())).toFixed(2));//
	 $('#IVAMonto').text((parseFloat($('#Subtotal').text())*(parseFloat($('#IVACotizacion').val())/100)).toFixed(2));//
	 $('#Total').text((parseFloat($('#Subtotal').text())+parseFloat($('#IVAMonto').text())).toFixed(2));		//
 });	 																										//
 //=============================================================================================================//
 
function GuardarCotizacionInfo(){
	var numeroCotizacion = $('#numeroCotizacion').val();
	var tituloCotizacion = $('#tituloCotizacion').val();
	var tipoCotizacion = $('#tipoCotizacion').val();
	var tipoPrecioVentas = $('#tipoPrecioVentas').val();
	var gerenteVentas = $('#gerenteVentas').val();
	var clienteCotizacion = $('#clienteCotizacion').val();
	var agenteCotizacion = $('#agenteCotizacion').val();
	var IVACotizacion = $('#IVACotizacion').val();
	var observacionCotizacion = $('#observacionCotizacion').val();
	var idCotizacion = $('#idCotizacion').val();
	console.log(idCotizacion);
	
	if(idCotizacion==null || idCotizacion=='' || idCotizacion==undefined){//Si es nulo significa que es un nuevo registro
	var listaDatos = [numeroCotizacion,
				  	  tituloCotizacion,
				  	  tipoCotizacion,
				  	  tipoPrecioVentas,
				  	  gerenteVentas,
				  	  agenteCotizacion,
				  	  clienteCotizacion,
				  	  observacionCotizacion,
				  	  IVACotizacion];
	var lista = listaDatos.toString();
	$.ajax({
		method: "POST",
	    url: "/GuardarCotizacionInfo",
	    data: {"_csrf": $('#token').val(),
	    		lista:lista
	    },
	    success: (data) => {
	    	console.log(data);
			if(data!=0){
				$('#idCotizacion').val(data);
	        	$('#custom-tabs-two-profile-tab').attr("href","#prendasDiv");
	        	$('#custom-tabs-two-profile-tab').attr("data-toggle","pill");
	        	$('#custom-tabs-two-profile-tab').click();
				  
			}else{
				Swal.fire({
		            position: 'center',
		            icon: 'error',
		            title: 'Error',
		            html: '¡Algo salió mal al guardar los datos!',
		            showConfirmButton: false,
		            timer: 3500,
		        })
			}
	    },
	    error: (e) => {
	    	Swal.fire({
	            position: 'center',
	            icon: 'error',
	            title: 'Error',
	            html: '¡Algo salió mal al guardar los datos!',
	            showConfirmButton: false,
	            timer: 3500,
	        })
	    }
	});
	}
//	
//	Para Editar una cotización existente
//	
	else{
		var listaDatos = [numeroCotizacion,
						  tituloCotizacion,
						  tipoCotizacion,
						  tipoPrecioVentas,
						  gerenteVentas,
						  agenteCotizacion,
						  clienteCotizacion,
		  	  			  observacionCotizacion,
		  	  			  idCotizacion,
		  	  			  IVACotizacion];
		var lista = listaDatos.toString();
		$.ajax({
		method: "POST",
		url: "/EditarCotizacionInfo",
		data: {"_csrf": $('#token').val(),
				lista:lista
		},
		success: (data) => {
			console.log("ya edita");
			if(data!=0){
            	$('#custom-tabs-two-profile-tab').click();
            	
			}else{
				Swal.fire({
		          position: 'center',
		          icon: 'error',
		          title: 'Error',
		          html: '¡Algo salió mal al guardar los datos!',
		          showConfirmButton: false,
		          timer: 3500,
		      })
			}
		},
		error: (e) => {
			Swal.fire({
		      position: 'center',
		      icon: 'error',
		      title: 'Error',
		      html: '¡Algo salió mal al guardar los datos!',
		      showConfirmButton: false,
		      timer: 3500,
		  })
		}
		});
	}
}



function GuardarCotizacionPrendas(){
	var idCotizacion = $('#idCotizacion').val();
	var idCotizacionPrendas = $('#idCotizacionToPrendas').val();
	var tablita = table.rows().data();
	var contador=0;
	var subtotal = 0;
	var contenedorPrendas=[];
	
	
	if(idCotizacionPrendas==null || idCotizacionPrendas=='' || idCotizacionPrendas==undefined){
		var contadorsito = 0;
		table.draw();
		for (contador=0;contador<contadorGeneral;contador++){
			var record={};
			
			if(tablita.rows('#row'+contador).any()){
				console.log("el contador es"+contador);
				record["idPrenda"] = $('#idPrenda'+contador).val();
				record["idModelo"] = $('#idModelo'+contador).val();
				record["idTela"] = $('#idTela'+contador).val();
				record["coordinado"] = $('#idCoordinadoPrenda'+contador).val();
				record["cantidad"] = $('#cantidad'+contador).val();
				record["precioBordado"] = $('#bordadoPrecioCotizacion'+contador).val();
				record["porcentajeCotizacion"] = $('#porcentajeCotizacion'+contador).val();
				record["montoCotizacion"] = $('#montoCotizacion'+contador).val();
				record["precioFinal"] = $('#precioFinal'+contador).val();
				record["importeFinal"] = parseFloat($('#precioFinal'+contador).val())*parseFloat($('#cantidad'+contador).val());
					
				contenedorPrendas.push(record);
				console.log("si quiera entra?");
				subtotal += parseFloat($('#precioFinal'+contador).val())*parseFloat($('#cantidad'+contador).val());
				
				if(contadorsito==4){
					contadorsito=-1;
					table.page( 'next' ).draw( 'page' );
				}
				contadorsito++;
			}
			else{
				console.log("no existe datos");
			}
		}
		$('#Subtotal').text(subtotal);
		$.ajax({
			method: "POST",
			url: "/GuardarCotizacionPrendas",
			data: {"_csrf": $('#token').val(),
					lista:JSON.stringify(contenedorPrendas),
					idCotizacion: idCotizacion
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
				Swal.fire({
					icon: 'success',
					title: 'Cotización Agregada',
					text: '¡Se ha completado la cotización con éxito!',
					showConfirmButton: false,
			        timer: 2000,
				})
			}
		});
		console.log(contenedorPrendas);
		$('#idCotizacionToPrendas').val(idCotizacion);
		location.href = "agregar-cotizacion/"+idCotizacion;
	}
	else{
		var contadorsito = 0;
		table.draw();
		for (contador=0;contador<contadorGeneral;contador++){
			var record={};
			
			if(tablita.rows('#row'+contador).any()){
				console.log("el contador es"+contador);
				record["idPrenda"] = $('#idPrenda'+contador).val();
				record["idModelo"] = $('#idModelo'+contador).val();
				record["idTela"] = $('#idTela'+contador).val();
				record["coordinado"] = $('#idCoordinadoPrenda'+contador).val();
				record["cantidad"] = $('#cantidad'+contador).val();
				record["precioBordado"] = $('#bordadoPrecioCotizacion'+contador).val();
				record["porcentajeCotizacion"] = $('#porcentajeCotizacion'+contador).val();
				record["montoCotizacion"] = $('#montoCotizacion'+contador).val();
				record["precioFinal"] = $('#precioFinal'+contador).val();
				record["importeFinal"] = parseFloat($('#precioFinal'+contador).val())*parseFloat($('#cantidad'+contador).val());
				record["idCotizacionPrenda"] = $('#idCotizacionPrenda'+contador).val();
					
				contenedorPrendas.push(record);
				//console.log(table.cell( contador, 6 ).data());
				console.log("si quiera entra?");
				subtotal += parseFloat($('#precioFinal'+contador).val())*parseFloat($('#cantidad'+contador).val());
				
				if(contadorsito==4){
					contadorsito=-1;
					table.page( 'next' ).draw( 'page' );
				}
				contadorsito++;
			}
			else{
				console.log("no existe datos");
			}
			
		}
		$('#Subtotal').text(subtotal);
		$.ajax({
			method: "POST",
			url: "/EditarCotizacionPrendas",
			data: {"_csrf": $('#token').val(),
					lista:JSON.stringify(contenedorPrendas),
					idCotizacionPrendas: idCotizacionPrendas
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
						title: 'Cotización Agregada',
						text: '¡Se ha completado la cotización con éxito!',
						showConfirmButton: false,
				        timer: 2000,
					})
					location.reload();
				}
				else{
					Swal.fire({
				          position: 'center',
				          icon: 'error',
				          title: 'Error',
				          html: '¡Algo salió mal al guardar los datos!',
				          showConfirmButton: false,
				          timer: 3500,
				      })
				}
			}
		});
		console.log(contenedorPrendas);
	}
}



function GuardarCotizacionTotal(){
	
	var idCotizacion = ($('#idCotizacion').val()==null || $('#idCotizacion').val()=='' || $('#idCotizacion').val()==undefined)?0.0:$('#idCotizacion').val();
	var anticipoCotizacion = ($('#anticipoCotizacion').val()==null || $('#anticipoCotizacion').val()=='' || $('#anticipoCotizacion').val()==undefined)?0.0: $('#anticipoCotizacion').val();
	var anticipoMontoCotizacion = ($('#anticipoMontoCotizacion').val()==null || $('#anticipoMontoCotizacion').val()=='' || $('#anticipoMontoCotizacion').val()==undefined)?0.0:$('#anticipoMontoCotizacion').val();
	var descuentoCotizacion = ($('#descuentoCotizacion').val()==null || $('#descuentoCotizacion').val()=='' || $('#descuentoCotizacion').val()==undefined)?0.0:$('#descuentoCotizacion').val();
	var descuentoMontoCotizacion = ($('#descuentoMontoCotizacion').val()==null || $('#descuentoMontoCotizacion').val()=='' || $('#descuentoMontoCotizacion').val()==undefined)?0.0:$('#descuentoMontoCotizacion').val();
	var tablaTotal =  $(".tablaTotal").find('tr');
	var celdas = $(tablaTotal[0]).find("td");
	console.log("si entra asi");
	var listaDatos = [anticipoCotizacion,
					  anticipoMontoCotizacion,
					  descuentoCotizacion,
					  descuentoMontoCotizacion,
					  $(celdas[0]).text(),
					  $(celdas[1]).text(),
					  $(celdas[2]).text(),
					  $(celdas[3]).text(),
					  idCotizacion];
	var lista = listaDatos.toString();
	
	$.ajax({
		method: "POST",
		url: "/EditarCotizacionTotal",
		data: {"_csrf": $('#token').val(),
				lista:lista
		},
		success: (data) => {
			console.log("ya edita");
			if(data!=0){
				Swal.fire({
					icon: 'success',
					title: 'Cotización Agregada',
					text: '¡Se ha completado la cotización con éxito!',
					showConfirmButton: false,
			        timer: 2000,
			        onClose: () => {
		            	location.href = "/cotizaciones";
			        }   
				})
			}else{
				Swal.fire({
		          position: 'center',
		          icon: 'error',
		          title: 'Error',
		          html: '¡Algo salió mal al guardar los datos!',
		          showConfirmButton: false,
		          timer: 3500,
		      })
			}
		},
		error: (e) => {
			Swal.fire({
		      position: 'center',
		      icon: 'error',
		      title: 'Error',
		      html: '¡Algo salió mal al guardar los datos!',
		      showConfirmButton: false,
		      timer: 3500,
		  })
		}
	});
}

function ValidarCotizacionInfo(){
	if(($('#numeroCotizacion').val()==null  || $('#numeroCotizacion').val()==''  || $('#numeroCotizacion').val()==undefined)  ||
	   ($('#tituloCotizacion').val()==null  || $('#tituloCotizacion').val()==''  || $('#tituloCotizacion').val()==undefined)  ||
	   ($('#tipoCotizacion').val()==null    || $('#tipoCotizacion').val()==''    || $('#tipoCotizacion').val()==undefined)    ||
	   ($('#gerenteVentas').val()==null     || $('#gerenteVentas').val()==''     || $('#gerenteVentas').val()==undefined)     ||
	   ($('#clienteCotizacion').val()==null || $('#clienteCotizacion').val()=='' || $('#clienteCotizacion').val()==undefined) ||
	   ($('#agenteCotizacion').val()==null  || $('#agenteCotizacion').val()==''  || $('#agenteCotizacion').val()==undefined)  ||
	   ($('#IVACotizacion').val()==null     || $('#IVACotizacion').val()==''     || $('#IVACotizacion').val()==undefined)     ||
	   ($('#tipoPrecioVentas').val()==null  || $('#tipoPrecioVentas').val()==''  || $('#tipoPrecioVentas').val()==undefined)){
		
		Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Error',
            html: 'Debe llenar correctamente todos los campos',
            showConfirmButton: false,
            timer: 3500,
        })
	}
	else{
		GuardarCotizacionInfo();
	}
}



function ValidarRegistroTablita(){
	
	var coordinadoCotizacion = $('#coordinadoCotizacion').val();
	var prendaCotizacion = $('#prendaCotizacion').val();
	var modeloCotizacion = $('#modeloCotizacion').val();
	var telaCotizacion = $('#telaCotizacion').val();
	var cantidadCotizacion = $('#cantidadCotizacion').val();
	var table = $('#tablaCotizacionesAgregar').DataTable().rows().data();
	var validador = 0;
	
	console.log(modeloCotizacion);
	console.log(telaCotizacion);
	if(coordinadoCotizacion=="" || coordinadoCotizacion==null || coordinadoCotizacion==undefined ||
	   prendaCotizacion==""     || prendaCotizacion==null     || prendaCotizacion==undefined     ||
	   modeloCotizacion==""     || modeloCotizacion==null     || modeloCotizacion==undefined     ||
	   telaCotizacion==""       || telaCotizacion==null       || telaCotizacion==undefined       ||	
	   cantidadCotizacion==""   || cantidadCotizacion==null   || cantidadCotizacion==undefined){
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: '¡Debe agregar un campo válido!',
			showConfirmButton: false,
	        timer: 3500
		  })
	}
	
	else{
//		table.each(function (value) {
//			if ($('#idCotizacionTablita'+telaCotizacion+'').val()==telaCotizacion){
//				validador=1;
//				
//				Swal.fire({
//					icon: 'error',
//					title: 'Error',
//					text: 'Ya se agregó esa cotización',
//					showConfirmButton: false,
//			        timer: 3500
//				  })
//			}
//		});
		
		if(validador==0){
			AgregarRegistroTablita();
		}
	
	}
}

function ValidarCotizacionPrendas(){
	var tablita = table.rows().data().toArray();
	var validador = 0;
	if (tablita[0]==null){
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'Debe haber alguna cotización',
			showConfirmButton: false,
	        timer: 3500
		  })
	}
	else{
		GuardarCotizacionPrendas();
	}
}

function ValidarCotizacionTotal(){
	
	GuardarCotizacionTotal();
}
