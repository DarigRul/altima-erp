function EditarEncogimiento(id) {
    $("#entretelaDefault").text("");
    $('#selectEntretela').selectpicker('val', 0);
    $.ajax({
        type: "GET",
        url: `/getTelaCalidad/${id}`,
        success: function (response) {
            $("#planchaLargo").val(response.planchaLargo)
            $("#bloqueLargo").val(response.bloqueLargo)
            $("#planchaAncho").val(response.planchaAncho)
            $("#bloqueAncho").val(response.bloqueAncho)
            let promedioLargo = (response.bloqueLargo + response.planchaLargo) / 2
            $("#promedioLargo").val(promedioLargo);
            let promedioAncho = (response.bloqueAncho + response.planchaAncho) / 2
            $("#promedioAncho").val(promedioAncho);
            $("#idTelaCalidad").val(response.idTelaCalidad);
        }
    })
    $.ajax({
        type: "GET",
        url: `/getIdEntretelaByIdTela/${id}`,
        success: function (response) {
            if (response.produccion === undefined) {
                $('#selectEntretela').selectpicker('val', response.diseno.idMaterial);
            }
            else {
                $('#selectEntretela').selectpicker('val', response.produccion.idMaterial);
            }
            $("#entretelaDefault").text(response.diseno.nombreMaterial);
        }
    })
    $("#idTelaE").val(id);
    $("#modalCalidadTela").modal("show");

}

$('#modalCalidadTela').on('hidden.bs.modal', function () {
    // do something…
    console.log("entra")
    $("#modalCalidadTela").modal("hide");
    $("#planchaLargo").val(0)
    $("#bloqueLargo").val(0)
    $("#planchaAncho").val(0)
    $("#bloqueAncho").val(0)
    $("#promedioLargo").val(0);
    $("#promedioAncho").val(0);
    $("#idTelaCalidad").val('');
    $("#idTelaE").val('');
})

$('input[type="file"]').change(function (e) {
    var fileName = e.target.files[0].name;
    $('.custom-file-label').html(fileName);
});

function calcularPromedioLargo() {
    let porcentajes = {
        planchaLargo: +$("#planchaLargo").val(),
        bloqueLargo: +$("#bloqueLargo").val()
    }
    console.log(porcentajes)
    let promedioLargo = (porcentajes.bloqueLargo + porcentajes.planchaLargo) / 2
    $("#promedioLargo").val(promedioLargo);

}
function calcularPromedioAncho() {
    let porcentajes = {
        bloqueAncho: +$("#bloqueAncho").val(),
        planchaAncho: +$("#planchaAncho").val()
    }
    console.log(porcentajes)
    let promedioAncho = (porcentajes.bloqueAncho + porcentajes.planchaAncho) / 2
    $("#promedioAncho").val(promedioAncho);

}

$("#guardarEncogimiento").click(function (e) {
    e.preventDefault();
    $(this).attr("disabled", true);
    let porcentajes = {
        bloqueAncho: +$("#bloqueAncho").val(),
        planchaAncho: +$("#planchaAncho").val(),
        planchaLargo: +$("#planchaLargo").val(),
        bloqueLargo: +$("#bloqueLargo").val(),
        idTela: $("#idTelaE").val(),
        idTelaCalidad: $("#idTelaCalidad").val(),
        idEntretela: +$("#selectEntretela").val()
    }
    let promedios = {
        promedioAncho: $("#promedioAncho").val(),
        promedioLargo: $("#promedioLargo").val()
    }

    if (promedios.promedioAncho == 0 && promedios.promedioLargo == 0) {
        $(this).attr("disabled", false);
        Swal.fire({
            icon: 'error',
            title: 'Ambos promedios son 0',
            showConfirmButton: true,
        })
    }
    else if (porcentajes.idEntretela === 0) {
        $(this).attr("disabled", false);
        Swal.fire({
            icon: 'error',
            title: 'Elige una entretela',
            showConfirmButton: true,
        })
    }
    else {
        $.ajax({
            type: "POST",
            url: `/postTelaCalidad?_csrf=${$("[name='_csrf']").val()}&promedioLargo=${promedios.promedioLargo}&promedioAncho=${promedios.promedioAncho}`,
            data:
                JSON.stringify(porcentajes)
            ,
            dataType: 'json',
            contentType: 'application/json',
            success: function (response) {
                Swal.fire({
                    icon: 'success',
                    title: 'Los porcentajes se guardaron correctamente',
                    showConfirmButton: false,
                    timer: 2000
                }).then(function () {
                    $(`#${porcentajes.idTela}-pruebaEncogimientoLargo`).text(promedios.promedioLargo);
                    $(`#${porcentajes.idTela}-pruebaEncogimientoAncho`).text(promedios.promedioAncho);
                    $("#guardarEncogimiento").attr("disabled", false);
                    $("#modalCalidadTela").modal("hide");

                })
            },
            error: function (response) {
                $("#guardarEncogimiento").attr("disabled", false);
            }
        });
    }
});

