package com.devpet.chat.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChatMessage  {

    public ChatMessage() {
    }

    @Builder
    public ChatMessage(MessageType type, String roomId, String sender, String receiver, String message ,String timeLog) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timeLog = timeLog;
    }

    // 메시지 타입 : 입장, 퇴장, 채팅

    public enum MessageType {
        INIT, ENTER, QUIT, TALK, URL, IMAGE, VIDEO
    }


    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String receiver;    //메시지 받는사람
    private String message; // 메시지
    private String timeLog;
}
