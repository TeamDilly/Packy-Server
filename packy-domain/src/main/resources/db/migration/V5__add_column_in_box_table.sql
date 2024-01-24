alter table box
    add column part_img_url varchar(255);

UPDATE box
SET part_img_url = 'https://packy-bucket.s3.ap-northeast-2.amazonaws.com/admin/design/box-part/box_part_1.png'
WHERE id = 1;
UPDATE box
SET part_img_url = 'https://packy-bucket.s3.ap-northeast-2.amazonaws.com/admin/design/box-part/box_part_1.png'
WHERE id = 2;
UPDATE box
SET part_img_url = 'https://packy-bucket.s3.ap-northeast-2.amazonaws.com/admin/design/box-part/box_part_1.png'
WHERE id = 3;
UPDATE box
SET part_img_url = 'https://packy-bucket.s3.ap-northeast-2.amazonaws.com/admin/design/box-part/box_part_1.png'
WHERE id = 4;
