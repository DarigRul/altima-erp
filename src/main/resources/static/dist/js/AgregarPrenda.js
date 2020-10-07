$(document).ready(function () {

	//Validacion para que no se repita el nombre de la prenda
	$("#DescripcionPrenda").keyup(function (key) {
		$.ajax({
			type: "GET",
			url: "/validacion_descripcion_prenda",
			data: { valor: this.value },
			success: (data) => {
				console.log($('#NombreOriginalPrenda').val());
				if ($('#NombreOriginalPrenda').val() == this.value) {
					//
				}
				else {
					if (data) {
						$('#WarningNombrePrenda').css("display", "none");
						BanderaDescripcionRepetida = true;
					}
					else {
						$('#WarningNombrePrenda').css("display", "inline");
						BanderaDescripcionRepetida = false;
					}
				}
			},
			error: (e) => {
				console.log(e);
			}
		});
	});

	$("#file").change(function () {
		var $this = $(this), $clone = $this.clone();
		$this.after($clone).appendTo($('#ContenedorFrente'));
		CambiarImgFrente = true;
	});
	$("#file2").change(function () {
		var $this = $(this), $clone = $this.clone();
		$this.after($clone).appendTo($('#ContenedorEspalda'));
		CambiarImgEspalda = true;
	});
});

function RecogerDatosPrimeraParte() {
	//var today = new Date();
	//var actual = today.getFullYear() + '-' + today.getMonth() + '-' + today.getDate() + ' ' + today.getHours() + ':' + today.getMinutes() + ':' + today.getSeconds();
	objeto_prenda['idFamiliaPrenda'] = $('#TipoPrenda').val();
	objeto_prenda['tipoPrenda'] = $('#TipoPrenda').val();
	objeto_prenda['creadoPor'] = "Adan";
	objeto_prenda['actualizadoPor'] = "Adan";
	objeto_prenda['numeroPrenda'] = "1";
	objeto_prenda['descripcionPrenda'] = $('#DescripcionPrenda').val();
	objeto_prenda['generoPrenda'] = $('#GeneroPrenda').val();
	objeto_prenda['detallePrenda'] = $('#DetallePrenda').val();
	objeto_prenda['notaEspecial'] = $('#NotaEspecial').val();
	objeto_prenda['consumoTela'] = 0.0;
	objeto_prenda['consumoForro'] = 0.0;
	objeto_prenda['precio'] = 0.0;
	objeto_prenda['precioLocalActual'] = 0.0;
	objeto_prenda['precioLocalAnterior'] = 0.0;
	objeto_prenda['precioForaneoActual'] = 0.0;
	objeto_prenda['precioForaneoAnterior'] = 0.0;
	objeto_prenda['cvePrenda'] = 0;
	objeto_prenda['tipoLargo'] = "Ninguno";
	objeto_prenda['especificacion'] = "Ninguna";
	objeto_prenda['modeloBoton'] = "Ninguno";
	objeto_prenda['estatusRecepcionMuestra'] = "Ninguno";
	objeto_prenda['devolucion'] = "";
	objeto_prenda['precioMprod'] = 0.0;
	objeto_prenda['precioMmuestra'] = 0.0;
	objeto_prenda['categoria'] = "Ninguna";
	objeto_prenda['totalPrendas'] = 0;
	objeto_prenda['mostrar'] = "Ninguna";
	objeto_prenda['idLookup'] = 0;
	objeto_prenda['idLookup2'] = 0;
	objeto_prenda['idLookup3'] = 0;
	if ($('#combi').is(':checked')) {
		objeto_prenda['combinacion'] = "1";
	}
	else {
		objeto_prenda['combinacion'] = "0";
	}
	if ($('#ticket').is(':checked')) {
		objeto_prenda['imprimirEtiquetas'] = "1";
	}
	else {
		objeto_prenda['imprimirEtiquetas'] = "0";
	}
}
function RecogerDatosSegundaParte() {
	objeto_prenda['detalleConfeccion'] = $('#DetalleConfeccion').val();
	//Campo en duro de lo de los marcadores, cambiarlo posteriormente.
	objeto_prenda['marcadores'] = "1";
}
function RecogerDatosTerceraParte() {
	var id = null;
	$('#tablita tr').each(function () {
		if (id == null) {
			id = $(this).find('td').eq(0).html()
		}
		else {
			id = $(this).find('td').eq(0).html() + "," + id;
		}
	});
	return id;
}
//Funcion para agregar un nuevo Material desde la vista agregar-confirmar prenda y editar-prenda
function AgregarElementoListaMateriales() {
	var id = $('#ListaDeMateriales').val();
	$('#AgregarElementoMaterial').prop('disabled', true);
	$('#SiguienteTerceraPestana').prop('disabled', true);
	//Este es un boolean para saber si ya se encontro un registro parecido
	var EncontreUnMaterialIgual = false;
	//Si ya existe un registro asi en el objeto que tenemos, se le suma uno a la cantidad.
	for (k = 0; k < objeto_materiales.length; k++) {
		if (objeto_materiales[k].id == id) {
			var can = objeto_materiales[k].cantidad;
			objeto_materiales[k].cantidad = 0;
			objeto_materiales[k].cantidad = (can + 1);
			$('#CantidadMaterial-' + objeto_materiales[k].identidad).val((can + 1));
			$('#AgregarElementoMaterial').prop('disabled', false);
			$('#SiguienteTerceraPestana').prop('disabled', false);
			EncontreUnMaterialIgual = true;
			console.log("si coincidio");
		}
	}
	//Solicitud Ajax para obtener los demas campos.
	if (!EncontreUnMaterialIgual) {
		$.ajax({
			type: "GET",
			url: "/detalle_material",
			data: { id },
			success: (data) => {
				console.log(data);
				$('#AgregarElementoMaterial').prop('disabled', false);
				$('#SiguienteTerceraPestana').prop('disabled', false);
				var identidad = id + '_' + data[0][1];
				var temp = {
					identidad: identidad, id: data[0][0], NoMaterial: data[0][1], Nombre: data[0][2], Clasificacion: data[0][3], Tamanio: data[0][5] + ' ' + data[0][4],
					Modelo: data[0][6], Proceso: data[0][7], cantidad: 1, cantidadRepuesto: 0
				};
				objeto_materiales.push(temp);
				console.log(temp);
				$('#CuerpoTablaMateriales').prepend("<tr id='RemoverElemento-" + identidad + "'>" +
					"<td>" + data[0][1] + "</td>" +
					"<td>" + data[0][2] + "</td>" +
					"<td>" + data[0][3] + "</td>" +
					"<td>" + data[0][5] + "</td>" +
					"<td>" + data[0][4] + "</td>" +
					"<td>" + data[0][6] + "</td>" +
					"<td>" + data[0][7] + "</td>" +
					"<td><input class='form-control' step='any' type='number' id='CantidadMaterial-" + identidad + "' value='1' onchange=\"CambiarCantidadMaterial(\'" + identidad + "\');\"/></td>" +
					"<td class='tdcenter'>" +
					"<button type='button' onclick=\"QuitarMaterial(\'" + identidad + "\');\" class='btn icon-btn btn-danger text-white btn_remove'><span class='btn-glyphicon spancircle fas fa-times fa-lg img-circle text-danger'></span>Eliminar</button>" +
					"</td>" +
					"</tr>");
			},
			error: (e) => {
				console.log(e);
			}
		});
	}//Cierra el if
}
function QuitarMaterial(identidad) {
	$('#RemoverElemento-' + identidad).remove();
	var removeIndex = objeto_materiales.map(function (item) { return item.identidad; }).indexOf(identidad);
	objeto_materiales.splice(removeIndex, 1);
}
function CambiarCantidadMaterial(identidad) {
	var cantidad = $('#CantidadMaterial-' + identidad).val();
	var CambiarCantidad = objeto_materiales.map(function (item) { return item.identidad; }).indexOf(identidad);

	objeto_materiales[CambiarCantidad].cantidad = 0;
	objeto_materiales[CambiarCantidad].cantidad = cantidad;
	if (cantidad <= 0) {
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'La cantidad debe de ser mayor a 0!'
		})
		objeto_materiales[CambiarCantidad].cantidad = 1;
		document.getElementById('CantidadMaterial-' + identidad).value = 1;
	}
	console.log(objeto_materiales[CambiarCantidad]);
}
function CambiarCantidadMaterialR(identidad) {
	var cantidad = $('#CantidadMaterialR-' + identidad).val();
	var CambiarCantidad = objeto_materiales.map(function (item) { return item.identidad; }).indexOf(identidad);

	objeto_materiales[CambiarCantidad].cantidadRepuesto = 0;
	objeto_materiales[CambiarCantidad].cantidadRepuesto = cantidad;
	if (cantidad < 0) {
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'La cantidad debe de ser mayor o igual a 0!'
		})
		objeto_materiales[CambiarCantidad].cantidadRepuesto = 1;
		document.getElementById('CantidadMaterialR-' + identidad).value = 1;
	}
	console.log(objeto_materiales);
}


