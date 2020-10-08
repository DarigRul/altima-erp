$(document).ready(function() {

	ListAgentesVenta();

});

function listarAreaAgentes(idArea, idDepartamento, idPuesto, idEmpleado){
	
	$.ajax({
		method: "GET",
	    url: "/areaAgentes",
	    data: {
	    },
	    success:(data) => {
	    	$('#areaAgente').find("option").remove();
	    	for (i in data){
	    		$('#areaAgente').append("<option value="+data[i].idLookup+">"+data[i].nombreLookup+"</option>");
	    		
	    	}
	    	$('#areaAgente').selectpicker("refresh");
	    	$('#areaAgente option[value="'+idArea+'"]').attr("selected", true);
	    	$('#areaAgente').selectpicker("refresh");
	    	ListDepa(idDepartamento, idPuesto, idEmpleado);
	    },
	    error:(e) =>{
	    	
	    }
	});
	
}

$('#areaAgente').on('change', function(){
	ListDepa();
})

function ListDepa(idDepartamento, idPuesto, idEmpleado){
	$('#depaAgente').find("option").remove();
	var idArea = $('#areaAgente').val();
	$.ajax({
		method: "GET",
	    url: "/departamentoAgentes",
	    data: {
	    	idArea: idArea
	    },
	    success:(data) => {
	    	
	    	for (i in data){
	    		
	    		$('#depaAgente').append("<option value="+data[i].idDepartamento+">"+data[i].nombreDepartamento+"</option>");
	    	}
	    	$('#depaAgente').selectpicker("refresh");
	    	$('#depaAgente option[value="'+idDepartamento+'"]').attr("selected", true);
	    	$('#depaAgente').selectpicker("refresh");
	    	ListPuestos(idPuesto, idEmpleado);
	    },
	    error:(e) =>{
	    	
	    }
	});
}

$('#depaAgente').on('change', function (){
	ListPuestos();
})

function ListPuestos(idPuesto, idEmpleado){
	$('#puestoAgente').find("option").remove();
	var idDepartamento = $('#depaAgente').val();
	$.ajax({
		method: "GET",
	    url: "/puestoAgentes",
	    data: {
	    	idDepartamento: idDepartamento
	    },
	    success:(data) => {
	    	for (i in data){
	    		$('#puestoAgente').append("<option value="+data[i].idPuesto+">"+data[i].nombrePuesto+"</option>");
	    	}
	    	$('#puestoAgente').selectpicker("refresh");
	    	$('#puestoAgente option[value="'+idPuesto+'"]').attr("selected", true);
	    	$('#puestoAgente').selectpicker("refresh");
	    	ListEmpleados(idEmpleado);
	    },
	    error:(e) =>{
	    	
	    }
	});
}

$('#puestoAgente').on('change', function (){
	ListEmpleados();
})

function ListEmpleados(idEmpleado){
	$('#nombreAgente').find("option").remove();
	var idArea = $('#areaAgente').val();
	var idDepartamento = $('#depaAgente').val();
	var idPuesto = $('#puestoAgente').val();
	
	$.ajax({
		method: "GET",
	    url: "/EmpleadosCatalogoComercial",
	    data: {
	    	idLookup: idArea,
	    	idDepartamento: idDepartamento,
	    	idPuesto: idPuesto
	    },
	    success:(data) => {
	    	for (i in data){
	    		$('#nombreAgente').append("<option value="+data[i][0]+">"+data[i][2] +" "+ data[i][3] +" "+ data[i][4]+"</option>");
	    	}
	    	$('#nombreAgente').selectpicker("refresh");
	    	$('#nombreAgente option[value="'+idEmpleado+'"]').attr("selected", true);
	    	$('#nombreAgente').selectpicker("refresh");
	    	
	    	if(idEmpleado){

	         	 Swal.fire({
	                  title: 'Cargando ',
	                  html: 'Por favor espere',// add html attribute if you want or remove
	                  allowOutsideClick: false,
	                  timerProgressBar: true,
	                  showConfirmButton: false,
 	    		      timer: 2
	              });
	   		    
	   	}
	    },
	    error:(e) =>{
	    }
	});
}

