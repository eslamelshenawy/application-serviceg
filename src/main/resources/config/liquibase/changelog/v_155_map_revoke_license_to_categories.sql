delete from application.support_service_application_categories
where support_service_id in (select id from application.lk_support_services lss where code = 'OPPOSITION_REVOKE_LICENCE_REQUEST');


INSERT INTO application.support_service_application_categories
(category_id, support_service_id)
VALUES(1, (select id from application.lk_support_services lss where code = 'OPPOSITION_REVOKE_LICENCE_REQUEST' limit 1 offset 0));

INSERT INTO application.support_service_application_categories
(category_id, support_service_id)
VALUES(2, (select id from application.lk_support_services lss where code = 'OPPOSITION_REVOKE_LICENCE_REQUEST' limit 1 offset 0));

INSERT INTO application.support_service_application_categories
(category_id, support_service_id)
VALUES(5, (select id from application.lk_support_services lss where code = 'OPPOSITION_REVOKE_LICENCE_REQUEST' limit 1 offset 0));
