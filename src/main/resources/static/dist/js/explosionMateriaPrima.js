var rollos = [];
var idTelas = [];
var telasInfo=[];
$(document).ready(function () {
    $.cookie.json = true;
    console.log($.cookie('rollosCookie'));
    if ($.cookie('rollosCookie') != undefined) {
        rollos = $.cookie('rollosCookie');
        var rolloIdTela = getUnique(rollos.map(rollo => rollo.idTela));
        rolloIdTela.forEach(idTela => {
            const findRollosByIdTela = rollos.filter(element => element.idTela == idTela);
            const apartado = findRollosByIdTela.reduce((ac, rollo) => ac + rollo.cantidad, 0);
            $("#apartado-" + idTela).text(apartado + parseFloat($(`#apartado-${idTela}`).data("content")));
            $("#disponible-" + idTela).text(($(`#disponible-${idTela}`).data("content") - apartado).toFixed(2));
            $("#faltante-" + idTela).text(($(`#consumo-${idTela}`).data("content") - apartado).toFixed(2));
        });


    }
});
function getExistenciaByAlmacen(idTela) {
    const findRollosByIdTela = rollos.filter(element => element.idTela == idTela);
    const apartado = findRollosByIdTela.reduce((ac, rollo) => ac + rollo.cantidad, 0);
    tablaMultialmacenes
        .clear()
        .draw();
    $("#mod-disponible").text((parseFloat($(`#disponible-${idTela}`).text())).toFixed(2));
    $("#mod-requerido").text($(`#consumo-${idTela}`).text());
    $("#mod-apartado").text(apartado + parseFloat($(`#apartado-${idTela}`).data("content")));
    $("#mod-restante").text($("#faltante-" + idTela).text());
    $.ajax({
        type: "GET",
        url: "/multialmacen-articulos",
        data:
        {
            tipo: 'tela',
            articulo: idTela,
        },
        success: function (data) {
            data.forEach(data => {
                const findRollosById = rollos.filter(element => element.idAlmacenLogico == data[6] && element.idTela == idTela);
                const apartadoByAlmacen = findRollosById.reduce((ac, rollo) => ac + rollo.cantidad, 0);
                if (data[4] == '1') {
                    tablaMultialmacenes.row.add(
                        [
                            data[1],
                            data[3],
                            `<p id="traspaso-${data[6]}-${idTela}">${apartadoByAlmacen}</p>`,
                            `<button id="modalTomar" class="btn btn-info btn-sm btn-circle popoverxd"
                            onclick='getExistenciaRolloByAlmacen(${data[6]},${idTela})' data-placement="top" data-content="Detalle"><i
                                class="fas fa-info"></i></button>`
                        ]
                    ).draw();
                }
            });

        }
    });
}

function getExistenciaRolloByAlmacen(idAlmacenLogico, idTela) {
    tablaMultialmacenesRollos
        .clear()
        .draw();
    console.table(rollos);
    console.log(idAlmacenLogico + " " + idTela)
    const findRollosById = rollos.filter(element => element.idAlmacenLogico == idAlmacenLogico && element.idTela == idTela);
    console.table(findRollosById);
    $('#selectRollo option').remove();
    $('#selectRollo').selectpicker('refresh');
    $.ajax({
        type: "GET",
        url: "/getRolloByidAlmacenLogico",
        data:
        {
            idAlmacenLogico: idAlmacenLogico,
            idTela: idTela,
            estatus: 1
        },
        success: function (response) {
            console.table(response);

            response.forEach(data => {
                $("#selectRollo").append("<option value='" + data.idRolloTela + "' data-cantidad='" + data.cantidad + "' data-lote='" + data.lote + "' data-idText='" + data.idText + "'  data-idAlmacenLogico='" + idAlmacenLogico + "' data-idTela='" + idTela + "'>" + data.idText + "-" + data.cantidad + "-" + data.lote + "</option>")
            });
            $('#selectRollo').selectpicker('refresh');
        }
    }).done(function () {
        findRollosById.forEach(tempRollo => {
            var fila = tablaMultialmacenesRollos.row.add(
                [
                    tempRollo.idText,
                    tempRollo.cantidad,
                    tempRollo.lote,
                    '<a class="btn btn-danger btn-circle btn-sm delete" onclick="deleteMovimiento(this,`' + tempRollo.idRollo + '`)"><i class="fas fa-times text-white"></i></a>'
                ]
            ).draw();
            $('#selectRollo').find('[value=' + tempRollo.idRollo + ']').remove();
            $('#selectRollo').selectpicker('refresh');
            $("#totalTela").text(findRollosById.reduce((ac, rollo) => ac + rollo.cantidad, 0));
        });
    });


}

