
  $( document ).ready(function() {
	    console.log( "ready!" );
	});
  
function notificacionesdisenio(id){
	console.log("carloseliosa");
	$.ajax({
        type: "POST",
        url: "/viewnotification",
        data: {
            "_csrf": $('#token').val(),
            'id': id

        }

    }).done(function(data) {
    	if (data==true) {
			console.log("true");
		} else {
console.log("false");
		}
    	console.log(data);
    	console.log("adios");
    	Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Insertado correctamente',
            showConfirmButton: false,
            timer: 1250
        })
    });
	
}