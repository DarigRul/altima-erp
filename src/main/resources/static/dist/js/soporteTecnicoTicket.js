Vue.component('star-rating', VueStarRating.default);

var app = new Vue({
    el: '#app',
    data: {
        ticket: {
            categoria: '',
            titulo: '',
            archivo: null,
            comentarios: '',
            idTicket: null,
        },
        submitTicketDisabled: false,
        submitTicket: true,
        ticketDetalle: {
            idText: '',
            fechaCreacion: '',
            empleado: '',
            area: '',
            categoria: '',
            titulo: ''
        },
        seguimiento: {
            idTicket: '',
            estatus: '',
            comentario: '',
            usuario: '',
            fecha: ''
        },
        submitSeguimiento: false,
        seguimientoTicket: [],
        rating: null,
        info:false
    }
    ,
    mounted() {

    },
    methods: {
        guardarTicket: async function () {
            this.submitTicketDisabled = true;
            console.log(this.ticket)
            const { categoria, titulo, archivo, comentarios } = this.ticket;
            if (categoria === '' ||
                titulo === '' ||
                archivo === null ||
                comentarios === '') {
                Swal.fire({
                    icon: 'error',
                    title: 'Error!',
                    text: 'Todos los campos son requeridos!',
                })
                this.submitTicketDisabled = false;
                return false;
            }
            let formData = new FormData();
            formData.append("archivoTicket", archivo)
            formData.append("comentarios", comentarios)
            formData.append("titulo", titulo)
            formData.append("categoria", categoria)
            try {
                let response = await axios.post(`/api/soporte-tecnico-ticket/?_csrf=${$("[name='_csrf']").val()}`, formData, { headers: { 'Content-Type': 'multipart/form-data' } });
                console.log(response)
                Swal.fire({
                    icon: 'success',
                    title: 'El ticket se creo correctamente!',
                })
            } catch (error) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error!',
                    text: 'Error en el Servidor!',
                })
                console.log(error);
            }
            location.reload();
        },
        modalAgregarTicket: function () {
            this.submitTicket = true;
            this.info = false;
            this.limpiarModal();
            $('#modalAgregarTicket').modal("show");
        },
        limpiarFile: function () {
            $('#archivoTicket').val(null);
            this.ticket.archivo = null;
        },
        modalSeguimiento: async function (id) {
            try {
                this.seguimientoTicket = []
                let response = await axios.get(`/api/soporte-tecnico-ticket/seguimiento/${id}`);
                let responseSeguimiento = await axios.get(`/api/soporte-tecnico-ticket/seguimientoTicket/${id}`);
                responseSeguimiento.data.map(seguimiento => {
                    let temp = {
                        idTicketSeguimiento: seguimiento.idTicketSeguimiento,
                        empleado: seguimiento.empleado,
                        fechaCreacion: seguimiento.fechaCreacion,
                        estado: seguimiento.estado,
                        comentario: seguimiento.comentario
                    }
                    this.seguimientoTicket.push(temp);
                })
                console.log(this.seguimientoTicket)
                let { idText, fechaCreacion, empleado, area, categoria, titulo } = response.data
                this.seguimiento.idTicket = id
                this.ticketDetalle.idText = idText
                this.ticketDetalle.fechaCreacion = fechaCreacion
                this.ticketDetalle.empleado = empleado
                this.ticketDetalle.area = area
                this.ticketDetalle.categoria = categoria
                this.ticketDetalle.titulo = titulo
                console.log(this.ticketDetalle)
                $('#modalSeguimiento').modal("show");
            } catch (error) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error!',
                    text: 'Error en el Servidor!',
                })
                console.log(error);
            }
        },
        guardarSeguimiento: async function () {
            this.submitSeguimiento = true;
            const { comentario, estatus, idTicket } = this.seguimiento;
            if (comentario === '' ||
                estatus === '' ||
                idTicket === '') {
                Swal.fire({
                    icon: 'error',
                    title: 'Error!',
                    text: 'Todos los campos son requeridos!',
                })
                this.submitSeguimiento = true;
                return false;
            }
            try {
                let response = await axios.post(`/api/soporte-tecnico-ticket/seguimiento/?_csrf=${$("[name='_csrf']").val()}`, {
                    comentario: comentario,
                    estado: estatus,
                    idTicket: idTicket
                })
                console.log(response)
                Swal.fire({
                    icon: 'success',
                    title: 'El seguimiento se creo correctamente!',
                })
            } catch (error) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error!',
                    text: 'Error en el Servidor!',
                })
                console.log(error);
            }
            location.reload();
        },
        onFileChange(e) {
            var files = e.target.files;
            if (!files.length)
                return;
            this.ticket.archivo = files[0];
        },
        async editarTicketModal(id) {
            this.info = false;
            this.submitTicket = false;
            this.limpiarModal()
            try {
                this.ticket.idTicket = id;
                let response = await axios.get(`/api/soporte-tecnico-ticket/${id}`);
                let { idCategoria, titulo, nombreArchivo, comentario } = response.data
                this.ticket.categoria = idCategoria
                this.ticket.titulo = titulo
                this.ticket.archivo = nombreArchivo
                this.ticket.comentarios = comentario
                $('#archivoTicket').text(nombreArchivo);
                $('#modalAgregarTicket').modal("show");
            } catch (error) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error!',
                    text: 'Error en el Servidor!',
                })
                console.log(error);
            }
        },
        editarTicket: async function () {
            this.submitTicketDisabled = true;
            console.log(this.ticket)
            const { categoria, titulo, archivo, comentarios, idTicket } = this.ticket;
            if (categoria === '' ||
                titulo === '' ||
                archivo === null ||
                comentarios === '' ||
                idTicket === '') {
                Swal.fire({
                    icon: 'error',
                    title: 'Error!',
                    text: 'Todos los campos son requeridos!',
                })
                this.submitTicketDisabled = false;
                return false;
            }
            let formData = new FormData();
            // formData.append("archivoTicket", archivo)
            formData.append("comentarios", comentarios)
            formData.append("titulo", titulo)
            formData.append("categoria", categoria)
            formData.append("idTicket", idTicket)
            try {
                let response = await axios.put(`/api/soporte-tecnico-ticket/?_csrf=${$("[name='_csrf']").val()}`, formData, { headers: { 'Content-Type': 'multipart/form-data' } });
                console.log(response)
                Swal.fire({
                    icon: 'success',
                    title: 'El ticket se edito correctamente!',
                })
            } catch (error) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error!',
                    text: 'Error en el Servidor!',
                })
                console.log(error);
            }
            location.reload();
        },
        cambiarPrioridad(id) {
            Swal.fire({
                title: `¿Estás seguro que quieres cambiar la prioridad?`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Aceptar',
                cancelButtonText: 'Cancelar'
            }).then(async (result) => {
                if (result.value) {
                    try {
                        let response = await axios.put(`/api/soporte-tecnico-ticket/prioridad/${id}?_csrf=${$("[name='_csrf']").val()}`);
                        Swal.fire({
                            icon: 'success',
                            title: 'El ticket se edito correctamente!',
                        })
                    } catch (error) {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error!',
                            text: 'Error en el Servidor!',
                        })
                        console.log(error);
                    }
                    location.reload();
                }
            })
        },
        limpiarModal() {
            this.ticket.categoria = ''
            this.ticket.titulo = ''
            this.ticket.archivo = null
            this.ticket.comentarios = ''
            $('#archivoTicket').val(null);
        },
        setRating(rating) {
            this.rating = rating
        },
        putCalificacion: function (id) {
            Swal.fire({
                title: `¿Estás seguro de esta calificación?`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Aceptar',
                cancelButtonText: 'Cancelar'
            }).then(async (result) => {
                if (result.value) {
                    try {
                        let response = await axios.put(`/api/soporte-tecnico-ticket/calificacion/${id}?_csrf=${$("[name='_csrf']").val()}&calificacion=${this.rating}`);
                        Swal.fire({
                            icon: 'success',
                            title: 'La calificación se edito correctamente!',
                        })
                    } catch (error) {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error!',
                            text: 'Error en el Servidor!',
                        })
                        console.log(error);
                    }
                }
            })
        },
        async infoTicketModal(id) {
            this.info = true;
            this.limpiarModal()
            try {
                this.ticket.idTicket = id;
                let response = await axios.get(`/api/soporte-tecnico-ticket/${id}`);
                let { idCategoria, titulo, nombreArchivo, comentario } = response.data
                this.ticket.categoria = idCategoria
                this.ticket.titulo = titulo
                this.ticket.archivo = nombreArchivo
                this.ticket.comentarios = comentario
                $('#archivoTicket').text(nombreArchivo);
                $('#modalAgregarTicket').modal("show");
            } catch (error) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error!',
                    text: 'Error en el Servidor!',
                })
                console.log(error);
            }
        },
    },
    computed: {
        isSubmitTicketDisabled: function () {
            console.log(this.submitTicketDisabled)
            return this.submitTicketDisabled;
        },
    },
})