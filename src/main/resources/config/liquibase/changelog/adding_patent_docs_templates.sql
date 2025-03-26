--liquibase formatted sql
-- changeset application-service:adding_patent_docs_templates.sql

INSERT INTO application.documents_template

(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, file_name, label_name_ar,
 label_name_en, nexuo_id, uploaded_date, category_id, lk_nexuo_user_id)

VALUES (9, 'user1', '2023-04-27 10:00:00', 'user1', '2023-04-27 10:00:00', 0, 'Summary.dotx', 'الملخص', 'Summary',
        '395f2ae3-f299-4666-b87e-4f4a9cf0dc91', '2023-04-27 10:00:00', 1, 2),

       (10, 'user2', '2023-04-27 10:10:00', 'user2', '2023-04-27 10:10:00', 0, 'Specifications_WithoutDrawing.dotx',
        'الوصف الكامل (احتواء الطلب على رسومات)', 'Specifications Without Drawing',
        'ef9a8213-e5ee-4aca-a49b-3162b8089b87', '2023-04-27 10:10:00', 1, 2),

       (11, 'user3', '2023-04-27 10:20:00', 'user3', '2023-04-27 10:20:00', 0, 'Specifications.dotx',
        'الوصف الكامل (في حال عدم احتواء الطلب على رسومات)', 'Specifications', 'c4de0613-5311-4940-81fd-a6ae7eefae9d',
        '2023-04-27 10:20:00', 1, 2),

       (12, 'user4', '2023-04-27 10:30:00', 'user4', '2023-04-27 10:30:00', 0, 'Claims.dotx', 'عناصر الحماية', 'Claims',
        'ac9d863a-6531-42ae-8142-a62ed5df39f3', '2023-04-27 10:30:00', 1, 2);