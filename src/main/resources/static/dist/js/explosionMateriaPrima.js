new Vue({
    el: '#app',
    data: {
        apartadoTelas:null
    },
    mounted () {
        let urlParams = new URLSearchParams(window.location.search);
        let idPedido = urlParams.get('idPedido');
        axios
        .get('/getApartadoTelasById', {
            params: {
                idPedido: idPedido
            }
        })
        .then(response => {
            console.log(response.data);
            // response.data.find(element => element[])
        })
        .catch(error => {
            console.log(error)
        })
        // .finally(() => $('#selectPedido').selectpicker('refresh')
        // )
    },
  })