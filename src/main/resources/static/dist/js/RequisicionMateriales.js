var Materiales = [];
var ordenCompraDetalle = [];
$('#selectAll').click(function (e) {
    if ($(this).hasClass('checkedAll')) {
        $('.messageCheckbox').prop('checked', false);
        $(this).removeClass('checkedAll');
        $(".messageCheckbox").removeClass('checkedThis');
        var inputElements = document.getElementsByClassName('messageCheckbox');
        for (var i = 0; i < inputElements.length; ++i) {
            if (!inputElements[i].checked) {
                var removeIndex = Materiales.indexOf(+inputElements[i].value)
                Materiales.splice(removeIndex, 1);
            }
        }
    } else {
        $('.messageCheckbox').prop('checked', true);
        $(this).addClass('checkedAll');
        $(".messageCheckbox").addClass('checkedThis');
        var inputElements = document.getElementsByClassName('messageCheckbox');
        for (var i = 0; i < inputElements.length; ++i) {
            if (inputElements[i].checked) {
                Materiales.push(+inputElements[i].value);
            }
            Materiales = [...new Set(Materiales)];
        }
    }
    console.log(Materiales);
});
$(".messageCheckbox").change(function (e) {
    e.preventDefault();
    if ($(this).hasClass('checkedThis')) {
        $(this).removeClass('checkedThis');
        var removeIndex = Materiales.indexOf(+$(this).val())
        Materiales.splice(removeIndex, 1);
    } else {
        $(this).addClass('checkedThis');
        Materiales.push(+$(this).val());
    }
    console.log(+$(this).val());
    console.log(Materiales);
});

$('#checkMaterialAgotado').change(function (e) {
    e.preventDefault();
    var checkMaterialAgotado = $(this).is(":checked") ? true : false;
    if (checkMaterialAgotado) {
        $('#fechaPromesa').val("");
        $('#fechaPromesa').attr("disabled", true);
    }
    else {
        $('#fechaPromesa').attr("disabled", false);
    }
});

$('#guardarFechaPromesa').click(function (e) {
    e.preventDefault();
    var checkMaterialAgotado = $('#checkMaterialAgotado').is(":checked") ? true : false;
    var fechaPromesa = $('#fechaPromesa').val();
    console.log(`${fechaPromesa}  ${checkMaterialAgotado}`);
    if ((fechaPromesa == "" || fechaPromesa == null) && checkMaterialAgotado === false) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        })
    }
    else if (Materiales[0] == null) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Agrega al menos un material faltante!',
        })
    }
    else {
        $.ajax({
            type: "POST",
            url: "/postFechaPromesaMateriales",
            data: {
                Materiales: Materiales.toString(),
                _csrf: $("[name='_csrf']").val(),
                checkMaterialAgotado: checkMaterialAgotado,
                fechaPromesa: fechaPromesa
            },
            success: function (response) {
                Swal.fire({

                    position: 'center',
                    icon: 'success',
                    title: 'Fecha Promesa generada correctamente!',
                    showConfirmButton: false,
                    timer: 2500
                }).then((result) => {
                    location.reload();
                });
            },
            error: function (response) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Error al modificar los campos!',
                }).then((response) => {
                    location.reload();
                });
            }
        });
    }
});

function aceptarComercial(idMaterialFaltante) {
    Swal.fire({
        title: 'Estás seguro que quieres aceptar el material extemporaneo',
        text: "No podras revertir esta acción!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Aceptar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.value) {
            $.ajax({
                type: "PATCH",
                url: "/patchMaterialFaltanteEstatus",
                data: {
                    _csrf: $("[name='_csrf']").val(),
                    idMaterialFaltante: idMaterialFaltante,
                    estatusComercial: true
                },
                success: function (response) {
                    Swal.fire({
                        icon: 'success',
                        title: 'El material se acepto exitosamente',
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        location.reload();
                    })
                },
                error: function (response) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Error: ' + response,
                        timer: 1500
                    }).then((result) => {
                        location.reload();
                    })
                }
            });
        }
    })
}

