
$(function () {
    $("#modalSelectProcesos").modal("show");
    $("#horasContra").inputmask({ "mask": "9{1,3}.99" });
    $("#horasFavor").inputmask({ "mask": "9{1,3}.99" });
    $("#horasHombre").inputmask({ "mask": "9{1,3}.99" });
})


function ver() {
    $("#verFechas").modal("show");
    $('#fechaInicio').val(null);
    $('#fechaFin').val(null);
}

function buscarFechas() {
    let idProceso = $("#procesosActivos").val();
    if ($('#fechaInicio').val() == "" || $('#fechaFin').val() == "") {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Por favor complete el formulario!'
        })
    }
    else {
        var t = $('#table-horas').DataTable();
        var rows = t
            .rows()
            .remove()
            .draw();
        $.ajax({
            type: "GET",
            url: "/get_buscar_fechas_calendario",
            data: {
                'fechaInicio': $('#fechaInicio').val(),
                'fechaFin': $('#fechaFin').val(),
                'idProceso': idProceso
            },
            success: (data) => {
                data.map(data => {
                    if ($("#rolEditar").length > 0) {
                        t.row.add([
                            '<p>' + data.fecha + '</p>',
                            '<p id="hour_men-' + data.idCalendarioFecha + '">' + data.horasHombre.toFixed(2) + '</p>',
                            '<p id="hour_favor-' + data.idCalendarioFecha + '">' + data.horasFavor.toFixed(2) + '</p>',
                            '<p id="hour_adeudo-' + data.idCalendarioFecha + '">' + data.horasContra.toFixed(2) + '</p>',
                            '<p id="hour_habi-' + data.idCalendarioFecha + '">0</p>',

                            '<td>  <button id="editar-' + data.idCalendarioFecha + '" onclick="editar(' + data.idCalendarioProceso + ',' + data.idCalendarioFecha + ')"  class="btn btn-warning btn-circle btn-sm"  data-toggle="modal" data-target="#detalleTelas" ><i class="fas fa-pen"></i></button> </td>  '


                        ]).draw(false);
                    }
                    else {

                        t.row.add([
                            '<p>' + data[i][1] + '</p>',
                            '<p id="hour_men-' + data.idCalendarioFecha + '">' + data.horasHombre.toFixed(2) + '</p>',
                            '<p id="hour_favor-' + data.idCalendarioFecha + '">' + data.horasFavor.toFixed(2) + '</p>',
                            '<p id="hour_adeudo-' + data.idCalendarioFecha + '">' + data.horasContra.toFixed(2) + '</p>',
                            '<p id="hour_habi-' + data.idCalendarioFecha + '">0</p>',

                            '<td> Sin acciones </td>  '


                        ]).draw(false);
                    }
                })
                $('#verFechas').modal('toggle');

            },
            error: (e) => {
                console.log(e);
            }
        });
    }
}

function editar(idCalendarioProceso, idCalendarioFecha) {
    $('#idCalendarioProceso').val(idCalendarioProceso);
    $('#idCalendarioFecha').val(idCalendarioFecha);
    $('#horasHombre').val('');
    $('#horasFavor').val('');
    $('#horasContra').val('');
    $('#horasObservaciones').val('');
    if (idCalendarioProceso === 0) {
        $('#btnGuardarHoras').show();
        $('#btnEditarHoras').hide();
    } else {
        $('#btnGuardarHoras').hide();
        $('#btnEditarHoras').show();
    }
    $.ajax({
        type: "GET",
        url: `/calendarioProduccion/${idCalendarioProceso}`,
        success: (data) => {
            let { horasHombre, horasFavor, horasContra, comentarios } = data;
            $('#horasHombre').val(horasHombre.toFixed(2));
            $('#horasFavor').val(horasFavor.toFixed(2));
            $('#horasContra').val(horasContra.toFixed(2));
            $('#horasObservaciones').val(comentarios);
            $("#detallesFecha").modal("show");
        },
        error: (e) => {
            $("#detallesFecha").modal("show");
            console.log(e);
        }
    });
}
function guardarHoras() {
    //horasHombre
    //horasAdeudo
    //horasObservaciones
    let idProceso = $("#procesosActivos").val();
    if ($('#horasHombre').val().includes('M') ||
        $('#horasHombre').val().includes('H') ||
        $('#horasFavor').val().includes('M') ||
        $('#horasFavor').val().includes('H') ||
        $('#horasContra').val().includes('M') ||
        $('#horasContra').val().includes('H') ||
        $('#horasHombre').val() == "" ||
        $('#horasContra').val() == "" ||
        idProceso.trim() === "" ||
        $('#horasFavor').val() == "") {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Por favor complete el formulario!'
        })
    }
    else {

        $.ajax({
            type: "Post",
            url: "/calendarioProduccion",
            data: {
                '_csrf': $('[name="_csrf"]').val(),
                'idCalendarioFecha': $('#idCalendarioFecha').val(),
                'hombre': $('#horasHombre').val(),
                'contra': $('#horasContra').val(),
                'favor': $('#horasFavor').val(),
                'comentarios': $('#horasObservaciones').val(),
                'idProceso': idProceso
            },
            success: (data) => {
                console.log(data)

                $('#hour_men-' + $('#idCalendarioFecha').val()).text($('#horasHombre').val());
                $('#hour_adeudo-' + $('#idCalendarioFecha').val()).text($('#horasContra').val());
                $('#hour_favor-' + $('#idCalendarioFecha').val()).text($('#horasFavor').val());
                $('#hour_habi-' + $('#idCalendarioFecha').val()).text('0');
                $(`#editar-${data.idCalendarioFecha}`).attr('onclick', `editar(${data.idCalendarioProceso},${data.idCalendarioFecha})`);
                $('#detallesFecha').modal('toggle');
                Swal.fire({
                    icon: 'success',
                    title: 'Guardado!',
                    text: 'Se ha guardado el registro.'
                })
                // $("#detallesFecha").modal("show");

            },
            error: (e) => {
                console.log(e);
            }
        });


    }
}

