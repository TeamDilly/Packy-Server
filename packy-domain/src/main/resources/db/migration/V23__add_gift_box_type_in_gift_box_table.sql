-- gift_box 테이블에 새로운 컬럼 추가
ALTER TABLE gift_box
ADD COLUMN gift_box_type enum('PRIVATE', 'SEMI_PUBLIC', 'PUBLIC') NOT NULL DEFAULT 'PRIVATE';