function ListAgentesVenta(){
	$.ajax({
		method: "GET",
	    url: "/empleadosAgentes",
	    data: {
	    },
	    success:(data) => {
	    	$('#borrarTablaAgentes').remove();
	        $('#crearTablaAgentes').append(" <div id='borrarTablaAgentes'>" +
	                "<table class='table table-striped table-bordered' id='tablaAgentesVenta' style='width: 100%'>" +
	                    "<thead>" +
	                        "<tr>" +
	                            "<th>Clave</th>" +
	                            "<th>Nombre</th>" +
	                            "<th>For&aacute;neo</th>" +
	                            "<th>Licitaci&oacute;n</th>" +
	                            "<th>Acciones</th>" +
	                        "</tr>" +
	                    "</thead>" +
	                 "</table>" +
	              "</div>");
	        var a;
            var b = [];
	    	for (i in data){
	    		 var creacion = data[i][8] == null ? "" : data[i][8];
                 a = [
                     "<tr>" +
                     "<td>" + data[i][1] + "</td>",
                     "<td>" + data[i][2]+" "+data[i][3] + " " + data[i][4] + "</td>",
                     "<td>" + (data[i][5]==1?'Si':'No') +"</td>",
                     "<td>" + (data[i][6]==1?'Si':'No') +"</td>",
                     "<td style='text-align: center'>" +
                     "<button class='btn btn-info btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-html='true' data-content='<strong>Creado por: </strong>" + data[i][7] + " <br /><strong>Fecha de creación:</strong> " + data[i][9] + "<br><strong>Modificado por:</strong>" + creacion + "<br><strong>Fecha de modicación:</strong>" + data[i][10] + "'><i class='fas fa-info'></i></button> " +
                     "<button onclick='editarAgente("+ data[i][0] +")' class='btn btn-warning btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>" +
                     (data[i][11] == 1 ?"<button onclick='bajarAgente("+ data[i][0] +")' class='btn btn-danger btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de baja'><i class='fas fa-caret-down'></i></button>":" ") +
                     (data[i][11] == 0 ?"<button onclick='altaAgente("+ data[i][0] +")' class='btn btn-success btn-circle btn-sm popoverxd' data-container='body' data-toggle='popover' data-placement='top' data-content='Dar de alta'><i class='fas fa-caret-up'></i></button>":" ") +
                     "</td>" +
                     "<tr>"
                 ];
                 b.push(a);
                 
	    	}

	        var tablaAgentesVenta = $('#tablaAgentesVenta').DataTable({
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
                        targets: 3,
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
	        
           // new $.fn.dataTable.FixedHeader(tablaAgentesVenta);
		},
		error:(e) =>{
		    	
		}
	
	});
}
$('#detalleAgente').on('shown.bs.modal', function() {
	ListAgentesVenta();
});

function agregarAgente() {
	$('#agregarAgente').modal('toggle');
	$('#foraneoAgente').prop("checked",false);
	$('#licitacionAgente').prop("checked",false);
	$('#idAgenteCatalogoComercial').val("");
	listarAreaAgentes();
}

function editarAgente(id) {
	$('#agregarAgente').modal('toggle');
	$('#foraneoAgente').prop("checked",false);
	$('#licitacionAgente').prop("checked",false);
	$('#idAgenteCatalogoComercial').val(id);
	
	$.ajax({
		method: "GET",
	    url: "/ExtraerPuestoAgenteCatalogo",
	    data: {
	    	idEmpleado: id
	    },
	    success:(data) => {
	    	if(data[3]!=null || data[3]!=undefined || data[3]!=''){

	         	 Swal.fire({
	                  title: 'Cargando ',
	                  html: 'Por favor espere',// add html attribute if you want or remove
	                  allowOutsideClick: false,
	                  timerProgressBar: true,
	                  onBeforeOpen: () => {
	                      Swal.showLoading()
	                  },
	              });
	   		    
	   	}
	    	listarAreaAgentes(data[3], data[2], data[1], data[0]);
	    }
	});
	
	$.ajax({
		method: "GET",
	    url: "/ExtraerDatosAgenteCatalogo",
	    data: {
	    	idEmpleado: id
	    },
	    success:(data) => {
	    	if(data.foraneo==1){
	    		$('#foraneoAgente').prop("checked",true);
	    	}
	    	if(data.licitacion==1){
	    		$('#licitacionAgente').prop("checked",true);
	    	}
	    }
	});
}

function guardarInformacionAgente(){
	
	var idEmpleado = $('#nombreAgente').val();
	var foraneo = $('input:checkbox[id=foraneoAgente]:checked').val();
	var licitacion = $('input:checkbox[id=licitacionAgente]:checked').val();
	
	//registro nuevo
	if($('#idAgenteCatalogoComercial').val()=='' || $('#idAgenteCatalogoComercial').val()==null || $('#idAgenteCatalogoComercial').val()==undefined){
		$.ajax({
			method: "GET",
		    url: "/guardarAgenteCatalogo",
		    data: {
		    	idEmpleado: idEmpleado,
		    	foraneo: foraneo,
		    	licitacion: licitacion
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
		    success:(data) => {
		    	if(data==1){
   	    		 Swal.fire({
   	    		        position: "center",
   	    		        icon: "success",
   	    		        title: "Agente agregado correctamente",
   	    		        showConfirmButton: false,
   	    		        timer: 2500,
   	    		        onClose:()=>{
   	    		        	$('#agregarAgente').modal('toggle');
   	    		        	ListAgentesVenta();
   	    		        }
   	    		      });
	   	    	}
	   	    	else{
	   	    		 Swal.fire({
	   	    		        position: "center",
	   	    		        icon: "error",
	   	    		        title: "Ya existe ese agente en la lista",
	   	    		        showConfirmButton: false,
	   	    		        timer: 2500
	   	    		      });
	   	    	}
		    },
		    error:(e) =>{
		    	
		    }
		});
	}
	//registro existente
	else{
		$.ajax({
			method: "GET",
		    url: "/editarAgenteCatalogo",
		    data: {
		    	idEmpleado: idEmpleado,
		    	foraneo: foraneo,
		    	licitacion: licitacion
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
		    success:(data) => {
		    	if(data==1){
	   	    		 Swal.fire({
	   	    		        position: "center",
	   	    		        icon: "success",
	   	    		        title: "Agente editado correctamente",
	   	    		        showConfirmButton: false,
	   	    		        timer: 2500,
	   	    		        onClose:()=>{
	   	    		        	$('#agregarAgente').modal('toggle');
	   	    		        	ListAgentesVenta();
	   	    		        }
	   	    		      });
		   	    }
	   	    	else{
	   	    		 Swal.fire({
	   	    		        position: "center",
	   	    		        icon: "error",
	   	    		        title: "Hubo un problema al guardar la información",
	   	    		        showConfirmButton: false,
	   	    		        timer: 2500,
	   	    		      });
	   	    	}
		    },
		    error:(e) =>{
		    	
		    }
		});
	}
}

$('#ValidarInformacionAgenteCatalogo').on("click", function(){
	if($('#areaAgente').val()=='' || $('#areaAgente').val()==null || $('#areaAgente').val()==undefined ||
	   $('#depaAgente').val()=='' || $('#depaAgente').val()==null || $('#depaAgente').val()==undefined || 
	   $('#puestoAgente').val()=='' || $('#puestoAgente').val()==null || $('#puestoAgente').val()==undefined || 
	   $('#nombreAgente').val()=='' || $('#nombreAgente').val()==null || $('#nombreAgente').val()==undefined ){
		Swal.fire({
	        position: "center",
	        icon: "error",
	        title: "Debe llenar todos los selectores",
	        showConfirmButton: false,
	        timer: 2500
	      })
	}
	else{
		guardarInformacionAgente();
	}
	
})

function bajarAgente(id) {
  Swal.fire({
    title: "¿Deseas dar de baja al agente?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Confirmar",
    cancelButtonText: "Cancelar",
    confirmButtonColor: "#0288d1",
    cancelButtonColor: "#dc3545",
  }).then((result) => {
    if (result.value) {
    	var idEmpleado = id;
    	$.ajax({
    		method: "GET",
    	    url: "/bajaAgenteVentasCatalogoComercial",
    	    data: {
    	    	idEmpleado:idEmpleado
    	    },
    	    success:(data) => {
    	    	if(data==1){
    	    		 Swal.fire({
    	    		        position: "center",
    	    		        icon: "success",
    	    		        title: "Agente dado de baja correctamente",
    	    		        showConfirmButton: false,
    	    		        timer: 2500,
    	    		        onClose:()=>{
    	    		        	ListAgentesVenta();
    	    		        }
    	    		      });
    	    	}
    	    	else{
    	    		 Swal.fire({
    	    		        position: "center",
    	    		        icon: "error",
    	    		        title: "No se pudo dar de baja",
    	    		        showConfirmButton: false,
    	    		        timer: 2500,
    	    		      });
    	    	}
    	    },
    	    error:(e) =>{
    		}
    	});
    }
  });
}

function altaAgente(id) {
  Swal.fire({
    title: "¿Deseas dar de alta al agente?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Confirmar",
    cancelButtonText: "Cancelar",
    confirmButtonColor: "#0288d1",
    cancelButtonColor: "#dc3545",
  }).then((result) => {
    if (result.value) {
    	var idEmpleado = id;
    	$.ajax({
    		method: "GET",
    	    url: "/altaAgenteVentasCatalogoComercial",
    	    data: {
    	    	idEmpleado:idEmpleado
    	    },
    	    success:(data) => {
    	    	if(data==1){
    	    		 Swal.fire({
    	    		        position: "center",
    	    		        icon: "success",
    	    		        title: "Agente dado de alta correctamente",
    	    		        showConfirmButton: false,
    	    		        timer: 2500,
    	    		        onClose:()=>{
    	    		        	ListAgentesVenta();
    	    		        }
    	    		      });
    	    	}
    	    	else{
    	    		 Swal.fire({
    	    		        position: "center",
    	    		        icon: "error",
    	    		        title: "No se pudo dar de alta",
    	    		        showConfirmButton: false,
    	    		        timer: 2500,
    	    		      });
    	    	}
    	    },
    	    error:(e) =>{
    		}
    	});
    }
  });
}

// Habilitar campos para Modelo
function listarModelos() {
  $.ajax({
    method: "GET",
    url: "/getModelos",
    data: {
      "tipoLookup": "Modelo"
    },
    success: (data) => {
      tableModelo.rows().remove().draw();
      for (i in data) {
        tableModelo.row.add(
          [
            data[i].idText,
            data[i].nombreLookup,
            data[i].atributo1,
            data[i].atributo2,
            '<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>ADMIN <br /><strong>Fecha de creación:</strong> 2020-05-12 00:00:00<br><strong>Modificado por:</strong>ADMIN<br><strong>Fecha de modicación:</strong>2020-05-22 16:41:42"><i class="fas fa-info"></i></button>' +
            '<button onclick="editarModelo(' + data[i].idLookup + ')" class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' +
            (data[i].estatus == 1 ? '<button onclick="bajarModelo(' + data[i].idLookup + ')" class="btn btn-danger btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>' : '') +
            (data[i].estatus == 0 ? '<button onclick="altaModelo(' + data[i].idLookup + ')" class="btn btn-success btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de alta"><i class="fas fa-caret-up"></i></button>' : '')
          ]
        ).draw();
      }

    }

  });

}

$("#detalleModelo").on("shown.bs.modal", function () {
  $(document).off("focusin.modal");
});
function agregarModelo() {
  Swal.fire({
    title: "Nuevo modelo",
    html: '<div class="row">' +
      '<div class="form-group col-md-4">' +
      '<label for="nombreModelo">Nombre</label>' +
      '<input type="text" class="form-control" id="nombreModelo" placeholder="Mariela">' +
      '</div>' +
      '<div class="form-group col-md-4">' +
      '<label for="telefonoModelo">Tel&eacute;fono</label>' +
      '<input type="text" class="form-control" id="telefonoModelo" placeholder="55 123 43 12">' +
      '</div>' +
      '<div class="form-group col-md-4">' +
      '<label for="precioModelo">Precio presentaci&oacute;n</label>' +
      '<input type="number" class="form-control" id="precioModelo" placeholder="10000">' +
      '</div>' +
      '</div>',
    showCancelButton: true,
    confirmButtonText: "Confirmar",
    cancelButtonText: "Cancelar",
    customClass: 'swal-modelo',
    confirmButtonColor: "#0288d1",
    cancelButtonColor: "#dc3545",
  }).then((result) => {
    var nombreModelo = $('#nombreModelo').val();
    var telefonoModelo = $('#telefonoModelo').val();
    var precioModelo = $('#precioModelo').val();
    const modelo = nombreModelo + "," + telefonoModelo + "," + precioModelo;
    // metodo post para guardar mi modelo
    $.ajax({
      type: "POST",
      url: "/postModelo",
      data: {
        "_csrf": $('#token').val(),
        "modelo": modelo
      },
      success: (data) => {
        if (data === 'Success') {
          $.ajax({
            method: "GET",
            url: "/getModelos",
            data: {
              "tipoLookup": "Modelo"
            },
            success: (data) => {
              tableModelo.rows().remove().draw();
              for (i in data) {
                tableModelo.row.add(
                  [
                    data[i].idText,
                    data[i].nombreLookup,
                    data[i].atributo1,
                    data[i].atributo2,
                    '<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>ADMIN <br /><strong>Fecha de creación:</strong> 2020-05-12 00:00:00<br><strong>Modificado por:</strong>ADMIN<br><strong>Fecha de modicación:</strong>2020-05-22 16:41:42"><i class="fas fa-info"></i></button>' +
                    '<button onclick="editarModelo(' + data[i].idLookup + ')" class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' +
                    (data[i].estatus == 1 ? '<button onclick="bajarModelo(' + data[i].idLookup + ')" class="btn btn-danger btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>' : '') +
                    (data[i].estatus == 0 ? '<button onclick="altaModelo(' + data[i].idLookup + ')" class="btn btn-success btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de alta"><i class="fas fa-caret-up"></i></button>' : '')
                  ]
                ).draw();
              }

            }

          });
          if (result.value) {
            Swal.fire({
              position: "center",
              icon: "success",
              title: "Modelo agregado correctamente",
              showConfirmButton: false,
              timer: 2500,
            });
          }
        }
        else {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: '¡Error de datos!',
          })
        }
      },
      error: function (data) {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: '¡Hay un problema en el servidor!',
        })
      }
    });


  });
}

