-- giftbox 테이블에 sticker_id 추가 및 FK 제약조건 추가
alter table gift_box
    add column sticker_id bigint default null;
alter table gift_box
    add constraint FKl0hwb278j7pdug1766vljb4y9 foreign key (sticker_id) references sticker (id);
