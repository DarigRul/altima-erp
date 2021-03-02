function listarPorProceso(){
    var tablaPrincipal = $('#tableEmpalme').DataTable();

	$.ajax({
		method:"GET",
		url:"/listar_tiempo_proceso_secuencial",
		data:{ idProceso:$("#procesosActivos").val() },
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
		success: (data) => {
				for (i in data){
					tablaPrincipal.row.add([
						data[i][0],
						data[i][1],
						data[i][2],
						data[i][3],
						data[i][4],
						data[i][5],
						data[i][6],
						'<p id="tiempoSecuencia'+data[i][0]+'"> '+(data[i][7] == null? '':data[i][7] )+' </p>',
                        '<p id="fechaSecuencia'+data[i][0]+'"> '+(data[i][8] == null? '':data[i][8] )+' </p>',
					
                        '<button onclick="verDetalles(this)" secuencia="'+data[i][0]+'" idProceso="'+$("#procesosActivos").val() +'"   class="btn btn-info btn-sm btn-circle popoverxd" data-placement="top" data-content="Detalles"><i class="fas fa-info"></i></button>'+
                            
                        (data[i][9] == data[i][7] && data[i][7]!= null ? '<button onclick="calendarizar(this)" secuencia="'+data[i][0]+'" fecha="'+data[i][8]+'"  idProceso="'+$("#procesosActivos").val() +'" class="btn bg-success btn-sm btn-circle popoverxd" data-placement="top" data-content="Caledarizar"><i class="fas fa-calendar-day"></i></button>':''),
                        
					
						
					]).draw(true);
				}
			
			
			Swal.fire({
			      position: 'center',
		          icon: 'success',
		          title: '¡Listo!',
		          showConfirmButton: false,
		          timer: 500,
			      onClose: () => {
			    	  $('#SeleccionPrograma').modal("hide");
			      }
			})
		},
		error: (data) => {
			
		}
	});
}
var secuenciaGlobal;
function verDetalles(e){
    var secuencia = e.getAttribute("secuencia");
    var idProceso = e.getAttribute("idProceso");
    var table = $('#tablaDetalles').DataTable();
    secuenciaGlobal= e.getAttribute("secuencia");
	var rows = table
    .rows()
    .remove()
	.draw(); 
    $.ajax({
        type: "GET",
        url:"/listar_detalles_tiempo_proceso_secuencial",
        data: { 
            'idProceso': idProceso,
            'secuencia':secuencia
        },
        beforeSend: function () {
       	 Swal.fire({
                title: 'Buscando ',
                html: 'Por favor espere',// add html attribute if you want or remove
                allowOutsideClick: false,
                timerProgressBar: true,
                onBeforeOpen: () => {
                    Swal.showLoading()
                },
            });
		},
        success: function(data) {
        
        	for (i in data) {
                var tiempo = data[i][6];
                if (tiempo == null){
                    tiempo= "";
                }
        		table.row.add([	
        			data[i][1],
        			data[i][2],
        			data[i][3],
        			data[i][4],
                    data[i][5],
                    "<p id='tiempoP"+data[i][0]+"' class='text-center'>" + tiempo+ "</p>",
                    
                    "<td class='text-center'>" +
                    '<button  onclick=addTiempo(this) id='+data[i][0]+' tiempo='+data[i][6]+' class="btn btn-altima btn-sm btn-circle popoverxd" data-placement="top" data-content="Asignaci&oacute;n de tiempo"><i class="fas fa-clock"></i></button>'+
		  			"</td>"
                    
        			
        		
        		]).node().id ="row";
        		table.draw( false );
			}

            Swal.fire({
                position: 'center',
                icon: 'success',
                title: '¡Listo!',
                showConfirmButton: false,
                timer: 500,
                onClose: () => {
                    $('#tiempoDetalle').modal('show');
                }
          })
            
            
        	//console.log(data)
        }
    })
}

function addTiempo (e){

    var id = e.getAttribute("id");
    var tiempo = e.getAttribute("tiempo");
    
    $("#tiempoMinutos").val( $('#tiempoP'+id).text());
    $("#idExplosiconProcesos").val(id);
    
    $('#asignacionTiempo').modal('show');
    

    
}


function guardarTiempo(){

    if (  $("#tiempoMinutos").val() == null || $("#tiempoMinutos").val() <=0  || $("#tiempoMinutos").val() == "" ){
        Swal.fire({
            icon: 'error',
            title: 'Error!',
            text: 'Complete el formulario.'
        })

        

    }else{
        $.ajax({
            type: "GET",
            url:"/add_tiempo_explosion_secuencial",
            data: { 
                'id': $("#idExplosiconProcesos").val(),
                'tiempo':$("#tiempoMinutos").val()
            },
           
            success: function(data) {
                if ( data == true){
                    Swal.fire({
                        icon: 'success',
                        title: 'Guardado!',
                        text: 'Se ha guardado el registro.'
                    })
                    
                    $('#tiempoP'+$("#idExplosiconProcesos").val()).text($("#tiempoMinutos").val());
                    $("#asignacionTiempo .close").click()
                    
                }
                //console.log(data)
            }
        })
      
    }

}