function editarModelo(idLookup) {

  $.ajax({
    method: "GET",
    url: "/getModelo",
    data: {
      "id": idLookup
    },
    success: (data) => {
      Swal.fire({
        title: "Editar modelo",
        html: '<div class="row">' +
          '<div class="form-group col-md-4">' +
          '<label for="nombreModeloE">Nombre</label>' +
          '<input type="text" value="' + data.nombreLookup + '" class="form-control" id="nombreModeloE" placeholder="Mariela">' +
          '</div>' +
          '<div class="form-group col-md-4">' +
          '<label for="telefonoModeloE">Tel&eacute;fono</label>' +
          '<input type="text" class="form-control" value="' + data.atributo1 + '" id="telefonoModeloE" placeholder="55 123 43 12">' +
          '</div>' +
          '<div class="form-group col-md-4">' +
          '<label for="precioModeloE">Precio presentaci&oacute;n</label>' +
          '<input type="number" class="form-control" id="precioModeloE" value="' + data.atributo2 + '" placeholder="10000">' +
          '</div>' +
          '</div>',
        showCancelButton: true,
        confirmButtonText: "Confirmar",
        cancelButtonText: "Cancelar",
        confirmButtonColor: "#0288d1",
        cancelButtonColor: "#dc3545",
        customClass: 'swal-modelo',
      }).then((result) => {
        if (result.value) {
          var nombreModelo = $('#nombreModeloE').val();
          var telefonoModelo = $('#telefonoModeloE').val();
          var precioModelo = $('#precioModeloE').val();
          const modelo = idLookup + "," +nombreModelo + "," + telefonoModelo + "," + precioModelo;

          
          $.ajax({
            type: "PATCH",
            url: "/patchModelo",
            data: {
              "_csrf": $('#token').val(),
              "modelo": modelo
            },
            success: (data) => {
              if (data === 'Success') {
                $.ajax({
                  method: "GET",
                  url: "/getModelos",
                  data: {
                    "tipoLookup": "Modelo"
                  },
                  success: (data) => {
                    tableModelo.rows().remove().draw();
                    for (i in data) {
                      tableModelo.row.add(
                        [
                          data[i].idText,
                          data[i].nombreLookup,
                          data[i].atributo1,
                          data[i].atributo2,
                          '<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>ADMIN <br /><strong>Fecha de creación:</strong> 2020-05-12 00:00:00<br><strong>Modificado por:</strong>ADMIN<br><strong>Fecha de modicación:</strong>2020-05-22 16:41:42"><i class="fas fa-info"></i></button>' +
                          '<button onclick="editarModelo(' + data[i].idLookup + ')" class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' +
                          (data[i].estatus == 1 ? '<button onclick="bajarModelo(' + data[i].idLookup + ')" class="btn btn-danger btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>' : '') +
                          (data[i].estatus == 0 ? '<button onclick="altaModelo(' + data[i].idLookup + ')" class="btn btn-success btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de alta"><i class="fas fa-caret-up"></i></button>' : '')
                        ]
                      ).draw();
                    }

                  }

                });
                if (result.value) {
                  Swal.fire({
                    position: "center",
                    icon: "success",
                    title: "Modelo editado correctamente",
                    showConfirmButton: false,
                    timer: 2500,
                  });
                }
              }
              else {
                Swal.fire({
                  icon: 'error',
                  title: 'Error',
                  text: '¡Error de datos!',
                })
              }
            },
            error: function (data) {
              Swal.fire({
                icon: 'error',
                title: 'Error',
                text: '¡Hay un problema en el servidor!',
              })
            }
          });
        }
      });

    }

  });
}

