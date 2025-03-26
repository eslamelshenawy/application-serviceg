INSERT INTO application.certificate_types_application_categories
(lk_certificate_type_id, lk_category_id)
VALUES((select lct.id from application.lk_certificate_types lct where lct.code = 'LICENSE_REGISTRATION_CERTIFICATE'), 5);

INSERT INTO application.certificate_types_application_categories
(lk_certificate_type_id, lk_category_id)
VALUES((select id from application.lk_certificate_types lct where lct.code = 'LICENSE_CANCELLATION_CERTIFICATE'), 5);
