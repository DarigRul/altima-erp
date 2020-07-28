//Función para agregar empresas
function agregarEmpresa() {
    Swal.fire({
        title: 'Nueva empresa',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="empresa">Nombre de la empresa</label>' +
            '<input type="text" class="swal2-input" name="empresaLookup" id="empresaLookup" placeholder="Parisina">' +
            '<input type="hidden" id="idLookup" value="">' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Guardar',
        confirmButtonColor: '#0288d1',
        preConfirm: (color) => {
            var nombreEmpresa = document.getElementById("empresaLookup").value;
            var idLookup = $('#idLookup').val();
            $.ajax({
                type: "POST",
                url: "/postEmpresa",
                data: {
                    "_csrf": $('#token').val(),
                    "nombreEmpresa": nombreEmpresa,
                    "idLookup": idLookup
                },
                success: (data) => {
                    console.log(data);
                    if (data == 1) {
                        $('#close').click();
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'Empresa editada correctamente',
                            showConfirmButton: false,
                            timer: 2300,
                            onClose: () => {
                                $('#btnEmpresaDetalle').click();
                            }
                        })
                        $('#idLookup').val("");
                    }
                    else if (data == 2) {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'Empresa agregada correctamente',
                            showConfirmButton: false,
                            timer: 2300
                        })
                    }
                    else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: '¡Intente de nuevo!'
                        })
                    }
                },
                error: function (data) {
                    Swal.close();
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: '¡Registro duplicado!'
                    })
                }
            });
        }
    }).then((result) => {
    })
}

//Habilitar input que se muestra deshabilitado
$('#empresaRH').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});

//Listar empresas insertadas
function listarEmpresa() {
    $.ajax({
        method: "GET",
        url: "/getLookupHR",
        data: {
            "_csrf": $('#token').val(),
            "tipo": "Empresa"
        },
        success: (data) => {
            tableRHEmpresa.rows().remove().draw();
            for (i in data) {
                tableRHEmpresa.row.add([
                    data[i]["idText"],
                    data[i]["nombreLookup"],
                    "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>Manuel Perez <br /><strong>Fecha de creaci&oacute;n: </strong>20/12/2019<br><strong>Modificado por: </strong>Jose luis<br><strong>Fecha de modicaci&oacute;n: </strong>21/02/2020'><i class='fas fa-info'></i></button>&nbsp;" +
                    "<button class='btn btn-warning btn-circle btn-sm popoverxd' onclick='editarEmpresa(" + data[i]["idLookup"] + ")' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>&nbsp;" +
                    "<button class='btn btn-danger btn_remove btn-circle btn-sm popoverxd' onclick='eliminarMaterialExtra(" + data[i]["idLookup"] + ")' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>&nbsp;" +
                    "<button class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-caret-up'></i></button>"
                ]).draw(false);
            }
        },
        error: (e) => {
            alert("Error en el servidor");
        }
    });
}

//Funcion de editar empresa
function editarEmpresa(idLookup) {
    agregarEmpresa();
    $.ajax({
        method: "GET",
        url: "/editarEmpresa",
        data: {
            "_csrf": $('#token').val(),
            idLookup: idLookup
        },
        success: (data) => {
            $('#empresaLookup').val(data.nombreLookup);
            $('#idLookup').val(data.idLookup);
        },
        error: (e) => {
            alert("Error en el servidor");
        }
    });
}

//Función para agregar Áreas
function agregarArea() {
    Swal.fire({
        title: 'Nueva &aacute;rea',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="area">Nombre de la &aacute;rea</label>' +
            '<input type="text" class="swal2-input" id="areaLookup" placeholder="Producción">' +
            '<input type="hidden" id="idLookup" value="">' +
            '</div>' +
            '<input type="hidden" class="swal2-input" id="idarea">' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Guardar',
        confirmButtonColor: '#0288d1',
        preConfirm: (color) => {
            var nombreArea = document.getElementById("areaLookup").value;
            var idLookup = $('#idLookup').val();
            $.ajax({
                type: "POST",
                url: "/postArea",
                data: {
                    "_csrf": $('#token').val(),
                    "nombreArea": nombreArea,
                    "idLookup": idLookup
                },
                success: (data) => {
                    console.log(data);
                    if (data == 1) {
                        $('#closeAreas').click();
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: '&Aacute;rea editada correctamente',
                            showConfirmButton: false,
                            timer: 2300,
                            onClose: () => {
                                $('#btnAreaDetalle').click();
                            }
                        })
                        $('#idLookup').val("");
                    }
                    else if (data == 2) {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: '&Aacute;rea agregada correctamente',
                            showConfirmButton: false,
                            timer: 2300
                        })
                    }
                    else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: '¡Intente de nuevo!'
                        })
                    }
                },
                error: function (data) {
                    Swal.close();
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: '¡Registro duplicado!'
                    })
                }
            });
        }
    }).then((result) => {
    })
}

