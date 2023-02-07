package com.devpet.chat.controller;

import com.devpet.chat.repo.ChatRepository;
import com.google.gson.Gson;
import com.devpet.chat.model.ChatRoom;
import com.devpet.chat.repo.ChatRoomRepository;
import com.devpet.chat.service.BackUpService;
import com.devpet.chat.service.ChatRoomService;
import com.devpet.chat.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

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
    public List<String> test() {
        List<String> t = new ArrayList<>();
        t.add("u");
        t.add("2");

        return t;
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllRoom();
        return chatRooms;
    }

//    @PostMapping("/room")
//    @ResponseBody
//    public ChatRoom createRoom(@RequestParam List<String> userId) {
//        ChatRoom chatRoom = ChatRoom.create(userId);
//        ChatMessage chatMessage = new ChatMessage(
//                ChatMessage.MessageType.INIT,
//                chatRoom.getRoomId(),
//                "SYSTEM", "채팅이 시작되었습니다.",
//                LocalDateTime.now().toString()
//        );
//        String message = gson.toJson(chatMessage);
//        return chatRoomRepository.createChatRoom(chatRoom);
//    }

    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestBody List<String> userId) {
        ChatRoom chatRoom = ChatRoom.create(userId);
        return chatRoomRepository.createChatRoom(chatRoom);
    }

//    @GetMapping("/room/enter/{roomId}")
//    public String roomDetail(@PathVariable String roomId) {
//        int lineNumber = 0;
//        Map<String , Object> messages = new HashMap<>();
//        List<ChatMessage> messageList = chatService.getMessageList(roomId, lineNumber);
//
//
//        messages.put("roomId", roomId);
//        messages.put("messageList", messageList);
//        return "/chat/roomdetail";
//

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }

//    @GetMapping("/user")
//    @ResponseBody
//    public LoginInfo getUserInfo() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        return LoginInfo.builder().name(name).token(jwtTokenProvider.generateToken(name)).build();
//    }
}
