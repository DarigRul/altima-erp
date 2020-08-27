function editUnity(idJS){
    $('#nuevaUnidad').modal('show');
    $.ajax({
      method: "GET",
      url: "/logistica-catalogos-unidades-datos",
      data: {
        idJS:idJS,
        "_csrf": $('#token').val()
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