//Habilitar input que se muestra deshabilitado
$('#areaRH').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});

//Listar Áreas insertadas
function listarAreas() {
    $.ajax({
        method: "GET",
        url: "/getLookupHR",
        data: {
            "_csrf": $('#token').val(),
            "tipo": "Area"
        },
        success: (data) => {
            tableRHArea.rows().remove().draw();
            for (i in data) {
                tableRHArea.row.add([
                    data[i]["idText"],
                    data[i]["nombreLookup"],
                    "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>Manuel Perez <br /><strong>Fecha de creaci&oacute;n: </strong>20/12/2019<br><strong>Modificado por: </strong>Jose luis<br><strong>Fecha de modicaci&oacute;n: </strong>21/02/2020'><i class='fas fa-info'></i></button>&nbsp;" +
                    "<button class='btn btn-warning btn-circle btn-sm popoverxd'  onclick='editarArea(" + data[i]["idLookup"] + ")' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>&nbsp;" +
                    "<button class='btn btn-danger btn_remove btn-circle btn-sm popoverxd' onclick='' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>&nbsp;" +
                    "<button class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-caret-up'></i></button>"
                ]).draw(false);
            }
        },
        error: (e) => {
            alert("Error en el servidor");
        }
    });
}

//Funcion de editar Area
function editarArea(idLookup) {
    agregarArea();
    $.ajax({
        method: "GET",
        url: "/editarArea",
        data: {
            "_csrf": $('#token').val(),
            idLookup: idLookup
        },
        success: (data) => {
            $('#areaLookup').val(data.nombreLookup);
            $('#idLookup').val(data.idLookup);
        },
        error: (e) => {
            alert("Error en el servidor");
        }
    });
}

//Función para agregar departamento
function agregarDepartamento(idArea) {
    mostrarAreas(idArea);
    Swal.fire({
        title: 'Nuevo departamento',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="departamento">Nombre del departamento</label>' +
            '<input type="text" class="swal2-input" id="departamento" placeholder="Ingresa el departamento">' +
            '</div>' +
            '</div>' +
            '<label for="areadep">Área</label>' +
            '<select class="swal2-input form-control selectpicker" title="Seleccione uno..."  id="listarAreasCatalogos">' +
            '</select>' +
            '<input type="hidden" id="idDepartamento" value="">' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#6C757D',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#28a745',
        preConfirm: (color) => {
            var nombreDepartamento = document.getElementById("departamento").value;
            var nomArea = document.getElementById("listarAreasCatalogos").value;
            var Departamento = $('#idDepartamento').val();
            $.ajax({
                type: "POST",
                url: "/postDepartamento",
                data: {
                    "_csrf": $('#token').val(),
                    "idDepartamento": Departamento,
                    "nombreDepartamento": nombreDepartamento,
                    "nomArea": nomArea
                },
                success: (data) => {
                    console.log(data);
                    if (data == 1) {
                        $('#closeDep').click();
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: '&Aacute;rea editada correctamente',
                            showConfirmButton: false,
                            timer: 2300,
                            onClose: () => {
                                $('#btnDepartamentoDetalle').click();
                            }
                        })
                        $('#idDepartamento').val("");
                    }
                    else if (data == 2) {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: '&Aacute;rea agregada correctamente',
                            showConfirmButton: false,
                            timer: 2300
                        })
                    }
                    else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: '¡Intente de nuevo!'
                        })
                    }
                },
                error: function (data) {
                    Swal.close();
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: '¡Registro duplicado!',
                    })
                }
            });
        }
    }).then((result) => {
    })
}

