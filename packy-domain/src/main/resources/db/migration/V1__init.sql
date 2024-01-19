create table box (
    id bigint not null auto_increment,
    bottom_img_url varchar(255),
    top_img_url varchar(255),
    primary key (id));

create table gift_box (
    box_id bigint,
    created_at datetime(6),
    id bigint not null auto_increment,
    letter_id bigint,
    message_id bigint,
    updated_at datetime(6),
    gift_message varchar(255),
    gift_url varchar(255),
    uuid varchar(255),
    youtube_url varchar(255),
    gift_type enum ('PHOTO','LINK'),
    primary key (id));

create table kakao_account (
    created_at datetime(6),
    member_id bigint,
    updated_at datetime(6),
    id varchar(255) not null,
    primary key (id));

create table letter (
    id bigint not null auto_increment,
    letter_paper_id bigint,
    content varchar(255),
    primary key (id));

create table letter_paper (
    id bigint not null auto_increment,
    envelope_url varchar(255),
    writing_paper_url varchar(255),
    primary key (id));

create table member (
    marketing_agreement bit,
    push_notification bit,
    status tinyint check (status between 0 and 3),
    created_at datetime(6),
    id bigint not null auto_increment,
    profile_img_id bigint,
    updated_at datetime(6),
    nickname varchar(255),
    provider enum ('KAKAO','APPLE'),
    role enum ('ROLE_USER','ROLE_ADMIN'),
    primary key (id));

create table member_gift_box (
    is_sender bit,
    gift_box_id bigint,
    id bigint not null auto_increment,
    member_id bigint,
    primary key (id));

create table message (
    id bigint not null auto_increment,
    img_url varchar(255),
    primary key (id));

create table music (
    id bigint not null auto_increment,
    youtube_url varchar(255),
    primary key (id));

create table music_hashtag (
    id bigint not null auto_increment,
    music_id bigint,
    hashtag varchar(255),
    primary key (id));

create table photo (
    sequence integer,
    gift_box_id bigint,
    id bigint not null auto_increment,
    description varchar(255),
    img_url varchar(255),
    primary key (id));

create table profile_image (
    id bigint not null auto_increment,
    img_url varchar(255),
    primary key (id));

create table refresh_token (
    created_at datetime(6),
    id bigint not null auto_increment,
    member_id bigint,
    updated_at datetime(6),
    refresh_token varchar(255),
    primary key (id));

alter table kakao_account add constraint UK_fnmy6p49vr4kxlrnh8n0t1gh9 unique (member_id);

alter table refresh_token add constraint UK_dnbbikqdsc2r2cee1afysqfk9 unique (member_id);

alter table gift_box add constraint FK3apxro9jqtfx2aciu1g5j0y19 foreign key (box_id) references box (id);

alter table gift_box add constraint FKcrj1ikxqr7xk10mcpiof618bi foreign key (letter_id) references letter (id);

alter table gift_box add constraint FK1d3khn4oudd7g9ugj5v1a86ya foreign key (message_id) references message (id);

alter table kakao_account add constraint FK54mr3x1wdsa08h33wvcnxryta foreign key (member_id) references member (id);

alter table letter add constraint FKfd8botskbwet6u5qadmmb1rs8 foreign key (letter_paper_id) references letter_paper (id);

alter table member add constraint FKifi814eqmrbmib4w8f06u2y2c foreign key (profile_img_id) references profile_image (id);

alter table member_gift_box add constraint FK9of1y5raka9lw47rcvpt28rm5 foreign key (gift_box_id) references gift_box (id);

alter table member_gift_box add constraint FKbxqfkxlxtwkc36xb4ayt92nr1 foreign key (member_id) references member (id);

alter table music_hashtag add constraint FK46ostouiltjwjcytcghio8w9a foreign key (music_id) references music (id);

alter table photo add constraint FKe18pcn71jkuf5jhque1cpj41f foreign key (gift_box_id) references gift_box (id);

alter table refresh_token add constraint FK5gdbafb2i76hk1ai18ah6an4w foreign key (member_id) references member (id);
