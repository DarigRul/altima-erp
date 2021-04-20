function modalEditarTiempos(idLookup, idTiempoFamiliaPrenda) {
    let { tiempoPrendaLisa, tiempoPrendaCuadros, tiempoPrendaFantasia, tiempoPrendaRayasVerticales, tiempoPrendaRayasHorizontales, tiempoTalla, tiempoRefilado } = getFields(idLookup);
    $('#tiempoPrendaLisa').val(tiempoPrendaLisa);
    $('#tiempoPrendaCuadros').val(tiempoPrendaCuadros);
    $('#tiempoPrendaFantasia').val(tiempoPrendaFantasia);
    $('#tiempoPrendaRayasVerticales').val(tiempoPrendaRayasVerticales);
    $('#tiempoPrendaRayasHorizontales').val(tiempoPrendaRayasHorizontales);
    $('#tiempoRefilado').val(tiempoRefilado);
    $('#tiempoTalla').val(tiempoTalla);
    $('#idTiempoFamiliaPrenda').val(idTiempoFamiliaPrenda);
    $('#idLookup').val(idLookup);
    $("#modalTiempoProceso").modal("show");
}

function guardarTiempos() {
    $("#btnGuardarTiempos").attr("disabled", true);
    let idLookup = $('#idLookup').val();
    let { tiempoPrendaLisa, tiempoPrendaCuadros, tiempoPrendaFantasia, tiempoPrendaRayasVerticales, tiempoPrendaRayasHorizontales, tiempoTalla, tiempoRefilado } = getValues();
    let idTiempoFamiliaPrenda = $('#idTiempoFamiliaPrenda').val();
    if (tiempoTalla.trim() === '' ||
        tiempoPrendaCuadros.trim() === '' ||
        tiempoPrendaLisa.trim() === '' ||
        tiempoPrendaFantasia.trim() === '' ||
        tiempoPrendaRayasVerticales.trim() === '' ||
        tiempoPrendaRayasHorizontales.trim() === '' ||
        tiempoRefilado.trim() === ''
    ) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        })
        return false;
    }
    let tiempos = {
        idFamiliaPrenda: idLookup,
        idTiempoFamiliaPrenda: idTiempoFamiliaPrenda,
        tiempoPrendaCuadros: tiempoPrendaCuadros,
        tiempoPrendaFantasia: tiempoPrendaFantasia,
        tiempoPrendaRayasVerticales: tiempoPrendaRayasVerticales,
        tiempoPrendaRayasHorizontales: tiempoPrendaRayasHorizontales,
        tiempoPrendaLisa: tiempoPrendaLisa,
        tiempoTalla: tiempoTalla,
        tiempoRefilado: tiempoRefilado,
    }
    if (idTiempoFamiliaPrenda.trim() === '') {
        $.ajax({
            type: "POST",
            url: `/api/tiempoFamiliaPrenda/?_csrf=${$("[name='_csrf']").val()}`,
            data:
                JSON.stringify(tiempos)
            ,
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                Swal.fire({
                    icon: 'success',
                    title: 'Los tiempos se editaron correctamente',
                    showConfirmButton: false,
                    timer: 2000
                }).then(function () {
                    updateTable(data);
                    $("#btnGuardarTiempos").attr("disabled", false);
                    $("#modalTiempoProceso").modal("hide");

                })
            },
            error: function (response) {
                $("#btnGuardarTiempos").attr("disabled", false);
            }
        });
    }
    else {
        $.ajax({
            type: "PUT",
            url: `/api/tiempoFamiliaPrenda/${idTiempoFamiliaPrenda}?_csrf=${$("[name='_csrf']").val()}`,
            data:
                JSON.stringify(tiempos)
            ,
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                Swal.fire({
                    icon: 'success',
                    title: 'Los tiempos se editaron correctamente',
                    showConfirmButton: false,
                    timer: 2000
                }).then(function () {
                    updateTable(data);
                    $("#btnGuardarTiempos").attr("disabled", false);
                    $("#modalTiempoProceso").modal("hide");

                })
            },
            error: function (response) {
                $("#btnGuardarTiempos").attr("disabled", false);
            }
        });
    }
}

function getFields(idLookup) {
    let fields = {
        tiempoTalla: $(`#tiempoTalla-${idLookup}`).text(),
        tiempoPrendaLisa: $(`#tiempoPrendaLisa-${idLookup}`).text(),
        tiempoPrendaCuadros: $(`#tiempoPrendaCuadros-${idLookup}`).text(),
        tiempoPrendaFantasia: $(`#tiempoPrendaFantasia-${idLookup}`).text(),
        tiempoPrendaRayasVerticales: $(`#tiempoPrendaRayasVerticales-${idLookup}`).text(),
        tiempoPrendaRayasHorizontales: $(`#tiempoPrendaRayasHorizontales-${idLookup}`).text(),
        tiempoRefilado: $(`#tiempoRefilado-${idLookup}`).text(),
    }
    return fields;
}
function getValues() {
    let values = {
        tiempoTalla: $(`#tiempoTalla`).val(),
        tiempoPrendaLisa: $(`#tiempoPrendaLisa`).val(),
        tiempoPrendaCuadros: $(`#tiempoPrendaCuadros`).val(),
        tiempoPrendaFantasia: $(`#tiempoPrendaFantasia`).val(),
        tiempoPrendaRayasVerticales: $(`#tiempoPrendaRayasVerticales`).val(),
        tiempoPrendaRayasHorizontales: $(`#tiempoPrendaRayasHorizontales`).val(),
        tiempoRefilado: $(`#tiempoRefilado`).val(),
    }
    return values;
}
function updateTable(data) {
    $(`#tiempoPrendaLisa-${data.idFamiliaPrenda}`).text(data.tiempoPrendaLisa)
    $(`#tiempoPrendaCuadros-${data.idFamiliaPrenda}`).text(data.tiempoPrendaCuadros)
    $(`#tiempoPrendaFantasia-${data.idFamiliaPrenda}`).text(data.tiempoPrendaFantasia)
    $(`#tiempoPrendaRayasVerticales-${data.idFamiliaPrenda}`).text(data.tiempoPrendaRayasVerticales)
    $(`#tiempoPrendaRayasHorizontales-${data.idFamiliaPrenda}`).text(data.tiempoPrendaRayasHorizontales)
    $(`#tiempoTalla-${data.idFamiliaPrenda}`).text(data.tiempoTalla)
    $(`#tiempoRefilado-${data.idFamiliaPrenda}`).text(data.tiempoRefilado)
}

