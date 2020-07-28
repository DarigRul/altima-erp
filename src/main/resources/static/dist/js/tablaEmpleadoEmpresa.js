(function () {
    var _div = document.createElement('div');
    jQuery.fn.dataTable.ext.type.search.html = function (data) {
        _div.innerHTML = data;
        return _div.textContent ?
            _div.textContent
                .replace(/[áÁàÀâÂäÄãÃåÅæÆ]/g, 'a')
                .replace(/[çÇ]/g, 'c')
                .replace(/[éÉèÈêÊëË]/g, 'e')
                .replace(/[íÍìÌîÎïÏîĩĨĬĭ]/g, 'i')
                .replace(/[ñÑ]/g, 'n')
                .replace(/[óÓòÒôÔöÖœŒ]/g, 'o')
                .replace(/[ß]/g, 's')
                .replace(/[úÚùÙûÛüÜ]/g, 'u')
                .replace(/[ýÝŷŶŸÿ]/g, 'n') :
            _div.innerText
                .replace(/[áÁàÀâÂäÄãÃåÅæÆ]/g, 'a')
                .replace(/[çÇ]/g, 'c')
                .replace(/[éÉèÈêÊëË]/g, 'e')
                .replace(/[íÍìÌîÎïÏîĩĨĬĭ]/g, 'i')
                .replace(/[ñÑ]/g, 'n')
                .replace(/[óÓòÒôÔöÖœŒ]/g, 'o')
                .replace(/[ß]/g, 's')
                .replace(/[úÚùÙûÛüÜ]/g, 'u')
                .replace(/[ýÝŷŶŸÿ]/g, 'n');
    };
});
$(document).ready(function () {
    var table = $('#tablaEE')
        .DataTable({
            "ordering": false,  
            "bPaginate": false,
            "responsive": true,
            "stateSave": true,
            "drawCallback": function () {
                $('.popoverxd').popover({
                    container: 'body',
                    trigger: 'hover'
                });
            },
            "columnDefs": [{
                "type": "html",
                "targets": '_all'
            }],
            "language": {
                "sProcessing": "Procesando...",
                "sLengthMenu": "Mostrar _MENU_ registros",
                "sZeroRecords": "No se encontraron resultados",
                "sEmptyTable": "Ningún dato disponible en esta tabla =(",
                "sInfo": "_TOTAL_ registros",
                "sInfoEmpty": "0 registros",
                "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                "sInfoPostFix": "",
                "sSearch": "Buscar:",
                "sUrl": "",
                "sInfoThousands": ",",
                "sLoadingRecords": "Cargando...",
                "oPaginate": {
                    "sFirst": "Primero",
                    "sLast": "Último",
                    "sNext": "Siguiente",
                    "sPrevious": "Anterior"
                },
                "oAria": {
                    "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                    "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                },
                "buttons": {
                    "copy": "Copiar",
                    "colvis": "Visibilidad"
                }
            }
        });
    new $.fn.dataTable.FixedHeader(table);

});
// Remove accented character from search input as well
$('#example_filter input[type=search]').keyup(function () {
    var table = $('#tablaEE').DataTable();
    table.search(
        jQuery.fn.DataTable.ext.type.search.html(this.value)
    ).draw();
});

$(document).ready(function () {

    $('#checkBoxAll').click(function () {

        $('input:checkbox').prop('checked', this.checked);
    });
});

function elimiarEmpleado() {

    document.getElementById("formEmpleadoEliminar").submit();
}

function actualizarempleadonombre() {
    alert("ActualizarCambios");
    document.getElementById("formEmpleadoActualizar").submit();
}

function editarmodal() {

    var id = document.getElementById("idEmpleado").value;
    alert("obtner" + id);
}

function editarempleado(id) {
    var nombre = document.getElementById("miboton" + id).value;
    document.getElementById("myText").value = nombre;
    document.getElementById("idcambiarnombre").value = id;
}


function cambiarnombredgp() {
    document.getElementById("formcambiardgp").submit();
}



function seleccionados() {

    var ids = '';
    $('table [type="checkbox"]').each(function (i, chk) {
        if (chk.checked) {
            //console.log("Checked!", i, chk);
            ids = ids + chk.value + ',';
        }

    });
    document.getElementById("get_id_obterneridsempleado").value = ids;
    console.log(ids);

}

function getsucursalyrazonsocial() {
    document.getElementById("formsucursalrazonsocial").submit();
}


$('#coorSelectForm').on('change', function () {

    var selectValor = $(this).val();
    //alert (selectValor);
    if (selectValor == 'listaSelect') {
        $('#listaEmpleadosForm').show();
        $('#SPF').hide();
        $('#varegistroempleado').hide();
    } else if (selectValor == 'stockSelect') {
        $('#listaEmpleadosForm').hide();
        $('#SPF').show();
        $('#varegistroempleado').hide();
    } else if (selectValor == 'registroSelect') {
        $('#listaEmpleadosForm').hide();
        $('#SPF').hide();
        $('#varegistroempleado').show();

    }
    
});
