function listarOperaciones (idFamilia){
    $.ajax({
		method: "GET",
		url: "/listar_operaciones_by_famlia_prenda",
		data:{'familia': idFamilia, 'prenda': $('#idPrenda').val() } ,
		success: (data) => {
            $("#operacionAsignacion").empty();
            $(data).each(function(i, v){ 
                
                    $('#operacionAsignacion').append('<option  value="'+v[0]+'">' + v[1]+ '</option>');
                
                
            })
            $('#operacionAsignacion').selectpicker('refresh');
       	
		},
		error: (e) => {

		}
	})

}
function eliminar(id){
    Swal.fire({
        title: '¿Deseas eliminar está operación?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmar'
    }).then((result) => {
        if (result.value) {
            //
            $.ajax({
                method: "GET",
                url: "/eliminar_operaciones_by_prenda",
                data:{'idAsignacion': id } ,
                success: (data) => {
                    if (data == true){
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'Eliminado correctamente',
                            showConfirmButton: false,
                            timer: 2500
                        })
                        location.reload();
                    }
                    
                },
                error: (e) => {
                }
            })
           
        }
    });
    
}
function agregarOperacion() {
    //
    if ( $('#operacionAsignacion').val() == null ||$('#operacionAsignacion').val() == "" ){

        Swal.fire({
            icon: 'error',
            title: 'Error!',
            text: 'Eliga una operación.'
          })
    }
    else{
        $.ajax({
            method: "GET",
            url: "/guardar_operaciones_by_prenda",
            data:{'idoperacion': $('#operacionAsignacion').val(), 'prenda': $('#idPrenda').val() } ,
            success: (data) => {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Insertado correctamente',
                    showConfirmButton: false,
                    timer: 2500
                })
                location.reload();
            },
            error: (e) => {
            }
        })

    }
}