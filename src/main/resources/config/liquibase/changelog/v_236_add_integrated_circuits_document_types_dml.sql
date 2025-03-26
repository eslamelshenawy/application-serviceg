INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), NULL, NULL, NULL, NULL, 0,
 'INTEGRATED_CIRCUITS', 'وثائق التصميم التخطيطي للدارات المتكاملة', NULL, 'Integrated Circuits Front Shape', 'وثائق الدارات المتكاملة الشكل الامامي', 'INTEGRATED_CIRCUITS_FRONT_SHAPE_DOCUMENT');

INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), NULL, NULL, NULL, NULL, 0,
'INTEGRATED_CIRCUITS', 'وثائق التصميم التخطيطي للدارات المتكاملة', NULL, 'Integrated Circuits Back Shape', 'وثائق الدارات المتكاملة الشكل الخلفي', 'INTEGRATED_CIRCUITS_BACK_SHAPE_DOCUMENT');

INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), NULL, NULL, NULL, NULL, 0,
'INTEGRATED_CIRCUITS', 'وثائق التصميم التخطيطي للدارات المتكاملة', NULL, 'Integrated Circuits Commercial Exploitation', 'وثائق الاستغلال التجاري للدارات المتكاملة', 'INTEGRATED_CIRCUITS_COMMERCIAL_EXPLOITATION_DOC');

INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), NULL, NULL, NULL, NULL, 0,
'INTEGRATED_CIRCUITS', 'وثائق التصميم التخطيطي للدارات المتكاملة', NULL, 'Integrated Circuits Legal Document', 'الوثائق القانونيه للدارات المتكاملة', 'INTEGRATED_CIRCUITS_LEGAL_DOCUMENT');

INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), NULL, NULL, NULL, NULL, 0,
'INTEGRATED_CIRCUITS', 'وثائق التصميم التخطيطي للدارات المتكاملة', NULL, 'Integrated Circuits Waiver Document', 'وثائق تنازل الدارات المتكاملة', 'INTEGRATED_CIRCUITS_WAIVER_DOCUMENT');

INSERT INTO application.lk_document_type
 (id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), NULL, NULL, NULL, NULL, 0,
  'INTEGRATED_CIRCUITS', 'وثائق التصميم التخطيطي للدارات المتكاملة', NULL, 'Notify Checker Modification Documents', 'إشعار تقرير الفحص للتصحيح', 'INTEGRATED_CIRCUITS_CHECKER_MODIFICATION_DOCUMENT');

INSERT INTO application.lk_document_type
 (id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), NULL, NULL, NULL, NULL, 0,
  'INTEGRATED_CIRCUITS', 'وثائق التصميم التخطيطي للدارات المتكاملة', NULL, 'Notify Checker Rejection Documents', 'إشعار رفض الطلب', 'INTEGRATED_CIRCUITS_CHECKER_REJECTION_DOCUMENT');

INSERT INTO application.lk_document_type
 (id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), NULL, NULL, NULL, NULL, 0,
  'INTEGRATED_CIRCUITS', 'وثائق التصميم التخطيطي للدارات المتكاملة', NULL, 'Notify Checker Grant Documents', 'إشعار بسداد المقابل المالي للمنح', 'INTEGRATED_CIRCUITS_CHECKER_GRANT_DOCUMENT');