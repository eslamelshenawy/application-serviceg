INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en)
VALUES((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'PENDING', 'معلق ', ' pending');

ALTER TABLE application.opposition_revoke_licence_request DROP COLUMN opposition_type;

