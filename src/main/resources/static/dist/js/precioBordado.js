
		function eliminar(t) {
			var td = t.parentNode;
			var tr = td.parentNode;
			var table = tr.parentNode;
			table.removeChild(tr);
		}
		
		function porcentaje(e) {
			
				var precio = e.getAttribute("precio");
				var id = e.getAttribute("id_prenda");
				var precioBordado = e.getAttribute("precio_bordado");
			
				
				var adicional = $("#adicional"+id).val();
				
				if (adicional == ''){
					adicional='0.00';
					
				}
				
				adicional= Number.parseFloat(adicional).toFixed(2);;
				 $("#adicional"+id).val(adicional);
				var monto = (adicional/100)*precio;
				 $("#monto"+id).val(monto.toFixed(2));
				 var precioFinal = (Number(precio)+Number(precioBordado)+Number(monto));	 
				 document.getElementById("final"+id).textContent = precioFinal.toFixed(2); 
				 
				 
				 
				 monto= monto.toFixed(2);
				 precioFinal= precioFinal.toFixed(2);
				 
				 $.ajax({
				        method: "post",
				        url: "/precio-final",
				        data: {
				        	
				        	id:id,
				        	porcentaje:adicional,
				        	monto :monto,
				            preciof:precioFinal,
	                        _csrf: $("#token").val(),
	                    },
				        
				        beforeSend: function () {
		                   
		                },
		                success: function (r) {
		                	
		                	Swal.fire({
		        				position: 'center',
		        				icon: 'success',
		        				title: 'Precio  modificado',
		        				showConfirmButton: false,
		        				timer: 1250
		        			})
		                },
		                error: function () {
		                    alert("Ocurrio un error en el servidor de modelo ..");
		                    prenda.prop("disabled", false);
		                },

				    });
				
		
			
			
					
		}
		
		
	function monto(e) {
			var precio = e.getAttribute("precio");
			var id = e.getAttribute("id_prenda");
			var precioBordado = e.getAttribute("precio_bordado");
			var monto = $("#monto"+id).val();
			
			if (monto == ''){
				monto='0.00';
				
			}
			monto= Number.parseFloat(monto).toFixed(2);;
			 $("#monto"+id).val(monto);
		
			
			
			var adicinal = (monto*100)/precio;
			 $("#adicional"+id).val(adicinal.toFixed(2));
			 var precioFinal = (Number(precio)+Number(precioBordado)+Number(monto));
			 document.getElementById("final"+id).textContent = precioFinal.toFixed(2); 
			 
			 adicinal = adicinal.toFixed(2);

			 precioFinal=precioFinal.toFixed(2);
			 
			 $.ajax({
			        method: "post",
			        url: "/precio-final",
			        data: {
			        	
			        	id:id,
			        	porcentaje:adicinal,
			        	monto :monto,
			            preciof:precioFinal,
                     _csrf: $("#token").val(),
                 },
			        
			        beforeSend: function () {
	                   
	                },
	                success: function (r) {
	                	
	                	Swal.fire({
	        				position: 'center',
	        				icon: 'success',
	        				title: 'Precio  modificado',
	        				showConfirmButton: false,
	        				timer: 1250
	        			})
	                },
	                error: function () {
	                    alert("Ocurrio un error en el servidor de modelo ..");
	                    prenda.prop("disabled", false);
	                },

			    });
			 
		}
	
	
	function obser(e) {
		var id = e.getAttribute("id_prenda");
		var ob = e.getAttribute("obser");
		$("#id_coor_prenda").val(id);
		$("#observacionesDP").val(ob);
	
	}
	
	
	function obser_guardar() {
		
		
		var id =$("#id_coor_prenda").val();
		var ob= $("#observacionesDP").val();
		 $.ajax({
		        method: "post",
		        url: "/observacion-prenda",
		        data: {
		        	
		        	id:id,
		        	observacion:ob,
              		_csrf: $("#token").val(),
          },
		        
		        beforeSend: function () {
                
             },
             success: function (r) {
             	
             	Swal.fire({
     				position: 'center',
     				icon: 'success',
     				title: 'Observacion modificada',
     				showConfirmButton: false,
     				timer: 1250
     			})
     			
     			location.reload();
             },
             error: function () {
                 alert("Ocurrio un error en el servidor de modelo ..");
                 prenda.prop("disabled", false);
             },

		    });
	}
	
	
	 function bordados(id) {
		 
		 document.formBordado.id_coor_prenda.value = id
		 $.ajax({
			    method: "GET",
			    url: "/mostrar-bordados",
			    data: { id: id},
			    beforeSend: function () {
		        	 Swal.fire({
		                 title: 'Cargando ',
		                 html: 'Por favor espere',// add html attribute if you
													// want or remove
		                 allowOutsideClick: false,
		                 timerProgressBar: true,
		                 onBeforeOpen: () => {
		                     Swal.showLoading()
		                 },
		             });
		        	
		        },
			    success: (data) => {
			    	$("#contenedor").empty();
			    	$('#contenedor').append(
			    			"<table class'table table-bordered tablaModalPrecio' style='width:100%' id='table'>" +
		                    	"<thead>" +
		                               "<tr>" +
		                                  "<th>Precio</th>" +
		                                  "<th>Archivo</th>" +
		                                  "<th>Eliminar</th>" +
		                                 "</tr>" +
		                        "</thead>" +
		                     "</table>" );
			    	
			        var a;
			        var b = [];
			        for (i in data){
			        	
			        	
			        		a = [
								"<tr>" +
								"<td>" + data[i].precioBordado+ "</td>",
								"<td>" + data[i].archivoBordado + "</td>",		
								" <td>"+
								
								"<button  onclick='eliminar(" + data[i].idPrendaBordado + ")' class='btn btn-danger'>Eliminar</button>"+
								
								"</td>"+
								"<tr>"
								];
			        	
							b.push(a);	
			        }	        
				    var tabla = $('#table').DataTable({
		            	"data":b,
		                "ordering": true,
		                "pageLength": 5,
		                "lengthMenu": [
		                    [5, 10, 25, 50, 10],
		                    [5, 10, 25, 50, 10]
		                ],
		                "language": {
		                    "sProcessing": "Procesando...",
		                    "sLengthMenu": "Mostrar _MENU_ registros",
		                    "sZeroRecords": "No se encontraron resultados",
		                    "sEmptyTable": "Ningún dato disponible en esta tabla =(",
		                    "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
		                    "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
		                    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
		                    "sInfoPostFix": "",
		                    "sSearch": "Buscar:",
		                    "sUrl": "",
		                    "sInfoThousands": ",",
		                    "sLoadingRecords": "Cargando...",
		                    "oPaginate": {
		                        "sFirst": "Primero",
		                        "sLast": "Último",
		                        "sNext": "Siguiente",
		                        "sPrevious": "Anterior"
		                    },
		                    "oAria": {
		                        "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
		                        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
		                    },
		                    "buttons": {
		                        "copy": "Copiar",
		                        "colvis": "Visibilidad"
		                    }
		                }
		            });
			    },
			    
			    complete: function() {
					Swal.fire({
		 				title: 'Agregado correctamente',
		 				showConfirmButton: false,
		 				timer: 1
		 			})
			    },
			    error: (e) => {
			        // location.reload();
			    }
		}
		)
	 }
	 
	 $(".two-decimals").change(function(){ 
		    this.value = parseFloat(this.value).toFixed(2); 
		}); 
	  
	 $( "#formBordado" ).bind( "submit", function (e) {
		 $("#guardar_bordado").prop("disabled", true);
		    e.preventDefault();
		    
		    
		    
		    var id= document.formBordado.id_coor_prenda.value;
	        var form = $('#formBordado')[0];
			// Create an FormData object
	        var data = new FormData(form);
		    $.ajax({
		        method: "post",
		        url: "/guardar-bordado",
		        data: data,
		        cache: false,
		        /* upload */
		        contentType: false,
		        processData: false,
		        
		        beforeSend: function () {
                   
                },
                success: function (r) {
                	bordados(id);
                	$('#idBordado').val(null);
                	$('#idBordado').selectpicker('refresh');
                	
                	
                	$('#bordadoPrecio').val('');
                	$('#imagenTela').val('');
                	 $("#guardar_bordado").prop("disabled", false)
                	
                	Swal.fire({
        				position: 'center',
        				icon: 'success',
        				title: 'Agregado correctamente',
        				showConfirmButton: false,
        				timer: 1250
        			})
                },
                error: function () {
                    alert("Ocurrio un error en el servidor de modelo ..");
                    prenda.prop("disabled", false);
                },

		    });

		} )
		
		
		
	
