DELETE FROM application.support_service_type_status
WHERE lk_support_service_type_id = (
    SELECT id
    FROM application.lk_support_services
    WHERE code = 'LICENSING_REGISTRATION' AND name_ar LIKE '%قيد الترخيص%'
)
AND lk_support_service_status_id = (
    SELECT id
    FROM application.lk_support_service_request_status
    WHERE code = 'DRAFT'
);