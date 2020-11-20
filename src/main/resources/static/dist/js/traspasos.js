var movimientos = [];
var movimientoCabecero = [];
window.onload = function () {

  var today = new Date();
  var sevendays = new Date();
  var dd = today.getDate();
  var mm = today.getMonth() + 1; //January is 0!
  var yyyy = today.getFullYear();
  if (dd < 10) {
    dd = '0' + dd
  }
  if (mm < 10) {
    mm = '0' + mm
  }
  var dd2 = sevendays.getDate() - 7;
  var mm2 = sevendays.getMonth() + 1; //January is 0!
  var yyyy2 = sevendays.getFullYear();
  if (dd2 < 10) {
    dd2 = '0' + dd2
  }
  if (mm2 < 10) {
    mm2 = '0' + mm2
  }

  today = yyyy + '-' + mm + '-' + dd;
  sevendays = yyyy2 + '-' + mm2 + '-' + dd2;
  document.getElementById("fechaMovimiento").setAttribute("max", today);
  document.getElementById("fechaMovimiento").setAttribute("min", sevendays);
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
        $("#almacenOrigenTraspaso").append("<option value='" + data[0] + "' data-id='" + data[2] + "' data-tipo='" + data[13] + "' data-salida='" + data[4] + "'>" + data[1] + "</option>")
        $("#almacenDestinoTraspaso").append("<option value='" + data[0] + "' data-id='" + data[2] + "'  data-tipo='" + data[13] + "' data-entrada='" + data[6] + "'>" + data[1] + "</option>")
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
  $(`#movimientoSalida option[value=${$('#almacenOrigenTraspaso').children('option:selected').data('salida')}]`).prop('selected', true);
  $('#movimientoSalida').selectpicker('refresh');
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
        $("#articuloTraspaso").append("<option value='" + data.idMaterial + "' data-tipo='" + data.tipo + "'data-idText='" + data.idText + "'data-unidadMedida='" + data.unidadMedida + "'>" + data.idText + "-" + data.nombreMaterial + "</option>")
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
  var rollo = null;
  var idRollo = null;
  var lote = null;
  $('#agregarArticulo').prop("disabled", true);
  if (condicionAlmacen($("#articuloTraspaso").children('option:selected').data('tipo'),$("#almacenDestinoTraspaso").children('option:selected').data('tipo'),$("#almacenOrigenTraspaso").children('option:selected').data('tipo'))) {
    var cantidad = $('#rollo').children('option:selected').data('cantidad');
    idRollo = $('#rollo').val();
    rollo = $('#rollo').children('option:selected').data('idtext');
    lote = $('#rollo').children('option:selected').data('lote');
  } else {
    var cantidad = $('#cantidadTraspaso').val();
  }
  console.log(id);
  var temp = {
    'idText': idText,
    'unidadMedida': unidadMedida,
    'descripcion': descripcion,
    'tipo': tipo,
    'cantidad': cantidad,
    'id': id,
    'ubicacion': null,
    'idRollo': idRollo,
    'rollo': rollo,
    'lote': lote
  }
  // No deja agregar el mismo articulo
  // const found = movimientos.find(element => element.idText == temp.idText);
  // if (found != null) {
  //   return false;
  // }

  //validacion
  if (id == null || cantidad == null || id == "" || cantidad == "" || $('#almacenOrigenTraspaso').val() == "" || $('#almacenDestinoTraspaso').val() == "" || $('#almacenOrigenTraspaso').val() == null || $('#almacenDestinoTraspaso').val() == null) {
    Swal.fire({
      icon: 'error',
      title: 'Error',
      text: 'Todos los campos deben de estar llenos!',
    })
    $('#agregarArticulo').prop("disabled", false);
  } else {
    if ($('#almacenOrigenTraspaso').val() == $('#almacenDestinoTraspaso').val()) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Los Almacenes no pueden ser los mismos!',
      })
      $('#agregarArticulo').prop("disabled", false);

      return false;
    }
    $('#almacenOrigenTraspaso').prop("disabled", true);
    $('#almacenDestinoTraspaso').prop("disabled", true);
    $('#almacenOrigenTraspaso').selectpicker('refresh');
    $('#almacenDestinoTraspaso').selectpicker('refresh');
    if (cantidad <= 0) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'La cantidad debe ser mayor a 0!',
      })
      $('#agregarArticulo').prop("disabled", false);

    } else {

      $.ajax({
        type: "Get",
        url: "/getExistArticuloInAlmacen",
        data: {
          idAlmacenLogico: $('#almacenDestinoTraspaso').val(),
          idArticulo: id,
          Tipo: tipo
        },
        success: function (response) {
          if (response == "true") {
            $.ajax({
              type: "Get",
              url: "/getExistenciaArticulo",
              data: {
                idAlmacenLogico: $('#almacenOrigenTraspaso').val(),
                idArticulo: id,
                Tipo: tipo

              },
              success: function (response) {
                if ((response - cantidad) < 0) {
                  Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'La cantidad existente es: ' + response
                  })
                  $('#agregarArticulo').prop("disabled", false);

                  return false;
                }
                else {
                  if ($('#articuloTraspaso').children('option:selected').data('tipo') != 'tela') {
                    $('#articuloTraspaso').find('[value=' + temp.id + ']').remove();
                    $('#articuloTraspaso').selectpicker('refresh');
                  }
                  else {
                    $('#rollo').find('[value=' + temp.idRollo + ']').remove();
                    $('#rollo').selectpicker('refresh');
                  }
                  var fila = table.row.add(
                    [cantidad,
                      idText,
                      descripcion,
                      rollo == null ? 'N/A' : rollo,
                      unidadMedida,
                      '<a class="btn btn-danger btn-circle btn-sm delete" onclick="deleteMovimiento(this,`' + (tipo == 'tela' ? idRollo : id + tipo) + '`)"><i class="fas fa-times text-white"></i></a>' +
                      (condicionAlmacen($("#articuloTraspaso").children('option:selected').data('tipo'),$("#almacenDestinoTraspaso").children('option:selected').data('tipo'),$("#almacenOrigenTraspaso").children('option:selected').data('tipo')) ? '<button onClick="abrirUbicacion(`' + id + tipo + (idRollo == null ? '' : idRollo) + '`)" type="button" data-toggle="modal" id="modal_ubicacion" data-target="#modalUbicacion" class="btn btn-primary btn-circle btn-sm popoverxd"><i class="fas fa-thumbtack"></i></button>' : '')

                    ]
                  ).draw();
                  $.ajax({
                    type: "GET",
                    url: "getUbicacionByRollo",
                    data: { 'idRollo': idRollo },
                    success: function (data) {
                      if (data[0] != null && $('#almacenOrigenTraspaso').children('option:selected').data('id')==$('#almacenDestinoTraspaso').children('option:selected').data('id')) {
                        temp.ubicacion = data[0].idUbicacion;
                        movimientos.push(temp);
                      }
                      else {
                        movimientos.push(temp);
                      }
                    }
                  });
                  $('#agregarArticulo').prop("disabled", false);
                }
              }
            });
          } else {
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'El Articulo no esta agregado en este almacen!',
            })
            $('#agregarArticulo').prop("disabled", false);

          }
        }
      });

    }

  }
  console.log(movimientos);
});

