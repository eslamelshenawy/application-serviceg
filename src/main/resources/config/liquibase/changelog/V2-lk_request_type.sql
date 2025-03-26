--liquibase formatted sql
-- changeset application-service:V2_lk_request_type.sql
INSERT INTO application.lk_request_type(id, created_by_user, created_date, modified_by_user, modified_date,is_deleted,code,is_approval_required, is_internal, "name",name_en,saip_code)
VALUES (312,NULL,Null,Null,NULL,0,Null,NULL,Null, N'تعديل ثاني', 'APPLICATION UPDATE 2', N'APPLICATION_UPDATE_2');

INSERT INTO application.lk_request_type(id, created_by_user, created_date, modified_by_user, modified_date,is_deleted,code,is_approval_required, is_internal, "name",name_en,saip_code)
VALUES (313,NULL,Null,Null,NULL,0,Null,NULL,Null, N'تعديل الاول', 'APPLICATION UPDATE 1', N'APPLICATION_UPDATE_1');

INSERT INTO application.lk_request_type(id, created_by_user, created_date, modified_by_user, modified_date,is_deleted,code,is_approval_required, is_internal, "name",name_en,saip_code)
VALUES (314,NULL,Null,Null,NULL,0,Null,NULL,Null, N'اضافة طلب لطلب', 'ADD APPLICATION TO APPLICATION', N'ADD_APPLICATION_TO_APPLICATION');

INSERT INTO application.lk_request_type(id, created_by_user, created_date, modified_by_user, modified_date,is_deleted,code,is_approval_required, is_internal, "name",name_en,saip_code)
VALUES (315,NULL,Null,Null,NULL,0,Null,NULL,Null, N'تكاليف نشر ورقة', 'PAPER', N'PAPER');



