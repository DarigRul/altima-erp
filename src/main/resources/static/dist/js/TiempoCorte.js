$(function() {      

 })
function verDetalles(e){
    var folio = e.getAttribute("folio");
    var table = $('#tablaDetalles').DataTable();
	var rows = table
    .rows()
    .remove()
	.draw(); 
    $.ajax({
        type: "GET",
        url:"/listar_coordinado_prenda_por_folio",
        data: { 
            'folio': folio
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
                    '<button data-toggle="modal" data-target="#asignacionTiempo" onclick=addTiempo(this) id='+data[i][0]+' tiempo='+data[i][6]+' class="btn btn-altima btn-sm btn-circle popoverxd"  data-content="Asignaci&oacute;n de tiempo"><i class="fas fa-clock"></i></button>'+
		  			"</td>"
                    
        			
        		
        		]).node().id ="row";
        		table.draw( false );
			}
        	//console.log(data)
        }
    })
}

function addTiempo (e){

    var id = e.getAttribute("id");
    var tiempo = e.getAttribute("tiempo");
    
    $("#tiempoMinutos").val( $('#tiempoP'+id).text());
    $("#idCoorPrenda").val(id);
    
    

    
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
            url:"/add_tiempo_coordinado_prenda",
            data: { 
                'id': $("#idCoorPrenda").val(),
                'tiempo':$("#tiempoMinutos").val()
            },
           
            success: function(data) {
                if ( data == true){
                    Swal.fire({
                        icon: 'success',
                        title: 'Guardado!',
                        text: 'Se ha guardado el registro.'
                    })
                    
                    $('#tiempoP'+$("#idCoorPrenda").val()).text($("#tiempoMinutos").val());
                    $("#asignacionTiempo .close").click()
                    
                }
                //console.log(data)
            }
        })
      
    }

}

function rendondiar(el) {
    return document.getElementById(el);
  }
  
  rendondiar('tiempoMinutos').addEventListener('input',function() {
    var val = this.value;
    this.value = val.replace(/\D|\-/,'');
  });

  
$('#tiempoDetalle').on('hidden.bs.modal', function () {
	
    Swal.fire({
        title: 'Actualizando',
        icon: 'success',
        allowOutsideClick: false,
        timerProgressBar: true,
        onBeforeOpen: () => {
            Swal.showLoading()
            location.reload();
        },
    });
    
   
});



function calendarizar (e){

    var folio = e.getAttribute("folio");
    $('#idCoorPrendaFolio').val(folio);
     
    $.ajax({
        type: "GET",
        url:"/buscar_fecha_existente_por_folio",
        data: { 
            'folio':folio
        },
       
        success: function(data) {
            if (data== null){
                $('#idFecha').val(null);
            }else{
                $('#idFecha').val(data);
            }
        }
    })
    
    
}

function guardarCalendarioFolio(){

    if ( $('#idCoorPrendaFolio').val() == null ||  $('#idFecha').val() == "" || $('#idFecha').val() == null){
        Swal.fire({
            icon: 'error',
            title: 'Error!',
            text: 'Complete el formulario.'
        })

    }else{

        $.ajax({
            type: "GET",
            url:"/guardar_fecha_por_folio",
            data: {'folio':$('#idCoorPrendaFolio').val(), 'Fecha': $('#idFecha').val()},
            success: function(data) {
                $('#calendarizar').modal('toggle');
                
                if ( data == true){
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
        url:"/validar_fecha_produccion_calendario",
        data: {'folio':$('#idCoorPrendaFolio').val(), 'fecha': $('#idFecha').val()},
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
  function sumarDias(fecha, dias){
    fecha.setDate(fecha.getDate() + dias);
    return fecha;
  }
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

  function buscarfecha (){
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

  }

function formato (hora){
    hora =hora.replace(/[:]/gi,'.');

    var s = hora.split('.'); 
    hora = s[0] + "." + s[1];
    return hora;
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