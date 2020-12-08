var movimientos = [];
var movimientoCabecero = [];
function listarConceptos(movimiento) {
    $('#conceptoMovimiento option').remove();
    $('#conceptoMovimiento').selectpicker('refresh');
    let params = {
        "Tipo": movimiento
    };

    let query = Object.keys(params)
        .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(params[k]))
        .join('&');

    let url = '/entradas-salidas?' + query;

    fetch(url)
        .then(function (response) {
            if (response.ok) {
                console.log('Respuesta de red OK y respuesta HTTP OK');

            } else {
                console.log('Respuesta de red OK pero respuesta HTTP no OK');
            }
            return response.json();
        })
        .then(function (data) {
            data.forEach(function (data) {
                //aqui va el codigo
                $("#conceptoMovimiento").append("<option value='" + data.idLookup + "'>" + data.nombreLookup + "</option>")
            })
            $('#conceptoMovimiento').selectpicker('refresh');
        })
        .catch(function (error) {
            console.log('Hubo un problema con la petición Fetch:' + error.message);
        });

}

window.onload = function () {

    var today = new Date();
    var sevendays = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd
    }
    if (mm < 10) {
        mm = '0' + mm
    }
    var dd2 = sevendays.getDate() - 7;
    var mm2 = sevendays.getMonth() + 1; //January is 0!
    var yyyy2 = sevendays.getFullYear();
    if (dd2 < 10) {
        dd2 = '0' + dd2
    }
    if (mm2 < 10) {
        mm2 = '0' + mm2
    }

    today = yyyy + '-' + mm + '-' + dd;
    sevendays = yyyy2 + '-' + mm2 + '-' + dd2;
    document.getElementById("fechaMovimiento").setAttribute("max", today);
    document.getElementById("fechaMovimiento").setAttribute("min", sevendays);

    $('#almacenFisicoMovimiento').val("");

    let url = '/get-all-amp-logico';

    fetch(url)
        .then(function (response) {
            if (response.ok) {
                console.log('Respuesta de red OK y respuesta HTTP OK');

            } else {
                console.log('Respuesta de red OK pero respuesta HTTP no OK');
            }
            return response.json();
        })
        .then(function (data) {
            data.forEach(function (data) {
                //aqui va el codigo
                $("#almacenLogicoMovimiento").append("<option value='" + data[0] + "' data-id='" + data[3] + "' data-name='" + data[2] + "' data-entrada='" + data[6] + "' data-salida='" + data[4] + "'>" + data[1] + "</option>")
            })
            $('#almacenLogicoMovimiento').selectpicker('refresh');
        })
        .catch(function (error) {
            console.log('Hubo un problema con la petición Fetch:' + error.message);
        });

}
//Esta parte para listar los articulos correspondientes al almacen
$('#almacenLogicoMovimiento').change(function () {
    $('#articuloMovimiento option').remove();
    $('#articuloMovimiento').selectpicker('refresh');
    $(`#conceptoMovimiento option[value=${$(this).children('option:selected').data((document.getElementById('tipoMovimiento').value == "Entrada" ? 'entrada' : 'salida'))}]`).prop('selected', true);
    $('#conceptoMovimiento').selectpicker('refresh');
    $("#almacenFisicoMovimiento").val($(this).children('option:selected').data('id'));
    let params = {
        "idAlmacenLogico": $("#almacenLogicoMovimiento").val()
    };

    let query = Object.keys(params)
        .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(params[k]))
        .join('&');

    let url = '/getArticulosMultialmacen?' + query;

    fetch(url)
        .then(function (response) {
            if (response.ok) {
                console.log('Respuesta de red OK y respuesta HTTP OK');

            } else {
                console.log('Respuesta de red OK pero respuesta HTTP no OK');
            }
            return response.json();
        })
        .then(function (data) {
            data.forEach(function (data) {
                //aqui va el codigo
                $("#articuloMovimiento").append("<option value='" + data.idMaterial + "' data-tipo='" + data.tipo + "'data-idText='" + data.idText + "'data-unidadMedida='" + data.unidadMedida + "'>" + data.idText + '-' + data.nombreMaterial + "</option>")
            })
            $('#articuloMovimiento').selectpicker('refresh');
        })
        .catch(function (error) {
            console.log('Hubo un problema con la petición Fetch:' + error.message);
        });

});

