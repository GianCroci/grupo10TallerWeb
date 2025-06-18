const socket = new SockJS('/spring/wschat');
const stompClient = StompJs.Stomp.over(socket);

stompClient.onConnect = (frame) => {
    console.log("Conectado como " + usuario);

    stompClient.subscribe("/user/" + usuario + "/queue/messages", (m) => {
        console.log("Mensaje recibido del servidor: ", m.body);

        const data = JSON.parse(m.body);
        const messagesContainer = document.getElementById("chat-messages");
        const newMessage = document.createElement("p");
        newMessage.textContent = `${data.remitente}: ${data.mensaje}`;
        messagesContainer.appendChild(newMessage);
    });
};
/*stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/messages', (m) => {
        console.log(JSON.parse(m.body).content);
        const messagesContainer = document.getElementById("chat-messages");
        const newMessage = document.createElement("p")
        newMessage.textContent = JSON.parse(m.body).content;
        messagesContainer.appendChild(newMessage);
    });
};*/

stompClient.activate();

function sendMessage() {
    const message = document.getElementById("message").value;

    // Mostrarlo en pantalla como "yo"
    const messagesContainer = document.getElementById("chat-messages");
    const newMessage = document.createElement("p");
    newMessage.textContent = `Yo: ${message}`;
    messagesContainer.appendChild(newMessage);

    stompClient.publish({
        destination: "/app/chat",
        body: JSON.stringify({
            destinatario: destinatario,
            remitente: usuario,
            mensaje: message
        })
    });

    document.getElementById("message").value = "";
}
