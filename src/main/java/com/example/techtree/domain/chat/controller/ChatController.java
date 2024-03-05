package com.example.techtree.domain.chat.controller;

import com.example.techtree.domain.chat.entity.ChatRoom;
import com.example.techtree.domain.chat.entity.ChatRoomDto;
import com.example.techtree.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @RequestMapping("/chatList")
    public String chatList(Model model) {
        List<ChatRoomDto> roomList = chatService.findAllRoom();
        model.addAttribute("roomList", roomList);
        return "chat/chatList";
    }

    @PostMapping("/createRoom")
    public String createRoom(@RequestParam String name, Model model, String username) {
        ChatRoomDto room = chatService.createRoom(name);
        model.addAttribute("room", room);
        model.addAttribute("username", username);
        return "chat/chatRoom";
    }

    @GetMapping("/chatRoom")
    public String chatRoom(Model model, @RequestParam(required = false) String roomId) {
        ChatRoomDto room = chatService.findRoomById(roomId);
        model.addAttribute("room", room);
        return "chat/chatRoom";
    }
}