$(document).ready(function() {
		   $('.selectpicker').selectpicker({
			   style: 'border border-bootstrap'
		   });
		   
		   
		   
		    $( "#tipo" ).change(function() {

		        if($('#tipo').val()=="tela"){
		        	document.getElementById("table-tela").style.display = "block"
		        	document.getElementById("table-forro").style.display = "none"
		        	$("#boton-agregar").attr('onclick', 'tallas ("Tela");');
		        	$("#btn-tallas").attr('onclick', 'agregar("Tela");');
		        	
		        }
		        else if($('#tipo').val()=="forro"){
		        	
		        	$.ajax({  
		        		data:{"idPrenda":$("#id_prenda").val()},
		        	    method: "GET",
		        	    url: "/validar-forro-prenda",
		        	    success: (data) => {
		        	    	if ( data == true){
		        	    		document.getElementById("table-tela").style.display = "none"
		    			        	
		        			    document.getElementById("table-forro").style.display = "block"
		        			        
		        			    	$("#boton-agregar").attr('onclick', 'tallas ("Forro");');
		    		        	$("#btn-tallas").attr('onclick', 'agregar("Forro");');
		        	    	}
		        	    	else{
		        	    		Swal.fire({
		        	    			  icon: 'error',
		        	    			  title: 'Lo sentimos',
		        	    			  text: 'No existe un forro asignado a esta prenda',
		        	    			})
		        	    			 $("#boton-agregar").attr('onclick', 'error(this);');
		        	    			
		        	    		
		        	    	}
		        	    	console.log(data);	
		        	    } , 
		        	    error: (e) => {
		        	    }
		        	})
		        	
		        	
		        	
		        }
		        else if($('#status').val()=="forro"){
		            var url = "/agregar-material/forro";  
		        }
		        $(location).attr('href',url);
		});
	
		   
		   
		});



function error(e) {
	$("#eliminar").empty();
	$('#modalConsumoPorPrendas').modal('hide');
	Swal.fire({
		  icon: 'error',
		  title: 'Lo sentimos',
		  text: 'No existe un forro asignado a esta prenda',
		})
		
}

function editar_tallas(e , tipo) {
	var num_talla= e.getAttribute("num_talla");
	var talla = e.getAttribute("id_talla");
	var id_prenda = $("#id_prenda").val();

	var select_talla = $("#tallaConsumo");
	select_talla.find("option").remove();
	$('#tallaConsumo').append('<option value="' + talla + '">'+num_talla+'</option>');
	$('.tallaConsumo').selectpicker('refresh');
	
	$("#eliminar").empty();
	$.ajax({  
		data:{"idTalla":talla,
				"idPrenda":id_prenda,
				"tipo":tipo 
		},
	    method: "GET",
	    url: "/editar-tallas",
	    success: (data) => {
	    	$(data).each(function (i, v) {
	    		 $("#eliminar").append(
	                      '<div class="form-group col-md-3">' +
	                          '<label for="coorAdorno">' +v[1] +"</label>" +
	                          '<input type="number" step="any" min="0"   name="' +v[0]+'" id="' +v[0]+'" class="form-control" value="' +v[2]+'"  required>' +
	                        '</div>'
	                  );
	    	});
	    } , 
	    error: (e) => {
	    }
	})
}

function tallas(tipo) {
	console.log(tipo);
	console.log($("#id_prenda").val());
	$("#eliminar").empty();
	var talla = $("#tallaConsumo");
	talla.find("option").remove();
	$.ajax({  
		data: { id:  $("#id_prenda").val(), tipo:tipo },
	    method: "GET",
	    url: "/listar-tallas-prenda",
	    success: (data) => {
	    	
	  
	    	$.each(data, function(key, val) {
	    		$('#tallaConsumo').append('<option value="' + val[0] + '">'+val[1]+'</option>');})
	    		$('.tallaConsumo').selectpicker('refresh');
	    } , 
	    error: (e) => {
	    }
	})	
	
	$.ajax({  
		data:{"Tipo":"Largo"},
	    method: "GET",
	    url: "/listar-catalogo-produccion",
	    success: (data) => {
	    	$(data).each(function (i, v) {
	    		 $("#eliminar").append(
	                      '<div class="form-group col-md-3">' +
	                          '<label for="coorAdorno">' +v.nombreLookup +"</label>" +
	                          '<input type="number" step="any" min="0"  name="' +v.idLookup+'" id="' +v.idLookup+'" class="form-control" value="0"  required>' +
	                        '</div>'
	                  );
	    	});
	    } , 
	    error: (e) => {
	    }
	})
	$('#modalConsumoPorPrendas').modal('show'); // abrir
}
function agregar(tipo) {
	 var datos= [];
	  var  x = new Boolean(false);
	  $("#eliminar input").each(function (index, dato) {
          if ($(dato).val() == null || $(dato).val() == "" || $(dato).val() < 0 ) {
       			x = true;
          } else {
              datos.push({ id_material: $(dato).attr("name"), color: $(dato).val() });
          }
      });
	  var select = document.getElementById("tallaConsumo");
	  var id_talla = select.value;
	  var talla = select.options[select.selectedIndex].innerText;
	  console.log("--------------");
	  console.log(datos);
	  console.log(id_talla);
	  
	  if ( x==false) {
		  $.ajax({
	          type: "POST",
	          url: "/guardar-largo-prenda",
	          data: {
	              //datosMateriales:datosMateriales,

	              datos: JSON.stringify(datos),

	              idPrenda: $("#id_prenda").val(),
	              idTalla: id_talla,
	              _csrf: $("#token").val(),
	              tipo:tipo 
	          },

	          beforeSend: function () {
	              Swal.fire({
	                  position: "center",
	                  icon: "success",
	                  title: "Agregado correctamente",
	                  allowOutsideClick: false,
	                  timerProgressBar: true,
	                  showConfirmButton: false,
	                  onBeforeOpen: () => {},
	              });
	          },
	          success: function (data) {
	        	  console.log(data);
	          },
	          complete: function () {
	              location.reload();
	          },
	      });
	  }
	  else{
		  
		  Swal.fire({
			  icon: 'error',
			  title: 'Lo sentimos',
			  text: 'Datos erroneos',
			})
			
	  }
	  
	  
	  
}






	