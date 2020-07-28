$(document).ready(function() {
});


function listarTabla(){

	var table = $('table.tablexd')
			.DataTable(
					{
						"ordering" : false,
						"pageLength" : 5,
						"responsive" : true,
						"lengthMenu" : [
								[ 5, 10, 25, 50, 100 ],
								[ 5, 10, 25, 50, 100 ] ],
						"language" : {
							"sProcessing" : "Procesando...",
							"sLengthMenu" : "Mostrar _MENU_ registros",
							"sZeroRecords" : "No se encontraron resultados",
							"sEmptyTable" : "Ningún dato disponible en esta tabla =(",
							"sInfo" : "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
							"sInfoEmpty" : "Mostrando registros del 0 al 0 de un total de 0 registros",
							"sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
							"sInfoPostFix" : "",
							"sSearch" : "Buscar:",
							"sUrl" : "",
							"sInfoThousands" : ",",
							"sLoadingRecords" : "Cargando...",
							"oPaginate" : {
								"sFirst" : "Primero",
								"sLast" : "Último",
								"sNext" : "Siguiente",
								"sPrevious" : "Anterior"
							},
							"oAria" : {
								"sSortAscending" : ": Activar para ordenar la columna de manera ascendente",
								"sSortDescending" : ": Activar para ordenar la columna de manera descendente"
							},
							"buttons" : {
								"copy" : "Copiar",
								"colvis" : "Visibilidad"
							}
						}
					});
	new $.fn.dataTable.FixedHeader(table);
}


function nextTab(elem) {
	$(elem).parent().next().find('a[data-toggle="tab"]').click();

}
function prevTab(elem) {
	$(elem).parent().prev().find('a[data-toggle="tab"]').click();

}

function listarOperarios(){

				
	var operarioEncogi = $('#operarioEncogiS').val();
	if(operarioEncogi!="" || operarioEncogi!=null || operarioEncogi!=undefined){
	$('#operarioEncogi option[value="'+operarioEncogi+'"]').attr("selected", true);}
	var operarioLavado = $('#operarioLavadoS').val();
	if(operarioLavado!="" || operarioLavado!=null || operarioLavado!=undefined){
	$('#operarioLavado option[value="'+operarioLavado+'"]').attr("selected", true);}
	var operarioCostura = $('#operarioCosturaS').val();
	if(operarioCostura!="" || operarioCostura!=null || operarioCostura!=undefined){
	$('#operarioCostura option[value="'+operarioCostura+'"]').attr("selected", true);}
	var operarioContaminacion = $('#operarioContaminacionS').val();
	if(operarioContaminacion!="" || operarioContaminacion!=null || operarioContaminacion!=undefined){
	$('#operarioContaminacion option[value="'+operarioContaminacion+'"]').attr("selected", true);}
	$('.idOperarios').selectpicker('refresh');
console.log(operarioEncogi);
	
}

function listarEntretelas(){
	var tela = $('#entretelas').val();
	$('#entretelaEncogi option[value="'+tela+'"]').attr("selected", true);
	$('#entretelaEncogi').selectpicker('refresh');

}

function listarProveedores(){
		
	proveedor = $('#proovedores').val();
	$('#proveedorEncogi option[value="'+proveedor+'"]').attr("selected", true);
	$('#proveedorEncogi').selectpicker('refresh');
}

function listarAgujas(){

	var aguja = $('#aguja').val();
	$('#tipoAguja option[value="'+aguja+'"]').attr("selected", true);
	$('#tipoAguja').selectpicker('refresh');

}




//=================== funciones para guardar las pruebas =======================//
//                                    |                                         //
//                                    |                                         //
//                                    v                                         //


