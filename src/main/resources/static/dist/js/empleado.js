$( document ).ready(function() {
    console.log( "ready!" );
    var id=$('#idEmpleado').val();
    $.ajax({
        method: "GET",
           url: "/rh-editar-empleado",
           
           data: {

               "_csrf": $('#token').val(),
               "id": id
           },
           success: (data) => {
        	   console.log(data);
        	 
        	   for(var key in data) {
             	  console.log(key + ' Dentro de for data: ' +data[0][key]);
               }
        	   
        	  $('#area').find('[value='+data[0][7]+']').prop('selected', true);
              $('#area').selectpicker('refresh');
              mostrarDepartamentosEditar(data[0][7],data[0][8]);  
              mostrarPuestosEditar(data[0][8],data[0][9]);      
              
           },
          
           error: (e) => {
                location.reload();
           }
       });
});

function mostrarDepartamentosEditar(id,iddepartamento) {
	console.log("hola depa"+ iddepartamento);
    $.ajax({
        method: "GET",
           url: "/rh-listarDepas",
           
           data: {

               "_csrf": $('#token').val(),
               "area": id
           },
           success: (data) => {
        	  
              for(var key in data) {
            	  console.log(key + ' data: ' +data[0][key]);
            	  $('#departamento').append('<option value="'+data[key][0]+'">'+data[key][1]+'</option>')
              }
              $('#departamento').find('[value='+iddepartamento+']').prop('selected', true);
              $('#departamento').selectpicker('refresh');
              console.log(data);
              
           },
          
           error: (e) => {
                location.reload();
           }
       });
}

function mostrarPuestosEditar(id,idpuesto) {
	console.log("hola"+ id);
    $.ajax({
        method: "GET",
           url: "/rh-listarPuestos",
           
           data: {

               "_csrf": $('#token').val(),
               "departamento": id
           },	
           success: (data) => {
              for(var key in data) {
            	  console.log(key + ' data: ' +data[0][key]);
            	  $('#puestos').append('<option value="'+data[key][0]+'">'+data[key][1]+'</option>')
            	 
              }
              $('#puestos').find('[value='+idpuesto+']').prop('selected', true);
              $('#puestos').selectpicker('refresh');
           
              
           },
          
           error: (e) => {
                location.reload();
           }
       });
}