//Habilitar input que se muestra deshabilitado
$('#departamentosRH').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});

//función para mostrar áreas en departamentos
function mostrarAreas(idArea) {
    $.ajax({
        method: "GET",
        url: "/rh-listarAreas",
        data: {
            "_csrf": $('#token').val(),
            "tipo": "Area"
        },
        success: (data) => {
            console.log(data);
            for (var key in data) {
                $('#listarAreasCatalogos').append('<option value="' + data[key]["idLookup"] + '">' + data[key]["nombreLookup"] + '</option>')
            }
            if (idArea != null) {
                $('#listarAreasCatalogos option[value="' + idArea + '"]').attr("selected", true);
            }
            $('#listarAreasCatalogos').selectpicker('refresh');
        },
        error: (e) => {
            location.reload();
        }
    });
}

//Listar departamentos insertados
function listarDepartamentos() {
    $.ajax({
        method: "GET",
        url: "/getListarDepartamentos",
        data: {
            "_csrf": $('#token').val()
        },
        success: (data) => {
            tableRHDepartamento.rows().remove().draw();
            for (i in data) {
                tableRHDepartamento.row.add([
                    data[i][0],
                    data[i][1],
                    data[i][3],
                    "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>Manuel Perez <br /><strong>Fecha de creaci&oacute;n: </strong>20/12/2019<br><strong>Modificado por: </strong>Jose luis<br><strong>Fecha de modicaci&oacute;n: </strong>21/02/2020'><i class='fas fa-info'></i></button>&nbsp;" +
                    "<button class='btn btn-warning btn-circle btn-sm popoverxd' onclick='editarDepartamento(" + data[i][0] + ")' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>&nbsp;" +
                    "<button class='btn btn-danger btn_remove btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>&nbsp;" +
                    "<button class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-caret-up'></i></button>"
                ]).draw(false);
            }
        },
        error: (e) => {
            alert("Error en el servidor");
        }
    });
}

//Funcion de editar departamento
function editarDepartamento(idLookup) {
    $.ajax({
        method: "GET",
        url: "/editarDepartamento",
        data: {
            "_csrf": $('#token').val(),
            idLookup: idLookup
        },
        success: (data) => {
            agregarDepartamento(data[2]);
            $('#departamento').val(data[1]);
            $('#idDepartamento').val(data[0]);
        },
        error: (e) => {
            alert("Error en el servidor");
        }
    });
}

//Función para agregar puestos
function agregarPuesto(idDepartamento, check) {
    mostrarDepartamentos(idDepartamento);
    Swal.fire({
        title: 'Agregar Puesto',
        html: '<div class="row">' +
            '<div class="form-group col-md-12">' +
            '<label for="puesto">Nombre Puesto</label>' +
            '<input type="text" class="swal2-input" id="puesto" placeholder="Coordinador">' +
            '</div>' +
            '<div class="form-group col-md-12">' +
            '<label for="exampleFormControlSelect1">Departamento</label>' +
            '<select class="form-control" id="seleccionarDepa">' +
            '</select>' +
            '</div>' +
            '<div class="form-group col-md-6">' +
            '<label for="horarioExtra">Genera tiempo extra</label>' +
            '<input type="checkbox" class="swal2-input" value="false" name="hExtra" id="horarioExtra" onclick="$(this).val(this.checked ? true : false)">' +
            '</div>' +
            '<div class="form-group col-md-6">' +
            '<label for="puesto">Plaza</label>' +
            '<input type="text" class="swal2-input" id="plaza" placeholder="Coordinador">' +
            '</div>' +
            '<div class="form-group col-md-6">' +
            '<label for="puesto">Sueldo</label>' +
            '<input type="text" class="swal2-input" id="sueldo" placeholder="Coordinador">' +
            '</div>' +
            '<div class="form-group col-md-6">' +
            '<label for="puesto">Perfil</label>' +
            '<input type="text" class="swal2-input" id="perfil" placeholder="Coordinador">' +
            '</div>' +
            '<input type="hidden" id="idPuesto">' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#6C757D',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#28a745',
        preConfirm: (color) => {
            var nombrePuesto = document.getElementById("puesto").value;
            var nomPlazas = document.getElementById("plaza").value;
            var sueldos = document.getElementById("sueldo").value;
            var perfiles = document.getElementById("perfil").value;
            var departamento = document.getElementById("seleccionarDepa").value;
            var checkbox = document.getElementById("horarioExtra").value;
            var idPuesto = document.getElementById("idPuesto").value
            console.log(checkbox);
            $.ajax({
                type: "POST",
                url: "/postPuesto",
                data: {
                    "_csrf": $('#token').val(),
                    "nombrePuesto": nombrePuesto,
                    "nomPlazas": nomPlazas,
                    "sueldos": sueldos,
                    "perfiles": perfiles,
                    "departamento": departamento,
                    "checkbox": checkbox,
                    "idPuesto": idPuesto
                },
                success: (data) => {
                    if (data == 1) {
                        $('#closePuestos').click();
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: '&Aacute;rea editada correctamente',
                            showConfirmButton: false,
                            timer: 2300,
                            onClose: () => {
                                $('#btnPuestosDetalle').click();
                            }
                        })
                        $('#idPuesto').val("");
                    } else if (data = 2) {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: '&Aacute;rea agregada correctamente',
                            showConfirmButton: false,
                            timer: 2300
                        })
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: '¡Intente de nuevo!'
                        })
                    }
                },
                error: function (data) {
                    Swal.close();
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: '¡Registro duplicado!',
                    })
                }
            });
        }
    }).then((result) => {
    })

}

