var tableRHEmpresa;
var tableRHArea;
var tableRHDepartamento;
var tableRHPuestos;
var tableRHHorarios;
var tableRHCalendario;
$(document).ready(function () {
   tableRHEmpresa= $('#tableEmpresa')
      .DataTable({
         "ordering": false,
         "pageLength": 5,
         "responsive": true,
         "drawCallback": function () {
            $('.popoverxd').popover({
               container: 'body',
               trigger: 'hover'
            });
         },
         "columnDefs": [
             {"className": "dt-center", "targets": 2}
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
   new $.fn.dataTable.FixedHeader(tableRHEmpresa);

});

$(document).ready(function () {
   tableRHArea= $('#tableRHArea')
      .DataTable({
         "ordering": false,
         "pageLength": 5,
         "responsive": true,
         "columnDefs": [
             {"className": "dt-center", "targets": 2}
           ],
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
   new $.fn.dataTable.FixedHeader(tableRHArea);

});

$(document).ready(function () {
	tableRHDepartamento= $('#tableDepartamento')
	      .DataTable({
	         "ordering": false,
	         "pageLength": 5,
	         "responsive": true,
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
	   new $.fn.dataTable.FixedHeader(tableRHDepartamento);

	});

$(document).ready(function () {
	tableRHPuestos= $('#tablePuestos')
	      .DataTable({
	         "ordering": false,
	         "pageLength": 5,
	         "responsive": true,
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
	   new $.fn.dataTable.FixedHeader(tableRHPuestos);

	});

$(document).ready(function () {
	tableRHHorarios= $('#tableHorarios')
	      .DataTable({
	         "ordering": false,
	         "pageLength": 5,
	         "responsive": true,
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
	   new $.fn.dataTable.FixedHeader(tableRHHorarios);

	});

$(document).ready(function () {
	tableRHCalendario= $('#tableCalendarios')
	      .DataTable({
	         "ordering": false,
	         "pageLength": 5,
	         "responsive": true,
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
	   new $.fn.dataTable.FixedHeader(tableRHCalendario);

	});