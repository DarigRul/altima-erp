/**


 * 
 */
$(document).ready(function() {
	$("#telaCotizacion").prop("disabled", true);
    $("#modeloCotizacion").prop("disabled", true);
    if(valorIndice==null){
    	$(".agenteCotizacionInput").remove();
    }
    else{
    	$(".agenteCotizacionSelect").remove();
    }
    
    optionsBordados();
	marcarSelects();
	mapearTablita();
	
});

var contadorGeneral=0;
var listaBordados = "";

function optionsBordados(valueBordado) {
		console.log(bordados);
		listaBordados = "";
		for(i in bordados){
			console.log(bordados[i].atributo1);
			console.log(bordados[i].nombreLookup);
			
			if(valueBordado==bordados[i].atributo1){
				listaBordados+="<option selected value="+bordados[i].atributo1+">"+bordados[i].nombreLookup+"</option>";
			}
			else{
				listaBordados+="<option value="+bordados[i].atributo1+">"+bordados[i].nombreLookup+"</option>";
			}
			
		}
		
}


$('#agenteCotizacion').on("change",function(){
	if(valorIndice==null){
	var idAgente = $('#agenteCotizacion').val();
	 $.ajax({
		  method: "GET",
		    url: "/ListarClientesporAgente",
		    data: {idAgente : idAgente},
		    beforeSend: function () {
		    	$('#clienteCotizacion').prop("disabled", true);
		    },
		    success: (data) => {
		    	console.log(data);
		    	$('#clienteCotizacion').find("option").remove();
		    	for(i in data){
		    		$('#clienteCotizacion').append("<option value="+data[i][0]+">"+data[i][1]+"</option>");
		    	}
		    	if(data==''){
		    		$('#clienteCotizacion').prop("disabled", true);
		    	}
		    	else{
		    		$('#clienteCotizacion').prop("disabled", false);
		    	}
		    	$(".selectpicker").selectpicker("refresh");
		    },
		    error: (e) => {
		    	
		    }
	 })
	}
	else{
		var idAgente = $('#agenteCotizacion').data('agente');
		 $.ajax({
			  method: "GET",
			    url: "/ListarClientesporAgente",
			    data: {idAgente : idAgente},
			    beforeSend: function () {
			    	$('#clienteCotizacion').prop("disabled", true);
			    },
			    success: (data) => {
			    	$('#clienteCotizacion').find("option").remove();
			    	for(i in data){
			    		$('#clienteCotizacion').append("<option value="+data[i][0]+">"+data[i][1]+"</option>");
			    	}
			    	
			    	if(data==''){
			    		$('#clienteCotizacion').prop("disabled", true);
			    	}
			    	else{
			    		$('#clienteCotizacion').prop("disabled", false);
			    	}
			    	$(".selectpicker").selectpicker("refresh");
			    },
			    error: (e) => {
			    	
			    }
		 })
	}
})

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
	  
	  if($('#tipoCotizacion').val()=='1' || $('#tipoCotizacion').val()=='3'){
		  
		  $.ajax({
			  method: "GET",
			    url: "/ExtraerFamiliaComposicion",
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
	  }
	  else{
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
	  }
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
			
			 tablaPrendasCotizar[i][10],
			 tablaPrendasCotizar[i][9],
			 "<input type='hidden' id='idPrenda"+contadorGeneral+"' class='form-control' value='"+tablaPrendasCotizar[i][1]+"'>" +
			 "<input type='hidden' class='form-control' id='idCotizacionPrenda"+contadorGeneral+"' value="+tablaPrendasCotizar[i][0]+">" +
			 "<input type='hidden' id='idModelo"+contadorGeneral+"' class='form-control' value='"+tablaPrendasCotizar[i][3]+"'>" +
			 "<input type='hidden' class='form-control idCoordinadoPrenda' id='idCoordinadoPrenda"+contadorGeneral+"' value="+tablaPrendasCotizar[i][9]+">" +
			 "<input type='hidden' class='form-control cantidad' id='cantidad"+contadorGeneral+"' value="+tablaPrendasCotizar[i][10]+">" +
			 "<input type='hidden' id='idTela"+contadorGeneral+"' class='form-control' value='"+tablaPrendasCotizar[i][5]+"'>" +
			 tablaPrendasCotizar[i][2] +
			 "<input type='hidden' class='form-control importeFinal' id='importeFinal"+contadorGeneral+"' value="+tablaPrendasCotizar[i][19]+" disabled>" +
			 "<input type='hidden' class='form-control precioFinal' id='precioFinal"+contadorGeneral+"' value="+tablaPrendasCotizar[i][18]+" disabled>",
			 (tablaPrendasCotizar[i][4]==null || tablaPrendasCotizar[i][4]=="" || tablaPrendasCotizar[i][4]==undefined)?'':tablaPrendasCotizar[i][4],
			 tablaPrendasCotizar[i][6] ,
			 tablaPrendasCotizar[i][7] ,
			 tablaPrendasCotizar[i][8] ,
			 precioInicial,
			 "<select class='form-control selectpicker bordadoPrecioCotizacion' id='bordadoPrecioCotizacion"+contadorGeneral+"' title='Selecciona uno...' value="+tablaPrendasCotizar[i][15]+">" +
			 listaBordados+"</select>",
			 "<input type='number' class='form-control porcentajeCotizacion' id='porcentajeCotizacion"+contadorGeneral+"' placeholder='5' value="+tablaPrendasCotizar[i][16]+">" ,
			 "<input type='number' class='form-control montoCotizacion' id='montoCotizacion"+contadorGeneral+"' placeholder='15' value="+tablaPrendasCotizar[i][17]+">" ,
			 tablaPrendasCotizar[i][18],
			 tablaPrendasCotizar[i][19],
			 "<a class='btn btn-danger btn-circle btn-sm text-white popoverxd' id='borrar' data-container='body' data-placement='top'><i class='fas fa-minus'></i></a>"    
			 ]).node().id ="row"+contadorGeneral;
		table.draw( false );
		$('#bordadoPrecioCotizacion'+contadorGeneral+' option[value="'+tablaPrendasCotizar[i][15]+'"]').attr("selected", true);
		$('#bordadoPrecioCotizacion'+contadorGeneral).selectpicker("refresh");
		contadorGeneral++;
	}
}

