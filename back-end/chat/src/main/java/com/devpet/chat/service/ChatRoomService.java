package com.devpet.chat.service;

import com.google.gson.Gson;
import com.devpet.chat.model.ChatMessage;
import com.devpet.chat.repo.ChatRoomRepository;
import com.devpet.chat.repo.RoomInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatRoomService {

    private final ChatRoomRepository repository;
    private final RoomInfoRepository roomInfoRepository;
    private final Gson gson;


    /**
     * 방 정보와 읽은 메시지 line을 받아 Json에서 ChatMessage 객체로 변환하여 반환
     * @param roomId
     * @return
     */
    public List<ChatMessage> getMessageList(String roomId, int lineNumber){
        List<String> messageList = repository.getMessageList(roomId, lineNumber);
        List<ChatMessage> messages = new ArrayList<>();
        if(!messageList.isEmpty()){
            for(String message : messageList){
                    ChatMessage messageObject =  gson.fromJson(message, ChatMessage.class);
                    messages.add(messageObject);
            }

        }
        log.info("messageList : {}", messageList);
        return messages;
    }


}
