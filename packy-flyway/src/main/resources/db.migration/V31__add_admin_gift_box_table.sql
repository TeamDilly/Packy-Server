-- admin_gift_box 테이블 생성
create table admin_gift_box (gift_box_id bigint, id bigint not null auto_increment, screen_type enum ('ONBOARDING'), primary key (id));

alter table admin_gift_box add constraint UK_ch8i00c0ry5pmqba9wfjygo7b unique (gift_box_id);
alter table admin_gift_box add constraint FKrqscgkmmi1tdameg7ecnogr35 foreign key (gift_box_id) references gift_box (id);
