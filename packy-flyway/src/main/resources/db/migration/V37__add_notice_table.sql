-- Notice 테이블 생성
create table notice
(
    created_at datetime(6)  null,
    id         bigint auto_increment
        primary key,
    sequence   bigint       null,
    updated_at datetime(6)  null,
    img_url    varchar(255) null,
    notice_url varchar(255) null
);
