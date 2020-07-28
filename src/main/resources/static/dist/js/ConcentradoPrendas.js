$(document).ready(function () {
	
	//Esto elimina los puntos y simbolos raros del input
	$("#cantidadPrendas").keypress(function(e){
		if(e["originalEvent"]["key"] == "." || e["originalEvent"]["key"] == "-" || e["originalEvent"]["key"] == "e" ||
			e["originalEvent"]["key"] == "," || e["originalEvent"]["key"] == "+"){
			return false;
		}
	});
	
	$("#cantidadPrendasEspecial").keypress(function(e){
		if(e["originalEvent"]["key"] == "." || e["originalEvent"]["key"] == "-" || e["originalEvent"]["key"] == "e" ||
				e["originalEvent"]["key"] == "," || e["originalEvent"]["key"] == "+"){
				return false;
			}
	});
	
	//Cada que se abre el modal, se cargan los empleados
	$( "#AgregarPrendaEmpleadoPlus" ).click(function() {
		  $.ajax({
				type: "GET",
				url: "/get_empleados",
				data: { 
					id: $('#idPedido').val()
				},
				success: (data) => {
					//Se pintan los empleados
					for(var i = 0; i < data.length; i++){
						$('#ListaEmpleados').append("<option value='" + data[i][0] + "'>" + data[i][1] + "</option>");
					}
					$('#ListaEmpleados').selectpicker('refresh'); 
					
					//Se muestra el modal
					$('#modalConcentradoPrendas').modal('toggle');
				},
				error: (e) => {
					console.log(e);
				}
			});
	});
	
	//Cada que se cierra el modal se limpian los campos
	$("#modalConcentradoPrendas").on("hidden.bs.modal", function () {
		//Se limpia Empleado
		$('#ListaEmpleados').empty();
		$("#ListaEmpleados").prop('disabled', false);
		$('#ListaEmpleados').selectpicker('refresh'); 
		//Se limpian coordinados
		$("#ListaCoordinados").val('');
		$("#ListaCoordinados").prop('disabled', false);
		$('#ListaCoordinados').selectpicker('refresh'); 
		//Se limpian los modelos
		$("#ListaModelos").val('');
		$("#ListaModelos").empty();
		$('#ListaModelos').selectpicker('refresh'); 
		//Se limpian cantidades
		$('#cantidadPrendas').val(0);
		$('#cantidadPrendas').selectpicker('refresh'); 
		$('#cantidadPrendasEspecial').val(0),
		$('#cantidadPrendasEspecial').selectpicker('refresh'); 
		//Se limpia la tabla
		table.clear().draw();
		//Se limpian los registros de forma logica
		PrendasEmpleado = null;
		PrendasEmpleado = [];
		//Se limpian los objetos de los modelos que ya fueron agregados
		ModelosAgregados = null;
		ModelosAgregados = [];
	});
	
	//Funcion que cambia los modelos y las telas del select al cambiar el coordinado
	$("#ListaCoordinados").change(function () {
		//Se muestra el icono de cargando
		$('#CargandoModelos').show();
		$("#ListaModelos").prop('disabled', true);
		
		$.ajax({
			type: "GET",
			url: "/get_prendas_de_coordinado",
			data: { 
				id: $('#ListaCoordinados').val()
			},
			success: (data) => {
				
				//Se limpian y pintan opciones
				$('#ListaModelos').empty();
				//Este primer for recorre los modelos que obtengo de la BD
				for(var i = 0; i < data.length; i++){
					//Se busca el id del modelo dentro del objeto que tenemos ya hecho.
					var indexPrendaEmpleado = PrendasEmpleado.map(function (item) { return item.modelo; }).indexOf(String(data[i][0]));
					//Si ya esta en la tabla de abajo, no se pinta en el select.
					if(indexPrendaEmpleado < 0){
						$('#ListaModelos').append("<option value='" + data[i][0] + "'>" + data[i][1] + " - " + data[i][2] + "</option>");
					}
				}
				//Se esconde el cargando modelos y se habilita el select
				$('#CargandoModelos').hide();
				$("#ListaModelos").prop('disabled', false);
				
				//Se refresca el select para que note los cambios
				$('#ListaModelos').selectpicker('refresh'); 
			},
			error: (e) => {
				console.log(e);
			}
		});
	});
	
	
	//Funcion que cambia los coordinados que se muestran en la pantalla principal ListaCoordinadosGeneral
	$("#ListaCoordinadosGeneral").change(function () {
		idCoordinado = $("#ListaCoordinadosGeneral").val();
		$('#CargandoCoordinados').show();
		$("#ListaCoordinadosGeneral").prop('disabled', true);
		
		$.ajax({
			type: "GET",
			url: "/get_cantidades_prendas_de_coordinado",
			data: { 
				id: $('#ListaCoordinadosGeneral').val(),
				idPedido: $('#idPedido').val()
			},
			success: (data) => {	
				
				//Se habilita el select para cambiar de general a especial
				$("#cargaTipopedido").prop('disabled', false);
				
				//Se limpia y elimina la tabla actual
				table1.destroy();
				$('#TablaGeneral').remove();
				
				//se crea de nuevo
				CrearNuevaTabla(data[0], data[1]);
				
				//Se esconde el spinner
				$('#CargandoCoordinados').hide();
				$("#ListaCoordinadosGeneral").prop('disabled', false);
				
				//Se selecciona el formato general
				$("#cargaTipopedido").val("1");
				$('#cargaTipopedido').selectpicker('refresh');
			},
			error: (e) => {
				console.log(e);
			}
		});
	});
	
	
	
	$("#cargaTipopedido").change(function () {
		if($("#cargaTipopedido").val() == 1){
			$('#CargandoTipoConcentrado').show();
			$("#cargaTipopedido").prop('disabled', true);
			
			$.ajax({
				type: "GET",
				url: "/get_cantidades_prendas_de_coordinado",
				data: { 
					id: $('#ListaCoordinadosGeneral').val(),
					idPedido: $('#idPedido').val()
				},
				success: (data) => {	
					//Se limpia y elimina la tabla actual
					table1.destroy();
					$('#TablaGeneral').remove();
					
					//se crea de nuevo
					CrearNuevaTabla(data[0], data[1]);
					
					//Se esconde el spinner
					$('#CargandoTipoConcentrado').hide();
					$("#cargaTipopedido").prop('disabled', false);
				},
				error: (e) => {
					console.log(e);
				}
			});
			
		}
		else{
			
			$('#CargandoTipoConcentrado').show();
			$("#cargaTipopedido").prop('disabled', true);
			$.ajax({
				type: "GET",
				url: "/get_cantidades_prendas_de_coordinado",
				data: { 
					id: $('#ListaCoordinadosGeneral').val(),
					idPedido: $('#idPedido').val()
				},
				success: (data) => {	
					//Se limpia y elimina la tabla actual
					table1.destroy();
					$('#TablaGeneral').remove();
					
					//se crea de nuevo
					CrearNuevaTablaEspecial(data[0], data[1]);
					
					//Se esconde el spinner
					$('#CargandoTipoConcentrado').hide();
					$("#cargaTipopedido").prop('disabled', false);
				},
				error: (e) => {
					console.log(e);
				}
			});

		}
	});
});

