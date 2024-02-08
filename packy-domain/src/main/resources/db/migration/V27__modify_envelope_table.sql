alter table envelope drop column border_color_code;

alter table envelope
    add column envelope_border_color_code varchar(255) not null,
    add column envelope_opacity integer not null,
    add column letter_border_color_code varchar(255) not null,
    add column letter_opacity integer not null;
