var movimientos = [];
var movimientoCabecero = [];
window.onload = function () {

  let url = '/get-all-amp-logico';

  fetch(url)
    .then(function (response) {
      if (response.ok) {
        console.log('Respuesta de red OK y respuesta HTTP OK');

      } else {
        console.log('Respuesta de red OK pero respuesta HTTP no OK');
      }
      return response.json();
    })
    .then(function (data) {
      data.forEach(function (data) {
        //aqui va el codigo
        $("#almacenOrigenTraspaso").append("<option value='" + data[0] + "' data-id='" + data[3] + "'>" + data[1] + "</option>")
        $("#almacenDestinoTraspaso").append("<option value='" + data[0] + "' data-id='" + data[3] + "'>" + data[1] + "</option>")
      })
      $('#almacenOrigenTraspaso').selectpicker('refresh');
      $('#almacenDestinoTraspaso').selectpicker('refresh');
    })
    .catch(function (error) {
      console.log('Hubo un problema con la petición Fetch:' + error.message);
    });

  let params = {
    "Tipo": 'Salida'
  };

  let query = Object.keys(params)
    .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(params[k]))
    .join('&');

  let urlsal = '/entradas-salidas?' + query;

  fetch(urlsal)
    .then(function (response) {
      if (response.ok) {
        console.log('Respuesta de red OK y respuesta HTTP OK');

      } else {
        console.log('Respuesta de red OK pero respuesta HTTP no OK');
      }
      return response.json();
    })
    .then(function (data) {
      data.forEach(function (data) {
        //aqui va el codigo
        $("#movimientoSalida").append("<option value='" + data.idLookup + "'>" + data.nombreLookup + "</option>")
      })
      $('#movimientoSalida').selectpicker('refresh');
    })
    .catch(function (error) {
      console.log('Hubo un problema con la petición Fetch:' + error.message);
    });

  let paramsEntrada = {
    "Tipo": 'Entrada'
  };

  let queryEntrada = Object.keys(paramsEntrada)
    .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(paramsEntrada[k]))
    .join('&');

  let urlEntrada = '/entradas-salidas?' + queryEntrada;

  fetch(urlEntrada)
    .then(function (response) {
      if (response.ok) {
        console.log('Respuesta de red OK y respuesta HTTP OK');

      } else {
        console.log('Respuesta de red OK pero respuesta HTTP no OK');
      }
      return response.json();
    })
    .then(function (data) {
      data.forEach(function (data) {
        //aqui va el codigo
        $("#movimientoEntrada").append("<option value='" + data.idLookup + "'>" + data.nombreLookup + "</option>")
      })
      $('#movimientoEntrada').selectpicker('refresh');
    })
    .catch(function (error) {
      console.log('Hubo un problema con la petición Fetch:' + error.message);
    });

}

$('#almacenOrigenTraspaso').change(function () {
  $('#articuloTraspaso option').remove();
  $('#articuloTraspaso').selectpicker('refresh');

  let params = {
    "idAlmacenLogico": $("#almacenOrigenTraspaso").val()
  };

  let query = Object.keys(params)
    .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(params[k]))
    .join('&');

  let url = '/getArticulosMultialmacen?' + query;

  fetch(url)
    .then(function (response) {
      if (response.ok) {
        console.log('Respuesta de red OK y respuesta HTTP OK');

      } else {
        console.log('Respuesta de red OK pero respuesta HTTP no OK');
      }
      return response.json();
    })
    .then(function (data) {
      data.forEach(function (data) {
        //aqui va el codigo
        $("#articuloTraspaso").append("<option value='" + data.idMaterial + "' data-tipo='" + data.tipo + "'data-idText='" + data.idText + "'data-unidadMedida='" + data.unidadMedida + "'>" + data.nombreMaterial + "</option>")
      })
      $('#articuloTraspaso').selectpicker('refresh');
    })
    .catch(function (error) {
      console.log('Hubo un problema con la petición Fetch:' + error.message);
    });
});

