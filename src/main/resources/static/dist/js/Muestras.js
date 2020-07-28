//Agregar pedido muestra
function agregarFecha() {
    Swal.fire({
        title: 'Nueva muestra/reposicion',
        html:'<div class="row">'+
        '<div class="form-group col-sm-4">'+
         '<label for="acceso">Fecha de captura</label>'+
          '<input type="date" class="form-control" id="recepcion">'+
        '</div>'+
        '<div class="form-group col-sm-4">'+
         '<label for="acceso">Fecha de entrega</label>'+
          '<input type="date" class="form-control" id="entrega">'+
        '</div>'+
        '<div class="form-group col-sm-4">'+
        '<label>Cliente</label>'+
        '<select class="form-control select2" style="width: 100%;">'+
          '<option selected="selected">Alabama</option>'+
          '<option>Alaska</option>'+
          '<option>California</option>'+
          '<option>Delaware</option>'+
          '<option>Tennessee</option>'+
          '<option>Texas</option>'+
          '<option>Washington</option>'+
        '</select>'+
        '</div>'+
        '</div>'+
        '<div class="row">'+
        '<div class="form-group col-sm-12">'+
         '<label for="observacion">Observaciones</label>'+
          '<textarea class="form-control" rows="3" placeholder="Especificar" id="observacion"></textarea>'+
        '</div>'+
        '</div>'+
        '<div class="row">'+
        '<div class="form-group col-sm-3">'+
         '<label for="coor">Coordinaci&oacute;n</label>'+
          '<input type="text" class="form-control" id="coor">'+
        '</div>'+
        '<div class="form-group col-sm-3">'+
        '<label>Modelo</label>'+
        '<select class="form-control select2" style="width: 100%;">'+
          '<option selected="selected">Alabama</option>'+
          '<option>Alaska</option>'+
          '<option>California</option>'+
          '<option>Delaware</option>'+
          '<option>Tennessee</option>'+
          '<option>Texas</option>'+
          '<option>Washington</option>'+
        '</select>'+
        '</div>'+
        '<div class="form-group col-sm-3">'+
        '<label>Clave tela</label>'+
        '<select class="form-control select2" style="width: 100%;">'+
          '<option selected="selected">Alabama</option>'+
          '<option>Alaska</option>'+
          '<option>California</option>'+
          '<option>Delaware</option>'+
          '<option>Tennessee</option>'+
          '<option>Texas</option>'+
          '<option>Washington</option>'+
        '</select>'+
        '</div>'+
        '<div class="form-group col-sm-3">'+
         '<label for="coor">Cantidad</label>'+
          '<input type="text" class="form-control" id="coor">'+
        '</div>'+
        '</div>'+
        '<div class="row">'+
        '<div class="form-group col-sm-12">'+
         '<label for="observacion">Observaciones (tallas)</label>'+
          '<textarea class="form-control" rows="3" placeholder="Especificar" id="observacion"></textarea>'+
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
        customClass: 'swal-wide',
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