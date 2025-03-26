INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'opposition revoke license request', 'OPPOSITION_REVOKE_LICENCE_REQUEST ', 'اعتراض علي طلب شطب ترخيص', 'اعتراض علي طلب شطب ترخيص', 'OPPOSITION_REVOKE_LICENCE_REQUEST', NULL,0);