function AgregarRegistroTablita (){
	var tipoCotizacion = $('#tipoCotizacion').val();
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
				idPrenda: idPrenda,
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
			
			if(data==null || data=='' || data==undefined || data.length==0){
				Swal.fire({
		            position: 'center',
		            icon: 'error',
		            title: 'Error',
		            html: '¡Esta prenda aún no tiene precio!',
		            showConfirmButton: false,
		            timer: 3500
		        })
			}
			else{
				console.log(data);
				if(tipoCotizacion=='1'){
					table.row.add([	
						
					 cantidad,
					 idCoordinadoPrenda,
					 "<input type='hidden' id='idPrenda"+contadorGeneral+"' class='form-control' value='"+idPrenda+"'>"+
					 "<input type='hidden' class='form-control' id='idCotizacionPrenda"+contadorGeneral+"' value="+-1+">" +
					 "<input type='hidden' id='idModelo"+contadorGeneral+"' class='form-control' value='"+idModelo+"'>" +
					 "<input type='hidden' class='form-control idCoordinadoPrenda' id='idCoordinadoPrenda"+contadorGeneral+"' value="+idCoordinadoPrenda+">" +
					 "<input type='hidden' class='form-control cantidad' id='cantidad"+contadorGeneral+"' value="+cantidad+">" +
					 "<input type='hidden' id='idTela"+contadorGeneral+"' class='form-control' value='"+idTela  +"'>"+
					 $('#prendaCotizacion').find('option:selected').text() +
					 "<input type='hidden' class='form-control importeFinal' id='importeFinal"+contadorGeneral+"' value="+data[5]*cantidad+" disabled>" +
					 "<input type='hidden' class='form-control precioFinal' id='precioFinal"+contadorGeneral+"' value="+data[5]+" disabled>",
					 $('#modeloCotizacion').find('option:selected').text(),
					 "",
					 data[0],
					 data[1] ,
					 data[5],
					 "<select class='form-control selectpicker bordadoPrecioCotizacion' id='bordadoPrecioCotizacion"+contadorGeneral+"' title='Selecciona uno...'>" +
					 listaBordados+"</select>" ,
					 "<input type='number' class='form-control porcentajeCotizacion' id='porcentajeCotizacion"+contadorGeneral+"' placeholder='5' value=0>" ,
					 "<input type='number' class='form-control montoCotizacion' id='montoCotizacion"+contadorGeneral+"' placeholder='15' value=0>" ,
					 data[5],
					 data[5]*cantidad,
					 "<a class='btn btn-danger btn-circle btn-sm text-white popoverxd' id='borrar' data-container='body' data-placement='top'><i class='fas fa-minus'></i></a>"    
					 ]).node().id ="row"+contadorGeneral;
					table.draw( false );
					$('#bordadoPrecioCotizacion'+contadorGeneral).selectpicker("refresh");
					contadorGeneral++;
				}
				else{
					table.row.add([	
									
					 cantidad,
					 idCoordinadoPrenda,
					 "<input type='hidden' id='idPrenda"+contadorGeneral+"' class='form-control' value='"+idPrenda+"'>"+
					 "<input type='hidden' class='form-control' id='idCotizacionPrenda"+contadorGeneral+"' value="+-1+">" +
					 "<input type='hidden' id='idModelo"+contadorGeneral+"' class='form-control' value='"+idModelo+"'>" +
					 "<input type='hidden' class='form-control idCoordinadoPrenda' id='idCoordinadoPrenda"+contadorGeneral+"' value="+idCoordinadoPrenda+">" +
					 "<input type='hidden' class='form-control cantidad' id='cantidad"+contadorGeneral+"' value="+cantidad+">" +
					 "<input type='hidden' id='idTela"+contadorGeneral+"' class='form-control' value='"+idTela  +"'>"+
					 $('#prendaCotizacion').find('option:selected').text() +
					 "<input type='hidden' class='form-control importeFinal' id='importeFinal"+contadorGeneral+"' value="+precioInicial*cantidad+" disabled>" +
					 "<input type='hidden' class='form-control precioFinal' id='precioFinal"+contadorGeneral+"' value="+precioInicial+" disabled>",
					 $('#modeloCotizacion').find('option:selected').text(),
					 $('#telaCotizacion').find('option:selected').text(),
					 data[0],
					 data[1] ,
					 precioInicial,
					 "<select class='form-control selectpicker bordadoPrecioCotizacion' id='bordadoPrecioCotizacion"+contadorGeneral+"' title='Selecciona uno...'>" +
					 listaBordados+"</select>" ,
					 "<input type='number' class='form-control porcentajeCotizacion' id='porcentajeCotizacion"+contadorGeneral+"' placeholder='5' value=0>" ,
					 "<input type='number' class='form-control montoCotizacion' id='montoCotizacion"+contadorGeneral+"' placeholder='15' value=0>" ,
					 precioInicial,
					 precioInicial*cantidad,
					 "<a class='btn btn-danger btn-circle btn-sm text-white popoverxd' id='borrar' data-container='body' data-placement='top'><i class='fas fa-minus'></i></a>"    
					 ]).node().id ="row"+contadorGeneral;
					table.draw( false );
					$('#bordadoPrecioCotizacion'+contadorGeneral).selectpicker("refresh");
					contadorGeneral++;
				}
			}
		},
		error: (e) => {
		}
		
	});
}