function PruebasEncogimiento(){
	
//===================variables de pruebas de encogimiento=======================
	var tipoTela= $('#tela').val();
	var operario= $('#operarioEncogi').val();
	var frealizacion= $('#fechaRealizacionEncogi').val();
	
//===================variables de Pruebas de Fusión=======================
	var entretela= $('#entretelaEncogi').val();
	var adherencia= $('input:radio[name=fusionAdherencia]:checked').val();
	var proveedor= $('#proveedorEncogi').val();
	var temperatura= $('#temperaturaPruebaVapor').val();
	var tiempo= $('#tiempoPruebaVapor').val();
	var presion= $('#presionPruebaVapor').val();
	var medidaHiloPruebaVapor= $('#medidaHiloPruebaVapor').val();
	var medidaTramaPruebaVapor= $('#finalTramaPruebaVapor').val();
//resultado
	var diferenciaHiloPruebaVapor= $('#diferenciaHiloPruebaVapor').val();
	//var finalHiloMedPruebaVapor= $('#finalHiloMedPruebaVapor').val();
	var diferenciaTramaPruebaVapor= $('#diferenciaTramaPruebaVapor').val();
	//var finalTramaMedPruebaVapor= $('#finalTramaMedPruebaVapor').val();
	var observacionesReultPruebaVapor= $('#observacionesReultPruebaVapor').val();

	
//===================variables de Plancha de vapor=======================
	var medidaHiloPlanchaVapor= $('#medidaHiloPlanchaVapor').val();
	var medidaTramaPlanchaVapor= $('#medidaTramaPlanchaVapor').val();
	//resultado
	var diferenciaHiloPlanchaVapor= $('#diferenciaHiloPlanchaVapor').val();
	//var finalHiloMedPlanchaVapor= $('#finalHiloMedPlanchaVapor').val();
	var diferenciaTramaPlanchaVapor= $('#diferenciaTramaPlanchaVapor').val();
	//var finalTramaMedPlanchaVapor= $('#finalTramaMedPlanchaVapor').val();
	var observacionesReultPlanchaVapor= $('#observacionesReultPlanchaVapor').val();
	var idCalidad = $('#idCalidadEnco').val();
	
	var valordeReferencia = " ";
	var idMaterial = $('#idMaterial').val();
	var tipoMaterial = $('#tipoMaterial').val();
	var datos = [tipoTela,
				operario, 
				frealizacion,
				entretela,
				adherencia,
				proveedor,
				temperatura,
				tiempo,
				presion,
				medidaHiloPruebaVapor,
				medidaTramaPruebaVapor,
				diferenciaHiloPruebaVapor,
				diferenciaTramaPruebaVapor,
				observacionesReultPruebaVapor,
				medidaHiloPlanchaVapor,
				medidaTramaPlanchaVapor,
				diferenciaHiloPlanchaVapor,
				diferenciaTramaPlanchaVapor,
				observacionesReultPlanchaVapor,
				idCalidad,
				valordeReferencia,
				idMaterial,
				tipoMaterial];
	var dato = datos.toString();
	console.log(datos);
	$.ajax({
	    type: "POST",
	    url: "/guardarPruebaEncogimiento",
		
		 data: {
		    	"_csrf": $('#token').val(),
		    	datos:dato

		    },
	    
	    success: (data) => {
	    	if(data==1){
		    	$('#Guardandoencogi').hide();
		    	$('#botonguardarencogi').show();
				$('#botonguardarencogi').attr('disabled',true);
				
		    	Swal.fire({
		            position: 'center',
		            icon: 'success',
		            title: 'Insertado correctamente',
		            showConfirmButton: false,
		            timer: 1550,
		            onClose: () => {
		            	location.reload();
				  }   
		        })
	    	}
	    	else{
	    		$('#Guardandoencogi').hide();
		    	$('#botonguardarencogi').show();
				$('#botonguardarencogi').attr('disabled',false);
	    		Swal.fire({
		            position: 'center',
		            icon: 'error',
		            title: 'Error',
		            html: 'Algo salió mal, no pudo guardarse correctamente',
		            showConfirmButton: false,
		            timer: 3500,
		        })
	    	}
	    },
	   
	    error: (e) => {
	    }
	});
}

