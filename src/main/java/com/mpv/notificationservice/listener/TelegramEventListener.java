package com.mpv.notificationservice.listener;

import com.mpv.notificationservice.entity.TelegramInbox;
import com.mpv.notificationservice.repository.TelegramInboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramEventListener {


    private final TelegramInboxRepository telegramInboxRepository;

    @KafkaListener(topics = "telegram-events", groupId = "notification-group")
    public void listen(ConsumerRecord<String, String> record) {
        String key = record.key();
        String value = record.value();

        if (telegramInboxRepository.existsByKeyAndValue(key, value)) {
            log.warn("Дубликат telegram_events: key={}", key);
            return;
        }

        TelegramInbox telegramInbox = TelegramInbox.builder()
                .topic(record.topic())
                .key(key)
                .value(value)
                .build();

        telegramInboxRepository.save(telegramInbox);
        log.info("Сохранено telegram-events: key={}", key);
    }
}
