 	$(document).ready(function() {
    $('#tablaAMPInv thead tr').clone(true).appendTo('#tablaAMPInv thead');
    $('#tablaAMPInv thead tr:eq(1) th').each(function(i) {
        var title = $(this).text();
        $(this).html('<input type="text" class="form-control" placeholder="Buscar ' + title + '" />');

        $('input', this).on('keyup change', function() {
            if (table.column(i).search() !== this.value) {
                table
                    .column(i)
                    .search(this.value)
                    .draw();
            }
        });
    });
    var table = $('#tablaAMPInv')
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
                { "width": "10%", "targets": 0 },
                { "width": "25%", "targets": 1},
                { "width": "15%", "targets": 2 },
                { "width": "10%", "targets": 3 },
                { "width": "10%", "targets": 4 },
                { "width": "10%", "targets": 5 },
                { "width": "20%", "targets": 6 },
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
                "sInfoFiltered": "(filtrado de un total de MAX registros)",
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