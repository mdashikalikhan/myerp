const chatBody = document.getElementById("chatBody");
const messageInput = document.getElementById("messageInput");
const sendMessageBtn = document.getElementById("sendMessageBtn");
const typingIndicator = document.getElementById("typingIndicator");
const typingUser = document.getElementById("typingUser");
let typingTimeout;

let stompClient = null;

// Connect to WebSocket server using SockJS and STOMP
function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/public', function (message) {
            const data = JSON.parse(message.body);
            if (data.type === "CHAT") {
                addMessageToChat(data.content, data.sender, false);
            } else if (data.type === "TYPING") {
                showTypingIndicator(data.sender);
            }
        });
    });
}

// Send message function
function sendMessage() {
    const messageContent = messageInput.value.trim();
    if (messageContent) {
        const chatMessage = {
            sender: "YourUsername", // Replace with dynamic username
            content: messageContent,
            type: "CHAT"
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        addMessageToChat(messageContent, "You", true);
        messageInput.value = "";
    }
}

// Show typing indicator
function showTypingIndicator(user) {
    typingUser.innerText = user;
    typingIndicator.style.display = "block";
    clearTimeout(typingTimeout);
    typingTimeout = setTimeout(() => {
        typingIndicator.style.display = "none";
    }, 2000);
}

// Emit typing event
function emitTyping() {
    const chatMessage = {
        sender: "YourUsername", // Replace with dynamic username
        type: "TYPING"
    };
    stompClient.send("/app/chat.addUser", {}, JSON.stringify(chatMessage));
}

// Add message to chat
function addMessageToChat(message, sender, isSelf) {
    const messageDiv = document.createElement("div");
    messageDiv.className = `chat-message ${isSelf ? "self" : ""}`;
    messageDiv.innerHTML = `
        <div class="message">
            <strong>${sender}:</strong> ${message}
        </div>
    `;
    chatBody.appendChild(messageDiv);
    chatBody.scrollTop = chatBody.scrollHeight;
}

// Emoji picker
document.getElementById("emojiPickerBtn").addEventListener("click", () => {
    messageInput.value += "😊"; // Simple emoji picker demo
});

// File upload handling
document.getElementById("fileUploadBtn").addEventListener("click", () => {
    document.getElementById("fileInput").click();
});

document.getElementById("fileInput").addEventListener("change", (event) => {
    const file = event.target.files[0];
    if (file) {
        alert(`File uploaded: ${file.name}`);
    }
});

// Logout function
function logout() {
    alert("You have logged out.");
    window.location.href = "/login";  // Redirect to login page
}

// Event Listeners
document.addEventListener("DOMContentLoaded", () => {
    connect();

    messageInput.addEventListener("keypress", emitTyping);
    sendMessageBtn.addEventListener("click", sendMessage);
});
