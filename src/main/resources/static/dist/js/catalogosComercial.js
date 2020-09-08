// Habilitar campos para Agente
$("#detalleAgente").on("shown.bs.modal", function () {
  $(document).off("focusin.modal");
});
function agregarAgente() {
  Swal.fire({
    title: "Nuevo agente",
    html: '<div class="row">'+
                '<div class="form-group col-md-12">'+
                '<label for="nombreAgente">Nombre</label>'+
                  '<select class="form-control" id="nombreAgente">'+
                    '<option value="1">Agente 1</option>'+
                    '<option value="1">Agente 2</option>'+
                  '</select>'+
                '</div>'+
                '<div class="form-group col-md-12">'+
                  '<h4>Caracter&iacute;stica de venta</h4>'+
                '</div>'+
                '<div class="form-group col-md-6">'+
                  '<div class="form-check">'+
                  '<input class="form-check-input" type="checkbox" value="" id="foraneoAgente">'+
                    '<label class="form-check-label" for="foraneoAgente">'+
                      'For&aacute;neos'+
                    '</label>'+
                  '</div>'+
                '</div>'+
                '<div class="form-group col-md-6">'+
                  '<div class="form-check">'+
                  '<input class="form-check-input" type="checkbox" value="" id="licitacionAgente">'+
                    '<label class="form-check-label" for="licitacionAgente">'+
                      'Licitaciones'+
                    '</label>'+
                  '</div>'+
                '</div>'+
            '</div>',
    showCancelButton: true,
    confirmButtonText: "Confirmar",
    cancelButtonText: "Cancelar",
    confirmButtonColor: "#0288d1",
    cancelButtonColor: "#dc3545",
  }).then((result) => {
    if (result.value) {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Agente agregado correctamente",
        showConfirmButton: false,
        timer: 2500,
      });
    }
  });
}
function editarAgente() {
  Swal.fire({
    title: "Editar agente",
    html: '<div class="row">'+
                '<div class="form-group col-md-12">'+
                '<label for="nombreAgenteE">Nombre</label>'+
                  '<select class="form-control" id="nombreAgenteE">'+
                    '<option value="1">Agente 1</option>'+
                    '<option value="1">Agente 2</option>'+
                  '</select>'+
                '</div>'+
                '<div class="form-group col-md-12">'+
                  '<h4>Caracter&iacute;stica de venta</h4>'+
                '</div>'+
                '<div class="form-group col-md-6">'+
                  '<div class="form-check">'+
                  '<input class="form-check-input" type="checkbox" value="" id="foraneoAgenteE">'+
                    '<label class="form-check-label" for="foraneoAgenteE">'+
                      'For&aacute;neos'+
                    '</label>'+
                  '</div>'+
                '</div>'+
                '<div class="form-group col-md-6">'+
                  '<div class="form-check">'+
                  '<input class="form-check-input" type="checkbox" value="" id="licitacionAgenteE">'+
                    '<label class="form-check-label" for="licitacionAgenteE">'+
                      'Licitaciones'+
                    '</label>'+
                  '</div>'+
                '</div>'+
            '</div>',
    showCancelButton: true,
    confirmButtonText: "Confirmar",
    cancelButtonText: "Cancelar",
    confirmButtonColor: "#0288d1",
    cancelButtonColor: "#dc3545",
  }).then((result) => {
    if (result.value) {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Agente editado correctamente",
        showConfirmButton: false,
        timer: 2500,
      });
    }
  });
}
function bajarAgente() {
  Swal.fire({
    title: "¿Deseas dar de baja al agente?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Confirmar",
    cancelButtonText: "Cancelar",
    confirmButtonColor: "#0288d1",
    cancelButtonColor: "#dc3545",
  }).then((result) => {
    if (result.value) {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Agente dado de baja correctamente",
        showConfirmButton: false,
        timer: 2500,
      });
    }
  });
}
function altaAgente() {
  Swal.fire({
    title: "¿Deseas dar de alta al agente?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Confirmar",
    cancelButtonText: "Cancelar",
    confirmButtonColor: "#0288d1",
    cancelButtonColor: "#dc3545",
  }).then((result) => {
    if (result.value) {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Agente dado de alta correctamente",
        showConfirmButton: false,
        timer: 2500,
      });
    }
  });
}
// Habilitar campos para Modelo
$("#detalleModelo").on("shown.bs.modal", function () {
    $(document).off("focusin.modal");
  });
  function agregarModelo() {
    Swal.fire({
      title: "Nuevo modelo",
      html: '<div class="row">'+
                '<div class="form-group col-md-4">'+
                  '<label for="nombreModelo">Nombre</label>'+
                  '<input type="text" class="form-control" id="nombreModelo" placeholder="Mariela">'+
                '</div>'+
                '<div class="form-group col-md-4">'+
                  '<label for="telefonoModelo">Tel&eacute;fono</label>'+
                  '<input type="text" class="form-control" id="telefonoModelo" placeholder="55 123 43 12">'+
                '</div>'+
                '<div class="form-group col-md-4">'+
                  '<label for="precioModelo">Precio presentaci&oacute;n</label>'+
                  '<input type="number" class="form-control" id="precioModelo" placeholder="10000">'+
                '</div>'+
            '</div>',
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      customClass: 'swal-modelo',
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Modelo agregado correctamente",
          showConfirmButton: false,
          timer: 2500,
        });
      }
    });
  }
  function editarModelo() {
    Swal.fire({
      title: "Editar modelo",
      html: '<div class="row">'+
                '<div class="form-group col-md-4">'+
                  '<label for="nombreModeloE">Nombre</label>'+
                  '<input type="text" class="form-control" id="nombreModeloE" placeholder="Mariela">'+
                '</div>'+
                '<div class="form-group col-md-4">'+
                  '<label for="telefonoModeloE">Tel&eacute;fono</label>'+
                  '<input type="text" class="form-control" id="telefonoModeloE" placeholder="55 123 43 12">'+
                '</div>'+
                '<div class="form-group col-md-4">'+
                  '<label for="precioModeloE">Precio presentaci&oacute;n</label>'+
                  '<input type="number" class="form-control" id="precioModeloE" placeholder="10000">'+
                '</div>'+
            '</div>',
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
      customClass: 'swal-modelo',
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Modelo editado correctamente",
          showConfirmButton: false,
          timer: 2500,
        });
      }
    });
  }
  function bajarModelo() {
    Swal.fire({
      title: "¿Deseas dar de baja al modelo?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Modelo dado de baja correctamente",
          showConfirmButton: false,
          timer: 2500,
        });
      }
    });
  }
  function altaModelo() {
    Swal.fire({
      title: "¿Deseas dar de alta al modelo?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Modelo dado de alta correctamente",
          showConfirmButton: false,
          timer: 2500,
        });
      }
    });
  }
  
