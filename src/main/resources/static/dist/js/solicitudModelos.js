var solicitudModelo = [];
var solicitudModeloDetalle = [];
$('#agenteVentas').change(function (e) {
    $('#cliente option').remove();
    $('#cliente').selectpicker('refresh');
    $.ajax({
        type: "GET",
        url: "/ListarClientesporAgente",
        data: {
            'idAgente': this.value
        },
        success: function (data) {
            data.forEach(function (data) {
                //aqui va el codigo

                $("#cliente").append("<option value='" + data[0] + "'>" + data[1] + "</option>")
            })
            $('#cliente').selectpicker('refresh');
        }
    });

});

$('#guardar').click(function (e) {
    e.preventDefault();
    $("#guardar").prop("disabled", true);
    var idAgenteVentas = $('#agenteVentas').val();
    var idCliente = $('#cliente').val();
    var fechaCita = $('#fechaCita').val();
    var horaSalida = $('#horaSalida').val();
    var cantidadDama = $('#cantidadDama').val();
    var cantidadCaballero = $('#cantidadCaballero').val();
    if (idAgenteVentas[0] == null || idCliente == null || fechaCita == null || horaSalida == null || idAgenteVentas == "" || idCliente == "" || fechaCita == "" || horaSalida == "") {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        })
        $("#guardar").prop("disabled", false);
    }
    else {
        temp = {
            'idAgenteVentas': idAgenteVentas,
            'idCliente': idCliente,
            'fechaCita': fechaCita,
            'horaSalida': horaSalida,
            'cantidadDama': cantidadDama,
            'cantidadCaballero': cantidadCaballero
        }
        solicitudModelo.push(temp);
        $.ajax({
            type: "POST",
            url: "postSolicitudModelo",
            data: {
                'solicitudModelo': JSON.stringify(solicitudModelo),
                '_csrf': $('[name=_csrf]').val()
            },
            success: function (msg) {

                if (msg == "Error") {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Error en el servidor!',
                    })
                    $("#guardar").prop("disabled", false);
                    location.reload();
                }
                Swal.fire({
                    icon: 'success',
                    title: 'La solicitud se guardo exitosamente',
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    location.reload();
                })

            },
            error: (e) => {
                $("#guardar").prop("disabled", false);
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Error: ' + e,
                })
                location.reload();
            }
        });


    }

});

function asignarModelos(idSolicitud) {
    $('#modeloNuevo option').remove();
    $('#modeloNuevo').selectpicker('refresh');
    $.ajax({
        type: "GET",
        url: "/getComercialLookupByTipo",
        data: {
            'tipoLookup': 'Modelo'
        },
        success: function (data) {
            data.forEach(function (data) {
                //aqui va el codigo
                $("#modeloNuevo").append("<option value='" + data.idLookup + "' data-id='" + idSolicitud + "' >" + data.nombreLookup + "</option>")
            })
            $('#modeloNuevo').selectpicker('refresh');
            $.ajax({
                type: "GET",
                url: "getModelosByIdSolicitud",
                data: {
                    'idSolicitud': idSolicitud
                },
                success: function (data) {
                    console.log(data);
                    tablaModelosMuestrario.rows().remove().draw();
                    for (i in data) {
                        tablaModelosMuestrario.row.add(
                            [
                                data[i].idModelo,
                                '<a class="btn btn-danger btn-circle btn-sm delete" onclick="deleteModelo(' + data[i].idSolicitudModeloDetalle + ' , ' + data[i].idSolicitudModelo + ')"><i class="fas fa-times text-white"></i></a>'
                            ]
                        ).draw();
                    }
                }
            });
        }
    });
}

$('#agregarModelo').click(function (e) {
    e.preventDefault();
    var idModelo = $('#modeloNuevo').val();
    var idSolicitud = $('#modeloNuevo').children('option:selected').data('id');

    $.ajax({
        type: "POST",
        url: "postSolicitudModeloDetalle",
        data: {
            '_csrf': $('[name=_csrf]').val(),
            'idModelo': idModelo,
            'idSolicitud': idSolicitud
        },
        success: function (data) {
            if (data == "Error") {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Error en el servidor!',
                })

            } else {
                Swal.fire({
                    icon: 'success',
                    title: 'El modelo se guardo exitosamente',
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    $.ajax({
                        type: "GET",
                        url: "getModelosByIdSolicitud",
                        data: {
                            'idSolicitud': idSolicitud
                        },
                        success: function (data) {
                            tablaModelosMuestrario.rows().remove().draw();
                            for (i in data) {
                                tablaModelosMuestrario.row.add(
                                    [
                                        data[i].idModelo,
                                        '<a class="btn btn-danger btn-circle btn-sm delete" onclick="deleteModelo(' + data[i].idSolicitudModeloDetalle + ' , ' + data[i].idSolicitudModelo + ')"><i class="fas fa-times text-white"></i></a>'
                                    ]
                                ).draw();
                            }
                        }
                    });
                })
            }

        },
        error: (e) => {
            $("#guardar").prop("disabled", false);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Error: ' + e,
            })
            location.reload();
        }
    });

});

