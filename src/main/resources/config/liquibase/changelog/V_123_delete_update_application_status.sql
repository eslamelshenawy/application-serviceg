DELETE FROM application.lk_application_status
WHERE id=35;

UPDATE application.lk_application_status
SET  ips_status_desc_ar='معادة لمسؤول التصنيف', ips_status_desc_en='Returned to the classification officer', ips_status_desc_ar_external='تحت الدراسة', ips_status_desc_en_external='Under Process'
WHERE id=10;