function cargando() {
	 Swal.fire({
        title: 'Cargando ',
        html: 'Por favor espere',// add html attribute if you want or remove
        allowOutsideClick: false,
        timerProgressBar: true,
        onBeforeOpen: () => {
            Swal.showLoading()
        },	
    });
}

function PruebasLavado(){
	
//===================variables de pruebas de lavado y pilling=======================
	var tipoTela= $('#telas').val();
	var operario= $('#operarioLavado').val();
	var frealizacion= $('#fechaRealizacionLavado').val();
	
//===================variables de Prueba de lavado=======================
	var medidaHiloPruebaLavado= $('#medidaHiloPruebaLavado').val();
	var medidaTramaPruebaLavado= $('#medidaTramaPruebaLavado').val();
//resultado
	var diferenciaHiloPlanchaVapor= $('#diferenciaHiloPruebaLavado').val();
	//var finalHiloMedPlanchaVapor= $('#finalHiloMedPruebaLavado').val();
	var diferenciaTramaPlanchaVapor= $('#diferenciaTramaPruebaLavado').val();
	//var finalTramaMedPlanchaVapor= $('#finalTramaMedPruebaLavado').val();
	var observacionesReultPlanchaVapor= $('#observacionesReultPruebaLavado').val();
	
//===================variables de Prueba de solidez/color/Pilling=======================
	
	var calidad = $('input:radio[name=calidadLavado]:checked').val();	
	var pilling = $('input:radio[name=decision]:checked').val();
	var observacionesReultPilling= $('#observacionesReultPilling').val();
	var idCalidad = $('#idCalidadLavado').val();
	var valordeReferencia = " ";
	var idMaterial = $('#idMaterial').val();
	var tipoMaterial = $('#tipoMaterial').val();
	var datos =[tipoTela,
				operario,
				frealizacion,
				medidaHiloPruebaLavado,
				medidaTramaPruebaLavado,
				diferenciaHiloPlanchaVapor,
				diferenciaTramaPlanchaVapor,
				observacionesReultPlanchaVapor,
				calidad,
				pilling,
				observacionesReultPilling,
				idCalidad,
				valordeReferencia,
				idMaterial,
				tipoMaterial];
	var dato = datos.toString();
	console.log(datos);
	$.ajax({
	    type: "POST",
	    url: "/guardarPruebaLavado",
		
		 data: {
		    	"_csrf": $('#token').val(),
		    	datos:dato

		},
	    success: (data) => {
	    	if(data==1){
		    	$('#Guardandolavado').hide();
		    	$('#botonguardarlavado').show();
				$('#botonguardarlavado').attr('disabled',true);
				
		    	Swal.fire({
		            position: 'center',
		            icon: 'success',
		            title: 'Insertado correctamente',
		            showConfirmButton: false,
		            timer: 1550,
		            onClose: () => {
		            	location.reload();
				  }   
		        })
	    	}
	    	else{
	    		$('#Guardandolavado').hide();
		    	$('#botonguardarlavado').show();
				$('#botonguardarlavado').attr('disabled',false);
	    		Swal.fire({
		            position: 'center',
		            icon: 'error',
		            title: 'Error',
		            html: 'Algo salió mal, no pudo guardarse correctamente',
		            showConfirmButton: false,
		            timer: 3500,
		        })
	    	}
	    		
	    	
	    },
	   
	    error: (e) => {

	    }
	});
}


