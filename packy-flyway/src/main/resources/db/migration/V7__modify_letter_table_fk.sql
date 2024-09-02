-- letter_paper_id 컬럼의 제약 조건 삭제
ALTER TABLE `letter`
DROP FOREIGN KEY `FKfd8botskbwet6u5qadmmb1rs8`;

-- letter_paper_id 컬럼 삭제
ALTER TABLE `letter`
DROP COLUMN `letter_paper_id`;

-- envelope_id 컬럼 추가
ALTER TABLE `letter`
    ADD COLUMN `envelope_id` bigint DEFAULT NULL;

-- envelope_id에 대한 외래 키 추가
alter table letter add constraint FK15ga9hy6doxcngqk59v4pen6r foreign key (envelope_id) references envelope (id);

