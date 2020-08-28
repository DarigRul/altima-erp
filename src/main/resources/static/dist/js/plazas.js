function editarPlazas(id) {
    $.ajax({
        method: "GET",
        url: "/rh-editar-incrementos",
        data: {
            "_csrf": $('#token').val(),
            "id": id
        },
        success: (data) => {
            console.log(data);
            for (var key in data) {
                console.log(key + ' Dentro de for data: ' + data[0][key]);
            }
            $('#area').find('[value=' + data[0][3] + ']').prop('selected', true);
            $('#area').selectpicker('refresh');
            mostrarDepartamentosEditar(data[0][3]);
            mostrarPuestosEditar(data[0][1]);
            document.getElementById("idPlaza").value = data[0][2];
            document.getElementById("numeroPlazaPrueba").value = data[0][11];
            document.getElementById("sueldo").value = data[0][7];
            document.getElementById("observaciones").value = data[0][10];
        },
        error: (e) => {
            location.reload();
        }
    });
}

function mostrarDepartamentosEditar(id) {
    console.log("hola" + id);
    $.ajax({
        method: "GET",
        url: "/rh-listarDepas",
        data: {
            "_csrf": $('#token').val(),
            "area": id
        },
        success: (data) => {
            for (var key in data) {
                console.log(key + ' data: ' + data[0][key]);
                $('#departamento').append('<option value="' + data[key][0] + '">' + data[key][1] + '</option>')
            }
            $('#departamento').find('[value=' + data[key][0] + ']').prop('selected', true);
            $('#departamento').selectpicker('refresh');
            console.log(data);
        },
        error: (e) => {
            location.reload();
        }
    });
}

function mostrarPuestosEditar(id) {
    console.log("hola" + id);
    $.ajax({
        method: "GET",
        url: "/rh-listarPuestos",
        data: {
            "_csrf": $('#token').val(),
            "departamento": id
        },
        success: (data) => {
            for (var key in data) {
                console.log(key + ' data: ' + data[0][key]);
                $('#puestos').append('<option value="' + data[key][0] + '">' + data[key][1] + '</option>')
            }
            $('#puestos').find('[value=' + data[key][0] + ']').prop('selected', true);
            $('#puestos').selectpicker('refresh');
        },
        error: (e) => {
            location.reload();
        }
    });
}

function listarDepartamentos(id) {
    console.log("hola" + id);
    $.ajax({
        method: "GET",
        url: "/rh-listarDepas",
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
                console.log(key + ' data: ' + data[0][key]);
                $('#departamento').append('<option value="' + data[key][0] + '">' + data[key][1] + '</option>')
            }
            $('#departamento').selectpicker('refresh');
            //console.log(data);
        },
        error: (e) => {
            location.reload();
        }
    });
}

var obtenerSueldo;
function listarPuestos(id) {
    console.log("hola" + id);
    $.ajax({
        method: "GET",
        url: "/rh-listarPuestos",
        data: {
            "_csrf": $('#token').val(),
            "departamento": id
        },
        success: (data) => {
            obtenerSueldo = data;
            $('#puestos').empty();
            for (var key in data) {
                console.log(key + ' data: ' + data[0][key]);
                $('#puestos').append('<option value="' + data[key][0] + '">' + data[key][1] + '</option>')
            }
            $('#puestos').selectpicker('refresh');
        },
        error: (e) => {
            location.reload();
        }
    });
}

function listarSueldos() {
    console.log("hola el id del sueldo es: ");
    for (i in obtenerSueldo) {
        if (obtenerSueldo[i][0] == document.getElementById('puestos').value) {
            $('#sueldo').empty();
            console.log(obtenerSueldo[i][2]);
            document.getElementById('sueldo').value = obtenerSueldo[i][2];
        }
    }
}

function limpiarModalPlazas() {
    $('#puestos').empty();
    $('#puestos').selectpicker('refresh');
    $('#departamento').empty();
    $('#departamento').selectpicker('refresh');
    document.getElementById("numeroPlazaPrueba").value = "";
    document.getElementById("sueldo").value = "";
    document.getElementById("observaciones").value = "";
}