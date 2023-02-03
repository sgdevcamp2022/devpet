package com.devpet.chat.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private List<String> userId;
    private long userCount; // 채팅방 인원수

    public static ChatRoom create(List<String> userIdList) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.userId = userIdList;
        return chatRoom;
    }
}
