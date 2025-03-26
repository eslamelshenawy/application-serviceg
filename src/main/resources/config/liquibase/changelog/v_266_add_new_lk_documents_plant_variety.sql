INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'A document related to the previous statement for vegetarian', 'VEGETARIAN_DOCUMENT_PERVIOUS_TEST', 'إرفاق المستندات المتعلقة بالكشف السابق', 'إرفاق المستندات المتعلقة بالكشف السابق', 'PLANT_VARIETIES', NULL,0);
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Main image of the plant variety', 'MAIN_PHOTO_PLANT_VEGETARIAN', 'صورة رئيسية للصنف النباتي', 'صورة رئيسية للصنف النباتي', 'PLANT_VARIETIES', NULL,0);
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Dus Testing Result Attachment', 'DUS_DOCUMENT_TESTING', 'إرفاق نتيجة الاختبار dus', 'إرفاق نتيجة الاختبار dus', 'PLANT_VARIETIES', NULL,0);
