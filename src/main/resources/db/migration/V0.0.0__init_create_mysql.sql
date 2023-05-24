DROP TABLE IF EXISTS activity_notification;
CREATE TABLE activity_notification (
    id bigint not null primary key auto_increment,
    user_id varchar(255) not null,
    entity_id varchar(100) not null,
    entity_module varchar(30) not null,
    title varchar(255) not null,
    unread boolean not null,
    payload TEXT
);
CREATE UNIQUE INDEX unique_key_user_entity ON activity_notification(user_id, entity_id, entity_module);