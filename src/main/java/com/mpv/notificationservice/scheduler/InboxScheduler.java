package com.mpv.notificationservice.scheduler;

import com.mpv.notificationservice.config.InboxProperties;
import com.mpv.notificationservice.entity.InboxMessage;
import com.mpv.notificationservice.repository.EmailInboxRepository;
import com.mpv.notificationservice.repository.PushInboxRepository;
import com.mpv.notificationservice.repository.SmsInboxRepository;
import com.mpv.notificationservice.repository.TelegramInboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InboxScheduler {

    private final SmsInboxRepository smsRepo;
    private final TelegramInboxRepository telegramRepo;
    private final PushInboxRepository pushRepo;
    private final EmailInboxRepository emailRepo;
    private final InboxProperties inboxProperties;

    @Transactional
    @Scheduled(fixedDelayString = "${inbox.delay-ms}")
    public void processSms() {
        process(smsRepo.findByProcessedFalseOrderByCreatedAtAsc(
                PageRequest.of(0, inboxProperties.getBatchSize())), smsRepo);
    }

    @Transactional
    @Scheduled(fixedDelayString = "${inbox.delay-ms}")
    public void processEmail() {
        process(emailRepo.findByProcessedFalseOrderByCreatedAtAsc(
                PageRequest.of(0, inboxProperties.getBatchSize())), emailRepo);
    }

    @Transactional
    @Scheduled(fixedDelayString = "${inbox.delay-ms}")
    public void processPush() {
        process(pushRepo.findByProcessedFalseOrderByCreatedAtAsc(
                PageRequest.of(0, inboxProperties.getBatchSize())), pushRepo);
    }

    @Transactional
    @Scheduled(fixedDelayString = "${inbox.delay-ms}")
    public void processTelegram() {
        process(telegramRepo.findByProcessedFalseOrderByCreatedAtAsc(
                PageRequest.of(0, inboxProperties.getBatchSize())), telegramRepo);
    }

    private <T extends InboxMessage> void process(List<T> messages, JpaRepository<T, ?> repo) {
        for (T msg : messages) {
            try {
                log.info("Обработано событие: Key: {}, Payload: {}, topic: {}",
                        msg.getKey(), msg.getValue(), msg.getTopic());
                msg.setProcessed(true);
                repo.save(msg);
            } catch (Exception e) {
                log.error("Ошибка обработки key={}: {}", msg.getKey(), e.getMessage());
                msg.setAttempt(msg.getAttempt() + 1);
                repo.save(msg);
            }
        }
    }
}