//Agregamos el Articulo
$('#agregarArticulo').click(function () {
    var tipoMovimiento = $('#tipoMovimiento').val()
    if ($("#tipoMovimiento").val() == "Salida") {
        var idText = $('#articuloMovimiento').children('option:selected').data('idtext');
        var unidadMedida = $('#articuloMovimiento').children('option:selected').data('unidadmedida');
        var descripcion = $('#articuloMovimiento').children('option:selected').text();
        var tipo = $('#articuloMovimiento').children('option:selected').data('tipo');
        var id = $('#articuloMovimiento').val();
        var cantidad = $('#cantidadMovimiento').val();
        var lote = $('#rollo').children('option:selected').data('lote');
        var rollo = $('#rollo').children('option:selected').data('idtext');
        var idRollo = $("#rollo").val();
    } else {
        var idText = $('#articuloMovimiento').children('option:selected').data('idtext');
        var unidadMedida = $('#articuloMovimiento').children('option:selected').data('unidadmedida');
        var descripcion = $('#articuloMovimiento').children('option:selected').text();
        var tipo = $('#articuloMovimiento').children('option:selected').data('tipo');
        var id = $('#articuloMovimiento').val();
        var cantidad = $('#cantidadMovimiento').val();
        var lote = $("#lote").val();
        var rollo = null;
        var idRollo = null;
    }

    console.log(id);

    var temp = {
        'idText': idText,
        'unidadMedida': unidadMedida,
        'descripcion': descripcion,
        'tipo': tipo,
        'cantidad': cantidad,
        'id': id,
        'lote': lote,
        'rollo': rollo,
        'idRollo': idRollo
    }


    //validacion
    if (id == null || cantidad == null || id == "" || cantidad == "" || $('#tipoMovimiento').val() == null || $('#tipoMovimiento').val() == "") {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        })
    }
    else if ((lote == null || lote == "") && $('#tipoMovimiento').val() == 'Entrada' && tipo == 'tela') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        })
    }
    else if ((rollo == null || rollo == "") && $('#tipoMovimiento').val() == 'Salida' && tipo == 'tela') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        })
    }
    else {
        $('#tipoMovimiento').prop("disabled", true);
        $('#almacenLogicoMovimiento').prop("disabled", true);
        $('#tipoMovimiento').selectpicker('refresh');
        $('#almacenLogicoMovimiento').selectpicker('refresh');
        if (cantidad <= 0) {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'La cantidad debe ser mayor a 0!',
            })
        }

        else {
            $.ajax({
                type: "Get",
                url: "/getExistenciaArticulo",
                data: {
                    idAlmacenLogico: $('#almacenLogicoMovimiento').val(),
                    idArticulo: id,
                    Tipo: tipo

                },
                success: function (response) {
                    if (tipo != 'tela') {
                        $('#articuloMovimiento').find('[value=' + temp.id + ']').remove();
                        $('#articuloMovimiento').selectpicker('refresh');
                    }
                    if ((response - cantidad) < 0 && $('#tipoMovimiento').val() == 'Salida') {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'La cantidad existente es: ' + response
                        })
                        return false;
                    }
                    else if (($('#rollo').children('option:selected').data('cantidad') - cantidad) < 0 && $('#tipoMovimiento').val() == 'Salida' && tipo == 'tela') {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'La cantidad existente es: ' + $('#rollo').children('option:selected').data('cantidad')
                        })
                        return false;
                    }
                    else {
                        var fila = table.row.add(
                            [cantidad,
                                idText,
                                descripcion,
                                rollo == null ? 'N/A' : rollo,
                                unidadMedida,
                                tipo === 'tela' ? lote : 'N/A',
                                '<a class="btn btn-danger btn-circle btn-sm delete" onclick="deleteMovimiento(this,`' + id + tipo + '`)"><i class="fas fa-times text-white"></i></a>']
                        ).draw();
                        $("#lote").val('');
                        movimientos.push(temp);
                        if (tipoMovimiento == "Salida") {
                            $('#rollo').find(`[value=${idRollo}]`).remove();
                            $('#rollo').selectpicker('refresh');
                        }
                        var filterMovimientos = movimientos.filter(linea => linea.id + linea.tipo == id + 'tela');
                        var sumaMovimientos = filterMovimientos.reduce((acc, linea) => acc + parseFloat(linea.cantidad), 0);
                        $("#telaTomada").text(sumaMovimientos);

                    }
                }
            });
        }

    }
    console.log(movimientos);
});

