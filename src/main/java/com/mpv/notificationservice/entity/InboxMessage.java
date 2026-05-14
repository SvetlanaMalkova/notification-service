package com.mpv.notificationservice.entity;

public interface InboxMessage {
    String getKey();

    String getValue();

    String getTopic();

    void setProcessed(boolean processed);

    int getAttempt();

    void setAttempt(int attempt);
}
