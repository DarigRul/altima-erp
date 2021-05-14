
function addTalla(){
	$("#talla").val(null);
    listarGeneros(null);
	$('#idLookuptalla').val(null);
    $("#tituloTallas").text('Nueva talla');
    $('#modalAgregarTallas').modal('show'); // abrir
}
function listarGeneros(id){
	$.ajax({
		method: "GET",
		url: "/listar",
		data:{"Tipo":"Familia Genero"},
		success: (data) => {
			$("#generoTalla").empty();
			$.each(data, function(key, val) {
	    		$('#generoTalla').append('<option value="' + val.idLookup + '">'+val.nombreLookup+'</option>');
			})
			$('#generoTalla').selectpicker('refresh');
		},
		complete: function() {   
            $('#generoTalla').val(id);
            $('#generoTalla').selectpicker('refresh');
        },
		error: (e) => {}
	})
}
function guardarTallas(){
	if ( $("#talla").val() == "" || $("#generoTalla").val() == ""  || $("#generoTalla").val() <0   ){
        $('#alertTallas').css('display', '');
    }else{
		$('#alertTallas').css('display', 'none');
		var talla = document.getElementById("talla").value;
		var select = document.getElementById("generoTalla");
		var id_genero = select.value;
		var genero = select.options[select.selectedIndex].innerText;
		$.ajax({
            type: "GET",
            url: "/verificar-duplicado-produccion",
            data: {
				'Lookup': talla,
				'Tipo': "Talla",
				'Atributo1': id_genero,
				'Atributo2': genero	
            }
        }).done(function(data) {
            if (data == false) {
                $.ajax({
                    type: "POST",
                    url: "/guardar-catalogo-produccion-tallas",
                    data: {
						'idProcesoTalla':$('#idLookuptalla').val(),
                        "_csrf": $('#token').val(),
						'num_talla': talla,
						'genero': genero,
						'id_genero': id_genero
                    }
    
                }).done(function(data) {
                    $('#modalAgregarTallas').modal('hide'); // abrir
                    listarTallas();
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
function listarTallas (){
    $.ajax({
		method: "GET",
		url: "/listar-catalogo-produccion",
		data:{"Tipo":"Talla"} ,
		success: (data) => {
        	var tabla = $('#tabladetalleTalla').DataTable();      	 
        	tabla.clear();
            $(data).each(function(i, v){ // indice, valor
            	var fecha;
        		var actualizo;
            	if (v.actualizadoPor == null && v.ultimaFechaModificacion == null ){
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
						v.atributo2 ,
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover"  data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					(rolEditar == 1 ? '<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarTalla(this)" idLookup ="'+v.idLookup+'"  talla="'+v.nombreLookup+'" idGenero="'+v.atributo1+'" data-container="body" data-toggle="popover"  data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="bajarTalla('+v.idLookup+')" idLookup ="'+v.idLookup+'" estatus="0" letrero="desactivar" tipo="la familia" data-container="body" data-toggle="popover"  data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>': " ")
               		]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
						v.atributo2 ,
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover"  data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
                		(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarTalla(this)" idLookup ="'+v.idLookup+'"  talla="'+v.nombreLookup+'" idGenero="'+v.atributo1+'" data-container="body" data-toggle="popover"  data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="subirTalla('+v.idLookup+')" idLookup ="'+v.idLookup+'" estatus="1" letrero="activar" tipo="la familia" data-container="body" data-toggle="popover"  data-content="Reactivar"><i class="fas fa-caret-up"></i></button>': " ") 
               		]).node().id ="row";
            	}
           		tabla.draw( false );
        	})
        },
		error: (e) => {}
	})
}
function editarTalla(e){
	$("#talla").val(e.getAttribute("talla"));
    listarGeneros(e.getAttribute("idGenero"));
	$('#idLookuptalla').val(e.getAttribute("idLookup"));
    $("#tituloTallas").text('Editar talla');
    $('#modalAgregarTallas').modal('show'); // abrir

}


function bajarTalla(id) {
	Swal.fire({
		title: '¿Deseas dar de baja este catalago?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Confirmar'
	}).then((result) => {
		if (result.value && id != null) {

			$.ajax({
				type: "POST",
				url: "/baja-catalogo-produccion",
				data: {
					"_csrf": $('#token').val(),
					'id': id
				}

			}).done(function (data) {

				listarTallas();
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


function subirTalla(id) {
	Swal.fire({
		title: '¿Deseas reactivar este catalago?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Confirmar'
	}).then((result) => {
		if (result.value && id != null) {
			$.ajax({
				type: "POST",
				url: "/reactivar-catalogo-produccion",
				data: {
					"_csrf": $('#token').val(),
					'idcatalogo': id
				}

			}).done(function (data) {

				listarTallas();
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