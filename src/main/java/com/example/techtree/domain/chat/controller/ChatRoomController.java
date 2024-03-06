package com.example.techtree.domain.chat.controller;

import com.example.techtree.domain.chat.entity.ChatMessage;
import com.example.techtree.domain.chat.entity.ChatRoom;
import com.example.techtree.domain.chat.service.ChatMessageService;
import com.example.techtree.domain.chat.service.ChatRoomService;
import com.example.techtree.global.rsData.RsData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chat/room")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @GetMapping("/{roomId}")
    public String showRoom(
            @PathVariable final long roomId,
            final String writerName, Model model
    ) {
        ChatRoom room = chatRoomService.findById(roomId).get();
        model.addAttribute("room", room);
        return "chat/chatRoom";
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

        return "redirect:/chat/room/list";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<ChatRoom> chatRooms = chatRoomService.findAll();
        model.addAttribute("chatRooms", chatRooms);

        return "chat/chatList";
    }

    @Getter
    @Setter
    public static class WriteRequestBody {
        private String writerName;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class WriteResponseBody {
        private Long chatMessageId;
    }

    @PostMapping("/{roomId}/write")
    @ResponseBody
    public RsData<WriteResponseBody> write(
            @PathVariable final long roomId,
            @RequestBody final WriteRequestBody requestBody
    ) {
        ChatMessage chatMessage = chatRoomService.write(roomId, requestBody.getWriterName(), requestBody.getContent());

        return RsData.of("S-1", "%d번 메시지를 작성하였습니다.".formatted(chatMessage.getId()), new WriteResponseBody(chatMessage.getId()));
    }

    @Getter
    @AllArgsConstructor
    public static class GetMessagesAfterResponseBody {
        private List<ChatMessage> messages;
    }

    @GetMapping("/{roomId}/messagesAfter/{afterId}")
    @ResponseBody
    public RsData<GetMessagesAfterResponseBody> getMessagesAfter(
            @PathVariable final long roomId,
            @PathVariable final long afterId
    ) {
        List<ChatMessage> messages = chatMessageService.findByChatRoomIdAndIdAfter(roomId, afterId);

        return RsData.of("S-1", "%d개의 메시지를 가져왔습니다.".formatted(messages.size()), new GetMessagesAfterResponseBody(messages));
    }
}