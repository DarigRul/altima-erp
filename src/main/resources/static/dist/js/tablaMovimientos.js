$(document).ready(function() {
	    var table = $('#movimientoMuestrario')
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
	            "lengthMenu": [
	                [5, 10, 25, 50, 100],
	                [5, 10, 25, 50, 100]
	            ],
	            "language": {
	                "sProcessing": "Procesando...",
	                "sLengthMenu": "Mostrar _MENU_ registros",
	                "sZeroRecords": "No se encontraron resultados",
	                "sEmptyTable": "Ningún dato disponible en esta tabla =(",
	                "sInfo": "Del _START_ al _END_ de un total de _TOTAL_ registros",
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