function bajarModelo(idLookup) {
  // alert(idLookup);
  Swal.fire({
      title: "¿Deseas dar de baja al Modelo?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
  }).then((result) => {
      if (result.value) {

          $.ajax({
              method: "GET",
              url: "/bajarModelo",
              data: {
                  "_csrf": $('#token').val(),
                  "idLookup": idLookup
              },
              success: (data) => {

              },
              error: function(data) {
                  alert("Error en el servidor");
              }
          });

          Swal.fire({
              position: "center",
              icon: "success",
              title: "Modelo dado de baja correctamente",
              showConfirmButton: false,
              timer: 2500,
          });
          setTimeout(function() {
              location.reload();
          }, 2300);
      }
  });
}

function altaModelo(idLookup) {
  Swal.fire({
      title: "¿Deseas dar de alta al Modelo?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
  }).then((result) => {
      if (result.value) {

          $.ajax({
              type: "GET",
              url: "/altaModelo",
              data: {
                  "_csrf": $('#token').val(),
                  "idLookup": idLookup
              },
              success: (data) => {

              },
              error: function(data) {
                  alert("Error en el servidor")
              }
          });


          Swal.fire({
              position: "center",
              icon: "success",
              title: "Modelo dado de alta correctamente",
              showConfirmButton: false,
              timer: 2500,
          });
          setTimeout(function() {
              location.reload();
          }, 2300);
      }
  });
}



// Habilitar campos para Precio
function listarPrecios() {
  $.ajax({
      method: "GET",
      url: "/getPrecios",
      data: {
          "tipoLookup": "Personalizado"
      },
      success: (data) => {
          tablePrecios.rows().remove().draw();
          for (i in data) {
              tablePrecios.row.add(
                  [
                      data[i].idText,
                      data[i].nombreLookup,
                      data[i].atributo1,
                      '<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>ADMIN <br /><strong>Fecha de creación:</strong> 2020-05-12 00:00:00<br><strong>Modificado por:</strong>ADMIN<br><strong>Fecha de modicación:</strong>2020-05-22 16:41:42"><i class="fas fa-info"></i></button>' +
                      '<button onclick="editarPrecio(' + data[i].idLookup + ')" class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' +
                      (data[i].estatus == 1 ? '<button onclick="bajarPrecio(' + data[i].idLookup + ')" class="btn btn-danger btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>' : '') +
                      (data[i].estatus == 0 ? '<button onclick="altaPrecio(' + data[i].idLookup + ')" class="btn btn-success btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de alta"><i class="fas fa-caret-up"></i></button>' : '')
                  ]
              ).draw();
          }
      }
  });
}

$("#detallePrecio").on("shown.bs.modal", function() {
  $(document).off("focusin.modal");
});

function agregarPrecio() {
  Swal.fire({
      title: "Nuevo precio",
      html: '<div class="row">' +
          '<div class="form-group col-md-6">' +
          '<label for="descripcionPrecio">Descripci&oacute;n</label>' +
          '<input type="text" class="form-control" id="descripcionPrecio" placeholder="Especificar">' +
          '</div>' +
          '<div class="form-group col-md-6">' +
          '<label for="numeroPrecio">Precio</label>' +
          '<input type="number" class="form-control" id="numeroPrecio" placeholder="30">' +
          '</div>' +
          '</div>',
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
  }).then((result) => {
      var descripcionPrecio = $('#descripcionPrecio').val();
      var numeroPrecio = $('#numeroPrecio').val();
      const precio = descripcionPrecio + "," + numeroPrecio;
      //alert(precio.join());
      //alert(descripcionPrecio + "" + numeroPrecio);
      //metodo post para guardar precio
      $.ajax({
          type: "POST",
          url: "/postPrecio",
          data: {
              "_csrf": $('#token').val(),
              "precio": precio
          },
          success: (data) => {
              if (data === 'success') {
                  $.ajax({
                      method: "GET",
                      url: "/getPrecios",
                      data: {
                          "tipoLookup": "Personalizado"
                      },
                      success: (data) => {
                          tablePrecios.rows().remove().draw();
                          for (i in data) {
                              tablePrecios.row.add(
                                  [
                                      data[i].idText,
                                      data[i].nombreLookup,
                                      data[i].atributo1,
                                      '<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>ADMIN <br /><strong>Fecha de creación:</strong> 2020-05-12 00:00:00<br><strong>Modificado por:</strong>ADMIN<br><strong>Fecha de modicación:</strong>2020-05-22 16:41:42"><i class="fas fa-info"></i></button>' +
                                      '<button onclick="editarPrecio(' + data[i].idLookup + ')" class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' +
                                      (data[i].estatus == 1 ? '<button onclick="bajarPrecio(' + data[i].idLookup + ')" class="btn btn-danger btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>' : '') +
                                      (data[i].estatus == 0 ? '<button onclick="altaPrecio(' + data[i].idLookup + ')" class="btn btn-success btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de alta"><i class="fas fa-caret-up"></i></button>' : '')
                                  ]
                              ).draw();
                          }
                      }
                  });
                  if (result.value) {
                      swal.fire({
                          position: "center",
                          icon: "success",
                          title: "Precio de personalizado agregado correctamente",
                          showConfirmButton: false,
                          timer: 2500,
                      });
                  }

              } else {
                  swal.fire({
                      icon: 'error',
                      title: 'Error',
                      text: '¡Error de datos!'
                  })
              }
          },
          error: function(data) {
              swal.fire({
                  icon: 'error',
                  title: 'Error',
                  text: '¡Hay un problema en el servidor!',
              })
          }
      });
  });
}