function AgregarPrendaEmpleadoValidacionCamposVacios(){
	//Primera alerta que pide que los campos esten llenos
	if($('#ListaEmpleados').val() != "" && $('#ListaCoordinados').val() != "" && $('#ListaModelos').val()){
		
		//Esto valida las cantidades
		var can = parseInt($('#cantidadPrendas').val());
		var canSp = parseInt($('#cantidadPrendasEspecial').val());
		if(can.toFixed(0) > 0 || canSp.toFixed(0) > 0){
			
			AgregarPrendaEmpleado();
			//Esto valida que no sean decimales
		}
		else{
			Swal.fire({
				icon: 'error',
				title: 'Error!',
				text: 'Debes agregar al menos una cantidad'
			})
		}
	}
	else{
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'Debes llenar todos los campos!'
		})
	}
}

function AgregarPrendaEmpleado()
{
	//Se bloquea el select de empleado para que no seleccione otro por ahora
	$("#ListaEmpleados").prop('disabled', true);
	$('#ListaEmpleados').selectpicker('refresh');
	
	//Se bloquea el boton para que no haya pez xd
	$("#BotonAgregar1").prop("disabled", true);
	
	//Objeto temporal para almacenar los datos
	var temp = {
			identidad: $('#ListaEmpleados').val() + "_" + $('#ListaCoordinados').val() + "_" + $('#ListaModelos').val(),
			empleado: $('#ListaEmpleados').val(),
			coordinado: $('#ListaCoordinados').val(),
			modelo: $('#ListaModelos').val(),
			cantidad: $('#cantidadPrendas').val(),
			cantidadSp: $('#cantidadPrendasEspecial').val(),
			modeloTexto: $('#ListaModelos option:selected').text()
		}
	
	var repetido = false;
	for(var i = 0; i < PrendasEmpleado.length; i++){
		if(temp["identidad"] == PrendasEmpleado[i]["identidad"]){
			Swal.fire({
				icon: 'error',
				title: 'Repetido',
				text: 'Ya agregaste ese registro!'
			  })
			repetido = true;
		}
	}
	
	if(!repetido){
		
		$.ajax({
			type: "GET",
			url: "/get_numero_de_coordinado",
			data: { 
				id: $('#ListaCoordinados').val(),
			},
			success: (data) => {	
				//Se mete el objeto al array de objetos
				PrendasEmpleado.push(temp);
				//Se pinta el row en la tabla
				var fila = table.row.add( [ data["numeroCoordinado"], 
											temp["modeloTexto"], 
											"<label id='ContenedorRemoverCantidad_" + temp["identidad"] + "'><p id='RemoverCantidad_" + temp["identidad"] + "'>" + temp["cantidad"] + "</p></label>", 
											"<label id='ContenedorRemoverCantidadSp_" + temp["identidad"] + "'><p id='RemoverCantidadSp_" + temp["identidad"] + "'>" + temp["cantidadSp"] + "</p></label>", 
											"<button class='btn btn-danger' type='button' onclick=\"QuitarPrendaEmpleado(\'" + temp["identidad"] + "'\, \'" + temp["modelo"] + "\');\">Eliminar</button>"] ).draw().node();
				$( fila ).prop('id', "Remover_" + temp["identidad"] + "_Remover");
				fila = null;
				
				//Se elimina esa prenda del select
				var ModeloTemp = {id: $('#ListaModelos').val(), nombre: $( "#ListaModelos option:selected" ).text()}
				$('#ListaModelos option[value=' + ModeloTemp["id"] + ']').remove();
				$('#ListaModelos').selectpicker('refresh'); 
				ModelosAgregados.push(ModeloTemp);
				
				//Se limpian cantidades
				$('#cantidadPrendas').val(0);
				$('#cantidadPrendasEspecial').val(0);
				
				//Se habilita de nuevo el boton
				$("#BotonAgregar1").prop('disabled', false);
			},
			error: (e) => {
				console.log(e);
			}
		});
	}
	

}

