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
    $('#almacenFisicoMovimiento').val("");
    // let params = {
    //     "Tipo": movimiento
    // };

    // let query = Object.keys(params)
    //     .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(params[k]))
    //     .join('&');

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
                $("#almacenLogicoMovimiento").append("<option value='" + data[0] + "' data-id='" + data[3] + "'>" + data[1] + "</option>")
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
                $("#articuloMovimiento").append("<option value='" + data.idMaterial + "' data-tipo='" + data.tipo + "'data-idText='" + data.idText + "'data-unidadMedida='" + data.unidadMedida + "'>" + data.nombreMaterial + "</option>")
            })
            $('#articuloMovimiento').selectpicker('refresh');
        })
        .catch(function (error) {
            console.log('Hubo un problema con la petición Fetch:' + error.message);
        });
});

//Agregamos el Articulo
$('#agregarArticulo').click(function () {
    var idText = $('#articuloMovimiento').children('option:selected').data('idtext');
    var unidadMedida = $('#articuloMovimiento').children('option:selected').data('unidadmedida');
    var descripcion = $('#articuloMovimiento').children('option:selected').text();
    var tipo = $('#articuloMovimiento').children('option:selected').data('tipo');
    var id = $('#articuloMovimiento').val();
    var cantidad = $('#cantidadMovimiento').val();
    console.log(id);
    var temp = {
        'idText': idText,
        'unidadMedida': unidadMedida,
        'descripcion': descripcion,
        'tipo': tipo,
        'cantidad': cantidad,
        'id': id
    }



    const found = movimientos.find(element => element.idText == temp.idText);
    if (found != null) {
        return false;
    }

    //validacion
    if (id == null || cantidad == null || id == "" || cantidad == "") {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        })
    } else {
        if (cantidad <= 0) {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'La cantidad debe ser mayor a 0!',
            })
        } else {
            $('#articuloMovimiento').find('[value=' + temp.id + ']').remove();
            $('#articuloMovimiento').selectpicker('refresh');
            var fila = table.row.add(
                [cantidad,
                    idText,
                    descripcion,
                    unidadMedida,
                    '<a class="btn btn-danger btn-circle btn-sm delete" onclick="deleteMovimiento(this,`' + id + tipo + '`)"><i class="fas fa-times text-white"></i></a>' +
                    '<a data-toggle="modal" data-target="#cambioProveedor" class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-placement="top" data-content="Proveedor"><i class="fas fa-store text-white" style="margin-left: -2px;"></i></a>']
            ).draw();
            movimientos.push(temp);
        }

    }
    console.log(movimientos);
});

function deleteMovimiento(fila, id) {
    // Se agrega al select
    const found = movimientos.find(element => element.id + element.tipo == id);
    console.log(found);
    $("#articuloMovimiento").append("<option value='" + found.id + "' data-tipo='" + found.tipo + "'data-idText='" + found.idText + "'data-unidadMedida='" + found.unidadMedida + "'>" + found.descripcion + "</option>");
    $('#articuloMovimiento').selectpicker('refresh');

    // Se elimina el objeto del array de objetos
    var removeIndex = movimientos.map(function (item) { return item.id + item.tipo; }).indexOf(id);
    movimientos.splice(removeIndex, 1);
    table
        .row($(fila).parents('tr'))
        .remove()
        .draw();
}


$('#guardarMovimientos').click(function () {
    var observaciones = $('#observacionesMovimientos').val()
    var fechaMovimiento = $('#fechaMovimiento').val()
    var tipoMovimiento = $('#tipoMovimiento').val()
    var concepto = $('#conceptoMovimiento').val()
    var idAlmacenLogico = $('#almacenLogicoMovimiento').val()
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
            'idAlmacenLogico': idAlmacenLogico
        }
        movimientoCabecero.push(temp);
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

                    if (msg == "Error") {
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
                        timer:2500
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

