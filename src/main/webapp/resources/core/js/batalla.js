let stompClient;
let salaId;
let nombreJugador;

console.log("Cargando batalla.js");

function conectarBatalla(idSala, nombre) {
    salaId = idSala;
    nombreJugador = nombre;

    const socket = new SockJS("/spring/wsbatalla");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        console.log("Conectado a la sala de batalla: " + salaId + " como " + nombreJugador);

        stompClient.subscribe("/sala/batalla/" + salaId, function (mensaje) {
            const estado = JSON.parse(mensaje.body);

            console.log("Estado recibido:", estado);
            console.log("Soy:", nombreJugador);
            console.log("Jugador A:", estado.nombreJugadorA);
            console.log("Jugador B:", estado.nombreJugadorB);

            document.getElementById("estado").innerText = estado.mensaje;


            const jugadorA = nombreJugador === estado.nombreJugadorA;

            if (jugadorA) {

                document.getElementById("hpJugador").innerText = estado.hpJugador;
                document.getElementById("hpRival").innerText = estado.hpRival;
                console.log("Jugador A - Mi HP:", estado.hpJugador, "HP Rival:", estado.hpRival);
                if (estado.hpJugador <= 0){
                    document.getElementById("estado").innerText = estado.mensaje + "Perdiste";
                    document.getElementById("btnSalir").style.display = "block";
                } if(estado.hpRival <= 0) {
                    document.getElementById("estado").innerText = estado.mensaje + "Ganaste";
                    document.getElementById("btnSalir").style.display = "block";
                    stompClient.send("/app/batalla/ganador", {}, JSON.stringify({
                        idGanador: idPersonaje
                    }));
                }
            } else {

                document.getElementById("hpJugador").innerText = estado.hpRival;
                document.getElementById("hpRival").innerText = estado.hpJugador;
                if (estado.hpRival <= 0){
                    document.getElementById("estado").innerText = estado.mensaje + "Perdiste";
                    document.getElementById("btnSalir").style.display = "block";
                    document.getElementById("btnAtacar").style.display = "none !important";
                } if(estado.hpJugador <= 0) {
                    document.getElementById("estado").innerText = estado.mensaje + "Ganaste";
                    document.getElementById("btnSalir").style.display = "block";
                    document.getElementById("btnAtacar").style.display = "none";
                    stompClient.send("/app/batalla/ganador", {}, JSON.stringify({
                        idGanador: idPersonaje
                    }));
                }
                console.log("Jugador B - Mi HP:", estado.hpRival, "HP Rival:", estado.hpJugador);
            }


            document.getElementById("btnAtacar").disabled = estado.turno !== nombreJugador;
        });

        console.log("Enviando mensaje para iniciar batalla a sala: " + salaId);
        stompClient.send("/app/batalla/iniciar/" + salaId, {}, {});
    });
}

function atacar() {
    const ataque = {
        remitente: nombreJugador
    };

    console.log("Atacó " + ataque.remitente);
    stompClient.send("/app/batalla/" + salaId, {}, JSON.stringify(ataque));
}

function salir() {
    window.location.href = "/spring/home";
}