function agregarRollo() {
    var idText = $('#selectRollo').children('option:selected').data('idtext');
    var cantidad = $('#selectRollo').children('option:selected').data('cantidad');
    var lote = $('#selectRollo').children('option:selected').data('lote');
    var idRollo = $('#selectRollo').val();
    var idTela = $('#selectRollo').children('option:selected').data('idtela');
    var idAlmacenLogico = $('#selectRollo').children('option:selected').data('idalmacenlogico');
    if (idRollo == '' || idRollo == null) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        })
        return false;
    }
    var tempRollo = {
        'idText': idText,
        'cantidad': cantidad,
        'lote': lote,
        'idRollo': idRollo,
        'idTela': idTela,
        'idAlmacenLogico': idAlmacenLogico
    }
    const findRollosByIdTela = rollos.filter(element => element.idTela == tempRollo.idTela);
    const apartado = findRollosByIdTela.reduce((ac, rollo) => ac + rollo.cantidad, 0);
    if (apartado >= $(`#consumo-${idTela}`).text()) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Ya tienes el metraje suficiente!',
        })
    }
    else {
        tablaMultialmacenesRollos
            .clear()
            .draw();
        rollos.push(tempRollo);
        $.cookie('rollosCookie', rollos, { expires: 7, path: $(location).attr('pathname') });
        $('#selectRollo').find('[value=' + tempRollo.idRollo + ']').remove();
        $('#selectRollo').selectpicker('refresh');
        const findRollosByIdTela = rollos.filter(element => element.idTela == tempRollo.idTela);
        const apartado = findRollosByIdTela.reduce((ac, rollo) => ac + rollo.cantidad, 0);
        const findRollosById = rollos.filter(element => element.idAlmacenLogico == tempRollo.idAlmacenLogico && element.idTela == tempRollo.idTela);
        const apartadoByAlmacen = findRollosById.reduce((ac, rollo) => ac + rollo.cantidad, 0);
        $(`#traspaso-${idAlmacenLogico}-${idTela}`).text(apartadoByAlmacen);
        findRollosById.forEach(tempRollo => {
            var fila = tablaMultialmacenesRollos.row.add(
                [
                    tempRollo.idText,
                    tempRollo.cantidad,
                    tempRollo.lote,
                    '<a class="btn btn-danger btn-circle btn-sm delete" onclick="deleteMovimiento(this,`' + tempRollo.idRollo + '`)"><i class="fas fa-times text-white"></i></a>'
                ]
            ).draw();
            $('#selectRollo').find('[value=' + tempRollo.idRollo + ']').remove();
            $('#selectRollo').selectpicker('refresh');
            $("#totalTela").text(findRollosById.reduce((ac, rollo) => ac + rollo.cantidad, 0));

        });
        //aqui es para los datos de requerido, apartado, disponible etc...
        $("#apartado-" + tempRollo.idTela).text(apartado + parseFloat($(`#apartado-${tempRollo.idTela}`).data("content")));
        $("#disponible-" + tempRollo.idTela).text((parseFloat($(`#disponible-${tempRollo.idTela}`).data("content")) - apartado).toFixed(2));
        $("#faltante-" + tempRollo.idTela).text((parseFloat($(`#consumo-${tempRollo.idTela}`).data("content")) - apartado).toFixed(2));
        $("#mod-apartado").text(apartado + parseFloat($(`#apartado-${tempRollo.idTela}`).data("content")));
        $("#mod-disponible").text((parseFloat($(`#disponible-${tempRollo.idTela}`).data("content")) - apartado).toFixed(2));
        $("#mod-restante").text((parseFloat($(`#consumo-${tempRollo.idTela}`).data("content")) - apartado).toFixed(2));

    }
}

