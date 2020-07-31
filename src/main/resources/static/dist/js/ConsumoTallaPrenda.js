$(document).ready(function() {
		   $('.selectpicker').selectpicker({
			   style: 'border border-bootstrap'
		   });
		   
		});


function materiales(s){
	var value = s[s.selectedIndex].value;
	var id = s[s.selectedIndex].id;
	
	  if(value =="Tela"){
      	console.log("tea");
      	 var url = "/consumo-talla-prenda/"+$('#id_prenda').val()+"/Tela/"+id; 
      	 $(location).attr('href',url);
      	
      	
      }
      
      if(value=="Forro"){
      	console.log("tea");
      	 var url = "/consumo-talla-prenda/"+$('#id_prenda').val()+"/Forro/"+id; 
      	 $(location).attr('href',url);
      	
      }
      
     if(value=="tela-combinacion"){
    	 
    	 var url = "/consumo-talla-prenda/"+$('#id_prenda').val()+"/tela-combinacion/"+id; 
      	 $(location).attr('href',url);
    	
      	
      }
     
     if(value=="tela-entretela"){
    	 
    	 var url = "/consumo-talla-prenda/"+$('#id_prenda').val()+"/tela-entretela/"+id; 
      	 $(location).attr('href',url);
    	
      	
      }
	   

	  }
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
	if (tipo==1){tipo="Tela";}
	if (tipo==2){tipo="Forro";}
	if (tipo==3){tipo="tela-combinacion";}
	
	if (tipo==4){tipo="tela-entretela";}
	
	if (tipo =="Tela" || tipo=="Forro"){
	
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
	$('#modalConsumoPorPrendas').modal('show'); // abrir
	}
	if (tipo =="tela-combinacion" || tipo=="tela-entretela"){
		var num_talla= e.getAttribute("num_talla");
		var talla = e.getAttribute("id_talla");
		var select_talla = $("#tallaConsumo2");
		select_talla.find("option").remove();
		$('#tallaConsumo2').append('<option  value="' + talla + '">'+num_talla+'</option>');
		$('.tallaConsumo2').selectpicker('refresh');
		$('.tallaConsumo2').val(talla);
		$('.tallaConsumo2').selectpicker('refresh');
			
		var largo = $("#largoConsumo2");
		largo.find("option").remove();
		$.ajax({  
			data: { tipo:tipo },
		    method: "GET",
		    url: "/listar-largos-prenda",
		    success: (data) => {
		    	
		  
		    	$.each(data, function(key, val) {
		    		$('#largoConsumo2').append('<option value="' + val[0] + '">'+val[1]+'</option>');})
		    		$('.largoConsumo2').selectpicker('refresh');
		    } , 
		    error: (e) => {
		    }
		})
		$('#modalConsumoPorPrendas2').modal('show'); // abrir
		
	}
}

