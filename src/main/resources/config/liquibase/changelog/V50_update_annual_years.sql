--liquibase formatted sql
-- changeset application-service:V50_update_annual_years.sql

-- add column
alter table application.lk_annual_request_years  add column years_count int4 not null default 0;

-- truncate old data
ALTER TABLE application.annual_fees_request DROP CONSTRAINT fkrn34v66rhk1qk60igw53u19xd;

truncate table application.lk_annual_request_years;

-- insert new data
INSERT INTO application.lk_annual_request_years
(id, code, name_ar, name_en, years_count)
VALUES(1, '2_YEARS', 'سنتين', '2 years', 2);
INSERT INTO application.lk_annual_request_years
(id, code, name_ar, name_en, years_count)
VALUES(2, '3_YEARS', '3 سنوات', '3 years', 3);
INSERT INTO application.lk_annual_request_years
(id, code, name_ar, name_en, years_count)
VALUES(3, '4_YEARS', '4 سنوات', '4 years', 4);
INSERT INTO application.lk_annual_request_years
(id, code, name_ar, name_en, years_count)
VALUES(4, '5_YEARS', '5 سنوات', '5 years', 5);
INSERT INTO application.lk_annual_request_years
(id, code, name_ar, name_en, years_count)
VALUES(5, '6_YEARS', '6 سنوات', '6 years', 6);
INSERT INTO application.lk_annual_request_years
(id, code, name_ar, name_en, years_count)
VALUES(6, '7_YEARS', '7 سنوات', '7 years', 7);
INSERT INTO application.lk_annual_request_years
(id, code, name_ar, name_en, years_count)
VALUES(7, '8_YEARS', '8 سنوات', '8 years', 8);
INSERT INTO application.lk_annual_request_years
(id, code, name_ar, name_en, years_count)
VALUES(8, '9_YEARS', '9 سنوات', '9 years', 9);
INSERT INTO application.lk_annual_request_years
(id, code, name_ar, name_en, years_count)
VALUES(9, '10_YEARS', '10 سنوات', '10 years', 10);
INSERT INTO application.lk_annual_request_years
(id, code, name_ar, name_en, years_count)
VALUES(10, '11_YEARS', '11 سنه', '11 years', 11);

-- restore fk
ALTER TABLE application.annual_fees_request ADD CONSTRAINT fkrn34v66rhk1qk60igw53u19xd FOREIGN KEY (annual_year_id) REFERENCES application.lk_annual_request_years (id);
