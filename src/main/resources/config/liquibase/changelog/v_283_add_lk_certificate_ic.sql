INSERT INTO application.lk_certificate_types
(id, code, name_ar, name_en, enabled)
VALUES((select max(id) + 1 from application.lk_certificate_types), 'INTEGRATED_CIRCUITS_ISSUE_CERTIFICATE', 'شهادة دارات متكامله', 'Integrated circuits issue certificate', true);
