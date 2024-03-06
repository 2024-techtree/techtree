package com.example.techtree.domain.chat.service;

import com.example.techtree.domain.chat.dao.ChatRoomRepository;
import com.example.techtree.domain.chat.entity.ChatMessage;
import com.example.techtree.domain.chat.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void make(String name) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .build();

        chatRoomRepository.save(chatRoom);
    }

    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAll();
    }

    @Transactional
    public ChatMessage write(long roomId, String writerName, String content) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();

        ChatMessage chatMessage = chatRoom.writeMessage(writerName, content);

        return chatMessage;
    }

    public Optional<ChatRoom> findById(long roomId) {
        return chatRoomRepository.findById(roomId);
    }
}
