function addArreglo() {
    idPrenda  = [];
    idComplejidad=[];

	selectPrendas(null);
    selectComplejidad(null);
	
	var table = $('#tablaPrendas').DataTable();
	var rows = table
		.rows()
		.remove()
		.draw();
	$('#botonGuardarArreglo').attr('onclick', 'guardarArreglo()');
	$('#nombreArreglo').val(null);
    $("#idLookupArreglo").val(null);
    $("#arregloTitulo").text('Nuevo arreglo');

    $('#addArreglo').modal('show');

}

function selectPrendas(id){

    $.ajax({
		method: "GET",
		url: "/listar_lookup_servicio_prendas",
		data:{},
		success: (data) => {
			$("#prendaArreglo").empty();
			$.each(data, function(key, val) {
	    		$('#prendaArreglo').append('<option value="'+val[0]+'" text="'+val[1]+'" >'+val[1]+'</option>');
			})
			$('#prendaArreglo').selectpicker('refresh');
		},
		complete: function() {   
            $('#prendaArreglo').val(id);
            $('#prendaArreglo').selectpicker('refresh');
        },
		error: (e) => {}
	})


}
function selectComplejidad(id){
    $.ajax({
		method: "GET",
		url: "/listar_lookup_servicio_cliente_estatus_1",
		data:{'Tipo':'Complejidad'},
		success: (data) => {
			$("#complejidadArreglo").empty();
			$.each(data, function(key, val) {
	    		$('#complejidadArreglo').append('<option value="' + val.idLookup + '" text="' + val.nombreLookup + '" >'+val.nombreLookup+'</option>');
			})
			$('#complejidadArreglo').selectpicker('refresh');
		},
		complete: function() {   
            $("#boton-add-prenda").prop("disabled", false);
            $('#complejidadArreglo').val(id);
            $('#complejidadArreglo').selectpicker('refresh');
        },
		error: (e) => {}
	})

}

var idPrenda  = [];
var idComplejidad=[];

