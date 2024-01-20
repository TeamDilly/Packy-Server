create table apple_account
(
    created_at    datetime(6)  null,
    member_id     bigint       null,
    updated_at    datetime(6)  null,
    id            varchar(255) not null
        primary key,
    refresh_token varchar(255) not null,
    constraint UK_dgjlx80xq0hvlqlrolg0nxfbl
        unique (member_id),
    constraint FKjp63nbe4doslyu7tfmv57nb71
        foreign key (member_id) references member (id)
);