$('#tipoCotizacion').on('change', function(){

	if($(this).val()=='1'){
		cotizacionGeneral();
	}
	else if($(this).val()=='2'){
		cotizacionDesglosada();
	}
	else{
		cotizacionDesglosadaByTipoComposicion();
	}
	
})


function cotizacionGeneral(){
	var tablita = table.rows().data().toArray();

	console.log(tablita[0]==null);
	if (tablita[0]==null){
		table.columns(':eq(1)').visible(true);
		table.columns(':eq(0)').visible(true);
		table.columns(':eq(3)').visible(true);
		table.columns(':eq(4)').visible(true);
		table.columns(':eq(6)').visible(true);
		table.columns(':eq(12)').visible(true);
		
		$('.cantidadCotizacion').hide();
		$('.coordinadoCotizacion').hide();
		$('.modeloCotizacion').hide();
		$('#GeneralDesglosada').text("Familia de composición");
		
		table.columns(':eq(1)').visible(false);
		table.columns(':eq(0)').visible(false);
		table.columns(':eq(3)').visible(false);
		table.columns(':eq(4)').visible(false);
		table.columns(':eq(6)').visible(false);
		table.columns(':eq(12)').visible(false);
		table.draw();
		if($('#idCotizacion').val()){
			ValidarCotizacionInfo();
		}
	}
	else{
		$('#tipoCotizacion').val("");
		$('#tipoCotizacion').selectpicker("refresh");
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'Para cambiar de cotización, debe borrar todos los registros de la tabla en la pestaña de prendas',
			showConfirmButton: false,
	        timer: 5500
		  })
	}
}

function cotizacionDesglosada(){
	var tablita = table.rows().data().toArray();

	if (tablita[0]==null){
	
		table.columns().visible(true);
		$('#GeneralDesglosada').text("Tela");
		$('.cantidadCotizacion').show();
		$('.coordinadoCotizacion').show();
		$('.modeloCotizacion').show();
		table.draw();
		
		if($('#idCotizacion').val()){
			ValidarCotizacionInfo();
		}
	}
	else{
		$('#tipoCotizacion').val("");
		$('#tipoCotizacion').selectpicker("refresh");
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'Para cambiar de cotización, debe borrar todos los registros de la tabla en la pestaña de prendas',
			showConfirmButton: false,
	        timer: 5500
		  })
	}
}

