package com.example.techtree.domain.inquiry.chat.service;

import com.example.techtree.domain.inquiry.chat.dao.ChatRoomRepository;
import com.example.techtree.domain.inquiry.chat.entity.ChatMessage;
import com.example.techtree.domain.inquiry.chat.entity.ChatRoom;
import com.example.techtree.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    @Autowired
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void make(Member member) {

        ChatRoom chatRoom = ChatRoom.builder()
                .name(member.getUsername())
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

    public void deleteChatRoom(Long Id) {
        chatRoomRepository.deleteById(Id);
    }
}
