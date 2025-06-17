const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/spring/wschat'
});

stompClient.onConnect = (frame) => {
    console.log("Conectado como " + usuario);

    stompClient.subscribe("/user/queue/messages", (m) => {
        const data = JSON.parse(m.body);
        const messagesContainer = document.getElementById("chat-messages");
        const newMessage = document.createElement("p");
        newMessage.textContent = data.remitente + ": " + data.mensaje;
        messagesContainer.appendChild(newMessage);
    });
};

stompClient.activate();

function sendMessage() {
    const message = document.getElementById("message").value;

    stompClient.publish({
        destination: "/app/chat",
        body: JSON.stringify({
            destinatario: destinatario,
            mensaje: message
        })
    });

    document.getElementById("message").value = "";
}
