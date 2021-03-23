function agregarConversion(){
    $('#cliente').val(null);
    $('#personas').val(null);
    $('#prendas').val(null);
    $("#fRecepcion").val(null);
    $("#fEntrega").val(null);
    $("#conversion").val(null);
    $("#porcentaje").val(null);
    $("#observaciones").val(null);
    listarPedidos(null);
    $("#idConversionTallas").val(null);
    $('#modalConversion').modal('show')
}

function listarPedidos( id){
    var pedidos =$("#noPedido");
    pedidos.find('option').remove();
    $.ajax({
        type: "GET",
        url:"/mostrar_pedido_estatus_3_conversion",
        data: {},
        success: function(data) {
            $.each(data, function(key,val){
                pedidos.append('<option value="'+val[0]+'">'+val[1]+'  </option> ');
            })
            $("#noPedido").selectpicker("refresh");
        }
    })
}

function datosPedidos (id){
    var now = new Date();
    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);
    var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
    $.ajax({
        type:'GET',
        url:'devolver_datos_del_pedido_by_id',
        data:{
            'id':id
        },
        success: function(data){
            $('#cliente').val(data[0][0]);
            $('#personas').val(data[0][1]);
            $('#prendas').val(data[0][2]);
            $("#fRecepcion").val(today);
            $("#fEntrega").val(null);
            $("#conversion").val(null);
            $("#porcentaje").val(null);
            $("#observaciones").val(null);
        }
    })
}
$("#conversion").change(function(){
    if ($("#conversion").val() <=0){
        $("#conversion").val("");
        $("#porcentaje").val("")
    }else if ($('#prendas').val()  != ""){
        $('#porcentaje').val( ($('#prendas').val() / $("#conversion").val()).toFixed(2))
    }
});
function guardar(){

    if ($("#fRecepcion").val() == "" || $("#fEntrega").val() =="" ||
        $("#conversion").val() =="" || $("#porcentaje").val() ==""||
        $("#observaciones").val() ==""  || $("#noPedido").val() =="" || $("#noPedido").val() == null){
        Swal.fire({
            icon: 'error',
            title: 'Error!',
            text: 'Complete el formulario.'
        })
    }
    else{
        $.ajax({
            url:'/guardar_conversion_tallas',
            type:'GET',
            data:{
                'fechaRecepcion': $("#fRecepcion").val() ,
                'fechaEntrega' : $("#fEntrega").val(),
                'conversiones': $("#conversion").val(),
                'porcentaje' : $("#porcentaje").val() ,
                'observaciones':$("#observaciones").val(),
                'idPedido': $("#noPedido").val(),
                'idConversionTallas': $("#idConversionTallas").val()
            },
            success:function(data){
                if (data == true){
                    Swal.fire({
                        icon: 'success',
                        title: 'Guardado!',
                        text: 'Se ha guardado el registro.',
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

function editar(id){

    var pedidos =$("#noPedido");
    pedidos.find('option').remove();
    $.ajax({
        type:'GET',
        url:'/devolver_datos_del_pedido_by_id_editar',
        data:{
            'id':id
        },
        success: function(data){
            pedidos.append('<option value="'+data[0][1]+'">'+data[0][2]+'  </option> ');
            $("#noPedido").selectpicker("refresh");
            $('#noPedido').val(data[0][1]);
            $("#noPedido").selectpicker("refresh");
            $('#cliente').val(data[0][3]);
            $('#personas').val(data[0][4]);
            $('#prendas').val(data[0][5]);
            $("#fRecepcion").val(data[0][6]);
            $("#fEntrega").val(data[0][7]);
            $("#conversion").val(data[0][8]);
            $("#porcentaje").val(data[0][9]);
            $("#observaciones").val(data[0][10]);
            $("#idConversionTallas").val(data[0][0]);
            
            $('#modalConversion').modal('show');
        }
    })


}
function marcarInsidencia(id, accion){
    var titulo;
    var textBoton;
    if ( accion ==1){
        titulo='&iquest;Est&aacute; seguro que desea marcar la incidencia?';
        textBoton='Si, marcar';
    }else if (accion==0){
        titulo='&iquest;Est&aacute; seguro que desea desmarcar la incidencia?';
        textBoton='Si, desmarcar';
    }
    Swal.fire({
        title: titulo,
        text: "Puede cambiarlo en otro momento",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        cancelButtonText: 'Cancelar',
        confirmButtonText: textBoton,
        reverseButtons: true

    }).then((result) => {
        if (result.value) {
            $.ajax({
                type:'GET',
                url:'/marcar_incidencia_by_id',
                data:{
                    'id':id,
                    'accion':accion
                },
                success: function(data){
                    Swal.fire({
                        icon: 'success',
                        title: 'Guardado!',
                        text: 'Se ha '+data+'.',
                        allowOutsideClick: false,
                        confirmButtonText: 'OK',
                        reverseButtons: true
                    }).then((result) => {
                        if (result.value) {
                            location.reload();
                        }
                    })
                }
            })
        }
    })
}
    