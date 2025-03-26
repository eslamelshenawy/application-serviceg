INSERT INTO application.lk_document_type(id, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), 0, 'PRIORITY_PETITION_REQUEST_ACCEPTANCE', 'اشعار قبول طلب التماس تصحيح او اضافه اسبقيه', NULL,
       'PriorityPetitionRequestAcceptance', 'اشعار قبول طلب التماس تصحيح او اضافه اسبقيه', 'PriorityPetitionRequestAcceptance');

INSERT INTO application.lk_document_type(id, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), 0, 'PRIORITY_PETITION_REQUEST_REJECTION', 'اشعار رفض طلب التماس تصحيح او اضافه اسبقيه', NULL,
       'PriorityPetitionRequestRejection', 'اشعار رفض طلب التماس تصحيح او اضافه اسبقيه', 'PriorityPetitionRequestRejection');

INSERT INTO application.lk_document_type(id, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), 0, 'PRIORITY_PETITION_REQUEST_CONDITIONAL_REJECTION', 'اشعار رفض مرتقب لطلب التماس تصحيح او اضافه اسبقيه', NULL,
       'PriorityPetitionRequestConditionalRejection', 'اشعار رفض مرتقب لطلب التماس تصحيح او اضافه اسبقيه', 'PriorityPetitionRequestConditionalRejection');

INSERT INTO application.lk_document_type(id, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), 0, 'PRIORITY_PETITION_REQUEST_EXPIRY_REJECTION', 'اشعار رفض طلب التماس تصحيح او اضافه اسبقيه', NULL,
       'PriorityPetitionRequestExpiryRejection', 'اشعار رفض طلب التماس تصحيح او اضافه اسبقيه', 'PriorityPetitionRequestExpiryRejection');

INSERT INTO application.lk_document_type(id, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), 0, 'PRIORITY_MODIFY_REQUEST_ACCEPTANCE', 'اشعار قبول طلب تصحيح او اضافه اسبقيه', NULL,
       'PriorityModifyRequestAcceptance', 'اشعار قبول طلب تصحيح او اضافه اسبقيه', 'PriorityModifyRequestAcceptance');

INSERT INTO application.lk_document_type(id, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), 0, 'PRIORITY_MODIFY_REQUEST_REJECTION', 'اشعار رفض طلب تصحيح او اضافه اسبقيه', NULL,
       'PriorityModifyRequestRejection', 'اشعار رفض طلب تصحيح او اضافه اسبقيه', 'PriorityModifyRequestRejection');

INSERT INTO application.lk_document_type(id, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), 0, 'PRIORITY_MODIFY_REQUEST_CONDITIONAL_REJECTION', 'اشعار رفض مرتقب لطلب تصحيح او اضافه اسبقيه', NULL,
       'PriorityModifyRequestConditionalRejection', 'اشعار رفض مرتقب لطلب تصحيح او اضافه اسبقيه', 'PriorityModifyRequestConditionalRejection');

INSERT INTO application.lk_document_type(id, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), 0, 'PRIORITY_MODIFY_REQUEST_EXPIRY_REJECTION', 'اشعار رفض طلب تصحيح او اضافه اسبقيه', NULL,
       'PriorityModifyRequestExpiryRejection', 'اشعار رفض طلب تصحيح او اضافه اسبقيه', 'PriorityModifyRequestExpiryRejection');
