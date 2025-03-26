INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select (max(id) + 1) from application.lk_document_type), NULL, NULL, NULL, NULL, 0, 'APPLICATION', 'إشعار إسقاط الطلب', NULL, 'DroppedRequestReport', 'إشعار إسقاط الطلب', 'DROPPED_REQUEST_REPORT');
INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select (max(id) + 1) from application.lk_document_type), NULL, NULL, NULL, NULL, 0, 'APPLICATION', 'إشعار رفض الطلب', NULL, 'ObjectionRequestReport', 'إشعار رفض الطلب', 'OBJECTION_REQUEST_REPORT');
INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select (max(id) + 1) from application.lk_document_type), NULL, NULL, NULL, NULL, 0, 'APPLICATION', 'إشعار تقرير الفحص الشكلي', NULL, 'Noticeofformalinspectionreport', 'إشعار تقرير الفحص الشكلي', 'NOTICE_OF_FORMAL_CHECKER_REPORT');