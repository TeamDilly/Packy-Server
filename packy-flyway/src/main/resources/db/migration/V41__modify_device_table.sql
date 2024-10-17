-- token을 fcm_token으로 이름 변경
ALTER TABLE device CHANGE COLUMN token fcm_token varchar(255);

-- created_at, updated_at 컬럼 추가
ALTER TABLE device ADD COLUMN created_at datetime(6), ADD COLUMN updated_at datetime(6);

-- device_id 컬럼 추가
ALTER TABLE device ADD COLUMN device_id varchar(255);

-- platform 컬럼 추가
ALTER TABLE device ADD COLUMN platform enum('ANDROID', 'IOS');