function deleteMovimiento(fila, id) {
    var tipoMovimiento = $('#tipoMovimiento').val()
    const found = movimientos.find(element => element.id + element.tipo == id);
    console.log(found);
    if (found.tipo != 'tela') {
        $("#articuloMovimiento").append("<option value='" + found.id + "' data-tipo='" + found.tipo + "'data-idText='" + found.idText + "'data-unidadMedida='" + found.unidadMedida + "'>" + found.descripcion + "</option>");
        $('#articuloMovimiento').selectpicker('refresh');
    }

    // Se elimina el objeto del array de objetos
    var removeIndex = movimientos.map(function (item) { return item.id + item.tipo; }).indexOf(id);
    movimientos.splice(removeIndex, 1);
    table
        .row($(fila).parents('tr'))
        .remove()
        .draw();

    if ($('#articuloMovimiento').val()==found.id&&found.tipo == 'tela'&&tipoMovimiento=='Salida') {
        $('#rollo option').remove();
        $('#rollo').selectpicker('refresh');
        $.ajax({
            type: "GET",
            url: "/getRolloByidPedidoAndidTela",
            data: {
                'idPedido': $('#referenciaMovimiento').val(),
                'idTela': found.id,
                'estatus': 0
            },
            success: function (data) {
                var filterMovimientos = movimientos.filter(linea => linea.id + linea.tipo == found.id + 'tela');
                // esta parte es para filtrar los rollos que ya estaban agregados
                let difference=data;
                if (filterMovimientos.length > 0) {
                    difference = data.filter(function(x){
                        var condicion=true;
                        filterMovimientos.forEach(element => {
                            if (element.idRollo==x.idRolloTela) {
                                condicion= false;
                            }
                        });
                        return condicion;
                    });
                }
                difference.forEach(function (data) {
                    $("#rollo").append("<option value='" + data.idRolloTela + "' data-cantidad='" + data.cantidad + "' data-lote='" + data.lote + "' data-idText='" + data.idText + "'>" + data.idText + "-" + data.cantidad + "-" + data.lote + "</option>");
                })
                $('#rollo').selectpicker('refresh');
            }
        });
    }
}


