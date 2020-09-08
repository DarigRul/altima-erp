function listarDepartamentos(id, aux) {
    $.ajax({
        method: "GET",
        url: "/rh-listarDepa-Permiso",
        data: {
            "_csrf": $('#token').val(),
            "area": id
        },
        success: (data) => {
            $('#puestos').empty();
            $('#puestos').selectpicker('refresh');
            $('#departamento').empty();
            $('#departamento').selectpicker('refresh');
            for (var key in data) {
                $('#departamento').append('<option value="' + data[key][0] + '">' + data[key][1] + '</option>')
            }
            $('#departamento').val(aux);
            $('#departamento').selectpicker('refresh');
        },
        error: (e) => {
            location.reload();
        }
    });

}

function listarPuestos(id, aux) {
    $.ajax({
        method: "GET",
        url: "/rh-listarPuestos-Permiso",
        data: {
            "_csrf": $('#token').val(),
            "departamento": id
        },
        success: (data) => {
            $('#empleado').empty();
            $('#empleado').selectpicker('refresh');
            $('#puestos').empty();
            $('#puestos').selectpicker('refresh');
            for (var key in data) {
                $('#puestos').append('<option value="' + data[key][0] + '">' + data[key][1] + '</option>')
            }
            $('#puestos').val(aux);
            $('#puestos').selectpicker('refresh');
        },
    });
}

function listarEmpleado(id, aux) {
    $.ajax({
        method: "GET",
        url: "/rh-filtrar-empleado",
        data: {
            "_csrf": $('#token').val(),
            "id": id
        },
        success: (data) => {
            for (var key in data) {
                $('#empleado').append('<option value="' + data[key][0] + '">' + data[key][1] + '</option>')
            }
            $('#empleado').val(aux);
            $('#empleado').selectpicker('refresh');
        },
    });
}