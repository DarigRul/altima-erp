function listarLinea(id, aux) {
    $.ajax({
        method: "GET",
           url: "/listar-linea",
           data: {
               "_csrf": $('#token').val(),
               "id": id
           },
           success: (data) => {
        	   $('#idLinea').empty();
        	   $('#idLinea').selectpicker('refresh');
        	   $('#idLinea').empty();
        	   $('#idLinea').selectpicker('refresh');
              for(var key in data) {
            	  $('#idLinea').append('<option value="'+data[key][0]+'">'+data[key][1]+'</option>')
              }
              $('#idLinea').val(aux);
              $('#idLinea').selectpicker('refresh');
           },
           error: (e) => {
                location.reload();
           }
       });
} 

function valida_envia(){
   	if (document.fvalida.idClasificacion.value.length==0){
   		Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Seleccione una clasificacion',
            showConfirmButton: false,
            timer: 1250
          }) 
      		//document.fvalida.nombre.focus()
      		return 0;
   	}
   	
   	if (document.fvalida.idLinea.value.length==0){
   		Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Seleccione una linea',
            showConfirmButton: false,
            timer: 1250
          }) 
      		//document.fvalida.nombre.focus()
      		return 0;
   	}
   	
   	if (document.fvalida.articulo.value.length==0){
   		Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Ingrese un el articulo',
            showConfirmButton: false,
            timer: 1250
          }) 
      		//document.fvalida.nombre.focus()
      		return 0;
   	}
   	
   	if (document.fvalida.descripcionInventario.value.length==0){
   		Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Ingrese una descripcion',
            showConfirmButton: false,
            timer: 1250
          }) 
      		//document.fvalida.nombre.focus()
      		return 0;
   	}
   	
   
   	if (document.fvalida.idUnidadMedida.value.length==0){
   		Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Seleccione una unidad de medida',
            showConfirmButton: false,
            timer: 1250
          }) 
      		//document.fvalida.nombre.focus()
      		return 0;
   	}
   
	
   	if (document.fvalida.color.value.length==0){
   		Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Ingrese un color',
            showConfirmButton: false,
            timer: 1250
          }) 
      		//document.fvalida.nombre.focus()
      		return 0;
   	}
   	
   	if (document.fvalida.codigoColor.value.length==0){
   		Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Ingrese el codigo del color',
            showConfirmButton: false,
            timer: 1250
          }) 
      		//document.fvalida.nombre.focus()
      		return 0;
   	}
   	document.fvalida.submit();
}