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

        stompClient.subscribe("/sala/batalla/" + salaId, function (mensaje)  {

            const estado = JSON.parse(mensaje.body);

                console.log("Objeto estado parseado:", estado);
                console.log("estado.turno:", estado.turno);
                console.log("nombreJugador:", nombreJugador);
                console.log("Comparación:", estado.turno === nombreJugador);

            document.getElementById("estado").innerText = estado.mensaje;
            document.getElementById("hpJugador").innerText = estado.hpJugador;
            document.getElementById("hpRival").innerText = estado.hpRival;
            document.getElementById("btnAtacar").disabled = estado.turno !== nombreJugador;


            document.getElementById("estado").innerText = "Conectado a la sala.";
        });


        console.log(" Enviando mensaje para iniciar batalla a sala: " + salaId);

            stompClient.send("/app/batalla/iniciar/" + salaId, {}, {});
    });

}

function atacar() {
    const ataque = {
        remitente: nombreJugador
    };

    stompClient.send("/app/batalla/" + salaId, {}, JSON.stringify(ataque));

}
