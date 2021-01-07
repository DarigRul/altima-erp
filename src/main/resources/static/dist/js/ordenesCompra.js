var iva = []
var ordenCabecero = []
var ordenDetalle = []
$(document).ready(function () {
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();

    today = mm + '/' + dd + '/' + yyyy;
    $("#curdate").text(today);
    $.ajax({
        type: "GET",
        url: "/getComercialLookupByTipo",
        data: {
            tipoLookup: 'iva'
        },
        success: function (response) {
            iva = response;
        }
    });
});

$("#proveedorSelect").change(function (e) {
    e.preventDefault();
    $('#claveMaterialProveedor option').remove();
    $('#claveMaterialProveedor').selectpicker('refresh');
    var id = $("#proveedorSelect").val();
    $.ajax({
        type: "GET",
        url: `/proveedor/${id}`,
        success: function (response) {
            $('#rfc').text(response.rfcProveedor);
            $('#calle').text(response.calle);
            $('#noext').text(response.numeroExterior);
            $('#noint').text(response.numeroInterior);
            $('#colonia').text(response.colonia);
            $('#cp').text(response.codigoPostal);
            $('#poblacion').text(response.poblacion);
            $('#pais').text(response.pais);
        },
        error: function (response) {
            console.log(response)
        }
    });
    $.ajax({
        type: "GET",
        url: `/materiales_all_by_proveedor/${id}`,
        success: function (response) {
            $("#claveMaterialProveedor").append(`<option value="">Seleccione uno...</option>`)
            response.forEach(function (data) {
                //aqui va el codigo
                $("#claveMaterialProveedor").append(`<option data-tipo='${data.tipo}' data-idtext='${data.id_text}' data-nombre='${data.nombre}' data-color='${data.color}' value='${data.id_material}'>${data.id_text} - ${data.nombre} </option>`)
            })
            $('#claveMaterialProveedor').selectpicker('refresh');
        },
        error: function (response) {
            console.log(response)
        }
    });
});

$('#agregarMaterial').click(function (e) {
    e.preventDefault();
    $(this).attr('disabled', true);
    var cantidad = $('#cantidadProveedor').val();
    var idMaterial = $('#claveMaterialProveedor').val();
    var color = $('#claveMaterialProveedor').children('option:selected').data('color') == '' ? $('#coloresProveedor').children('option:selected').data('name') : $('#claveMaterialProveedor').children('option:selected').data('color')
    var idText = $('#claveMaterialProveedor').children('option:selected').data('idtext')
    var nombre = $('#claveMaterialProveedor').children('option:selected').data('nombre')
    var tipo = $('#claveMaterialProveedor').children('option:selected').data('tipo')
    var idColor = $("#coloresProveedor").val();

    const found = ordenDetalle.find(element => element.idMaterial + element.tipo + element.idColor == idMaterial + tipo + idColor);
    if (found != null) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Registro duplicado!',
        }).then(result => {
            $(this).attr('disabled', false);
        })
        return false;
    }

    console.log(idMaterial);
    if (cantidad.trim() === '' || idMaterial.trim() === '' || cantidad <= 0) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        }).then(result => {
            $(this).attr('disabled', false);
        })

    }
    else {
        temp = {
            'cantidad': cantidad,
            'idMaterial': idMaterial,
            'tipo': tipo,
            'idColor': idColor,
            'iva': null,
            'precioU': 0,
            'montoCD': 0
        }
        ordenDetalle.push(temp);
        var fila = tableOrden.row.add(
            [cantidad,
                idText,
                nombre,
                color == "" ? 'N/A' : color,
                `<select class="form-control selectIva selectpicker" id="iva-${idMaterial}-${tipo}-${idColor}" onChange="ivaf('${idMaterial}-${tipo}-${idColor}',this.value)" > <option value="">Seleccione uno...</option> </select>`,
                `<input type="number" disabled class="form-control" id="precio-${idMaterial}-${tipo}-${idColor}" onInput="precioU('${idMaterial}-${tipo}-${idColor}',this.value,${cantidad})">`,
                `<input type="number" disabled class="form-control" id="monto-${idMaterial}-${tipo}-${idColor}" onInput="monto('${idMaterial}-${tipo}-${idColor}',this.value)" > `,
                `<input type="number" disabled class="form-control" id="porcentaje-${idMaterial}-${tipo}-${idColor}" onInput="porcentaje('${idMaterial}-${tipo}-${idColor}',this.value)" > `,
                `<p class="subtotal" id="subtotal-${idMaterial}-${tipo}-${idColor}">0</p>`,
                `<a class="btn btn-danger btn-circle btn-sm delete" onclick="deleteMovimiento(this,'${idMaterial}${tipo}${idColor}')"><i class="fas fa-times text-white"></i></a>`
            ]
        ).draw();
        $(this).attr('disabled', false);
        $('#selectIva').selectpicker('refresh');
        $.ajax({
            type: "GET",
            url: "getComercialLookupByTipo",
            data: {
                tipoLookup: 'iva'
            },
            success: function (response) {
                response.forEach(data => {
                    $(`#iva-${idMaterial}-${tipo}-${idColor}`).append(`<option value='${data.atributo1}'>${data.atributo1}%</option>`)
                });
                $(`#iva-${idMaterial}-${tipo}-${idColor}`).selectpicker('refresh');

            }
        });
    }
});

