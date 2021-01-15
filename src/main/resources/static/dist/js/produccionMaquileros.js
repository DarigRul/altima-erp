function capitalizarPrimeraLetra(e) {
	var palabra = e.value;
	if(!e.value) return;
	var mayuscula = palabra.substring(0,1).toUpperCase();
	if (palabra.length > 0) {
	  var minuscula = palabra.substring(1);
	}
	e.value = mayuscula.concat(minuscula);
}

$(function() {
    $("#cpMaquilero").inputmask({"mask": "99999"});  
    $("#telefono").inputmask({"mask": "(999)999 99 99"});
    $("#numeroExtMaquilero").inputmask({"mask": "9{1,10}"});
    $("#produccionMax").inputmask({"mask": "9{1,10}"});
     
    
})

$('#SN').click(function(){
    if($(this).prop("checked") == true){
        $("#numeroExtMaquilero").prop('value', '');
        $("#numeroIntMaquilero").prop('value', '');
        $("#numeroExtMaquilero").prop('disabled', true);
        $("#numeroIntMaquilero").prop('disabled', true);
    }
    else if($(this).prop("checked") == false){
        $("#numeroExtMaquilero").prop('disabled', false);
        $("#numeroIntMaquilero").prop('disabled', false);
    }
});
function limpiarformMaquileros(){
    
    $('#nombreMaquilero').val(null);
    $('#idMaquilero').val(null);
    $('#calleMaquilero').val(null);
    $('#SN').prop('checked', false);
    $("#numeroExtMaquilero").prop('disabled', false);
    $("#numeroIntMaquilero").prop('disabled', false);
    $('#numeroExtMaquilero').val(null);
    $('#numeroIntMaquilero').val(null);
    $('#estadoMaquilero').val(null);
    $('#estadoMaquilero').selectpicker('refresh');
    $('#municipioMaquilero').val(null);
    $('#coloniaMaquilero').val(null);
    $('#cpMaquilero').val(null);
    $('#idUbicacion').val(null);
    $('#idUbicacion').selectpicker('refresh');
    $('#produccionMax').val(null);
    $('#tipo').val(null);
    $('#tipo').selectpicker('refresh');
    $('#descripcion').val(null);
    $('#telefono').val(null);
}
function llenarSelectUbicaciones(idValor){
    $.ajax({
		method: "GET",
		url: "/listar_ubucaciones_activas",
		data:{} ,
		success: (data) => {
            $("#idUbicacion").empty();
            $(data).each(function(i, v){ 
                if ( v.idLookup == idValor){
                    $('#idUbicacion').append('<option  value="'+v.idLookup+'" selected    >' + v.nombreLookup+ '</option>');
                }
                else{
                    $('#idUbicacion').append('<option  value="'+v.idLookup+'">' + v.nombreLookup+ '</option>');
                }
                
            })
            $('#idUbicacion').selectpicker('refresh');
       	
		}, complete: function() {   
            $('#idUbicacion').val(idValor);
            $('#idUbicacion').selectpicker('refresh');
         
        },
		error: (e) => {

		}
	})

}
  function agregarMaquileros(){
    limpiarformMaquileros();
    llenarSelectUbicaciones(null);
	$('#addMaquilero').modal('show'); // abrir

}

function guardarMaquilero(){
    
    if (    $('#nombreMaquilero').val() == "" ||
            $('#calleMaquilero').val() == "" ||
            $('#estadoMaquilero').val() == "" ||
            $('#municipioMaquilero').val() == "" ||
            $('#coloniaMaquilero').val() == "" ||
            $('#cpMaquilero').val() == "" ||
            $('#idUbicacion').val() == "" ||
            $('#produccionMax').val() == "" ||
            $('#tipo').val() == ""||
            $('#telefono').val() == "" ){
    
                Swal.fire({
					position: 'center',
					icon: 'error',
					title: 'Complete el formulario!',
					showConfirmButton: true
				});

    }
    else if (! $('#SN').prop('checked') && $('#numeroExtMaquilero').val()  == ""){
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Complete el formulario!',
            showConfirmButton: true
        });
    }
    else{
        
        $.ajax({
            method: "GET",
            url: "/guardar_maquilero",
            data:{
                'nombreMaquilero':$('#nombreMaquilero').val(),
                'idMaquilero':$('#idMaquilero').val(),
                'calleMaquilero':$('#calleMaquilero').val(),
                'numeroExtMaquilero':$('#numeroExtMaquilero').val(),
                'numeroIntMaquilero':$('#numeroIntMaquilero').val(),
                'estadoMaquilero':$('#estadoMaquilero').val(),
                'municipioMaquilero':$('#municipioMaquilero').val(),
                'coloniaMaquilero':$('#coloniaMaquilero').val(),
                'cpMaquilero':$('#cpMaquilero').val(),
                'idUbicacion':$('#idUbicacion').val(),
                'produccionMax':$('#produccionMax').val(),
                'tipo':$('#tipo').val(),
                'descripcion':$('#descripcion').val(),
                'telefono':$('#telefono').val()
            } ,
            success: (data) => {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Insertado correctamente',
                    showConfirmButton: false,
                    timer: 1250
                })
                listarMaquila();
                $('#addMaquilero').modal('toggle'); 
               
               
            },
            error: (e) => {
    
            }
        })

    }
}

