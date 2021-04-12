var num_movimiento;
function agregarRecepcion_Devolucion(){
    $('#maquilero').prop('disabled', false);
    $('#maquilero').val(null);
    $('#maquilero').selectpicker('refresh');
    $('#noPedido').empty();
    $('#noPedido').selectpicker('refresh');
    $('#op').empty();
    $('#op').selectpicker('refresh');
    $('#cantidad').val(null);
    $('#cantidadPendente').val(null);
    $("#boton-add").prop('disabled', true);
    var table = $('#tablaop').DataTable();
	        var rows = table
            .rows()
            .remove()
	        .draw(); 
    $.ajax({
        type:'GET',
        url:'numero_movimiento_recepcion',
        data:{},
        success:function(data){
            num_movimiento=data;
            console.log(num_movimiento);
        }

    })
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
            'pendiente':$('#cantidadPendente').val(),
            'num': num_movimiento
        },
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
        success:function(data){
            llenarTabla(data);
        }
       
    })
}
function llenarTabla(data){
    
    var table = $('#tablaop').DataTable();
    var rows = table
    .rows()
    .remove()
    .draw(); 
    for (i in data) {
        table.row.add([	
            data[i][1],
            data[i][2],
            data[i][3],
            data[i][4],
            data[i][5],
            data[i][6],
            data[i][7],
            data[i][8],
            data[i][9],
            data[i][10],
            (data[i][8] >0 ? '<botton onclick="recibir(this);" id="' + data[i][0] + '" pendiente="' + data[i][8] + '" class="btn btn-success text-white btn-circle btn-sm btn-alta popoverxd" data-container="body" data-toggle="popover" data-placement="left" data-content="Recibir"><i class="fas fa-long-arrow-alt-up"></i></botton>' : "")+
            (data[i][9] >0 ? '<botton onclick="recibir_devolucion(this);" id="' + data[i][0] + '" canDev="' + data[i][9] + '" canReci="' + data[i][7] + '" class="btn btn-warning text-white btn-circle btn-sm btn-alta popoverxd" data-container="body" data-toggle="popover" data-placement="left" data-content="Recibir devoluciones"> <i class="fas fa-exchange-alt"></i> </botton>' : "")+
            (data[i][7] >0 ? '<botton onclick="devolver(this);" id="' + data[i][0] + '" recibida="' + data[i][7] + '"  canDev="' + data[i][9] + '"  class="btn btn-danger text-white btn-circle btn-sm btn-alta popoverxd" data-container="body" data-toggle="popover" data-placement="left" data-content="Devolver"><i class="fas fa-long-arrow-alt-down"></i></botton>' : "")
            
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
            $("#modalRecepcionDevolucion").modal('show');
        }
  })
}
function detalles(num_movimiento, idMaquilador){
    $('#maquilero').prop('disabled', true);
    $('#maquilero').val(idMaquilador);
    $('#maquilero').selectpicker('refresh');
    $('#noPedido').empty();
    $('#noPedido').selectpicker('refresh');
    $('#op').empty();
    $('#op').selectpicker('refresh');
    $('#cantidad').val(null);
    $('#cantidadPendente').val(null);
    $("#boton-add").prop('disabled', true);

    
    $.ajax({
        url:'detalles_recepcion_devolucion',
        type:'GET',
        data:{
            'num_movimiento':num_movimiento,
            'idMaquilero':idMaquilador
        },
        beforeSend: function () {
       	 Swal.fire({
                title: 'Cargando',
                html: 'Por favor espere',// add html attribute if you want or remove
                allowOutsideClick: false,
                timerProgressBar: true,
                onBeforeOpen: () => {
                    Swal.showLoading()
                },
            });
		},
        success:function(data){

            llenarTabla(data);
        }
       
    })
}
$('#modalRecepcionDevolucion').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});

