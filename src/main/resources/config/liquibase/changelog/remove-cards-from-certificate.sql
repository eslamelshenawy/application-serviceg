DELETE FROM application.certificate_types_application_categories
WHERE lk_certificate_type_id = (
    SELECT lct.id
    FROM application.lk_certificate_types lct
    WHERE lct.code = 'LICENSE_REGISTRATION_CERTIFICATE'
) AND lk_category_id = 5;

DELETE FROM application.certificate_types_application_categories
WHERE lk_certificate_type_id = (
    SELECT id
    FROM application.lk_certificate_types lct
    WHERE lct.code = 'LICENSE_CANCELLATION_CERTIFICATE'
) AND lk_category_id = 5;
