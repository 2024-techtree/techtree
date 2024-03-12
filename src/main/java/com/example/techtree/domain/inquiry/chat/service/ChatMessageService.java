package com.example.techtree.domain.inquiry.chat.service;

import com.example.techtree.domain.inquiry.chat.dao.ChatMessageRepository;
import com.example.techtree.domain.inquiry.chat.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> findByChatRoomIdAndIdAfter(long roomId, long afterId) {
        return chatMessageRepository.findByChatRoomIdAndIdAfter(roomId, afterId);
    }
}