// Habilitar campos para Precio
$("#detallePrecio").on("shown.bs.modal", function () {
    $(document).off("focusin.modal");
  });
  function agregarPrecio() {
    Swal.fire({
      title: "Nuevo precio",
      html: '<div class="row">'+
              '<div class="form-group col-md-6">'+
                '<label for="descripcionPrecio">Descripci&oacute;n</label>'+
                '<input type="text" class="form-control" id="descripcionPrecio" placeholder="Especificar">'+
              '</div>'+
              '<div class="form-group col-md-6">'+
                '<label for="numeroPrecio">Precio</label>'+
                '<input type="number" class="form-control" id="numeroPrecio" placeholder="30">'+
              '</div>'+
          '</div>',
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Precio agregado correctamente",
          showConfirmButton: false,
          timer: 2500,
        });
      }
    });
  }
  function editarPrecio() {
    Swal.fire({
      title: "Editar precio",
      html: '<div class="row">'+
              '<div class="form-group col-md-6">'+
                '<label for="descripcionPrecioE">Descripci&oacute;n</label>'+
                '<input type="text" class="form-control" id="descripcionPrecioE" placeholder="Especificar">'+
              '</div>'+
              '<div class="form-group col-md-6">'+
                '<label for="numeroPrecioE">Precio</label>'+
                '<input type="number" class="form-control" id="numeroPrecioE" placeholder="30">'+
              '</div>'+
          '</div>',
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Precio editado correctamente",
          showConfirmButton: false,
          timer: 2500,
        });
      }
    });
  }
  function bajarPrecio() {
    Swal.fire({
      title: "¿Deseas dar de baja al precio?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Precio dado de baja correctamente",
          showConfirmButton: false,
          timer: 2500,
        });
      }
    });
  }
  function altaPrecio() {
    Swal.fire({
      title: "¿Deseas dar de alta al precio?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Precio dado de alta correctamente",
          showConfirmButton: false,
          timer: 2500,
        });
      }
    });
  }
  
