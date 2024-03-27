package com.example.techtree.domain.inquiry.chat.service;

import com.example.techtree.domain.inquiry.chat.dao.ChatRoomRepository;
import com.example.techtree.domain.inquiry.chat.dto.ChatRoomDto;
import com.example.techtree.domain.inquiry.chat.entity.ChatMessage;
import com.example.techtree.domain.inquiry.chat.entity.ChatRoom;
import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.saving.goal.entity.GoalStatus;
import com.example.techtree.global.rsData.DataNotFoundException;
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
    public ChatRoom make(ChatRoomDto chatRoomDto, Member member) {
        ChatRoom chatRoom = chatRoomDto.toEntity(member);

        chatRoomRepository.save(chatRoom);

        return chatRoom;
    }

    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAll();
    }

    @Transactional
    public ChatMessage write(long roomId, String content, String writer) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();

        ChatMessage chatMessage = chatRoom.writeMessage(content, writer);

        return chatMessage;
    }

    public Optional<ChatRoom> findById(long roomId) {
        return chatRoomRepository.findById(roomId);
    }

    public void deleteChatRoom(Long Id) {

        chatRoomRepository.deleteById(Id);
    }

    public void completeChatRoom(Long roomId) {
        Optional<ChatRoom> optionalChatRoom = findById(roomId);
        if (optionalChatRoom.isPresent()) {
            ChatRoom chatRoom = optionalChatRoom.get();
            chatRoom.setGoalStatus(GoalStatus.COMPLETED);
            chatRoomRepository.save(chatRoom);
        } else {
            throw new DataNotFoundException("roomId not found");
        }
    }
}
