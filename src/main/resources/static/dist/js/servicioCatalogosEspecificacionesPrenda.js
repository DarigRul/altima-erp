function addEspecificacionesPrenda(){
	$("#EspecificacionesPrenda").val(null);
	$('#idLookupEspecificacionesPrenda').val(null);
    $('#alertEspecificacionesPrenda').css('display', 'none');
    $("#tituloEspecificacionesPrenda").text('Nueva especificación');
    $('#modalAgregarEspecificacionesPrenda').modal('show'); // abrir
}

function guardarEspecificacionesPrenda(){    
	if ( $("#EspecificacionesPrenda").val() == "" ){
        $('#alertEspecificacionesPrenda').css('display', '');
    }else{
		$('#alertEspecificacionesPrenda').css('display', 'none');
		$.ajax({
            type: "GET",
            url: "/verificar_duplicado_servicio_cliente",
            data: {
                'Lookup': $("#EspecificacionesPrenda").val(),
                'Tipo': "Especificación"
            }
        }).done(function(data) {
            if (data == false) {
                $.ajax({
                    type: "POST",
                    url: "/guardar_lookup_servicio_cliente",
                    data: {
                        "_csrf": $('#token').val(), 
                        'idLook': $("#idLookupEspecificacionesPrenda").val(),
                        'nombre': $("#EspecificacionesPrenda").val(),
                        'clave':'ESP',
                        'tipo':'Especificación'
                    }
    
                }).done(function(data) {
                    $('#modalAgregarEspecificacionesPrenda').modal('hide'); // abrir
                    listarEspecificaciones();
                });
                Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Insertado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                    // / window.setTimeout(function(){location.reload()}, 2000);
            } // /fin segundoif
            else {
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: 'registro duplicado no se ha insertado',
                    showConfirmButton: false,
                    timer: 1250
                })
    
            }
        });
	}
}

function listarEspecificaciones(){

    $.ajax({
		method: "GET",
		url: "/listar_lookup_servicio_cliente",
		data:{"Tipo":"Especificación"} ,
		success: (data) => {
        	var tabla = $('#tabladetalleEspecificacionesPrenda').DataTable();      	 
        	tabla.clear();
            $(data).each(function(i, v){ // indice, valor
            	var fecha;
        		var actualizo;
            	if (v.actualizadoPor == null || v.ultimaFechaModificacion == null ){
            		fecha='';
            		actualizo='';
            	}else{
            		actualizo=v.actualizadoPor;
            		fecha=v.ultimaFechaModificacion;
            	}
            	if (v.estatus == 1 ){
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover"  data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					(rolEditar == 1 ? '<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarEspecificacion(this)" idLookup ="'+v.idLookup+'"  lookup="'+v.nombreLookup+'" descripcion="'+v.descripcionLookup+'" data-container="body" data-toggle="popover"  data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="0" letrero="desactivar" tipo="la familia" data-container="body" data-toggle="popover"  data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>': " ")
               		]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover"  data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
                		(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarEspecificacion(this)" idLookup ="'+v.idLookup+'"  lookup="'+v.nombreLookup+'" descripcion="'+v.descripcionLookup+'" data-container="body" data-toggle="popover"  data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="1" letrero="activar" tipo="la familia" data-container="body" data-toggle="popover"  data-content="Reactivar"><i class="fas fa-caret-up"></i></button>': " ") 
               		]).node().id ="row";
            	}
           		tabla.draw( false );
        	})
        },
		error: (e) => {}
	})
}
function editarEspecificacion(e){
    $("#EspecificacionesPrenda").val(e.getAttribute("lookup"));
	$('#idLookupEspecificacionesPrenda').val(e.getAttribute("idLookup"));
    $('#alertEspecificacionesPrenda').css('display', 'none');
    $("#tituloEspecificacionesPrenda").text('Editar especificación');
    $('#modalAgregarEspecificacionesPrenda').modal('show'); // abrir
}