function eliminar(idbaja) {
	var idbaja = idbaja;
	
	 var id= document.formBordado.id_coor_prenda.value ;
	Swal.fire({
		title: 'Deseas eliminar el bordado?',
		icon: 'warning',
		showCancelButton: true,
		cancelButtonColor: '#6C757D',
		cancelButtonText: 'Cancelar',
		confirmButtonText: 'Eliminar',
		confirmButtonColor: '#dc3545',
	}).then((result) => {
		if (result.value && idbaja != null) {

			$.ajax({
				type: "POST",
				url: "/eliminar-bordado",
				data: {
					"_csrf": $('#token').val(),
					'id': idbaja

					// ,'Descripcion':Descripcion
				}

			}).done(function (data) {

				bordados(id);
			});
			Swal.fire({
				position: 'center',
				icon: 'success',
				title: 'dado de baja correctamente',
				showConfirmButton: false,
				timer: 1250
			})
		} // ////////////termina result value
	})
}

	 
	 function bordado_precio(id) {
		 $.ajax({
	            method: "GET",
	               url: "/precio-bordado",
	               
	               data: {
	                   "_csrf": $('#token').val(),
	                   "id": id
	               },
	               success: (data) => {
	            	   $('#bordadoPrecio').val(data);                
	               },
	           });
	 }