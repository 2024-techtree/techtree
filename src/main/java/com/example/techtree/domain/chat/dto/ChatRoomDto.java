package com.example.techtree.domain.chat.entity;

import com.example.techtree.domain.chat.service.ChatService;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@Data
public class ChatRoomDto {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoomDto(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public ChatRoom toEntity(String roomId, String name) {
        return ChatRoom.builder()
                .roomId(roomId)
                .name(name)
                .build();
    }
}