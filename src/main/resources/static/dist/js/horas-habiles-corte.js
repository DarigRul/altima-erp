
$(function() {      
    Inputmask('datetime', {'inputFormat':'HH:MM'}).mask("#horasHombre");
    Inputmask('datetime', {'inputFormat':'HH:MM'}).mask("#horasAdeudo");
})


function ver() {
    $("#verFechas").modal("show");
    $('#fechaInicio').val(null);
    $('#fechaFin').val(null);
}

function buscarFechas(){
    if ( $('#fechaInicio').val() == "" || $('#fechaFin').val() == ""){
        Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'Por favor complete el formulario!'
		})
    }
    else{
        var t = $('#table-horas').DataTable();
	    var rows = t
        .rows()
        .remove()
	    .draw(); 
	$.ajax({
		type: "GET",
		url: "/get_buscar_fechas_calendario",
        data: { 
            'fechaInicio': $('#fechaInicio').val(),
            'fehaFin': $('#fechaFin').val()
        },
		success: (data) => {
            for (i in data) {
                    
                t.row.add( [
                    '<p>'+data[i][1]+'</p>',
                    '<p id="hour_men-'+data[i][0]+'">'+data[i][2]+'</p>',
                    '<p id="hour_adeudo-'+data[i][0]+'">'+data[i][3]+'</p>',
                    '<p id="hour_habi-'+data[i][0]+'">'+data[i][4]+'</p>',
                    
                    '<td>  <button  onclick="editar(' + data[i][0]+ ')"  class="btn btn-warning btn-circle btn-sm"  data-toggle="modal" data-target="#detalleTelas" ><i class="fas fa-pen"></i></button> </td>  '
                
        
                ] ).draw( false );
            }
            $('#verFechas').modal('toggle');
			
		},
		error: (e) => {
			console.log(e);
		}
	});
    }
}

function editar (id){
	$.ajax({
		type: "GET",
		url: "/get_calendario_id",
        data: { 
            'id': id
        },
		success: (data) => {
            console.log(data)
            
            $('#idCalendario').val(data.idCalendarioFecha);
            $('#horasHombre').val(data.hombre);
            $('#horasAdeudo').val(data.adeudo);
            $('#horasObservaciones').val(data.observacion);
            $("#detallesFecha").modal("show");
            
		},
		error: (e) => {
			console.log(e);
		}
	});
}
function guardarHoras (){
    //horasHombre
    //horasAdeudo
    //horasObservaciones
    if ($('#horasHombre').val().includes('M') || 
        $('#horasHombre').val().includes('H') ||  
        $('#horasAdeudo').val().includes('M') || 
        $('#horasAdeudo').val().includes('H') ||
        $('#horasHombre').val()=="" ||
        $('#horasAdeudo').val()==""){
        Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'Por favor complete el formulario!'
		})
    }
    else{

        $.ajax({
            type: "GET",
            url: "/guardar_calendario_produccion",
            data: { 
                'idCalendario': $('#idCalendario').val(),
                'hombre': $('#horasHombre').val(),
                'adeudo': $('#horasAdeudo').val(),
                'obs': $('#horasObservaciones').val()
            },
            success: (data) => {
                console.log(data)

                $('#hour_men-'+$('#idCalendario').val()).text($('#horasHombre').val());
                $('#hour_adeudo-'+$('#idCalendario').val()).text($('#horasAdeudo').val());
                $('#hour_habi-'+$('#idCalendario').val()).text( data );

                $('#detallesFecha').modal('toggle');
                Swal.fire({
					icon: 'success',
					title: 'Guardado!',
					text: 'Se ha guardado el registro.'
				})
               // $("#detallesFecha").modal("show");
                
            },
            error: (e) => {
                console.log(e);
            }
        });


    }
}