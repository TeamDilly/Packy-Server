-- 외래 키 제약 조건 삭제
ALTER TABLE admin_gift_box DROP FOREIGN KEY FKrqscgkmmi1tdameg7ecnogr35;
ALTER TABLE gift_box_sticker DROP FOREIGN KEY FK454wobqqtgwjl6so84qkwfbu6;
ALTER TABLE photo DROP FOREIGN KEY FKe18pcn71jkuf5jhque1cpj41f;
ALTER TABLE receiver DROP FOREIGN KEY FKm8lwiysco4e9tma0bfdx7ootn;

-- auto increment 옵션 삭제
ALTER TABLE gift_box MODIFY COLUMN id BIGINT NOT NULL;

-- 외래 키 제약 조건 다시 추가
ALTER TABLE admin_gift_box ADD CONSTRAINT FKrqscgkmmi1tdameg7ecnogr35 FOREIGN KEY (gift_box_id) REFERENCES gift_box(id);
ALTER TABLE gift_box_sticker ADD CONSTRAINT FK454wobqqtgwjl6so84qkwfbu6 FOREIGN KEY (gift_box_id) REFERENCES gift_box(id);
ALTER TABLE photo ADD CONSTRAINT FKe18pcn71jkuf5jhque1cpj41f FOREIGN KEY (gift_box_id) REFERENCES gift_box(id);
ALTER TABLE receiver ADD CONSTRAINT FKm8lwiysco4e9tma0bfdx7ootn FOREIGN KEY (gift_box_id) REFERENCES gift_box(id);
