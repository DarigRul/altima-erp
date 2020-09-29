function listarCoordinados(id, aux) {
    $.ajax({
        method: "GET",
        url: "/listar-coordinado",
        data: {
            "_csrf": $('#token').val(),
            "idEmpleado": id
        },
        success: (data) => {
            $('#Coordinado').empty();
            $('#Coordinado').selectpicker('refresh');
           // $('#departamento').empty();
            //$('#departamento').selectpicker('refresh');
            for (var key in data) {
                $('#Coordinado').append('<option value="' + data[key][0] + '">' + data[key][1] + '</option>')
            }
            $('#Coordinado').val(aux);
            $('#Coordinado').selectpicker('refresh');
        },
        error: (e) => {
          //  location.reload();
        }
    });
    //listarModelo
}

function listarModelo(id, aux) {
	
	var idEmpleado = $('#idEmpleado').val();
    $.ajax({
        method: "GET",
        url: "/listar-modelo",
        data: {
            "_csrf": $('#token').val(),
            "idCoor": id,
            "idEmpleado":idEmpleado
        },
        success: (data) => {
            $('#modelo-consentrado').empty();
            $('#modelo-consentrado').selectpicker('refresh');
           // $('#departamento').empty();
            //$('#departamento').selectpicker('refresh');
            for (var key in data) {
                $('#modelo-consentrado').append('<option value="' + data[key][0] + '">' + data[key][1] + '</option>')
            }
            $('#modelo-consentrado').val(aux);
            $('#modelo-consentrado').selectpicker('refresh');
        },
        error: (e) => {
            location.reload();
        }
    });
    //listarModelo

}


function listarCambio(id, aux) {
	var idPrincipal = $('#idPrincipal').val();
    $.ajax({
        method: "GET",
        url: "/listar-cambios-posibles",
        data: {
            "_csrf": $('#token').val(),
            "modelo": id,
            "idPrincipal":idPrincipal
        },
        success: (data) => {
            $('#cambio').empty();
            $('#cambio').selectpicker('refresh');
           // $('#departamento').empty();
            //$('#departamento').selectpicker('refresh');
            for (var key in data) {
                $('#cambio').append('<option value="' + data[key][0] + '">' + data[key][1] + '</option>')
            }
            $('#cambio').val(aux);
            $('#cambio').selectpicker('refresh');
        },
        error: (e) => {
            location.reload();
        }
    });
}
function agregar(){
	if ( document.getElementById("idEmpleado").value && 
			document.getElementById("Coordinado").value
			&& 
			document.getElementById("modelo-consentrado").value
			&& 
			document.getElementById("cambio").value){
		
		
		$.ajax({
	        type: "POST",
	        url:"/guardar-cambio-spf",
	        data: { 
	        	"idConsentrado":$('#modelo-consentrado').val(),
	        	"cambio":$('#cambio').val(),
	             "_csrf": $('#token').val()
	        },
	        beforeSend: function () {
	        	
	        	 //document.getElementById("btn-estatus").disabled=true;
	        },
	    
	        success: function(data) {
	        	
	        	
	        	 Swal.fire({
	        		 position: 'center',
	     				icon: 'success',
	     				title: 'Guardado correctamente',
	     				showConfirmButton: false,
						timer: 1250
	                 
	             });
	       },
	       complete: function() {
	    	   //document.getElementById("btn-estatus").disabled=false;
	    	   //listar(idTicket2)
	    	   location.reload();
			
		    },
	    })
	}
	else{
		Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Datos incompletos',
            showConfirmButton: false,
            timer: 1250
          })   
	}

}