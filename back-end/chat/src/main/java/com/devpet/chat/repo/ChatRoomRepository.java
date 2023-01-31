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
public class ChatRoomRepository {
    // Redis CacheKeys
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static final String CHAT_ROOMS = "CHAT_ROOM";

    public static final String USER_COUNT = "USER_COUNT"; // 채팅룸에 입장한 클라이언트수 저장
    public static final String MESSAGE_COUNT = "MESSAGE_COUNT"; //채팅 라인 카운터
    public static final String MESSAGES = "MESSAGES"; //채팅 라인 카운터
    public static final String ENTER_INFO = "ENTER_INFO"; // 채팅룸에 입장한 클라이언트의 sessionId와 채팅룸 id를 맵핑한 정보 저장
    public static final Gson gson = new Gson();

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

    public long findAllChatCounter(String roomId) {
        return Long.valueOf(Optional.ofNullable(valueOps.get(MESSAGE_COUNT + "_" + roomId)).orElse("0"));
    }

    // 특정 채팅방 조회
    public ChatRoom findRoomById(String id) {
        return hashOpsChatRoom.get(CHAT_ROOMS, id);
    }

    // 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.

    /**
     *
     * @param name
     * @param chatRoom
     * @param chatMessage
     * @return
     */
    public ChatRoom createChatRoom(String name, ChatRoom chatRoom, String chatMessage) {


        hashOpsChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
//        messageLog.leftPush(chatRoom.getRoomId()+"log", chatMessage);
        chatCounter.set(MESSAGE_COUNT+ "_" + chatRoom.getRoomId(), 0L);
        return chatRoom;
    }

    // 유저가 입장한 채팅방ID와 유저 세션ID 맵핑 정보 저장
    public void setUserEnterInfo(String sessionId, String roomId) {
        hashOpsEnterInfo.put(ENTER_INFO, sessionId, roomId);
    }

    // 유저 세션으로 입장해 있는 채팅방 ID 조회
    public String getUserEnterRoomId(String sessionId) {
        return hashOpsEnterInfo.get(ENTER_INFO, sessionId);
    }

    // 유저 세션정보와 맵핑된 채팅방ID 삭제
    public void removeUserEnterInfo(String sessionId) {
        hashOpsEnterInfo.delete(ENTER_INFO, sessionId);
    }

    // 채팅방 유저수 조회
    public long getUserCount(String roomId) {
        return Long.valueOf(Optional.ofNullable(valueOps.get(USER_COUNT + "_" + roomId)).orElse("0"));
    }

    // 채팅방에 입장한 유저수 +1
    public long plusUserCount(String roomId) {
        return Optional.ofNullable(valueOps.increment(USER_COUNT + "_" + roomId)).orElse(0L);
    }

    // 채팅방에 입장한 유저수 -1
    public long minusUserCount(String roomId) {
        return Optional.ofNullable(valueOps.decrement(USER_COUNT + "_" + roomId)).filter(count -> count > 0).orElse(0L);
    }

    /**
     * 받은 메시지 저장
     * @param chatMessage
     */
    public void saveMessage(ChatMessage chatMessage) {
        String roomId = chatMessage.getRoomId();
        messageLog.rightPush(MESSAGES + "_" + roomId, chatMessage);
        chatCounter.increment(MESSAGE_COUNT+ "_" + roomId);

    }

    public List<String> getMessageList(String roomId, int lineNumber){
            return redisTemplate.opsForList().range(MESSAGES+ "_" + roomId, lineNumber, lineNumber+10);
    }

    public List<String> getAllMessageList(String roomId){
        long count = Long.valueOf(Optional.ofNullable(valueOps.get(MESSAGE_COUNT + "_" + roomId)).orElse("0"));
        return redisTemplate.opsForList().range(MESSAGES+ "_" + roomId, 0, count);
    }

}
