INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'license request', 'LICENSING_REGISTRATION ', 'طلب ترخيص', 'طلب ترخيص', 'LICENSING_REGISTRATION', NULL,0);


INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'revoke license request', 'REVOKE_LICENCE_REQUEST ', 'شطب طلب ترخيص', 'شطب طلب ترخيص', 'REVOKE_LICENCE_REQUEST', NULL,0);
