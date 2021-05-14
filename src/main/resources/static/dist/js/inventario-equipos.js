function nuevo() {
    $('#tipo').val(null);
    $('#tipo').selectpicker('refresh');
    $('#proveedor').val(null);
    $('#fecha').val(null);
    $('#marca').val(null);
    $('#modelo').val(null);
    $('#serie').val(null);
    $('#procesador').val(null)
    $('#ram').val(null);
    $('#disco').val(null);
    $('#pantalla').val(null);
    $('#ns_pantalla').val(null);
    $('#ip').val(null);
    $("#observaciones").val(null);
    $('#asignado').val(null);
    $('#asignado').selectpicker('refresh');
    $("#modalAgregar").modal("show");
}

function guardar() {

    if ($('#tipo').val() == "" || $('#proveedor').val() == "" ||
        $('#fecha').val() == "" || $('#marca').val() == "" ||
        $('#modelo').val() == "" || $('#serie').val() == "" ||
        $('#procesador').val() == "" || $('#ram').val() == "" ||
        $('#disco').val() == "" || $('#pantalla').val() == "" ||
        $('#ns_pantalla').val() == "" || $('#ip').val() == "" ||
        $('#asignado').val() == "") {
        Swal.fire({
            icon: 'error',
            title: 'Error!',
            text: 'Complete el formulario.'
        })
    }
    else {
        $.ajax({
            type: 'POST',
            url: "/guardar_inventario_soporte_tecnico",
            data: {
                'idInventario': $('#idInventario').val(),
                'tipo': $('#tipo').val(),
                'proveedor': $('#proveedor').val(),
                'fecha': $('#fecha').val(),
                'marca': $('#marca').val(),
                'modelo': $('#modelo').val(),
                'serie': $('#serie').val(),
                'procesador': $('#procesador').val(),
                'ram': $('#ram').val(),
                'discoDuro': $('#disco').val(),
                'pantalla': $('#pantalla').val(),
                'nsPantalla': $('#ns_pantalla').val(),
                'direccionIp': $('#ip').val(),
                'asignadoA': $('#asignado').val(),
                'observaciones': $('#observaciones').val(),
                "_csrf": $('#token').val(),
            },
            success: function (data) {
                if (data == true) {
                    $("#modalAgregar").modal("hide");
                    Swal.fire({
                        icon: 'success',
                        title: 'Guardado!',
                        text: 'Guardado correctamente.'
                    })
                    location.reload();
                }
            }


        })
    }
}
function editar(id) {
    $.ajax({
        type: "POST",
        url: "buscar_id_inventaio_soporte",
        data: { 'id': id, "_csrf": $('#token').val() },
        success: function (data) {
            $('#idInventario').val(data.idInventarioEquipo);
            $('#tipo').val(data.tipo);
            $('#tipo').selectpicker('refresh');
            $('#proveedor').val(data.provedor);
            $('#fecha').val(data.fecha);
            $('#marca').val(data.marca);
            $('#modelo').val(data.modelo);
            $('#serie').val(data.serie);
            $('#procesador').val(data.procesador)
            $('#ram').val(data.ram);
            $('#disco').val(data.discoDuro);
            $('#pantalla').val(data.pantalla);
            $('#ns_pantalla').val(data.nsPantalla);
            $("#observaciones").val(data.observaciones);
            $('#ip').val(data.direccion_ip);
            $('#asignado').val(data.asignadoA);
            $('#asignado').selectpicker('refresh');
            $("#modalAgregar").modal("show");
        }


    })
}
function alta(id) {
    Swal.fire({
        title: '&iquest;Est&aacute; seguro que desea dar de alta a este equipo?',
        text: "Puede cambiarlo en otro momento",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Si, dar de alta',
        reverseButtons: true

    }).then((result) => {
        if (result.value) {
            $.ajax({
                type: "POST",
                url: "/cambiar_estatus_inventario_soporte",
                data: { 'id': id, 'estatus': 1, "_csrf": $('#token').val() },
                success: function (data) {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: '¡Dado de alta correctamente!',
                        showConfirmButton: true
                    })
                    location.reload();
                }


            })
        }
    })
}