function Guardar() {

	$('#ElDeGuardar').remove();
	$('#Guardando').css('display', 'block');

	RecogerDatosPrimeraParte();
	RecogerDatosSegundaParte();
	var token = $('#token').val();
	var header = $('#token').val();

	if (accion == "editar") {
		//Solicitud Ajax
		$.ajax({
			type: "POST",
			url: "/editar_prenda",
			data: {
				"_csrf": $('#token').val(),
				"disenioprenda": JSON.stringify(objeto_prenda)
			},
			success: (data) => {
				console.log(data);
				console.log(prendasmarcadores);
				$('#ContenedorBotonAgregarOtro').append("<input type='hidden' name='accion' id='accion' value='editar'/>");
				$.ajax({
					type: "POST",
					url: "/guardar_final",
					data: {
						"_csrf": $('#token').val(),
						"objeto_materiales": JSON.stringify(objeto_materiales),
						"objeto_patronajes": JSON.stringify(objeto_patronajes),
						"objeto_marcadores": RecogerDatosTerceraParte(),
						"clientes": JSON.stringify($('#ClientePrenda').val()),
						"accion": accion,
						"idPrenda": data
					},
					success: (data) => {
						$('#BotonBloquearGuardar').prop('disabled', false);
						$('#FormImagenes').click();
					},
					failure: function (errMsg) {
						alert(errMsg);
					}
				});
			},
			failure: function (errMsg) {
				alert(errMsg);
			}
		});
	}

	if (accion == "agregar") {
		//Solicitud Ajax
		$.ajax({
			type: "POST",
			url: "/guardar_prenda",
			data: {
				"_csrf": $('#token').val(),
				"disenioprenda": JSON.stringify(objeto_prenda)
			},
			success: (data) => {
				$('#ContenedorBotonAgregarOtro').append("<input type='hidden' name='idPrenda' id='idPrenda' value='" + data + "'/>");
				$('#ContenedorBotonAgregarOtro').append("<input type='hidden' name='accion' id='accion' value='agregar'/>");
				$.ajax({
					type: "POST",
					url: "/guardar_final",
					data: {
						"_csrf": $('#token').val(),
						"objeto_materiales": JSON.stringify(objeto_materiales),
						"objeto_patronajes": JSON.stringify(objeto_patronajes),
						"objeto_marcadores": RecogerDatosTerceraParte(),
						"clientes": JSON.stringify($('#ClientePrenda').val()),
						"accion": $('#accionPag').val(),
						"idPrenda": data
					},
					success: (data) => {
						$('#BotonBloquearGuardar').prop('disabled', false);
						console.log(data);
						//window.location.href = '/prendas';
						$('#FormImagenes').click();
					},
					failure: function (errMsg) {
						alert(errMsg);
					}
				});
			},
			failure: function (errMsg) {
				alert(errMsg);
			}
		});
	}

	if (accion == "copiar") {
		console.log(objeto_prenda);
		//Solicitud Ajax
		$.ajax({
			type: "POST",
			url: "/copiar_prenda",
			data: {
				"_csrf": $('#token').val(),
				"disenioprenda": JSON.stringify(objeto_prenda)
			},
			success: (data) => {
				$('#ContenedorBotonAgregarOtro').append("<input type='hidden' name='idPrenda' id='idPrenda' value='" + data + "'/>");
				$('#ContenedorBotonAgregarOtro').append("<input type='hidden' name='accion' id='accion' value='copiar'/>");
				$.ajax({
					type: "POST",
					url: "/guardar_final",
					data: {
						"_csrf": $('#token').val(),
						"objeto_materiales": JSON.stringify(objeto_materiales),
						"objeto_patronajes": JSON.stringify(objeto_patronajes),
						"objeto_marcadores": RecogerDatosTerceraParte(),
						"clientes": JSON.stringify($('#ClientePrenda').val()),
						"accion": accion,
						"idPrenda": data
					},
					success: (data) => {
						$('#BotonBloquearGuardar').prop('disabled', false);
						$('#FormImagenes').click();
					},
					failure: function (errMsg) {
						alert(errMsg);
					}
				});
			},
			failure: function (errMsg) {
				alert(errMsg);
			}
		});
	}
}
//Esta valida que los campos esten llenos dentro de la primer pestaña
function ValidarPrimerPestana() {
	if ($('#DescripcionPrenda').val() != "" && $('#DetallePrenda').val() != "" && $('#GeneroPrenda').val() != ""
		&& $('#file-input-1').val() != "" && $('#file-input-2').val() != "" && BanderaDescripcionRepetida) {
		var nombres = [];
		var idContenedores = [];
		var bandera = true;
		var banderaImagenes = true;

		//Validacion de los nombres de imagen
		if (accion == "editar") {
			//Se obtienen los divs visibles
			for (var i = 1; i < 7; i++) {
				if ($('#Contenedor-' + i).css('display') == 'block') {
					nombres[i] = $('#name-edit-' + i).val();
					idContenedores[i] = i;
				}
			}
		}
		else {
			//Se obtienen los divs visibles
			for (var i = 1; i < 7; i++) {
				if ($('#Contenedor-' + i).css('display') == 'block') {
					nombres[i] = $('#name-' + i).val();
					idContenedores[i] = i;
				}
			}
		}

		//Se ordena el array
		nombres = nombres.filter(item => item);

		//Se recorren los que estan visibles y si coincide alguno, se pone falso
		for (j = 1; j < ((nombres.length) + 1); j++) {
			for (h = 1; h < ((nombres.length) + 1); h++) {
				if (nombres[j] == nombres[h] && j != h) {
					bandera = false;
					console.log("aqui");
				}
			}
		}

		//Se recorren los contenedores visibles, si no tienen imganes se ponen falso
		if (accion == "editar") {
			for (k = 1; k < idContenedores.length; k++) {
				if ($("#img-edit-" + k).attr('src') == "/dist/img/preview.png" && k != 1 && k != 2) {
					banderaImagenes = false;
					console.log("esta vacia la: " + k);
				}
			}
		}
		else {
			for (k = 1; k < idContenedores.length; k++) {
				if ($("#img-" + k).attr('src') == "/dist/img/preview.png" && k != 1 && k != 2) {
					banderaImagenes = false;
					console.log("esta vacia la: " + k);
				}
			}
		}

		//Se recorren los contenedores visibles, si no tienen nombre las imagenes se ponen falso
		if (accion == "editar") {
			for (k = 1; k < idContenedores.length; k++) {
				if ($('#name-edit-' + k).val() == "" && k != 1 && k != 2) {
					banderaImagenes = false;
				}
			}
		}
		else {
			for (k = 1; k < idContenedores.length; k++) {
				if ($('#name-' + k).val() == "" && k != 1 && k != 2) {
					banderaImagenes = false;
				}
			}
		}

		if (bandera) {
			if (banderaImagenes) {
				$('#SiguientePrimeraPestana').click();
			}
			else {
				Swal.fire({
					icon: 'error',
					title: 'Error',
					text: 'Debes llenar todos los campos correctamente.!'
				})
			}
		}
		else {
			Swal.fire({
				icon: 'error',
				title: 'Error',
				text: 'Los nombres de las imagenes no se deben repetir.!'
			})
		}
	}
	else {
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'Debes llenar todos los campos correctamente.!'
		})
	}
}
//Esto solo valida que los campos esten llenos dentro de la segunda pestaña
function ValidarSegundaPestana() {

	var rowCount = $('#tablita tr').length;
	if ($('#DetalleConfeccion').val() != "" && rowCount > 0) {
		$('#SiguienteSegundaPestana').click();
	}
	else {
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'Debes llenar todos los campos correctamente.'
		})
	}
}
//Esto valida que la tercer pestana, de materiales, tenga al menos un material
function ValidarTerceraPestana() {
	if (objeto_materiales.length === 0) {
		//$('#AlertaTerceraPestana').css('display', 'block');
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'Debes agregar un material.'
		})
	}
	else {

		var HayTelaPrincipal = false;

		for (i = 0; i < objeto_materiales.length; i++) {
			if (objeto_materiales[i].Nombre == "Tela principal" || objeto_materiales[i].Nombre == "Tela Principal") {
				HayTelaPrincipal = true;
			}
		}

		if (HayTelaPrincipal) {
			$('#AlertaTerceraPestana').css('display', 'none');
			$('#SiguienteTerceraPestana').click();
			//AsignarID(id);	
		}
		else {
			Swal.fire({
				icon: 'error',
				title: 'Error',
				text: 'Debes agregar una tela principal!'
			})
		}
	}
}
function ValidarCuartaPestana() {
	var j = 0;
	var validacion = true;
	$('#CuerpoPatronaje tr').each(function () {
		j++;
	});
	if (j == 0) {
		//$('#AlertaCuartaPestana').css('display', 'block');
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'Debes agregar un elemento.'
		})
	}
	else {
		//Se deshabilita el boton
		//$('#AlertaCuartaPestana').css('display', 'none');
		$('#CuerpoPatronaje tr').each(function () {
			if ($('#CantidadTela' + $(this).find('td').eq(0).html()).val() == "" || $('#CantidadForro' + $(this).find('td').eq(0).html()).val() == "" || $('#CantidadEntretela' + $(this).find('td').eq(0).html()).val() == "" || $('#CantidadTelaSecundaria' + $(this).find('td').eq(0).html()).val() == "" || $('#CantidadForroSecundario' + $(this).find('td').eq(0).html()).val() == "") {
				Swal.fire({
					icon: 'error',
					title: 'Error',
					text: 'Todos los campos deben de estar llenos!'
				})

				objeto_patronajes = [];
				console.log("que pedo con esta pus " + objeto_patronajes);
				validacion = false;
				return true;
			}
			if ($('#CantidadTela' + $(this).find('td').eq(0).html()).val() < 0 || $('#CantidadForro' + $(this).find('td').eq(0).html()).val() < 0 || $('#CantidadEntretela' + $(this).find('td').eq(0).html()).val() < 0 || $('#CantidadTelaSecundaria' + $(this).find('td').eq(0).html()).val() < 0 || $('#CantidadForroSecundario' + $(this).find('td').eq(0).html()).val() < 0) {
				Swal.fire({
					icon: 'error',
					title: 'Error',
					text: 'No puede haber cantidades negativas!'
				})

				objeto_patronajes = [];
				console.log("que pedo con esta pus " + objeto_patronajes);
				validacion = false;
				return true;
			}
			if (validacion === true) {
				_id = $(this).find('td').eq(0).html();
				var temp = { id: $(this).find('td').eq(0).html(), cantidadTela: $('#CantidadTela' + $(this).find('td').eq(0).html()).val(), cantidadForro: $('#CantidadForro' + $(this).find('td').eq(0).html()).val(), cantidadEntretela: $('#CantidadEntretela' + $(this).find('td').eq(0).html()).val(), cantidadTelaSecundaria: $('#CantidadTelaSecundaria' + $(this).find('td').eq(0).html()).val(), cantidadForroSecundario: $('#CantidadForroSecundario' + $(this).find('td').eq(0).html()).val() };
				objeto_patronajes.push(temp);
			}
		});
		if (validacion === true) {
			$('#SiguienteCuartaPestana').click();
			$('#BotonBloquearGuardar').prop('disabled', true);
		}
	}
}
//Este valida que las cantidades del patronaje no esten nulas cuando se confirma una prenda
function ValidarCantidadesPatronaje() {
	if ($('#CantidadTela').val() != "" && $('#CantidadTelaSecundaria').val() != "" && $('#CantidadForro').val() != "" && $('#CantidadForroSecundario').val() != "" && $('#CantidadEntretela').val() != "") {
		//$('#AlertaCantidadesPatronaje').css('display', 'none');
		console.log("si hace la entrasion");
		AgregarElementoListaPatronaje();
	}
	else {
		//$('#AlertaCantidadesPatronaje').css('display', 'block');
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'Debes agregar las cantidades.'
		})
	}
}
//Cambios Uriel ***********************Marcadores
function guardarMarcador() {
	var _id = document.getElementById("marcador_").value;
	var _text = document.getElementById("marcador_").options[document.getElementById("marcador_").selectedIndex].text;
	var fila = "<tr><td style='display: none;'>" + _id + "</td><td>" + _text + "</td><td class='text-center'>" + '<button type="button" name="remove" id="' + _id + '"onclick="eliminarMarcador(this)" class="btn icon-btn btn-danger text-white btn_remove"><span class="btn-glyphicon spancircle fas fa-times fa-lg img-circle text-danger"></span>Eliminar</button></td></tr>';
	var campo_id;
	$('#tablita tr').each(function () {
		if ($(this).find('td').eq(0).html() != null) {
			console.log("entra1");
			if (campo_id == null) {
				campo_id = $(this).find('td').eq(0).html()
			}
			else {
				campo_id = $(this).find('td').eq(0).html() + "," + campo_id;
			}
		}
	});
	if (campo_id != null) {
		console.log("entra2 " + campo_id);
		for (var j = 0; j <= campo_id.split(",").length; j++) {
			if (_id == campo_id.split(",")[j]) {
				console.log("entra3 " + _id);
				Swal.fire({
					icon: 'error',
					title: 'Error',
					text: 'Ya has agregado ese marcador.'
				})
				return false;
			}
		}
	}
	var btn = document.createElement("TR");
	btn.innerHTML = fila;
	document.getElementById("tablita").appendChild(btn);
}
function eliminarMarcador(t) {
	var td = t.parentNode;
	var tr = td.parentNode;
	var table = tr.parentNode;
	table.removeChild(tr);
}
function guardarPatronaje() {
	var _id = document.getElementById("ListaPatronaje").value;
	var _text = document.getElementById("ListaPatronaje").options[document.getElementById("ListaPatronaje").selectedIndex].text;
	var fila = "<tr><td style='display: none;'>" + _id + "</td>" +
		"<td>" + _text + "</td>" +
		"<td>" + '<input type="number" class="form-control" min="0" placeholder="10" value="0" id="CantidadTela' + _id + '">' + "</td>" +
		"<td>" + '<input type="number" class="form-control" min="0" placeholder="10" value="0" id="CantidadTelaSecundaria' + _id + '">' + "</td>" +
		"<td>" + '<input type="number" class="form-control" min="0" placeholder="10" value="0" id="CantidadForro' + _id + '">' + "</td>" +
		"<td>" + '<input type="number" class="form-control" min="0" placeholder="10" value="0" id="CantidadForroSecundario' + _id + '">' + "</td>" +
		"<td>" + '<input type="number" class="form-control" min="0" placeholder="10" value="0" id="CantidadEntretela' + _id + '">' + "</td>" +
		"<td class='tdcenter'>" + '<button type="button" name="remove" id="' + _id + '"onclick="eliminarPatronaje(this)" class="btn icon-btn btn-danger text-white btn_remove"><span class="btn-glyphicon spancircle fas fa-times fa-lg img-circle text-danger"></span>Eliminar</button></td>' +
		'</tr>';
	var campo_id;
	$('#CuerpoPatronaje tr').each(function () {
		if ($(this).find('td').eq(0).html() != null) {
			console.log("entra1");
			if (campo_id == null) {
				campo_id = $(this).find('td').eq(0).html()
			}
			else {
				campo_id = $(this).find('td').eq(0).html() + "," + campo_id;
			}
		}
	});
	if (campo_id != null) {
		console.log("entra2 " + campo_id);
		for (var j = 0; j <= campo_id.split(",").length; j++) {
			if (_id == campo_id.split(",")[j]) {
				console.log("entra3 " + _id);
				Swal.fire({
					icon: 'error',
					title: 'Error',
					text: 'Ya has agregado ese elemento.'
				})
				return false;
			}
		}
	}
	var btn = document.createElement("TR");
	btn.innerHTML = fila;
	document.getElementById("CuerpoPatronaje").prepend(btn);
}
function eliminarPatronaje(t) {
	var td = t.parentNode;
	var tr = td.parentNode;
	var table = tr.parentNode;
	table.removeChild(tr);
}


