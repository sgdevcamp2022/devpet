package com.devpet.chat.controller;

import com.devpet.chat.model.ChatMessage;
import com.devpet.chat.repo.ChatRepository;
import com.devpet.chat.repo.ChatRoomRepository;
import com.devpet.chat.service.ChatService;
import com.devpet.chat.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final ChatService chatService;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        // Websocket에 발행된 메시지를 redis로 발행(publish)
        message.setTimeLog(LocalDateTime.now().toString());
        chatService.sendChatMessage(message);
    }

    @GetMapping("/messages")
    @ResponseBody
    public List<String> getMessageList(@RequestHeader("Authorization") String token){
        String userId = jwtTokenProvider.getEmail(token);
        List<String> ChatMessageList = chatService.getUserMessages(userId);
        return ChatMessageList;
    }

}
