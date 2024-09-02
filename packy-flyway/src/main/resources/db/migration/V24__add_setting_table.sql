create table setting (
    id bigint not null auto_increment,
    url varchar(255),
    setting_tag enum ('OFFICIAL_SNS','INQUIRY','SEND_COMMENT','TERMS_OF_USE','PRIVACY_POLICY'),
    primary key (id)
 );
