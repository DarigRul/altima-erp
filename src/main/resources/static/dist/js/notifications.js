function icononotificacion() {
			$.ajax({
				type : "GET",
				url : "/countnotifications"
			})
			.done(
					function(data) {
						if (data > 0) {
							document.getElementById('navNotDis').innerHTML += "<span class='badge badge-pill badge-danger'>"
									+ data + "</span>";
						}
					});
}

$(document).ready(function() {
	icononotificacion();

});

function notificacionesdisenio(id) {
	$.ajax({
		type : "POST",
		url : "/viewnotification",
		data : {
			"_csrf" : $('#token').val(),
			'id' : id
		}

	}).done(function(data) {
		if (data == true) {
			icononotificacion();
		} else {
			icononotificacion();
		}
	});

}