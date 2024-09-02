-- GiftMessage 컬럼 삭제
ALTER TABLE gift_box
DROP COLUMN gift_message;

-- Sticker FK 제거
ALTER TABLE gift_box
DROP FOREIGN KEY FKl0hwb278j7pdug1766vljb4y9;

-- Sticker 컬럼 삭제
ALTER TABLE gift_box
DROP COLUMN sticker_id;
