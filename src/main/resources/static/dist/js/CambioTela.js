function SelectPedido(){
	$.ajax({
		method:'GET',
		url:'/listar-pedidos-cerrados',
		data:{},
		success: (data)=>{
			$.each(data, function (key,val){
				$('#numeroPedido').append('<option value="'+val[0]+'"> '+val[1]+'</option>');
			})
			$('#numeroPedido').selectpicker('refresh');
		},
		error:(e)=>{			
		}
	})
}
function infoPedido (id){
	$.ajax({
		method:'GET',
		url:'/info-pedido',
		data:{'id':id},
		success: (data)=>{
			$('#fechaPedido').val(data[0][1]);
			$('#clientePedido').val(data[0][0]);
		},
		error:(e)=>{
		}
	})
}
function agregarSolicitud(){
	if ( $('#motivoPedido').val() != '' && $('#numeroPedido').val() != '' ){
		$.ajax({
			method:'POST',
			url:'/guardar-solicitud-tela',
			data:{
				'idPedido':$('#numeroPedido').val(),
				'motivo': $('#motivoPedido').val(),
				"_csrf": $('#token').val()
				},
		        beforeSend: function () {
		        	 document.getElementById("btn-guarda").disabled=true;
		        },
			success: (data)=>{
				 Swal.fire({
	        		 position: 'center',
	     				icon: 'success',
	     				title: 'Agregado correctamente',
	     				showConfirmButton: false,
						timer: 1250
	             });
				 location.reload();
			},
			error:(e)=>{
			}
		})
	}
	
	else{
		console.log("else entra");
		if ( $('#numeroPedido').val() == '' ){
			Swal.fire({
				position: 'center',
				icon: 'warning',
				title: 'Seleccione un pedido',
				showConfirmButton: false,
				timer: 1250
			})
		}
		if ( $('#motivoPedido').val() == '' ){
			Swal.fire({
				position: 'center',
				icon: 'warning',
				title: 'Ingrese un motivo',
				showConfirmButton: false,
				timer: 1250
			})
		}
	}
}