$("#claveMaterialProveedor").change(function (e) {
    e.preventDefault();
    var color = $(this).children('option:selected').data('color')
    console.log(color)
    $('#coloresProveedor option').remove();
    $("#coloresProveedor").append(`<option value="">Seleccione uno...</option>`)
    $('#coloresProveedor').selectpicker('refresh');
    if (color.trim() === '') {
        $('#coloresProveedor').attr('disabled', false);
        $('#coloresProveedor').selectpicker('refresh');
        $.ajax({
            type: "GET",
            url: "/getDisenioLookupByTipo",
            data: {
                tipo: 'color'
            },
            success: function (response) {
                console.log(response)
                response.forEach(function (data) {
                    //aqui va el codigo
                    $("#coloresProveedor").append(`<option data-content="<i class='fa fa-tint' style='color:${data.atributo1};'></i>  ${data.nombreLookup}" value='${data.idLookup}' data-name='${data.nombreLookup}' ></option>`)
                })
                $('#coloresProveedor').selectpicker('refresh');
            },
            error: function (response) {

            }
        });
    }
    else {
        $('#coloresProveedor').attr('disabled', true);
        $('#coloresProveedor').selectpicker('refresh');

    }
});

function deleteMovimiento(fila, id) {
    const ordenDetalleNew = ordenDetalle.filter(orden => orden.idMaterial + orden.tipo + orden.idColor != id);
    ordenDetalle = ordenDetalleNew;
    console.log(ordenDetalle);
    tableOrden
        .row($(fila).parents('tr'))
        .remove()
        .draw();
}

function precioU(id, wrote, cantidad) {
    var ivaSelected = parseFloat(($(`#iva-${id}`).val() / 100) + 1);
    var indexMaterial = ordenDetalle.map(orden => `${orden.idMaterial}-${orden.tipo}-${orden.idColor}`).indexOf(id);
    var sum = 0;
    console.log(indexMaterial);
    ordenDetalle[indexMaterial].precioU = +wrote;
    ordenDetalle[indexMaterial].montoCD = 0;
    $(`#monto-${id}`).val(0);
    $(`#porcentaje-${id}`).val(0);
    $(`#monto-${id}`).attr("disabled", false);
    $(`#porcentaje-${id}`).attr("disabled", false);
    $(`#subtotal-${id}`).text((parseFloat(wrote).toFixed(2) * parseInt(cantidad) * ivaSelected).toFixed(2));
    $('.subtotal').each(function () {
        sum += parseFloat($(this).text());
        $(`#total`).text('$'+sum.toFixed(2));
    });
}

