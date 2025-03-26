UPDATE application.lk_support_service_request_status
SET name_ar_external='مقبول من لجنة التظلمات', name_en_external='Accepted Appeal'
WHERE code='ACCEPTED_BY_COMMITTEE';

UPDATE application.lk_support_service_request_status
SET name_ar_external='متظلم لدى لجنة التظلمات', name_en_external='Appeal Committee'
WHERE code ='COMPLAINANT_TO_COMMITTEE';

UPDATE application.lk_support_service_request_status
SET name_ar_external='سداد رسوم التظلم', name_en_external='Pay AppealFees'
WHERE code='PENDING_FEES_COMPLAINT';

UPDATE application.lk_support_service_request_status
SET name_ar_external='متنازل عنه', name_en_external='Waived'
WHERE code='WAIVED';

UPDATE application.lk_support_service_request_status
SET name_en='Rejected by Trademark Management', name_ar_external='مرفوض', name_en_external='Rejected'
WHERE code='REJECTED_BY_MANAGEMENT';

UPDATE application.lk_support_service_request_status
SET name_ar_external='مقبول', name_en_external='Accepted'
WHERE code='TRADEMARK_IMAGE_MODIFIED';

UPDATE application.lk_support_service_request_status
SET name_ar_external='نشر صورة العلامة', name_en_external='Image published'
WHERE code='PUBLISHED_ELECTRONIC';

UPDATE application.lk_support_service_request_status
SET name_ar_external='سداد رسوم النشر', name_en_external='Pay Publishing Fees'
WHERE code='PENDING_IMG_FEES_MOD_PUB';

UPDATE application.lk_support_service_request_status
SET name_ar_external='مقبول', name_en_external='Accepted'
WHERE code='ACCEPTED_MOD_REQUEST';

UPDATE application.lk_support_service_request_status
SET name_ar_external='معاد لمقدم الطلب', name_en_external='Return to Applicant'
WHERE code='ACCEPTED_WITH_MOD';

UPDATE application.lk_support_service_request_status
SET name_ar_external='تحت الدراسة', name_en_external='Under Procedure'
WHERE code='UNDER_REVIEW_AUDITOR';

UPDATE application.lk_support_service_request_status
SET name_ar_external='تحت الدراسة', name_en_external='Under Procedure'
WHERE code='UNDER_REVIEW_SUBJECTIVE';

UPDATE application.lk_support_service_request_status
SET name_ar_external='تحت الدراسة', name_en_external='Under Procedure'
WHERE code='UNDER_REVIEW_VISUAL';

UPDATE application.lk_support_service_request_status
SET name_ar_external='سداد رسوم التقديم', name_en_external='Pay Application Fees'
WHERE code='PENDING_IMG_FEES_MOD_REQ';

UPDATE application.lk_support_service_request_status
SET name_ar_external='معلق ', name_en_external=' pending'
WHERE code='PENDING';
