package com.example.techtree.domain.chat.dao;

import com.example.techtree.domain.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomIdAndIdAfter(long roomId, long afterId);
}