function PruebaCostura(){
	
	//===================variables de pruebas de Costura=======================
	var tipoTela= $('#telass').val();
	var operario= $('#operarioCostura').val();
	var frealizacion= $('#fechaRealizacionCostura').val();
	
//===================variables de Prueba de costura=======================
	var tipoAguja = $('#tipoAguja').val();
	var Deslizamiento = $('input:radio[name=decisionDeslizamiento]:checked').val();
	
	var Rasgado = $('input:radio[name=decisionRasgado]:checked').val();
	var observacionesRasgado= $('#observacionesRasgado').val();
	var idCalidad = $('#idCalidadCostura').val();
	var valordeReferencia = " ";
	var idMaterial = $('#idMaterial').val();
	var tipoMaterial = $('#tipoMaterial').val();
	var datos =[tipoTela,
				operario,
				frealizacion,
				tipoAguja,
				Deslizamiento,
				Rasgado,
				observacionesRasgado,
				idCalidad,
				valordeReferencia,
				idMaterial,
				tipoMaterial];
	var dato = datos.toString();
	console.log(datos);
	$.ajax({	  
		type: "POST",
	    url: "/guardarPruebaCostura",
		
		 data: {
		    	"_csrf": $('#token').val(),
		    	datos:dato

		},
	    success: (data) => {
	    	if(data==1){
		    	$('#Guardandocostura').hide();
		    	$('#botonguardarcostura').show();
				$('#botonguardarcostura').attr('disabled',true);
				
		    	Swal.fire({
		            position: 'center',
		            icon: 'success',
		            title: 'Insertado correctamente',
		            showConfirmButton: false,
		            timer: 1550,
		            onClose: () => {
		            	location.reload();
				  }   
		        })
	    	}
	    	else{
	    		$('#Guardandocostura').hide();
		    	$('#botonguardarcostura').show();
				$('#botonguardarcostura').attr('disabled',false);
	    		Swal.fire({
		            position: 'center',
		            icon: 'error',
		            title: 'Error',
		            html: 'Algo salió mal, no pudo guardarse correctamente',
		            showConfirmButton: false,
		            timer: 3500,
		        })
	    	}
	    },
	   
	    error: (e) => {
	    }
	});
}

function PruebaContaminacion() {
	
	//===================variables de pruebas de Contaminacion=======================
	var tipoTela= $('#telasss').val();
	var operarioContaminacion= $('#operarioContaminacion').val();
	var frealizacionContaminacion= $('#fechaRealizacionContaminacion').val();
	
	//===================variables de resultados de pruebas de Contaminacion=======================
	var calidadContaminacion = $('input:radio[name=calidadConta]:checked').val();

	var observacionesReultSolidez= $('#observacionesReultContaminacion').val();
	var idCalidad = $('#idCalidadEnco').val();
	console.log(idCalidad);
	var valordeReferencia = " ";
	var idMaterial = $('#idMaterial').val();
	var tipoMaterial = $('#tipoMaterial').val();
	var datos= [tipoTela,
				operarioContaminacion,
				frealizacionContaminacion,
				calidadContaminacion,
				observacionesReultSolidez,
				idCalidad,
				valordeReferencia,
				idMaterial,
				tipoMaterial];
	var dato = datos.toString();
	console.log(datos);
$.ajax({	  
		type: "POST",
	    url: "/guardarPruebaContaminacion",
		
		 data: {
		    	"_csrf": $('#token').val(),
		    	datos:dato
	
		},
	    
	    success: (data) => {
	    	if(data==1){
		    	$('#Guardandocontaminacion').hide();
		    	$('#botonguardarcontaminacion').show();
				$('#botonguardarcontaminacion').attr('disabled',true);
				
		    	Swal.fire({
		            position: 'center',
		            icon: 'success',
		            title: 'Insertado correctamente',
		            showConfirmButton: false,
		            timer: 1550,
		            onClose: () => {
		            	location.reload();
				  }   
		        })
	    	}
	    	else{
		    	$('#Guardandocontaminacion').hide();
		    	$('#botonguardarcontaminacion').show();
				$('#botonguardarcontaminacion').attr('disabled',false);
	    		Swal.fire({
		            position: 'center',
		            icon: 'error',
		            title: 'Error',
		            html: 'Algo salió mal, no pudo guardarse correctamente',
		            showConfirmButton: false,
		            timer: 3500,
		        })
	    	}
	    },
	   
	    error: (e) => {
	    }
	});
	
}

