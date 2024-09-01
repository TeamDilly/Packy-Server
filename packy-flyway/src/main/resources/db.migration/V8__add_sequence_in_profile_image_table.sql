-- profile_image 테이블에 sequence 컬럼 추가
alter table profile_image add column sequence bigint;

SET @row_number = 0;
UPDATE profile_image
SET sequence = (@row_number:=@row_number + 1)
    ORDER BY id;
