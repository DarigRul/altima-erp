(function() {
  var _div = document.createElement('div');
  jQuery.fn.dataTable.ext.type.search.html = function(data) {
    _div.innerHTML = data;
    return _div.textContent ?
      _div.textContent
        .replace(/[áÁàÀâÂäÄãÃåÅæÆ]/g, 'a')
        .replace(/[çÇ]/g, 'c')
        .replace(/[éÉèÈêÊëË]/g, 'e')
        .replace(/[íÍìÌîÎïÏîĩĨĬĭ]/g, 'i')
        .replace(/[ñÑ]/g, 'n')
        .replace(/[óÓòÒôÔöÖœŒ]/g, 'o')
        .replace(/[ß]/g, 's')
        .replace(/[úÚùÙûÛüÜ]/g, 'u')
        .replace(/[ýÝŷŶŸÿ]/g, 'n') :
      _div.innerText
        .replace(/[áÁàÀâÂäÄãÃåÅæÆ]/g, 'a')
        .replace(/[çÇ]/g, 'c')
        .replace(/[éÉèÈêÊëË]/g, 'e')
        .replace(/[íÍìÌîÎïÏîĩĨĬĭ]/g, 'i')
        .replace(/[ñÑ]/g, 'n')
        .replace(/[óÓòÒôÔöÖœŒ]/g, 'o')
        .replace(/[ß]/g, 's')
        .replace(/[úÚùÙûÛüÜ]/g, 'u')
        .replace(/[ýÝŷŶŸÿ]/g, 'n');
  };
})();
$(document).ready(function() {
	    var table = $('.tablaCotizaciones')
	        .DataTable({
	            "ordering": false,
	            "pageLength": 5,
	            "responsive": true,
	            "stateSave":true,
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
// Remove accented character from search input as well
$('#example_filter input[type=search]').keyup( function () {
    var table = $('.tablaCotizaciones').DataTable(); 
    table.search(
        jQuery.fn.DataTable.ext.type.search.html(this.value)
    ).draw();
} );

function solicitarAutorizacion(id){
	
	Swal.fire({
		  title: '¿Desea solicitar autorización?',
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
			          title: '¡Autorización solicitada!',
			          showConfirmButton: false,
			          timer: 1550,
				      onClose: () => {
				    	  location.href = "/pedirAutorizacion/"+id;
				      }
				})
			}
		})
}

function Autorizar(id){
	
	Swal.fire({
		  title: '¿Desea solicitar autorización?',
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
			          title: '¡Autorización solicitada!',
			          showConfirmButton: false,
			          timer: 1550,
				      onClose: () => {
				    	  location.href = "/Autorizar/"+id;
				      }
				})
			}
		})
}