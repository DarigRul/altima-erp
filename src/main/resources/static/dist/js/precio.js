$(document).ready(function () {
  var precioLocalNuevo = $("#precioLocalNuevo").val();
  var precioForaneNuevo = $("#precioForaneNuevo").val();
  var precioLineaExpressLocalNuevo = $("#precioLineaExpressLocalNuevo").val();
  var precioLineaExpressForaneoNuevo = $("#precioLineaExpressForaneoNuevo").val();
  $("#precioLocalNuevo").change(function () {
    document.getElementById("precioLocalAntiguo").value = precioLocalNuevo;
  });
  $("#precioForaneNuevo").change(function () {
    document.getElementById("precioForaneoAntiguo").value = precioForaneNuevo;
  });
  $("#precioLineaExpressLocalNuevo").change(function () {
    document.getElementById("precioLineaExpressLocalAnterior").value = precioLineaExpressLocalNuevo;
  });
  $("#precioLineaExpressForaneoNuevo").change(function () {
    document.getElementById("precioLineaExpressForaneoAnterior").value = precioLineaExpressForaneoNuevo;
  });
});

