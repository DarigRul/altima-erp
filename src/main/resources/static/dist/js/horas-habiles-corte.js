
$(function() {      

   $("#horasAdeudo").inputmask({"mask": "9{1,3}.99"});
   $("#horasHombre").inputmask({"mask": "9{1,3}.99"});
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
                if ( $("#rolEditar").length > 0 ) {
                    t.row.add( [
                        '<p>'+data[i][1]+'</p>',
                        '<p id="hour_men-'+data[i][0]+'">'+data[i][2]+'</p>',
                        '<p id="hour_adeudo-'+data[i][0]+'">'+data[i][3]+'</p>',
                        '<p id="hour_habi-'+data[i][0]+'">'+restarHoras("" + data[i][2] + "",  ""+data[i][3] + "")+'</p>',
                        
                        '<td>  <button  onclick="editar(' + data[i][0]+ ')"  class="btn btn-warning btn-circle btn-sm"  data-toggle="modal" data-target="#detalleTelas" ><i class="fas fa-pen"></i></button> </td>  '
                    
            
                    ] ).draw( false );
                }
                else{

                    t.row.add( [
                        '<p>'+data[i][1]+'</p>',
                        '<p id="hour_men-'+data[i][0]+'">'+data[i][2]+'</p>',
                        '<p id="hour_adeudo-'+data[i][0]+'">'+data[i][3]+'</p>',
                        '<p id="hour_habi-'+data[i][0]+'">'+data[i][4]+'</p>',
                        
                        '<td> Sin acciones </td>  '
                    
            
                    ] ).draw( false );
                }
                
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

                $('#hour_habi-'+$('#idCalendario').val()).text( restarHoras("" + $('#horasHombre').val() + "",  ""+$('#horasAdeudo').val() + ""),);

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

function validarHoras(text, nombreInput){
    var arrayDeCadenas = text.split('.');
    var hours = parseInt(arrayDeCadenas[0]);
    var minute = parseInt(arrayDeCadenas[1]);

    if ( parseInt(arrayDeCadenas[0])  < 10 ){
        hours = '0'+hours;
        
    }
    if ( parseInt(arrayDeCadenas[1])  > 59){
        minute='59';
        
    }else if ( parseInt(arrayDeCadenas[1])  < 10) {
        minute = minute+'0';
    }
    $("#"+nombreInput).val(hours+'.'+minute);  
}

function restarHoras(start, end){
    s = start.split('.'); 
    e = end.split('.'); 
    min = s[1]-e[1]; 
    hour_carry = 0; 
    if(min < 0){ 
        min += 60; 
        hour_carry += 1; 
    } 
    hour = s[0]-e[0]-hour_carry; 

    if ( hour < 10  && hour >0){
        hour = '0'+hour;
        
    }else if (hour <0 && hour >-10 ){
        hour=hour*-1;
        hour='-0'+hour;
    }
    else if ( hour ==0){
        hour = '0'+hour;
    }
    if ( min  < 10) {
        min = '0'+min;
    }
    diff = hour + "." + min;

    return diff

}