function editarPrecio(idLookup) {

  $.ajax({
      method: "GET",
      url: "/getPrecio",
      data: {
          "id": idLookup
      },
      success: (data) => {
          Swal.fire({
              title: "Editar precio",
              html: '<div class="row">' +
                  '<div class="form-group col-md-6">' +
                  '<label for="descripcionPrecioE">Descripci&oacute;n</label>' +
                  '<input type="text" value="' + data.nombreLookup + '" class="form-control" id="descripcionPrecioE" placeholder="Especificar">' +
                  '</div>' +
                  '<div class="form-group col-md-6">' +
                  '<label for="numeroPrecioE">Precio</label>' +
                  '<input type="number" value="' + data.atributo1 + '" class="form-control" id="numeroPrecioE" placeholder="30">' +
                  '</div>' +
                  '</div>',
              showCancelButton: true,
              confirmButtonText: "Confirmar",
              cancelButtonText: "Cancelar",
              confirmButtonColor: "#0288d1",
              cancelButtonColor: "#dc3545",
          }).then((result) => {
              if (result.value) {
                  var descripcionPrecio = $('#descripcionPrecioE').val();
                  var numeroPrecio = $('#numeroPrecioE').val();
                  const precio = idLookup + "," + descripcionPrecio + "," + numeroPrecio;
                  //alert(precio);

                  $.ajax({
                      type: "PATCH",
                      url: "/patchPrecio",
                      data: {
                          "_csrf": $('#token').val(),
                          "precio": precio
                      },
                      success: (data) => {
                          if (data === 'success') {
                              $.ajax({
                                  method: "GET",
                                  url: "/getPrecios",
                                  data: {
                                      "tipoLookup": "Personalizado"
                                  },
                                  success: (data) => {
                                      tablePrecios.rows().remove().draw();
                                      for (i in data) {
                                          tablePrecios.row.add(
                                              [
                                                  data[i].idText,
                                                  data[i].nombreLookup,
                                                  data[i].atributo1,
                                                  '<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>ADMIN <br /><strong>Fecha de creación:</strong> 2020-05-12 00:00:00<br><strong>Modificado por:</strong>ADMIN<br><strong>Fecha de modicación:</strong>2020-05-22 16:41:42"><i class="fas fa-info"></i></button>' +
                                                  '<button onclick="editarPrecio(' + data[i].idLookup + ')" class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' +
                                                  (data[i].estatus == 1 ? '<button onclick="bajarPrecio(' + data[i].idLookup + ')" class="btn btn-danger btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>' : '') +
                                                  (data[i].estatus == 0 ? '<button onclick="altaPrecio(' + data[i].idLookup + ')" class="btn btn-success btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de alta"><i class="fas fa-caret-up"></i></button>' : '')
                                              ]
                                          ).draw();
                                      }
                                  }
                              });
                              if (result.value) {
                                  swal.fire({
                                      position: "center",
                                      icon: "success",
                                      title: "Precio de personalizado editado correctamente",
                                      showConfirmButton: false,
                                      timer: 2500,
                                  });
                              }

                          } else {
                              swal.fire({
                                  icon: 'error',
                                  title: 'Error',
                                  text: '¡Error de datos!'
                              })
                          }
                      },
                      error: function(data) {
                          swal.fire({
                              icon: 'error',
                              title: 'Error',
                              text: '¡Hay un problema en el servidor!',
                          })
                      }
                  });



              }
          });
      }
  });
}



function bajarPrecio(idLookup) {
  // alert(idLookup);
  Swal.fire({
      title: "¿Deseas dar de baja al precio?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
  }).then((result) => {
      if (result.value) {
          $.ajax({
              method: "GET",
              url: "/bajarPrecio",
              data: {
                  "_csrf": $('#token').val(),
                  "idLookup": idLookup
              },
              success: (data) => {

              },
              error: function(data) {
                  alert("Error en el servidor");
              }
          });
          Swal.fire({
              position: "center",
              icon: "success",
              title: "Precio dado de baja correctamente",
              showConfirmButton: false,
              timer: 2500,
          });
          setTimeout(function() {
              location.reload();
          }, 2300);
      }
  });
}

function altaPrecio(idLookup) {
  Swal.fire({
      title: "¿Deseas dar de alta al precio?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
  }).then((result) => {
      if (result.value) {
          $.ajax({
              type: "GET",
              url: "/altaPrecio",
              data: {
                  "csrf": $('#token').val(),
                  "idLookup": idLookup
              },
              success: (data) => {

              },
              error: function(data) {
                  alert("Error en el servidor")
              }
          });
          Swal.fire({
              position: "center",
              icon: "success",
              title: "Precio dado de alta correctamente",
              showConfirmButton: false,
              timer: 2500,
          });
          setTimeout(function() {
              location.reload();
          }, 2300);
      }
  });
}

// Habilitar campos para IVA
function listarIvas() {
  $.ajax({
      method: "GET",
      url: "/getIvas",
      data: {
          "tipoLookup": "Iva"
      },
      success: (data) => {
          tableIvas.rows().remove().draw();
          for (i in data) {
              tableIvas.row.add(
                  [
                      data[i].idText,
                      data[i].atributo1,
                      '<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>ADMIN <br /><strong>Fecha de creación:</strong> 2020-05-12 00:00:00<br><strong>Modificado por:</strong>ADMIN<br><strong>Fecha de modicación:</strong>2020-05-22 16:41:42"><i class="fas fa-info"></i></button>' +
                      '<button onclick="editarIVA(' + data[i].idLookup + ')" class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' +
                      (data[i].estatus == 1 ? '<button onclick="bajarIVA(' + data[i].idLookup + ')" class="btn btn-danger btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>' : '') +
                      (data[i].estatus == 0 ? '<button onclick="altaIVA(' + data[i].idLookup + ')" class="btn btn-success btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de alta"><i class="fas fa-caret-up"></i></button>' : '')
                  ]
              ).draw();
          }
      }
  });
}
$("#detalleIVA").on("shown.bs.modal", function() {
  $(document).off("focusin.modal");
});

