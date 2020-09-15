 $(document).ready(function() {
	listarVendedores();
    listarEmpresas();
});
 

 function listarVendedores(){
		$.ajax({
			 method: "GET",
			    url: "/listarVendedores",
			    success: (data) => {
					for (i in data){
						if(data[i][2]==null || data[i][3]==null){
							$('#vendedorMovi').append("<option value='"+data[i][0]+"'>"+ data[i][1] + "</option>");
						}
						else{
							$('#vendedorMovi').append("<option value='"+data[i][0]+"'>"+ data[i][1] + " " + data[i][2] + " " + data[i][3] +"</option>");
						}
					}
					$('#vendedorMovi').selectpicker('refresh');
			    },
			    error: (e) => {
			    }
			});
	}
 
 function listarEmpresas(){

		$.ajax({
			 method: "GET",
			    url: "/listarEmpresasMovimiento",
			    success: (data) => {
					for (i in data){
						if(data[i].apellidoPaterno==null || data[i].apellidoMaterno==null){
							$('#empresaMovi').append("<option value='"+data[i].idCliente+"'>"+ data[i].nombre + "</option>");
						}
						else{
							$('#empresaMovi').append("<option value='"+data[i].idCliente+"'>"+ data[i].nombre + " " + data[i].apellidoPaterno + " " + data[i].apellidoMaterno +"</option>");
						}
					}
					$('#empresaMovi').selectpicker('refresh');
			    },
			    error: (e) => {
			        // location.reload();
			    }
			});
	}
 
 function limpiarModal(){
		$('#vendedorMovi').find("option").remove();
		$('#empresaMovi').find("option").remove();
		$('#encargadoRecibir').val("");
		$('#movimiento').val('');
		   listarEmpresas();
		   listarVendedores();
		   $('.selectCustom').selectpicker('refresh');
	}

 
 function foreach(root, selector, callback) {
	   if (typeof selector == 'string') {
	      var all = root.querySelectorAll(selector);
	      for (var each = 0; each < all.length; each++) {
	         callback(all[each]);
	      }
	   } else {
	      for (var each = 0; each < selector.length; each++) {
	         foreach(root, selector[each], callback);
	      }
	   }
	}


//Funcion para guardar un nuevo movimiento con sus muestras  //  
	function guardarNuevoMovimiento(tablaMuestra) {
	   var table = document.getElementById(tablaMuestra);
	   var filas = $("#tablaMuestra").find('tr'); 
	   var datosJson = [];
	   var vendedorMovi = $('#vendedorMovi').val();
	   var empresaMovi = $('#empresaMovi').val();
	   var movimiento = $('#movimiento').val();
	   var encargado = $('#encargadoRecibir').val();
	   var validacion=true;
	   var validador = 0;
	   var i = 0;


//hace uso de la funcion de foreach para validar que realmente estén llenados los campos en el modal  //
	   if(vendedorMovi=="" || vendedorMovi==null || vendedorMovi==undefined || 
		  empresaMovi=="" || empresaMovi==null || empresaMovi==undefined || shoppingCart.listCart()[0]==undefined ||
		  encargado=="" || encargado==null || encargado==undefined){
		   console.log("faltan datos");
		   Swal.fire({
				icon: 'error',
				title: 'Error',
				text: '¡Todos los campos deben de estar llenos!',
				showConfirmButton: false,
		        timer: 3500
			  })
			validacion=false;
	   }

//La función de este ciclo es realizar un JSON con todas las muestras agregadas en la tabla  //
	   if (table) {
		   if(validacion==true){
			   for(i=1; i<filas.length; i++){
				   var celdas = $(filas[i]).find("td");
			       var record = {cantidad:  $(celdas[0]).text(), 
								 nombreMuestra: $(celdas[1]).text(), 
								 modeloPrenda:  $(celdas[2]).text(), 
								 idPrenda:      $($(celdas[2]).children("input")[0]).val(),
								 codigoTela:    $(celdas[3]).text(),
								 idTela:      	$($(celdas[3]).children("input")[0]).val(),
								 idmuestra:		$($(celdas[3]).children("input")[1]).val()};
		         datosJson.push(record);
		         console.log($($(celdas[2]).children("input")[0]).val());
		         console.log($($(celdas[3]).children("input")[0]).val());
			   }
		   }
	   }
	   if(validacion==true){
console.log(encargado);
//AJAX para mandar los datos del JSON y los datos del vendedor y la empresa(Cliente)  //
		   $.ajax({
			   method: "POST",
			   url: "/guardarSolicitudMovimiento",
			   data:{
				   "_csrf": $('#token').val(),
				   vendedor: vendedorMovi,
				   empresa: empresaMovi,
				   encargado: encargado,
				   idMovimiento:movimiento,
				   "object_muestras": JSON.stringify(datosJson)
			   },
			   beforeSend: function () {
		        	 Swal.fire({
		                 title: 'Cargando ',
		                 html: 'Por favor espere',// add html attribute if you want or remove
		                 allowOutsideClick: false,
		                 timerProgressBar: true,
		                 onBeforeOpen: () => {
		                     Swal.showLoading()
		                 },
		             });
			   },
			   success: (data) => {
				   if(data==1){
					   Swal.fire({
							icon: 'success',
							title: 'Rack agregado',
							text: '¡Se ha modificado un movimiento!',
							showConfirmButton: false,
					        timer: 2000,
					        onClose: () => {
					        	$('#addTraspaso').modal(false);
					        	
					        }
						  })
				   }
				   else if(data==2){
					   Swal.fire({
							icon: 'success',
							title: 'Rack agregado',
							text: '¡Se ha agregado el Rack!',
							showConfirmButton: false,
					        timer: 2000,
					        onClose: () => {
					        	shoppingCart.clearCart();
					        	location.reload();
					        }
						  })
				   }
				   else{
					   Swal.fire({
							icon: 'error',
							title: '¡Algo salió mal!',
							text: 'Intente de nuevo más tarde',
							showConfirmButton: false,
					        timer: 2000,
					        onClose: () => {
					        }
						  })
				   }
			   },
			   error: (e) =>{
			   }
		   });
	   }
	   return datosJson;
	}


