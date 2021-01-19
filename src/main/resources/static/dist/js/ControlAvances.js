/**
 * author Víctor Hugo García Ilhuicatzi
 */
$(document).ready(function() {
	 $('#SeleccionPrograma').modal("show");
	
});

function listarExplosionPorProceso(){
	var idProceso = $('#procesosActivos').val();
	var tablaPrincipal = $('#tablaExplosionesPorProceso').DataTable();
	tablaPrincipal.rows().remove().draw();
	
	$.ajax({
		method:"GET",
		url:"/listarExplosion",
		data:{idProceso},
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
			var finalizarProceso = "";
			for (i in data){
				
				if(data[i][11]!=2){
					finalizarProceso = '<a class="btn btn-warning btn-circle btn-sm popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Finalizar proceso" onclick="finalizarProceso('+data[i][0]+')">' +
					'<i class="fas fa-thumbs-up"></i></a>';
				}
				else{
					finalizarProceso = "";
				}
				
				tablaPrincipal.row.add([
					data[i][0],//id_explosion_pedido
					data[i][1],//id_text de pedido
					data[i][2],//nombre del cliente
					data[i][3],//coordinado
					data[i][4],//familia de prenda
					data[i][5],//descripcion de la prenda
					data[i][7],//clave de la tela
					data[i][8],//numero de prendas a confeccionar
					data[i][9],//fecha inicio
					data[i][10],//fecha fin
					(data[i][11]==2)?"Finalizado":"En proceso",//estatus de proceso (0 sin proceso, 1 el proceso y 2 proceso finalizado)
					data[i][12],//programa
					data[i][13],//fecha entrega
					data[i][14],//tiempo de corte
					data[i][15],//estatus del registro en general (0 pendiente, 1 explosionado)
                    '<a class="btn btn-primary text-white btn-circle btn-sm btn-alta popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Explosionar prendas" >' +
                        '<i class="fas fa-certificate"></i> </a>' +
                       
                    '<a class="btn btn-success text-white btn-circle btn-sm btn-alta popoverxd" data-container="body" data-toggle="popover" data-placement="top" data-content="Consumo real" >' +
                        '<i class="fas fa-exclamation"></i> </a>' +
                        
                    finalizarProceso,
				]).draw(true);
			}
			
			Swal.fire({
			      position: 'center',
		          icon: 'success',
		          title: '¡Listo!',
		          showConfirmButton: false,
		          timer: 500,
			      onClose: () => {
			    	  $('#SeleccionPrograma').modal("hide");
			      }
			})
		},
		error: (data) => {
			
		}
	});
}

function finalizarProceso(idExplosion){
	

	Swal.fire({
        title: '¿Desea finalizar el proceso?',
        icon: 'question',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Reactivar',
        confirmButtonColor: '#28A745',
    }).then((result) => {
    	if (result.value){
    		location.href="/finalizarProceso/"+idExplosion;
    		Swal.fire({
			      position: 'center',
		          icon: 'success',
		          title: '¡Proceso finalizado!',
		          showConfirmButton: false
			})
			
    	}
    })
    
}