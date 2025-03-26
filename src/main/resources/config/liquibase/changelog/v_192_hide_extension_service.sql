UPDATE application.lk_support_services s
SET is_deleted = 1
WHERE EXISTS (
    SELECT 1
    FROM application.support_service_application_categories sc
    WHERE sc.support_service_id = s.id
      AND sc.category_id = 1 and s.code = 'EXTENSION'
);