//Habilitar input que se muestra deshabilitado
$('#puestoRH').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});

//Función para mostrar departamentos en puestos
function mostrarDepartamentos(idDepartamento) {
    $.ajax({
        method: "GET",
        url: "/getMostrarDepartamentos",
        data: {
            "_csrf": $('#token').val(),
        },
        success: (data) => {
            console.log(data);
            for (var key in data) {
                $('#seleccionarDepa').append('<option value="' + data[key]["idDepartamento"] + '">' + data[key]["nombreDepartamento"] + '</option>')
            }
            if (idDepartamento != null) {
                $('#seleccionarDepa option[value="' + idDepartamento + '"]').attr("selected", true);
            }
        },
        error: (e) => {
            location.reload();
        }
    });
}

//Listar puestos insertados
function ListarPuestos() {
    $.ajax({
        method: "GET",
        url: "/getListarPuestos",
        data: {
            "_csrf": $('#token').val()
        },
        success: (data) => {
            tableRHPuestos.rows().remove().draw();
            for (i in data) {
                tableRHPuestos.row.add([
                    data[i][0],
                    data[i][1],
                    data[i][3],
                    data[i][4] == true ? "Aceptado" : "Rechazado",
                    data[i][5],
                    data[i][6],
                    data[i][7],
                    "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>Manuel Perez <br /><strong>Fecha de creaci&oacute;n: </strong>20/12/2019<br><strong>Modificado por: </strong>Jose luis<br><strong>Fecha de modicaci&oacute;n: </strong>21/02/2020'><i class='fas fa-info'></i></button>&nbsp;" +
                    "<button class='btn btn-warning btn-circle btn-sm popoverxd' onclick='editarPuesto(" + data[i][0] + ")' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>&nbsp;" +
                    "<button class='btn btn-danger btn_remove btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>&nbsp;" +
                    "<button class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-caret-up'></i></button>"
                ]).draw(false);
            }
        },
        error: (e) => {
            alert("Error en el servidor");
        }
    });
}

//Funcion de editar puestos
function editarPuesto(idLookup) {
    $.ajax({
        method: "GET",
        url: "/editarPuesto",
        data: {
            "_csrf": $('#token').val(),
            idLookup: idLookup
        },
        success: (data) => {
            agregarPuesto(data[2], data[4]);
            $('#puesto').val(data[1]);
            $('#idPuesto').val(data[0]);
            $('#horarioExtra').val(data[4]);
            $("#horarioExtra[value='true']").attr("checked", "checked")
            $('#plaza').val(data[5]);
            $('#sueldo').val(data[6]);
            $('#perfil').val(data[7]);
        },
        error: (e) => {
            alert("Error en el servidor");
        }
    });
}