function agregarIVA() {
  Swal.fire({
      title: "Nuevo IVA",
      html: '<div class="row">' +
          '<div class="form-group col-md-12">' +
          '<label for="numeroPorcentaje">Porcentaje</label>' +
          '<input type="number" class="form-control" id="numeroPorcentaje" placeholder="16">' +
          '</div>' +
          '</div>',
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
  }).then((result) => {

      var numeroPorcentaje = $('#numeroPorcentaje').val();
      const iva = numeroPorcentaje;
      //alert(precio.join());
      //alert(numeroPorcentaje);
      //metodo post para guardar iva
      $.ajax({
          type: "POST",
          url: "/postIva",
          data: {
              "_csrf": $('#token').val(),
              "iva": iva
          },
          success: (data) => {
              if (data === 'success') {
                  $.ajax({
                      method: "GET",
                      url: "/getIvas",
                      data: {
                          "tipoLookup": "Iva"
                      },
                      success: (data) => {
                          tableIvas.rows().remove().draw();
                          for (i in data) {
                              tableIvas.row.add(
                                  [
                                      data[i].idText,
                                      data[i].atributo1,
                                      '<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>ADMIN <br /><strong>Fecha de creación:</strong> 2020-05-12 00:00:00<br><strong>Modificado por:</strong>ADMIN<br><strong>Fecha de modicación:</strong>2020-05-22 16:41:42"><i class="fas fa-info"></i></button>' +
                                      '<button onclick="editarIVA(' + data[i].idLookup + ')" class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' +
                                      (data[i].estatus == 1 ? '<button onclick="bajarIVA(' + data[i].idLookup + ')" class="btn btn-danger btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>' : '') +
                                      (data[i].estatus == 0 ? '<button onclick="altaIVA(' + data[i].idLookup + ')" class="btn btn-success btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de alta"><i class="fas fa-caret-up"></i></button>' : '')
                                  ]
                              ).draw();
                          }
                      }
                  });


                  if (result.value) {
                      swal.fire({
                          position: "center",
                          icon: "success",
                          title: "Precio de iva agregado correctamente",
                          showConfirmButton: false,
                          timer: 2500,
                      });
                  }
              } else {
                  swal.fire({
                      icon: 'error',
                      title: 'Error',
                      text: '¡Error de datos!',
                  })
              }
          },
          error: function(data) {
              swal.fire({
                  icon: 'error',
                  title: 'Error',
                  text: '¡Hay un problema en el servidor!',
              })
          }
      });
  });
}

function editarIVA(idLookup) {
  $.ajax({
      method: "GET",
      url: "/getIva",
      data: {
          "id": idLookup
      },
      success: (data) => {
          Swal.fire({
              title: "Editar IVA",
              html: '<div class="row">' +
                  '<div class="form-group col-md-12">' +
                  '<label for="numeroPorcentajeE">Porcentaje</label>' +
                  '<input type="number" value="' + data.atributo1 + '" class="form-control" id="numeroPorcentajeE" placeholder="16">' +
                  '</div>' +
                  '</div>',
              showCancelButton: true,
              confirmButtonText: "Confirmar",
              cancelButtonText: "Cancelar",
              confirmButtonColor: "#0288d1",
              cancelButtonColor: "#dc3545",
          }).then((result) => {
              if (result.value) {
                  var numeroPorcentaje = $('#numeroPorcentajeE').val();
                  const iva = idLookup + "," + numeroPorcentaje;
                  //alert(iva);

                  $.ajax({
                      type: "PATCH",
                      url: "/patchIva",
                      data: {
                          "_csrf": $('#token').val(),
                          "iva": iva
                      },
                      success: (data) => {
                          if (data === 'success') {
                              $.ajax({
                                  method: "GET",
                                  url: "/getIvas",
                                  data: {
                                      "tipoLookup": "Iva"
                                  },
                                  success: (data) => {
                                      tableIvas.rows().remove().draw();
                                      for (i in data) {
                                          tableIvas.row.add(
                                              [
                                                  data[i].idText,
                                                  data[i].atributo1,
                                                  '<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>ADMIN <br /><strong>Fecha de creación:</strong> 2020-05-12 00:00:00<br><strong>Modificado por:</strong>ADMIN<br><strong>Fecha de modicación:</strong>2020-05-22 16:41:42"><i class="fas fa-info"></i></button>' +
                                                  '<button onclick="editarIVA(' + data[i].idLookup + ')" class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' +
                                                  (data[i].estatus == 1 ? '<button onclick="bajarIVA(' + data[i].idLookup + ')" class="btn btn-danger btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>' : '') +
                                                  (data[i].estatus == 0 ? '<button onclick="altaIVA(' + data[i].idLookup + ')" class="btn btn-success btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de alta"><i class="fas fa-caret-up"></i></button>' : '')
                                              ]
                                          ).draw();
                                      }
                                  }
                              });


                              if (result.value) {
                                  swal.fire({
                                      position: "center",
                                      icon: "success",
                                      title: "Precio de iva editado correctamente",
                                      showConfirmButton: false,
                                      timer: 2500,
                                  });
                              }
                          } else {
                              swal.fire({
                                  icon: 'error',
                                  title: 'Error',
                                  text: '¡Error de datos!',
                              })
                          }
                      },
                      error: function(data) {
                          swal.fire({
                              icon: 'error',
                              title: 'Error',
                              text: '¡Hay un problema en el servidor!',
                          })
                      }
                  });




              }
          });

      }

  });
}

function bajarIVA(idLookup) {
  // alert(idLookup);
  Swal.fire({
      title: "¿Deseas dar de baja al IVA?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
  }).then((result) => {
      if (result.value) {

          $.ajax({
              method: "GET",
              url: "/bajarIVA",
              data: {
                  "_csrf": $('#token').val(),
                  "idLookup": idLookup
              },
              success: (data) => {

              },
              error: function(data) {
                  alert("Error en el servidor");
              }
          });

          Swal.fire({
              position: "center",
              icon: "success",
              title: "IVA dado de baja correctamente",
              showConfirmButton: false,
              timer: 2500,
          });
          setTimeout(function() {
              location.reload();
          }, 2300);
      }
  });
}

function altaIVA(idLookup) {
  Swal.fire({
      title: "¿Deseas dar de alta al IVA?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
  }).then((result) => {
      if (result.value) {

          $.ajax({
              type: "GET",
              url: "/altaIVA",
              data: {
                  "_csrf": $('#token').val(),
                  "idLookup": idLookup
              },
              success: (data) => {

              },
              error: function(data) {
                  alert("Error en el servidor")
              }
          });


          Swal.fire({
              position: "center",
              icon: "success",
              title: "IVA dado de alta correctamente",
              showConfirmButton: false,
              timer: 2500,
          });
          setTimeout(function() {
              location.reload();
          }, 2300);
      }
  });
}



