$(document).ready(async function () {
    let json = [];
    const response = await getTallasByEmpleado();
    const {tallas}=response;
    const familiaPrenda = [...new Set(tallas.map(talla => talla.familiaPrenda))];
    let prendasTh;
    familiaPrenda.map(fp => {
        console.log(fp)
        prendasTh=prendasTh+`<th>${fp}</th>`
        
    })
    $(`#thFamP`).after(prendasTh);
    const idsEmpleado = [...new Set(tallas.map(talla => talla.idEmpleado))];
    idsEmpleado.map(idEmpleado => {
        let temp;
        tallas.map(talla => {
            if (idEmpleado === talla.idEmpleado) {
                temp = {
                    ...temp,
                    nombreEmpleado: talla.nombreEmpleado,
                    idEmpleado: talla.idEmpleado
                }
            }
            familiaPrenda.map(fp => {
                if (talla.idEmpleado === idEmpleado && fp === talla.familiaPrenda) {
                    temp = {
                        ...temp,
                        [fp]: talla.talla
                    }
                }
            })
        })
        json.push(temp);
    })
    let pivotTable = $('#tablaConcentradoTallas').DataTable({
        "ordering": false,
        "pageLength": 5,
        "scrollX": true,
        "stateSave": true,
        "language": {
            "sProcessing": "Procesando...",
            "sLengthMenu": "Mostrar _MENU_ registros",
            "sZeroRecords": "No se encontraron resultados",
            "sEmptyTable": "Ningún dato disponible en esta tabla =(",
            "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
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
                "colvis": "Visibilidad",
                "print": "Imprimir",
            }
        },
        "lengthMenu": [
            [5, 10, 25, 50, 100],
            [5, 10, 25, 50, 100]
        ],
    })
    console.log(json)
    json.map(talla => {
        let rowHtml = `<tr><td>${talla.nombreEmpleado}</td>`
        familiaPrenda.map(fp => {
            console.log(fp)
            rowHtml = rowHtml + `<td>${talla[fp] === undefined ? 'Sin Asignar' : talla[fp]}</td>`

        })
        rowHtml = rowHtml + `<td><a class="btn btn-warning btn-circle btn-sm popoverxd" target="_blank"
                                         href="/editar-concentrado-de-tallas/${response.idPedido}/${talla.idEmpleado}"
                                         data-container="body" 
                                         data-toggle="popover" 
                                         data-content="Editar"><i class="fas fa-pen"></i></a></td></tr>`
        pivotTable.row.add($(rowHtml)).draw()
    })

});

async function getTallasByEmpleado() {
    let result;
    try {
        result = await $.ajax({
            type: "GET",
            url: `/api${window.location.pathname}`,
        });
        return result;
    } catch (error) {
        console.error(error);
    }
}