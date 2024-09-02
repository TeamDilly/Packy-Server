create table notice_image
(
    id        bigint auto_increment
        primary key,
    notice_id bigint       null,
    sequence  bigint       null,
    img_url   varchar(255) null,
    constraint FK13h2d99jqpw7xnaj5w1crvv8m
        foreign key (notice_id) references notice (id)
);
