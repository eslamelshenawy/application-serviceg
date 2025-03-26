UPDATE application.lk_application_status
SET ips_status_desc_ar_external='تحت الدراسة'
WHERE code='UNDER_PROCESS_BY_CLASSIFICATION_OFFICIAL' and application_category_id=1;