function cotizacionDesglosadaByTipoComposicion(){
	var tablita = table.rows().data().toArray();

	if (tablita[0]==null){
		table.columns(':eq(0)').visible(true);
		table.columns(':eq(1)').visible(true);
		table.columns(':eq(12)').visible(true);
		
		$('.cantidadCotizacion').show();
		$('.coordinadoCotizacion').show();
		$('.modeloCotizacion').hide();
		$('#GeneralDesglosada').text("Familia de composición");
		table.columns(':eq(3)').visible(false);
		table.columns(':eq(4)').visible(false);
		table.columns(':eq(6)').visible(false);
		table.draw();
		table.draw();
		if($('#idCotizacion').val()){
			ValidarCotizacionInfo();
		}
	}
	else{
		$('#tipoCotizacion').val("");
		$('#tipoCotizacion').selectpicker("refresh");
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'Para cambiar de cotización, debe borrar todos los registros de la tabla en la pestaña de prendas',
			showConfirmButton: false,
	        timer: 5500
		  })
	}
}

//=Borrar registro de la tabla de agregar una muestra==//
//                                                     //
 $(document).on('click', '#borrar', function (event) { //
     event.preventDefault();                           //
     let hola = $(this).closest('tr');  		       //
     table.row(hola).remove().draw();                  //
 });                                                   //
//=====================================================//

