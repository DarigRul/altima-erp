/**
 * Victor Hugo García Ilhuicatzi
 * Uso el controller de AgenteVentaController
 * Service de comercialAgentesVentaService
 */
$(document).ready(function() {
	
	tablaSeguimiento();
});
var tabla;

function mostrarListaSeguimiento(idCliente){
	tabla.rows().remove();
	$.ajax({
		method:"GET",
		url:"/seguimientoDetalles",
		data:{
			idCliente: idCliente
		},
		beforeSend: function () {
       	 Swal.fire({
                title: 'Cargando ',
                html: 'Por favor espere',// add html attribute if you want or remove
                allowOutsideClick: false,
                timerProgressBar: true,
                onBeforeOpen: () => {
                    Swal.showLoading()
                },
            });
		},
		success: (data) => {
			Swal.fire({
				position: 'center',
		        icon: 'success',
                title: 'Listo! ',
                showConfirmButton: false,
                timer: 1000
            });
			
			for (i in data){
				tabla.row.add([
					data[i][1],
					data[i][2],
					"<input type='hidden' value="+data[i][0]+">"+
					"<input type='hidden' value="+data[i][3]+">"+
					(data[i][5]==null?"":data[i][5]),
					'<button th:if="${(#authorization.expression("hasRole(""ROLE_COMERCIAL_AGENTES_SEGUIMIENTOS_EDITAR"")") or #authorization.expression("hasRole(""ROLE_ADMINISTRADOR"")"))}"' + 
					' class="btn btn-warning btn-circle btn-sm popoverxd" id="buttonObs"'+
                    ' data-container="body" data-placement="top" data-content="Actualizar observaci&oacute;n"><i class="fas fa-pen"></i></button>'
				]).node().id ="row"+data[i][0];
				tabla.draw( false );
			}
			$('#accionesClienteAgente').modal('toggle');
		} 
		
	});
}

//abrir modal para agregar observaciones al seguimiento //
//														//
$(document).on('click', '#buttonObs', function (event) {//
event.preventDefault();                           		//
let hola = $(this).closest('tr');  		       			//
var idRegistro = $(hola.find("input")[0]).val();		//
var nombreTabla = $(hola.find("input")[1]).val();		//
abrirModalObservacion(idRegistro, nombreTabla)			//
});                                                   	//
//======================================================//

function abrirModalObservacion(idRegistro, nombreTabla){
	$('#idC').val(idRegistro);
	$('#nomT').val(nombreTabla);
	$('#ObservacionTexto').remove();
	$('#modalObservacionesDiv').append('<input type="text" class="form-control" id="ObservacionTexto">');
	$('#ModalObservacionSeguimiento').modal("toggle");
}

function guardarObservacionSeguimiento() {
	var idRegistro = $('#idC').val();
	var nombreTabla = $('#nomT').val();
	var observaciones = $('#ObservacionTexto').val();
	
	if(observaciones == '' || observaciones == null || observaciones == undefined){
		Swal.fire({
			position: 'center',
	        icon: 'error',
            title: 'Debe llenar el campo! ',
            showConfirmButton: false,
            timer: 2500
        });
	}
	
	else{
		$.ajax({
			method: "POST",
			url: "/guardarObservacionesSeguimiento",
			data:{
				"_csrf": $('#token').val(),
				idRegistro: idRegistro,
				nombreTabla: nombreTabla,
				observaciones: observaciones
			},
			beforeSend: function () {
		       	 Swal.fire({
		                title: 'Cargando ',
		                html: 'Por favor espere',// add html attribute if you want or remove
		                allowOutsideClick: false,
		                timerProgressBar: true,
		                onBeforeOpen: () => {
		                    Swal.showLoading()
		                },
		            });
			},
			success: (data) => {
				Swal.fire({
					position: 'center',
			        icon: 'success',
	                title: 'Se ha guardado correctamente! ',
	                showConfirmButton: false,
	                timer: 2550,
	                onClose: () =>{
	                	$('#ModalObservacionSeguimiento').modal('toggle');
	                	$('#accionesClienteAgente').modal('toggle');
	                	$('#accionesClienteAgente').modal('show');
	                }
	            });
			}
			
		});
	}

}

function tablaSeguimiento(){
	tabla = $('#tablaSeguimientosAgente')
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
}