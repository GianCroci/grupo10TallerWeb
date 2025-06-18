const socket = new SockJS('/spring/wschat');
const stompClient = StompJs.Stomp.over(socket);

stompClient.onConnect = (frame) => {
    console.log("Conectado como " + usuario);

    stompClient.subscribe("/user/queue/messages", (m) => {
        const data = JSON.parse(m.body);
        const messagesContainer = document.getElementById("chat-messages");
        const newMessage = document.createElement("p");
        newMessage.textContent = JSON.parse(m.body).content;
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

    stompClient.publish({
        destination: "/app/chat",
        body: JSON.stringify({
            destinatario: destinatario,
            mensaje: message
        })
    });

    document.getElementById("message").value = "";
}
