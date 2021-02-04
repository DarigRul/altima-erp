var idG;
function listRutas(id, idRuta) {
    $('#selectRutas option').remove();
    $('#selectRutas').selectpicker('refresh');
    idG = id;
    const opt = $("#selectRutas option").length;
        $.ajax({
            type: "GET",
            url: "listar-catalogo-produccion-procesos",
            data: {
                'Tipo': 'Ruta'
            },
            success: function (data) {
                $("#selectRutas").append(`<option>Selecciona uno...</option>`);
                data.forEach(function (data) {
                    //aqui va el codigo
                    $("#selectRutas").append(`<option  value="${data.idLookup}">${data.nombreLookup}</option>`)
                })
                $('#selectRutas').find(`[value=${idRuta}]`).attr("selected", "selected");
                $('#selectRutas').selectpicker('refresh');
            }
        });
}

$("#agregarRuta").click(function (e) {
    e.preventDefault();
    const idRuta = $(`#selectRutas`).val();
    if (idRuta.trim() === '' || idRuta.trim() === null) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        })
    }
    else {
        $(location).attr('href', `agregarIdRuta/${idG}/${idRuta}`)
    }
});