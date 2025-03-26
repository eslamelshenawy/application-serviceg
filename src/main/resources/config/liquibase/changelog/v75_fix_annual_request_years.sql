
ALTER TABLE application.annual_fees_request DROP CONSTRAINT fkrn34v66rhk1qk60igw53u19xd;



truncate table application.lk_annual_request_years;



INSERT INTO application.lk_annual_request_years

(id, code, name_ar, name_en, years_count)

VALUES(1, '1_YEARS', 'سنه', '1 year', 1);

INSERT INTO application.lk_annual_request_years

(id, code, name_ar, name_en, years_count)

VALUES(2, '2_YEARS', 'سنتين', '2 years', 2);

INSERT INTO application.lk_annual_request_years

(id, code, name_ar, name_en, years_count)

VALUES(3, '3_YEARS', '3 سنوات', '3 years', 3);

INSERT INTO application.lk_annual_request_years

(id, code, name_ar, name_en, years_count)

VALUES(4, '4_YEARS', '4 سنوات', '4 years', 4);

INSERT INTO application.lk_annual_request_years

(id, code, name_ar, name_en, years_count)

VALUES(5, '5_YEARS', '5 سنوات', '5 years', 5);

INSERT INTO application.lk_annual_request_years

(id, code, name_ar, name_en, years_count)

VALUES(6, '6_YEARS', '6 سنوات', '6 years', 6);

INSERT INTO application.lk_annual_request_years

(id, code, name_ar, name_en, years_count)

VALUES(7, '7_YEARS', '7 سنوات', '7 years', 7);

INSERT INTO application.lk_annual_request_years

(id, code, name_ar, name_en, years_count)

VALUES(8, '8_YEARS', '8 سنوات', '8 years', 8);

INSERT INTO application.lk_annual_request_years

(id, code, name_ar, name_en, years_count)

VALUES(9, '9_YEARS', '9 سنوات', '9 years', 9);

INSERT INTO application.lk_annual_request_years

(id, code, name_ar, name_en, years_count)

VALUES(10, '10_YEARS', '10 سنوات', '10 years', 10);

INSERT INTO application.lk_annual_request_years

(id, code, name_ar, name_en, years_count)

VALUES(11, '11_YEARS', '11 سنه', '11 years', 11);







alter table application.annual_fees_request add constraint fkrn34v66rhk1qk60igw53u19xd foreign key (annual_year_id) references application.lk_annual_request_years;
