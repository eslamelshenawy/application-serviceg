UPDATE application.lk_application_status
SET ips_status_desc_ar='بانتظار إرفاق وثائق الأولوية', ips_status_desc_en='Waiting Priority Documents Attachment',
ips_status_desc_ar_external='بانتظار إرفاق وثائق الأولوية', ips_status_desc_en_external='Waiting Priority Documents Attachment'
WHERE
code='IC_WAITING_DOCUMENTS_ATTACHMENT';