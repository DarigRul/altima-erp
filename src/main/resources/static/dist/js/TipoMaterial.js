function showMaterial(id) {
	if (id == "selecciona") {
		$("#telaForm").hide();
        $("#forroForm").hide();
        $("#materialForm").hide();
       }
	if (id == "tela") {
		$("#telaForm").show();
        $("#forroForm").hide();
        $("#materialForm").hide();
       }
	if (id == "forro") {
		$("#telaForm").hide();
        $("#forroForm").show();
        $("#materialForm").hide();
       }
	if (id == "material") {
		$("#telaForm").hide();
        $("#forroForm").hide();
        $("#materialForm").show();
       }
}