// ============Validaciones de datos==================
function ValidacionEncogimiento() {
   var tipoMaterial= $('#tipoMaterial').val();
    if(tipoMaterial==1){
		if ($('#operarioEncogi').val() != ""
	        && $('#fechaRealizacionEncogi').val() != ""
	        && $('#entretelaEncogi').val() != ""
	        && $('#adherenciaEncogi').val() != ""
	        && $('#proveedorEncogi').val() != ""
	        && $('#temperaturaPruebaVapor').val() != ""
	        && $('#tiempoPruebaVapor').val() != ""
	        && $('#presionPruebaVapor').val() != ""
	        && $('#medidaHiloPruebaVapor').val() != ""
	        && $('#finalTramaPruebaVapor').val() != ""
	        && $('#diferenciaHiloPruebaVapor').val() != ""
	        && $('#diferenciaTramaPruebaVapor').val() != ""
	        && $('#medidaHiloPlanchaVapor').val() != ""
	        && $('#medidaTramaPlanchaVapor').val() != ""
	        && $('#diferenciaHiloPlanchaVapor').val() != ""
	        && $('#diferenciaTramaPlanchaVapor').val() != ""
	    ) {
	        $('#AlertaPestanaEncogimiento').css('display', 'none');
	        $('#enlaceEncogimiento').click();
	        $('#botonguardarencogi').hide();
			$('#Guardandoencogi').show();
	    }
	    else {
	        $('#AlertaPestanaEncogimiento').css('display', 'block');
	    }
	
    }
    
    else{
    	if ($('#operarioEncogi').val() != ""
	        && $('#fechaRealizacionEncogi').val() != ""
	        && $('#medidaHiloPlanchaVapor').val() != ""
	        && $('#medidaTramaPlanchaVapor').val() != ""
	        && $('#diferenciaHiloPlanchaVapor').val() != ""
	        && $('#diferenciaTramaPlanchaVapor').val() != ""
	    ) {
	        $('#AlertaPestanaEncogimiento').css('display', 'none');
	        $('#enlaceEncogimiento').click();
	        $('#botonguardarencogi').hide();
			$('#Guardandoencogi').show();
	    }
	    else {
	        $('#AlertaPestanaEncogimiento').css('display', 'block');
	    }
    }
}

function ValidacionLavado() {
    if ($('#operarioLavado').val() != ""
        && $('#fechaRealizacionLavado').val() != ""
        && $('#medidaHiloPruebaLavado').val() != ""
        && $('#medidaTramaPruebaLavado').val() != ""
        && $('#diferenciaHiloPruebaLavado').val() != ""
        && $('#diferenciaTramaPruebaLavado').val() != ""
        && $('input:radio[name=calidadLavado]:checked').val() == "buena"
        || $('input:radio[name=calidadLavado]:checked').val() == "regular"
        || $('input:radio[name=calidadLavado]:checked').val() == "mala"
        && $('input:radio[name=decision]:checked').val() == "si"
        || $('input:radio[name=decision]:checked').val() == "no"
    ) {
        $('#AlertaPestanaLavado').css('display', 'none');
        $('#enlaceLavado').click();
        $('#botonguardarlavado').hide();
		$('#Guardandolavado').show();
        
    }
    else {
        $('#AlertaPestanaLavado').css('display', 'block');
    }
}

