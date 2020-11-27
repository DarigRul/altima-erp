/**
 * Autor: Victor Hugo Garcia Ilhuicatzi
 */

function listarClientes(){
	if(listaClientes[0].idCliente!=undefined){
		
	
	for(i in listaClientes){
		
		$('#idCliente').append("<option value="+listaClientes[i].idCliente+">"+listaClientes[i].nombre+" "+
								(listaClientes[i].apellidoPaterno==null?"":listaClientes[i].apellidoPaterno)+" "+
								(listaClientes[i].apellidoPaterno==null?"":listaClientes[i].apellidoPaterno)+"</option");
	}
	$('.selectpicker').selectpicker("refresh");
	
	}
	
	else{
		for(i in listaClientes){
			
			$('#idCliente').append("<option value="+listaClientes[i][0]+">"+listaClientes[i][1]+"</option");
		}
	}
}
	

function guardarPreapartado (){
	var idCliente = $('#idCliente').val();
	var numPersonas = $('#numPersonas').val();
	console.log(idCliente);
	location.href = "confirmacion-pre-apartado/h58fhgkt673GSRF"+idCliente+"GH63GS63dd"+numPersonas+"gresdr2";
	
}

function guardarEstatusPedidoPreapartado(){
	var idPreapartado = $('#idPreapartado').val();
	var estatusPedido = $('#estatusPedido').val();
	var refPedido = $('#refPedido').val();
	location.href = "confirmacion-estatus-pre-apartado/h58fhg"+idPreapartado+"kt673GSRF"+estatusPedido+"GH63GS63dd"+refPedido+"gresdr2";
	
}

function rechazarPedidoPreapartado(idPreapartado){
	
	Swal.fire({
		  title: '¿Desea rechazar el pre-apartado?',
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
			          title: '¡Pre-apartado autorizado!',
			          showConfirmButton: false,
			          timer: 1550,
				      onClose: () => {
				    	  var estatusValue = 2;
						  location.href = "confirmacion-estatus-pre-apartado/h58fhg"+idPreapartado+"kt673GSRFGH63GS63dd"+estatusValue+"gresdr2";
				      }
				})
				
			}
		});
}

function autorizarPedidoPreapartado(idPreapartado){

	Swal.fire({
		  title: '¿Desea autorizar el pre-apartado?',
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
			          title: '¡Pre-apartado autorizado!',
			          showConfirmButton: false,
			          timer: 1550,
				      onClose: () => {
				    	  var estatusValue = 1;
						  location.href = "confirmacion-estatus-pre-apartado/h58fhg"+idPreapartado+"kt673GSRFGH63GS63dd"+estatusValue+"gresdr2";
				      }
				})
				
			}
		});
	
}

function enviarPreapartado(){
	Swal.fire({
	      position: 'center',
        icon: 'success',
        title: '¡Pre-apartado enviado correctamente!',
        showConfirmButton: false,
        timer: 2000,
	      onClose: () => {
	    	  
	      }
	})
	
}

function agregarPrendaCoordinado(idCoordinado){
	var idTela = $('#telaCotizacion').val();
	var idPrenda = $('#modeloCotizacion').val();
	var idFamPrenda = $('#prendaCotizacion').val();
	
	location.href ="/nueva-prenda-coordinado/"+idCoordinado+"FEDrf3"+idTela+"5edcs3"+idPrenda+"fsc5FS3sd"+idFamPrenda;
	
}

function idPreapartadoB(idPreapartado){
	
	$('#idPreapartado').val(idPreapartado);
}

function coordinados(idPreapartado){
	location.href = "/Coordinados-pre-apartado/"+idPreapartado;
}


$('#estatusPedido').on('change', function(){
	
	if($(this).val()==2){
		$('.perdidoHide').hide();
		$('#refPedido').val("");
	}
	else{
		$('.perdidoHide').show();
	}
	
})


$('#prendaCotizacion').on('change', function () {
	  var idPrenda = $('#prendaCotizacion').val();
	  console.log(idPrenda);
	  
	  $.ajax({
		  method: "GET",
		    url: "/ExtraerTelas",
		    data: {idFamPrenda : idPrenda},
		    beforeSend: function () {
		    	$('#modeloCotizacion').prop("disabled", true);
          },
		    success: function (r) {
		    	$('#modeloCotizacion').prop("disabled", false);

              // Limpiamos el select
		      $("#telaCotizacion").find("option").remove();
              $("#telaCotizacion").selectpicker("refresh");
              $(r).each(function (i, v) {
                  // indice, valor
              	$("#telaCotizacion").append('<option value="' + v[0] + '">' + v[1] + "</option>");
              });

              $("#telaCotizacion").prop("disabled", false);
              $("#telaCotizacion").selectpicker("refresh");
          },
          error: function () {
              alert("Ocurrio un error en el servidor de modelo ..");
              $('#modeloCotizacion').prop("disabled", false);
          },
	  });
	  
	  $.ajax({
		  method: "GET",
		    url: "/ExtraerModelos",
		    data: {idFamPrenda : idPrenda},
		    beforeSend: function () {
		    	$('#prendaCotizacion').prop("disabled", true);
          },
		    success: function (r) {
              
		    	$('#prendaCotizacion').prop("disabled", false);

              // Limpiamos el select
		    	$("#modeloCotizacion").find("option").remove();
              $(".selectpicker").selectpicker("refresh");
              $(r).each(function (i, v) {
                  // indice, valor
              	$("#modeloCotizacion").append('<option value="' + v[0] + '">' + v[1] + "</option>");
              });

              $("#modeloCotizacion").prop("disabled", false);
              $("#modeloCotizacion").selectpicker("refresh");
          },
          error: function () {
              alert("Ocurrio un error en el servidor de modelo ..");
              $('#prendaCotizacion').prop("disabled", false);
          },
	  });
	  
	 
})
