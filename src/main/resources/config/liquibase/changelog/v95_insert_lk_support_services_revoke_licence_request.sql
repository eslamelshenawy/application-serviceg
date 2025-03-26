INSERT INTO application.lk_support_services (id,created_by_user,created_date,modified_by_user,modified_date, is_deleted, cost,desc_ar, desc_en,name_ar,name_en,code)
VALUES((select (max(id) + 1) from application.lk_support_services),null,null,null,null, 0, 0,'شطب ترخيص', 'Revoke Licence Request', 'شطب ترخيص', 'Revoke Licence Request','REVOKE_LICENCE_REQUEST');


INSERT INTO application.lk_task_eqm_types (id, code, name_ar, name_en)
VALUES ((SELECT max(id) + 1 FROM application.lk_task_eqm_types), 'REVOKE_LICENCE_REQUEST', 'شطب ترخيص', 'Revoke Licence Request');