--liquibase formatted sql
-- changeset application-service:v80_eqm_type_and_item.sql


-- add item value type
ALTER TABLE application.lk_task_eqm_items ADD rating_value_type varchar(255) NULL;


CREATE TABLE application.lk_task_eqm_types (
                                               id int4 NOT NULL,
                                               code varchar(255) NULL,
                                               name_ar varchar(255) NULL,
                                               name_en varchar(255) NULL,
                                               CONSTRAINT lk_task_eqm_types_pkey PRIMARY KEY (id),
                                               CONSTRAINT lk_task_eqm_types_un UNIQUE (code)
);

CREATE TABLE application.task_eqm_type_items (
                                                 lk_task_eqm_type_id int4 NOT NULL,
                                                 lk_task_eqm_item_id int4 NOT NULL,
                                                 CONSTRAINT task_eqm_type_items_pk PRIMARY KEY (lk_task_eqm_type_id, lk_task_eqm_item_id),
                                                 CONSTRAINT fkgyk6vcqi6iv5ou5h0yi6a9qhq FOREIGN KEY (lk_task_eqm_type_id) REFERENCES application.lk_task_eqm_types(id),
                                                 CONSTRAINT fkl6vt3wf988v58fs0g3f23yrcm FOREIGN KEY (lk_task_eqm_item_id) REFERENCES application.lk_task_eqm_items(id)
);




-- create eqm types
INSERT INTO application.lk_task_eqm_types
(id, code, name_ar, name_en)
VALUES(1, 'PATENT', 'براءات الاختراع', 'Patent');
INSERT INTO application.lk_task_eqm_types
(id, code, name_ar, name_en)
VALUES(2, 'INDUSTRIAL_DESIGN', 'التصماميم الصناعيه', 'Industrial Design');
INSERT INTO application.lk_task_eqm_types
(id, code, name_ar, name_en)
VALUES(3, 'TRADEMARK', 'العلامات التجاريه', 'Trademark');
INSERT INTO application.lk_task_eqm_types
(id, code, name_ar, name_en)
VALUES(4, 'TRADEMARK_AGENCY', 'وكاله العلامات التجاريه', 'Trademark Agency');



ALTER TABLE application.lk_task_eqm_items DROP CONSTRAINT if exists fksbv44e7y6emr22lpsg8pb69dp;
ALTER TABLE application.lk_task_eqm_items DROP COLUMN if exists application_category_id;
truncate  table application.lk_task_eqm_items cascade;


INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, rating_value_type)
VALUES(1, 'TECHNICAL_NATURE', 'الطبيعة التقنية', 'Technical Nature', 'INTEGER');
INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, rating_value_type)
VALUES(2, 'APPLICABILITY', 'قابلية التطبيق', 'Applicability', 'INTEGER');
INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, rating_value_type)
VALUES(3, 'INNOVATION', 'الابتكارية', 'Innovation', 'INTEGER');
INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, rating_value_type)
VALUES(4, 'TECHNICAL_RESEARCH', 'البحث الفني', 'Technical Research', 'INTEGER');
INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, rating_value_type)
VALUES(5, 'SERIOUSNESS', 'الجدة', 'Seriousness', 'INTEGER');

INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, rating_value_type)
VALUES(6, 'TM_AGENCY_ATTACHMENTS', 'المرفقات التى تم تقديمها لتدعيم الطلب', 'Attachments ?', 'BOOLEAN');
INSERT INTO application.lk_task_eqm_items
(id, code, name_ar, name_en, rating_value_type)
VALUES(7, 'TM_AGENCY_INFO', 'هل بيانات مقدم الطلب كامله و واضحه ؟', 'Is applicant information clear ?', 'BOOLEAN');



-- insert agency
INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(1, 1);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(1, 2);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(1, 3);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(1, 4);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(1, 5);


INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(2, 1);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(2, 2);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(2, 3);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(2, 4);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(2, 5);



INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(3, 1);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(3, 2);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(3, 3);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(3, 4);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(3, 5);



INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(4, 6);

INSERT INTO application.task_eqm_type_items
(lk_task_eqm_type_id, lk_task_eqm_item_id)
VALUES(4, 7);
