package com.devpet.chat.controller;

import com.google.gson.Gson;
import com.devpet.chat.model.ChatMessage;
import com.devpet.chat.model.ChatRoom;
import com.devpet.chat.model.LoginInfo;
import com.devpet.chat.repo.ChatRoomRepository;
import com.devpet.chat.service.BackUpService;
import com.devpet.chat.service.ChatRoomService;
import com.devpet.chat.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomService chatRoomService;
    private final Gson gson;

    private final BackUpService backUpService;

    @GetMapping("/room")
    public String rooms() {
        return "/chat/room";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        backUpService.WriteChatMessages();
        backUpService.ReadChatMessages();
        return "SUCCESS";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllRoom();
        chatRooms.stream().forEach(room -> room.setUserCount(chatRoomRepository.getUserCount(room.getRoomId())));
        return chatRooms;
    }

    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        ChatMessage chatMessage = new ChatMessage(ChatMessage.MessageType.INIT, chatRoom.getRoomId(), "SYSTEM", "채팅이 시작되었습니다.", LocalDateTime.now().toString());
        String message = gson.toJson(chatMessage);
        return chatRoomRepository.createChatRoom(name, chatRoom, message);
    }

    @GetMapping("/room/enter/{roomId}")

    public String roomDetail(@PathVariable String roomId) {
        int lineNumber = 0;
        Map<String , Object> data = new HashMap<>();
        List<ChatMessage> messageList = chatRoomService.getMessageList(roomId, lineNumber);


        data.put("roomId", roomId);
        data.put("messageList", messageList);
        return "/chat/roomdetail";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }

    @GetMapping("/user")
    @ResponseBody
    public LoginInfo getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return LoginInfo.builder().name(name).token(jwtTokenProvider.generateToken(name)).build();
    }
}
