CREATE TABLE IF NOT EXISTS sms_inbox
(
    id         UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    topic      VARCHAR   NOT NULL,
    key        VARCHAR   NOT NULL,
    value      TEXT      NOT NULL,
    processed  BOOLEAN   NOT NULL DEFAULT FALSE,
    attempt    INTEGER   NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS email_inbox
(
    id         UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    topic      VARCHAR   NOT NULL,
    key        VARCHAR   NOT NULL,
    value      TEXT      NOT NULL,
    processed  BOOLEAN   NOT NULL DEFAULT FALSE,
    attempt    INTEGER   NOT NULL DEFAULT 1
);
CREATE TABLE IF NOT EXISTS push_inbox
(
    id         UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    topic      VARCHAR   NOT NULL,
    key        VARCHAR   NOT NULL,
    value      TEXT      NOT NULL,
    processed  BOOLEAN   NOT NULL DEFAULT FALSE,
    attempt    INTEGER   NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS telegram_inbox
(
    id         UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    topic      VARCHAR   NOT NULL,
    key        VARCHAR   NOT NULL,
    value      TEXT      NOT NULL,
    processed  BOOLEAN   NOT NULL DEFAULT FALSE,
    attempt    INTEGER   NOT NULL DEFAULT 1
);