function ValidacionCostura() {
    if ($('#operarioCostura').val() != ""
        && $('#fechaRealizacionCostura').val() != ""
        && $('#tipoAguja').val() != ""

        && $('input:radio[name=decisionDeslizamiento]:checked').val() == "si"
        || $('input:radio[name=decisionDeslizamiento]:checked').val() == "no"

        && $('input:radio[name=decisionRasgado]:checked').val() == "si"
        || $('input:radio[name=decisionRasgado]:checked').val() == "no"
    ) {
        $('#AlertaPestanaCostura').css('display', 'none');
        $('#enlaceCostura').click();
        $('#botonguardarcostura').hide();
		$('#Guardandocostura').show();
        
    }
    else {
        $('#AlertaPestanaCostura').css('display', 'block');
    }
}

function ValidacionContaminacion() {
    if ($('#operarioContaminacion').val() != ""
        && $('#fechaRealizacionContaminacion').val() != ""

        && $('input:radio[name=calidadConta]:checked').val() == "buena"
        || $('input:radio[name=calidadConta]:checked').val() == "regular"
        || $('input:radio[name=calidadConta]:checked').val() == "mala"
    ) {
        $('#AlertaPestanaContaminacion').css('display', 'none');
        $('#enlaceContaminacion').click();
        $('#botonguardarcontaminacion').hide();
		$('#Guardandocontaminacion').show();
        
    }
    else {
        $('#AlertaPestanaContaminacion').css('display', 'block');
    }
}




//Calcular los porcentajes de manera dinámica
function HiloFusion() {
    var total = 0;	
    total = $('#finalHiloMedPruebaVapor').val();
    medidaFusion = ($('#medidaHiloPruebaVapor').val()==null || $('#medidaHiloPruebaVapor').val() == undefined || $('#medidaHiloPruebaVapor').val() == "")?0:parseFloat($('#medidaHiloPruebaVapor').val());
    diferenciaFusion = ($('#diferenciaHiloPruebaVapor').val()==null || $('#diferenciaHiloPruebaVapor').val() == undefined || $('#diferenciaHiloPruebaVapor').val() == "")?0:parseFloat($('#diferenciaHiloPruebaVapor').val());
    total = (total == null || total == undefined || total == "") ? 0 : total;
    total = ((diferenciaFusion *100 / medidaFusion)-100);
    if(medidaFusion!=0 && diferenciaFusion!=0){
    	$('#finalHiloMedPruebaVapor').val(total.toFixed(2)+'%');
    }
    else{
    	$('#finalHiloMedPruebaVapor').val('0%');
    }
}

//Calcular los porcentajes de manera dinámica
function TramaFusion() {
    var total = 0;	
    total = $('#finalTramaMedPruebaVapor').val();
    medidaFusion = ($('#finalTramaPruebaVapor').val()==null || $('#finalTramaPruebaVapor').val() == undefined || $('#finalTramaPruebaVapor').val() == "")?0:parseFloat($('#finalTramaPruebaVapor').val());
    diferenciaFusion = ($('#diferenciaTramaPruebaVapor').val()==null || $('#diferenciaTramaPruebaVapor').val() == undefined || $('#diferenciaTramaPruebaVapor').val() == "")?0:parseFloat($('#diferenciaTramaPruebaVapor').val());
    total = (total == null || total == undefined || total == "") ? 0 : total;
    total = ((diferenciaFusion *100 / medidaFusion)-100);
    if(medidaFusion!=0 && diferenciaFusion!=0){
    	$('#finalTramaMedPruebaVapor').val(total.toFixed(2)+'%');
    }
    else{
    	$('#finalTramaMedPruebaVapor').val('0%');
    }
}

