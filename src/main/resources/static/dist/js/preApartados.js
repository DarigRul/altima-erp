/**
 * Autor: Victor Hugo Garcia Ilhuicatzi
 */
var arrayId = [];

$('#selectAgente').on('change',function(){
	var idAgente = $(this).val();
	$('#idCliente').find('option').remove();
	$('#idCliente').selectpicker('refresh');
		$.ajax({
			 method: "GET",
			    url: "/listarEmpresasMovimiento",
			    data: {
			    	idAgente: idAgente
			    },
			    success: (data) => {
					for (i in data){
						$('#idCliente').append("<option value='"+data[i][0]+"'>"+ data[i][1] + "</option>");
					}

					$('#idCliente').selectpicker('refresh');
			    },
			    error: (e) => {
			        // location.reload();

			    }
			});
	})

function listarClientes(){
	if(listaClientes[0].idCliente!=undefined){
		
	
	for(i in listaClientes){
		
		$('#idCliente').append("<option value="+listaClientes[i].idCliente+">"+listaClientes[i].nombre+" "+
								(listaClientes[i].apellidoPaterno==null?"":listaClientes[i].apellidoPaterno)+" "+
								(listaClientes[i].apellidoPaterno==null?"":listaClientes[i].apellidoPaterno)+"</option");
	}
	$('.selectpicker').selectpicker("refresh");
	
	}
	
	else{
		for(i in listaClientes){
			
			$('#idCliente').append("<option value="+listaClientes[i][0]+">"+listaClientes[i][1]+"</option");
		}
	}
}
	

function guardarPreapartado (){
	var idCliente = $('#idCliente').val();
	var numPersonas = $('#numPersonas').val();
	var selectAgente = $('#selectAgente').val();
	console.log(idCliente);
	location.href = "confirmacion-pre-apartado/h58fhgkt673GSRF"+idCliente+"GH63"+selectAgente+"GS63dd"+numPersonas+"gresdr2";
	
}

function guardarEstatusPedidoPreapartado(){
	var idPreapartado = $('#idPreapartado').val();
	var estatusPedido = $('#estatusPedido').val();
	var refPedido = $('#refPedido').val();
	location.href = "confirmacion-estatus-pre-apartado/h58fhg"+idPreapartado+"kt673GSRF"+estatusPedido+"GH63GS63dd"+refPedido+"gresdr2";
	
}

function guardarVigenciaPedidoPreapartado(){
	var idPreapartado = $('#idPreapartado').val();
	var fechaVigencia = $('#fechaVigencia').val();
	console.log(idPreapartado);
	Swal.fire({
	      position: 'center',
      icon: 'success',
      title: '¡Fecha de vigencia enviada correctamente!',
      showConfirmButton: false,
      timer: 2000,
	      onClose: () => {
	    	  location.href = "confirmacion-vigencia-pre-apartado/h58fhg"+idPreapartado+"kt673GSRF"+fechaVigencia+"GH63GS63ddgresdr2";
	      }
	})
	
}

function rechazarPedidoPreapartado(idPreapartado){
	
	Swal.fire({
		  title: '¿Desea rechazar el pre-apartado?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar',
		  cancelButtonText: 'Cancelar'
		}).then((result) => {
			if (result.value) {
				Swal.fire({
				      position: 'center',
			          icon: 'success',
			          title: '¡Pre-apartado autorizado!',
			          showConfirmButton: false,
			          timer: 1550,
				      onClose: () => {
				    	  var estatusValue = 2;
						  location.href = "confirmacion-estatus-pre-apartado/h58fhg"+idPreapartado+"kt673GSRFGH63GS63dd"+estatusValue+"gresdr2";
				      }
				})
				
			}
		});
}

function autorizarPedidoPreapartado(idPreapartado){

	Swal.fire({
		  title: '¿Desea autorizar el pre-apartado?',
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Confirmar',
		  cancelButtonText: 'Cancelar'
		}).then((result) => {
			if (result.value) {
				
				Swal.fire({
				      position: 'center',
			          icon: 'success',
			          title: '¡Pre-apartado autorizado!',
			          showConfirmButton: false,
			          timer: 1550,
				      onClose: () => {
				    	  var estatusValue = 1;
						  location.href = "confirmacion-estatus-pre-apartado/h58fhg"+idPreapartado+"kt673GSRFGH63GS63dd"+estatusValue+"gresdr2";
				      }
				})
				
			}
		});
	
}

