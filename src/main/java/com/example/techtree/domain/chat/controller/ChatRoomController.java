package com.example.techtree.domain.chat.controller;

import com.example.techtree.domain.chat.entity.ChatRoom;
import com.example.techtree.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chat/room")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/chat/room/{roomId}")
    @ResponseBody
    public String showRoom(
            @PathVariable final long roomId,
            @RequestParam(defaultValue = "NoName") final String writerName
    ) {
        return "%d번 채팅방 입니다. writer : %s".formatted(roomId, writerName);
    }

    @GetMapping("/make")
    public String showMake() {
        return "chat/chatmake";
    }

    @PostMapping("/make")
    public String make(
            final String name
    ) {
        chatRoomService.make(name);

        return "redirect:/chat/room/make?message=Chat Room Created";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<ChatRoom> chatRooms = chatRoomService.findAll();
        model.addAttribute("chatRooms", chatRooms);
        return "chat/chatList";
    }
}