function deleteMovimiento(fila, id) {
  // Se agrega al select
  if ($('#articuloTraspaso').children('option:selected').data('tipo') != 'tela') {
    const found = movimientos.find(element => element.id + element.tipo == id);
    $("#articuloTraspaso").append("<option value='" + found.idMaterial + "' data-tipo='" + found.tipo + "'data-idText='" + found.idText + "'data-unidadMedida='" + found.unidadMedida + "'>" + found.idText + "-" + found.nombreMaterial + "</option>")
    $('#articuloTraspaso').selectpicker('refresh');
  }
  else {
    const foundRollo = movimientos.find(element => element.idRollo == id);

    $("#rollo").append(`<option value="${foundRollo.idRollo}" data-cantidad="${foundRollo.cantidad}" data-lote="${foundRollo.lote}" data-idtext="${foundRollo.rollo}">${foundRollo.rollo}</option>`);
    $('#rollo').selectpicker('refresh');
  }
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
  var fechaMovimiento = $('#fechaMovimiento').val()
  var observaciones = $('#observacionesMovimientos').val()
  var condition = true;
  var idAlmacenFisico = $("#almacenDestinoTraspaso").children('option:selected').data('id')
  movimientos.forEach(data => {
    console.log(data);
    if (data.tipo == 'tela' && data.ubicacion == null&&$("#almacenDestinoTraspaso").children('option:selected').data('tipo')=='1'&& ($("#almacenOrigenTraspaso").children('option:selected').data('tipo') == '1'||$("#almacenOrigenTraspaso").children('option:selected').data('tipo') == '3')) {
      console.log("entra");
      condition = false;
      return false;
    }
  });
  $('#guardarTraspasos').prop("disabled", true);
  console.log(movimientos);
  if (movimientos[0] == null || almacenOrigenTraspaso[0] == null || movimientoSalida == null || almacenDestinoTraspaso == null || movimientoEntrada == null || fechaMovimiento == null || almacenOrigenTraspaso == "" || movimientoSalida == "" || almacenDestinoTraspaso == "" || movimientoEntrada == "" || fechaMovimiento == "") {
    Swal.fire({
      icon: 'error',
      title: 'Error',
      text: 'Todos los campos deben de estar llenos!',
    })
    $('#guardarTraspasos').prop("disabled", false);
  }
  else if (!condition) {
    Swal.fire({
      icon: 'error',
      title: 'Error',
      text: 'Ingresa todas las ubicaciones!',
    })
    $('#guardarTraspasos').prop("disabled", false);

  }
  else {
    temp = {
      'almacenOrigenTraspaso': almacenOrigenTraspaso,
      'movimientoSalida': movimientoSalida,
      'almacenDestinoTraspaso': almacenDestinoTraspaso,
      'movimientoEntrada': movimientoEntrada,
      'observaciones': observaciones,
      'fechaMovimiento': fechaMovimiento,
      'idAlmacenFisico': idAlmacenFisico

    }
    movimientoCabecero.push(temp);
    $.ajax({
      type: "POST",
      url: "postTraspasos",
      data: {
        'cabecero': JSON.stringify(movimientoCabecero),
        'movimientos': JSON.stringify(movimientos),
        '_csrf': $('[name=_csrf]').val()
      },
      success: function (msg) {

        if (msg == "Error") {
          Swal.fire({

            position: 'center',
            icon: 'error',
            title: 'Un error ocurrio durante el traspaso verifique los datos!',
            showConfirmButton: false,
            timer: 2500
          }).then((result) => {
            // Reload the Page
            $(location).attr('href', '/movimientos-amp')

          });
        }
        Swal.fire({
          position: 'center',
          icon: 'success',
          title: 'Transpaso generado correctamente!',
          showConfirmButton: false,
          timer: 2500
        }).then((result) => {
          // Reload the Page
          $(location).attr('href', '/movimientos-amp')

        });
      },
      error: (e) => {
        Swal.fire({

          position: 'center',
          icon: 'error',
          title: 'Un error ocurrio durante el transpaso verifique los datos!',
          showConfirmButton: false,
          timer: 2500
        }).then((result) => {
          // Reload the Page
          $(location).attr('href', '/movimientos-amp')

        });
      }
    });
  }
});


