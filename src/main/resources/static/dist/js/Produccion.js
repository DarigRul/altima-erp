//Agregar tela
function editarPedido() {
    Swal.fire({
        title: 'Editar pedido',
        html:'<div class="row">'+
        '<div class="form-group col-sm-12">'+
          '<label for="pedidonom">Pedido</label>'+
          '<input type="text" class="form-control" disabled id="pedidonom" value="SDF234">'+
        '</div>'+
        '<div class="form-group col-sm-6">'+
          '<label for="acceso">Clave de acceso</label>'+
          '<input type="text" class="form-control" id="acceso">'+
        '</div>'+
        '<div class="form-group col-sm-6">'+
         '<label for="acceso">Fecha de entrega</label>'+
          '<input type="date" class="form-control" id="entrega">'+
        '</div>'+
        '</div>'+
        '<div class="row">'+
        '<div class="form-group col-sm-6">'+
         '<label for="acceso">Fecha de recepci&oacute;n</label>'+
          '<input type="date" class="form-control" id="recepcion">'+
        '</div>'+
        '<div class="form-group col-sm-6">'+
         '<label for="acceso">D&iacute;as de entrega</label>'+
          '<input type="text" class="form-control" id="entrega">'+
        '</div>'+
        '</div>'+
        '<div class="row">'+
        '<div class="form-group col-sm-6">'+
         '<label for="acceso">Fecha de anticipo</label>'+
          '<input type="date" class="form-control" id="anticipo">'+
        '</div>'+
        '<div class="form-group col-sm-6">'+
         '<label for="acceso">Fecha toma de tallas</label>'+
          '<input type="date" class="form-control" id="tomatallas">'+
        '</div>'+
        '<div class="form-group col-sm-12">'+
            '<div class="custom-control custom-switch custom-switch-off-danger custom-switch-on-success">'+
            '<input type="checkbox" class="custom-control-input" id="pago">'+
            '<label class="custom-control-label" for="pago">Pago anticipado</label>'+
            '</div>'+
        '</div>'+
        '</div>',
        inputAttributes: {
          autocapitalize: 'off'
        },
        showCancelButton: true,
        cancelButtonColor: '#6C757D',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Guardar',
        confirmButtonColor:'#007BFF',
        showLoaderOnConfirm: true,
        preConfirm: (login) => {
          return fetch(`//api.github.com/users/${login}`)
            .then(response => {
              if (!response.ok) {
                throw new Error(response.statusText)
              }
              return response.json()
            })
            .catch(error => {
              Swal.showValidationMessage(
                `Error: ${error}`
              )
            })
        },
        allowOutsideClick: () => !Swal.isLoading()
      }).then((result) => {
        if (result.value) {
          Swal.fire({
            title: `${result.value.login}'s avatar`,
            imageUrl: result.value.avatar_url
          })
        }
      })
  }