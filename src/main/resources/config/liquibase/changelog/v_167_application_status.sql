UPDATE application.lk_application_status
SET ips_status_desc_ar_external = 'تحت الدراسة شكلياً', ips_status_desc_en_external = 'Under formal study'
WHERE code = 'RETURN_TO_THE_FORMAL_EXAMINER' and application_category_id = 1