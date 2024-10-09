create table device (
    id bigint not null auto_increment,
    member_id bigint,
    token varchar(255),
    primary key (id)
);

alter table device add constraint FKs2ah6o1y9r1ox99fh8vj5y0ol foreign key (member_id) references member (id)
