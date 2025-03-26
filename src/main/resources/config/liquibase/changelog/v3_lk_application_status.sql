UPDATE application.lk_application_status
SET ips_status_desc_en= N'Under formal process',code='UNDER_FORMAL_PROCESS',ips_status_desc_ar= N'تحت الاجراء الشكلي'
WHERE id=5;
UPDATE application.lk_application_status
SET ips_status_desc_en= N'Formal rejection',code='FORMAL_REJECTION',ips_status_desc_ar= N'رفض شكلي'
WHERE id=8;

INSERT INTO application.lk_application_status (id,is_deleted,ips_status_desc_ar,ips_status_desc_en,code)
VALUES (24,0,N'رفض موضوعي', N'Objective rejection','OBJECTIVE_REJECTION');
INSERT INTO application.lk_application_status (id,is_deleted,ips_status_desc_ar,ips_status_desc_en,code)
VALUES (25,0,N'تحت الاجراء الموضوعي', N'Under objective process','UNDER_OBJECTIVE_PROCESS');
