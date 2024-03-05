package com.example.techtree.domain.chat.dto;

import com.example.techtree.domain.chat.entity.ChatMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {

    public enum MessageType {
        ENTER, TALK, QUIT
    }

    private ChatMessage.MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
