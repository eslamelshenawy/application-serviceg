INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES((select max(id + 1) from application.lk_support_service_request_status), 'REJECTED_BY_APPEAL_COMMITTEE', 'مرفوض من قبل لجنة التظلمات', 'Rejected From Appeal Committee', 'مرفوض من قبل لجنة التظلمات', 'Rejected From Appeal Committee');


INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES((select max(id + 1) from application.lk_support_service_request_status), 'APPEAL_RETURNED_TO_DEPARTMENT', 'معاد للادارة المختصة', 'Returned To Department', 'متظلم لدي لجنة التظلمات', 'Complainant to Complaints Committee');
