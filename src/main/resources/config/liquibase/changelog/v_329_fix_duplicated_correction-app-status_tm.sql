UPDATE application.lk_application_status
SET ips_status_desc_ar_external='معاد لمقدم الطلب'
WHERE code='INVITATION_FOR_OBJECTIVE_CORRECTION'
AND application_category_id = (select id from application.lk_application_category where saip_code = 'TRADEMARK');