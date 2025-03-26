INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select (max(id) + 1) from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تحت المراجعة من المدقق الشكلى', 'Under review by an checker auditor', 'UNDER_REVIEW_BY_AN_CHECKER_AUDITOR', 'تحت الدراسة', 'Under Process');
