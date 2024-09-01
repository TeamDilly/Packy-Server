-- 컬럼 추가
ALTER TABLE box ADD COLUMN lottie_make_url VARCHAR(255) NOT NULL;
ALTER TABLE box ADD COLUMN lottie_arrived_url VARCHAR(255) NOT NULL;

-- 데이터 삽입
UPDATE box SET lottie_make_url = CONCAT('https://packy-bucket.s3.ap-northeast-2.amazonaws.com/admin/design/Box_motion/make/Box_motion_make_', id, '.json');
UPDATE box SET lottie_arrived_url = CONCAT('https://packy-bucket.s3.ap-northeast-2.amazonaws.com/admin/design/Box_motion/arr/Box_motion_arr_', id, '.json');
