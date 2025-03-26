INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id+1) from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'معاد للفاحص الشكلي', 'Returning To Formal Examiner', 'RETURNING_TO_FORMAL_EXAMINER', 'تحت الفحص الشكلي', 'Under Formal Process', 3);



UPDATE application.lk_application_status
SET ips_status_desc_ar_external='تحت الفحص الشكلي', ips_status_desc_en_external='Under Formal Process'
WHERE code = 'UNDER_REVIEW_BY_AN_CHECKER_AUDITOR' and application_category_id=3;


UPDATE application.lk_application_status
SET ips_status_desc_ar_external='تحت الفحص الشكلي', ips_status_desc_en_external='Under Formal Process'
WHERE code = 'UNDER_FORMAL_PROCESS' and application_category_id=3;


INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id+1) from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بانتظار الرد علي تقرير الفحص الشكلي', 'Waiting For Reply Of Formal Process Report', 'WAITING_FOR_REPLY_OF_FORMAL_PROCESS_REPORT', 'بانتظار الرد علي تقرير الفحص الشكلي', 'Waiting For Reply Of Formal Process Report', 3);



INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id+1) from application.lk_application_status),NULL, NULL, NULL, NULL, 0, 'الرد علي تقرير الفحص الشكلي مع الاعتراض', 'Reply To Formal Process With Opposition', 'REPLY_TO_FORMAL_PROCESS_WITH_OPPOSITION', 'تحت الفحص الشكلي', 'Under Formal Process', 3);



INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id+1) from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'مرفوض شكليا', 'Formally Rejected', 'FORMALLY_REJECTED', 'مرفوض شكليا', 'Formally Rejected', 3);


INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id+1) from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بانتظار سداد تكاليف الفحص الموضوعي /النشر', 'Waiting For Substantive Examination And Publication', 'WAITING_FOR_SUBSTANTIVE_EXAMINATION_AND_PUBLICATION', 'بانتظار سداد تكاليف الفحص الموضوعي و النشر', 'Waiting For Substantive Examination And Publication', 3);








