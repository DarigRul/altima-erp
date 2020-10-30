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
	var tabla = $('#tablaGeneral').DataTable();
	var td = t.parentNode;
	var tr = td.parentNode;
	var table = tr.parentNode;
	
	 tabla.row(tr).remove().draw(false);
	
}