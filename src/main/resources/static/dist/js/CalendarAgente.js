//Calendario



  $(function () {
	    var dialog, form,
	    
     	tips = $( ".validateTips" ); 
    
    function validateDateRange(start, end) {
    	
    	var aux1 = new Date(start);
    	var aux2 = new Date(end);
    	var hoursStart;
    	var hoursEnd;
    	
    	if (aux1.getHours() <10 ){hoursStart= '0'+aux1.getHours()}
    	else {hoursStart= aux1.getHours()}
    	
    	if (aux2.getHours() <10 ){var hoursEnd= '0'+aux2.getHours()}
    	else {hoursEnd= aux2.getHours()}
    	
    	if ( aux1.getMinutes() <10 ){ hoursStart= hoursStart+':0'+aux1.getMinutes()  }
    	else { hoursStart= hoursStart+':'+aux1.getMinutes()}
    	
    	if ( aux2.getMinutes() <10 ){ hoursEnd= hoursEnd+':0'+aux2.getMinutes()  }
    	else { hoursEnd= hoursEnd+':'+aux2.getMinutes()}
    	
    	console.log(hoursEnd);
    	if(!start.isValid() || start== null ) {
    		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La fecha de inicio no es válida.',
				showConfirmButton: false,
				timer: 1250
			})
    		
    		return false; 
    	}//dddfff
    	
    	if(!end.isValid()) {
    		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La fecha de finalización no es válida.',
				showConfirmButton: false,
				timer: 1250
			})
    		return false; 
    	}
    	
    	if(start.isAfter(end)) {
	 		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La fecha de finalización debe ser posterior a la fecha de inicio.',
				showConfirmButton: false,
				timer: 1250
			})
	 		return false; 
	 	}
    	
    	if(start.isSame(end)) {
    		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La fecha de finalización debe ser posterior a la fecha de inicio.',
				showConfirmButton: false,
				timer: 1250
			})
    		return false;
    	}
    	
    	if(start.isSame(end)) {
    		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La fecha de finalización debe ser posterior a la fecha de inicio.',
				showConfirmButton: false,
				timer: 1250
			})
    		return false;
    	}
    	
    	var hoy = new Date();
    	
    	
    	if ( hoy >= start){
    		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La fecha de inicio debe ser mayor o igual a la fecha actual.',
				showConfirmButton: false,
				timer: 1250
			})
			return false;
    	}
    	
    	if ( hoy >= end){
    		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La fecha de finalizacion debe ser mayor o igual a la fecha actual.',
				showConfirmButton: false,
				timer: 1250
			})
			return false;
    	}
    	if(hoursStart < "06:00") {
    		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La hora de inicio debe ser posterior a las 06:00.',
				showConfirmButton: false,
				timer: 1250
			})
    		return false;
    	}
    	
    	
    	if(hoursEnd > "22:00"  ) {
    		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La hora de finalización debe ser menor a las 22:00.',
				showConfirmButton: false,
				timer: 1250
			})
    		return false;
    	}
    	
    	
    	return true;
    }
    
	 
   	function removeEvent() {  
   		
   		Swal.fire({
   			title: '¿Deseas dar de baja este evento?',
   			icon: 'warning',
   			showCancelButton: true,
   			cancelButtonColor: '#6C757D',
   			cancelButtonText: 'Cancelar',
   			confirmButtonText: 'Dar de baja',
   			confirmButtonColor: '#dc3545',
   		}).then((result) => {
   			
   			if (result.value) {
   				
   			
    	var eventData;
		if (idCalendario.value) {
			eventData = idCalendario.value;
			console.log("id:"+eventData);
			$('#agendarCita').modal('hide');
    		$.ajax({
    	        type: "DELETE",
    	        url: "/event",
    	        
    	        data: {
    	        	"id":eventData,
					'_csrf': $('#token').val()
				},
    	        
    	        
    	        success: (data) => {
    	        	calendar.refetchEvents();
    	        	console.log("id="+eventData);
    	        	
    	        	Swal.fire({
    					position: 'center',
    					icon: 'success',
    					title: 'Dada de baja correctamente',
    					showConfirmButton: false,
    					timer: 1250
    				})
    	        },
    	        error: (e) => {
    	            $("#error").text(e.responseText);
    	        }
    	      });
			
		}
	    return true;
   		}
   		else{$('#agendarCita').modal('hide');}
   		})
    }
	   
    function editEvent(info) {
    	var eventStart = moment(info.event.start).format("YYYY-MM-DDTHH:mm:ss"); //moment(event.start);
 			var eventEnd = moment(info.event.end).format("YYYY-MM-DDTHH:mm:ss");
 			
       	newtitle.value = info.event.title;
	    description.value = info.event.extendedProps.description; 
	    startdateandtime.value = eventStart;			
	    enddateandtime.value = eventEnd;
	    idCalendario.value =info.event.extendedProps.idCalendario; 
	    
	    color.value =info.event.borderColor;  // borderColor
	    $(".empresa1").val(info.event.extendedProps.idCliente);
	    $(".empresa1").selectpicker("refresh");
	    
	    $('#agendarCita').modal('toggle');
	    
    }	
    
    function saveEvent() {
    	var valid = true;
    	
	 	var eventStart = moment(startdateandtime.value);
 		var eventEnd = moment(enddateandtime.value);
   
 		valid = valid && newtitle.value;
	 	valid = valid && validateDateRange(eventStart, eventEnd );
	 	var select=document.getElementById('empresa1');
	   
		if ( valid  &&  select.value>=0 && select.value != "" ) {
	    	var eventData;
			if (newtitle.value) {
				eventData = {
					idCalendario: idCalendario.value,
					title: newtitle.value,
					start: startdateandtime.value,
					end:  enddateandtime.value, 
					description: description.value,
					color: color.value,
					idCliente: empresa1.value
				};
		//		alert(eventData.title.value + " " + eventData.start.value + " " + eventData.finish.value)
		//		$('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
			}
			
			calendar.unselect();
			$('#agendarCita').modal('hide');
    		$.ajax({
    	        type: "PATCH",
    	        url: "/event",
			    data: {
			    	'idCalendario': idCalendario.value,
					'title': newtitle.value,
					'start': startdateandtime.value,
					'end':  enddateandtime.value, 
					'description': description.value,
					'color': color.value,
					'idCliente': empresa1.value,
					'_csrf': $('#token').val()
				},
				success: (data) => {	
    	        	calendar.refetchEvents()
    	        	console.log(JSON.stringify(eventData));
    	        	
    	        	Swal.fire({
    					position: 'center',
    					icon: 'success',
    					title: 'Cita guardada correctamente',
    					showConfirmButton: false,
    					timer: 1250
    				})
    				
    				
    				
    	        },
    	        error: (e) => {
    	        	console.log("error 111");
    	        }
    	      });
    	}
		else{
			if (  select.value == ""){
				 Swal.fire({
						position: 'center',
						icon: 'error',
						title: 'Seleccione una empresa',
						showConfirmButton: false,
						timer: 1250
					})
			}
			if (  !newtitle.value){
				 Swal.fire({
						position: 'center',
						icon: 'error',
						title: 'Ingrese el titulo',
						showConfirmButton: false,
						timer: 1250
					})
			}
			if (  !startdateandtime.value){
				 Swal.fire({
						position: 'center',
						icon: 'error',
						title: 'Ingrese la fecha de inicio',
						showConfirmButton: false,
						timer: 1250
					})
			}
			if (  !enddateandtime.value){
				 Swal.fire({
						position: 'center',
						icon: 'error',
						title: 'Ingrese la fecha de finalización',
						showConfirmButton: false,
						timer: 1250
					})
			}
			
		}
		
	 	return valid;
    }
    function moveEvent(info) {
    	var eventStart = moment(info.event.start).format("YYYY-MM-DDTHH:mm:ss"); //moment(event.start);
		var eventEnd = moment(info.event.end).format("YYYY-MM-DDTHH:mm:ss");
		
		var eventColor = info.event.borderColor;
		var evetidCliente = info.event.extendedProps.idCliente;
    	var valid = true;
    	
    	
    	valid = valid && info.event.title;
		if ( valid ) {
			console.log( evetidCliente);
	    	var eventData;
			if (info.event.title) {
				eventData = {
					//idCalendario: info.event.idCalendario,
					idCalendario: info.event.extendedProps.idCalendario,
					title: info.event.title,
					start: eventStart,
					end:  eventEnd, 
					description: description.value,
					color :eventColor,// borderColor,
					idCliente :evetidCliente// borderColor
					
				};
				console.log("que pedal");
		//		alert(eventData.title.value + " " + eventData.start.value + " " + eventData.finish.value)
		//		$('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
			}
			calendar.unselect();
			$('#agendarCita').modal('hide');
			console.log(JSON.stringify(eventData));
    		$.ajax({
    	        type: "PATCH",
    	        url: "/event",
    	        
    	        data: {
    	        	'idCalendario': info.event.extendedProps.idCalendario,
					'title': info.event.title,
					'start': eventStart,
					'end':  eventEnd, 
					'description': description.value,
					'color': eventColor,
					'idCliente': evetidCliente,
					'_csrf': $('#token').val()
				},
    	        success: (data) => {
    	        	calendar.refetchEvents();
    	        	console.log(JSON.stringify(eventData));
    	        	
    	        	Swal.fire({
    					position: 'center',
    					icon: 'success',
    					title: 'Cita movida correctamente',
    					showConfirmButton: false,
    					timer: 1250
    				})
    	        },
    	        error: (e) => {
    	        	console.log("error 222");
    	        }
    	      });
    	}
	 	return valid;
    }
    
   function addEvent(start, end) {
      	var valid = true;
      	
 			var eventStart = moment(startdateandtime.value);
 			var eventEnd = moment(enddateandtime.value);
		valid = valid && newtitle.value;
	 	valid = valid && startdateandtime.value;
	 	valid = valid && validateDateRange(eventStart, eventEnd);
	 	
	 	var select=document.getElementById('empresa1');
		   
		if ( valid  &&  select.value>=0 && select.value != "" ) {
	    	var eventData;
			if (newtitle.value ) {
				eventData = {
					title: newtitle.value,
					description: description.value,
					start: startdateandtime.value,
					end: enddateandtime.value, 
					color: color.value,
					idCliente: empresa1.value
				};
				//$('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
			}
			
			calendar.unselect();
			$('#agendarCita').modal('hide');
			console.log(JSON.stringify(eventData));
			 $.ajax({
			    type: "POST",
			    url: "/event",
			    data:{
			    	'title': newtitle.value,
					'description': description.value,
					'start': startdateandtime.value,
					'end': enddateandtime.value, 
					
					'color': color.value,
			    	'_csrf': $('#token').val(),
			    	'idCliente': empresa1.value,
			    },
			    success: function(data){
			    	calendar.refetchEvents();
			    	console.log(JSON.stringify(eventData));
			    	Swal.fire({
    					position: 'center',
    					icon: 'success',
    					title: 'Cita guardada correctamente',
    					showConfirmButton: false,
    					timer: 1250
    				})
			    },
			    failure: function(errMsg) {
			        alert(errMsg);
			    }
			});
      }
		
		else{
			if (  select.value == ""){
				 Swal.fire({
						position: 'center',
						icon: 'error',
						title: 'Seleccione una empresa',
						showConfirmButton: false,
						timer: 1250
					})
			}
			if (  !newtitle.value){
				 Swal.fire({
						position: 'center',
						icon: 'error',
						title: 'Ingrese el titulo',
						showConfirmButton: false,
						timer: 1250
					})
			}
			if (  !startdateandtime.value){
				 Swal.fire({
						position: 'center',
						icon: 'error',
						title: 'Ingrese la fecha de inicio',
						showConfirmButton: false,
						timer: 1250
					})
			}
			if (  !enddateandtime.value){
				 Swal.fire({
						position: 'center',
						icon: 'error',
						title: 'Ingrese la fecha de finalización',
						showConfirmButton: false,
						timer: 1250
					})
			}
			
		}
      return valid;
    }
    $("#submit-modal").click((e) => {
    	e.preventDefault();
    	if($("#submit-modal"). val()=='1'){
  
    		addEvent();
    		
    	}
    	else{saveEvent();}	
    });
    $("#nueva-cita").click((e) => {
    	
    	idCalendario.value="";
		newtitle.value="";
		startdateandtime.value="";
		enddateandtime.value="";
		description.value="";
		$("#empresa1").val(null);
	    $("#empresa1").selectpicker("refresh");
		
		color.value="";
    	
    	e.preventDefault();
    	$('#delete-modal').hide();
    	$('#submit-modal-text').text("Agendar");
    	$('#submit-modal').val("1");
    });
    
    $("#delete-modal").click((e) => {
    	console.log("hola");
    	e.preventDefault();
    	
    	removeEvent();
    }); 

    var Calendar = FullCalendar.Calendar;
    var calendarEl = document.getElementById('calendar');
    var calendar = new Calendar(calendarEl, {
      locale:"es-us",
      height: 600,
      plugins: [ 'bootstrap', 'interaction', 'dayGrid', 'timeGrid','list'],
      header    : {
        left  : 'prev,next today',
        center: 'title',
        right : 'dayGridMonth,timeGridWeek,listMonth'
      },
      	allDaySlot:false,
		defaultDate: moment().format("YYYY-MM-DD"),
		editable: true,
		slotLabelInterval : '00:15:00',
		slotDuration : '00:15:00',
		viewSubSlotLabel : true,
		minTime:'06:00:00',
		maxTime:'22:00:00',
		contentHeight: 'auto',
		eventDrop: function(info) {
			console.log(info.event.start,info.event.end);
			Swal.fire({
				title: '¿Te gustaria cambiar la cita?', 
				showCancelButton: true, 
				confirmButtonText: 'Cambiar',
				cancelButtonText: 'Cancelar',
				confirmButtonColor: '#dc3545'}).then(result => {
				  if (result.value) {
					  moveEvent(info);
				  } else {
					  info.revert();
				  }
				})
			
			  },
	   eventResize: function(info) {
		   Swal.fire({
				title: '¿Te gustaria cambiar la cita?', 
				showCancelButton: true, 
				confirmButtonText: 'Cambiar',
				cancelButtonText: 'Cancelar',
				confirmButtonColor: '#dc3545'}).then(result => {
				  if (result.value) {
					  moveEvent(info);
				  } else {
					  info.revert();
				  }
				})
		  },
		eventLimit: true, // allow "more" link when too many events
		events: {
	        url: '/allevents',
	        type: 'GET',
	        error: function() {
	            alert('there was an error while fetching events!');
	        },
	        //color: 'blue',   // a non-ajax option
	        //textColor: 'white' // a non-ajax option
	    },
		selectable: true,
		selectHelper: true,
		selectMirror: true,
		timeFormat: 'h(:mm)',
		select: function(arg) {

	    	idCalendario.value="";
			newtitle.value="";
			startdateandtime.value="";
			enddateandtime.value="";
			description.value="";
			
			color.value="";
			$("#empresa1").val(null);
		    $("#empresa1").selectpicker("refresh");
			console.log("inicio:"+ moment(arg.start).format("YYYY-MM-DDTHH:mm:ss")+"final:"+moment(arg.end).format("YYYY-MM-DDTHH:mm:ss"));
			startdateandtime.value = moment(arg.start).format("YYYY-MM-DDTHH:mm:ss");
			enddateandtime.value = moment(arg.end).format("YYYY-MM-DDTHH:mm:ss");
			$('#submit-modal-text').text("Agendar");
			$('#delete-modal').hide();
			$('#agendarCita').modal('toggle');
		},
		eventClick: function(info) {
			//info.jsEvent.preventDefault(); 
			$('#delete-modal').show();
			$('#submit-modal-text').text("Actualizar");
			$('#submit-modal').val("0");
			editEvent(info);
			
	       //$('#calendar').fullCalendar('updateEvent', event);
 	    }	
	  	
// 		loading: function(bool) {
// 			$('#loading').toggle(bool);
// 		}
    });

    calendar.render();
    
 $("#nueva-cita2").click((e) => {
    	
	 	idCalendario2.value="";
    	title_even.value="";
    	timestart_even.value="";
    	timeend_even.value="";
    	desc_even.value="";
		
    	color2.value="";
    	
    	for (var i = 0; i < 7; i++) {
    		document.getElementById("d"+i).checked = false; 
    		document.getElementById("dd"+i).checked = false; 
    		}
    	
    
	    
	    $("#empresa_even").val(null);
	    $("#empresa_even").selectpicker("refresh");
    	e.preventDefault();
    	$('#submit-modal2').val("1");
    });
    
    $("#submit-modal2").click((e) => {
    	var fechas = [];
    	e.preventDefault();
    	var select=document.getElementById('empresa_even');
    	var validFechas = false;
    	var valid = true;
    	$("input:checkbox:checked").each(   
    		    function() {
    		    	fechas.push({ fecha:$(this).val() });
    		    	validFechas = true;
    		    }
    		    
    		);
    	
    	valid = valid && title_even.value;
	 	valid = valid && validateHourRange(timestart_even.value, timeend_even.value);
    	
    	if(valid && select.value>=0 && select.value != "" && validFechas){
    		 var hoy = new Date();
    		 var aux ;
    		 var aux2 ;
    		 fechas.forEach( function(valor, indice, array) {
    			 aux = valor.fecha +timestart_even.value;
    			 aux2 = valor.fecha +timeend_even.value;
    			 
    			    if ( (new Date(aux).getTime() >= new Date(hoy).getTime()) ){
    			    	console.log(aux);
    			    	
    			    	$.ajax({
    	 			    type: "POST",
    	 			    url: "/event",
    	 			    data:{
    	 			    	'title': title_even.value,
    	 					'description': desc_even.value,
    	 					'start': aux,
    	 					'end': aux2, 
    	 					'color': color2.value,
    	 			    	'_csrf': $('#token').val(),
    	 			    	'idCliente': empresa_even.value,
    	 			    },
    	 			    success: function(data){
    	 			    	calendar.refetchEvents();
    	 			    	
    	 			    },
    	 			    failure: function(errMsg) {
    	 			        alert(errMsg);
    	 			    }
    	 			});
    			    }
    			});
    		 $('#agendarCitaMasiva').modal('hide');
    		 Swal.fire({
					position: 'center',
					icon: 'success',
					title: 'Cita guardada correctamente',
					showConfirmButton: false,
					timer: 1250
				})
    	}
    	
    	else{
			if (  select.value == ""){
				 Swal.fire({
						position: 'center',
						icon: 'error',
						title: 'Seleccione una empresa',
						showConfirmButton: false,
						timer: 1250
					})
			}
			if (  !title_even.value){
				 Swal.fire({
						position: 'center',
						icon: 'error',
						title: 'Ingrese el titulo',
						showConfirmButton: false,
						timer: 1250
					})
			}
			if (  !timestart_even.value){
				 Swal.fire({
						position: 'center',
						icon: 'error',
						title: 'Ingrese la hora de inicio',
						showConfirmButton: false,
						timer: 1250
					})
			}
			if (  !timeend_even.value){
				 Swal.fire({
						position: 'center',
						icon: 'error',
						title: 'Ingrese la fecha de finalización',
						showConfirmButton: false,
						timer: 1250
					})
			}
			if (  !validFechas){
				 Swal.fire({
						position: 'center',
						icon: 'error',
						title: 'Seleccione un al menos un día',
						showConfirmButton: false,
						timer: 1250
					})
			}
			
			
		}
    	
    	
    	 
    	//hhhhh
    });
    
    
    function validateHourRange(start, end) {
    	console.log("inicio:"+start+"fin:"+end);
    	if(start == null || start == "" ) {
    		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La hora de inicio no es válida.',
				showConfirmButton: false,
				timer: 1250
			})
    		
    		return false; 
    	}//dddfff
    	
    	if(end == null || end == "") {
    		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La hora de finalización no es válida.',
				showConfirmButton: false,
				timer: 1250
			})
    		return false; 
    	}
    	
    	if(start > end) {
	 		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La hora de finalización debe ser posterior a la hora de inicio.',
				showConfirmButton: false,
				timer: 1250
			})
	 		return false; 
	 	}
    	
    	if(start == end) {
    		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La hora de finalización debe ser posterior a la hora de inicio.',
				showConfirmButton: false,
				timer: 1250
			})
    		return false;
    	}
    	
    	if(start < "06:00") {
    		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La hora de inicio debe ser posterior a las 06:00.',
				showConfirmButton: false,
				timer: 1250
			})
    		return false;
    	}
    	
    	if(end > "22:00") {
    		Swal.fire({
				position: 'center',
				icon: 'error',
				title: 'La hora de finalización debe ser menor a las 22:00.',
				showConfirmButton: false,
				timer: 1250
			})
    		return false;
    	}
    	
    
    	
    	
    
    	return true;
    }

  })
  
  
  $(document).ready(function(){
	  var hoy = new Date();
	  var months = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];

	  for (var i = 0; i < 7; i++) {
		  if ( i >= hoy.getDay() ){
			  document.getElementById("d"+i).disabled=false;
			 
			  if (hoy.getDate() <10  ){
				  $('#d'+i).val(hoy.getFullYear()+'-'+months[hoy.getMonth()]+'-0'+hoy.getDate()+'T');
			  }
			  else{
				  $('#d'+i).val(hoy.getFullYear()+'-'+months[hoy.getMonth()]+'-'+hoy.getDate()+'T');
				 
			  }
			  
			  hoy.setDate(hoy.getDate() + 1);
		  }
		   
		}
	  
	  for (var i = 0; i < 7; i++) {
		 
			  document.getElementById("dd"+i).disabled=false;
			 
			  if (hoy.getDate() <10  ){
				  $('#dd'+i).val(hoy.getFullYear()+'-'+months[hoy.getMonth()]+'-0'+hoy.getDate()+'T');
				  console.log (hoy.getFullYear()+'-'+months[hoy.getMonth()]+'-0'+hoy.getDate()+'T');
			  }
			  else{
				  $('#dd'+i).val(hoy.getFullYear()+'-'+months[hoy.getMonth()]+'-'+hoy.getDate()+'T');
				  console.log (hoy.getFullYear()+'-'+months[hoy.getMonth()]+'-'+hoy.getDate()+'T');
				 
			  }
			  
			  hoy.setDate(hoy.getDate() + 1);
		  
		   
		}
	  
    });
  
  
  function imprimir() {
	  $(".fc-listMonth-button").click();
		window.print();
	}