-- 기존 컬럼 삭제
alter table box drop column full_img_url,
drop column part_img_url,
drop column top_img_url;

-- 새로운 컬럼 추가
alter table box add column normal_img_url varchar(255) not null,
                  add column set_img_url varchar(255) not null,
                  add column small_img_url varchar(255) not null,
                  add column top_img_url varchar(255) not null;
