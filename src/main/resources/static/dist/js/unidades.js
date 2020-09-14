//Funcion para agregar empresas
function addUnity() {
    $('#nuevaUnidad').modal('show');
    if ($('#placaUnidad').val()) {
      var placaJS = $("#placaUnidad").val();
      var modeloJS = $("#modeloUnidad").val();
      var choferJS = $("#choferUnidad").val();
      var idJS = $("#idUnidad").val();
      $.ajax({
        type: "GET",
        url: "/duplicado-unidad",
        data: {
          "placaJS":placaJS
        },
      }).done(function (data) {
        if (data == false) {
          $.ajax({
            type: "GET",
            url: "/duplicado-id-unidad",
            data: {
              "idJS":idJS
            },
          }).done(function (data) {
            if (data == true) {
          $.ajax({
            type: "POST",
            url: "/unidad-nueva",
            data: {
              _csrf: $("#token").val(),
              placaJS: placaJS,
              modeloJS: modeloJS,
              choferJS: choferJS,
              idJS: idJS,

            },
            success: (data) => {
              if (data == 1) {
                Swal.fire({
                  position: "center",
                  icon: "success",
                  title: "Unidad editada correctamente",
                  showConfirmButton: false,
                  timer: 2500
                });
              } else if (data == 2) {
                Swal.fire({
                  position: "center",
                  icon: "success",
                  title: "Unidad agregada correctamente",
                  showConfirmButton: false,
                  timer: 2500
                });
              } else {
                Swal.fire({
                  icon: "error",
                  title: "Error",
                  text: "&iexcl;Intente de nuevo!",
                });
              }
            },
          }).done(function (data) {
            setTimeout(function() {
              location.reload();
          }, 2400);
          });
        
            } else {
              Swal.fire({
                position: "center",
                icon: "error",
                title: "El n&uacute;mero de la placa ya existe",
                showConfirmButton: false,
                timer: 2000,
              });
              console.log('Numero de placa existente 1')
            }
          });
        } else {
          Swal.fire({
            position: "center",
            icon: "error",
            title: "El n&uacute;mero de la placa ya existe",
            showConfirmButton: false,
            timer: 2000,
          });
          console.log('Numero de placa existente 2')
        }
      });
    }
}
function editUnity(idJS) {
  $('#nuevaUnidad').modal('show');
  $.ajax({
    method: "GET",
    url: "/logistica-catalogos-unidades-datos",
    data: {
      idJS: idJS,
      _csrf: $("#token").val(),
    },
    success: (data) => {
      $("#idUnidad").val(data.idUnidad);
      $("#placaUnidad").val(data.idText);
      $("#modeloUnidad").val(data.modelo);
      if (data.idEmpleado != null) {
        $('#choferUnidad option[value="' + data.idEmpleado + '"]').attr(
          "selected",
          true
        );
        $("#choferUnidad").selectpicker("refresh");
      }
    },
    error: (e) => {
      alert("Error en el servidor");
    },
  });
}

function activarUnidad(idJS) {
  Swal.fire({
    title: "¿La unidad estar&aacute; en circulaci&oacute;n?",
    icon: "warning",
    showCancelButton: true,
    cancelButtonColor: "#dc3545",
    confirmButtonColor: "#0288d1",
    confirmButtonText: "Confirmar",
    cancelButtonText: "Cancelar",
  }).then((result) => {
    if (result.value) {
      $.ajax({
        type: "GET",
        url: "/alta-unidad",
        data: {
            "_csrf": $('#token').val(),
            "idJS": idJS
        },
        success: (data) => {
        },
        error: function (data) {
            alert("Error en el servidor");
        }
    });
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Unidad en circulaci&oacute;n",
        showConfirmButton: false,
        timer: 2500,
      });
      setTimeout(function() {
        location.reload();
    }, 2300);
    }
  });
}
function inactivaUnidad(idJS) {
  Swal.fire({
    title: "¿La unidad estar&aacute; fuera de circulaci&oacute;n?",
    icon: "warning",
    showCancelButton: true,
    cancelButtonColor: "#dc3545",
    confirmButtonColor: "#0288d1",
    confirmButtonText: "Confirmar",
    cancelButtonText: "Cancelar",
  }).then((result) => {
    if (result.value) {
      $.ajax({
        type: "GET",
        url: "/baja-unidad",
        data: {
            "_csrf": $('#token').val(),
            "idJS": idJS
        },
        success: (data) => {
        },
        error: function (data) {
            alert("Error en el servidor");
        }
    });
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Unidad fuera de circulaci&oacute;n",
        showConfirmButton: false,
        timer: 2500,
      });
      setTimeout(function() {
        location.reload();
    }, 2300);
    }
  });
}

$("#agregarUnidad").click( function()
           {
             $('#placaUnidad').val('');
             $('#choferUnidad option:selected').removeAttr("selected");
             $("#choferUnidad").selectpicker("refresh");
             $('#modeloUnidad').val('');
             $('#idUnidad').val('');
           }
      );