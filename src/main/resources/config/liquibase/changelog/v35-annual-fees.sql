








create table application.annual_fees_request (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, request_number varchar(500), service_type varchar(255), service_type_pay boolean, annual_year_id int8, application_support_services_type_id int8, post_request_id int8, primary key (id));
     create table application.lk_annual_request_years (id int8 not null, code varchar(255), name_ar varchar(255), name_en varchar(255), primary key (id));
     create table application.lk_post_request_reasons (id int8 not null, code varchar(255), name_ar varchar(255), name_en varchar(255), primary key (id));

     alter table application.annual_fees_request add constraint FKrn34v66rhk1qk60igw53u19xd foreign key (annual_year_id) references application.lk_annual_request_years;
     alter table application.annual_fees_request add constraint FKn8n06817l77515ogpmqqb3kwn foreign key (application_support_services_type_id) references application.application_support_services_type;
     alter table application.annual_fees_request add constraint FKavm1k2a2v6nwljxyi0sawb3b6 foreign key (post_request_id) references application.lk_post_request_reasons;



INSERT INTO application.lk_annual_request_years (id, code, name_ar, name_en)
VALUES (1, '2_YEARS','سنتين','2 years');
INSERT INTO application.lk_annual_request_years (id, code, name_ar, name_en)
VALUES (2, '3_YEARS','3 سنوات','3 years');
INSERT INTO application.lk_annual_request_years (id, code, name_ar, name_en)
VALUES (3, '4_YEARS','4 سنوات','4 years');
INSERT INTO application.lk_annual_request_years (id, code, name_ar, name_en)
VALUES (4, '5_YEARS','5 سنوات','5 years');


INSERT INTO application.lk_post_request_reasons (id, code, name_ar, name_en)
VALUES (1, 'Postponement due to appeal','تاجيل بسب استئناف','Postponement due to appeal');
INSERT INTO application.lk_post_request_reasons (id, code, name_ar, name_en)
VALUES (2, 'Postponed due to opposition','تاجيل بسب معارضة','Postponed due to opposition');
INSERT INTO application.lk_post_request_reasons (id, code, name_ar, name_en)
VALUES (3, 'Postponement due to not obtaining a protection document','تاجيل بسب عدم الحصول علي وثيقة الحماية','Postponement due to not obtaining a protection document');
