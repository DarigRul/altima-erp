$(document).ready(function() {

	listarRutas();

});
//Funcion para llenar la tabla con las rutas de la base de datos
function listarRutas() {
	
	$.ajax({
	    method: "GET",
	    url: "/listarRutas",
	    success: (data) => {
	    	$('#quitar').remove();
	    	$('#contenedorTabla').append("<div class='card-body' id='quitar'>" +
	    			"<table class='table tablexd table-striped table-bordered' id='idtable'>" +
                                        "<thead>" +
                                            "<tr>" +
                                                "<th>Clave</th>" +
                                                "<th>Nombre</th>" + 
                                                "<th>Descripci&oacute;n</th>" +
                                                "<th>Acciones</th>" +
                                            "</tr>" +
                                        "</thead>" +
                                    "</table>" + "</div>");
	        var a;
	        var b = [];
	        for (i in data){
	        	
					a = [
					"<tr>" +
					"<td>" + data[i].idText + "</td>",
					"<td>" + data[i].nombreRuta + "</td>",
					"<td>" + data[i].descripcionRuta + "</td>",
					"<td class='alignCenter'>" +
					    "<button data-target='#agregarRuta' data-toggle='modal' class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-placement='top' data-content='Editar'><i class='fas fa-pen' onclick='editarRuta(" + data[i].idRuta +")'></i></button>&nbsp;" +
					"</td>" +
					"</tr>"
					];
					b.push(a);
	        }
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
	        	});
		    var table = $('#idtable')
	        .DataTable({
	        	"data":b,
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
		 // Remove accented character from search input as well
		    $('#example_filter input[type=search]').keyup( function () {
		        var table = $('.tablaGeneral').DataTable(); 
		        table.search(
		            jQuery.fn.DataTable.ext.type.search.html(this.value)
		        ).draw();
		    } );
	    },
	    error: (e) => {
	        // location.reload();
	    }
}
)}

//Funcion para crear una nueva ruta a la tabla y actualizarla
function crear_datos() {
// Recoges variables
	var nombreRuta = $('#nombreRuta').val();
	var descripcion = $('#descripcionRuta').val();
	var idd = $('#procesoRuta').val();
	var proc = idd.toString();
	console.log(idd);

$.ajax({
    type: "POST",
    
    url: "/crear_ruta",
    data: {
    	"_csrf": $('#token').val(),
        nombre: nombreRuta,
        descripcion: descripcion,
        procesos: proc,

    },
    
    success: (data) => {
    	$('#agregarRuta').modal('toggle');
        listarRutas();
        procesos();
        console.log("entra ruta");
        $("#agregarRuta").find("input,textarea,select").val("");
    	
    },
    
    error: (e) => {
        // location.reload();
    }
});

}

//Funcion para listar los procesos en el modal
function procesos(){
	$.ajax({
	    type: "POST",
	    url: "/listarProcesos",
	    data: {
	    	"_csrf": $('#token').val()
	    },
	    
	    success: (data) => {
			$('.opcionruta').remove();
			console.log(data);
			for (i in data){
			$('#procesoRuta').append("<option class='opcionruta' value='"+data[i].idLookup+"'>"+ data[i].nombreLookup +"</opcion>");
			}
	    },
	    
	    error: (e) => {
	        // location.reload();
	    }
	});
}

function editarRuta(id) {
	procesos();
	
	$.ajax({
	    type: "POST",
	    url: "/editarRuta",
	    data: {
	    	"_csrf": $('#token').val(),
	    	idRuta : id
	    },
	    
	    success: (data) => {
	    	
	    	
	        console.log(data);
	        $('#nombreRuta').val(data[0].nombreRuta);
	    	$('#descripcionRuta').val(data[0].descripcionRuta);
	    	var a = 8;
	    	console.log(a);
	    	
	    	for (i in data){
	    		if(i>0){
		    		console.log(data[i][1]);
	    			$( '#agregarRuta' ).find('option[value="' + data[i][1] + '"]').prop('selected', true);
	    		}
	    	}
	    	
	    },
	    
	    error: (e) => {
	        // location.reload();
	    }
	});
}

function vaciarModal(){
	
	$.ajax({	  
		type: "POST",
	    url: "/borrarRegistro",
	    data: {
	    	"_csrf": $('#token').val()
	    },
	    
	    success: (data) => {
			$("#agregarRuta").find("input,textarea,select").val("");
			$('.opcionruta').remove();
			procesos();
	    },
	   
	    error: (e) => {
	        // location.reload();
	    }
	});
	
}

function ValidarPrimerPestana()
{
	if($('#nombreRuta').val() != "" && $('#descripcionRuta').val() != "" && $('#procesoRuta').val() != "")
	{
		$('#AlertaPestana').css('display', 'none');
		$('#action2').click();
	}
	else
	{
		$('#AlertaPestana').css('display', 'block');
	}
}


