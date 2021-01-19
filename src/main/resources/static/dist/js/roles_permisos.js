//Ajax para traer los departamentos
function cargarDepartamento() {
    var array = [];
    $.ajax({
    	method: "GET",
		url: "/listRoles",
		success: (data) => {
			var arreglo = [];
			for (i in data){
				arreglo.push(data[i]);
			}
			array = arreglo;

		    addOptions("departamento", array);
		    $('.selectpicker').selectpicker('refresh');
		},
		error: (e) => {
		}
	}); 
}


//Función para agregar departamentos a un <select>.
function addOptions(domElement, array) {
    var selector = document.getElementsByName(domElement)[0];
    for (departamento in array) {
        var opcion = document.createElement("option");
        opcion.text = array[departamento];
        // Añadimos un value a los option para hacer mas facil escoger los pueblos
        opcion.value = array[departamento].replace(/ /g, "");
        selector.add(opcion);
    }
}


//Función para llenar las secciones de acuerdo al departamento
function cargarSeccion() {
	
	$.ajax({
		method: "GET",
		url: "/listSecciones",
		success: (data) => {
			var mensajeria = [];
			var disenio = [];
			var comercial = [];
			var logistica = [];
			var producción = [];
//			var servicioalcliente = [];
			var usuario = [];
			var recursoshumanos = [];
			var requisiciones = [];
			for(i in data){
				if (data[i][0]=="Mensajería"){
					mensajeria.push(data[i][1]);
				}
				if (data[i][0]=="Diseño"){
					disenio.push(data[i][1]);
				}
				if (data[i][0]=="Comercial"){
					comercial.push(data[i][1]);
				}
				if (data[i][0]=="Logística"){
					logistica.push(data[i][1]);
				}
				if (data[i][0]=="Producción"){
					producción.push(data[i][1]);
				}
//				if (data[i][0]=="Servicio al cliente"){
//					servicioalcliente.push(data[i][1]);
//				}
				if (data[i][0]=="Usuarios"){
					usuario.push(data[i][1]);
				}
				if (data[i][0]=="Recursos humanos"){
					recursoshumanos.push(data[i][1]);
				}
				if (data[i][0]=="Requisiciones"){
					requisiciones.push(data[i][1]);
				}
			}
	
		    var listaPueblos = {
		      Mensajería: mensajeria,
		      Diseño: disenio,
		      Comercial: comercial,
		      Logística: logistica,
		      Producción: producción,
//		      Servicioalcliente: servicioalcliente,
		      Usuarios: usuario,
		      Recursoshumanos : recursoshumanos,
		      Requisiciones : requisiciones
		    }
		
		    var departamento = document.getElementById('departamento')
		    var roles = document.getElementById('rol_select')
		    var departamentoSeleccionado = departamento.value
		
		    // Se limpian las secciones
		    roles.innerHTML = '<option value="">Seleccione una Sección...</option>'
		    
		    if(departamentoSeleccionado !== ''){
		      // Se seleccionan los pueblos y se ordenan
		    	departamentoSeleccionado = listaPueblos[departamentoSeleccionado]
		    	departamentoSeleccionado.sort()
		    
		      // Insertamos los pueblos
		      departamentoSeleccionado.forEach(function(rol){
		        let opcion = document.createElement('option')
		        opcion.value = rol
		        opcion.text = rol
		        roles.add(opcion)
		      });
		    }
		    $('.selectpicker').selectpicker('refresh');
		},
		error: (e) => {
		}
	});
  }

//Función para cargar los permisos dentro de la tabla
function cargarPermiso(departamento,rol_select) {
console.log(departamento, rol_select);

	$.ajax({
		method: "GET",
		url: "/listPermisos",
		data:{
			departamento: departamento,
			seccion: rol_select
		},
		success: (data) => {
			var opciones="";
			for(i in data){
				opciones+= "<option value='"+data[i][0]+"'>"+data[i][1]+"</option>";
			}
			
			permiso = opciones;
		}
	});

  }