// ************************************************
// Shopping Cart API
// ************************************************

var shoppingCart = (function () {
  // =============================
  // Private methods and propeties
  // =============================
  cart = [];

  // Constructor
  function Item(name, price, count, value, idPedido, idPrenda, idTela, talla, nomTel) {
    this.name = name;
    this.price = price;
    this.count = count;
    this.value = value;
    this.idPedido = idPedido;
    this.idPrenda = idPrenda;
    this.idTela = idTela;
    this.talla = talla;
    this.nomTel = nomTel;
  }

  // Save cart
  function saveCart() {
    sessionStorage.setItem('shoppingCart', JSON.stringify(cart));
  }

  // Load cart
  function loadCart() {
    cart = JSON.parse(sessionStorage.getItem('shoppingCart'));
  }
  if (sessionStorage.getItem("shoppingCart") != null) {
    loadCart();
  }


  // =============================
  // Public methods and propeties
  // =============================
  var obj = {};

  // Add to cart
  obj.addItemToCart = function (name, price, count, value, idPedido, idPrenda, idTela, talla, nomTel) {
	  if(cart.length==0){
		  Swal.fire({
		      position: 'center',
		      icon: 'success',
		      title: '¡Añadido al Rack!',
		      showConfirmButton: false,
		      timer: 1550
		})
	  }
    for (var item in cart) {
      if (cart[item].idPedido === idPedido) {
    	  Swal.fire({
    	      position: 'center',
    	      icon: 'success',
    	      title: '¡Ya está en el Rack!',
    	      showConfirmButton: false,
    	      timer: 1550
    	})
        return;
      }
      else{
    	  Swal.fire({
    	      position: 'center',
    	      icon: 'success',
    	      title: '¡Añadido al Rack!',
    	      showConfirmButton: false,
    	      timer: 1550
    	})
      }
    }
    var item = new Item(name, price, count, value, idPedido, idPrenda, idTela, talla, nomTel);
    cart.push(item);
    saveCart();
  }
  // Set count from item
  obj.setCountForItem = function (name, count) {
    for (var i in cart) {
      if (cart[i].name === name) {
        cart[i].count = count;
        break;
      }
    }
  };
  // Remove item from cart
  obj.removeItemFromCart = function (name) {
    for (var item in cart) {
      if (cart[item].name === name) {
        cart[item].count--;
        if (cart[item].count === 0) {
          cart.splice(item, 1);
        }
        break;
      }
    }
    saveCart();
  }

  // Remove all items from cart
  obj.removeItemFromCartAll = function (name) {
    for (var item in cart) {
      if (cart[item].name === name) {
        cart.splice(item, 1);
        break;
      }
    }
    saveCart();
  }

  // Clear cart
  obj.clearCart = function () {
    cart = [];
    saveCart();
  }

  // Count cart 
  obj.totalCount = function () {
    var totalCount = 0;
    for (var item in cart) {
      totalCount += parseInt(cart[item].count);
    }
    return totalCount;
  }

  // Total cart
  obj.totalCart = function () {
    var totalCart = 0;
    for (var item in cart) {
      totalCart += cart[item].price * cart[item].count;
    }
    return Number(totalCart.toFixed(2));
  }

  // List cart
  obj.listCart = function () {
    var cartCopy = [];
    for (i in cart) {
      item = cart[i];
      itemCopy = {};
      for (p in item) {
        itemCopy[p] = item[p];

      }
      itemCopy.total = Number(item.price * item.count).toFixed(2);
      cartCopy.push(itemCopy)
    }
    return cartCopy;
  }

  // cart : Array
  // Item : Object/Class
  // addItemToCart : Function
  // removeItemFromCart : Function
  // removeItemFromCartAll : Function
  // clearCart : Function
  // countCart : Function
  // totalCart : Function
  // listCart : Function
  // saveCart : Function
  // loadCart : Function
  return obj;
})();

    	

