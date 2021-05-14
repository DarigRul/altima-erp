function addComplejidad (){
	$("#complejidad").val(null);
    $("#idLookupComplejidad").val(null);
    $("#abreviacionComplejidad").val(null);
    $("#tituloComplejidad").text('Nueva complejidad');
    $('#alertComplejidad').css('display', 'none');
    $('#modalComplejidad').modal('show'); // abrir
}


function guardarComplejidad(){    
	if ( $("#complejidad").val() == "" || $("#abreviacionComplejidad").val() == "" ){
        $('#alertComplejidad').css('display', '');
    }else{
		$('#alertComplejidad').css('display', 'none');
		$.ajax({
            type: "GET",
            url: "/verificar_duplicado_servicio_cliente",
            data: {
                'Lookup': $("#complejidad").val(),
                'descripcion': $("#abreviacionComplejidad").val(),
                'Tipo': "Complejidad"
            }
        }).done(function(data) {
            if (data == false) {
                $.ajax({
                    type: "POST",
                    url: "/guardar_lookup_servicio_cliente",
                    data: {
                        "_csrf": $('#token').val(), 
                        'idLook': $("#idLookupComplejidad").val(),
                        'nombre': $("#complejidad").val(),
                        'descripcion':$("#abreviacionComplejidad").val(),
                        'clave':'COP',
                        'tipo':'Complejidad'
                    }
    
                }).done(function(data) {
                    $('#modalComplejidad').modal('hide'); // abrir
                    listarComplejidades();
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


function listarComplejidades(){
    $.ajax({
		method: "GET",
		url: "/listar_lookup_servicio_cliente",
		data:{"Tipo":"Complejidad"} ,
		success: (data) => {
        	var tabla = $('#tabladetalleComplejidad').DataTable();      	 
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
						v.descripcionLookup ,
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover"  data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					(rolEditar == 1 ? '<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarComplejidad(this)" idLookup ="'+v.idLookup+'"  lookup="'+v.nombreLookup+'" descripcion="'+v.descripcionLookup+'" data-container="body" data-toggle="popover"  data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="0" letrero="desactivar" tipo="la familia" data-container="body" data-toggle="popover"  data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>': " ")
               		]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
						v.descripcionLookup ,
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover"  data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
                		(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarComplejidad(this)" idLookup ="'+v.idLookup+'"  lookup="'+v.nombreLookup+'" descripcion="'+v.descripcionLookup+'" data-container="body" data-toggle="popover"  data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="1" letrero="activar" tipo="la familia" data-container="body" data-toggle="popover"  data-content="Reactivar"><i class="fas fa-caret-up"></i></button>': " ") 
               		]).node().id ="row";
            	}
           		tabla.draw( false );
        	})
        },
		error: (e) => {}
	})
}
function editarComplejidad(e){
    $('#alertComplejidad').css('display', 'none');
    $("#complejidad").val(e.getAttribute("lookup"));
    $("#idLookupComplejidad").val(e.getAttribute("idLookup"));
    $("#abreviacionComplejidad").val(e.getAttribute("descripcion"));
    $("#tituloComplejidad").text('Editar complejidad');
    $('#modalComplejidad').modal('show'); // abrir
}