//===========Cambiar dinámicamente el precio del bordado en el precio unitario final============//
 $(document).on('change', '.bordadoPrecioCotizacion',function (event) {							//
	 event.preventDefault(); 																	//
		table.columns(':eq(1)').visible(true);													//
		table.columns(':eq(0)').visible(true);													//
		table.columns(':eq(3)').visible(true);
		table.columns(':eq(4)').visible(true);
		table.columns(':eq(6)').visible(true);
		table.columns(':eq(12)').visible(true);	
		let fila = $(this).closest('tr');														//
	 var bordadoPrecioCotizacion = parseFloat((fila.find('select').val()=='')?0:fila.find('select').val());				//
	 var array = table.row(fila).data();														//
	 var montoCotizacion = parseFloat((fila.find('.montoCotizacion').val()=='')?0:fila.find('.montoCotizacion').val());//
	 var precio = parseFloat(array[7])+bordadoPrecioCotizacion+montoCotizacion;					//
	 fila.find('.precioFinal').val(precio.toFixed(2)); 											//
	 fila.find('.importeFinal').val((precio*parseFloat(fila.find('.cantidad').val())).toFixed(2));//
	 fila.find('td:eq(11)').text(fila.find('.precioFinal').val());								//
	 fila.find('td:eq(12)').text(fila.find('.importeFinal').val());								//
	 if($('#tipoCotizacion').val()==1){															//
		table.columns(':eq(1)').visible(false);													//
		table.columns(':eq(0)').visible(false);													//
		table.columns(':eq(3)').visible(false);
		table.columns(':eq(4)').visible(false);
		table.columns(':eq(6)').visible(false);
		table.columns(':eq(12)').visible(false);												//
	 }																							//
	 if($('#tipoCotizacion').val()==3){
		table.columns(':eq(3)').visible(false);
		table.columns(':eq(4)').visible(false);
		table.columns(':eq(6)').visible(false);	 
	 }
	 $('.selectpicker').selectpicker("refresh");																		//
	 table.draw(false);  																		//
 });																							//
 //=============================================================================================//

 //=================Cambiar dinámicamente el porcentaje adicional en el precio unitario final===================//
 $(document).on('change', '.porcentajeCotizacion', function (event) {											//
	 event.preventDefault(); 																					//
		table.columns(':eq(1)').visible(true); 																	//
		table.columns(':eq(0)').visible(true); 																	//
		table.columns(':eq(3)').visible(true);
		table.columns(':eq(4)').visible(true);
		table.columns(':eq(6)').visible(true);
		table.columns(':eq(12)').visible(true); 																//
	 var porcentajeCotizacion = parseFloat(($(this).val()=='')?0:$(this).val());								//
	 let fila = $(this).closest('tr');																			//
	 var array = table.row(fila).data();																		//
	 var bordadoPrecioCotizacion = parseFloat((fila.find('select').val()=='')?0:fila.find('select').val());//
	 var precio = parseFloat(array[7])*(porcentajeCotizacion/100);												//
	 fila.find('.montoCotizacion').val(precio.toFixed(2));														//
	 var montoCotizacion = parseFloat(precio);																	//
	 fila.find('.precioFinal').val((parseFloat(array[7])+montoCotizacion+bordadoPrecioCotizacion).toFixed(2));	//
	 fila.find('.importeFinal').val((parseFloat(fila.find('.precioFinal').val())*parseFloat(fila.find('.cantidad').val())).toFixed(2));//
	 fila.find('td:eq(11)').text(fila.find('.precioFinal').val()); 												//
	 fila.find('td:eq(12)').text(fila.find('.importeFinal').val()); 											//
	 if($('#tipoCotizacion').val()==1){ 																		//
		table.columns(':eq(1)').visible(false); 																//
		table.columns(':eq(0)').visible(false); 																//
		table.columns(':eq(3)').visible(false);
		table.columns(':eq(4)').visible(false);
		table.columns(':eq(6)').visible(false);
		table.columns(':eq(12)').visible(false); 																//
	 } 																											//
	 if($('#tipoCotizacion').val()==3){
		table.columns(':eq(3)').visible(false);
		table.columns(':eq(4)').visible(false);
		table.columns(':eq(6)').visible(false);	 
	}
	 if($(this).val()==''){ 																					//
		 $(this).val(0); 																						//
	 } 																											//
	 table.draw(false); 																						//
 });	 																										//
 //=============================================================================================================//
 
 
 //==============================Cambiar dinámicamente el monto adicional en el precio unitario final===============================//
 $(document).on('change', '.montoCotizacion', function (event) {																	//
	 event.preventDefault();  																										//
		table.columns(':eq(1)').visible(true);																						//
		table.columns(':eq(0)').visible(true);																						//
		table.columns(':eq(3)').visible(true);
		table.columns(':eq(4)').visible(true);
		table.columns(':eq(6)').visible(true);
		table.columns(':eq(12)').visible(true);																						//
	 var montoCotizacion = parseFloat(($(this).val()=='')?0:$(this).val());															//
	 let fila = $(this).closest('tr'); 																								//
	 var array = table.row(fila).data(); 																							//
	 var bordadoPrecioCotizacion = parseFloat((fila.find('select').val()=='')?0:fila.find('select').val());											//
	 var precio = parseFloat(montoCotizacion*100/parseFloat(array[7]));																//
	 fila.find('.porcentajeCotizacion').val((precio>=0)?precio.toFixed(2):0); 														//
	 fila.find('.precioFinal').val(((parseFloat(array[7])+montoCotizacion+bordadoPrecioCotizacion)>=0)?								//
			 						(parseFloat(array[7])+montoCotizacion+bordadoPrecioCotizacion).toFixed(2):parseFloat(array[7]));//
	 fila.find('.importeFinal').val((parseFloat(fila.find('.precioFinal').val())*parseFloat(fila.find('.cantidad').val())).toFixed(2));//
 fila.find('td:eq(11)').text(fila.find('.precioFinal').val());																		//
 fila.find('td:eq(12)').text(fila.find('.importeFinal').val());																		//
	 if($('#tipoCotizacion').val()==1){																								//
		table.columns(':eq(1)').visible(false);																						//
		table.columns(':eq(0)').visible(false);																						//
		table.columns(':eq(3)').visible(false);
		table.columns(':eq(4)').visible(false);
		table.columns(':eq(6)').visible(false);
		table.columns(':eq(12)').visible(false);																					//
	 }																																//
	 if($('#tipoCotizacion').val()==3){
		table.columns(':eq(3)').visible(false);
		table.columns(':eq(4)').visible(false);
		table.columns(':eq(6)').visible(false);	 
	}
	 if($(this).val()==''){																											//
		 $(this).val(0);																											//
	 }																																//
	 table.draw(false);  																											//
 });	 																															//
 //=================================================================================================================================// 
	 
 
 //======================Cambiar dinámicamente el porcentaje de anticipo en el subtotal=========================//
 $(document).on('change', '#anticipoCotizacion', function (event) {												//
	 event.preventDefault(); 																					//
	 var porcentajeAnticipo = parseFloat(($(this).val()=='')?0:$(this).val());									//
	 var descuentoMontoCotizacion = parseFloat(($('#descuentoMontoCotizacion').val()=='')?0:$('#descuentoMontoCotizacion').val());//
	 var anticipoMontoCotizacion = parseFloat(($('#anticipoMontoCotizacion').val()=='')?0:$('#anticipoMontoCotizacion').val());//
	 var subtotal = parseFloat($('#SubtotalInicial').val());													//
	 var Total = parseFloat($('#Subtotal').text());																//
	 $('#anticipoMontoCotizacion').val((subtotal*(porcentajeAnticipo/100)).toFixed(2));							//
	 anticipoMontoCotizacion = (parseFloat($('#anticipoMontoCotizacion').val())).toFixed(2);
	 $('#Subtotal2').text((subtotal+descuentoMontoCotizacion).toFixed(2));				//
	 $('#IVAMonto').text((parseFloat($('#Subtotal2').text())*(parseFloat($('#IVACotizacion').val())/100)).toFixed(2));//
	 $('#Total').text((parseFloat($('#Subtotal2').text())+parseFloat($('#IVAMonto').text())).toFixed(2));		//
	 $('#DescuentoCargo').text((parseFloat(descuentoMontoCotizacion)).toFixed(2));
	 $('#AnticipoMontoFinal').text((parseFloat(anticipoMontoCotizacion)).toFixed(2));
	 $('#Total2').text((parseFloat($('#Total').text())-anticipoMontoCotizacion).toFixed(2));
	 if($(this).val()==''){ 																					//
		 $(this).val(0); 																						//
	 } 																											//
 });	 																										//
 //=============================================================================================================//

 //========================Cambiar dinámicamente el monto de anticipo en el subtotal============================//
 $(document).on('change', '#anticipoMontoCotizacion', function (event) {										//
	 event.preventDefault(); 																					//
	 var anticipoMontoCotizacion = parseFloat(($(this).val()=='')?0:$(this).val());								//
	 var descuentoMontoCotizacion = parseFloat(($('#descuentoMontoCotizacion').val()=='')?0:$('#descuentoMontoCotizacion').val());//
	 var subtotal = parseFloat($('#SubtotalInicial').val());													//
	 var Total = parseFloat($('#Subtotal').text());																//
	 $('#anticipoCotizacion').val(((anticipoMontoCotizacion*100)/subtotal).toFixed(2));							//
	 $('#Subtotal2').text((subtotal+descuentoMontoCotizacion).toFixed(2));				//
	 $('#IVAMonto').text((parseFloat($('#Subtotal2').text())*(parseFloat($('#IVACotizacion').val())/100)).toFixed(2));//
	 $('#Total').text((parseFloat($('#Subtotal2').text())+parseFloat($('#IVAMonto').text())).toFixed(2));		//
	 $('#DescuentoCargo').text((parseFloat(descuentoMontoCotizacion)).toFixed(2));
	 $('#AnticipoMontoFinal').text((parseFloat(anticipoMontoCotizacion)).toFixed(2));
	 $('#Total2').text((parseFloat($('#Total').text())-anticipoMontoCotizacion).toFixed(2));
	 if($(this).val()==''){																						//
		 $(this).val(0);																						//
	 } 																											//
 });	 																										//
 //=============================================================================================================//
 
 //=====================Cambiar dinámicamente el porcentaje de descuento en el subtotal=========================//
 $(document).on('change', '#descuentoCotizacion', function (event) {											//
	 event.preventDefault(); 																					//
	 var descuentoCotizacion = parseFloat(($(this).val()=='')?0:$(this).val());									//
	 var descuentoMontoCotizacion = parseFloat(($('#descuentoMontoCotizacion').val()=='')?0:$('#descuentoMontoCotizacion').val());//
	 var anticipoMontoCotizacion = parseFloat(($('#anticipoMontoCotizacion').val()=='')?0:$('#anticipoMontoCotizacion').val());//
	 var subtotal = parseFloat($('#SubtotalInicial').val());													//
	 var Total = parseFloat($('#Subtotal').text());																//
	 $('#descuentoMontoCotizacion').val((subtotal*(descuentoCotizacion/100)).toFixed(2));						//
	 descuentoMontoCotizacion = parseFloat($('#descuentoMontoCotizacion').val());
	 $('#Subtotal2').text((subtotal+descuentoMontoCotizacion).toFixed(2));					//
	 $('#IVAMonto').text((parseFloat($('#Subtotal2').text())*(parseFloat($('#IVACotizacion').val())/100)).toFixed(2));//
	 $('#Total').text((parseFloat($('#Subtotal2').text())+parseFloat($('#IVAMonto').text())).toFixed(2));		//
	 $('#DescuentoCargo').text((parseFloat(descuentoMontoCotizacion)).toFixed(2));
	 $('#AnticipoMontoFinal').text((parseFloat(anticipoMontoCotizacion)).toFixed(2));
	 $('#Total2').text((parseFloat($('#Total').text())-anticipoMontoCotizacion).toFixed(2));
	 if($(this).val()==''){																						//
		 $(this).val(0);																						//
	 }																											//
 });	 																										//
 //=============================================================================================================//
 
 //========================Cambiar dinámicamente el monto de descuento en el subtotal============================//
 $(document).on('change', '#descuentoMontoCotizacion', function (event) {										//
	 event.preventDefault(); 																					//
	 var descuentoMontoCotizacion = parseFloat(($(this).val()=='')?0:$(this).val());							//
	 var anticipoMontoCotizacion = parseFloat(($('#anticipoMontoCotizacion').val()=='')?0:$('#anticipoMontoCotizacion').val());//
	 var subtotal = parseFloat($('#SubtotalInicial').val());													//
	 var Total = parseFloat($('#Subtotal').text());																//
	 $('#descuentoCotizacion').val(((descuentoMontoCotizacion*100)/subtotal).toFixed(2));						//
	 $('#Subtotal2').text((subtotal+descuentoMontoCotizacion).toFixed(2));				//
	 $('#IVAMonto').text((parseFloat($('#Subtotal2').text())*(parseFloat($('#IVACotizacion').val())/100)).toFixed(2));//
	 $('#Total').text((parseFloat($('#Subtotal2').text())+parseFloat($('#IVAMonto').text())).toFixed(2));		//
	 $('#DescuentoCargo').text((parseFloat(descuentoMontoCotizacion)).toFixed(2));
	 $('#AnticipoMontoFinal').text((parseFloat(anticipoMontoCotizacion)).toFixed(2));
	 $('#Total2').text((parseFloat($('#Total').text())-anticipoMontoCotizacion).toFixed(2));
	 if($(this).val()==''){																						//
		 $(this).val(0);																						//
	 }																											//
 });	 																										//
 //=============================================================================================================//
 
