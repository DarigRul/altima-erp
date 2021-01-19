var tablaMultialmacenes = $('.tableExplosion')
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

var tablaMultialmacenesRollos = $('.tableExplosionRollos')
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
    'columnDefs': [{
       'targets': 0,
       'searchable': false,
       'orderable': false,
       'className': 'dt-body-center',
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