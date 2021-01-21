var materialesFaltantes = [];
$('#selectAll').click(function (e) {
    if ($(this).hasClass('checkedAll')) {
        $('.messageCheckbox').prop('checked', false);
        $(this).removeClass('checkedAll');
        $(".messageCheckbox").removeClass('checkedThis');
        var inputElements = document.getElementsByClassName('messageCheckbox');
        for (var i = 0; i < inputElements.length; ++i) {
            if (!inputElements[i].checked) {
                var removeIndex = materialesFaltantes.indexOf(+inputElements[i].value)
                materialesFaltantes.splice(removeIndex, 1);
            }
        }
    } else {
        $('.messageCheckbox').prop('checked', true);
        $(this).addClass('checkedAll');
        $(".messageCheckbox").addClass('checkedThis');
        var inputElements = document.getElementsByClassName('messageCheckbox');
        for (var i = 0; i < inputElements.length; ++i) {
            if (inputElements[i].checked) {
                materialesFaltantes.push(+inputElements[i].value);
            }
            materialesFaltantes = [...new Set(materialesFaltantes)];
        }
    }
    console.log(materialesFaltantes);
});
$(".messageCheckbox").change(function (e) {
    e.preventDefault();
    if ($(this).hasClass('checkedThis')) {
        $(this).removeClass('checkedThis');
        var removeIndex = materialesFaltantes.indexOf(+$(this).val())
        materialesFaltantes.splice(removeIndex, 1);
    } else {
        $(this).addClass('checkedThis');
        materialesFaltantes.push(+$(this).val());
    }
    console.log(+$(this).val());
    console.log(materialesFaltantes);
});

function generarOrden() {
    const params = new URLSearchParams();
    var materialesS = materialesList.filter(function (material) {
        console.log(material.idRequisicionAlmacenMaterial)
        var con = false;
        materialesFaltantes.forEach(data => {
            if (material.idRequisicionAlmacenMaterial == data) {
                con = true
            }
        });
        return con;
    })
    var proveedorArray = materialesS.map(orden => orden.idProveedor);

    if (materialesFaltantes[0] == null) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Agrega al menos un Material!',
        })
            .then((result) => {

            })
        return false;
    }
    else if (!allEqual(proveedorArray)) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Solo puede haber un proveedor!',
        }).then((result) => {
            $('#generarOrden').modal('hide');
        })
    }
    else {
        params.append('idMateriales', materialesFaltantes.toString());
        params.append("idProveedor", materialesS[0].idProveedor)
        console.log(params);
        window.location.href = "/listado-de-requisiciones-goc?" + params;
    }
}



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
            response.forEach(data => {
                $(`#selectIva`).append(`<option value='${data.atributo1}'>${data.atributo1}%</option>`)
            });
            $(`#selectIva`).selectpicker('refresh');
        }
    });

    materialesList.forEach(material => {
        temp = {
            'cantidad': material.cantidad,
            'idMaterial': material.idMaterial,
            'tipo': material.tipo,
            'idColor': material.idColor,
            'precioU': material.precio,
            'montoCD': 0
        }
        ordenDetalle.push(temp);
        $(`#selectIva`).change();
    });
    console.log(ordenDetalle);
    // if (cabecero !== null) {
    //     $('#rfc').text(proveedor.rfcProveedor);
    //     $('#calle').text(proveedor.calle);
    //     $('#noext').text(proveedor.numeroExterior);
    //     $('#noint').text(proveedor.numeroInterior);
    //     $('#colonia').text(proveedor.colonia);
    //     $('#cp').text(proveedor.codigoPostal);
    //     $('#poblacion').text(proveedor.poblacion);
    //     $('#pais').text(proveedor.pais);
    //     $("#proveedorSelect").append(`<option selected value="${proveedor.idProveedor}">${proveedor.nombreProveedor}</option>`)
    //     $("#proveedorSelect").attr("disabled",true)
    //     $('#proveedorSelect').change()
    //     $('#proveedorSelect').selectpicker('refresh');



    // }
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
                $("#claveMaterialProveedor").append(`<option data-almacen='${data.almacen}' data-precio='${data.precio}' data-tipo='${data.tipo}' data-idtext='${data.id_text}' data-nombre='${data.nombre}' data-color='${data.color}' value='${data.id_material}'>${data.id_text} - ${data.nombre} </option>`)
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
    $(`#proveedorSelect`).attr('disabled', true);
    $('#proveedorSelect').selectpicker('refresh');
    var cantidad = $('#cantidadProveedor').val();
    var idMaterial = $('#claveMaterialProveedor').val();
    var color = $('#claveMaterialProveedor').children('option:selected').data('color') == '' ? $('#coloresProveedor').children('option:selected').data('name') : $('#claveMaterialProveedor').children('option:selected').data('color')
    var idText = $('#claveMaterialProveedor').children('option:selected').data('idtext')
    var nombre = $('#claveMaterialProveedor').children('option:selected').data('nombre')
    var tipo = $('#claveMaterialProveedor').children('option:selected').data('tipo')
    var idColor = $("#coloresProveedor").val() === '' ? 0 : $("#coloresProveedor").val();
    var precio = $(`#claveMaterialProveedor`).children('option:selected').data('precio');
    var almacen = $(`#claveMaterialProveedor`).children('option:selected').data('almacen');
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
            'precioU': precio,
            'montoCD': 0
        }
        ordenDetalle.push(temp);
        var fila = tableOrden.row.add(
            [cantidad,
                idText,
                nombre,
                color == "" ? 'N/A' : color,
                almacen,
                `<input type="number"  class="form-control" value='${precio}' id="precio-${idMaterial}-${tipo}-${idColor}" onInput="precioU('${idMaterial}-${tipo}-${idColor}',this.value,${cantidad})">`,
                `<input type="number" class="form-control" id="monto-${idMaterial}-${tipo}-${idColor}" value='0' onInput="monto('${idMaterial}-${tipo}-${idColor}',this.value)" > `,
                `<input type="number" class="form-control" id="porcentaje-${idMaterial}-${tipo}-${idColor}" value='0' onInput="porcentaje('${idMaterial}-${tipo}-${idColor}',this.value)" > `,
                `<p class="subtotal" id="subtotal-${idMaterial}-${tipo}-${idColor}">${precio * cantidad}</p>`,
                `<a class="btn btn-danger btn-circle btn-sm delete" onclick="deleteMovimiento(this,'${idMaterial}${tipo}${idColor}')"><i class="fas fa-times text-white"></i></a>`
            ]
        ).draw();
        $(this).attr('disabled', false);
        totales();
    }
});

