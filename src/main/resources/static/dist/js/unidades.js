function addUnity(){
    var no_placaJS = $('#placaUnidad').val();
    var modeloJS = $('#modeloUnidad').val();
    var choferJS = $('#choferUnidad').val();
    var idJS = $('#idUnidad').val();

    $.ajax({
      type: "POST",
      url: "/logistica-catalogos-unidades-editar",
      data: {
          no_placaJS:no_placaJS,
          idJS:idJS,
          modeloJS:modeloJS,
          choferJS:choferJS
      },
      success: (data) => {
        console.log(data);
        if (data == 1) {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Unidad editada correctamente",
            showConfirmButton: false,
            timer: 2300
          });
        } else {
          Swal.fire({
            position: "center",
            icon: "error",
            title: "Datos erroneos",
            showConfirmButton: false,
            timer: 2300
          });
        }
      },
    }); 
}
function editUnity(idJS){
    $('#nuevaUnidad').modal('show');
    $.ajax({
      method: "GET",
      url: "/logistica-catalogos-unidades-datos",
      data: {
        idJS:idJS,
      },
      success: (data) => {
        $('#idUnidad').val(data.idUnidad);
        $('#placaUnidad').val(data.idText);
        $('#modeloUnidad').val(data.modelo);
        if (data.idEmpleado != null) {
            $('#choferUnidad option[value="' + data.idEmpleado + '"]').attr("selected", true);
            $('#choferUnidad').selectpicker('refresh');
        }
      },
      error: (e) => {
        alert("Error en el servidor");
      },
    });
}
function activarUnidad() {
    Swal.fire({
        title: '¿La unidad estar&aacute; en circulaci&oacute;n?',
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        confirmButtonColor: '#0288d1',
        confirmButtonText: 'Confirmar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.value) {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Unidad en circulaci&oacute;n',
                showConfirmButton: false,
                timer: 2500
            })
        }
    })
}
function inactivaUnidad() {
    Swal.fire({
        title: '¿La unidad estar&aacute; fuera de circulaci&oacute;n?',
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#dc3545',
        confirmButtonColor: '#0288d1',
        confirmButtonText: 'Confirmar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.value) {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Unidad fuera de circulaci&oacute;n',
                showConfirmButton: false,
                timer: 2500
            })
        }
    })
}