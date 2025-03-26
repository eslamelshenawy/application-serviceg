--liquibase formatted sql
-- changeset application-service:V2_lk_request_type.sql

INSERT INTO application.lk_request_type( id,created_by_user, created_date, modified_by_user, modified_date,is_deleted,code,is_approval_required, is_internal, "name",name_en,saip_code)
VALUES (316,NULL,Null,Null,NULL,0,Null,NULL,Null, N'طلب تخلي', 'RELINQUISHMENT', N'RELINQUISHMENT');

INSERT INTO application.lk_request_type( id,created_by_user, created_date, modified_by_user, modified_date,is_deleted,code,is_approval_required, is_internal, "name",name_en,saip_code)
VALUES (317,NULL,Null,Null,NULL,0,Null,NULL,Null, N'طلب الالتماس لاستعادة', 'PETITION FOR RECOVERY', N'PETITION_FOR_RECOVERY');



INSERT INTO application.lk_request_type( id,created_by_user, created_date, modified_by_user, modified_date,is_deleted,code,is_approval_required, is_internal, "name",name_en,saip_code)
VALUES (318,NULL,Null,Null,NULL,0,Null,NULL,Null, N'الأشكال الورقية', 'PAPER SHAPES', N'PAPER_SHAPES');

INSERT INTO application.lk_request_type( id,created_by_user, created_date, modified_by_user, modified_date,is_deleted,code,is_approval_required, is_internal, "name",name_en,saip_code)
VALUES (319,NULL,Null,Null,NULL,0,Null,NULL,Null, N'مطالبات الأوراق', 'PAPER CLAIMS', N'PAPER_CLAIMS');




INSERT INTO application.lk_request_type( id,created_by_user, created_date, modified_by_user, modified_date,is_deleted,code,is_approval_required, is_internal, "name",name_en,saip_code)
VALUES (320,NULL,Null,Null,NULL,0,Null,NULL,Null, N'تعديل لاول لفحص موضوعي', 'SUBS UPDATE 1', N'SUBS_UPDATE_1');

INSERT INTO application.lk_request_type(id, created_by_user, created_date, modified_by_user, modified_date,is_deleted,code,is_approval_required, is_internal, "name",name_en,saip_code)
VALUES (321,NULL,Null,Null,NULL,0,Null,NULL,Null, N'تعديل ثاني لفحص موضوعي', 'SUBS UPDATE 2', N'SUBS_UPDATE_2');



INSERT INTO application.lk_request_type(id, created_by_user, created_date, modified_by_user, modified_date,is_deleted,code,is_approval_required, is_internal, "name",name_en,saip_code)
VALUES (322,NULL,Null,Null,NULL,0,Null,NULL,Null, N'تعديل الأولي', 'INITIAL UPDATE', N'INITIAL_UPDATE');






