package com.mpv.notificationservice.repository;

import com.mpv.notificationservice.entity.EmailInbox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmailInboxRepository extends JpaRepository<EmailInbox, UUID> {

    boolean existsByKeyAndValue(String key, String value);

    List<EmailInbox> findByProcessedFalseOrderByCreatedAtAsc(Pageable pageable);
}