function enviarPreapartado(){
	Swal.fire({
	      position: 'center',
        icon: 'success',
        title: '¡Pre-apartado enviado correctamente!',
        showConfirmButton: false,
        timer: 2000,
	      onClose: () => {
	    	  
	      }
	})
	
}

function agregarPrendaCoordinado(idCoordinado){
	var idTela = $('#telaCoordinado').val();
	var idPrenda = $('#modeloCoordinado').val();
	var idFamPrenda = $('#prendaCoordinado').val();
	var idsTelas = "";
	var idsMateriales = "";
	var idPreapartado = $('#idPreapartado').val();
	
	$("#elimnar22 select").each(function(index, dato1){
   	 
		console.log('entro al for 22');
   			 
		if ( $(dato1).val() == null || $(dato1).val() == ""  ){
		 
			return;
		}
		else{
			idsTelas += $(dato1).val() + 'id_tela';
			idsMateriales += $(dato1).attr('name') + 'id_materialConv';
		}
	});
	
	location.href ="/nueva-prenda-coordinado/"+idCoordinado+"FED"+idsTelas+"rREdw3"+idsMateriales+"232f3"+idTela+"5edcs3"+idPrenda+"fsc5FS3sd"+idFamPrenda+"/"+idPreapartado;
	
}

//Esta URL está en coordinadoController
function detalles(id) {
    console.log("id->" + id);
    $.ajax({
        method: "GET",
        url: "/detalles-material-preapartado",
        data: {
            id: id,
            _csrf: $("#token").val(),
        },
        success: (data) => {
            $("#quitarDetalles").remove();
            $("#contenedorTablaContador").append(
                "<div class='modal-body' id='quitarDetalles'>" +
                    "<table class='table table-striped table-bordered' id='idtableDetalles' style='width:100%' >" +
                    "<thead>" +
                    "<tr>" +
                    "<th>Material</th>" +
                    "<th>Color</th>" +
                    "</tr>" +
                    "</thead>" +
                    "</table>" +
                    "</div>"
            );
            var a;
            var b = [];
            for (i in data) {
                console.log("material->" + data[i][0]);
                console.log("cofigo  ->" + data[i][2]);
                console.log("color   ->" + data[i][1]);
                a = ["<tr>" + "<td>" + data[i][0] + "</td>", "<td> <i class=" + "'fa fa-tint'" + "  style=" + "'color:" + data[i][2] + "'" + ";  > </i>  " + data[i][1] + "  </td> ", "<tr>"];

                b.push(a);
            }
            var tabla = $("#idtableDetalles").DataTable({
                data: b,
                ordering: true,
                pageLength: 5,
                lengthMenu: [
                    [5, 10, 25, 50, 10],
                    [5, 10, 25, 50, 10],
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
				},
            });
            $("#detalles").modal("show");
        },
        error: (e) => {
            // location.reload();nnn
        },
    });
}

function idPreapartadoB(idPreapartado){
	
	$('#idPreapartado').val(idPreapartado);
}

function coordinados(idPreapartado){
	location.href = "/Coordinados-pre-apartado/"+idPreapartado;
}


$('#estatusPedido').on('change', function(){
	
	if($(this).val()==2){
		$('.perdidoHide').hide();
		$('#refPedido').val("");
	}
	else{
		$('.perdidoHide').show();
	}
	
})


