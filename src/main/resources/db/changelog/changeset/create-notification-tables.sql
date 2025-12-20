-- liquibase formatted sql

--changeset bovsunovsky:create-notification-tables

CREATE TABLE IF NOT EXISTS notifications (
    id               BIGSERIAL PRIMARY KEY,
    user_id          BIGINT,
    source_service   VARCHAR(50),
    source_id        BIGINT,
    notification_type VARCHAR(100) NOT NULL,
    message_body     TEXT NOT NULL,
    delivery_status  VARCHAR(50),
    sent_at          TIMESTAMP,
    created_at       TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_notification_settings (
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT NOT NULL,
    email_enabled BOOLEAN NOT NULL,
    settings_json JSONB
);

CREATE TABLE IF NOT EXISTS notification_templates (
    id              BIGSERIAL PRIMARY KEY,
    template_key    VARCHAR(100) NOT NULL,
    subject_template VARCHAR(255),
    body_template   VARCHAR,
    created_at      TIMESTAMP
);
