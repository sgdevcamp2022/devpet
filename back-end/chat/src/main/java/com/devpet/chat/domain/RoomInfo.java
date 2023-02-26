package com.devpet.chat.domain;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
//@Setter
//@Getter
public class RoomInfo {

    @Id
    @Column(name="room_id")
    private String roomId;
    private Long chatCounter;

    @OneToMany @JoinColumn(name = "room_id")        //MEMBER 테이블의 TEAM_ID (FK)
    List<UserInfo> userInfos;


    public RoomInfo() {
        userInfos  = new ArrayList<UserInfo>();
    }
}