function ReloadList() {

	$('#BotonRecargaListas').html("cargando awanta...");
	//Se rescatan valores de los selects actuales si es que los tienen
	var TipoPrenda = $('#TipoPrenda').val();
	var GeneroPrenda = $('#GeneroPrenda').val();
	var ClientePrenda = $('#ClientePrenda').val();
	var MarcadorPrenda = $('#marcador_').val();
	var MaterialesPrenda = $('#ListaDeMateriales').val();
	var PatronajePrenda = $('#ListaPatronaje').val();

	//Se limpian los select 
	var SelectTipoPrenda = $('#TipoPrenda');
	SelectTipoPrenda.selectpicker('val', '');
	SelectTipoPrenda.find('option').remove();
	var SelectGeneroPrenda = $('#GeneroPrenda');
	SelectGeneroPrenda.selectpicker('val', '');
	SelectGeneroPrenda.find('option').remove();
	var SelectClientePrenda = $('#ClientePrenda');
	SelectClientePrenda.selectpicker('val', '');
	SelectClientePrenda.find('option').remove();
	var Selectmarcador_ = $('#marcador_');
	Selectmarcador_.selectpicker('val', '');
	Selectmarcador_.find('option').remove();
	var SelectListaDeMateriales = $('#ListaDeMateriales');
	SelectListaDeMateriales.selectpicker('val', '');
	SelectListaDeMateriales.find('option').remove();
	var SelectListaPatronaje = $('#ListaPatronaje');
	SelectListaPatronaje.selectpicker('val', '');
	SelectListaPatronaje.find('option').remove();

	//Se obtienen las nuevas listas
	$.ajax({
		type: "GET",
		url: "/nuevas_listas",
		data: {
			"_csrf": $('#token').val()
		},
		success: (res) => {

			//Select de Prenda
			for (i = 0; i < res[0].length; i++) {
				SelectTipoPrenda.append("<option value='" + res[0][i][0] + "'>" + res[0][i][1] + "</option>");
			}
			SelectTipoPrenda.val(TipoPrenda);
			SelectTipoPrenda.selectpicker("refresh");

			//Select de Genero
			for (i = 0; i < res[1].length; i++) {
				SelectGeneroPrenda.append("<option value='" + res[1][i].idLookup + "'>" + res[1][i].nombreLookup + "</option>");
			}
			SelectGeneroPrenda.val(GeneroPrenda);
			SelectGeneroPrenda.selectpicker("refresh");

			//Select de Cliente
			for (i = 0; i < res[2].length; i++) {
				SelectClientePrenda.append("<option value='" + res[2][i].idCliente + "'>" + res[2][i].nombre + "</option>");
			}
			SelectClientePrenda.append("<option value='0'>Cliente General</option>");
			SelectClientePrenda.val(ClientePrenda);
			SelectClientePrenda.selectpicker("refresh");

			//Select de Marcadores
			for (i = 0; i < res[3].length; i++) {
				Selectmarcador_.append("<option value='" + res[3][i].idLookup + "'>" + res[3][i].nombreLookup + "</option>");
			}
			Selectmarcador_.val(MarcadorPrenda);
			Selectmarcador_.selectpicker("refresh");

			//Select de Materiales
			for (i = 0; i < res[4].length; i++) {
				SelectListaDeMateriales.append("<option value='" + res[4][i][0] + "'>" + res[4][i][1] + "</option>");
			}
			SelectListaDeMateriales.val(MaterialesPrenda);
			SelectListaDeMateriales.selectpicker("refresh");

			//Select de Patronajes
			for (i = 0; i < res[5].length; i++) {
				SelectListaPatronaje.append("<option value='" + res[5][i][0] + "'>" + res[5][i][2] + "</option>");
			}
			SelectListaPatronaje.val(PatronajePrenda);
			SelectListaPatronaje.selectpicker("refresh");

			$('#BotonRecargaListas').html("Reload again");
		},
		failure: function (errMsg) {
			alert(errMsg);
		}
	});

	//Se actualizan las listas con  refresh
	$('#GeneroPrenda').selectpicker("refresh");
	$('#ClientePrenda').selectpicker("refresh");
	$('#marcador_').selectpicker("refresh");
	$('#ListaDeMateriales').selectpicker("refresh");
	$('#ListaPatronaje').selectpicker("refresh");
}