function rechazarComercial(idMaterialFaltante) {
    Swal.fire({
        title: 'Estás seguro que quieres rechazar el material extemporaneo',
        text: "No podras revertir esta acción!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Aceptar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.value) {
            $.ajax({
                type: "PATCH",
                url: "/patchMaterialFaltanteEstatus",
                data: {
                    _csrf: $("[name='_csrf']").val(),
                    idMaterialFaltante: idMaterialFaltante,
                    estatusComercial: false
                },
                success: function (response) {
                    Swal.fire({
                        icon: 'success',
                        title: 'El material se rechazo exitosamente',
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        location.reload();
                    })
                },
                error: function (response) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Error: ' + response,
                        timer: 1500
                    }).then((result) => {
                        location.reload();
                    })
                }
            });
        }
    })
}

$('#btnGenerarOrden').click(function (e) {
    tableModal
        .clear()
        .draw();
    ordenCompraDetalle = [];
    if (Materiales[0] == null) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Agrega al menos un material faltante!',
        })
            .then((result) => {
                $('#generarOrden').modal('hide');
            })
    }
    else {
        $.ajax({
            type: "GET",
            url: "/getMaterialesFaltantesByIds",
            data: {
                ids: Materiales.toString()
            },
            success: function (response) {
                console.log(response);
                response.forEach(materialesFiltrados => {
                    var temp = {
                    		idMaterialFaltante: materialesFiltrados.idMaterialFaltante,
                        idMaterial: materialesFiltrados.idMaterial,
                        nombreMaterial: materialesFiltrados.nombreMaterial,
                        claveMaterial: materialesFiltrados.claveMaterial,
                        color: materialesFiltrados.color,
                        idProveedor: materialesFiltrados.idProveedor,
                        nombreProveedor: materialesFiltrados.nombreProveedor,
                        cantidad: materialesFiltrados.cantidad,
                        cantidadExtra: 0,
                        precioU: materialesFiltrados.precioUnitario,
                        montoCD: 0,
                        //

                    }
                    $('#proveedor').text(temp.nombreProveedor);
                    ordenCompraDetalle.push(temp);

                });
                console.log(ordenCompraDetalle)
                ordenCompraDetalle.forEach(detalle => {
                    var fila = tableModal.row.add(
                        [
                            detalle.cantidad,
                            `<input type="number" step="any" class="form-control" onInput='materialExtra(${detalle.idMaterialFaltante},this.value)'>`,
                            `<p id="materialExtra-${detalle.idMaterialFaltante}">${detalle.cantidad}</p>`,
                            detalle.claveMaterial,
                            detalle.nombreMaterial,
                            detalle.color,
                            `<input type="number" value="${detalle.precioU}" class="form-control" onInput='precioU(${detalle.idMaterialFaltante},this.value)'>`,
                            `<input type="number" value="0" step="any" onInput='porcentaje(${detalle.idMaterialFaltante},this.value)' class="form-control dc" id="porcentaje-${detalle.idMaterialFaltante}">`,
                            `<input type="number" value="0" step="any" onInput='monto(${detalle.idMaterialFaltante},this.value)' class="form-control dc" id="monto-${detalle.idMaterialFaltante}">`,
                            `<p id="subtotal-${detalle.idMaterialFaltante}">${detalle.cantidad * detalle.precioU}</p>`
                        ]
                    ).draw();
                });
                var proveedorArray = ordenCompraDetalle.map(orden => orden.idProveedor);

                if (!allEqual(proveedorArray)) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Solo puede haber un proveedor!',
                    }).then((result) => {
                        $('#generarOrden').modal('hide');
                    })
                }
                $(`.dc`).val(0);
                totales();
            }
        });
    }
});

