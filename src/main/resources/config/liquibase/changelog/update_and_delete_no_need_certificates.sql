UPDATE application.lk_certificate_types
SET code='PATENT_ISSUE_CERTIFICATE'
WHERE id=14 and name_en='Request to reissue a protection document';

DELETE FROM application.certificate_types_application_categories
WHERE lk_certificate_type_id=2 AND lk_category_id=1;

DELETE FROM application.certificate_types_application_categories
WHERE lk_certificate_type_id=9 AND lk_category_id=1;

DELETE FROM application.certificate_types_application_categories
WHERE lk_certificate_type_id=10 AND lk_category_id=1;