/*
 * BUSCADOR 
 * 
 */
//$("#TipoPrenda").autocomplete({
//
//	source : function(request, response) {
//		$.ajax({
//			url : "/cargar-familias/" + request.term,
//			dataType : "json",
//			data : {
//				term : request.term
//			},
//			success : function(data) {
//				response($.map(data, function(item) {
//					return {
//						value : item.id,
//						label : item.nombre,
//						precio : item.precio,
//					};
//				}));
//				console.log(data);
//			},
//		});
//	},
//	select : function(event, ui) {
//		$("#buscar_producto").val(ui.item.label);
//		alert('seleccionado');
//		if(itemsHelper.hasProducto(ui.item.value)){
//			itemsHelper.incrementaCantidad(ui.item.value, ui.item.precio);
//			return false;
//		}
//		
//		var linea = $("#plantillaItemsFactura").html();
//
//		linea = linea.replace(/{ID}/g, ui.item.value);
//		linea = linea.replace(/{NOMBRE}/g, ui.item.label);
//		linea = linea.replace(/{PRECIO}/g, ui.item.precio);
//
//		$("#cargarItemProductos tbody").append(linea);
//		itemsHelper.calcularImporte(ui.item.value, ui.item.precio, 1);
//
//		return false;
//	}
//});


/***
*
*	Estas funciones son para las imagenes
*
***/

