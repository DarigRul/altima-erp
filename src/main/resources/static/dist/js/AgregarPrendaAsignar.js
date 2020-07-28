
	function AsignarID()
	{
		//Solicitud Ajax
	    $.ajax({
	        type: "GET",
	        url: "/asignar_id",
	        data: {
	        	"_csrf": $('#token').val(),
	        	"id" : message.idPrenda
	        },
	        success: (data) => {
	        	console.log(data);        	
			},
			failure: function(errMsg) {
		        alert(errMsg);
		    }
		});
	}	
