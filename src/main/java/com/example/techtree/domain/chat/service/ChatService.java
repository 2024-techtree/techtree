package com.example.techtree.domain.chat.service;

import com.example.techtree.domain.chat.dao.ChatRepository;
import com.example.techtree.domain.chat.entity.ChatRoom;
import com.example.techtree.domain.chat.entity.ChatRoomDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<String, ChatRoomDto> chatRooms;
    private final ChatRepository chatRepository;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoomDto> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoomDto findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoomDto createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        ChatRoomDto chatRoom = ChatRoomDto.builder()
                .roomId(randomId)
                .name(name)
                .build();
        ChatRoom saveRoom = chatRoom.toEntity(randomId, name);
        this.chatRepository.save(saveRoom);
        chatRooms.put(randomId, chatRoom);
        return chatRoom;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}