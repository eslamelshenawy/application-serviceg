INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted,
 ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
SELECT
    (MAX(id) + 1),
    NULL, -- created_by_user
    NULL, -- created_date
    NULL, -- modified_by_user
    NULL, -- modified_date
    0,    -- is_deleted
    'الرد علي تقرير الفحص الشكلي مع الاعتراض', -- ips_status_desc_ar
    'Reply To Formal Process With Opposition', -- ips_status_desc_en
    'REPLY_TO_FORMAL_PROCESS_WITH_OPPOSITION_PAT', -- code
    'تحت الفحص الشكلي', -- ips_status_desc_ar_external
    'Under Formal Process', -- ips_status_desc_en_external
    (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PATENT') -- application_category_id
FROM application.lk_application_status;