function materialExtra(idMaterialFaltante, wrote) {
    console.log(ordenCompraDetalle);
    var indexMaterial = ordenCompraDetalle.map(item => item.idMaterialFaltante).indexOf(idMaterialFaltante);
    ordenCompraDetalle[indexMaterial].cantidadExtra = +wrote;
    const sub = (ordenCompraDetalle[indexMaterial].cantidad + ordenCompraDetalle[indexMaterial].cantidadExtra) * ordenCompraDetalle[indexMaterial].precioU
    var totalMetraje = ordenCompraDetalle[indexMaterial].cantidad + (wrote == "" ? 0 : ordenCompraDetalle[indexMaterial].cantidadExtra)
    $(`#materialExtra-${idMaterialFaltante}`).text(parseFloat(totalMetraje).toFixed(2));
    ordenCompraDetalle[indexMaterial].montoCD = 0;
    $(`#monto-${idMaterialFaltante}`).val(0);
    $(`#porcentaje-${idMaterialFaltante}`).val(0);
    $(`#subtotal-${idMaterialFaltante}`).text(parseFloat(sub).toFixed(2));
    totales();
}

function precioU(idMaterialFaltante, wrote) {
    var indexMaterial = ordenCompraDetalle.map(item => item.idMaterialFaltante).indexOf(idMaterialFaltante);
    ordenCompraDetalle[indexMaterial].precioU = +wrote;
    ordenCompraDetalle[indexMaterial].montoCD = 0;
    const sub = (ordenCompraDetalle[indexMaterial].cantidad + ordenCompraDetalle[indexMaterial].cantidadExtra) * ordenCompraDetalle[indexMaterial].precioU
    $(`#monto-${idMaterialFaltante}`).val(0);
    $(`#porcentaje-${idMaterialFaltante}`).val(0);
    $(`#monto-${idMaterialFaltante}`).attr("disabled", false);
    $(`#porcentaje-${idMaterialFaltante}`).attr("disabled", false);
    $(`#subtotal-${idMaterialFaltante}`).text(parseFloat(sub).toFixed(2));
    console.table(ordenCompraDetalle[indexMaterial]);
    totales();
}

function porcentaje(idMaterialFaltante, wrote) {
    var indexMaterial = ordenCompraDetalle.map(item => item.idMaterialFaltante).indexOf(idMaterialFaltante);
    if (wrote == "") {
        ordenCompraDetalle[indexMaterial].montoCD = 0;
        const sub = (ordenCompraDetalle[indexMaterial].cantidad + ordenCompraDetalle[indexMaterial].cantidadExtra) * ordenCompraDetalle[indexMaterial].precioU

        $(`#monto-${idMaterialFaltante}`).val(0);
        $(`#subtotal-${idMaterialFaltante}`).text(sub.toFixed(2));
        totales();

        return false
    }
    var monto = ((ordenCompraDetalle[indexMaterial].precioU * (ordenCompraDetalle[indexMaterial].cantidad + ordenCompraDetalle[indexMaterial].cantidadExtra)) / 100) * parseFloat(wrote)
    $(`#monto-${idMaterialFaltante}`).val(monto);
    ordenCompraDetalle[indexMaterial].montoCD = monto;
    $(`#subtotal-${idMaterialFaltante}`).text(((ordenCompraDetalle[indexMaterial].precioU * (ordenCompraDetalle[indexMaterial].cantidad + ordenCompraDetalle[indexMaterial].cantidadExtra)) + monto).toFixed(2));
    console.table(ordenCompraDetalle[indexMaterial]);
    totales();
}

