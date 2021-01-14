var telasFaltantes = [];
var ordenCompraDetalle = [];
$('#selectAll').click(function (e) {
    if ($(this).hasClass('checkedAll')) {
        $('.messageCheckbox').prop('checked', false);
        $(this).removeClass('checkedAll');
        $(".messageCheckbox").removeClass('checkedThis');
        var inputElements = document.getElementsByClassName('messageCheckbox');
        for (var i = 0; i < inputElements.length; ++i) {
            if (!inputElements[i].checked) {
                var removeIndex = telasFaltantes.indexOf(+inputElements[i].value)
                telasFaltantes.splice(removeIndex, 1);
            }
        }
    } else {
        $('.messageCheckbox').prop('checked', true);
        $(this).addClass('checkedAll');
        $(".messageCheckbox").addClass('checkedThis');
        var inputElements = document.getElementsByClassName('messageCheckbox');
        for (var i = 0; i < inputElements.length; ++i) {
            if (inputElements[i].checked) {
                telasFaltantes.push(+inputElements[i].value);
            }
            telasFaltantes = [...new Set(telasFaltantes)];
        }
    }
    console.log(telasFaltantes);
});
$(".messageCheckbox").change(function (e) {
    e.preventDefault();
    if ($(this).hasClass('checkedThis')) {
        $(this).removeClass('checkedThis');
        var removeIndex = telasFaltantes.indexOf(+$(this).val())
        telasFaltantes.splice(removeIndex, 1);
    } else {
        $(this).addClass('checkedThis');
        telasFaltantes.push(+$(this).val());
    }
    console.log(+$(this).val());
    console.log(telasFaltantes);
});

$('#checkTelaAgotada').change(function (e) {
    e.preventDefault();
    var checkTelaAgotada = $(this).is(":checked") ? true : false;
    if (checkTelaAgotada) {
        $('#fechaPromesa').val("");
        $('#fechaPromesa').attr("disabled", true);
    }
    else {
        $('#fechaPromesa').attr("disabled", false);
    }
});