$('#prendaCoordinado').on('change', function () {
	  var idPrenda = $('#prendaCoordinado').val();
	  console.log(idPrenda);
	  
	  document.getElementById("contenedor-espalda").style.display = "none";
  	
	  document.getElementById("contenedor-frente").style.display = "none";
  	
	  document.getElementById("contenedor-tela").style.display = "none";
  	
	  $("#elimnar").empty();
  	
	  $("#elimnar333").empty();
	  
	  $.ajax({
		  method: "GET",
		    url: "/ExtraerTelas",
		    data: {idFamPrenda : idPrenda},
		    beforeSend: function () {
		    	$('#modeloCoordinado').prop("disabled", true);
          },
		    success: function (r) {
		    	$('#modeloCoordinado').prop("disabled", false);

              // Limpiamos el select
		      $("#telaCoordinado").find("option").remove();
              $("#telaCoordinado").selectpicker("refresh");
              $(r).each(function (i, v) {
                  // indice, valor
              	$("#telaCoordinado").append('<option value="' + v[0] + '">' + v[1] + "</option>");
              });

              $("#telaCoordinado").prop("disabled", false);
              $("#telaCoordinado").selectpicker("refresh");
          },
          error: function () {
              alert("Ocurrio un error en el servidor de modelo ..");
              $('#modeloCoordinado').prop("disabled", false);
          },
	  });
	  
	  $.ajax({
		  method: "GET",
		    url: "/ExtraerModelos",
		    data: {idFamPrenda : idPrenda},
		    beforeSend: function () {
		    	$('#prendaCoordinado').prop("disabled", true);
          },
		    success: function (r) {
              
		    	$('#prendaCoordinado').prop("disabled", false);

              // Limpiamos el select
		    	$("#modeloCoordinado").find("option").remove();
              $(".selectpicker").selectpicker("refresh");
              $(r).each(function (i, v) {
                  // indice, valor
              	$("#modeloCoordinado").append('<option value="' + v[0] + '">' + v[1] + "</option>");
              });

              $("#modeloCoordinado").prop("disabled", false);
              $("#modeloCoordinado").selectpicker("refresh");
          },
          error: function () {
              alert("Ocurrio un error en el servidor de modelo ..");
              $('#prendaCoordinado').prop("disabled", false);
          },
	  });
	  
	 
})

	    $("#telaCoordinado").change(function(){
	    	
	        ///////validacion de precio/////////////////////////////////////////////////
	    	
		    var valordetela = $("#telaCoordinado").val();
		    var prenda= $("#modeloCoordinado").val();
		    
		     console.log("idsss en pantalla coordinado  tela  " +  valordetela)
		     console.log("idsss  idsss en pantalla coordinado   "+prenda)
		        
		         $.ajax({
                data: { idtela : valordetela,
                idprenda :prenda	},
                url:   '/validacion-compocision-tela',
                type:  'GET',
                beforeSend: function () 
                {
                	//prenda.prop('disabled', true);
                },
                success:  function (r) 
                {
                	
                	console.log("resultado de rrrrrrrrrrrrrrrrrrrrrrrrr para vali  " + r )
                	
                	if(r=="0"){
                		
                              Swal.fire({
							  title: 'Intenta con otra tela :(',
							  text: "La prenda con la tela seleccionada  no cuenta con un precio establecido",
							  icon: 'error',
							 
							  confirmButtonColor: '#3085d6',
							
							  confirmButtonText: 'Entendido'
							}).then((result) => {
							  if (result.value) {
								  $('#prendaCoordinado').val("default");
                                  $('#modeloCoordinado').val("default");
                                  $('#telaCoordinado').val("default");
                                  
                                  $('#prendaCoordinado').selectpicker("refresh");
                                  $('#modeloCoordinado').selectpicker("refresh");
                                  $('#telaCoordinado').selectpicker("refresh");
                                  
                                  document.getElementById("contenedor-tela").style.display = "none";
                                  document.getElementById("contenedor-espalda").style.display = "none";
                                  document.getElementById("contenedor-frente").style.display = "none";
                                  
                                  $('#elimnar22').empty();
                                  $('#elimnar222').empty();
                                  
							  }
							})
                	}
                },
                error: function()
                {
                    alert('Ocurrio un error en el servidor ..');
                    prenda.prop('disabled', false);
                }
            });
	    	   
	    	  $("#elimnar333 select").each(function(index, dato){
					 
					 if ( $(dato).val() == null || $(dato).val() == ""  ){
						
						 return;
					 }
					 else{
						 arrayId.push($(dato).val());
					 }
			        });
	    	  
	    	  console.log(arrayId.toString());
	    	
	    	console.log("id tela"+$(this).val())
   
	    	$("#elimnar").empty();

    if($(this).val() != null && $(this).val() != ''  )
    {
    	
    	$.ajax({
            url : "/mostrar-ruta-imagen-tela",
            type : "GET",
            data: { id : $(this).val()},
            success: function(data){
            	
            	document.getElementById("contenedor-tela").style.display = "block";
       		
            	document.getElementById("img3").style.maxWidth = "18rem";
            	document.getElementById("img3").style.maxHeight = "18rem";
       		 document.getElementById("img3").src ="/dist/img/preview.png";
       		
       		 if ( data != null && data != '' && data.length >1){
       			
       			$.ajax({
        			    url:"https://res.cloudinary.com/dti-consultores/image/upload/v1595530979/telas/"+data,
        			    type:'HEAD',
        			    error: function()
        			    {
        			        //file not exists
        			    	document.getElementById("img3").src ="/dist/img/preview.png";
        			    	
        			    	
        			    },
        			    success: function()
        			    {
        			        //file exists
        			    	document.getElementById("img3").src="https://res.cloudinary.com/dti-consultores/image/upload/v1595530979/telas/"+data;
        			    }
        			});
       		 }	
            	
           	 
            },
            error : function(xhr,errmsg,err) {
              console.log(xhr.status + ": " + xhr.responseText);
            }
          });
        $.ajax({
            data: { id : $('#modeloCoordinado').val() },
            url:   '/mostrar-materiales-prenda',
            type:  'GET',
            beforeSend: function () 
            {
            	// durante la petici?n
            },
            success:  function (r) 
            {
                // se elimina el div pa que no haya gases por si el puto usuario la caga 
                	
				$("#elimnar").empty()

                //cursos.prop('disabled', false);
            },
            error: function()
            {
                alert('Ocurrio un error en el servidor ..');
               // alumnos.prop('disabled', false);
            }
        });
    }
    else
    {
       // cursos.find('option').remove();
        //cursos.prop('disabled', true);
    }
})


