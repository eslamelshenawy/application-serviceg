INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'مسودة', 'Draft', 'DRAFT', 'مسودة', 'Draft', 4);

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بانتظار سداد رسوم تقديم الطلب', 'Waiting for payment of the application fee', 'WAITING_FOR_APPLICATION_FEE_PAYMENT', 'سداد رسوم تقديم الطلب', 'Pay the application submission fee', 4);

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'جديد', 'New', 'NEW', 'جديد', 'New', 4);

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'مغلق لعدم سداد رسوم التقديم', 'Closed due to non-payment of application fees', 'CLOSED_DUE_TO_NON_PAYMENT_OF_APPLICATION_FEES', 'مغلق لعدم سداد رسوم التقديم', 'Closed due to non-payment of application fees', 4);

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تحت الفحص الشكلي', 'Under study by the formal examination official', 'UNDER_FORMAL_PROCESS', 'تحت الدراسة شكلياً', 'Under Procedure', 4);

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تحت الدراسة من مسؤول الفحص الشكلي', 'Under study by the formal examination official', 'UNDER_FORMAL_PROCESS', 'تحت الدراسة', 'Under Procedure', 4);

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تحت المراجعة من المدقق الشكلى', 'Under review by an checker auditor', 'UNDER_REVIEW_BY_AN_CHECKER_AUDITOR', 'تحت الدراسة', 'Under Process', 4);

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'معاد للفاحص الشكلي', 'Return to formal examiner', 'RETURN_TO_THE_FORMAL_EXAMINER', 'تحت الدراسة شكلياً', 'Under formal study', 4);

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'معاد لمقدم الطلب من الفحص الشكلى', 'Return to the applicant', 'INVITATION_FOR_FORMAL_CORRECTION', 'معاد لأستكمال البيانات ', 'Return to complete the data', 4);

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'مرفوض', 'Rejected', 'LACK_RESPONSE_REJECTION', 'مرفوض لعدم الرد', 'Rejected for lack of response', 4);

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'رفض شكلي', 'Rejected', 'FORMAL_REJECTION', 'مرفوض شكليا', 'Formal Rejection', 4);

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بإنتظار سداد المقابل المالي للمنح والنشر', 'Payment of financial fees for grants and publication is pending', 'PAYMENT_OF_FINANCIAL_GRANTS_FEES_IS_PENDING', 'بإنتظار سداد المقابل المالي للمنح والنشر', 'Payment of the financial fees for grants and publication is pending', 4);

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'ساقط', 'Dismissed payment of the financial fees for grants and publication', 'DISMISSED_PAYMENT_OF_GRANTS_PUBLICATION_FEES', 'ساقط لعدم سداد رسوم المنح والنشر', 'Dismissed payment of the financial fees for grants and publication', 4);

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تم النشر إلكترونياً', 'Published Electronically', 'PUBLISHED_ELECTRONICALLY', 'تم النشر إلكترونياً', 'Publication of acceptance of integrated circuits registration', 4);

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تم المنح', 'granted', 'ACCEPTANCE', 'تم المنح', 'granted', 4);

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'ابطال بموجب حكم قضائي', 'Dropping by judicial ruling', 'REVOKED_PURSUANT_TO_COURT_RULING', 'ابطال بموجب حكم قضائي', 'Dropping by judicial ruling', 4);

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'ساقط', 'Dismissed non-payment of annual financial compensation fees', 'DISMISSED_PAYMENT_OF_ANNUAL_FEES', 'ساقط لعدم سداد رسوم المقابل المالي السنوي', 'Dismissed non-payment of annual financial compensation fees', 4);