console.log("si entro al editar coleccion pa checar")


var genero;
var idFamPrenda;
var idPedido;
var cantidadVolatil;





	 


     var arrayId = [];
     
	$(document).ready(function() {
		
		var maximo;
		
		
		   $('.selectpicker').selectpicker({
			   style: 'border border-bootstrap'
		   });
		   
		  
		   
		   // Bloqueamos los select  coorTela , coorModelo
		    $("#coorTela").prop('disabled', true);
		    $("#coorModelo").prop('disabled', true);
		    
		    
		    $("#coorPrenda").change(function(){
		        // Guardamos el select dependientes
		    	var contadorMaximo;	
		    	var contadorDetalle;
		    	  var cadenaTesto=document.getElementById('coorPrenda');
	    		  var valordecadena=cadenaTesto.options[cadenaTesto.selectedIndex].text;
	    		  console.log("aqui esta la pinche cadena verso  " + valordecadena);
	    		
	    		  var nombres = valordecadena.split("--");
	    		  var textoGenero  = nombres[0];	
	    		   genero = nombres[1];	
	    		   
	    		   idFamPrenda = $(coorPrenda).val();    		   
	    		   idPedido = $(pedido).val();
	    		   cantidadVolatil = $(canti).val();
	    		
	    		   
	    		  
	    		  console.log("aqui esta la pinche geneero culero  " + genero);
	    		  console.log("aqui esta el pinche id de la prenda  " + idFamPrenda);
	    		  console.log("aqui esta el pinche id del pedodppppppp  " + idPedido);
	    		  console.log("aqui esta la cantida volatil  " + cantidadVolatil);
	    		  
	    		  
	    		
	    		  
	    			 $.ajax({
			                data: { idFamPrenda : idFamPrenda,
			                	
			                genero : genero,
			                
			                idPedido: idPedido
			                
			                },
			                url:   '/maximo-coleccion',
			                type:  'GET',
			                
			                success:  function (r) 
			                {
			                	
			           
			          
			                      
			                
			                
			                
			                
			                
			                
			                $.ajax({
				                data: { idFamPrenda :idFamPrenda,
				                	
				                genero : genero,
				                
				                idPedido: idPedido
				                
				                },
				                url:   '/count-detalle-coleccion',
				                type:  'GET',
				                
				                success:  function (g) 
				                {
				                	
				                	
				                	
				                console.log("aquiesta la r"+r);	
				                console.log("aquiesta la g"+g);	
				                
				                var num1=parseInt(g);
				                var num2=parseInt(cantidadVolatil);
				             
				                
				                var sumaAntecesor= num1+num2;
				                
				                console.log("aqui estoyyyyyyyyyyy   " + sumaAntecesor)
				                
				                if(sumaAntecesor > r){
				                	
				                	Swal.fire({
				      	              position: 'center',
				      	              icon: 'warning',
				      	              title: 'superaste la cantidad de prendas',
				      	                text: 'NOTA: Intenta ingresar otra prenda para el pedido que no sea ' +textoGenero+ ' para '  +genero+  ' Cantidad maxima permitida para el pedido: ' +r ,
				      	              showConfirmButton: true,
				      	         
				      	              
				      	            }) 
				      	            
				      	         // setTimeout(() => {  location.reload(); }, 6000);
				      	          //location.reload();
				                	
				                }
				                
				                
				                // ajax para id Genero
				                $.ajax({
					                data: { idFamPrenda :idFamPrenda,
					                	
					                genero : genero,
					                
					                idPedido: idPedido
					                
					                },
					                url:   '/obtener-id-genero',
					                type:  'GET',
					                
					                success:  function (k) 
					                {
					                	
					                	$( '#generoDiv' ).remove();
					                	console.log("aqui esta el succes k   " + k)
					                	if(genero == 'Dama'){
					                		console.log("entre al if de dama " )
					                		$('#padreGenero').append('<div class="form-group col-md-3" id="generoDiv" ><label  > Genero: '+genero+' </label><input type="hidden" class="form-control" id="coorGenero" placeholder="Dama" value="'+k+'" data-content="" name="coorGenero" disabled ></div>');
					                	}else if(genero == 'Caballero'){
					                		console.log("entre al if de caballero " )
					                		
					                		$('#padreGenero').append('<div class="form-group col-md-3" id="generoDiv" ><label> Genero: '+genero+' </label><input type="hidden" class="form-control" id="coorGenero" placeholder="Caballero" value="'+k+'"  name="coorGenero" disabled ></div>');
					                		
					                	}else{
					                		console.log("entre al if defautl unixes " )
					                		$('#padreGenero').append('<div class="form-group col-md-3" id="generoDiv"><label> Genero: '+genero+' </label><input type="hidden" class="form-control" id="coorGenero" placeholder="Unisex" value="'+k+'"  name="coorGenero" disabled ></div>');
					                	}
					                	
					             
					             
					                },
					                error: function()
					                {
					                    alert('Ocurrio un error en el servidor de detalle  ..');
					                    prenda.prop('disabled', false);
					                }
					            });
				               	
				             
				                },
				                error: function()
				                {
				                    alert('Ocurrio un error en el servidor de detalle  ..');
				                    prenda.prop('disabled', false);
				                }
				            });
	                        
			                
			                },
			                error: function()
			                {
			                    alert('Ocurrio un error en el servidor de maximo  ..');
			                    prenda.prop('disabled', false);
			                }
			            });
	    			 
	    			 
	    		  
	    		
	    		  
	    		  
		  
		    	
		        var modelo = $("#coorModelo");
		        var tela = $("#coorTela");

		        // Guardamos el select de prenda
		        var prenda = $(this);

		        if($(this).val() != '')
		        {
		        	
		        	
		        	 $.ajax({
			                data: { id : prenda.val(),
			                	
			                genero : genero
			                
			                },
			                url:   '/mostrar-modelo-mejorado',
			                type:  'GET',
			                beforeSend: function () 
			                {
			                	prenda.prop('disabled', true);
			                },
			                success:  function (r) 
			                {
			                	prenda.prop('disabled', false);

			                    // Limpiamos el select
			                    modelo.find('option').remove();
			                    modelo.append('<option value="" selected>Seleccione uno...</option>');
			                    $('.coorModelo').selectpicker('refresh');
			                    $(r).each(function(i, v){ // indice, valor
			                        modelo.append('<option value="'+v[0]+'">' + v[1] + '</option>');
			                  
			                    })
			                    
			                   $(".coorModelo").val($("#aux_modelo").val());

			                    $('.coorModelo').prop('disabled', false);
			                    $('.coorModelo').selectpicker('refresh');
			                },
			                error: function()
			                {
			                    alert('Ocurrio un error en el servidor de modelo  ..');
			                    prenda.prop('disabled', false);
			                }
			            });
		        	
		        	
		          $.ajax({
		                data: { id : prenda.val() },
		                url:   '/mostrar-tela-primera',
		                type:  'GET',
		                beforeSend: function () 
		                {
		                	prenda.prop('disabled', true);
		                },
		                success:  function (r) 
		                {
		                	prenda.prop('disabled', false);

		                    // Limpiamos el select
		                    tela.find('option').remove();
		                    tela.append('<option value="" selected>Seleccione uno...</option>');
		                    $('.coorTela').selectpicker('refresh');
		                    $(r).each(function(i, v){ // indice, valor
		                        tela.append('<option value="'+v[0]+'">' + v[1] + '</option>');
		                    })

		                    $(".coorTela").val($("#aux_tela").val());
		                    $('.coorTela').prop('disabled', false);
		                    $('.coorTela').selectpicker('refresh');
		                    $('#coorTela').change();
		                },
		                error: function()
		                {
		                    alert('Ocurrio un error en el servidor ..');
		                    prenda.prop('disabled', false);
		                }
		            });
		            
		           
		        }
		        else
		        {
		            
		            $('.coorModelo').prop('disabled', false);
                    $('.coorModelo').selectpicker('refresh');
		        }
		    })
		    
		    

		    
		    
   

  		  
		    
		    
		    // MATERIALES
		    
		    
		    $("#coorTela").change(function(){
		    	
		    	  $("#elimnar333 select").each(function(index, dato){
						 
						 if ( $(dato).val() == null || $(dato).val() == ""  ){
							 alert("Hello! I am an alert box!!");
							 return;
						 }
						 else{
							 arrayId.push($(dato).val());
						 }
				        });
		    	  
		    	  console.log(arrayId.toString());
		    	
		    	console.log("id tela"+$(this).val())
       
		    	$("#elimnar").empty()

        if($(this).val() != '')
        {
            $.ajax({
                data: { id : $('#coorModelo').val() },
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

                    $(r).each(function(i, v){
                    	console.log("i="+i)

                    	$("#elimnar").append('<div class="form-group col-sm-3">'+
								   '<label  id="label_m'+i+'" for="coorAdorno">'+v[1]+'</label>'+
								   '<select   name="'+v[0]+'"  id="material_'+i+'"  class="form-control selectCustom '+v[0]+'"  data-live-search="true" required>'+
								  
								      
								    '</select>'+
								'</div>');
                    	
                        $.ajax({
                            url : "/mostrar-materiales-colores",
                            type : "GET",
                            data: { idMaterial : arrayId[i] , 
                            	idTela: $('#coorTela').val(), 
                            	idCoorPrenda:$('#id_coordinado_prenda').val()
                            	
                            	},
                            success: function(data){
                            	
                            	$.each(data, function(key, val) {
                		    		$('.'+v[0]+'').append('<option value="'+val[1]+'_'+val[2]+'" data-content="<i class='+"'fa fa-tint'"+'  style='+"'color:"+val[2]+"'"+';  > </i> '+val[1]+'  "> </option> ');
                		    		
                		    		
                            	})
                            	$('.'+v[0]+'').selectpicker(["refresh"]);
                            	
                            
                            	
                            },
                            error : function(xhr,errmsg,err) {
                              console.log(xhr.status + ": " + xhr.responseText);
                            }
                          });
                        
                        
                        
                  	  console.log("aqui esta el indexxxx"+ i);
		    		  var texto2=document.getElementById('select'+i+'');
		    		  var valordeltexto2=texto2.options[texto2.selectedIndex].text;
		    		  console.log("aqui esta el valor del select con iterator" + valordeltexto2);
		    		  console.log(valordeltexto2);
		    		  
		    		
		    		 
		    		  
		    		  document.getElementById('label_m'+i+'').innerHTML= valordeltexto2;
		    		  
		    		
                     
                    	
                    	
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
    
    
    
    
    
    
    
    
    
    
    	////////////////////////////////////////////EXTRAAAAAAAAAAAAA
    	
    	
    	
    	  $("#coorTela").change(function(){
		    	console.log("id tela"+$(this).val())
       
		    	$("#elimnar22").empty()

        if($(this).val() != '')
        {
            $.ajax({
                data: { id : $('#coorModelo').val() },
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
								   '<select   name="'+v[0]+'"  id="material_'+i+'"  class="form-control selectCustom '+v[0]+'"  data-live-search="true" required>'+
								  
								      
								    '</select>'+
								'</div>');
                    	
                    	
                    	
                        $.ajax({
                            url : "/mostrar-listas-tela",
                            type : "GET",
                            data: {  
                            	id:$('#coorPrenda').val(), 
                            	
                            	
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
	
	
	
	  $("#coorTela").change(function(){
		  
		  
		
		  
		  
		
		  
		  
	    	console.log("id tela"+$(this).val())
 
	    	$("#elimnar222").empty()

  if($(this).val() != '')
  {
      $.ajax({
          data: { id : $('#coorModelo').val() },
          url:   '/mostrar-materiales-prenda-extra2',
          type:  'GET',
          beforeSend: function () 
          {
          	// durante la petici?n
          },
          success:  function (r) 
          {
              // se elimina el div pa que no haya gases por si el puto usuario la caga 
              	
				$("#elimnar222").empty()

              $(r).each(function(i, v){
              	console.log("RORTULUS="+i)

              	$("#elimnar222").append('<div class="form-group col-sm-3">'+
							   '<label for="coorAdorno">'+v[1]+'</label>'+
							   '<select   name="'+v[0]+'"  id="material_'+i+'"  class="form-control selectCustom '+v[0]+'"  data-live-search="true" required>'+
							  
							      
							    '</select>'+
							'</div>');
              	
              	
            	
                $.ajax({
                    url : "/mostrar-listas-forro",
                    type : "GET",
                    data: {  
                    	id:$('#coorTela').val(), 
                    	
                    	
                    	},
                    success: function(data){
                    	
                    	$.each(data, function(key, val) {
        		    		$('.'+v[0]+'').append('<option value="'+val[0]+'" data-content="<i class='+"'fa fa-tint'"+'  style='+"'color:"+val[2]+"'"+';  > </i> '+val[1]+'  "> </option> ');
        		    		
        		    		
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
        	  
        	  
              alert('error en el segundo Ajax ..');
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


	
	
/////////////////////////////////////////////EXTRA 333333333333333333333333333333333333333333333


  $("#coorModelo").change(function(){
		    	console.log("id tela"+$(this).val())
       
		    	$("#elimnar333").empty()
		    	
		    	
		    
		    	

        if($(this).val() != '')
        {
            $.ajax({
                data: { id : $('#coorModelo').val() },
                url:   '/mostrar-materiales-prenda',
                type:  'GET',
                beforeSend: function () 
                {
                	// durante la petici?n
                },
                success:  function (r) 
                {
                    // se elimina el div pa que no haya gases por si el puto usuario la caga 
                    	
					$("#elimnar333").empty()

                    $(r).each(function(i, v){
                    	
                    	
                  
                    	
                    	
                    	
                    	console.log("i="+i)

                    	$("#elimnar333").append('<div class="form-group col-sm-3">'+
 							   '<label id="label_'+i+'" for="coorAdorno">'+v[1]+'</label>'+
 							   '<select onchange="selected('+i+');"  id="select'+i+'" value="'+v[0]+'" class="form-control selectCustom '+v[2]+'"  data-live-search="true" required>'+
 							  
 							      
 							    '</select>'+
 							'</div>');
                    	
                    	
                    	
                    	
                    	
                    	   $.ajax({
                               url : "/mostrar-lista-materiales-extra",
                               type : "GET",
                               data: { idPrendaMaterial :v[2] 
                          
                               	
                               	},
                               success: function(data){
                               	
                               	$.each(data, function(key, val) {
                               		if (key == 0){
                               			
                               			$('.'+v[2]+'').append('<option value="'+val[0]+'" data-content=" '+val[1]+' -Principal- ">'+val[1]+'  </option> ');
                               			
                               		}
                               		
                               		$('.'+v[2]+'').append('<option value="'+val[0]+'" data-content=" '+val[1]+'  ">'+val[1]+'  </option> ');
                   		    		
                   		    		
                               	})
                               	$('.'+v[2]+'').selectpicker(["refresh"]);
                               	
                               
                               	
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
    
   
  
    
    
    ///////////////////////////////////////////////fin de extra3333333333333
	


		    
		    
		});
	
	
function selected(id){
	
	

	
	
		
	  var texto=document.getElementById('select'+id+'');
	  var valordeltexto=texto.options[texto.selectedIndex].text;
	  console.log("aqui esta el valor del select");
	  console.log(valordeltexto);
	  
	
	  document.getElementById('label_'+id+'').innerHTML= valordeltexto;
	

	  
	  
	  
		  
		
		 
		
	
	//console.log("si entre aqui alv "+ id);
		
	//console.log($('#'+id+'').val());
	
	
	
	
}
	 
	 
	
	
	
	
	
	
	
	 function agregar() {
		 
		 
		 
		 
		 
		 
		 
		 console.log("si entre al 1");
		 var  datosMateriales = [];
		 var  datosMateriales22 = [];
		 var  datosMateriales222 = [];
		 
		 $("#elimnar select").each(function(index, dato){
			 
			 if ( $(dato).val() == null || $(dato).val() == ""  ){
				 alert("Hello! I am an alert box!!");
				 return;
			 }
			 else{
			 datosMateriales.push({'id_material':$(dato).attr('name'), 'color':$(dato).val()});
			 }
	        });
		 

	     console.log(datosMateriales);
	     

	     $("#elimnar22 select").each(function(index, dato1){
	    	 
	    	 console.log('entro al for 22');
	    			 
	    			 if ( $(dato1).val() == null || $(dato1).val() == ""  ){
	    				 alert("Hello! I am an alert box!!");
	    				 return;
	    			 }
	    			 else{
	    			 datosMateriales22.push({'id_tela':$(dato1).val()});
	    			 }
	    	        });
	    		 

	    	     console.log(datosMateriales22);
	    	     
	    	     
	    	     
	    	     
	    	     
	    	 $("#elimnar222 select").each(function(index, dat){
	    		 
	    	     console.log('entro al for 222');
	    			 
	    			 if ( $(dat).val() == null || $(dat).val() == ""  ){
	    				 alert("Hello! I am an alert box!!");
	    				 return;
	    			 }
	    			 else{
	    				 datosMateriales222.push({'id_forro':$(dat).val()});
	    			 }
	    	        });
	    		 

	    	     console.log(datosMateriales222);
	     
	
	     
	    	     console.log("si entre al 2");
		 
		if       (document.getElementById("coorPrenda").value && 
	    		  document.getElementById("coorModelo").value &&
	    		  document.getElementById("canti").value &&
	    		  document.getElementById("coorTela").value &&
	    		  document.getElementById("coorlargo").value &&
	    		  document.getElementById("coortalla").value &&
	    		  document.getElementById("pedido").value
	    		 ) {
			
			var idPrenda=document.getElementById("coorPrenda").value;
			var idModelo=document.getElementById("coorModelo").value;
			var idTela=document.getElementById("coorTela").value;
			var idTalla=document.getElementById("coortalla").value;
			var idLargo=document.getElementById("coorlargo").value;
			var idPedido=document.getElementById("pedido").value;
			var cantidad=document.getElementById("canti").value;
			var idGeneroinsert=document.getElementById("coorGenero").value;
			
		
			
			
			console.log("El id de prenda es: "+idPrenda);
			console.log("El id de modelo es: "+idModelo);
			console.log("El id de tela es: "+idTela);
			console.log("El id del pedido es: "+idPedido);
			console.log("El id de la talla es: "+idTalla);
			console.log("El id del largo  es: "+idLargo);
			console.log("la cantidad es  es: "+cantidad);
			console.log("El genero nefasto  es: "+idGeneroinsert);
			  
	
			
			console.log("si entre al 3");
			
			   console.log( $('#token').val());
			
			$.ajax({
	        type: "POST",
	        url:"/guardar-pedido-interno-rest",
	        data: { 
	        	//datosMateriales:datosMateriales,
	        	
	        	datosMateriales :JSON.stringify(datosMateriales),
	        	datosMateriales22 :JSON.stringify(datosMateriales22),
	        	datosMateriales222 :JSON.stringify(datosMateriales222),
	        	
	        	'arrayId' : arrayId.toString(),
	           
	        	
	        	'idPrenda': idPrenda,
	        	'idModelo': idModelo,
	        	'idTela': idTela,
	        	'idPedido': idPedido,
	        	'cantidad': cantidad,
	        	'idTalla': idTalla,
	        	'idLargo': idLargo,
	        	'idGeneroinsert': idGeneroinsert,
	        	
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
	      }else 
	      {
	    	  Swal.fire({
	              position: 'center',
	              icon: 'warning',
	              title: 'Datos Incorrectos',
	              showConfirmButton: false,
	              timer: 1250
	            })       
	      }
	}
	
	
	 
	 function detalles(id) {
		 
		 console.log("aqui esta el id con el que vas a probar"+id);
			$.ajax({
			    method: "GET",
			    url: "/pedido-detalles-material",
			    data: { 
		        	'id': id,
		        	 "_csrf": $('#token').val(),
		        },
			    success: (data) => {
			    	$('#quitarDetalles').remove();
			    	$('#contenedorTablaContador').append(
			    			
			    			"<div class='modal-body' id='quitarDetalles'>" +
			    			
			    			"<table class='table table-striped table-bordered' id='idtableDetalles' style='width:100%' >" +
		                                        "<thead>" +
		                                            "<tr>" +
		                                                "<th>Material</th>" +
		                                                "<th>Color</th>" +
		                                                
		                                            "</tr>" +
		                                        "</thead>" +
		                                    "</table>" + "</div>");
			        var a;
			        var b = [];
			        for (i in data){
			        	
			        		a = [
								"<tr>" +
								"<td>" + data[i][0] + "</td>",
								'<td> <i class='+"'fa fa-tint'"+'  style='+"'color:"+data[i][2]+"'"+';  > </i>  '+data[i][1]+'  </td> ',
								"<tr>"
								];
			    
							b.push(a);	
			        }	        
				    var tabla = $('#idtableDetalles').DataTable({
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
		                    "sEmptyTable": "Ning?n dato disponible en esta tabla =(",
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
		                        "sLast": "?ltimo",
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
				    $('#detalles').modal('show');
			    },
			    error: (e) => {
			        // location.reload();nnn
			    }
		})
		
	 }
	 
	 
	 
	 function editar(e) {
			var id = e.getAttribute("id");
			
			console.log("el id es "+id);
			
			$.ajax({
			    method: "GET",
			    url: "/update-coor-prenda",
			    data: { 
		        	'id': id,
		        	 "_csrf": $('#token').val(),
		        },
		        beforeSend: function () {
		        	 Swal.fire({
		                 title: 'Cargando ',
		                 html: 'Por favor espere',// add html attribute if you want or remove
		                 allowOutsideClick: false,
		                 timerProgressBar: true,
		                 onBeforeOpen: () => {
		                     Swal.showLoading()
		                 },
		             });
		        	
		        },
			    success: (data) => {
			    	$("#id_coordinado_prenda").val(id);
			    	
			    	
			    	
			    	$("#aux_modelo").val(data.idPrenda)
			    	$("#aux_tela").val(data.idTela)
			    	
			    	
			    	$('.coorPrenda').selectpicker('val', data.idFamilaGenero);

			    	
			    	$("#coorPrenda").change();
			    	
			    	
			    	
			    },
			    complete: function() {
					Swal.fire({
		 				title: 'Agregado correctamente',
		 				showConfirmButton: false,
		 				timer: 1
		 			})
			    },
			    error: (e) => {
			        // location.reload();nnn
			    }
		})

		}
	 
	 
	 
	 
	

	 