//Función para agregar un rol a la tabla
function guardarRol(){
	var filas = $("#tablita").find('tr');
	
	var resultD = $('#departamento').val();
	var resultS = $('#rol_select').val();
	var validador = true;
	
	if(resultD=="" || resultS==""){
		validador = false;
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: '¡Debe agregar un campo válido!',
			showConfirmButton: false,
	        timer: 3500
		  })
	}
	
	else{
		for(i=0; i<filas.length; i++){
			var celdas = $(filas[i]).find("td");
			if($(celdas[0]).text()==resultD && $(celdas[1]).text()==resultS){
				validador = false;
				Swal.fire({
					icon: 'error',
					title: 'Error',
					text: 'Ya se agregó esa muestra',
					showConfirmButton: false,
			        timer: 3500
				  })
			}
		}
	}
	
	//si cumple las validaciones de llenado, se agrega el rol a la tabla
	if(validador==true){
	    var _nom = document.getElementById("departamento").value;
	    if(_nom=="Servicioalcliente"){_nom="Servicio al cliente";}
	    if(_nom=="Recursoshumanos"){_nom="Recursos humanos";}
	    var _ape = document.getElementById("rol_select").value;
	    var fila="<tr><td>"+_nom+"</td><td>"+_ape +"</td><td><select multiple class='form-control selectpicker'>"+permiso+"</select></td><td>"+'<button type="button" name="remove" class="btn btn-danger btn_remove borrar">Eliminar</button></td></tr>';

	    $("#tablita").append(fila);
	    $('.selectpicker').selectpicker(["refresh"]);
	}
	
}

//   Borrar registro de la tabla de agregar un rol    //
//                                                    //
$(document).on('click', '.borrar', function (event) { //
event.preventDefault();                               //
$(this).closest('tr').remove();                       //
});                                                   //
//====================================================//

 // Iniciar la carga de provincias solo para comprobar que funciona
cargarDepartamento();