function editarMaquileros(id){
    limpiarformMaquileros();
    $.ajax({
        method: "GET",
        url: "/editar_maquilero",
        data:{
            'idMaquilero':id
        
        } ,
        success: (data) => {
            
            $('#nombreMaquilero').val(data.nombre);
            $('#idMaquilero').val(data.idMaquilador);
            $('#calleMaquilero').val(data.calle);
            if (data.numeroExt =="" || data.numeroEx== null ){
                $('#SN').prop('checked', true);
                $("#numeroExtMaquilero").prop('disabled', true);
                $("#numeroIntMaquilero").prop('disabled', true);
                
            }
            $('#numeroExtMaquilero').val(data.numeroExt);
            $('#numeroIntMaquilero').val(data.numeroInt);
            $('#estadoMaquilero').val(data.estado);
            $('#estadoMaquilero').selectpicker('refresh');
            $('#municipioMaquilero').val(data.municipio);
            $('#coloniaMaquilero').val(data.colonia);
            $('#cpMaquilero').val(data.codigoPostal);
          
            llenarSelectUbicaciones(data.idUbicacion);
            $('#produccionMax').val(data.produccionMaxima);
            $('#tipo').val(data.tipo);
            $('#tipo').selectpicker('refresh');
            $('#descripcion').val(data.descripcion);
            $('#telefono').val(data.telefono);
            $('#addMaquilero').modal('show'); // abrir
          
           
           
        },
        error: (e) => {

        }
    })
}
function bajaMaquileros(id){
	Swal.fire({
		  title: '¿Deseas dar de baja el maquilero?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar'
		}).then((result) => {
		  if (result.value && id != null) {

	            $.ajax({
	                type: "POST",
	                url: "/baja-catalogo-produccion-maquilero",
	                data: {
	                    "_csrf": $('#token').val(),
	                    'id': id
	                }

	            }).done(function(data) {

	            	listarMaquila();
	            });
	            Swal.fire({
	                position: 'center',
	                icon: 'success',
	                title: 'Dado de baja correctamente',
	                showConfirmButton: false,
	                timer: 1250
	            })
		  }
		});
}
function altaMaquileros(id){
	Swal.fire({
		  title: '¿Deseas reactivar el maquilero?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar'
		}).then((result) => {
		  if (result.value && id != null) {
			  $.ajax({
	                type: "POST",
	                url: "/reactivar-catalogo-produccion-maquilero",
	                data: {
	                    "_csrf": $('#token').val(),
	                    'idcatalogo': id
	                }

	            }).done(function(data) {

	            	listarMaquila();
	            });
	            Swal.fire({
	                position: 'center',
	                icon: 'success',
	                title: 'Reactivado correctamente',
	                showConfirmButton: false,
	                timer: 1250
	            })
		  }
		});
}

