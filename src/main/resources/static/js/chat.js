var stompClient = null;

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        var currentUserId = document.getElementById("currentUserId").value;
        stompClient.subscribe('/topic/messages/' + currentUserId, function (message) {
            showMessage(JSON.parse(message.body));
        });
    });
}

function sendMessage() {
    var currentUserId = document.getElementById("currentUserId").value;
    var recipientId = document.getElementById("recipientId").value;
    var messageContent = document.getElementById("message").value;
    var message = {
        senderId: currentUserId,
        recipientId: recipientId,
        content: messageContent,
        timestamp: new Date().toISOString()
    };
    stompClient.send("/app/chat", {}, JSON.stringify(message));
    showMessage(message);
    document.getElementById("message").value = "";
}

function showMessage(message) {
    var messageArea = document.getElementById("messageArea");
    var messageElement = document.createElement("li");

    var messageClass = message.senderId == document.getElementById("currentUserId").value ? "you" : "recipient";
    messageElement.className = "chat-message " + messageClass;

    var avatarElement = document.createElement("i");
    avatarElement.appendChild(document.createTextNode(message.senderId == document.getElementById("currentUserId").value ? 'You' : 'Recipient'));
    messageElement.appendChild(avatarElement);

    var textElement = document.createElement("p");
    textElement.appendChild(document.createTextNode(message.content));
    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
}



document.addEventListener("DOMContentLoaded", function () {
    connect();
    document.getElementById("sendButton").addEventListener("click", sendMessage);
});