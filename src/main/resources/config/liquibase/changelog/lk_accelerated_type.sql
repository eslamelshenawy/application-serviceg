--liquibase formatted sql
-- changeset application-service:lk_accelerated_type.sql

INSERT INTO application.lk_accelerated_type (id, created_by_user, created_date, modified_by_user, modified_date,
                                             is_deleted,  name_ar, name_en, "show", application_category_id)
VALUES (1, '', '2020-10-10 00:00:00', '', '2020-10-10 00:00:00', 0,  'طلب الفحص السريع', 'Fast track examination',
        true, 1),
       (2, '', '2020-10-10 00:00:00', '', '2020-10-10 00:00:00', 0,  'طلب الفحص PPH', 'PPH examination', true, 1),
       (3, '', '2020-10-10 00:00:00', '', '2020-10-10 00:00:00', 0,  'طلب الفحص المعجل', 'Accelerated examination',
        true, 1);
