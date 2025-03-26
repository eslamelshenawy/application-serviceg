INSERT INTO application.lk_applicant_category
(id, created_by_user, created_date, modified_by_user, saip_code, modified_date, is_deleted, applicant_category_name_ar, applicant_category_name_en)
VALUES((SELECT max(id)+1 FROM application.lk_applicant_category), NULL, NULL, NULL, 'LEGAL_REPRESENTATIVE', NULL, 0, 'ممثل قانوني', 'Legal Representative');
