package com.devpet.chat.service;

import com.google.gson.Gson;
import com.devpet.chat.domain.ChatMessageDo;
import com.devpet.chat.domain.RoomInfo;
import com.devpet.chat.domain.UserInfo;
import com.devpet.chat.model.ChatRoom;
import com.devpet.chat.repo.ChatMessageRepository;
import com.devpet.chat.repo.ChatRoomRepository;
import com.devpet.chat.repo.RoomInfoRepository;
import com.devpet.chat.repo.UserInfoRepository;
import lombok.RequiredArgsConstructor;

import org.apache.commons.io.input.ReversedLinesFileReader;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class BackUpService {

    private final RoomInfoRepository roomInfoRepository;
    private final UserInfoRepository userInfoRepository;
    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository messageRepository;
    private final Gson gson;
    @Transactional
    public void saveRoomInfo() {
        List<ChatRoom> rooms = chatRoomRepository.findAllRoom();
        List<RoomInfo> roomInfo = new ArrayList<>();

        for (ChatRoom chatRoom : rooms) {
            long counter = chatRoomRepository.findAllChatCounter(chatRoom.getRoomId());
            RoomInfo info = RoomInfo.builder()
                    .roomId(chatRoom.getName())
                    .chatCounter(counter).build();
            roomInfo.add(info);
        }
        roomInfoRepository.saveAll(roomInfo);
    }

    @Transactional
    public void saveUserInfo(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    public void saveChatMessages() {
        List<ChatRoom> rooms = chatRoomRepository.findAllRoom();

        for (ChatRoom chatRoom :
                rooms) {
            String roomId = chatRoom.getRoomId();
            List<String> messages = chatRoomRepository.getAllMessageList(roomId);
            List<ChatMessageDo> chatMessageDos = new ArrayList<>();
            messages.forEach(s-> chatMessageDos.add(gson.fromJson(s, ChatMessageDo.class)));

            messageRepository.saveAll(chatMessageDos);
        }
    }

    /**
     * RoomID를 활용한 log file 생성
     *
     */
    public void WriteChatMessages() {
        List<ChatRoom> rooms = chatRoomRepository.findAllRoom();

        for (ChatRoom chatRoom :
                rooms) {
            String roomId = chatRoom.getRoomId();
            List<String> messages = chatRoomRepository.getAllMessageList(roomId);
            try {
                // 1. 파일 객체 생성
                File file = new File("src/main/resources/templates/chatlog");
                // 2. 파일 존재여부 체크 및 생성
                if (!file.exists()) {
                    file.mkdir();
                }
                // 3. Buffer를 사용해서 File에 write할 수 있는 BufferedWriter 생성
                BufferedWriter writer = new BufferedWriter( new FileWriter( "src/main/resources/templates/chatlog/"+roomId+".txt" , true) );
                // 4. 파일에 쓰기
                for (String s : messages) {
                    writer.append(s);
                    writer.newLine();
                }
                // 5. BufferedWriter close
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * log file 읽어오기
     *
     */
    public void ReadChatMessages(){
        // 0. 파일 준비
        File file = new File("src/main/resources/templates/chatlog/" + "50bac344-4e3a-422c-9b5d-a09b7b856552" + ".txt");

        try {
            //'commons-io:commons-io:2.8.0' 에서 불러옴
            // 1. ReversedLinesFileReader  준비
            ReversedLinesFileReader reader
                    = new ReversedLinesFileReader(file, Charset.forName("UTF-8"));

            // 2. 뒤에서 1줄 읽기
//            String lastLine = reader.readLine();
            List<String> lines = reader.readLines(30);
            // 3. 결과 출력
            System.out.println(lines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