function baja(id) {
    Swal.fire({
        title: '&iquest;Est&aacute; seguro que desea dar de baja a este equipo?',
        text: "Puede cambiarlo en otro momento",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Si, dar de baja',
        reverseButtons: true

    }).then((result) => {
        if (result.value) {
            $.ajax({
                type: "POST",
                url: "/cambiar_estatus_inventario_soporte",
                data: { 'id': id, 'estatus': 0, "_csrf": $('#token').val() },
                success: function (data) {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: '¡Dado de baja correctamente!',
                        showConfirmButton: true
                    })
                    location.reload();
                }


            })
        }
    })
}
var idEquipo;
function mantenimiento(e) {
    $("#idv1").hide();
    $("#idv2").hide();
    $("#idv3").hide();
    $("#idv4").hide();
    $("#idv5").hide();

    $('#fechaMantenimiento').val(null);
    $('#tipoMantenimiento').val(null);
    $('#tipoMantenimiento').selectpicker('refresh');
    $('#motivoMantenimiento').val(null);
    $('#fechaProxMantenimiento').val(null);
    $('#Actividad').val(null);

    $.ajax({
        type: "POST",
        url: "buscar_id_inventaio_soporte",
        data: { 'id': e.getAttribute("id"), "_csrf": $('#token').val() },
        success: function (data) {
            $('#id1').val(data.fecha);
            $('#id2').val(e.getAttribute("tipo"));
            $('#id3').val(data.marca);
            $('#id4').val(data.modelo);
            $('#id5').val(data.serie);
            idEquipo = e.getAttribute("id");

            $("#modalMantenimiento").modal("show");
        }


    })

}
function validarTipo(value) {

    if (value == 'Remplazo de Pieza') {
        $("#idv1").show();
        $("#idv2").show();
        $("#idv3").show();
        $("#idv4").show();

        $("#fechaCompra").val(null);
        $("#pro").val(null);
        $("#ns_pantalla").val(null);
        $("#des").val(null);



    }
    else {
        $("#idv1").hide();
        $("#idv2").hide();
        $("#idv3").hide();
        $("#idv4").hide();
        $("#idv5").hide();
    }
}
function guardarMan() {
    var valid = true;
    if ($('#tipoMantenimiento').val() == 'Remplazo de Pieza') {
        if ($("#fechaCompra").val() == "" ||
            $("#pro").val() == "" ||
            $("#ns_pantallaMante").val() == "" ||
            $("#des").val() == "") {
            valid = false;

        }
        else {
            valid = true;
        }

    }
    else if ($('#tipoMantenimiento').val() == "") {
        valid = false;
    }
    else {
        valid = true;
    }
    console.log(valid);
    if ($('#fechaMantenimiento').val() == "" || $('#motivoMantenimiento').val() == "" | $('#fechaProxMantenimiento').val() == "" || valid != true) {

        Swal.fire({
            icon: 'error',
            title: 'Error!',
            text: 'Complete el formulario.'
        })
    }
    else {
        $.ajax({
            type: "POST",
            url: "/guardar_mantenimieto",
            data: {
                'idEquipo': idEquipo,
                'fecha': $('#fechaMantenimiento').val(),
                'tipo': $('#tipoMantenimiento').val(),
                'motivo': $('#motivoMantenimiento').val(),
                'fechaCompra': $("#fechaCompra").val(),
                'proveedor': $("#pro").val(),
                'NS': $("#ns_pantallaMante").val(),
                'descripcion': $("#des").val(),
                'fechaProxima': $('#fechaProxMantenimiento').val(),

                'actividad': $('#Actividad').val(),

                "_csrf": $('#token').val()
            },
            success: function (data) {

                $("#modalMantenimiento").modal("hide");

                Swal.fire({
                    icon: 'success',
                    title: 'Guardado!',
                    text: '¡Guardado correctamente!.'
                }).then(function () {
                    location.reload();
                })
            }


        })
    }
}