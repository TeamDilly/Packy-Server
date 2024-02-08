-- member 컬럼을 sender로 변경
-- 1. 기존 외래 키 제약 조건 삭제
ALTER TABLE member_gift_box
DROP FOREIGN KEY FKbxqfkxlxtwkc36xb4ayt92nr1;

-- 2. 컬럼 이름 변경
ALTER TABLE member_gift_box
CHANGE COLUMN member_id sender_id BIGINT;

-- 3. 새로운 외래 키 제약 조건 추가
ALTER TABLE member_gift_box
ADD CONSTRAINT FKt8kv89kwdufulwkxx3quai6vx
FOREIGN KEY (sender_id) REFERENCES member(id);

-- receiver 컬럼 추가
ALTER TABLE member_gift_box
ADD COLUMN receiver_id BIGINT;

-- receiver FK 추가
ALTER TABLE member_gift_box
ADD CONSTRAINT FKr2l76todkes6bx3jlaye5hfbo
FOREIGN KEY (receiver_id) REFERENCES member(id);

-- senderName, receiverName 추가
ALTER TABLE member_gift_box
ADD COLUMN senderName VARCHAR(255),
ADD COLUMN receiverName VARCHAR(255);

