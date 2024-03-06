package com.example.techtree.domain.chat.entity;

import com.example.techtree.global.entity.chat.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@ToString(callSuper = true)
public class ChatMessage extends BaseEntity {
    @Getter
    private String name;

    private String content;

    @ManyToOne
    private ChatRoom chatRoom;
}
