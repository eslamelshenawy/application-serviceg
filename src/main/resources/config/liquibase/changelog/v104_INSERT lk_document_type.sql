INSERT INTO application.lk_document_type
(id, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES
((SELECT MAX(id) + 1 FROM application.lk_document_type), 0, 'APPLICATION', 'shapes', 0, 'Application Drawing', 'الرسومات', 'app_drawing');