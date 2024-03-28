package com.example.techtree.domain.inquiry.chat.controller;

import com.example.techtree.domain.inquiry.chat.dto.ChatRoomDto;
import com.example.techtree.domain.inquiry.chat.entity.ChatMessage;
import com.example.techtree.domain.inquiry.chat.entity.ChatRoom;
import com.example.techtree.domain.inquiry.chat.service.ChatMessageService;
import com.example.techtree.domain.inquiry.chat.service.ChatRoomService;
import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.member.service.MemberService;
import com.example.techtree.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/chat/room")
@RequiredArgsConstructor
public class ChatRoomController {
    @Autowired
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{roomId}")
    public String showRoom(
            @PathVariable final long roomId,
            final String writerName,
            Model model
    ) {
        String loginId = memberService.getLoginId();
        Member member = memberService.findByLoginId(loginId);
        System.out.println("member = " + member.getRole());
        ChatRoom room = chatRoomService.findById(roomId).get();
        model.addAttribute("room", room);
        model.addAttribute("username", member.getUsername());
        System.out.println("현재 관리자 아이디 = " + room.getName());
        System.out.println("현재 관리자 아이디 = " + member.getUsername());
        return "domain/chat/chatRoom";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/make")
    public String showMake(Principal principal, Model model, ChatRoomDto chatRoomDto) {
        System.out.println("login principal = " + principal.getName());
        model.addAttribute("chatDto", chatRoomDto);
        if(principal == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그인이 필요합니다.");
        }
        return "domain/chat/chatmake";
    }

    @PostMapping("/make")
    public String make(Model model, @Valid @ModelAttribute("chatDto") ChatRoomDto chatRoomDto
    , BindingResult bindingResult) {
        String loginId = memberService.getLoginId();
        Member member = memberService.findByLoginId(loginId);
        ChatRoom chatRoom;
        model.addAttribute("member", member);
        if(bindingResult.hasErrors()) {
            return "domain/chat/chatmake";
        }
        try {
            chatRoom = chatRoomService.make(chatRoomDto, member);   // 치환 후 redirect 에서 바로 방으로 이동
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "domain/chat/chatmake";
        }
        return "redirect:/chat/room/" + chatRoom.getId();
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<ChatRoom> chatRooms = chatRoomService.findAll();
        String loginId = memberService.getLoginId();
        Member member = memberService.findByLoginId(loginId);
        System.out.println("chatRooms = " + chatRooms.get(1).getGoalStatus());
        model.addAttribute("chatRooms", chatRooms);
        model.addAttribute("member", member);

        return "domain/chat/chatList";
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
        private ChatMessage message;
    }

    @PostMapping("/{roomId}/write")
    @ResponseBody
    public RsData<?> write(
            @PathVariable final long roomId,
            @RequestBody final WriteRequestBody requestBody) {
        String loginId = memberService.getLoginId();
        Member member = memberService.findByLoginId(loginId);
        requestBody.setWriterName(member.getUsername());

        ChatMessage chatMessage = chatRoomService.write(roomId, requestBody.getContent(), requestBody.getWriterName());

        RsData<WriteResponseBody> writeRs = RsData.of("S-1", "%d번 메시지를 작성하였습니다.".formatted(chatMessage.getId()), new WriteResponseBody(chatMessage));

        messagingTemplate.convertAndSend("/topic/chat/room/" + roomId + "/messageCreated", writeRs);

        System.out.println("member.getUsername() = " + member.getUsername());

        return RsData.of("S-1", "성공");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete")
    public String deleteChatRoom(@RequestParam(value = "id") Long id) {
        chatRoomService.deleteChatRoom(id);
        return "redirect:/chat/room/list";
    }

    @PutMapping("/complete/{roomId}")
    public ResponseEntity<String> completeChatRoom(@PathVariable Long roomId) {
        // roomId에 해당하는 채팅방을 찾아 상태를 변경합니다.
        System.out.println("종료를 진행합니다. " + roomId);
        chatRoomService.completeChatRoom(roomId);
        Optional<ChatRoom> chatRoom = chatRoomService.findById(roomId);
        System.out.println(" chatRoom의 상태 " + chatRoom.get().getGoalStatus());

        return ResponseEntity.status(HttpStatus.OK).body("Chat room completed successfully!");
    }
}