-- 새로운 열 추가
ALTER TABLE member
    ADD COLUMN status_new enum ('NOT_REGISTERED','REGISTERED','WITHDRAWAL','BLACKLIST');

-- 기존 데이터를 새로운 열에 복사
UPDATE member
SET status_new =
        CASE
            WHEN `status` = 0 THEN 'NOT_REGISTERED'
            WHEN `status` = 1 THEN 'REGISTERED'
            WHEN `status` = 2 THEN 'WITHDRAWAL'
            WHEN `status` = 3 THEN 'BLACKLIST'
            ELSE NULL
            END;

-- 기존 열 삭제
ALTER TABLE member
DROP COLUMN status;

-- 새로운 열의 이름을 원래대로 변경
ALTER TABLE member
    CHANGE COLUMN status_new status enum ('NOT_REGISTERED','REGISTERED','WITHDRAWAL','BLACKLIST');
