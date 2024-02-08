-- envelope 테이블 컬럼 변경
ALTER TABLE envelope CHANGE COLUMN envelope_url img_url varchar(255);
alter table envelope add column sequence bigint;
SET @row_number = 0;
UPDATE box
SET sequence = (@row_number:=@row_number + 1)
    ORDER BY id;