function modalPrendasLargoTalle() {
    $("#modalPrendasLargoTalle").modal("show");
    let tablaPrendaCondicion = $("#tablaPrendaCondicion").DataTable();
    tablaPrendaCondicion
        .clear()
        .draw();
    $.ajax({
        type: "GET",
        url: "/api/prendasTiemposCondicion/",
        success: function (data) {
            data.map(prenda => {
                tablaPrendaCondicion.row.add([
                    prenda.familiaPrenda,
                    `<button onClick="eliminarPrendaCondicion(${prenda.idFamiliaPrenda})" class="btn btn-danger btn-circle btn-sm text-white popoverxd" data-container="body" data-toggle="popover" data-content="Declinar" data-original-title="" title=""><i class="fas fa-times"></i></button>`
                ]).draw(false)
            })
        }
    });
}

function eliminarPrendaCondicion(id) {
    Swal.fire({
        title: '¿Deseas eliminar la Familia Prenda?',
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Confirmar',
        confirmButtonColor: '#0288d1',
    }).then((result) => {
        if (result.value && id != null) {
            $.ajax({
                type: "DELETE",
                url: `/api/prendasTiemposCondicion/${id}`,
                data: {
                    '_csrf': $("[name='_csrf']").val(),
                },
                success: function () {
                    Swal.fire({
                        icon: 'success',
                        title: 'La familia prenda se elimino correctamente',
                        showConfirmButton: false,
                        timer: 2000
                    })
                    let tablaPrendaCondicion = $("#tablaPrendaCondicion").DataTable();
                    tablaPrendaCondicion
                        .clear()
                        .draw();
                    $.ajax({
                        type: "GET",
                        url: "/api/prendasTiemposCondicion/",
                        success: function (data) {
                            data.map(prenda => {
                                tablaPrendaCondicion.row.add([
                                    prenda.familiaPrenda,
                                    `<button onClick="eliminarPrendaCondicion(${prenda.idFamiliaPrenda})" class="btn btn-danger btn-circle btn-sm text-white popoverxd" data-container="body" data-toggle="popover" data-content="Declinar" data-original-title="" title=""><i class="fas fa-times"></i></button>`
                                ]).draw(false)
                            })
                        }
                    });
                },
                error: function () {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error en el servidor',
                        showConfirmButton: false,
                        timer: 2000
                    })
                }
            });
        }
    })
}

function guardarPrendas() {
    let idFamiliaPrenda = $('#familiaPrenda').val();
    let familiaPrenda = $('#familiaPrenda').find("option:selected").text();
    let data = {
        idFamiliaPrenda: idFamiliaPrenda,
        familiaPrenda: familiaPrenda
    }
    if (idFamiliaPrenda === undefined || familiaPrenda.trim() === undefined) {
        Swal.fire({
            icon: 'error',
            title: 'Todos los campos son obligatorios',
            showConfirmButton: false,
            timer: 2000
        })
    }
    else {
        $.ajax({
            type: "POST",
            url: `/api/prendasTiemposCondicion/?_csrf=${$("[name='_csrf']").val()}`,
            data:
                JSON.stringify(data)
            ,
            dataType: 'json',
            contentType: 'application/json',
            success: function (response) {
                Swal.fire({
                    icon: 'error',
                    title: 'Registro duplicado',
                    showConfirmButton: false,
                    timer: 2000
                })
            },
            error: function (data) {
                Swal.fire({
                    icon: 'success',
                    title: 'La familia de prenda se agrego correctamente',
                    showConfirmButton: false,
                    timer: 2000
                })
                let tablaPrendaCondicion = $("#tablaPrendaCondicion").DataTable();
                tablaPrendaCondicion
                    .clear()
                    .draw();
                $.ajax({
                    type: "GET",
                    url: "/api/prendasTiemposCondicion/",
                    success: function (data) {
                        data.map(prenda => {
                            tablaPrendaCondicion.row.add([
                                prenda.familiaPrenda,
                                `<button onClick="eliminarPrendaCondicion(${prenda.idFamiliaPrenda})" class="btn btn-danger btn-circle btn-sm text-white popoverxd" data-container="body" data-toggle="popover" data-content="Declinar" data-original-title="" title=""><i class="fas fa-times"></i></button>`
                            ]).draw(false)
                        })
                    }
                });
            },
        });
    }

}