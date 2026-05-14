package com.mpv.notificationservice.listener;

import com.mpv.notificationservice.entity.EmailInbox;
import com.mpv.notificationservice.repository.EmailInboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailEventListener {

    private final EmailInboxRepository emailInboxRepository;

    @KafkaListener(topics = "email-events", groupId = "notification-group")
    public void listen(ConsumerRecord<String, String> record) {
        String key = record.key();
        String value = record.value();

        if (emailInboxRepository.existsByKeyAndValue(key, value)) {
            log.warn("Дубликат email_events: key={}", key);
            return;
        }

        EmailInbox emailInbox = EmailInbox.builder()
                .topic(record.topic())
                .key(key)
                .value(value)
                .build();

        emailInboxRepository.save(emailInbox);
        log.info("Сохранено email-events: key={}", key);
    }
}