function agregarPrenda() {
	var t = $('#tablaPrendas').DataTable();
    if ( $('#prendaArreglo').val() != "" && $('#complejidadArreglo').val() != "" ){
        if ( idPrenda.includes($('#prendaArreglo').val()) && idComplejidad.includes($('#complejidadArreglo').val())  ){
            Swal.fire({
                position: 'center',
                icon: 'warning',
                title: 'Repetido',
                showConfirmButton: true
            });
        }
        else{
            idPrenda.push($('#prendaArreglo').val());
            idComplejidad.push($('#complejidadArreglo').val());
            t.row.add([
				'<td>' + $("#prendaArreglo option:selected").attr("text") + '</td>',

				'<td>' + $("#complejidadArreglo option:selected").attr("text") + '</td>',
				'<td><button type="button" onclick="eliminar(this,'+$('#prendaArreglo').val()+','+$('#complejidadArreglo').val()+')"   class="btn btn-sm icon-btn btn-danger text-white btn_remove"><span class="btn-glyphicon spancircle fas fa-times fa-lg img-circle text-danger"></span>Eliminar</button></td>'


			]).draw(false);
            $('#prendaArreglo').val(null);
            $('#prendaArreglo').selectpicker('refresh');
            $('#complejidadArreglo').val(null);
            $('#complejidadArreglo').selectpicker('refresh');  
        }
    }
    else{
        Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Seleccione una prenda',
			showConfirmButton: true
		});
    }
}
function eliminar(t, prendaArreglo, complejidadArreglo, idBase) {

    for( contador=0; contador < idPrenda.length; contador++ ) {
        if (prendaArreglo.toString()==idPrenda[contador].toString() && complejidadArreglo.toString()==idComplejidad[contador].toString()  ){
            idPrenda.splice(contador,1);
            idComplejidad.splice(contador,1);
        }
    }

    if ( idBase!= null){
        $.ajax({
            data: { 'id': idBase },
            url: '/elimiar_MN_arreglo',
            type: 'GET',
    
            success: function (data) {
                
            },
            complete: function () {
                var tabla = $('#tablaProcesos').DataTable();
                var td = t.parentNode;
                var tr = td.parentNode;
                var table = tr.parentNode;
                tabla.row(tr).remove().draw(false);
    
            },
        })
    }
	var tabla = $('#tablaPrendas').DataTable();
	var td = t.parentNode;
	var tr = td.parentNode;
	var table = tr.parentNode;
	tabla.row(tr).remove().draw(false);
}
function guardarArreglo(){
    if ( $('#nombreArreglo').val() == ""){
        Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Ingrese un nombre',
			showConfirmButton: true
		});

    }
    else if( $.isEmptyObject(idPrenda) ){
        Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Seleccione una prenda',
			showConfirmButton: true
		});
    }
    else{
        
		$.ajax({
			type: "GET",
			url: "/verificar_duplicado_servicio_cliente",
			data: {
				'Lookup': $('#nombreArreglo').val(),
				'Tipo': "Arreglo"
			}

		}).done(function (data) {
			if (data == false) {
                //String , String 

				$.ajax({
					type: "POST",
					url: "/guardar_lookup_servicio_cliente_arreglo_prendas",
					data: {
                        "_csrf": $('#token').val(), 
                        'idLook': $("#idLookupArreglo").val(),
                        'nombre': $("#nombreArreglo").val(),
                        'atributo1':idPrenda.toString(),
                        'atributo2':idComplejidad.toString(),
                        'clave':'ARR',
                        'tipo':'Arreglo'
                        
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
					success: function (data) {
						listarArreglos();
						$("#addArreglo").modal("hide");
						Swal.fire({
							position: 'center',
							icon: 'success',
							title: 'Agregado correctamente',
							showConfirmButton: true,
							onBeforeOpen: () => {

							},
						});
					}
				})
				// / window.setTimeout(function(){location.reload()}, 2000);
			}// /fin segundoif
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

function guardarEditarArreglo(){
    if ( $('#nombreArreglo').val() == ""){
        Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Ingrese un nombre',
			showConfirmButton: true
		});

    }
    else if( $.isEmptyObject(idPrenda) ){
        Swal.fire({
			position: 'center',
			icon: 'warning',
			title: 'Seleccione una prenda',
			showConfirmButton: true
		});
    }
    else{
        
		$.ajax({
			type: "GET",
			url: "/validar-arreglo-editar",
			data: {
                'idLookup': $('#idLookupArreglo').val(),
				'nombre': $('#nombreArreglo').val()
			}

		}).done(function (data) {
			if (data == false) {
                //String , String 

				$.ajax({
					type: "POST",
					url: "/guardar_lookup_servicio_cliente_arreglo_prendas",
					data: {
                        "_csrf": $('#token').val(), 
                        'idLook': $("#idLookupArreglo").val(),
                        'nombre': $("#nombreArreglo").val(),
                        'atributo1':idPrenda.toString(),
                        'atributo2':idComplejidad.toString(),
                        'clave':'ARR',
                        'tipo':'Arreglo'
                        
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

					success: function (data) {
						listarArreglos();
						$("#addArreglo").modal("hide");
						Swal.fire({
							position: 'center',
							icon: 'success',
							title: 'Agregado correctamente',
							showConfirmButton: true,
							onBeforeOpen: () => {

							},
						});
					}
				})
				// / window.setTimeout(function(){location.reload()}, 2000);
			}// /fin segundoif
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
function listarArreglos(){
    $.ajax({
		method: "GET",
		url: "/listar_lookup_servicio_cliente",
		data:{"Tipo":"Arreglo"} ,
		success: (data) => {
        	var tabla = $('#tableArreglos').DataTable();      	 
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
						'<button class="btn btn-primary btn-circle btn-sm popoverxd" onclick="verPrendasArreglo('+v.idLookup+')"  data-container="body" data-toggle="popover" data-placement="top" data-content="Prendas"><i class="fas fa-tshirt"></i></button>',
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					(rolEditar == 1 ? '<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarArreglo(this)" idLookup ="'+v.idLookup+'"  lookup="'+v.nombreLookup+'" descripcion="'+v.descripcionLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="0" letrero="desactivar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>': " ")
               		]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
						'<button class="btn btn-primary btn-circle btn-sm popoverxd" onclick="verPrendasArreglo('+v.idLookup+')"  data-container="body" data-toggle="popover" data-placement="top" data-content="Prendas"><i class="fas fa-tshirt"></i></button>',
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
                		(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarArreglo(this)" idLookup ="'+v.idLookup+'"  lookup="'+v.nombreLookup+'" descripcion="'+v.descripcionLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="1" letrero="activar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Reactivar"><i class="fas fa-caret-up"></i></button>': " ") 
               		]).node().id ="row";
            	}
           		tabla.draw( false );
        	})
        },
		error: (e) => {}
	})
}
function verPrendasArreglo(id){
    
    var tabla = $('#tableListaProcesos').DataTable();
    var rows = tabla
        .rows()
        .remove()
        .draw();
    $('#infoPrendas').modal('show');
	$.ajax({
		method: "GET",
		url: "/listar_prendas_arreglo",
		data: {
			"id": id
		},
		success: (data) => {
		
			for (i in data) {
				tabla.row.add([data[i][1],data[i][2] ]).node().id = "row";
				tabla.draw(false);
			}
		},
		error: (e) => {
		}
	})

}

function editarArreglo(e){
    idPrenda  = [];
    idComplejidad=[];

	selectPrendas(null);
    selectComplejidad(null);
	
	var table = $('#tablaPrendas').DataTable();
	var rows = table
		.rows()
		.remove()
		.draw();
	$('#botonGuardarArreglo').attr('onclick', 'guardarEditarArreglo()');
	$('#nombreArreglo').val(e.getAttribute('lookup'));
    $("#arregloTitulo").text('Editar arreglo');
    $("#idLookupArreglo").val(e.getAttribute("idLookup"));

    var tabla = $('#tablaPrendas').DataTable();
    var rows = tabla
        .rows()
        .remove()
        .draw();

	$.ajax({
		method: "GET",
		url: "/listar_prendas_arreglo",
		data: {"id": e.getAttribute("idLookup")},
		success: (data) => {
			for (i in data) {
                idPrenda.push(data[i][3].toString());
                idComplejidad.push(data[i][4].toString());
				tabla.row.add([
					data[i][1],
					data[i][2],
					'<td><button type="button" onclick="eliminar(this,'+data[i][3]+','+data[i][4]+','+data[i][0]+')"   class="btn btn-sm icon-btn btn-danger text-white btn_remove"><span class="btn-glyphicon spancircle fas fa-times fa-lg img-circle text-danger"></span>Eliminar</button></td>'

				]).node().id = "row";
				tabla.draw(false);
			}
		},
		error: (e) => {
		}
	})
    $('#addArreglo').modal('show');
    
}