function QuitarPrendaEmpleado(identidad, modelo)
{
	var row = table.row( $('#Remover_' + identidad + '_Remover') );
    var rowNode = row.node();
    row.remove().draw();
	
	//Se elimina el objeto del array de objetos
	var removeIndex = PrendasEmpleado.map(function (item) { return item.identidad; }).indexOf(identidad);
	PrendasEmpleado.splice(removeIndex, 1);
	
	//Si ya no hay ninguna prenda, se desbloquea el empleado
	if(PrendasEmpleado.length == 0){
		$("#ListaEmpleados").prop('disabled', false);
		$('#ListaEmpleados').selectpicker('refresh'); 
	}
	
	//Se agrega la prenda de nuevo al select de opciones
	var modeloEliminado = ModelosAgregados.map(function (item) { return item.id; }).indexOf(modelo);
	$('#ListaModelos').append("<option value='" + ModelosAgregados[modeloEliminado]["id"] + "'>" + ModelosAgregados[modeloEliminado]["nombre"] + "</option>");
	$('#ListaModelos').selectpicker('refresh'); 
	ModelosAgregados.splice(modeloEliminado, 1);
}

function EnviarEmpleadoPrendas(){
	$('#BotonCargandoGuardando').show();
	$('#BotonGuardarModal').hide();
	
	
	$.ajax({
		type: "GET",
		url: "/guardar_prendas_empleado",
		data: { 
			"prendas_empleado": JSON.stringify(PrendasEmpleado),
			"idCoordinado" : $('#ListaCoordinados').val(),
			"idEmpleado": $('#ListaEmpleados').val()
		},
		success: (data) => {
			
			//Se limpia la tabla y los inputs
			$('#cantidadPrendas').val(0);
			$('#cantidadPrendasEspecial').val(0);
			table.clear().draw();
			
			$('#BotonCargandoGuardando').hide();
			$('#BotonGuardarModal').show();
			
			Swal.fire({
				icon: 'success',
				title: 'Guardado',
				text: 'Se ha guardado el registro!'
			})
			
			if(PrendasEmpleado.length != 0){
				$('#ListaEmpleados option[value=' + PrendasEmpleado[0]["empleado"] + ']').remove();
				$('#ListaEmpleados').selectpicker('refresh');
			}
			
			PrendasEmpleado = null;
			PrendasEmpleado = [];
			
			$("#ListaEmpleados").prop('disabled', false);
			$('#ListaEmpleados').selectpicker('refresh');
			
			//Se actualiza la tabla
			$.ajax({
				type: "GET",
				url: "/get_cantidades_prendas_de_coordinado",
				data: { 
					id: $('#ListaCoordinados').val(),
					idPedido: $('#idPedido').val()
				},
				success: (data) => {
					//Se limpia y elimina la tabla actual
					table1.destroy();
					$('#TablaGeneral').remove();
					
					//se crea de nuevo
					CrearNuevaTabla(data[0], data[1]);
					
					//Se esconde el spinner
					$('#CargandoTipoConcentrado').hide();
					$("#cargaTipopedido").prop('disabled', false);
					$("#cargaTipopedido").val("1");
					$('#cargaTipopedido').selectpicker('refresh');
					
					//Se cierra el modal xd
					$('#modalConcentradoPrendas').modal('toggle');
					
					//Se cambia el select de coordinados generales
					$('#ListaCoordinadosGeneral').val($('#ListaCoordinados').val());
					$('#ListaCoordinadosGeneral').selectpicker('refresh');
				},
				error: (e) => {
					console.log(e);
				}
			});
			
		},
		error: (e) => {
			console.log(e);
		}
	});
}

