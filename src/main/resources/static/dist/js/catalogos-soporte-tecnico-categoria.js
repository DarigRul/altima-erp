function addCategoria(){
	$("#categoria").val(null);
    $('#idLookupCategoria').val(null);

    $('#alertCategoria').css('display', 'none');
    $("#tituloCategoria").text('Nuevo categoría');
    $('#modalAgregarCategoria').modal('show'); // abrir
}


function guardarCategoria(){    
	if ( $("#categoria").val() == ""  || $("#categoria").val() ==""){
        $('#alertCategoria').css('display', '');
    }else{
		$('#alertCategoria').css('display', 'none');
		$.ajax({
            type: "GET",
            url: "/verificar_duplicado_soporte_tecnico",
            data: {
                'Lookup': $("#categoria").val(),
                'Tipo': "Categoria",
            }
        }).done(function(data) {
            if (data == false) {
                $.ajax({
                    type: "POST",
                    url: "/guardar_lookup_soporte_tecnico",
                    data: {
                        "_csrf": $('#token').val(), 
                        'idLook': $("#idLookupCategoria").val(),
                        'nombre': $("#categoria").val(),
                        'clave':'CAT',
                        'tipo':'Categoria'
                    }
    
                }).done(function(data) {
                    $('#modalAgregarCategoria').modal('hide'); // abrir
                    listarCategoria();
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



function listarCategoria(){

    $.ajax({
		method: "GET",
		url: "/listar_lookup_soporte_tecnico",
		data:{"Tipo":"Categoria"} ,
		success: (data) => {
        	var tabla = $('#tabladetalleCategoria').DataTable();      	 
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
    					(rolEditar == 1 ? '<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarcategoria(this)" idLookup ="'+v.idLookup+'"  lookup="'+v.nombreLookup+'" descripcion="'+v.descripcionLookup+'" data-container="body" data-toggle="popover"  data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="0" letrero="desactivar" tipo="la familia" data-container="body" data-toggle="popover"  data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>': " ")
               		]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover"  data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
                		(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarcategoria(this)" idLookup ="'+v.idLookup+'"  lookup="'+v.nombreLookup+'" descripcion="'+v.descripcionLookup+'" data-container="body" data-toggle="popover"  data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="1" letrero="activar" tipo="la familia" data-container="body" data-toggle="popover"  data-content="Reactivar"><i class="fas fa-caret-up"></i></button>': " ") 
               		]).node().id ="row";
            	}
           		tabla.draw( false );
        	})
        },
		error: (e) => {}
	})
}

function editarcategoria(e){

    $("#categoria").val(e.getAttribute("lookup"));
    $('#idLookupCategoria').val(e.getAttribute("idLookup"));

    $('#alertCategoria').css('display', 'none');
    $("#tituloCategoria").text('Editar categoría');
    $('#modalAgregarCategoria').modal('show'); // abrir

}