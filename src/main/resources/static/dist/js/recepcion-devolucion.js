function agregarRecepcion_Devolucion(){
    $('#maquilero').val(null);
    $('#maquilero').selectpicker('refresh');
    $('#noPedido').empty();
    $('#noPedido').selectpicker('refresh');
    $('#op').empty();
    $('#op').selectpicker('refresh');
    $('#cantidad').val(null);
    $('#cantidadPendente').val(null);
    $("#boton-add").prop('disabled', true);
    $("#modalRecepcionDevolucion").modal('show');
}

function listarPedidos(id){
    $('#noPedido').empty();
    $('#noPedido').selectpicker('refresh');
    $('#op').empty();
    $('#op').selectpicker('refresh');
    $('#cantidad').val(null);
    $('#cantidadPendente').val(null);
    $("#boton-add").prop('disabled', true);
    $.ajax({
        url:'listar_pedidos_by_maquilero',
        type:'GET',
        data:{'idMaquilero':id},
        success:function(data){

            for (i in data) {
                $('#noPedido').append("<option value=" + data[i][0] + ">" + data[i][1] + "</option>");
                
            }
            $('#noPedido').selectpicker('refresh');

        }
    })
}
function listarop (idPedido){
    $('#op').empty();
    $('#op').selectpicker('refresh');
    $('#cantidad').val(null);
    $('#cantidadPendente').val(null);
    $.ajax({
        url:'listar_op_by_maquilero_and_pedido',
        type:'GET',
        data:{'idMaquilero':$('#maquilero').val() , 'idPedido': idPedido},
        success:function(data){

            for (i in data) {
                if ( data[i][1]  >0){
                    $('#op').append("<option value=" + data[i][0] + " cantidadMax="+data[i][1] +">" + data[i][0] + "</option>");
                }
                
                
            }
            $('#op').selectpicker('refresh');

        }
    })
}
function cantidadFaltantes(){
    $('#cantidadPendente').val($("#op option:selected").attr("cantidadMax"));
}
$( "#cantidad" ).change(function() {
    if ( parseInt ($("#cantidad").val()) <= parseInt ( $("#op option:selected").attr("cantidadMax") ) && $("#cantidad").val() >0){
        
        $("#cantidadPendente").val(  parseInt ( $("#op option:selected").attr("cantidadMax") ) -$("#cantidad").val() ) ;
        $("#boton-add").prop('disabled', false);

    }
    else{
        $("#boton-add").prop('disabled', true);
        $("#cantidad").val("");
        $("#cantidadPendente").val($("#op option:selected").attr("cantidadMax") ) ;
    }
});

function agregar(){

    $.ajax({
        url:'guardar_recepcion_devolucion',
        type:'GET',
        data:{
            'idOp':$('#op').val(),
            'idMaquilero':$('#maquilero').val(),
            'recibida':$('#cantidad').val(),
            'pendiente':$('#cantidadPendente').val()
        },
        success:function(data){

            console.log(data)
        }
    })


}