//Función para guardar el usuario con todos sus roles
function guardarUsuario(){
	var filas = $("#tablita").find('tr');
	
	var empleado = $('#empleado').val();
	var nombreUsuario = $('#username').val();
	var password = $('#pass').val();
	var confirmPass = $('#confirmPass').val();
	var statusUser = $('#statusUser').val();
	var userid = $('#idUser').val();
	var datosJson = [];
	var permisos=[];
	var i;
	var validador = true;
	
// Condicion para validar que no existe un usuario
	if($('#idUser').val()==""){                 //
		
		for(i in userValid){
			
			if(nombreUsuario==userValid[i][1] || nombreUsuario.toLowerCase()=="admin"){
				Swal.fire({
					icon: 'error',
					title: 'Error',
					text: '¡Ya existe ese nombre de usuario, elija otro!',
					showConfirmButton: false,
			        timer: 3500
				  })
				validador = false;
			}
		}
		
		
		if(filas.length==0 || empleado=="" || nombreUsuario=="" || statusUser=="" || password=="" || confirmPass==""){
		console.log(filas);
		Swal.fire({
			icon: 'error',
			title: 'Error',
			text: '¡Todos los campos deben de estar llenos!',
			showConfirmButton: false,
	        timer: 3500
		  })
		validador = false;
		}
		
			if(password!=confirmPass){
				validador = false;
				Swal.fire({
					icon: 'error',
					title: 'Error',
					text: '¡Las contraseñas deben coincidir!',
					showConfirmButton: false,
			        timer: 3500
				  })
			}
		
		else{
			for(i=0; i<filas.length; i++){
				var celdas = $(filas[i]).find("td");
				if($($(celdas[2]).find("select")).val()==""){
					validador = false;
					Swal.fire({
						icon: 'error',
						title: 'Error',
						text: '¡Debe haber al menos un permiso!',
						showConfirmButton: false,
				        timer: 3500
					  })
				}
			}
		}
// Si las validaciones son correctas, manda los JSON al controller mediante Ajax			
		if (validador==true){
			$('#cancelar').css('display', 'none');
			$('#cambiarContra').attr('disabled',true);
			$('#botonguardar').css('display', 'none');
			$('#Guardando').css('display', 'inline');
			for(i=0; i<filas.length; i++){
				
				var celdas = $(filas[i]).find("td");
				var record = {departamento:  $(celdas[0]).text(), 
							  seccion: 		 $(celdas[1]).text()};
				permisos.push($($(celdas[2]).find("select")).val());
				datosJson.push(record);
				
			}
			
			var listainsert=[];
			var contador = 0;
			
			for(u=0; u<filas.length; u++){
				var celdas = $(filas[u]).find("td");
				for(i in RolesGenerales){

					if($(celdas[0]).text()==RolesGenerales[i][1] && RolesGenerales[i][2]==""){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("entra depa");
						contador++;
					}

					if($(celdas[0]).text()==RolesGenerales[i][1] && $(celdas[1]).text().includes("Muestrario")==true && RolesGenerales[i][2].includes("Muestrario")==true){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("entra muestrario");
						contador++;
					}
					if($(celdas[0]).text()==RolesGenerales[i][1] && $(celdas[1]).text().includes("Agentes")==true && RolesGenerales[i][2].includes("Agentes")==true){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("entra agentes");
						contador++;
					}
					if($(celdas[0]).text()==RolesGenerales[i][1] && $(celdas[1]).text().includes("Expediente")==true && RolesGenerales[i][2].includes("Expediente")==true){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("entra expediente");
						contador++;
					}
					if($(celdas[0]).text()==RolesGenerales[i][1] && $(celdas[1]).text().includes("Amp")==true && RolesGenerales[i][2].includes("Amp")==true){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("amp");
						contador++;
					}
					if($(celdas[0]).text()==RolesGenerales[i][1] && $(celdas[1]).text().includes("Recepcion")==true && RolesGenerales[i][2].includes("Recepcion")==true){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("entra recepcion");
						contador++;
					}
					if($(celdas[0]).text()==RolesGenerales[i][1] && $(celdas[1]).text().includes("Ventas")==true && RolesGenerales[i][2].includes("Ventas")==true){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("entra ventas");
						contador++;
					}
					if($(celdas[0]).text()==RolesGenerales[i][1] && $(celdas[1]).text().includes("Gerencia comercial")==true && RolesGenerales[i][2]=="Gerencia comercial"){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("entra gerencia c");
						contador++;
					}
					if($(celdas[0]).text()==RolesGenerales[i][1] && $(celdas[1]).text().includes("Marketing")==true &&RolesGenerales[i][2].includes("Marketing")==true){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("entra marketing");
						contador++;
					}
				}
			}
			
			var filtered = listainsert.filter(function(el) { return el; });
			permisos.push(filtered);
			console.log(permisos);
			  
			$.ajax({
		    	method: "POST",
				url: "/guardarUser",
				data:{
					"_csrf": $('#token').val(),
					Empleado: empleado,
					NombreUser: nombreUsuario,
					Password: password,
					ConfirmPass: confirmPass,
					StatusUser: statusUser,
					"DatosJson": JSON.stringify(datosJson),
					"Permisos": JSON.stringify(permisos),
					idUser: userid
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
					$('#botonguardar').css('display', 'inline');
					$('#botonguardar').attr('disabled',true);
					$('#Guardando').css('display', 'none');
					
					if(data==2){
						Swal.fire({
							icon: 'error',
							title: 'Error',
							text: '¡Algo salió mal!',
							showConfirmButton: false,
					        timer: 3500
						  })
						
					}
					else{
						Swal.fire({
							icon: 'success',
							title: 'Correcto',
							text: '¡Se han insertado los datos!',
							showConfirmButton: false,
					        timer: 4000,
					        onClose: () => {
					        	location.href="editar_usuario/"+data;
						  }
						})
					}
				},
				error: (e) => {
				}	
			});
		}
	}
	
// Condicion para validar que ya existe un usuario
	else{
		var userCurrentName = $('#userCurrentName').val();
		if(nombreUsuario!=userCurrentName){	
			for(i in userValid){
				if(nombreUsuario==userValid[i][1] || nombreUsuario.toLowerCase()=="admin"){
					Swal.fire({
						icon: 'error',
						title: 'Error',
						text: '¡Ya existe ese nombre de usuario, elija otro!',
						showConfirmButton: false,
				        timer: 3500
					  })
					validador = false;
				}
			}
		}
		
		if(filas.length==0 || empleado=="" || nombreUsuario=="" || statusUser==""){
			console.log(filas);
			Swal.fire({
				icon: 'error',
				title: 'Error',
				text: '¡Todos los campos deben de estar llenos!',
				showConfirmButton: false,
		        timer: 3500
			  })
			validador = false;
			}

			else{
				for(i=0; i<filas.length; i++){
					var celdas = $(filas[i]).find("td");
					if($($(celdas[2]).find("select")).val()==""){
						validador = false;
						Swal.fire({
							icon: 'error',
							title: 'Error',
							text: '¡Debe haber al menos un permiso!',
							showConfirmButton: false,
					        timer: 3500
						  })
					}
				}
			}
		
		
		
		
		
		if (validador==true){
			$('#cancelar').css('display', 'none');
			$('#cambiarContra').attr('disabled',true);
			$('#botonguardar').css('display', 'none');
			$('#Guardando').css('display', 'inline');
			for(i=0; i<filas.length; i++){
				var celdas = $(filas[i]).find("td");
				var record = {departamento:  $(celdas[0]).text(), 
							  seccion: 		 $(celdas[1]).text()};
				permisos.push($($(celdas[2]).find("select")).val());
				datosJson.push(record);
				
			}
			
			var listainsert=[];
			var contador = 0;
			
			for(u=0; u<filas.length; u++){
				var celdas = $(filas[u]).find("td");
				for(i in RolesGenerales){

					if($(celdas[0]).text()==RolesGenerales[i][1] && RolesGenerales[i][2]==""){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("entra depa");
						contador++;
					}

					if($(celdas[0]).text()==RolesGenerales[i][1] && $(celdas[1]).text().includes("Muestrario")==true && RolesGenerales[i][2].includes("Muestrario")==true){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("entra muestrario");
						contador++;
					}
					if($(celdas[0]).text()==RolesGenerales[i][1] && $(celdas[1]).text().includes("Agentes")==true && RolesGenerales[i][2].includes("Agentes")==true){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("entra agentes");
						contador++;
					}
					if($(celdas[0]).text()==RolesGenerales[i][1] && $(celdas[1]).text().includes("Expediente")==true && RolesGenerales[i][2].includes("Expediente")==true){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("entra expediente");
						contador++;
					}
					if($(celdas[0]).text()==RolesGenerales[i][1] && $(celdas[1]).text().includes("Amp")==true && RolesGenerales[i][2].includes("Amp")==true){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("amp");
						contador++;
					}
					if($(celdas[0]).text()==RolesGenerales[i][1] && $(celdas[1]).text().includes("Recepcion")==true && RolesGenerales[i][2].includes("Recepcion")==true){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("entra recepcion");
						contador++;
					}
					if($(celdas[0]).text()==RolesGenerales[i][1] && $(celdas[1]).text().includes("Ventas")==true && RolesGenerales[i][2].includes("Ventas")==true){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("entra ventas");
						contador++;
					}
					if($(celdas[0]).text()==RolesGenerales[i][1] && $(celdas[1]).text().includes("Gerencia comercial")==true && RolesGenerales[i][2]=="Gerencia comercial"){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("entra gerencia c");
						contador++;
					}
					if($(celdas[0]).text()==RolesGenerales[i][1] && $(celdas[1]).text().includes("Marketing")==true &&RolesGenerales[i][2].includes("Marketing")==true){
						listainsert[contador]= RolesGenerales[i][0].toString();
						console.log("entra marketing");
						contador++;
					}
				}
			}
			
			var filtered = listainsert.filter(function(el) { return el; });
			permisos.push(filtered);
			console.log(permisos);
			
			$.ajax({
		    	method: "POST",
				url: "/guardarUser",
				data:{
					"_csrf": $('#token').val(),
					Empleado: empleado,
					NombreUser: nombreUsuario,
					Password: password,
					ConfirmPass: confirmPass,
					StatusUser: statusUser,
					"DatosJson": JSON.stringify(datosJson),
					"Permisos": JSON.stringify(permisos),
					idUser: userid
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
					$('#botonguardar').css('display', 'inline');
					$('#botonguardar').attr('disabled',true);
					$('#Guardando').css('display', 'none');
					
					if(data==2){
						Swal.fire({
							icon: 'error',
							title: 'Error',
							text: '¡Algo salió mal!',
							showConfirmButton: false,
					        timer: 3500
						  })
						
					}
					else{
						Swal.fire({
							icon: 'success',
							title: 'Correcto',
							text: '¡Se han insertado los datos!',
							showConfirmButton: false,
					        timer: 4000,
					        onClose: () => {
					        	location.reload();
						  }
						})
					}
					
				},
				error: (e) => {
				}	
			});
		}
	}
}


// Función para cargar los select de los permisos actuales de acuerdo a su rol
function mapearPermisos(){
	console.log("Si esta entrando");
	var filas = $("#tablita").find('tr');
	for(u=0; u<filas.length; u++){
		var celdas = $(filas[u]).find("td");
		var data = roles;
		
		for (i in data){
			var infoVista = $(celdas[1]).text().trim();
			var infoQuery = data[i][1].trim();
			if(infoVista==infoQuery){
			$(celdas[2]).find("select").append("<option value="+ data[i][0] +">"+ data[i][2] +"</option>");
			}
			
		}
	}

	checkSelect();
	
}

//Función para marcar como seleccionados los permisos que tiene activos
function checkSelect(){
	var filas = $("#tablita").find('tr');
	for(i=0; i<filas.length; i++){
		var celdas = $(filas[i]).find("td");
		var data = permisos;
		for (u in data){
			var infoVista = $(celdas[1]).text().trim();
			var infoQuery = data[u][1].trim();
			if(infoVista==infoQuery){
				console.log($(celdas[1]).text());
				console.log(data[u][0]);
				$($(celdas[2]).find("option[value="+data[u][0]+"]")).attr("selected", true);
				
			}
		}
	}	
		console.log("si se mapea");
} 

