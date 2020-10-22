new Vue({
    el: '#app',
    data() {
        return {
            selectAgente: document.getElementById("selectAgenteVal").value,
            selectPedido: null,
            nuevaFechaEntrega: null,
            motivo: null,
            agentes: null,
            pedidos: null,
            pedido: "",
            csrf: document.getElementById("token").value,
            buttonSave: false,
            buttonEdit: false,
        }
    },
    methods: {
        getAgentes: function () {
            $('#selectPedido option').remove();
            $('#selectPedido').selectpicker('refresh')
            Object.assign(this.$data, this.$options.data());
            this.buttonSave=true;
            axios
                .get('/getAgenteVentas')
                .then(response => {
                    this.agentes = response.data;
                })
                .catch(error => {
                    console.log(error)
                    this.errored = true
                })
                .finally(() => $('#selectAgente').selectpicker('refresh')
                )
        },
        getPedidos: function () {
            this.buttonSave=true;
            axios
                .get('/getPedidoByEmpleado', {
                    params: {
                        idEmpleado: this.selectAgente
                    }
                })
                .then(response => {
                    this.pedidos = response.data
                    console.log(response.data);
                })
                .catch(error => {
                    console.log(error)
                    this.errored = true
                })
                .finally(() => $('#selectPedido').selectpicker('refresh')
                )
        },
        getSolicitud: function (idSolicitudCambioFecha, idText, cliente) {
            $('#selectPedido option').remove();
            $('#selectPedido').selectpicker('refresh')
            $('#selectAgente option').remove();
            $('#selectAgente').selectpicker('refresh')
            Object.assign(this.$data, this.$options.data());
            axios
                .get('/getSolicitud', {
                    params: {
                        idSolicitudCambioFecha: idSolicitudCambioFecha
                    }
                })
                .then(response => {
                    this.pedidos = response.data
                    this.pedidos = [{ idText: idText, idPedidoInformacion: response.data.idPedidoInformacion,idSolicitudCambioFecha:response.data.idSolicitudCambioFecha }]
                    this.selectPedido = response.data.idPedidoInformacion;
                    this.pedido = { cliente: cliente, fechaEntrega: response.data.fechaAnterior }
                    this.motivo = response.data.motivo
                    this.nuevaFechaEntrega = response.data.fechaNueva
                    this.idSolicitudCambioFecha=response.data.idSolicitudCambioFecha;
                    if (response.data.estatus == 0) {
                        this.buttonEdit = true;
                        this.buttonSave = false;

                    } else {
                        this.buttonEdit = false;
                        this.buttonSave = false;
                    }

                })
                .catch(error => {
                    console.log(error)
                    this.errored = true
                })
                .finally(function () {

                    $('#selectPedido').selectpicker('refresh');
                })
        },
        getPedidoSelect: function () {
            var pedido = this.pedidos.find(x => x.idPedidoInformacion === this.selectPedido);
            this.pedido = pedido;
            console.log(this.pedido);
        },
        postSolicitud: function () {
            if (!this.selectPedido || !this.nuevaFechaEntrega || !this.pedido.fechaEntrega || !this.motivo) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Todos los campos deben de estar llenos!',
                })
                return false;
            }
            var solicitud = [];
            var temp = {
                'selectPedido': this.selectPedido,
                'nuevaFechaEntrega': this.nuevaFechaEntrega,
                'fechaEntrega': this.pedido.fechaEntrega,
                'motivo': this.motivo
            };
            solicitud.push(temp);
            const params = new URLSearchParams();
            params.append('_csrf', this.csrf);
            params.append('solicitud', JSON.stringify(solicitud));
            axios.post('/postSolicitudCambioFecha', params)
                .then(function (response) {
                    console.log(response);
                    Swal.fire({
                        icon: 'success',
                        title: 'La solicitud se guardo exitosamente',
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        location.reload();
                    })
                })
                .catch(function (error) {
                    console.log(error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Error: ' + error.data,
                        timer: 1500
                    }).then((result) => {
                        location.reload();
                    })
                });
        },
        aceptarSolicitud: function (idSolicitudCambioFecha) {
            Swal.fire({
                title: 'Estás seguro que quieres aceptar la solicitud',
                text: "No podras revertir esta acción!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Aceptar',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.value) {
                    const params = new URLSearchParams();
                    params.append('_csrf', this.csrf);
                    params.append('idSolicitudCambioFecha', idSolicitudCambioFecha);

                    axios.patch('/patchSolicitudCambioFechaAceptar', params)
                        .then(function (response) {
                            console.log(response);
                            Swal.fire({
                                icon: 'success',
                                title: 'La solicitud se acepto exitosamente',
                                showConfirmButton: false,
                                timer: 1500
                            }).then(() => {
                                location.reload();
                            })
                        })
                        .catch(function (error) {
                            console.log(error);
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: 'Error: ' + error.data,
                                timer: 1500
                            }).then((result) => {
                                location.reload();
                            })
                        });
                }
            })

        },
        rechazarSolicitud: function (idSolicitudCambioFecha) {
            Swal.fire({
                title: 'Estás seguro que quieres rechazar la solicitud',
                text: "No podras revertir esta acción!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Aceptar',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.value) {
                    const params = new URLSearchParams();
                    params.append('_csrf', this.csrf);
                    params.append('idSolicitudCambioFecha', idSolicitudCambioFecha);
                    axios.patch('/patchSolicitudCambioFechaRechazar', params)
                        .then(function (response) {
                            console.log(response);
                            Swal.fire({
                                icon: 'success',
                                title: 'La solicitud se rechazo exitosamente',
                                showConfirmButton: false,
                                timer: 1500
                            }).then(() => {
                                location.reload();
                            })
                        })
                        .catch(function (error) {
                            console.log(error);
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: 'Error: ' + error.data,
                                timer: 1500
                            }).then((result) => {
                                location.reload();
                            })
                        });
                }
            })
        },
        patchSolicitud:function () {
            if (!this.selectPedido || !this.nuevaFechaEntrega || !this.pedido.fechaEntrega || !this.motivo) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Todos los campos deben de estar llenos!',
                })
                return false;
            }
            const params = new URLSearchParams();
            params.append('_csrf', this.csrf);
            params.append('idSolicitudCambioFecha', this.pedidos[0].idSolicitudCambioFecha);
            params.append('fecha', this.nuevaFechaEntrega);
            axios.patch('/patchSolicitudCambioFecha', params)
                .then(function (response) {
                    console.log(response);
                    Swal.fire({
                        icon: 'success',
                        title: 'La solicitud se edito exitosamente',
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        location.reload();
                    })
                })
                .catch(function (error) {
                    console.log(error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Error: ' + error.data,
                        timer: 1500
                    }).then((result) => {
                        location.reload();
                    })
                });
        }
    },
})
