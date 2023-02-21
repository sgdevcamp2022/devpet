package com.devpet.chat.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name="chat_message")
public class ChatMessageDo {

    public ChatMessageDo() {
    }

    // 메시지 타입 : 입장, 퇴장, 채팅

    public enum MessageType {
        INIT, ENTER, QUIT, TALK, URL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
    private long userCount; // 채팅방 인원수, 채팅방 내에서 메시지가 전달될때 인원수 갱신시 사용
    private String timeLog;
}
