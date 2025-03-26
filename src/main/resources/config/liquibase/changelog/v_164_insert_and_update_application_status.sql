update application.lk_application_status
set ips_status_desc_ar = 'تحت الفحص الشكلي', ips_status_desc_ar_external = 'تحت الدراسة شكلياً', created_by_user = 0
where code = 'UNDER_FORMAL_PROCESS' and application_category_id = 1;


update application.lk_application_status
set ips_status_desc_ar = 'تحت التدقيق الشكلي', ips_status_desc_ar_external = 'تحت الدراسة شكلياً', created_by_user = 0
where code = 'UNDER_REVIEW_BY_AN_CHECKER_AUDITOR' and application_category_id = 1;


update application.lk_application_status
set ips_status_desc_ar = 'انتظار فحص شكلي', created_by_user = 0
where code = 'NEW' and application_category_id = 1;


update application.lk_application_status
set ips_status_desc_ar = 'في انتظار سداد رسوم الإيداع', ips_status_desc_ar_external = 'انتظار سداد رسوم الإيداع', created_by_user = 0
where code = 'WAITING_FOR_APPLICATION_FEE_PAYMENT' and application_category_id = 1;


update application.lk_application_status
set ips_status_desc_ar = 'رفض شكلي', ips_status_desc_ar_external = 'مرفوض شكلياً', created_by_user = 0
where code = 'FORMAL_REJECTION' and application_category_id = 1;


INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), 1, NULL, NULL, NULL, 0, 'تحت دراسة المسار المعجل', 'Under accelerated track study', 'UNDER_ACCELERATED_TRACK_STUDY', 'تحت الدراسة شكلياً', 'Under formal study', 1);


INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), 1, NULL, NULL, NULL, 0, 'مرفوض كطلب معجل', 'Denied as expedited application', 'DENIED_AS_EXPEDITED_APPLICATION', 'تحت الدراسة شكلياً', 'Under formal study', 1);


INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), 1, NULL, NULL, NULL, 0, 'مقبول كطلب معجل', 'Accepted as an expedited application', 'ACCEPTED_AS_AN_EXPEDITED_APPLICATION', 'تحت الدراسة شكلياً في المسار السريع', 'Under Formal study in the fast track', 1);


INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), 1, NULL, NULL, NULL, 0, 'في انتظار سداد المسار المعجل / رسوم النشر', 'Pending payment of accelerated track/publication fee', 'PENDING_PAYMENT_OF_ACCELERATED_TRACK_PUBLICATION_FEE', 'تحت الدراسة شكلياًً', 'Under formal study', 1);


INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), 1, NULL, NULL, NULL, 0, 'ساقط', 'Fallen', 'FALLEN', 'مرفوض شكلياً', 'Formally Denied', 1);


INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), 1, NULL, NULL, NULL, 0, 'الرد على تقرير الفحص الشكلي', 'Respond to the formal examination report', 'RESPOND_TO_THE_FORMAL_EXAMINATION_REPORT', 'تحت الدراسة شكلياً', 'Under formal study', 1);


INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), 1, NULL, NULL, NULL, 0, 'إرسال تقرير الفحص الشكلي', 'Send formal examination report', 'SEND_FORMAL_EXAMINATION_REPORT', 'بانتظار الرد على تقرير الفحص الشكلي', 'Waiting for formal examination report response', 1);


INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), 1, NULL, NULL, NULL, 0, 'بانتظار سداد تكاليف الفحص الموضوعي /النشر', 'Pending payment of substantive examination/publication costs', 'PENDING_PAYMENT_OF_SUBSTANTIVE_EXAMINATION_PUBLICATION_COSTS', 'انتظار سداد رسوم الفحص الموضوعي / النشر', 'Pending payment of substantive examination/publication fees', 1);


INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), 1, NULL, NULL, NULL, 0, 'قبول الفحص الشكلي (تحت التصنيف)', 'Acceptance of formal examination (under classification)', 'ACCEPTANCE_OF_FORMAL_EXAMINATION_UNDER_CLASSIFICATION', 'مقبول شكلياً', 'Formally accepted', 1);


INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), 1, NULL, NULL, NULL, 0, 'ارسال تكاليف الفحص الموضوعي / النشر', 'Submit the costs of substantive examination/publication', 'SUBMIT_THE_COSTS_OF_SUBSTANTIVE_EXAMINATION_PUBLICATION', 'انتظار سداد رسوم الفحص الموضوعي / النشر', 'Pending payment of substantive examination/publication fees', 1);


INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), 1, NULL, NULL, NULL, 0, 'معاد للفاحص الشكلي', 'Return to formal examiner', 'RETURN_TO_THE_FORMAL_EXAMINER', '', '', 1);