function listarTickets() {
    $.ajax({
        method: "GET",
        url: "/getTickets",
        data: {
            "tipoLookup": "Ticket"
        },
        success: (data) => {
            tableTicket.rows().remove().draw();
            for (i in data) {
                tableTicket.row.add(
                    [
                        data[i].idText,
                        data[i].nombreLookup,
                        (data[i].atributo1==1?"Si":"No"),
                        (data[i].atributo2==1?"Si":"No"),
                        '<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>ADMIN <br /><strong>Fecha de creación:</strong> 2020-05-12 00:00:00<br><strong>Modificado por:</strong>ADMIN<br><strong>Fecha de modicación:</strong>2020-05-22 16:41:42"><i class="fas fa-info"></i></button>' +
                        '<button onclick="editarTicket(' + data[i].idLookup + ')" class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' +
                        (data[i].estatus == 1 ? '<button onclick="bajarTicket(' + data[i].idLookup + ')" class="btn btn-danger btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>' : ' ') +
                        (data[i].estatus == 0 ? '<button onclick="altaTicket(' + data[i].idLookup + ')" class="btn btn-success btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de alta"><i class="fas fa-caret-up"></i></button>' : ' ')
                    ]
                ).draw();
            }

        }

    });
}

$("#detalleTicket").on("shown.bs.modal", function() {
    $(document).off("focusin.modal");
});

function agregarTicket() {
    Swal.fire({
        title: "Nuevo ticket",
        html: '<div class="row">' +
            '<div class="form-group col-md-12">' +
            '<label for="descripcionTicket">Descripci&oacute;n</label>' +
            '<input type="text" placeholder="Especificar" class="form-control" id="descripcionTicket">' +
            '</div>' +
            '<div class="form-group col-md-12">' +
            '<h4>¿Qui&eacute;n puede agregar?</h4>' +
            '</div>' +
            '<div class="form-group col-md-6">' +
            '<div class="form-check">' +
            '<input class="form-check-input" type="checkbox" value="0" id="auxiliarTicket">' +
            '<label class="form-check-label" for="auxiliarTicket">' +
            'Auxiliar' +
            '</label>' +
            '</div>' +
            '</div>' +
            '<div class="form-group col-md-6">' +
            '<div class="form-check">' +
            '<input class="form-check-input" type="checkbox" value="0" id="solicitanteTicket">' +
            '<label class="form-check-label" for="solicitanteTicket">' +
            'Solicitante' +
            '</label>' +
            '</div>' +
            '</div>' +
            '</div>',
        showCancelButton: true,
        confirmButtonText: "Confirmar",
        cancelButtonText: "Cancelar",
        confirmButtonColor: "#0288d1",
        cancelButtonColor: "#dc3545",
    }).then((result) => {
    	if (result.value) {
	        var descripcionTicket = $('#descripcionTicket').val();
	        var auxiliarTicket = $('input:checkbox[id=auxiliarTicket]:checked').val();
	        var solicitanteTicket = $('input:checkbox[id=solicitanteTicket]:checked').val();
	        const ticket = descripcionTicket + "," + auxiliarTicket + "," + solicitanteTicket;
	        //alert(auxiliarTicket + " " + solicitanteTicket);
	        //metodo post para guardar mi ticket
	        $.ajax({
	            type: "POST",
	            url: "/postTicket",
	            data: {
	                "_csrf": $('#token').val(),
	                "ticket": ticket,
	                auxiliarTicket: auxiliarTicket,
	                solicitanteTicket: solicitanteTicket
	            }, 
	            success: (data) => {
	                if (data === 'Success') {
	                    $.ajax({
	                        method: "GET",
	                        url: "/getTickets",
	                        data: {
	                            "tipoLookup": "Ticket"
	                        },
	                        success: (data) => {
	                            tableTicket.rows().remove().draw();
	                            for (i in data) {
	                                tableTicket.row.add(
	                                    [
	                                        data[i].idText,
	                                        data[i].nombreLookup,
	                                        (data[i].atributo1==1?"Si":"No"),
	                                        (data[i].atributo2==1?"Si":"No"),
	                                        '<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>ADMIN <br /><strong>Fecha de creación:</strong> 2020-05-12 00:00:00<br><strong>Modificado por:</strong>ADMIN<br><strong>Fecha de modicación:</strong>2020-05-22 16:41:42"><i class="fas fa-info"></i></button>' +
	                                        '<button onclick="editarTicket(' + data[i].idLookup + ')" class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' +
	                                        (data[i].estatus == 1 ? '<button onclick="bajarTicket(' + data[i].idLookup + ')" class="btn btn-danger btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>' : ' ') +
	                                        (data[i].estatus == 0 ? '<button onclick="altaTicket(' + data[i].idLookup + ')" class="btn btn-success btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de alta"><i class="fas fa-caret-up"></i></button>' : ' ')
	                                    ]
	                                ).draw();
	                            }
	
	                        }
	
	                    });
	                    if (result.value) {
	                        Swal.fire({
	                            position: "center",
	                            icon: "success",
	                            title: "Ticket agregado correctamente",
	                            showConfirmButton: false,
	                            timer: 2500,
	                        });
	                    }
	                } else {
	                    Swal.fire({
	                        icon: 'error',
	                        title: 'Error',
	                        text: '¡Error de datos!',
	                    })
	                }
	            },
	            error: function(data) {
	                Swal.fire({
	                    icon: 'error',
	                    title: 'Error',
	                    text: '¡Hay un problema en el servidor!',
	                })
	            }
	        });

    	}
    });

}

