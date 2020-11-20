$(document).ready(function () {

    listarColores();
    listarTrazos();
    listarPrendas();
    listarGeneros();
    listarComposiciones();
    listarCuidados();
    listarMedidas();
    listarMateriales();
    listarMarcadores();
    listarComposiciones1();
    listarPreciosComposicion();
});

function listarcuidadosjson() {
    $.ajax({
        method: "GET",
        url: "/listar",
        data: {
            "Tipo": "Instruccion Cuidado"
        },
        success: (data2) => {
            $('#selectcuidados').append("<div >" +
                "<select class='form-control' id='selectcuidado'>" +

                "</select>" +
                "</div>");
            for (i in data2) {
                $('#selectcuidado').append(

                    "<option value=" + data2[i].idLookup + " >" + data2[i].nombreLookup + "</option>"

                );
            }


        }

    });


}

function listarcuidadosjson2(idcomposicion) {
    var idcomp = idcomposicion;

    $.ajax({
        method: "POST",
        url: "/composicionescuidadosrest",
        data: {
            "_csrf": $('#token').val(),
            'FamiliaComposicion': idcomp
        },
        success: (data3) => {

            $('#selectcuidados2').append("<div >" +
                "<table  class='table'>" +
                "<thead>" +
                "<tr>" +
                "<th scope='col'>Código cuidado</th>" +
                "<th scope='col'>Nombre</th>" +
                "<th scope='col'></th>" +
                "</tr>" +
                "</thead>" +
                "<tbody id='selectcuidado2'>" +
                "</tbody>" +
                "</table>" +
                "</div>");
            for (i in data3) {
                $('#selectcuidado2').append(
                    "<tr>" +
                    "<td>" + data3[i][1] + "</td>" +
                    "<td>" + data3[i][2] + "</td>" +
                    "<td><button class='btn btn-danger btn-circle btn-sm'><i class='fas fa-minus' onclick=bajarcomposicioncuidado(" + data3[i][5] + ")></i></button></td>" +
                    "</tr>"
                );
            }

        } // /////////////

    });


}
// /////////////////////////

