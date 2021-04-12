$(document).ready(function () {
    $("#SAM").inputmask({"mask": "9{1,3}.9{1,2}"});
    $("#turno").inputmask({"mask": "9{1,3}"});
    listarfamilia();
    listarAFI();
    listarComponentes();
    listarActivoFijo();
    listarColor();
    listarMAF();
    listarMIN();
    listaroperaciones();
    listarHerramientas();
    listarMedidas();
    listarMateriales();
    
    

});

function listarHerramientas() {

    $.ajax({
        method: "GET",
        url: "/lista-maquila",
        data: {
            "Tipo": "Herramientas"
        },
        success: (data) => {
            $('#quitar88').remove();
            $('#contenedorTabla88').append("<div class='modal-body' id='quitar88'>" +
                "<table class='table table-striped table-bordered' id='idtable88' style='width:100%'>" +
                "<thead>" +
                "<tr>" +
                "<th>Clave</th>" +
                "<th>Articulo</th>" +
                "<th>Marca</th>" +
                "<th>Costo</th>" +
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
                        "<td>" + data[i].atributo1 + "</td>",
                        "<td class='text-center'>" +
                        "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                        "<button onclick='editarHerramienta(this);' idlookup='" + data[i].idLookup + "' articulo='" + data[i].nombreLookup + "' marca='" + data[i].descripcionLookup + "'  costo='" + data[i].atributo1 + "' class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " +
                        (data[i].estatus == 1 ? "<button onclick='bajarHerramienta(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
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
                            "<td>" + data[i].atributo1 + "</td>",
                            "<td class='text-center'>" +
                            "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                            (rolEditar == 1 ? "<button onclick='editarHerramienta(this);' idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "' simbolo='" + data[i].descripcionLookup + "' class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>" : " ") +
                            (rolEliminar == 1 ? "<button onclick='bajarHerramienta(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                            "</td>" +

                            "<tr>"
                        ];
                        b.push(a);
                    }
                }
            }
            var tablaHerramientas = $('#idtable88').DataTable({
                "data": b,
                "ordering": false,
                "pageLength": 5,
                "responsive": true,
                "stateSave": true,
                "drawCallback": function() {
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
            new $.fn.dataTable.FixedHeader(tablaHerramientas);
        },
        error: (e) => {

        }
    })
}

function listarMedidas() {

    $.ajax({
        method: "GET",
        url: "/lista-maquila",
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
                "drawCallback": function() {
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


function listarMateriales() {

    $.ajax({
        method: "GET",
        url: "/lista-maquila",
        data: {
            "Tipo": "Material"
        },
        success: (data) => {
            $('#quitar9').remove();
            $('#contenedorTabla9').append("<div class='modal-body' id='quitar9'>" +
                "<table class='table table-striped table-bordered' id='idtable9' style='width:100%'>" +
                "<thead>" +
                "<tr>" +
                "<th>Clave</th>" +
                "<th>Nombre</th>" +
                "<th>Codigo</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "</table>" + "</div>");
            var a;
            var b = [];
            if (rolAdmin == 1) {
                for (i in data) {
                    var creacion = data[i].creadoPor == null ? "" : data[i].creadoPor;
                    a = [
                        "<tr>" +
                        "<td>" + data[i].idText + "</td>",
                        "<td>" + data[i].nombreLookup + "</td>",
                        "<td>" + data[i].descripcionLookup + "</td>",
                        "<td style='text-align: center;'>" +
                        "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].ultimaFechaModificacion + "'><i class='fas fa-info'></i></button> " +
                        "<button onclick='editarMaterial(this);'  idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "' codigo='" + data[i].descripcionLookup + "'  class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " +
                        (data[i].estatus == 1 ? "<button onclick='bajarMaterial(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                        (data[i].estatus == 0 ? "<button onclick='reactivar(" + data[i].idLookup + ")' class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-sort-up'></i></button>" : " ") +
                        "</td>" +

                        "<tr>"
                    ];
                    b.push(a);
                }
            } else {
                for (i in data) {
                    var creacion = data[i].creadoPor == null ? "" : data[i].creadoPor;
                    if (data[i].estatus == 1) {
                        a = [
                            "<tr>" +
                            "<td>" + data[i].idText + "</td>",
                            "<td>" + data[i].nombreLookup + "</td>",
                            "<td>" + data[i].descripcionLookup + "</td>",
                            "<td style='text-align: center;'>" +
                            "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i].creadoPor + " <br /><strong>Fecha de creación:</strong> " + data[i].fechaCreacion + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i].fechaCreacion + "'><i class='fas fa-info'></i></button> " +
                            (rolEditar == 1 ? "<button onclick='editarMaterial(this);'  idlookup='" + data[i].idLookup + "' nombre='" + data[i].nombreLookup + "' codigo='" + data[i].descripcionLookup + "' class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button> " : "") +
                            (rolEditar == 1 ? "<button onclick='bajarMaterial(" + data[i].idLookup + ")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>" : " ") +
                            (rolEliminar == 1 ? "<button onclick='reactivar(" + data[i].idLookup + ")' class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Reactivar'><i class='fas fa-sort-up'></i></button>" : " ") +
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
                "drawCallback": function() {
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
                url: "/reactivarcatalogomaquila",
                data: {
                    "_csrf": $('#token').val(),
                    'idcatalogo': id
                }

            }).done(function(data) {
                switch (data) {
                   
                   
                    case "Unidad Medida":
                        listarMedidas();
                        break;
                    case "Material":
                        listarMateriales();
                       
                }
            });
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Reactivado correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        } 
    })
}
///////////////////////7
$('#detalleHerramienta').on('shown.bs.modal', function() {
    $(document).off('focusin.modal');
});
function agregarHerramienta() {
    Swal.fire({
        title: 'Agregar herramienta y utilería',
        html: '<div class="row">' +
            '<div class="form-group col-sm-6">' +
            '<label for="pedidonom">Articulo</label>' +
            '<input type="text" class="swal2-input" id="articulo"  >' +
            '</div>' +
            '<div class="form-group col-sm-6">' +
            '<label for="pedidonom">Marca</label>' +
            '<input type="text" class="swal2-input" id="marca" >' +
            '</div>' +
            '<div class="form-group col-sm-6">' +
            '<label for="pedidonom">Costo</label>' +
            '<input type="number" min="0" class="swal2-input" id="costo"  >' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Agregar',
        confirmButtonColor: '#0288d1',
        preConfirm: (articulo, marca,costo) => {
            if (document.getElementById("articulo").value.length < 1 || document.getElementById("marca").value.length < 1 || document.getElementById("costo").value<0 ) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {

        if (result.value && document.getElementById("articulo").value && document.getElementById("marca").value) {
            var Articulo = document.getElementById("articulo").value;
            var Marca = document.getElementById("marca").value;
            var Costo = document.getElementById("costo").value;
            // /////////////////
            $.ajax({
                type: "GET",
                url: "/verifduplicadomaquila",
                data: {
                    'Lookup': Articulo,
                    'Tipo': 'Herramientas',
                      'Descripcion': Marca
                     
                }

            }).done(function(data) {
                if (data == false) {
                    // //////////////

                    $.ajax({
                        type: "POST",
                        url: "/guardarcatalogomaquila",
                        data: {
                            "_csrf": $('#token').val(),
                            'Articulo': Articulo,
                            'Marca': Marca,
                            'Costo': Costo

                        }

                    }).done(function(data) {
                        listarHerramientas();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Insertado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
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



function editarHerramienta(e) {
    var descr = e.getAttribute("descripcion");


    Swal.fire({
        title: 'Editar herramientas y utilería',
        html: '<div class="row">' +
            '<div class="form-group col-sm-6">' +
            '<label for="pedidonom">Articulo</label>' +
            '<input type="text" value="' + e.getAttribute("articulo") + '"  class="swal2-input" id="nombreart" placeholder="Metro">' +
            '</div>' +
            '<div class="form-group col-sm-6">' +
            '<label for="pedidonom">Marca</label>' +
            '<input type="text" value="' + e.getAttribute("marca") + '"  class="swal2-input" id="marcaart" placeholder="Metro">' +
            '</div>' +
            '<div class="form-group col-sm-6">' +
            '<label for="pedidonom">Costo</label>' +
            '<input type="text" value="' + e.getAttribute("costo") + '"  class="swal2-input" id="costoart" placeholder="Metro">' +
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
        preConfirm: (nombreart, marcaart) => {
            if (document.getElementById("nombreart").value.length < 1 || document.getElementById("marcaart").value.length < 1 ||  document.getElementById("costoart").value.length < 1) {
             
            	Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("nombreart").value && document.getElementById("idlookup").value && document.getElementById("marcaart").value && document.getElementById("costoart").value) {
            var Articulo = document.getElementById("nombreart").value;

            var idLookup = document.getElementById("idlookup").value;
            var Marca = document.getElementById("marcaart").value;
            var Costo = document.getElementById("costoart").value;

            $.ajax({
                type: "GET",
                url: "/verifduplicadomaquila",
                data: {
                  
                    	 'Lookup': Articulo,
                         'Tipo': 'Herramientas',
                           'Descripcion': Marca
                          

                }

            }).done(function(data) {
                if (data == false) {
                    $.ajax({
                        type: "POST",
                        url: "/editarcatalogomaquila",
                        data: {
                            "_csrf": $('#token').val(),
                            'Articulo': Articulo,
                            'Marca': Marca,
                            'Costo': Costo,
                            'idLookup': idLookup
                        }

                    }).done(function(data) {
                        listarHerramientas();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'editado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                } 
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

        } 
    })
}
function bajarHerramienta(idbaja) {
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
                url: "/bajacatalogomaquila",
                data: {
                    "_csrf": $('#token').val(),
                    'idcatalogo': id

                }

            }).done(function(data) {

                listarHerramientas();
            });
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Dada de baja correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        }
    })
}
////////////////////////

$('#detalleMedida').on('shown.bs.modal', function() {
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
                url: "/verifduplicadomaquila",
                data: {
                    'Lookup': Medida,
                    'Tipo': 'Unidad Medida'
                      
                }

            }).done(function(data) {
                if (data == false) {
                    // //////////////

                    $.ajax({
                        type: "POST",
                        url: "/guardarcatalogomaquila",
                        data: {
                            "_csrf": $('#token').val(),
                            'UnidadMedida': Medida,
                            'Simbolo': Simbolo

                        }

                    }).done(function(data) {
                        listarMedidas();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Insertado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
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



function editarMedida(e) {
    var descr = e.getAttribute("descripcion");


    Swal.fire({
        title: 'Editar unidad de medida',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre unidad de medida</label>' +
            '<input type="text" value="' + e.getAttribute("nombre") + '" name="nombre" class="swal2-input" id="nombrem" placeholder="Metro">' +
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
            if (document.getElementById("nombrem").value.length < 1 || document.getElementById("simbolo").value.length < 1) {
             
            	Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("nombrem").value && document.getElementById("idlookup").value && document.getElementById("simbolo").value) {
            var Medida = document.getElementById("nombrem").value;

            var idLookup = document.getElementById("idlookup").value;
            var Simbolo = document.getElementById("simbolo").value;
            $.ajax({
                type: "GET",
                url: "/verifduplicadomaquila",
                data: {
                    'Lookup': Medida,
                    'Tipo': "Unidad Medida"


                }

            }).done(function(data) {
                if (data == false) {
                    $.ajax({
                        type: "POST",
                        url: "/editarcatalogomaquila",
                        data: {
                            "_csrf": $('#token').val(),
                            'UnidadMedida': Medida,
                            'idLookup': idLookup,
                            'Simbolo': Simbolo
                        }

                    }).done(function(data) {
                        listarMedidas();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'editado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                } 
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

        } 
    })
}
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
                url: "/bajacatalogomaquila",
                data: {
                    "_csrf": $('#token').val(),
                    'idcatalogo': id

                }

            }).done(function(data) {

                listarMedidas();
            });
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Dada de baja correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        }
    })
}


$('#detalleMaterial').on('shown.bs.modal', function() {
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
            '<label for="pedidonom">Codigo</label>' +
            '<input type="text" class="swal2-input" name="codigo" id="codigo" placeholder="Mat0000">' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Agregar',
        confirmButtonColor: '#0288d1',
        preConfirm: ( material, codigo) => {

            if (
                document.getElementById("material").value.length < 1 ||
                document.getElementById("codigo").value.length < 1 
            ) {
                Swal.showValidationMessage(
                    `Complete todos los campos`


                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("material").value &&
            document.getElementById("codigo").value
           ) {
            var Material = document.getElementById("material").value;
            var Codigo = document.getElementById("codigo").value;
           
           
            $.ajax({
                type: "GET",
                url: "/verifduplicadomaquila",
                data: {
                    'Lookup': Material,
                    'Tipo': "Material"
                }

            }).done(function(data) {
                if (data == false) {

                    $.ajax({
                        type: "POST",
                        url: "/guardarcatalogomaquila",
                        data: {
                            "_csrf": $('#token').val(),
                            'Material': Material,
                            'Codigo': Codigo
                        }

                    }).done(function(data) {
                        listarMateriales();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Insertado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })

                }
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



function editarMaterial(e) {
   

    Swal.fire({
        title: 'Editar material',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Nombre medida</label>' +
            '<input type="text" name="nombre" value="' + e.getAttribute("nombre") + '" class="swal2-input" id="nombremat" placeholder="Entretela">' +
            '</div>' +
            '<div class="form-group col-sm-12">' +
            '<label for="pedidonom">Codigo</label>' +
            '<input type="text" name="codigo" value="' + e.getAttribute("codigo") + '" class="swal2-input" id="codigo" placeholder="Mat0000">' +
            '</div>' +
            '<input type="hidden" value=" ' + e.getAttribute("idlookup") + ' " class="swal2-input" id="idlookup" placeholder="Parisina">' +
            '</div>',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#0288d1',
        preConfirm: (nombremat,codigo) => {
            if (document.getElementById("nombremat").value.length < 1 || document.getElementById("codigo").value.length < 1 ) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
    }).then((result) => {
        if (result.value && document.getElementById("nombremat").value && document.getElementById("idlookup").value) {
            var Material = document.getElementById("nombremat").value;

            var Codigo = document.getElementById("codigo").value;
            var idLookup = document.getElementById("idlookup").value;
           
            $.ajax({
                type: "GET",
                url: "/verifduplicadomaquila",
                data: {
                    'Lookup': Material,
                    'Tipo': "Material"


                }

            }).done(function(data) {
                if (data == false) {
                    $.ajax({
                        type: "POST",
                        url: "/editarcatalogomaquila",
                        data: {
                            "_csrf": $('#token').val(),
                            'Material': Material,
                            'Codigo': Codigo,
                            'idLookup': idLookup
                        }

                    }).done(function(data) {
                        listarMateriales();
                    });
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'editado correctamente',
                        showConfirmButton: false,
                        timer: 1250
                    })
                } 
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

        }
    })
   
}

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
                url: "/bajacatalogomaquila",
                data: {
                    "_csrf": $('#token').val(),
                    'idcatalogo': id

                }

            }).done(function(data) {

                listarMateriales();
            });
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'dado de baja correctamente',
                showConfirmButton: false,
                timer: 1250
            })
        } 
    })
}
/////////////////7

function familiaOpereacion(){
    $("#idLookupfamilia").val(null);
    $("#familia").val(null);
    $("#tituloFamilaOperaciones").text('Nueva familia');
    
    $('#agregarFamilaOperaciones').modal('show'); // abrir
}

function llenarSelectFamila(id){
    $.ajax({
		method: "GET",
		url: "/listar_maquila_object_estatus",
		data:{
            'Tipo': 'Familia', 
            'estatus':'1'
        } ,
		success: (data) => {
            $("#idFamilia").empty();
            $(data).each(function(i, v){ 
                if ( v.idLookup == id){
                    $('#idFamilia').append('<option  value="'+v.idLookup+'" selected    >' + v.nombreLookup+ '</option>');
                }
                else{
                    $('#idFamilia').append('<option  value="'+v.idLookup+'">' + v.nombreLookup+ '</option>');
                }
                
            })
            $('#idFamilia').selectpicker('refresh');
       	
		}, complete: function() {   
            $('#idFamilia').val(id);
            $('#idFamilia').selectpicker('refresh');
         
        },
		error: (e) => {

		}
	})
}
function llenarSelectMaquina (id){
    //
    $.ajax({
		method: "GET",
		url: "/get_listarFamiliabyMaquinaria",
		data:{} ,
		success: (data) => {
            $("#idMaquina").empty();
            $(data).each(function(i, v){ 
                
                $('#idMaquina').append('<option  value="'+v[0]+'">' + v[1]+ '</option>');
                
                
            })
            $('#idMaquina').selectpicker('refresh');
       	
		}, complete: function() {   
            $('#idMaquina').val(id);
            $('#idMaquina').selectpicker('refresh');
         
        },
		error: (e) => {

		}
	})
}


function agregarOperacionMaquila(){
    $('#alertOperaciones').css('display', 'none');
    llenarSelectFamila(null);
    llenarSelectMaquina(null);

    document.formOperacion.operacion.value=null;
    document.formOperacion.SAM.value=null;
    document.formOperacion.turno.value=null;
    document.formOperacion.idLookup.value=null;
    $('#modalOperacionesMaquila').modal('show'); // abrir
}


function llenarSelectActivoFijoMaquila(idValor){
    $.ajax({
		method: "GET",
		url: "/listar_maquila_object_estatus",
		data:{
            'Tipo': 'Activo Fijo', 
            'estatus':'1'
        } ,
		success: (data) => {
            $("#Mactivoid").empty();
            $(data).each(function(i, v){ 
                if ( v.idLookup == idValor){
                    $('#Mactivoid').append('<option  value="'+v.idLookup+'" selected    >' + v.nombreLookup+ '</option>');
                }
                else{
                    $('#Mactivoid').append('<option  value="'+v.idLookup+'">' + v.nombreLookup+ '</option>');
                }
                
            })
            $('#Mactivoid').selectpicker('refresh');
       	
		}, complete: function() {   
            $('#Mactivoid').val(idValor);
            $('#Mactivoid').selectpicker('refresh');
         
        },
		error: (e) => {

		}
	})

}

function agregarMaquina(){

    $("#tituloAFI").text('Nuevo activo');
    $('#alertMaquina').css('display', 'none');
    document.formMaquina.nombre.value=null;
    document.formMaquina.idLookup.value=null;
    llenarSelectActivoFijoMaquila(null)

    $('#modalAgregarMaquina').modal('show'); // abrir
}
function agregarComponente() {
    $('#alertComponentes').css('display', 'none');
    $("#tituloComponentes").text('Nuevo componente');
    document.formComponentes.nombre.value = null;
    document.formComponentes.idLookup.value=null;
    $('#modalAgregarComponentes').modal('show'); // abrir
    
}
function agregarActivoFijo() {
    $('#alertActivoFijo').css('display', 'none');
    $("#tituloActivoFijo").text('Nuevo activo fijo');
    
    document.formActivoFijo.nombre.value = null;
    document.formActivoFijo.idLookup.value= null;
    $('#modalAgregarActivoFijo').modal('show'); // abrir 
    
}

function llenarSelectProveedoresColor(proveedor){
    
    $.ajax({
        type: "GET",
        url: "/listarProveedoresColores",
        success: (data) => {
            $("#proveedorColor").empty();

            for (i in data) {
                $('#proveedorColor').append("<option value=" + data[i].idProveedor + " name" + data[i].nombreProveedor + ">" + data[i].nombreProveedor + "</option>");
            }
        $('#proveedorColor').selectpicker('refresh');
       	
    }, complete: function() {   
        $('#proveedorColor').val(proveedor);
        $('#proveedorColor').selectpicker('refresh');
     
    }
    })
}
function agregarColor() {
    $('#alertColor').css('display', 'none');
    $("#tituloColor").text('Nuevo color');
    document.formColor.nombre.value = null;
    document.formColor.codigoColor.value = null;
    document.formColor.calibre.value = null;
    document.formColor.idLookup.value = null;
    llenarSelectProveedoresColor(null);
    
    $('#modalAgregarColor').modal('show'); // abrir
    
}
function agregarMovFijo(){
    $('#alertMovFijo').css('display', 'none');
    $("#tituloMovFijo").text('Nuevo movimiento fijo');
    document.formMovFijo.nombre.value = null;
    document.formMovFijo.idLookup.value=null;
    $('#tipoMov').val(null);
    $('#tipoMov').selectpicker('refresh');
    $('#modalAgregarMovFijo').modal('show'); // abrir
    
}

function agregarMovIns(){
    $('#alertMovIns').css('display', 'none');
    $("#tituloMovIns").text('Nuevo movimiento insumo');
    document.formMovIns.nombre.value = null;
    document.formMovIns.idLookup.value=null;
    $('#tipoMovIns').val(null);
    $('#tipoMovIns').selectpicker('refresh');
    $('#modalAgregarMovIns').modal('show'); // abrir

}

function guardarFamilia(){
    if ( $("#familia").val() == "" ){
        $('#alertFamilia').css('display', '');
    }else{
        $('#alertFamilia').css('display', 'none');
        $.ajax({
            type: "GET",
            url: "/verificar_duplicado_maquila_lookup",
            data: {
                'Lookup': $("#familia").val(),
                'Tipo': "Familia"
            }
        }).done(function(data) {
            if (data == false) {
                $.ajax({
                    type: "POST",
                    url: "/guardar-lookup-maquila",
                    data: {
                        "_csrf": $('#token').val(), 
                        'idLook': $("#idLookupfamilia").val(),
                        'nombre': $("#familia").val(),
                        'tipo':'Familia'
                    }
    
                }).done(function(data) {
                    $('#agregarFamilaOperaciones').modal('hide'); // abrir
                    listarfamilia();
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
    }


}

function guardarMaquina(){
    if (document.formMaquina.nombre.value == "" || document.formMaquina.Mactivoid.value == ""  ){
        $('#alertMaquina').css('display', '');
    }else{
        $('#alertMaquina').css('display', 'none');
        $.ajax({
            type: "GET",
            url: "/verificar_duplicado_maquila_lookup",
            data: {
                'Lookup': document.formMaquina.nombre.value,
                'Tipo': "AFI",
                'descripcion':document.formMaquina.Mactivoid.value
            }
        }).done(function(data) {
            if (data == false) {
                $.ajax({
                    type: "POST",
                    url: "/guardar-lookup-maquila",
                    data: {
                        "_csrf": $('#token').val(), 
                        'idLook': document.formMaquina.idLookup.value,
                        'nombre': document.formMaquina.nombre.value,
                        'descripcion': document.formMaquina.Mactivoid.value,
                        'tipo':'AFI'
                    }
    
                }).done(function(data) {
                    $('#modalAgregarMaquina').modal('hide'); // abrir
                    listarAFI();
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
    }


}
function guardarComponentes(){
    
    if (document.formComponentes.nombre.value == "" ){
        $('#alertComponentes').css('display', '');
    }else{
        $('#alertComponentes').css('display', 'none');
        $.ajax({
            type: "GET",
            url: "/verificar_duplicado_maquila_lookup",
            data: {
                'Lookup': document.formComponentes.nombre.value,
                'Tipo': "Componente"
            }
        }).done(function(data) {
            if (data == false) {
                $.ajax({
                    type: "POST",
                    url: "/guardar-lookup-maquila",
                    data: {
                        "_csrf": $('#token').val(), 
                        'idLook': document.formComponentes.idLookup.value,
                        'nombre': document.formComponentes.nombre.value,
                        'tipo':'Componente'
                    }
    
                }).done(function(data) {
                    $('#modalAgregarComponentes').modal('hide'); // abrir
                    listarComponentes();
                });
                Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Guardado correctamente',
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
    }
}

function guardarActivoFijo (){

    if ( document.formActivoFijo.nombre.value  == "" ){
        $('#alertActivoFijo').css('display', '');
    }else{
        $('#alertActivoFijo').css('display', 'none');
        $.ajax({
            type: "GET",
            url: "/verificar_duplicado_maquila_lookup",
            data: {
                'Lookup': document.formActivoFijo.nombre.value,
                'Tipo': "Activo Fijo"
            }
        }).done(function(data) {
            if (data == false) {
                $.ajax({
                    type: "POST",
                    url: "/guardar-lookup-maquila",
                    data: {
                        "_csrf": $('#token').val(), 
                        'idLook': document.formActivoFijo.idLookup.value,
                        'nombre': document.formActivoFijo.nombre.value,
                        'tipo':'Activo Fijo'
                    }
    
                }).done(function(data) {
                    $('#modalAgregarActivoFijo').modal('hide'); // abrir
                    listarActivoFijo();
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
    }

}
function guardarOperacion(){

    
    if ( document.formOperacion.idFamilia.value  == "" ||
     document.formOperacion.operacion.value   == "" || 
     document.formOperacion.idMaquina.value == "" || 
     document.formOperacion.SAM.value == ""  || document.formOperacion.turno.value ==""){
        $('#alertOperaciones').css('display', '');
    }else{
        $('#alertOperaciones').css('display', 'none');
        $.ajax({
            type: "GET",
            url: "/verificar_duplicado_maquila_lookup",
            data: {
                'Lookup':document.formOperacion.operacion.value,
                'Tipo': "Operacion",
                'descripcion': document.formOperacion.idFamilia.value  ,
                'atributo1':document.formOperacion.idMaquina.value,
                'atributo2':document.formOperacion.SAM.value,
                'atributo3':document.formOperacion.turno.value
            }
        }).done(function(data) {
            if (data == false) {
                $.ajax({
                    type: "POST",
                    url: "/guardar-lookup-maquila",
                    data: {
                        //String descripcion, String atributo1, String atributo
                        "_csrf": $('#token').val(), 

                        'idLook': document.formOperacion.idLookup.value,
                        'nombre':  document.formOperacion.operacion.value ,
                        'tipo': "Operacion",
                        'descripcion':document.formOperacion.idFamilia.value,
                        'atributo1':document.formOperacion.idMaquina.value,
                        'atributo2':document.formOperacion.SAM.value,
                        'atributo3':document.formOperacion.turno.value
                    }
    
                }).done(function(data) {
                    $('#modalOperacionesMaquila').modal('hide'); // abrir
                    listaroperaciones();
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
    }

}
function guardarColor(){

    if ( document.formColor.nombre.value  == "" || document.formColor.codigoColor.value  == "" || document.formColor.proveedorColor.value  == "" || document.formColor.calibre.value  == ""  ){
        $('#alertColor').css('display', '');
    }else{
        $('#alertColor').css('display', 'none');
        $.ajax({
            type: "GET",
            url: "/verificar_duplicado_maquila_lookup",
            data: {
                'Lookup': document.formColor.nombre.value,
                'Tipo': "Color",
                'descripcion':document.formColor.codigoColor.value ,
                'atributo1':document.formColor.proveedorColor.value,
                'atributo2':document.formColor.calibre.value
            }
        }).done(function(data) {
            if (data == false) {
                $.ajax({
                    type: "POST",
                    url: "/guardar-lookup-maquila",
                    data: {
                        //String descripcion, String atributo1, String atributo
                        "_csrf": $('#token').val(), 
                        'idLook': document.formColor.idLookup.value,
                        'nombre': document.formColor.nombre.value,
                        'tipo':'Color',
                        'descripcion':document.formColor.codigoColor.value ,
                        'atributo1':document.formColor.proveedorColor.value,
                        'atributo2':document.formColor.calibre.value
                    }
    
                }).done(function(data) {
                    $('#modalAgregarColor').modal('hide'); // abrir
                    listarColor();
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
    }


}

function guardarMovFijo(){

    if ( document.formMovFijo.nombre.value  == "" || document.formMovFijo.tipoMov.value  == ""  ){
        $('#alertMovFijo').css('display', '');
    }else{
        $('#alertMovFijo').css('display', 'none');
        $.ajax({
            type: "GET",
            url: "/verificar_duplicado_maquila_lookup",
            data: {
                'Lookup': document.formMovFijo.nombre.value,
                'Tipo': "MAF",
                'descripcion':document.formMovFijo.tipoMov.value 
            }
        }).done(function(data) {
            if (data == false) {
                $.ajax({
                    type: "POST",
                    url: "/guardar-lookup-maquila",
                    data: {
                        //String descripcion, String atributo1, String atributo
                        "_csrf": $('#token').val(), 
                        'idLook': document.formMovFijo.idLookup.value,
                        'nombre': document.formMovFijo.nombre.value,
                        'tipo':'MAF',
                        'descripcion':document.formMovFijo.tipoMov.value 
                    }
    
                }).done(function(data) {
                    $('#modalAgregarMovFijo').modal('hide'); // abrir
                    listarMAF();
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
    }


}


function guardarMovIns(){

    if ( document.formMovIns.nombre.value  == "" || document.formMovIns.tipoMovIns.value  == ""  ){
        $('#alertMovIns').css('display', '');
    }else{
        $('#alertMovIns').css('display', 'none');
        $.ajax({
            type: "GET",
            url: "/verificar_duplicado_maquila_lookup",
            data: {
                'Lookup': document.formMovIns.nombre.value,
                'Tipo': "MIN",
                'descripcion':document.formMovIns.tipoMovIns.value 
            }
        }).done(function(data) {
            if (data == false) {
                $.ajax({
                    type: "POST",
                    url: "/guardar-lookup-maquila",
                    data: {
                        //String descripcion, String atributo1, String atributo
                        "_csrf": $('#token').val(), 
                        'idLook': document.formMovIns.idLookup.value,
                        'nombre': document.formMovIns.nombre.value,
                        'tipo':'MIN',
                        'descripcion':document.formMovIns.tipoMovIns.value 
                    }
    
                }).done(function(data) {
                    $('#modalAgregarMovIns').modal('hide'); // abrir
                    listarMIN();
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
    }


}
function listaroperaciones (){
    //listar_maquila_operaciones
    $.ajax({
		method: "GET",
		url: "/listar_maquila_operaciones",
		data:{} ,
		success: (data) => {

        	var tabla = $('#tableOperacionesMaquila').DataTable();
        	
        
        	 
        	tabla.clear();
        	    
            $(data).each(function(i, v){ // indice, valor
            	var fecha;
        		var actualizo;
            	if (v[11] == null && v[12] == null ){
            		fecha='';
            		actualizo='';
            	}else{
            		actualizo=v[11];
            		fecha=v[12];
            	}
            	
            	if (v[8] == 1 ){
            		tabla.row.add([	
                		v[1],
                        v[2],
                        v[3],
                        v[4],
                        v[5],
                        v[6],
                        v[7],
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v[9] +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v[10]+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					(rolEditar == 1 ? '<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarOperacion(this)" idLookup ="'+v[0]+'"  nombre="'+v[2]+'"  data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v[0]+'" estatus="0" letrero="desactivar" tipo="este catalago" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>': " ") 
            
               		 ]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v[1],
                        v[2],
                        v[3],
                        v[4],
                        v[5],
                        v[6],
                        v[7],
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v[9] +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v[10]+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarOperacion(this)" idLookup ="'+v[0]+'"  nombre="'+v[2]+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ?'<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v[0]+'" estatus="1" letrero="activar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Reactivar"><i class="fas fa-caret-up"></i></button>': " ") 
            
               		 ]).node().id ="row";
            	}
            	
           	tabla.draw( false );
            	//fila = '<tr> <td>'+v[1]+'</td>  <td >'+ v[2] +'</td> <td >'+ v[3] +'</td> <td >'+ v[4] +'</td>  </tr>' ;
            	
            	
            	
            })
            
       	
		},
		error: (e) => {

		}
	})
}
function listarfamilia(){
    $.ajax({
		method: "GET",
		url: "/listar_maquila_object",
		data:{
            "Tipo":"Familia"
		} ,
		success: (data) => {

        	var tabla = $('#tableFamila').DataTable();
        	
        
        	 
        	tabla.clear();
        	    
            $(data).each(function(i, v){ // indice, valor
            	var fecha;
        		var actualizo;
            	if (v.actualizadoPor == null && v.ultimaFechaModificacion == null ){
            		fecha='';
            		actualizo='';
            	}else{
            		actualizo=v.actualizadoPor;
            		fecha=v.ultimaFechaModificacion;
            	}
            	
            	if (v.estatus == 1 ){
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					(rolEditar == 1 ? '<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarfamilia(this)" idLookup ="'+v.idLookup+'"  nombre="'+v.nombreLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="0" letrero="desactivar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>': " ")
            
               		 ]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
                		(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarfamilia(this)" idLookup ="'+v.idLookup+'"  nombre="'+v.nombreLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="1" letrero="activar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Reactivar"><i class="fas fa-caret-up"></i></button>': " ") 
            
               		 ]).node().id ="row";
            	}
            	
           	tabla.draw( false );
            	//fila = '<tr> <td>'+v[1]+'</td>  <td >'+ v[2] +'</td> <td >'+ v[3] +'</td> <td >'+ v[4] +'</td>  </tr>' ;
            	
            	
            	
            })
            
       	
		},
		error: (e) => {

		}
	})
}

function listarAFI(){
    $.ajax({
		method: "GET",
		url: "/listar_AFI_maquila",
		data:{} ,
		success: (data) => {

        	var tabla = $('#tableMaquinaActivo').DataTable();
        	
        
        	 
        	tabla.clear();
        	    
            $(data).each(function(i, v){ // indice, valor
            	var fecha;
        		var actualizo;
            	if (v[6] == null && v[7] == null ){
            		fecha='';
            		actualizo='';
            	}else{
            		actualizo=v[6];
            		fecha=v[7];
            	}
            	
            	if (v[8] == 1 ){
            		tabla.row.add([	
                		v[1],
                        v[2],
                        v[3],
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v[4] +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v[5]+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					(rolEditar == 1 ? '<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarAFI(this)" idLookup ="'+v[0]+'"  nombre="'+v[2]+'"  data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v[0]+'" estatus="0" letrero="desactivar" tipo="este catalago" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>': " ") 
            
               		 ]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v[1],
                        v[2],
                        v[3],
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v[4] +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v[5]+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					(rolEditar == 1 ? '<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarAFI(this)" idLookup ="'+v[0]+'"  nombre="'+v[2]+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ? '<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v[0]+'" estatus="1" letrero="activar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Reactivar"><i class="fas fa-caret-up"></i></button>': " ") 
            
               		 ]).node().id ="row";
            	}
            	
           	tabla.draw( false );
            	//fila = '<tr> <td>'+v[1]+'</td>  <td >'+ v[2] +'</td> <td >'+ v[3] +'</td> <td >'+ v[4] +'</td>  </tr>' ;
            	
            	
            	
            })
            
       	
		},
		error: (e) => {

		}
	})
}

function listarComponentes (){
    $.ajax({
		method: "GET",
		url: "/listar_maquila_object",
		data:{
            "Tipo":"Componente"
		} ,
		success: (data) => {

        	var tabla = $('#tableComponentes').DataTable();
        	
        
        	 
        	tabla.clear();
        	    
            $(data).each(function(i, v){ // indice, valor
            	var fecha;
        		var actualizo;
            	if (v.actualizadoPor == null && v.ultimaFechaModificacion == null ){
            		fecha='';
            		actualizo='';
            	}else{
            		actualizo=v.actualizadoPor;
            		fecha=v.ultimaFechaModificacion;
            	}
            	
            	if (v.estatus == 1 ){
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarComponente(this)" idLookup ="'+v.idLookup+'"  nombre="'+v.nombreLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ?'<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="0" letrero="desactivar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>': " ") 
            
               		 ]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
                		(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarComponente(this)" idLookup ="'+v.idLookup+'"  nombre="'+v.nombreLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ?'<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="1" letrero="activar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Reactivar"><i class="fas fa-caret-up"></i></button>': " ") 
            
               		 ]).node().id ="row";
            	}
            	
           	tabla.draw( false );
            	//fila = '<tr> <td>'+v[1]+'</td>  <td >'+ v[2] +'</td> <td >'+ v[3] +'</td> <td >'+ v[4] +'</td>  </tr>' ;
            	
            	
            	
            })
            
       	
		},
		error: (e) => {

		}
	})
}
function listarActivoFijo(){
    $.ajax({
		method: "GET",
		url: "/listar_maquila_object",
		data:{
            "Tipo":"Activo Fijo"
		} ,
		success: (data) => {

        	var tabla = $('#tableActivoFijo').DataTable();
        	
        
        	 
        	tabla.clear();
        	    
            $(data).each(function(i, v){ // indice, valor
            	var fecha;
        		var actualizo;
            	if (v.actualizadoPor == null && v.ultimaFechaModificacion == null ){
            		fecha='';
            		actualizo='';
            	}else{
            		actualizo=v.actualizadoPor;
            		fecha=v.ultimaFechaModificacion;
            	}
            	
            	if (v.estatus == 1 ){
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarActivoFijo(this)" idLookup ="'+v.idLookup+'"  nombre="'+v.nombreLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' : " ") +
    					(rolEliminar == 1 ?'<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="0" letrero="desactivar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>' : " ") 
            
               		 ]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
                		(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarActivoFijo(this)" idLookup ="'+v.idLookup+'"  nombre="'+v.nombreLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' : " ") +
    					(rolEliminar == 1 ?'<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="1" letrero="activar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Reactivar"><i class="fas fa-caret-up"></i></button>' : " ") 
            
               		 ]).node().id ="row";
            	}
            	
           	tabla.draw( false );
            	//fila = '<tr> <td>'+v[1]+'</td>  <td >'+ v[2] +'</td> <td >'+ v[3] +'</td> <td >'+ v[4] +'</td>  </tr>' ;
            	
            	
            	
            })
            
       	
		},
		error: (e) => {

		}
	})
}
function listarColor() {
    $.ajax({
		method: "GET",
		url: "/listar_color_maquila",
		data:{} ,
		success: (data) => {

        	var tabla = $('#tableColor').DataTable();
        	
        	 
        	tabla.clear();
        	    
            $(data).each(function(i, v){ // indice, valor
            	var fecha;
        		var actualizo;
            	if (v[9] == null && v[10] == null ){
            		fecha='';
            		actualizo='';
            	}else{
            		actualizo=v[9];
            		fecha=v[10];
            	}
            	
            	if (v[6] == 1 ){
            		tabla.row.add([	
                		v[1] ,
                        v[2] ,
                        "<td> <input type='color' value=" + v[3] + " disabled> </td>",
                        v[4] ,
                        v[5] ,
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v[7] +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v[8]+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarColor(this)" idLookup ="'+v[0]+'"  data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ?'<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v[0]+'" estatus="0" letrero="desactivar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>': " ") 
            
               		 ]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v[1] ,
                        v[2] ,
                        "<td> <input type='color' value=" + v[3] + " disabled> </td>",
                        v[4] ,
                        v[5] ,
                		
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
                		(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarColor(this)" idLookup ="'+v[0]+'"   data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>': " ") +
    					(rolEliminar == 1 ?'<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v[0]+'" estatus="1" letrero="activar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Reactivar"><i class="fas fa-caret-up"></i></button>': " ") 
            
               		 ]).node().id ="row";
            	}
            	
           	tabla.draw( false );
            	//fila = '<tr> <td>'+v[1]+'</td>  <td >'+ v[2] +'</td> <td >'+ v[3] +'</td> <td >'+ v[4] +'</td>  </tr>' ;
            	
            	
            	
            })
            
       	
		},
		error: (e) => {

		}
	})
    
}

function listarMAF(){

    $.ajax({
		method: "GET",
		url: "/listar_maquila_object",
		data:{
            "Tipo":"MAF"
		} ,
		success: (data) => {

        	var tabla = $('#tableMovFijo').DataTable();
        	
        
        	 
        	tabla.clear();
        	    
            $(data).each(function(i, v){ // indice, valor
            	var fecha;
        		var actualizo;
            	if (v.actualizadoPor == null && v.ultimaFechaModificacion == null ){
            		fecha='';
            		actualizo='';
            	}else{
            		actualizo=v.actualizadoPor;
            		fecha=v.ultimaFechaModificacion;
            	}
            	
            	if (v.estatus == 1 ){
            		tabla.row.add([	
                		v.idText ,
                        v.nombreLookup ,
                        v.descripcionLookup,
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarMAF(this)" idLookup ="'+v.idLookup+'"  nombre="'+v.nombreLookup+'" desc="'+v.descripcionLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>': " ")+
    					(rolEliminar == 1 ?'<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="0" letrero="desactivar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>' :" ")
            
               		 ]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
                		v.descripcionLookup,
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
                		(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarMAF(this)" idLookup ="'+v.idLookup+'"  nombre="'+v.nombreLookup+'" desc="'+v.descripcionLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>':" ")+
    					(rolEliminar == 1 ?'<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="1" letrero="activar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Reactivar"><i class="fas fa-caret-up"></i></button>':" ")
            
               		 ]).node().id ="row";
            	}
            	
           	tabla.draw( false );
            	//fila = '<tr> <td>'+v[1]+'</td>  <td >'+ v[2] +'</td> <td >'+ v[3] +'</td> <td >'+ v[4] +'</td>  </tr>' ;
            	
            	
            	
            })
            
       	
		},
		error: (e) => {

		}
	})
}
function listarMIN(){

    
    $.ajax({
		method: "GET",
		url: "/listar_maquila_object",
		data:{
            "Tipo":"MIN"
		} ,
		success: (data) => {

        	var tabla = $('#tableMIN').DataTable();
        	
        
        	 
        	tabla.clear();
        	    
            $(data).each(function(i, v){ // indice, valor
            	var fecha;
        		var actualizo;
            	if (v.actualizadoPor == null && v.ultimaFechaModificacion == null ){
            		fecha='';
            		actualizo='';
            	}else{
            		actualizo=v.actualizadoPor;
            		fecha=v.ultimaFechaModificacion;
            	}
            	
            	if (v.estatus == 1 ){
            		tabla.row.add([	
                		v.idText ,
                        v.nombreLookup ,
                        v.descripcionLookup,
                		
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
    					(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarMIN(this)" idLookup ="'+v.idLookup+'"  nombre="'+v.nombreLookup+'" desc="'+v.descripcionLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>':" ")+
    					(rolEliminar == 1 ?'<button class="btn btn-danger btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="0" letrero="desactivar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>':" ")
            
               		 ]).node().id ="row";
            	}else{
            		tabla.row.add([	
                		v.idText ,
                		v.nombreLookup ,
                		v.descripcionLookup,
                		'<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>'+v.creadoPor +' <br /><strong>Fecha de creaci&oacute;n: </strong> '+v.fechaCreacion+' <br><strong>Modificado por: </strong>'+actualizo+'<br><strong>Fecha de modicaci&oacute;n: </strong>'+fecha+'"><i class="fas fa-info"></i></button>'+
                		(rolEditar == 1 ?'<button class="btn btn-warning btn-circle btn-sm popoverxd" onclick="editarMIN(this)" idLookup ="'+v.idLookup+'"  nombre="'+v.nombreLookup+'" desc="'+v.descripcionLookup+'" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>':" ")+
    					(rolEliminar == 1 ?'<button class="btn btn-success btn-circle btn-sm popoverxd" onclick="cambioEstatus(this)" idLookup ="'+v.idLookup+'" estatus="1" letrero="activar" tipo="la familia" data-container="body" data-toggle="popover" data-placement="top" data-content="Reactivar"><i class="fas fa-caret-up"></i></button>':" ")
            
               		 ]).node().id ="row";
            	}
            	
           	tabla.draw( false );
            	//fila = '<tr> <td>'+v[1]+'</td>  <td >'+ v[2] +'</td> <td >'+ v[3] +'</td> <td >'+ v[4] +'</td>  </tr>' ;
            	
            	
            	
            })
            
       	
		},
		error: (e) => {

		}
	})
}
function editarOperacion(e){
    $('#alertOperaciones').css('display', 'none');
    
    $.ajax({
		method: "GET",
		url: "/debolver_lookup_maquila_by_id",
		data:{id:e.getAttribute("idLookup")} ,
		success: (data) => {
        
            
            document.formOperacion.operacion.value=data.nombreLookup;
            document.formOperacion.SAM.value=data.atributo2;
            document.formOperacion.turno.value=data.atributo3;

            document.formOperacion.idLookup.value=data.idLookup;

            llenarSelectFamila(data.descripcionLookup);
            llenarSelectMaquina(data.atributo1);
       	
		}, complete: function() {   
            $('#modalOperacionesMaquila').modal('show'); // abrir 
        },
		error: (e) => {

		}
	})
    

}
function editarAFI(e){
    $('#alertMaquina').css('display', 'none');
    $("#tituloAFI").text('Editar activo');

    $.ajax({
		method: "GET",
		url: "/debolver_lookup_maquila_by_id",
		data:{id:e.getAttribute("idLookup")} ,
		success: (data) => {
            document.formMaquina.nombre.value=data.nombreLookup;

            console.log(data);
            document.formMaquina.idLookup.value=data.idLookup;

            llenarSelectActivoFijoMaquila(data.descripcionLookup);
       	
		}, complete: function() {   
        },
		error: (e) => {

		}
	})
    $('#modalAgregarMaquina').modal('show'); // abrir 
    
}
function editarfamilia (e){
    $("#tituloFamilaOperaciones").text('Editar familia');
    $("#idLookupfamilia").val( e.getAttribute("idLookup"));
    $("#familia").val( e.getAttribute("nombre"));
    $('#agregarFamilaOperaciones').modal('show'); // abrir 
   
}
function editarComponente (e){
    $('#alertComponentes').css('display', 'none');
    $("#tituloComponentes").text('Editar componente');
    
    document.formComponentes.nombre.value = e.getAttribute("nombre");
    document.formComponentes.idLookup.value=e.getAttribute("idLookup");
    $('#modalAgregarComponentes').modal('show'); // abrir 

}
function editarActivoFijo(e){
    $('#alertActivoFijo').css('display', 'none');
    $("#tituloActivoFijo").text('Editar activo fijo');
    
    document.formActivoFijo.nombre.value = e.getAttribute("nombre");
    document.formActivoFijo.idLookup.value=e.getAttribute("idLookup");
    $('#modalAgregarActivoFijo').modal('show'); // abrir 

}

function editarMAF(e){

    $('#alertMovFijo').css('display', 'none');
    $("#tituloMovFijo").text('Editar movimiento fijo');
    document.formMovFijo.nombre.value = e.getAttribute("nombre");
    document.formMovFijo.idLookup.value=e.getAttribute("idLookup");
    $('#tipoMov').val(e.getAttribute("desc"));
    $('#tipoMov').selectpicker('refresh');
    $('#modalAgregarMovFijo').modal('show'); // abrir

}
function editarMIN(e){

    $('#alertMovIns').css('display', 'none');
    $("#tituloMovIns").text('Editar movimiento insumo');
    document.formMovIns.nombre.value = e.getAttribute("nombre");
    document.formMovIns.idLookup.value=e.getAttribute("idLookup");
    $('#tipoMovIns').val(e.getAttribute("desc"));
    $('#tipoMovIns').selectpicker('refresh');
    $('#modalAgregarMovIns').modal('show'); // abrir

}
function editarColor(e){
    
    $('#alertColor').css('display', 'none');
    $("#tituloColor").text('Editar color');

    $.ajax({
		method: "GET",
		url: "/debolver_lookup_maquila_by_id",
		data:{id:e.getAttribute("idLookup")} ,
		success: (data) => {
            document.formColor.nombre.value = data.nombreLookup;
    document.formColor.codigoColor.value = data.descripcionLookup;
    document.formColor.calibre.value = data.atributo2;
    document.formColor.idLookup.value = data.idLookup;
    llenarSelectProveedoresColor(data.atributo1);
    console.log(data)
       	
		}, complete: function() {   
        },
		error: (e) => {

		}
	})
    $('#modalAgregarColor').modal('show'); // abrir 
    

}
function cambioEstatus (e){
    // idLookup ="'+v.idLookup+'" estatus="1" letrero="Activar" tipo="Familia"
    Swal.fire({
        title: '¿Deseas '+e.getAttribute("letrero")+' este catalago?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmar'
      }).then((result) => {
        if (result.value && e.getAttribute("idLookup") != null) {
            $.ajax({
                  type: "POST",
                  url: "/cambio_estatus_maquila",
                  data: {
                      "_csrf": $('#token').val(),
                      'idLookup': e.getAttribute("idLookup"),
                      'estatus':e.getAttribute("estatus")
                  }

              }).done(function(data) {
                switch (data){
                   
                    case "Familia":listarfamilia();
                    break;
                    case "Activo Fijo":  listarActivoFijo();
                    break;
                    case "AFI" : listarAFI();
                    break;
                    case "Componente" : listarComponentes();
                    break;
                    case "Color" :listarColor();
                    break;
                    case "MAF": listarMAF();
                    break;
                    case "MIN" : listarMIN();
                    break;

                    case "Operacion" : listaroperaciones();
                    break;
                }
              });
              Swal.fire({
                  position: 'center',
                  icon: 'success',
                  title: 'Cambio de estatus correctamente',
                  showConfirmButton: false,
                  timer: 1250
              })
        }
      });
}