function AgregarImagen() {
	for (var i = 1; i < 7; i++) {
		if ($('#Contenedor-' + i).css('display') == 'none') {
			$('#Contenedor-' + i).css('display', 'block');
			break;
		}
		else {
			console.log("ya esta visible");
		}
	}

	if ($('#Contenedor-1').css('display') == 'block' && $('#Contenedor-2').css('display') == 'block' && $('#Contenedor-3').css('display') == 'block'
		&& $('#Contenedor-4').css('display') == 'block' && $('#Contenedor-5').css('display') == 'block' && $('#Contenedor-6').css('display') == 'block') {
		$('#ContenedorBotonAgregarOtro').hide();
	}
}

function AgregarImagenEditar() {
	for (var i = 1; i < 7; i++) {
		if ($('#Contenedor-' + i).css('display') == 'none') {
			$('#Contenedor-' + i).css('display', 'block');
			break;
		}
		else {
			console.log("ya esta visible");
		}
	}

	if ($('#Contenedor-1').css('display') == 'block' && $('#Contenedor-2').css('display') == 'block' && $('#Contenedor-3').css('display') == 'block'
		&& $('#Contenedor-4').css('display') == 'block' && $('#Contenedor-5').css('display') == 'block' && $('#Contenedor-6').css('display') == 'block') {
		$('#ContenedorBotonAgregarOtro').hide();
	}
}

