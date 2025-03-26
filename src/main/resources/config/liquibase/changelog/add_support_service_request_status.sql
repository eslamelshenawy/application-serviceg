INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES((select (max(id) + 1) from application.lk_support_service_request_status), 'PAY_PUBLICATION_FEES',
       'سداد رسوم النشر', 'Pay Publication Fees', 'سداد رسوم النشر', 'Pay Publishing Fees');
