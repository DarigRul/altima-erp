// Habilitar campos para Agente
$("#detalleAgente").on("shown.bs.modal", function () {
  $(document).off("focusin.modal");
});
function agregarAgente() {
  Swal.fire({
    title: "Nuevo agente",
    html: "<label>Form Agente</label>",
    showCancelButton: true,
    confirmButtonText: "Confirmar",
    cancelButtonText: "Cancelar",
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
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
    html: "<label>Form Agente</label>",
    showCancelButton: true,
    confirmButtonText: "Confirmar",
    cancelButtonText: "Cancelar",
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
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
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
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
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
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
      html: "<label>Form Modelo</label>",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
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
      html: "<label>Form Modelo</label>",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
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
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
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
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
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
      html: "<label>Form Precio</label>",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
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
      html: "<label>Form Precio</label>",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
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
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
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
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
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
      html: "<label>Form IVA</label>",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
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
      html: "<label>Form IVA</label>",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
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
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
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
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
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
      html: "<label>Form Ticket</label>",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
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
      html: "<label>Form Ticket</label>",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
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
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
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
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
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
  