function deleteModelo(id, idSolicitud) {
    $.ajax({
        type: "DELETE",
        url: "deleteSolicitudModeloDetalle",
        data: {
            '_csrf': $('[name=_csrf]').val(),
            'id': id
        },
        success: function (data) {
            if (data == "Error") {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Error: ' + e,
                })
            } else {
                Swal.fire({
                    icon: 'success',
                    title: '¡Modelo eliminado correctamente!',
                    showConfirmButton: false,
                    timer: 1500
                })
                $.ajax({
                    type: "GET",
                    url: "getModelosByIdSolicitud",
                    data: {
                        'idSolicitud': idSolicitud
                    },
                    success: function (data) {
                        tablaModelosMuestrario.rows().remove().draw();
                        for (i in data) {
                            tablaModelosMuestrario.row.add(
                                [
                                    data[i].idModelo,
                                    '<a class="btn btn-danger btn-circle btn-sm delete" onclick="deleteModelo(' + data[i].idSolicitudModeloDetalle + ' , ' + data[i].idSolicitudModelo + ')"><i class="fas fa-times text-white"></i></a>'
                                ]
                            ).draw();
                        }
                    }
                });

            }

        }
    });

}

//Aqui empieza el desarrollo de la parte de Agentes

$('#nuevaSolicitud').click(function (e) {
    $('#agenteVentas option').remove();
    $('#cliente option').remove();
    $('#modeloNuevoAgente option').remove();
    $.ajax({
        type: "GET",
        url: "/getComercialLookupByTipo",
        data: {
            'tipoLookup': 'Modelo'
        },
        success: function (data) {
            data.forEach(function (data) {
                //aqui va el codigo
                $("#modeloNuevoAgente").append("<option value='" + data.idLookup + "'>" + data.nombreLookup + "</option>")
            })
            $('#modeloNuevoAgente').selectpicker('refresh');
        }
    });
    $.ajax({
        type: "GET",
        url: "/getAgenteVentas",
        success: function (data) {
            data.forEach(function (data) {
                //aqui va el codigo

                $("#agenteVentas").append("<option value='" + data.idAgenteVentas + "'>" + data.nombreAgenteVentas + "</option>")
            })
            $('#agenteVentas').selectpicker('refresh');
        }
    });

});

$('#agregarNuevoModelo').click(function (e) {

    var idModelo = $("#modeloNuevoAgente").val();
    var nombreModelo = $('#modeloNuevoAgente option:selected').text();
    if (idModelo == null || idModelo == "" || nombreModelo == null || nombreModelo == "") {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Debes de elegir un Modelo!',
        })
    } else {
        $('#modeloNuevoAgente').find(`[value=${idModelo}]`).remove();
        $('#modeloNuevoAgente').selectpicker('refresh');
        tablaModelos.row.add([
            nombreModelo,
            `<a class="btn btn-danger btn-circle btn-sm delete" Onclick="deleteModeloAgente(this,${idModelo},'${nombreModelo}')"><i class="fas fa-times text-white"></i></a>`
        ]).draw(false);
        solicitudModeloDetalle.push(parseInt(idModelo));
    }

});

function deleteModeloAgente(row, idModelo, nombreModelo) {
    tablaModelos
        .row($(row).parents('tr'))
        .remove()
        .draw();
    const index = solicitudModeloDetalle.indexOf(idModelo);
    if (index > -1) {
        solicitudModeloDetalle.splice(index, 1);
    }
    $("#modeloNuevoAgente").append("<option value='" + idModelo + "'>" + nombreModelo + "</option>");
    $('#modeloNuevoAgente').selectpicker('refresh');
}

$("#guardarAgente").click(function (e) {
    e.preventDefault();
    var idAgente = $("#agenteVentas").val();
    var idCliente = $("#cliente").val();
    var fechaCita = $("#fechaCita").val();
    var horaSalida = $("#horaSalida").val();
    console.log(solicitudModeloDetalle[0]);
    if (idAgente == null || idCliente == null || fechaCita == null || horaSalida == null || idAgente == '' || idCliente == '' || fechaCita == '' || horaSalida == '' || solicitudModeloDetalle[0] == null) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        })
    } else {
        var temp = {
            'idAgente': idAgente,
            'idCliente': idCliente,
            'fechaCita': fechaCita,
            'horaSalida': horaSalida
        }
        solicitudModelo.push(temp);
        $.ajax({
            type: "POST",
            url: "postSolicitudModelosAgente",
            data: {
                '_csrf': $('[name=_csrf]').val(),
                'solicitudModeloDetalle': JSON.stringify(solicitudModeloDetalle),
                'solicitudModelo': JSON.stringify(solicitudModelo)
            },
            success: function (data) {
                if (data == "Error") {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Error en el servidor!',
                    }).then((result) => {
                        location.reload();
                      })

                } else {
                    Swal.fire({
                        icon: 'success',
                        title: 'El modelo se guardo exitosamente',
                        showConfirmButton: false,
                        timer: 1500
                    }).then((result) => {
                        location.reload();
                      })
                }
            },
            error: (e) => {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Error: ' + e,
                }).then((result) => {
                    location.reload();
                  })

            }
        });
    }


});

function verDetallePedidoModelo(idSolicitud) {
    $.ajax({
        type: "GET",
        url: "getModelosByIdSolicitud",
        data: {
            'idSolicitud': idSolicitud
        },
        success: function (data) {
            console.log(data);
            tablaModelosDetalle.rows().remove().draw();
            for (i in data) {
                tablaModelosDetalle.row.add(
                    [
                        data[i].idModelo,
                        data[i].telefono
                    ]
                ).draw();
            }
        }
    });
}

function clearTables() {
    tablaModelosDetalle.rows().remove().draw();
}