function tallas(tipo) {
	if (tipo== null){
		Swal.fire({
			  icon: 'error',
			  title: 'Error',
			  text: 'Seleccione un material',
			})
			return;
	}
	if (tipo==1){tipo="tela";}
	if (tipo==2){tipo="forro";}
	if (tipo==3){tipo="tela-combinacion";}
	if (tipo==4){tipo="tela-entretela";}
	
	if (tipo =="tela" || tipo=="forro"){
		
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
	
	 if (tipo =="tela-combinacion" || tipo =="tela-entretela"){
		
		var talla = $("#tallaConsumo2");
		talla.find("option").remove();
		$.ajax({  
			data: { id:  $("#id_prenda").val(), tipo:tipo },
		    method: "GET",
		    url: "/listar-tallas-prenda",
		    success: (data) => {
		    	
		  
		    	$.each(data, function(key, val) {
		    		$('#tallaConsumo2').append('<option value="' + val[0] + '">'+val[1]+'</option>');})
		    		$('.tallaConsumo2').selectpicker('refresh');
		    } , 
		    error: (e) => {
		    }
		})
		
		var largo = $("#largoConsumo2");
		largo.find("option").remove();
		$.ajax({  
			data: { tipo:tipo },
		    method: "GET",
		    url: "/listar-largos-prenda",
		    success: (data) => {
		    	
		  
		    	$.each(data, function(key, val) {
		    		$('#largoConsumo2').append('<option value="' + val[0] + '">'+val[1]+'</option>');})
		    		$('.largoConsumo2').selectpicker('refresh');
		    } , 
		    error: (e) => {
		    }
		})
		
		$('#modalConsumoPorPrendas2').modal('show'); // abrir
		
	}
}
function agregar(tipo) {
	if (tipo==1){tipo="Tela";}
	if (tipo==2){tipo="Forro";}
	if (tipo==3){tipo="tela-combinacion";}
	if (tipo==4){tipo="tela-entretela";}
	
	if (tipo =="Tela" || tipo=="Forro"){
	 var datos= [];
	  var  x = new Boolean(false);
	  $("#eliminar input").each(function (index, dato) {
          if ($(dato).val() == null || $(dato).val() == "" || $(dato).val() < 0 ) {
       			x = true;
       			console.log("ff");
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
	if (tipo =="tela-combinacion" || tipo =="tela-entretela"){
		if ( $("#largo").val() != "" &&
			$("#ancho").val() != ""  &&
			$("#largo").val() > 0 &&
			$("#ancho").val() > 0 &&
			$("#tallaConsumo2").val() != null &&
			$("#largoConsumo2").val() != null){
			$.ajax({
		          type: "POST",
		          url: "/guardar-largo-prenda-extras",
		          data: {
		              idPrenda: $("#id_prenda").val(),
		              idTalla:  $("#tallaConsumo2").val(),
		              idLargo:  $("#largoConsumo2").val(),
		              tipo:tipo,
		              largo:   $("#largo").val(),
		              ancho:   $("#ancho").val(),
		              idMaterial:   $("#id_tela_combinacion").val(),
		              _csrf: $("#token").val()
		              
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
			console.log("if no");
		}
		
	}
	  
}

$("#largoConsumo2").change(function(){
	
	var tipo =  $("#aux-tipo").val();
	
	if ( tipo == 3 ){tipo ="tela-combinacion"}
	if ( tipo == 4 ){tipo ="tela-entretela"}
	if ( $("#tallaConsumo2").val() == null ||  $("#tallaConsumo2").val() == '' ){
		Swal.fire({
			  icon: 'error',
			  title: 'Lo sentimos',
			  text: 'Por favor seleccione una talla',
			})

	}
	else{
		$.ajax({  
			data: { 
				idTalla:  $("#tallaConsumo2").val(),
				idPrenda: $("#id_prenda").val(),
	            idLargo:  $("#largoConsumo2").val(),
	            idMaterial:   $("#id_tela_combinacion").val(),
	            tipo:tipo			
			},
		    method: "GET",
		    url: "/buscar-largos-anchos",
		    beforeSend: function () {
	        	 Swal.fire({
	                 title: 'Verificando existencia ',
	                 html: 'Por favor espere',// add html attribute if you want or remove
	                 allowOutsideClick: false,
	                 timerProgressBar: true,
	                 onBeforeOpen: () => {
	                     Swal.showLoading()
	                 },
	             });
	        	
	        },
		    success: (data) => {
		    	
		    	$("#ancho").val(data[0]);
		    	$("#largo").val(data[1]);
		    	
		    } , 
		    
		    complete: function(data) {
				Swal.fire({
	 				title: 'Agregado correctamente',
	 				showConfirmButton: false,
	 				timer: 1
	 			})
		    },
		    error: (e) => {
		    }
		});
	}
})


$("#tallaConsumo2").change(function(){
	$("#largoConsumo2").val(null);
    $("#largoConsumo2").selectpicker("refresh");
})




	