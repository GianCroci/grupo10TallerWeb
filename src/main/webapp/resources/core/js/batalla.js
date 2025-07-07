let stompClient;
let salaId;
let nombreJugador;

function conectarBatalla(idSala, nombre) {
    salaId = idSala;
    nombreJugador = nombre;

    const socket = new SockJS("/wsbatalla");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        stompClient.subscribe("/sala/batalla/" + salaId, function (mensaje) {
            const estado = JSON.parse(mensaje.body);

            document.getElementById("estado").innerText = estado.mensaje;
            document.getElementById("hpJugador").innerText = estado.hpJugador;
            document.getElementById("hpRival").innerText = estado.hpRival;

            document.getElementById("btnAtacar").disabled = estado.turno !== nombreJugador;
        });

        document.getElementById("estado").innerText = "Conectado a la sala.";
    });
}

function atacar() {
    const ataque = {
        remitente: nombreJugador
    };

    stompClient.send("/app/batalla/" + salaId, {}, JSON.stringify(ataque));
}
