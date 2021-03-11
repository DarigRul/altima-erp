$(document).ready(function () {

	listarTallas();
	listarTipos();
    listarComplejidades();
    listarArreglos();
    listarEspecificaciones();
    listarActividades();
	listarMaterial();
});
function addTiposIngreso(){
	$("#tipoIngreso").val(null);
    $("#idLookupTipoIngreso").val(null);
    $("#diasTipoIngreso").val(null);
    $("#tituloTiposIngreso").text('Nuevo tipo de ingreso');
    $('#alertTiposIngreso').css('display', 'none');
    $('#modalTiposIngreso').modal('show'); // abrir
}

function guardarTiposIngreso(){    
	if ( $("#tipoIngreso").val() == "" || $("#diasTipoIngreso").val() == "" ||  $("#diasTipoIngreso").val() <=0 ){
        $('#alertTiposIngreso').css('display', '');
    }else{
		$('#alertTiposIngreso').css('display', 'none');
		$.ajax({
            type: "GET",
            url: "/verificar_duplicado_servicio_cliente",
            data: {
                'Lookup': $("#tipoIngreso").val(),
                'descripcion': $("#diasTipoIngreso").val(),
                'Tipo': "Tipo ingreso"
            }
        }).done(function(data) {
            if (data == false) {
                $.ajax({
                    type: "POST",
                    url: "/guardar_lookup_servicio_cliente",
                    data: {
                        "_csrf": $('#token').val(), 
                        'idLook': $("#idLookupTipoIngreso").val(),
                        'nombre': $("#tipoIngreso").val(),
                        'descripcion':$("#diasTipoIngreso").val(),
                        'clave':'TIP',
                        'tipo':'Tipo ingreso'
                    }
    
                }).done(function(data) {
                    $('#modalTiposIngreso').modal('hide'); // abrir
                    listarTipos();
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


function listarTipos (){
    $.ajax({
		method: "GET",
		url: "/listar_lookup_servicio_cliente",
		data:{"Tipo":"Tipo ingreso"} ,
		success: (data) => {
        	var tabla = $('#tabladetalleTiposIngreso').DataTable();      	 
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
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					(rolEditar == 1 ? '<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarTipos(this)" idLookup ="'+v.idLookup+'"  lookup="'+v.nombreLookup+'" descripcion="'+v.descripcionLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="0" letrero="desactivar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>': " ")
               		]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
						v.descripcionLookup ,
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
                		(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarTipos(this)" idLookup ="'+v.idLookup+'"  lookup="'+v.nombreLookup+'" descripcion="'+v.descripcionLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="1" letrero="activar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Reactivar"><i class="fas fa-caret-up"></i></button>': " ") 
               		]).node().id ="row";
            	}
           		tabla.draw( false );
        	})
        },
		error: (e) => {}
	})
}

function editarTipos(e){
    $('#alertTiposIngreso').css('display', 'none');
    $("#tipoIngreso").val(e.getAttribute("lookup"));
    $("#idLookupTipoIngreso").val(e.getAttribute("idLookup"));
    $("#diasTipoIngreso").val(e.getAttribute("descripcion"));
    $("#tituloTiposIngreso").text('Editar tipo de ingreso');
    $('#modalTiposIngreso').modal('show'); // abrir
}


function cambioEstatus (e){
    Swal.fire({
        title: '¿Deseas '+e.getAttribute("letrero")+' este catalago?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmar'
      }).then((result) => {
        if (result.value && e.getAttribute("idLookup") != null) {
            $.ajax({
                type: "POST",
                url: "/cambio_estatus_servicio_cliente",
                data: {
                    "_csrf": $('#token').val(),
                    'idLookup': e.getAttribute("idLookup"),
                    'estatus':e.getAttribute("estatus")
                },
                success: function (data) {
                    switch (data){
                   
                        case "Tipo ingreso":listarTipos();
                        break;
                        case "Complejidad":listarComplejidades();
                        break;
                        case "Arreglo":listarArreglos();
                        break;
                        case "Especificación":listarEspecificaciones();
                        break;
                        case "Actividad":listarActividades();
                        break;
                        case "Material":listarMaterial();
                        break;
                        
                    }
                    
                },
                complete: function () {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Estatus actualizado',
                        showConfirmButton: true
                    })
    
                }
                
            });
              
        }
      });
}