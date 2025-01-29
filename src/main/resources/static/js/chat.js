const chatBody = document.getElementById("chatBody");
const messageInput = document.getElementById("messageInput");
const sendMessageBtn = document.getElementById("sendMessageBtn");
const typingIndicator = document.getElementById("typingIndicator");
const typingUser = document.getElementById("typingUser");
const emojiPickerBtn = document.getElementById("emojiPickerBtn");
const fileUploadBtn = document.getElementById("fileUploadBtn");
const fileInput = document.getElementById("fileInput");

let stompClient = null;
let typingTimeout;
const username = "Admin"; // Replace with dynamic session username if available

// Connect to WebSocket server using SockJS and STOMP
function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        console.log("Connected to WebSocket");
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

// Graceful disconnect
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
        console.log("Disconnected from WebSocket");
    }
}

// Send chat message
function sendMessage() {
    const messageContent = messageInput.value.trim();
    if (messageContent) {
        const chatMessage = {
            sender: username,
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
    if (user !== username) {
        typingUser.innerText = user;
        typingIndicator.style.display = "block";
        clearTimeout(typingTimeout);
        typingTimeout = setTimeout(() => {
            typingIndicator.style.display = "none";
        }, 2000);
    }
}

// Emit typing event (debounced)
function emitTyping() {
    clearTimeout(typingTimeout);
    const chatMessage = {
        sender: username,
        type: "TYPING"
    };
    stompClient.send("/app/chat.typing", {}, JSON.stringify(chatMessage));
}

// Add message to chat
function addMessageToChat(message, sender, isSelf) {
    const messageDiv = document.createElement("div");
    messageDiv.className = `chat-message ${isSelf ? "self" : ""}`;
    messageDiv.innerHTML = `<div class="message"><strong>${sender}:</strong> ${message}</div>`;
    chatBody.appendChild(messageDiv);
    chatBody.scrollTop = chatBody.scrollHeight;
}

// Emoji picker (basic)
emojiPickerBtn.addEventListener("click", () => {
    messageInput.value += "😊"; // Simple emoji picker demo
});

// File upload handling
fileUploadBtn.addEventListener("click", () => {
    fileInput.click();
});

fileInput.addEventListener("change", (event) => {
    const file = event.target.files[0];
    if (file) {
        const chatMessage = {
            sender: username,
            content: `📎 File uploaded: ${file.name}`,
            type: "CHAT"
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        addMessageToChat(`📎 File uploaded: ${file.name}`, "You", true);
    }
});

// Logout function
function logout() {
    disconnect();
    alert("You have logged out.");
    window.location.href = "/login";  // Redirect to login page
}

// Event Listeners
document.addEventListener("DOMContentLoaded", () => {
    connect();
    messageInput.addEventListener("keypress", emitTyping);
    sendMessageBtn.addEventListener("click", sendMessage);
});
