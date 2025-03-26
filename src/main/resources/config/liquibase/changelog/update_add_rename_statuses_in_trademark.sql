INSERT INTO application.lk_support_service_request_status
    (id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES ((select (max(id) + 1) from application.lk_support_service_request_status), 'APPEAL_REQUEST_CORRECTION',
        'معاد لمقدم الطلب لاستكمال بيانات التظلم',
        'The applicant is invited to complete the appeal information',
        'معاد لإستكمال البيانات ', 'Return to the complete data');

UPDATE application.lk_application_status
SET ips_status_desc_ar='متظلم امام لجنة التظلمات'
  , ips_status_desc_en='Complainant before the Grievances Committee'
  , ips_status_desc_ar_external='التظلم أمام لجنة التظلمات'
  , ips_status_desc_en_external='Grievance before the Grievances Committee'
WHERE id = 17
  and code = 'COMPLAINANT_TO_THE_GRIEVANCE_COMMITTEE';

UPDATE application.lk_application_status
SET ips_status_desc_ar='بانتظار سداد رسوم النشر',
    ips_status_desc_en='Pending payment of publication fees'
WHERE code = 'PUBLICATION_FEES_ARE_PENDING';


UPDATE application.lk_application_status
SET ips_status_desc_ar='تحت الدراسة من الفاحص الموضوعي'
WHERE code = 'UNDER_OBJECTIVE_PROCESS';

