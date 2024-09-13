-- setting 테이블에 name 추가
alter table setting
add column name varchar(255);

-- 기존에 있던 설정 이름 추가
update setting set name = '패키 공식 SNS' where id = 1;
update setting set name = '1:1 문의하기' where id = 2;
update setting set name = '패키에게 의견 보내기' where id = 3;
update setting set name = '이용약관' where id = 4;
update setting set name = '개인정보처리방침' where id = 5;
