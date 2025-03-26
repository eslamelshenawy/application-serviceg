--liquibase formatted sql
-- changeset application-service:lk_application_category.sql


INSERT INTO application.lk_application_category
(id, is_deleted, application_category_is_active, application_category_desc_ar, application_category_desc_en,abbreviation)
VALUES (1, 0, true, 'براءة اختراع', 'Patent','PT'),
       (2, 0, true, 'نموذج صناعي', 'Industrial Design','ID'),
       (3, 0, true, 'صنف نباتي', 'Plant Varieties','PV'),
       (4, 0, true, 'دارة متكاملة', 'Integrated Circuits','IC'),
       (5, 0, true, 'العلامات التجارية', 'Trademark','TM'),
       (6, 0, true, 'حق المؤلف', 'Copyright','CR'),
       (7, 0, true, 'المؤشرات الجغرافية', 'Geographical Indication','GI'),
       (8, 0, true, 'صحيفة الملكية الفكرية', 'Publication','PB'),
       (9, 0, true, 'القضية والشكوى', 'Case and complain','CC'),
       (10, 0, true, 'رخصة', 'License','LC');

