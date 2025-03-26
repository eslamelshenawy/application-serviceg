update application.lk_application_status
set ips_status_desc_ar = 'منسحب'
where ips_status_desc_ar = 'متنازل عنه';




INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code)
VALUES((SELECT MAX(id) + 1 FROM application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'متخلي عنه', 'Abandoned', 'ABANDONED');