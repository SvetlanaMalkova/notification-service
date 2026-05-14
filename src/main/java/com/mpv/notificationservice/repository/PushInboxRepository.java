package com.mpv.notificationservice.repository;

import com.mpv.notificationservice.entity.PushInbox;
import com.mpv.notificationservice.entity.SmsInbox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PushInboxRepository extends JpaRepository<PushInbox, UUID> {

    boolean existsByKeyAndValue(String key, String value);

    List<PushInbox> findByProcessedFalseOrderByCreatedAtAsc(Pageable pageable);
}

