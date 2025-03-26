

create table application.lk_task_eqm_items (id int4 not null, code varchar(255), name_ar varchar(255), name_en varchar(255), application_category_id int8, primary key (id));
create table application.task_eqm (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, average float8 not null, comments varchar(255), is_enough boolean not null, task_id varchar(255), application_info_id int8, primary key (id));
create table application.task_eqm_rating_items (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, value int4 not null, task_eqm_id int8, task_eqm_item_id int4, primary key (id));
alter table application.lk_task_eqm_items add constraint FKsbv44e7y6emr22lpsg8pb69dp foreign key (application_category_id) references application.lk_application_category;
alter table application.task_eqm add constraint FK3axf7q9rws9mc1xyg6e042e5e foreign key (application_info_id) references application.applications_info;
alter table application.task_eqm_rating_items add constraint FK46rky4qywrfvb8r6h9er27uhe foreign key (task_eqm_id) references application.task_eqm;


INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, application_category_id)
VALUES(1, 'TECHNICAL_NATURE', 'الطبيعة التقنية', 'Technical Nature', 1);

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, application_category_id)
VALUES(2, 'APPLICABILITY', 'قابلية التطبيق', 'Applicability', 1);

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, application_category_id)
VALUES(3, 'INNOVATION', 'الابتكارية', 'Innovation', 1);

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, application_category_id)
VALUES(4, 'TECHNICAL_RESEARCH', 'البحث الفني', 'Technical Research', 1);

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, application_category_id)
VALUES(5, 'SERIOUSNESS', 'الجدة', 'Seriousness', 1);


--  industrial

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, application_category_id)
VALUES(6, 'TECHNICAL_NATURE', 'الطبيعة التقنية', 'Technical Nature', 2);

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, application_category_id)
VALUES(7, 'APPLICABILITY', 'قابلية التطبيق', 'Applicability', 2);

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, application_category_id)
VALUES(8, 'INNOVATION', 'الابتكارية', 'Innovation', 2);

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, application_category_id)
VALUES(9, 'TECHNICAL_RESEARCH', 'البحث الفني', 'Technical Research', 2);

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, application_category_id)
VALUES(10, 'SERIOUSNESS', 'الجدة', 'Seriousness', 2);


--  trademark

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, application_category_id)
VALUES(11, 'TECHNICAL_NATURE', 'الطبيعة التقنية', 'Technical Nature', 5);

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, application_category_id)
VALUES(12, 'APPLICABILITY', 'قابلية التطبيق', 'Applicability', 5);

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, application_category_id)
VALUES(13, 'INNOVATION', 'الابتكارية', 'Innovation', 5);

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, application_category_id)
VALUES(14, 'TECHNICAL_RESEARCH', 'البحث الفني', 'Technical Research', 5);

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, application_category_id)
VALUES(15, 'SERIOUSNESS', 'الجدة', 'Seriousness', 5);