// Habilitar campos para IVA
$("#detalleIVA").on("shown.bs.modal", function () {
    $(document).off("focusin.modal");
  });
  function agregarIVA() {
    Swal.fire({
      title: "Nuevo IVA",
      html: '<div class="row">'+
              '<div class="form-group col-md-12">'+
                '<label for="numeroPorcentaje">Porcentaje</label>'+
                '<input type="number" class="form-control" id="numeroPorcentaje" placeholder="16">'+
              '</div>'+
          '</div>',
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "IVA agregado correctamente",
          showConfirmButton: false,
          timer: 2500,
        });
      }
    });
  }
  function editarIVA() {
    Swal.fire({
      title: "Editar IVA",
      html: '<div class="row">'+
              '<div class="form-group col-md-12">'+
                '<label for="numeroPorcentajeE">Porcentaje</label>'+
                '<input type="number" class="form-control" id="numeroPorcentajeE" placeholder="16">'+
              '</div>'+
          '</div>',
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "IVA editado correctamente",
          showConfirmButton: false,
          timer: 2500,
        });
      }
    });
  }
  function bajarIVA() {
    Swal.fire({
      title: "¿Deseas dar de baja al IVA?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "IVA dado de baja correctamente",
          showConfirmButton: false,
          timer: 2500,
        });
      }
    });
  }
  function altaIVA() {
    Swal.fire({
      title: "¿Deseas dar de alta al IVA?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "IVA dado de alta correctamente",
          showConfirmButton: false,
          timer: 2500,
        });
      }
    });
  }
  

 
// Habilitar campos para Ticket
$("#detalleTicket").on("shown.bs.modal", function () {
    $(document).off("focusin.modal");
  });
  function agregarTicket() {
    Swal.fire({
      title: "Nuevo ticket",
      html: '<div class="row">'+
                '<div class="form-group col-md-12">'+
                '<label for="descripcionTicket">Descripci&oacute;n</label>'+
                  '<input type="text" placeholder="Especificar" class="form-control" id="descripcionTicket">'+
                '</div>'+
                '<div class="form-group col-md-12">'+
                  '<h4>¿Qui&eacute;n puede agregar?</h4>'+
                '</div>'+
                '<div class="form-group col-md-6">'+
                  '<div class="form-check">'+
                  '<input class="form-check-input" type="checkbox" value="" id="auxiliarTicket">'+
                    '<label class="form-check-label" for="auxiliarTicket">'+
                      'Auxiliar'+
                    '</label>'+
                  '</div>'+
                '</div>'+
                '<div class="form-group col-md-6">'+
                  '<div class="form-check">'+
                  '<input class="form-check-input" type="checkbox" value="" id="solicitanteTicket">'+
                    '<label class="form-check-label" for="solicitanteTicket">'+
                      'Solicitante'+
                    '</label>'+
                  '</div>'+
                '</div>'+
            '</div>',
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Ticket agregado correctamente",
          showConfirmButton: false,
          timer: 2500,
        });
      }
    });
  }
  function editarTicket() {
    Swal.fire({
      title: "Editar ticket",
      html: '<div class="row">'+
                '<div class="form-group col-md-12">'+
                '<label for="descripcionTicketE">Descripci&oacute;n</label>'+
                  '<input type="text" placeholder="Especificar" class="form-control" id="descripcionTicketE">'+
                '</div>'+
                '<div class="form-group col-md-12">'+
                  '<h4>¿Qui&eacute;n puede agregar?</h4>'+
                '</div>'+
                '<div class="form-group col-md-6">'+
                  '<div class="form-check">'+
                  '<input class="form-check-input" type="checkbox" value="" id="auxiliarTicketE">'+
                    '<label class="form-check-label" for="auxiliarTicketE">'+
                      'Auxiliar'+
                    '</label>'+
                  '</div>'+
                '</div>'+
                '<div class="form-group col-md-6">'+
                  '<div class="form-check">'+
                  '<input class="form-check-input" type="checkbox" value="" id="solicitanteTicketE">'+
                    '<label class="form-check-label" for="solicitanteTicketE">'+
                      'Solicitante'+
                    '</label>'+
                  '</div>'+
                '</div>'+
            '</div>',
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Ticket editado correctamente",
          showConfirmButton: false,
          timer: 2500,
        });
      }
    });
  }
  function bajarTicket() {
    Swal.fire({
      title: "¿Deseas dar de baja al ticket?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Ticket dado de baja correctamente",
          showConfirmButton: false,
          timer: 2500,
        });
      }
    });
  }
  function altaTicket() {
    Swal.fire({
      title: "¿Deseas dar de alta al ticket?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#0288d1",
      cancelButtonColor: "#dc3545",
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Ticket dado de alta correctamente",
          showConfirmButton: false,
          timer: 2500,
        });
      }
    });
  }
  

