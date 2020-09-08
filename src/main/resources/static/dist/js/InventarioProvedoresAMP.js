function agregarProveedor(){
	var select = $("#proveedorModal");
	$("#id").val(null);
	 $.ajax({
         data: {},
         url:   '/proveedores-activos',
         type:  'GET',
         beforeSend: function () 
         {
         	select.prop('disabled', true);
         },
         success:  function (r) 
         {
         	select.prop('disabled', false);
             select.find('option').remove();
             $('.proveedorModal').selectpicker('refresh');
             $(r).each(function(i, v){ // indice, valor
                 select.append('<option value="'+v[0]+'">' + v[1] + '</option>');
             })
             $('.proveedorModal').prop('disabled', false);
             $('.proveedorModal').selectpicker('refresh');
         },
         error: function(){
             alert('Ocurrio un error en el servidor de modelo ..');
             select.prop('disabled', false);
         }
     });
	 
	 $("#proveedorModal").val(null);
	 $("#proveedorClave").val(null);
	 $("#proveedorCosto").val(null);
	 
	 $("#proveedorSurtido").val(null);
}
function guardarProveedor(){
	
	if ( 
			document.getElementById("proveedorModal").value && 
			document.getElementById("proveedorClave").value && 
			document.getElementById("proveedorCosto").value && 
			document.getElementById("proveedorSurtido").value){
		
		var id = $("#id").val();

		var idProveedor  = $("#proveedorModal").val();  
		var clave = $("#proveedorClave").val();  
		var costo =$("#proveedorCosto").val();  
		var dias =$("#proveedorSurtido").val(); 
		var idInventario =$("#idInventario").val(); 
		var tipo =$("#tipo").val(); 
	$.ajax({
        type: "POST",
        url:"/agregar-proveedor",
        data: { 
        	"id":id,
        	"idProveedor":idProveedor,
        	"clave":clave,
        	"costo":costo,
        	"dias":dias,
        	"idInventario":idInventario,
        	"tipo":tipo,
             "_csrf": $('#token').val(),
        },
        beforeSend: function () {
        	 Swal.fire({
        		 position: 'center',
     				icon: 'success',
     				title: 'Agregado correctamente',
                 allowOutsideClick: false,
                 timerProgressBar: true,
                 showConfirmButton: false,
                 onBeforeOpen: () => {
                    
                 },
             });
        	
        },
    
        success: function(data) {
       },
       complete: function() {   
    	   location.reload();
		
	    },
    })
	}
	else {
		Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Datos Incorrectos',
            showConfirmButton: false,
            timer: 1250
          })  
	}
	
}


function editar(e){
	var select = $("#proveedorModal");
	$.ajax({
        data: {},
        url:   '/proveedores-activos',
        type:  'GET',
        beforeSend: function () 
        {
        	select.prop('disabled', true);
        },
        success:  function (r) 
        {
        	select.prop('disabled', false);
            select.find('option').remove();
            $('.proveedorModal').selectpicker('refresh');
            $(r).each(function(i, v){ // indice, valor
                select.append('<option value="'+v[0]+'">' + v[1] + '</option>');
            })
            $(".proveedorModal").val(idProveedor);
            $('.proveedorModal').prop('disabled', false);
            $('.proveedorModal').selectpicker('refresh');
        },
        error: function(){
            alert('Ocurrio un error en el servidor de modelo ..');
            select.prop('disabled', false);
        }
    });
	var id = e.getAttribute("id");
	var idProveedor  = e.getAttribute("idProveedor"); 
	var clave = e.getAttribute("ClaveInt"); 
	var costo =e.getAttribute("Precio");  
	var dias =e.getAttribute("dias");
	var idInventario =e.getAttribute("id"); 
	$("#id").val(id);
  
	
	$("#proveedorClave").val(clave);  
	$("#proveedorCosto").val(costo);  
	$("#proveedorSurtido").val(dias); 
	 
	console.log(idProveedor)
	
}

function precios(e){
	var id = e.getAttribute("id");
	 $("#tablePrecios tbody").empty(); 
	 console.log(id);
	 var fila="";
	 $.ajax({
	        data: {id:id},
	        url:   '/historial-precio',
	        type:  'GET',
	        success:  function (r) 
	        {
	            $(r).each(function(i, v){ // indice, valor
	                
	            	fila = '<tr> <td>'+v[0]+'</td>  <td >'+ v[1] +'</td> </tr>'+fila ;
	            	
	            })
	            document.getElementById("tablePreciosBody").innerHTML =fila;
	       	
	            
	        },
	        error: function(){
	            alert('Ocurrio un error en el servidor de modelo ..');
	            select.prop('disabled', false);
	        }
	    });
	 
}

function baja(e) {
	Swal.fire({
		  title: '&iquest;Est&aacute; seguro que desea dar de baja a este proveedor?',
		 // text: "Puede cambiarlo en otro momento",
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  cancelButtonText: 'Cancelar',
		  confirmButtonText: 'Aceptar',
		  reverseButtons: true

		}).then((result) => {
		  if (result.value) {
			 location.href="/delete-proveedor-amp/"+e
		  }
		})
}


