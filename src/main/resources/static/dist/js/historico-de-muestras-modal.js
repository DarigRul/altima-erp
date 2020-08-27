
  function expirados() {
		
		 $.ajax({
		        data: {},
		        url:   '/moviminetos-expirados',
		        type:  'GET',
		        success:  function (r) 
		        {
		        	var tabla = $('#tableSeguimiento').DataTable();
		        	
		        
		        	 
		        	tabla.clear();
		        	    
		        	
		            $(r).each(function(i, v){ // indice, valor
		                
		            	if ( v[8] >= 5){
		            	tabla.row.add([	
		            		v[0],
		            		v[1],
		            		v[2],
		            		v[3],
		            		v[4],
		            		v[5],
		            		v[6],
		            		v[7],
		            		v[8]
		           		  
		           		 ]).node().id ="row";
		           	tabla.draw( false );}
		            	//fila = '<tr> <td>'+v[1]+'</td>  <td >'+ v[2] +'</td> <td >'+ v[3] +'</td> <td >'+ v[4] +'</td>  </tr>' ;
		            	
		            	
		            	
		            })
		            
		       	
		            
		        },
		        error: function(){
		            alert('Ocurrio un error en el servidor de modelo ..');
		            select.prop('disabled', false);
		        }
		    });
		 
		 
	}
  