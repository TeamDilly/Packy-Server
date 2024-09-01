-- GiftBoxSticker 테이블 생성
create table gift_box_sticker
(
    location    int    null,
    gift_box_id bigint null,
    id          bigint auto_increment
        primary key,
    sticker_id  bigint null,
    constraint FK454wobqqtgwjl6so84qkwfbu6
        foreign key (gift_box_id) references gift_box (id),
    constraint FKj9pif7u3npxman0g7v6qt9xs3
        foreign key (sticker_id) references sticker (id)
);