function monto(idMaterialFaltante, wrote) {
    var indexMaterial = ordenCompraDetalle.map(item => item.idMaterialFaltante).indexOf(idMaterialFaltante);
    if (wrote == "") {
        ordenCompraDetalle[indexMaterial].montoCD = 0;
        const sub = (ordenCompraDetalle[indexMaterial].cantidad + ordenCompraDetalle[indexMaterial].cantidadExtra) * ordenCompraDetalle[indexMaterial].precioU

        $(`#porcentaje-${idMaterialFaltante}`).val(0);
        $(`#subtotal-${idMaterialFaltante}`).text(sub.toFixed(2));
        totales();

        return false
    }
    var porcentaje = (parseFloat(+wrote) / (ordenCompraDetalle[indexMaterial].precioU * (ordenCompraDetalle[indexMaterial].cantidad + ordenCompraDetalle[indexMaterial].cantidadExtra))) * 100
    $(`#porcentaje-${idMaterialFaltante}`).val(porcentaje);
    ordenCompraDetalle[indexMaterial].montoCD = parseFloat(wrote);
    $(`#subtotal-${idMaterialFaltante}`).text(((ordenCompraDetalle[indexMaterial].precioU * (ordenCompraDetalle[indexMaterial].cantidad + ordenCompraDetalle[indexMaterial].cantidadExtra)) + ordenCompraDetalle[indexMaterial].montoCD).toFixed(2));
    console.table(ordenCompraDetalle[indexMaterial]);
    totales();

}
$("#enviarOrden").click(function (e) {
    e.preventDefault();

    var ordenesPrecio = ordenCompraDetalle.map(orden => orden.precioU);
    var lengthOrdenesPrecio = ordenesPrecio.filter(precio => precio > 0).length;
    var iva = $(`#selectIva`).val();
    if (lengthOrdenesPrecio == 0) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todas los materiales deben tener un precio!',
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
    } else {
        $.ajax({
            type: "POST",
            url: "/postOrdenCompraMaterial",
            data: {
                _csrf: $('[name=_csrf]').val(),
                ordenCompraDetalle: JSON.stringify(ordenCompraDetalle),
                idProveedor: ordenCompraDetalle[0].idProveedor,
                iva: iva,
                ids:Materiales.toString()
            },
            success: function (response) {
                Swal.fire({
                    icon: 'success',
                    title: 'La orden se creo exitosamente',
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    location.reload();
                })
            },
            error: function (response) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Error: ' + response,
                    timer: 1500
                }).then((result) => {
                    location.reload();
                })
            }
        });
    }

});

function allEqual(arr) {
    return new Set(arr).size == 1;
}

$('#cerrarModalOrden').click(function (e) {
    e.preventDefault();
    Swal.fire({
        title: 'Estás seguro que quieres cerrar la ventana',
        text: "Se eliminaran todos los datos!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Aceptar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.value) {
            $('#generarOrden').modal('hide');
        }
    })
});

function totales() {
    const iva = +$(`#selectIva`).val() / 100;
    const totalIva = ordenCompraDetalle.reduce((acc, detalle) => acc + ((detalle.precioU * (detalle.cantidad + detalle.cantidadExtra)) + detalle.montoCD) * iva, 0);
    $(`#iva`).text('$' + totalIva.toFixed(2));
    const descuento = ordenCompraDetalle.reduce((acc, detalle) => acc + detalle.montoCD, 0);
    $(`#descuento`).text('$' + descuento.toFixed(2));
    const subtotal = ordenCompraDetalle.reduce((acc, detalle) => acc + detalle.precioU * (detalle.cantidad + detalle.cantidadExtra), 0);
    $(`#subtotal`).text('$' + subtotal.toFixed(2));
    $(`#total`).text('$' + (subtotal + totalIva + descuento).toFixed(2));
}
$(`#selectIva`).change(function (e) {
    e.preventDefault();
    totales();
});


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
        var indexMaterial = MaterialFaltanteList.map(material => material.idMaterialFaltante).indexOf(id);
        MaterialFaltanteList[indexMaterial].idProveedor=+idProveedor;
        MaterialFaltanteList[indexMaterial].nombreProveedor=nombre;
        $(`#nombreProveedor-${id}`).text(MaterialFaltanteList[indexMaterial].nombreProveedor);
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
});