-- 컬럼명을 screen_type에서 type으로 변경
alter table admin_gift_box change screen_type type enum('ONBOARDING');