$('#guardarFechaPromesa').click(function (e) {
    e.preventDefault();
    var checkTelaAgotada = $('#checkTelaAgotada').is(":checked") ? true : false;
    var fechaPromesa = $('#fechaPromesa').val();
    console.log(`${fechaPromesa}  ${checkTelaAgotada}`);
    if ((fechaPromesa == "" || fechaPromesa == null) && checkTelaAgotada === false) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        })
    }
    else if (telasFaltantes[0] == null) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Agrega al menos una Tela Faltante!',
        })
    }
    else {
        $.ajax({
            type: "POST",
            url: "/postFechaPromesa",
            data: {
                telasFaltantes: telasFaltantes.toString(),
                _csrf: $("[name='_csrf']").val(),
                checkTelaAgotada: checkTelaAgotada,
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

function aceptarComercial(idTelaFaltante) {
    Swal.fire({
        title: 'Estás seguro que quieres aceptar la tela extemporanea',
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
                url: "/patchTelaFaltanteEstatus",
                data: {
                    _csrf: $("[name='_csrf']").val(),
                    idTelaFaltante: idTelaFaltante,
                    estatusComercial: true
                },
                success: function (response) {
                    Swal.fire({
                        icon: 'success',
                        title: 'La Tela se acepto exitosamente',
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

function rechazarComercial(idTelaFaltante) {
    Swal.fire({
        title: 'Estás seguro que quieres rechazar la tela extemporanea',
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
                url: "/patchTelaFaltanteEstatus",
                data: {
                    _csrf: $("[name='_csrf']").val(),
                    idTelaFaltante: idTelaFaltante,
                    estatusComercial: false
                },
                success: function (response) {
                    Swal.fire({
                        icon: 'success',
                        title: 'La Tela se rechazo exitosamente',
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
    if (telasFaltantes[0] == null) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Agrega al menos una Tela Faltante!',
        })
            .then((result) => {
                $('#generarOrden').modal('hide');
            })
    }

    else {
        telasFaltantes.forEach(idTelaFaltante => {
            var telasFiltradas = telasFaltantesList.filter(telaFaltante => telaFaltante.idTelaFaltante == idTelaFaltante)
            var temp = {
                idTelaFaltante: telasFiltradas[0].idTelaFaltante,
                idTela: telasFiltradas[0].idTela,
                idTextTela: telasFiltradas[0].idTextTela,
                nombreTela: telasFiltradas[0].nombreTela,
                idProveedor: telasFiltradas[0].idProveedor,
                nombreProveedor: telasFiltradas[0].nombreProveedor,
                cantidad: telasFiltradas[0].cantidad,
                cantidadExtra: 0,
                precioU: telasFiltradas[0].precio,
                montoCD: 0

            }
            $('#proveedor').text(temp.nombreProveedor);
            ordenCompraDetalle.push(temp);
        });
        ordenCompraDetalle.forEach(detalle => {
            var fila = tableModal.row.add(
                [
                    detalle.cantidad,
                    `<input type="number" step="any" class="form-control" onInput='metrajeExtra(${detalle.idTelaFaltante},this.value)'>`,
                    `<p id="metrajeExtra-${detalle.idTelaFaltante}">${detalle.cantidad}</p>`,
                    detalle.idTextTela,
                    detalle.nombreTela,
                    `<input type="number" value="${detalle.precioU}" class="form-control" onInput='precioU(${detalle.idTelaFaltante},this.value)'>`,

                    `<input type="number" value="0" step="any" onInput='porcentaje(${detalle.idTelaFaltante},this.value)' class="form-control dc" id="porcentaje-${detalle.idTelaFaltante}">`,
                    `<input type="number" value="0" step="any" onInput='monto(${detalle.idTelaFaltante},this.value)' class="form-control dc" id="monto-${detalle.idTelaFaltante}">`,
                    `<p id="subtotal-${detalle.idTelaFaltante}">${detalle.cantidad * detalle.precioU}</p>`
                ]
            ).draw();
        });
    }
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
});

function metrajeExtra(idTelaFaltante, wrote) {
    console.log(ordenCompraDetalle);
    var indexTela = ordenCompraDetalle.map(item => item.idTelaFaltante).indexOf(idTelaFaltante);
    ordenCompraDetalle[indexTela].cantidadExtra = +wrote;
    const sub = (ordenCompraDetalle[indexTela].cantidad + ordenCompraDetalle[indexTela].cantidadExtra) * ordenCompraDetalle[indexTela].precioU
    var totalMetraje = ordenCompraDetalle[indexTela].cantidad + (wrote == "" ? 0 : ordenCompraDetalle[indexTela].cantidadExtra)
    $(`#metrajeExtra-${idTelaFaltante}`).text(totalMetraje.toFixed(2));
    ordenCompraDetalle[indexTela].montoCD = 0;
    $(`#monto-${idTelaFaltante}`).val(0);
    $(`#porcentaje-${idTelaFaltante}`).val(0);
    $(`#subtotal-${idTelaFaltante}`).text(parseFloat(sub).toFixed(2));
    totales();
}

function precioU(idTelaFaltante, wrote) {
    var indexTela = ordenCompraDetalle.map(item => item.idTelaFaltante).indexOf(idTelaFaltante);
    ordenCompraDetalle[indexTela].precioU = +wrote;
    ordenCompraDetalle[indexTela].montoCD = 0;
    const sub = (ordenCompraDetalle[indexTela].cantidad + ordenCompraDetalle[indexTela].cantidadExtra) * ordenCompraDetalle[indexTela].precioU
    $(`#monto-${idTelaFaltante}`).val(0);
    $(`#porcentaje-${idTelaFaltante}`).val(0);
    $(`#monto-${idTelaFaltante}`).attr("disabled", false);
    $(`#porcentaje-${idTelaFaltante}`).attr("disabled", false);
    $(`#subtotal-${idTelaFaltante}`).text(parseFloat(sub).toFixed(2));
    console.table(ordenCompraDetalle[indexTela]);
    totales();
}

function porcentaje(idTelaFaltante, wrote) {
    var indexTela = ordenCompraDetalle.map(item => item.idTelaFaltante).indexOf(idTelaFaltante);
    if (wrote == "") {
        ordenCompraDetalle[indexTela].montoCD = 0;
        const sub = (ordenCompraDetalle[indexTela].cantidad + ordenCompraDetalle[indexTela].cantidadExtra) * ordenCompraDetalle[indexTela].precioU

        $(`#monto-${idTelaFaltante}`).val(0);
        $(`#subtotal-${idTelaFaltante}`).text(sub.toFixed(2));
        totales();

        return false
    }
    var monto = ((ordenCompraDetalle[indexTela].precioU * (ordenCompraDetalle[indexTela].cantidad + ordenCompraDetalle[indexTela].cantidadExtra)) / 100) * parseFloat(wrote)
    $(`#monto-${idTelaFaltante}`).val(monto);
    ordenCompraDetalle[indexTela].montoCD = monto;
    $(`#subtotal-${idTelaFaltante}`).text(((ordenCompraDetalle[indexTela].precioU * (ordenCompraDetalle[indexTela].cantidad + ordenCompraDetalle[indexTela].cantidadExtra)) + monto).toFixed(2));
    console.table(ordenCompraDetalle[indexTela]);
    totales();
}

function monto(idTelaFaltante, wrote) {
    var indexTela = ordenCompraDetalle.map(item => item.idTelaFaltante).indexOf(idTelaFaltante);
    if (wrote == "") {
        ordenCompraDetalle[indexTela].montoCD = 0;
        const sub = (ordenCompraDetalle[indexTela].cantidad + ordenCompraDetalle[indexTela].cantidadExtra) * ordenCompraDetalle[indexTela].precioU

        $(`#porcentaje-${idTelaFaltante}`).val(0);
        $(`#subtotal-${idTelaFaltante}`).text(sub.toFixed(2));
        totales();

        return false
    }
    var porcentaje = (parseFloat(+wrote) / (ordenCompraDetalle[indexTela].precioU * (ordenCompraDetalle[indexTela].cantidad + ordenCompraDetalle[indexTela].cantidadExtra))) * 100
    $(`#porcentaje-${idTelaFaltante}`).val(porcentaje);
    ordenCompraDetalle[indexTela].montoCD = parseFloat(wrote);
    $(`#subtotal-${idTelaFaltante}`).text(((ordenCompraDetalle[indexTela].precioU * (ordenCompraDetalle[indexTela].cantidad + ordenCompraDetalle[indexTela].cantidadExtra)) + ordenCompraDetalle[indexTela].montoCD).toFixed(2));
    console.table(ordenCompraDetalle[indexTela]);
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
            text: 'Todas las telas deben tener un precio!',
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
            url: "/postOrdenCompraTela",
            data: {
                _csrf: $('[name=_csrf]').val(),
                ordenCompraDetalle: JSON.stringify(ordenCompraDetalle),
                idProveedor: ordenCompraDetalle[0].idProveedor,
                iva: iva
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