function QuitarImagenEdit(id) {
	$('#Contenedor-' + id).hide();
	$('#status-input-edit-' + id).val("delete");
	$('#name-edit-' + id).val('');
	$('#file-input-edit-' + id).val('');

	//Se quita la previsualizacion de la imagen
	$('#img-edit-' + id).remove();
	$('#contenedor-imagen-' + id).append("<img class='card-img-top' id='img-edit-" + id + "'  src='/dist/img/preview.png' style='max-width: 289px !important; max-height: 289px !important; width: auto !important; height: auto !important;'>");

	if ($('#Contenedor-1').css('display') == 'block' && $('#Contenedor-2').css('display') == 'block' && $('#Contenedor-3').css('display') == 'block'
		&& $('#Contenedor-4').css('display') == 'block' && $('#Contenedor-5').css('display') == 'block' && $('#Contenedor-6').css('display') == 'block') {
		$('#ContenedorBotonAgregarOtro').hide();
	}
	else {
		$('#ContenedorBotonAgregarOtro').show();
	}
}

function QuitarImagen(id) {
	$('#Contenido-' + id).remove();

	$('#Contenedor-' + id).hide();

	$('#Contenedor-' + id).append("<div class='card' style='width: 18rem;' id='Contenido-" + id + "'>" +
		"<div class='image-upload-" + id + "'>" +
		"<label for='file-input-" + id + "'>" +
		"<img class='card-img-top' id='img-" + id + "' src='/dist/img/preview.png'>" +
		"</label>" +
		"<input id='file-input-" + id + "' name='file-input-" + id + "' onchange='PreviewImage(this, " + id + ");' type='file' style='display:none'/>" +
		"</div>" +
		"<div class='card-body'>" +
		"<p class='card-text'><input type='text' id='name-" + id + "' name='name-" + id + "' class='form-control' placeholder='Nombre de Imagen'></p>" +
		"<button class='btn btn-danger' onclick='QuitarImagen(" + id + ")'>Eliminar</button>" +
		"</div>" +
		"</div>");

	if ($('#Contenedor-1').css('display') == 'block' && $('#Contenedor-2').css('display') == 'block' && $('#Contenedor-3').css('display') == 'block'
		&& $('#Contenedor-4').css('display') == 'block' && $('#Contenedor-5').css('display') == 'block' && $('#Contenedor-6').css('display') == 'block') {
		$('#ContenedorBotonAgregarOtro').hide();
	}
	else {
		$('#ContenedorBotonAgregarOtro').show();
	}

}

