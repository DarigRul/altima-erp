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
            timer: 2000
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
                    timer: 2000
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
                                timer: 2000
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
                        timer: 2000
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
                timer: 2000
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

function miniTrazo(id) {
    $(`#enviarMiniTrazo`).val(id);
    $(`.galeriarow`).empty();
    $.ajax({
        type: "GET",
        url: `/getMiniTrazoByIdPrenda/${id}`,
        success: function (response) {
            response.map(minitrazo => {
                $(`.galeriarow`).append(`
                <div class="col-md-4">
                    <div class="card" >
                        <a target="_blank" href="https://res.cloudinary.com/dti-consultores/image/upload/v1613432318/miniTrazo/${minitrazo.ruta}"><img class="card-img-top" src="https://res.cloudinary.com/dti-consultores/image/upload/v1613432318/miniTrazo/${minitrazo.ruta}" alt="Card image cap"></a>
                        <div class="card-body">
                            <p class="card-text">${minitrazo.descripcion}</p>
                            <button onClick="eliminarMiniTrazo('${minitrazo.idMiniTrazo}','${minitrazo.idPrenda}')" class="btn btn-danger deleteMT">Eliminar</button>
                        </div>
                    </div>
                </div>
                `);
            })
        },
        error: function (e) {
            $(`.galeriarow`).append(`
                <h1 class="display-3">No existen imagenes...</h1>
            `);
        }
    });
}

function agregarMiniTrazo() {
    $(".galeria").hide();
    $(`#agregarMiniTrazo`).hide();
    $(`#verMiniTrazo`).show();
    $(`.agregarMT`).show();
}

function verMiniTrazo() {
    $(".galeria").show();
    $(`#agregarMiniTrazo`).show();
    $(`#verMiniTrazo`).hide();
    $(`.agregarMT`).hide();
}

$("#enviarMiniTrazo").click(function (e) {
    e.preventDefault();
    const imagenMiniTrazo = $(`#imagenMiniTrazo`).val();
    const descripcion = $(`#descripcion`).val();
    const idPrenda = +$(this).val();
    if (imagenMiniTrazo.trim() === '' || descripcion.trim() === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        })
        return
    }

    console.log(idPrenda)
    // Get form
    var form = $('#fileUploadForm')[0];

    var data = new FormData(form);

    data.append("idPrenda", idPrenda);
    data.append("descripcion", descripcion)

    $("#enviarMiniTrazo").prop("disabled", true);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/guardarMiniTrazo",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            Swal.fire({
                icon: 'success',
                title: 'El mini trazo se agregó correctamente',
                showConfirmButton: false,
                timer: 2000
            })
            $(`#imagenMiniTrazo`).val("");
            $(`#descripcion`).val("");
            miniTrazo(idPrenda)
            console.log("SUCCESS : ", data);
            $("#enviarMiniTrazo").prop("disabled", false);
            $(".galeria").show();
            $(`#agregarMiniTrazo`).show();
            $(`#verMiniTrazo`).hide();
            $(`.agregarMT`).hide();


        },
        error: function (e) {

            console.log("ERROR : ", e);

        }
    });

});

function eliminarMiniTrazo(idMiniTrazo, idPrenda) {
    $(`.deleteMT`).attr("disabled", true);
    $.ajax({
        type: "DELETE",
        url: `/deleteMiniTrazo/${idMiniTrazo}`,
        data: {
            _csrf: $(`[name="_csrf"]`).val()
        },
        success: function (response) {
            Swal.fire({
                icon: 'success',
                title: 'El mini trazo se elimino correctamente',
                showConfirmButton: false,
                timer: 2000
            })
            miniTrazo(idPrenda)
            $(`.deleteMT`).attr("disabled", false);

        },
        error: function (e) {

            console.log("ERROR : ", e);

        }
    });

}