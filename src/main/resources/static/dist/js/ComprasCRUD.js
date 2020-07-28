$('#detalleClasificacion').on('shown.bs.modal', function() {
    $(document).off('focusin.modal');
});

function agregarClasificacion() {
    Swal.fire({
        title: 'Nueva clasificaci&oacute;n',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="nombreClas">Nombre</label>' +
            '<input type="text" class="form-control" id="nombreClas" placeholder="Materia Prima">' +
            '</div>' +
            '</div>',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Agregar',
        cancelButtonText: 'Cancelar',
        showLoaderOnConfirm: true
    }).then((result) => {
        if (result.value) {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Clasificaci&oacute;n agregada correctamente',
                showConfirmButton: false,
                timer: 2500
            })
        }
    });
}

function editarClasificacion() {
    Swal.fire({
        title: 'Editar clasificaci&oacute;n',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            '<label for="nombreClasedit">Nombre</label>' +
            '<input type="text" class="form-control" id="nombreClasedit" placeholder="Materia Prima">' +
            '</div>' +
            '</div>',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Actualizar',
        cancelButtonText: 'Cancelar',
        showLoaderOnConfirm: true
    }).then((result) => {
        if (result.value) {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Clasificaci&oacute;n actualizada correctamente',
                showConfirmButton: false,
                timer: 2500
            })
        }
    });
}

function eliminarClasificacion() {
    Swal.fire({
        title: '¿Deseas eliminar la clasificaci&oacute;n?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.value) {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Clasificaci&oacute;n eliminada correctamente',
                showConfirmButton: false,
                timer: 2400
            })
        }
    })
}

function reactivarClasificacion() {
    Swal.fire({
        title: '¿Deseas reactivar la clasificaci&oacute;n?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirmar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.value) {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Clasificaci&oacute;n reactivada correctamente',
                showConfirmButton: false,
                timer: 2400
            })
        }
    })
}
$(document).ready(function() {
    var table = $('.tablaClasificacion')
        .DataTable({
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
            }],
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
    new $.fn.dataTable.FixedHeader(table);

});