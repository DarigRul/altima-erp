$(document).ready(function () {

	
	
});

$('#materialesAlmacen').on('shown.bs.modal', function () {
	$(document).off('focusin.modal');
});

function inputapartados(){


	Swal.fire({
		  title: 'Ingrese la cantidad a apartar',
		  input: 'text',
		  //inputValue: inputValue,
		  showCancelButton: true,
		  inputValidator: (value) => {
		    if (!value) {
		      return 'Ingrese un valor valido'
		    }
		  }
		})

		if (ipAddress) {
		  Swal.fire(`Your IP address is ${ipAddress}`)
		}

		
}



function tablamulti(material){
	 //return false;
	 //return true;
	//false;
	$.ajax({
		method: "GET",
		url: "/explosion-materiales-habilitacion",
		data: {

			'IdArticulo':material
		},
		success: (data) => {
			$('#quitarmultialmacenes').remove();
			$('#materialesAlmacenes').append("<div class='modal-body modal-rounded-footer' id='quitarmultialmacenes'>"+
                "<ul class='list-group list-group-horizontal-md' style='margin-bottom: 20px;'>"+
                    "<li class='list-group-item' style='width: 25%;'><strong>Disponible: </strong><a>45</a></li>"+
                    "<li class='list-group-item' style='width: 25%;'><strong>Requerido: </strong><a>78</a></li>"+
                    "<li class='list-group-item' style='width: 25%;'><strong>Apartado: </strong><a>78</a></li>"+
                    "<li class='list-group-item' style='width: 25%;'><strong>Restante: </strong><a>0</a></li>"+
                "</ul>"+
                "<table class='table tablaGeneral table-striped table-bordered' style='width:100%;' id='idtablemultialmacenes'>"+
                    "<thead>"+
                        "<tr>"+
                            "<th>Almac&eacute;n</th>"+
                            "<th>Existencia</th>"+
                            "<th>Traspaso</th>"+
                            "<th>Acciones</th>"+
                        "</tr>"+
                    "</thead>"+
					  "</table>"+
            "</div>");
			var a;
			var b = [];

			for (i in data) {
				// var creacion = data[i].actualizadoPor == null ? "" :
				// data[i].actualizadoPor;
				a = [
					"<tr>" +
					"<td>" + data[i][3] + "</td>",
					
					"<td>" + data[i][9] + "</td>",
					
					"<td>" + data[i][8] + "</td>",
					"<td>" + 
					"<button id='modalTomar' onclick='inputapartados()' class='btn btn-altima btn-sm btn-circle popoverxd'     data-placement='top' data-content='Tomar'><i class='fas fa-hand-pointer'></i></button>"+
					"</td>",

					"<tr>"
					];
				b.push(a);
			}

			var tablaMultialmacenes = $('#idtablemultialmacenes').DataTable({
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
					targets: 1,
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
						"sEmptyTable": "NingÃºn dato disponible en esta tabla =(",
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
							"sLast": "Ãšltimo",
							"sNext": "Siguiente",
							"sPrevious": "Anterior"
						},

						"buttons": {
							"copy": "Copiar",
							"colvis": "Visibilidad"
						}
					}
			});
			new $.fn.dataTable.FixedHeader(tablaMultialmacenes);
		},
		error: (e) => {

		}
	})

}