//Agregamos el Articulo
$('#agregarArticulo').click(function () {
  var idText = $('#articuloTraspaso').children('option:selected').data('idtext');
  var unidadMedida = $('#articuloTraspaso').children('option:selected').data('unidadmedida');
  var descripcion = $('#articuloTraspaso').children('option:selected').text();
  var tipo = $('#articuloTraspaso').children('option:selected').data('tipo');
  var id = $('#articuloTraspaso').val();
  var cantidad = $('#cantidadTraspaso').val();
  console.log(id);
  var temp = {
    'idText': idText,
    'unidadMedida': unidadMedida,
    'descripcion': descripcion,
    'tipo': tipo,
    'cantidad': cantidad,
    'id': id
  }

  const found = movimientos.find(element => element.idText == temp.idText);
  if (found != null) {
    return false;
  }

  //validacion
  if (id == null || cantidad == null || id == "" || cantidad == "") {
    Swal.fire({
      icon: 'error',
      title: 'Error',
      text: 'Todos los campos deben de estar llenos!',
    })
  } else {
    if (cantidad <= 0) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'La cantidad debe ser mayor a 0!',
      })
    } else {
      $('#articuloTraspaso').find('[value=' + temp.id + ']').remove();
      $('#articuloTraspaso').selectpicker('refresh');
      var fila = table.row.add(
        [cantidad,
          idText,
          descripcion,
          unidadMedida,
          '<a class="btn btn-danger btn-circle btn-sm delete" onclick="deleteMovimiento(this,`' + id + tipo + '`)"><i class="fas fa-times text-white"></i></a>']
      ).draw();
      movimientos.push(temp);
    }

  }
  console.log(movimientos);
});

function deleteMovimiento(fila, id) {
  // Se agrega al select
  const found = movimientos.find(element => element.id + element.tipo == id);
  console.log(found);
  $("#articuloTraspaso").append("<option value='" + found.id + "' data-tipo='" + found.tipo + "'data-idText='" + found.idText + "'data-unidadMedida='" + found.unidadMedida + "'>" + found.descripcion + "</option>");
  $('#articuloTraspaso').selectpicker('refresh');

  // Se elimina el objeto del array de objetos
  var removeIndex = movimientos.map(function (item) { return item.id + item.tipo; }).indexOf(id);
  movimientos.splice(removeIndex, 1);
  table
    .row($(fila).parents('tr'))
    .remove()
    .draw();
}

$('#guardarTraspasos').click(function () {
  var almacenOrigenTraspaso = $('#almacenOrigenTraspaso').val()
  var movimientoSalida = $('#movimientoSalida').val()
  var almacenDestinoTraspaso = $('#almacenDestinoTraspaso').val()
  var movimientoEntrada = $('#movimientoEntrada').val()
  console.log(movimientos);
  if (almacenOrigenTraspaso[0] == null || movimientoSalida == null || almacenDestinoTraspaso == null || movimientoEntrada == null || almacenOrigenTraspaso == "" || movimientoSalida == "" || almacenDestinoTraspaso == "" || movimientoEntrada == "") {
      Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'Todos los campos deben de estar llenos!',
      })
  }
  else {
      temp = {
          'almacenOrigenTraspaso': almacenOrigenTraspaso,
          'movimientoSalida': movimientoSalida,
          'almacenDestinoTraspaso': almacenDestinoTraspaso,
          'movimientoEntrada': movimientoEntrada
      }
      movimientoCabecero.push(temp);
          $.ajax({
              type: "POST",
              url: "postTraspasos",
              data: {
                  'cabecero': JSON.stringify(movimientoCabecero),
                  'movimientos': JSON.stringify( ),
                  '_csrf': $('[name=_csrf]').val()
              },
              success: function (msg) {

                  if (msg == "Error") {
                      alert("Error en el servidor");
                  }
                  $(location).attr('href', '/movimientos-amp')
              },
              error: (e) => {
                  alert(e);
              }
          });

  }

});