function recibir(e){
    Swal.fire({
        title: 'Recibir',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<input type="hidden" class="form-control" name="idDevolucion" id="idDevolucion" value="' + e.getAttribute("id") + '" placeholder="0">' +
            
            '<input type="hidden" class="form-control" id="pendiente" name="pendiente" value="' + e.getAttribute("pendiente")  + '" >' +
            '<label for="proveedorColor">Recibir</label>' +
            '<input type="number" class="form-control" id="recibir"  name="recibir"  placeholder="0">' +

            '</div>' +
            '</div>',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#0288d1',
        preConfirm: (recibir) => {
            if ( document.getElementById("recibir").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete el campo`
                )
            }
            else if (document.getElementById("recibir").value > document.getElementById("pendiente").value || document.getElementById("recibir").value <= 0  ) {
                Swal.showValidationMessage(
                    `Cantidad erronea`
                )
            }
        }
    }).then((result) => {
        if (result.value ) {
            $.ajax({
                type:'GET',
                url:'guardar_recibir_by_id',
                data:{
                    'id':$("#idDevolucion").val(),
                    'pendiente': ( parseInt($("#pendiente").val())-parseInt($("#recibir").val())),
                    'recibido': (parseInt($("#pendiente").val())+parseInt($("#recibir").val())),
                    'cantidad': (parseInt($("#recibir").val()))
                },
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
                success:function(data){
                    llenarTabla(data);
                }
            })

        } // /fin if
    })
}


function devolver(e){
    Swal.fire({
        title: 'Devolver',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<input type="hidden" class="form-control" name="idDevolucion" id="idDevolucion" value="' + e.getAttribute("id") + '" placeholder="0">' +
            
            '<input type="hidden" class="form-control" id="recibida" name="recibida" value="' + e.getAttribute("recibida")  + '" >' +
            '<input type="hidden" class="form-control" id="canDev" name="canDev" value="' + e.getAttribute("canDev")  + '" >' +
            
            '<label for="proveedorColor">Devolver</label>' +
            '<input type="number" class="form-control" id="devolver"  name="devolver"  placeholder="0">' +

            '</div>' +
            '</div>',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#0288d1',
        preConfirm: (devolver) => {
            if ( document.getElementById("devolver").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete el campo`
                )
            }
            else if (document.getElementById("devolver").value > document.getElementById("recibida").value || document.getElementById("devolver").value <= 0  ) {
                Swal.showValidationMessage(
                    `Cantidad erronea`
                )
            }
        }
    }).then((result) => {
        if (result.value ) {
            $.ajax({
                type:'GET',
                url:'guardar_devolver_by_id',
                data:{
                    'id':$("#idDevolucion").val(),
                    'recibido': (parseInt($("#recibida").val())-parseInt($("#devolver").val())),
                    'dev': (parseInt($("#canDev").val())+parseInt($("#devolver").val())),
                    'cantidad': (parseInt($("#devolver").val()))
                },
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
                success:function(data){
                    llenarTabla(data);
                }
            })

        } // /fin if
    })
}

function recibir_devolucion(e){

    Swal.fire({
        title: 'Recibir devoluciones',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<input type="hidden" class="form-control" name="idDevolucion" id="idDevolucion" value="' + e.getAttribute("id") + '" placeholder="0">' +
            '<input type="hidden" class="form-control" name="canReci" id="canReci" value="' + e.getAttribute("canReci") + '" placeholder="0">' +
            
            '<input type="hidden" class="form-control" id="canDev" name="canDev" value="' + e.getAttribute("canDev")  + '" >' +
            '<label for="proveedorColor">Recibir</label>' +
            '<input type="number" class="form-control" id="recibir"  name="recibir"  placeholder="0">' +

            '</div>' +
            '</div>',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#0288d1',
        preConfirm: (devolver) => {
            if ( document.getElementById("recibir").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete el campo`
                )
            }
            else if (document.getElementById("recibir").value > document.getElementById("canDev").value || document.getElementById("recibir").value <= 0  ) {
                Swal.showValidationMessage(
                    `Cantidad erronea`
                )
            }
        }
    }).then((result) => {
        if (result.value ) {
            $.ajax({
                type:'GET',
                url:'guardar_recibir_devolucion_by_id',
                data:{
                    'id':$("#idDevolucion").val(),
                    'recibido': (parseInt($("#canReci").val())+parseInt($("#recibir").val())),
                    'dev': (parseInt($("#canDev").val())-parseInt($("#recibir").val())),
                    'cantidad': (parseInt($("#recibir").val()))
                },
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
                success:function(data){
                    llenarTabla(data);
                }
            })

        } // /fin if
    })
}
function historico (num_movimiento, idMaquilador){
    //detallesHistorico
    
    //
    $.ajax({
        type:'GET',
        url:"detalles_historico_recepcion_devolucion",
        data:{
            'num_movimiento':num_movimiento,
            'idMaquilero':idMaquilador
            

        },
        beforeSend: function () {
       	 Swal.fire({
                title: 'Cargando',
                html: 'Por favor espere',// add html attribute if you want or remove
                allowOutsideClick: false,
                timerProgressBar: true,
                onBeforeOpen: () => {
                    Swal.showLoading()
                },
            });
		},
        success:function(data){   
            var table = $('#detalles').DataTable();
            var rows = table
            .rows()
            .remove()
            .draw(); 
            for (i in data) {
                table.row.add([	
                    data[i][0],
                    data[i][1],
                    data[i][2],
                    data[i][3],
                    data[i][4],
                    data[i][5],
                    data[i][6],
                    data[i][7],
                    data[i][8]
                    
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
                    $("#detallesHistorico").modal('show');
                }
        })
        }
    })
}

$('#modalRecepcionDevolucion').on('hidden.bs.modal', function () {
	
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