$("#telaCoordinado").change(function(){
	    	console.log("id tela"+$(this).val())
   
	    	$("#elimnar22").empty()

    if($(this).val() != '')
    {
        $.ajax({
            data: { id : $('#modeloCoordinado').val() },
            url:   '/mostrar-materiales-prenda-extra',
            type:  'GET',
            beforeSend: function () 
            {
            	// durante la petici?n
            },
            success:  function (r) 
            {
                // se elimina el div pa que no haya gases por si el puto usuario la caga 
                	
				$("#elimnar22").empty()

                $(r).each(function(i, v){
                	console.log("RORTULUS="+i)

                	$("#elimnar22").append('<div class="form-group col-sm-3">'+
							   '<label for="coorAdorno">'+v[1]+'</label>'+
							   '<select  onchange="imagenTelasConv(this.value,'+v[0]+')"  title="Seleccione una.." name="'+v[0]+'"  id="material_'+i+'"  class="form-control selectCustom '+v[0]+'"  data-live-search="true" required>'+
							  
							      
							    '</select>'+
							    
							    '<div class="image-upload">'+
							    '<label for="file-input">'+
							    '<img  id="img-tela-convi-'+v[0]+'" name="img-tela-convi-'+v[0]+'" class="card-img-top" src="/dist/img/preview.png">'+
							    '</label>'+
                 '</div>'+
							'</div>');
                	
                    $.ajax({
                        url : "/mostrar-listas-tela",
                        type : "GET",
                        data: {  
                        	id:$('#prendaCoordinado').val(), 
                        	},
                        success: function(data){
                        	
                        	$.each(data, function(key, val) {
            		    		$('.'+v[0]+'').append('<option value="'+val[0]+'" data-content=" <i class='+"'fa fa-tint'"+'  style='+"'color:"+val[2]+"'"+';  > </i> '+val[1]+'  "> </option> ');
            		    		
                        	})
                        	$('.'+v[0]+'').selectpicker(["refresh"]);
                        	
                        },
                        error : function(xhr,errmsg,err) {
                          console.log(xhr.status + ": " + xhr.responseText);
                        }
                      });
                })

                //cursos.prop('disabled', false);
            },
            error: function()
            {
                alert('Ocurrio un error en el servidor ..');
               // alumnos.prop('disabled', false);
            }
        });
    }
    else
    {
       // cursos.find('option').remove();
        //cursos.prop('disabled', true);
    }
})


