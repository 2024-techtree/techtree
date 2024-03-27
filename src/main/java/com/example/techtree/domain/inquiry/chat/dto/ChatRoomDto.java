package com.example.techtree.domain.inquiry.chat.dto;

import com.example.techtree.domain.inquiry.chat.entity.ChatRoom;
import com.example.techtree.domain.member.entity.Member;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatRoomDto {

    @Size(
            min = 5, max = 15, message = "최소 5글자, 최대 20글자 입력해주세요."

    )
    private String title;

    public ChatRoom toEntity(Member member){
        return ChatRoom.builder()
                .member(member)
                .name(member.getUsername())
                .title(title)
                .build();
    }
}
