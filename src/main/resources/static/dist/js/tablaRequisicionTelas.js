$(document).ready(function() {
    $('.requisicionTabla thead tr').clone(true).appendTo( '.requisicionTabla thead' );
    $('.requisicionTabla thead tr:eq(1) th.select-filter').each( function (i) {
        var title = $(this).text();
        $(this).html( '<input class="form-control" type="text" placeholder="Buscar" />' );
 
        $( 'input', this ).on( 'keyup change', function () {
            if ( table.column(i).search() !== this.value ) {
                table
                    .column(i)
                    .search( this.value )
                    .draw();
            }
        } );
    } );
    var table = $('.requisicionTabla')
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
            "columnDefs": [
                { "width": "1%", "targets": 0 },
                { "width": "5%", "targets": 1 },
                { "width": "5%", "targets": 2 },
                { "width": "15%", "targets": 3 },
                { "width": "10%", "targets": 4 },
                { "width": "15%", "targets": 5 },
                { "width": "4%", "targets": 6 },
                { "width": "10%", "targets": 7 },
                { "width": "5%", "targets": 8 },
                { "width": "10%", "targets": 9 },
                { "width": "10%", "targets": 10 },
                { "width": "10%", "targets": 11 },
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
    new $.fn.dataTable.FixedHeader(table);

});