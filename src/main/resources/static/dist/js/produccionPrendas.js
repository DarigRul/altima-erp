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
            $("#selectRutas").append(`<option value="">Selecciona uno...</option>`);
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
    $(this).attr("disabled", true);
    if (idRuta.trim() === '' || idRuta.trim() === null) {
        $(this).attr("disabled", false);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        })
    }
    else {


        Swal.fire({
            icon: 'success',
            title: 'La ruta se agrego correctamente',
            showConfirmButton: false,
            timer: 1500
        }).then(() => {
            $(location).attr('href', `agregarIdRuta/${idG}/${idRuta}`)
        })
    }
});

function recibirProduccion(idPrenda, mostrar, ruta) {
    console.log(ruta)
    if (mostrar === 'true') {
        Swal.fire({
            title: `¿Estás seguro que quieres regresar la prenda?`,
            text: "Motivo",
            icon: 'warning',
            input: 'textarea',
            inputPlaceholder: 'Escribe la razón por la cual regresas esta prenda...',
            inputAttributes: {
                'aria-label': 'Type your message here'
            },
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Aceptar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.value.trim() !== '' || result.value == null) {
                const params = new URLSearchParams();
                params.append('devolucion', result.value);
                Swal.fire({
                    icon: 'success',
                    title: 'La prenda se regreso correctamente',
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    $(location).attr('href', `/regresar_prenda_produccion/${idPrenda}/?${params}`)
                })


            }
            else {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Todos los campos deben de estar llenos!',
                })
            }
        })
    } else {
        if (ruta == 'null') {
            let selectRutas = {};
            $.ajax({
                type: "GET",
                url: "listar-catalogo-produccion-procesos",
                data: {
                    'Tipo': 'Ruta'
                },
                success: function (data) {
                    data.forEach(function (data) {
                        selectRutas[data.idLookup] = data.nombreLookup;
                    })
                    console.log(selectRutas);

                    Swal.fire({
                        title: `¿Estás seguro que quieres recibir la prenda?`,
                        icon: 'warning',
                        input: 'select',
                        inputOptions: selectRutas,
                        inputPlaceholder: 'Selecciona una ruta',
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Aceptar',
                        cancelButtonText: 'Cancelar',
                    }).then((result) => {
                        console.log(result)
                        if (result.value.trim() !== '' || result.value == null) {
                            const params = new URLSearchParams();
                            params.append('idRuta', result.value);
                            Swal.fire({
                                icon: 'success',
                                title: 'La prenda se recibio correctamente',
                                showConfirmButton: false,
                                timer: 1500
                            }).then(() => {
                                $(location).attr('href', `/recibir_prenda_produccion/${idPrenda}/?${params}`)
                            })


                        }
                        else {
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: 'Todos los campos deben de estar llenos!',
                            })
                        }
                    })
                }
            });


        }
        else {
            Swal.fire({
                title: `¿Estás seguro que quieres recibir la prenda?`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Aceptar',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.value) {
                    Swal.fire({
                        icon: 'success',
                        title: 'La prenda se recibio correctamente',
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        $(location).attr('href', `/recibir_prenda_produccion/${idPrenda}`)
                    })


                }
            })
        }

    }

}

function listHistorico(id) {

    var tableHistorico = $('#tableHistorico').DataTable();
    tableHistorico.clear().draw(false);

    $.ajax({
        type: "GET",
        url: `getPrendaHistorico/${id}`,
        success: function (data) {
            data.forEach(function (historico) {
                console.log(historico)
                tableHistorico.row.add([
                    historico.tipo == 'devolver' ? `<div class="text-center"><button type="button" onClick="verComentario('${historico.comentario}')" class="btn btn-link">${historico.tipo}</button> </div>` : `<p class="text-center">${historico.tipo}</p>`,
                    historico.fecha,
                    historico.creadoPor
                ]).draw(false)
            })
        }
    });
}
function revisarPrenda(id) {
    Swal.fire({
        title: `¿Estás seguro que quieres revisar la prenda?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Aceptar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.value) {
            Swal.fire({
                icon: 'success',
                title: 'La prenda se reviso correctamente',
                showConfirmButton: false,
                timer: 1500
            }).then(() => {
                $(location).attr('href', `/revisarPrenda/${id}`)
            })


        }
    })
}

function verComentario(comentario) {
    console.log(comentario)
    Swal.fire({
        title: 'Comentario',
        text: comentario,
    })
}