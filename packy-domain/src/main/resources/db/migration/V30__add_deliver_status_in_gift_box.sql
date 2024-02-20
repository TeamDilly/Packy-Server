-- gift_box 테이블에 deliver_status 추가
alter table gift_box
add column deliver_status enum('WAITING', 'DELIVERED') not null default 'WAITING';

-- 기존에 만들어진 박스는 DELIVERED 처리
update gift_box
set deliver_status = 'DELIVERED';
