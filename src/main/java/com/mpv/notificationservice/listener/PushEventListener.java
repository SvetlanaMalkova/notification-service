package com.mpv.notificationservice.listener;

import com.mpv.notificationservice.entity.PushInbox;
import com.mpv.notificationservice.repository.PushInboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PushEventListener {

    private final PushInboxRepository pushInboxRepository;

    @KafkaListener(topics = "push-events", groupId = "notification-group")
    public void listen(ConsumerRecord<String, String> record) {
        String key = record.key();
        String value = record.value();

        if (pushInboxRepository.existsByKeyAndValue(key, value)) {
            log.warn("Дубликат push_events: key={}", key);
            return;
        }

        PushInbox pushInbox = PushInbox.builder()
                .topic(record.topic())
                .key(key)
                .value(value)
                .build();

        pushInboxRepository.save(pushInbox);
        log.info("Сохранено push-events: key={}", key);
    }
}