function PreviewImage(input, id) {
	var nombre = input.value;
	if (/\s/.test(nombre.substring(12))) {
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'Por seguridad, no deben haber espacios en las imagenes que subas'
		})
	}
	else {
		if (input.files && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function (e) {
				$('#img-' + id).attr('src', e.target.result);
			}

			reader.readAsDataURL(input.files[0]);
		}
	}

}

function PreviewImageEdit(input, id) {
	var nombre = input.value;
	if (/\s/.test(nombre.substring(12))) {
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'Por seguridad, no deben haber espacios en las imagenes que subas'
		})
	}
	else {

		$('#status-input-edit-' + id).val("Alter");

		if (input.files && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function (e) {
				$('#img-edit-' + id).attr('src', e.target.result);
			}

			reader.readAsDataURL(input.files[0]);
		}
	}
}


function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function (e) {
			$('#blah1').attr('src', e.target.result);
		}
		reader.readAsDataURL(input.files[0]); // convert to base64 string
	}
}
function readURL2(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function (e) {
			$('#blah2').attr('src', e.target.result);
		}
		reader.readAsDataURL(input.files[0]); // convert to base64 string
	}
}
$("#file").change(function () {
	readURL(this);
	console.log("cambio");
});
$("#file2").change(function () {
	readURL2(this);
	console.log("cambio");
});
//Funciones para limpiar inputs
function LimpiarInput1() {
	$('#file').val(null);
	$('#blah1').attr("src", "/dist/img/preview.png");
}
function LimpiarInput2() {
	$('#file2').val(null);
	$('#blah2').attr("src", "/dist/img/preview.png");
}
var id_material_prenda;

