-- Message 테이블 제약 조건 삭제
alter table gift_box
drop foreign key `FKfd8botskbwet6u5qadmmb1rs8`;

-- Message 테이블 삭제
drop table if exists message;

-- box 테이블 컬럼 변경
-- alter table box drop column top_img_url;
alter table box add column sequence bigint;
alter table box add column full_img_url varchar(255);

-- letter 테이블을 envelope 테이블로 변경
RENAME TABLE letter_paper TO envelope;

-- writing_paper_url 컬럼 삭제
ALTER TABLE envelope
DROP COLUMN writing_paper_url;




