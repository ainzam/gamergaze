$(document).ready(function() {
    var stompClient = null;
    connect();

    function connect() {
        var socket = new SockJS('/websocket-endpoint'); // Reemplaza '/websocket-endpoint' con tu endpoint
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/messages/' + recipientId, function(message) {
                showMessage(JSON.parse(message.body));
            });
        });
    }

    function showMessage(message) {
        // Agrega lógica para mostrar el mensaje en el DOM
        $('#chat-messages').append('<div><p>' + message.content + '</p></div>');
    }

    $('#message-form').on('submit', function(e) {
        e.preventDefault();
        var content = $('#message-input').val().trim();
        if (content) {
            var message = {
                senderId: currentUserId, // Reemplaza currentUserId con el ID del usuario actual
                recipientId: recipientId, // RecipientId debe definirse en tu HTML
                content: content
            };
            stompClient.send('/app/chat/' + recipientId, {}, JSON.stringify(message));
            $('#message-input').val('');
        }
    });
});
