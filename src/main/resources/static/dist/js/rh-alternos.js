//Funci&oacute;n para agregar departamento
console.log("entre a mi documento")

function agregarAlterno() {
   mostrarAreas();
    Swal.fire({
        title: 'Nuevo Empleado aplicado para venta',
        html: '<div class="row">' +
            '<div class="form-group col-sm-12">' +
            
            '<label for="areadep">&Aacute;rea</label>' +
            '<select class="swal2-input form-control selectpicker" title="Seleccione uno..."  id="listarAreas"  onchange="changes()"  ></select>' +
            '<input type="hidden" id="idEmpleado" value="">' +
            '</div>'+
            ////
   '<div class="form-group col-sm-12">' +
            
            '<label for="areadep">Departamento</label>' +
            '<select class="swal2-input form-control selectpicker" title="Seleccione uno..."  id="departamento"  onchange="departamento()"  ></select>' +
            '<input type="hidden" id="idEmpleado" value="">' +
            '</div>'+
            
            
            ////
            
            
            
   '<div class="form-group col-sm-12">' +
            
            '<label for="areadep">Puesto</label>' +
            '<select class="swal2-input form-control selectpicker" title="Seleccione uno..."  id="puesto"  onchange="puestos()"  ></select>' +
            '<input type="hidden" id="idEmpleado" value="">' +
            '</div>'+
            
            
            ///
            '<label for="empleado">Nombre del Empleado</label>' +
            '<select class="swal2-input form-control selectpicker" title="Seleccione uno..."  id="empleado"></select>' +
            '<input type="hidden" id="idArea" value="">' +
            '</div>' +
            '</div>' 
         ,
        showCancelButton: true,
        cancelButtonColor: '#6C757D',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Actualizar',
        confirmButtonColor: '#28a745',
        preConfirm: () => {
            if (document.getElementById("idEmpleado").value.length < 1 || document.getElementById("listarAreas").value.length < 1) {
                Swal.showValidationMessage(
                    `Complete todos los campos`
                )
            }
        }
        
        
    }).then((result) => {
        if (result.value && document.getElementById("listarEmpleados").value) {
            var nombreDepartamento = document.getElementById("listarEmpleados").value;
            var nomArea = document.getElementById("listarAreas").value;
            var Departamento = $('#idDepartamento').val();
            $.ajax({
                type: "GET",
                url: "/duplicadoDepartamento",
                data: {
                    "nombreDepartamento": nombreDepartamento,
                    "nomArea": nomArea
                }
            }).done(function (data) {
                if (data == false) {
                    $.ajax({
                        type: "POST",
                        url: "/postDepartamento",
                        data: {
                            "_csrf": $('#token').val(),
                            "idDepartamento": Departamento,
                            "nombreDepartamento": nombreDepartamento,
                            "nomArea": nomArea
                        },
                        success: (data) => {
                            console.log(data);
                            if (data == 1) {
                                $('#closeDep').click();
                                Swal.fire({
                                    position: 'center',
                                    icon: 'success',
                                    title: 'Departamento editado correctamente',
                                    showConfirmButton: false,
                                    timer: 2300,
                                    onClose: () => {
                                        $('#btnDepartamentoDetalle').click();
                                    }
                                })
                                $('#idDepartamento').val("");
                            }
                            else if (data == 2) {
                                Swal.fire({
                                    position: 'center',
                                    icon: 'success',
                                    title: 'Departamento     agregado correctamente',
                                    showConfirmButton: false,
                                    timer: 2300,
                                    onClose: () => {
                                        $('#btnDepartamentoDetalle').click();
                                    }
                                })
                            }
                            else {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: '&iexcl;Intente de nuevo!'
                                })
                            }
                        },
                        error: function (data) {
                            Swal.close();
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: '&iexcl;Registro duplicado!',
                            })
                        }
                    });
                }
            })
        }
    })
}

//Habilitar input que se muestra deshabilitado
$('#empleadosAlternos').on('shown.bs.modal', function () {
    $(document).off('focusin.modal');
});






function changes() {
	
	
	console.log("estoy entrado a la function cheinch para listar depas")
	var area= $('#listarAreas').val();
   	console.log("valor del area"+ area)
   	
   	
    $.ajax({
        method: "GET",
           url: "/rh-listarDepa-Permiso",
           
          
           data: {

               "_csrf": $('#token').val(),
               "area": area
           },
           success: (data) => {
        	   $('#puesto').empty();
        	   $('#puesto').selectpicker('refresh');
        	   $('#departamento').empty();
        	   $('#departamento').selectpicker('refresh');
              for(var key in data) {
            	  $('#departamento').append('<option value="'+data[key][0]+'">'+data[key][1]+'</option>')
              }
              
         
              $('#departamento').selectpicker('refresh');
              
           },
          
           error: (e) => {
                location.reload();
           }
       });
    
   	
   	
   	
	}

function departamento() {
	
	console.log("estoy entrado a la function que llena puestos")
	var iddep= $('#departamento').val();
   	console.log("valor del depto"+ iddep)

    $.ajax({
        method: "GET",
           url: "/rh-listarPuestos-Permiso",
           
           data: {

               "_csrf": $('#token').val(),
               "departamento": iddep
           },
           success: (data2) => {
        		console.log("esta entrando al succes del ajax")
        		console.log("aqui va el data")
        		console.log(data2)
        		
        		
        	   $('#empleado').empty();
        	   $('#empleado').selectpicker('refresh');
        	   $('#puesto').empty();
        	   $('#puesto').selectpicker('refresh');
              for(var key1 in data2) {
            	  $('#puesto').append('<option value="'+data2[key1][0]+'">'+data2[key1][1]+'</option>')
              }
              
          
              $('#puesto').selectpicker('refresh');
              
           },
          
          
       });
    

}

function puestos() {
	
	console.log("estoy entrado a la function que llena empleados")
	var id= $('#puesto').val();
   	console.log("valor del puesto"+ id)
	
    $.ajax({
        method: "GET",
           url: "/rh-filtrar-empleado",
           
           data: {

               "_csrf": $('#token').val(),
               "id": id
           },
           success: (data) => {
        	   
         	
               for(var key in data) {
             	  $('#empleado').append('<option value="'+data [key][0]+'">'+data[key][1]+'</option>')
             	 
               }
           
               $('#empleado').selectpicker('refresh');
             
              

              
           },
          
       });
    
}

	

	
	







