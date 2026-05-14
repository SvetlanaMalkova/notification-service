package com.mpv.notificationservice.listener;

import com.mpv.notificationservice.entity.SmsInbox;
import com.mpv.notificationservice.repository.SmsInboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmsEventListener {

    private final SmsInboxRepository smsInboxRepository;

    @KafkaListener(topics = "sms-events", groupId = "notification-group")
    public void listen(ConsumerRecord<String, String> record) {
        String key = record.key();
        String value = record.value();

        if (smsInboxRepository.existsByKeyAndValue(key, value)){
            log.warn("Дубликат sms_events: key={}", key);
            return;
        }

        SmsInbox smsInbox = SmsInbox.builder()
                .topic(record.topic())
                .key(key)
                .value(value)
                .build();

        smsInboxRepository.save(smsInbox);
        log.info("Сохранено sms-events: key={}", key);
    }
}
