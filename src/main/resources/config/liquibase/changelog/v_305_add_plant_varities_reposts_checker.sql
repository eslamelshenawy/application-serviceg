INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id+1) from application.lk_document_type), NULL, NULL, NULL, NULL, 0, 'PLANT_VARIETIES', 'وثائق المستنبطين للتصنيفات النباتية', NULL, 'Second Notify Checker Modification Documents', 'إشعار تقرير الفحص الشكلي الثاني', 'PLANT_VARIETIES_SECOND_CHECKER_MODIFICATION_DOCUMENT');
INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id+1) from application.lk_document_type), NULL, NULL, NULL, NULL, 0, 'PLANT_VARIETIES', 'وثائق المستنبطين للتصنيفات النباتية', NULL, 'Notify Checker Rejection Documents', 'إشعار رفض الطلب', 'PLANT_VARIETIES_CHECKER_REJECTION_DOCUMENT');
INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id+1) from application.lk_document_type), NULL, NULL, NULL, NULL, 0, 'PLANT_VARIETIES', 'وثائق المستنبطين للتصنيفات النباتية', NULL, 'First Notify Checker Modification Documents', 'إشعار تقرير الفحص الشكلي الأول', 'PLANT_VARIETIES_CHECKER_MODIFICATION_DOCUMENT');