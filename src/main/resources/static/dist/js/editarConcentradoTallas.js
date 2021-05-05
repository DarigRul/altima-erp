$(document).ready(async function () {
    const response = await getTallasByIdEmpleado();
    const { tallas } = response;
    const tablaTallas = $("#tablaTallas").DataTable();
    $('#idPedido').val(response.idPedido);
    $('#empleado').selectpicker('val', response.idEmpleado);
    $("#empleado").attr("disabled", true);
    $('#empleado').selectpicker('refresh')
    tallas.map(talla => {
        tablaTallas.row.add([
            talla.familiaPrenda,
            talla.talla,
            `<button class="btn btn-warning btn-circle btn-sm popoverxd"
                data-container="body" 
                data-toggle="popover" 
                data-content="Modificaciones" onClick="agregarModificacion(${talla.idConcentradoTallas})">
                <i class="fas fa-pencil-ruler"></i></button>
                <button class="btn btn-danger btn-circle btn-sm popoverxd"
                data-container="body" 
                data-toggle="popover" 
                data-content="Eliminar" onClick="eliminarPrendaTalla(${talla.idConcentradoTallas})">
                <i class="fas fa-times"></i></button>`
        ]).draw(false)
    })
});
function guardarTallaPrenda() {
    $('#btnGuardar').attr("disabled", true);
    const csrf = $("[name='_csrf']").val();
    const { idEmpleado, idFamiliaPrenda, idTalla, idLargo, idPedido } = getAllFields();
    if (idEmpleado.trim() === '' ||
        idFamiliaPrenda.trim() === '' ||
        idTalla === '' ||
        idLargo === ''
    ) {
        Swal.fire({
            icon: 'error',
            title: 'Error!',
            text: 'Todos los campos son requeridos!',
        })
        $('#btnGuardar').attr("disabled", false);
        return false;
    }
    const jsonTallaPrenda = {
        idEmpleadoPedido: idEmpleado,
        idTalla: idTalla === null ? 0 : +idTalla,
        idLargo: idLargo === null ? 0 : +idLargo,
        idFamiliaPrenda: idFamiliaPrenda,
        idPedido: +idPedido
    }
    console.log(jsonTallaPrenda)
    $.ajax({
        type: "POST",
        url: `/api/postTallaPrenda?_csrf=${csrf}`,
        data: JSON.stringify(jsonTallaPrenda),
        dataType: 'json',
        contentType: 'application/json',
        beforeSend: function () {
            Swal.fire({
                title: 'Cargando ',
                html: 'Por favor espere', // add html attribute if you want or remove
                allowOutsideClick: false,
                timerProgressBar: true,
                onBeforeOpen: () => {
                    Swal.showLoading()
                },
            });
        },
        success: async function (data) {
            Swal.fire({
                icon: 'success',
                title: 'La talla se agrego correctamente!',
            })
            const response = await getTallasByIdEmpleado();
            const { tallas } = response;
            const tablaTallas = $("#tablaTallas").DataTable();
            tablaTallas.clear();
            tallas.map(talla => {
                tablaTallas.row.add([
                    talla.familiaPrenda,
                    talla.talla,
                    `<button class="btn btn-warning btn-circle btn-sm popoverxd"
                        data-container="body" 
                        data-toggle="popover" 
                        data-content="Modificaciones" onClick="agregarModificacion(${talla.idConcentradoTallas})">
                        <i class="fas fa-pencil-ruler"></i></button>
                        <button class="btn btn-danger btn-circle btn-sm popoverxd"
                        data-container="body" 
                        data-toggle="popover" 
                        data-content="Eliminar" onClick="eliminarPrendaTalla(${talla.idConcentradoTallas})">
                        <i class="fas fa-times"></i></button>`
                ]).draw(false)
            })
            $('#btnGuardar').attr("disabled", false);
        },
        error: function () {
            Swal.fire({
                icon: 'error',
                title: 'Error!',
                text: 'Registro duplicado!',
            })
            $('#btnGuardar').attr("disabled", false);
        }
    });

}
function getAllFields() {
    const idEmpleado = $("#empleado").val();
    const idFamiliaPrenda = $("#prenda").val();
    const idGenero = $("#genero").val();
    const idTalla = $("#talla").val();
    const idLargo = $("#largo").val();
    const idPedido = $("#idPedido").val();
    const tallaPrenda = {
        idEmpleado: idEmpleado,
        idFamiliaPrenda: idFamiliaPrenda,
        idGenero: idGenero,
        idTalla: idTalla,
        idLargo: idLargo,
        idPedido: idPedido
    }
    return tallaPrenda;
}
function getAllFieldsModalEspecificacion() {
    const idConcentradoTalla = $("#idConcentradoTallas").val();
    const pulgada = $("#pulgada").val();
    const especificacion = $("#especificacion").val();
    const especificaciones = {
        idConcentradoTalla: idConcentradoTalla,
        pulgada: pulgada,
        especificacion: especificacion,
    }
    return especificaciones;
}
async function listarTallas(idGenero) {
    $('#talla').empty()
    $("#talla").selectpicker("refresh");
    const tallas = await getTallasByGenero(idGenero);
    tallas.map(talla => {
        $("#talla")
            .append(`<option value="${talla.idLookup}">${talla.nombreLookup}</option>`)

    })
    $("#talla").selectpicker("refresh");
}
async function getTallasByIdEmpleado() {
    let result;
    try {
        result = await $.ajax({
            type: "GET",
            url: `/api${window.location.pathname}`,
        });
        return result;
    } catch (error) {
        console.error(error);
        return [];
    }
}
async function getTallasByGenero(idGenero) {
    let result;
    try {
        result = await $.ajax({
            type: "GET",
            url: `/api/getTallasByGenero`,
            data: {
                'idGenero': idGenero
            }
        });
        return result;
    } catch (error) {
        console.error(error);
        return [];
    }
}
async function getModificacionesByIdConcentradoTalla(idConcentradoTalla) {
    let result;
    try {
        result = await $.ajax({
            type: "GET",
            url: `/api/getModificacionesByIdConcentradoTalla`,
            data: {
                'idConcentradoTalla': idConcentradoTalla
            }
        });
        return result;
    } catch (error) {
        console.error(error);
        return [];
    }
}
$('#checkTallaEspecial').click(function () {
    if ($(this).is(':checked')) {
        $("#talla").val(0).attr("disabled", true);
        $("#largo").val(0).attr("disabled", true);
    } else {
        $("#talla").val(undefined).attr("disabled", false);
        $("#largo").val(undefined).attr("disabled", false);
    }
    $("#talla").selectpicker("refresh");
    $("#largo").selectpicker("refresh");

})
function agregarModificacion(idConcentradoTallas) {
    $("#idConcentradoTallas").val(idConcentradoTallas);
    listarEspecificaciones(idConcentradoTallas);
    $("#modalEspecificaciones").modal("show");
}
function guardarModificacion(e) {
    const csrf = $("[name='_csrf']").val();
    $('#btnGuardarModificacion').attr("disabled", true);
    e.preventDefault();
    const { especificacion, pulgada, idConcentradoTalla } = getAllFieldsModalEspecificacion()
    console.log(idConcentradoTalla)
    if (especificacion.trim() === '' ||
        pulgada.trim() === '' ||
        pulgada.trim() == 0 ||
        idConcentradoTalla.trim() === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error!',
            text: 'Todos los campos son requeridos!',
        })
        $('#btnGuardarModificacion').attr("disabled", false);
        return false;
    }
    const jsonModificacion = {
        pulgadas: pulgada,
        idEspecificacion: especificacion,
        idConcentradoTalla: idConcentradoTalla
    }
    $.ajax({
        type: "POST",
        url: `/api/postTallaModificacion?_csrf=${csrf}`,
        data: JSON.stringify(jsonModificacion),
        dataType: 'json',
        contentType: 'application/json',
        beforeSend: function () {
            Swal.fire({
                title: 'Cargando ',
                html: 'Por favor espere', // add html attribute if you want or remove
                allowOutsideClick: false,
                timerProgressBar: true,
                onBeforeOpen: () => {
                    Swal.showLoading()
                },
            });
        },
        success: function (data) {
            Swal.fire({
                icon: 'success',
                title: 'La modificación se agrego correctamente!',
            })
            listarEspecificaciones(idConcentradoTalla);
            $('#btnGuardarModificacion').attr("disabled", false);
        },
        error: function () {
            Swal.fire({
                icon: 'error',
                title: 'Error!',
                text: 'Registro duplicado!',
            })
            $('#btnGuardarModificacion').attr("disabled", false);
        }
    });
}
async function listarEspecificaciones(idConcentradoTalla) {
    const modificaciones = await getModificacionesByIdConcentradoTalla(idConcentradoTalla);
    const tablaEspecificaciones = $("#tablaEspecificaciones").DataTable();
    tablaEspecificaciones.clear().draw(false);
    modificaciones.map(modificacion => {
        tablaEspecificaciones.row.add([
            modificacion.especificacion,
            modificacion.pulgadas,
            `<button class="btn btn-danger btn-circle btn-sm popoverxd"
                data-container="body" 
                data-toggle="popover" 
                data-content="Eliminar" onClick="eliminarModificacion(${modificacion.idModificacion},${idConcentradoTalla})"><i class="fas fa-times"></i></button>`
        ]).draw(false)
    })
}
function eliminarModificacion(idModificacion, idConcentradoTalla) {
    Swal.fire({
        title: `¿Estás seguro que quieres eliminar la modificación?`,
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
                url: `/api/deleteModificacion/${idModificacion}`,
                data: {
                    _csrf: $(`[name="_csrf"]`).val()
                },
                success: function () {
                    listarEspecificaciones(idConcentradoTalla)
                    Swal.fire({
                        icon: 'success',
                        title: 'La modificación se elimino correctamente',
                        showConfirmButton: false,
                        timer: 2000
                    })
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                    alert("Error en el servidor")
                }
            });
        }
    })
}
function eliminarPrendaTalla(idConcentradoTalla) {
    Swal.fire({
        title: `¿Estás seguro que quieres eliminar la talla?`,
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
                url: `/api/deletePrendaTalla/${idConcentradoTalla}`,
                data: {
                    _csrf: $(`[name="_csrf"]`).val()
                },
                success:async function () {
                    const response = await getTallasByIdEmpleado();
                    const { tallas } = response;
                    const tablaTallas = $("#tablaTallas").DataTable();
                    tablaTallas.clear().draw(false);
                    Swal.fire({
                        icon: 'success',
                        title: 'La talla se elimino correctamente',
                        showConfirmButton: false,
                        timer: 2000
                    })
                    if (tallas===undefined) return false; 
                    tallas.map(talla => {
                        tablaTallas.row.add([
                            talla.familiaPrenda,
                            talla.talla,
                            `<button class="btn btn-warning btn-circle btn-sm popoverxd"
                                data-container="body" 
                                data-toggle="popover" 
                                data-content="Modificaciones" onClick="agregarModificacion(${talla.idConcentradoTallas})">
                                <i class="fas fa-pencil-ruler"></i></button>
                                <button class="btn btn-danger btn-circle btn-sm popoverxd"
                                data-container="body" 
                                data-toggle="popover" 
                                data-content="Eliminar" onClick="eliminarPrendaTalla(${talla.idConcentradoTallas})">
                                <i class="fas fa-times"></i></button>`
                        ]).draw(false)
                    })
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                    alert("Error en el servidor")
                }
            });
        }
    })
}