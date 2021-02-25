function EditarEncogimiento(id) {
    $("#modalCalidadTela").modal("show");
}

function subirPDF(idTela, ruta, descripcion) {
    $(`#modalPDF`).modal("show");
    $('#idTela').val(idTela);
    if (ruta != 'null') {
        $('.custom-file-label').html(ruta);
        $('#descripcion').val(descripcion);
        $(`#abrirArchivo`).attr('href', `https://res.cloudinary.com/dti-consultores/image/upload/v1613677976/pdf/${ruta}`)

    }
    else {
        $('.custom-file-label').html("No se eligió ningún archivo");
        $('#descripcion').val('');
        $(`#abrirArchivo`).attr('href', ``)
    }
}
$('input[type="file"]').change(function (e) {
    var fileName = e.target.files[0].name;
    $('.custom-file-label').html(fileName);
});

$("#enviarEncogimiento").click(function (e) {
    e.preventDefault();
    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);
    $(this).attr("disabled", true);
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/guardarPdfEncogimiento",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            Swal.fire({
                icon: 'success',
                title: 'El archivo se agregó correctamente',
                showConfirmButton: false,
                timer: 2000
            })

            console.log("SUCCESS : ", data);
            $('#enviarEncogimiento').attr("disabled", false);
            window.open(`https://res.cloudinary.com/dti-consultores/image/upload/v1613677976/pdf/${data.rutaPdfEncogimiento}`, '_blank')
            location.reload();
        },
        error: function (e) {
            $('#enviarEncogimiento').attr("disabled", false);
            console.log("ERROR : ", e);

        }
    });
});