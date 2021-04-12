function agregarProceso(){
    $('#idProceso').prop('disabled', false);
    $("#idProceso").val(null);
    $("#idProceso").selectpicker("refresh");
    $("#observacion").val(null);
    $("#idObservacionProceso").val(null);
    
    $("#modalProceso").modal("show");

}

function guardar(){
    if ( $("#idProceso").val() == null || $("#idProceso").val()=="" || $("#observacion").val()=="" ){
        Swal.fire({
            icon:"error",
            title:"Error",
            text:"Complete el formulario"
        })

    }
    else{
        $('#botonGuardar').prop('disabled', true);
         
        
        $.ajax({
            type:"GET",
            url:"/validar_duplicado_observacion",
            data:{
                'idObservacion':$("#idObservacionProceso").val(),
                'idProceso':$("#idProceso").val(),
                'observacion':$("#observacion").val()
            },
            success:function(data){
                if ( data == true){
                    Swal.fire({
                        icon: 'warning',
                        title: 'Duplicado!',
                        text:"Ya existe un registro con las mismas caracterizticas!",
                        confirmButtonText: 'OK',
                        reverseButtons: true
                    })
                    $('#botonGuardar').prop('disabled', false);
                }
                else{
                    $.ajax({
                        type:"GET",
                        url:"/guardar_reporte_actividades",
                        data:{
                            'idObservacionProceso':$("#idObservacionProceso").val(),
                            'idProceso':$("#idProceso").val(),
                            'observacion':$("#observacion").val()
                        },
                        success:function(data){
                            console.log(data)
                            if (data == true){
                                Swal.fire({
                                    icon: 'success',
                                    title: 'Guardado!',
                                    text:"Se ha guardado correctamente!",
                                    allowOutsideClick: false,
                                    confirmButtonText: 'OK',
                                    reverseButtons: true
                                }).then((result) => {
                                    if (result.value) {
                                        location.reload();
                                    }
                                })
                            }
                            else{
                                $("#modalProceso").modal("hide");
                            }
                        }

                    })


                }
            }

        })


    }
}
function editar(id){
    $('#idProceso').prop('disabled', true);
    $.ajax({
        type:'GET',
        url:'/obtener_observacion_by_id',
        data:{'idObservacionProceso':id},
        success: function(data){
            $("#idObservacionProceso").val(data.idObservacionProceso)
            $("#observacion").val(data.observacion);
            $("#idProceso").val(data.idProceso);
            $("#idProceso").selectpicker("refresh");
        }
    })
    $("#modalProceso").modal("show");
}
function guardarUsuario(){
    if ($("#idUsuario").val() == null || $("#idUsuario").val() == "" ){

        Swal.fire({
            icon:"error",
            title:"Error",
            text:"Seleccione un ussuario"
        })
    }else{
        $('#bton_asignar').prop('disabled', true);
        
        $.ajax({
            type:'GET',
            url:'/asignar_reporte_actividades',
            data:{
                'idObservacionProceso': $("#idObservacionProcesoAsignacion").val(),
                'idUsuario': $("#idUsuario").val()

            },
            success: function(data){
               if(data== true){
                Swal.fire({
                    icon: 'success',
                    title: 'Guardado!',
                    text:"Se ha guardado correctamente!",
                    allowOutsideClick: false,
                    confirmButtonText: 'OK',
                    reverseButtons: true
                }).then((result) => {
                    if (result.value) {
                        location.reload();
                    }
                })
               }
            }
        })
    }
}
function modalusuario (id){

    $.ajax({
        type:'GET',
        url:'/obtener_observacion_by_id',
        data:{'idObservacionProceso':id},
        success: function(data){
            $("#idObservacionProcesoAsignacion").val(data.idObservacionProceso);
            $("#idUsuario").val(data.idUsuario);
            $("#idUsuario").selectpicker("refresh");
        }
    })
    
}