function editarHoras() {
    $(`#btnEditarHoras`).attr("disabled", true);
    let csrf = $('[name="_csrf"]').val()
    let idCalendarioProceso = $('#idCalendarioProceso').val()
    let horasHombre = $('#horasHombre').val()
    let horasContra = $('#horasContra').val()
    let horasFavor = $('#horasFavor').val()
    let comentarios = $('#horasObservaciones').val()
    let idProceso = $("#procesosActivos").val();
    if ($('#horasHombre').val().includes('M') ||
        $('#horasHombre').val().includes('H') ||
        $('#horasFavor').val().includes('M') ||
        $('#horasFavor').val().includes('H') ||
        $('#horasContra').val().includes('M') ||
        $('#horasContra').val().includes('H') ||
        $('#horasHombre').val() == "" ||
        $('#horasContra').val() == "" ||
        idProceso.trim() === "" ||
        $('#horasFavor').val() == "") {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Por favor complete el formulario!'
        })
        $(`#btnEditarHoras`).attr("disabled", false);

    }
    else {
        let calendarioProceso = {
            'horasHombre': horasHombre,
            'horasContra': horasContra,
            'horasFavor': horasFavor,
            'comentarios': comentarios
        }

        $.ajax({
            type: "Put",
            url: `/calendarioProduccion/${idCalendarioProceso}?_csrf=${csrf}`,
            data:
                JSON.stringify(calendarioProceso)
            ,
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                Swal.fire({
                    icon: 'success',
                    title: 'Editado correctamente',
                    showConfirmButton: false,
                    timer: 2000
                }).then(function () {
                    console.log(data)
                    $('#hour_men-' + $('#idCalendarioFecha').val()).text($('#horasHombre').val());
                    $('#hour_adeudo-' + $('#idCalendarioFecha').val()).text($('#horasContra').val());
                    $('#hour_favor-' + $('#idCalendarioFecha').val()).text($('#horasFavor').val());
                    $('#hour_habi-' + $('#idCalendarioFecha').val()).text('0');
                    $(`#btnEditarHoras`).attr("disabled", false);
                    $('#detallesFecha').modal('hide');
                })
            },
            error: function (response) {
                $(`#btnEditarHoras`).attr("disabled", false);
            }
        });


    }
}

function validarHoras(text, nombreInput) {
    var arrayDeCadenas = text.split('.');
    var hours = parseInt(arrayDeCadenas[0]);
    var minute = parseInt(arrayDeCadenas[1]);

    if (parseInt(arrayDeCadenas[0]) < 10) {
        hours = '0' + hours;

    }
    if (parseInt(arrayDeCadenas[1]) > 59) {
        minute = '59';

    } else if (parseInt(arrayDeCadenas[1]) < 10) {
        minute = minute + '0';
    }
    $("#" + nombreInput).val(hours + '.' + minute);
}