// /////////////////////////////
function listarColores() {
    var Proveedores = [];
    $.ajax({
        method: "GET",
        url: "/listar",
        data: {
            "Tipo": "Color"
        },
        success: (data) => {
            $.ajax({
                type: "GET",
                url: "/listarProveedoresColores",
                success: (datitos) => {
                    Proveedores = datitos;

                    $('#quitar2').remove();
                    $('#contenedorTabla2').append("<div class='modal-body' id='quitar2'>" +
                        "<table class='table table-striped table-bordered' id='idtable2' style='width:100%'>" +
                        "<thead>" +
                        "<tr>" +
                        "<th>Clave</th>" +
                        "<th>Nombre</th>" +
                        "<th>Color</th>" +
                        "<th>Proveedor</th>" +
                        "<th>Acciones</th>" +
                        "</tr>" +
                        "</thead>" +
                        "</table>" + "</div>");
                    var a;
                    var idProveedor;
                    var b = [];

                    if (rolAdmin == 1) {
                        var nombreProveedor = '';
                        for (i in data) {
                            nombreProveedor = '';
                            var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;

                            if (data[i].atributo2 == null || data[i].atributo2 == '' || data[i].atributo2 == undefined) {
                                idProveedor = '';
                                nombreProveedor == '';
                            } else {
                                idProveedor = parseInt(data[i].atributo2);
                                nombreProveedor = Proveedores[idProveedor - 1].nombreProveedor;
                            }

                            a = [
                                "<tr>" +
                                "<td>" + data[i].idText + "</td>",
                                "<td>" + data[i].nombreLookup + "</td>",
                                "<td> <input type='color' value=" + data[i].atributo1 + " disabled> </td>",
                                "<td>" + nombreProveedor + "</td>",
                                "<td style='text-align: center'>" +
                                "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                                " <button id='" + data[i].idLookup + "' value='" + data[i].nombreLookup + "' color='" + data[i].atributo1 + "' proveedorColor='" + data[i].atributo2 + "' class='btn btn-warning btn-circle btn-sm popoverxd edit_data_color' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " +
                                (data[i].estatus == 1 ? "<button onclick='bajarColor(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                                (data[i].estatus == 0 ? "<button onclick='reactivar(" + data[i].idLookup + ")' class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-sort-up'></i></button>" : " ") +
                                "</td>" +

                                "<tr>"
                            ];
                            b.push(a);
                        }
                    } else {
                        var nombreProveedor = '';
                        for (i in data) {
                            nombreProveedor = '';
                            var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;

                            if (data[i].atributo2 == null || data[i].atributo2 == '' || data[i].atributo2 == undefined) {
                                idProveedor = '';
                                nombreProveedor == '';
                            } else {
                                idProveedor = parseInt(data[i].atributo2);
                                nombreProveedor = Proveedores[idProveedor - 1].nombreProveedor;
                            }
                            var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                            if (data[i].estatus == 1) {
                                a = [
                                    "<tr>" +
                                    "<td>" + data[i].idText + "</td>",
                                    "<td>" + data[i].nombreLookup + "</td>",
                                    "<td> <input type='color' value=" + data[i].atributo1 + " disabled> </td>",
                                    "<td>" + nombreProveedor + "</td>",
                                    "<td style='text-align: center'>" +
                                    "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                                    (rolEditar == 1 ? "<button id='" + data[i].idLookup + "' value='" + data[i].nombreLookup + "' color='" + data[i].atributo1 + "' class='btn btn-warning btn-circle btn-sm popoverxd edit_data_color' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>" : " ") +
                                    (rolEliminar == 1 ? "<button onclick='bajarColor(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                                    "</td>" +

                                    "<tr>"
                                ];
                                b.push(a);

                            }
                        }
                    }
                    var tablaColores = $('#idtable2').DataTable({
                        "data": b,
                        "ordering": false,
                        "pageLength": 5,
                        "responsive": true,
                        "stateSave": true,
                        "drawCallback": function () {
                            $('.popoverxd').popover({
                                container: 'body',
                                trigger: 'hover'
                            });
                        },
                        "columnDefs": [{
                            "type": "html",
                            "targets": '_all'
                        },
                        {
                            targets: 3,
                            className: 'dt-body-center'
                        }
                        ],
                        "lengthMenu": [
                            [5, 10, 25, 50, 100],
                            [5, 10, 25, 50, 100]
                        ],
                        "language": {
                            "sProcessing": "Procesando...",
                            "sLengthMenu": "Mostrar _MENU_ registros",
                            "sZeroRecords": "No se encontraron resultados",
                            "sEmptyTable": "Ningún dato disponible en esta tabla =(",
                            "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                            "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                            "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                            "sInfoPostFix": "",
                            "sSearch": "Buscar:",
                            "sUrl": "",
                            "sInfoThousands": ",",
                            "sLoadingRecords": "Cargando...",
                            "oPaginate": {
                                "sFirst": "Primero",
                                "sLast": "Último",
                                "sNext": "Siguiente",
                                "sPrevious": "Anterior"
                            },
                            "oAria": {
                                "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                            },
                            "buttons": {
                                "copy": "Copiar",
                                "colvis": "Visibilidad"
                            }
                        }
                    });
                    new $.fn.dataTable.FixedHeader(tablaColores);
                }
            })
        },
        error: (e) => {

        }
    })
}

function listarTrazos() {

    $.ajax({
        method: "GET",
        url: "/listar",
        data: {
            "Tipo": "Pieza Trazo"
        },
        success: (data) => {
            $('#quitar3').remove();
            $('#contenedorTabla3').append("<div class='modal-body' id='quitar3'>" +
                "<table class='table table-striped table-bordered' id='idtable3' style='width:100%'>" +
                "<thead>" +
                "<tr>" +
                "<th>Clave</th>" +
                "<th>Nombre</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "</table>" + "</div>");
            var a;
            var b = [];
            if (rolAdmin == 1) {
                for (i in data) {
                    var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                    a = [
                        "<tr>" +
                        "<td>" + data[i].idText + "</td>",
                        "<td>" + data[i].nombreLookup + "</td>",
                        "<td style='text-align: center;'>" +
                        "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                        "<button id='" + data[i].idLookup + "' value='" + data[i].nombreLookup + "' class='btn btn-warning btn-circle btn-sm popoverxd edit_data_trazo' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " +
                        (data[i].estatus == 1 ? "<button onclick='bajarTrazo(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                        (data[i].estatus == 0 ? "<button onclick='reactivar(" + data[i].idLookup + ")' class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-sort-up'></i></button>" : " ") +
                        "<button style='display:none' onclick='agregarConsumoTrazo()' id='agregarConsumoButton' class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Agregar consumo'><i class='flaticon-camera-film-strip' style='margin-left:-2px'></i></button> " +
                        "</td>" +

                        "<tr>"
                    ];
                    b.push(a);
                }
            } else {
                for (i in data) {
                    var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                    if (data[i].estatus == 1) {
                        a = [
                            "<tr>" +
                            "<td>" + data[i].idText + "</td>",
                            "<td>" + data[i].nombreLookup + "</td>",
                            "<td style='text-align: center;'>" +
                            "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                            (rolEditar == 1 ? "<button id='" + data[i].idLookup + "' value='" + data[i].nombreLookup + "' class='btn btn-warning btn-circle btn-sm popoverxd edit_data_trazo' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>" : " ") +
                            (rolEliminar == 1 ? "<button onclick='bajarTrazo(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                            "</td>" +

                            "<tr>"
                        ];
                        b.push(a);
                    }
                }
            }
            var tablaTrazos = $('#idtable3').DataTable({
                "data": b,
                "ordering": false,
                "pageLength": 5,
                "responsive": true,
                "stateSave": true,
                "drawCallback": function () {
                    $('.popoverxd').popover({
                        container: 'body',
                        trigger: 'hover'
                    });
                },
                "columnDefs": [{
                    "type": "html",
                    "targets": '_all'
                },
                {
                    targets: 2,
                    className: 'dt-body-center'
                }
                ],
                "lengthMenu": [
                    [5, 10, 25, 50, 100],
                    [5, 10, 25, 50, 100]
                ],
                "language": {
                    "sProcessing": "Procesando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "No se encontraron resultados",
                    "sEmptyTable": "Ningún dato disponible en esta tabla =(",
                    "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "sInfoThousands": ",",
                    "sLoadingRecords": "Cargando...",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                    },
                    "oAria": {
                        "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                    },
                    "buttons": {
                        "copy": "Copiar",
                        "colvis": "Visibilidad"
                    }
                }
            });
            new $.fn.dataTable.FixedHeader(tablaTrazos);
        },
        error: (e) => {

        }
    })
}

function listarPrendas() {

    $.ajax({
        method: "GET",
        url: "/listar",
        data: {
            "Tipo": "Familia Prenda"
        },
        success: (data) => {
            $('#quitar4').remove();
            $('#contenedorTabla4').append("<div class='modal-body' id='quitar4'>" +
                "<table class='table table-striped table-bordered' id='idtable4' style='width:100%'>" +
                "<thead>" +
                "<tr>" +
                "<th>Clave</th>" +
                "<th>Nombre</th>" +
                "<th>Ubicaci&oacute;n de la prenda</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "</table>" + "</div>");
            var a;
            var b = [];
            if (rolAdmin == 1) {
                for (i in data) {
                    var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                    a = [
                        "<tr>" +
                        "<td>" + data[i].idText + "</td>",
                        "<td>" + data[i].nombreLookup + "</td>",
                        "<td>" + data[i].atributo1 + "</td>",
                        "<td style='text-align: center;'>" +
                        "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                        "<button onclick='editarPrenda(this);' idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "' posicion='" + data[i].atributo1 + "' descripcion='" + data[i].descripcion + "' class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " +
                        (data[i].estatus == 1 ? "<button onclick='bajarPrenda(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                        (data[i].estatus == 0 ? "<button onclick='reactivar(" + data[i].idLookup + ")' class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-sort-up'></i></button>" : " ") +
                        "</td>" +

                        "<tr>"
                    ];
                    b.push(a);
                }
            } else {
                for (i in data) {
                    var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                    if (data[i].estatus == 1) {
                        a = [
                            "<tr>" +
                            "<td>" + data[i].idText + "</td>",
                            "<td>" + data[i].nombreLookup + "</td>",
                            "<td>" + data[i].atributo1 + "</td>",
                            "<td style='text-align: center;'>" +
                            "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                            (rolEditar == 1 ? "<button onclick='editarPrenda(this);' idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "' posicion='" + data[i].atributo1 + "' descripcion='" + data[i].descripcion + "' class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>" : " ") +
                            (rolEliminar == 1 ? "<button onclick='bajarPrenda(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                            "</td>" +

                            "<tr>"
                        ];
                        b.push(a);
                    }
                }
            }
            var tablaPrendas = $('#idtable4').DataTable({
                "data": b,
                "ordering": false,
                "pageLength": 5,
                "responsive": true,
                "stateSave": true,
                "drawCallback": function () {
                    $('.popoverxd').popover({
                        container: 'body',
                        trigger: 'hover'
                    });
                },
                "columnDefs": [{
                    "type": "html",
                    "targets": '_all'
                },
                {
                    targets: 3,
                    className: 'dt-body-center'
                }
                ],
                "lengthMenu": [
                    [5, 10, 25, 50, 100],
                    [5, 10, 25, 50, 100]
                ],
                "language": {
                    "sProcessing": "Procesando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "No se encontraron resultados",
                    "sEmptyTable": "Ningún dato disponible en esta tabla =(",
                    "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "sInfoThousands": ",",
                    "sLoadingRecords": "Cargando...",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                    },
                    "oAria": {
                        "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                    },
                    "buttons": {
                        "copy": "Copiar",
                        "colvis": "Visibilidad"
                    }
                }
            });
            new $.fn.dataTable.FixedHeader(tablaPrendas);
        },
        error: (e) => {

        }
    })
}

function listarGeneros() {

    $.ajax({
        method: "GET",
        url: "/listar",
        data: {
            "Tipo": "Familia Genero"
        },
        success: (data) => {
            $('#quitar5').remove();
            $('#contenedorTabla5').append("<div class='modal-body' id='quitar5'>" +
                "<table class='table table-striped table-bordered' id='idtable5' style='width:100%'>" +
                "<thead>" +
                "<tr>" +
                "<th>Clave</th>" +
                "<th>Nombre</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "</table>" + "</div>");
            var a;
            var b = [];
            if (rolAdmin == 1) {
                for (i in data) {
                    var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                    a = [
                        "<tr>" +
                        "<td>" + data[i].idText + "</td>",
                        "<td>" + data[i].nombreLookup + "</td>",
                        "<td style='text-align: center;'>" +
                        "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                        "<button onclick='editarGenero(this);' idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "'  class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " +
                        (data[i].estatus == 1 ? "<button onclick='bajarGenero(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                        (data[i].estatus == 0 ? "<button onclick='reactivar(" + data[i].idLookup + ")' class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-sort-up'></i></button>" : " ") +
                        "</td>" +

                        "<tr>"
                    ];
                    b.push(a);
                }
            } else {
                for (i in data) {
                    var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                    if (data[i].estatus == 1) {
                        a = [
                            "<tr>" +
                            "<td>" + data[i].idText + "</td>",
                            "<td>" + data[i].nombreLookup + "</td>",
                            "<td style='text-align: center;'>" +
                            "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                            (rolEditar == 1 ? "<button onclick='editarGenero(this);' idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "'  class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>" : " ") +
                            (rolEliminar == 1 ? "<button onclick='bajarGenero(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                            "</td>" +

                            "<tr>"
                        ];
                        b.push(a);
                    }
                }
            }
            var tablaGeneros = $('#idtable5').DataTable({
                "data": b,
                "ordering": false,
                "pageLength": 5,
                "responsive": true,
                "stateSave": true,
                "drawCallback": function () {
                    $('.popoverxd').popover({
                        container: 'body',
                        trigger: 'hover'
                    });
                },
                "columnDefs": [{
                    "type": "html",
                    "targets": '_all'
                },
                {
                    targets: 2,
                    className: 'dt-body-center'
                }
                ],
                "lengthMenu": [
                    [5, 10, 25, 50, 100],
                    [5, 10, 25, 50, 100]
                ],
                "language": {
                    "sProcessing": "Procesando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "No se encontraron resultados",
                    "sEmptyTable": "Ningún dato disponible en esta tabla =(",
                    "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "sInfoThousands": ",",
                    "sLoadingRecords": "Cargando...",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                    },
                    "oAria": {
                        "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                    },
                    "buttons": {
                        "copy": "Copiar",
                        "colvis": "Visibilidad"
                    }
                }
            });
            new $.fn.dataTable.FixedHeader(tablaGeneros);
        },
        error: (e) => {

        }
    })
}
// /////////////////////////////
function listarComposiciones() {

    $.ajax({
        method: "GET",
        url: "/listar",
        data: {
            "Tipo": "Familia Composicion"
        },
        success: (data) => {
            $('#quitar6').remove();
            $('#contenedorTabla6').append("<div class='modal-body' id='quitar6'>" +
                "<table class='table  table-striped table-bordered' id='idtable6' style='width:100%'>" +
                "<thead>" +
                "<tr>" +
                "<th>Clave</th>" +
                "<th>Nombre</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "</table>" + "</div>");
            var a;
            var b = [];
            if (rolAdmin == 1) {
                for (i in data) {
                    var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                    a = [
                        "<tr>" +
                        "<td>" + data[i].idText + "</td>",
                        "<td>" + data[i].nombreLookup + "</td>",
                        "<td style='text-align: center;'>" +
                        "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                        "<button onclick='editarComposicion(this);' idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "'  class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " +
                        (data[i].estatus == 1 ? "<button onclick='bajarComposicion(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                        (data[i].estatus == 0 ? "<button onclick='reactivar(" + data[i].idLookup + ")' class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-sort-up'></i></button>" : " ") +
                        "</td>" +

                        "<tr>"
                    ];
                    b.push(a);
                }
            } else {
                for (i in data) {
                    var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                    if (data[i].estatus == 1) {
                        a = [
                            "<tr>" +
                            "<td>" + data[i].idText + "</td>",
                            "<td>" + data[i].nombreLookup + "</td>",
                            "<td style='text-align: center;'>" +
                            "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                            (rolEditar == 1 ? "<button onclick='editarComposicion(this);' idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "'  class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>" : " ") +
                            (rolEliminar == 1 ? "<button onclick='bajarComposicion(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                            "</td>" +

                            "<tr>"
                        ];
                        b.push(a);
                    }
                }
            }
            var tablaComponer = $('#idtable6').DataTable({
                "data": b,
                "ordering": false,
                "pageLength": 5,
                "responsive": true,
                "stateSave": true,
                "drawCallback": function () {
                    $('.popoverxd').popover({
                        container: 'body',
                        trigger: 'hover'
                    });
                },
                "columnDefs": [{
                    "type": "html",
                    "targets": '_all'
                },
                {
                    targets: 2,
                    className: 'dt-body-center'
                }
                ],
                "lengthMenu": [
                    [5, 10, 25, 50, 100],
                    [5, 10, 25, 50, 100]
                ],
                "language": {
                    "sProcessing": "Procesando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "No se encontraron resultados",
                    "sEmptyTable": "Ningún dato disponible en esta tabla =(",
                    "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "sInfoThousands": ",",
                    "sLoadingRecords": "Cargando...",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                    },
                    "oAria": {
                        "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                    },
                    "buttons": {
                        "copy": "Copiar",
                        "colvis": "Visibilidad"
                    }
                }
            });
            new $.fn.dataTable.FixedHeader(tablaComponer);
        },
        error: (e) => {

        }
    })
}

function listarCuidados() {

    $.ajax({
        method: "GET",
        url: "/listar",
        data: {
            "Tipo": "Instruccion Cuidado"
        },
        success: (data) => {
            $('#quitar7').remove();
            $('#contenedorTabla7').append("<div class='modal-body' id='quitar7'>" +
                "<table class='table table-striped table-bordered' id='idtable7' style='width:100%'>" +
                "<thead>" +
                "<tr>" +
                "<th>Clave</th>" +
                "<th>Nombre</th>" +
                "<th>S&iacute;mbolo</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "</table>" + "</div>");
            var a;
            var b = [];
            if (rolAdmin == 1) {
                for (i in data) {
                    var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                    a = [
                        "<tr>" +
                        "<td>" + data[i].idText + "</td>",
                        "<td>" + data[i].nombreLookup + "</td>",
                        "<td> <img class='img-thumbnail rounded float-left' style='width: 50px; height: 50px;' src='/uploads/cuidados/" + data[i].atributo1 + "' /> </td>",
                        "<td style='text-align: center;'>" +
                        "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                        "<button onclick='editarCuidado(this);' imagen='" + data[i].atributo1 + "' idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "'  class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " +
                        (data[i].estatus == 1 ? "<button onclick='bajarCuidado(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                        (data[i].estatus == 0 ? "<button onclick='reactivar(" + data[i].idLookup + ")' class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-sort-up'></i></button>" : " ") +
                        "</td>" +

                        "<tr>"
                    ];
                    b.push(a);
                }
            } else {
                for (i in data) {
                    var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                    if (data[i].estatus == 1) {
                        a = [
                            "<tr>" +
                            "<td>" + data[i].idText + "</td>",
                            "<td>" + data[i].nombreLookup + "</td>",
                            "<td> <img class='img-thumbnail rounded float-left' style='width: 50px; height: 50px;' src='/uploads/cuidados/" + data[i].atributo1 + "' /> </td>",
                            "<td style='text-align: center;'>" +
                            "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                            (rolEditar == 1 ? "<button onclick='editarCuidado(this);' idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "'  class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>" : " ") +
                            (rolEliminar == 1 ? "<button onclick='bajarCuidado(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                            "</td>" +

                            "<tr>"
                        ];
                        b.push(a);
                    }
                }
            }
            var tablaCuidados = $('#idtable7').DataTable({
                "data": b,
                "ordering": false,
                "pageLength": 5,
                "responsive": true,
                "stateSave": true,
                "drawCallback": function () {
                    $('.popoverxd').popover({
                        container: 'body',
                        trigger: 'hover'
                    });
                },
                "columnDefs": [{
                    "type": "html",
                    "targets": '_all'
                },
                {
                    targets: 3,
                    className: 'dt-body-center'
                }
                ],
                "lengthMenu": [
                    [5, 10, 25, 50, 100],
                    [5, 10, 25, 50, 100]
                ],
                "language": {
                    "sProcessing": "Procesando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "No se encontraron resultados",
                    "sEmptyTable": "Ningún dato disponible en esta tabla =(",
                    "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "sInfoThousands": ",",
                    "sLoadingRecords": "Cargando...",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                    },
                    "oAria": {
                        "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                    },
                    "buttons": {
                        "copy": "Copiar",
                        "colvis": "Visibilidad"
                    }
                }
            });
            new $.fn.dataTable.FixedHeader(tablaCuidados);
        },
        error: (e) => {

        }
    })
}

function listarMedidas() {

    $.ajax({
        method: "GET",
        url: "/listar",
        data: {
            "Tipo": "Unidad Medida"
        },
        success: (data) => {
            $('#quitar8').remove();
            $('#contenedorTabla8').append("<div class='modal-body' id='quitar8'>" +
                "<table class='table table-striped table-bordered' id='idtable8' style='width:100%'>" +
                "<thead>" +
                "<tr>" +
                "<th>Clave</th>" +
                "<th>Nombre</th>" +
                "<th>Símbolo</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "</table>" + "</div>");
            var a;
            var b = [];
            if (rolAdmin == 1) {
                for (i in data) {
                    var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                    a = [
                        "<tr>" +
                        "<td>" + data[i].idText + "</td>",
                        "<td>" + data[i].nombreLookup + "</td>",
                        "<td>" + data[i].descripcionLookup + "</td>",
                        "<td class='text-center'>" +
                        "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                        "<button onclick='editarMedida(this);' idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "' simbolo='" + data[i].descripcionLookup + "' class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " +
                        (data[i].estatus == 1 ? "<button onclick='bajarMedida(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                        (data[i].estatus == 0 ? "<button onclick='reactivar(" + data[i].idLookup + ")' class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-sort-up'></i></button>" : " ") +
                        "</td>" +

                        "<tr>"
                    ];
                    b.push(a);
                }
            } else {
                for (i in data) {
                    var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                    if (data[i].estatus == 1) {
                        a = [
                            "<tr>" +
                            "<td>" + data[i].idText + "</td>",
                            "<td>" + data[i].nombreLookup + "</td>",
                            "<td>" + data[i].descripcionLookup + "</td>",
                            "<td class='text-center'>" +
                            "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                            (rolEditar == 1 ? "<button onclick='editarMedida(this);' idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "' simbolo='" + data[i].descripcionLookup + "' class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>" : " ") +
                            (rolEliminar == 1 ? "<button onclick='bajarMedida(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                            "</td>" +

                            "<tr>"
                        ];
                        b.push(a);
                    }
                }
            }
            var tabla = $('#idtable8').DataTable({
                "data": b,
                "ordering": false,
                "pageLength": 5,
                "responsive": true,
                "stateSave": true,
                "drawCallback": function () {
                    $('.popoverxd').popover({
                        container: 'body',
                        trigger: 'hover'
                    });
                },
                "columnDefs": [{
                    "type": "html",
                    "targets": '_all'
                },
                {
                    targets: 3,
                    className: 'dt-body-center'
                }
                ],
                "lengthMenu": [
                    [5, 10, 25, 50, 100],
                    [5, 10, 25, 50, 100]
                ],
                "language": {
                    "sProcessing": "Procesando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "No se encontraron resultados",
                    "sEmptyTable": "Ningún dato disponible en esta tabla =(",
                    "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "sInfoThousands": ",",
                    "sLoadingRecords": "Cargando...",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                    },
                    "oAria": {
                        "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                    },
                    "buttons": {
                        "copy": "Copiar",
                        "colvis": "Visibilidad"
                    }
                }
            });
            new $.fn.dataTable.FixedHeader(tabla);
        },
        error: (e) => {

        }
    })
}
// ///////////////////

function listarMateriales() {

    $.ajax({
        method: "GET",
        url: "/listar-material-clasificacion",
        data: {},
        success: (data) => {
            $('#quitar9').remove();
            $('#contenedorTabla9').append("<div class='modal-body' id='quitar9'>" +
                "<table class='table table-striped table-bordered' id='idtable9' style='width:100%'>" +
                "<thead>" +
                "<tr>" +
                "<th>Clave</th>" +
                "<th>Nombre</th>" +
                "<th>Tipo</th>" +
                "<th>Clasificacion</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "</table>" + "</div>");
            var a;
            var b = [];
            if (rolAdmin == 1) {
                for (i in data) {
                    var creacion = data[i][9] == null ? "" : data[i][9];
                    a = [
                        "<tr>" +
                        "<td>" + data[i][1] + "</td>",
                        "<td>" + data[i][2] + "</td>",
                        (data[i][3] == 1 ? "<td>Material Principal</td>" : "<td>Material General</td>"),
                        "<td>" + data[i][4] + "</td>",
                        "<td style='text-align: center;'>" +
                        "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i][8] + " <br /><strong>Fecha de creación:</strong> " + data[i][7] + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i][10] + "'><i class='fas fa-info'></i></button> " +
                        "<button onclick='editarMaterial(this);' atributo1='" + data[i][3] + "' atributo2='" + data[i][5] + "' idlookup='" + data[i][0] + "' nombre='" + data[i][2] + "'  class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " +
                        (data[i][6] == 1 ? "<button onclick='bajarMaterial(" + data[i][0] + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                        (data[i][6] == 0 ? "<button onclick='reactivar(" + data[i][0] + ")' class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-sort-up'></i></button>" : " ") +
                        "</td>" +

                        "<tr>"
                    ];
                    b.push(a);
                }
            } else {
                for (i in data) {
                    var creacion = data[i][9] == null ? "" : data[i][9];
                    if (data[i][6] == 1) {
                        a = [
                            "<tr>" +
                            "<td>" + data[i][1] + "</td>",
                            "<td>" + data[i][2] + "</td>",
                            (data[i][3] == 1 ? "<td>Material Principal</td>" : "<td>Material General</td>"),
                            "<td>" + data[i][4] + "</td>",
                            "<td style='text-align: center;'>" +
                            "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i][7] + " <br /><strong>Fecha de creación:</strong> " + data[i][8] + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i][10] + "'><i class='fas fa-info'></i></button> " +
                            (rolEditar == 1 ? "<button onclick='editarMaterial(this);' atributo1='" + data[i][3] + "' atributo2='" + data[i][5] + "' idlookup='" + data[i][0] + "' nombre='" + data[i][2] + "'  class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " :"")+
                            (rolEditar == 1 ? "<button onclick='bajarMaterial(" + data[i][0] + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                            (rolEliminar == 1 ? "<button onclick='reactivar(" + data[i][0] + ")' class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-sort-up'></i></button>" : " ") +
                            "</td>" +

                            "<tr>"


                        ];
                        b.push(a);
                    }
                }
            }
            var tablaMateriales = $('#idtable9').DataTable({
                "data": b,
                "ordering": false,
                "pageLength": 5,
                "responsive": true,
                "stateSave": true,
                "drawCallback": function () {
                    $('.popoverxd').popover({
                        container: 'body',
                        trigger: 'hover'
                    });
                },
                "columnDefs": [{
                    "type": "html",
                    "targets": '_all'
                },
                {
                    targets: 3,
                    className: 'dt-body-center'
                }
                ],
                "lengthMenu": [
                    [5, 10, 25, 50, 100],
                    [5, 10, 25, 50, 100]
                ],
                "language": {
                    "sProcessing": "Procesando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "No se encontraron resultados",
                    "sEmptyTable": "Ningún dato disponible en esta tabla =(",
                    "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "sInfoThousands": ",",
                    "sLoadingRecords": "Cargando...",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                    },
                    "oAria": {
                        "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                    },
                    "buttons": {
                        "copy": "Copiar",
                        "colvis": "Visibilidad"
                    }
                }
            });
            new $.fn.dataTable.FixedHeader(tablaMateriales);
        },
        error: (e) => {

        }
    })
}
// /////////////

function listarMarcadores() {

    $.ajax({
        method: "GET",
        url: "/listar",
        data: {
            "Tipo": "Marcador"
        },
        success: (data) => {
            $('#quitar10').remove();
            $('#contenedorTabla10').append("<div class='modal-body' id='quitar10'>" +
                "<table class='table table-striped table-bordered' id='idtable10' style='width:100%'>" +
                "<thead>" +
                "<tr>" +
                "<th>Clave</th>" +
                "<th>Nombre</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "</table>" + "</div>");
            var a;
            var b = [];
            if (rolAdmin == 1) {
                for (i in data) {
                    var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                    a = [
                        "<tr>" +
                        "<td>" + data[i].idText + "</td>",
                        "<td>" + data[i].nombreLookup + "</td>",
                        "<td style='text-align: center;'>" +
                        "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                        "<button onclick='editarMarcador(this);' idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "'  class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " +
                        (data[i].estatus == 1 ? "<button onclick='bajarMarcador(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                        (data[i].estatus == 0 ? "<button onclick='reactivar(" + data[i].idLookup + ")' class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-sort-up'></i></button>" : " ") +
                        "</td>" +

                        "<tr>"
                    ];
                    b.push(a);
                }
            } else {
                for (i in data) {
                    var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                    if (data[i].estatus == 1) {
                        a = [
                            "<tr>" +
                            "<td>" + data[i].idText + "</td>",
                            "<td>" + data[i].nombreLookup + "</td>",
                            "<td style='text-align: center;'>" +
                            "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                            (rolEditar == 1 ? "<button onclick='editarMarcador(this);' idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "'  class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>" : " ") +
                            (rolEliminar == 1 ? "<button onclick='bajarMarcador(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                            "</td>" +

                            "<tr>"
                        ];
                        b.push(a);
                    }
                }
            }
            var tablaMarcador = $('#idtable10').DataTable({
                "data": b,
                "ordering": false,
                "pageLength": 5,
                "responsive": true,
                "stateSave": true,
                "drawCallback": function () {
                    $('.popoverxd').popover({
                        container: 'body',
                        trigger: 'hover'
                    });
                },
                "columnDefs": [{
                    "type": "html",
                    "targets": '_all'
                },
                {
                    targets: 2,
                    className: 'dt-body-center'
                }
                ],
                "lengthMenu": [
                    [5, 10, 25, 50, 100],
                    [5, 10, 25, 50, 100]
                ],
                "language": {
                    "sProcessing": "Procesando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "No se encontraron resultados",
                    "sEmptyTable": "Ningún dato disponible en esta tabla =(",
                    "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "sInfoThousands": ",",
                    "sLoadingRecords": "Cargando...",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                    },
                    "oAria": {
                        "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                    },
                    "buttons": {
                        "copy": "Copiar",
                        "colvis": "Visibilidad"
                    }
                }
            });
            new $.fn.dataTable.FixedHeader(tablaMarcador);
        },
        error: (e) => {

        }
    })
}

function listarComposiciones1() {

    $.ajax({
        method: "GET",
        url: "/listar",
        data: {
            "Tipo": "Composicion"
        },
        success: (data) => {
            $('#quitar11').remove();
            $('#contenedorTabla11').append("<div class='modal-body' id='quitar11'>" +
                "<table class='table table-striped table-bordered' id='idtable11' style='width:100%'>" +
                "<thead>" +
                "<tr>" +
                "<th>Clave</th>" +
                "<th>Nombre</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "</table>" + "</div>");
            var a;
            var b = [];
            if (rolAdmin == 1) {
                for (i in data) {
                    var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                    a = [
                        "<tr>" +
                        "<td>" + data[i].idText + "</td>",
                        "<td>" + data[i].nombreLookup + "</td>",
                        "<td style='text-align: center;'>" +
                        "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                        "<button onclick='editarComposicion1(this);' idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "'  class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " +
                        (data[i].estatus == 1 ? "<button onclick='bajarComposicion1(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                        (data[i].estatus == 0 ? "<button onclick='reactivar(" + data[i].idLookup + ")' class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-sort-up'></i></button>" : " ") +
                        "</td>" +
                        "<tr>"
                    ];
                    b.push(a);
                }
            } else {
                for (i in data) {
                    var creacion = data[i].actualizadoPor == null ? "" : data[i].actualizadoPor;
                    if (data[i].estatus == 1) {
                        a = [
                            "<tr>" +
                            "<td>" + data[i].idText + "</td>",
                            "<td>" + data[i].nombreLookup + "</td>",
                            "<td style='text-align: center;'>" +
                            "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                            (rolEditar == 1 ? "<button onclick='editarComposicion1(this);' idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "'  class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>" : " ") +
                            (rolEliminar == 1 ? "<button onclick='bajarComposicion1(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                            "</td>" +
                            "<tr>"
                        ];
                        b.push(a);
                    }
                }
            }
            var tablaMarcador = $('#idtable11').DataTable({
                "data": b,
                "ordering": false,
                "pageLength": 5,
                "responsive": true,
                "stateSave": true,
                "drawCallback": function () {
                    $('.popoverxd').popover({
                        container: 'body',
                        trigger: 'hover'
                    });
                },
                "columnDefs": [{
                    "type": "html",
                    "targets": '_all'
                },
                {
                    targets: 2,
                    className: 'dt-body-center'
                }
                ],
                "lengthMenu": [
                    [5, 10, 25, 50, 100],
                    [5, 10, 25, 50, 100]
                ],
                "language": {
                    "sProcessing": "Procesando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "No se encontraron resultados",
                    "sEmptyTable": "Ningún dato disponible en esta tabla =(",
                    "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "sInfoThousands": ",",
                    "sLoadingRecords": "Cargando...",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                    },
                    "oAria": {
                        "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                    },
                    "buttons": {
                        "copy": "Copiar",
                        "colvis": "Visibilidad"
                    }
                }
            });
            new $.fn.dataTable.FixedHeader(tablaMarcador);
        },
        error: (e) => {

        }
    })
}

function listarPreciosComposicion() {

    $.ajax({
        type: "GET",
        url: "/listarPrecioComposiciones",
        success: (data) => {

            $('#quitar12').remove();
            $('#contenedorTabla12').append("<div class='modal-body' id='quitar12'>" +
                "<table class='table table-striped table-bordered' id='idtable12' style='width:100%'>" +
                "<thead>" +
                "<tr>" +
                "<th>Prenda</th>" +
                "<th>Familia de Composicion</th>" +
                "<th>Precio</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "</table>" + "</div>");
            var a;
            var b = [];
            if (rolAdmin == 1) {
                console.log(data);
                for (i in data) {
                    var creacion = data[i][5] == null ? "" : data[i][5];

                    a = [
                        "<tr>" +
                        "<td>" + data[i][1] + "</td>",
                        "<td>" + data[i][2] + "</td>",
                        "<td>" + data[i][3] + "</td>" +
                        "<td> <input type='hidden' value=" + data[i][0] + " disabled> </td>",
                        "<td style='text-align: center'>" +
                        "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i][4] + " <br /><strong>Fecha de creación:</strong> " + data[i][6] + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i][7] + "'><i class='fas fa-info'></i></button> " +
                        " <button onclick='editarPrecioFamComposicion(" + data[i][0] + "," + data[i][9] + "," + data[i][10] + ", " + data[i][3] + ")' id='" + data[i][0] + "' class='btn btn-warning btn-circle btn-sm popoverxd edit_precio_composicion' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " +
                        (data[i][8] == 1 ? "<button onclick='bajarPrecioComposicion(" + data[i][0] + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                        (data[i][8] == 0 ? "<button onclick='reactivarPrecioComposicion(" + data[i][0] + ")' class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-sort-up'></i></button>" : " ") +
                        "</td>" +

                        "<tr>"
                    ];
                    b.push(a);
                }
            } else {
                for (i in data) {
                    var creacion = data[i][5] == null ? "" : data[i][5];
                    if (data[i][8] == 1) {
                        a = [
                            "<tr>" +
                            "<td>" + data[i][1] + "</td>",
                            "<td>" + data[i][2] + "</td>",
                            "<td>$ " + data[i][3] + "</td>" +
                            "<td> <input type='hidden' value=" + data[i][0] + " disabled> </td>",
                            "<td style='text-align: center'>" +
                            "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i][4] + " <br /><strong>Fecha de creación:</strong> " + data[i][6] + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i][7] + "'><i class='fas fa-info'></i></button> " +
                            (rolEditar == 1 ? " <button onclick='editarPrecioFamComposicion(" + data[i][0] + "," + data[i][9] + "," + data[i][10] + ", " + data[i][3] + ")' id='" + data[i][0] + "' class='btn btn-warning btn-circle btn-sm popoverxd edit_precio_composicion' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " : " ") +
                            (rolEliminar == 1 ? "<button onclick='bajarPrecioComposicion(" + data[i][0] + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                            "</td>" +

                            "<tr>"
                        ];
                        b.push(a);

                    }
                }
            }
            var tablaPrecioComposicion = $('#idtable12').DataTable({
                "data": b,
                "ordering": false,
                "pageLength": 5,
                "responsive": true,
                "stateSave": true,
                "drawCallback": function () {
                    $('.popoverxd').popover({
                        container: 'body',
                        trigger: 'hover'
                    });
                },
                "columnDefs": [{
                    "type": "html",
                    "targets": '_all'
                },
                {
                    targets: 3,
                    className: 'dt-body-center'
                }
                ],
                "lengthMenu": [
                    [5, 10, 25, 50, 100],
                    [5, 10, 25, 50, 100]
                ],
                "language": {
                    "sProcessing": "Procesando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "No se encontraron resultados",
                    "sEmptyTable": "Ningún dato disponible en esta tabla =(",
                    "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "sInfoThousands": ",",
                    "sLoadingRecords": "Cargando...",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                    },
                    "oAria": {
                        "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                    },
                    "buttons": {
                        "copy": "Copiar",
                        "colvis": "Visibilidad"
                    }
                }
            });
            new $.fn.dataTable.FixedHeader(tablaPrecioComposicion);


        },
        error: (e) => {

        }
    })
}

function agregarPrecioComposicion() {
    Swal.fire({
        title: 'Agregar precio a una composición',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="prenda">Prenda</label>' +
            '<select class="form-control" id="selectPrenda" data-live-search="true">' + listarPrendasSelect() + "</select>" +
            '<label for="famComposicion">Familia de composici&oacute;n</label>' +
            '<select class="form-control" data-live-search="true" id="famComposicion">' + listarFamiliaComposicion() + '</select>' +
            '<label for="precioFamComposicion">Precio </label><br>' +
            '<input type="number" class="swal2-input" id="precioFamComposicion" placeholder="120.50">' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Agregar',
        confirmButtonColor: '#0288d1',
        preConfirm: (color) => {
            if ($('#selectPrenda').val() == "" || $('#famComposicion').val() == "" || $('#precioFamComposicion').val() == "") {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value) {
            var prenda = $('#selectPrenda').val();
            var famComposicion = $('#famComposicion').val();
            var precio = $('#precioFamComposicion').val();
            $.ajax({
                method: "GET",
                url: "/verifduplicadoPrecioComposicion",
                data: {
                    idPrenda: prenda,
                    idFamComposicion: famComposicion
                },
                success: (data) => {
                    console.log(data);
                    if (data == false) {
                        $.ajax({
                            method: "POST",
                            url: "/agregarPrecioComposicion",
                            data: {
                                "_csrf": $('#token').val(),
                                idPrenda: prenda,
                                idFamComposicion: famComposicion,
                                precio: precio
                            },

                            success: (data) => {
                                listarPreciosComposicion();
                                Swal.fire({
                                    position: 'center',
                                    icon: 'success',
                                    title: 'Insertado correctamente',
                                    showConfirmButton: false,
                                    timer: 1250
                                })
                            },
                            error: (e) => {
                                Swal.fire({
                                    position: 'center',
                                    icon: 'error',
                                    title: 'Algo salión mal, intente más tarde',
                                    showConfirmButton: false,
                                    timer: 1250
                                })
                            }
                        })
                    } else {
                        Swal.fire({
                            position: 'center',
                            icon: 'error',
                            title: 'Registro duplicado',
                            showConfirmButton: false,
                            timer: 1250
                        })
                    }
                },
                error: (e) => { }
            })
        }
    });
}

function editarPrecioFamComposicion(idPrecioComposicion, idPrenda, idFamComposicion, precio) {
    Swal.fire({
        title: 'Agregar precio a una composición',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="prenda">Prenda</label>' +
            '<select class="form-control" id="selectPrenda" data-live-search="true">' + listarPrendasSelect(idPrenda) + "</select>" +
            '<label for="famComposicion">Familia de composici&oacute;n</label>' +
            '<select class="form-control" data-live-search="true" id="famComposicion">' + listarFamiliaComposicion(idFamComposicion) + '</select>' +
            '<label for="precioFamComposicion">Precio </label><br>' +
            '<input type="number" class="swal2-input" name="precioComposiciones" id="precioFamComposicion" placeholder="120.50" value=' + precio + '>' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Agregar',
        confirmButtonColor: '#0288d1',
        preConfirm: (color) => {
            if ($('#selectPrenda').val() == "" || $('#famComposicion').val() == "" || $('#precioFamComposicion').val() == "") {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value) {
            var prenda = $('#selectPrenda').val();
            var famComposicion = $('#famComposicion').val();
            var precio = $('#precioFamComposicion').val();
            var idPrecioCompos = idPrecioComposicion;

            $.ajax({
                method: "POST",
                url: "/editarPrecioComposicion",
                data: {
                    "_csrf": $('#token').val(),
                    idPrenda: prenda,
                    idFamComposicion: famComposicion,
                    precio: precio,
                    idPrecioComposicion: idPrecioCompos
                },

                success: (data) => {
                    listarPreciosComposicion();
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Editado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                },
                error: (e) => {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Algo salión mal, intente más tarde',
                        showConfirmButton: false,
                        timer: 1250
                    })
                }
            })
        } else {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Registro duplicado',
                showConfirmButton: false,
                timer: 1250
            })
        }
    })
}

function bajarPrecioComposicion(idPrecioCompos) {
    $.ajax({
        method: "POST",
        url: "/bajarPrecioComposicion",
        data: {
            "_csrf": $('#token').val(),
            idPrecioComposicion: idPrecioCompos
        },

        success: (data) => {
            listarPreciosComposicion();
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Se dió de baja correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        },
        error: (e) => {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Algo salión mal, intente más tarde',
                showConfirmButton: false,
                timer: 1250
            })
        }
    });
}

function reactivarPrecioComposicion(idPrecioCompos) {
    $.ajax({
        method: "POST",
        url: "/reactivarPrecioComposicion",
        data: {
            "_csrf": $('#token').val(),
            idPrecioComposicion: idPrecioCompos
        },

        success: (data) => {
            listarPreciosComposicion();
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Se dió de alta correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        },
        error: (e) => {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Algo salión mal, intente más tarde',
                showConfirmButton: false,
                timer: 1250
            })
        }
    });
}

function listarPrendasSelect(idPrenda) {
    $('#selectPrenda').find("option").remove();
    $.ajax({
        type: "GET",
        url: "/listar",
        data: { "Tipo": "Familia Prenda" },
        success: (data) => {
            var listaPrendas = "";
            for (i in data) {
                listaPrendas += "<option value=" + data[i].idLookup + ">" + data[i].nombreLookup + "</option>";
            }
            $('#selectPrenda').append(listaPrendas);
            if (idPrenda != null) {
                $('#selectPrenda option[value=' + idPrenda + ']').attr("selected", true);
            }
        },
        error: (e) => {

        }
    });
}

function listarFamiliaComposicion(idFamComposicion) {
    $('#famComposicion').find("option").remove();
    $.ajax({
        type: "GET",
        url: "/listar",
        data: { "Tipo": "Familia Composicion" },
        success: (data) => {
            var listaPrendas = "";
            for (i in data) {
                listaPrendas += "<option value=" + data[i].idLookup + ">" + data[i].nombreLookup + "</option>";
            }
            $('#famComposicion').append(listaPrendas);
            if (idFamComposicion != null) {
                $('#famComposicion option[value=' + idFamComposicion + ']').attr("selected", true);
            }
        },
        error: (e) => {

        }
    });
}


// Habilitar form de SweetAlert2
$('#detalleMarcas').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});
// //////////////////////7
// Habilitar form de SweetAlert2
$('#detalleColores').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});


function listarProveedores(proveedor) {
    $.ajax({
        type: "GET",
        url: "/listarProveedoresColores",
        success: (data) => {

            for (i in data) {
                $('#proveedorColor').append("<option value=" + data[i].idProveedor + " name" + data[i].nombreProveedor + ">" + data[i].nombreProveedor + "</option>");
            }
            if (proveedor != 1) {
                $('#proveedorColor option[value="' + proveedor + '"]').attr("selected", true);
            }
        }
    })
}
// Agregar Color
function agregarColor() {
    Swal.fire({
        title: 'Agregar color',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre del color</label>' +
            '<input type="text" class="swal2-input" name="color" id="color" placeholder="Rojo">' +
            '<label for="pedidonom">Código del color</label>' +
            '<input type="color" class="swal2-input" id="codigocolor" placeholder="Rojo">' +
            '<label for="proveedorColor">Proveedor</label>' +
            '<select class="form-control" data-live-search="true" id="proveedorColor"><option value="error">Seleccione uno...</option>' + listarProveedores(1) + '</select>' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Agregar',
        confirmButtonColor: '#0288d1',
        preConfirm: (color) => {
            if (document.getElementById("color").value.length < 1 || $('#proveedorColor').val() == "error") {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("color").value) {
            var Color = document.getElementById("color").value;
            var CodigoColor = document.getElementById("codigocolor").value;
            var proveedorColor = document.getElementById("proveedorColor").value;


            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': Color,
                    'Tipo': "Color"


                }

            }).done(function (data) {
                console.log($('#proveedorColor').val());
                if (data == false) {


                    $.ajax({
                        type: "POST",
                        url: "/guardarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'Color': Color,
                            'CodigoColor': CodigoColor,
                            'proveedorColor': proveedorColor

                        }

                    }).done(function (data) {
                        listarColores();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Insertado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                    // / window.setTimeout(function(){location.reload()}, 2000);
                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'registro duplicado no se ha insertado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });



        } // ///fin primer if
    })
}
// Editar color
$(document).on('click', '.edit_data_color', function () {
    var color_id = $(this).attr("id");
    var color_nombre = $(this).attr("value");
    var color_repr = $(this).attr("color");
    var provee = $(this).attr("proveedorColor");
    console.log(provee);
    Swal.fire({
        title: 'Editar color',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre del color</label>' +
            '<input type="text" class="form-control" name="color" id="color" value="' + color_nombre + '" placeholder="Rojo">' +
            '<label for="pedidonom">Codigo del color</label>' +
            '<input type="color" class="form-control" id="color_repr" value="' + color_repr + '" placeholder="Rojo">' +
            '<label for="proveedorColor">Proveedor</label>' +
            '<select class="form-control" id="proveedorColor" value=' + provee + '><option value="">Seleccione uno...</option>' + listarProveedores(provee) + '</select>' +

            '</div>' +
            '</div>',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#0288d1',
        preConfirm: (color) => {
            if (document.getElementById("color").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }


        }
    }).then((result) => {
        if (result.value && document.getElementById("color").value) {
            var Color = document.getElementById("color").value;
            var ColorRepr = document.getElementById("color_repr").value;
            var proveedorr = document.getElementById("proveedorColor").value;
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': Color,
                    'Tipo': "Color"
                }

            }).done(function (data) {
                if (data == false) {
                    $.ajax({
                        type: "POST",
                        url: "/editarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'Color': Color,
                            'idLookup': color_id,
                            'CodigoColor': ColorRepr,
                            'proveedor': proveedorr
                            // ,'Descripcion':Descripcion
                        }

                    }).done(function (data) {
                        listarColores();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'editado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                } else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Registro duplicado no se ha editado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });

        } // ////////////fin primer if
    })
})
// /////////////////////Reactivar
function reactivar(idreactivar) {
    var id = idreactivar;
    Swal.fire({
        title: '¿Desea reactivar?',
        icon: 'question',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Reactivar',
        confirmButtonColor: '#28A745',
    }).then((result) => {
        if (result.value && id != null) {
            $.ajax({
                type: "POST",
                url: "/reactivarcatalogo",
                data: {
                    "_csrf": $('#token').val(),
                    'idcatalogo': id
                }

            }).done(function (data) {
                switch (data) {
                    case "Color":
                        listarColores();
                        break;
                    case "Pieza Trazo":
                        listarTrazos();
                        break;
                    case "Familia Prenda":
                        listarPrendas();
                        break;
                    case "Familia Genero":
                        listarGeneros();
                        break;
                    case "Familia Composicion":
                        listarComposiciones();
                        break;
                    case "Instruccion Cuidado":
                        listarCuidados();
                        break;
                    case "Unidad Medida":
                        listarMedidas();
                        break;
                    case "Material":
                        listarMateriales();
                        break;
                    case "Marcador":
                        listarMarcadores();
                        break;
                    case "Composicion":
                        listarComposiciones1();
                }
            });
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Reactivado correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        } // ////////////termina result value
    })
}

// Dar de baja color
function bajarColor(idbaja) {
    var id = idbaja;
    Swal.fire({
        title: '¿Deseas dar de baja el color?',
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Dar de baja',
        confirmButtonColor: '#0288d1',
    }).then((result) => {
        if (result.value && id != null) {

            $.ajax({
                type: "POST",
                url: "/bajacatalogo",
                data: {
                    "_csrf": $('#token').val(),
                    'idcatalogo': id

                    // ,'Descripcion':Descripcion
                }

            }).done(function (data) {

                listarColores();
            });
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'dado de baja correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        } // ////////////termina result value
    })
}
// Reactivar color
$('#detalleTrazo').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});

// Agregar Pieza de Trazo
function agregarTrazo() {
    Swal.fire({
        title: 'Agregar trazo',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre pieza trazo</label>' +
            '<input type="text" class="swal2-input" name="trazo" id="trazo" placeholder="Delantero">' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Agregar',
        confirmButtonColor: '#0288d1',
        preConfirm: (trazo) => {
            if (document.getElementById("trazo").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("trazo").value) {
            var Trazo = document.getElementById("trazo").value;

            // //////////
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': Trazo,
                    'Tipo': 'Pieza Trazo'
                    // ,'Descripcion':Descripcion
                }

            }).done(function (data) {
                if (data == false) {
                    // /////////


                    $.ajax({
                        type: "POST",
                        url: "/guardarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'PiezaTrazo': Trazo
                            // ,'Descripcion':Descripcion
                        }

                    }).done(function (data) {
                        listarTrazos();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Insertado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                } else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'registro duplicado no se ha insertado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });

        } // /////////
    })
}
// Editar Trazo
$(document).on('click', '.edit_data_trazo', function () {
    var trazo_id = $(this).attr("id");
    var trazo_nombre = $(this).attr("value");
    Swal.fire({
        title: 'Editar pieza trazo',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre pieza trazo</label>' +
            '<input type="text" class="form-control" name="trazo" id="trazo" value="' + trazo_nombre + '" placeholder="Delantero">' +
            '<input type="hidden" value=" ' + trazo_id + ' ">' +
            '</div>' +
            '</div>',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#0288d1',
        preConfirm: (trazo) => {
            if (document.getElementById("trazo").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("trazo").value) {
            var Trazo = document.getElementById("trazo").value;
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': Trazo,
                    'Tipo': "Pieza Trazo"


                }

            }).done(function (data) {
                if (data == false) {

                    $.ajax({
                        type: "POST",
                        url: "/editarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'PiezaTrazo': Trazo,
                            'idLookup': trazo_id
                            // ,'Descripcion':Descripcion
                        }

                    }).done(function (data) {
                        listarTrazos();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'editado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Registro duplicado no se ha editado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });

        } // /fin if
    })
})
// Dar de baja pieza de trazo
function bajarTrazo(idbaja) {
    var id = idbaja;
    Swal.fire({
        title: '¿Deseas dar de baja el trazo?',
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Dar de baja',
        confirmButtonColor: '#0288d1',
    }).then((result) => {
        if (result.value && id != null) {

            $.ajax({
                type: "POST",
                url: "/bajacatalogo",
                data: {
                    "_csrf": $('#token').val(),
                    'idcatalogo': id

                    // ,'Descripcion':Descripcion
                }

            }).done(function (data) {

                listarTrazos();
            });
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'dado de baja correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        } // ////////////termina result value
    })
}
// Reactivar pieza de trazo
$('#detallePrenda').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});
// Agregar Familia de prendas
function agregarPrenda() {
    Swal.fire({
        title: 'Agregar familia prenda',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre de la familia prendas</label>' +
            '<input type="text" class="swal2-input" name="familia" id="familia" placeholder="Pantalón">' +
            '<label for="posicion">Ubicaci&oacute;n de la prenda</label>' +
            "<select class='form-control' id='posicion'>" +
            "<option value='superior'>Superior</option>" +
            "<option value='inferior'>Inferior</option>" +
            "</select>" +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Agregar',
        confirmButtonColor: '#0288d1',
        preConfirm: (familia) => {
            if (document.getElementById("familia").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("familia").value) {
            var FamiliaPrenda = document.getElementById("familia").value;
            var Posicion = document.getElementById("posicion").value;
            // ///////////////

            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': FamiliaPrenda,
                    'Tipo': "Familia Prenda"

                    // ,'Descripcion':Descripcion
                }

            }).done(function (data) {
                if (data == false) {
                    // /////////////////
                    $.ajax({
                        type: "POST",
                        url: "/guardarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'FamiliaPrenda': FamiliaPrenda,
                            'Posicion': Posicion


                            // ,'Descripcion':Descripcion
                        }

                    }).done(function (data) {
                        listarPrendas();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Insertado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'registro duplicado no se ha insertado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });

        } // /////////
    })
}

// Editar Prenda


function editarPrenda(e) {
    var descr = e.getAttribute("descripcion");
    var pos = e.getAttribute("posicion") == "inferior" ? "selected" : "";
    Swal.fire({
        title: 'Editar prenda',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre de la familia prendas</label>' +
            '<input type="text" value="' + e.getAttribute("nombre") + '" class="swal2-input" name="nombre" id="nombre" placeholder="Pantalón">' +
            "<select class='form-control' id='posicion' value='" + e.getAttribute("posicion") + "'>" +
            "<option value='superior' >Superior</option>" +
            "<option value='inferior' " + pos + ">Inferior</option>" +
            "</select>" +
            '</div>' +
            '<div class="form-group col-sm-12">' +
            '<input type="hidden" value=" ' + e.getAttribute("idlookup") + ' " class="swal2-input" id="idlookup" placeholder="Pantalón">' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#0288d1',
        preConfirm: (nombre) => {
            if (document.getElementById("nombre").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("nombre").value && document.getElementById("idlookup").value) {
            var FamiliaPrenda = document.getElementById("nombre").value;
            var idLookup = document.getElementById("idlookup").value;
            var Posicion = document.getElementById("posicion").value;
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': FamiliaPrenda,
                    'Tipo': "Familia Prenda",
                    'atributo': Posicion


                }

            }).done(function (data) {
                if (data == false) {
                    $.ajax({
                        type: "POST",
                        url: "/editarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'FamiliaPrenda': FamiliaPrenda,
                            'Posicion': Posicion,
                            'idLookup': idLookup
                            // ,'Descripcion':Descripcion
                        }

                    }).done(function (data) {
                        listarPrendas();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'editado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Registro duplicado no se ha editado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });

        } // //fin if
    })
}

// Dar de baja prenda
function bajarPrenda(idbaja) {
    var id = idbaja;
    Swal.fire({
        title: '¿Deseas dar de baja la prenda?',
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Dar de baja',
        confirmButtonColor: '#0288d1',
    }).then((result) => {
        if (result.value && id != null) {
            $.ajax({
                type: "POST",
                url: "/bajacatalogo",
                data: {
                    "_csrf": $('#token').val(),
                    'idcatalogo': id

                    // ,'Descripcion':Descripcion
                }

            }).done(function (data) {

                listarPrendas();
            });
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'dado de baja correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        } // ////////////termina result value
    })
}
// Reactivar prenda
$('#detalleGenero').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});
// Agregar familia de genero
function agregarGenero() {
    Swal.fire({
        title: 'Agregar genero',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre del genero</label>' +
            '<input type="text" class="swal2-input" name="genero" id="genero" placeholder="Caballero">' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Agregar',
        confirmButtonColor: '#0288d1',
        preConfirm: (genero) => {
            if (document.getElementById("genero").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("genero").value) {
            var Genero = document.getElementById("genero").value;
            // //////////////
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': Genero,
                    'Tipo': 'Familia Genero'
                    // ,'Descripcion':Descripcion
                }

            }).done(function (data) {
                if (data == false) {
                    // ///////////
                    $.ajax({
                        type: "POST",
                        url: "/guardarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'FamiliaGenero': Genero

                            // ,'Descripcion':Descripcion
                        }

                    }).done(function (data) {
                        listarGeneros();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Insertado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'registro duplicado no se ha insertado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });

        }
    })
}

// Editar genero


function editarGenero(e) {
    var descr = e.getAttribute("descripcion");
    Swal.fire({
        title: 'Editar genero',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre de genero</label>' +
            '<input type="text" value="' + e.getAttribute("nombre") + '" name="nombre" class="swal2-input" id="nombre" placeholder="Caballero">' +
            '</div>' +
            '<div class="form-group col-sm-12">' +

            '<input type="hidden" value=" ' + e.getAttribute("idlookup") + ' " class="swal2-input" id="idlookup" placeholder="Parisina">' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#0288d1',
        preConfirm: (nombre) => {
            if (document.getElementById("nombre").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("nombre").value && document.getElementById("idlookup").value) {
            var FamiliaGenero = document.getElementById("nombre").value;

            var idLookup = document.getElementById("idlookup").value;
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': FamiliaGenero,
                    'Tipo': "Familia Genero"


                }

            }).done(function (data) {
                if (data == false) {
                    $.ajax({
                        type: "POST",
                        url: "/editarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'FamiliaGenero': FamiliaGenero,
                            'idLookup': idLookup
                            // ,'Descripcion':Descripcion
                        }

                    }).done(function (data) {
                        listarGeneros();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'editado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                    // window.setTimeout(function(){location.reload()}, 2000);
                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Registro duplicado no se ha editado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });
        } // fin if
    })
}
// Dar de baja familia de genero
function bajarGenero(idbaja) {
    var id = idbaja;
    Swal.fire({
        title: '¿Deseas dar de baja el genero?',
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Dar de baja',
        confirmButtonColor: '#0288d1',
    }).then((result) => {
        if (result.value && id != null) {
            $.ajax({
                type: "POST",
                url: "/bajacatalogo",
                data: {
                    "_csrf": $('#token').val(),
                    'idcatalogo': id

                    // ,'Descripcion':Descripcion
                }

            }).done(function (data) {

                listarGeneros();
            });
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'dado de baja correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        } // ////////////termina result value
    })
}
// Reactivar familia de genero
$('#detalleMantenimiento').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});
// Agregar composicion
function agregarComposicion() {
    listarcuidadosjson();
    Swal.fire({
        title: 'Agregar composición',
        html: '<div class="row" id="composicioncuidado">' +
            '<div id="hola" class="form-group col-sm-12">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre de composición</label>' +
            '<input type="text" class="form-control" name="composicion" id="composicion" placeholder="Polylicra">' +
            '</div>' +
            '<div class="form-group col-sm-12" id="selectcuidados">' +
            '<label for="pedidonom">Instrucciones de cuidado</label>' +

            '</div>' +
            '<div class="form-group col-sm-12">' +
            '<button class="btn btn-danger btn-block" id="agregarInstrucciones" onclick="insertar()">Agregar</button>' +
            '</div>' +
            '<div class="form-group col-sm-12" id="tabla">' +
            '<label for="pedidonom">Listado de indicaciones</label>' +
            '<table class="table">' +
            '<thead>' +
            '<tr>' +
            '<th scope="col">Código cuidado</th>' +
            '<th scope="col">Nombre</th>' +
            '<th scope="col"></th>' +
            '</tr>' +
            '</thead>' +
            '<tbody>' +

            '</tbody>' +
            '</table>' +
            '</div>' +
            '</div>' +
            '</div>',
        showCancelButton: false,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Confirmar',
        confirmButtonColor: '#0288d1',
        customClass: 'swal-wide',
        preConfirm: (composicion) => {
            console.log(document.getElementById("composicion").value);
            if (document.getElementById("composicion").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        listarComposiciones();

    })

}

function bajarcomposicioncuidado(idbaja) {
    var id = idbaja;
    if (id != null) {


        $.ajax({
            type: "POST",
            url: "/eliminarcomposicioncuidado",
            data: {
                "_csrf": $('#token').val(),
                'id': id

                // ,'Descripcion':Descripcion
            }

        }).done(function (data) {
            var idcomposicion = data.idLookup;
            var nombrecomposicion = data.nombreLookup;
            listarcuidadosjson2(idcomposicion);
            // ////////////
            listarcuidadosjson();
            // / $("#composicioncuidado").remove();
            Swal.fire({
                title: 'Agregar composición',
                html: '<div class="row" id="composicioncuidado">' +
                    '<div id="hola" class="form-group col-sm-12">' +
                    '<div class="form-group col-sm-12">' +
                    '<label for="pedidonom">Nombre de composición</label>' +
                    '<input type="text" class="form-control" id="composicion" value="' + nombrecomposicion + '" readonly>' +
                    '<input type="hidden" class="form-control" id="composicionmm" value="' + idcomposicion + '" placeholder="Polylicra">' +
                    '</div>' +
                    '<div class="form-group col-sm-12" id="selectcuidados">' +
                    '<label for="pedidonom">Instrucciones de cuidado</label>' +

                    '</div>' +
                    '<div class="form-group col-sm-12">' +
                    '<button class="btn btn-primary btn-block" id="agregarInstrucciones" onclick="insertar()">Agregar</button>' +
                    '</div>' +
                    '<div class="form-group col-sm-12" id="selectcuidados2">' +
                    '<label for="pedidonom">Listado de indicaciones</label>' +

                    '</div>' +
                    '</div>' +
                    '</div>',
                showCancelButton: false,
                cancelButtonColor: '#dc3545',
                cancelButtonText: 'Cancelar',
                confirmButtonText: 'Confirmar',
                confirmButtonColor: '#0288d1',
                customClass: 'swal-wide',
            });

        });

    } // ////////////termina result value
}


// ///////////////


function insertar() {
    if (document.getElementById("composicion").value.length < 1) {

        Swal.showValidationMessage(
            `Complete todos los campos`
        )
    }
    if (document.getElementById("selectcuidado").value && document.getElementById("composicion").value) {
        var Cuidado = document.getElementById("selectcuidado").value;
        console.log(document.getElementById("composicion").value);
        try {
            var Composicion = document.getElementById("composicion").value;
        } catch (err) {
            console.log("not found");
        }

        try {
            var Cuidado = document.getElementById("selectcuidado").value;
        } catch (err) {
            console.log("not found");
        }
        try {
            var Idcomposicion = document.getElementById("composicionmm").value;
        } catch (err) {
            console.log("not found");
        }

        if (Idcomposicion != null) {
            console.log(Idcomposicion);
            Composicion = null;
        }

        // ////////////////
        $.ajax({
            type: "GET",
            url: "/verifduplicado",
            data: {
                'Lookup': Composicion,
                'Tipo': 'Familia Composicion'
                // ,'Descripcion':Descripcion
            }

        }).done(function (data) {
            if (data == false) {
                // /////////////////
                $.ajax({
                    type: "POST",
                    url: "/composicioncuidadorest",
                    data: {
                        "_csrf": $('#token').val(),
                        'FamiliaComposicion': Composicion,
                        'idcuidado': Cuidado,
                        'idcomposicion': Idcomposicion
                        // ,'Descripcion':Descripcion
                    },


                }).done(function (data) {
                    var idcomposicion = data[0];
                    var nombrecomposicion = data[1];
                    listarcuidadosjson2(idcomposicion);
                    // ////////////
                    listarcuidadosjson();
                    // / $("#composicioncuidado").remove();
                    Swal.fire({
                        title: 'Agregar composición',
                        html: '<div class="row" id="composicioncuidado">' +
                            '<div id="hola" class="form-group col-sm-12">' +
                            '<div class="form-group col-sm-12">' +
                            '<label for="pedidonom">Nombre de composición</label>' +
                            '<input type="text" class="form-control" name="composicion" id="composicion" value="' + nombrecomposicion + '" readonly>' +
                            '<input type="hidden" class="form-control" id="composicionmm" value="' + idcomposicion + '" placeholder="Polylicra">' +
                            '</div>' +
                            '<div class="form-group col-sm-12" id="selectcuidados">' +
                            '<label for="pedidonom">Instrucciones de cuidado</label>' +

                            '</div>' +
                            '<div class="form-group col-sm-12">' +
                            '<button class="btn btn-primary btn-block" id="agregarInstrucciones" onclick="insertar()">Agregar</button>' +
                            '</div>' +
                            '<div class="form-group col-sm-12" id="selectcuidados2">' +
                            '<label for="pedidonom">Listado de indicaciones</label>' +
                            '</div>' +
                            '</div>' +
                            '</div>',
                        showCancelButton: false,
                        cancelButtonColor: '#dc3545',
                        cancelButtonText: 'Cancelar',
                        confirmButtonText: 'Finalizar',
                        confirmButtonColor: '#0288d1',
                        customClass: 'swal-wide',
                        preConfirm: (composicion) => {
                            if (document.getElementById("composicion").value.length < 1) {
                                Swal.showValidationMessage(
                                    `Complete todos los campos`
                                )
                            }
                        }
                    });
                    listarComposiciones();
                    // mostrar();
                    // composicionescuidados();
                });

            } // /fin segundoif
            else {
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: 'registro duplicado no se ha insertado',
                    showConfirmButton: false,
                    timer: 1250
                })

            }
        });
        /*
         * Swal.fire({ position: 'center', icon: 'success', title: 'Insertado
         * correctamente', showConfirmButton: false, timer: 1250 })
         */
    }

}
// Editar genero
function mostrar() {
    $('#hola').remove();
    $('#composicioncuidado').append("<div id='hola' class='form-group col-sm-12'>" +
        "<div class='form-group col-sm-12'><label for='pedidonom'>Nombre de composición</label><input type='text' class='form-control' id='composicion' placeholder='Algodón'></div>" +
        "<div class='form-group col-sm-12' id='selectcuidados'>" +
        "<label for='pedidonom'>Instrucciones de cuidado</label>" +
        "<div>" +
        "<select class='form-control' id='selectcuidado'>" +
        "<option value='114'>Planchar en seco</option>" +
        "<option value='115'>Lavar a mano</option>" +
        "<option value='118'>No lavar</option>" +
        "<option value='122'>Lavar con agua caliente</option>" +
        "</select>" +
        "</div>" +
        "</div>" +
        "<div class='form-group col-sm-12'><button class='btn btn-primary btn-block' id='agregarInstrucciones' onclick='insertar()'>Agregar</button></div>" +
        "<div class='form-group col-sm-12'>" +
        "<label for='pedidonom'>Listado de indicaciones</label>" +
        "<table class='table'>" +
        "<thead>" +
        "<tr>" +
        "<th scope='col'>undefined</th>" +
        "<th scope='col'>Nombre</th>" +
        "<th scope='col'></th>" +
        "</tr>" +
        "</thead>" +
        "<tbody>" +
        "<tr>" +
        "<th scope='row'>INSTR0001</th>" +
        "<td>Lavar a mano</td>" +
        "<td><button class='btn btn-danger btn-circle btn-sm'><i class='fas fa-minus'></i></button></td>" +
        "</tr>" +
        "</tbody>" +
        "</table>" +
        "</div>" +
        "</div>");

}
// /////////////
// ///////////////////TABLA MUCHOS MUCHOS
function composicionescuidados() {
    $.ajax({
        method: "GET",
        url: "/composicioncuidadorest",
        success: (data) => {
            $('#composicioncuidado').remove();
            $('#composiciocuidado').append("<div class='row' id='composiciocuidado'>" +
                "<div class='form-group col-sm-12'>" +
                "<label for='pedidonom'>Nombre de composición</label>" +
                "<input type='text' class='form-control' id='composicion' placeholder='Polylicra'>" +
                "</div>" +
                "<div class='form-group col-sm-12' id='selectcuidados'>" +
                "<label for='pedidonom'>Instrucciones de cuidado</label>" +

                "</div>" +
                "<div class='form-group col-sm-12'>" +
                "<button class='btn btn-primary btn-block' id='agregarInstrucciones' onclick='insertar()'>Agregar</button>" +
                "</div>" +
                "<div class='form-group col-sm-12'>" +
                "<label for='pedidonom'>Listado de indicaciones</label>" +
                "<table class='table'>" +
                "<thead>" +
                "<tr>" +
                "<th scope='col'></th>" +
                "<th scope='col'>Nombre</th>" +
                "<th scope='col'></th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                "<tr>" +
                "<th scope='row'>INSTR0001</th>" +
                "<td>Lavar a mano</td>" +
                "<td><button class='btn btn-danger btn-circle btn-sm'><i class='fas fa-minus'></i></button></td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>");


        }

    })
}

function editarComposicion(e) {
    var idcomposicion = e.getAttribute("idlookup");
    console.log(idcomposicion);
    listarcuidadosjson2(idcomposicion);
    // ////////////
    listarcuidadosjson();
    Swal.fire({
        title: 'Editar composición',
        html: '<div class="row" id="composicioncuidado">' +
            '<div id="hola" class="form-group col-sm-12">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre de composición</label>' +
            '<input type="text" value="' + e.getAttribute("nombre") + '" name="nombre" class="form-control" id="composicion" placeholder="Polylicra">' +
            '<input type="hidden" value=" ' + e.getAttribute("idlookup") + ' " class="swal2-input" id="composicionmm" placeholder="Parisina">' +
            '</div>' +
            '<div class="form-group col-sm-12" id="selectcuidados">' +
            '<label for="pedidonom">Instrucciones de cuidado</label>' +

            '</div>' +
            '<div class="form-group col-sm-12">' +
            '<button class="btn btn-danger btn-block" id="agregarInstrucciones" onclick="insertar()">Agregar</button>' +
            '</div>' +
            '<div class="form-group col-sm-12" id="selectcuidados2">' +
            '<label for="pedidonom">Listado de indicaciones</label>' +
            '</div>' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cerrar',
        confirmButtonText: 'Actualizar nombre de composición',
        confirmButtonColor: '#0288d1',
        customClass: 'swal-wide',
        preConfirm: (nombre) => {
            if (document.getElementById("nombre").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("composicion").value && document.getElementById("composicionmm").value) {
            var FamiliaComposicion = document.getElementById("composicion").value;

            var idLookup = document.getElementById("composicionmm").value;
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': FamiliaComposicion,
                    'Tipo': "Familia Composicion"


                }

            }).done(function (data) {
                if (data == false) {
                    $.ajax({
                        type: "POST",
                        url: "/editarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'FamiliaComposicion': FamiliaComposicion,
                            'idLookup': idLookup
                            // ,'Descripcion':Descripcion
                        }

                    }).done(function (data) {
                        listarComposiciones();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'editado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Registro duplicado no se ha editado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });

        } // /fin if
    })
}


// Dar de baja familia de genero
function bajarComposicion(idbaja) {
    var id = idbaja;
    Swal.fire({
        title: '¿Deseas dar de baja la familia de composici&oacute;n?',
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Dar de baja',
        confirmButtonColor: '#0288d1',
    }).then((result) => {
        if (result.value && id != null) {


            $.ajax({
                type: "POST",
                url: "/bajacatalogo",
                data: {
                    "_csrf": $('#token').val(),
                    'idcatalogo': id


                }

            }).done(function (data) {

                listarComposiciones();
            });
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'dado de baja correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        } // ////////////termina result value
    })
}
// Reactivar familia de genero
$('#detalleCuidado').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});

function agregarCuidado() {
    var token = $('#token').val();

    Swal.fire({

        title: 'Agregar instrucción de cuidado',
        html: '<form method="POST" enctype="multipart/form-data" id="fileUploadForm" >' +
            '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre instrucción de cuidado</label>' +
            '<input type="text" class="swal2-input" name="InstruccionCuidado" required id="cuidado" placeholder="Lavar a mano">' +
            '<label for="pedidonom">Icono instrucción de cuidado</label>' +
            '<input required type="file" class="swal2-input" name="iconocuidado" id="iconocuidado" placeholder="Lavar a mano">' +
            '<input type="hidden" value="' + token + '" name="_csrf">' +
            '</div>' +
            '</div>' +
            '</form>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Agregar',
        confirmButtonColor: '#0288d1',
        preConfirm: (InstruccionCuidado, iconocuidado) => {
            if (document.getElementById("cuidado").value.length < 1 || document.getElementById("iconocuidado").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("cuidado").value && document.getElementById("iconocuidado").value) {
            var Cuidado = document.getElementById("cuidado").value;
            var iconocuidado = document.getElementById("iconocuidado").files[0].name;
            var form = $('#fileUploadForm')[0];

            // ////////////////////
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': Cuidado,
                    'Tipo': 'Instruccion Cuidado'
                    // ,'Descripcion':Descripcion
                }

            }).done(function (data) {
                if (data == false) {
                    // ////////////////
                    var data1 = new FormData(form);
                    $.ajax({
                        type: "POST",
                        url: "/guardarcatalogo",
                        data: data1,
                        processData: false,
                        contentType: false,
                        cache: false,
                        timeout: 600000

                    }).done(function (data) {
                        listarCuidados();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Insertado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })

                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'registro duplicado no se ha insertado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });
            // window.setTimeout(function(){location.reload()}, 2000);
        }
        /*
         * else { Swal.fire({ position: 'center', icon: 'error', title:
         * 'complete todos los campos', showConfirmButton: false, timer:
         * 1250 }) }
         */
    })
}

// Editar genero


function editarCuidado(e) {
    var token = $('#token').val();
    Swal.fire({
        title: 'Editar instruccion de cuidado',
        html: '<form method="POST" enctype="multipart/form-data" id="fileUploadForm1" >' +
            '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre instrucción de cuidado</label>' +
            '<input type="text" value="' + e.getAttribute("nombre") + '" name="InstruccionCuidado" class="swal2-input" id="nombre" placeholder="Lavar a mano">' +
            '</div>' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Icono instrucción de cuidado</label>' +
            '<div class="custom-file">' +
            '<input type="file" class="custom-file-input" name="iconocuidado" id="iconocuidado"/>' +
            '<label class="custom-file-label" for="iconocuidado">' + e.getAttribute("imagen") + '</label>' +
            '</div>' +
            '<input type="hidden" value="' + token + '" name="_csrf">' +
            '<input type="hidden" value=" ' + e.getAttribute("idlookup") + ' " name="idLookup" class="swal2-input" id="idlookup" placeholder="Parisina">' +

            '</div>' +
            '</div>' +
            '</form>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#0288d1',
        preConfirm: (InstruccionCuidado, iconocuidado) => {
            if (document.getElementById("nombre").value.length < 1 && document.getElementById("iconocuidado").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("nombre").value && document.getElementById("idlookup").value) {
            var InstruccionCuidado = document.getElementById("nombre").value;
            var image = e.getAttribute("imagen");
            // console.log(document.getElementById("iconocuidado").files[0].name);
            var iconocuidado;
            try {
                iconocuidado = document.getElementById("iconocuidado").files[0].name;
            } catch (e) {
                iconocuidado = image;
            }
            console.log(iconocuidado);
            var form = $('#fileUploadForm1')[0];
            var idLookup = document.getElementById("idlookup").value;
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': InstruccionCuidado,
                    'Tipo': "Instruccion Cuidado"


                }

            }).done(function (data) {
                if (data == false) {
                    var data1 = new FormData(form);
                    console.log(data1);
                    $.ajax({
                        type: "POST",
                        url: "/editarcatalogo",
                        data: data1,
                        processData: false,
                        contentType: false,
                        cache: false,
                        timeout: 600000

                    }).done(function (data) {
                        listarCuidados();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'editado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Registro duplicado no se ha editado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });

        } // /fin if
    })
}
// Dar de baja familia de genero
function bajarCuidado(idbaja) {
    var id = idbaja;
    Swal.fire({
        title: '¿Deseas dar de baja el cuidado?',
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Dar de baja',
        confirmButtonColor: '#0288d1',
    }).then((result) => {
        if (result.value && id != null) {


            $.ajax({
                type: "POST",
                url: "/bajacatalogo",
                data: {
                    "_csrf": $('#token').val(),
                    'idcatalogo': id

                    // ,'Descripcion':Descripcion
                }

            }).done(function (data) {

                listarCuidados();
            });
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'dado de baja correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        } // ////////////termina result value
    })
}
// Reactivar familia de genero
$('#detalleMedida').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});

function agregarMedida() {
    Swal.fire({
        title: 'Agregar unidades de medida',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre unidad de medida</label>' +
            '<input type="text" class="swal2-input" id="medida" name="medida" placeholder="Metro">' +
            '<label for="pedidonom">Símbolo unidad de medida</label>' +
            '<input type="text" class="swal2-input" id="simbolo" name="simbolo" placeholder="m">' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Agregar',
        confirmButtonColor: '#0288d1',
        preConfirm: (medida, simbolo) => {
            if (document.getElementById("medida").value.length < 1 || document.getElementById("simbolo").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {

        if (result.value && document.getElementById("medida").value && document.getElementById("simbolo").value) {
            var Medida = document.getElementById("medida").value;
            var Simbolo = document.getElementById("simbolo").value;
            // /////////////////
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': Medida,
                    'Tipo': 'Unidad Medida'
                    // ,'Descripcion':Descripcion
                }

            }).done(function (data) {
                if (data == false) {
                    // //////////////

                    $.ajax({
                        type: "POST",
                        url: "/guardarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'UnidadMedida': Medida,
                            'Simbolo': Simbolo

                            // ,'Descripcion':Descripcion
                        }

                    }).done(function (data) {
                        listarMedidas();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Insertado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })

                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'registro duplicado no se ha insertado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });
            // window.setTimeout(function(){location.reload()}, 2000);
        }
        /*
         * else { Swal.fire({ position: 'center', icon: 'error', title:
         * 'complete todos los campos', showConfirmButton: false, timer:
         * 1250 }) }
         */

    })

}

// Editar genero


function editarMedida(e) {
    var descr = e.getAttribute("descripcion");


    Swal.fire({
        title: 'Editar unidad de medida',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre unidad de medida</label>' +
            '<input type="text" value="' + e.getAttribute("nombre") + '" name="nombre" class="swal2-input" id="nombre" placeholder="Metro">' +
            '<label for="pedidonom">Símbolo unidad de medida</label>' +
            '<input type="text" value="' + e.getAttribute("simbolo") + '" name="simbolo" class="swal2-input" id="simbolo" placeholder="Metro">' +
            '</div>' +
            '<div class="form-group col-sm-12">' +

            '<input type="hidden" value=" ' + e.getAttribute("idlookup") + ' " class="swal2-input" id="idlookup" placeholder="Parisina">' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#0288d1',
        preConfirm: (nombre, simbolo) => {
            if (document.getElementById("nombre").value.length < 1 || document.getElementById("simbolo").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("nombre").value && document.getElementById("idlookup").value && document.getElementById("simbolo").value) {
            var Medida = document.getElementById("nombre").value;

            var idLookup = document.getElementById("idlookup").value;
            var Simbolo = document.getElementById("simbolo").value;
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': Medida,
                    'Tipo': "Unidad Medida"


                }

            }).done(function (data) {
                if (data == false) {
                    $.ajax({
                        type: "POST",
                        url: "/editarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'UnidadMedida': Medida,
                            'idLookup': idLookup,
                            'Simbolo': Simbolo
                            // ,'Descripcion':Descripcion
                        }

                    }).done(function (data) {
                        listarMedidas();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'editado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Registro duplicado no se ha editado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });

        } // /fin if
    })
}
// Dar de baja familia de genero
function bajarMedida(idbaja) {
    var id = idbaja;
    Swal.fire({
        title: '¿Deseas dar de baja la medida?',
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Dar de baja',
        confirmButtonColor: '#0288d1',
    }).then((result) => {
        if (result.value && id != null) {


            $.ajax({
                type: "POST",
                url: "/bajacatalogo",
                data: {
                    "_csrf": $('#token').val(),
                    'idcatalogo': id

                    // ,'Descripcion':Descripcion
                }

            }).done(function (data) {

                listarMedidas();
            });
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Dada de baja correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        } // ////////////termina result value
    })
}
// Reactivar familia de genero
$('#detalleMaterial').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});

function agregarMaterial() {
    Swal.fire({
        title: 'Agregar material',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre material</label>' +
            '<input type="text" class="swal2-input" name="material" id="material" placeholder="Entretela">' +
            '</div>' +
            '<div class="form-group col-sm-12">' +
            '<label for="tipomaterial">Tipo</label>' +
            '<select name="tipomaterial" id="tipomaterial" class="form-control">' +
            '<option>Seleccione tipo</option>' +
            '<option value="0">Material General</option>' +
            '<option value="1">Material Principal</option>' +
            '</select>' +
            '</div>' +
            '<div class="form-group col-sm-12">' +
            '<label for="ubicacionTalla">Clasificaci&oacute;n</label>' +
            '<select class="form-control" id="clasificacion" name="clasificacion" >' +
            '<option value="0">Seleccione clasificaci&oacute;n</option>' +
            '</select>' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Agregar',
        confirmButtonColor: '#0288d1',
        preConfirm: (tipomaterial, material) => {
            if (document.getElementById("tipomaterial").value.length != 1 ||
                document.getElementById("material").value.length < 1 ||
                document.getElementById("clasificacion").value == 0
            ) {
                Swal.showValidationMessage(
                    `Complete todos los campos`


                )
                console.log("ggg")
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("material").value &&
            document.getElementById("tipomaterial").value.length == 1 &&
            document.getElementById("clasificacion").value) {
            var Material = document.getElementById("material").value;
            var TipoMaterial = document.getElementById("tipomaterial").value;
            var CategoriaMaterial = document.getElementById("clasificacion").value;
            // console.log(TipoMaterial)
            console.log(CategoriaMaterial)
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': Material,
                    'Tipo': "Material"
                    // ,'Descripcion':Descripcion
                }

            }).done(function (data) {
                if (data == false) {
                    // ////////////

                    $.ajax({
                        type: "POST",
                        url: "/guardarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'Material': Material,
                            'TipoMaterial': TipoMaterial,
                            'CategoriaMaterial': CategoriaMaterial
                            // ,'Descripcion':Descripcion
                        }

                    }).done(function (data) {
                        listarMateriales();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Insertado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })

                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'registro duplicado no se ha insertado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });
            // window.setTimeout(function(){location.reload()}, 2000);
        }
    })
    $.ajax({
        method: "GET",
        url: "/listar-amp",
        data: {
            "Tipo": "Clasificacion"
        },
        success: (data) => {
            $.each(data, function (key, val) {
                $('#clasificacion').append('<option value="' + val.idLookup + '">' + val.nombreLookup + '</option>');
            })
            //$('.selectpicker').selectpicker(["refresh"]);
        },
        error: (e) => {

        }
    })
}

// Editar genero


function editarMaterial(e) {
    var descr = e.getAttribute("descripcion");

    var idClasificacion = e.getAttribute("atributo2");
    // / var atributo1= e.getAttribute("atributo1");

    Swal.fire({
        title: 'Editar material',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre medida</label>' +
            '<input type="text" name="nombre" value="' + e.getAttribute("nombre") + '" class="swal2-input" id="nombre" placeholder="Entretela">' +
            '</div>' +
            '<div class="form-group col-sm-12">' +
            '<label for="tipomaterial">Tipo</label>' +
            '<select name="tipomaterial" id="tipomaterial" class="form-control">' +
            (e.getAttribute("atributo1") == 1 ? "<option value='1'>Material Principal</option>" : "<option value='0'>Material General</option>") +
            (e.getAttribute("atributo1") == 1 ? "<option value='0'>Material General</option>" : "<option value='1'>Material Principal</option>") +
            '</select>' +
            '</div>' +
            '<div class="form-group col-sm-12">' +
            '<label for="ubicacionTalla">Clasificaci&oacute;n</label>' +
            '<select class="form-control" id="clasificacion" name="clasificacion" >' +

            '</select>' +
            '</div>' +
            '<input type="hidden" value=" ' + e.getAttribute("idlookup") + ' " class="swal2-input" id="idlookup" placeholder="Parisina">' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#0288d1',
        preConfirm: (nombre) => {
            if (document.getElementById("nombre").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("nombre").value && document.getElementById("idlookup").value) {
            var Material = document.getElementById("nombre").value;

            var idLookup = document.getElementById("idlookup").value;
            var TipoMaterial = document.getElementById("tipomaterial").value;
            var CategoriaMaterial = document.getElementById("clasificacion").value;
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': Material,
                    'Tipo': "Material"


                }

            }).done(function (data) {
                if (data == false) {
                    $.ajax({
                        type: "POST",
                        url: "/editarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'Material': Material,
                            'idLookup': idLookup,
                            'TipoMaterial': TipoMaterial,
                            'CategoriaMaterial': CategoriaMaterial
                            // ,'Descripcion':Descripcion
                        }

                    }).done(function (data) {
                        listarMateriales();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'editado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Registro duplicado no se ha editado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });

        } // /fin if
    })
    $.ajax({
        method: "GET",
        url: "/listar-amp",
        data: {
            "Tipo": "Clasificacion"
        },
        success: (data) => {
            $.each(data, function (key, val) {

                if (val.idLookup == idClasificacion) {
                    $('#clasificacion').append('<option selected value="' + val.idLookup + '">' + val.nombreLookup + '</option>');
                } else {
                    $('#clasificacion').append('<option value="' + val.idLookup + '">' + val.nombreLookup + '</option>');
                }

            })
            //$('.selectpicker').selectpicker(["refresh"]);
        },
        error: (e) => {

        }
    })
}
// Dar de baja familia de genero
function bajarMaterial(idbaja) {
    var id = idbaja;
    Swal.fire({
        title: '¿Deseas dar de baja el material?',
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Dar de baja',
        confirmButtonColor: '#0288d1',
    }).then((result) => {
        if (result.value && id != null) {
            $.ajax({
                type: "POST",
                url: "/bajacatalogo",
                data: {
                    "_csrf": $('#token').val(),
                    'idcatalogo': id

                    // ,'Descripcion':Descripcion
                }

            }).done(function (data) {

                listarMateriales();
            });
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'dado de baja correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        } // ////////////termina result value
    })
}
// Reactivar familia de genero
$('#detalleMarcador').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});

function agregarMarcador() {
    Swal.fire({
        title: 'Agregar marcador',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre marcador</label>' +
            '<input type="text" class="swal2-input" name="marcador" id="marcador" placeholder="Cierre en la bolsa">' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Agregar',
        confirmButtonColor: '#0288d1',
        preConfirm: (marcador) => {
            if (document.getElementById("marcador").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("marcador").value) {
            var Marcador = document.getElementById("marcador").value;
            // /////////////
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': Marcador,
                    'Tipo': "Marcador"
                    // ,'Descripcion':Descripcion
                }

            }).done(function (data) {
                if (data == false) {

                    // ////////////////

                    $.ajax({
                        type: "POST",
                        url: "/guardarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'Marcador': Marcador

                            // ,'Descripcion':Descripcion
                        }

                    }).done(function (data) {
                        listarMarcadores();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Insertado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })

                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'registro duplicado no se ha insertado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });
            // window.setTimeout(function(){location.reload()}, 2000);
        }
    })
}

// Editar genero


function editarMarcador(e) {
    var descr = e.getAttribute("descripcion");


    Swal.fire({
        title: 'Editar marcador',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre marcador</label>' +
            '<input type="text" name="nombre" value="' + e.getAttribute("nombre") + '" class="swal2-input" id="nombre" placeholder="Cierre en la bolsa">' +
            '</div>' +
            '<div class="form-group col-sm-12">' +

            '<input type="hidden" value=" ' + e.getAttribute("idlookup") + ' " class="swal2-input" id="idlookup" placeholder="Parisina">' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#FFC107',
        preConfirm: (nombre) => {
            if (document.getElementById("nombre").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("nombre").value && document.getElementById("idlookup").value) {
            var Marcador = document.getElementById("nombre").value;

            var idLookup = document.getElementById("idlookup").value;
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': Marcador,
                    'Tipo': "Marcador"


                }

            }).done(function (data) {
                if (data == false) {
                    $.ajax({
                        type: "POST",
                        url: "/editarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'Marcador': Marcador,
                            'idLookup': idLookup
                            // ,'Descripcion':Descripcion
                        }

                    }).done(function (data) {
                        listarMarcadores();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'editado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Registro duplicado no se ha editado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });

        } // /fin if
    })
}
// Dar de baja familia de genero
function bajarMarcador(idbaja) {
    var id = idbaja;
    Swal.fire({
        title: '¿Deseas dar de baja el marcador?',
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Dar de baja',
        confirmButtonColor: '#FFC107',
    }).then((result) => {
        if (result.value && id != null) {
            $.ajax({
                type: "POST",
                url: "/bajacatalogo",
                data: {
                    "_csrf": $('#token').val(),
                    'idcatalogo': id

                    // ,'Descripcion':Descripcion
                }

            }).done(function (data) {

                listarMarcadores();
            });
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'dado de baja correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        } // ////////////termina result value
    })
}
// Reactivar familia de genero

// Habilitar form de SweetAlert2
$('#detalleComposicion').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});

function agregarComposicion1() {
    Swal.fire({
        title: 'Agregar composición',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre composición</label>' +
            '<input type="text" class="swal2-input" name="composicion1" id="composicion1" placeholder="Polyester">' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Agregar',
        confirmButtonColor: '#0288d1',
        preConfirm: (composicion1) => {
            if (document.getElementById("composicion1").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("composicion1").value) {
            var Composicion = document.getElementById("composicion1").value;
            // //////////////
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': Composicion,
                    'Tipo': "Composicion"
                    // ,'Descripcion':Descripcion
                }

            }).done(function (data) {
                if (data == false) {
                    // /////////////

                    $.ajax({
                        type: "POST",
                        url: "/guardarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'Composicion': Composicion

                            // ,'Descripcion':Descripcion
                        }

                    }).done(function (data) {
                        listarComposiciones1();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Insertado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })

                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Registro duplicado no se ha editado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });
            // window.setTimeout(function(){location.reload()}, 2000);
        }
    })
}

// Editar genero


function editarComposicion1(e) {
    var descr = e.getAttribute("descripcion");


    Swal.fire({
        title: 'Editar composición',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre composición</label>' +
            '<input type="text" name="nombre" value="' + e.getAttribute("nombre") + '" class="swal2-input" id="nombre" placeholder="Polyester">' +
            '</div>' +
            '<div class="form-group col-sm-12">' +

            '<input type="hidden" value=" ' + e.getAttribute("idlookup") + ' " class="swal2-input" id="idlookup" placeholder="Parisina">' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#0288d1',
        preConfirm: (nombre) => {
            if (document.getElementById("nombre").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("nombre").value && document.getElementById("idlookup").value) {
            var Composicion = document.getElementById("nombre").value;

            var idLookup = document.getElementById("idlookup").value;
            $.ajax({
                type: "GET",
                url: "/verifduplicado",
                data: {
                    'Lookup': Composicion,
                    'Tipo': "Composicion"


                }

            }).done(function (data) {
                if (data == false) {
                    $.ajax({
                        type: "POST",
                        url: "/editarcatalogo",
                        data: {
                            "_csrf": $('#token').val(),
                            'Composicion': Composicion,
                            'idLookup': idLookup
                            // ,'Descripcion':Descripcion
                        }

                    }).done(function (data) {
                        listarComposiciones1();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'editado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                } // /fin segundoif
                else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Registro duplicado no se ha editado',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
            });

        } // /fin if
    })
}
// Dar de baja familia de genero
function bajarComposicion1(idbaja) {
    var id = idbaja;
    Swal.fire({
        title: '¿Deseas dar de baja la composici&oacute;n?',
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Dar de baja',
        confirmButtonColor: '#0288d1',
    }).then((result) => {
        if (result.value && id != null) {
            $.ajax({
                type: "POST",
                url: "/bajacatalogo",
                data: {
                    "_csrf": $('#token').val(),
                    'idcatalogo': id


                }

            }).done(function (data) {

                listarComposiciones1();
            });
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'dado de baja correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        } // ////////////termina result value
    })
}
// Reactivar familia de genero