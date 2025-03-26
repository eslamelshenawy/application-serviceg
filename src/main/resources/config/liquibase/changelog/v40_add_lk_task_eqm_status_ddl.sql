--liquibase formatted sql
-- changeset application-service:v40_add_lk_task_eqm_status_ddl.sql
CREATE TABLE application.lk_task_eqm_status (
                                                         id int4 NOT NULL,
                                                         name_ar varchar(255) NULL,
                                                         name_en varchar(255) NULL,
                                                         code varchar(255) NULL,
                                                         created_date timestamp NULL,
                                                         is_deleted int4 NOT NULL,
                                                         CONSTRAINT lk_task_eqm_status_pkey PRIMARY KEY (id)
);
------------------------------------ insert into lk_task_eqm_status ---------------------------
INSERT INTO application.lk_task_eqm_status (id, name_ar, name_en, code, created_date, is_deleted)
VALUES (1, 'قيد اللجنة', 'In Committee', 'IN_COMMITTEE', NULL, 0);

INSERT INTO application.lk_task_eqm_status (id, name_ar, name_en, code, created_date, is_deleted)
VALUES (2, 'بالانتظار', 'Pending', 'PENDING', NULL, 0);

INSERT INTO application.lk_task_eqm_status (id, name_ar, name_en, code, created_date, is_deleted)
VALUES (3, 'معترض', 'Objected', 'OBJECTED', NULL, 0);

INSERT INTO application.lk_task_eqm_status (id, name_ar, name_en, code, created_date, is_deleted)
VALUES (4, 'مقبول', 'Accepted', 'ACCEPTED', NULL, 0);

------------------------------------ insert into lk_application_status ---------------------------

INSERT INTO application.lk_application_status (id,created_by_user,created_date,modified_by_user,modified_date,is_deleted,ips_status_desc_ar,ips_status_desc_en,code) VALUES
    ((select (max(id) + 1) from application.lk_application_status) ,NULL,NULL,NULL,NULL,0,'قيد اللجنة','In Committee', 'IN_COMMITTEE');

---------------------------------
ALTER TABLE application.task_eqm ADD COLUMN IF NOT EXISTS lk_task_eqm_status_id int4 NULL;
ALTER TABLE application.task_eqm ADD CONSTRAINT FK_TASK_EQM_ON_LK_TASK_EQM_STATUS FOREIGN KEY (lk_task_eqm_status_id)
    REFERENCES application.lk_task_eqm_status(id);