function GuardarCotizacionInfo(){
	var numeroCotizacion = $('#numeroCotizacion').val();
	var tituloCotizacion = $('#tituloCotizacion').val();
	var tipoCotizacion = $('#tipoCotizacion').val();
	
	
	var tipoPrecioVentas = $('#tipoPrecioVentas').val();
	var gerenteVentas = $('#gerenteVentas').val();
	var clienteCotizacion = $('#clienteCotizacion').val();
	 if(valorIndice==null){
	    	var agenteCotizacion = $('#agenteCotizacion').val();
	    }
	    else{
	    	var agenteCotizacion = $('#agenteCotizacion').data('agente');
	    }
	
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
	    	console.log(data);
			if(data!=0){
				Swal.fire({
		            position: 'center',
		            icon: 'success',
		            title: 'Correcto',
		            html: '¡Se han guardado los datos correctamente!',
		            showConfirmButton: false,
		            timer: 3500
		        })
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
		            timer: 3500
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
	            timer: 3500
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
			console.log("ya edita");
			if(data!=0){

				Swal.fire({
		            position: 'center',
		            icon: 'success',
		            title: 'Correcto',
		            html: '¡Se han editado los datos correctamente!',
		            showConfirmButton: false,
		            timer: 3500
		        })
            	$('#custom-tabs-two-profile-tab').click();
			}else{
				Swal.fire({
		          position: 'center',
		          icon: 'error',
		          title: 'Error',
		          html: '¡Algo salió mal al guardar los datos!',
		          showConfirmButton: false,
		          timer: 3500
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



function GuardarCotizacionPrendas(refEditPrenda){
	var idCotizacion = $('#idCotizacion').val();
	var idCotizacionPrendas = $('#idCotizacionToPrendas').val();
	var tipoCotizacion = $('#tipoCotizacion').val();
	var tablita = table.rows().data();
	var contador=0;
	var subtotal = 0;
	var validadorModelo = 0;
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
				if(tipoCotizacion=='2' && ($('#idModelo'+contador).val()==null || $('#idModelo'+contador).val()=='' || $('#idModelo'+contador).val()==undefined)){
					validadorModelo = 1;
				}
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
		
		$('#descuentoMontoCotizacion').val(0);
		$('#anticipoMontoCotizacion').val(0);
		$('#SubtotalInicial').val(subtotal);
		$('#Subtotal').text(subtotal);
		$('#Subtotal2').text((parseFloat($('#Subtotal').text())+parseFloat($('#descuentoMontoCotizacion').val())).toFixed(2));
		$('#IVAMonto').text((parseFloat($('#Subtotal').text())*(parseFloat($('#IVACotizacion').val())/100)).toFixed(2));
		$('#Total').text((parseFloat($('#Subtotal2').text())+parseFloat($('#IVAMonto').text())).toFixed(2));
		$('#Total2').text((parseFloat($('#Total').text())-parseFloat($('#anticipoMontoCotizacion').val())).toFixed(2));
		$('#tipoCotizacion').attr("disabled", true);
		
		if(validadorModelo==1){
			Swal.fire({
				icon: 'error',
				title: 'Error al guardar la cotización',
				text: '¡Asegúrese de seleccionar un modelo y una tela por cada registro!',
				showConfirmButton: false,
		        timer: 4500
			})
		}
		else{
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
				if(data!='0'){
					Swal.fire({
						icon: 'success',
						title: 'Cotización Agregada',
						text: '¡Se han agregado las prendas con éxito!',
						showConfirmButton: false,
				        timer: 2000,
				        onClose: () => {
				        	$('#custom-precios').attr("href","#preciosDiv");
				        	$('#custom-precios').attr("data-toggle","pill");
				        	$('#custom-precios').click();
				        }
					})
				}
				else{
					Swal.fire({
				          position: 'center',
				          icon: 'error',
				          title: 'Error',
				          html: '¡Algo salió mal al guardar los datos!',
				          showConfirmButton: false,
				          timer: 3500
				      })
				}
			}
		});
		}
		console.log(contenedorPrendas);
		$('#idCotizacionToPrendas').val(idCotizacion);
		//location.href = "agregar-cotizacion/"+idCotizacion;
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
				if(tipoCotizacion=='2' && ($('#idModelo'+contador).val()==null || $('#idModelo'+contador).val()=='' || $('#idModelo'+contador).val()==undefined)){
					validadorModelo = 1;
				}
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
		table.draw();
		$('#Subtotal').text(subtotal);
		$('#SubtotalInicial').val(subtotal);
		$('#Subtotal2').text((parseFloat($('#Subtotal').text())+parseFloat($('#descuentoMontoCotizacion').val())).toFixed(2));
		$('#IVAMonto').text((parseFloat($('#Subtotal').text())*(parseFloat($('#IVACotizacion').val())/100)).toFixed(2));
		$('#Total').text((parseFloat($('#Subtotal2').text())+parseFloat($('#IVAMonto').text())).toFixed(2));
		$('#Total2').text((parseFloat($('#Total').text())-parseFloat($('#anticipoMontoCotizacion').val())).toFixed(2));
		
		if(validadorModelo==1){
			Swal.fire({
				icon: 'error',
				title: 'Error al guardar la cotización',
				text: '¡Asegúrese de seleccionar un modelo por cada registro!',
				showConfirmButton: false,
		        timer: 4500
			})
		}
		else{
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
					if(refEditPrenda!=0){
						Swal.fire({
							icon: 'success',
							title: 'Cotización Editada',
							text: '¡Se han editado las prendas con éxito!',
							showConfirmButton: false,
					        timer: 2000,
					        onClose: () => {
					        	$('#custom-precios').click();
					        }
						})
					}
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
		}
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
					  $(celdas[3]).text(),
					  $(celdas[6]).text(),
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
	var tipoCotizacion = $('#tipoCotizacion').val();
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
	if($('#tipoCotizacion').val()==1){
		if(prendaCotizacion==""     || prendaCotizacion==null     || prendaCotizacion==undefined     ||
		   telaCotizacion==""       || telaCotizacion==null       || telaCotizacion==undefined){
			Swal.fire({
				icon: 'error',
				title: 'Error',
				text: '¡Debe agregar un campo válido!',
				showConfirmButton: false,
		        timer: 3500
			  })
		}
		else{			
			if(validador==0){
				 $('#cantidadCotizacion').val(1);
				 $('#coordinadoCotizacion').val(1);
				AgregarRegistroTablita();
			}
		}
	}
	else if($('#tipoCotizacion').val()==2){
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
			if(validador==0){
				AgregarRegistroTablita();
			}
		}
	}
	else{
		if(coordinadoCotizacion=="" || coordinadoCotizacion==null || coordinadoCotizacion==undefined ||
				   prendaCotizacion==""     || prendaCotizacion==null     || prendaCotizacion==undefined     ||
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
					if(validador==0){
						AgregarRegistroTablita();
					}
				}
	}
}

function ValidarCotizacionPrendas(refEditPrenda){
	var tablita = table.rows().data().toArray();
	var validador = 0;
	var tablita = table.rows().data();
	var contador = 0;
	var contadorsito=0;
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
		table.draw();
		for (contador=0;contador<contadorGeneral;contador++){
			if(tablita.rows('#row'+contador).any()){
				console.log("el contador es"+contador);
				if($('#bordadoPrecioCotizacion'+contador).val()==null || $('#bordadoPrecioCotizacion'+contador).val()=='' || $('#bordadoPrecioCotizacion'+contador).val()==undefined){
					validador=1;
				}
				if(contadorsito==4){
					contadorsito=-1;
					table.page( 'next' ).draw( 'page' );
				}
				contadorsito++;
			}
		}
		if(validador==0){
		GuardarCotizacionPrendas(refEditPrenda);
		}
		else{
			Swal.fire({
				icon: 'error',
				title: 'Error',
				text: 'Debe haber tener un precio de bordado',
				showConfirmButton: false,
		        timer: 3500
			  })
		}
	}
}

function ValidarCotizacionTotal(){
	
	GuardarCotizacionTotal();
}

