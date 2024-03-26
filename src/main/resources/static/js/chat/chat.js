// chat.js

// 이전 대화 내용을 저장할 localStorage 키 값
const CHAT_HISTORY_KEY = 'chat_history';

document.addEventListener("DOMContentLoaded", function () {
    const chatMessagesEl = document.getElementById('chatMessages');

    // 폼 제출 시 메시지를 전송합니다.
    function submitWriteForm(form) {
        form.content.value = form.content.value.trim();

        if (form.content.value.length === 0) {
            alert('내용을 입력해주세요.');
            form.content.focus();
            return;
        }

        const action = `/chat/room/${roomId}/write`;

        fetch(action, {
            method: 'POST',
            headers: {
                'accept': 'application/json',
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': token,
            },
            body: JSON.stringify({
                writerName: username,
                content: form.content.value,
            }),
        }).catch(error => alert(error));

        form.content.value = '';
        form.content.focus();
    }

    // 받은 메시지를 화면에 출력합니다.
    function drawMoreChatMessage(message) {
        const messageContainer = document.createElement('li');
        messageContainer.textContent = `${message.name}: ${message.content}`;

        if (message.name === username) {
            messageContainer.classList.add('sent'); // 본인 메시지인 경우
        } else {
            messageContainer.classList.add('received'); // 상대방 메시지인 경우
        }

        chatMessagesEl.insertBefore(messageContainer, chatMessagesEl.firstChild); // 새 메시지를 상단에 추가합니다.
    }

    // 이전 대화 내용을 불러와 화면에 출력합니다.
    function loadChatHistory() {
        const chatHistory = JSON.parse(localStorage.getItem(CHAT_HISTORY_KEY)) || [];   // localstorage 사용 X
        chatHistory.forEach(message => {
            drawMoreChatMessage(message);
        });
    }

    // 웹 소켓 연결을 시도합니다.
    const socket = new SockJS('/ws');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe(`/topic/chat/room/${roomId}/messageCreated`, function (data) {
            const jsonData = JSON.parse(data.body);
            const newMessage = jsonData.data.message;
            drawMoreChatMessage(newMessage);

            // 새로운 메시지를 로컬 저장소에 추가합니다.
            /*const chatHistory = JSON.parse(localStorage.getItem(CHAT_HISTORY_KEY)) || [];
            chatHistory.push(newMessage);
            localStorage.setItem(CHAT_HISTORY_KEY, JSON.stringify(chatHistory));*/
        });

        // 이전 대화 내용을 불러옵니다.
        // loadChatHistory();
    });

    // 메시지 전송 폼을 가져와 이벤트를 추가합니다.
    const messageForm = document.querySelector('form');
    messageForm.addEventListener('submit', function (event) {
        event.preventDefault();
        submitWriteForm(this);
    });
});