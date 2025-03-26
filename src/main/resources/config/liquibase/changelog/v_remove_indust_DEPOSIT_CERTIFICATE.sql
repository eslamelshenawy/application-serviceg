-- Auto-generated SQL script #202409041126
DELETE FROM application.certificate_types_application_categories
WHERE lk_certificate_type_id=(SELECT id FROM application.lk_certificate_types WHERE code='DEPOSIT_CERTIFICATE')
  AND lk_category_id=(SELECT id FROM application.lk_application_category WHERE saip_code='INDUSTRIAL_DESIGN');