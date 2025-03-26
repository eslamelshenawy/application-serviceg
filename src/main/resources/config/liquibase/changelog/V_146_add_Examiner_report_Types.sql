
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'EXAMINER_REPORT_1', 'EXAMINER_REPORT_1', 'تقرير الفخص الموضوعي الاول', 'تقرير الفخص الموضوعي الاول', 'EXAMINER_REPORT_1', NULL,0);

INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'EXAMINER_REPORT_2', 'EXAMINER_REPORT_2', 'تقرير الفخص الموضوعي الثاني', 'تقرير الفخص الموضوعي الثاني', 'EXAMINER_REPORT_2', NULL,0);



