-- gift_box 테이블에 sender_deleted 컬럼 추가
alter table gift_box
add column sender_deleted bit not null default false;

-- receiver 테이블에 status 컬럼 추가
alter table receiver
add column status enum('RECEIVED', 'DELETED') not null default 'RECEIVED';

