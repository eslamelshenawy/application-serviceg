--liquibase formatted sql
-- changeset application-service:update_size_field.sql


UPDATE application.documents_template
SET size =
        CASE
            WHEN file_name = 'Summary.dotx' THEN 24
            WHEN file_name = 'Specifications_WithoutDrawing.dotx' THEN 28
            WHEN file_name = 'Claims.dotx' THEN 23
            WHEN file_name = 'Specifications.dotx' THEN 25
            ELSE size
            END
WHERE file_name IN ('Claims.dotx', 'Specifications_WithoutDrawing.dotx','Summary.dotx','Specifications.dotx');

UPDATE application.documents_template
SET label_name_ar =  'الوصف الكامل (في حال عدم احتواء الطلب على رسومات)'
where id = 10;

UPDATE application.documents_template
SET label_name_ar =  'الوصف الكامل (احتواء الطلب على رسومات)'
where id = 11;