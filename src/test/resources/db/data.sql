TRUNCATE TABLE flyway_schema_history;

ALTER TABLE activity_notification AUTO_INCREMENT = 1;
TRUNCATE TABLE activity_notification;

INSERT INTO activity_notification (user_id, entity_id, entity_module, title, unread, payload)
VALUES ('65189df4-a121-4557-8baa-c7d5b79dd607', '1', 'test', 'test notification', true, 'test payload');

COMMIT;