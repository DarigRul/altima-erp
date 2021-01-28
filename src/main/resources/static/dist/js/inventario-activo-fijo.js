
function llenarSelectActivoFijoMaquila(idValor){
    $.ajax({
		method: "GET",
		url: "/listar_maquila_object_estatus",
		data:{
            'Tipo': 'Activo Fijo', 
            'estatus':'1'
        } ,
		success: (data) => {
            $("#tipo").empty();
            $(data).each(function(i, v){ 
                
                    $('#tipo').append('<option  value="'+v.idLookup+'">' + v.nombreLookup+ '</option>');
                
                
            })
            $('#tipo').selectpicker('refresh');
       	
		}, complete: function() {   
            $('#tipo').val(idValor);
            $('#tipo').selectpicker('refresh');
         
        },
		error: (e) => {

		}
	})

}

function nuevo(){

    llenarSelectActivoFijoMaquila(null);
    $('#idInventario').val(null);
    $('#activoFijo').val(null);
    $('#modelo').val(null);
    $('#marca').val(null);
    $('#serie').val(null);
    $('#motor').val(null);
    $('#modalAgregar').modal('show'); // abriraddcomponenes
}
function guardar(){

    if ( $('#tipo').val() == "" || 
            $('#activoFijo').val() == "" || 
            $('#modelo').val() == "" ||
            $('#marca').val() == "" ||
            $('#serie').val() == "" ||
            $('#motor').val() == "" ){
        $('#alertIAFA').css('display', '');
    }
    else{
        $('#alertIAFA').css('display', 'none');

        $.ajax({
            type: "GET",
            url: "/guardar_inventario_maquila_fijo",
            data: {

                'idInventario':$('#idInventario').val(), 
                'tipo':$('#tipo').val(),
                'activoFijo': $('#activoFijo').val(),
                'modelo':$('#modelo').val(),
                'marca':$('#marca').val(), 
                'serie':$('#serie').val() ,
                'motor':$('#motor').val()
              
            }

        }).done(function(data) {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Insertado correctamente',
                showConfirmButton: false,
                timer: 1250
            })
            location.reload()
        });
    }

}

function editar (id){
    $.ajax({
		method: "GET",
		url: "/buscar_inventario_maquila_fijo",
		data:{
            'idInventario':id
        } ,
		success: (data) => {
            $('#idInventario').val(data.idInventario);
            llenarSelectActivoFijoMaquila(data.idLookup);
            $('#activoFijo').val(data.activoFijo);
            $('#modelo').val(data.modelo);
            $('#marca').val(data.marca);
            $('#serie').val(data.serie);
            $('#motor').val(data.motor);
       	
		}, complete: function() {    
            $('#modalAgregar').modal('show'); // abrir        
        },
		error: (e) => {

		}
	})
}
//
function estatus (id,estatus){
    Swal.fire({
        title: '¿Deseas cambiar el estatus?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmar'
      }).then((result) => {
        if (result.value && id != null) {
            $.ajax({
                  type: "GET",
                  url: "/estatus_inventario_maquila_fijo",
                  data: {
                    'idInventario':id, 'estatus':estatus
                  }

              }).done(function(data) {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Cambio de estatus correctamente',
                    showConfirmButton: false,
                    timer: 1250
                })
                location.reload()
              });
              
        }
      });
}
function listarComponentesbyid(id){
    $.ajax({
		method: "GET",
		url: "/listar_componentes_by_id_inventario",
		data:{'idInventario':id} ,
		success: (data) => {

        	var tabla = $('#tablaComponentes').DataTable();
        
        	 
        	var rows = tabla
                .rows()
                .remove()
                .draw();
            $(data).each(function(i, v){ // indice, valor
            	
            		tabla.row.add([	
                		v[1],
                        v[2],
                        v[3],
                		
                		
    					'<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="eliminarComponentes('+v[0]+')"   tipo="este catalago" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>'
            
               		 ]).node().id ="row";
            	
            	
           	tabla.draw( false );
            	//fila = '<tr> <td>'+v[1]+'</td>  <td >'+ v[2] +'</td> <td >'+ v[3] +'</td> <td >'+ v[4] +'</td>  </tr>' ;
            	
            	
            	
            })
            
       	
		},
		error: (e) => {

		}
	})
}

function llenarSelectComponentes(idValor){
    $.ajax({
		method: "GET",
		url: "/listar_maquila_object_estatus",
		data:{
            'Tipo': 'Componente', 
            'estatus':'1'
        } ,
		success: (data) => {
            $("#idComponente").empty();
            $(data).each(function(i, v){ 
                
                    $('#idComponente').append('<option  value="'+v.idLookup+'">' + v.nombreLookup+ '</option>');
                
                
            })
            $('#idComponente').selectpicker('refresh');
       	
		}, complete: function() {   
            $('#idComponente').val(idValor);
            $('#idComponente').selectpicker('refresh');
         
        },
		error: (e) => {

		}
	})

}
function componentes(id){
    $('#addcomponenes').modal('show');
    llenarSelectComponentes(null);
    listarComponentesbyid(id);
    $('#marcaComponente').val(null);
    $('#cantidad').val(null);
    $('#ComponentesInventario').val(id);

}

function agregarComponente (){
    if ( $('#marcaComponente').val() == "" || $('#cantidad').val()==""  || $('#idComponente').val() =="" ){
        Swal.fire({
            icon: 'error',
            title: 'Complete todos los campos!'
          })
    }
    else{
        //

        //String , String  , String , String 

        $.ajax({
            type: "GET",
            url: "/guardar_componente_inventario",
            data: {

                'idComponentes':$('#idComponente').val(), 
                'idInventario':$('#ComponentesInventario').val(),
                'activoFijo': $('#activoFijo').val(),
                'marca':$('#marcaComponente').val(),
                'cantidad':$('#cantidad').val()
              
            }

        }).done(function(data) {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Insertado correctamente'
            })
            //location.reload()
            llenarSelectComponentes(null);
            listarComponentesbyid($('#ComponentesInventario').val());
            $('#marcaComponente').val(null);
            $('#cantidad').val(null);
        });
    }

}

function eliminarComponentes(id){

    Swal.fire({
        title: '¿Deseas eliminar el componente?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmar'
      }).then((result) => {
        if (result.value && id != null) {
            $.ajax({
                  type: "GET",
                  url: "/eliminar_componente_inventario",
                  data: {
                    'id':id
                  }

              }).done(function(data) {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Eliminado correctamente!'
                })
                llenarSelectComponentes(null);
                listarComponentesbyid($('#ComponentesInventario').val());
                $('#marcaComponente').val(null);
                $('#cantidad').val(null);
              });
              
        }
      });

}