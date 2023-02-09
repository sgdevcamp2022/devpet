package com.devpet.chat.repo;

import com.devpet.chat.domain.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomInfoRepository extends JpaRepository<RoomInfo, String> {
    List<RoomInfo> findByRoomId(String roomId);
}
