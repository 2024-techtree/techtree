package com.example.techtree.domain.inquiry.chat.entity;

import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.saving.goal.entity.GoalStatus;
import com.example.techtree.global.entity.chat.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Setter
@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@SuperBuilder
@ToString(callSuper = true)
public class ChatRoom extends BaseEntity {
    private String name;
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @OrderBy("id DESC")
    @JsonIgnore
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    private String title;
    @Enumerated(EnumType.STRING)
    @Column
    private GoalStatus goalStatus; // 진행 혹은 완료

    public ChatMessage writeMessage(String content, String writer) {
        System.out.println("getMember().getUsername() = " + this.name);
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(this)
                .name(writer)    // 시큐리티에서 가져온 name 사용
                .content(content)
                .build();

        chatMessages.add(chatMessage);

        return chatMessage;
    }
}
