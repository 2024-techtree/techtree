package com.example.techtree.domain.chat;

import com.example.techtree.domain.chat.entity.ChatMessage;
import com.example.techtree.domain.chat.entity.ChatRoom;
import com.example.techtree.domain.chat.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        /*TextMessage textMessage = new TextMessage("welcome chat server");
        session.sendMessage(textMessage);*/
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        Set<WebSocketSession> sessions = room.getSessions();
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getMessage() + "님이 입장했습니다.");
            sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(chatMessage)));
        } else if (chatMessage.getType().equals(ChatMessage.MessageType.QUIT)) {
            sessions.remove(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장했습니다..");
            sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(chatMessage)));
        } else {
            sendToEachSocket(sessions, message);
        }
    }
    private void sendToEachSocket(Set<WebSocketSession> sessions, TextMessage message) {
        sessions.parallelStream().forEach( roomSession -> {
            try{
                roomSession.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    }
}