function editarTicket(idLookup) {
    //alert(idLookup);
    $.ajax({
        method: "GET",
        url: "/getTicket",
        data: {
            "id": idLookup
        },
        success: (data) => {
            Swal.fire({
                title: "Editar ticket",
                html: '<div class="row">' +
                    '<div class="form-group col-md-12">' +
                    '<label for="descripcionTicketE">Descripci&oacute;n</label>' +
                    '<input type="text" value="' + data.nombreLookup + '" placeholder="Especificar" class="form-control" id="descripcionTicketE">' +
                    '</div>' +
                    '<div class="form-group col-md-12">' +
                    '<h4>¿Qui&eacute;n puede agregar?</h4>' +
                    '</div>' +
                    '<div class="form-group col-md-6">' +
                    '<div class="form-check">' +
                    '<input class="form-check-input" type="checkbox" value="0" id="auxiliarTicketE">' +
                    '<label class="form-check-label" for="auxiliarTicketE">' +
                    'Auxiliar' +
                    '</label>' +
                    '</div>' +
                    '</div>' +
                    '<div class="form-group col-md-6">' +
                    '<div class="form-check">' +
                    '<input class="form-check-input" type="checkbox" value="0" id="solicitanteTicketE">' +
                    '<label class="form-check-label" for="solicitanteTicketE">' +
                    'Solicitante' +
                    '</label>' +
                    '</div>' +
                    '</div>' +
                    '</div>',
                showCancelButton: true,
                confirmButtonText: "Confirmar",
                cancelButtonText: "Cancelar",
                confirmButtonColor: "#0288d1",
                cancelButtonColor: "#dc3545",
            }).then((result) => {
                if (result.value) {
                    var descripcionTicket = $('#descripcionTicketE').val();
                    var auxiliarTicket = $('input:checkbox[id=auxiliarTicketE]:checked').val();
                    var solicitanteTicket = $('input:checkbox[id=solicitanteTicketE]:checked').val();
                    const ticket = idLookup + "," + descripcionTicket + "," + auxiliarTicket + "," + solicitanteTicket;
                    //alert(ticket);
                    $.ajax({
                        type: "PATCH",
                        url: "/patchTicket",
                        data: {
                            "_csrf": $('#token').val(),
                            "ticket": ticket,
                            auxiliarTicket: auxiliarTicket,
                            solicitanteTicket: solicitanteTicket
                        },
                        success: (data) => {
                            if (data === 'Success') {
                                $.ajax({
                                    method: "GET",
                                    url: "/getTickets",
                                    data: {
                                        "tipoLookup": "Ticket"
                                    },
                                    success: (data) => {
                                        tableTicket.rows().remove().draw();
                                        for (i in data) {

                                            tableTicket.row.add(
                                                [
                                                    data[i].idText,
                                                    data[i].nombreLookup,
                                                    (data[i].atributo1==1?"Si":"No"),
                                                    (data[i].atributo2==1?"Si":"No"),
                                                    '<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>ADMIN <br /><strong>Fecha de creación:</strong> 2020-05-12 00:00:00<br><strong>Modificado por:</strong>ADMIN<br><strong>Fecha de modicación:</strong>2020-05-22 16:41:42"><i class="fas fa-info"></i></button>' +
                                                    '<button onclick="editarTicket(' + data[i].idLookup + ')" class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' +
                                                    (data[i].estatus == 1 ? '<button onclick="bajarTicket(' + data[i].idLookup + ')" class="btn btn-danger btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>' : ' ') +
                                                    (data[i].estatus == 0 ? '<button onclick="altaTicket(' + data[i].idLookup + ')" class="btn btn-success btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de alta"><i class="fas fa-caret-up"></i></button>' : ' ')
                                                ]
                                            ).draw();
                                        }

                                    }

                                });
                                if (result.value) {
                                    Swal.fire({
                                        position: "center",
                                        icon: "success",
                                        title: "Ticket editado correctamente",
                                        showConfirmButton: false,
                                        timer: 2500,
                                    });
                                }
                            } else {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: '¡Error de datos!',
                                })
                            }
                        },
                        error: function(data) {
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: '¡Hay un problema en el servidor!',
                            })
                        }
                    });
                }
            });

        }

    });

}

function bajarTicket(idLookup) {
    //alert(idLookup);
    Swal.fire({
        title: "¿Deseas dar de baja al ticket?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Confirmar",
        cancelButtonText: "Cancelar",
        confirmButtonColor: "#0288d1",
        cancelButtonColor: "#dc3545",
    }).then((result) => {
        if (result.value) {
            $.ajax({
                method: "GET",
                url: "/bajarTicket",
                data: {
                    "_csrf": $('#token').val(),
                    "idLookup": idLookup
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
	                	$.ajax({
	                        method: "GET",
	                        url: "/getTickets",
	                        data: {
	                            "tipoLookup": "Ticket"
	                        },
	                        success: (data) => {
	                            tableTicket.rows().remove().draw();
	                            for (i in data) {
	
	                                tableTicket.row.add(
	                                    [
	                                        data[i].idText,
	                                        data[i].nombreLookup,
	                                        (data[i].atributo1==1?"Si":"No"),
	                                        (data[i].atributo2==1?"Si":"No"),
	                                        '<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>ADMIN <br /><strong>Fecha de creación:</strong> 2020-05-12 00:00:00<br><strong>Modificado por:</strong>ADMIN<br><strong>Fecha de modicación:</strong>2020-05-22 16:41:42"><i class="fas fa-info"></i></button>' +
	                                        '<button onclick="editarTicket(' + data[i].idLookup + ')" class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' +
	                                        (data[i].estatus == 1 ? '<button onclick="bajarTicket(' + data[i].idLookup + ')" class="btn btn-danger btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>' : ' ') +
	                                        (data[i].estatus == 0 ? '<button onclick="altaTicket(' + data[i].idLookup + ')" class="btn btn-success btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de alta"><i class="fas fa-caret-up"></i></button>' : ' ')
	                                    ]
	                                ).draw();
	                            }
	                            if (result.value) {
	                                Swal.fire({
	                                    position: "center",
	                                    icon: "success",
	                                    title: "Ticket dado de baja correctamente",
	                                    showConfirmButton: false,
	                                    timer: 2500,
	                                });
	                            }
	                        }
	                    });
                	
                },
                error: function(data) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: '¡Hay un problema en el servidor!',
                    })
                }
          });
        }
    });
}

function altaTicket(idLookup) {
    Swal.fire({
        title: "¿Deseas dar de alta al ticket?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Confirmar",
        cancelButtonText: "Cancelar",
        confirmButtonColor: "#0288d1",
        cancelButtonColor: "#dc3545",
    }).then((result) => {
        if (result.value) {
            $.ajax({
                method: "GET",
                url: "/altaTicket",
                data: {
                    "_csrf": $('#token').val(),
                    "idLookup": idLookup
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
	                	$.ajax({
	                        method: "GET",
	                        url: "/getTickets",
	                        data: {
	                            "tipoLookup": "Ticket"
	                        },
	                        success: (data) => {
	                        	
	                            tableTicket.rows().remove().draw();
	                            for (i in data) {
	
	                                tableTicket.row.add(
	                                    [
	                                        data[i].idText,
	                                        data[i].nombreLookup,
	                                        (data[i].atributo1==1?"Si":"No"),
	                                        (data[i].atributo2==1?"Si":"No"),
	                                        '<button class="btn btn-info btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content="<strong>Creado por: </strong>ADMIN <br /><strong>Fecha de creación:</strong> 2020-05-12 00:00:00<br><strong>Modificado por:</strong>ADMIN<br><strong>Fecha de modicación:</strong>2020-05-22 16:41:42"><i class="fas fa-info"></i></button>' +
	                                        '<button onclick="editarTicket(' + data[i].idLookup + ')" class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Editar"><i class="fas fa-pen"></i></button>' +
	                                        (data[i].estatus == 1 ? '<button onclick="bajarTicket(' + data[i].idLookup + ')" class="btn btn-danger btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de baja"><i class="fas fa-caret-down"></i></button>' : ' ') +
	                                        (data[i].estatus == 0 ? '<button onclick="altaTicket(' + data[i].idLookup + ')" class="btn btn-success btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Dar de alta"><i class="fas fa-caret-up"></i></button>' : ' ')
	                                    ]
	                                ).draw();
	                            }
	
	                            if (result.value) {
	                                Swal.fire({
	                                    position: "center",
	                                    icon: "success",
	                                    title: "Ticket dado de alta correctamente",
	                                    showConfirmButton: false,
	                                    timer: 2500,
	                                });
	                            }
	                        }
	
	                    });
                },
                error: function(data) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: '¡Hay un problema en el servidor!',
                    })
                    alert("Error de servidor");
                }
        });
        }
    });
}
