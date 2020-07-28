$(document).ready(function() {
    var table = $('.tablaConsumo')
        .DataTable({
            "ordering": false,
            "bSort" : false,
            "lengthChange": false,
            "lengthMenu": [[-1], ["All"]],
            "paging": false,
            "bInfo": false,
            "scrollX": true,
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