//función para agregar horarios
function agregarHorario() {
    Swal.fire({
        title: 'Agregar Horario',
        html:
            '<div class="row">' +
            '<label class="col-md-6" for="horarioinicio">Inicio Horario</label>' +
            '<label class="col-md-6" for="horariosalida">Salida Horario</label>' +
            '<input type="time" class="swal2-input col-md-5" id="horarioinicio">' +
            '<input type="time" class="swal2-input col-md-5 offset-md-2" id="horariosalida">' +
            '</div>' +
            '<div class="row">' +
            '<label class="col-md-6" for="horacomidainicio">Inicio Comida</label>' +
            '<label class="col-md-6" for="horacomidafin">Final Comida</label>' +
            '<input type="time" class="swal2-input col-md-5" id="horacomidainicio">' +
            '<input type="time" class="swal2-input col-md-5 offset-md-2" id="horacomidafin">' +
            '</div>' +
            '<input type="hidden" class="swal2-input" id="idHorario">' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#6C757D',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#28a745',
        preConfirm: (color) => {
            var idHorario = document.getElementById("idHorario").value;
            var horaInicio = document.getElementById("horarioinicio").value;
            var horaSalida = document.getElementById("horariosalida").value;
            var horaComida = document.getElementById("horacomidainicio").value;
            var horaRegresoComida = document.getElementById("horacomidafin").value;
            $.ajax({
                type: "POST",
                url: "/postHorarioLaboral",
                data: {
                    "_csrf": $('#token').val(),
                    "idHorario": idHorario,
                    "horaInicio": horaInicio,
                    "horaSalida": horaSalida,
                    "horaComida": horaComida,
                    "horaRegresoComida": horaRegresoComida
                },
                success: (data) => {
                    if (data == 1) {
                        $('#closeHorario').click();
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'Horario editado correctamente',
                            showConfirmButton: false,
                            timer: 2300,
                            onClose: () => {
                                $('#btnHorariosDetalle').click();
                            }
                        })
                        $('#idHorario').val("");
                    } else if (data = 2) {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'Horario agregado correctamente',
                            showConfirmButton: false,
                            timer: 2300
                        })
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: '¡Intente de nuevo!'
                        })
                    }
                },
                error: function (data) {
                    Swal.close();
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: '¡Registro duplicado!',
                    })
                }
            });
        }
    }).then((result) => {
    })
}

//Listar horarios insertados

function ListarHorarios() {
    $.ajax({
        method: "GET",
        url: "/getListarHorarios",
        data: {
            "_csrf": $('#token').val()
        },
        success: (data) => {
            tableRHHorarios.rows().remove().draw();
            for (i in data) {
                tableRHHorarios.row.add([
                    data[i]["idHorario"],
                    data[i]["horaInicial"],
                    data[i]["horaFinal"],
                    data[i]["inicioComida"],
                    data[i]["finalComida"],
                    "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>Manuel Perez <br /><strong>Fecha de creaci&oacute;n: </strong>20/12/2019<br><strong>Modificado por: </strong>Jose luis<br><strong>Fecha de modicaci&oacute;n: </strong>21/02/2020'><i class='fas fa-info'></i></button>&nbsp;" +
                    "<button class='btn btn-warning btn-circle btn-sm popoverxd' onclick='editarHorario(" + data[i]["idHorario"] + ")' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>&nbsp;" +
                    "<button class='btn btn-danger btn_remove btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>&nbsp;" +
                    "<button class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-caret-up'></i></button>"
                ]).draw(false);
            }
        },
        error: (e) => {
            alert("Error en el servidor");
        }
    });
}

//Funcion de editar horario
function editarHorario(idHorario) {
    agregarHorario();
    $.ajax({
        method: "GET",
        url: "/editarHorario",
        data: {
            "_csrf": $('#token').val(),
            idHorario: idHorario
        },
        success: (data) => {
            $('#idHorario').val(data[0][0]);
            $('#horarioinicio').val(data[0][1]);
            $('#horariosalida').val(data[0][2]);
            $('#horacomidainicio').val(data[0][3]);
            $('#horacomidafin').val(data[0][4]);
        },
        error: (e) => {
            alert("Error en el servidor");
        }
    });
}

