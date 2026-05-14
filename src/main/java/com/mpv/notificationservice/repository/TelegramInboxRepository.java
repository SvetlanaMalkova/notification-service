package com.mpv.notificationservice.repository;

import com.mpv.notificationservice.entity.SmsInbox;
import com.mpv.notificationservice.entity.TelegramInbox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TelegramInboxRepository extends JpaRepository<TelegramInbox, UUID> {

    boolean existsByKeyAndValue(String key, String value);

    List<TelegramInbox> findByProcessedFalseOrderByCreatedAtAsc(Pageable pageable);
}

