update application.lk_application_status SET
ips_status_desc_ar = 'مرفوض من قبل إدارة العلامات التجارية',
ips_status_desc_en = 'rejected from trademark management',
ips_status_desc_ar_external = 'رفض تسجيل العلامة التجارية',
ips_status_desc_en_external = 'trademark registration rejected'
where code = 'OBJECTIVE_REJECTION' and application_category_id = 5;