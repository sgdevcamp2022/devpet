package com.devpet.chat.repo;

import com.devpet.chat.domain.ChatMessageDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessageDo, String> {
}