///////////////////////////////////////////EXTRAAAAAAAAAAAAAA22222222



  $("#telaCoordinado").change(function(){

    	$("#elimnar222").empty()

//if($(this).val() != '')
//{
//  $.ajax({
//      data: { id : $('#modeloCoordinado').val() },
//      url:   '/mostrar-materiales-prenda-extra2',
//      type:  'GET',
//      beforeSend: function () 
//      {
//      	// durante la petici?n
//      },
//      success:  function (r) 
//      {
//          // se elimina el div pa que no haya gases por si el puto usuario la caga 
//          	
//			$("#elimnar222").empty();
//
//          $(r).each(function(i, v){
//          	console.log("RORTULUS="+i)
//
//          	$("#elimnar222").append('<div class="form-group col-sm-3">'+
//						   '<label for="coorAdorno">'+v[1]+'</label>'+
//						   '<select   name="'+v[0]+'"  id="material_'+i+'"  class="form-control selectCustom '+v[0]+'"  data-live-search="true" required>'+
//						  
//						      
//						    '</select>'+
//						'</div>');
//        	
//            $.ajax({
//                url : "/mostrar-listas-forro",
//                type : "GET",
//                data: {  
//                	id:$('#telaCoordinado').val(), 
//                	},
//                success: function(data){
//                	
//                	$.each(data, function(key, val) {
//    		    		$('.'+v[0]+'').append('<option value="'+val[0]+'" data-content="<i class='+"'fa fa-tint'"+'  style='+"'color:"+val[2]+"'"+';  > </i> '+val[1]+'  "> </option> ');
//    		    		
//    		    		
//                	})
//                	$('.'+v[0]+'').selectpicker(["refresh"]);
//                	
//                },
//                error : function(xhr,errmsg,err) {
//                  console.log(xhr.status + ": " + xhr.responseText);
//                }
//              });
//          })
//
//          //cursos.prop('disabled', false);
//      },
//      error: function()
//      {
//          alert('error en el segundo Ajax ..');
//         // alumnos.prop('disabled', false);
//      }
//  });
//}
//else
//{
// // cursos.find('option').remove();
//  //cursos.prop('disabled', true);
//}
})


