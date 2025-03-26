INSERT INTO application.lk_application_status
(id, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select (max(id) + 1) from application.lk_application_status), 0, 'مرفوض بسبب حكم قضائي', 'Rejected due to a court ruling', 'REJECTED_DUE_TO_COURT_RULING', 'مرفوض بسبب حكم قضائي', 'Rejected due to a court ruling');

INSERT INTO application.lk_application_status
(id, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select (max(id) + 1) from application.lk_application_status), 0, 'مقبول من قبل لجنة التظلمات', 'Accepted by the Grievances Committee', 'ACCEPTED_BY_THE_GRIEVANCES_COMMITTEE', 'مقبول من قبل لجنة التظلمات', 'Accepted by the Grievances Committee');

INSERT INTO application.lk_application_status
(id, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select (max(id) + 1) from application.lk_application_status), 0, 'مرفوض من قبل لجنة التظلمات', 'Rejected by the Grievances Committee', 'REJECTED_BY_THE_GRIEVANCES_COMMITTEE', 'مرفوض من قبل لجنة التظلمات', 'Rejected by the Grievances Committee');

INSERT INTO application.lk_application_status
(id, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select (max(id) + 1) from application.lk_application_status), 0, 'تحت الدراسة من مسئول التصنيفات ', 'Under study by the classifications official', 'UNDER_STUDY_BY_THE_CLASSIFICATIONS_OFFICIAL', 'تحت الدراسة', 'Under Process');

INSERT INTO application.lk_application_status
(id, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select (max(id) + 1) from application.lk_application_status), 0, 'معاد لمقدم الطلب لقبول التعديلات الشكلية', 'The applicant is invited to accept the formal amendments', 'THE_APPLICANT_IS_INVITED_TO_ACCEPT_FORMAL_AMENDMENTS', 'معاد لقبول التعديلات', 'Deadline for accepting amendments');

INSERT INTO application.lk_application_status
(id, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external)
VALUES((select (max(id) + 1) from application.lk_application_status), 0, 'مغلق لعدم سداد رسوم التقديم', 'Closed due to non-payment of application fees', 'CLOSED_DUE_TO_NON_PAYMENT_OF_APPLICATION_FEES', 'مغلق لعدم سداد رسوم التقديم', 'Closed due to non-payment of application fees');