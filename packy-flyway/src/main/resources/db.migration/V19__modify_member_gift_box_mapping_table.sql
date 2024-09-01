-- member_gift_box 테이블 삭제
alter table member_gift_box drop foreign key FK9of1y5raka9lw47rcvpt28rm5;
alter table member_gift_box drop foreign key FKr2l76todkes6bx3jlaye5hfbo;
alter table member_gift_box drop foreign key FKt8kv89kwdufulwkxx3quai6vx;

-- receiver 테이블 생성
create table receiver (
    created_at datetime(6),
    gift_box_id bigint,
    id bigint not null auto_increment,
    receiver_id bigint,
    updated_at datetime(6),
    primary key (id));

alter table receiver add constraint FKm8lwiysco4e9tma0bfdx7ootn foreign key (gift_box_id) references gift_box (id);
alter table receiver add constraint FK2g086tp9ylguk8g7w6uhq15d4 foreign key (receiver_id) references member (id);

-- GiftBox에 senderId 컬럼 및 FK 추가
alter table gift_box add column sender_id bigint;
alter table gift_box add constraint FKjgc7m3mfk297m853chvdkitoi foreign key (sender_id) references member (id);
