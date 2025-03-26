INSERT INTO application.lk_document_type
 (id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select max(id)+1 from application.lk_document_type), NULL, NULL, NULL, NULL, 0,
  'INTEGRATED_CIRCUITS', 'وثائق التصميم التخطيطي للدارات المتكاملة', NULL, 'Integrated Circuits Grant Certificate Document', 'وثائق شهادات المنح للدارات المتكاملة', 'INTEGRATED_CIRCUITS_GRANT_CERTIFICATE_DOCUMENT');