function HiloVapor() {
    var total = 0;	
    total = $('#finalHiloMedPlanchaVapor').val();
    medidaVapor = ($('#medidaHiloPlanchaVapor').val()==null || $('#medidaHiloPlanchaVapor').val() == undefined || $('#medidaHiloPlanchaVapor').val() == "")?0:parseFloat($('#medidaHiloPlanchaVapor').val());
    diferenciaVapor = ($('#diferenciaHiloPlanchaVapor').val()==null || $('#diferenciaHiloPlanchaVapor').val() == undefined || $('#diferenciaHiloPlanchaVapor').val() == "")?0:parseFloat($('#diferenciaHiloPlanchaVapor').val());
    total = (total == null || total == undefined || total == "") ? 0 : total;
    total = ((diferenciaVapor *100 / medidaVapor)-100);
    if(medidaVapor!=0 && diferenciaVapor!=0){
    	$('#finalHiloMedPlanchaVapor').val(total.toFixed(2)+'%');
    }
    else{
    	$('#finalHiloMedPlanchaVapor').val('0%');
    }
}

//Calcular los porcentajes de manera dinámica
function TramaVapor() {
    var total = 0;	
    total = $('#finalTramaMedPlanchaVapor').val();
    medidaVapor = ($('#medidaTramaPlanchaVapor').val()==null || $('#medidaTramaPlanchaVapor').val() == undefined || $('#medidaTramaPlanchaVapor').val() == "")?0:parseFloat($('#medidaTramaPlanchaVapor').val());
    diferenciaVapor = ($('#diferenciaTramaPlanchaVapor').val()==null || $('#diferenciaTramaPlanchaVapor').val() == undefined || $('#diferenciaTramaPlanchaVapor').val() == "")?0:parseFloat($('#diferenciaTramaPlanchaVapor').val());
    total = (total == null || total == undefined || total == "") ? 0 : total;
    total = ((diferenciaVapor *100 / medidaVapor)-100);
    if(medidaVapor!=0 && diferenciaVapor!=0){
    	$('#finalTramaMedPlanchaVapor').val(total.toFixed(2)+'%');
    }
    else{
    	$('#finalTramaMedPlanchaVapor').val('0%');
    }
}

function HiloLavado() {
    var total = 0;	
    total = $('#finalHiloMedPruebaLavado').val();
    medidaLavado = ($('#medidaHiloPruebaLavado').val()==null || $('#medidaHiloPruebaLavado').val() == undefined || $('#medidaHiloPruebaLavado').val() == "")?0:parseFloat($('#medidaHiloPruebaLavado').val());
    diferenciaLavado = ($('#diferenciaHiloPruebaLavado').val()==null || $('#diferenciaHiloPruebaLavado').val() == undefined || $('#diferenciaHiloPruebaLavado').val() == "")?0:parseFloat($('#diferenciaHiloPruebaLavado').val());
    total = (total == null || total == undefined || total == "") ? 0 : total;
    total = ((diferenciaLavado *100 / medidaLavado)-100);
    if(medidaLavado!=0 && diferenciaLavado!=0){
    	$('#finalHiloMedPruebaLavado').val(total.toFixed(2)+'%');
    }
    else{
    	$('#finalHiloMedPruebaLavado').val('0%');
    }
}

//Calcular los porcentajes de manera dinámica
function TramaLavado() {
    var total = 0;	
    total = $('#finalTramaMedPruebaLavado').val();
    medidaLavado = ($('#medidaTramaPruebaLavado').val()==null || $('#medidaTramaPruebaLavado').val() == undefined || $('#medidaTramaPruebaLavado').val() == "")?0:parseFloat($('#medidaTramaPruebaLavado').val());
    diferenciaLavado = ($('#diferenciaTramaPruebaLavado').val()==null || $('#diferenciaTramaPruebaLavado').val() == undefined || $('#diferenciaTramaPruebaLavado').val() == "")?0:parseFloat($('#diferenciaTramaPruebaLavado').val());
    total = (total == null || total == undefined || total == "") ? 0 : total;
    total = ((diferenciaLavado *100 / medidaLavado)-100);
    if(medidaLavado!=0 && diferenciaLavado!=0){
    	$('#finalTramaMedPruebaLavado').val(total.toFixed(2)+'%');
    }
    else{
    	$('#finalTramaMedPruebaLavado').val('0%');
    }
}