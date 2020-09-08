if( /Android|webOS|iPhone|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
	function changeClassById() {
		document.getElementById("medidasCatalogo").className = "col-lg-4 col-12";
		document.getElementById("coloresCatalogo").className = "col-lg-4 col-12";
		document.getElementById("trazosCatalogo").className = "col-lg-4 col-12";
		document.getElementById("cuidadosCatalogo").className = "col-lg-4 col-12";
		document.getElementById("prendasCatalogo").className = "col-lg-4 col-12";
		document.getElementById("generosCatalogo").className = "col-lg-4 col-12";
		document.getElementById("materialesCatalogo").className = "col-lg-4 col-12";
		document.getElementById("composicionesCatalogo").className = "col-lg-4 col-12";
		document.getElementById("marcadoresCatalogo").className = "col-lg-4 col-12";
		document.getElementById("composicionCatalogo").className = "col-lg-4 col-12";
		document.getElementById("precioComposicionCatalogo").className = "col-lg-4 col-12";
		$('#idtable3 tbody tr').addClass('text-center');
	}
	window.onload = changeClassById();
}
else
	{
	
	}
