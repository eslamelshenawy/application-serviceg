INSERT INTO application.support_service_type_status (lk_support_service_type_id, lk_support_service_status_id)
SELECT
    (SELECT id
     FROM application.lk_support_services
     WHERE code = 'LICENSING_REGISTRATION' AND name_ar LIKE '%قيد الترخيص%'),
    (SELECT id
     FROM application.lk_support_service_request_status
     WHERE code = 'WAIVED');