function EditarRegistro(id, nombreEmpleado){
	
	//Se abre el modal
	$('#modalConcentradoPrendas').modal('toggle');
	//Se selecciona el empleado
	$('#ListaEmpleados').append("<option value='" + id + "'>" + nombreEmpleado + "</option>");
	$('#ListaEmpleados').selectpicker('refresh');
	$('#ListaEmpleados').val(id);
	$('#ListaEmpleados').selectpicker('refresh');
	$('#ListaEmpleados').prop('disabled', true);
	$('#ListaEmpleados').selectpicker('refresh');
	
	//Se selecciona el coordinado actual y se bloquea
	$("#ListaCoordinados").val($("#ListaCoordinadosGeneral").val());
	$('#ListaCoordinados').prop('disabled', true);
	$('#ListaCoordinados').selectpicker('refresh');
	
	//Se guarda el idEmpleado
	$('#idEmpleado').val('0');
	$('#idEmpleado').val(id);
	
	//Se pintan los registros
	$.ajax({
		type: "GET",
		url: "/get_prendas_de_empleado_editar",
		data: { 
			idEmpleado: id,
			idCoordinado: $("#ListaCoordinados").val()
		},
		success: (data) => {
			
			//Se llena el objeto de forma logica
			for(var k = 0; k < data[0].length; k++){
				var temp = {
						identidad: data[0][k][8] + "_" + data[0][k][6] + "_" + data[0][k][5],
						empleado: data[0][k][8],
						coordinado: data[0][k][6],
						modelo: data[0][k][5],
						cantidad: data[0][k][3],
						cantidadSp: data[0][k][4],
						modeloTexto: data[0][k][2]
				}
				PrendasEmpleado.push(temp);
				
				//Se pinta el row en la tabla
				var fila = table.row.add([ 
											data[0][k][1], 
											data[0][k][2],
											"<label id='ContenedorRemoverCantidad_" + temp["identidad"] + "'><p id='RemoverCantidad_" + temp["identidad"] + "'>" + temp["cantidad"] + "</p></label>", 
											"<label id='ContenedorRemoverCantidadSp_" + temp["identidad"] + "'><p id='RemoverCantidadSp_" + temp["identidad"] + "'>" + temp["cantidadSp"] + "</p></label>",
											"<button class='btn btn-danger' type='button' onclick=\"QuitarPrendaEmpleado(\'" + temp["identidad"] + "'\, " + temp["modelo"] + ");\">Eliminar</button>"] ).draw().node();
				$( fila ).prop('id', "Remover_" + temp["identidad"] + "_Remover");
				fila = null;
			}
			
			//Esto es para pintar los modelos en el select
			for(j = 0; j < data[1].length; j++){
				var CoincidioModelo = true;
				for(h = 0; h < data[0].length; h++){
					if(data[1][j][0] == data[0][h][5]){
						CoincidioModelo = false;
						var temp = {id: data[1][j][0], nombre: data[1][j][1] + "-" + data[1][j][2]};
						ModelosAgregados.push(temp);
					}
				}
				if(CoincidioModelo){
					$('#ListaModelos').append("<option value='" +  data[1][j][0] + "'>" + data[1][j][1] + "-" + data[1][j][2] + "</option>");
				}
			}
			$('#ListaModelos').selectpicker('refresh');
		},
		error: (e) => {
			console.log(e);
		}
	});
	
}


