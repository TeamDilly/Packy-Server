-- message_id에 걸린 인덱스 제거
ALTER TABLE gift_box DROP INDEX FK1d3khn4oudd7g9ugj5v1a86ya;

-- message_id 컬럼 제거
ALTER TABLE gift_box DROP COLUMN message_id;
