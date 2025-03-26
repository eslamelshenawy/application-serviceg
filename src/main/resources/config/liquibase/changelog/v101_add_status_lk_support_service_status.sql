INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en)
VALUES((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'OPPOSITION_WATING', 'إنتظار الاعتراض', 'oppostion wating');
INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en)
VALUES((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'LICENSE_REVOKED', 'ترخيص مشطوب', 'License Revoked');
INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en)
VALUES((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'UNDER_OPPOSITION', 'قيد الاعتراض', 'under opposition');
INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en)
VALUES((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'WITHDRAWAL', 'انسحاب ', ' withdrawal');