function abrirUbicacion(id) {
  $('#selectUbicacion option').remove();
  $('#selectUbicacion').selectpicker('refresh');
  $.ajax({
    type: "GET",
    url: "/listar-ubicacion-almacen",
    data: {
      'id': $('#almacenDestinoTraspaso').children('option:selected').data('id'),
      'estatus': true
    },
    success: function (data) {
      data.forEach(function (data) {
        //aqui va el codigo
        $("#selectUbicacion").append("<option value='" + data.idUbicacion + "' data-id='" + id + "'>" + data.nombre + "</option>")
      })
      objIndex = movimientos.findIndex((obj => obj.id + obj.tipo + (obj.idRollo == null ? '' : obj.idRollo) == id));
      $(`#selectUbicacion option[value=${movimientos[objIndex].ubicacion}]`).prop('selected', true);
      $('#selectUbicacion').selectpicker('refresh');
    }
  });

}

$('#almacenDestinoTraspaso').change(function (e) {
  $('#selectUbicacion option').remove();
  $('#selectUbicacion').selectpicker('refresh');
  $(`#movimientoEntrada option[value=${$('#almacenDestinoTraspaso').children('option:selected').data('entrada')}]`).prop('selected', true);
  $('#movimientoEntrada').selectpicker('refresh');
  e.preventDefault();
  $.ajax({
    method: "GET",
    url: "/listar-ubicacion-almacen",
    data: {
      "id": $(this).children('option:selected').data('id')
    },
    success: (data) => {

      data.forEach(function (data) {
        $("#selectUbicacion").append("<option value='" + data.idMaterial + "'>" + data.nombre + "</option>")
      });
      $('#selectUbicacion').selectpicker('refresh');


    }
  })
});
$("#agregarUbicacion").click(function (e) {
  e.preventDefault();
  if ($("#selectUbicacion").val() == null || $("#selectUbicacion").val() == '') {
    Swal.fire({
      icon: 'error',
      title: 'Error',
      text: 'Todos los campos deben de estar llenos!',
    })
  } else {
    objIndex = movimientos.findIndex((obj => obj.id + obj.tipo + (obj.idRollo == null ? '' : obj.idRollo) == $("#selectUbicacion").children('option:selected').data('id')));
    movimientos[objIndex].ubicacion = $("#selectUbicacion").val();
    console.log(movimientos[objIndex]);
    Swal.fire({
      position: 'center',
      icon: 'success',
      title: 'Ubicación agregada correctamente!',
      showConfirmButton: false,
      timer: 2500
    })
  }

});
$("#articuloTraspaso").change(function (e) {
  e.preventDefault();
  $('#rollo option').remove();
  $('#rollo').selectpicker('refresh');
  if (condicionAlmacen($("#articuloTraspaso").children('option:selected').data('tipo'),$("#almacenDestinoTraspaso").children('option:selected').data('tipo'),$("#almacenOrigenTraspaso").children('option:selected').data('tipo'))) {
    $("#rollo").prop("disabled", false);
    $("#cantidadTraspaso").prop("disabled", true);
    $('#rollo').selectpicker('refresh');
    $.ajax({
      type: "GET",
      url: "/getRolloByidAlmacenFisico",
      data: { 'idAlmacenFisico': $("#almacenOrigenTraspaso").children('option:selected').data('id'), 'idTela': $(this).val() },
      success: function (data) {
        data.forEach(function (data) {
          $("#rollo").append("<option value='" + data.idRolloTela + "' data-cantidad='" + data.cantidad + "' data-lote='" + data.lote + "' data-idText='" + data.idText + "'>" + data.idText+ "-" + data.cantidad +"-"+ data.lote + "</option>")
        })
        $('#rollo').selectpicker('refresh');
      }
    });
  }
  else {
    $("#rollo").prop("disabled", true);
    $("#cantidadTraspaso").prop("disabled", false);
    $('#rollo').selectpicker('refresh');
  }
});

$("#rollo").change(function (e) {
  e.preventDefault();
  $('#cantidadTraspaso').val($(this).children('option:selected').data('cantidad'));
});

function condicionAlmacen(tipo,almacenDestinoTraspaso,almacenOrigenTraspaso) {
  return tipo == 'tela' && almacenDestinoTraspaso == '1'&& (almacenOrigenTraspaso == '1'|| almacenOrigenTraspaso == '3')?true:false;
}