$('#guardarMovimientos').click(function () {
    var observaciones = $('#observacionesMovimientos').val()
    var fechaMovimiento = $('#fechaMovimiento').val()
    var tipoMovimiento = $('#tipoMovimiento').val()
    var concepto = $('#conceptoMovimiento').val()
    var idAlmacenLogico = $('#almacenLogicoMovimiento').val()
    var idAlmacenFisico = $("#almacenLogicoMovimiento").children('option:selected').data('name')
    var idPedido = $("#referenciaMovimiento").val()
    console.log(movimientos);
    if (movimientos[0] == null || tipoMovimiento == null || concepto == null || idAlmacenLogico == null || fechaMovimiento == null || tipoMovimiento == "" || concepto == "" || idAlmacenLogico == "" || fechaMovimiento == "") {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        })
    }
    else {
        temp = {
            'observaciones': observaciones,
            'fechaMovimiento': fechaMovimiento,
            'tipoMovimiento': tipoMovimiento,
            'concepto': concepto,
            'idAlmacenLogico': idAlmacenLogico,
            'idAlmacenFisico': idAlmacenFisico,
            'idPedido': idPedido
        }
        movimientoCabecero.push(temp);
        console.log($("#referenciaMovimiento").val())
        if (temp.tipoMovimiento === 'Entrada') {
            $.ajax({
                type: "POST",
                url: "postMovimientosEntradaAlmacen",
                data: {
                    'cabecero': JSON.stringify(movimientoCabecero),
                    'movimientos': JSON.stringify(movimientos),
                    '_csrf': $('#token').val()
                },
                success: function (msg) {
                    console.log(msg.data);
                    console.log(JSON.stringify(movimientoCabecero));
                    if (msg.mensaje == "Error") {
                        Swal.fire({

                            position: 'center',
                            icon: 'error',
                            title: 'Un error ocurrio durante la entrada verifique los datos!',
                            showConfirmButton: false,
                            timer: 2500
                        }).then((result) => {
                            // Reload the Page
                            $(location).attr('href', '/movimientos-amp')
                        });
                    }
                    Swal.fire({

                        position: 'center',
                        icon: 'success',
                        title: 'Entrada generada correctamente!',
                        showConfirmButton: false,
                        timer: 2500
                    }).then((result) => {
                        // Reload the Page
                        const params = new URLSearchParams();
                        params.append('listIdText', JSON.stringify(msg.data));
                        params.append('format', "pdf");
                        console.log(params);
                        window.open("/rollotelabarcode?" + params);
                        $(location).attr('href', '/movimientos-amp')
                    });
                },
                error: (e) => {
                    Swal.fire({

                        position: 'center',
                        icon: 'error',
                        title: 'Un error ocurrio durante la entrada verifique los datos!',
                        showConfirmButton: false,
                        timer: 2500
                    }).then((result) => {
                        // Reload the Page
                        $(location).attr('href', '/movimientos-amp')

                    });
                }
            });
        } else {
            $.ajax({
                type: "POST",
                url: "postMovimientosSalidaAlmacen",
                data: {
                    'cabecero': JSON.stringify(movimientoCabecero),
                    'movimientos': JSON.stringify(movimientos),
                    '_csrf': $('#token').val()
                },
                success: function (msg) {

                    if (msg == "Error") {
                        Swal.fire({

                            position: 'center',
                            icon: 'error',
                            title: 'Un error ocurrio durante la salida verifique los datos!',
                            showConfirmButton: false,
                            timer: 2500
                        }).then((result) => {
                            // Reload the Page
                            $(location).attr('href', '/movimientos-amp')

                        });

                    }
                    Swal.fire({

                        position: 'center',
                        icon: 'success',
                        title: 'Salida generada correctamente!',
                        showConfirmButton: false,
                        timer: 2500
                    }).then((result) => {
                        // Reload the Page
                        $(location).attr('href', '/movimientos-amp')

                    });
                },
                error: (e) => {
                    Swal.fire({

                        position: 'center',
                        icon: 'error',
                        title: 'Un error ocurrio durante la salida verifique los datos!',
                        showConfirmButton: false,
                        timer: 2500
                    }).then((result) => {
                        // Reload the Page
                        $(location).attr('href', '/movimientos-amp')

                    });
                }
            });
        }

    }

});

