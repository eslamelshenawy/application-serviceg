UPDATE application.lk_certificate_status
SET code='PENDING', name_ar='سداد رسوم التقديم ', name_en='Pay the application fees'
WHERE id=3;

-------------------------------

UPDATE application.lk_support_service_request_status
SET code='DRAFT', name_ar=' سداد رسوم التقديم', name_en='Pay the application fees'
WHERE id=1;
UPDATE application.lk_support_service_request_status
SET code='UNDER_PROCEDURE', name_ar='تحت الدراسة', name_en='Under Procedure'
WHERE id=2;
UPDATE application.lk_support_service_request_status
SET code='REQUEST_CORRECTION', name_ar='معاد لمقدم الطلب', name_en='Return to the applicant'
WHERE id=3;
UPDATE application.lk_support_service_request_status
SET code='APPROVED', name_ar=' سداد رسوم النشر', name_en='Pay publication fees'
WHERE id=4;

------------------------

UPDATE application.lk_application_status
SET  ips_status_desc_ar='مكتمل', ips_status_desc_en='Completed'
WHERE id=26;
UPDATE application.lk_application_status
SET  ips_status_desc_ar='معاد لمقدم الطلب', ips_status_desc_en='Return to the applicant'
WHERE id=9;
UPDATE application.lk_application_status
SET  ips_status_desc_ar='سداد رسوم النشر', ips_status_desc_en='Pay publication fees'
WHERE id=14;
UPDATE application.lk_application_status
SET  ips_status_desc_ar='مكتمل', ips_status_desc_en='Completed'
WHERE id=15;
UPDATE application.lk_application_status
SET  ips_status_desc_ar='مرفوض', ips_status_desc_en='Rejected'
WHERE id=8;
UPDATE application.lk_application_status
SET  ips_status_desc_ar='مرفوض', ips_status_desc_en='Rejected'
WHERE id=24;
UPDATE application.lk_application_status
SET  ips_status_desc_ar='معاد لمقدم الطلب', ips_status_desc_en='Return to the applicant'
WHERE id=23;
UPDATE application.lk_application_status
SET  ips_status_desc_ar='تحت الدراسة', ips_status_desc_en='Under Procedure'
WHERE id=25;
UPDATE application.lk_application_status
SET  ips_status_desc_ar='تحت الدراسة', ips_status_desc_en='Under Procedure'
WHERE id=5;
-----------------------------------------------------------------------------------------------------
INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES((select (max(id) + 1) from application.lk_support_service_request_status), 'LICENSED', 'تم الترخيص ', ' licensed', 'تم الترخيص ', ' licensed');

INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES((select (max(id) + 1) from application.lk_support_service_request_status), 'WAIVED', 'متنازل عنه', ' Waived', 'متنازل عنه ', ' Waived');

INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES((select (max(id) + 1) from application.lk_support_service_request_status), 'RENEWED', 'تم التجديد ', ' renewed', 'تم التجديد ', ' renewed');

INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES((select (max(id) + 1) from application.lk_support_service_request_status), 'TRADMARK_REVOKED', 'تم شطب العلامة ', ' tradmark revoked', 'تم شطب العلامة ', ' tradmark revoked');

INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES((select (max(id) + 1) from application.lk_support_service_request_status), 'DENIED_AGENCY', 'وكالة مرفوضة ', ' Denied agency', 'وكالة مرفوضة ', ' Denied agency');

INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES((select (max(id) + 1) from application.lk_support_service_request_status), 'TRANSFERRED_OWNERSHIP', 'تم نقل الملكية ', 'transferred Ownership', 'تم نقل الملكية ', ' transferred Ownership');
