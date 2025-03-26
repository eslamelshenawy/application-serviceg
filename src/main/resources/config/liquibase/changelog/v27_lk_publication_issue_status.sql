CREATE TABLE application.lk_publication_issue_status (
                                                         id int4 NOT NULL,
                                                         name_ar varchar(255) NULL,
                                                         name_en varchar(255) NULL,
                                                         code varchar(255) NULL,
                                                         created_date timestamp NULL,
                                                         is_deleted int4 NOT NULL,
                                                         CONSTRAINT lk_publication_issue_status_pkey PRIMARY KEY (id)
);
------------------------------------
INSERT INTO application.lk_publication_issue_status
(id, name_ar, name_en, code, created_date, is_deleted)
VALUES(1, 'بإنتظار الاجراء', 'Awaiting action', 'AWAITING_ACTION', NULL, 0);
INSERT INTO application.lk_publication_issue_status
(id, name_ar, name_en, code, created_date, is_deleted)
VALUES(2, 'بإنتظار تعديل XML', 'Awaiting for update XML', 'AWAITING_FOR_UPDATE_XML', NULL, 0);
INSERT INTO application.lk_publication_issue_status
(id, name_ar, name_en, code, created_date, is_deleted)
VALUES(3, 'قبول', 'acceptance', 'ACCEPTANCE', NULL, 0);
---------------------------------
ALTER TABLE application.publication_issue ADD COLUMN IF NOT EXISTS lk_publication_issue_status_id int4 NULL;
ALTER TABLE application.publication_issue ADD CONSTRAINT FK_PUBLICATION_ISSUE_ON_LK_PUBLICATION_ISSUE_STATUS FOREIGN KEY (lk_publication_issue_status_id)
    REFERENCES application.lk_publication_issue_status(id);