$('#tiempoDetalle').on('hidden.bs.modal', function () {
	var sum=0;
    $('#tablaDetalles tr').each(function () {
        if ( $(this).find('td').eq(5).text() != undefined && $(this).find('td').eq(5).text() != null && $(this).find('td').eq(5).text() != "" ){

            sum += parseInt($(this).find('td').eq(5).text());

            
        }
        
        
    });

    $("#tiempoSecuencia"+secuenciaGlobal).text(sum);
    

});


function calendarizar (e){
    $('#idFecha').val(e.getAttribute("fecha"));
    $('#secuenciaFecha').val(e.getAttribute("secuencia"));
    $('#idPRocesoFecha').val(e.getAttribute("idProceso"));
    $('#calendarizar').modal('show');

}

function guardarCalendarioFolio(){
    console.log($('#idFecha').val())

    if ( $('#secuenciaFecha').val() == null ||  $('#idFecha').val() == "" || $('#idFecha').val() == null){
        Swal.fire({
            icon: 'error',
            title: 'Error!',
            text: 'Complete el formulario.'
        })

    }else{

        $.ajax({
            type: "GET",
            url:"/guardar_fecha_por_secuencia",
            data: {'secuencia':$('#secuenciaFecha').val(), 'fecha': $('#idFecha').val(), 'idProceso':$('#idPRocesoFecha').val()},
            success: function(data) {
                $('#calendarizar').modal('toggle');
                
                if ( data == true){
                    $("#fechaSecuencia"+secuenciaGlobal).text($('#idFecha').val());
                    
                    Swal.fire({
                        icon: 'success',
                        title: 'Guardado!',
                        text: 'Se ha guardado el registro.'
                    })

                }
              
                
            }
        })


    }
}

$( "#idFecha" ).change(function() {

    //validar_fecha_produccion_calendario
    $.ajax({
        type: "GET",
        url:"/validar_fecha_produccion_calendario_proceso",
        data: {'secuencia':$('#secuenciaFecha').val(), 'fecha': $('#idFecha').val(),'idProceso':$('#idPRocesoFecha').val()},
        beforeSend: function () {
            Swal.fire({
                title: 'Verificando fecha.',
                html: '¡Por favor espere!',// add html attribute if you want or remove
                allowOutsideClick: false,
                timerProgressBar: true,
                onBeforeOpen: () => {
                    Swal.showLoading()
                },
            });  
       },
        success: function(data) {
            
            if ( data ==0){
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Esta fecha no cuenta con calendarización!'
                })
                $('#idFecha').val(null);
            }
            else if ( data ==1){
                Swal.fire({
                    icon: 'warning',
                    title: 'El folio supera el tiempo de la fecha.',
                    text: '¿Le gustaria continuar?',
                    showCancelButton: true,
                    confirmButtonText: 'Continuar',
                    cancelButtonText: 'Cancelar',
                  }).then((result) => {
                    /* Read more about isConfirmed, isDenied below */
                    if (result.isConfirmed) {


                    } else if (
                        /* Read more about handling dismissals below */
                        result.dismiss === Swal.DismissReason.cancel
                      ){
                        console.log("aaaaaaaaa")
                        $('#idFecha').val(null);
                    }
                  })

            }
            else if (data == 2){
                Swal.fire({
                    icon: 'success',
                    title: 'Fecha valida'
                })
            }
        }
    })
    
  });

  function verCalendario(){
    //calendarioFechaInicio  calendarioFechaFin
    var now = new Date();
    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);
    var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
    $("#calendarioFechaInicio").val(today);

    now = sumarDias(now,+7);
    day = ("0" + now.getDate()).slice(-2);
    month = ("0" + (now.getMonth() + 1)).slice(-2);
    var today2 = now.getFullYear()+"-"+(month)+"-"+(day) ;
    $("#calendarioFechaFin").val(today2);

    var table = $('#tablaDetallesCalendario').DataTable();
	var rows = table
    .rows()
    .remove()
	.draw(); 
    $.ajax({
        type: "GET",
        url:"/listar_fechas_calendario",
        data: { 
            'fecha1': $("#calendarioFechaInicio").val(),
            'fecha2':$("#calendarioFechaFin").val()
        },
       
        success: function(data) {
        
        	for (i in data) {

                
               
        		table.row.add([	
                    data[i][0],
                    restarHoras("" + data[i][1] + "",  ""+data[i][2] + ""),
                    formato("" + data[i][3] + ""),
                    restarHoras(restarHoras("" + data[i][1] + "",  ""+data[i][2] + ""),  formato("" + data[i][3] + ""))
        		]).node().id ="row";
        		table.draw( false );
			}
        	console.log(data)
        }
    })



  
    $('#verCalendarioModal').modal('show'); // abrir
    
}

function sumarDias(fecha, dias){
    fecha.setDate(fecha.getDate() + dias);
    return fecha;
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

function formato (hora){
    hora =hora.replace(/[:]/gi,'.');

    var s = hora.split('.'); 
    hora = s[0] + "." + s[1];
    return hora;
}