function restarHoras(start, end) {
    s = start.split('.');
    e = end.split('.');
    min = s[1] - e[1];
    hour_carry = 0;
    if (min < 0) {
        min += 60;
        hour_carry += 1;
    }
    hour = s[0] - e[0] - hour_carry;

    if (hour < 10 && hour > 0) {
        hour = '0' + hour;

    } else if (hour < 0 && hour > -10) {
        hour = hour * -1;
        hour = '-0' + hour;
    }
    else if (hour == 0) {
        hour = '0' + hour;
    }
    if (min < 10) {
        min = '0' + min;
    }
    diff = hour + "." + min;

    return diff

}

function calendario() {

    $("#guardarYear").attr("disables", true);
    let year = $("#datepicker").val();
    if (year.trim() === '' || year == null) {
        Swal.fire({
            icon: 'error',
            title: 'Error!',
            text: 'Todos los campos son requeridos'
        })
        $("#guardarYear").attr("disables", false);
    } else {
        $.ajax({
            type: "GET",
            url: "/get_validar_calendario",
            data: {
                'year': year
            },
            success: function (data) {
                if (data == true) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error!',
                        text: 'Ya existe el calendario de este año.'
                    })
                }
                else {

                    $.ajax({
                        type: "POST",
                        url: "/post_crear_calendario",
                        data: {
                            '_csrf': $("[name='_csrf']").val(),
                            'year': year

                        },
                        success: function (data) {
                            if (data == true) {
                                Swal.fire({
                                    icon: 'success',
                                    title: 'Guardado!',
                                    text: 'Se ha guardado el registro.'
                                })
                            }
                            else {

                            }
                            $("#guardarYear").attr("disables", false);
                        }
                    })

                }
                console.log(data)
                //console.log(data)
            }
        })
    }
}

$("#datepicker").datepicker({
    format: "yyyy",
    viewMode: "years",
    minViewMode: "years"
});

function abrirCalendario() {
    $("#modalSelectYear").modal("show");
}

function listarTiempoProceso() {
    let idProceso = $("#procesosActivos").val();
    $("#listarTiempoProceso").attr("disabled", true);
    if (idProceso.trim() === "") {
        Swal.fire({
            icon: 'error',
            title: 'Error!',
            text: 'Seleccione un proceso'
        })
        $("#listarTiempoProceso").attr("disabled", false);
    } else {
        var d = new Date();
        let fechaInicio = d.addDays(-7).toISOString().split('T')[0]
        let fechaFin = d.addDays(7).toISOString().split('T')[0]
        var t = $('#table-horas').DataTable();
        var rows = t
            .rows()
            .remove()
            .draw();
        $.ajax({
            type: "GET",
            url: "/get_buscar_fechas_calendario",
            data: {
                'fechaInicio': fechaInicio,
                'fechaFin': fechaFin,
                'idProceso': idProceso
            },
            success: (data) => {
                data.map(data => {
                    if ($("#rolEditar").length > 0) {
                        t.row.add([
                            '<p>' + data.fecha + '</p>',
                            '<p id="hour_men-' + data.idCalendarioFecha + '">' + data.horasHombre.toFixed(2) + '</p>',
                            '<p id="hour_favor-' + data.idCalendarioFecha + '">' + data.horasFavor.toFixed(2) + '</p>',
                            '<p id="hour_adeudo-' + data.idCalendarioFecha + '">' + data.horasContra.toFixed(2) + '</p>',
                            '<p id="hour_habi-' + data.idCalendarioFecha + '">0</p>',

                            '<td>  <button id="editar-' + data.idCalendarioFecha + '" onclick="editar(' + data.idCalendarioProceso + ',' + data.idCalendarioFecha + ')"  class="btn btn-warning btn-circle btn-sm"  data-toggle="modal" data-target="#detalleTelas" ><i class="fas fa-pen"></i></button> </td>  '


                        ]).draw(false);
                    }
                    else {

                        t.row.add([
                            '<p>' + data[i][1] + '</p>',
                            '<p id="hour_men-' + data.idCalendarioFecha + '">' + data.horasHombre.toFixed(2) + '</p>',
                            '<p id="hour_favor-' + data.idCalendarioFecha + '">' + data.horasFavor.toFixed(2) + '</p>',
                            '<p id="hour_adeudo-' + data.idCalendarioFecha + '">' + data.horasContra.toFixed(2) + '</p>',
                            '<p id="hour_habi-' + data.idCalendarioFecha + '">0</p>',

                            '<td> Sin acciones </td>  '


                        ]).draw(false);
                    }
                })
            },
            error: (e) => {
                console.log(e);
            }
        });
        $("#modalSelectProcesos").modal("hide");
    }
}

Date.prototype.addDays = function (days) {
    var date = new Date(this.valueOf());
    date.setDate(date.getDate() + days);
    return date;
}