// *****************************************
// Triggers / Events
// ***************************************** 
// Add item
$('.add-to-cart').click(function (event) {
	event.preventDefault();
	Swal.fire({
	    title: 'Seleccione la cantidad',
	    html: '<div class="row">' +
	        '<div class="form-group col-sm-12">' +
	        '<label for="cantidad">Cantidad</label>' +
	        '<input type="number" class="swal2-input" name="color" id="cantidad" placeholder="3">' +
	        '</div>' +
	        '</div>',
	    showCancelButton: true,
	    cancelButtonColor: '#dc3545',
	    cancelButtonText: 'Cancelar',
	    confirmButtonText: 'Agregar',
	    confirmButtonColor: '#0288d1',
	    preConfirm: (color) => {
	        if ($('#cantidad').val()=="" || $('#cantidad').val()==null || $('#cantidad').val()==undefined) {
	            Swal.showValidationMessage(
	                `Complete todos los campos`
	            )
	        }
	        
	        else if($('#cantidad').val() > $(this).data('cantidad')){
	        	Swal.showValidationMessage(
		                `Ha excedido la cantidad en stock`
		            )
	        }
	        else if($('#cantidad').val() < 1){
	        	Swal.showValidationMessage(
		                `No puede haber números negativos o 0`
		            )
	        }
	    }
	}).then((result) => {
	    if (result.value){
	    	
	    	  var name = $(this).data('name');
	    	  var price = Number($(this).data('price'));
	    	  var value = $(this).data('value');
	    	  var count = $('#cantidad').val();
	    	  var idPedido = $(this).data('pedido');
	    	  var idPrenda = $(this).data('idp');
	    	  var idTela = $(this).data('idt');
	    	  var talla = $(this).data('talla');
	    	  var nomTel = $(this).data('nomTel');
	    	  console.log(idPedido);
	    	  shoppingCart.addItemToCart(name, price, count, value, idPedido, idPrenda, idTela, talla, nomTel);
	    	  displayCart();
	    }
	})
});

// Clear items
$('.clear-cart').click(function () {
  shoppingCart.clearCart();
  displayCart();
});


function displayCart() {
  var cartArray = shoppingCart.listCart();
  var output = "";
  var ceros = "00";
  $('#movimiento').val('');
  $('.cartLess').remove();
  $('#exampleModalLongTitle').remove();
  $('.close').remove();
  $('#titleModal').append("<h5 class='modal-title' id='exampleModalLongTitle'>Rack</h5>"+
		  				   "<button type='button' class='close' data-dismiss='modal' aria-label='Close'>"+
  						   "<span aria-hidden='true'>&times;</span>"+
  						   "</button>");
  
  output =  "<thead>"+
                  "<tr>"+
                    "<th>Cantidad</th>"+
                    "<th>Prenda</th>"+
                    "<th>Modelo Prenda</th>"+
                    "<th>Precio</th>"+
                    "<th>Eliminar</th>"+
                  "</tr>"+
                "</thead>";
  console.log(cartArray);
  for (var i in cartArray) {
    output += "<tr>"
      + "<td id='cantidad"+cartArray[i].idPedido+"'>" + cartArray[i].count +"</td>" 
      + "<td id='nombreMuestra"+cartArray[i].idPedido+"'>" + cartArray[i].value + "</td>"
      + "<td id='modeloPrenda"+cartArray[i].idPedido+"'>"+ cartArray[i].name 
      + "<input type='hidden' id='idPrenda"+cartArray[i].idPedido+"' class='idMuestras' value='"+cartArray[i].idPrenda+"'>" + "</td>"
      + "<td>$" + cartArray[i].price
      + "<input type='hidden' id='idTela"+cartArray[i].idPedido+"' class='idMuestras' value='"+cartArray[i].idTela+"'>"
      + "<input type='hidden' id='idMuestras"+cartArray[i].idPedido+"' class='idMuestras' value='"+cartArray[i].idPedido+"'>"+ "</td>"
      + "<td class='tdcenter'><button class='delete-item btn btn-danger' data-name=" + cartArray[i].name + ">X</button></td>"
      + "</tr>";
  }
  $('#tablaMuestra').html(output);
  
  $('.total-cart').html(shoppingCart.totalCart());
  $('.total-count').html(shoppingCart.totalCount());
}

// Delete item button

$('#tablaMuestra').on("click", ".delete-item", function (event) {
  var name = $(this).data('name')
  shoppingCart.removeItemFromCartAll(name);
  displayCart();
})


// -1
$('#tablaMuestra').on("click", ".minus-item", function (event) {
  var name = $(this).data('name')
  shoppingCart.removeItemFromCart(name);
  displayCart();
})
// +1
$('#tablaMuestra').on("click", ".plus-item", function (event) {
  var name = $(this).data('name')
  shoppingCart.addItemToCart(name);
  displayCart();
})

// Item count input
$('#tablaMuestra').on("change", ".item-count", function (event) {
  var name = $(this).data('name');
  var count = Number($(this).val());
  shoppingCart.setCountForItem(name, count);
  displayCart();
});

displayCart();