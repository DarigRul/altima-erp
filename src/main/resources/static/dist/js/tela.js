var lookup;
$( document ).ready(function() {
    $.ajax({
        method: "GET",
           url: "/getTipo",
           
           data: {

               "_csrf": $('#token').val()
           },
           success: (data) => {
            lookup=data;
               for (i in data){
                   console.log("orale"+lookup[i].nombreLookup);
                   console.log("aqui"+data[i].idLookup);
+
                    $('#selecttipom').append("<option value='"+data[i].idLookup+"'>"+data[i].nombreLookup+"</option> ");

               }
               $('#selecttipom').selectpicker('refresh'); 
           },
          
           error: (e) => {
                location.reload();
           }
       });
});


  $( "#addMat" ).click(function() {
    var _idmat = $("#selecttipom" ).children("option:selected").val();
    var _texttipo = $("#selecttipom" ).children("option:selected").html();
    var _idcol = $('#matcol').val();
    var _idcod = $('#matcod').val();
	var pos = $('#clasi').val();
	if(_idmat=="" || _idcol=="" || _idcol==""){
		return false;
	}
    var fila = "<tr><td style='display: none;'>" +_idmat + "</td>"+
        "<tr><td>" +_texttipo + "</td>"+
        "<td>" + pos + "</td>"+
		"<td>" +_idcol+ "</td>"+
		"<td><input disabled type='color' value='"+_idcod+"'></td>"+
		"<td style='display: none;'>" +_idcod+ "</td>"+
		"<td>" +'<button type="button" name="remove" id="' +_idmat + '"onclick="eliminarMaterial(this)" class="btn btn-sm icon-btn btn-danger text-white btn_remove"><span class="btn-glyphicon spancircle fas fa-times fa-lg img-circle text-danger"></span>Eliminar</button></td>'+

		'</tr>';
	var campo_id;
	var pos_;
	$('#tablita tr').each(function () {
		if($(this).find('td').eq(0).html()!=null){
			if(campo_id==null){
				campo_id=$(this).find('td').eq(0).html()
				pos_=$(this).find('td').eq(2).html()
			}
			else{
				campo_id=$(this).find('td').eq(0).html()+","+campo_id;
				pos_=$(this).find('td').eq(2).html()+","+pos_;
			}
		}
	});
	if(campo_id!=null){
		console.log("entra2 "+campo_id);
		for(var j=0;j<=campo_id.split(",").length;j++){

			if(_idmat==campo_id.split(",")[j] && pos=='principal'){
				console.log("entra3 "+_idmat);
				return false;
			}
		}
	}
	var btn = document.createElement("TR");
	btn.innerHTML = fila;
	document.getElementById("tablita").appendChild(btn);
  });

function eliminarMaterial(t) {
	var td = t.parentNode;
	var tr = td.parentNode;
	var table = tr.parentNode;
	table.removeChild(tr);
}