package com.devpet.chat.repo;

import com.google.gson.Gson;
import com.devpet.chat.model.ChatMessage;
import com.devpet.chat.model.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatRepository {
    // Redis CacheKeys
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    //    public static final String MESSAGE_COUNT = "MESSAGE_COUNT"; //채팅 라인 카운터
    public static final String MESSAGES = "MESSAGES"; //채팅 라인 카운터
    public static final String ENTER_INFO = "ENTER_INFO"; // 채팅룸에 입장한 클라이언트의 sessionId와 채팅룸 id를 맵핑한 정보 저장

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, ChatRoom> hashOpsChatRoom;
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOpsEnterInfo;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, Long> chatCounter;

    @Resource(name = "redisTemplate")
    ListOperations<String, ChatMessage> messageLog;

    // 모든 채팅방 조회
    public List<ChatRoom> findAllRoom() {
        return hashOpsChatRoom.values(CHAT_ROOMS);
    }

    // 특정 채팅방 조회
    public ChatRoom findRoomById(String id) {
        return hashOpsChatRoom.get(CHAT_ROOMS, id);
    }

    // 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.


    /**
     * 받은 메시지 저장
     *
     * @param chatMessage
     */
    public void saveMessage(ChatMessage chatMessage) {
        String roomId = chatMessage.getRoomId();
        String userName = chatMessage.getReceiver();
        messageLog.rightPush(MESSAGES + "_" + userName, chatMessage);
        messageLog.rightPush(MESSAGES + "_" + roomId, chatMessage);

    }

    public List<String> getMessageList(String userName) {
        Long number = redisTemplate.opsForList().size(MESSAGES + "_" + userName);
        List<String> messageList = redisTemplate.opsForList().range(MESSAGES + "_" + userName, 0, number);
        redisTemplate.delete(MESSAGES + "_" + userName);
        return messageList;
    }

}