function deleteMovimiento(fila, id) {
    // se vacia la tabla
    tablaMultialmacenesRollos
        .clear()
        .draw();
    //volvemos a poner los datos en el select 
    const found = rollos.find(element => element.idRollo == id);
    $("#selectRollo").append("<option value='" + id + "' data-cantidad='" + found.cantidad + "' data-lote='" + found.lote + "' data-idText='" + found.idText + "' data-idAlmacenLogico='" + found.idAlmacenLogico + "' data-idTela='" + found.idTela + "'>" + found.idText + "-" + found.cantidad + "-" + found.lote + "</option>")
    $('#selectRollo').selectpicker('refresh');
    // Se elimina el objeto del array de objetos
    var removeIndex = rollos.map(function (item) { return item.idRollo }).indexOf(id);
    rollos.splice(removeIndex, 1);
    $.cookie('rollosCookie', rollos, { expires: 7, path: $(location).attr('pathname') });
    //lo usamos para actulizar datos
    const findRollosByIdTela = rollos.filter(element => element.idTela == found.idTela);
    const apartado = findRollosByIdTela.reduce((ac, rollo) => ac + rollo.cantidad, 0);
    //aqui es para los datos de requerido, apartado, disponible etc...
    console.log(apartado)
    $("#mod-apartado").text(apartado + parseFloat($(`#apartado-${found.idTela}`).data("content")));
    $("#apartado-" + found.idTela).text(apartado + parseFloat($(`#apartado-${found.idTela}`).data("content")));
    $("#disponible-" + found.idTela).text((parseFloat($(`#disponible-${found.idTela}`).data("content")) - apartado).toFixed(2));
    $("#faltante-" + found.idTela).text((parseFloat($(`#consumo-${found.idTela}`).data("content")) - apartado).toFixed(2));
    $("#mod-disponible").text((parseFloat($(`#disponible-${found.idTela}`).data("content")) - apartado).toFixed(2));
    $("#mod-restante").text((parseFloat($(`#consumo-${found.idTela}`).data("content")) - apartado).toFixed(2));
    const findRollosById = rollos.filter(element => element.idAlmacenLogico == found.idAlmacenLogico && element.idTela == found.idTela);
    const apartadoByAlmacen = findRollosById.reduce((ac, rollo) => ac + rollo.cantidad, 0);
    $(`#traspaso-${found.idAlmacenLogico}-${found.idTela}`).text(apartadoByAlmacen);
    //se recorre de nuevo la tabla para actualizar los datos que vienen del json rollo
    findRollosById.forEach(tempRollo => {
        var fila = tablaMultialmacenesRollos.row.add(
            [
                tempRollo.idText,
                tempRollo.cantidad,
                tempRollo.lote,
                '<a class="btn btn-danger btn-circle btn-sm delete" onclick="deleteMovimiento(this,`' + tempRollo.idRollo + '`)"><i class="fas fa-times text-white"></i></a>'
            ]
        ).draw();
        $('#selectRollo').find('[value=' + tempRollo.idRollo + ']').remove();
        $('#selectRollo').selectpicker('refresh');
        $("#totalTela").text(findRollosById.reduce((ac, rollo) => ac + rollo.cantidad, 0));
    });
    if (findRollosById.length == 0) {
        $("#totalTela").text(0)
    }

}

function deleteRollos() {
    Swal.fire({
        icon: 'warning',
        title: '¿Seguro que quieres eliminar los datos?',
        showCancelButton: true,
        confirmButtonText: 'Aceptar',
        cancelButtonText: 'Cancelar',
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        reverseButtons: true
    }).then((result) => {
        if (result.value) {

            if ($.removeCookie('rollosCookie'), { path: $(location).attr('pathname') }) {
                $.removeCookie('rollosCookie', { path: $(location).attr('pathname') });
                location.reload();
            }
            else {
                alert("No hay datos");
            }
        }
    });
}

function getUnique(array) {
    var uniqueArray = [];

    for (i = 0; i < array.length; i++) {
        if (uniqueArray.indexOf(array[i]) === -1) {
            uniqueArray.push(array[i]);
        }
    }
    return uniqueArray;
}
function getUniqueId(array) {
    var uniqueArray = [];

    for (i = 0; i < array.length; i++) {
        if (uniqueArray.indexOf(array[i]) === -1) {
            uniqueArray.push(

                array[i]

            );
        }
    }
    return uniqueArray;
}

function guardar(idPedido) {

    var fields = document.getElementsByName("items[]");
    for (var i = 0; i < fields.length; i++) {
        idTelas.push(fields[i].value);
    }
    idTelas.forEach(idTela => {
        var telaFaltante=[];
        telaFaltante={
            'total':+$(`#consumo-${idTela}`).text(),
            'faltante':+$(`#faltante-${idTela}`).text(),
            'apartado':+$(`#apartado-${idTela}`).text(),
            'idTela':idTela,
        }
        telasInfo.push(telaFaltante);
    });
    var telasFaltantes=telasInfo.filter(tela=>tela.faltante>=0);
    $.ajax({
        type: "POST",
        url: "/postExplosionTelas",
        data: {
            'idPedido': idPedido,
            'rollos': JSON.stringify(rollos),
            'idAlmacenes': JSON.stringify(getUniqueId(rollos.map(rollo => rollo.idAlmacenLogico))),
            'telasFaltantes':JSON.stringify(telasFaltantes),
            '_csrf': $("[name='_csrf']").val(),
        },
        success: function (response) {
            Swal.fire({

                position: 'center',
                icon: 'success',
                title: 'Explosión generada correctamente!',
                showConfirmButton: false,
                timer: 2500
            }).then((result) => {
                $.removeCookie('rollosCookie', { path: $(location).attr('pathname') });
                $(location).attr('href', '/explosion-de-materiales')

            });
        },
        error: (e) => {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'La cantidad disponible cambio ó la tela no esta agregada al almacen de apartados!',
            })
        }
    });

    // var groupBy = function (xs, key) {
    //     return xs.reduce(function (rv, x) {
    //         (rv[x[key]] = rv[x[key]] || []).push(x);
    //         return rv;
    //     }, {});
    // };
    // var groubedByTeam = groupBy(rollos, 'idTela')
    // console.log(groubedByTeam);
}



