INSERT INTO application.lk_document_type
(id, is_deleted, category, description, doc_order, "name", name_ar, code)
SELECT MAX(id) + 1, 0, 'APPLICATION', 'المستندات الداعمة المتعلقة بالكشف للعموم', 0, 'Supporting documents related to revealed to public', 'المستندات الداعمة المتعلقة بالكشف للعموم', 'Supporting documents related to revealed to public'
FROM application.lk_document_type;