$("#claveMaterialProveedor").change(function (e) {
    e.preventDefault();
    var color = $(this).children('option:selected').data('color')
    console.log(color)
    $('#coloresProveedor option').remove();
    $("#coloresProveedor").append(`<option value="" data-name="">Seleccione uno...</option>`)
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
    console.log(ordenDetalle);
    const ordenDetalleNew = ordenDetalle.filter(orden => orden.idMaterial + orden.tipo + orden.idColor != id);
    ordenDetalle = ordenDetalleNew;
    console.log(ordenDetalle);
    tableOrden
        .row($(fila).parents('tr'))
        .remove()
        .draw();
    totales();

}

function cantidad(id, wrote) {
    var indexMaterial = ordenDetalle.map(orden => `${orden.idMaterial}-${orden.tipo}-${orden.idColor}`).indexOf(id);
    ordenDetalle[indexMaterial].cantidad = +wrote;
    const sub = ordenDetalle[indexMaterial].cantidad * ordenDetalle[indexMaterial].precioU
    console.log(`#subtotal-${id}`)
    ordenDetalle[indexMaterial].montoCD = 0;
    $(`#porcentaje-${id}`).val(0);
    $(`#monto-${id}`).val(0);
    $(`#subtotal-${id}`).text(parseFloat(sub).toFixed(2));
    totales();
}

function precioU(id, wrote, cantidad) {
    var indexMaterial = ordenDetalle.map(orden => `${orden.idMaterial}-${orden.tipo}-${orden.idColor}`).indexOf(id);
    var sum = 0;
    console.log(indexMaterial);
    ordenDetalle[indexMaterial].precioU = +wrote;
    ordenDetalle[indexMaterial].montoCD = 0;
    $(`#monto-${id}`).val(0);
    $(`#porcentaje-${id}`).val(0);
    $(`#monto-${id}`).attr("disabled", false);
    $(`#porcentaje-${id}`).attr("disabled", false);
    $(`#subtotal-${id}`).text((parseFloat(wrote).toFixed(2) * parseFloat(ordenDetalle[indexMaterial].cantidad)).toFixed(2));
    totales();

}

function porcentaje(id, wrote) {
    var sum = 0;
    var indexMaterial = ordenDetalle.map(orden => `${orden.idMaterial}-${orden.tipo}-${orden.idColor}`).indexOf(id);
    if (wrote == "") {
        ordenDetalle[indexMaterial].montoCD = 0;
        $(`#monto-${id}`).val(0);
        $(`#subtotal-${id}`).text((ordenDetalle[indexMaterial].precioU * ordenDetalle[indexMaterial].cantidad).toFixed(2));
        totales();
        return false
    }
    var monto = (ordenDetalle[indexMaterial].precioU * ordenDetalle[indexMaterial].cantidad / 100) * parseFloat(wrote)
    $(`#monto-${id}`).val(monto.toFixed(2));
    ordenDetalle[indexMaterial].montoCD = monto;
    $(`#subtotal-${id}`).text(((ordenDetalle[indexMaterial].precioU * ordenDetalle[indexMaterial].cantidad) + ordenDetalle[indexMaterial].montoCD).toFixed(2));
    totales();

}

function monto(id, wrote) {
    var sum = 0;
    var indexMaterial = ordenDetalle.map(orden => `${orden.idMaterial}-${orden.tipo}-${orden.idColor}`).indexOf(id);
    if (wrote == "") {
        ordenDetalle[indexMaterial].montoCD = 0;
        $(`#porcentaje-${id}`).val(0);
        $(`#subtotal-${id}`).text((ordenDetalle[indexMaterial].precioU * ordenDetalle[indexMaterial].cantidad).toFixed(2));
        totales();
        return false
    }
    var porcentaje = (parseFloat(+wrote) / (ordenDetalle[indexMaterial].precioU * ordenDetalle[indexMaterial].cantidad)) * 100
    $(`#porcentaje-${id}`).val(porcentaje.toFixed(2));
    ordenDetalle[indexMaterial].montoCD = parseFloat(wrote);
    $(`#subtotal-${id}`).text(((ordenDetalle[indexMaterial].precioU * ordenDetalle[indexMaterial].cantidad) + ordenDetalle[indexMaterial].montoCD).toFixed(2));
    totales();

}

$("#enviarOrden").click(function (e) {
    e.preventDefault();
    $("#enviarOrden").attr('disabled', true);
    var ordenesPrecio = ordenDetalle.map(orden => orden.precioU);
    var lengthOrdenesPrecio = ordenesPrecio.filter(precio => precio === 0).length;
    var iva = $(`#selectIva`).val();
    if (lengthOrdenesPrecio > 0) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los materiales deben de tener un precio!',
        }).then(result => {
            $("#enviarOrden").attr('disabled', false);
        })

    }
    else if (iva.trim() === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'El campo Iva es requerido!',
        }).then(result => {
            $("#enviarOrden").attr('disabled', false);
        })
    }
    else {
        $.ajax({
            type: "POST",
            url: "/postOrdenCompra",
            data: {
                _csrf: $('[name=_csrf]').val(),
                ordenCompraDetalle: JSON.stringify(ordenDetalle),
                idProveedor: idProveedor,
                iva: iva
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

$(`#selectIva`).change(function (e) {
    e.preventDefault();
    totales();

});

function totales() {
    const iva = +$(`#selectIva`).val() / 100;
    const totalIva = ordenDetalle.reduce((acc, detalle) => acc + ((detalle.precioU * detalle.cantidad) + detalle.montoCD) * iva, 0);
    $(`#iva`).text('$' + totalIva.toFixed(2));
    const descuento = ordenDetalle.reduce((acc, detalle) => acc + detalle.montoCD, 0);
    $(`#descuento`).text('$' + descuento.toFixed(2));
    const subtotal = ordenDetalle.reduce((acc, detalle) => acc + detalle.precioU * detalle.cantidad, 0);
    $(`#subtotal`).text('$' + subtotal.toFixed(2));
    $(`#total`).text('$' + (subtotal + totalIva + descuento).toFixed(2));
}
function allEqual(arr) {
    return new Set(arr).size == 1;
}

function cambiarProveedor(idMaterial, tipo, id) {
    $('#selectProveedor option').remove();
    $('#selectProveedor').selectpicker('refresh');
    $("#selectProveedor").append(`<option value="">Seleccione uno...</option>`)
    $.ajax({
        type: "GET",
        url: "/getProveedorByIdMaterialAndTipo",
        data: {
            id: idMaterial,
            tipo: tipo
        },
        success: function (responsetxt) {
            const response = JSON.parse(responsetxt);
            console.log(response);
            response.forEach(proveedor => {
                $("#selectProveedor").append(`<option value="${proveedor.idProveedor}" data-clavep="${proveedor.claveProveedor}" data-id="${id}" data-nombre="${proveedor.nombreProveedor}">${proveedor.nombreProveedor}</option>`)
            });
            $('#selectProveedor').selectpicker('refresh');
        },
        error: function (response) {
            console.log(response)
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'No existen proveedores para este material',
            }).then((result) => {
                $('#cambiarProveedor').modal('toggle');
                // $(location).attr('href', '/listado-de-requisiciones');
            })
        }
    });
}

$("#guardarProveedor").click(function (e) {
    $("#guardarProveedor").attr("disabled", true);
    e.preventDefault();
    const id = $("#selectProveedor").find(':selected').data('id');
    const clavep = $("#selectProveedor").find(':selected').data('clavep');
    const nombre = $("#selectProveedor").find(':selected').data('nombre');
    const idProveedor = $("#selectProveedor").val();

    if (idProveedor.trim() === '' || idProveedor == undefined) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Seleccione un proveedor!',
        }).then(result => {
            $("#guardarProveedor").attr('disabled', false);
        })
    } else {
        var indexMaterial = materialesList.map(material => material.idRequisicionAlmacenMaterial).indexOf(id);
        materialesList[indexMaterial].idProveedor=+idProveedor;
        materialesList[indexMaterial].nombreProveedor=nombre;
        materialesList[indexMaterial].modelo=clavep;
        $(`#nombreProveedor-${id}`).text(materialesList[indexMaterial].nombreProveedor);
        $(`#modelo-${id}`).text(materialesList[indexMaterial].modelo);
        Swal.fire({
            icon: 'success',
            title: 'El proveedor se cambio exitosamente!',
            showConfirmButton: false,
            timer: 1500
        }).then(() => {
            $('#cambiarProveedor').modal('toggle');
        })
        $("#guardarProveedor").attr('disabled', false);
    }
    console.table(materialesList)
});