function listarMaquila() {

	$.ajax({
		method: "GET",
		url: "/listar_maquileros",
		data:{
		} ,
		success: (data) => {

        	var tabla = $('#tableMaquila').DataTable();
        	
        
        	 
        	tabla.clear();
        	    
            $(data).each(function(i, v){ // indice, valor
            	var fecha;
        		var actualizo;
            	if (v[10] == null && v[11] == null ){
            		fecha='';
            		actualizo='';
            	}else{
            		actualizo=v[10];
            		fecha=v[11];
            	}
            	
            	if (v[7] == 1 ){
            		tabla.row.add([	
                		v[1] ,//idtext
                		v[2] ,//nombre
                        v[3] ,//tipo
                        v[4],//des
                        v[5],//produ
                        '<button class="btn btn-primary btn-circle btn-sm popoverxd" onclick="mostrarUbicacion('+v[0]+')"  data-container="body" data-toggle="popover" data-placement="top" data-content="Ubicación"> <i class="fas fa-map-marker-alt"></i></button>',//direccion, 
                        v[6],//ubicacion;
                        
                        
                        
                    
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v[8] +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v[9]+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarMaquileros('+v[0]+')"  data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>'+
    					'<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="bajaMaquileros('+v[0]+')" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>'
            
               		 ]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v[1] ,//idtext
                		v[2] ,//nombre
                        v[3] ,//tipo
                        v[4],//des
                        v[5],//produ
                        '<button class="btn btn-primary btn-circle btn-sm popoverxd" onclick="mostrarUbicacion('+v[0]+')"  data-container="body" data-toggle="popover" data-placement="top" data-content="Ubicación"> <i class="fas fa-map-marker-alt"></i></button>',//direccion, 
                        v[6],//ubicacion;
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v[8] +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v[9]+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarMaquileros('+v[0]+')"  data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>'+
    					'<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="altaMaquileros('+v[0]+')" data-container="body" data-toggle="popover" data-placement="top" data-content="Reactivar"><i class="fas fa-caret-up"></i></button>'
            
               		 ]).node().id ="row";
            	}
            	
           	tabla.draw( false );
            	//fila = '<tr> <td>'+v[1]+'</td>  <td >'+ v[2] +'</td> <td >'+ v[3] +'</td> <td >'+ v[4] +'</td>  </tr>' ;
            	
            	
            	
            })
            
       	
		},
		error: (e) => {

		}
	})
}


function mostrarUbicacion(id){

    $.ajax({
        method: "GET",
        url: "/editar_maquilero",
        data:{
            'idMaquilero':id
        
        } ,
        success: (data) => {
            var numEx;
            var numIn;
            if ( data.numeroExt =="" || data.numeroEx== null){
                numEx="Sin numero";
                numIn="Sin numero";
            }else{
                numEx=data.numeroExt;
                numIn=data.numeroInt;
            }
            Swal.fire({
                title: '<strong>Ubicación</strong>',
                html:
                '<p> Maquilero:<strong>'+data.nombre+'</strong></p>'+
                '<p> Estado:<strong>'+data.estado+'</strong></p>'+
                '<p> Municipio:<strong>'+data.municipio+'</strong></p>'+
                '<p> Colonia:<strong>'+data.colonia+'</strong></p>'+
                '<p>Codigo postal:<strong>'+data.codigoPostal+'</strong></p>'+
                '<p> Calle:<strong>'+data.calle+'</strong></p>'+
                '<p> Num. Exterior:<strong>'+numEx+'</strong></p>'+
                '<p> Num. Interno:<strong>'+numIn+'</strong></p>',
                showCloseButton: true,
                focusConfirm: false,
                confirmButtonText:
                  '<i class="fa fa-thumbs-up"></i> Echo!'
            
              })
            
            /*$('#nombreMaquilero').val(data.nombre);
            $('#idMaquilero').val(data.idMaquilador);
            $('#calleMaquilero').val(data.calle);
            if (data.numeroExt =="" || data.numeroEx== null ){
                $('#SN').prop('checked', true);
                $("#numeroExtMaquilero").prop('disabled', true);
                $("#numeroIntMaquilero").prop('disabled', true);
                
            }
            $('#numeroExtMaquilero').val(data.numeroExt);
            $('#numeroIntMaquilero').val(data.numeroInt);
            $('#estadoMaquilero').val(data.estado);
            $('#estadoMaquilero').selectpicker('refresh');
            $('#municipioMaquilero').val(data.municipio);
            $('#coloniaMaquilero').val(data.colonia);
            $('#cpMaquilero').val(data.codigoPostal);
            $('#idUbicacion').val(data.idUbicacion);
            $('#idUbicacion').selectpicker('refresh');
            $('#produccionMax').val(data.produccionMaxima);
            $('#tipo').val(data.tipo);
            $('#tipo').selectpicker('refresh');
            $('#descripcion').val(data.descripcion);
            $('#telefono').val(data.telefono);*/
          
           
           
        },
        error: (e) => {

        }
    })
   
}