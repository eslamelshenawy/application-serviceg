INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES((SELECT max(id)+1 FROM application.lk_support_service_request_status), 'ACCEPTED', 'قبول طلب الإلتماس', ' Accepted', 'قبول طلب الإلتماس', 'Accepted');