function getMaterialesByTipo(idTipoMaterial, idMaterial, idMaterialPrenda) {
	Swal.fire({
		title: 'Cargando',
		allowEscapeKey: false,
		allowOutsideClick: false,
		onOpen: () => {
			Swal.showLoading();
		}
	})
	console.log("si entra nmz");
	tableMaterialExtra.rows().remove().draw();
	id_material_prenda = idMaterialPrenda;
	$('#_materiales option').remove();
	$('#_materiales').selectpicker('refresh');
	$.ajax({
		method: "GET",
		url: "/getMaterialesByTipo",

		data: {

			"_csrf": $('#token').val(),
			"idTipoMaterial": idTipoMaterial,
			"idMaterial": idMaterial
		},
		success: (data) => {
			tableMaterialExtra.rows().remove().draw();
			for (i in data) {
				$("#_materiales").append("<option value='" + data[i][0] + "'>" + data[i][1] + "</option>")
			}
			$('#_materiales').selectpicker('refresh');
			//Swal.close();
		},

		error: (e) => {
			alert("error alv");
			//Swal.close();
		}
	});

	$.ajax({
		method: "GET",
		url: "/getMaterialesExtra",

		data: {
			"_csrf": $('#token').val(),
			"idMaterialPrenda": idMaterialPrenda
		},
		success: (data) => {
			tableMaterialExtra.rows().remove().draw();
			for (i in data) {
				tableMaterialExtra.row.add([
					data[i][0],
					data[i][1],
					data[i][2],
					"<button type='button' onclick='eliminarMaterialExtra(" + data[i][3] + ")' class='btn icon-btn btn-danger text-white btn_remove'><span class='btn-glyphicon spancircle fas fa-times fa-lg img-circle text-danger'></span>Eliminar</button>"
				]).draw(false);
			}
			Swal.close();
		},

		error: (e) => {
			alert("error alv");
			Swal.close();
		}
	});
}

$("#agregar_material_extra").click(function () {
	Swal.fire({
		title: 'Cargando!',
		onBeforeOpen: () => {
			Swal.showLoading()
		},
	})

	var id_material = document.getElementById("_materiales").value
	console.log();
	$.ajax({
		type: "POST",
		url: "/postMaterialesExtra",
		data: {
			"_csrf": $('#token').val(),
			"id_material": id_material,
			"id_material_prenda": id_material_prenda
		},
		success: (data) => {
			tableMaterialExtra.rows().remove().draw();
			for (i in data) {
				tableMaterialExtra.row.add([
					data[i][0],
					data[i][1],
					data[i][2],
					"<button type='button' onclick='eliminarMaterialExtra(" + data[i][3] + ")' class='btn icon-btn btn-danger text-white'><span class='btn-glyphicon spancircle fas fa-times fa-lg img-circle text-danger'></span>Eliminar</button>"
				]).draw(false);
			}
			Swal.close();
		},
		error: function (data) {
			Swal.close();
			Swal.fire({
				icon: 'error',
				title: 'Error',
				text: '¡Registro duplicado!',
			})
		}
	});

});

function eliminarMaterialExtra(idMaterialExtra) {
	Swal.fire({
		title: 'Cargando!',
		onBeforeOpen: () => {
			Swal.showLoading()
		},
	})
	var idMaterialPrenda = id_material_prenda;
	tableMaterialExtra.rows().remove().draw();
	$.ajax({
		method: "DELETE",
		url: "/deleteMaterialesExtra",

		data: {

			"_csrf": $('#token').val(),
			"idMaterialExtra": idMaterialExtra,
			"idMaterialPrenda": idMaterialPrenda
		},
		success: (data) => {
			console.log(data);
			for (i in data) {
				tableMaterialExtra.row.add([
					data[i][0],
					data[i][1],
					data[i][2],
					"<button type='button' onclick='eliminarMaterialExtra(" + data[i][3] + ")' class='btn icon-btn btn-danger text-white btn_remove'><span class='btn-glyphicon spancircle fas fa-times fa-lg img-circle text-danger'></span>Eliminar</button>"
				]).draw(false);
			}
			Swal.close();
		},

		error: (e) => {
			alert("error alv");
			Swal.close();
		}
	});
}
//Agregar Cantidad Repuesto
var idRepuesto;
function cantidadRepuesto(id) {
	var material = objeto_materiales.map(function (item) { return item.identidad; }).indexOf(id);
	idRepuesto = material;
	$("#cantidadRepuestoInput").val(objeto_materiales[material].cantidadRepuesto);


}
$("#agregarCantidadRepuesto").click(function (e) {
	if ($('#cantidadRepuestoInput').val() > objeto_materiales[idRepuesto].cantidad) {
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'La cantidad de repuesto debe ser menor a la cantidad!',
		})
	}
	else if ($('#cantidadRepuestoInput').val() < 0) {
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: 'La cantidad de repuesto debe ser mayor o igual a 0!',
		})
	}
	else {
		Swal.fire({
			icon: 'success',
			title: 'La cantidad se guardo correctamente',
			showConfirmButton: false,
			timer: 2000
		})
		objeto_materiales[idRepuesto].cantidadRepuesto = $('#cantidadRepuestoInput').val();
		console.log(objeto_materiales[idRepuesto]);
		$('#modalCantidadRepuesto').modal('toggle');
	}
});