//Función para agregar calendarios
function agregarCalendario() {
    Swal.fire({
        title: 'Agregar Calendario',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="diafestivo">Fecha</label>' +
            '<input type="date" class="swal2-input" id="diafestivo">' +
            '</div>' +
            '</div>' +
            '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="nombrefecha">Nombre</label>' +
            '<input type="text" class="swal2-input" id="nombrefecha" placeholder="Dia de la independencia">' +
            '</div>' +
            '</div>' +
            '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="estatusfecha">Status</label>' +
            '<input type="checkbox" class="swal2-input" value="false"  id="checkbox" onclick="$(this).val(this.checked ? true : false)" >' +
            '</div>' +
            '</div>' +
            '<input type="hidden" class="swal2-input" id="idCalendario">' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#6C757D',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#28a745',
        preConfirm: (color) => {
            var idCalendario = document.getElementById("idCalendario").value;
            var fechaFestivo = document.getElementById("diafestivo").value;
            var festividad = document.getElementById("nombrefecha").value;
            var estatusFestivo = document.getElementById("checkbox").value;
            console.log(estatusFestivo);
            console.log(fechaFestivo);
            $.ajax({
                type: "POST",
                url: "/postCalendarios",
                data: {
                    "_csrf": $('#token').val(),
                    "idCalendario": idCalendario,
                    "fechaFestivo": fechaFestivo,
                    "festividad": festividad,
                    "estatusFestivo": estatusFestivo
                },
                success: (data) => {
                    if (data == 1) {
                        $('#closeCalendario').click();
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'Calendario editado correctamente',
                            showConfirmButton: false,
                            timer: 2300,
                            onClose: () => {
                                $('#btnCalendarioDetalle').click();
                            }
                        })
                        $('#idCalendario').val("");
                    } else if (data = 2) {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'Calendario agregado correctamente',
                            showConfirmButton: false,
                            timer: 2300
                        })
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: '¡Intente de nuevo!'
                        })
                    }
                },
                error: function (data) {
                    Swal.close();
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: '¡Registro duplicado!',
                    })
                }
            });
        }
    }).then((result) => {
    })
}

//Habilitar input que se muestra deshabilitado
$('#detalleCuidado').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});

//Listar calendarios insertados
function ListarCalendarios() {
    $.ajax({
        method: "GET",
        url: "/getListarCalendarios",
        data: {
            "_csrf": $('#token').val()
        },
        success: (data) => {
            tableRHCalendario.rows().remove().draw();
            for (i in data) {
                tableRHCalendario.row.add([
                    data[i]["idCalendario"],
                    data[i]["fecha"],
                    data[i]["nombreCalendario"],
                    data[i]["estatus"] == true ? "Valido" : "No valido",
                    "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>Manuel Perez <br /><strong>Fecha de creaci&oacute;n: </strong>20/12/2019<br><strong>Modificado por: </strong>Jose luis<br><strong>Fecha de modicaci&oacute;n: </strong>21/02/2020'><i class='fas fa-info'></i></button>&nbsp;" +
                    "<button class='btn btn-warning btn-circle btn-sm popoverxd' onclick='editarCalendario(" + data[i]["idCalendario"] + ")' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>&nbsp;" +
                    "<button class='btn btn-danger btn_remove btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>&nbsp;" +
                    "<button class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-caret-up'></i></button>"
                ]).draw(false);
            }
        },
        error: (e) => {
            alert("Error en el servidor");
        }
    });
}

//Funcion de editar calendareio
function editarCalendario(idCalendario) {
    agregarCalendario();
    $.ajax({
        method: "GET",
        url: "/editarCalendario",
        data: {
            "_csrf": $('#token').val(),
            idCalendario: idCalendario
        },
        success: (data) => {
            $('#idCalendario').val(data.idCalendario);
            $('#diafestivo').val(data.fecha);
            $('#nombrefecha').val(data.nombreCalendario);
            $('#checkbox').val(data.estatus);
            $("#checkbox[value='true']").attr("checked", "checked")
            console.log(data.idCalendario);
            console.log(data.fecha);
            console.log(data.nombreCalendario);
            console.log(data.estatus);
        },
        error: (e) => {
            alert("Error en el servidor");
        }
    });
}