var movimientos = [];
var movimientoCabecero=[];
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
    if (id == null || cantidad == null || id == "" || cantidad == "") {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        })
    } else {
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
    console.log(movimientos);
});

function deleteMovimiento(fila, id) {
    //Se elimina el objeto del array de objetos
    var removeIndex = movimientos.map(function (item) { return item.id + item.tipo; }).indexOf(id);
    movimientos.splice(removeIndex, 1);
    table
        .row($(fila).parents('tr'))
        .remove()
        .draw();
    console.log(movimientos);
}


$('#guardarMovimientos').click(function () {
    temp={
        'observaciones':$('#observacionesMovimientos').val(),
        'fechaMovimiento':$('#fechaMovimiento').val(),
        'tipoMovimiento':$('#tipoMovimiento').val(),
        'concepto':$('#conceptoMovimiento').val(),
        'idAlmacenLogico':$('#almacenLogicoMovimiento').val()
    }
    movimientoCabecero.push(temp);
    console.log(movimientoCabecero);

    $.ajax({
        type: "POST",
        url: "postMovimientosAlmacen",
        data: {
            'cabecero':JSON.stringify(movimientoCabecero),
            'movimientos':JSON.stringify(movimientos),
            '_csrf':$('#token').val()
        },
        success: function (msg) {
            if (msg=="Error") {
                alert("Error en el servidor");
            } 
        },
        error: (e) => {
            alert(e);
        }
    });
});