UPDATE application.lk_document_type
SET "name"='First Notify Checker Modification Documents', name_ar='إشعار تقرير الفحص الأول'
WHERE code='INTEGRATED_CIRCUITS_CHECKER_MODIFICATION_DOCUMENT';

INSERT INTO application.lk_document_type
 (id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), NULL, NULL, NULL, NULL, 0,
  'INTEGRATED_CIRCUITS', 'وثائق التصميم التخطيطي للدارات المتكاملة', NULL, 'Second Notify Checker Modification Documents', 'إشعار تقرير الفحص الثاني', 'INTEGRATED_CIRCUITS_SECOND_CHECKER_MODIFICATION_DOCUMENT');