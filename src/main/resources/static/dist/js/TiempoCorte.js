console.log("d")
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
                    '<button data-toggle="modal" data-target="#asignacionTiempo" onclick=addTiempo(this) id='+data[i][0]+' tiempo='+data[i][6]+' class="btn btn-altima btn-sm btn-circle popoverxd" data-placement="top" data-content="Asignaci&oacute;n de tiempo"><i class="fas fa-clock"></i></button>'+
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

function calendario(){
    $.ajax({
        type: "GET",
        url:"/get_validar_calendario",
        data: {},
       
        success: function(data) {
            if (data == true){
                Swal.fire({
                    icon: 'error',
                    title: 'Error!',
                    text: 'Ya existe el calendario de este año.'
                })
            }
            else{

                $.ajax({
                    type: "GET",
                    url:"/get_crear_calendario",
                    data: {},
                   
                    success: function(data) {
                        if (data == true){
                            Swal.fire({
                                icon: 'success',
                                title: 'Guardado!',
                                text: 'Se ha guardado el registro.'
                            })
                        }
                        else{
            
                        }
                      //console.log(data)
                        //console.log(data)
                    }
                })

            }
          console.log(data)
            //console.log(data)
        }
    })

    
  //
}