function documento(id) {
    $(`#idTela`).val(id);
    $(`.galeriarow`).empty();
    $.ajax({
        type: "GET",
        url: `/getDocumentoCalidadByIdTela/${id}`,
        success: function (response) {
            response.map(documento => {
                let date = new Date(documento.ultimaFechaModificacion)
                let day = date.getDate()
                let month = date.getMonth() + 1
                let year = date.getFullYear()

                if (month < 10) {
                    console.log(`${day}-0${month}-${year}`)
                } else {
                    console.log(`${day}-${month}-${year}`)
                }
                $(`.galeriarow`).append(`
                <div class="col-md-4">
                    <div class="card" >
                        <a target="_blank" href="https://res.cloudinary.com/dti-consultores/image/upload/v1613432318/documentosCalidad/${documento.ruta}"><img class="card-img-top" src="https://res.cloudinary.com/dti-consultores/image/upload/v1613432318/documentosCalidad/${documento.ruta}" alt="Card image cap"></a>
                        <div class="card-body">
                            <p class="card-text">${documento.descripcion}</p>
                            <p class="card-text">${day}-${month}-${year}</p>
                            <button onClick="eliminarMiniTrazo('${documento.idTelaCalidadImagen}','${documento.idTela}')" class="btn btn-danger">Eliminar</button>
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

function agregarDocumento() {
    $(".galeria").hide();
    $(`#agregarDocumento`).hide();
    $(`#verDocumento`).show();
    $(`.agregarD`).show();
}

function verDocumento() {
    $(".galeria").show();
    $(`#agregarDocumento`).show();
    $(`#verDocumento`).hide();
    $(`.agregarD`).hide();
}

$("#enviarDocumento").click(function (e) {
    e.preventDefault();
    const imagenDocumento = $(`#imagenDocumento`).val();
    const descripcion = $(`#descripcion`).val();
    const idTela = +$("#idTela").val();
    if (imagenDocumento.trim() === '' || descripcion.trim() === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben de estar llenos!',
        })
        return
    }

    console.log(idTela)
    // Get form
    var form = $('#fileUploadForm')[0];

    var data = new FormData(form);

    data.append("idTela", idTela);
    data.append("descripcion", descripcion)

    $("#enviarDocumento").prop("disabled", true);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/guardarDocumentoCalidad",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            Swal.fire({
                icon: 'success',
                title: 'El documento se agregó correctamente',
                showConfirmButton: false,
                timer: 2000
            })
            $(`#imagenDocumento`).val("");
            $(`#descripcion`).val("");
            documento(idTela)
            console.log("SUCCESS : ", data);
            $("#enviarDocumento").prop("disabled", false);
            $(".galeria").show();
            $(`#agregarDocumento`).show();
            $(`#verDocumento`).hide();
            $(`.agregarD`).hide();


        },
        error: function (e) {

            console.log("ERROR : ", e);

        }
    });

});

function eliminarMiniTrazo(idDocumento, idTela) {
    Swal.fire({
        title: `¿Estás seguro que quieres eliminar el documento?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Aceptar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.value) {
            $.ajax({
                type: "DELETE",
                url: `/deleteDocumentoCalidad/${idDocumento}`,
                data: {
                    _csrf: $(`[name="_csrf"]`).val()
                },
                success: function (response) {
                    Swal.fire({
                        icon: 'success',
                        title: 'El Documento se elimino correctamente',
                        showConfirmButton: false,
                        timer: 2000
                    })
                    documento(idTela)
                },
                error: function (e) {

                    console.log("ERROR : ", e);

                }
            });
        }
    })
}