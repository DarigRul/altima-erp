function agregarProceso(){
    $("#idProceso").val(null);
    $("#idProceso").selectpicker("refresh");
    $("#observacion").val(null);
    $("#modalProceso").modal("show");

}
function actualizarDatos(id){
    // Controlador CatalogoServicioController
    $.ajax({
        type:'GET',
        url:'/obtener_lookup_by_id',
        data:{'id':id},
        success: function(data){
            $("#observacion").val(data.descripcionLookup);
        }

    })
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
         // Controlador CatalogoServicioController
        $.ajax({
            type:"GET",
            url:"/guardar_reporte_actividades",
            data:{
                'idProceso':$("#idProceso").val(),
                'observacion':$("#observacion").val()
            },
            success:function(data){
                console.log(data)
                if (data == true){
                    Swal.fire({
                        icon:"success",
                        title:"Correcto",
                        text:"Se ha guardado correctamente!"
                    })
                    location.reload();
                }
                else{
                    $("#modalProceso").modal("hide");
                }
            }

        })

    }
}
function editar(id){
    $.ajax({
        type:'GET',
        url:'/obtener_lookup_by_id',
        data:{'id':id},
        success: function(data){
            $("#observacion").val(data.descripcionLookup);
            $("#idProceso").val(id);
            $("#idProceso").selectpicker("refresh");
        }

    })
    $("#modalProceso").modal("show");
}