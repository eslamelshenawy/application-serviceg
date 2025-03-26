INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES((select max(id) + 1 from application.lk_support_service_request_status), 'HAVE_SIMILAR', 'يوجد مشابه', 'Have Similar', 'يوجد مشابه', 'Have Similar');

INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES((select max(id) + 1 from application.lk_support_service_request_status), 'NOT_SIMILAR', ' لا يوجد مطابق', 'Not Similar', 'لا يوجد مطابق', 'Not Similar');

