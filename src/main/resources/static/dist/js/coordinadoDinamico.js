console.log("ya entre loco al coordinado");


var arrayId = [];

$(document).ready(function() {
	   $('.selectpicker').selectpicker({
		   style: 'border border-bootstrap'
	   });
	   
	  
	   
	   // Bloqueamos los select  coorTela , coorModelo
	    $("#coorTela").prop('disabled', true);
	    $("#coorModelo").prop('disabled', true);
	    
	    
	    $("#coorPrenda").change(function(){
	       
	    	document.getElementById("contenedor-espalda").style.display = "none";
	    	
	    	document.getElementById("contenedor-frente").style.display = "none";
	    	
	    	document.getElementById("contenedor-tela").style.display = "none";
	    	
	    	$("#elimnar").empty();
	    	
	    	$("#elimnar333").empty();
	    	
	        var modelo = $("#coorModelo");
	        var tela = $("#coorTela");

	        // Guardamos el select de prenda
	        var prenda = $(this);

	        if($(this).val() != '')
	        {
	        	
	        	
	        	 $.ajax({
		                data: { id : prenda.val() },
		                url:   '/mostrar-modelo',
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
		                    //modelo.append('<option value="" selected>Seleccione uno...</option>');
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
		                    alert('Ocurrio un error en el servidor de modelo ..');
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
	                	
	                	console.log("este ajax es que esta llenando la tela")
	                	prenda.prop('disabled', false);

	                    // Limpiamos el select
	                    tela.find('option').remove();
	                    //tela.append('<option value="" selected>Seleccione uno...</option>');
	                    $('.coorTela').selectpicker('refresh');
	                    $(r).each(function(i, v){ // indice, valor
	                        tela.append('<option value="'+v[0]+'">' + v[1] + '</option>');
	                    })

	                    $(".coorTela").val($("#aux_tela").val());
	                    $('.coorTela').prop('disabled', false);
	                    $('.coorTela').selectpicker('refresh');
	               
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
	    	
	    	
	        ///////validacion de precio/////////////////////////////////////////////////
	    	
		    var valordetela = $("#coorTela").val();
		    var prenda= $("#coorModelo").val();
		    
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
                                  location.reload();
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
	    	
	    	
	    	$("#elimnar").empty();
	    	$("#elimnar22").empty();
	    	$("#elimnar222").empty();
	    	
	    	$(".coorTela").val(null);
		    $(".coorTela").selectpicker("refresh");
		    document.getElementById("contenedor-tela").style.display = "none";
	    	
	    	
	    	 $.ajax({
                 url : "/mostrar-ruta-imagen",
                 type : "GET",
                 data: { id : $('#coorModelo').val(), tipo:'Frente' , tipo2:'Delantero'},
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
                 data: { id : $('#coorModelo').val(), tipo:'Espalda' ,tipo2:'Trasero'},
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
                           			
                           		}else{
                           			
                           			$('.'+v[2]+'').append('<option value="'+val[0]+'" data-content=" '+val[1]+'  ">'+val[1]+'  </option> ');
                           			
                           		}
                           		
                           		
                           		
                           		
               		    		
               		    		
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
    				 
    				 return;
    			 }
    			 else{
					datosMateriales22.push({'id_tela':$(dato1).val(), 'id_materialConv':$(dato1).attr('name')   });
    			 }
    	        });
    		 

    	     console.log(datosMateriales22);
    	     
    	     
    	     
    	     
    	     
    	 $("#elimnar222 select").each(function(index, dat){
    		 
    	     console.log('entro al for 222');
    			 
    			 if ( $(dat).val() == null || $(dat).val() == ""  ){
    				 
    				 return;
    			 }
    			 else{
    				 datosMateriales222.push({'id_forro':$(dat).val()});
    			 }
    	        });
    		 

    	     console.log(datosMateriales222);
     

     
    	     console.log("si entre al 2");
	 
	if       (document.getElementById("coorPrenda").value && 
    		  	
    		  document.getElementById("coorTela").value &&  
    		  document.getElementById("coorModelo").value &&   
    		  document.getElementById("coorCoordinado").value 
    
    		 ) {
		
		var idPrenda=document.getElementById("coorPrenda").value;
		var idModelo=document.getElementById("coorModelo").value;
	
		var idTela=document.getElementById("coorTela").value;
		var idCoordinado=document.getElementById("coorCoordinado").value;


	
	
		
		
		console.log("El id de prenda es: "+idPrenda);
		
		console.log("El id de tela es: "+idTela);
		console.log("El id de del coordinado: "+idCoordinado);


		  

		
		console.log("si entre al 3");
		
		   console.log( $('#token').val());
		
		$.ajax({
        type: "POST",
        url:"/guardar-pedido-interno-rest-coordinado",
        data: { 
        	//datosMateriales:datosMateriales,
        	
        	datosMateriales :JSON.stringify(datosMateriales),
        	datosMateriales22 :JSON.stringify(datosMateriales22),
        	datosMateriales222 :JSON.stringify(datosMateriales222),
        	
        	'arrayId' : arrayId.toString(),
           
        	
        	'idPrenda': idPrenda,
        
        	'idTela': idTela,
        	
        	'idTela': idTela,
        	
        	'idModelo': idModelo,
        	
        	'idCoordinado': idCoordinado,
       
        	
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
     console.log("id->" + id);
     $.ajax({
         method: "GET",
         url: "/detalles-material",
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
 
 
