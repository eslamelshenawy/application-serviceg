UPDATE application.lk_application_status
SET ips_status_desc_ar='متظلم لدى لجنة التظلمات',
    ips_status_desc_ar_external='متظلم لدى لجنة التظلمات'
where code = 'COMPLAINANT_TO_THE_GRIEVANCE_COMMITTEE';

ALTER TABLE application.lk_tm_agency_request_status ADD column if not exists name_ar_external varchar(255) NULL;

ALTER TABLE application.lk_tm_agency_request_status ADD column if not exists name_en_external varchar(255) NULL;


UPDATE application.lk_tm_agency_request_status
SET  name_ar='جديد', name_en='New', name_ar_external='جديد', name_en_external='New'
WHERE code='NEW';
UPDATE application.lk_tm_agency_request_status
SET name_ar='تحت الدراسة', name_en='Under Procedure', name_ar_external='تحت الدراسة', name_en_external='Under Procedure'
WHERE code='UNDER_PROCEDURE';
UPDATE application.lk_tm_agency_request_status
SET name_ar='معاد لمقدم الطلب', name_en='Request Correction', name_ar_external='معاد لأستكمال البيانات', name_en_external='Return to complete the data'
WHERE code='REQUEST_CORRECTION';
UPDATE application.lk_tm_agency_request_status
SET name_ar='وكالة مرفوضة', name_en='Rejected', name_ar_external='مرفوض', name_en_external='Rejected'
WHERE code='REJECTED';
UPDATE application.lk_tm_agency_request_status
SET name_ar='وكالة قائمة', name_en='Accepted', name_ar_external='مقبول', name_en_external='Accepted'
WHERE code='ACCEPTED';
UPDATE application.lk_tm_agency_request_status
SET name_ar='وكاله منتهية', name_en='Expired', name_ar_external='منتهيه', name_en_external='Expired'
where code='EXPIRED';

