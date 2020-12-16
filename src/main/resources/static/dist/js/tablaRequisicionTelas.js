var table;
$(document).ready(function () {
    $('.requisicionTabla thead tr').clone(true).appendTo('.requisicionTabla thead');
    $('.requisicionTabla thead tr:eq(0) th').each(function () {
        var title = $('.requisicionTabla thead th').eq($(this).index()).text();
        $(this).html('<input type="text" class="form-control" placeholder="Buscar" />');
    });
    table = $('.requisicionTabla')
        .DataTable({
            "ordering": false,
            "orderCellsTop": true,
            "fixedHeader": true,
            "pageLength": 5,
            "stateSave": true,
            "scrollX": true,
            "drawCallback": function () {
                $('.popoverxd').popover({
                    container: 'body',
                    trigger: 'hover'
                });
            },
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
    // Restore state
    var state = table.state.loaded();
    if (state) {
        table.columns().eq(0).each(function (colIdx) {
            var colSearch = state.columns[colIdx].search;

            if (colSearch.search) {
                $('input', table.column(colIdx).header()).val(colSearch.search);
            }
        });

        table.draw();
    }


    // Apply the search
    table.columns().eq(0).each(function (colIdx) {
        $('input', table.column(colIdx).header()).on('keyup change', function () {
            table
                .column(colIdx)
                .search(this.value)
                .draw();
        });
    });
});