function porcentaje(id, wrote) {
    var sum = 0;
    var ivaSelected = parseFloat(($(`#iva-${id}`).val() / 100) + 1);
    var indexMaterial = ordenDetalle.map(orden => `${orden.idMaterial}-${orden.tipo}-${orden.idColor}`).indexOf(id);
    if (wrote == "") {
        ordenDetalle[indexMaterial].montoCD = 0;
        $(`#monto-${id}`).val(0);
        $(`#subtotal-${id}`).text((ordenDetalle[indexMaterial].precioU * ordenDetalle[indexMaterial].cantidad * ivaSelected).toFixed(2));
        return false
    }
    var monto = (ordenDetalle[indexMaterial].precioU * ordenDetalle[indexMaterial].cantidad / 100) * parseFloat(wrote)
    $(`#monto-${id}`).val(monto.toFixed(2));
    ordenDetalle[indexMaterial].montoCD = monto;
    $(`#subtotal-${id}`).text(((ordenDetalle[indexMaterial].precioU * ordenDetalle[indexMaterial].cantidad * ivaSelected) + ordenDetalle[indexMaterial].montoCD).toFixed(2));
    $('.subtotal').each(function () {
        sum += parseFloat($(this).text());
        $(`#total`).text('$'+sum.toFixed(2));
    });
}

function monto(id, wrote) {
    var sum = 0;
    var ivaSelected = parseFloat(($(`#iva-${id}`).val() / 100) + 1);
    var indexMaterial = ordenDetalle.map(orden => `${orden.idMaterial}-${orden.tipo}-${orden.idColor}`).indexOf(id);
    if (wrote == "") {
        ordenDetalle[indexMaterial].montoCD = 0;
        $(`#porcentaje-${id}`).val(0);
        $(`#subtotal-${id}`).text((ordenDetalle[indexMaterial].precioU * ordenDetalle[indexMaterial].cantidad * ivaSelected).toFixed(2));
        return false
    }
    var porcentaje = (parseFloat(+wrote) / (ordenDetalle[indexMaterial].precioU * ordenDetalle[indexMaterial].cantidad)) * 100
    $(`#porcentaje-${id}`).val(porcentaje.toFixed(2));
    ordenDetalle[indexMaterial].montoCD = parseFloat(wrote);
    $(`#subtotal-${id}`).text(((ordenDetalle[indexMaterial].precioU * ordenDetalle[indexMaterial].cantidad * ivaSelected) + ordenDetalle[indexMaterial].montoCD).toFixed(2));
    $('.subtotal').each(function () {
        sum += parseFloat($(this).text());
        $(`#total`).text('$'+sum.toFixed(2));
    });
}

function ivaf(id, value) {
    console.log(value);
    if (value.trim() === '') {
        $(`#precio-${id}`).val(0);
        $(`#precio-${id}`).attr("disabled", true);
    } else {
        var indexMaterial = ordenDetalle.map(orden => `${orden.idMaterial}-${orden.tipo}-${orden.idColor}`).indexOf(id);
        ordenDetalle[indexMaterial].iva = +value;
        $(`#precio-${id}`).val(0);
        $(`#precio-${id}`).attr("disabled", false);
        $(`#iva-${id}`).attr("disabled", true);
    }

}

$("#enviarOrden").click(function (e) {
    e.preventDefault();
    $("#enviarOrden").attr('disabled', true);
    var ordenesPrecio = ordenDetalle.map(orden => orden.precioU);
    var lengthOrdenesPrecio = ordenesPrecio.filter(precio => precio === 0).length;
    if (lengthOrdenesPrecio > 0) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los materiales deben de tener un precio!',
        }).then(result => {
            $("#enviarOrden").attr('disabled', false);
        })

    } else {
        $.ajax({
            type: "POST",
            url: "/postOrdenCompra",
            data: {
                _csrf: $('[name=_csrf]').val(),
                ordenCompraDetalle: JSON.stringify(ordenDetalle),
                idProveedor: $(`#proveedorSelect`).val()
            },
            success: function (response) {
                Swal.fire({
                    icon: 'success',
                    title: 'La orden se creo exitosamente',
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    $(location).attr('href', '/orden-de-compra');
                })
            },
            error: function (response) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Error: ' + response,
                    timer: 1500
                }).then((result) => {
                    $(location).attr('href', '/orden-de-compra');
                })
            }
        });
    }

});