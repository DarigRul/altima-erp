$(document).ready(function () {

	listarEquipos();
    listarCategoria();
});
function addEquipos(){
	$("#comunEquipo").val(null);
    $('#comunEquipo').selectpicker('refresh');
	$('#idLookupEquipo').val(null);
    $('#tipoEquipo').val(null);

    $('#alertEquipo').css('display', 'none');
    $("#tituloEquipo").text('Nuevo equipo');
    $('#modalAgregarEquipos').modal('show'); // abrir
}

function guardarEquipo(){    
	if ( $("#comunEquipo").val() == ""  || $("#tipoEquipo").val() ==""){
        $('#alertEquipo').css('display', '');
    }else{
		$('#alertEquipo').css('display', 'none');
		$.ajax({
            type: "GET",
            url: "/verificar_duplicado_soporte_tecnico",
            data: {
                'Lookup': $("#tipoEquipo").val(),
                'Tipo': "Equipo",
                'descripcion': $("#comunEquipo").val()
            }
        }).done(function(data) {
            if (data == false) {
                $.ajax({
                    type: "POST",
                    url: "/guardar_lookup_soporte_tecnico",
                    data: {
                        "_csrf": $('#token').val(), 
                        'idLook': $("#idLookupEquipo").val(),
                        'nombre': $("#tipoEquipo").val(),
                        'descripcion': $("#comunEquipo").val(),
                        'clave':'EQU',
                        'tipo':'Equipo'
                    }
    
                }).done(function(data) {
                    $('#modalAgregarEquipos').modal('hide'); // abrir
                    listarEquipos();
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

function listarEquipos(){

    $.ajax({
		method: "GET",
		url: "/listar_lookup_soporte_tecnico",
		data:{"Tipo":"Equipo"} ,
		success: (data) => {
        	var tabla = $('#tablaDetalleEquipo').DataTable();      	 
        	tabla.clear();
            console.log(data);
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
    					(rolEditar == 1 ? '<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarEquipo(this)" idLookup ="'+v.idLookup+'"  lookup="'+v.nombreLookup+'" descripcion="'+v.descripcionLookup+'" data-container="body" data-toggle="popover"  data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="0" letrero="desactivar" tipo="la familia" data-container="body" data-toggle="popover"  data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>': " ")
               		]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
                        v.descripcionLookup ,
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover"  data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
                		(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarEquipo(this)" idLookup ="'+v.idLookup+'"  lookup="'+v.nombreLookup+'" descripcion="'+v.descripcionLookup+'" data-container="body" data-toggle="popover"  data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="1" letrero="activar" tipo="la familia" data-container="body" data-toggle="popover"  data-content="Reactivar"><i class="fas fa-caret-up"></i></button>': " ") 
               		]).node().id ="row";
            	}
           		tabla.draw( false );
        	})
        },
		error: (e) => {}
	})
}
function editarEquipo(e){

    $("#comunEquipo").val(e.getAttribute("descripcion"));
    $('#comunEquipo').selectpicker('refresh');
	$('#idLookupEquipo').val(e.getAttribute("idLookup"));
    $('#tipoEquipo').val(e.getAttribute("lookup"));

    $('#alertEquipo').css('display', 'none');
    $("#tituloEquipo").text('Editar equipo');
    $('#modalAgregarEquipos').modal('show'); // abrir
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
                url: "/cambio_estatus_soporte_tecnico",
                data: {
                    "_csrf": $('#token').val(),
                    'idLookup': e.getAttribute("idLookup"),
                    'estatus':e.getAttribute("estatus")
                },
                success: function (data) {
                    switch (data){
                   
                        case "Equipo":listarEquipos();
                        break;
                        case "Categoria":listarCategoria();
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