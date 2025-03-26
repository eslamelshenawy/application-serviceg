INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code)
VALUES((SELECT MAX(id) + 1 FROM application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'رفض القبول بشرط', 'Reject Acceptance With Condition', 'REJECT_ACCEPTANCE_WITH_CONDITION');
