-- last_viewed_admin_type 테이블 추가
create table last_viewed_admin_type (
    id bigint not null auto_increment,
    member_id bigint,
    admin_type enum ('ONBOARDING'),
    primary key (id));

-- last_viewed_admin_type 테이블 외래키 추가
alter table last_viewed_admin_type add constraint UK_9e9i7j34sirppf0c43dsu7gfy unique (member_id);
alter table last_viewed_admin_type add constraint FKcx4i86qiiuokp7x2ekvmrvtv9 foreign key (member_id) references member (id);
