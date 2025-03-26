UPDATE application.lk_application_status
SET  ips_status_desc_ar='تحت الدراسة من مسؤول الفحص الشكلي', ips_status_desc_en='Under study by the formal examination official'
WHERE id=5;
UPDATE application.lk_application_status
SET  ips_status_desc_ar='تحت الدراسة من الفحص الموضوعي', ips_status_desc_en='Under consideration by the objective examiner'
WHERE id=25;
UPDATE application.lk_application_status
SET   ips_status_desc_ar_external='معاد لأستكمال البيانات ', ips_status_desc_en_external='Return to complete the data'
WHERE id=23;
UPDATE application.lk_application_status
SET  ips_status_desc_ar='مرفوض من قبل إدارة العلامات التجارية', ips_status_desc_en='Rejected by the Trademark Administration', ips_status_desc_ar_external='رفض تسجيل العلامة التجارية', ips_status_desc_en_external='Refusal to register the trademark'
WHERE id=24;
UPDATE application.lk_application_status
SET  ips_status_desc_ar='معترض لدى إدارة العلامات التجارية', ips_status_desc_en='Objector to the Trademark Administration', ips_status_desc_ar_external='معترض على قبول تسجيل العلامة التجارية', ips_status_desc_en_external='Objecting to the acceptance of the trademark registration'
WHERE id=19;

INSERT INTO application.lk_application_status
(id, is_deleted,ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select max(id) + 1 from application.lk_application_status),0, 'تحت الدراسة من مسئول التصنيفات ', 'Under Process by the classifications official', 'UNDER_PROCESS_BY_CLASSIFICATION_OFFICIAL', 'تحت الدراسة', 'Under Process');

INSERT INTO application.lk_application_status
(id, is_deleted,ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select max(id) + 1 from application.lk_application_status),0, 'معاد لمسئول التصنيفات ', 'Return To the classifications official', 'RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL', 'تحت الدراسة', 'Under Process');

INSERT INTO application.lk_application_status
(id, is_deleted,ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select max(id) + 1 from application.lk_application_status),0, 'تحت المراجعة من المدقق الموضوعي ', 'Under review by an objective auditor', 'UNDER_REVIEW_BY_AN_OBJECTIVE_AUDITOR', 'تحت الدراسة', 'Under Process');
INSERT INTO application.lk_application_status
(id, is_deleted,ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select max(id) + 1 from application.lk_application_status),0, 'معاد لمقدم الطلب لقبول الطلب باشتراط', 'The applicant is invited to accept the application with a condition', 'THE_APPLICANT_INVITED_TO_ACCEPT_APPLICATION_WITH_CONDITION', 'معاد لقبول الاشتراط', 'Return to accept the condition');

INSERT INTO application.lk_application_status
(id, is_deleted,ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select max(id) + 1 from application.lk_application_status),0, 'قبول بشرط التعديل', 'Acceptance subject to modification', 'ACCEPTANCE_SUBJECT_MODIFICATION', 'معاد لمقدم الطلب', 'Return to Applicant');

INSERT INTO application.lk_application_status
(id,is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select max(id) + 1 from application.lk_application_status),0, 'مقبول من قبل إدارة العلامات التجارية', 'Accepted by Trademark Administration', 'ACCEPTED_TRADEMARK_ADMINISTRATION', 'قبول تسجيل العلامة التجارية', 'Acceptance of trademark registration');

INSERT INTO application.lk_application_status
(id,is_deleted,ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select max(id) + 1 from application.lk_application_status),0, 'تم النشر إلكترونياً', 'Published Electronically', 'PUBLISHED_ELECTRONICALLY_TR', 'نشر قبول تسجيل العلامة التجارية  ', 'Publication of acceptance of trademark registration');

INSERT INTO application.lk_application_status
(id,is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select max(id) + 1 from application.lk_application_status),0, 'مرفوض بسبب قبول الاعتراضً', 'Rejected because the objection was accepted', 'REJECTED_BECAUSE_THE_OPPOSITION_ACCEPTED', 'رفض تسجيل العلامة التجارية بسبب قبول الاعتراض ', 'Refusal to register the trademark due to the acceptance of the objection');

INSERT INTO application.lk_application_status
(id,is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select max(id) + 1 from application.lk_application_status),0, 'بأنتظار سداد رسوم تسجيل العلامة التجارية', 'Waiting to pay the trademark registration fees', 'WAITING_TO_PAY_TRADEMARK_REGISTRATION_FEES', 'سداد رسوم تقديم الطلب تسجيل العلامة التجارية', 'Pay the trademark registration application fee');

INSERT INTO application.lk_application_status
(id,is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select max(id) + 1 from application.lk_application_status),0, 'العلامة التجارية مسجلة', 'The trademark is registered', 'THE_TRADEMARK_IS_REGISTERED', 'العلامة التجارية مسجلة', 'The trademark is registered');


INSERT INTO application.lk_application_status
(id,is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select max(id) + 1 from application.lk_application_status),0, 'شطب من مالك العلامة ', 'Cancellation of the trademark owner', 'REVOKED_OF_THE_TRADEMARK_OWNER', 'شطب من مالك العلامة', 'Cancellation of the trademark owner');

INSERT INTO application.lk_application_status
(id,is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select max(id) + 1 from application.lk_application_status),0, 'شطب بموجب حكم قضائي ', 'Cancellation pursuant to a court rulingd', 'REVOKED_PURSUANT_TO_COURT_RULING', 'شطب بموجب حكم قضائي ', 'Cancellation pursuant to a court ruling');

INSERT INTO application.lk_application_status
(id, is_deleted,ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select max(id) + 1 from application.lk_application_status),0, 'شطب لعدم تجديد التسجيل ', 'Cancellation for non-renewal of registration', 'REVOKED_FOR_NON_RENEWAL_OF_REGISTRATION', 'شطب لعدم تجديد الحماية', 'Cancellation for non-renewal of protection');