$("#articuloMovimiento").change(function (e) {
    e.preventDefault();
    $('#rollo option').remove();
    $('#rollo').selectpicker('refresh');
    $("#lote").val('');
    var idText = $(this).children('option:selected').data('idtext');
    var idTela = $(this).val();
    if ($(this).children('option:selected').data('tipo') === 'tela') {
        $("#lote").prop("disabled", false);
    } else {
        $("#lote").prop("disabled", true);
    }
    //salida rollo
    if ($("#tipoMovimiento").val() == 'Salida' && $(this).children('option:selected').data('tipo') == 'tela' && $('#referenciaMovimiento').val() == '') {
        $("#rollo").prop("disabled", false);
        $("#lote").prop("disabled", true);
        $.ajax({
            type: "GET",
            url: "/getRolloByidAlmacenLogico",
            data: {
                'idAlmacenLogico': $("#almacenLogicoMovimiento").val(),
                'idTela': $(this).val(),
                'estatus': 1
            },
            success: function (data) {
                data.forEach(function (data) {
                    $("#rollo").append("<option value='" + data.idRolloTela + "' data-cantidad='" + data.cantidad + "' data-lote='" + data.lote + "' data-idText='" + data.idText + "'>" + data.idText + "-" + data.cantidad + "-" + data.lote + "</option>")
                })
                $('#rollo').selectpicker('refresh');
            }
        });
    }
    else if ($("#tipoMovimiento").val() == 'Salida' && $(this).children('option:selected').data('tipo') == 'tela' && $('#referenciaMovimiento').val() != '') {
        $("#rollo").prop("disabled", false);
        $("#lote").prop("disabled", true);
        $("#floating").removeClass("d-none");
        $.ajax({
            type: "GET",
            url: "/getRolloByidPedidoAndidTela",
            data: {
                'idPedido': $('#referenciaMovimiento').val(),
                'idTela': idTela,
                'estatus': 0
            },
            success: function (data) {
                var filterMovimientos = movimientos.filter(linea => linea.id + linea.tipo == idTela + 'tela');
                // esta parte es para filtrar los rollos que ya estaban agregados
                let difference=data;
                if (filterMovimientos.length > 0) {
                    difference = data.filter(function(x){
                        var condicion=true;
                        filterMovimientos.forEach(element => {
                            if (element.idRollo==x.idRolloTela) {
                                condicion= false;
                            }
                        });
                        return condicion;
                    });
                }
                difference.forEach(function (data) {
                    $("#rollo").append("<option value='" + data.idRolloTela + "' data-cantidad='" + data.cantidad + "' data-lote='" + data.lote + "' data-idText='" + data.idText + "'>" + data.idText + "-" + data.cantidad + "-" + data.lote + "</option>");
                })
                $('#rollo').selectpicker('refresh');
            }
        });
        $.ajax({
            type: "GET",
            url: "/getApartadoTelasByIdTela",
            data:
            {
                idPedido: $('#referenciaMovimiento').val(),
                idTela: idTela
            },
            success: function (data) {
                var filterMovimientos = movimientos.filter(linea => linea.id + linea.tipo == idTela + 'tela');
                var sumaMovimientos = filterMovimientos.reduce((acc, linea) => acc + parseFloat(linea.cantidad), 0);
                console.log(sumaMovimientos);
                $("#totalTela").text(data);
                $("#telaSurtida").text(idText);
                $("#telaTomada").text(sumaMovimientos);
            },
            error: function (e) {
                console.log(e);
                $("#totalTela").text(0.0);
                $("#telaSurtida").text('');
                $("#telaTomada").text(0.0);
            }
        });
    }
    else {
        $("#rollo").prop("disabled", true);
        $('#rollo').selectpicker('refresh');

    }
});

$("#rollo").change(function (e) {
    e.preventDefault();
    $('#cantidadMovimiento').val($(this).children('option:selected').data('cantidad'));
});

function checkDisabled() {
    $('#referenciaMovimiento option').remove();
    $('#referenciaMovimiento').selectpicker('refresh');
    $('#referenciaMovimiento').prop('disabled', true);
    $('#checkEnableReferencia').prop('checked', false);
    $('#referenciaMovimiento').selectpicker('refresh');
    $("#checkEnableReferencia").removeClass("d-none");
    $("#checkDisableReferencia").addClass("d-none");
}
function checkEnable() {
    $('#referenciaMovimiento option').remove();
    $('#referenciaMovimiento').selectpicker('refresh');
    $('#referenciaMovimiento').prop('disabled', false);
    $('#checkDisableReferencia').prop('checked', true);
    $('#referenciaMovimiento').selectpicker('refresh');
    $("#checkEnableReferencia").addClass("d-none");
    $("#checkDisableReferencia").removeClass("d-none");
    $.ajax({
        type: "GET",
        url: "/getPedidosByEstatus",
        data: {
            estatus: 1
        },
        success: function (data) {
            data.forEach(pedido => {
                $("#referenciaMovimiento").append("<option value='" + pedido.idPedidoInformacion + "'>" + pedido.idText + "</option>")
            })
            $('#referenciaMovimiento').selectpicker('refresh');
        }
    });
}