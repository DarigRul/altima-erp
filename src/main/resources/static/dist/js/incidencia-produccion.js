function nuevo(){
    $('#maquilero').val(null);
    $('#maquilero').selectpicker('refresh');
    $('#noPedido').empty();
    $('#noPedido').selectpicker('refresh');
    $('#op').empty();
    $('#op').selectpicker('refresh');
    $('#cantidad').val(null);
    $('#monto').val(null);
    $('#reporte').val(null);
    $("#flexRadioDefault1").prop("checked", false);
    $("#flexRadioDefault2").prop("checked", true);
    $('#monto').prop('disabled',true)
    $("#nuevaIncidencia").modal("show");
}

function listarPedidos(id){
    $('#noPedido').empty();
    $('#noPedido').selectpicker('refresh');
    $('#op').empty();
    $('#op').selectpicker('refresh');
    $.ajax({
        url:'/listar_pedidos_by_maquilero_incidencia',
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
function listarOp(id){
    $('#op').empty();
    $('#op').selectpicker('refresh');
    $.ajax({
        url:'/listar_op_incidencia',
        type:'GET',
        data:{'idPedido':id},
        success:function(data){

            for (i in data) {
                $('#op').append("<option value=" + data[i][0] + ">" + data[i][0] + "</option>");
                
            }
            $('#op').selectpicker('refresh');

        }
    })
    
}
$( "#flexRadioDefault1" ).click(function() {
    $('#monto').prop('disabled',false)
});
$( "#flexRadioDefault2" ).click(function() {
    $('#monto').prop('disabled',true)
    $('#monto').val("");
});

$('#cantidad').on('input', function(e) {
   valor = parseInt($('#cantidad').val())
    if (isNaN(valor)) {
        $('#cantidad').val("");
    }else if (valor<=0){
        $('#cantidad').val("");
    }
    else{
        $('#cantidad').val(valor);
    }
})

$('#monto').on('input', function(e) {
     if (isNaN($('#monto').val())) {
         $('#monto').val("");
     }else if ($('#monto').val()<=0){
         $('#monto').val("");
     }
     else{
         $('#monto').val(trunc($('#monto').val(),2));
     }
})

function trunc (x, posiciones = 0) {
    var s = x.toString()
    var l = s.length
    var decimalLength = s.indexOf('.') + 1
  
    if (l - decimalLength <= posiciones){
      return x
    }
    // Parte decimal del número
    var isNeg  = x < 0
    var decimal =  x % 1
    var entera  = isNeg ? Math.ceil(x) : Math.floor(x)
    // Parte decimal como número entero
    // Ejemplo: parte decimal = 0.77
    // decimalFormated = 0.77 * (10^posiciones)
    // si posiciones es 2 ==> 0.77 * 100
    // si posiciones es 3 ==> 0.77 * 1000
    var decimalFormated = Math.floor(
      Math.abs(decimal) * Math.pow(10, posiciones)
    )
    // Sustraemos del número original la parte decimal
    // y le sumamos la parte decimal que hemos formateado
    var finalNum = entera + 
      ((decimalFormated / Math.pow(10, posiciones))*(isNeg ? -1 : 1))
    
    return finalNum
}
function save(){
    var valid;
    if ( $("input[id='flexRadioDefault1']").is(':checked')  ){
        if ( $('#monto').val() == ""){
            valid=false;
        }
        else{
            valid=true;
        }
    }
    else{
        valid=true;
    }
   
    

    if ( $('#op').val() == ""  || valid==false || $('#cantidad').val() == "" ){
        Swal.fire({
            position: 'center',
            icon: 'warning',
            title: '¡Complete el formulario!',
            showConfirmButton: true,
            
        })

    }
    else{
        //String ,String ,String , String ,String ,String , String 
        $.ajax({
            url:'/guardar_incidencia_produccion',
            type:'GET',
            data:{'maquilero':$('#maquilero').val(),
                    'pedido': $('#noPedido').val(),
                    'op':$('#op').val(),
                    'cantidad':$('#cantidad').val(),
                    'descuento':(valid != true ? 1 : 0 ),
                    'monto':$('#monto').val(),
                    'reporte':$('#reporte').val() },
            success:function(data){
                if (data==true){
                    $("#nuevaIncidencia").modal("hide");
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: '¡Guardado!',
                        showConfirmButton: true
                    })
                    location.reload();
                }
    
            }
        })
    }
}
  
function aceptar(id){
    Swal.fire({
        icon: 'warning',
        title: '¿Quiere aceptar esta incidencia?.',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Aceptar',
        confirmButtonColor: '#0288d1',
    }).then((result) => {
        if (result.value && id != null) {
            $.ajax({
                url:'/editar_incidencia_produccion',
                type:'GET',
                data:{'id':id},
                success:function(data){
                    if (data==true){
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: '¡Guardado!',
                            showConfirmButton: true
                        })
                        location.reload();
                    }
        
                }
            })
        } // ////////////termina result value
    })

}