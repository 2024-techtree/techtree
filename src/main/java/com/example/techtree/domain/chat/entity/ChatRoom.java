package com.example.techtree.domain.chat.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class ChatRoom {
    private String roomId;
    private String name;
//    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }
}
