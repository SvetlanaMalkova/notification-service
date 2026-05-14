package com.mpv.notificationservice.repository;

import com.mpv.notificationservice.entity.SmsInbox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SmsInboxRepository extends JpaRepository<SmsInbox, UUID> {

    boolean existsByKeyAndValue(String key, String value);

    List<SmsInbox> findByProcessedFalseOrderByCreatedAtAsc(Pageable pageable);
}