function CrearNuevaTabla(data, ListaEmpleados){	
	//Se crea el row
	var coordinados = "";
	var modelos = "";
	var telas = "";
	var prendas = "";
	var colores = "";
	var tdVacios = "";
	var tdVaciosParaCuerpo = "";
	
	//Array de Objetos para guardar los modelos
	PrendasArray = [];
	for(var j = 0; j < data.length; j++){
		var PrendasObjeto = {posicion: j + 1, identificador: data[j][0], modelo: data[j][1], tela: data[j][2], color: data[j][5]};
		coordinados += "<td>" + data[j][3] + "</td>";
		modelos += "<td>" + data[j][1] + "</td>";
		telas += "<td>" + data[j][2] + "</td>";
		prendas += "<td>" + data[j][4] + "</td>";
		colores += "<td>" + data[j][5] + "</td>";
		tdVacios += "<td></td>";
		tdVaciosParaCuerpo += "<td></td>" + ",";
		
		PrendasArray.push(PrendasObjeto);
		PrendasObjeto = null;
	}
	
	$('#ContenedorDeTabla').append("<table class='table table-bordered tablaConcentrado' id='TablaGeneral'>" +
                                        "<thead>" +
                                            "<tr>" +
                                                "<th>Coordinado</th>" +
                                                coordinados + 
                                                "<th colspan='2'>Coordinado</th>" +
                                            "</tr>" +
                                            "<tr>" +
                                                "<th>Modelo</th>" +
                                                modelos + 
                                                "<th colspan='2'>Modelo</th>" +
                                            "</tr>" +
                                            "<tr>" + 
                                                "<th>Clave Tela</th>" +
                                                telas +
                                                "<th colspan='2'>Clave Tela</th>" +
                                            "</tr>" +
                                            "<tr>" +
                                            	"<th>Prenda</th>" + 
                                            	prendas +
                                            	"<th colspan='2'>Prenda</th>" +
                                            "</tr>" +
                                            "<tr>" + 
                                            	"<th>Color</th>" +
                                            	colores +
                                            	"<th colspan='2'>Color</th>" +
                                            "</tr>" +
                                            "<tr>" + 
	                                            "<th>Empleado(a)</th>" +
	                                            tdVacios +
	                                        	"<th>Total</th>" +
	                                        	"<th>Editar</th>" +
                                            "</tr>" +
                                        "</thead>" +
                                        "<tbody>" +
                                        "</tbody>" +
                                    "</table>");
	
			table1 = $('.tablaConcentrado')
			    .DataTable({
			    	"scrollX": true,
			        "order": [[ 0, "asc" ]],
			        "pageLength": 5,
			        "responsive": true,
			        "stateSave":true,
			        "drawCallback": function() {
			            $('.popoverxd').popover({
			                container: 'body',
			                trigger: 'hover'
			            });
			          },
			          "columnDefs": [{
			              "type": "html",
			              "targets": '_all'
			            }],
			        "lengthMenu": [
			            [5, 10, 25, 50, 100],
			            [5, 10, 25, 50, 100]
			        ],
			        "language": {
			            "sProcessing": "Procesando...",
			            "sLengthMenu": "Mostrar _MENU_ registros",
			            "sZeroRecords": "No se encontraron resultados",
			            "sEmptyTable": "NingÃºn dato disponible en esta tabla =(",
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
			                "sLast": "Ãšltimo",
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
			
			new $.fn.dataTable.FixedHeader(table1);

			var ArrayFinal = [];
			for(var empleado = 0; empleado < ListaEmpleados.length; empleado++){
				
				//Variables declaradas
				var EmpleadoPrenda = {};
				var EmpleadoPrendaPosible = ArrayFinal.filter(p => p.id == ListaEmpleados[empleado][2]);
				//Se revisa si es que existe, si existe, se utiliza ese objeto previamente creado
				if(EmpleadoPrendaPosible.length > 0){
					EmpleadoPrenda = ArrayFinal.filter(p => p.id == ListaEmpleados[empleado][2]);
					
					//Se les agregan los modelos de prendas
					for(var prenda = 0; prenda < PrendasArray.length; prenda++){
						EmpleadoPrenda["modelo-" + prenda] = PrendasArray[prenda]["identificador"];
						//La cantidad
						EmpleadoPrenda["cantidad del modelo-" + PrendasArray[prenda]["identificador"]] = 0;
					}
				}
				else{
					EmpleadoPrenda = {id: ListaEmpleados[empleado][2], nombre: ListaEmpleados[empleado][1]};
					
					//Se les agregan los modelos de prendas
					for(var prenda = 0; prenda < PrendasArray.length; prenda++){
						EmpleadoPrenda["modelo-" + prenda] = PrendasArray[prenda]["identificador"];
						//La cantidad
						EmpleadoPrenda["cantidad del modelo-" + PrendasArray[prenda]["identificador"]] = 0;
					}
					
					ArrayFinal.push(EmpleadoPrenda);
				}
			}
			
			//Se organiza el objeto por ultima vez para obtener las cantidades
			for(var count = 0; count < ArrayFinal.length; count++){
				for(var count2 = 0; count2 < ListaEmpleados.length; count2++){
					if(ArrayFinal[count].id == ListaEmpleados[count2][2]){
						ArrayFinal[count]["cantidad del modelo-" + ListaEmpleados[count2][5]] = ListaEmpleados[count2][6]; 
					}
				}
			}

			
			
			//Otro ciclon xd
			for(var contador = 0; contador < ArrayFinal.length; contador++){
				var ArrayIndividual = [];
				var CantidadesASumar = [];
				ArrayIndividual[0] = ArrayFinal[contador]["nombre"];
				
				for(var contador2 = 0; contador2 < PrendasArray.length; contador2++){
					ArrayIndividual[PrendasArray[contador2]["posicion"]] = ArrayFinal[contador]["cantidad del modelo-" + PrendasArray[contador2]["identificador"]];
					CantidadesASumar[contador2] = ArrayFinal[contador]["cantidad del modelo-" + PrendasArray[contador2]["identificador"]];;
				}
				
				ArrayIndividual[contador2 + 1] = CantidadesASumar.reduce(function(a, b){ return a + b;}, 0);
				ArrayIndividual[contador2 + 2] = "<td class='text-center'>" +
													"<button class='btn btn-warning btn-circle btn-sm popoverxd' onclick=\"EditarRegistro(\'" + ArrayFinal[contador]["id"] + "'\, \'" + ArrayFinal[contador]["nombre"] + "\');\" data-container='body' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>" +				
												"</td>";
				table1.rows.add( [ ArrayIndividual ] ).draw();
			}		
}


function CrearNuevaTablaEspecial(data, ListaEmpleados){
	//Se crea el row
	var coordinados = "";
	var modelos = "";
	var telas = "";
	var prendas = "";
	var colores = "";
	var tdVacios = "";
	var tdVaciosParaCuerpo = "";
	
	//Array de Objetos para guardar los modelos
	PrendasArray = [];
	for(var j = 0; j < data.length; j++){
		var PrendasObjeto = {posicion: j + 1, identificador: data[j][0], modelo: data[j][1], tela: data[j][2], color: data[j][5]};
		coordinados += "<td>" + data[j][3] + "</td>";
		modelos += "<td>" + data[j][1] + "</td>";
		telas += "<td>" + data[j][2] + "</td>";
		prendas += "<td>" + data[j][4] + "</td>";
		colores += "<td>" + data[j][5] + "</td>";
		tdVacios += "<td></td>";
		tdVaciosParaCuerpo += "<td></td>" + ",";
		
		PrendasArray.push(PrendasObjeto);
		PrendasObjeto = null;
	}
	
	$('#ContenedorDeTabla').append("<table class='table table-bordered tablaConcentrado' id='TablaGeneral'>" +
                                        "<thead>" +
                                            "<tr>" +
                                                "<th>Coordinado</th>" +
                                                coordinados + 
                                                "<th colspan='2'>Coordinado</th>" +
                                            "</tr>" +
                                            "<tr>" +
                                                "<th>Modelo</th>" +
                                                modelos + 
                                                "<th colspan='2'>Modelo</th>" +
                                            "</tr>" +
                                            "<tr>" + 
                                                "<th>Clave Tela</th>" +
                                                telas +
                                                "<th colspan='2'>Clave Tela</th>" +
                                            "</tr>" +
                                            "<tr>" +
                                            	"<th>Prenda</th>" + 
                                            	prendas +
                                            	"<th colspan='2'>Prenda</th>" +
                                            "</tr>" +
                                            "<tr>" + 
                                            	"<th>Color</th>" +
                                            	colores +
                                            	"<th colspan='2'>Color</th>" +
                                            "</tr>" +
                                            "<tr>" + 
	                                            "<th>Empleado(a)</th>" +
	                                            tdVacios +
	                                        	"<th>Total</th>" +
	                                        	"<th>Editar</th>" +
                                            "</tr>" +
                                        "</thead>" +
                                        "<tbody>" +
                                        "</tbody>" +
                                    "</table>");
	
			table1 = $('.tablaConcentrado')
			    .DataTable({
			    	"scrollX": true,
			        "order": [[ 0, "asc" ]],
			        "pageLength": 5,
			        "responsive": true,
			        "stateSave":true,
			        "drawCallback": function() {
			            $('.popoverxd').popover({
			                container: 'body',
			                trigger: 'hover'
			            });
			          },
			          "columnDefs": [{
			              "type": "html",
			              "targets": '_all'
			            }],
			        "lengthMenu": [
			            [5, 10, 25, 50, 100],
			            [5, 10, 25, 50, 100]
			        ],
			        "language": {
			            "sProcessing": "Procesando...",
			            "sLengthMenu": "Mostrar _MENU_ registros",
			            "sZeroRecords": "No se encontraron resultados",
			            "sEmptyTable": "NingÃºn dato disponible en esta tabla =(",
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
			                "sLast": "Ãšltimo",
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
			
			new $.fn.dataTable.FixedHeader(table1);

			var ArrayFinal = [];
			for(var empleado = 0; empleado < ListaEmpleados.length; empleado++){
				
				//Variables declaradas
				var EmpleadoPrenda = {};
				var EmpleadoPrendaPosible = ArrayFinal.filter(p => p.id == ListaEmpleados[empleado][2]);
				//Se revisa si es que existe, si existe, se utiliza ese objeto previamente creado
				if(EmpleadoPrendaPosible.length > 0){
					EmpleadoPrenda = ArrayFinal.filter(p => p.id == ListaEmpleados[empleado][2]);
					
					//Se les agregan los modelos de prendas
					for(var prenda = 0; prenda < PrendasArray.length; prenda++){
						EmpleadoPrenda["modelo-" + prenda] = PrendasArray[prenda]["identificador"];
						//La cantidad
						EmpleadoPrenda["cantidad del modelo-" + PrendasArray[prenda]["identificador"]] = 0;
					}
				}
				else{
					EmpleadoPrenda = {id: ListaEmpleados[empleado][2], nombre: ListaEmpleados[empleado][1]};
					
					//Se les agregan los modelos de prendas
					for(var prenda = 0; prenda < PrendasArray.length; prenda++){
						EmpleadoPrenda["modelo-" + prenda] = PrendasArray[prenda]["identificador"];
						//La cantidad
						EmpleadoPrenda["cantidad del modelo-" + PrendasArray[prenda]["identificador"]] = 0;
					}
					
					ArrayFinal.push(EmpleadoPrenda);
				}
			}
			
			//Se organiza el objeto por ultima vez para obtener las cantidades
			for(var count = 0; count < ArrayFinal.length; count++){
				for(var count2 = 0; count2 < ListaEmpleados.length; count2++){
					if(ArrayFinal[count].id == ListaEmpleados[count2][2]){
						ArrayFinal[count]["cantidad del modelo-" + ListaEmpleados[count2][5]] = ListaEmpleados[count2][7]; 
					}
				}
			}

			
			
			//Otro ciclon xd
			for(var contador = 0; contador < ArrayFinal.length; contador++){
				var ArrayIndividual = [];
				var CantidadesASumar = [];
				ArrayIndividual[0] = ArrayFinal[contador]["nombre"];
				
				for(var contador2 = 0; contador2 < PrendasArray.length; contador2++){
					ArrayIndividual[PrendasArray[contador2]["posicion"]] = ArrayFinal[contador]["cantidad del modelo-" + PrendasArray[contador2]["identificador"]];
					CantidadesASumar[contador2] = ArrayFinal[contador]["cantidad del modelo-" + PrendasArray[contador2]["identificador"]];;
				}
				
				ArrayIndividual[contador2 + 1] = CantidadesASumar.reduce(function(a, b){ return a + b;}, 0);
				ArrayIndividual[contador2 + 2] = "<td class='text-center'>" +
													"<button class='btn btn-warning btn-circle btn-sm popoverxd' onclick=\"EditarRegistro(\'" + ArrayFinal[contador]["id"] + "'\, \'" + ArrayFinal[contador]["nombre"] + "\');\" data-container='body' data-placement='top' data-content='Editar'><i class='fas fa-pen'></i></button>" +				
												"</td>";
				table1.rows.add( [ ArrayIndividual ] ).draw();
			}		
}