$("#modeloCoordinado").change(function(){
	    	
	    	
	    	$("#elimnar").empty();
	    	$("#elimnar22").empty();
	    	$("#elimnar222").empty();
	    	
	    	$("#telaCoordinado").val("default");
		    $("#telaCoordinado").selectpicker("refresh");
		    document.getElementById("contenedor-tela").style.display = "none";
	    	
	    	
	    	 $.ajax({
                 url : "/mostrar-ruta-imagen",
                 type : "GET",
                 data: { id : $('#modeloCoordinado').val(), tipo:'Frente' , tipo2:'Delantero'},
                 success: function(data){
                	 
                		 document.getElementById("contenedor-frente").style.display = "block";
                		 document.getElementById("img1").style.maxWidth = "18rem";
                		 document.getElementById("img1").style.maxHeight = "18rem";
                		 document.getElementById("img1").src ="/dist/img/preview.png";
                		 
                		 console.log(data);
                		 if ( data[0][0] != null && data[0][0] != '' && data[0][0].length >1){
                			
                			$.ajax({
                 			    url:"https://res.cloudinary.com/dti-consultores/image/upload/v1595530979/prendas/"+data[0][0],
                 			    type:'HEAD',
                 			    error: function()
                 			    {
                 			        //file not exists
                 			    	document.getElementById("img1").src ="/dist/img/preview.png";
                 			    	$("#text-img1").text("Sin imagen");
                 			    },
                 			    success: function()
                 			    {
                 			        //file exists
                 			    	
                 			    	
                 			    	document.getElementById("img1").src="https://res.cloudinary.com/dti-consultores/image/upload/v1595530979/prendas/"+data[0][0];
                 			    	
                 			    	$("#text-img1").text(data[0][1]);
                 			    	
                 			    	
                 			    }
                 			});
                		 	} 
                 },
                 error : function(xhr,errmsg,err) {
                   console.log(xhr.status + ": " + xhr.responseText);
                 }
               });
	    	 
	    	 $.ajax({
                 url : "/mostrar-ruta-imagen",
                 type : "GET",
                 data: { id : $('#modeloCoordinado').val(), tipo:'Espalda' ,tipo2:'Trasero'},
                 success: function(data){
                	
                	 document.getElementById("contenedor-espalda").style.display = "block";
            		 document.getElementById("img2").style.maxWidth = "18rem";
            		 document.getElementById("img2").style.maxHeight = "18rem";
            		 
            		 document.getElementById("img2").src ="/dist/img/preview.png";
            		
            		 if ( data[0][0] != null && data[0][0] != '' && data[0][0].length >1){
            			
            			$.ajax({
             			    url:"https://res.cloudinary.com/dti-consultores/image/upload/v1595530979/prendas/"+data[0][0],
             			    type:'HEAD',
             			    error: function()
             			    {
             			        //file not exists
             			    	document.getElementById("img2").src ="/dist/img/preview.png";
             			    	$("#text-img2").text("Sin imagen");
             			    },
             			    success: function()
             			    {
             			        //file exists
             			    	document.getElementById("img2").src="https://res.cloudinary.com/dti-consultores/image/upload/v1595530979/prendas/	"+data[0][0];
             			    	$("#text-img2").text(data[0][1]);
             			    }
             			});
            		 }
                	 
                 },
                 error : function(xhr,errmsg,err) {
                   console.log(xhr.status + ": " + xhr.responseText);
                 }
               });
	    	$("#elimnar333").empty()

//    if($(this).val() != '')
//    {
//        $.ajax({
//            data: { id : $('#modeloCoordinado').val() },
//            url:   '/mostrar-materiales-prenda',
//            type:  'GET',
//            beforeSend: function () 
//            {
//            	// durante la petici?n
//            },
//            success:  function (r) 
//            {
//                // se elimina el div pa que no haya gases por si el puto usuario la caga 
//                	
//				$("#elimnar333").empty()
//
//                $(r).each(function(i, v){
//                	
//                	console.log("i="+i)
//
//                	$("#elimnar333").append('<div class="form-group col-sm-3">'+
//							   '<label id="label_'+i+'" for="coorAdorno">'+v[1]+'</label>'+
//							   '<select onchange="selected('+i+');"  id="select'+i+'" value="'+v[0]+'" class="form-control selectCustom '+v[2]+'"  data-live-search="true" required>'+
//							  
//							      
//							    '</select>'+
//							'</div>');
//                	
//                	   $.ajax({
//                           url : "/mostrar-lista-materiales-extra",
//                           type : "GET",
//                           data: { idPrendaMaterial :v[2] 
//                           	
//                           	},
//                           success: function(data){
//                           	
//                           	$.each(data, function(key, val) {
//                           		if (key == 0){
//                           			
//                           			$('.'+v[2]+'').append('<option value="'+val[0]+'" data-content=" '+val[1]+' -Principal- ">'+val[1]+'  </option> ');
//                           			
//                           		}else{
//                           			
//                           			$('.'+v[2]+'').append('<option value="'+val[0]+'" data-content=" '+val[1]+'  ">'+val[1]+'  </option> ');
//                           			
//                           		}
//               		    		
//                           	})
//                           	$('.'+v[2]+'').selectpicker(["refresh"]);
//                           	
//                           },
//                           error : function(xhr,errmsg,err) {
//                             console.log(xhr.status + ": " + xhr.responseText);
//                           }
//                         });
//                })
//
//                //cursos.prop('disabled', false);
//            },
//            error: function()
//            {
//                alert('Ocurrio un error en el servidor ..');
//               // alumnos.prop('disabled', false);
//            }
//        });
//    }
//    else
//    {
//       // cursos.find('option').remove();
//        //cursos.prop('disabled', true);
//    }
})


 function imagenTelasConv(idTela, idSelect){
	 
	 console.log("holaa soy idTela "+idTela);
	 console.log("holaa soy Select "+idSelect);
	 $.ajax({
         url : "/mostrar-ruta-imagen-tela",
         type : "GET",
         data: { id : idTela},
         success: function(data){
  
    		 if ( data != null && data != '' && data.length >1){
    			
    			$.ajax({
     			    url:"https://res.cloudinary.com/dti-consultores/image/upload/v1595530979/telas/"+data,
     			    type:'HEAD',
     			    error: function()
     			    {
     			        //file not exists
     			    	document.getElementById("img-tela-convi-"+idSelect).src ="/dist/img/preview.png";
     			    	
     			    	
     			    },
     			    success: function()
     			    {
     			        //file exists
     			    	document.getElementById("img-tela-convi-"+idSelect).src="https://res.cloudinary.com/dti-consultores/image/upload/v1595530979/telas/"+data;
     			    }
     			});
    		 }	
         },
         error : function(xhr,errmsg,err) {
           console.log(xhr.status + ": " + xhr.responseText);
         }
       });
 }


