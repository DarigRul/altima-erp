function agregar (){
	
	var t = $('#tablaGeneral').DataTable();
	var repetido = false;

	if ( document.getElementById("materialRequisicion").selectedIndex != null
			&& document.getElementById("cantidadRequisicion").value>0){
		
		$('#tablaGeneral tr').each(function () {
			
			if ($(this).find('td').eq(0).html() == $("#materialRequisicion option:selected").attr("value") &&
					$(this).find('td').eq(1).html() == $("#materialRequisicion option:selected").attr("tipo")){
				Swal.fire({
					position: 'center',
					icon: 'error',
					title: 'Ya ha seleccionado ese material',
					showConfirmButton: false,
					timer: 1250
				});
				repetido = true;
			}
			

		});
		
	if ( repetido != true){
		t.row.add( [
			'<td  >'+$("#materialRequisicion option:selected").attr("value")+'</td>',
			'<td >'+$("#materialRequisicion option:selected").attr("tipo")+'</td>',
			'<td>'+document.getElementById("cantidadRequisicion").value+'</td>',
			'<td>'+$("#materialRequisicion option:selected").attr("idText")+'</td>',
			'<td>'+$("#materialRequisicion option:selected").attr("nombre")+'</td>',
			'<td>'+$("#materialRequisicion option:selected").attr("unidad")+'</td>',
			'<td>'+$("#materialRequisicion option:selected").attr("tamanio")+'</td>',
			'<td>'+$("#materialRequisicion option:selected").attr("color")+'</td>',
			'	<td><button type="button" onclick="eliminar(this)" class="btn btn-sm icon-btn btn-danger text-white btn_remove"><span class="btn-glyphicon spancircle fas fa-times fa-lg img-circle text-danger"></span>Eliminar</button></td>  '
		

        ] ).draw( false );
		
		document.getElementById("materialRequisicion").value = null;
		$('#materialRequisicion').change();
		document.getElementById("cantidadRequisicion").value = null;
	}
		
	
		}
	else{
		Swal.fire({
			position: 'center',
			icon: 'error',
			title: 'Complete todos los campos',
			showConfirmButton: false,
			timer: 1250
		});
	}
}

function eliminar(t) {
	Swal.fire({
		  title: '&iquest;Est&aacute; seguro que desea eliminar este registro?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  cancelButtonText: 'Cancelar',
		  confirmButtonText: 'Si, Eliminar',
		  reverseButtons: true
		}).then((result) => {
		  if (result.value) {
			  Swal.fire({
	        		 position: 'center',
	     				icon: 'success',
	     				title: 'Eliminado correctamente',
	     				showConfirmButton: false,
						timer: 1250
	                 
	             });
				var tabla = $('#tablaGeneral').DataTable();
				var td = t.parentNode;
				var tr = td.parentNode;
				var table = tr.parentNode;
				tabla.row(tr).remove().draw(false);
				
		  }
		})
}
function eliminar2(id, t) {
	Swal.fire({
		  title: '&iquest;Est&aacute; seguro que desea eliminar este registro?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  cancelButtonText: 'Cancelar',
		  confirmButtonText: 'Si, Eliminar',
		  reverseButtons: true
		}).then((result) => {
		  if (result.value) {
			  
			  $.ajax({
				  data: {'idRequision':id},
			        url:   '/elimiar-requisicion-materiales',
			        type:  'GET',
			    
			        success: function(data) {
			        	 Swal.fire({
			        		 position: 'center',
			     				icon: 'success',
			     				title: 'Eliminado correctamente',
			     				showConfirmButton: false,
								timer: 1250
			                 
			             });
			       },
			       complete: function() {   
			    	   var tabla = $('#tablaGeneral').DataTable();
						var td = t.parentNode;
						var tr = td.parentNode;
						var table = tr.parentNode;
						tabla.row(tr).remove().draw(false);
					
				    },
			    })
		  }
		})
}

function enviar() {
	 var  datos = [];
	$('#tablaGeneral tr').each(function () {
		 if ($(this).find('td').eq(1).html() !=null){
			 datos.push({
				 'id_material':$(this).find('td').eq(0).html(), 
				 'tipo':$(this).find('td').eq(1).html(),
				 'cantidad':$(this).find('td').eq(2).html()	 
			 });
		 }		
	});
	
	if ($.isEmptyObject(datos) ){
		Swal.fire({
			position: 'center',
			icon: 'error',
			title: 'Ingrese datos, por favor',
			showConfirmButton: false,
			timer: 1250
		});
	}
	else{
		$.ajax({
	        type: "POST",
	        url:"/guardar-requisicion-materiales",
	        data: { 
	        	datos :JSON.stringify(datos),
	        	'idRequisicion': $('#idRequisicion').val(),
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
	    	   var url = "/requisicion-de-almacen";  
	    		 $(location).attr('href',url);
			
		    },
	    })
	}
	
}

function editar (id){
	
	var url = "/requisicion-de-almacen-editar/"+id+"";  
	 $(location).attr('href',url);
}